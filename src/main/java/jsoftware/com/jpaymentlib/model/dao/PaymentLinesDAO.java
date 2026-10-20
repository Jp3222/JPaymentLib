/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import jsoftware.com.jpaymentlib.model.dto.PaymentLinesDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentLinesDAO {

    /**
     * Inserta un registro de línea de captura de pago en la base de datos de
     * manera transaccional. Asigna la fecha actual de emisión y la fecha de
     * expiración calculada. Si la operación es exitosa, enriquece el DTO
     * asignándole el ID autogenerado por MySQL.
     *
     * Todos los registros recién creados quedan con status 8 (PENDIENTE).
     *
     * @param connection Enlace de conexión compartida del pool transaccional.
     * @param dto Contenedor fuertemente tipado con la información de la línea
     * de pago.
     * @param dayExp Días de vigencia para esta línea.
     * @return true si la inserción fue exitosa; false en caso contrario.
     * @throws SQLException Si ocurre un fallo de sintaxis o conectividad.
     */
    public boolean insert(JDBConnection connection, PaymentLinesDTO dto, int dayExp) throws SQLException {
        if (dto == null || connection == null || connection.getConnection() == null) {
            return false;
        }

        String query = """
                       INSERT INTO pym_payment_lines
                       (
                           payment_id, payment_line, amount_due, timestamp_emission, 
                           timestamp_expiration, employee_id, status
                       )
                       VALUES
                       (?, ?, ?, CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL ? DAY), ?, 8)
                       """;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // 1. payment_id (long)
            ps.setLong(1, dto.getPayment_id());

            // 2. payment_line (String)
            ps.setString(2, dto.getPayment_line());

            // 3. amount_due (BigDecimal - Conserva precisión financiera)
            ps.setBigDecimal(3, dto.getAmount_due());

            // 4. dayExp (int - Parámetro de días de vigencia)
            ps.setInt(4, dayExp);

            // 5. employee_id (int - Manejo seguro si no está asignado)
            if (dto.getEmployee_id() <= 0) {
                ps.setNull(5, Types.INTEGER);
            } else {
                ps.setInt(5, dto.getEmployee_id());
            }

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            // Recuperación e inyección de la llave generada (AI)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    dto.setId(generatedId);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Actualiza el empleado asignado, el estado y la fecha de modificación de
     * un registro de línea de pago a través de su ID primario.
     *
     * @param connection Enlace de conexión compartida del pool transaccional.
     * @param dto Contenedor con los datos a actualizar ("id", "employee_id",
     * "status").
     * @return true si el registro fue actualizado exitosamente; false en caso
     * contrario.
     * @throws SQLException Si ocurre un fallo de sintaxis o conectividad.
     */
    public boolean update(JDBConnection connection, PaymentLinesDTO dto) throws SQLException {
        if (dto == null || connection == null || connection.getConnection() == null) {
            return false;
        }

        if (dto.getId() <= 0) {
            return false;
        }

        String query = """
                       UPDATE pym_payment_lines
                       SET employee_id = ?,
                           status = ?,
                           date_update = CURRENT_TIMESTAMP
                       WHERE id = ?
                       """;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(query)) {

            // 1. employee_id (int)
            if (dto.getEmployee_id() <= 0) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, dto.getEmployee_id());
            }

            // 2. status (int)
            ps.setInt(2, dto.getStatus());

            // 3. id (long - Clave primaria)
            ps.setLong(3, dto.getId());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }
}
