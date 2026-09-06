/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto;

import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jutil.db.JDBMapObject;

/**
 *
 * @author juanp
 */
public class PaymentLinesDTO extends JDBMapObject {

    public String getPaymentId() {
        return Func.nullSafeToString(get("payment_id"));
    }

    public String getPaymentLine() {
        return Func.nullSafeToString(get("payment_line"));
    }

    public String getAmountDue() {
        return Func.nullSafeToString(get("amount_due"));
    }

    public String getTimestampEmission() {
        return Func.nullSafeToString(get("timestamp_emission"));
    }

    public String getTimestampExpiration() {
        return Func.nullSafeToString(get("timestamp_expiration"));
    }

    public String getTimestampPaid() {
        return Func.nullSafeToString(get("timestamp_paid"));
    }

    public String getBankInstitution() {
        return Func.nullSafeToString(get("bank_institution"));
    }

    public String getBankTransaction_id() {
        return Func.nullSafeToString(get("bank_transaction_id"));
    }

    public String getEmployeeId() {
        return Func.nullSafeToString(get("employee_id"));
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
}
