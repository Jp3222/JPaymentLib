package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jsoftware.com.jpaymentlib.model.dto.PaymentSpecificationDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 * DAO para gestionar las operaciones de lectura de especificaciones de pago
 * (pym_payment_specification) mapeando directamente hacia el DTO fuertemente
 * tipado.
 *
 * @author Juan Pablo Campos Casasanero
 */
public class PaymentSpecificationDAO {

    /**
     * Mapea una fila del ResultSet directamente al PaymentSpecificationDTO.
     *
     * @param rs ResultSet posicionado en un registro válido.
     * @return DTO poblado con tipos de datos nativos.
     * @throws SQLException Si ocurre un error al consultar el ResultSet.
     */
    private PaymentSpecificationDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        PaymentSpecificationDTO dto = new PaymentSpecificationDTO();

        dto.setId(rs.getInt("id"));
        dto.setConcept_id(rs.getInt("concept_id"));
        dto.setSequence(rs.getInt("sequence"));
        dto.setImport_id(rs.getInt("import_id"));
        dto.setRuler_id(rs.getInt("ruler_id"));
        dto.setFiscal_year(rs.getInt("fiscal_year"));

        // Mapeo directo a API java.time utilizando JDBC 4.2+
        dto.setDate_start_application(rs.getObject("date_start_application", LocalDate.class));
        dto.setDate_end_application(rs.getObject("date_end_application", LocalDate.class));

        dto.setStatus(rs.getInt("status"));
        dto.setOffice_id(rs.getInt("office_id"));

        // Manejo explícito de columna con soporte de valores NULL (Integer Wrapper)
        int adminId = rs.getInt("administration_id");
        dto.setAdministration_id(rs.wasNull() ? null : adminId);

        dto.setLast_employee_update(rs.getInt("last_employee_update"));

        // Mapeo directo de Timestamp a LocalDateTime
        dto.setDate_update(rs.getObject("date_update", LocalDateTime.class));
        dto.setDate_register(rs.getObject("date_register", LocalDateTime.class));
        dto.setDate_end(rs.getObject("date_end", LocalDateTime.class));

        return dto;
    }

    /**
     * Lee un listado de especificaciones de pago filtrado por sus IDs
     * principales (id IN (...)).
     *
     * @param connection Conexión activa a la base de datos.
     * @param specificationIds Lista de IDs de la especificación a consultar.
     * @return Lista de DTOs correspondientes a las especificaciones vigentes.
     * @throws SQLException Si ocurre un error durante la ejecución SQL.
     */
    public List<PaymentSpecificationDTO> getSpecificationList(JDBConnection connection, List<Integer> specificationIds) throws SQLException {
        if (specificationIds == null || specificationIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<PaymentSpecificationDTO> list = new ArrayList<>(specificationIds.size());

        // Construction de placeholders '?' para la cláusula IN (...)
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < specificationIds.size(); i++) {
            placeholders.append("?");
            if (i < specificationIds.size() - 1) {
                placeholders.append(",");
            }
        }

        String query = "SELECT * FROM pym_payment_specification WHERE id IN (" + placeholders.toString() + ") AND status = 1";

        try (PreparedStatement ps = connection.getConnection().prepareStatement(query)) {
            for (int i = 0; i < specificationIds.size(); i++) {
                ps.setInt(i + 1, specificationIds.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToDTO(rs));
                }
            }
        }

        return list;
    }

    /**
     * Lee una especificación de pago individual por su ID.
     *
     * @param connection Conexión activa a la base de datos.
     * @param id Identificador único de la especificación.
     * @return Optional con el DTO en caso de encontrarlo y estar activo.
     * @throws SQLException Si ocurre un error durante la ejecución SQL.
     */
    public Optional<PaymentSpecificationDTO> getSpecification(JDBConnection connection, int id) throws SQLException {
        String query = "SELECT * FROM pym_payment_specification WHERE id = ? AND status = 1";

        try (PreparedStatement ps = connection.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToDTO(rs));
                }
            }
        }

        return Optional.empty();
    }
}
