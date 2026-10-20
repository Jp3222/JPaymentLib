package jsoftware.com.jpaymentlib.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para la tabla pym_payment_import. Mapea los importes financieros
 * asociados a los conceptos de pago, utilizando BigDecimal para garantizar
 * precisión exacta en montos, descuentos, recargos y subsidios.
 *
 * @author Juan Pablo Campos Casasanero
 */
public class PaymentImportDTO implements Serializable, Comparable<PaymentImportDTO> {

    private static final long serialVersionUID = 1L;

    /**
     * ID del registro (Llave primaria autoincremental)
     */
    private int id;

    /**
     * ID del concepto al que pertenece el importe
     */
    private int concept_id;

    /**
     * ID de la administración que registró el importe (Soporta nulos)
     */
    private Integer administration_id;

    /**
     * Divisa del importe (por defecto 'MXM')
     */
    private String currency;

    /**
     * Importe base a pagar
     */
    private BigDecimal amount;

    /**
     * Recargo por mora
     */
    private BigDecimal surcharge;

    /**
     * Descuento aplicado
     */
    private BigDecimal discount;

    /**
     * Subsidio aplicado
     */
    private BigDecimal subsidy;

    /**
     * Indica si el cálculo es variable (0 = Fijo, 1 = Variable: AMOUNT * UNITS)
     */
    private boolean is_variable;

    /**
     * ID del tipo de unidad de medida
     */
    private int unit_id;

    /**
     * Número de unidades
     */
    private int units;

    /**
     * Estatus del registro (1 = Activo por defecto)
     */
    private int status;

    /**
     * ID del último empleado que actualizó el registro
     */
    private int last_employee_update;

    /**
     * ID de la oficina
     */
    private int office_id;

    /**
     * Fecha y hora de última actualización
     */
    private LocalDateTime date_update;

    /**
     * Fecha y hora de registro
     */
    private LocalDateTime date_register;

    /**
     * Fecha y hora de finalización (Soporta nulos)
     */
    private LocalDateTime date_end;

    /**
     * Constructor por defecto. Inicializa los campos de importe en
     * BigDecimal.ZERO para evitar NullPointerException.
     */
    public PaymentImportDTO() {
        this.id = -1;
        this.concept_id = -1;
        this.administration_id = null;
        this.currency = "MXM";
        this.amount = BigDecimal.ZERO;
        this.surcharge = BigDecimal.ZERO;
        this.discount = BigDecimal.ZERO;
        this.subsidy = BigDecimal.ZERO;
        this.is_variable = false;
        this.unit_id = -1;
        this.units = 1;
        this.status = 1;
        this.last_employee_update = -1;
        this.office_id = -1;
        this.date_update = null;
        this.date_register = null;
        this.date_end = null;
    }

    // --- GETTERS & SETTERS ---
    /**
     * Obtiene el ID del registro.
     *
     * @return Entero con el ID único.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del registro.
     *
     * @param id Identificador único.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el ID del concepto asociado.
     *
     * @return Entero con el ID del concepto.
     */
    public int getConcept_id() {
        return concept_id;
    }

    /**
     * Establece el ID del concepto asociado.
     *
     * @param concept_id ID del concepto.
     */
    public void setConcept_id(int concept_id) {
        this.concept_id = concept_id;
    }

    /**
     * Obtiene el ID de la administración asociada.
     *
     * @return Integer con el ID de la administración o null si no aplica.
     */
    public Integer getAdministration_id() {
        return administration_id;
    }

    /**
     * Establece el ID de la administración asociada.
     *
     * @param administration_id ID de la administración.
     */
    public void setAdministration_id(Integer administration_id) {
        this.administration_id = administration_id;
    }

    /**
     * Obtiene el código de divisa.
     *
     * @return String con la divisa (ej. "MXM").
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Establece la divisa del importe.
     *
     * @param currency Código de divisa.
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Obtiene el importe base a pagar.
     *
     * @return BigDecimal con el monto base.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Establece el importe base a pagar.
     *
     * @param amount Monto base en BigDecimal.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Obtiene el monto de recargo por mora.
     *
     * @return BigDecimal con el recargo.
     */
    public BigDecimal getSurcharge() {
        return surcharge;
    }

