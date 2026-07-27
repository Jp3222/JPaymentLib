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
import jsoftware.com.jpaymentlib.model.dto.PaymentImportDTO;
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
}
