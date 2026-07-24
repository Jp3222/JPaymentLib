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
import jsoftware.com.jpaymentlib.model.dto.PaymentImportDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentImportDAO {

    public PaymentImportDTO getRulerList(JDBConnection connection, List<Integer> concept_list) throws SQLException {
        List<PaymentImportDTO> list = new ArrayList<>(concept_list.size());
        String query = "SELECT * FROM pym_payment_concept WHERE id IN(" + list.toString().replace('[', '(').replace(']', ')') + ") AND status = 1";
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            String[] fields = new String[md.getColumnCount()];
            for (int i = 0; i < fields.length; i++) {
                fields[i] = md.getColumnLabel(i + 1);
            }
            PaymentImportDTO dto = null;
            while (rs.next()) {
                dto = new PaymentImportDTO();
                for (String i : fields) {
                    dto.put(i, rs.getString(i));
                }

            }
            return dto;
        }

    }
}
