/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author juanp
 */
public class PaymentLinesDTO implements Serializable, Comparable<PaymentLinesDTO> {

    private long id;
    private int payment_id;
    private String payment_line;
    private BigDecimal amount_due;
    private LocalDateTime timestamp_emission;
    private LocalDateTime timestamp_expiration;
    private LocalDateTime timestamp_paid;
    private String bank_institution;
    private String bank_transaction_id;
    private int employee_id;
    private int status;
    private LocalDateTime date_update;
    private LocalDateTime date_register;
    private LocalDateTime date_end;

    public PaymentLinesDTO() {
        this.id = -1;
        this.payment_id = -1;
        this.payment_line = null;
        this.amount_due = null;
        this.timestamp_emission = null;
        this.timestamp_expiration = null;
        this.timestamp_paid = null;
        this.bank_institution = null;
        this.bank_transaction_id = null;
        this.employee_id = -1;
        this.status = -1;
        this.date_update = null;
        this.date_register = null;
        this.date_end = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_line() {
        return payment_line;
    }

    public void setPayment_line(String payment_line) {
        this.payment_line = payment_line;
    }

    public BigDecimal getAmount_due() {
        return amount_due;
    }

    public void setAmount_due(BigDecimal amount_due) {
        this.amount_due = amount_due;
    }

    public LocalDateTime getTimestamp_emission() {
        return timestamp_emission;
    }

    public void setTimestamp_emission(LocalDateTime timestamp_emission) {
        this.timestamp_emission = timestamp_emission;
    }

    public LocalDateTime getTimestamp_expiration() {
        return timestamp_expiration;
    }

    public void setTimestamp_expiration(LocalDateTime timestamp_expiration) {
        this.timestamp_expiration = timestamp_expiration;
    }

    public LocalDateTime getTimestamp_paid() {
        return timestamp_paid;
    }

    public void setTimestamp_paid(LocalDateTime timestamp_paid) {
        this.timestamp_paid = timestamp_paid;
    }

    public String getBank_institution() {
        return bank_institution;
    }

    public void setBank_institution(String bank_institution) {
        this.bank_institution = bank_institution;
    }

    public String getBank_transaction_id() {
        return bank_transaction_id;
    }

    public void setBank_transaction_id(String bank_transaction_id) {
        this.bank_transaction_id = bank_transaction_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getDate_update() {
        return date_update;
    }

    public void setDate_update(LocalDateTime date_update) {
        this.date_update = date_update;
    }

    public LocalDateTime getDate_register() {
        return date_register;
    }

    public void setDate_register(LocalDateTime date_register) {
        this.date_register = date_register;
    }

    public LocalDateTime getDate_end() {
        return date_end;
    }

    public void setDate_end(LocalDateTime date_end) {
        this.date_end = date_end;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PaymentLinesDTO other = (PaymentLinesDTO) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentLinesDTO{");
        sb.append("id=").append(id);
        sb.append(", payment_id=").append(payment_id);
        sb.append(", payment_line=").append(payment_line);
        sb.append(", amount_due=").append(amount_due);
        sb.append(", timestamp_emission=").append(timestamp_emission);
        sb.append(", timestamp_expiration=").append(timestamp_expiration);
        sb.append(", timestamp_paid=").append(timestamp_paid);
        sb.append(", bank_institution=").append(bank_institution);
        sb.append(", bank_transaction_id=").append(bank_transaction_id);
        sb.append(", employee_id=").append(employee_id);
        sb.append(", status=").append(status);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(PaymentLinesDTO o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
