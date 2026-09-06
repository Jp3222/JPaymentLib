/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.model;

/**
 *
 * @author juanp
 */
public abstract class AbstractCalculation implements CalculationModel {
    private boolean apply;

    public AbstractCalculation() {
    }
    
    @Override
    public void setApply(boolean apply) {
        this.apply = apply;
    }

    @Override
    public boolean isApply() {
        return apply;
    }

}
