package saml.uur.tools;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import saml.uur.utils.CheckedSimpleIntegerProperty;
import saml.uur.language.Language;
import saml.uur.utils.Utils;
import saml.uur.workspace.Workspace;

/******************************************************************************
 * Instances of class saml.saml.uur.tools.ATool are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public abstract class ATool {

    //== CONSTANT INSTANCE ATTRIBUTES ==========================================
    /** settings of the tool */
    public final VBox settingBox = new VBox(8);

    protected final Workspace workspace;
    protected final ObjectProperty<Color> colorProperty;
    protected final IntegerProperty sizeProperty = new CheckedSimpleIntegerProperty(5);

    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    protected TextField sizeTF;

    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    protected ATool(Workspace workspace, ObjectProperty<Color> colorProperty) {
        this.workspace = workspace;
        this.colorProperty = colorProperty;
        settingBox.setPadding(new Insets(5));
    }

    //==========================================================================
    //== PUBLIC METHODS OF INSTANCES ===========================================

    /**
     * this is called when mouseEvent occurs
     */
    public void mouseActions(MouseEvent mouseEvent) {
        final EventType<? extends MouseEvent> eventType = mouseEvent.getEventType();
        if (eventType.equals(MouseEvent.MOUSE_PRESSED)) {
            mousePressedAction(mouseEvent);
        } else if (eventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            mouseDraggedAction(mouseEvent);
        } else if (eventType.equals(MouseEvent.MOUSE_RELEASED)) {
            mouseReleasedAction(mouseEvent);
        }
    }

    /**
     * creates toggle button representing the tool
     * @return Toggle Button
     */
    public abstract ToggleButton createButton();

    /**
     * resets setting to default for this tool
     */
    public abstract void resetSettings();

    //== PRIVATE METHODS OF INSTANCES ==========================================

    /**
     * defines behavior when mouse is pressed
     */
    protected abstract void mousePressedAction(MouseEvent mouseEvent);

    /**
     * defines behavior when mouse is dragged
     */
    protected abstract void mouseDraggedAction(MouseEvent mouseEvent);

    /**
     * defines behavior when mouse is released
     */
    protected abstract void mouseReleasedAction(MouseEvent mouseEvent);


    /**
     * creates color slider
     * @return slider
     */
    protected Slider getSlider() {
        Slider colorSL = new Slider(Utils.MIN_SIZE,Utils.MAX_SIZE, 5);
        colorSL.setPrefWidth(180);
        colorSL.setBlockIncrement(5);
        colorSL.setMinorTickCount(5);
        colorSL.setMajorTickUnit(50);
        colorSL.setShowTickLabels(true);
        colorSL.setShowTickMarks(true);
        return colorSL;
    }

    /**
     * creates VBox with basic setting of the tool
     */
    protected void setSettingsBox() {
        Label sizeLB = new Label(Language.bundle.getString("size"));
        Slider sizeSL = getSlider();
        sizeTF = new TextField();
        sizeTF.focusedProperty().addListener(e -> validateAction());
        sizeTF.setOnKeyPressed(this::commitAction);
        sizeTF.setPrefWidth(40);
        Utils.bindControl(sizeProperty, sizeTF, sizeSL);
        HBox sizeBox = new HBox(8, sizeSL, sizeTF);
        settingBox.getChildren().addAll(sizeLB, sizeBox);
    }


    private void commitAction(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            validateAction();
        }
    }

    /**
     * takes care of invalid inputs
     */
    private void validateAction() {
        if (sizeTF.getText() == null || sizeTF.getText().length() == 0) {
            sizeTF.setText(sizeProperty.get() + "");
        }
        try {
            int number = Integer.parseInt(sizeTF.getText());
            if (number < Utils.MIN_SIZE || number > Utils.MAX_SIZE) {
                sizeTF.setText(sizeProperty.get() + "");
            }
        } catch (NumberFormatException e) {
            //do nothing, checked property will do it
        }
    }

}
