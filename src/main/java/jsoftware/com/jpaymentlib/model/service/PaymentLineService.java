/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Optional;
import jsoftware.com.jblue.model.abst.AbstractService;
import jsoftware.com.jpaymentlib.model.dto.PaymentHeaderDTO;
import jsoftware.com.jpaymentlib.model.dto.PaymentLinesDTO;
import jsoftware.com.jutil.db.JDBConnection;

/**
 *
 * @author juanp
 */
public class PaymentLineService extends AbstractService {

    public PaymentLineService(boolean dev_flag, String process_name) {
        super(dev_flag, process_name);
    }

    public Optional<PaymentLinesDTO> getLine(PaymentHeaderDTO header) {
        Optional<PaymentLinesDTO> res = Optional.empty();

        PaymentLinesDTO dto = new PaymentLinesDTO();
        dto.setAmount_due(header.getTotal_amount());
        // FECHA Y HORA DE EMISIÓN ACTUAL
        LocalDateTime timestamp_emission = LocalDateTime.now();
        dto.setTimestamp_emission(timestamp_emission);

        // CÁLCULO DE EXPIRACIÓN: Exactamente a 4 Sábados de distancia
        LocalDateTime timestamp_expiration = timestamp_emission;
        int saturdaysCount = 0;

        // Avanzamos día a día hasta encontrar y contar 4 sábados
        while (saturdaysCount < 4) {
            timestamp_expiration = timestamp_expiration.plusDays(1);
            if (timestamp_expiration.getDayOfWeek() == DayOfWeek.SATURDAY) {
                saturdaysCount++;
            }
        }

        dto.setTimestamp_expiration(timestamp_expiration);
        return Optional.of(dto);
    }

    boolean save(JDBConnection connection, PaymentLinesDTO line) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
