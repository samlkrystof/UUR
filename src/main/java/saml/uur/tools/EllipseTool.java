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
 * Instances of class saml.uur.tools.EllipseTool are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class EllipseTool extends ATool {

    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    /** action start point */
    private Point2D point;

    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    protected EllipseTool(Workspace workspace, ObjectProperty<Color> colorProperty) {
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
        double[] dims = Utils.calculateDimensions(point, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
        workspace.getPreviewGContext().strokeOval(dims[0], dims[1], dims[2], dims[3]);
    }

    /**
     * defines behavior when mouse is released
     */
    @Override
    protected void mouseReleasedAction(MouseEvent mouseEvent) {
        double[] dims = Utils.calculateDimensions(point, new Point2D(mouseEvent.getX(), mouseEvent.getY()));
        workspace.getDrawGContext().strokeOval(dims[0], dims[1], dims[2], dims[3]);
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
        button.setTooltip(new Tooltip(Language.bundle.getString("draw.ellipse")));
        if (Utils.images.ellipseIMG == null) {
            button.setText(Language.bundle.getString("ellipse"));
        } else {
            ImageView icon = new ImageView(Utils.images.ellipseIMG);
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
