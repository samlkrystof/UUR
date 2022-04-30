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
 * Instances of class saml.uur.tools.ArrowTool are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class ArrowTool extends ATool {

    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    /** action start point */
    private Point2D point;


    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    protected ArrowTool(Workspace workspace, ObjectProperty<Color> colorProperty) {
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
        drawArrow(point, new Point2D(mouseEvent.getX(), mouseEvent.getY()), workspace.getPreviewGContext());
    }

    /**
     * defines behavior when mouse is released
     */
    @Override
    protected void mouseReleasedAction(MouseEvent mouseEvent) {
        workspace.clearPreviewCanvas();
        drawArrow(point, new Point2D(mouseEvent.getX(), mouseEvent.getY()), workspace.getDrawGContext());
        workspace.save();
    }

    private void drawArrow(final Point2D first, final Point2D second, GraphicsContext context) {
        context.beginPath();
        context.moveTo(first.getX(), first.getY());
        context.strokeLine(first.getX(), first.getY(), second.getX(), second.getY());

        //unit vector
        double uX = first.getX() - second.getX();
        double uY = first.getY() - second.getY();
        final double uLength = 1 / Math.sqrt(uX * uX + uY * uY);
        uX *= uLength;
        uY *= uLength;

        double vX = uY;
        double vY = -uX;

        final double lengthOfLine = Utils.computeLengthOfLine(first, second) / 3;
        final double cX = first.getX() - uX * lengthOfLine;
        final double cY = first.getY() - uY * lengthOfLine;

        vX *= lengthOfLine / 2;
        vY *= lengthOfLine / 2;
        //tip of the arrow
        double[] xCoords = new double[]{cX + vX, first.getX(), cX - vX};
        double[] yCoords = new double[]{cY + vY, first.getY(), cY - vY};

        workspace.setGLineCap(StrokeLineCap.SQUARE);
        context.strokePolyline(xCoords, yCoords, 3);
        workspace.setGLineCap(StrokeLineCap.ROUND);

        context.closePath();
    }

    //== PUBLIC METHODS OF INSTANCES ===========================================

    /**
     * creates toggle button representing the tool
     * @return Toggle Button
     */
    @Override
    public ToggleButton createButton() {
        ToggleButton button = new ToggleButton();
        button.setTooltip(new Tooltip(Language.bundle.getString("draw.arrow")));
        if (Utils.images.arrowIMG == null) {
            button.setText(Language.bundle.getString("arrow"));
        } else {
            ImageView icon = new ImageView(Utils.images.arrowIMG);
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
        workspace.setGLineCap(StrokeLineCap.ROUND);
        workspace.setGColor(colorProperty.get());
    }

}
