package saml.uur.utils;

import javafx.beans.property.SimpleIntegerProperty;

/******************************************************************************
 * Instances of class saml.uur.utils.CheckedSimpleIntegerProperty are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class CheckedSimpleIntegerProperty extends SimpleIntegerProperty {


    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    public CheckedSimpleIntegerProperty(int initialValue) {
        super(initialValue);
        setInternal(initialValue);
    }

    //==========================================================================
    //== PUBLIC METHODS OF INSTANCES ===========================================
    @Override
    public void set(int newValue) {
        setInternal(newValue);
    }

    @Override
    public void setValue(Number v) {
        setInternal(v.intValue());
    }

    //== PRIVATE METHODS OF INSTANCES ==========================================

    private void setInternal(int intValue) {
        if (intValue >= Utils.MIN_SIZE && intValue <= Utils.MAX_SIZE) {
            super.set(intValue);
        }
    }

}
