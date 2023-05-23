package eventHandler.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class IngredientRow {
    private StringProperty name;
    private ObjectProperty<LocalDate> exprDate = new SimpleObjectProperty<>();

    public IngredientRow(StringProperty name, LocalDate date) {
        this.name = name;
        this.exprDate.set(date);
    }
    public StringProperty getName() {
        return name;
    }

    public ObjectProperty<LocalDate> getExprDate() {
        return exprDate;
    }

}
