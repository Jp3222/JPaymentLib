/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import jsoftware.com.jpaymentlib.model.dto.PaymentConceptDTO;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 *
 * @author juanp
 */
public class PaymentConceptDAO extends AbstractDAO {

    public PaymentConceptDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    public Optional<PaymentConceptDTO> getConceptList(JDBConnection connection, int concept_id) throws SQLException {
        String query = "SELECT id, sequence, name, description, document_id, based_on_document, "
                + "status, office_id, last_employee_update, date_update, date_register, date_end "
                + "FROM pym_payment_concept WHERE id = ? AND status = 1";

        try (PreparedStatement ps = connection.getConnection().prepareStatement(query)) {
            ps.setInt(1, concept_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }

                PaymentConceptDTO dto = new PaymentConceptDTO();

                // Mapeo directo de campos primitivos y objetos
                dto.setId(rs.getInt("id"));
                dto.setSequence(rs.getInt("sequence"));
                dto.setName(rs.getString("name"));
                dto.setDescription(rs.getString("description"));

                // Manejo de Integer nulo para document_id
                int docId = rs.getInt("document_id");
                dto.setDocument_id(rs.wasNull() ? null : docId);

                dto.setBased_on_document(rs.getString("based_on_document"));
                dto.setStatus(rs.getInt("status"));
                dto.setOffice_id(rs.getInt("office_id"));
                dto.setLast_employee_update(rs.getInt("last_employee_update"));

                // Conversión de Timestamps de SQL a LocalDateTime
                dto.setDate_update(rs.getTimestamp("date_update") != null ? rs.getTimestamp("date_update").toLocalDateTime() : null);
                dto.setDate_register(rs.getTimestamp("date_register") != null ? rs.getTimestamp("date_register").toLocalDateTime() : null);
                dto.setDate_end(rs.getTimestamp("date_end") != null ? rs.getTimestamp("date_end").toLocalDateTime() : null);

                return Optional.of(dto);
            }
        }
    }
}
