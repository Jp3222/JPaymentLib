/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto.wrp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jsoftware.com.jpaymentlib.model.dto.PaymentDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentHeaderDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentLinesDTO;

/**
 * Envoltorio que contiene la cabezera y la lista de detalles de pago generados
 *
 * @author juanp
 */
public class PaymentWrapper {

    private List<Integer> concept_list;
    private PaymentHeaderDTO header;
    private List<PaymentDetailDTO> detail;
    private PaymentLinesDTO line;
    private PaymentDTO payment;
    private PaymentSpecificationWrapperDTO specification_list;

    public PaymentWrapper() {
        this.header = new PaymentHeaderDTO();
        this.detail = new ArrayList<>();
        this.line = new PaymentLinesDTO();
    }

    public List<Integer> getConcept_list() {
        return concept_list;
    }

    public void setConcept_list(List<Integer> concept_list) {
        this.concept_list = concept_list;
    }

    public PaymentHeaderDTO getHeader() {
        return header;
    }

    public void setHeader(PaymentHeaderDTO header) {
        this.header = header;
    }

    public List<PaymentDetailDTO> getDetail() {
        return detail;
    }

    public void setDetail(List<PaymentDetailDTO> detail) {
        this.detail = detail;
    }

    public PaymentLinesDTO getLine() {
        return line;
    }

    public void setLine(PaymentLinesDTO line) {
        this.line = line;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }

    public PaymentSpecificationWrapperDTO getSpecification_list() {
        return specification_list;
    }

    public void setSpecification_list(PaymentSpecificationWrapperDTO specification_list) {
        this.specification_list = specification_list;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.header);
        hash = 61 * hash + Objects.hashCode(this.detail);
        hash = 61 * hash + Objects.hashCode(this.line);
        hash = 61 * hash + Objects.hashCode(this.payment);
        hash = 61 * hash + Objects.hashCode(this.specification_list);
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
        final PaymentWrapper other = (PaymentWrapper) obj;
        if (!Objects.equals(this.header, other.header)) {
            return false;
        }
        if (!Objects.equals(this.detail, other.detail)) {
            return false;
        }
        if (!Objects.equals(this.line, other.line)) {
            return false;
        }
        if (!Objects.equals(this.payment, other.payment)) {
            return false;
        }
        return Objects.equals(this.specification_list, other.specification_list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentWrapper{");
        sb.append("header=").append(header);
        sb.append(", detail=").append(detail);
        sb.append(", line=").append(line);
        sb.append(", payment=").append(payment);
        sb.append(", dto=").append(specification_list);
        sb.append('}');
        return sb.toString();
    }

}
