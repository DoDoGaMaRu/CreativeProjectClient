package eventHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import network.Requester;
import network.protocol.Request;
import network.protocol.RequestCode;
import network.protocol.RequestType;
import network.protocol.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button loginButton;
    @FXML
    private TextField idTextField;
    @FXML
    private PasswordField pwTextField;

    private final Requester requester =  Requester.getRequester();

    public void login(ActionEvent actionEvent) throws IOException {
        JSONObject userJson = makeLoginInfoJSON();
        Request req = Request.builder()
                .type(RequestType.POST)
                .code((byte) (RequestCode.USER | RequestCode.LOGIN))
                .body(userJson)
                .build();
        Response res = requester.sendRequest(req);
        JSONObject resBody = res.getBody();

        if (resBody.get("token") == null) {
            // TODO 로그인 실패

        }
        else {
            // set cookie
            requester.cookie().put("serial", resBody.get("serial"));
            requester.cookie().put("id", userJson.get("id"));
            requester.cookie().put("token", resBody.get("token"));

            Stage thisStage = (Stage)loginButton.getScene().getWindow();
            Parent menu = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/menu.fxml"));
            Scene sc = new Scene(menu);
            thisStage.setScene(sc);
            thisStage.show();
        }
    }

    public void goSignUp(MouseEvent mouseEvent) throws IOException {
        Stage signUpStage = new Stage();
        Parent signUp = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/signUp.fxml"));
        Scene sc = new Scene(signUp);
        signUpStage.setScene(sc);
        signUpStage.show();
    }

    public JSONObject makeLoginInfoJSON() {
        JSONObject user = new JSONObject();
        user.put("id", idTextField.getText());
        user.put("pw", pwTextField.getText());
        return user;
    }
}
