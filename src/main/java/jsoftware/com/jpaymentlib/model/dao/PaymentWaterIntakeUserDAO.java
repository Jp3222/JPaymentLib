/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import jsoftware.com.jpaymentlib.model.dto.PaymentWaterIntakeUserDTO;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 *
 * @author juanp
 */
public class PaymentWaterIntakeUserDAO extends AbstractDAO {

    public PaymentWaterIntakeUserDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    /**
     * Inserta un registro de PaymentWaterIntakeUser en la base de datos.
     *
     * @param connection Conexión activa a la base de datos (maneja
     * transacciones externas).
     * @param dto Objeto PaymentWaterIntakeUserDTO con los datos a persistir.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean insert(JDBConnection connection, PaymentWaterIntakeUserDTO dto) {
        boolean success = false;
        String sql = "INSERT INTO pym_water_intake_user ("
                + "payment_id, "
                + "fiscal_year, "
                + "water_intake_user_id, "
                + "water_intake_number, "
                + "observation) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Mapeo de parámetros según los tipos de la BD y el DTO
            pstmt.setInt(1, dto.getPayment_id());
            pstmt.setInt(2, dto.getFiscal_year());
            pstmt.setInt(3, dto.getWater_intake_user_id());
            pstmt.setString(4, dto.getWater_intake_number());
            pstmt.setString(5, dto.getObservation());
            
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Recuperamos la llave generada (ID autoincremental) y enriquecemos el DTO
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dto.setId(generatedKeys.getInt(1));
                    }
                }
                success = true;
            }

        } catch (SQLException e) {
            System.getLogger(PaymentWaterIntakeUserDAO.class.getName())
                    .log(System.Logger.Level.ERROR, "Error al insertar PaymentWaterIntakeUser", e);
            success = false;
        }

        return success;
    }
}
