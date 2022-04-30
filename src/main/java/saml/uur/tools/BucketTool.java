package saml.uur.tools;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import saml.uur.language.Language;
import saml.uur.utils.MyPoint2D;
import saml.uur.utils.Utils;
import saml.uur.workspace.Workspace;

import java.util.Stack;

/******************************************************************************
 * Instances of class saml.uur.tools.BucketTool are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class BucketTool extends ATool {


    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    protected BucketTool(Workspace workspace, ObjectProperty<Color> colorProperty) {
        super(workspace, colorProperty);
    }

    //==========================================================================
    //== PRIVATE METHODS OF INSTANCES ==========================================

    /**
     * defines behavior when mouse is pressed
     */
    @Override
    protected void mousePressedAction(MouseEvent mouseEvent) {
        WritableImage image = workspace.getDrawCanvas().snapshot(null, null);

        PixelReader reader = image.getPixelReader();
        PixelWriter writer = workspace.getDrawGContext().getPixelWriter();
        MyPoint2D point = new MyPoint2D((int) mouseEvent.getX(), (int) mouseEvent.getY());
        int sourceColor = reader.getArgb(point.x, point.y);

        if (colorProperty.get().equals(reader.getColor(point.x, point.y))) {
            return;
        }

        //to keep track about pixels which were checked
        boolean[][] checked = new boolean[(int)image.getHeight()][(int) image.getWidth()];
        Stack<MyPoint2D> stack = new Stack<>();
        stack.push(point);
        while (!stack.isEmpty()) {
            point = stack.pop();
            if (!checked[point.y][point.x]) {
                checked[point.y][point.x] = true;
                if (reader.getArgb(point.x, point.y) == sourceColor) {
                    writer.setColor(point.x, point.y, colorProperty.get());
                    for (int i = -1; i < 2; i += 2) {
                        if (Utils.checkBorders(point.x + i, point.y, image) && !checked[point.y][point.x + i]) {
                            stack.push(new MyPoint2D(point.x + i, point.y));
                        }
                        if (Utils.checkBorders(point.x, point.y + i, image) && !checked[point.y + i][point.x]) {
                            stack.push(new MyPoint2D(point.x, point.y + i));
                        }
                    }
                }
            }
        }
    }

    /**
     * defines behavior when mouse is dragged
     */
    @Override
    protected void mouseDraggedAction(MouseEvent mouseEvent) {}

    /**
     * defines behavior when mouse is released
     */
    @Override
    protected void mouseReleasedAction(MouseEvent mouseEvent) {
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
        button.setTooltip(new Tooltip(Language.bundle.getString("fill.with.color")));
        if (Utils.images.bucketIMG == null) {
            button.setText(Language.bundle.getString("bucket"));
        } else {
            ImageView icon = new ImageView(Utils.images.bucketIMG);
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
