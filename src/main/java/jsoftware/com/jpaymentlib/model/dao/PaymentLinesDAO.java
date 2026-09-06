/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jsoftware.com.jpaymentlib.model.dto.PaymentLinesDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentLinesDAO {

    /**
     * Inserta un registro de cabecera de pago en la base de datos de manera
     * transaccional. Si la operación es exitosa, enriquece el DTO asignándole
     * el ID autogenerado por MySQL.
     *
     * Todos los registros quedan con status 8(PENDIENTE)
     *
     * @param connection Enlace de conexión compartida del pool transaccional.
     * @param dto Contenedor dinámico con la información del cobro actual.
     * @param dayExp Días de vigencia para esta línea.
     * @return true si la inserción fue exitosa y se generó la clave primaria;
     * false en caso contrario.
     * @throws SQLException Si ocurre un fallo de sintaxis, conexión o
     * restricciones en el motor.
     */
    public boolean insert(JDBConnection connection, PaymentLinesDTO dto, int dayExp) throws SQLException {
        String query = """
                       INSERT INTO pym_payment_header
                       (
                         payment_id, payment_line, amount_due, timestamp_emission, 
                         timestamp_expiration, employee_id, status
                       )
                       VALUES
                       (?, ?, ?, CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL ? DAY), ?, 8)
                       """;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dto.getPaymentId());
            ps.setString(2, dto.getPaymentLine());
            ps.setString(3, dto.getAmountDue());
            ps.setInt(4, dayExp);
            ps.setString(5, dto.getEmployeeId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            // Recuperación e inyección de la llave generada (AI)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    dto.put("id", String.valueOf(generatedId));
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Actualiza el empleado asignado, el estado y la fecha de modificación de
     * un registro de cabecera de pago a través de su ID primario.
     *
     * @param connection Enlace de conexión compartida del pool transaccional.
     * @param dto Contenedor dinámico que incluye el "id", "employee_id" y
     * "status".
     * @return true si el registro fue actualizado exitosamente; false en caso
     * contrario.
     * @throws SQLException Si ocurre un fallo de sintaxis, conexión o
     * restricciones en el motor.
     */
    public boolean update(JDBConnection connection, PaymentLinesDTO dto) throws SQLException {
        if (dto == null || connection == null || connection.getConnection() == null) {
            return false;
        }
        // Validación de la clave primaria para ejecutar la cláusula WHERE
        String id = dto.getId();
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        String query = """
                       UPDATE pym_payment_header
                       SET employee_id = ?,
                           status = ?,
                           date_update = CURRENT_TIMESTAMP
                       WHERE id = ?
                       """;
        try (PreparedStatement ps = connection.getConnection().prepareStatement(query)) {
            ps.setString(1, dto.getEmployeeId());
            ps.setString(2, dto.getStatus());
            // Cast implícito de MySQL al evaluar la columna ID (Integer/Long)
            ps.setString(3, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }
}
