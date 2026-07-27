/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.dto.wrp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentHeaderDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentLines;

/**
 * Envoltorio que contiene la cabezera y la lista de detalles de pago generados
 *
 * @author juanp
 */
public class PaymentWrapper {

    private final PaymentHeaderDTO header;
    private final List<PaymentDetailDTO> detail;
    private final PaymentLines line;

    public PaymentWrapper(PaymentHeaderDTO header, List<PaymentDetailDTO> detail) {
        this(header, detail, null);
    }

    public PaymentWrapper(PaymentHeaderDTO header, List<PaymentDetailDTO> detail, PaymentLines line) {
        this.header = header;
        this.detail = detail;
        this.line = line;
    }

    public PaymentHeaderDTO getHeader() {
        return header;
    }

    public List<PaymentDetailDTO> getDetail() {
        return detail;
    }

    public PaymentLines getLine() {
        return line;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.header);
        hash = 71 * hash + Objects.hashCode(this.detail);
        hash = 71 * hash + Objects.hashCode(this.line);
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
        return Objects.equals(this.line, other.line);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentWrapper{");
        sb.append("header=").append(header);
        sb.append(", detail=").append(detail);
        sb.append(", line=").append(line);
        sb.append('}');
        return sb.toString();
    }

}
