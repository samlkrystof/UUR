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
 * Instances of class saml.uur.tools.BrushTool are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class BrushTool extends ATool {



    protected BrushTool(Workspace workspace, ObjectProperty<Color> colorProperty) {
        super(workspace, colorProperty);
        setSettingsBox();
        sizeProperty.addListener((observable, oldValue, newValue) -> workspace.setGLineWidth(newValue.intValue()));
        this.colorProperty.addListener((observable, oldValue, newValue) -> workspace.setGColor(newValue));
        workspace.setGLineCap(StrokeLineCap.ROUND);
    }


    /**
     * defines behavior when mouse is pressed
     */
    @Override
    protected void mousePressedAction(MouseEvent mouseEvent) {
        GraphicsContext drawGContext = workspace.getDrawGContext();
        drawGContext.beginPath();
        drawGContext.moveTo(mouseEvent.getX(), mouseEvent.getY());
        drawGContext.stroke();
    }

    /**
     * defines behavior when mouse is dragged
     */
    @Override
    protected void mouseDraggedAction(MouseEvent mouseEvent) {
        GraphicsContext drawGContext = workspace.getDrawGContext();
        drawGContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
        drawGContext.stroke();
        drawGContext.closePath();
        drawGContext.beginPath();
        drawGContext.moveTo(mouseEvent.getX(), mouseEvent.getY());
    }

    /**
     * defines behavior when mouse is released
     */
    @Override
    protected void mouseReleasedAction(MouseEvent mouseEvent) {
        GraphicsContext drawGContext = workspace.getDrawGContext();
        drawGContext.lineTo(mouseEvent.getX(), mouseEvent.getY());
        drawGContext.stroke();
        drawGContext.closePath();
        workspace.save();
    }

    /**
     * creates toggle button representing the tool
     * @return Toggle Button
     */
    @Override
    public ToggleButton createButton() {
        ToggleButton button = new ToggleButton();
        button.setTooltip(new Tooltip(Language.bundle.getString("draw.with.brush")));
        if (Utils.images.brushIMG == null) {
           button.setText(Language.bundle.getString("brush"));
        } else {
            ImageView icon = new ImageView(Utils.images.brushIMG);

            icon.setPreserveRatio(true);
            icon.setFitWidth(40);
            button.setGraphic(icon);
        }
        return button;
    }

    @Override
    public void resetSettings() {
        workspace.setGLineWidth(sizeProperty.intValue());
        workspace.setGLineCap(StrokeLineCap.ROUND);
        workspace.setGColor(colorProperty.get());
    }

    //== CONSTANT CLASS ATTRIBUTES =============================================
    //== VARIABLE CLASS ATTRIBUTES =============================================
    //== STATIC INITIALIZER BLOCK ==============================================
    //== CONSTANT INSTANCE ATTRIBUTES ==========================================
    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================
    //==========================================================================
    //== PUBLIC CLASS METHODS ==================================================
    //== PRIVATE CLASS METHODS =================================================
    //== ACCESS METHODS OF INSTANCES ===========================================
    //== PUBLIC METHODS OF INSTANCES ===========================================
    //== PRIVATE METHODS OF INSTANCES ==========================================
}
