package jsoftware.com.jpaymentlib.model.model;

/**
 * Contrato base para los modelos de cálculo aritmético y reglas de afectación 
 * financiera en el motor de cobros (Descuentos, Recargos, Subsidios, etc.).
 * <br>
 * Diseñado para operar directamente con cadenas de texto (String) provenientes 
 * de la estructura dinámica de los DTOs.
 *
 * @author JUAN PABLO CAMPOS CASASANERO
 * @since 2026-07-18
 * @version 1.0
 */
public interface CalculationModel {

    /**
     * Ejecuta la operación aritmética correspondiente al modelo sobre los valores proporcionados.
     * 
     * @param base   El importe base sobre el cual se aplicará la regla.
     * @param amount El valor del modificador (importe, porcentaje o tasa).
     * @return El resultado del cálculo en formato String con precisión decimal.
     */
    String getTotal(String base, String amount);

    /**
     * Define si la regla de cálculo debe aplicarse o ignorarse en la transacción.
     * 
     * @param apply Cadena de texto que activa ("1") o desactiva ("0") la aplicación.
     */
    void setApply(String apply);

    /**
     * Retorna el estado lógico de aplicación del modelo de cálculo.
     * 
     * @return true si la regla está activa y debe procesarse.
     */
    boolean isApply();
}