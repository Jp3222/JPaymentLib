/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author juanp
 */
public class PaymentWaterIntakeUser {

    private int id;
    private int fiscal_year;
    private int water_intake_user_id;
    private String water_intake_number;
    private String observation;
    private LocalDateTime date_register;

    public PaymentWaterIntakeUser() {
        this.id = -1;
        this.fiscal_year = -1;
        this.water_intake_user_id = -1;
        this.water_intake_number = null;
        this.observation = null;
        this.date_register = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFiscal_year() {
        return fiscal_year;
    }

    public void setFiscal_year(int fiscal_year) {
        this.fiscal_year = fiscal_year;
    }

    public int getWater_intake_user_id() {
        return water_intake_user_id;
    }

    public void setWater_intake_user_id(int water_intake_user_id) {
        this.water_intake_user_id = water_intake_user_id;
    }

    public String getWater_intake_number() {
        return water_intake_number;
    }

    public void setWater_intake_number(String water_intake_number) {
        this.water_intake_number = water_intake_number;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDateTime getDate_register() {
        return date_register;
    }

    public void setDate_register(LocalDateTime date_register) {
        this.date_register = date_register;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        hash = 23 * hash + this.water_intake_user_id;
        hash = 23 * hash + Objects.hashCode(this.water_intake_number);
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
        final PaymentWaterIntakeUser other = (PaymentWaterIntakeUser) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.water_intake_user_id != other.water_intake_user_id) {
            return false;
        }
        return Objects.equals(this.water_intake_number, other.water_intake_number);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentWaterIntakeUser{");
        sb.append("id=").append(id);
        sb.append(", fiscal_year=").append(fiscal_year);
        sb.append(", water_intake_user_id=").append(water_intake_user_id);
        sb.append(", water_intake_number=").append(water_intake_number);
        sb.append(", observation=").append(observation);
        sb.append(", date_register=").append(date_register);
        sb.append('}');
        return sb.toString();
    }

}
