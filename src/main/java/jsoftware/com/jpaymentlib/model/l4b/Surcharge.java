/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.l4b;

import java.math.BigDecimal;
import jsoftware.com.jpaymentlib.model.model.AbstractCalculation;
import jsoftware.com.jpaymentlib.util.FuncBusiness;

/**
 *
 * @author juanp
 */
public class Surcharge extends AbstractCalculation {

    @Override
    public String getTotal(String base, String amount) {
        // Optimización: Retorno inmediato para evitar instanciaciones innecesarias si no aplica
        if (!isApply()) {
            return base;
        }
        BigDecimal surcharge = FuncBusiness.surcharge(base, amount);
        return surcharge.toPlainString();
    }

}
