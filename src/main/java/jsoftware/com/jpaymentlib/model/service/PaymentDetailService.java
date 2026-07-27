package jsoftware.com.jpaymentlib.model.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jsoftware.com.jpaymentlib.model.dao.PaymentConceptDAO;
import jsoftware.com.jpaymentlib.model.dao.PaymentImportDAO;
import jsoftware.com.jpaymentlib.model.dao.PaymentRulersDAO;
import jsoftware.com.jpaymentlib.model.dao.PaymentSpecificationDAO;
import jsoftware.com.jpaymentlib.model.dto.PaymentConceptDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentImportDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentRulersDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentSpecificationDTO;
import jsoftware.com.jpaymentlib.model.dto.wrp.PaymentSpecificationWrapperDTO;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jpaymentlib.model.l4b.Payment;
import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jutil.db.JDBConnection;

/**
 * Servicio encargado de ejecutar los cobros de tramites correspondientes
 *
 * @author Juan P. Campos C.
 * @since 2026-07-18
 * @version 1.1
 */
public class PaymentDetailService {

    private final PaymentSpecificationDAO specification_dao;
    private final PaymentConceptDAO concept_dao;
    private final PaymentRulersDAO rulers_dao;
    private final PaymentImportDAO imports_dao;

    public PaymentDetailService(boolean flag_dev, String name_module) {
        specification_dao = new PaymentSpecificationDAO();
        concept_dao = new PaymentConceptDAO(flag_dev, name_module);
        rulers_dao = new PaymentRulersDAO();
        imports_dao = new PaymentImportDAO();
    }

    /**
     * Este metodo construye una lista de detalles de pago, a partir de una
     * lista de especificaciones de pago, la funcion implementa unicamente la
     * logica para el pago de un tramite.
     * <br>
     * <br>
     * A pertir de esta lista, se construye la cabezera de pago
     *
     * @param connection - conexion activa de base de datos.
     * @param specification_list - Lista de ID's de las especificaciones a pagar
     * @return una lista de detalles de pago, en otro caso una lista vacia
     * @throws PaymentException
     * @throws SQLException
     */
    public List<PaymentDetailDTO> getDetailList(JDBConnection connection, List<Integer> specification_list) throws PaymentException, SQLException {
        //OBTENCION DE ESPECIFICACIONES
        List<PaymentSpecificationWrapperDTO> list = getPaymentSpecification(connection, specification_list);

        //LISTA DE DETALLES, EN CASO DE SALIR MAL, SE RETORNA VACIA
        List<PaymentDetailDTO> details = new ArrayList<>();

        // 2. LÓGICA DE COBRO Y CONSTRUCCIÓN DE DETALLES
        for (PaymentSpecificationWrapperDTO i : list) {
            PaymentSpecificationDTO specification = i.getSpecification();
            LocalDate now = LocalDate.now();

            // Validación de vigencia: Fecha de inicio
            LocalDate date_start = LocalDate.parse(specification.getDateStartApplication());
            if (date_start.isAfter(now)) {
                continue;
            }

            // Validación de vigencia: Fecha de fin
            LocalDate date_end = LocalDate.parse(specification.getDateEndApplication());
            if (date_end != null && date_end.isBefore(now)) {
                continue;
            }

            // Validación de estado del concepto administrativo
            PaymentConceptDTO concept = i.getConcepts();
            String final_status = concept.getStatus();
            if (!final_status.equals("1")) {
                throw new PaymentException(0, "EL TRAMITE CONTIENE CONCEPTOS ANTIGUOS, CONSULTE EL MODULO DE CONCEPTOS");
            }

            // SE INICIA EL ARMADO DEL DTO DE DETALLE (Mapeo flexible basado en Strings)
            PaymentDetailDTO detail = new PaymentDetailDTO();
            detail.put("specification_id", specification.getId()); // SOLUCIÓN 4: Extraer el ID plano
            detail.put("payment_concept_id", concept.getId());
            detail.put("fiscal_year", String.valueOf(now.getYear()));

            PaymentImportDTO imports = i.getImports();

            // Instancia del motor analítico
            Payment pym = new Payment(i);
            String total = pym.calculation();

            // LÓGICA DE ASIGNACIÓN DE IMPORTES PROCESADOS POR EL MOTOR
            detail.put("quantity", pym.isVariable() ? imports.getUnits() : "1");
            detail.put("unit_price", imports.getAmount());
            detail.put("subtotal", pym.getSubTotal());
            detail.put("surcharge", pym.getSSurcharge());
            detail.put("discount", pym.getSDiscount());
            detail.put("total_amount", total);
            detail.put("fiscal_year", String.valueOf(now.getYear()));
            // SOLUCIÓN CRÍTICA 3: Agregar el detalle construido a la lista final de retorno
            details.add(detail);
        }

        return details;
    }

    /**
     * Es
     * @param connection
     * @param specification_id
     * @param months_list
     * @return
     */
    public List<PaymentDetailDTO> getDetailList(JDBConnection connection, String specification_id, List<String> months_list) {
        return null;
    }

    public List<PaymentSpecificationWrapperDTO> getPaymentSpecification(JDBConnection connection, List<Integer> specification_list) throws SQLException, PaymentException {
        List<PaymentSpecificationWrapperDTO> list = new ArrayList<>();

        // 1. SE OBTIENEN LAS ESPECIFICACIONES DE LOS CONCEPTOS SOLICITADOS
        List<PaymentSpecificationDTO> specifications = specification_dao.getSpecificationList(connection, specification_list);

        for (PaymentSpecificationDTO specification : specifications) {
            // SE BUSCA EL CONCEPTO INDIVIDUAL
            Optional<PaymentConceptDTO> concept = concept_dao.getConceptList(connection, specification.getConceptId());
            if (concept.isEmpty()) {
                throw new PaymentException(1, "CONCEPTO DE PAGO NO EXISTENTE");
            }

            // SE BUSCA SU IMPORTE
            Optional<PaymentImportDTO> imports = imports_dao.getImportList(connection, specification.getImportId());
            if (concept.isEmpty()) {
                throw new PaymentException(1, "IMPORTES DE PAGO NO EXISTENTES");
            }

            // SE BUSCA SUS REGLAS
            Optional<PaymentRulersDTO> ruler = rulers_dao.getRulerList(connection, specification.getRulerId());
            if (concept.isEmpty()) {
                throw new PaymentException(1, "REGLAS DE PAGO NO EXISTENTES");
            }

            // SE CREA Y LLENA EL ENVOLTORIO
            PaymentSpecificationWrapperDTO wrp = new PaymentSpecificationWrapperDTO();
            wrp.setSpecification(specification);
            wrp.setConcepts(concept.get());
            wrp.setImports(imports.get());
            wrp.setRulers(ruler.get());
            // SOLUCIÓN CRÍTICA 1: Se añade el wrapper a la lista para que el siguiente bucle tenga datos
            list.add(wrp);
        }
        return list;
    }
}
