package eventHandler;

import domainObject.Recipe;
import eventHandler.components.IngredientRow;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import network.Requester;
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

public class AfterCookController implements Initializable {
    @FXML
    private ImageView logoImage;
    @FXML
    private TableView<IngredientRow> regTableView;
    @FXML
    private TableColumn<IngredientRow, String> nameTableCol;
    @FXML
    private TableColumn<IngredientRow, LocalDate> exprDateTableCol;
    ObservableList<IngredientRow> data;
    private LocalDate today = LocalDate.now();
    ObservableList<IngredientRow> myRefData;
    private JSONArray myIngredients = new JSONArray();
    private final Requester requester =  Requester.getRequester();
    private Recipe recipe;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        System.out.println(myKey);
        return jsonObject;
    }

    public void refresh() {
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/afterCook.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AfterCookController controller = loader.getController();

        controller.setMyIngredients(getMyIngredients());
        controller.setRecipe(recipe);
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void setMyIngredients(JSONArray myIngredients) {
        this.myIngredients = myIngredients;
        myRefData = MyIngredientListControl.addMyIngredientData(myIngredients);
        regTableView.setItems(myRefData);
    }

    public JSONArray getMyIngredients() {
        JSONObject jsonObject = makeRcpSeqJSON();
        Request req = Request.builder()
                .type(RequestType.GET)
                .code((byte) (RequestCode.INGREDIENT | RequestCode.COINCIDE))
                .cookie(requester.cookie())
                .body(jsonObject)
                .build();
        Response res = requester.sendRequest(req);
        return (JSONArray) res.getBody().get("myIngredients");
    }

    public JSONObject makeRcpSeqJSON() {
        JSONObject jsonObject = new JSONObject();
        Long temp = Long.valueOf(recipe.getRcpSeq());
        jsonObject.put("rcpSeq", temp);
        return jsonObject;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
