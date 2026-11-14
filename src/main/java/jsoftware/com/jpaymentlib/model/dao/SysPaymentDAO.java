/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jsoftware.com.jblue.model.exp.DataAccesObjectException;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class SysPaymentDAO {

    public List<Integer> getconceptList(JDBConnection connection, int process, int committee, int office) throws SQLException {
        List<Integer> list = new ArrayList<>(15);
        String query = "SELECT specification_id FROM sys_pym_concept "
                + "WHERE process_id = ? AND committee_id = ? AND  office = ? AND status = 1";
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setInt(1, process);
            ps.setInt(2, committee);
            ps.setInt(3, office);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    list.add(rs.getInt(1));
                }
            }
        }
        return list;
    }

    public int getSpecification(JDBConnection connection, int water_intake_user_id) throws SQLException, DataAccesObjectException {
        List<Integer> list = new ArrayList<>(15);
        String query = """
                       SELECT ID_ESPECIFICACION FROM vwc_specification_by_wki_user 
                       WHERE id_toma = ? 
                       AND FISCAL_YEAR = ?
                       AND CURRENT_DATE 
                       BETWEEN FECHA_INICIO 
                       AND FECHA_FIN
                       """;
        try (PreparedStatement ps = connection.getNewPreparedStatement(query)) {
            ps.setInt(1, water_intake_user_id);
            ps.setInt(2, LocalDate.now().getYear());
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    list.add(rs.getInt(1));
                }
            }
        }
        if (!list.isEmpty() && list.size() > 1) {
            throw new DataAccesObjectException("LA TOMA REGISTRADA TIENE MAS DE UNA ESPECIFICACION DE PAGO");
        }
        return list.getFirst();
    }
}
