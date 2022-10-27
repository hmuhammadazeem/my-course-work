package screens;

import db_adapters.Couplet;
import db_adapters.Poem;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.control.TableView;

public class ViewPoem {
    protected GridPane poemDetails;
    protected GridPane poemMeta;
    protected TableView<Couplet> poemCouplets;

    public Pane getScreen(Poem poem, ObservableList<Couplet> couplets){
        poemDetails = new GridPane();
        poemMeta = new GridPane();

        poemDetails.addColumn(2);

        poemCouplets = new TableView();

        poemMeta.addColumn(2);

        poemMeta.setVgap(8);
        poemMeta.setHgap(4);

        Button backButton = new Button("Go Back");

        Label l1 = new Label("Poem ID: ");
        Label l2 = new Label("Poem Title: ");
        Label l3 = new Label("Book: ");
        Label l4 = new Label("Poet: ");

        Label k1 = new Label(String.valueOf(poem.getId().get()));
        Label k2 = new Label(poem.getPoemTitle().getValue());
        Label k3 = new Label(poem.getBookName().getValue());
        Label k4 = new Label(poem.getPoetName().getValue());

        poemMeta.add(l1, 0, 0);
        poemMeta.add(k1, 1, 0);
        poemMeta.add(l2, 0, 1);
        poemMeta.add(k2, 1, 1);
        poemMeta.add(l3, 0, 2);
        poemMeta.add(k3, 1, 2);
        poemMeta.add(l4, 0, 3);
        poemMeta.add(k4, 1, 3);
        poemMeta.add(backButton, 0, 4);

        TableColumn<Couplet, Integer> column1 = new TableColumn("CoupletID");
        TableColumn<Couplet, String> column2 = new TableColumn("Line 1");
        TableColumn<Couplet, String> column3 = new TableColumn("Line 2");

        column1.setCellValueFactory(cellData -> cellData.getValue().getCoupletIdAsObj());
        column2.setCellValueFactory(cellData -> cellData.getValue().line1Property());
        column3.setCellValueFactory(cellData -> cellData.getValue().line2Property());

        poemCouplets.getColumns().addAll(column1, column2, column3);

        poemDetails.add(poemMeta, 0, 0);
        poemDetails.add(poemCouplets, 1, 0);

        poemCouplets.setItems(couplets);

        poemMeta.minWidth(400);
        poemCouplets.prefWidth(500);
        poemDetails.setHgap(20);
        Insets pad = new Insets(10, 0, 0,15);
        poemDetails.setPadding(pad);

        return poemDetails;
    }
}
