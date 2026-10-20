/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class SysPaymentDAO {

    public List<Integer> getconceptList(JDBConnection connection, int process_type) throws SQLException {
        List<Integer> list = new ArrayList<>(15);
        String query = "SELECT specification_id FROM sys_pym_concept WHERE process_id = ? AND status = 1";
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setInt(1, process_type);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    list.add(rs.getInt(1));
                }
            }
        }
        return list;
    }
    
    public List<Integer> getSpecification(JDBConnection connection, int specification_id) throws SQLException {
        List<Integer> list = new ArrayList<>(15);
        String query = "SELECT specification_id FROM pym_payment_specification WHERE id = ? AND status = 1";
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setInt(1, specification_id);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    list.add(rs.getInt(1));
                }
            }
        }
        return list;
    }
}
