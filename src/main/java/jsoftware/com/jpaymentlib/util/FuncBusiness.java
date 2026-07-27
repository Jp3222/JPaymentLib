package jsoftware.com.jpaymentlib.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Utilería de cálculos aritméticos y reglas financieras para el motor de
 * cobros. Centraliza las operaciones asegurando precisión decimal fija y manejo
 * seguro de Strings.
 *
 * @author juanp
 * @since 2026-07-18
 * @version 1.1
 */
public class FuncBusiness {

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
     */
    private static BigDecimal safeBigDecimal(String value) {
        if (Func.isNotNullEmptyBlank(value)) {
            try {
                return new BigDecimal(value.trim());
            } catch (NumberFormatException e) {
                // Podrías añadir un log aquí si es necesario registrar el fallo de casteo
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal surcharge(String base, String surcharge) {
        BigDecimal a = safeBigDecimal(base);
        BigDecimal b = safeBigDecimal(surcharge);
        return a.add(b);
    }

    public static BigDecimal subsidy(String base, String subsidy) {
        BigDecimal a = safeBigDecimal(base);
        BigDecimal b = safeBigDecimal(subsidy);
        return a.subtract(b);
    }

    public static BigDecimal discount(String base, String discount) {
        BigDecimal a = safeBigDecimal(base);
        BigDecimal b = safeBigDecimal(discount);
        return a.subtract(b);
    }

    public static BigDecimal variable(String base, String variable, String items) {
        BigDecimal a = safeBigDecimal(base);

        // Si la bandera variable está activa, multiplicamos la base por la cantidad de items/unidades
        if (isApply(variable)) {
            BigDecimal b = safeBigDecimal(items);
            return a.multiply(b);
        }

        // Si no es variable, el importe base se mantiene intacto (multiplicado por 1 implícito)
        return a;
    }

    /**
     * Función que redondea un importe en base a las banderas activas de
     * configuración.
     *
     * @param base - Monto base en formato String.
     * @param round - Bandera que indica si el monto aplica redondeo ("1",
     * "true", "S", etc.).
     * @param up - Bandera que indica si el redondeo es hacia arriba (CEILING) o
     * estándar (HALF_UP).
     * @return El importe procesado como BigDecimal con 2 decimales (o escala 0
     * si es redondeo a enteros).
     */
    public static BigDecimal round(String base, String round, String up) {
        // Conversión segura de la cadena recibida
        BigDecimal monto = safeBigDecimal(base);

        // Si la bandera de redondeo no está activa, retornamos el valor original sin modificar
        if (!isApply(round)) {
            return monto;
        }

        // Definimos la regla de redondeo según la bandera 'up'
        // 'up' activo -> CEILING (Redondea siempre hacia el infinito positivo / arriba)
        // 'up' inactivo -> HALF_UP (Redondeo aritmético estándar)
        RoundingMode modoRedondeo = isApply(up) ? RoundingMode.CEILING : RoundingMode.HALF_UP;

        // Aplicamos la escala requerida (ej. 0 para enteros o 2 para centavos finales)
        // NOTA: Cambia la escala a 0 si tu lógica de cobro requiere ajustar a enteros.
        return monto.setScale(2, modoRedondeo);
    }
}
