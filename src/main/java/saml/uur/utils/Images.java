package saml.uur.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import saml.uur.language.Language;

/******************************************************************************
 * Instances of class saml.uur.utils.Images are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class Images {

    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    public Image arrowIMG;
    public Image brushIMG;
    public Image bucketIMG;
    public Image czIMG;
    public Image dropperIMG;
    public Image ellipseIMG;
    public Image eraserIMG;
    public Image lidlIMG;
    public Image lineIMG;
    public Image newIMG;
    public Image openFolderIMG;
    public Image rectangleIMG;
    public Image redoIMG;
    public Image saveIMG;
    public Image ukIMG;
    public Image undoIMG;

    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    public Images() {
        try {
            arrowIMG = new Image(getClass().getResourceAsStream("/images/arrow.png"));
            brushIMG = new Image(getClass().getResourceAsStream("/images/brush.png"));
            bucketIMG = new Image(getClass().getResourceAsStream("/images/bucket.png"));
            czIMG = new Image(getClass().getResourceAsStream("/images/cz.png"));
            dropperIMG = new Image(getClass().getResourceAsStream("/images/dropper.png"));
            ellipseIMG = new Image(getClass().getResourceAsStream("/images/ellipse.png"));
            eraserIMG = new Image(getClass().getResourceAsStream("/images/eraser.png"));
            lidlIMG = new Image(getClass().getResourceAsStream("/images/lidl.png"));
            lineIMG = new Image(getClass().getResourceAsStream("/images/line.png"));
            newIMG = new Image(getClass().getResourceAsStream("/images/new.png"));
            openFolderIMG = new Image(getClass().getResourceAsStream("/images/open.png"));
            rectangleIMG = new Image(getClass().getResourceAsStream("/images/rectangle.png"));
            redoIMG = new Image(getClass().getResourceAsStream("/images/redo.png"));
            saveIMG = new Image(getClass().getResourceAsStream("/images/save.png"));
            ukIMG = new Image(getClass().getResourceAsStream("/images/uk.png"));
            undoIMG = new Image(getClass().getResourceAsStream("/images/undo.png"));
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Language.bundle.getString("error.while.loading.icons"));
            alert.setHeaderText(Language.bundle.getString("some.of.the.icon.were.not.loaded"));
            alert.setContentText(Language.bundle.getString("the.text.is.used.instead.of.pictures"));
            alert.show();
        }
    }
    //==========================================================================
}
