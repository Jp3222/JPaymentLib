package jsoftware.com.jpaymentlib.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utilería de cálculos aritméticos y reglas financieras para el motor de
 * cobros. Centraliza las operaciones asegurando precisión decimal fija y manejo
 * seguro de Strings.
 *
 * @author juanp
 * @since 2026-07-18
 * @version 1.2
 */
public class FuncBusiness {

    /**
     * Constructor privado para prevenir la instanciación de la clase
     * utilitaria.
     */
    private FuncBusiness() {
    }

    /**
     * Evalúa si una bandera de negocio está activa (valor numérico "1").
     *
     * @param b Cadena a evaluar.
     * @return true si es una cadena válida y es igual a "1".
     */
    public static boolean isApply(String b) {
        return Func.isNotNullEmptyBlank(b) && "1".equals(b.trim());
    }

    /**
     * Realiza una conversión segura de String a BigDecimal. Si la cadena es
     * inválida, nula o vacía, retorna un valor por defecto (ZERO).
     *
     * @param value Cadena con el número a convertir.
     * @return Instancia de BigDecimal con el valor parseado o ZERO si falla.
     */
    public static BigDecimal safeBigDecimal(String value) {
        if (Func.isNotNullEmptyBlank(value)) {
            try {
                return new BigDecimal(value.trim());
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal surcharge(BigDecimal base, BigDecimal surcharge) {
        return base.add(surcharge);
    }

    public static BigDecimal subsidy(BigDecimal base, BigDecimal subsidy) {
        return base.subtract(subsidy);
    }

    public static BigDecimal discount(BigDecimal base, BigDecimal discount) {
        return base.subtract(discount);
    }

    public static BigDecimal variable(BigDecimal base, boolean variable, BigDecimal items) {
        // Validación defensiva contra null y verificación de cantidad mayor a cero
        if (variable && items != null && items.compareTo(BigDecimal.ZERO) > 0) {
            return base.multiply(items);
        }
        return base;
    }

    /**
     * Redondea un importe en base a las banderas activas de configuración.
     *
     * @param base Monto base a procesar.
     * @param round Bandera de redondeo (true si se debe redondear).
     * @param up Indica si el redondeo es hacia arriba (CEILING) o estándar
     * (HALF_UP).
     * @return El importe procesado como BigDecimal con 2 decimales ajustados.
     */
    public static BigDecimal round(BigDecimal base, boolean round, boolean up) {
        if (base == null) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        // CORRECCIÓN CRÍTICA: Si la bandera de redondeo NO está activa, 
        // se devuelve el monto original sin alterar la regla de redondeo especial
        if (!round) {
            return base;
        }

        // Definimos la regla de redondeo según la bandera 'up'
        // 'up' activo -> CEILING (Redondea siempre hacia el infinito positivo / arriba)
        // 'up' inactivo -> HALF_UP (Redondeo aritmético estándar)
        RoundingMode modoRedondeo = up ? RoundingMode.CEILING : RoundingMode.HALF_UP;
        return base.setScale(2, modoRedondeo);
    }
}
