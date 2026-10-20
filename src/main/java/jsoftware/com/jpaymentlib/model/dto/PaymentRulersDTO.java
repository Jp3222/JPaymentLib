package jsoftware.com.jpaymentlib.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * dTO que representa la tabla 'pym_payment_rulers'. Transporta los datos de
 * configuración y reglas de cálculo de cobros.
 *
 * @author JuAN PABLO CAMPOS CASASANERO
 * @version 1.0
 */
public class PaymentRulersDTO implements Serializable, Comparable<PaymentRulersDTO> {

    private static final long serialVersionuId = 1L;

    private int id;
    private String name;
    private String ruler_type;
    private String value_type;
    private BigDecimal value;
    private boolean editable;
    private boolean apply_rulers;
    private boolean round;
    private boolean round_up;
    private boolean apply_discount;
    private boolean apply_surcharge;
    private boolean apply_subsidy;
    private boolean is_Mandatory;
    private boolean has_automatic_increment;
    private BigDecimal increment_period;
    private int days_to_paid;
    private int payday;
    private int days_covered;
    private int status;
    private int last_employee_update;
    private LocalDateTime date_update;
    private LocalDateTime date_register;
    private LocalDateTime date_end;

    public PaymentRulersDTO() {
        this.id = -1;
        this.name = null;
        this.ruler_type = null;
        this.value_type = null;
        this.value = null;
        this.editable = false;
        this.apply_rulers = false;
        this.round = false;
        this.round_up = false;
        this.apply_discount = false;
        this.apply_surcharge = false;
        this.apply_subsidy = false;
        this.is_Mandatory = false;
        this.has_automatic_increment = false;
        this.increment_period = null;
        this.days_to_paid = -1;
        this.payday = -1;
        this.days_covered = -1;
        this.status = -1;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuler_type() {
        return ruler_type;
    }

    public void setRuler_type(String ruler_type) {
        this.ruler_type = ruler_type;
    }

    public String getValue_type() {
        return value_type;
    }

    public void setValue_type(String value_type) {
        this.value_type = value_type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isApply_rulers() {
        return apply_rulers;
    }

    public void setApply_rulers(boolean apply_rulers) {
        this.apply_rulers = apply_rulers;
    }

    public boolean isRound() {
        return round;
    }

    public void setRound(boolean round) {
        this.round = round;
    }

    public boolean isRound_up() {
        return round_up;
    }

    public void setRound_up(boolean round_up) {
        this.round_up = round_up;
    }

    public boolean isApply_discount() {
        return apply_discount;
    }

    public void setApply_discount(boolean apply_discount) {
        this.apply_discount = apply_discount;
    }

    public boolean isApply_surcharge() {
        return apply_surcharge;
    }

    public void setApply_surcharge(boolean apply_surcharge) {
        this.apply_surcharge = apply_surcharge;
    }

    public boolean isApply_subsidy() {
        return apply_subsidy;
    }

    public void setApply_subsidy(boolean apply_subsidy) {
        this.apply_subsidy = apply_subsidy;
    }

    public boolean isIs_Mandatory() {
        return is_Mandatory;
    }

    public void setIs_Mandatory(boolean is_Mandatory) {
        this.is_Mandatory = is_Mandatory;
    }

    public boolean isHas_automatic_increment() {
        return has_automatic_increment;
    }

    public void setHas_automatic_increment(boolean has_automatic_increment) {
        this.has_automatic_increment = has_automatic_increment;
    }

    public BigDecimal getIncrement_period() {
        return increment_period;
    }

    public void setIncrement_period(BigDecimal increment_period) {
        this.increment_period = increment_period;
    }

    public int getDays_to_paid() {
        return days_to_paid;
    }

    public void setDays_to_paid(int days_to_paid) {
        this.days_to_paid = days_to_paid;
    }

    public int getPayday() {
        return payday;
    }

    public void setPayday(int payday) {
        this.payday = payday;
    }

    public int getDays_covered() {
        return days_covered;
    }

    public void setDays_covered(int days_covered) {
        this.days_covered = days_covered;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.name);
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
        final PaymentRulersDTO other = (PaymentRulersDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentRulersdTO{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", ruler_type=").append(ruler_type);
        sb.append(", value_type=").append(value_type);
        sb.append(", value=").append(value);
        sb.append(", editable=").append(editable);
        sb.append(", apply_rulers=").append(apply_rulers);
        sb.append(", round=").append(round);
        sb.append(", round_up=").append(round_up);
        sb.append(", apply_discount=").append(apply_discount);
        sb.append(", apply_surcharge=").append(apply_surcharge);
        sb.append(", apply_subsidy=").append(apply_subsidy);
        sb.append(", is_Mandatory=").append(is_Mandatory);
        sb.append(", has_automatic_increment=").append(has_automatic_increment);
        sb.append(", increment_period=").append(increment_period);
        sb.append(", days_to_paid=").append(days_to_paid);
        sb.append(", payday=").append(payday);
        sb.append(", days_covered=").append(days_covered);
        sb.append(", status=").append(status);
        sb.append(", last_cmployee_update=").append(last_employee_update);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(PaymentRulersDTO o) {
        if (o == null) {
            return 1;
        }
        return Integer.compare(this.id, o.id);
    }

}
