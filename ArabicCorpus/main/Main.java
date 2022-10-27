package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import screens.PoemsScreen;

public class Main extends Application {

    private GridPane pane = new GridPane();
    private VBox menu = new VBox();
    private Stage mainStageRef;

    // Screen Singleton Instances
    protected PoemsScreen poemsScreen = new PoemsScreen(new MainScreenModifier());

    public void initDashboard() {
        pane.setMinSize(900, 600);
        pane.addColumn(3);
        menu.setMinHeight(pane.getMinHeight());
        menu.setMinWidth(menu.getMinWidth() + 100);
        menu.setStyle("-fx-background-color: darkgray");
        Label item1 = new Label();
        Label item2 = new Label();
        Label item3 = new Label();
        item1.setText("Roots Manager");
        item2.setText("Poems Manager");
        item3.setText("Tokens Manager");


        item1.setOnMouseClicked((e) -> {
            updateScreen(((Label)e.getSource()).getText());
        });

        item2.setOnMouseClicked((e) -> {
            updateScreen(((Label)e.getSource()).getText());
        });

        item3.setOnMouseClicked((e) -> {
            updateScreen(((Label)e.getSource()).getText());
        });

        menu.getChildren().add(item1);
        menu.getChildren().add(item2);
        menu.getChildren().add(item3);
        menu.prefHeightProperty().bind(pane.heightProperty());
        pane.add(menu, 0, 0);
    }


    public void updateScreen(String name) {
        if(pane.getChildren().contains(poemsScreen.getScreen())) {
            pane.getChildren().remove(poemsScreen.getScreen());
        }
        switch (name) {
            case "Poems Manager":
                pane.add(poemsScreen.getScreen(), 1, 0);
                poemsScreen.getScreen().prefHeightProperty().bind(pane.heightProperty());
                poemsScreen.refresh();
                break;
            case "Roots Manager":
                // Get relevant screen
                break;
            case "Token Manager":
                // Get relevant screen
                break;
            default:
                break;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStageRef = primaryStage;
        primaryStage.setTitle("Arabic Corpus");
        initDashboard();
        Scene scene = new Scene(pane, 900, 600);
        pane.prefHeightProperty().bind(primaryStage.heightProperty());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public class MainScreenModifier{
        public void insertThirdPane(Pane third){
            if(pane.getChildren().size() > 2){
                pane.getChildren().remove(2);
            }
            pane.add(third, 2, 0);
            Button b = ((Button)((GridPane) third.getChildren().get(0)).getChildren().get(8));
            b.setOnMouseClicked((e) -> {
                if(pane.getChildren().size() > 2){
                    pane.getChildren().remove(2);
                }
            });
        }
    }
}
