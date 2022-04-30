package saml.uur.tools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import saml.uur.workspace.Workspace;

import java.util.ArrayList;

/******************************************************************************
 * Instances of class saml.uur.tools.Tools are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class Tools {

    //== CONSTANT INSTANCE ATTRIBUTES ==========================================

    private final ObjectProperty<ATool> selectedTool = new SimpleObjectProperty<>();
    private final ArrayList<ATool> toolList = new ArrayList<>(8);
    private final ObjectProperty<Color> selectedColor = new SimpleObjectProperty<>(Color.BLACK);

    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    public Tools(Workspace workspace) {
        toolList.add(new BrushTool(workspace, selectedColor));
        toolList.add(new BucketTool(workspace, selectedColor));
        toolList.add(new EllipseTool(workspace, selectedColor));
        toolList.add(new LineTool(workspace, selectedColor));
        toolList.add(new RectangleTool(workspace, selectedColor));
        toolList.add(new EraserTool(workspace, selectedColor));
        toolList.add(new ArrowTool(workspace, selectedColor));
        toolList.add(new DropperTool(workspace, selectedColor));
        selectedTool.set(toolList.get(0));
        selectedTool.get().resetSettings();
    }

    //==========================================================================
    //== ACCESS METHODS OF INSTANCES ===========================================

    public ATool getSelectedTool() {
        return selectedTool.get();
    }

    public ObjectProperty<ATool> selectedToolProperty() {
        return selectedTool;
    }

    public ArrayList<ATool> getToolList() {
        return toolList;
    }

    public ObjectProperty<Color> selectedColorProperty() {
        return selectedColor;
    }

    public void setSelectedTool(ATool selectedTool) {
        this.selectedTool.set(selectedTool);
    }

}
