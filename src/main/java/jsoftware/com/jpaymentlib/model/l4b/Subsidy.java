package jsoftware.com.jpaymentlib.model.l4b;

import java.math.BigDecimal;
import jsoftware.com.jpaymentlib.model.model.AbstractCalculation;
import jsoftware.com.jpaymentlib.util.FuncBusiness;

/**
 * Modelo concreto encargado de calcular el impacto financiero de un subsidio 
 * sobre un importe base determinado.
 * <br>
 * Si la regla no está activa, el importe base se mantiene intacto sin alterar 
 * la memoria.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-07-18
 * @version 1.0
 */
public class Subsidy extends AbstractCalculation {

    /**
     * Aplica la deducción del subsidio al importe base si la regla está activa.
     * 
     * @param base   El monto original del concepto antes del subsidio.
     * @param amount El valor o descuento monetario que otorga el subsidio.
     * @return El importe neto resultante en formato numérico plano.
     */
    @Override
    public String getTotal(String base, String amount) {
        // Optimización: Retorno inmediato para evitar instanciaciones innecesarias si no aplica
        if (!isApply()) {
            return base;
        }
        
        // Corrección semántica: variable renombrada a 'netAmount' o 'result'
        BigDecimal netAmount = FuncBusiness.subsidy(base, amount);
        return netAmount.toPlainString();
    }
}