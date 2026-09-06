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
     * Strings recibidos en la Vista/DTO a los tipos nativos que la BD requiere.
     * Enriquece el DTO asignando el ID generado tras la inserción exitosa.
     */
    public boolean insert(JDBConnection conn, PaymentDTO dto) throws SQLException, PaymentException {
        boolean res = false;
        String sql = "INSERT INTO pym_payment (payment_type_id, sequence, line, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.getNewPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, Integer.parseInt(dto.getPaymentTypeId()));
            ps.setInt(2, Integer.parseInt(dto.getSequence()));
            ps.setInt(3, Integer.parseInt(dto.getLine()));
            ps.setString(4, dto.getStatus());
            int affectedRows = ps.executeUpdate();
            res = affectedRows == 1;
            if (!res) {
                throw new PaymentException(1, "NO SE PUDO REGISTRAR EL PAGO: " + affectedRows);
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    throw new PaymentException(1, "NO SE PUDO GENERAR EL FOLIO DE PAGO: " + affectedRows);
                }
                int generatedId = rs.getInt(1);
                // Enriquecer el DTO con el ID generado en formato String
                dto.put("id", String.valueOf(generatedId));
            }
        }
        return res;
    }

    public boolean delete(JDBConnection conn, int id) throws SQLException {
        return updateStatus(conn, id, "3");
    }

    /**
     * Actualiza únicamente el estado del pago por su ID.
     */
    public boolean updateStatus(JDBConnection conn, int id, String newStatus) throws SQLException {
        String sql = "UPDATE pym_payment SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.getNewPreparedStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Busca un registro por su ID y mapea el resultado directamente en un
     * PaymentDTO transformando los valores a String para mantener consistencia
     * con la capa superior.
     */
    public PaymentDTO selectById(JDBConnection conn, int id) throws SQLException {
        String sql = "SELECT * FROM pym_payment WHERE id = ?";
        try (PreparedStatement ps = conn.getNewPreparedStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PaymentDTO dto = new PaymentDTO();
                    dto.put("id", String.valueOf(rs.getInt("id")));
                    dto.put("payment_type_id", String.valueOf(rs.getInt("payment_type_id")));
                    dto.put("sequence", String.valueOf(rs.getInt("sequence")));
                    dto.put("line", String.valueOf(rs.getInt("line")));
                    dto.put("status", rs.getString("status"));

                    Timestamp dateRegister = rs.getTimestamp("date_register");
                    dto.put("date_register", dateRegister != null ? dateRegister.toString() : null);

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

    /**
     * Incrementa de manera atómica y recupera el siguiente valor disponible
     * para la secuencia solicitada mediante la función almacenada
     * {@code seq_nextval}.
     * <br><br>
     * <strong>Comportamiento Concurrente:</strong> Este método es 100% inmune a
     * condiciones de carrera gracias al uso interno de
     * {@code LAST_INSERT_ID()}.
     *
     * @param connection Conexión activa provista por el orquestador
     * transaccional.
     * @param sequenceName Nombre único de la secuencia en la base de datos (ej:
     * 'seq_receipt_folio').
     * @return El número de folio generado de tipo {@code long}.
     * @throws DataAccesObjectException Si la secuencia no existe, está
     * inactiva, o si ocurre una falla de conectividad en el motor relacional.
     */
    public String getNextValString(JDBConnection connection, String sequenceName) throws SQLException, PaymentException {
        String fol = null;
        // Estructura limpia de invocación de función determinista
        String sql = "SELECT seq_nextval(?) AS next_val";

        // Implementación con try-with-resources para asegurar el cierre del canal físico
        try (PreparedStatement ps = connection.getNewPreparedStatement(sql)) {

            ps.setString(1, sequenceName.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new PaymentException(1, "[1] ERROR AL OBTENER UN NUMERO DE FOLIO");
                }
                fol = rs.getString("next_val");
                if (Func.isNullEmptyBlank(fol)) {
                    throw new PaymentException(2, "[2] ERROR AL OBTENER UN NUMERO DE FOLIO");
                }
            }
        }
        return fol;
    }
}
