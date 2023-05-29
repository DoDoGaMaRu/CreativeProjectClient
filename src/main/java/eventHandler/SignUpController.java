package eventHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import network.Requester;
import network.protocol.Request;
import network.protocol.RequestCode;
import network.protocol.RequestType;
import network.protocol.Response;
import org.json.simple.JSONObject;

public class SignUpController {
    @FXML
    private TextField idTextField;
    @FXML
    private PasswordField pwTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Button registerButton;
    @FXML
    private Label idDuplicatedChkLabel;
    @FXML
    private Label phoneNumDuplicatedChkLabel;

    private final Requester requester =  Requester.getRequester();

    public void register(ActionEvent actionEvent) {
        Request req = Request.builder()
                .type(RequestType.POST)
                .code((byte) (RequestCode.USER | RequestCode.REGIST))
                .body(makeSignupJson())
                .build();
        Response res = requester.sendRequest(req);

        JSONObject dupl_chk = res.getBody();
        boolean id_dupl_chk = (boolean) dupl_chk.get("IDDupl");
        boolean phone_dupl_chk = (boolean) dupl_chk.get("phNumDupl");

        idDuplicatedChkLabel.setText(id_dupl_chk ? "Id Duplicated":"");
        phoneNumDuplicatedChkLabel.setText(phone_dupl_chk ? "Phone Number Duplicated":"");
        if (!id_dupl_chk && !phone_dupl_chk) {
            regist();
        }
    }

    private void regist() {
        alertPopUp();
        Stage stage = (Stage)registerButton.getScene().getWindow();
        stage.close();
    }

    private void alertPopUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("알림");
        alert.setContentText("회원가입 성공!");
        alert.setHeaderText(null);
        alert.show();
    }

    public JSONObject makeSignupJson() {
        JSONObject signup = new JSONObject();
        signup.put("id", idTextField.getText());
        signup.put("pw", pwTextField.getText());
        signup.put("name", nameTextField.getText());
        signup.put("phone", phoneTextField.getText());

        return signup;
    }

}
