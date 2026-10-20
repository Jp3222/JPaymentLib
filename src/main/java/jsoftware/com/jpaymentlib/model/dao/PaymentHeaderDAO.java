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
     * el ID autogenerado por MySQL mediante su setter nativo.
     *
     * @param connection Enlace de conexión compartida del pool transaccional.
     * @param dto Contenedor fuertemente tipado con la información del cobro
     * actual.
     * @return true si la inserción fue exitosa y se generó la clave primaria;
     * false en caso contrario.
     * @throws SQLException Si ocurre un fallo de sintaxis, conexión o
     * restricciones.
     */
    public boolean insert(JDBConnection connection, PaymentHeaderDTO dto) throws SQLException {
        if (dto == null) {
            return false;
        }

        String query = """
                       INSERT INTO pym_payment_header
                       (
                           payment_id, uuid, user_id, fiscal_year, cash_box_turn_id, 
                           authorized_by, apply_discount, apply_surcharge, subtotal, 
                           discount, surcharge, total_amount, amount_received, 
                           change_returned, transaction_reference, status
                       )
                       VALUES
                       (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                       """;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // 1. payment_id (long)
            ps.setLong(1, dto.getPayment_id());

            // 2. uuid (String)
            ps.setString(2, dto.getUuid());

            // 3. user_id (int)
            ps.setInt(3, dto.getUser_id());

            // 4. fiscal_year (long)
            ps.setLong(4, dto.getFiscal_year());

            // 5. cash_box_turn_id (long)
            ps.setLong(5, dto.getCash_box_turn_id());

            // 6. authorized_by (int - Soporta asignación segura de nulos / inicializado en -1)
            if (dto.getAuthorized_by() <= 0) {
                ps.setNull(6, Types.INTEGER);
            } else {
                ps.setInt(6, dto.getAuthorized_by());
            }

            // 7. apply_discount (boolean -> tinyint/boolean en BD)
            ps.setBoolean(7, dto.isApply_discount());

            // 8. apply_surcharge (boolean -> tinyint/boolean en BD)
            ps.setBoolean(8, dto.isApply_surcharge());

            // 9. subtotal (BigDecimal - Mantiene precisión exacta)
            ps.setBigDecimal(9, dto.getSubtotal());

            // 10. discount (BigDecimal)
            ps.setBigDecimal(10, dto.getDiscount());

            // 11. surcharge (BigDecimal)
            ps.setBigDecimal(11, dto.getSurcharge());

            // 12. total_amount (BigDecimal)
            ps.setBigDecimal(12, dto.getTotal_amount());

            // 13. amount_received (BigDecimal)
            ps.setBigDecimal(13, dto.getAmount_received());

            // 14. change_returned (BigDecimal)
            ps.setBigDecimal(14, dto.getChange_returned());

            // 15. transaction_reference (String)
            ps.setString(15, dto.getTransaction_reference());

            // 16. status (int)
            ps.setInt(16, dto.getStatus());

            // Ejecución del comando en MySQL
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            // Recuperación e inyección directa del ID autogenerado en el DTO
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
}
