package jsoftware.com.jpaymentlib.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 * Data Access Object para la gestión de partidas / desgloses de pago
 * (pym_payment_detail).
 *
 * @author Juan Pablo Campos Casasanero
 */
public class PaymentDetailDAO extends AbstractDAO {

    public PaymentDetailDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    /**
     * Inserta un lote (batch) de detalles de pago dentro de la misma
     * transacción.
     *
     * @param connection Conexión activa provista por la transacción.
     * @param list Lista de DTOs a insertar.
     * @return true si todos los registros fueron procesados exitosamente.
     * @throws SQLException Si ocurre un error durante la inserción en BD.
     */
    public boolean insert(JDBConnection connection, List<PaymentDetailDTO> list) throws SQLException {
        if (list == null || list.isEmpty()) {
            return false;
        }

        String query = """
                       INSERT INTO pym_payment_detail
                       (
                           payment_id, no, payment_concept_id, specification_id, 
                           period_date, fiscal_year, quantity, unit_price, 
                           subtotal, discount, surcharge, total_amount, status
                       ) 
                       VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                       """;

        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            for (PaymentDetailDTO dto : list) {
                // 1. payment_id (long)
                ps.setLong(1, dto.getPayment_id());

                // 2. no (int) - número de renglón / partida
                ps.setInt(2, dto.getNo());

                // 3. payment_concept_id (int)
                ps.setInt(3, dto.getPayment_concept_id());

                // 4. specification_id (int)
                ps.setInt(4, dto.getSpecification_id());

                // 5. period_date (LocalDate -> java.sql.Date)
                if (dto.getPeriod_date() != null) {
                    ps.setDate(5, Date.valueOf(dto.getPeriod_date()));
                } else {
                    ps.setNull(5, java.sql.Types.DATE);
                }

                // 6. fiscal_year (int)
                ps.setInt(6, dto.getFiscal_year());

                // 7. quantity (int)
                ps.setInt(7, dto.getQuantity());

                // 8. unit_price (BigDecimal)
                ps.setBigDecimal(8, dto.getUnit_price());

                // 9. subtotal (BigDecimal)
                ps.setBigDecimal(9, dto.getSubtotal());

                // 10. discount (BigDecimal)
                ps.setBigDecimal(10, dto.getDiscount());

                // 11. surcharge (BigDecimal)
                ps.setBigDecimal(11, dto.getSurcharge());

                // 12. total_amount (BigDecimal)
                ps.setBigDecimal(12, dto.getTotal_amount());

                // 13. status (int)
                ps.setInt(13, dto.getStatus());

                ps.addBatch();
            }

            int[] results = ps.executeBatch();
            return results.length == list.size();
        }
    }

    /**
     * Recupera todas las partidas asociadas a un folio de pago específico.
     *
     * @param connection Conexión activa.
     * @param paymentId ID del pago padre.
     * @return Lista de PaymentDetailDTO mapeados desde la BD.
     * @throws SQLException Si ocurre una falla en la consulta SQL.
     */
    public List<PaymentDetailDTO> selectByPaymentId(JDBConnection connection, long paymentId) throws SQLException {
        List<PaymentDetailDTO> list = new ArrayList<>();
        String sql = """
                     SELECT id, payment_id, no, payment_concept_id, specification_id,
                            period_date, fiscal_year, quantity, unit_price, subtotal,
                            discount, surcharge, total_amount, status, date_update,
                            date_register, date_end
                     FROM pym_payment_detail
                     WHERE payment_id = ? AND status = 1
                     ORDER BY no ASC
                     """;

        try (PreparedStatement ps = connection.getNewPreparedStatement(sql)) {
            ps.setLong(1, paymentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PaymentDetailDTO dto = new PaymentDetailDTO();
                    dto.setId(rs.getLong("id"));
                    dto.setPayment_id(rs.getLong("payment_id"));
                    dto.setNo(rs.getInt("no"));
                    dto.setPayment_concept_id(rs.getInt("payment_concept_id"));
                    dto.setSpecification_id(rs.getInt("specification_id"));

                    Date periodDate = rs.getDate("period_date");
                    if (periodDate != null) {
                        dto.setPeriod_date(periodDate.toLocalDate());
                    }

                    dto.setFiscal_year(rs.getInt("fiscal_year"));
                    dto.setQuantity(rs.getInt("quantity"));
                    dto.setUnit_price(rs.getBigDecimal("unit_price"));
                    dto.setSubtotal(rs.getBigDecimal("subtotal"));
                    dto.setDiscount(rs.getBigDecimal("discount"));
                    dto.setSurcharge(rs.getBigDecimal("surcharge"));
                    dto.setTotal_amount(rs.getBigDecimal("total_amount"));
                    dto.setStatus(rs.getInt("status"));

                    Timestamp dateUpdate = rs.getTimestamp("date_update");
                    if (dateUpdate != null) {
                        dto.setDate_update(dateUpdate.toLocalDateTime());
                    }

                    Timestamp dateRegister = rs.getTimestamp("date_register");
                    if (dateRegister != null) {
                        dto.setDate_register(dateRegister.toLocalDateTime());
                    }

                    Timestamp dateEnd = rs.getTimestamp("date_end");
                    if (dateEnd != null) {
                        dto.setDate_end(dateEnd.toLocalDateTime());
                    }

                    list.add(dto);
                }
            }
        }
        return list;
    }
}
