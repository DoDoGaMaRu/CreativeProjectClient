package eventHandler.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ImgLabelRow {
    private ObjectProperty<ImageView> img = new SimpleObjectProperty<>();
    private ObjectProperty<Label> text = new SimpleObjectProperty<>();

    public ImgLabelRow(ImageView img, Label name) {
        this.img.set(img);
        this.text.set(name);
    }

    public ObjectProperty<Label> getText() {
        return text;
    }

    public ObjectProperty<ImageView> getImg() {
        return img;
    }
}
