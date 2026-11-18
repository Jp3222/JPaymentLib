/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author juanp
 */
public class PaymentInputDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // DATOS DE LA ESPECIFICACION //
    private int committee_id;
    private int office_id;
    private int status_id;
    private int administration_id;

    //DATOS DE AUDITORIA
    private int employee_id;

    //DATOS DEL CONTRIBUYENTE
    private int water_intake_user_id;
    private int user_id;
    private String user_name;
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

    protected PaymentInputDTO(PaymentInputBuilder builder) {
        this.committee_id = builder.getCommittee_id();
        this.office_id = builder.getOffice_id();
        this.status_id = builder.getStatus_id();
        this.administration_id = builder.getAdministration_id();
        this.employee_id = builder.getEmployee_id();
        this.water_intake_user_id = builder.getWater_intake_user_id();
        this.user_id = builder.getUser_id();
        this.user_name = builder.getUser_name();
        this.water_intake_number = builder.getWater_intake_number();
        this.month_list = builder.getMonth_list();
        this.months_paid = builder.getMonths_paid();
        this.process_id = builder.getProcess_id();
        this.process_type_id = builder.getProcess_type_id();
        this.map = builder.getMap();
        this.service_type = builder.getService_type();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCommittee_id() {
        return committee_id;
    }

    public int getOffice_id() {
        return office_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public int getAdministration_id() {
        return administration_id;
    }

    public void setAdministration_id(int administration_id) {
        this.administration_id = administration_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public int getWater_intake_user_id() {
        return water_intake_user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getWater_intake_number() {
        return water_intake_number;
    }

    public List<Integer> getMonth_list() {
        return month_list;
    }

    public int getMonths_paid() {
        return months_paid;
    }

    public int getProcess_id() {
        return process_id;
    }

    public int getProcess_type_id() {
        return process_type_id;
    }

    public Map<Integer, List<Integer>> getMap() {
        return map;
    }

    public int getService_type() {
        return service_type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentInputDTO{");
        sb.append("committee_id=").append(committee_id);
        sb.append(", office_id=").append(office_id);
        sb.append(", status_id=").append(status_id);
        sb.append(", employee_id=").append(employee_id);
        sb.append(", water_intake_user_id=").append(water_intake_user_id);
        sb.append(", user_id=").append(user_id);
        sb.append(", water_intake_number=").append(water_intake_number);
        sb.append(", month_list=").append(month_list);
        sb.append(", months_paid=").append(months_paid);
        sb.append(", process_id=").append(process_id);
        sb.append(", process_type_id=").append(process_type_id);
        sb.append(", map=").append(map);
        sb.append(", service_type=").append(service_type);
        sb.append('}');
        return sb.toString();
    }

}
