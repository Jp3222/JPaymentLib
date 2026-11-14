/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.service;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Optional;
import jsoftware.com.jblue.model.abst.AbstractService;
import jsoftware.com.jblue.model.dto.ProgramHistoryDTO;
import jsoftware.com.jblue.model.exp.ServiceException;
import jsoftware.com.jblue.model.exp.imp.CorruptInsertionException;
import jsoftware.com.jblue.model.exp.imp.KeyNotGenerateException;
import jsoftware.com.jblue.model.service.HistoryService;
import jsoftware.com.jblue.sys.SystemSession;
import jsoftware.com.jpaymentlib.model.dao.PaymentConceptDAO;
import jsoftware.com.jpaymentlib.model.dao.PaymentDAO;
import jsoftware.com.jpaymentlib.model.dto.PaymentConceptDTO;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jpaymentlib.model.l4b.Payment;
import jsoftware.com.jpaymentlib.util.Func;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentConceptService extends AbstractService {

    private PaymentDAO pym;
    private PaymentConceptDAO dao;
    private HistoryService hys;

    public PaymentConceptService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
        pym = new PaymentDAO();
        dao = new PaymentConceptDAO(dev_flag, process_name);
        hys = new HistoryService(dev_flag, process_name);
    }

    public boolean save(JDBConnection connection, SystemSession ss, PaymentConceptDTO dto) {
        boolean res = false;
        try {
            String sequence = pym.getNextValString(connection, "CONCEPTOS_PAGO");
            if (Func.isNullEmptyBlank(sequence)) {
                return returnMessageError("NO SE PUDO GENERAR EL FOLIO DEL CONCEPTO");
            }

            int final_employee = Integer.parseInt(ss.getCurrentEmployee().getId());
            int final_office = Integer.parseInt(ss.getCurrent_instance().getOfficeId());

            // 1. Concepto
            dto.setLast_employee_update(final_employee);
            dto.setOffice_id(final_office);
            dto.setStatus(1);
            // Ajusta la negación (!) si tu insert devuelve true en éxito
            res = dao.insert(connection, dto);
            if (!res) {
                return returnMessageError("NO SE PUDO REGISTRAR EL CONCEPTO DE PAGO");
            }

            ProgramHistoryDTO history = ss.getProgramHistoryDTO(115, dto.getId());
            res = hys.insert(connection, ss, history);
            if (!res) {
                return returnMessageError("REGISTRO EN BITACORA CORRUPTO");
            }
            res = true;
        } catch (SQLException | PaymentException | ServiceException | UnknownHostException | CorruptInsertionException | KeyNotGenerateException ex) {
            res = false;
            System.getLogger(PaymentConceptService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return res;
    }

    public boolean update() {
        boolean res = false;
        return res;
    }

    Optional<PaymentConceptDTO> getConceptList(JDBConnection connection, int concept_id) throws SQLException {
        return dao.getConceptList(connection, concept_id);
    }

}
