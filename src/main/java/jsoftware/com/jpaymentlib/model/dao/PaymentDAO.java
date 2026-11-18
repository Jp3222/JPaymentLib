package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import jsoftware.com.jpaymentlib.model.dto.PaymentDTO;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jutil.db.JDBConnection;

public class PaymentDAO {

    /**
     * Inserta un nuevo registro utilizando el mapeo desde el DTO. Convierte los
     * datos nativos del DTO a la BD y enriquece el DTO asignando el ID generado
     * tras la inserción exitosa.
     */
    public boolean insert(JDBConnection conn, PaymentDTO dto) throws SQLException, PaymentException {
        boolean res = false;
        String sql = "INSERT INTO pym_payment (user_id, payment_type_id, payment_method_id, sequence, line, observation, status, committee_id, office_id, administration_id, last_employee_update) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = conn.getNewPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, dto.getUser_id());
            ps.setInt(2, dto.getPayment_type_id());
            ps.setInt(3, dto.getPayment_method_id());
            ps.setString(4, dto.getSequence());
            ps.setString(5, dto.getLine());
            ps.setString(6, dto.getObservation());
            ps.setInt(7, dto.getStatus());
            ps.setInt(8, dto.getCommittee_id());
            ps.setInt(9, dto.getOffice_id());
            ps.setInt(10, dto.getAdministration_id());
            ps.setInt(11, dto.getLast_employee_update());
            int affectedRows = ps.executeUpdate();
            res = affectedRows == 1;

            if (!res) {
                throw new PaymentException(1, "NO SE PUDO REGISTRAR EL PAGO: " + affectedRows);
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                // CORRECCIÓN: Se debe verificar si NO hay un siguiente registro
                if (!rs.next()) {
                    throw new PaymentException(1, "NO SE PUDO GENERAR EL FOLIO DE PAGO.");
                }
                int generatedId = rs.getInt(1);
                // Enriquecer el DTO con el ID de tipo int
                dto.setId(generatedId);
            }
        }
        return res;
    }

    public boolean delete(JDBConnection conn, int id) throws SQLException {
        return updateStatus(conn, id, 3); // Cambiado a int según el tipo status en DTO
    }

    /**
     * Actualiza únicamente el estado del pago por su ID.
     */
    public boolean updateStatus(JDBConnection conn, int id, int newStatus) throws SQLException {
        String sql = "UPDATE pym_payment SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.getNewPreparedStatement(sql)) {
            ps.setInt(1, newStatus);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Busca un registro por su ID y mapea el resultado directamente en un
     * PaymentDTO.
     */
    public PaymentDTO selectById(JDBConnection conn, int id) throws SQLException {
        String sql = "SELECT id, payment_type_id, sequence, line, status, date_register FROM pym_payment WHERE id = ?";
        try (PreparedStatement ps = conn.getNewPreparedStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PaymentDTO dto = new PaymentDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setPayment_type_id(rs.getInt("payment_type_id"));
                    dto.setSequence(rs.getString("sequence"));
                    dto.setLine(rs.getString("line"));
                    dto.setStatus(rs.getInt("status"));

                    Timestamp dateRegister = rs.getTimestamp("date_register");
                    if (dateRegister != null) {
                        dto.setDate_register(dateRegister.toLocalDateTime());
                    }

                    return dto;
                }
            }
        }
        return null;
    }

    public String getPaymentReferenceNumber(JDBConnection connection) throws SQLException, PaymentException {
        return getNextValString(connection, "PAGO_TRAMITES");
    }

    public String getPayLine(JDBConnection connection) throws SQLException, PaymentException {
        return getNextValString(connection, "LINEA_PAGOS");
    }

    public String getPay(JDBConnection connection) throws SQLException, PaymentException {
        return getNextValString(connection, "PAGOS");
    }

    /**
     * Incrementa de manera atómica y recupera el siguiente valor disponible
     * mediante la función almacenada {@code seq_nextval}.
     */
    public String getNextValString(JDBConnection connection, String sequenceName) throws SQLException, PaymentException {
        String fol = null;
        String sql = "SELECT seq_nextval(?) AS next_val";

        try (PreparedStatement ps = connection.getNewPreparedStatement(sql)) {
            ps.setString(1, sequenceName);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new PaymentException(1, "[1] ERROR AL OBTENER UN NUMERO DE FOLIO");
                }
                fol = rs.getString(1);
                if (Func.isNullEmptyBlank(fol)) {
                    throw new PaymentException(2, "[2] ERROR AL OBTENER UN NUMERO DE FOLIO");
                }
            }
        }
        return fol;
    }
}
