package saml.uur.tools;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import saml.uur.language.Language;
import saml.uur.utils.Utils;
import saml.uur.workspace.Workspace;

/******************************************************************************
 * Instances of class saml.uur.tools.DropperTool are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class DropperTool extends ATool {


    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    protected DropperTool(Workspace workspace, ObjectProperty<Color> colorProperty) {
        super(workspace, colorProperty);
    }

    //==========================================================================
    //== PRIVATE METHODS OF INSTANCES ==========================================

    /**
     * defines behavior when mouse is pressed
     */
    @Override
    protected void mousePressedAction(MouseEvent mouseEvent) {
        setColor((int)mouseEvent.getX(), (int)mouseEvent.getY());
    }

    /**
     * defines behavior when mouse is dragged
     */
    @Override
    protected void mouseDraggedAction(MouseEvent mouseEvent) {
        setColor((int)mouseEvent.getX(), (int)mouseEvent.getY());

    }

    /**
     * defines behavior when mouse is released
     */
    @Override
    protected void mouseReleasedAction(MouseEvent mouseEvent) {
        setColor((int)mouseEvent.getX(), (int)mouseEvent.getY());
    }


    private void setColor(int x, int y) {
        colorProperty.set(workspace.getDrawCanvas().snapshot(null, null)
                .getPixelReader().getColor(x, y));
    }


    //== PUBLIC METHODS OF INSTANCES ===========================================
    /**
     * creates toggle button representing the tool
     * @return Toggle Button
     */
    @Override
    public ToggleButton createButton() {
        ToggleButton button = new ToggleButton();
        button.setTooltip(new Tooltip(Language.bundle.getString("pick.color.from.canvas")));
        if (Utils.images.dropperIMG == null) {
            button.setText(Language.bundle.getString("dropper"));
        } else {
            ImageView icon = new ImageView(Utils.images.dropperIMG);
            icon.setPreserveRatio(true);
            icon.setFitWidth(40);
            button.setGraphic(icon);
        }
        return button;
    }

    /**
     * resets setting to default for this tool
     */
    @Override
    public void resetSettings() {
        //nothing to reset
    }

}
