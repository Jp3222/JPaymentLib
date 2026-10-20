package jsoftware.com.jpaymentlib.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para la tabla pym_payment_specification. Mapea la matriz de
 * especificaciones de pago, tarifas vigentes, reglas asociadas y vigencias por
 * ejercicio fiscal con tipos fuertemente tipados.
 *
 * @author Juan Pablo Campos Casasanero
 */
public class PaymentSpecificationDTO implements Serializable, Comparable<PaymentSpecificationDTO> {

    private static final long serialVersionUID = 1L;

    /**
     * ID del registro (Llave primaria autoincremental)
     */
    private int id;

    /**
     * Relación con el concepto de pago maestro (pym_payment_concept.id)
     */
    private int concept_id;

    /**
     * Folio del concepto vinculado que mantiene la misma secuencia
     */
    private int sequence;

    /**
     * Relación con el importe/precio vigente (pym_payment_import.id)
     */
    private int import_id;

    /**
     * Relación con la regla aplicable (pym_payment_rulers.id)
     */
    private int ruler_id;

    /**
     * Año fiscal en el que aplica esta configuración (Ej. 2026)
     */
    private int fiscal_year;

    /**
     * Fecha de inicio de aplicación de esta tarifa
     */
    private LocalDate date_start_application;

    /**
     * Fecha de fin de aplicación de esta tarifa
     */
    private LocalDate date_end_application;

    /**
     * Estatus de la especificación (VIGENTE, SUSPENDIDO, HISTORICO)
     */
    private int status;

    /**
     * ID de la oficina que registró esta especificación
     */
    private int office_id;

    /**
     * ID de la administración que registró la especificación (Permite nulos)
     */
    private int administration_id;

    /**
     * ID del último empleado que configuró la matriz
     */
    private int last_employee_update;

    /**
     * Fecha y hora de última actualización del registro
     */
    private LocalDateTime date_update;

    /**
     * Fecha y hora de creación del registro
     */
    private LocalDateTime date_register;

    /**
     * Fecha y hora asignada al finalizar o cancelar la especificación
     */
    private LocalDateTime date_end;

    /**
     * Constructor por defecto. Inicializa los campos numéricos en -1 o null
     * según corresponda.
     */
    public PaymentSpecificationDTO() {
        this.id = -1;
        this.concept_id = -1;
        this.sequence = -1;
        this.import_id = -1;
        this.ruler_id = -1;
        this.fiscal_year = LocalDate.now().getYear();
        this.date_start_application = null;
        this.date_end_application = null;
        this.status = -1;
        this.office_id = -1;
        this.administration_id = -1;
        this.last_employee_update = -1;
        this.date_update = null;
        this.date_register = null;
        this.date_end = null;
    }

    // --- GETTERS & SETTERS ---
    /**
     * Obtiene el ID del registro.
     *
     * @return Entero con el ID del registro.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del registro.
     *
     * @param id Identificador único del registro.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el ID del concepto maestro de pago.
     *
     * @return Entero con el ID del concepto.
     */
    public int getConcept_id() {
        return concept_id;
    }

    /**
     * Establece el ID del concepto maestro de pago.
     *
     * @param concept_id ID del concepto asociado.
     */
    public void setConcept_id(int concept_id) {
        this.concept_id = concept_id;
    }

    /**
     * Obtiene la secuencia/folio vinculado al concepto.
     *
     * @return Entero con el número de secuencia.
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * Establece la secuencia/folio vinculado al concepto.
     *
     * @param sequence Secuencia correspondiente.
     */
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    /**
     * Obtiene el ID del importe/precio vigente.
     *
     * @return Entero con el ID del importe.
     */
    public int getImport_id() {
        return import_id;
    }

    /**
     * Establece el ID del importe/precio vigente.
     *
     * @param import_id ID del importe asignado.
     */
    public void setImport_id(int import_id) {
        this.import_id = import_id;
    }

    /**
     * Obtiene el ID de la regla aplicable.
     *
     * @return Entero con el ID de la regla.
     */
    public int getRuler_id() {
        return ruler_id;
    }

    /**
     * Establece el ID de la regla aplicable.
     *
     * @param ruler_id ID de la regla.
     */
    public void setRuler_id(int ruler_id) {
        this.ruler_id = ruler_id;
    }

    /**
     * Obtiene el año fiscal de aplicación.
     *
     * @return Entero con el ejercicio fiscal.
     */
    public int getFiscal_year() {
        return fiscal_year;
    }

    /**
     * Establece el año fiscal de aplicación.
     *
     * @param fiscal_year Año fiscal.
     */
    public void setFiscal_year(int fiscal_year) {
        this.fiscal_year = fiscal_year;
    }

    /**
     * Obtiene la fecha de inicio de aplicación de la tarifa.
     *
     * @return LocalDate con la fecha inicial.
     */
    public LocalDate getDate_start_application() {
        return date_start_application;
    }

    /**
     * Establece la fecha de inicio de aplicación de la tarifa.
     *
     * @param date_start_application Fecha inicial.
     */
    public void setDate_start_application(LocalDate date_start_application) {
        this.date_start_application = date_start_application;
    }

