/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import jsoftware.com.jpaymentlib.model.dto.PaymentRulersDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentRulersDAO {

    public Optional<PaymentRulersDTO> getRulerList(JDBConnection connection, int ruler_id) throws SQLException {
        String query = "SELECT * FROM pym_payment_rulers WHERE id = ? AND status = 1";

        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setInt(1, ruler_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }

                PaymentRulersDTO dto = new PaymentRulersDTO();

                // Mapeo manual de campos desde el ResultSet hacia el DTO
                dto.setId(rs.getInt("id"));
                dto.setName(rs.getString("name"));
                dto.setRuler_type(rs.getString("ruler_type"));
                dto.setValue_type(rs.getString("value_type"));
                dto.setValue(rs.getBigDecimal("value"));

                // Booleans
                dto.setEditable(rs.getBoolean("editable"));
                dto.setApply_rulers(rs.getBoolean("apply_rulers"));
                dto.setRound(rs.getBoolean("round"));
                dto.setRound_up(rs.getBoolean("round_up"));
                dto.setApply_discount(rs.getBoolean("apply_discount"));
                dto.setApply_surcharge(rs.getBoolean("apply_surcharge"));
                dto.setApply_subsidy(rs.getBoolean("apply_subsidy"));
                dto.setIs_Mandatory(rs.getBoolean("is_mandatory"));
                dto.setHas_automatic_increment(rs.getBoolean("has_automatic_increment"));

                // Montos y Enteros
                dto.setIncrement_period(rs.getBigDecimal("increment_period"));
                dto.setDays_to_paid(rs.getInt("days_to_paid"));
                dto.setPayday(rs.getInt("payday"));
                dto.setDays_covered(rs.getInt("days_covered"));
                dto.setStatus(rs.getInt("status"));
                dto.setLast_employee_update(rs.getInt("last_employee_update"));

                // Fechas (Timestamp a LocalDateTime)
                if (rs.getTimestamp("date_update") != null) {
                    dto.setDate_update(rs.getTimestamp("date_update").toLocalDateTime());
                }
                if (rs.getTimestamp("date_register") != null) {
                    dto.setDate_register(rs.getTimestamp("date_register").toLocalDateTime());
                }
                if (rs.getTimestamp("date_end") != null) {
                    dto.setDate_end(rs.getTimestamp("date_end").toLocalDateTime());
                }

                return Optional.of(dto);
            }
        }
    }

    public boolean insert(JDBConnection connection, PaymentRulersDTO rulers) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
