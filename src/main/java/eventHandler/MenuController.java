package eventHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private ImageView logoImage;
    @FXML
    private Button refButton;
    @FXML
    private Button cookableButton;
    @FXML
    private Button exprDateButton;

    public void goRefControl(ActionEvent actionEvent) throws IOException {
        Stage thisStage = (Stage)refButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/myRef.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void goCookable(ActionEvent actionEvent) throws IOException {
        goSelectedPage(cookableButton);
    }

    public void goExprDate(ActionEvent actionEvent) {
        goSelectedPage(exprDateButton);
    }

    public void goSelectedPage(Button button) {
        String title = button.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/recipeList.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RecipeListController controller = loader.getController();
        controller.setTitle(title);

        Stage stage = (Stage)logoImage.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Stage thisStage = (Stage)refButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

}
