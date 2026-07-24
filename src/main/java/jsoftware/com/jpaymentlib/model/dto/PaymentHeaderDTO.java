package jsoftware.com.jpaymentlib.model.dto;

import java.util.Map;
import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jutil.db.JDBMapObject;

/**
 * DTO correspondiente a la información de la cabecera de los pagos del sistema
 * (PaymentHeader).
 * <br>
 * Utiliza la estructura interna dinámica de Map para transportar las cadenas de
 * texto de manera flexible entre los formularios de Swing y la capa DAO de
 * MySQL.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-07-18
 * @version 1.0
 */
public class PaymentHeaderDTO extends JDBMapObject {

    private static final long serialVersionUID = 1L;

    public PaymentHeaderDTO(Map<String, Object> map) {
        super(map);
    }

    public PaymentHeaderDTO() {
        // Inicializa con una capacidad de 36 para albergar eficientemente los 25 campos restantes
        // (excluyendo el 'id' gestionado por el padre), evitando redimensionamientos en memoria.
        super(36);
    }

    // Se omite getId() -> Heredado directamente de JDBMapObject de forma limpia

    public String getProcessId() {
        return Func.nullSafeToString(get("process_id"));
    }

    public String getSequence() {
        return Func.nullSafeToString(get("sequence"));
    }

    public String getUuid() {
        return Func.nullSafeToString(get("uuid"));
    }

    public String getUserId() {
        return Func.nullSafeToString(get("user_id"));
    }

    public String getWkiUserId() {
        return Func.nullSafeToString(get("wki_user_id"));
    }

    public String getOfficeId() {
        return Func.nullSafeToString(get("office_id"));
    }

    public String getCashBoxTurnId() {
        return Func.nullSafeToString(get("cash_box_turn_id"));
    }

    public String getAuthorizedBy() {
        return Func.nullSafeToString(get("authorized_by"));
    }

    public String getPaymentMethodId() {
        return Func.nullSafeToString(get("payment_method_id"));
    }

    public String getPaymentTypeId() {
        return Func.nullSafeToString(get("payment_type_id"));
    }

    public String getSubtotal() {
        return Func.nullSafeToString(get("subtotal"));
    }

    public String getDiscount() {
        return Func.nullSafeToString(get("discount"));
    }

    public String getTotalSurcharge() {
        return Func.nullSafeToString(get("total_surcharge"));
    }

    public String getTotalAmount() {
        return Func.nullSafeToString(get("total_amount"));
    }

    public String getAmountReceived() {
        return Func.nullSafeToString(get("amount_received"));
    }

    public String getChangeReturned() {
        return Func.nullSafeToString(get("change_returned"));
    }

    public String getIsSurchargePaid() {
        return Func.nullSafeToString(get("is_surcharge_paid"));
    }

    public String getTransactionReference() {
        return Func.nullSafeToString(get("transaction_reference"));
    }

    public String getCancellationReason() {
        return Func.nullSafeToString(get("cancellation_reason"));
    }

    public String getPrintCount() {
        return Func.nullSafeToString(get("print_count"));
    }

    public String getStatus() {
        return Func.nullSafeToString(get("status"));
    }

    public String getEmployeeId() {
        return Func.nullSafeToString(get("employee_id"));
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