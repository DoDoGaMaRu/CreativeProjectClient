package eventHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
        boolean id_dupl_chk = is_duplicated_id(idTextField.getText());
        boolean phone_dupl_chk = is_duplicated_phone_num(phoneTextField.getText());

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

    private boolean is_duplicated_id(String id) {
        return id.equals("admin");
        //TODO DB에서 받아오는걸로 구현해야함
    }

    private boolean is_duplicated_phone_num(String phone_number) {
        return phone_number.equals("1234");
        //TODO DB에서 받아오는걸로 구현해야함
    }

    private void regist() {
        //TODO DB에 사용자 정보 저장
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

}
