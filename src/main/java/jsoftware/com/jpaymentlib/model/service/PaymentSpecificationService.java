/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import jsoftware.com.jblue.model.abst.AbstractService;
import jsoftware.com.jpaymentlib.model.dao.PaymentConceptDAO;
import jsoftware.com.jpaymentlib.model.dao.PaymentImportDAO;
import jsoftware.com.jpaymentlib.model.dao.PaymentRulersDAO;
import jsoftware.com.jpaymentlib.model.dao.PaymentSpecificationDAO;
import jsoftware.com.jpaymentlib.model.dto.MonthDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentConceptDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentImportDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentRulersDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentSpecificationDTO;
import jsoftware.com.jpaymentlib.model.dto.wrp.PaymentSpecificationWrapperDTO;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentSpecificationService extends AbstractService {

    private final PaymentSpecificationDAO specification_dao;
    private final PaymentConceptDAO concept_dao;
    private final PaymentRulersDAO rulers_dao;
    private final PaymentImportDAO imports_dao;

    public PaymentSpecificationService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
        specification_dao = new PaymentSpecificationDAO();
        concept_dao = new PaymentConceptDAO(dev_flag, process_name);
        rulers_dao = new PaymentRulersDAO();
        imports_dao = new PaymentImportDAO();
    }

    public List<PaymentSpecificationWrapperDTO> getPaymentSpecification(JDBConnection connection, List<Integer> specification_list) throws SQLException, PaymentException {
        List<PaymentSpecificationWrapperDTO> list = new ArrayList<>();

        // 1. SE OBTIENEN LAS ESPECIFICACIONES DE LOS CONCEPTOS SOLICITADOS
        List<PaymentSpecificationDTO> specifications = specification_dao.getSpecificationList(connection, specification_list);

        for (PaymentSpecificationDTO specification : specifications) {
            // SE BUSCA EL CONCEPTO INDIVIDUAL
            Optional<PaymentConceptDTO> concept = concept_dao.getConceptList(connection, specification.getConcept_id());
            System.out.println(concept.get().toString());
            if (!concept.isPresent()) {
                throw new PaymentException(1, "CONCEPTO DE PAGO NO EXISTENTE");
            }

            // SE BUSCA SU IMPORTE
            Optional<PaymentImportDTO> imports = imports_dao.getImportList(connection, specification.getImport_id());
            if (!imports.isPresent()) {
                throw new PaymentException(1, "IMPORTES DE PAGO NO EXISTENTES");
            }

            // SE BUSCA SUS REGLAS
            Optional<PaymentRulersDTO> ruler = rulers_dao.getRulerList(connection, specification.getRuler_id());
            System.out.println(ruler.get().toString());
            if (ruler.isEmpty()) {
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

    public Optional<PaymentSpecificationWrapperDTO> getPaymentSpecification(JDBConnection connection, int id) throws SQLException, PaymentException {
        Optional<PaymentSpecificationWrapperDTO> opt = Optional.empty();

        // 1. SE OBTIENEN LAS ESPECIFICACIONES DE LOS CONCEPTOS SOLICITADOS
        Optional<PaymentSpecificationDTO> specifications = specification_dao.getSpecification(connection, id);

        if (specifications.isPresent()) {
            PaymentSpecificationDTO specification = specifications.get();
            // SE BUSCA EL CONCEPTO INDIVIDUAL
            Optional<PaymentConceptDTO> concept = concept_dao.getConceptList(connection, specification.getConcept_id());
            System.out.println(concept.get().toString());
            if (!concept.isPresent()) {
                throw new PaymentException(1, "CONCEPTO DE PAGO NO EXISTENTE");
            }

            // SE BUSCA SU IMPORTE
            Optional<PaymentImportDTO> imports = imports_dao.getImportList(connection, specification.getImport_id());
            System.out.println(imports.get().toString());
            if (!concept.isPresent()) {
                throw new PaymentException(1, "IMPORTES DE PAGO NO EXISTENTES");
            }

            // SE BUSCA SUS REGLAS
            Optional<PaymentRulersDTO> ruler = rulers_dao.getRulerList(connection, specification.getRuler_id());
            System.out.println(ruler.get().toString());
            if (!concept.isPresent()) {
                throw new PaymentException(1, "REGLAS DE PAGO NO EXISTENTES");
            }

            // SE CREA Y LLENA EL ENVOLTORIO
            PaymentSpecificationWrapperDTO wrp = new PaymentSpecificationWrapperDTO();
            wrp.setSpecification(specification);
            wrp.setConcepts(concept.get());
            wrp.setImports(imports.get());
            wrp.setRulers(ruler.get());
            opt = Optional.of(wrp);
        }
        return opt;
    }

    public List<MonthDTO> getMonths(List<Integer> list) {
        return months.stream().filter((t) -> list.contains(t.getMonth_id())).toList();

    }

    private MonthDTO ENE = new MonthDTO(1, "ENERO", "ENE");
    private MonthDTO FEB = new MonthDTO(2, "FEBRERO", "FEB");
    private MonthDTO MAR = new MonthDTO(3, "MARZO", "MAR");
    private MonthDTO ABR = new MonthDTO(4, "ABRIL", "ABR");
    private MonthDTO MAY = new MonthDTO(5, "MAYO", "MAY");
    private MonthDTO JUN = new MonthDTO(6, "JUNIO", "JUN");
    private MonthDTO JUL = new MonthDTO(7, "JULIO", "JUL");
    private MonthDTO AGO = new MonthDTO(8, "AGOSTO", "AGO");
    private MonthDTO SEP = new MonthDTO(9, "SEPIEMBRE", "SEP");
    private MonthDTO OCT = new MonthDTO(10, "OCTUBRE", "OCT");
    private MonthDTO NOV = new MonthDTO(11, "NOVIEMBRE", "NOV");
    private MonthDTO DIC = new MonthDTO(12, "DICIEMBRE", "DIC");

    private final List<MonthDTO> months = new ArrayList(Arrays.asList(ENE, FEB, MAR, ABR, MAY, JUN, JUL, AGO, SEP, OCT, NOV, DIC));
}
