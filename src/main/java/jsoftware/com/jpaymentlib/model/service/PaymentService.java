/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import jsoftware.com.jblue.model.abst.AbstractService;
import jsoftware.com.jblue.model.exp.DataAccesObjectException;
import jsoftware.com.jblue.sys.SystemSession;
import jsoftware.com.jblue.utils.platf.FuncLogs;
import jsoftware.com.jpaymentlib.model.dao.OperationDAO;
import jsoftware.com.jpaymentlib.model.dao.PaymentDAO;
import jsoftware.com.jpaymentlib.model.dao.PaymentReprintDAO;
import jsoftware.com.jpaymentlib.model.dao.PaymentWaterIntakeUserDAO;
import jsoftware.com.jpaymentlib.model.dao.SysPaymentDAO;
import jsoftware.com.jpaymentlib.model.dto.OperationDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentHeaderDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentInputDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentLinesDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentReprintDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentWaterIntakeUserDTO;
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
    private final PaymentWaterIntakeUserDAO user_dao;
    private final PaymentReprintDAO reprin_dao;
    private final OperationDAO operation_dao;
    private final SysPaymentDAO sys_pym;

    public PaymentService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
        payment_dao = new PaymentDAO();
        details_service = new PaymentDetailService(dev_flag, process_name);
        header_service = new PaymentHeaderService(dev_flag, process_name);
        line_service = new PaymentLineService(dev_flag, process_name);
        sys_pym = new SysPaymentDAO();
        user_dao = new PaymentWaterIntakeUserDAO(dev_flag, user_message);
        reprin_dao = new PaymentReprintDAO();
        operation_dao = new OperationDAO();
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
    private PaymentWrapper getQuoteForProcedure(JDBConnection connection, PaymentInputDTO input) throws PaymentException, SQLException {
        PaymentWrapper wrp = new PaymentWrapper();
        PaymentDTO pym = new PaymentDTO();
        pym.setCommittee_id(input.getCommittee_id());
        pym.setOffice_id(input.getOffice_id());
        pym.setAdministration_id(input.getAdministration_id());
        pym.setLast_employee_update(input.getEmployee_id());
        pym.setUser_id(input.getUser_id());
        pym.setObservation("PAGO POR TRAMITE");
        pym.setPayment_type_id(1);
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

    private PaymentWrapper getQuoteForMonthly(JDBConnection connection, PaymentInputDTO input) throws PaymentException, SQLException, DataAccesObjectException {
        PaymentWrapper wrp = new PaymentWrapper();

        PaymentDTO pym = new PaymentDTO();
        pym.setCommittee_id(input.getCommittee_id());
        pym.setOffice_id(input.getOffice_id());
        pym.setLast_employee_update(input.getEmployee_id());
        pym.setUser_id(input.getUser_id());
        pym.setAdministration_id(input.getAdministration_id());
        pym.setPayment_type_id(2);
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
        //
        Optional<PaymentLinesDTO> line = line_service.getLine(header.get());
        if (header.isEmpty()) {
            throw new PaymentException(1, "NO SE PUDO CALCULAR LOS IMPORTES DEL PAGO");
        }
        PaymentWaterIntakeUserDTO payment_water_intake_user = new PaymentWaterIntakeUserDTO();
        payment_water_intake_user.setWater_intake_user_id(input.getWater_intake_user_id());
        payment_water_intake_user.setWater_intake_number(input.getWater_intake_number());
        payment_water_intake_user.setObservation("CONTRIBUYENTE: " + input.getUser_name());
        //
        PaymentReprintDTO reprint = new PaymentReprintDTO();
        reprint.setEmployee_id(input.getEmployee_id());
        //
        OperationDTO operation = new OperationDTO();
        operation.setCommiteeId(input.getCommittee_id());
        operation.setOfficeId(input.getOffice_id());
        operation.setAdministrationId(input.getAdministration_id());
        operation.setEmployeeId(input.getEmployee_id());

        //
        wrp.setPayment(pym);
        wrp.setDetail(detail_list);
        wrp.setHeader(header.get());
        wrp.setReprint(reprint);
        wrp.setPayment_water_intake_user(payment_water_intake_user);
        wrp.setLine(line.get());
        wrp.setOperation(operation);
        return wrp;
    }

    /**
     * Metodo que hace los registro de pago necesario EN
     *
     * @param connection
     * @param ss
     * @param pw
     * @return
     */
    public boolean save(JDBConnection connection, SystemSession ss, PaymentWrapper pw) {
        boolean res = false;

        try {
            PaymentDTO payment = pw.getPayment();
            String sequence = payment_dao.getPay(connection);
            //SE ASIGNA LA SECUENCIA DE PAGO
            payment.setSequence(sequence);
            if (payment.getStatus() == 8) {
                String line_str = payment_dao.getPayLine(connection);
                //SE ASIGNA LA LINEA DE PAGO
                payment.setLine(line_str);
            }
            //----------------------------------------------------------------//
            //REGISTRO DEL PAGO
            res = payment_dao.insert(connection, payment);
            if (!res) {
                throw new PaymentException(1, "NO SE GENERO EL REGISTRO DE PAGO");
            }
            //----------------------------------------------------------------//
            PaymentHeaderDTO header = pw.getHeader();
            header.setPayment_id(payment.getId());
            header.setUuid(UUID.randomUUID().toString().toUpperCase());
            //SI SE APLICARON DESCUENTOS, 
            //SE GRABA EL PRESIDENTE QUE LOS AUTORIZO EN SU MOMENTO
            if (header.isApply_discount()) {
                header.setAuthorized_by(Integer.parseInt(ss.getCurrentAdministration().getPresident()));
            }
            //----------------------------------------------------------------//
            //REGISTRO DE LA CABECERA DEL PAGO
            res = header_service.save(connection, header);
            if (!res) {
                throw new PaymentException(2, "NO SE GENERO LA CABEZERA DE PAGO");
            }
            List<PaymentDetailDTO> detail = pw.getDetail();
            for (PaymentDetailDTO i : detail) {
                i.setPayment_id(payment.getId());
            }
            //----------------------------------------------------------------//
            //REGISTRO DEL PAGO DESGLOSADO
            res = details_service.save(connection, detail);
            if (!res) {
                throw new PaymentException(3, "NO SE GENERO EL PAGO DESGLOSADO");
            }
            //----------------------------------------------------------------//
            //REGISTRO DE INFORMACION DE LA TOMA DE AGUA PAGADA
            PaymentWaterIntakeUserDTO payment_water_intake_user = pw.getPayment_water_intake_user();
            payment_water_intake_user.setPayment_id(payment.getId());
            payment_water_intake_user.setFiscal_year(header.getFiscal_year());
            payment_water_intake_user.setObservation(payment_water_intake_user.getObservation() + ", PAGO: AP, AÑO FISCAL: " + LocalDate.now().getYear());
            res = user_dao.insert(connection, payment_water_intake_user);
            if (!res) {
                throw new PaymentException(4, "NO SE GENERO PAGO DE TOMA");
            }
            //----------------------------------------------------------------//
            //REGISTRO DEL NUMERO DE REIMPRESIONES
            PaymentReprintDTO reprint = pw.getReprint();
            reprint.setPayment_id(payment.getId());
            reprint.setReprint_count(0);//EN CEROS PQ APENAS SE EMITE EL PAGO
            reprint.setObservation("FORMATO GENERADO");
            reprint.setEmployee_id(payment.getLast_employee_update());
            reprint.setStatus(payment.getStatus());//STATUS DEL PAGO(INICIALMENTE DEBERIA SER 1)
            reprint.setDate_end(null);
            res = reprin_dao.insert(connection, reprint);
            if (!res) {
                throw new PaymentException(5, "NO SE GENERO REGISTRO DE REIMPRESION");
            }
            //----------------------------------------------------------------//
            if (pw.isPaid()) {
                //REGISTRO DE INGRESOS
                OperationDTO operation = pw.getOperation();
                operation.setPaymentId(payment.getId());
                operation.setDescription("PAGO AUTOMATICO");
                operation.setOperation("+");
                operation.setAmount(header.getTotal_amount());
                operation.setStatus(payment.getStatus());
                res = operation_dao.insert(connection, operation);
                if (!res) {
                    throw new PaymentException(5, "NO SE GENERO REGISTRO EL INGRESO");
                }
            }

            //SI SE GENERO UNA LINEA DE PAGO SE REGISTRA
            if (pw.isPayment_line()) {
                //REGISTRO DE LINEA DE PAGO
                PaymentLinesDTO line = pw.getLine();
                res = line_service.save(connection, line);
                if (!res) {
                    throw new PaymentException(6, "NO SE GENERO LA LINEA DE PAGO");
                }
            }
        } catch (SQLException | PaymentException ex) {
            FuncLogs.log(getClass(), ex, process_name, "", ex.getMessage());
            return returnMessageError(ex.getMessage());
        }
        return res;
    }

    public boolean paymentService() {
        boolean res = false;
        return res;
    }
}
