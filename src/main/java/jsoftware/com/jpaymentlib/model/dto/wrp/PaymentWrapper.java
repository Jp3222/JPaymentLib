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

/**
 * Envoltorio que contiene la cabezera y la lista de detalles de pago generados
 *
 * @author juanp
 */
public class PaymentWrapper {

    private final PaymentHeaderDTO header;
    private final List<PaymentDetailDTO> detail;

    public PaymentWrapper() {
        this.header = new PaymentHeaderDTO();
        this.detail = new ArrayList<>();
    }

    public PaymentWrapper(PaymentHeaderDTO header, List<PaymentDetailDTO> detail) {
        this.header = header;
        this.detail = detail;
    }

    public List<PaymentDetailDTO> getDetail() {
        return detail;
    }

    public PaymentHeaderDTO getHeader() {
        return header;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.header);
        hash = 19 * hash + Objects.hashCode(this.detail);
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
        return Objects.equals(this.detail, other.detail);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentWrapper{");
        sb.append("header=").append(header.toString());
        sb.append(", detail=").append(detail.toString());
        sb.append('}');
        return sb.toString();
    }

}
