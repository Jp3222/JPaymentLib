/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Optional;
import jsoftware.com.jpaymentlib.model.dto.PaymentImportDTO;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentImportDAO {

    public Optional<PaymentImportDTO> getImportList(JDBConnection connection, int concept_id) throws SQLException {
        String query = """
        SELECT id, concept_id, administration_id, currency, amount, surcharge, 
               discount, subsidy, is_variable, unit_id, units, status, 
               last_employee_update, office_id, date_update, date_register, date_end 
        FROM pym_payment_import 
        WHERE id = ? AND status = 1
        """;

        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setInt(1, concept_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }

                PaymentImportDTO dto = new PaymentImportDTO();
                dto.setId(rs.getInt("id"));
                dto.setConcept_id(rs.getInt("concept_id"));

                int adminId = rs.getInt("administration_id");
                dto.setAdministration_id(rs.wasNull() ? null : adminId);

                dto.setCurrency(rs.getString("currency"));
                dto.setAmount(rs.getBigDecimal("amount"));
                dto.setSurcharge(rs.getBigDecimal("surcharge"));
                dto.setDiscount(rs.getBigDecimal("discount"));
                dto.setSubsidy(rs.getBigDecimal("subsidy"));
                dto.setIs_variable(rs.getBoolean("is_variable"));
                dto.setUnit_id(rs.getInt("unit_id"));
                dto.setUnits(rs.getInt("units"));
                dto.setStatus(rs.getInt("status"));
                dto.setLast_employee_update(rs.getInt("last_employee_update"));
                dto.setOffice_id(rs.getInt("office_id"));

                Timestamp dateUpdate = rs.getTimestamp("date_update");
                dto.setDate_update(dateUpdate != null ? dateUpdate.toLocalDateTime() : null);

                Timestamp dateRegister = rs.getTimestamp("date_register");
                dto.setDate_register(dateRegister != null ? dateRegister.toLocalDateTime() : null);

                Timestamp dateEnd = rs.getTimestamp("date_end");
                dto.setDate_end(dateEnd != null ? dateEnd.toLocalDateTime() : null);

                return Optional.of(dto);
            }
        }
    }

    public boolean insert(JDBConnection connection, PaymentImportDTO dto) throws SQLException, PaymentException {
        String query = """
        INSERT INTO jblue.pym_payment_import 
            (concept_id, administration_id, currency, amount, surcharge, discount, subsidy, is_variable, unit_id, units, status, last_employee_update, office_id)
        VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.getNewPreparedStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, dto.getConcept_id());

            if (dto.getAdministration_id() != null) {
                ps.setInt(2, dto.getAdministration_id());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setString(3, dto.getCurrency());
            ps.setBigDecimal(4, dto.getAmount());
            ps.setBigDecimal(5, dto.getSurcharge());
            ps.setBigDecimal(6, dto.getDiscount());
            ps.setBigDecimal(7, dto.getSubsidy());
            ps.setBoolean(8, dto.isvariable());
            ps.setInt(9, dto.getUnit_id());
            ps.setInt(10, dto.getUnits());
            ps.setInt(11, dto.getStatus());
            ps.setInt(12, dto.getLast_employee_update());
            ps.setInt(13, dto.getOffice_id());

            boolean res = ps.executeUpdate() == 1;
            if (!res) {
                throw new PaymentException(1, "REGISTRO DE IMPORTE ERRONEO");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) {
                    throw new PaymentException(2, "GENERACION DE LLAVE ERRONEA");
                }

                int generatedId = rs.getInt(1);
                LocalDateTime now = LocalDateTime.now();

                // Enriquecer el DTO con el ID generado y las fechas asignadas por la BD/aplicación
                dto.setId(generatedId);
                dto.setDate_register(now);
                dto.setDate_update(now);
                dto.setDate_end(null);

                return true;
            }
        }
    }

    public Optional<PaymentImportDTO> copyImport(JDBConnection connection, PaymentImportDTO dto) throws SQLException, PaymentException {
        String query = """
        INSERT INTO jblue.pym_payment_import 
            (concept_id, administration_id, currency, amount, surcharge, discount, subsidy, is_variable, unit_id, units, status, last_employee_update, office_id)
        SELECT 
            concept_id, administration_id, currency, amount, surcharge, discount, subsidy, is_variable, unit_id, units, ?, ?, ?
        FROM 
            pym_payment_import 
        WHERE 
            id = ?
        """;

        try (PreparedStatement ps = connection.getNewPreparedStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, dto.getStatus());
            ps.setInt(2, dto.getLast_employee_update());
            ps.setInt(3, dto.getOffice_id());
            ps.setInt(4, dto.getId());

            boolean res = ps.executeUpdate() == 1;
            if (!res) {
                throw new PaymentException(1, "REGISTRO DE IMPORTE ERRONEO");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) {
                    throw new PaymentException(2, "GENERACION DE LLAVE ERRONEA");
                }

                int generatedId = rs.getInt(1);
                LocalDateTime now = LocalDateTime.now();

                // Se crea la nueva instancia DTO duplicada y enriquecida
                PaymentImportDTO newDto = new PaymentImportDTO();
                newDto.setId(generatedId);
                newDto.setConcept_id(dto.getConcept_id());
                newDto.setAdministration_id(dto.getAdministration_id());
                newDto.setCurrency(dto.getCurrency());
                newDto.setAmount(dto.getAmount());
                newDto.setSurcharge(dto.getSurcharge());
                newDto.setDiscount(dto.getDiscount());
                newDto.setSubsidy(dto.getSubsidy());
                newDto.setIs_variable(dto.isvariable());
                newDto.setUnit_id(dto.getUnit_id());
                newDto.setUnits(dto.getUnits());
                newDto.setStatus(dto.getStatus());
                newDto.setLast_employee_update(dto.getLast_employee_update());
                newDto.setOffice_id(dto.getOffice_id());
                newDto.setDate_register(now);
                newDto.setDate_update(now);
                newDto.setDate_end(null);

                return Optional.of(newDto);
            }
        }
    }
}
