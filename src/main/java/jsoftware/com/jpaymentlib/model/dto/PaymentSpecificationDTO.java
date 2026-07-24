package jsoftware.com.jpaymentlib.model.dto;

import java.util.Map;
import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jutil.db.JDBMapObject;

/**
 * DTO correspondiente a la asignación y vigencia de las reglas de negocio de los 
 * conceptos de pago (PaymentRulers).
 * <br>
 * Utiliza la estructura interna dinámica de Map para transportar las cadenas de
 * texto de manera flexible entre los formularios de Swing y la capa DAO de
 * MySQL.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-07-18
 * @version 1.1
 */
public class PaymentSpecificationDTO extends JDBMapObject {

    private static final long serialVersionUID = 1L;

    public PaymentSpecificationDTO(Map<String, Object> map) {
        super(map);
    }

    public PaymentSpecificationDTO() {
        // Inicializa con una capacidad de 22 para albergar eficientemente los 14 campos restantes
        // (excluyendo el 'id' gestionado por el padre), evitando redimensionamientos en memoria.
        super(22);
    }

    // Se omite getId() -> Heredado directamente de JDBMapObject de forma limpia

    public String getSequence() {
        return Func.nullSafeToString(get("sequence"));
    }

    public String getConceptId() {
        return Func.nullSafeToString(get("concept_id"));
    }

    public String getImportId() {
        return Func.nullSafeToString(get("import_id"));
    }

    public String getRulerId() {
        return Func.nullSafeToString(get("ruler_id"));
    }

    public String getFiscalYear() {
        return Func.nullSafeToString(get("fiscal_year"));
    }

    public String getDateStartApplication() {
        return Func.nullSafeToString(get("date_start_application"));
    }

    public String getDateEndApplication() {
        return Func.nullSafeToString(get("date_end_application"));
    }

    public String getStatus() {
        return Func.nullSafeToString(get("status"));
    }

    public String getOfficeId() {
        return Func.nullSafeToString(get("office_id"));
    }

    public String getAdministrationId() {
        return Func.nullSafeToString(get("administration_id"));
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