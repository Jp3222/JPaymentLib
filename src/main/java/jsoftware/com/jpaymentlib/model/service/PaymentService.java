/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import jsoftware.com.jblue.model.abst.AbstractService;
import jsoftware.com.jblue.model.exp.DataAccesObjectException;
import jsoftware.com.jblue.sys.SystemSession;
import jsoftware.com.jpaymentlib.model.dao.PaymentDAO;
import jsoftware.com.jpaymentlib.model.dao.SysPaymentDAO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentHeaderDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentInputDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentLinesDTO;
import jsoftware.com.jpaymentlib.model.dto.wrp.PaymentWrapper;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author Usuario
 */
public class PaymentService extends AbstractService {

    private final PaymentDAO payment_dao;
    private final PaymentDetailService details_service;
    private final PaymentHeaderService header_service;
    private final PaymentLineService line_service;
    private final SysPaymentDAO sys_pym;

    public PaymentService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
        payment_dao = new PaymentDAO();
        details_service = new PaymentDetailService(dev_flag, process_name);
        header_service = new PaymentHeaderService(dev_flag, process_name);
        line_service = new PaymentLineService(dev_flag, process_name);
        sys_pym = new SysPaymentDAO();
    }

    public PaymentWrapper calculate(JDBConnection connection, PaymentInputDTO input) throws PaymentException, SQLException, DataAccesObjectException {
        switch (input.getService_type()) {
            case 1 -> {
                return getQuoteForProcedure(connection, input);
            }
            case 2 -> {
                return getQuoteForMonthly(connection, input);
            }
            default ->
                throw new AssertionError("SERVICIO NO DEFINIDO: " + input.getService_type());
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
        PaymentDTO pym = new PaymentDTO();
        pym.setCommittee_id(input.getCommittee_id());
        pym.setOffice_id(input.getOffice_id());
        pym.setLast_employee_update(input.getEmployee_id());
        pym.setUser_id(input.getUser_id());
        pym.setObservation("PAGO POR TRAMITE");
        List<Integer> concept_list = sys_pym.getconceptList(
                connection,
                input.getProcess_type_id(),
                input.getCommittee_id(),
                input.getOffice_id()
        );
        if (concept_list.isEmpty()) {
            throw new PaymentException(1, "NO SE PUDIERON OBTENER CONCEPTOS PARA ESTE TRAMITE");
        }
        List<PaymentDetailDTO> detail_list = details_service.getDetailList(connection, concept_list);
        if (detail_list.isEmpty()) {
            throw new PaymentException(2, "NO SE PUDO REALIZAR EL DESGLOSE DEL PAGO");
        }
        Optional<PaymentHeaderDTO> header = header_service.getPaymentHeader(connection, detail_list);
        if (header.isEmpty()) {
            throw new PaymentException(3, "NO SE PUDO CALCULAR LOS IMPORTES DEL PAGO");
        }
        Optional<PaymentLinesDTO> line = line_service.getLine(header.get());
        if (line.isEmpty()) {
            throw new PaymentException(4, "NO SE PUDO CALCULAR LA LINEA DE PAGO");
        }
        wrp.setPayment(pym);
        wrp.setLine(line.get());
        wrp.setConcept_list(concept_list);
        wrp.setDetail(detail_list);
        wrp.setHeader(header.get());

        return wrp;
    }

    public PaymentWrapper getQuoteForMonthly(JDBConnection connection, PaymentInputDTO input) throws PaymentException, SQLException, DataAccesObjectException {
        PaymentWrapper wrp = new PaymentWrapper();

        PaymentDTO pym = new PaymentDTO();
        pym.setCommittee_id(input.getCommittee_id());
        pym.setOffice_id(input.getOffice_id());
        pym.setLast_employee_update(input.getEmployee_id());
        pym.setUser_id(input.getUser_id());
        pym.setObservation("PAGO DE SERVICIO");

        int specification_id = sys_pym.getSpecification(connection, input.getWater_intake_user_id());

        List<PaymentDetailDTO> detail_list = details_service.getDetailList(connection, input.getMonth_list(), specification_id);
        if (detail_list.isEmpty()) {
            throw new PaymentException(1, "NO SE PUDO REALIZAR EL DESGLOSE DEL PAGO");
        }

        Optional<PaymentHeaderDTO> header = header_service.getPaymentHeader(connection, detail_list);
        if (header.isEmpty()) {
            throw new PaymentException(1, "NO SE PUDO CALCULAR LOS IMPORTES DEL PAGO");
        }
        wrp.setPayment(pym);
        wrp.setDetail(detail_list);
        wrp.setHeader(header.get());
        return wrp;
    }

    public boolean save(JDBConnection connection, SystemSession ss, PaymentWrapper pw) {
        boolean res = false;

        try {
            PaymentDTO payment = pw.getPayment();

            String sequence = payment_dao.getPay(connection);
            String line_str = payment_dao.getPayLine(connection);
            //SE ASIGNA LA SECUENCIA DE PAGO
            payment.setSequence(sequence);
            if (payment.getStatus() == 8) {
                //SE ASIGNA LA LINEA DE PAGO
                payment.setLine(line_str);
            }
            res = payment_dao.insert(connection, payment);
            if (!res) {
                throw new PaymentException(1, "NO SE GENERO EL REGISTRO DE PAGO");
            }
            PaymentHeaderDTO header = pw.getHeader();
            header.setPayment_id(payment.getId());
            header.setUuid(UUID.randomUUID().toString().toUpperCase());
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
            for (PaymentDetailDTO i : detail) {
                i.setPayment_id(payment.getId());
            }
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
        } catch (SQLException | PaymentException ex) {
            System.getLogger(PaymentService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return res;
    }

    public boolean paymentService() {
        boolean res = false;
        return res;
    }
}
