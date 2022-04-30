package saml.uur.language;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/******************************************************************************
 * Instances of class I18Support are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class I18Support {
    //== CONSTANT INSTANCE ATTRIBUTES ==========================================
    private final ResourceBundle bundle = ResourceBundle.getBundle("lang/messages");

    //== PUBLIC METHODS OF INSTANCES ===========================================
    public String getString(String key, Object... params) {
        try {
            String value = bundle.getString(key);
            if (params.length > 0) {
                return MessageFormat.format(value, params);
            }

            return value;
        } catch (MissingResourceException e) {
            return "! " + key + " !";
        }
    }
}
