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
public class MonthDTO implements Serializable, Comparable<MonthDTO> {

    private int month_id;
    private String month_name;
    private String short_name;

    public MonthDTO() {
        this(-1, "NO DEFINIDO", "N/D");
    }

    public MonthDTO(int month_id, String month_name, String short_name) {
        this.month_id = month_id;
        this.month_name = month_name;
        this.short_name = short_name;
    }

    public int getMonth_id() {
        return month_id;
    }

    public void setMonth_id(int month_id) {
        this.month_id = month_id;
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.month_id;
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
        final MonthDTO other = (MonthDTO) obj;
        return this.month_id == other.month_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MonthDTO{");
        sb.append("month_id=").append(month_id);
        sb.append(", month_name=").append(month_name);
        sb.append(", short_name=").append(short_name);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(MonthDTO o) {
        if (o == null) {
            return 1;
        }
        return Integer.compare(this.month_id, o.month_id);
    }

}
