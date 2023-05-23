package eventHandler.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class RecipeRow {
    private ObjectProperty<ImageView> img = new SimpleObjectProperty<>();
    private ObjectProperty<Label> foodName = new SimpleObjectProperty<>();

    public RecipeRow(ImageView img, Label name) {
        this.img.set(img);
        this.foodName.set(name);
    }

    public ObjectProperty<Label> getFoodName() {
        return foodName;
    }

    public ObjectProperty<ImageView> getImgSrc() {
        return img;
    }
}
