/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto.wrp;

import java.util.List;
import jsoftware.com.jpaymentlib.model.dto.OperationDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentHeaderDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentLinesDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentReprintDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentWaterIntakeUserDTO;

/**
 * Envoltorio que contiene la cabezera y la lista de detalles de pago generados
 *
 * @author juanp
 */
public class PaymentWrapper {

    private PaymentDTO payment;
    private PaymentHeaderDTO header;
    private List<PaymentDetailDTO> detail;
    private PaymentReprintDTO reprint;
    private PaymentWaterIntakeUserDTO payment_water_intake_user;
    private PaymentLinesDTO line;
    private OperationDTO operation;
    private List<Integer> concept_list;
    private PaymentSpecificationWrapperDTO specification_list;
    private boolean payment_line;
    private boolean paid;

    public PaymentWrapper() {
        this.payment = null;
        this.header = null;
        this.detail = null;
        this.reprint = null;
        this.payment_water_intake_user = null;
        this.line = null;
        this.operation = null;
        this.concept_list = null;
        this.specification_list = null;
        this.payment_line = false;
        this.paid = false;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
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

    public PaymentReprintDTO getReprint() {
        return reprint;
    }

    public void setReprint(PaymentReprintDTO reprint) {
        this.reprint = reprint;
    }

    public PaymentWaterIntakeUserDTO getPayment_water_intake_user() {
        return payment_water_intake_user;
    }

    public void setPayment_water_intake_user(PaymentWaterIntakeUserDTO payment_water_intake_user) {
        this.payment_water_intake_user = payment_water_intake_user;
    }

    public PaymentLinesDTO getLine() {
        return line;
    }

    public void setLine(PaymentLinesDTO line) {
        this.line = line;
    }

    public OperationDTO getOperation() {
        return operation;
    }

    public void setOperation(OperationDTO operation) {
        this.operation = operation;
    }

    public List<Integer> getConcept_list() {
        return concept_list;
    }

    public void setConcept_list(List<Integer> concept_list) {
        this.concept_list = concept_list;
    }

    public PaymentSpecificationWrapperDTO getSpecification_list() {
        return specification_list;
    }

    public void setSpecification_list(PaymentSpecificationWrapperDTO specification_list) {
        this.specification_list = specification_list;
    }

    public boolean isPayment_line() {
        return payment_line;
    }

    public void setPayment_line(boolean payment_line) {
        this.payment_line = payment_line;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentWrapper{");
        sb.append("payment=").append(payment);
        sb.append(", header=").append(header);
        sb.append(", detail=").append(detail);
        sb.append(", reprint=").append(reprint);
        sb.append(", payment_water_intake_user=").append(payment_water_intake_user);
        sb.append(", line=").append(line);
        sb.append(", operation=").append(operation);
        sb.append(", concept_list=").append(concept_list);
        sb.append(", specification_list=").append(specification_list);
        sb.append(", payment_line=").append(payment_line);
        sb.append(", paid=").append(paid);
        sb.append('}');
        return sb.toString();
    }

}
