/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.l4b;

import java.math.BigDecimal;
import java.time.LocalDate;
import jsoftware.com.jpaymentlib.model.dto.PaymentImportDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentRulersDTO;
import jsoftware.com.jpaymentlib.model.dto.wrp.PaymentSpecificationWrapperDTO;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jpaymentlib.util.FuncBusiness;

/**
 * Orquestador principal del motor de cobros (Facade). Coordina la aplicación
 * secuencial de recargos, descuentos y subsidios sobre los importes financieros
 * procesados por la librería de pagos.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-07-18
 * @version 1.6
 */
public class Payment {

    private final Subsidy lbl_subsidy;
    private final Discount lbl_discount;
    private final Surcharge lbl_surcharge;
    private final PaymentSpecificationWrapperDTO dto;
    private boolean variable;
    private BigDecimal sub_total;
    private BigDecimal discount;
    private BigDecimal surcharge;
    private BigDecimal total_amount;
    private StringBuilder observations;

    public Payment(PaymentSpecificationWrapperDTO dto) {
        this.dto = dto;
        if (dto != null && dto.getImports() != null) {
            this.variable = dto.getImports().isvariable();
        } else {
            this.variable = false;
        }
        this.lbl_subsidy = new Subsidy();
        this.lbl_discount = new Discount();
        this.lbl_surcharge = new Surcharge();
        this.sub_total = BigDecimal.ZERO;
        this.discount = BigDecimal.ZERO;
        this.surcharge = BigDecimal.ZERO;
        this.total_amount = BigDecimal.ZERO;
    }

    public Payment() {
        this(null);
    }

    public BigDecimal calculation() throws PaymentException {
        if (Func.isNull(dto) || Func.isNull(dto.getImports()) || Func.isNull(dto.getRulers())) {
            observations.append("DETALLE NULO");
            throw new PaymentException(1, "OBJETOS NO INICIALIZADOS");
        }
        PaymentImportDTO imports = dto.getImports();
        BigDecimal unit_amount = imports.getAmount();

        // VALIDACIÓN DE IMPORTE INICIAL
        if (unit_amount.compareTo(BigDecimal.ZERO) == 0) {
            observations.append("SUB TOTAL EN CEROS");
            throw new PaymentException(1, "SUB TOTAL EN CEROS: 0.00");
        }
        if (unit_amount.compareTo(BigDecimal.ZERO) < 0) {
            observations.append("SUB TOTAL EN NEGATIVO");
            throw new PaymentException(1, "SUB TOTAL NEGATIVO: " + unit_amount.toPlainString());
        }

        PaymentRulersDTO rulers = dto.getRulers();
        observations = new StringBuilder();

        // CÁLCULO DE BASE SEGÚN UNIDADES
        variable = imports.isvariable();
        BigDecimal items = FuncBusiness.safeBigDecimal(imports.getUnits());

        // SI ES VARIABLE EL CALCULO BASE SE MULTIPLICA POR LOS ITEMS COLOCADOS
        sub_total = FuncBusiness.variable(unit_amount, variable, items);
        total_amount = sub_total;

        // 1. APLICACIÓN DE SUBSIDIO
        if (rulers.isApply_rulers() && rulers.isApply_subsidy()) {
            discount = discount.add(imports.getSubsidy());
            lbl_subsidy.setApply(rulers.isApply_subsidy());
            total_amount = lbl_subsidy.getTotal(total_amount, imports.getSubsidy());
            observations.append("APLICACION DE SUBSIDIO,");
        }

        // 2. APLICACIÓN DE DESCUENTOS
        if (rulers.isApply_rulers() && rulers.isApply_discount()) {
            discount = discount.add(imports.getDiscount());
            lbl_discount.setApply(rulers.isApply_discount());
            total_amount = lbl_discount.getTotal(total_amount, imports.getDiscount());
            observations.append("APLICACION DE DESCUENTO,");
        }

        int current_month = dto.getCurrent_month();
        // FECHA ACTUAL
        LocalDate now = LocalDate.now();

        // EVALUACIÓN DE VENCIMIENTO PARA RECARGOS
        boolean apply_pay_day = false;
        if (Func.isNotNull(rulers.getPayday()) && rulers.getPayday() > 0) {
            int pay_day = rulers.getPayday();
            // Un mes genera recargo si:
            // A) El mes a pagar es anterior al mes actual (ej: Enero en curso de Noviembre).
            // B) Es el mes actual (en curso), pero ya pasó el día límite de pago (ej: estamos a 7 y el payday era 1).
            boolean is_past_month = current_month < now.getMonthValue();
            boolean is_current_month_expired = (current_month == now.getMonthValue()) && (now.getDayOfMonth() > pay_day);
            apply_pay_day = pay_day > 0 && (is_past_month || is_current_month_expired);
        }

        // 3. APLICACIÓN DE RECARGOS
        if (apply_pay_day && rulers.isApply_surcharge()) {
            surcharge = surcharge.add(imports.getSurcharge());
            lbl_surcharge.setApply(rulers.isApply_surcharge());
            total_amount = lbl_surcharge.getTotal(total_amount, imports.getSurcharge());
            observations.append("APLICACION DE RECARGOS,");
        }

        // REDONDEO FINAL CONSOLIDADO (Evita errores acumulativos por centavos)
        total_amount = FuncBusiness.round(total_amount, rulers.isRound(), rulers.isRound_up());

        // VALIDACIÓN DE IMPORTE FINAL
        if (total_amount.compareTo(BigDecimal.ZERO) == 0) {
            observations.append("SALDO EN CEROS");
            throw new PaymentException(1, "TOTAL EN CEROS: 0.00");
        }
        if (total_amount.compareTo(BigDecimal.ZERO) < 0) {
            observations.append("SALDO NEGATIVO");
            throw new PaymentException(1, "TOTAL NEGATIVO: " + total_amount.toPlainString());
        }
        //SIN COMPLICACIONES EN EL CALCULO
        observations.append("S/C");
        return total_amount;
    }

    public boolean isVariable() {
        return variable;
    }

    public void setVariable(boolean variable) {
        this.variable = variable;
    }

    public BigDecimal getSub_total() {
        return sub_total;
    }

    public void setSub_total(BigDecimal sub_total) {
        this.sub_total = sub_total;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public StringBuilder getObservations() {
        return observations;
    }
}
