package jsoftware.com.jpaymentlib.model.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;
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
public class PaymentHeaderService {

    private final PaymentDetailService service;
    private final PaymentHeaderDAO payment_header_dao;

    public PaymentHeaderService(boolean flag_dev, String name_module) {
        // SOLUCIÓN CRÍTICA 2: Inicializar el servicio dependiente para evitar NullPointerException
        this.service = new PaymentDetailService(flag_dev, name_module);
        this.payment_header_dao = new PaymentHeaderDAO(flag_dev, name_module);
    }

    /**
     * Método encargado de realizar la sumatoria del pago desglosado y generar
     * la cabecera de pago.
     *
     * @param connection Conexión a la base de datos para operaciones futuras
     * @param detail_list Lista de detalles de pago a procesar
     * @return Optional con el PaymentWrapper empaquetado
     * @throws PaymentException Si los importes acumulados son cero
     */
    public PaymentHeaderDTO getPaymentHeader(JDBConnection connection, List<PaymentDetailDTO> detail_list) throws PaymentException {
        if (detail_list == null || detail_list.isEmpty()) {
            throw new PaymentException(2, "DESGLOSE DE PAGO NO GENERADO");
        }
        BigDecimal sub_total = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal total_surcharge = BigDecimal.ZERO;
        BigDecimal total_amount = BigDecimal.ZERO;

        // 1. ACUMULACIÓN ARITMÉTICA SEGURA DE LOS DETALLES
        for (PaymentDetailDTO i : detail_list) {
            sub_total = sub_total.add(parseBigDecimal(i.getSubtotal()));
            discount = discount.add(parseBigDecimal(i.getDiscount()));
            total_surcharge = total_surcharge.add(parseBigDecimal(i.getSurcharge()));
            total_amount = total_amount.add(parseBigDecimal(i.getTotalAmount()));
        }

        // 2. VALIDACIÓN DE IMPORTES (compareTo ignora diferencia entre 0 y 0.00)
        if (sub_total.compareTo(BigDecimal.ZERO) == 0) {
            throw new PaymentException(1, "SUBTOTAL EN 0.00");
        }
        if (total_amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new PaymentException(2, "TOTAL EN 0.00");
        }
        // 2. VALIDACIÓN DE IMPORTES (compareTo ignora diferencia entre 0 y 0.00)
        if (sub_total.compareTo(BigDecimal.ZERO) < 0) {
            throw new PaymentException(3, "SUBTOTAL NEGATIVO: " + sub_total.toPlainString());
        }
        if (total_amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new PaymentException(4, "TOTAL NEGATIVO" + total_amount.toPlainString());
        }

        // 3. PREPARACIÓN DEL ENCABEZADO CON ESCALA MONETARIA (2 DECIMALES)
        PaymentHeaderDTO header = new PaymentHeaderDTO();
        header.put("sub_total", sub_total.setScale(2, RoundingMode.HALF_UP).toPlainString());
        header.put("discount", discount.setScale(2, RoundingMode.HALF_UP).toPlainString());
        header.put("surcharge", total_surcharge.setScale(2, RoundingMode.HALF_UP).toPlainString());
        header.put("total_amount", total_amount.setScale(2, RoundingMode.HALF_UP).toPlainString());
        return header;
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
