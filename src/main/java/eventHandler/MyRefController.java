package eventHandler;

import eventHandler.components.IngredientRow;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class MyRefController implements Initializable {

    @FXML
    private ImageView logoImage;
    @FXML
    private TextField searchTextField;
    @FXML
    private DatePicker exprtDatePicker;
    @FXML
    private TableView<IngredientRow> regTableView;
    @FXML
    private TableColumn<IngredientRow, String> nameTableCol;
    @FXML
    private TableColumn<IngredientRow, LocalDate> exprDateTableCol;
    @FXML
    private ListView<String> resultListView;
    ObservableList<String> searchResult;
    ObservableList<IngredientRow> myRefData;
    private LocalDate today = LocalDate.now();
    private JSONArray myIngredients = new JSONArray();
    private JSONArray ingredients = new JSONArray();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameTableCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        exprDateTableCol.setCellValueFactory(cellData -> cellData.getValue().getExprDate());
        exprDateTableCol.setCellFactory(column -> {
            return new TableCell<IngredientRow, LocalDate>() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);

                    TableRow currentRow = getTableRow();
                    if (!isEmpty()) {
                        if (date.isBefore(today)) {
                            currentRow.setStyle("-fx-background-color: #c6567c");
                        }
                        else if (date.isBefore(today.plusDays(7))) {
                            currentRow.setStyle("-fx-background-color: orange");
                        }
                        else {
                            currentRow.setStyle("-fx-background-color: white");
                        }
                        setText(date.toString());
                    }
                }
            };
        });

        myRefData = MyIngredientListControl.addMyIngredientData(myIngredients);
        regTableView.setItems(myRefData);
    }

    public void searchIngredientList(KeyEvent keyEvent) {
        if (searchTextField.getText().length() == 0) {
            return;
        }
        makeSearchJson();
        //TODO 서버에 JSON 전송
        //TODO 서버에서 JSON 받아옴
        ingredients = new JSONArray(); //서버에서 받아온것
        setSearchResult();
        resultListView.setItems(searchResult);
    }

    private void setSearchResult() {
        for (int i = 0; i < ingredients.size(); i++) {
            JSONObject jsonObject = (JSONObject) ingredients.get(i);
            searchResult.add(jsonObject.get("name").toString());
        }
    }


    private JSONObject makeSearchJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", searchTextField.getText());
        System.out.println(searchTextField.getText());
        return jsonObject;
    }

    public void setSelectedItem(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() > 1 ) {
            int idx = regTableView.getSelectionModel().getSelectedIndex();
            String name = nameTableCol.getCellData(idx);

            searchTextField.setText(name);
        }
    }

    public void add(ActionEvent actionEvent) {
        Long key = getKey(searchTextField.getText());
        makeKeyJSON(key); //TODO 여기서 만들어진 JSONobj를 서버에 보냄
        refresh();
    }

    private Long getKey(String name) {
        Long key = null;
        for (int i = 0; i < ingredients.size(); i++) {
            JSONObject jsonObject = (JSONObject) ingredients.get(i);
            if (jsonObject.get("name").equals(name)) {
                key = (Long) jsonObject.get("key");
            }
        }
        return key;
    }

    public JSONObject makeKeyJSON(Long key) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", key);
        jsonObject.put("exprDate", exprtDatePicker.getValue());

        return jsonObject;
    }

    public void goMenu(MouseEvent mouseEvent) throws IOException {
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/menu.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void delIngredient(MouseEvent mouseEvent) {
        if (myRefData.size() == 0) {
            return;
        }
        if( mouseEvent.getClickCount() > 1 ) {
            int idx = regTableView.getSelectionModel().getSelectedIndex();
            String name = nameTableCol.getCellData(idx);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("삭제");
            alert.setHeaderText(name + "를 삭제 하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            //TODO JSON에 getMyKey로 키값 전송해주기
            if ( result.get() == ButtonType.OK ) {
                refresh();
            }
        }
    }

    public Long getMyKey(String name) {
        Long myKey = null;
        for (int i = 0; i < myIngredients.size(); i++) {
            JSONObject jsonObj = (JSONObject)myIngredients.get(i);
            if (jsonObj.get("name").equals(name)) {
                myKey = (Long) jsonObj.get("myKey");
                break;
            }
        }
        return myKey;
    }

    public void refresh() {
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/myRef.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

}
