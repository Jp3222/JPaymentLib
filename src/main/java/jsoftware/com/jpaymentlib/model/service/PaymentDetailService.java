package jsoftware.com.jpaymentlib.model.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import jsoftware.com.jutil.db.JDBConnection;

/**
 * Servicio encargado de ejecutar los cobros de tramites correspondientes
 *
 * @author Juan P. Campos C.
 * @since 2026-07-18
 * @version 1.1
 */
public class PaymentDetailService {

    private final PaymentSpecificationDAO dao;
    private final PaymentConceptDAO concept_dao;
    private final PaymentRulersDAO rulers_dao;
    private final PaymentImportDAO imports_dao;

    public PaymentDetailService(boolean flag_dev, String name_module) {
        dao = new PaymentSpecificationDAO();
        concept_dao = new PaymentConceptDAO(flag_dev, name_module);
        rulers_dao = new PaymentRulersDAO();
        imports_dao = new PaymentImportDAO();
    }

    public List<PaymentDetailDTO> getDetailList(JDBConnection connection, List<Integer> concept_list) throws PaymentException, SQLException {
        List<PaymentSpecificationWrapperDTO> list = new ArrayList<>();
        List<PaymentDetailDTO> details = new ArrayList<>();

        // 1. SE OBTIENEN LAS ESPECIFICACIONES DE LOS CONCEPTOS SOLICITADOS
        List<PaymentSpecificationDTO> specifications = dao.getConceptList(connection, concept_list);

        for (PaymentSpecificationDTO spec : specifications) {
            // Nota: Se cambia concept_list por el ID específico del concepto de esta especificación
            int currentConceptId = Integer.parseInt(spec.getConceptId());

            // SE BUSCA EL CONCEPTO INDIVIDUAL
            PaymentConceptDTO concept = concept_dao.getConceptList(connection, concept_list);
            // SE BUSCA SU IMPORTE
            PaymentImportDTO imports = imports_dao.getRulerList(connection, concept_list);
            // SE BUSCA SUS REGLAS
            PaymentRulersDTO ruler = rulers_dao.getRulerList(connection, concept_list);

            // SE CREA Y LLENA EL ENVOLTORIO
            PaymentSpecificationWrapperDTO wrp = new PaymentSpecificationWrapperDTO();
            wrp.setSpecification(spec);
            wrp.setConcepts(concept);
            wrp.setImports(imports);
            wrp.setRulers(ruler);

            // SOLUCIÓN CRÍTICA 1: Se añade el wrapper a la lista para que el siguiente bucle tenga datos
            list.add(wrp);
        }

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
            if (date_end.isBefore(now)) {
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
            detail.put("payment_concept_id", concept.getId());
            detail.put("specification_id", specification.getId()); // SOLUCIÓN 4: Extraer el ID plano

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
}
