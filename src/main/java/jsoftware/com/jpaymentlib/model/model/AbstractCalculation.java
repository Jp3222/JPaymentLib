/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jpaymentlib.model.model;

import jsoftware.com.jpaymentlib.util.FuncBusiness;

/**
 *
 * @author juanp
 */
public abstract class AbstractCalculation implements CalculationModel {
    private boolean apply;

    public AbstractCalculation() {
    }
    
    @Override
    public void setApply(String apply) {
        this.apply = FuncBusiness.isApply(apply);
    }

    @Override
    public boolean isApply() {
        return apply;
    }

}
