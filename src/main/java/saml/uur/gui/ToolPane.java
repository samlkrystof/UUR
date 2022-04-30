package saml.uur.gui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import saml.uur.language.Language;
import saml.uur.tools.ATool;
import saml.uur.tools.EraserTool;
import saml.uur.tools.Tools;
import saml.uur.workspace.Workspace;

/******************************************************************************
 * Instances of class saml.uur.gui.ToolPane are special VBoxes for this app
 * It does everything needed for settings panel with tools
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class ToolPane extends VBox {

    //== CONSTANT INSTANCE ATTRIBUTES ==========================================

    /** list of tools */
    private final Tools tools;

    /** toggleGroup of buttons */
    private final ToggleGroup toolsGroup;

    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================

    public ToolPane(Workspace workspace, Tools tools) {
        super(8);
        this.tools = tools;
        TilePane pane = new TilePane(Orientation.HORIZONTAL, 5, 5);

        pane.setPrefColumns(4);
        toolsGroup = new ToggleGroup();
        ColorPicker colorPicker = new ColorPicker();

        colorPicker.setTooltip(new Tooltip(Language.bundle.getString("color.of.the.tool")));
        colorPicker.valueProperty().bindBidirectional(tools.selectedColorProperty());

        for (ATool tool: tools.getToolList()) {
            ToggleButton toggleBT = tool.createButton();
            toggleBT.setPrefWidth(35);
            toggleBT.setOnAction(event ->  {
                tools.setSelectedTool(tool);
                tool.resetSettings();
            });
            toolsGroup.getToggles().add(toggleBT);
            pane.getChildren().add(toggleBT);
        }

        HBox colorBox = new HBox(8, new Label(Language.bundle.getString("color")), colorPicker);
        colorBox.setPadding(new Insets(8));
        super.getChildren().addAll(pane, colorBox, tools.getSelectedTool().settingBox);

        tools.selectedToolProperty().addListener((observable, oldValue, newValue) -> {
                    super.getChildren().clear();
                    if (newValue instanceof EraserTool) {
                        super.getChildren().addAll(pane, newValue.settingBox);
                    } else {
                        super.getChildren().addAll(pane, colorBox, newValue.settingBox);
                    }
                });

        workspace.getPreviewCanvas().addEventHandler(MouseEvent.ANY, this::mouseAction);
    }

    //==========================================================================
    //== PRIVATE METHODS OF INSTANCES ==========================================
    private void mouseAction(MouseEvent mouseEvent) {
        tools.getSelectedTool().mouseActions(mouseEvent);
    }
}
