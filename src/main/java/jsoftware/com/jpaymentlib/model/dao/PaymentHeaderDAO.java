/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jsoftware.com.jpaymentlib.model.dto.PaymentHeaderDTO;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 *
 * @author juanp
 */
public class PaymentHeaderDAO extends AbstractDAO {

    public PaymentHeaderDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    /**
     * Inserta un registro de cabecera de pago en la base de datos de manera
     * transaccional. Si la operación es exitosa, enriquece el DTO asignándole
     * el ID autogenerado por MySQL.
     *
     * @param connection Enlace de conexión compartida del pool transaccional.
     * @param dto Contenedor dinámico con la información del cobro actual.
     * @return true si la inserción fue exitosa y se generó la clave primaria;
     * false en caso contrario.
     * @throws SQLException Si ocurre un fallo de sintaxis, conexión o
     * restricciones en el motor.
     */
    public boolean insert(JDBConnection connection, PaymentHeaderDTO dto) throws SQLException {
        if (dto == null) {
            return false;
        }

        String query = """
                       INSERT INTO pym_payment_header
                       (
                        process_id, sequence, uuid, user_id, wki_user_id, office_id, 
                        cash_box_turn_id, authorized_by, payment_method_id, payment_type_id, 
                        subtotal, discount, total_surcharge, total_amount, amount_received, 
                        change_returned, is_surcharge_paid, transaction_reference, 
                        cancellation_reason, print_count, status, employee_id
                       )
                       VALUES
                       (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                       """;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            // Se prepara la sentencia solicitando el retorno de las llaves generadas automáticamente (AI)

            // ESTRATEGIA: Mapeo manual con el casting final y seguro de String a los tipos de MySQL.
            // 1. process_id (int)
            ps.setInt(1, Integer.parseInt(dto.getProcessId()));

            // 2. sequence (int)
            ps.setInt(2, Integer.parseInt(dto.getSequence()));

            // 3. uuid (String)
            ps.setString(3, dto.getUuid());

            // 4. user_id (int)
            ps.setInt(4, Integer.parseInt(dto.getUserId()));

            // 5. wki_user_id (int - Toma de agua vinculada al usuario)
            ps.setInt(5, Integer.parseInt(dto.getWkiUserId()));

            // 6. office_id (int)
            ps.setInt(6, Integer.parseInt(dto.getOfficeId()));

            // 7. cash_box_turn_id (int)
            ps.setInt(7, Integer.parseInt(dto.getCashBoxTurnId()));

            // 8. authorized_by (int / String según tu lógica de control interno)
            ps.setString(8, dto.getAuthorizedBy());

            // 9. payment_method_id (int)
            ps.setInt(9, Integer.parseInt(dto.getPaymentMethodId()));

            // 10. payment_type_id (int)
            ps.setInt(10, Integer.parseInt(dto.getPaymentTypeId()));

            // 11. subtotal (double / decimal)
            ps.setDouble(11, Double.parseDouble(dto.getSubtotal()));

            // 12. discount (double / decimal)
            ps.setDouble(12, Double.parseDouble(dto.getDiscount()));

            // 13. total_surcharge (double / decimal)
            ps.setDouble(13, Double.parseDouble(dto.getTotalSurcharge()));

            // 14. total_amount (double / decimal)
            ps.setDouble(14, Double.parseDouble(dto.getTotalAmount()));

            // 15. amount_received (double / decimal)
            ps.setDouble(15, Double.parseDouble(dto.getAmountReceived()));

            // 16. change_returned (double / decimal)
            ps.setDouble(16, Double.parseDouble(dto.getChangeReturned()));

            // 17. is_surcharge_paid (String / tinyint)
            ps.setString(17, dto.getIsSurchargePaid());

            // 18. transaction_reference (String)
            ps.setString(18, dto.getTransactionReference());

            // 19. cancellation_reason (String)
            ps.setString(19, dto.getCancellationReason());

            // 20. print_count (int)
            ps.setInt(20, Integer.parseInt(dto.getPrintCount()));

            // 21. status (String / char)
            ps.setString(21, dto.getStatus());

            // 22. employee_id (int)
            ps.setInt(22, Integer.parseInt(dto.getEmployeeId()));

            // Ejecución de la sentencia
            int affectedRows = ps.executeUpdate();

            // Si no hubo filas afectadas, el insert falló drásticamente
            if (affectedRows == 0) {
                return false;
            }

            // 2. RECUPERACIÓN E INYECCIÓN DE LA LLAVE GENERADA
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    // Obtenemos el ID asignado por MySQL
                    long generatedId = rs.getLong(1);

                    // Enriquecemos el DTO inyectando el valor en formato String en el mapa
                    // Nota: Asumiendo que JDBMapObject expone un método put() o almacena en la clave "id"
                    dto.put("id", String.valueOf(generatedId));

                    return true;
                }
            }
            return false;
        }
    }

}
