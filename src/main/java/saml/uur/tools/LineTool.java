package saml.uur.tools;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import saml.uur.language.Language;
import saml.uur.utils.Utils;
import saml.uur.workspace.Workspace;

/******************************************************************************
 * Instances of class saml.uur.tools.LineTool are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class LineTool extends ATool {

    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    /** action start point */
    private Point2D point;

    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    protected LineTool(Workspace workspace, ObjectProperty<Color> colorProperty) {
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
        GraphicsContext drawGContext = workspace.getDrawGContext();
        GraphicsContext previewGContext = workspace.getPreviewGContext();
        drawGContext.beginPath();
        previewGContext.beginPath();

        drawGContext.moveTo(point.getX(), point.getY());
        previewGContext.moveTo(point.getX(), point.getY());
    }

    /**
     * defines behavior when mouse is dragged
     */
    @Override
    protected void mouseDraggedAction(MouseEvent mouseEvent) {
        GraphicsContext previewGContext = workspace.getPreviewGContext();
        workspace.clearPreviewCanvas();
        previewGContext.beginPath();
        previewGContext.moveTo(point.getX(), point.getY());
        previewGContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
        previewGContext.stroke();
        previewGContext.closePath();

    }

    /**
     * defines behavior when mouse is released
     */
    @Override
    protected void mouseReleasedAction(MouseEvent mouseEvent) {
        GraphicsContext drawGContext = workspace.getDrawGContext();
        workspace.clearPreviewCanvas();
        drawGContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
        drawGContext.stroke();
        drawGContext.closePath();
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
        button.setTooltip(new Tooltip(Language.bundle.getString("draw.line")));
        if (Utils.images.lineIMG == null) {
            button.setText(Language.bundle.getString("line"));
        } else {
            ImageView icon = new ImageView(Utils.images.lineIMG);
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
        workspace.setGLineCap(StrokeLineCap.SQUARE);
        workspace.setGColor(colorProperty.get());
    }

}