    /**
     * Establece el monto de recargo por mora.
     *
     * @param surcharge Monto del recargo en BigDecimal.
     */
    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
    }

    /**
     * Obtiene el descuento aplicado.
     *
     * @return BigDecimal con el descuento.
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * Establece el descuento aplicado.
     *
     * @param discount Monto del descuento en BigDecimal.
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * Obtiene el subsidio aplicado.
     *
     * @return BigDecimal con el subsidio.
     */
    public BigDecimal getSubsidy() {
        return subsidy;
    }

    /**
     * Establece el subsidio aplicado.
     *
     * @param subsidy Monto del subsidio en BigDecimal.
     */
    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    /**
     * Obtiene el indicador de importe variable.
     *
     * @return 1 si el cálculo depende de las unidades (AMOUNT * UNITS), 0 en
     * caso contrario.
     */
    public boolean isvariable() {
        return is_variable;
    }

    /**
     * Establece el indicador de importe variable.
     *
     * @param is_variable Flag de cálculo variable.
     */
    public void setIs_variable(boolean is_variable) {
        this.is_variable = is_variable;
    }

    /**
     * Obtiene el ID del tipo de unidad de medida.
     *
     * @return Entero con el ID de la unidad.
     */
    public int getUnit_id() {
        return unit_id;
    }

    /**
     * Establece el ID del tipo de unidad de medida.
     *
     * @param unit_id ID de la unidad.
     */
    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    /**
     * Obtiene el número de unidades.
     *
     * @return Entero con la cantidad de unidades.
     */
    public int getUnits() {
        return units;
    }

    /**
     * Establece el número de unidades.
     *
     * @param units Cantidad de unidades.
     */
    public void setUnits(int units) {
        this.units = units;
    }

    /**
     * Obtiene el estatus del registro.
     *
     * @return Entero con el código de estatus.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Establece el estatus del registro.
     *
     * @param status Código de estatus.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Obtiene el ID del último empleado que actualizó el registro.
     *
     * @return Entero con el ID del empleado.
     */
    public int getLast_employee_update() {
        return last_employee_update;
    }

    /**
     * Establece el ID del último empleado que actualizó el registro.
     *
     * @param last_employee_update ID del empleado.
     */
    public void setLast_employee_update(int last_employee_update) {
        this.last_employee_update = last_employee_update;
    }

    /**
     * Obtiene el ID de la oficina.
     *
     * @return Entero con el ID de la oficina.
     */
    public int getOffice_id() {
        return office_id;
    }

    /**
     * Establece el ID de la oficina.
     *
     * @param office_id ID de la oficina.
     */
    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    /**
     * Obtiene la fecha y hora de última actualización.
     *
     * @return LocalDateTime con la marca de tiempo.
     */
    public LocalDateTime getDate_update() {
        return date_update;
    }

    /**
     * Establece la fecha y hora de última actualización.
     *
     * @param date_update Marca de tiempo de actualización.
     */
    public void setDate_update(LocalDateTime date_update) {
        this.date_update = date_update;
    }

    /**
     * Obtiene la fecha y hora de registro.
     *
     * @return LocalDateTime con la marca de tiempo de creación.
     */
    public LocalDateTime getDate_register() {
        return date_register;
    }

    /**
     * Establece la fecha y hora de registro.
     *
     * @param date_register Marca de tiempo de creación.
     */
    public void setDate_register(LocalDateTime date_register) {
        this.date_register = date_register;
    }

    /**
     * Obtiene la fecha y hora de finalización del importe.
     *
     * @return LocalDateTime con la fecha de término o null si está activo.
     */
    public LocalDateTime getDate_end() {
        return date_end;
    }

    /**
     * Establece la fecha y hora de finalización del importe.
     *
     * @param date_end Marca de tiempo de término.
     */
    public void setDate_end(LocalDateTime date_end) {
        this.date_end = date_end;
    }

    // --- CONTRATO EQUALS, HASHCODE & TOSTRING ---
    /**
     * Genera el código hash utilizando la llave primaria (id).
     *
     * @return Entero con el hash calculado.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.id;
        return hash;
    }

    /**
     * Evalúa la igualdad entre instancias basándose en la llave primaria (id).
     *
     * @param obj Objeto objetivo a comparar.
     * @return true si representan el mismo registro en la base de datos, false
     * en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PaymentImportDTO other = (PaymentImportDTO) obj;
        return this.id == other.id;
    }

    /**
     * Retorna la representación en cadena de texto con el estado del objeto.
     *
     * @return Cadena formateada con el contenido de los miembros.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentImportDTO{");
        sb.append("id=").append(id);
        sb.append(", concept_id=").append(concept_id);
        sb.append(", administration_id=").append(administration_id);
        sb.append(", currency=").append(currency);
        sb.append(", amount=").append(amount);
        sb.append(", surcharge=").append(surcharge);
        sb.append(", discount=").append(discount);
        sb.append(", subsidy=").append(subsidy);
        sb.append(", is_variable=").append(is_variable);
        sb.append(", unit_id=").append(unit_id);
        sb.append(", units=").append(units);
        sb.append(", status=").append(status);
        sb.append(", last_employee_update=").append(last_employee_update);
        sb.append(", office_id=").append(office_id);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

    // --- COMPARABLE ---
    /**
     * Compara dos instancias de PaymentImportDTO por su id numérico para
     * ordenamiento natural. Se apoya en Integer.compare para prevenir Integer
     * Overflow.
     *
     * @param o Objeto objetivo de la comparación.
     * @return Valor negativo si este id es menor, 0 si son iguales o positivo
     * si es mayor.
     */
    @Override
    public int compareTo(PaymentImportDTO o) {
        return Integer.compare(this.id, o.id);
    }
}
