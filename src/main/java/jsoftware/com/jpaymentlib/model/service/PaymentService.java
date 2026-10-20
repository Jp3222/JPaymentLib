/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import jsoftware.com.jblue.model.abst.AbstractService;
import jsoftware.com.jblue.sys.SystemSession;
import jsoftware.com.jblue.utils.TestUtils;
import jsoftware.com.jpaymentlib.model.dao.PaymentDAO;
import jsoftware.com.jpaymentlib.model.dao.SysPaymentDAO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentHeaderDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentInputBuilder;
import jsoftware.com.jpaymentlib.model.dto.PaymentInputDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentLinesDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentRulersDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentSpecificationDTO;
import jsoftware.com.jpaymentlib.model.dto.wrp.PaymentSpecificationWrapperDTO;
import jsoftware.com.jpaymentlib.model.dto.wrp.PaymentWrapper;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author Usuario
 */
public class PaymentService extends AbstractService {

    private PaymentDAO payment_dao;
    private PaymentDetailService details_service;
    private PaymentHeaderService header_service;
    private PaymentLineService line_service;
    private SysPaymentDAO sys_pym;

    public PaymentService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
        payment_dao = new PaymentDAO();
        details_service = new PaymentDetailService(dev_flag, process_name);
        header_service = new PaymentHeaderService(dev_flag, process_name);
        sys_pym = new SysPaymentDAO();
    }

    public static void main(String... args) {
        JDBConnection connection;
        try {
            connection = TestUtils.connection();
            PaymentService service = new PaymentService(false, "xd");
            PaymentDTO dto = new PaymentDTO();
            dto.setStatus(1);
            PaymentInputDTO input = new PaymentInputBuilder()
                    .setCommittee_id(1)
                    .setEmployee_id(1)
                    .setOffice_id(1)
                    .setStatus(1)
                    .setPayment_method_id(1)
                    .setPayment_type_id(1)
                    .setProcess_id(1)
                    .setProcess_type_id(1)
                    .setUser_id(1)
                    .setWki_id(1)
                    .setQuote_for_process(true)
                    .builder();
            input.setCommittee_id(1);
            input.setEmployee_id(1);
            input.setOffice_id(1);
            input.setPayment_method_id(6);//NO DEFINIDO
            input.setPayment_type_id(1);
            input.setProcess_id(1);
            input.setProcess_type_id(1);
            input.setQuote_for_process(true);
            input.setStatus(8);
            input.setUser_id(1);
            input.setWki_id(1);
            PaymentWrapper paymentProcess = service.calculate(connection, input);
            System.out.println(paymentProcess.toString());

        } catch (IOException | SQLException | PaymentException ex) {
            System.getLogger(PaymentService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    public PaymentWrapper calculate(JDBConnection connection, PaymentInputDTO input) throws PaymentException, SQLException {
        if (input.isQuote_for_process()) {
            return getQuoteForProcedure(connection, input);
        } else {
            return getPaymentForProcedure(connection, input);
        }
    }

    /**
     * Funcion para la cotizacion de un tramite
     *
     * @param connection
     * @param input
     * @return
     * @throws PaymentException
     * @throws SQLException
     */
    public PaymentWrapper getQuoteForProcedure(JDBConnection connection, PaymentInputDTO input) throws PaymentException, SQLException {
        PaymentWrapper wrp = new PaymentWrapper();
        List<Integer> concept_list = sys_pym.getconceptList(connection, input.getProcess_type_id());
        if (concept_list.isEmpty()) {
            throw new PaymentException(1, "NO SE PUDIERON OBTENER CONCEPTOS PARA ESTE TRAMITE");
        }
        List<PaymentDetailDTO> detail_list = details_service.getDetailList(connection, concept_list);
        if (detail_list.isEmpty()) {
            throw new PaymentException(1, "NO SE PUDO REALIZAR EL DESGLOSE DEL PAGO");
        }
        Optional<PaymentHeaderDTO> header = header_service.getPaymentHeader(connection, detail_list);
        if (header.isEmpty()) {
            throw new PaymentException(1, "NO SE PUDO CALCULAR LOS IMPORTES DEL PAGO");
        }
        wrp.setConcept_list(concept_list);
        wrp.setDetail(detail_list);
        wrp.setHeader(header.get());
        return wrp;
    }

    public PaymentWrapper getPaymentForProcedure(JDBConnection connection, PaymentInputDTO input) throws SQLException, PaymentException {
        PaymentWrapper wrp = new PaymentWrapper();

        // 1. Mapeo correcto del DTO de pago
        String sequence = payment_dao.getPaymentReferenceNumber(connection);
        PaymentDTO dto = new PaymentDTO();
        dto.setSequence(sequence);
        dto.setPayment_type_id(input.getPayment_type_id());
        dto.setPayment_method_id(input.getPayment_method_id());
        dto.setUser_id(input.getUser_id());
        dto.setStatus(input.getStatus());

        // 2. Obtención de conceptos y construcción de estructura
        List<Integer> concept_list = sys_pym.getconceptList(connection, input.getProcess_type_id());
        if (concept_list.isEmpty()) {
            throw new PaymentException(1, "NO SE PUDIERON OBTENER CONCEPTOS PARA ESTE TRAMITE");
        }
        wrp.setConcept_list(concept_list);

        List<PaymentDetailDTO> detail_list = details_service.getDetailList(connection, concept_list);
        if (detail_list.isEmpty()) {
            throw new PaymentException(2, "NO SE PUDO GENERAR EL DESGLOSE DEL PAGO");
        }

        Optional<PaymentHeaderDTO> header = header_service.getPaymentHeader(connection, detail_list);
        if (header.isEmpty()) {
            throw new PaymentException(3, "NO SE PUDO GENERAR LA CABECERA DE PAGO");
        }

        // 3. Procesamiento de línea de pago si el estado es PENDIENTE (8)
        if (dto.getStatus() == 8) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String line = payment_dao.getPayLine(connection);

            PaymentLinesDTO line_dto = new PaymentLinesDTO();
            line_dto.setPayment_line(line);
            line_dto.setAmount_due(header.get().getTotal_amount());

            LocalDateTime now = LocalDateTime.now();
            line_dto.setTimestamp_emission(now);

            // Validar que la especificación y las reglas existan antes de acceder
            PaymentSpecificationWrapperDTO specification = wrp.getSpecification_list();
            if (specification == null || specification.getRulers() == null) {
                throw new PaymentException(0, "NO EXISTEN REGLAS DE PAGO ASOCIADAS");
            }

            PaymentRulersDTO rulers = specification.getRulers();
            int daysCovered = rulers.getDays_covered();
            if (daysCovered <= 0) {
                throw new PaymentException(0, "ERROR AL GENERAR LA LINEA DE PAGO: DÍAS CUBIERTOS INVÁLIDOS (" + daysCovered + ")");
            }

            LocalDateTime expiration = now.plusDays(daysCovered);
            line_dto.setTimestamp_expiration(expiration);
            line_dto.setStatus(dto.getStatus());
            wrp.setLine(line_dto);
        }

        wrp.setPayment(dto);
        wrp.setHeader(header.get());
        wrp.setDetail(detail_list);
        return wrp;
    }

    public PaymentWrapper getPaymentForMonth(JDBConnection connection, PaymentSpecificationDTO dto, int pay_month) throws PaymentException, SQLException {
        PaymentWrapper wrp = new PaymentWrapper();
        List<Integer> concept_list = sys_pym.getconceptList(connection, pay_month);
        if (concept_list.isEmpty()) {
            throw new PaymentException(1, "NO SE PUDIERON OBTENER CONCEPTOS PARA ESTE TRAMITE");
        }
        
        List<PaymentDetailDTO> detail_list = details_service.getDetailList(connection, concept_list);
        if (detail_list.isEmpty()) {
            throw new PaymentException(1, "NO SE PUDO REALIZAR EL DESGLOSE DEL PAGO");
        }
        
        Optional<PaymentHeaderDTO> header = header_service.getPaymentHeader(connection, detail_list);
        if (header.isEmpty()) {
            throw new PaymentException(1, "NO SE PUDO CALCULAR LOS IMPORTES DEL PAGO");
        }
        wrp.setConcept_list(concept_list);
        wrp.setDetail(detail_list);
        wrp.setHeader(header.get());
        return wrp;
    }

    public boolean save(JDBConnection connection, SystemSession ss, PaymentWrapper pw) throws SQLException, PaymentException {
        boolean res = false;
        PaymentDTO payment = pw.getPayment();
        payment.setLast_employee_update(Integer.parseInt(ss.getCurrentEmployee().getId()));
        payment.setCommittee_id(Integer.parseInt(ss.getCurrent_instance().getCommitteeId()));
        payment.setOffice_id(Integer.parseInt(ss.getCurrentEmployee().getOfficeId()));

        res = payment_dao.insert(connection, payment);
        if (!res) {
            throw new PaymentException(1, "NO SE GENERO EL REGISTRO DE PAGO");
        }
        PaymentHeaderDTO header = pw.getHeader();
        //SI SE APLICARON DESCUENTOS, 
        //SE GRABA EL PRESIDENTE QUE LOS AUTORIZO EN SU MOMENTO
        if (header.isApply_discount()) {
            header.setAuthorized_by(Integer.parseInt(ss.getCurrentAdministration().getPresident()));
        }
        res = header_service.save(connection, header);
        if (!res) {
            throw new PaymentException(2, "NO SE GENERO LA CABEZERA DE PAGO");
        }
        List<PaymentDetailDTO> detail = pw.getDetail();
        res = details_service.save(connection, detail);
        if (!res) {
            throw new PaymentException(1, "NO SE GENERO EL PAGO DESGLOSADO");
        }
        if (pw.getLine() == null) {
            return res;
        }
        PaymentLinesDTO line = pw.getLine();
        res = line_service.save(connection, line);
        if (!res) {
            throw new PaymentException(1, "NO SE GENERO LA LINEA DE PAGO");
        }
        return res;
    }

    public boolean paymentService() {
        boolean res = false;
        return res;
    }
}
