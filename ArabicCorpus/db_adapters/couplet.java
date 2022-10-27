package db_adapters;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import Database.DbConnector;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static utils.type_converters.getIntProp;
import static utils.type_converters.getStringProp;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;


public class Couplet {
    protected SimpleIntegerProperty poemId;
    protected SimpleIntegerProperty coupletId;
    protected SimpleStringProperty line1;
    protected SimpleStringProperty line2;

    protected Connection db;

    public Couplet(){ db = DbConnector.getConnection(); }

    public Couplet(int poemId, String l1, String l2){
        this.poemId = getIntProp(poemId);
        this.line1 = getStringProp(l1);
        this.line2 = getStringProp(l2);
    }

    public void insertCouplet(Couplet couplet){
        String query = "INSERT INTO `couplet`(coupletId,poemId,line1,line2) VALUES (NULL,?, ?, ?);";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, couplet.poemId.getValue());
            stmt.setString(2, couplet.line1.getValue());
            stmt.setString(3, couplet.line2.getValue());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCouplet(Couplet couplet){
        String query = "UPDATE `couplet` SET LINE1=?, LINE2=? WHERE coupletId=? and poemId=?;";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setString(1, couplet.line1.getValue());
            stmt.setString(2, couplet.line2.getValue());
            stmt.setInt(3, couplet.coupletId.getValue());
            stmt.setInt(4, couplet.poemId.getValue());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCouplet(int poemId, int coupletId){
        String query = "DELETE FROM `couplet` WHERE coupletId=? and poemId=?;";
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, coupletId);
            stmt.setInt(2, poemId);
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Couplet> getPoemCouplets(int poemId){
        ObservableList<Couplet> coupletList;
        String query = "SELECT coupletId, line1, line2 FROM `couplet` where poemId =?;";

        ResultSet results = null;
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setInt(1, poemId);
            stmt.execute();
            results = stmt.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        coupletList = FXCollections.observableArrayList();

        while (true) {
            try {
                if (!results.next()) break;
                Couplet couplet = new Couplet();
                couplet.poemId = getIntProp(poemId);
                couplet.coupletId = getIntProp(results.getInt("coupletId"));
                couplet.line1 = getStringProp(results.getString("line1"));
                couplet.line2 = getStringProp(results.getString("line2"));
                coupletList.add(couplet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return coupletList;
    }

    public int getPoemId() {
        return poemId.get();
    }

    public SimpleIntegerProperty poemIdProperty() {
        return poemId;
    }

    public ObservableValue<Integer> getPoemIdAsObj() {
        return poemId.asObject();
    }

    public int getCoupletId() {
        return coupletId.get();
    }

    public ObservableValue<Integer> getCoupletIdAsObj() {
        return coupletId.asObject();
    }

    public SimpleIntegerProperty coupletIdProperty() {
        return coupletId;
    }

    public String getLine1() {
        return line1.get();
    }

    public SimpleStringProperty line1Property() {
        return line1;
    }

    public String getLine2() {
        return line2.get();
    }

    public SimpleStringProperty line2Property() {
        return line2;
    }
}
