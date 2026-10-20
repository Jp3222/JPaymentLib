package jsoftware.com.jpaymentlib.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO para la tabla pym_payment_concept. Mapea los conceptos de pago maestros
 * del sistema con sus metadatos y referencias a documentos normativos.
 *
 * @author Juan Pablo Campos Casasanero
 */
public class PaymentConceptDTO implements Serializable, Comparable<PaymentConceptDTO> {

    private static final long serialVersionUID = 1L;

    /**
     * ID del registro (Llave primaria autoincremental)
     */
    private int id;

    /**
     * Folio o secuencia del registro
     */
    private int sequence;

    /**
     * Nombre del concepto
     */
    private String name;

    /**
     * Descripción específica del concepto
     */
    private String description;

    /**
     * ID del documento asociado (Soporta nulos)
     */
    private Integer document_id;

    /**
     * Documento en el cual está basado el importe
     */
    private String based_on_document;

    /**
     * Estatus del registro (1 = Activo por defecto)
     */
    private int status;

    /**
     * ID de la oficina que registró el concepto
     */
    private int office_id;

    /**
     * ID del último empleado que modificó el concepto
     */
    private int last_employee_update;

    /**
     * Fecha y hora de última actualización del registro
     */
    private LocalDateTime date_update;

    /**
     * Fecha y hora de registro del concepto
     */
    private LocalDateTime date_register;

    /**
     * Fecha y hora de finalización del concepto (Soporta nulos)
     */
    private LocalDateTime date_end;

    /**
     * Constructor por defecto. Inicializa los tipos primitivos con valores
     * seguros por omisión (-1).
     */
    public PaymentConceptDTO() {
        this.id = -1;
        this.sequence = -1;
        this.name = null;
        this.description = null;
        this.document_id = null;
        this.based_on_document = null;
        this.status = 1;
        this.office_id = -1;
        this.last_employee_update = -1;
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
     * @param id Identificador del registro.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el folio o secuencia del registro.
     *
     * @return Entero con la secuencia.
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * Establece el folio o secuencia del registro.
     *
     * @param sequence Secuencia del concepto.
     */
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    /**
     * Obtiene el nombre del concepto.
     *
     * @return String con el nombre.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del concepto.
     *
     * @param name Nombre asignado.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la descripción específica del concepto.
     *
     * @return String con la descripción o null si no existe.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Establece la descripción específica del concepto.
     *
     * @param description Detalle descriptivo.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtiene el ID del documento normativo/legal asociado.
     *
     * @return Integer con el ID del documento o null.
     */
    public Integer getDocument_id() {
        return document_id;
    }

    /**
     * Establece el ID del documento asociado.
     *
     * @param document_id ID del documento.
     */
    public void setDocument_id(Integer document_id) {
        this.document_id = document_id;
    }

    /**
     * Obtiene la referencia del documento base del importe.
     *
     * @return String con la descripción del documento base.
     */
    public String getBased_on_document() {
        return based_on_document;
    }

    /**
     * Establece la referencia del documento base del importe.
     *
     * @param based_on_document Documento de respaldo.
     */
    public void setBased_on_document(String based_on_document) {
        this.based_on_document = based_on_document;
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
     * @param status Identificador de estatus.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Obtiene el ID de la oficina de registro.
     *
     * @return Entero con el ID de la oficina.
     */
    public int getOffice_id() {
        return office_id;
    }

    /**
     * Establece el ID de la oficina de registro.
     *
     * @param office_id ID de la oficina.
     */
    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    /**
     * Obtiene el ID del último empleado que modificó el concepto.
     *
     * @return Entero con el ID del empleado.
     */
    public int getLast_employee_update() {
        return last_employee_update;
    }

    /**
     * Establece el ID del último empleado que modificó el concepto.
     *
     * @param last_employee_update ID del empleado.
     */
    public void setLast_employee_update(int last_employee_update) {
        this.last_employee_update = last_employee_update;
    }

    /**
     * Obtiene la fecha y hora de la última actualización.
     *
     * @return LocalDateTime con la marca de tiempo.
     */
    public LocalDateTime getDate_update() {
        return date_update;
    }

    /**
     * Establece la fecha y hora de la última actualización.
     *
     * @param date_update Marca de tiempo de actualización.
     */
    public void setDate_update(LocalDateTime date_update) {
        this.date_update = date_update;
    }

    /**
     * Obtiene la fecha y hora de registro del concepto.
     *
     * @return LocalDateTime con la marca de tiempo de creación.
     */
    public LocalDateTime getDate_register() {
        return date_register;
    }

    /**
     * Establece la fecha y hora de registro del concepto.
     *
     * @param date_register Marca de tiempo de creación.
     */
    public void setDate_register(LocalDateTime date_register) {
        this.date_register = date_register;
    }

    /**
     * Obtiene la fecha y hora de finalización del concepto.
     *
     * @return LocalDateTime con la fecha de término o null si está activo.
     */
    public LocalDateTime getDate_end() {
        return date_end;
    }

    /**
     * Establece la fecha y hora de finalización del concepto.
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
        int hash = 5;
        hash = 89 * hash + this.id;
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
        final PaymentConceptDTO other = (PaymentConceptDTO) obj;
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
        sb.append("PaymentConceptDTO{");
        sb.append("id=").append(id);
        sb.append(", sequence=").append(sequence);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", document_id=").append(document_id);
        sb.append(", based_on_document=").append(based_on_document);
        sb.append(", status=").append(status);
        sb.append(", office_id=").append(office_id);
        sb.append(", last_employee_update=").append(last_employee_update);
        sb.append(", date_update=").append(date_update);
        sb.append(", date_register=").append(date_register);
        sb.append(", date_end=").append(date_end);
        sb.append('}');
        return sb.toString();
    }

    // --- COMPARABLE ---
    /**
     * Compara dos instancias de PaymentConceptDTO por su id numérico para
     * ordenamiento natural. Se apoya en Integer.compare para mitigar riesgos de
     * Integer Overflow.
     *
     * @param o Objeto objetivo de la comparación.
     * @return Valor negativo si este id es menor, 0 si son iguales o positivo
     * si es mayor.
     */
    @Override
    public int compareTo(PaymentConceptDTO o) {
        return Integer.compare(this.id, o.id);
    }
}
