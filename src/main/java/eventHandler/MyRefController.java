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
import network.protocol.Request;
import network.protocol.RequestCode;
import network.protocol.RequestType;
import network.protocol.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import network.Requester;


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
    private JSONArray myIngredients;
    private JSONArray ingredients;
    private final Requester requester =  Requester.getRequester();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Request req = Request.builder()
                .type(RequestType.GET)
                .code((byte) (RequestCode.REFRIGERATOR | RequestCode.OPEN))
                .cookie(requester.cookie())
                .build();
        Response res = requester.sendRequest(req);
        myIngredients = (JSONArray) res.getBody().get("myIngredients");
        nameTableCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        exprDateTableCol.setCellValueFactory(cellData -> cellData.getValue().getExprDate());
        exprDateTableCol.setCellFactory(column -> {
            return new TableCell<IngredientRow, LocalDate>() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if(date == null) {
                        return;
                    }
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
        searchResult = FXCollections.observableArrayList();
    }

    public void search(ActionEvent actionEvent) {
        searchResult.clear();
        if (searchTextField.getText().length() == 0) {
            searchResult.add("검색어를 입력하세요");
        } else {
            JSONObject body = makeSearchJson();
            Request req = Request.builder()
                    .type(RequestType.GET)
                    .code((byte) (RequestCode.INGREDIENT | RequestCode.SEARCH))
                    .cookie(requester.cookie())
                    .body(body)
                    .build();
            Response res = requester.sendRequest(req);
            ingredients = (JSONArray) res.getBody().get("ingredients");
            setSearchResult();
        }
        resultListView.setItems(searchResult);
    }

    private void setSearchResult() {
        if (ingredients.size() == 0) {
            searchResult.add("검색 결과가 없습니다.");
        } else {
            for (Object ingredient : ingredients) {
                JSONObject jsonObject = (JSONObject) ingredient;
                searchResult.add(jsonObject.get("name").toString());
            }
        }
    }


    private JSONObject makeSearchJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", searchTextField.getText());
        return jsonObject;
    }

    public void setSelectedItem(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() > 1 ) {
            String name = resultListView.getSelectionModel().getSelectedItem();
            searchTextField.setText(name);
        }
    }

    public void add(ActionEvent actionEvent) {
        Long key = getKey(searchTextField.getText());
        if (key == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("알림");
            alert.setContentText("검색결과에서 이름을 설정하세요!");
            alert.setHeaderText(null);
            alert.show();
            return;
        }
        if (exprtDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("알림");
            alert.setContentText("유통기한을 설정하세요!");
            alert.setHeaderText(null);
            alert.show();
            return;
        }
        JSONObject body = makeKeyJSON(key);
        Request req = Request.builder()
                .type(RequestType.POST)
                .code((byte) (RequestCode.REFRIGERATOR | RequestCode.PUT_IN))
                .cookie(requester.cookie())
                .body(body)
                .build();
        Response res = requester.sendRequest(req);
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
            JSONObject body = getMyKey(name);
            if ( result.get() == ButtonType.OK ) {
                Request req = Request.builder()
                        .type(RequestType.POST)
                        .code((byte) (RequestCode.REFRIGERATOR | RequestCode.PUT_OUT))
                        .cookie(requester.cookie())
                        .body(body)
                        .build();
                Response res = requester.sendRequest(req);
                refresh();
            }
        }
    }

    public JSONObject getMyKey(String name) {
        Long myKey = null;
        for (int i = 0; i < myIngredients.size(); i++) {
            JSONObject jsonObj = (JSONObject)myIngredients.get(i);
            if (jsonObj.get("name").equals(name)) {
                myKey = (Long) jsonObj.get("myKey");
                break;
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("myKey", myKey);
        return jsonObject;
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
