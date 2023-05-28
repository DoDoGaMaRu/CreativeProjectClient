package eventHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
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

    public void register(ActionEvent actionEvent) {
        JSONObject dupl_chk = new JSONObject(); // Todo Network에서 받아오는 걸로 수정
        boolean id_dupl_chk = (boolean) dupl_chk.get("IDDupl");
        boolean phone_dupl_chk = (boolean) dupl_chk.get("phNumDupl");

        if (id_dupl_chk) {
            idDuplicatedChkLabel.setText("Id Duplicated");
        }
        if (phone_dupl_chk) {
            phoneNumDuplicatedChkLabel.setText("Phone Number Duplicated");
        }
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
        signup.put("IO", idTextField.getText());
        signup.put("PW", pwTextField.getText());
        signup.put("name", nameTextField.getText());
        signup.put("phoneNumber", phoneTextField.getText());

        return signup;
    }

}
