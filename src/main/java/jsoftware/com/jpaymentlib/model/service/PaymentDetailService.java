package jsoftware.com.jpaymentlib.model.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jsoftware.com.jblue.model.abst.AbstractService;
import jsoftware.com.jpaymentlib.model.dao.PaymentDetailDAO;
import jsoftware.com.jpaymentlib.model.dto.PaymentConceptDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentImportDTO;
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
public class PaymentDetailService extends AbstractService {

    //
    private final PaymentDetailDAO pym_detail_dao;
    private PaymentSpecificationService specification_service;

    public PaymentDetailService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
        pym_detail_dao = new PaymentDetailDAO(dev_flag, process_name);
        specification_service = new PaymentSpecificationService(dev_flag, process_name);
    }

    public boolean save(JDBConnection connection, List<PaymentDetailDTO> list) throws SQLException, PaymentException, SQLException, SQLException, SQLException, SQLException, SQLException, SQLException {
        boolean res = pym_detail_dao.insert(connection, list);
        if (!res) {
            throw new PaymentException(1, "NO PUDO REALIZAR EL REGISTRO DEL PAGO DESGLOSADO");
        }
        return res;
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
    public List<PaymentDetailDTO> getDetailList(JDBConnection connection, List<Integer> concept_list) throws PaymentException, SQLException {
        if (concept_list.isEmpty()) {
            throw new PaymentException(1, "LISTA DE ESPECIFICACIONES VACIA");
        }
        //OBTENCION DE ESPECIFICACIONES
        List<PaymentSpecificationWrapperDTO> list = specification_service.getPaymentSpecification(connection, concept_list);
        if (list.isEmpty()) {
            throw new PaymentException(2, "LISTA DE ESPECIFICACIONES NO GENERADA");
        }

        //LISTA DE DETALLES, EN CASO DE SALIR MAL, SE RETORNA VACIA
        List<PaymentDetailDTO> details = new ArrayList<>();

        int j = 1;
        // 2. LÓGICA DE COBRO Y CONSTRUCCIÓN DE DETALLES
        for (PaymentSpecificationWrapperDTO i : list) {
            PaymentSpecificationDTO specification = i.getSpecification();
            LocalDate now = LocalDate.now();

            if (specification.getDate_start_application() != null) {
                // Validación de vigencia: Fecha de inicio
                LocalDate date_start = specification.getDate_start_application();
                if (date_start.isAfter(now)) {
                    continue;
                }
            }

            if (specification.getDate_end_application() != null) {
                // Validación de vigencia: Fecha de fin
                LocalDate date_end = specification.getDate_end_application();
                if (date_end != null && date_end.isBefore(now)) {
                    continue;
                }
            }

            // Validación de estado del concepto administrativo
            PaymentConceptDTO concept = i.getConcepts();
            int final_status = concept.getStatus();
            if (final_status != 1) {
                throw new PaymentException(0, "EL TRAMITE CONTIENE CONCEPTOS ANTIGUOS, CONSULTE EL MODULO DE CONCEPTOS");
            }

            // SE INICIA EL ARMADO DEL DTO DE DETALLE (Mapeo flexible basado en Strings)
            PaymentDetailDTO detail = new PaymentDetailDTO();
            detail.setSpecification_id(specification.getId()); // SOLUCIÓN 4: Extraer el ID plano
            detail.setPayment_concept_id(concept.getId());
            detail.setFiscal_year(now.getYear());

            PaymentImportDTO imports = i.getImports();

            // Instancia del motor analítico
            Payment pym = new Payment(i);
            BigDecimal total = pym.calculation();
            detail.setDescription(concept.getName());
            String toString = pym.getObservations().toString();
            detail.setObservation(toString);
            if (toString.contains("S/C")) {
                detail.setStatus(1);
            }
            // LÓGICA DE ASIGNACIÓN DE IMPORTES PROCESADOS POR EL MOTOR
            detail.setNo(j);
            detail.setQuantity(pym.isVariable() ? imports.getUnits() : 1);
            detail.setUnit_price(imports.getAmount());
            detail.setSubtotal(pym.getSub_total());
            detail.setSurcharge(pym.getSurcharge());
            detail.setDiscount(pym.getDiscount());
            detail.setTotal_amount(total);
            detail.setFiscal_year(now.getYear());
            // SOLUCIÓN CRÍTICA 3: Agregar el detalle construido a la lista final de retorno
            details.add(detail);
            j++;
        }
        return details;
    }

    public List<PaymentDetailDTO> getDetailList(JDBConnection connection, List<Integer> month, int specification_id) throws PaymentException, SQLException {
        if (month.isEmpty()) {
            throw new PaymentException(1, "LISTA DE ESPECIFICACIONES VACIA");
        }
        //ONTENCIO DE ESPECIFICACION
        Optional<PaymentSpecificationWrapperDTO> option = specification_service.getPaymentSpecification(connection, specification_id);
        if (option.isEmpty()) {
            throw new PaymentException(2, "LISTA DE ESPECIFICACIONES NO GENERADA");
        }

        //LISTA DE DETALLES, EN CASO DE SALIR MAL, SE RETORNA VACIA
        List<PaymentDetailDTO> details = new ArrayList<>();

        PaymentSpecificationWrapperDTO i = option.get();
        i.setMonth_list(month);
        int j = 1;
        // 2. LÓGICA DE COBRO Y CONSTRUCCIÓN DE DETALLES
        for (Integer k : month) {
            PaymentSpecificationDTO specification = i.getSpecification();
            LocalDate now = LocalDate.now();

            if (specification.getDate_start_application() != null) {
                // Validación de vigencia: Fecha de inicio
                LocalDate date_start = specification.getDate_start_application();
                if (date_start.isAfter(now)) {
                    continue;
                }
            }

            if (specification.getDate_end_application() != null) {
                // Validación de vigencia: Fecha de fin
                LocalDate date_end = specification.getDate_end_application();
                if (date_end != null && date_end.isBefore(now)) {
                    continue;
                }
            }

            // Validación de estado del concepto administrativo
            PaymentConceptDTO concept = i.getConcepts();
            int final_status = concept.getStatus();
            if (final_status != 1) {
                throw new PaymentException(0, "EL TRAMITE CONTIENE CONCEPTOS ANTIGUOS, CONSULTE EL MODULO DE CONCEPTOS");
            }

            // SE INICIA EL ARMADO DEL DTO DE DETALLE (Mapeo flexible basado en Strings)
            PaymentDetailDTO detail = new PaymentDetailDTO();
            detail.setSpecification_id(specification.getId()); // SOLUCIÓN 4: Extraer el ID plano
            detail.setPayment_concept_id(concept.getId());
            detail.setFiscal_year(now.getYear());
            detail.setConcept(concept.getName());
            PaymentImportDTO imports = i.getImports();

            // Instancia del motor analítico
            Payment pym = new Payment(i);
            i.setCurrent_month(k);
            BigDecimal total = pym.calculation();
            detail.setDescription("MENSUALIDAD DEL MES DE " + specification_service.getMonths().get(k - 1).getMonth_name());
            String toString = pym.getObservations() + ", " + specification_service.getMonths().get(k - 1).getShort_name();
            detail.setObservation(toString);
            if (toString.contains("S/C")) {
                detail.setStatus(1);
            }
            // LÓGICA DE ASIGNACIÓN DE IMPORTES PROCESADOS POR EL MOTOR
            detail.setNo(j);
            detail.setQuantity(pym.isVariable() ? imports.getUnits() : 1);
            detail.setUnit_price(imports.getAmount());
            detail.setSubtotal(pym.getSub_total());
            detail.setSurcharge(pym.getSurcharge());
            detail.setDiscount(pym.getDiscount());
            detail.setTotal_amount(total);
            detail.setFiscal_year(now.getYear());
            detail.setPeriod_date(LocalDate.of(now.getYear(), k, i.getRulers().getPayday()));
            // SOLUCIÓN CRÍTICA 3: Agregar el detalle construido a la lista final de retorno
            details.add(detail);
            j++;
        }
        return details;
    }
}
