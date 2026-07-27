/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public Optional<PaymentConceptDTO> getConceptList(JDBConnection connection, String concept_id) throws SQLException {
        String query = "SELECT * FROM pym_payment_concept WHERE id = ? AND status = 1";
        try (PreparedStatement ps = connection.getConnection().prepareStatement(query)) {
            ps.setString(1, concept_id);
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                String[] fields = new String[md.getColumnCount()];

                for (int i = 0; i < fields.length; i++) {
                    fields[i] = md.getColumnLabel(i + 1);
                }

                // 3. Mapear cada fila al DTO e insertarlo a la lista de retorno
                if (!rs.next()) {
                    return Optional.empty();
                }
                PaymentConceptDTO dto = new PaymentConceptDTO();
                for (String field : fields) {
                    dto.put(field, rs.getString(field));
                }
                return Optional.of(dto);
            }
        }
    }
}
