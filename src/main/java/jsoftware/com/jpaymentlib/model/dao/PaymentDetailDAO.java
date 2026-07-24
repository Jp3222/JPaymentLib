/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.model.AbstractDAO;

/**
 *
 * @author juanp
 */
public class PaymentDetailDAO extends AbstractDAO {

    public PaymentDetailDAO(boolean flag_dev_log, String name_module) {
        super(flag_dev_log, name_module);
    }

    public boolean insert(JDBConnection connection, List<PaymentDetailDTO> list) throws SQLException {
        // Si la lista está vacía, evitamos abrir recursos innecesariamente
        if (list == null || list.isEmpty()) {
            return false;
        }
        // Corrección de sintaxis en la consulta: se elimina la coma final antes del paréntesis de cierre
        String query = """
                       INSERT INTO pym_payment_detail
                       (
                       payment_header_id, sequence, uuid, payment_concept_id, 
                       specification_id, period_date, fiscal_year, quantity, 
                       unit_price, subtotal, discount, surcharge, total_amount, status
                       ) 
                       VALUES
                       (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                       """;

        try (PreparedStatement ps = connection.getConnection().prepareStatement(query);) {
            // Se prepara la sentencia utilizando la conexión activa de la transacción
            for (PaymentDetailDTO dto : list) {
                // ESTRATEGIA: La Vista maneja Strings, el DTO los transporta, el DAO realiza el cast final.
                // 1. payment_header_id (int)
                ps.setString(1, dto.getPaymentHeaderId());
                // 2. sequence (int)
                ps.setString(2, dto.getSequence());
                // 3. uuid (String)
                ps.setString(3, dto.getUuid());
                // 4. payment_concept_id (int)
                ps.setString(4, dto.getPaymentConceptId());
                // 5. specification_id (int)
                ps.setString(5, dto.getSpecificationId());
                // 6. period_date (String / Date en la BD)
                ps.setString(6, dto.getPeriodDate());
                // 7. fiscal_year (int)
                ps.setString(7, dto.getFiscalYear());
                // 8. quantity (double / int según tu regla de negocio de importes medidos o fijos)
                ps.setString(8, dto.getQuantity());
                // 9. unit_price (double / decimal)
                ps.setString(9, dto.getUnitPrice());
                // 10. subtotal (double / decimal)
                ps.setString(10, dto.getSubtotal());
                // 11. discount (double / decimal)
                ps.setString(11, dto.getDiscount());
                // 12. surcharge (double / decimal)
                ps.setString(12, dto.getSurcharge());
                // 13. total_amount (double / decimal)
                ps.setString(13, dto.getTotalAmount());
                // 14. status (String / char)
                ps.setString(14, dto.getStatus());
                // Se agrega el registro actual al lote de comandos de la base de datos
                ps.addBatch();
            }
            // Se ejecuta todo el bloque en una sola petición a MySQL
            int[] results = ps.executeBatch();
            // Verificamos que se hayan procesado todos los registros esperados
            return results.length == list.size();
        }
    }
}
