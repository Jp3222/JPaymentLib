/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jsoftware.com.jpaymentlib.model.dao.PaymentDAO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentDetailDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentHeaderDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentLinesDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentRulersDTO;
import jsoftware.com.jpaymentlib.model.dto.wrp.PaymentSpecificationWrapperDTO;
import jsoftware.com.jpaymentlib.model.dto.wrp.PaymentWrapper;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author Usuario
 */
public class PaymentService {

    private PaymentDAO payment_dao;
    private PaymentDetailService details_service;
    private PaymentHeaderService header_service;

    public PaymentService(boolean flag_dev, String name_module) {
        payment_dao = new PaymentDAO();
        details_service = new PaymentDetailService(flag_dev, name_module);
        header_service = new PaymentHeaderService(flag_dev, name_module);
    }

    public PaymentWrapper paymentProcess(JDBConnection connection, PaymentDTO dto, List<Integer> concept_list) throws SQLException, PaymentException {
        boolean res = false;
        //SE GENERA EL REGISTRO DE PAGO
        String sequence = payment_dao.getPayLine(connection);
        dto.put("payment_type_id", "1");
        dto.put("sequence", sequence);
        String line = null;
        PaymentWrapper wrp = new PaymentWrapper();
        wrp.setConcept_list(concept_list);
        //SE GENERA EL DESGLOSE DEL PAGO
        List<PaymentDetailDTO> detail_list = details_service.getDetailList(connection, wrp);
        if (detail_list.isEmpty()) {
            throw new PaymentException(2, "NO SE PUDO GENERAR EL DESGLOSE DEL PAGO");
        }
        //SE GENERA LA CABECERA DEL PAGO
        PaymentHeaderDTO header = header_service.getPaymentHeader(connection, detail_list);
        if (header == null) {
            throw new PaymentException(3, "NO SE PUDO GENERAR LA CABECERA DE PAGO");
        }
        //wrp.setLine(pym_line);
        //SI EL STATUS ES 8 QUEDA COMO PAGO PENDIENTE
        if (dto.getStatus().equals("8")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //SE GENERA LA LINEA DE PAGO, PARA PAGAR MAS TARDE
            line = payment_dao.getPayLine(connection);
            PaymentLinesDTO line_dto = new PaymentLinesDTO();
            line_dto.put("payment_line", line);
            line_dto.put("amount_due", header.getTotalAmount());
            LocalDateTime now = LocalDateTime.now();
            line_dto.put("timestamp_emission", now.format(formatter));
            PaymentSpecificationWrapperDTO specification = wrp.getSpecification_list();
            PaymentRulersDTO rulers = specification.getRulers();
            if (rulers == null) {
                throw new PaymentException(0, "NO EXISTEN REGLAS DE PAGO");
            }
            if (rulers.getDaysCovered() == null) {
                throw new PaymentException(0, "ERROR AL GENERAR LA LINEA DE PAGO: LA REGLA '" + rulers.getName() + "' NO TIENE DIAS CUBIERTOS");
            }
            int value = Integer.parseInt(rulers.getDaysCovered());
            if (value <= 0) {
                throw new PaymentException(0, "ERROR AL GENERAR LA LINEA DE PAGO: LA REGLA '" + rulers.getName() + "' TIENE '" + value + "' DIAS CUBIERTOS");
            }
            LocalDateTime expiration = now.plusDays(value);
            line_dto.put("timestamp_expiration", expiration.format(formatter));
            line_dto.put("timestamp_paid", null);
            line_dto.put("bank_institution", "N/A");
            line_dto.put("bank_transaction_id", null);
            line_dto.put("employee_id", null);
            line_dto.put("status", "1");
            wrp.setLine(line_dto);
        }
        wrp.setPayment(dto);
        wrp.setHeader(header);
        wrp.setDetail(detail_list);

        return wrp;
    }

    public boolean save(JDBConnection connection, PaymentWrapper pw) throws SQLException, PaymentException {
        boolean res = false;
        res = payment_dao.insert(connection, pw.getPayment());
        if (!res) {
            throw new PaymentException(1, "NO SE GENERO EL REGISTRO DE PAGO");
        }
        res = header_service.save(connection, pw.getHeader());
        if (!res) {
            throw new PaymentException(2, "NO SE GENERO LA CABEZERA DE PAGO");
        }
        res = details_service.save(connection, pw.getDetail());
        if (!res) {
            throw new PaymentException(1, "NO SE GENERO EL PAGO DESGLOSADO");
        }
        return res;
    }

    public boolean paymentService() {
        boolean res = false;
        return res;
    }
}
