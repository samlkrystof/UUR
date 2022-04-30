package saml.uur.language;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import saml.uur.utils.Utils;

import java.util.Locale;

/******************************************************************************
 * Instances of class Language are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class Language {

    //== CONSTANT CLASS ATTRIBUTES =============================================
    private static final String CZ_LANG = "cz";
    private static final String EN_LANG = "en";
    private static final Locale CZECH = new Locale("cs","cz");

    //== VARIABLE CLASS ATTRIBUTES =============================================
    public static I18Support bundle;
    private static Image czIcon;
    private static Image enIcon;

    private static String selectedLanguage = EN_LANG;
    private static ObjectProperty<Image> selectedIcon;

    //== PUBLIC CLASS METHODS ==================================================

    public static void initialize() {
        selectedIcon = new SimpleObjectProperty<>();
        Locale.setDefault(Locale.ENGLISH);
        czIcon = Utils.images.czIMG;
        enIcon = Utils.images.ukIMG;
        selectedIcon.set(czIcon);
        bundle = new I18Support();
    }

    public static void changeLanguage() {
        if (selectedLanguage.equals(EN_LANG)) {
            selectedLanguage = CZ_LANG;
            Locale.setDefault(CZECH);
            selectedIcon.set(enIcon);
        } else {
            selectedLanguage = EN_LANG;
            Locale.setDefault(Locale.ENGLISH);
            selectedIcon.set(czIcon);
        }

        bundle = new I18Support();
    }

    public static ObjectProperty<Image> selectedIconProperty() {
        return selectedIcon;
    }

    public static Image getSelectedIcon() {
        return selectedIcon.get();
    }

}
