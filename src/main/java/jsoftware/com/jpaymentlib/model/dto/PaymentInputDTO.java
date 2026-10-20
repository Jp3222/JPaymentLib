/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto;

import java.io.Serializable;

/**
 *
 * @author juanp
 */
public class PaymentInputDTO implements Serializable {

    private int employee_id;
    private int committee_id;
    private int office_id;
    private int status;
    private int user_id;
    private int wki_id;
    private int process_type_id;
    private int process_id;
    private boolean quote_for_process;
    private int payment_type_id;
    private int payment_method_id;

    public PaymentInputDTO(PaymentInputBuilder builder) {
        this.employee_id = builder.getEmployee_id();
        this.committee_id = builder.getCommittee_id();
        this.office_id = builder.getOffice_id();
        this.status = builder.getStatus();
        this.user_id = builder.getUser_id();
        this.wki_id = builder.getWki_id();
        this.process_type_id = builder.getProcess_type_id();
        this.process_id = builder.getProcess_id();
        this.quote_for_process = builder.isQuote_for_process();
        this.payment_type_id = builder.getPayment_type_id();
        this.payment_method_id = builder.getPayment_method_id();
    }

    public PaymentInputDTO() {
        this.employee_id = -1;
        this.committee_id = -1;
        this.office_id = -1;
        this.status = -1;
        this.user_id = -1;
        this.wki_id = -1;
        this.process_type_id = -1;
        this.process_id = -1;
        this.quote_for_process = false;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getWki_id() {
        return wki_id;
    }

    public void setWki_id(int wki_id) {
        this.wki_id = wki_id;
    }

    public int getProcess_type_id() {
        return process_type_id;
    }

    public void setProcess_type_id(int process_type_id) {
        this.process_type_id = process_type_id;
    }

    public int getProcess_id() {
        return process_id;
    }

    public void setProcess_id(int process_id) {
        this.process_id = process_id;
    }

    public boolean isQuote_for_process() {
        return quote_for_process;
    }

    public void setQuote_for_process(boolean quote_for_process) {
        this.quote_for_process = quote_for_process;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentInput{");
        sb.append("employee_id=").append(employee_id);
        sb.append(", committee_id=").append(committee_id);
        sb.append(", office_id=").append(office_id);
        sb.append(", status=").append(status);
        sb.append(", user_id=").append(user_id);
        sb.append(", wki_id=").append(wki_id);
        sb.append(", process_type_id=").append(process_type_id);
        sb.append(", process_id=").append(process_id);
        sb.append('}');
        return sb.toString();
    }

}
