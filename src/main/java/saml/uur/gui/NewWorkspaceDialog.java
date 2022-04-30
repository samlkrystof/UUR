package saml.uur.gui;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;
import saml.uur.language.Language;
import saml.uur.utils.Utils;

/******************************************************************************
 * Instances of class saml.uur.gui.NewWorkspaceDialog are ...
 *
 *
 * @author Krystof Saml
 * @version 1.00.0000
 */

public class NewWorkspaceDialog extends Stage {

    //== CONSTANT INSTANCE ATTRIBUTES ==========================================

    private final ColorPicker picker = new ColorPicker(Color.WHITE);
    private final TextField widthTF = new TextField();
    private final TextField heightTF = new TextField();

    //== VARIABLE INSTANCE ATTRIBUTES ==========================================

    public boolean isOK;


    //==========================================================================
    //== CONSTRUCTORS AND FACTORY METHODS ======================================
    public NewWorkspaceDialog() {
        super();
        if (Utils.images.lidlIMG != null) {
            getIcons().add(Utils.images.lidlIMG);
        }
        setScene(new Scene(getRootPane()));
        setTitle(Language.bundle.getString("creating.new.workspace"));
        setWidth(200);
        initStyle(StageStyle.UTILITY);
    }
    //==========================================================================

    //== ACCESS METHODS OF INSTANCES ===========================================

    public int getNewWidth() {
        return Integer.parseInt(widthTF.getText());
    }

    public int getNewHeight() {
        return Integer.parseInt(heightTF.getText());
    }

    public Color getNewColor() {
        return picker.getValue();
    }

    //== PRIVATE METHODS OF INSTANCES ==========================================

    private Parent getRootPane() {
        VBox rootPane = new VBox(8);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setPadding(new Insets(10));

        Label pxLB = new Label("px");
        Label pxLB2 = new Label("px");

        Label widthLB = new Label(Language.bundle.getString("width"));
        HBox widthBox = new HBox(5, widthLB, widthTF, pxLB);
        Label heightLB = new Label(Language.bundle.getString("height"));
        HBox heightBox = new HBox(5, heightLB, heightTF, pxLB2);

        Label colorLB = new Label(Language.bundle.getString("color"));
        HBox colorBox = new HBox(5, colorLB, picker);

        TextFormatter<Integer> heightFormatter = new TextFormatter<>(new IntegerStringConverter(),400);
        TextFormatter<Integer> widthFormatter = new TextFormatter<>(new IntegerStringConverter(),600);

        widthTF.setTextFormatter(widthFormatter);
        heightTF.setTextFormatter(heightFormatter);
        widthTF.setPrefWidth(50);
        heightTF.setPrefWidth(50);
        HBox buttons = new HBox(10);
        buttons.setPadding(new Insets(10));

        Button confirmBT = new Button("OK");
        confirmBT.setOnAction(this::validateAction);

        Button cancelBT = new Button(Language.bundle.getString("cancel"));
        cancelBT.setOnAction(event -> this.close());

        buttons.getChildren().addAll(cancelBT, confirmBT);

        rootPane.getChildren().addAll(widthBox, heightBox, colorBox, buttons);
        return rootPane;
    }

    /**
     * checks the width, height and color, if they are valid, dialog closes
     * else alert is shown
     */
    private void validateAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Language.bundle.getString("input.parameters.error"));
        alert.setHeaderText(Language.bundle.getString("bad.parameters"));

        int width;
        int height;
        try {
            width = Integer.parseInt(widthTF.getText());
            height = Integer.parseInt(heightTF.getText());
        } catch (NumberFormatException e) {
            alert.setContentText(Language.bundle.getString("you.have.to.input.some.values"));
            alert.show();
            return;
        }
        if (picker.getValue() == null) {
            alert.setContentText(Language.bundle.getString("you.have.to.choose.color"));
        } else if (width < 1 || width > 2000) {
            alert.setContentText(Language.bundle.getString("width.must.be.between.1.2000"));
        } else if (height < 1 || height > 2000) {
            alert.setContentText(Language.bundle.getString("height.must.be.between.1.2000"));
        } else {
            isOK = true;
            this.close();
            return;
        }
        alert.show();
    }

}
