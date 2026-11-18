package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;
import jsoftware.com.jpaymentlib.model.dto.PaymentReprintDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 * DAO para la gestión de pym_payment_reprint.
 *
 * @author juan pablo campos casasanero
 */
public class PaymentReprintDAO {

    /**
     * Inserta un nuevo registro de reimpresión y enriquece el DTO con el ID
     * generado.
     */
    public boolean insert(JDBConnection connection, PaymentReprintDTO dto) throws SQLException {
        boolean success = false;
        String sql = "INSERT INTO pym_payment_reprint ("
                + "payment_id, reprint_count, observation, employee_id, status, date_end"
                + ") VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, dto.getPayment_id());
            pstmt.setInt(2, dto.getReprint_count());
            pstmt.setString(3, dto.getObservation());
            pstmt.setInt(4, dto.getEmployee_id());
            pstmt.setInt(5, dto.getStatus());

            if (dto.getDate_end() != null) {
                pstmt.setTimestamp(6, Timestamp.valueOf(dto.getDate_end()));
            } else {
                pstmt.setNull(6, java.sql.Types.TIMESTAMP);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dto.setId(generatedKeys.getInt(1));
                    }
                }
                success = true;
            }

        }

        return success;
    }

    /**
     * Actualiza exclusivamente el conteo de reimpresión (reprint_count) y el
     * empleado responsable (employee_id) basándose en el ID del registro.
     */
    public boolean update(JDBConnection connection, PaymentReprintDTO dto) throws SQLException {
        boolean success = false;
        String sql = "UPDATE pym_payment_reprint SET "
                + "reprint_count = ?, "
                + "employee_id = ? "
                + "WHERE id = ?";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {

            pstmt.setInt(1, dto.getReprint_count());
            pstmt.setInt(2, dto.getEmployee_id());
            pstmt.setInt(3, dto.getId());

            int affectedRows = pstmt.executeUpdate();
            success = (affectedRows > 0);

        }

        return success;
    }

    /**
     * Busca y retorna un registro de reimpresión filtrado por el ID de la
     * cabecera de pago (payment_id).
     */
    public Optional<PaymentReprintDTO> selectByPaymentId(JDBConnection connection, int paymentId) throws SQLException {
        String sql = "SELECT id, payment_id, reprint_count, observation, employee_id, "
                + "status, date_register, date_update, date_end "
                + "FROM pym_payment_reprint WHERE payment_id = ?";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setInt(1, paymentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    PaymentReprintDTO dto = new PaymentReprintDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setPayment_id(rs.getInt("payment_id"));
                    dto.setReprint_count(rs.getInt("reprint_count"));
                    dto.setObservation(rs.getString("observation"));
                    dto.setEmployee_id(rs.getInt("employee_id"));
                    dto.setStatus(rs.getInt("status"));

                    Timestamp regTs = rs.getTimestamp("date_register");
                    if (regTs != null) {
                        dto.setDate_register(regTs.toLocalDateTime());
                    }

                    Timestamp updTs = rs.getTimestamp("date_update");
                    if (updTs != null) {
                        dto.setDate_update(updTs.toLocalDateTime());
                    }

                    Timestamp endTs = rs.getTimestamp("date_end");
                    if (endTs != null) {
                        dto.setDate_end(endTs.toLocalDateTime());
                    }

                    return Optional.of(dto);
                }
            }
        }
        return Optional.empty();
    }
}