    /**
     * Obtiene la fecha límite de aplicación de la tarifa.
     *
     * @return LocalDate con la fecha final.
     */
    public LocalDate getDate_end_application() {
        return date_end_application;
    }

    /**
     * Establece la fecha límite de aplicación de la tarifa.
     *
     * @param date_end_application Fecha final.
     */
    public void setDate_end_application(LocalDate date_end_application) {
        this.date_end_application = date_end_application;
    }

    /**
     * Obtiene el estatus de la especificación.
     *
     * @return String con el estatus (ej. VIGENTE, SUSPENDIDO, HISTORICO).
     */
    public int getStatus() {
        return status;
    }

    /**
     * Establece el estatus de la especificación.
     *
     * @param status Estado de la tarifa.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Obtiene el ID de la oficina que registró la especificación.
     *
     * @return Entero con el ID de la oficina.
     */
    public int getOffice_id() {
        return office_id;
    }

    /**
     * Establece el ID de la oficina que registró la especificación.
     *
     * @param office_id ID de la oficina.
     */
    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    /**
     * Obtiene el ID de la administración responsable.
     *
     * @return Integer con el ID de la administración o null.
     */
    public Integer getAdministration_id() {
        return administration_id;
    }

    /**
     * Establece el ID de la administración responsable.
     *
     * @param administration_id ID de la administración (soporta valor null).
     */
    public void setAdministration_id(Integer administration_id) {
        this.administration_id = administration_id;
    }

    /**
     * Obtiene el ID del último empleado que configuró la especificación.
     *
     * @return Entero con el ID del empleado.
     */
    public int getLast_employee_update() {
        return last_employee_update;
    }

    /**
     * Establece el ID del último empleado que configuró la especificación.
     *
     * @param last_employee_update ID del empleado.
     */
    public void setLast_employee_update(int last_employee_update) {
        this.last_employee_update = last_employee_update;
    }

    /**
     * Obtiene la fecha y hora de la última actualización.
     *
     * @return LocalDateTime con la marca de tiempo de modificación.
     */
    public LocalDateTime getDate_update() {
        return date_update;
    }

    /**
     * Establece la fecha y hora de la última actualización.
     *
     * @param date_update Marca de tiempo de modificación.
     */
    public void setDate_update(LocalDateTime date_update) {
        this.date_update = date_update;
    }

    /**
     * Obtiene la fecha y hora de registro del elemento.
     *
     * @return LocalDateTime con la marca de tiempo de creación.
     */
    public LocalDateTime getDate_register() {
        return date_register;
    }

    /**
     * Establece la fecha y hora de registro del elemento.
     *
     * @param date_register Marca de tiempo de creación.
     */
    public void setDate_register(LocalDateTime date_register) {
        this.date_register = date_register;
    }

    /**
     * Obtiene la fecha y hora de cancelación o cierre del registro.
     *
     * @return LocalDateTime con la fecha de término o null si sigue activo.
     */
    public LocalDateTime getDate_end() {
        return date_end;
    }

    /**
     * Establece la fecha y hora de cancelación o cierre del registro.
     *
     * @param date_end Fecha y hora de término.
     */
    public void setDate_end(LocalDateTime date_end) {
        this.date_end = date_end;
    }

    // --- CONTRATO EQUALS, HASHCODE & TOSTRING ---
    /**
     * Genera el código hash utilizando el campo identificador llave primaria
     * (id).
     *
     * @return Entero con el hash generado.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        return hash;
    }

    /**
     * Evalúa si dos objetos PaymentSpecificationDTO son iguales comparando su
     * campo id.
     *
     * @param obj Objeto a comparar.
     * @return true si ambos representan el mismo registro en la base de datos,
     * false de lo contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PaymentSpecificationDTO other = (PaymentSpecificationDTO) obj;
        return this.id == other.id;
    }

    /**
     * Representación en formato cadena de texto con los valores de los
     * atributos del DTO.
     *
     * @return Cadena de texto con el contenido formateado.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentSpecificationDTO{");
        sb.append("id=").append(id);
        sb.append(", concept_id=").append(concept_id);
        sb.append(", sequence=").append(sequence);
        sb.append(", import_id=").append(import_id);
        sb.append(", ruler_id=").append(ruler_id);
        sb.append(", fiscal_year=").append(fiscal_year);
        sb.append(", date_start_application=").append(date_start_application);
        sb.append(", date_end_application=").append(date_end_application);
        sb.append(", status=").append(status);
        sb.append(", office_id=").append(office_id);
        sb.append(", administration_id=").append(administration_id);
        sb.append(", last_employee_update=").append(last_employee_update);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

    // --- COMPARABLE ---
    /**
     * Compara dos instancias de PaymentSpecificationDTO por su id numérico para
     * su ordenación natural. Utiliza Integer.compare para evitar situaciones de
     * Integer Overflow.
     *
     * @param o Instancia objetivo de la comparación.
     * @return Valor negativo si este id es menor, 0 si son iguales o positivo
     * si es mayor.
     */
    @Override
    public int compareTo(PaymentSpecificationDTO o) {
        return Integer.compare(this.id, o.id);
    }
}
