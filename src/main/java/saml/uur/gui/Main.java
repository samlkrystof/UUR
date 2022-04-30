package saml.uur.gui;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import saml.uur.language.Language;
import saml.uur.tools.Tools;
import saml.uur.utils.Utils;
import saml.uur.workspace.Workspace;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends Application {
    /** in case we nedd to make new scene */
    private Stage primaryStage;
    /** our workspace */
    private Workspace workspace;

    /** reference to tools */
    private Tools tools;

    /**
     * prepares workspace, language and tools for the app
     */
    @Override
    public void init() {
        workspace = new Workspace(600, 400, Color.WHITE);
        Language.initialize();
        tools = new Tools(workspace);
    }

    /** creates primary stage */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        if (Utils.images.lidlIMG != null) {
            primaryStage.getIcons().add(Utils.images.lidlIMG);
        }
        primaryStage.setTitle("Semestrální práce UUR - Kryštof Šaml");
        primaryStage.setScene(createScene());

        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }

    /**
     *  creates and returns Scene
     * @return new Scene
     */
    private Scene createScene() {
        Scene scene = new Scene(getRootPane(), 1000, 600);
        workspace.save();
        return scene;
    }

    /**
     * creates rootPane (borderpane)
     * @return Parent BorderPane
     */
    private Parent getRootPane() {
        BorderPane rootPane = new BorderPane();

        rootPane.setTop(getMenu());
        rootPane.setLeft(getSettingsPane());
        rootPane.setCenter(getDrawingPane());

        return rootPane;
    }

    /**
     *  creates drawing pane for the app
     * @return special stackpane including drawing canvas
     */
    private Node getDrawingPane() {

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane(stackPane);
        scrollPane.setCenterShape(true);

        stackPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, new Insets(0))));

        stackPane.getChildren().add(workspace.getDrawCanvas());
        stackPane.getChildren().add(workspace.getPreviewCanvas());

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }


    /**
     * creates toolpane
     * @return Vbox toolPane
     */
    private Node getSettingsPane() {
        ToolPane toolPane = new ToolPane(workspace, tools);
        toolPane.setPadding(new Insets(8));

        return toolPane;

    }

    /**
     * creates menu of the app
     * @return menubar
     */
    private Node getMenu() {

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu(Language.bundle.getString("file"));
        MenuItem saveItem;
        if (Utils.images.saveIMG == null) {
            saveItem = new MenuItem(Language.bundle.getString("save"));
        } else {
            saveItem = new MenuItem(Language.bundle.getString("save"), Utils.getImageView(Utils.images.saveIMG));
        }
        saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        saveItem.setOnAction(this::saveAction);

        MenuItem newItem;
        if (Utils.images.saveIMG == null) {
            newItem = new MenuItem(Language.bundle.getString("new"));
        } else {
            newItem = new MenuItem(Language.bundle.getString("new"), Utils.getImageView(Utils.images.newIMG));
        }
        newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        newItem.setOnAction(this::newWorkspaceAction);

        MenuItem openItem;
        if (Utils.images.openFolderIMG == null) {
            openItem = new MenuItem(Language.bundle.getString("open"));
        } else {
            openItem = new MenuItem(Language.bundle.getString("open"), Utils.getImageView(Utils.images.openFolderIMG));
        }
        openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        openItem.setOnAction(this::openAction);

        Menu edit = new Menu(Language.bundle.getString("edit"));

        MenuItem undoItem;
        if (Utils.images.undoIMG == null) {
            undoItem = new MenuItem(Language.bundle.getString("undo"));
        } else {
            undoItem = new MenuItem(Language.bundle.getString("undo"), Utils.getImageView(Utils.images.undoIMG));
        }
        undoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        undoItem.setOnAction(event -> workspace.undo());

        MenuItem redoItem;
        if (Utils.images.redoIMG == null) {
            redoItem = new MenuItem(Language.bundle.getString("redo"));
        } else {
            redoItem = new MenuItem(Language.bundle.getString("redo"), Utils.getImageView(Utils.images.redoIMG));
        }
        redoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Z"));
        redoItem.setOnAction(event -> workspace.redo());

        MenuItem languageItem = new MenuItem(Language.bundle.getString("language.switch"));
        languageItem.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));
        languageItem.setOnAction(this::changeLanguageAction);
        if (Language.getSelectedIcon() != null) {
            ImageView flagView = new ImageView();
            flagView.imageProperty().bind(Language.selectedIconProperty());
            flagView.setFitHeight(15);
            flagView.setPreserveRatio(true);
            languageItem.setGraphic(flagView);
        }

        menu.getItems().addAll(saveItem, openItem, newItem);
        edit.getItems().addAll(undoItem, redoItem, languageItem);

        menuBar.getMenus().addAll(menu, edit);

        return menuBar;
    }

    /**
     * changes the language and creates new scene
     */
    private void changeLanguageAction(ActionEvent actionEvent) {
        Language.changeLanguage();

        primaryStage.hide();
        primaryStage.setScene(createScene());
        primaryStage.show();
    }

    /**
     * shows newWorkspaceDialog, if conditions are met, creates new workspace
     */
    private void newWorkspaceAction(ActionEvent actionEvent) {
        NewWorkspaceDialog dialog = new NewWorkspaceDialog();
        dialog.showAndWait();
        if (dialog.isOK) {
            makeNewWorkspace(dialog.getNewWidth(), dialog.getNewHeight(), dialog.getNewColor());
        }

    }

    /**
     * open file action
     */
    private void openAction(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(Language.bundle.getString("open.image"));

        File pictures = new File("C:\\Users\\Public\\Pictures");
        if (pictures.exists()) {
            chooser.setInitialDirectory(pictures);
        }
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(Language.bundle.getString("image.files"),
                "*.jpg","*.png"));

        File selected = chooser.showOpenDialog(primaryStage);
        if (selected != null) {
            try {
                BufferedImage image = ImageIO.read(selected);
                Image fxImage = SwingFXUtils.toFXImage(image, null);
                if (fxImage.getWidth() > 4500 || fxImage.getHeight() > 4500) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(Language.bundle.getString("opening.image.error"));
                    alert.setHeaderText(Language.bundle.getString("can.t.open.the.image.because.of.too.big.dimensions"));
                    alert.setContentText(Language.bundle.getString("width.and.height.must.be.both.smaller.than.4500.px"));
                    alert.show();

                } else {
                    makeNewWorkspace(image.getWidth(), image.getHeight(), Color.WHITE);
                    workspace.getDrawCanvas().getGraphicsContext2D().drawImage(fxImage, 0, 0);
                }

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Language.bundle.getString("opening.image.error"));
                alert.setHeaderText(Language.bundle.getString("an.error.occurred.while.opening.the.image"));
                alert.setContentText(Language.bundle.getString("the.image.can.t.be.opened.due.to.error"));
                alert.show();
            }
        }

    }

    private void saveAction(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        File pictures = new File("C:\\Users\\Public\\Pictures");
        if (pictures.exists()) {
            chooser.setInitialDirectory(pictures);
        }
        chooser.setTitle(Language.bundle.getString("save.image"));
        chooser.setInitialFileName(Language.bundle.getString("drawing"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG","*.png"));
        File file = chooser.showSaveDialog(primaryStage);

        if(file != null){
            WritableImage image = new WritableImage(workspace.getWidth(), workspace.getHeight());
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(workspace.getDrawCanvas().snapshot(null,image),null),
                        "png",file);
            } catch (IOException | NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Language.bundle.getString("image.saving.error"));
                alert.setHeaderText(Language.bundle.getString("an.error.has.occurred.while.saving.image"));
                alert.setContentText(Language.bundle.getString("image.was.not.saved"));
                alert.show();
            }
        }
    }

    /**
     * creates new workspace with given parameters
     * @param width width of the workspace
     * @param height height of the workspace
     * @param background background color
     */
    private void makeNewWorkspace(int width, int height, Color background) {
        primaryStage.hide();
        workspace = new Workspace(width, height, background);
        tools = new Tools(workspace);
        primaryStage.setScene(createScene());
        primaryStage.show();
    }
}

