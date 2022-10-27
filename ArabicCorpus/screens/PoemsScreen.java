package screens;

import controllers.PoemsProvider;
import db_adapters.Couplet;
import db_adapters.Poem;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.util.Optional;

import main.Main.MainScreenModifier;
import screens.ViewPoem;


public class PoemsScreen {
    protected Pane root;
    protected PoemsProvider poemsProvider = new PoemsProvider();
    protected HBox poemMenu;
    protected TableView<Poem> table;

    protected final Dialog insertPoemDialog = new Dialog();
    protected final Dialog insertCoupletDialog = new Dialog();
    protected final Dialog updatePoemDialog = new Dialog();
    protected final ViewPoem poemDetails = new ViewPoem();


    Button addPoem;
    Button addCouplet;
    Button viewPoemDetails;
    Button delPoem;
    Button updatePoem;

    // Main Dashboard Modifier
    protected MainScreenModifier mainScreenModifier;

    public PoemsScreen(MainScreenModifier main) {
        mainScreenModifier = main;
        root = new FlowPane();
        init();
    }

    public void updatePoemData(Poem poem) {
        poemsProvider.updatePoem(poem);
        refresh();
    }

    protected void insertPoemDialogInit() {
        insertPoemDialog.setTitle("Insert New Poem");
        insertPoemDialog.setResizable(true);

        Label label1 = new Label("Name: ");
        Label label2 = new Label("Book: ");
        Label label3 = new Label("Poet: ");
        TextField text1 = new TextField();
        TextField text2 = new TextField();
        TextField text3 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        grid.add(label3, 1, 3);
        grid.add(text3, 2, 3);
        insertPoemDialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Insert Poem");
        insertPoemDialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        insertPoemDialog.setResultConverter(new Callback<ButtonType, Poem>() {
            @Override
            public Poem call(ButtonType b) {

                if (b == buttonTypeOk) {
                    String a, c, d;
                    a = text1.getText();
                    c = text2.getText();
                    d = text3.getText();
                    if (a.isEmpty() || c.isEmpty() || d.isEmpty()) {
                        return null;
                    }
                    return new Poem(a, c, d);
                }
                return null;
            }
        });


        // Poem Insertion Dialog Callback Handling
        addPoem.setOnMouseClicked((e) -> {
            if (!insertPoemDialog.isShowing()) {
                Optional<Poem> result = insertPoemDialog.showAndWait();
                if (result.isPresent()) {
                    if (insertPoemHandle(result.get())) {
                        showAlert("Insertion Successful",
                                "The specified poem has been inserted.",
                                Alert.AlertType.CONFIRMATION);
                    }
                } else {
                    showAlert("Insertion Error",
                            "Not enough data.!",
                            Alert.AlertType.ERROR);
                }
            }
        });
    }


    protected void insertCoupletDialogInit() {
        insertCoupletDialog.setTitle("Insert New Couplet");
        insertCoupletDialog.setResizable(true);

        Label label1 = new Label("PoemID: ");
        Label label2 = new Label("Line1: ");
        Label label3 = new Label("Line2: ");
        Label text1 = new Label();
        TextField text2 = new TextField();
        TextField text3 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        grid.add(label3, 1, 3);
        grid.add(text3, 2, 3);
        insertCoupletDialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Insert Couplet");
        insertCoupletDialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        insertCoupletDialog.setResultConverter(new Callback<ButtonType, Couplet>() {
            @Override
            public Couplet call(ButtonType b) {

                if (b == buttonTypeOk) {
                    String a, c, d;
                    a = text1.getText();
                    c = text2.getText();
                    d = text3.getText();
                    if (a.isEmpty() || c.isEmpty() || d.isEmpty()) {
                        return null;
                    }
                    return new Couplet((Integer.parseInt(a)), c, d);
                }
                return null;
            }
        });


        // Couplet Dialog Callback Handling
        addCouplet.setOnMouseClicked((e) -> {
            Poem poem = table.getSelectionModel().getSelectedItem();
            if (poem == null) {
                showAlert("Error", "No Poem Selected!", Alert.AlertType.ERROR);
                return;
            }
            ((Label) ((GridPane) insertCoupletDialog.getDialogPane()
                    .getContent()).getChildren().get(1)).setText(String.valueOf(poem.getId().get()));

            if (!insertCoupletDialog.isShowing()) {
                Optional<Couplet> result = insertCoupletDialog.showAndWait();
                if (result.isPresent()) {
                    insertCoupletHandle(result.get());
                } else {
                    showAlert("Insertion Error",
                            "Not enough data.!",
                            Alert.AlertType.ERROR);
                }
            }
        });
    }

    protected void viewPoemPaneHandleInit() {
        viewPoemDetails.setOnMouseClicked((e) -> {
            Poem poem = table.getSelectionModel().getSelectedItem();
            if (poem == null || poem.getId() == null) {
                showAlert("Error", "No poem selected!", Alert.AlertType.ERROR);
                return;
            }
            ObservableList<Couplet> couplets = poemsProvider.getPoemCouplets(poem.getId().getValue());
            Pane screen = poemDetails.getScreen(poem, couplets);
            mainScreenModifier.insertThirdPane(screen);
        });
    }

    protected Boolean insertPoemHandle(Poem poem) {
        return poemsProvider.insertPoem(poem);
    }

    protected void insertCoupletHandle(Couplet couplet) {
        poemsProvider.insertCoupletForPoem(couplet);
    }

