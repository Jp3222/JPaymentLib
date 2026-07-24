package jsoftware.com.jpaymentlib.model.dto;

import java.util.Map;
import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jutil.db.JDBMapObject;

/**
 * DTO correspondiente a la información del detalle de los pagos del sistema
 * (PaymentDetail).
 * <br>
 * Utiliza la estructura interna dinámica de Map para transportar las cadenas de
 * texto de manera flexible entre los formularios de Swing y la capa DAO de
 * MySQL.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-07-18
 * @version 1.0
 */
public class PaymentDetailDTO extends JDBMapObject {

    private static final long serialVersionUID = 1L;

    public PaymentDetailDTO(Map<String, Object> map) {
        super(map);
    }

    public PaymentDetailDTO() {
        // Inicializa con una capacidad de 24 para albergar eficientemente los 17 campos restantes
        // (excluyendo el 'id' gestionado por el padre), evitando redimensionamientos en memoria.
        super(24);
    }

    // Se omite getId() -> Heredado directamente de JDBMapObject de forma limpia
    public String getPaymentHeaderId() {
        return Func.nullSafeToString(get("payment_header_id"));
    }

    public String getSequence() {
        return Func.nullSafeToString(get("sequence"));
    }

    public String getUuid() {
        return Func.nullSafeToString(get("uuid"));
    }

    public String getPaymentConceptId() {
        return Func.nullSafeToString(get("payment_concept_id"));
    }

    public String getSpecificationId() {
        return Func.nullSafeToString(get("specification_id"));
    }

    public String getPeriodDate() {
        return Func.nullSafeToString(get("period_date"));
    }

    public String getFiscalYear() {
        return Func.nullSafeToString(get("fiscal_year"));
    }

    public String getQuantity() {
        return Func.nullSafeToString(get("quantity"));
    }

    public String getUnitPrice() {
        return Func.nullSafeToString(get("unit_price"));
    }

    public String getSubtotal() {
        return Func.nullSafeToString(get("subtotal"));
    }

    public String getDiscount() {
        return Func.nullSafeToString(get("discount"));
    }

    public String getSurcharge() {
        return Func.nullSafeToString(get("surcharge"));
    }

    public String getTotalAmount() {
        return Func.nullSafeToString(get("total_amount"));
    }

    public String getStatus() {
        return Func.nullSafeToString(get("status"));
    }

    public String getDateUpdate() {
        return Func.nullSafeToString(get("date_update"));
    }

    public String getDateRegister() {
        return Func.nullSafeToString(get("date_register"));
    }

    public String getDateEnd() {
        return Func.nullSafeToString(get("date_end"));
    }

    @Override
    public String toString() {
        // Garantiza una salida segura en tus logs de Log4j2 sin riesgo de NullPointerException
        return (values != null) ? values.toString() : "{}";
    }
}
