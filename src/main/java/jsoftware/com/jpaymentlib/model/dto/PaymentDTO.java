/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author juanp
 */
public class PaymentDTO implements Serializable, Comparable<PaymentDTO> {

    private static final long serialVersionUID = 1L;

    private int id;
    private int user_id;
    private int payment_type_id;
    private int payment_method_id;
    private String sequence;
    private String line;
    private String observation;
    private int status;
    private int committee_id;
    private int office_id;
    private int last_employee_update;
    private LocalDateTime date_update;
    private LocalDateTime date_register;
    private LocalDateTime date_end;

    public PaymentDTO() {
        this.id = -1;
        this.user_id = -1;
        this.payment_type_id = -1;
        this.payment_method_id = -1;
        this.sequence = null;
        this.line = null;
        this.observation = null;
        this.status = -1;
        this.committee_id = -1;
        this.office_id = -1;
        this.last_employee_update = -1;
        this.date_update = null;
        this.date_register = null;
        this.date_end = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(int payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCommittee_id() {
        return committee_id;
    }

    public void setCommittee_id(int committee_id) {
        this.committee_id = committee_id;
    }

    public int getOffice_id() {
        return office_id;
    }

    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    public int getLast_employee_update() {
        return last_employee_update;
    }

    public void setLast_employee_update(int last_employee_update) {
        this.last_employee_update = last_employee_update;
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
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.sequence);
        hash = 37 * hash + Objects.hashCode(this.line);
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
        final PaymentDTO other = (PaymentDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.sequence, other.sequence)) {
            return false;
        }
        return Objects.equals(this.line, other.line);
    }

  
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentDTO{");
        sb.append("id=").append(id);
        sb.append(", user_id=").append(user_id);
        sb.append(", payment_type_id=").append(payment_type_id);
        sb.append(", payment_method_id=").append(payment_method_id);
        sb.append(", sequence=").append(sequence);
        sb.append(", line=").append(line);
        sb.append(", observation=").append(observation);
        sb.append(", status=").append(status);
        sb.append(", committee_id=").append(committee_id);
        sb.append(", office_id=").append(office_id);
        sb.append(", last_employee_update=").append(last_employee_update);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(PaymentDTO o) {
        return Integer.compare(this.id, o.id);
    }

}
