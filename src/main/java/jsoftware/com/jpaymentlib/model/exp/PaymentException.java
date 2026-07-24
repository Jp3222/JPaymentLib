/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.exp;

/**
 *
 * @author juanp
 */
public class PaymentException extends Exception {

    public PaymentException(int error_code, String msg ) {
        super(msg);
    }

}
