package jsoftware.com.jpaymentlib.model.l4b;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jsoftware.com.jpaymentlib.model.dto.PaymentImportDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentRulersDTO;
import jsoftware.com.jpaymentlib.model.dto.wrp.PaymentSpecificationWrapperDTO;
import jsoftware.com.jpaymentlib.util.FuncBusiness;

/**
 * Orquestador principal del motor de cobros (Facade). Coordina la aplicación
 * secuencial de recargos, descuentos y subsidios sobre los importes financieros
 * procesados por la librería de pagos.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-07-18
 * @version 1.3
 */
public class Payment {

    private final Subsidy subsidy;
    private final Discount discount;
    private final Surcharge surcharge;
    private final PaymentSpecificationWrapperDTO dto;
    private boolean variable;
    private String sub_total;
    private String s_discount;
    private String s_surcharge;
    private String total_amount;

    /**
     * Inicializa los submodelos analíticos de cálculo aritmético y el contexto
     * del DTO.
     */
    public Payment(PaymentSpecificationWrapperDTO dto) {
        this.dto = dto;
        this.variable = FuncBusiness.isApply(dto.getImports().getIsVariable());
        this.subsidy = new Subsidy();
        this.discount = new Discount();
        this.surcharge = new Surcharge();
    }

    /**
     * Ejecuta la matriz de reglas financieras sobre el importe base utilizando
     * las variables de estado de la clase.
     *
     * @return El importe total neto liquidado en formato numérico plano.
     */
    public String calculation() {
        PaymentImportDTO imports = dto.getImports();
        PaymentRulersDTO rulers = dto.getRulers();

        // 1. Determinar el Monto Base inicial (Evaluando si es tarifa fija o variable)
        sub_total = subtotal();
        total_amount = sub_total;
        s_discount = "0.00"; // Inicializado en cero para permitir acumulación segura
        LocalDateTime now = LocalDateTime.now();

        // 2. Evaluación segura del día límite de pago (Evita NumberFormatException)
        String paydayStr = rulers.getPayday();
        int payday = (paydayStr != null && !paydayStr.trim().isEmpty()) ? Integer.parseInt(paydayStr.trim()) : 0;

        // 3. Aplicación de Recargo por vencimiento cronológico
        if (payday > 0 && now.getDayOfMonth() >= payday) {
            surcharge.setApply(rulers.getApplySurcharge());
            if (surcharge.isApply()) {
                total_amount = surcharge.getTotal(total_amount, imports.getSurcharge());
                s_surcharge = imports.getSurcharge();
            } else {
                s_surcharge = "0.00";
            }
        } else {
            s_surcharge = "0.00";
        }

        // 4. Aplicación de Descuento comercial o por campaña
        discount.setApply(rulers.getApplyDiscount());
        if (FuncBusiness.isApply(rulers.getApplyRulers()) && discount.isApply()) {
            total_amount = discount.getTotal(total_amount, imports.getDiscount());
            s_discount = imports.getDiscount();
        }

        // 5. Aplicación de Subsidio gubernamental o preferencial
        subsidy.setApply(rulers.getApplySubsidy());
        if (FuncBusiness.isApply(rulers.getApplyRulers()) && subsidy.isApply()) {
            total_amount = subsidy.getTotal(total_amount, imports.getSubsidy());
            // Se acumula el monto del subsidio al beneficio total de s_discount
            s_discount = FuncBusiness.surcharge(s_discount, imports.getSubsidy()).toPlainString();
        }

        return FuncBusiness.round(total_amount, rulers.getRound(), rulers.getRounUp()).toPlainString();
    }

    /**
     * Calcula el subtotal inicial procesando si el concepto es de costo
     * variable.
     */
    private String subtotal() {
        PaymentImportDTO imports = dto.getImports();

        BigDecimal v = FuncBusiness.variable(
                imports.getAmount(),
                imports.getIsVariable(),
                imports.getUnits()
        );

        return v.toPlainString();
    }

    // --- Getters de Estado ---
    public Discount getDiscount() {
        return discount;
    }

    public Subsidy getSubsidy() {
        return subsidy;
    }

    public Surcharge getSurcharge() {
        return surcharge;
    }

    public boolean isVariable() {
        return variable;
    }

    public String getSubTotal() {
        return sub_total;
    }

    public String getSDiscount() {
        return s_discount;
    }

    public String getSSurcharge() {
        return s_surcharge;
    }

    public String getTotalAmount() {
        return total_amount;
    }
}
