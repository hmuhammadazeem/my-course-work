package db_adapters;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;

import Database.DbConnector;

import static utils.type_converters.getIntProp;
import static utils.type_converters.getStringProp;


public class Poem {
    protected SimpleIntegerProperty poemId;
    protected SimpleStringProperty poemTitle;
    protected SimpleStringProperty bookName;
    protected SimpleStringProperty poetName;
    protected ObservableList<Couplet> couplets;

    protected Connection db;

    public Poem(){ db = DbConnector.getConnection(); }

    public Poem(String title, String book, String poet){
        this.poemTitle = getStringProp(title);
        this.bookName = getStringProp(book);
        this.poetName = getStringProp(poet);
    }


    public Boolean insertPoem(Poem poem){
        String query = "INSERT INTO `poem` (`poemId`, `poemTitle`, `bookName`, `poetName`) VALUES (NULL,?,?,?)";
        PreparedStatement stmt = null;
        Boolean result = null;
        try {
            stmt = db.prepareStatement(query);
            stmt.setString(1, poem.poemTitle.getValue());
            stmt.setString(2, poem.bookName.getValue());
            stmt.setString(3, poem.poemTitle.getValue());
            result = stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ObservableList<Poem> getPoemsList(){
        ObservableList<Poem> poemsList;
        String query = "SELECT poemId, poemTitle, bookName, poetName FROM `poem`;";
        Statement stmt = null;
        ResultSet results = null;
        try {
            stmt = db.prepareStatement(query);
            results = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        poemsList = FXCollections.observableArrayList();

        while (true) {
            try {
                if (!results.next()) break;
                Poem poem = new Poem();
                poem.poemId = getIntProp(results.getInt("poemId"));
                poem.poemTitle = getStringProp(results.getString("poemTitle"));
                poem.bookName = getStringProp(results.getString("bookName"));
                poem.poetName = getStringProp(results.getString("poetName"));
                poemsList.add(poem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return poemsList;
    }

    public void delPoem(int poemId){
        String query = "DELETE FROM `poem` WHERE poemId=?";

        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, poemId);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPoemId(int poemId) {
        this.poemId = getIntProp(poemId);
    }

    public void updatePoem(Poem poem){
        String query = "UPDATE `poem` SET poemTitle=?, bookName=?, poetName=? WHERE poemId=?;";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setString(1, poem.getPoemTitle().getValue());
            stmt.setString(2, poem.getBookName().getValue());
            stmt.setString(3, poem.getPoetName().getValue());
            stmt.setInt(4, poem.getId().getValue());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SimpleIntegerProperty getId(){
        return poemId;
    }

    public ObservableValue<Integer> getIdAsObj(){ return poemId.asObject(); }

    public SimpleStringProperty getPoemTitle() {
        return poemTitle;
    }

    public SimpleStringProperty getBookName() {
        return bookName;
    }

    public SimpleStringProperty getPoetName() {
        return poetName;
    }
}
