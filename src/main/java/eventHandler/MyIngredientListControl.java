package eventHandler;

import eventHandler.components.IngredientRow;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;

public class MyIngredientListControl {

    public static ObservableList<IngredientRow> addMyIngredientData(JSONArray jsonArray) {
        ObservableList<IngredientRow> myRefData = FXCollections.observableArrayList();
        if (jsonArray.size() == 0) {
            return myRefData;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            IngredientRow row = new IngredientRow(new SimpleStringProperty(jsonObject.get("name").toString()),
                    (LocalDate) jsonObject.get("exprDate"));
            myRefData.add(row);
        }
        return myRefData;
    }

}
