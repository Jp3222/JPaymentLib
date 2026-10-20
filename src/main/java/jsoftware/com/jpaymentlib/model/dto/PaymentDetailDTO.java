package jsoftware.com.jpaymentlib.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para la tabla pym_payment_detail. Transporta la información de desglose
 * de conceptos y meses pagados.
 *
 * @author Juan Pablo Campos Casasanero
 */
public class PaymentDetailDTO implements Serializable, Comparable<PaymentDetailDTO> {
    
    private static final long serialVersionUID = 1L;
    
    private long id;
    private long payment_id;
    private int no;
    private int payment_concept_id;
    private int specification_id;
    private LocalDate period_date;
    private int fiscal_year;
    private int quantity;
    private BigDecimal unit_price;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal surcharge;
    private BigDecimal total_amount;
    private int status;
    private LocalDateTime date_update;
    private LocalDateTime date_register;
    private LocalDateTime date_end;
    
    public PaymentDetailDTO() {
        this.id = -1;
        this.payment_id = -1;
        this.no = 0;
        this.payment_concept_id = -1;
        this.specification_id = -1;
        this.period_date = null;
        this.fiscal_year = LocalDate.now().getYear();
        this.quantity = 0;
        this.unit_price = BigDecimal.ZERO;
        this.subtotal = BigDecimal.ZERO;
        this.discount = BigDecimal.ZERO;
        this.surcharge = BigDecimal.ZERO;
        this.total_amount = BigDecimal.ZERO;
        this.status = 1;
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
    
    public int getNo() {
        return no;
    }
    
    public void setNo(int no) {
        this.no = no;
    }
    
    public int getPayment_concept_id() {
        return payment_concept_id;
    }
    
    public void setPayment_concept_id(int payment_concept_id) {
        this.payment_concept_id = payment_concept_id;
    }
    
    public int getSpecification_id() {
        return specification_id;
    }
    
    public void setSpecification_id(int specification_id) {
        this.specification_id = specification_id;
    }
    
    public LocalDate getPeriod_date() {
        return period_date;
    }
    
    public void setPeriod_date(LocalDate period_date) {
        this.period_date = period_date;
    }
    
    public int getFiscal_year() {
        return fiscal_year;
    }
    
    public void setFiscal_year(int fiscal_year) {
        this.fiscal_year = fiscal_year;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getUnit_price() {
        return unit_price;
    }
    
    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
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
        int hash = 3;
        hash = 41 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final PaymentDetailDTO other = (PaymentDetailDTO) obj;
        return this.id == other.id;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentDetailDTO{");
        sb.append("id=").append(id);
        sb.append(", payment_id=").append(payment_id);
        sb.append(", no=").append(no);
        sb.append(", payment_concept_id=").append(payment_concept_id);
        sb.append(", specification_id=").append(specification_id);
        sb.append(", period_date=").append(period_date);
        sb.append(", fiscal_year=").append(fiscal_year);
        sb.append(", quantity=").append(quantity);
        sb.append(", unit_price=").append(unit_price);
        sb.append(", subtotal=").append(subtotal);
        sb.append(", discount=").append(discount);
        sb.append(", surcharge=").append(surcharge);
        sb.append(", total_amount=").append(total_amount);
        sb.append(", status=").append(status);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

    // --- COMPARABLE ---
    @Override
    public int compareTo(PaymentDetailDTO o) {
        if (o == null) {
            return 1;
        }
        return Long.compare(this.id, o.getId());
    }
}
