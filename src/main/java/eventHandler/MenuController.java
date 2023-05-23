package eventHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button refButton;
    @FXML
    private Button cookableButton;

    public void goRefControl(ActionEvent actionEvent) throws IOException {
        Stage thisStage = (Stage)refButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/myRef.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void goCookable(ActionEvent actionEvent) throws IOException {
        String title = cookableButton.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/recipeList.fxml"));
        Parent root = loader.load();

        RecipeListController controller = loader.getController();
        controller.setTitle(title);

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
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
