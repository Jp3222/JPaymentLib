/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author juanp
 */
public class OperationDTO {

    private int id;
    private int paymentId;
    private String description;
    private String operation;
    private BigDecimal amount;
    private int status;
    private int commiteeId;
    private int officeId;
    private int employeeId;
    private int administrationId;
    private LocalDateTime dateRegister;

    public OperationDTO() {
        id = -1;
        this.paymentId = -1;
        this.description = null;
        this.operation = null;
        this.amount = null;
        this.status = -1;
        this.commiteeId = -1;
        this.officeId = -1;
        this.employeeId = -1;
        this.administrationId = -1;
        this.dateRegister = null;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCommiteeId() {
        return commiteeId;
    }

    public void setCommiteeId(int commiteeId) {
        this.commiteeId = commiteeId;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getAdministrationId() {
        return administrationId;
    }

    public void setAdministrationId(int administrationId) {
        this.administrationId = administrationId;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + this.paymentId;
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
        final OperationDTO other = (OperationDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return this.paymentId == other.paymentId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OperationDTO{");
        sb.append("id=").append(id);
        sb.append(", paymentId=").append(paymentId);
        sb.append(", description=").append(description);
        sb.append(", operation=").append(operation);
        sb.append(", amount=").append(amount);
        sb.append(", status=").append(status);
        sb.append(", commiteeId=").append(commiteeId);
        sb.append(", officeId=").append(officeId);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", administrationId=").append(administrationId);
        sb.append(", dateRegister=").append(dateRegister);
        sb.append('}');
        return sb.toString();
    }
    
    
}
