package saml.uur.tools;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import saml.uur.language.Language;
import saml.uur.utils.Utils;
import saml.uur.workspace.Workspace;

/******************************************************************************
 * Instances of class saml.uur.tools.RectangleTool are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class RectangleTool extends ATool {

    //== VARIABLE INSTANCE ATTRIBUTES ==========================================

    /** action start point */
    private Point2D point;

    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    protected RectangleTool(Workspace workspace, ObjectProperty<Color> colorProperty) {
        super(workspace, colorProperty);
        setSettingsBox();
        this.colorProperty.addListener((observable, oldValue, newValue) -> workspace.setGColor(newValue));
        sizeProperty.addListener((observable, oldValue, newValue) -> workspace.setGLineWidth(newValue.intValue()));

    }
    //==========================================================================
    //== PRIVATE METHODS OF INSTANCES ==========================================

    /**
     * defines behavior when mouse is pressed
     */
    @Override
    protected void mousePressedAction(MouseEvent mouseEvent) {
        point = new Point2D(mouseEvent.getX(), mouseEvent.getY());
    }

    /**
     * defines behavior when mouse is dragged
     */
    @Override
    protected void mouseDraggedAction(MouseEvent mouseEvent) {
        workspace.clearPreviewCanvas();
        Point2D endPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
        double[] dims = Utils.calculateDimensions(point, endPoint);
        workspace.getPreviewGContext().strokeRect(dims[0], dims[1], dims[2], dims[3]);
    }

    /**
     * defines behavior when mouse is released
     */
    @Override
    protected void mouseReleasedAction(MouseEvent mouseEvent) {
        Point2D endPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
        double[] dims = Utils.calculateDimensions(point, endPoint);
        workspace.getDrawGContext().strokeRect(dims[0], dims[1], dims[2], dims[3]);

        workspace.clearPreviewCanvas();
        workspace.save();
    }

    //== PUBLIC METHODS OF INSTANCES ===========================================

    /**
     * creates toggle button representing the tool
     * @return Toggle Button
     */
    @Override
    public ToggleButton createButton() {
        ToggleButton button = new ToggleButton();
        button.setTooltip(new Tooltip(Language.bundle.getString("draw.rectangle")));
        if (Utils.images.rectangleIMG == null) {
            button.setText(Language.bundle.getString("rectangle"));
        } else {
            ImageView icon = new ImageView(Utils.images.rectangleIMG);

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
        workspace.setGLineWidth(sizeProperty.intValue());
        workspace.setGColor(colorProperty.get());
    }

}
