package jsoftware.com.jpaymentlib.model.dto;

import java.util.Map;
import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jutil.db.JDBMapObject;

/**
 * DTO correspondiente a las reglas de negocio, afectaciones financieras y
 * temporalidades de los conceptos de pago (PaymentRulers).
 * <br>
 * Utiliza la estructura interna dinámica de Map para transportar las cadenas de
 * texto de manera flexible entre los formularios de Swing y la capa DAO de
 * MySQL.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-07-18
 * @version 1.2
 */
public class PaymentRulersDTO extends JDBMapObject {

    private static final long serialVersionUID = 1L;

    public PaymentRulersDTO(Map<String, Object> map) {
        super(map);
    }

    public PaymentRulersDTO() {
        // Inicializa con una capacidad de 29 para albergar eficientemente los 19 campos restantes
        // (excluyendo el 'id' gestionado por el padre), evitando redimensionamientos en memoria.
        super(29);
    }

    // Se omite getId() -> Heredado directamente de JDBMapObject de forma limpia
    public String getConceptId() {
        return Func.nullSafeToString(get("concept_id"));
    }

    public String getSequence() {
        return Func.nullSafeToString(get("sequence"));
    }

    public String getName() {
        return Func.nullSafeToString(get("name"));
    }

    public String getRulerType() {
        return Func.nullSafeToString(get("ruler_type"));
    }

    public String getValueType() {
        return Func.nullSafeToString(get("value_type"));
    }

    public String getValue() {
        return Func.nullSafeToString(get("value"));
    }

    public String getRound() {
        return Func.nullSafeToString(get("round"));
    }

    public String getApplyRulers() {
        return Func.nullSafeToString(get("apply_rulers"));
    }

    public String getRounUp() {
        return Func.nullSafeToString(get("round_up"));
    }

    public String getApplyDiscount() {
        return Func.nullSafeToString(get("apply_discount"));
    }

    public String getApplySurcharge() {
        return Func.nullSafeToString(get("apply_surcharge"));
    }

    public String getApplySubsidy() {
        return Func.nullSafeToString(get("apply_subsidy"));
    }

    public String getIsMandatory() {
        return Func.nullSafeToString(get("is_mandatory"));
    }

    public String getHasAutomaticIncrement() {
        return Func.nullSafeToString(get("has_automatic_increment"));
    }

    public String getIncrementPeriod() {
        return Func.nullSafeToString(get("increment_period"));
    }

    public String getPayday() {
        return Func.nullSafeToString(get("payday"));
    }

    public String getDaysCovered() {
        return Func.nullSafeToString(get("days_covered"));
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
