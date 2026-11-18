package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;
import jsoftware.com.jpaymentlib.model.dto.WaterIntakePaymentUserDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 * DAO para la gestión y persistencia de WaterIntakePaymentUserDTO.
 *
 * @author juan pablo campos casasanero
 */
public class WaterIntakePaymentUserDAO {

    /**
     * Inserta un nuevo registro y enriquece el DTO con el ID autogenerado.
     */
    public boolean insert(JDBConnection connection, WaterIntakePaymentUserDTO dto) throws SQLException {
        boolean success = false;
        String sql = "INSERT INTO wki_pym_user ("
                + "wki_id, fiscal_year, ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic, status, date_end"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, dto.getWki_id());
            pstmt.setInt(2, dto.getFiscal_year());
            pstmt.setBoolean(3, dto.isEne());
            pstmt.setBoolean(4, dto.isFeb());
            pstmt.setBoolean(5, dto.isMar());
            pstmt.setBoolean(6, dto.isAbr());
            pstmt.setBoolean(7, dto.isMay());
            pstmt.setBoolean(8, dto.isJun());
            pstmt.setBoolean(9, dto.isJul());
            pstmt.setBoolean(10, dto.isAgo());
            pstmt.setBoolean(11, dto.isSep());
            pstmt.setBoolean(12, dto.isOct());
            pstmt.setBoolean(13, dto.isNov());
            pstmt.setBoolean(14, dto.isDic());
            pstmt.setInt(15, dto.getStatus());
            if (dto.getDate_end() != null) {
                pstmt.setTimestamp(16, Timestamp.valueOf(dto.getDate_end()));
            } else {
                pstmt.setNull(16, java.sql.Types.TIMESTAMP);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dto.setId(generatedKeys.getInt(1));
                    }
                }
                success = true;
            }

        }
        return success;
    }

    /**
     * Actualiza únicamente el estatus buscando por el ID de la toma (wki_id).
     */
    public boolean updateStatusByWkiId(JDBConnection connection, int wkiId, int fiscal_year, int status) throws SQLException {
        boolean success = false;
        String sql = "UPDATE wki_pym_user SET status = ? WHERE wki_id = ? AND fiscal_year = ?";
        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setInt(1, status);
            pstmt.setInt(2, wkiId);
            pstmt.setInt(3, fiscal_year);
            int affectedRows = pstmt.executeUpdate();
            success = (affectedRows > 0);
        }
        return success;
    }

    /**
     * Actualiza únicamente los 12 meses (banderas de pago) buscando por wki_id
     * y año fiscal.
     */
    public boolean updateMonths(JDBConnection connection, WaterIntakePaymentUserDTO dto) throws SQLException {
        boolean success = false;
        String sql = "UPDATE wki_pym_user SET "
                + "ene = ?, feb = ?, mar = ?, abr = ?, may = ?, jun = ?, "
                + "jul = ?, ago = ?, sep = ?, oct = ?, nov = ?, dic = ? "
                + "WHERE wki_id = ? AND fiscal_year = ? AND status = 1";

        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setBoolean(1, dto.isEne());
            pstmt.setBoolean(2, dto.isFeb());
            pstmt.setBoolean(3, dto.isMar());
            pstmt.setBoolean(4, dto.isAbr());
            pstmt.setBoolean(5, dto.isMay());
            pstmt.setBoolean(6, dto.isJun());
            pstmt.setBoolean(7, dto.isJul());
            pstmt.setBoolean(8, dto.isAgo());
            pstmt.setBoolean(9, dto.isSep());
            pstmt.setBoolean(10, dto.isOct());
            pstmt.setBoolean(11, dto.isNov());
            pstmt.setBoolean(12, dto.isDic());
            pstmt.setInt(13, dto.getWki_id());
            pstmt.setInt(14, dto.getFiscal_year());
            int affectedRows = pstmt.executeUpdate();
            success = (affectedRows > 0);
        }

        return success;
    }

    /**
     * Busca y retorna un registro filtrado por wki_id y año fiscal.
     */
    public Optional<WaterIntakePaymentUserDTO> selectByWkiIdAndFiscalYear(JDBConnection connection, int wkiId, int fiscalYear) throws SQLException {
        String sql = "SELECT id, wki_id, fiscal_year, ene, feb, mar, abr, may, jun, "
                + "jul, ago, sep, oct, nov, dic, status, date_update, date_register, date_end "
                + "FROM wki_pym_user "
                + "WHERE wki_id = ? AND fiscal_year = ? AND status = 1";
        try (PreparedStatement pstmt = connection.getNewPreparedStatement(sql)) {
            pstmt.setInt(1, wkiId);
            pstmt.setInt(2, fiscalYear);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    WaterIntakePaymentUserDTO dto = new WaterIntakePaymentUserDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setWki_id(rs.getInt("wki_id"));
                    dto.setFiscal_year(rs.getInt("fiscal_year"));
                    dto.setEne(rs.getBoolean("ene"));
                    dto.setFeb(rs.getBoolean("feb"));
                    dto.setMar(rs.getBoolean("mar"));
                    dto.setAbr(rs.getBoolean("abr"));
                    dto.setMay(rs.getBoolean("may"));
                    dto.setJun(rs.getBoolean("jun"));
                    dto.setJul(rs.getBoolean("jul"));
                    dto.setAgo(rs.getBoolean("ago"));
                    dto.setSep(rs.getBoolean("sep"));
                    dto.setOct(rs.getBoolean("oct"));
                    dto.setNov(rs.getBoolean("nov"));
                    dto.setDic(rs.getBoolean("dic"));
                    dto.setStatus(rs.getInt("status"));

                    Timestamp updTs = rs.getTimestamp("date_update");
                    if (updTs != null) {
                        dto.setDate_update(updTs.toLocalDateTime());
                    }

                    Timestamp regTs = rs.getTimestamp("date_register");
                    if (regTs != null) {
                        dto.setDate_register(regTs.toLocalDateTime());
                    }

                    Timestamp endTs = rs.getTimestamp("date_end");
                    if (endTs != null) {
                        dto.setDate_end(endTs.toLocalDateTime());
                    }

                    return Optional.of(dto);
                }
            }
        }
        return Optional.empty();
    }
}
