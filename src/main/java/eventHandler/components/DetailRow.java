package eventHandler.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

public class DetailRow {
    private ObjectProperty<ImageView> image = new SimpleObjectProperty<>();
    private StringProperty description;

    public DetailRow(String imageUrl, StringProperty description) {
        ImageView temp = new ImageView(imageUrl);
        temp.setFitWidth(100);
        temp.setFitHeight(100);
        temp.setPreserveRatio(true);
        this.image.set(temp);
        this.description = description;
    }
    public StringProperty getDescription() {
        return description;
    }

    public ObjectProperty<ImageView> getImage() {
        return image;
    }
}