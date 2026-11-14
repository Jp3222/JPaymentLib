/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto.wrp;

import java.util.List;
import java.util.Objects;
import jsoftware.com.jpaymentlib.model.dto.PaymentConceptDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentImportDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentRulersDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentSpecificationDTO;
import jsoftware.com.jutil.db.JDBMapObject;

/**
 *
 * @author juanp
 */
public class PaymentSpecificationWrapperDTO extends JDBMapObject {

    private PaymentSpecificationDTO specification;
    private PaymentConceptDTO concepts;
    private PaymentImportDTO imports;
    private PaymentRulersDTO rulers;
    private List<Integer> month_list;
    private int current_month;

    public PaymentSpecificationWrapperDTO() {
    }

    public PaymentSpecificationDTO getSpecification() {
        return specification;
    }

    public void setSpecification(PaymentSpecificationDTO specification) {
        this.specification = specification;
    }

    public PaymentConceptDTO getConcepts() {
        return concepts;
    }

    public void setConcepts(PaymentConceptDTO concepts) {
        this.concepts = concepts;
    }

    public PaymentImportDTO getImports() {
        return imports;
    }

    public void setImports(PaymentImportDTO imports) {
        this.imports = imports;
    }

    public PaymentRulersDTO getRulers() {
        return rulers;
    }

    public void setRulers(PaymentRulersDTO rulers) {
        this.rulers = rulers;
    }

    public List<Integer> getMonth_list() {
        return month_list;
    }

    public void setMonth_list(List<Integer> month_list) {
        this.month_list = month_list;
    }

    public int getCurrent_month() {
        return current_month;
    }

    public void setCurrent_month(int current_month) {
        this.current_month = current_month;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.specification);
        hash = 59 * hash + Objects.hashCode(this.concepts);
        hash = 59 * hash + Objects.hashCode(this.imports);
        hash = 59 * hash + Objects.hashCode(this.rulers);
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
        final PaymentSpecificationWrapperDTO other = (PaymentSpecificationWrapperDTO) obj;
        if (!Objects.equals(this.specification, other.specification)) {
            return false;
        }
        if (!Objects.equals(this.concepts, other.concepts)) {
            return false;
        }
        if (!Objects.equals(this.imports, other.imports)) {
            return false;
        }
        return Objects.equals(this.rulers, other.rulers);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentSpecificationWrapperDTO{");
        sb.append("specification=").append(specification);
        sb.append(", concepts=").append(concepts);
        sb.append(", imports=").append(imports);
        sb.append(", rulers=").append(rulers);
        sb.append(", month_list=").append(month_list);
        sb.append(", current_month=").append(current_month);
        sb.append('}');
        return sb.toString();
    }

}
