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
import jsoftware.com.jpaymentlib.model.dao.PaymentRulersDAO;
import jsoftware.com.jpaymentlib.model.dto.PaymentRulersDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentRulerService extends AbstractService {

    private PaymentRulersDAO dao;
    private HistoryService hys;

    public PaymentRulerService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
        dao = new PaymentRulersDAO();
        hys = new HistoryService(dev_flag, process_name);
    }

    public boolean save(JDBConnection connection, SystemSession ss, PaymentRulersDTO dto) {
        boolean res = false;
        try {
            // Nota: ajusta los campos ID o lógica de setters si aplican a rulers
            if (!dao.insert(connection, dto)) {
                returnMessageError("NO SE PUDO REGISTRAR LA REGLA DEL IMPORTE DE PAGO");
                return false;
            }

            ProgramHistoryDTO history3 = ss.getProgramHistoryDTO(117, dto.getId());
            if (hys.insert(connection, ss, history3)) {
                returnMessageError("REGISTRO EN BITACORA CORRUPTO");
                return false;
            }

        } catch (ServiceException | SQLException | UnknownHostException | CorruptInsertionException | KeyNotGenerateException ex) {
            System.getLogger(PaymentRulerService.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return res;
    }

    public boolean update() {
        boolean res = false;
        return res;
    }

    Optional<PaymentRulersDTO> getRulerList(JDBConnection connection, int ruler_id) throws SQLException {
        return dao.getRulerList(connection, ruler_id);
    }
}
