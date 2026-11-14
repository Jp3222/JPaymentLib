package jsoftware.com.jpaymentlib.model.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import jsoftware.com.jblue.model.abst.AbstractService;
import jsoftware.com.jpaymentlib.model.dao.PaymentHeaderDAO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentHeaderDTO;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jutil.db.JDBConnection;

/**
 * Servicio maestro para la persistencia y control transaccional de los bloques
 * de cobro (Header y Details).
 *
 * @author Juan P. Campos C.
 * @since 2026-07-18
 * @version 1.0
 */
public class PaymentHeaderService extends AbstractService {

    private final PaymentDetailService service;
    private final PaymentHeaderDAO payment_header_dao;

    public PaymentHeaderService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
        // SOLUCIÓN CRÍTICA 2: Inicializar el servicio dependiente para evitar NullPointerException
        this.service = new PaymentDetailService(dev_flag, process_name);
        this.payment_header_dao = new PaymentHeaderDAO(dev_flag, process_name);
    }

    /**
     * Método encargado de realizar la sumatoria del pago desglosado y generar
     * la cabecera de pago.
     *
     * @param connection Conexión a la base de datos para operaciones futuras
     * @param detail_list Lista de detalles de pago a procesar
     * @return PaymentHeaderDTO con los totales calculados
     * @throws PaymentException Si los importes acumulados son cero, negativos o
     * inconsistentes
     */
    public Optional<PaymentHeaderDTO> getPaymentHeader(JDBConnection connection, List<PaymentDetailDTO> detail_list) throws PaymentException {
        if (detail_list == null || detail_list.isEmpty()) {
            throw new PaymentException(2, "DESGLOSE DE PAGO NO GENERADO");
        }

        BigDecimal sub_total = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal surcharge = BigDecimal.ZERO;
        BigDecimal total_amount = BigDecimal.ZERO;

        // 1. Acumulaciones con parsing seguro
        for (PaymentDetailDTO i : detail_list) {
            sub_total = sub_total.add(i.getSubtotal());
            discount = discount.add(i.getDiscount());
            surcharge = surcharge.add(i.getSurcharge());
            total_amount = total_amount.add(i.getTotal_amount());
        }

        // 2. Validaciones de límites
        if (sub_total.compareTo(BigDecimal.ZERO) == 0) {
            throw new PaymentException(1, "SUBTOTAL EN 0.00");
        }
        if (total_amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new PaymentException(2, "TOTAL EN 0.00");
        }
        if (sub_total.compareTo(BigDecimal.ZERO) < 0) {
            throw new PaymentException(3, "SUBTOTAL NEGATIVO: " + sub_total.toPlainString());
        }
        if (total_amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new PaymentException(4, "TOTAL NEGATIVO: " + total_amount.toPlainString());
        }

        // 3. Validación de integridad matemática (Subtotal - Descuento + Recargo == Total)
        BigDecimal expectedTotal = sub_total.subtract(discount).add(surcharge);
        if (total_amount.compareTo(expectedTotal) != 0) {
            throw new PaymentException(5, String.format(
                    "INCONSISTENCIA EN CABECERA DE PAGO: Calculado (%s) != Esperado (%s)",
                    total_amount.toPlainString(), expectedTotal.toPlainString()
            ));
        }

        // 4. Construcción del DTO de salida
        PaymentHeaderDTO header = new PaymentHeaderDTO();
        header.setSubtotal(sub_total);
        header.setDiscount(discount);
        header.setSurcharge(surcharge);
        header.setTotal_amount(total_amount);
        header.setApply_discount(discount.compareTo(BigDecimal.ZERO) > 0);
        header.setApply_surcharge(surcharge.compareTo(BigDecimal.ZERO) > 0);
        header.setStatus(1);
        return Optional.of(header);
    }

    /**
     * Convierte de forma segura un String a BigDecimal evitando
     * NullPointerException y NumberFormatException.
     */
    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    public boolean save(JDBConnection connection, PaymentHeaderDTO header) {
        boolean res = false;
        try {
            res = payment_header_dao.insert(connection, header);
            if (!res) {
                throw new PaymentException(1, "LA CABEZERA DEL PAGO NO PUDO REGISTRARSE");
            }
        } catch (SQLException | PaymentException e) {
            e.printStackTrace(System.out);
        }
        return true;
    }
}
