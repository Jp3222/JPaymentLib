package jsoftware.com.jpaymentlib.model.dto;

import java.util.Map;
import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jutil.db.JDBMapObject;

/**
 * DTO correspondiente a la información del catálogo de conceptos de pago
 * (PaymentConcept).
 * <br>
 * Utiliza la estructura interna dinámica de Map para transportar las cadenas de
 * texto de manera flexible entre los formularios de Swing y la capa DAO de
 * MySQL.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-07-18
 * @version 1.2
 */
public class PaymentConceptDTO extends JDBMapObject {

    private static final long serialVersionUID = 1L;

    public PaymentConceptDTO(Map<String, Object> map) {
        super(map);
    }

    public PaymentConceptDTO() {
        // Inicializa con una capacidad de 16 para albergar eficientemente los 10 campos restantes
        // (excluyendo el 'id' gestionado por el padre), evitando redimensionamientos en memoria.
        super(16);
    }

    // Se omite getId() -> Heredado directamente de JDBMapObject de forma limpia

    public String getSequence() {
        return Func.nullSafeToString(get("sequence"));
    }

    public String getName() {
        return Func.nullSafeToString(get("name"));
    }

    public String getDescription() {
        return Func.nullSafeToString(get("description"));
    }

    public String getBasedOnDocument() {
        return Func.nullSafeToString(get("based_on_document"));
    }

    public String getStatus() {
        return Func.nullSafeToString(get("status"));
    }

    public String getOfficeId() {
        return Func.nullSafeToString(get("office_id"));
    }

    public String getLastEmployeeUpdate() {
        return Func.nullSafeToString(get("last_employee_update"));
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