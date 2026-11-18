/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import jsoftware.com.jpaymentlib.model.dto.OperationDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class OperationDAO {

    /**
     * Inserta un nuevo registro y enriquece el DTO con el ID generado.
     */
    public boolean insert(JDBConnection conn, OperationDTO dto) throws SQLException {
        String sql = "INSERT INTO pym_operation (payment_id, description, operation, amount, status, "
                + "commitee_id, office_id, employee_id, administration_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.getNewPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, dto.getPaymentId());
            pstmt.setString(2, dto.getDescription());
            pstmt.setString(3, dto.getOperation());
            pstmt.setBigDecimal(4, dto.getAmount());
            pstmt.setInt(5, dto.getStatus());
            pstmt.setInt(6, dto.getCommiteeId());
            pstmt.setInt(7, dto.getOfficeId());
            pstmt.setInt(8, dto.getEmployeeId());
            pstmt.setInt(9, dto.getAdministrationId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dto.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza únicamente el status buscando por payment_id.
     */
    public boolean updateStatusByPaymentId(JDBConnection conn, int paymentId, int newStatus) throws SQLException {
        String sql = "UPDATE pym_operation SET status = ? WHERE payment_id = ?";
        try (PreparedStatement pstmt = conn.getNewPreparedStatement(sql)) {
            pstmt.setInt(1, newStatus);
            pstmt.setInt(2, paymentId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Retorna una lista de registros encontrados por payment_id.
     */
    public List<OperationDTO> selectByPaymentId(JDBConnection conn, int paymentId) throws SQLException {
        List<OperationDTO> list = new ArrayList<>();
        String sql = "SELECT id, payment_id, description, operation, amount, status, "
                + "commitee_id, office_id, employee_id, administration_id, date_register "
                + "FROM pym_operation WHERE payment_id = ?";

        try (PreparedStatement pstmt = conn.getNewPreparedStatement(sql)) {
            pstmt.setInt(1, paymentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OperationDTO dto = new OperationDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setPaymentId(rs.getInt("payment_id"));
                    dto.setDescription(rs.getString("description"));
                    dto.setOperation(rs.getString("operation"));
                    dto.setAmount(rs.getBigDecimal("amount"));
                    dto.setStatus(rs.getInt("status"));
                    dto.setCommiteeId(rs.getInt("commitee_id"));
                    dto.setOfficeId(rs.getInt("office_id"));
                    dto.setEmployeeId(rs.getInt("employee_id"));
                    dto.setAdministrationId(rs.getInt("administration_id"));
                    dto.setDateRegister(rs.getTimestamp("date_register").toLocalDateTime());

                    list.add(dto);
                }
            }
        }
        return list;
    }
}
