package jsoftware.com.jpaymentlib.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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
public class PaymentHeaderDTO implements Serializable, Comparable<PaymentHeaderDTO> {

    private long id;
    private long payment_id;
    private String uuid;
    private int fiscal_year;
    private int cash_box_turn_id;
    private int authorized_by;
    private boolean apply_discount;
    private boolean apply_surcharge;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal surcharge;
    private BigDecimal total_amount;
    private BigDecimal amount_received;
    private BigDecimal change_returned;
    private String transaction_reference;
    private int status;
    private LocalDateTime date_update;
    private LocalDateTime date_register;
    private LocalDateTime date_end;

    public PaymentHeaderDTO() {
        this.id = -1;
        this.payment_id = -1;
        this.uuid = null;
        this.fiscal_year = -1;
        this.cash_box_turn_id = -1;
        this.authorized_by = -1;
        this.apply_discount = false;
        this.apply_surcharge = false;
        this.subtotal = null;
        this.discount = null;
        this.surcharge = null;
        this.total_amount = null;
        this.amount_received = null;
        this.change_returned = null;
        this.transaction_reference = null;
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

    public long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(long payment_id) {
        this.payment_id = payment_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getFiscal_year() {
        return fiscal_year;
    }

    public void setFiscal_year(int fiscal_year) {
        this.fiscal_year = fiscal_year;
    }

    public int getCash_box_turn_id() {
        return cash_box_turn_id;
    }

    public void setCash_box_turn_id(int cash_box_turn_id) {
        this.cash_box_turn_id = cash_box_turn_id;
    }

    public int getAuthorized_by() {
        return authorized_by;
    }

    public void setAuthorized_by(int authorized_by) {
        this.authorized_by = authorized_by;
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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public BigDecimal getAmount_received() {
        return amount_received;
    }

    public void setAmount_received(BigDecimal amount_received) {
        this.amount_received = amount_received;
    }

    public BigDecimal getChange_returned() {
        return change_returned;
    }

    public void setChange_returned(BigDecimal change_returned) {
        this.change_returned = change_returned;
    }

    public String getTransaction_reference() {
        return transaction_reference;
    }

    public void setTransaction_reference(String transaction_reference) {
        this.transaction_reference = transaction_reference;
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
        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 71 * hash + Objects.hashCode(this.uuid);
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
        final PaymentHeaderDTO other = (PaymentHeaderDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.uuid, other.uuid);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nPaymentHeaderDTO{");
        sb.append("\n id=").append(id);
        sb.append(",\n payment_id=").append(payment_id);
        sb.append(",\n uuid=").append(uuid);
        sb.append(",\n fiscal_year=").append(fiscal_year);
        sb.append(",\n cash_box_turn_id=").append(cash_box_turn_id);
        sb.append(",\n authorized_by=").append(authorized_by);
        sb.append(",\n apply_discount=").append(apply_discount);
        sb.append(",\n apply_surcharge=").append(apply_surcharge);
        sb.append(",\n subtotal=").append(subtotal);
        sb.append(",\n discount=").append(discount);
        sb.append(",\n total_surcharge=").append(surcharge);
        sb.append(",\n total_amount=").append(total_amount);
        sb.append(",\n amount_received=").append(amount_received);
        sb.append(",\n change_returned=").append(change_returned);
        sb.append(",\n transaction_reference=").append(transaction_reference);
        sb.append(",\n status=").append(status);
        sb.append(",\n date_update=").append(date_update);
        sb.append(",\n date_register=").append(date_register);
        sb.append(",\n date_end=").append(date_end);
        sb.append("\n}");
        return sb.toString();
    }

    @Override
    public int compareTo(PaymentHeaderDTO o) {
        return Long.compare(this.id, o.id);
    }

}
