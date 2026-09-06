/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto;

import java.util.Map;
import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jutil.db.JDBMapObject;

/**
 *
 * @author juanp
 */
public class PaymentDTO extends JDBMapObject {

    private static final long serialVersionUID = 1L;

    public PaymentDTO(Map<String, Object> map) {
        super(map);
    }

    public PaymentDTO() {
        // Inicializa con una capacidad de 24 para albergar eficientemente los 17 campos restantes
        // (excluyendo el 'id' gestionado por el padre), evitando redimensionamientos en memoria.
        super(24);
    }

    public String getPaymentTypeId() {
        return Func.nullSafeToString(get("payment_type_id"));
    }

    public String getSequence() {
        return Func.nullSafeToString(get("sequence"));
    }

    public String getLine() {
        return Func.nullSafeToString(get("line"));
    }

    public String getStatus() {
        return Func.nullSafeToString(get("status"));
    }

    public String getDateRegister() {
        return Func.nullSafeToString(get("date_register"));
    }
}
