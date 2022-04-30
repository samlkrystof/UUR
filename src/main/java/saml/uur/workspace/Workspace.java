package saml.uur.workspace;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

/******************************************************************************
 * Instances of class saml.saml.uur.workspace.Workspace are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class Workspace {

    //== CONSTANT INSTANCE ATTRIBUTES ==========================================
    private final ObjectProperty<Canvas> drawCanvas = new SimpleObjectProperty<>();
    private final ObjectProperty<Canvas> previewCanvas = new SimpleObjectProperty<>();

    private final Color backgroundColor;

    private final ImageStack stack;

    //== VARIABLE INSTANCE ATTRIBUTES ==========================================
    private GraphicsContext drawGContext;
    private GraphicsContext previewGContext;

    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    public Workspace() {
        this(1000,1000, Color.WHITE);
    }

    public Workspace(int width, int height, Color color) {
        setBothCanvas(width, height);
        this.backgroundColor = color;
        stack = new ImageStack();
        drawGContext.setFill(color);
        drawGContext.fillRect(0,0, width, height);
    }

    //==========================================================================
    //== PUBLIC METHODS OF INSTANCES ===========================================

    public void undo() {
        Canvas canvas = drawCanvas.get();
        stack.increment();
        canvas.getGraphicsContext2D().drawImage(stack.getImage(), 0, 0);
    }

    public void redo() {
        Canvas canvas = drawCanvas.get();
        stack.decrement();
        canvas.getGraphicsContext2D().drawImage(stack.getImage(), 0, 0);
    }

    public void clearPreviewCanvas() {
        previewCanvas.get().getGraphicsContext2D().clearRect(0,0,getWidth(), getHeight());
    }

    private void setBothCanvas(int width, int height) {
        Canvas drawCanvas = new Canvas(width, height);
        Canvas previewCanvas = new Canvas(width, height);
        drawGContext = drawCanvas.getGraphicsContext2D();
        previewGContext = previewCanvas.getGraphicsContext2D();
        this.drawCanvas.set(drawCanvas);
        this.previewCanvas.set(previewCanvas);
    }

    public void save() {
        stack.addImageToStack(drawCanvas.getValue().snapshot(null, null));
    }

    public void setGLineWidth(int lineWidth) {
        drawGContext.setLineWidth(lineWidth);
        previewGContext.setLineWidth(lineWidth);
    }

    public void setGColor(Color color) {
        drawGContext.setStroke(color);
        previewGContext.setStroke(color);
    }

    public void setGLineCap(StrokeLineCap lineCap) {
        drawGContext.setLineCap(lineCap);
        previewGContext.setLineCap(lineCap);
    }

    //== ACCESS METHODS OF INSTANCES ===========================================

    public GraphicsContext getDrawGContext() {
        return drawGContext;
    }

    public GraphicsContext getPreviewGContext() {
        return previewGContext;
    }

    public Canvas getDrawCanvas() {
        return drawCanvas.get();
    }


    public Canvas getPreviewCanvas() {
        return previewCanvas.get();
    }


    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public int getWidth() {
        return (int) drawCanvas.get().getWidth();
    }

    public int getHeight() {
        return (int) drawCanvas.get().getHeight();
    }


}
