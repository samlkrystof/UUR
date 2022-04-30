package saml.uur.tools;

import javafx.beans.property.ObjectProperty;
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
 * Instances of class saml.uur.tools.EraserTool are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class EraserTool extends ATool {

    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    /** in case of transparent background */
    private boolean isBackgroundVisible = true;


    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================
    protected EraserTool(Workspace workspace, ObjectProperty<Color> colorProperty) {
        super(workspace, colorProperty);
        if (workspace.getBackgroundColor().getOpacity() < 0.00001) {
            isBackgroundVisible = false;
        }
        setSettingsBox();
        sizeProperty.addListener((observable, oldValue, newValue) -> workspace.setGLineWidth(newValue.intValue()));
    }

    //==========================================================================
    //== PRIVATE METHODS OF INSTANCES ==========================================
    /**
     * defines behavior when mouse is pressed
     */
    @Override
    protected void mousePressedAction(MouseEvent mouseEvent) {
        workspace.getDrawGContext().beginPath();
        if (isBackgroundVisible) {
            workspace.setGColor(workspace.getBackgroundColor());
            workspace.getDrawGContext().moveTo(mouseEvent.getX(), mouseEvent.getY());
        } else {
            double width = sizeProperty.doubleValue();
            double offset = width / 2;
            workspace.getDrawGContext().clearRect(mouseEvent.getX() - offset, mouseEvent.getY() - offset,
                    width, width);
        }
    }

    /**
     * defines behavior when mouse is dragged
     */
    @Override
    protected void mouseDraggedAction(MouseEvent mouseEvent) {
        GraphicsContext drawGContext = workspace.getDrawGContext();
        if (isBackgroundVisible) {

            drawGContext.setStroke(workspace.getBackgroundColor());
            drawGContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
            drawGContext.stroke();
            drawGContext.closePath();
            drawGContext.beginPath();
            drawGContext.moveTo(mouseEvent.getX(), mouseEvent.getY());
        } else {
            double width = sizeProperty.doubleValue();
            double offset = width / 2;
            drawGContext.clearRect(mouseEvent.getX() - offset, mouseEvent.getY() - offset,
                    width, width);
        }
    }

    /**
     * defines behavior when mouse is released
     */
    @Override
    protected void mouseReleasedAction(MouseEvent mouseEvent) {
        GraphicsContext drawGContext = workspace.getDrawGContext();
        if (isBackgroundVisible) {
            drawGContext.setStroke(workspace.getBackgroundColor());
            drawGContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
            drawGContext.stroke();
            drawGContext.closePath();
        } else {
            double width = sizeProperty.doubleValue();
            double offset = width / 2;
            drawGContext.clearRect(mouseEvent.getX() - offset, mouseEvent.getY() - offset,
                    width, width);
        }
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
        button.setTooltip(new Tooltip(Language.bundle.getString("eraser.tool")));
        if (Utils.images.eraserIMG == null) {
            button.setText(Language.bundle.getString("eraser"));
        } else {
            ImageView icon = new ImageView(Utils.images.eraserIMG);
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
        workspace.setGLineCap(StrokeLineCap.ROUND);
        workspace.setGColor(workspace.getBackgroundColor());
        workspace.setGLineWidth(sizeProperty.get());
    }

}
