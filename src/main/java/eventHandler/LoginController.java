package eventHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button loginButton;

    public void login(ActionEvent actionEvent) throws IOException {
        //TODO 로그인 정보 비교 구현 해야함
        Stage thisStage = (Stage)loginButton.getScene().getWindow();
        Parent menu = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/menu.fxml"));
        Scene sc = new Scene(menu);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void goSignUp(MouseEvent mouseEvent) throws IOException {
        Stage signUpStage = new Stage();
        Parent signUp = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/signUp.fxml"));
        Scene sc = new Scene(signUp);
        signUpStage.setScene(sc);
        signUpStage.show();
    }
}