    protected void fillTable(ObservableList<Poem> data) {
        table.setItems(data);
    }

    protected void showAlert(String title, String s, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(s);
        alert.show();
    }

    protected void delPoemHandle(int poemId) {
        poemsProvider.deletePoem(poemId);
        refresh();
    }

    protected void updatePoemPaneInit() {
        updatePoemDialog.setTitle("Update Poem");
        updatePoemDialog.setResizable(true);

        Label label = new Label("ID: ");
        Label label1 = new Label("Name: ");
        Label label2 = new Label("Book: ");
        Label label3 = new Label("Poet: ");
        Label text = new Label();
        TextField text1 = new TextField();
        TextField text2 = new TextField();
        TextField text3 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label, 0, 0);
        grid.add(text, 1, 0);
        grid.add(label1, 0, 1);
        grid.add(text1, 1, 1);
        grid.add(label2, 0, 2);
        grid.add(text2, 1, 2);
        grid.add(label3, 0, 3);
        grid.add(text3, 1, 3);
        grid.setVgap(4);
        grid.setHgap(4);
        updatePoemDialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Update Poem Info");
        updatePoemDialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        updatePoemDialog.setResultConverter(new Callback<ButtonType, Poem>() {
            @Override
            public Poem call(ButtonType b) {

                if (b == buttonTypeOk) {
                    String a, c, d, f;
                    f = text.getText();
                    a = text1.getText();
                    c = text2.getText();
                    d = text3.getText();
                    if (a.isEmpty() || c.isEmpty() || d.isEmpty()) {
                        return null;
                    }
                    Poem t = new Poem(a,c,d);
                    t.setPoemId(Integer.parseInt(f));

                    return t;
                }
                return null;
            }
        });


        // Poem Insertion Dialog Callback Handling
        updatePoem.setOnMouseClicked((e) -> {
            if (!updatePoemDialog.isShowing()) {
                Poem poem = table.getSelectionModel().getSelectedItem();

                if (poem == null || poem.getId() == null) {
                    showAlert("Error", "No poem selected!", Alert.AlertType.ERROR);
                    return;
                }

                ((Label) (((GridPane) updatePoemDialog.getDialogPane()
                        .getContent()).getChildren().get(1))).setText(String.valueOf(poem.getId().get()));
                ((TextField) (((GridPane) updatePoemDialog.getDialogPane()
                        .getContent()).getChildren().get(3))).setText(poem.getPoemTitle().getValue());
                ((TextField) (((GridPane) updatePoemDialog.getDialogPane()
                        .getContent()).getChildren().get(5))).setText(poem.getBookName().getValue());
                ((TextField) (((GridPane) updatePoemDialog.getDialogPane()
                        .getContent()).getChildren().get(7))).setText(poem.getPoetName().getValue());

                Optional<Poem> result = updatePoemDialog.showAndWait();

                if (result.isPresent()) {
                    updatePoemData(result.get());
                } else {
                    showAlert("Insertion Error",
                            "Not enough data.!",
                            Alert.AlertType.ERROR);
                }
            }
        });
    }

    protected void initView() {
        ObservableList<Poem> poemsList = poemsProvider.getPoemsList();
        table = new TableView<Poem>();
        table.setEditable(true);

        TableColumn<Poem, Integer> column1 = new TableColumn("ID");
        TableColumn<Poem, String> column2 = new TableColumn("Title");
        TableColumn<Poem, String> column3 = new TableColumn("Book");
        TableColumn<Poem, String> column4 = new TableColumn("Poet");

        column1.setCellValueFactory(cellData -> cellData.getValue().getIdAsObj());
        column2.setCellValueFactory(cellData -> cellData.getValue().getPoemTitle());
        column3.setCellValueFactory(cellData -> cellData.getValue().getBookName());
        column4.setCellValueFactory(cellData -> cellData.getValue().getPoetName());

        table.getColumns().addAll(column1, column2, column3, column4);

        poemMenu = new HBox();
        addPoem = new Button("Add New Poem");
        addCouplet = new Button("Add New Couplet");
        viewPoemDetails = new Button("View Poem");
        updatePoem = new Button("Update Poem");
        delPoem = new Button("Delete Poem");


        // Init Poem Manager Menu Initialization
        insertPoemDialogInit();
        insertCoupletDialogInit();
        viewPoemPaneHandleInit();
        updatePoemPaneInit();

        delPoem.setOnMouseClicked((e) -> {
            Poem poem = table.getSelectionModel().getSelectedItem();
            if (poem == null) {
                showAlert("Error", "No Poem Selected!", Alert.AlertType.ERROR);
                return;
            }
            delPoemHandle(poem.getId().get());
        });


        poemMenu.getChildren().add(0, addPoem);
        poemMenu.getChildren().add(1, addCouplet);
        poemMenu.getChildren().addAll(viewPoemDetails, updatePoem, delPoem);
        poemMenu.setSpacing(10);

        fillTable(poemsList);
    }

    protected void init() {
        initView();
        root.getChildren().add(table);
        root.getChildren().add(poemMenu);
        table.prefWidthProperty().bind(root.widthProperty());
        table.prefHeightProperty().bind(root.prefHeightProperty().subtract(50));
    }

    public Pane getScreen() {
        return root;
    }

    public void refresh() {
        fillTable(poemsProvider.getPoemsList());
        table.refresh();
    }
}
