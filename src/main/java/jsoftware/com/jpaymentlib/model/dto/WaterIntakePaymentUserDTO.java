/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto;

import java.time.LocalDateTime;

/**
 *
 * @author juanp
 */
public class WaterIntakePaymentUserDTO {

    private int id;
    private int wki_id;
    private int fiscal_year;
    private boolean ene;
    private boolean feb;
    private boolean mar;
    private boolean abr;
    private boolean may;
    private boolean jun;
    private boolean jul;
    private boolean ago;
    private boolean sep;
    private boolean oct;
    private boolean nov;
    private boolean dic;
    private int status;
    private LocalDateTime date_update;
    private LocalDateTime date_register;
    private LocalDateTime date_end;

    public WaterIntakePaymentUserDTO() {
        this.id = -1;
        this.wki_id = -1;
        this.fiscal_year = -1;
        this.ene = false;
        this.feb = false;
        this.mar = false;
        this.abr = false;
        this.may = false;
        this.jun = false;
        this.jul = false;
        this.ago = false;
        this.sep = false;
        this.oct = false;
        this.nov = false;
        this.dic = false;
        this.status = -1;
        this.date_update = null;
        this.date_register = null;
        this.date_end = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWki_id() {
        return wki_id;
    }

    public void setWki_id(int wki_id) {
        this.wki_id = wki_id;
    }

    public int getFiscal_year() {
        return fiscal_year;
    }

    public void setFiscal_year(int fiscal_year) {
        this.fiscal_year = fiscal_year;
    }

    public boolean isEne() {
        return ene;
    }

    public void setEne(boolean ene) {
        this.ene = ene;
    }

    public boolean isFeb() {
        return feb;
    }

    public void setFeb(boolean feb) {
        this.feb = feb;
    }

    public boolean isMar() {
        return mar;
    }

    public void setMar(boolean mar) {
        this.mar = mar;
    }

    public boolean isAbr() {
        return abr;
    }

    public void setAbr(boolean abr) {
        this.abr = abr;
    }

    public boolean isMay() {
        return may;
    }

    public void setMay(boolean may) {
        this.may = may;
    }

    public boolean isJun() {
        return jun;
    }

    public void setJun(boolean jun) {
        this.jun = jun;
    }

    public boolean isJul() {
        return jul;
    }

    public void setJul(boolean jul) {
        this.jul = jul;
    }

    public boolean isAgo() {
        return ago;
    }

    public void setAgo(boolean ago) {
        this.ago = ago;
    }

    public boolean isSep() {
        return sep;
    }

    public void setSep(boolean sep) {
        this.sep = sep;
    }

    public boolean isOct() {
        return oct;
    }

    public void setOct(boolean oct) {
        this.oct = oct;
    }

    public boolean isNov() {
        return nov;
    }

    public void setNov(boolean nov) {
        this.nov = nov;
    }

    public boolean isDic() {
        return dic;
    }

    public void setDic(boolean dic) {
        this.dic = dic;
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
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + this.wki_id;
        hash = 89 * hash + this.fiscal_year;
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
        final WaterIntakePaymentUserDTO other = (WaterIntakePaymentUserDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.wki_id != other.wki_id) {
            return false;
        }
        return this.fiscal_year == other.fiscal_year;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WaterIntakePaymentUserDTO{");
        sb.append("id=").append(id);
        sb.append(", wki_id=").append(wki_id);
        sb.append(", fiscal_year=").append(fiscal_year);
        sb.append(", ene=").append(ene);
        sb.append(", feb=").append(feb);
        sb.append(", mar=").append(mar);
        sb.append(", abr=").append(abr);
        sb.append(", may=").append(may);
        sb.append(", jun=").append(jun);
        sb.append(", jul=").append(jul);
        sb.append(", ago=").append(ago);
        sb.append(", sep=").append(sep);
        sb.append(", oct=").append(oct);
        sb.append(", nov=").append(nov);
        sb.append(", dic=").append(dic);
        sb.append(", status=").append(status);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

}
