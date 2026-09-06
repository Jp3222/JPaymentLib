/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import jsoftware.com.jpaymentlib.model.dto.PaymentImportDTO;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentImportDAO {

    public Optional<PaymentImportDTO> getImportList(JDBConnection connection, String concept_id) throws SQLException {
        String query = "SELECT * FROM pym_payment_concept WHERE id = ? AND status = 1";
        Optional<PaymentImportDTO> res = Optional.empty();
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setString(1, concept_id);
            try (ResultSet rs = ps.executeQuery();) {
                ResultSetMetaData md = rs.getMetaData();
                String[] fields = new String[md.getColumnCount()];
                for (int i = 0; i < fields.length; i++) {
                    fields[i] = md.getColumnLabel(i + 1);
                }
                if (!rs.next()) {
                    return res;
                }
                PaymentImportDTO dto = new PaymentImportDTO();
                for (String i : fields) {
                    dto.put(i, rs.getString(i));
                }
                res = Optional.of(dto);
                return res;
            }
        }
    }

    public boolean insert(JDBConnection connection, PaymentImportDTO dto) throws SQLException, PaymentException {
        boolean res = false;
        String query = """
                        INSERT INTO jblue.pym_payment_import 
                            (concept_id, administration_id, currency, amount, surcharge, discount, subsidy, is_variable, unit_id, units, status, last_employee_update)
                        VALUES(?,?,?,?,?,?,?,?,?,?,?,?)
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dto.getConceptId());
            ps.setString(2, dto.getAdministrationId());
            ps.setString(3, dto.getCurrency());
            ps.setString(4, dto.getAmount());
            ps.setString(5, dto.getSurcharge());
            ps.setString(6, dto.getDiscount());
            ps.setString(7, dto.getSubsidy());
            ps.setString(8, dto.getIsVariable());
            ps.setString(9, dto.getUnitId());
            ps.setString(10, dto.getUnits());
            ps.setString(11, dto.getStatus());
            ps.setString(12, dto.getLastEmployeeUpdate());
            ps.setString(13, dto.getOfficeId());
            res = ps.executeUpdate() == 1;
            if (!res) {
                throw new PaymentException(1, "REGISTRO DE IMPORTE ERRONEO");
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) {
                    throw new PaymentException(2, "GENERACION DE LLAVE ERRONEA");
                }
                String id = rs.getString("id");
                PaymentImportDTO new_dto = new PaymentImportDTO();
                new_dto.addAll(dto.getMap());
                new_dto.put("id", id);
                new_dto.put("date_register", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                new_dto.put("date_update", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                new_dto.put("date_end", null);
                return res;
            }
        }
    }

    public Optional<PaymentImportDTO> copyImport(JDBConnection connection, PaymentImportDTO dto) throws SQLException, PaymentException {
        boolean res = false;
        String query = """
                        INSERT INTO jblue.pym_payment_import 
                            (concept_id, administration_id, currency, amount, 
                            surcharge, discount, subsidy, is_variable, unit_id, 
                            units, status, last_employee_update)
                        SELECT 
                            concept_id, administration_id, currency, amount, 
                            surcharge, discount, subsidy, is_variable, unit_id, 
                            units, ?, ? 
                        FROM 
                            pym_payment_import 
                        WHERE 
                            id = ?
                     """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dto.getStatus());
            ps.setString(2, dto.getLastEmployeeUpdate());
            ps.setString(3, dto.getOfficeId());
            ps.setString(4, dto.getId());
            res = ps.executeUpdate() == 1;
            if (!res) {
                throw new PaymentException(1, "REGISTRO DE IMPORTE ERRONEO");
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) {
                    throw new PaymentException(2, "GENERACION DE LLAVE ERRONEA");
                }
                String id = rs.getString("id");
                PaymentImportDTO new_dto = new PaymentImportDTO();
                new_dto.addAll(dto.getMap());
                new_dto.put("id", id);
                new_dto.put("date_register", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                new_dto.put("date_update", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                new_dto.put("date_end", null);
                return Optional.of(new_dto);
            }
        }
    }
}
