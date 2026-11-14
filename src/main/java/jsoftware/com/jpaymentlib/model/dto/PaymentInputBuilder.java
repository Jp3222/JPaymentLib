/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto;

import java.util.List;
import java.util.Map;

/**
 *
 * @author juanp
 */
public class PaymentInputBuilder {

    private static final long serialVersionUID = 1L;

    // DATOS DE LA ESPECIFICACION //
    private int committee_id;
    private int office_id;
    private int status_id;

    //DATOS DE AUDITORIA
    private int employee_id;

    //DATOS DEL CONTRIBUYENTE
    private int water_intake_user_id;
    private int user_id;
    private String water_intake_number;

    // DATOS PARA EL COBRO DE MENSUALIDADES //
    private List<Integer> month_list;
    private int months_paid;

    // DATOS PARA EL COBRO DE UN TRAMITE //
    private int process_id;
    private int process_type_id;

    // DATOS PARA COBRAR OTROS EJERCICIOS FISCALES
    private Map<Integer, List<Integer>> map;

    // TIPO DE SERVICIO A USAR //
    private int service_type;

    public PaymentInputBuilder() {
        this.committee_id = -1;
        this.office_id = -1;
        this.status_id = -1;
        this.employee_id = -1;
        this.water_intake_user_id = -1;
        this.user_id = -1;
        this.water_intake_number = null;
        this.month_list = null;
        this.months_paid = -1;
        this.process_id = -1;
        this.process_type_id = -1;
        this.map = null;
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

    public int getStatus_id() {
        return status_id;
    }

    public PaymentInputBuilder setStatus_id(int status_id) {
        this.status_id = status_id;
        return this;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public PaymentInputBuilder setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
        return this;
    }

    public int getWater_intake_user_id() {
        return water_intake_user_id;
    }

    public PaymentInputBuilder setWater_intake_user_id(int water_intake_user_id) {
        this.water_intake_user_id = water_intake_user_id;
        return this;
    }

    public int getUser_id() {
        return user_id;
    }

    public PaymentInputBuilder setUser_id(int user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getWater_intake_number() {
        return water_intake_number;
    }

    public PaymentInputBuilder setWater_intake_number(String water_intake_number) {
        this.water_intake_number = water_intake_number;
        return this;
    }

    public List<Integer> getMonth_list() {
        return month_list;
    }

    public PaymentInputBuilder setMonth_list(List<Integer> month_list) {
        this.month_list = month_list;
        return this;
    }

    public int getMonths_paid() {
        return months_paid;
    }

    public PaymentInputBuilder setMonths_paid(int months_paid) {
        this.months_paid = months_paid;
        return this;
    }

    public int getProcess_id() {
        return process_id;
    }

    public PaymentInputBuilder setProcess_id(int process_id) {
        this.process_id = process_id;
        return this;
    }

    public int getProcess_type_id() {
        return process_type_id;
    }

    public PaymentInputBuilder setProcess_type_id(int process_type_id) {
        this.process_type_id = process_type_id;
        return this;
    }

    public Map<Integer, List<Integer>> getMap() {
        return map;
    }

    public PaymentInputBuilder setMap(Map<Integer, List<Integer>> map) {
        this.map = map;
        return this;
    }

    public int getService_type() {
        return service_type;
    }

    public PaymentInputBuilder setService_type(int service_type) {
        this.service_type = service_type;
        return this;
    }

    public PaymentInputDTO buildCalculateMonthlyPayment() {
        this.service_type = 2; // Ajusta según el ID del servicio de mensualidades
        return new PaymentInputDTO(this);
    }

    public PaymentInputDTO buildCalculateQuote() {
        this.service_type = 1; // Corregido: Asignar el ID correcto para cotizaciones (ej. 2)
        return new PaymentInputDTO(this);
    }

    public PaymentInputDTO buildSavePayment() {
        this.service_type = 3; // Corregido: Asignar el ID correcto para cotizaciones (ej. 2)
        return new PaymentInputDTO(this);
    }
}
