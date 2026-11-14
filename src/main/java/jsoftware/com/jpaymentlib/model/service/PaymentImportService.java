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
import jsoftware.com.jpaymentlib.model.dao.PaymentImportDAO;
import jsoftware.com.jpaymentlib.model.dto.PaymentImportDTO;
import jsoftware.com.jpaymentlib.model.exp.PaymentException;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentImportService extends AbstractService {

    private PaymentImportDAO dao;
    private HistoryService hys;

    public PaymentImportService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
        dao = new PaymentImportDAO();
        hys = new HistoryService(dev_flag, process_name);
    }

    public boolean save(JDBConnection connection, SystemSession ss, PaymentImportDTO dto) {
        boolean res = false;
        try {
            int final_employee = Integer.parseInt(ss.getCurrentEmployee().getId());
            int final_admin = Integer.parseInt(ss.getCurrentAdministration().getId());

            dto.setAdministration_id(final_admin);
            dto.setLast_employee_update(final_employee);

            if (!dao.insert(connection, dto)) {
                returnMessageError("NO SE PUDO REGISTRAR EL IMPORTE DE PAGO");
                return false;
            }

            ProgramHistoryDTO history2 = ss.getProgramHistoryDTO(116, dto.getId());
            if (!hys.insert(connection, ss, history2)) {
                returnMessageError("REGISTRO EN BITACORA CORRUPTO");
                return false;
            }

        } catch (ServiceException | SQLException | UnknownHostException | CorruptInsertionException | KeyNotGenerateException | PaymentException ex) {
            System.getLogger(PaymentImportService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return res;
    }

    public boolean update() {
        boolean res = false;
        return res;
    }

    Optional<PaymentImportDTO> getImportList(JDBConnection connection, int import_id) throws SQLException {
        return dao.getImportList(connection, import_id);
    }
}
