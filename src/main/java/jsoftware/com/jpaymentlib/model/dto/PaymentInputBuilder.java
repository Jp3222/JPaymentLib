/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto;

/**
 *
 * @author juanp
 */
public class PaymentInputBuilder {

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

    public PaymentInputBuilder() {
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

    public PaymentInputBuilder setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
        return this;
    }

    public int getCommittee_id() {
        return committee_id;
    }

    public PaymentInputBuilder setCommittee_id(int committee_id) {
        this.committee_id = committee_id;
        return this;
    }

    public int getOffice_id() {
        return office_id;
    }

    public PaymentInputBuilder setOffice_id(int office_id) {
        this.office_id = office_id;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public PaymentInputBuilder setStatus(int status) {
        this.status = status;
        return this;
    }

    public int getUser_id() {
        return user_id;
    }

    public PaymentInputBuilder setUser_id(int user_id) {
        this.user_id = user_id;
        return this;
    }

    public int getWki_id() {
        return wki_id;
    }

    public PaymentInputBuilder setWki_id(int wki_id) {
        this.wki_id = wki_id;
        return this;
    }

    public int getProcess_type_id() {
        return process_type_id;
    }

    public PaymentInputBuilder setProcess_type_id(int process_type_id) {
        this.process_type_id = process_type_id;
        return this;
    }

    public int getProcess_id() {
        return process_id;
    }

    public PaymentInputBuilder setProcess_id(int process_id) {
        this.process_id = process_id;
        return this;
    }

    public boolean isQuote_for_process() {
        return quote_for_process;
    }

    public PaymentInputBuilder setQuote_for_process(boolean quote_for_process) {
        this.quote_for_process = quote_for_process;
        return this;
    }

    public int getPayment_type_id() {
        return payment_type_id;
    }

    public PaymentInputBuilder setPayment_type_id(int payment_type_id) {
        this.payment_type_id = payment_type_id;
        return this;
    }

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public PaymentInputBuilder setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
        return this;
    }
    
    public PaymentInputDTO builder(){
        return new PaymentInputDTO(this);
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
