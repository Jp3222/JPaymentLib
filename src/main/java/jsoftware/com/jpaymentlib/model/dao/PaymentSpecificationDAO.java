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
import jsoftware.com.jpaymentlib.model.dto.PaymentSpecificationDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentSpecificationDAO {

    public List<PaymentSpecificationDTO> getSpecificationList(JDBConnection connection, List<Integer> concept_list) throws SQLException {
        if (concept_list == null || concept_list.isEmpty()) {
            return new ArrayList<>();
        }

        List<PaymentSpecificationDTO> list = new ArrayList<>(concept_list.size());

        // 1. Construcción dinámica de los placeholders '?' según el tamaño del lote
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < concept_list.size(); i++) {
            placeholders.append("?");
            if (i < concept_list.size() - 1) {
                placeholders.append(",");
            }
        }

        String query = "SELECT * FROM pym_payment_specification WHERE payment_concept_id IN (" + placeholders.toString() + ") AND status = 1";

        try (PreparedStatement ps = connection.getConnection().prepareStatement(query)) {
            // 2. Asignación segura de los IDs del lote al PreparedStatement
            for (int i = 0; i < concept_list.size(); i++) {
                ps.setInt(i + 1, concept_list.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                String[] fields = new String[md.getColumnCount()];

                for (int i = 0; i < fields.length; i++) {
                    fields[i] = md.getColumnLabel(i + 1);
                }

                // 3. Extracción de los registros devueltos por la base de datos
                while (rs.next()) {
                    PaymentSpecificationDTO dto = new PaymentSpecificationDTO();
                    for (String field : fields) {
                        // Respetamos tu patrón: String de MySQL hacia el Map interno del DTO
                        dto.put(field, rs.getString(field));
                    }
                    list.add(dto);
                }
            }
        }

        return list;
    }

}
