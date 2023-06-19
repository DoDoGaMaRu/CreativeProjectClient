package eventHandler.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;

public class RecentRow {
    private ObjectProperty<ImageView> img = new SimpleObjectProperty<>();
    private ObjectProperty<Label> text = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> exprDate = new SimpleObjectProperty<>();


    public RecentRow(ImageView img, Label name, LocalDateTime date) {
        this.img.set(img);
        this.text.set(name);
        this.exprDate.set(date);
    }

    public ObjectProperty<Label> getText() {
        return text;
    }

    public ObjectProperty<ImageView> getImg() {
        return img;
    }

    public ObjectProperty<LocalDateTime> getExprDate() {
        return exprDate;
    }

}
