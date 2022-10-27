package utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class type_converters {
    public static SimpleStringProperty getStringProp(String i){
        return new SimpleStringProperty(i);
    }

    public  static SimpleIntegerProperty getIntProp(int i){
        return new SimpleIntegerProperty(i);
    }
}
