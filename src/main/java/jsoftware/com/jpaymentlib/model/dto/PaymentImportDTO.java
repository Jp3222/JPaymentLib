package jsoftware.com.jpaymentlib.model.dto;

import java.util.Map;
import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jutil.db.JDBMapObject;

/**
 * DTO correspondiente a la información detallada de los importes, recargos y
 * subsidios asociados a los conceptos de pago (PaymentImport).
 * <br>
 * Utiliza la estructura interna dinámica de Map para transportar las cadenas de
 * texto de manera flexible entre los formularios de Swing y la capa DAO de
 * MySQL.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-07-18
 * @version 1.2
 */
public class PaymentImportDTO extends JDBMapObject {

    private static final long serialVersionUID = 1L;

    public PaymentImportDTO(Map<String, Object> map) {
        super(map);
    }

    public PaymentImportDTO() {
        // Inicializa con una capacidad de 24 para albergar eficientemente los 15 campos restantes
        // (excluyendo el 'id' gestionado por el padre), evitando redimensionamientos en memoria.
        super(24);
    }

    // Se omite getId() -> Heredado directamente de JDBMapObject de forma limpia

    public String getConceptId() {
        return Func.nullSafeToString(get("concept_id"));
    }

    public String getAdministrationId() {
        return Func.nullSafeToString(get("administration_id"));
    }

    public String getCurrency() {
        return Func.nullSafeToString(get("currency"));
    }

    public String getAmount() {
        return Func.nullSafeToString(get("amount"));
    }

    public String getSurcharge() {
        return Func.nullSafeToString(get("surcharge"));
    }

    public String getDiscount() {
        return Func.nullSafeToString(get("discount"));
    }

    public String getSubsidy() {
        return Func.nullSafeToString(get("subsidy"));
    }

    public String getIsVariable() {
        return Func.nullSafeToString(get("is_variable"));
    }

    public String getUnitId() {
        return Func.nullSafeToString(get("unit_id"));
    }

    public String getUnits() {
        return Func.nullSafeToString(get("units"));
    }

    public String getStatus() {
        return Func.nullSafeToString(get("status"));
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