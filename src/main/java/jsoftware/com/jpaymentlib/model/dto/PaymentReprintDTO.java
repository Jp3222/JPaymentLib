package jsoftware.com.jpaymentlib.model.dto;

import java.time.LocalDateTime;

/**
 * DTO para la tabla pym_payment_reprint. Auditoría y control de reimpresiones
 * de pagos.
 *
 * @author juan pablo campos casasanero
 */
public class PaymentReprintDTO {

    private int id;
    private int payment_id;
    private int reprint_count;
    private String observation;
    private int employee_id;
    private int status;
    private LocalDateTime date_register;
    private LocalDateTime date_update;
    private LocalDateTime date_end;

    public PaymentReprintDTO() {
        this.id = -1;
        this.payment_id = -1;
        this.reprint_count = 0;
        this.observation = null;
        this.employee_id = -1;
        this.status = 1;
        this.date_register = null;
        this.date_update = null;
        this.date_end = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getReprint_count() {
        return reprint_count;
    }

    public void setReprint_count(int reprint_count) {
        this.reprint_count = reprint_count;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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

    public LocalDateTime getDate_register() {
        return date_register;
    }

    public void setDate_register(LocalDateTime date_register) {
        this.date_register = date_register;
    }

    public LocalDateTime getDate_update() {
        return date_update;
    }

    public void setDate_update(LocalDateTime date_update) {
        this.date_update = date_update;
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
        hash = 41 * hash + this.id;
        hash = 41 * hash + this.payment_id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PaymentReprintDTO other = (PaymentReprintDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return this.payment_id == other.payment_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentReprintDTO{");
        sb.append("id=").append(id);
        sb.append(", payment_id=").append(payment_id);
        sb.append(", reprint_count=").append(reprint_count);
        sb.append(", observation=").append(observation);
        sb.append(", employee_id=").append(employee_id);
        sb.append(", status=").append(status);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }
}
