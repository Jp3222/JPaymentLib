package jsoftware.com.jpaymentlib.model.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import jsoftware.com.jpaymentlib.model.dao.PaymentHeaderDAO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentHeaderDTO;
import jsoftware.com.jpaymentlib.model.dto.wrp.PaymentWrapper;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.util.FuncLogs;

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
     * Metodo encargado de generar los importes en la cabezera y el desglose del
     * pago de un tramite, los insert y datos de auditoria y identidad, deberan
     * ser generados en la capa externa
     *
     * @param connection
     * @param concept_list
     * @return
     */
    public Optional<PaymentWrapper> getPaymentHeader(JDBConnection connection, List<Integer> concept_list) {
        // Si no hay conceptos que procesar, abortamos inmediatamente de forma segura
        if (concept_list == null || concept_list.isEmpty()) {
            return Optional.empty();
        }
        PaymentHeaderDTO header = null;
        try {
            // Se recupera la lista de detalles calculada por el validador de especificaciones
            List<PaymentDetailDTO> detail_list = service.getDetailList(connection, concept_list);

            if (detail_list.isEmpty()) {
                return Optional.empty();
            }

            BigDecimal sub_total = BigDecimal.ZERO;
            BigDecimal discount = BigDecimal.ZERO;
            BigDecimal total_surcharge = BigDecimal.ZERO;
            BigDecimal total_amount = BigDecimal.ZERO;

            // 1. ACUMULACIÓN ARITMÉTICA DE LOS DETALLES
            for (PaymentDetailDTO i : detail_list) {
                // SOLUCIÓN CRÍTICA 3: Extraer del Map interno usando las llaves del DTO
                // SOLUCIÓN CRÍTICA 1: Reasignar el resultado del .add() debido a la inmutabilidad de BigDecimal
                sub_total = sub_total.add(new BigDecimal(i.getSubtotal()));
                discount = discount.add(new BigDecimal(i.getDiscount()));
                total_surcharge = total_surcharge.add(new BigDecimal(i.getSurcharge()));
                total_amount = total_amount.add(new BigDecimal(i.getTotalAmount()));
            }

            // 2. PREPARACIÓN DEL ENCABEZADO (Mapeo manual flexible en String)
            header = new PaymentHeaderDTO();
            header.put("sub_total", sub_total.toPlainString());
            header.put("discount", discount.toPlainString());
            header.put("surcharge", total_surcharge.toPlainString());
            header.put("total_amount", total_amount.toPlainString());

            PaymentWrapper wrp = new PaymentWrapper(header, detail_list);
            return Optional.of(wrp);
            // TODO: Aquí invocarás a tu PaymentHeaderDAO para insertar el 'header', 
            // recuperar el ID generado, asignarlo a los 'details' e insertarlos en lote (Batch).
            // Operación realizada con éxito
        } catch (PaymentException | SQLException e) {
            //FuncLogs.logError(null, getClass(), e, "PaymentLib", "getPaymentHeader", e.getMessage());
            e.printStackTrace(System.out);
        }
        return Optional.empty();
    }

    public boolean save(JDBConnection connection, PaymentWrapper pw) {
        boolean res = false;
        try {
            res = payment_header_dao.insert(connection, pw.getHeader());
            if (!res) {
                throw new PaymentException(1, "LA CABEZERA DEL PAGO NO PUDO REGISTRARSE");
            }
            res = service.save(connection, pw.getDetail());
            if (!res) {
                throw new PaymentException(1, "LA CABEZERA DEL PAGO NO PUDO REGISTRARSE");
            }
        } catch (SQLException | PaymentException e) {
            e.printStackTrace(System.out);
        }
        return true;
    }
}
