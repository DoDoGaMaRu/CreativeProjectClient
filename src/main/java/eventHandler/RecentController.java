package eventHandler;

import domainObject.Recipe;
import domainObject.RecipeConverter;
import eventHandler.components.ImgLabelRow;
import eventHandler.components.IngredientRow;
import eventHandler.components.RecentRow;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
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
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class RecentController implements Initializable {
    @FXML
    private ImageView logoImage;
    @FXML
    private Label titleLabel;
    @FXML
    private TableView<RecentRow> recentListView;
    @FXML
    private TableColumn<RecentRow, ImageView> foodImageCol;
    @FXML
    private TableColumn<RecentRow, Label> foodNameCol;
    @FXML
    private TableColumn<RecentRow, LocalDateTime> dateCol;
    private JSONArray myList;
    private final Requester requester =  Requester.getRequester();
    ObservableList<RecentRow> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Request req = Request.builder()
                .type(RequestType.GET)
                .code((byte) (RequestCode.USER | RequestCode.COOKED))
                .cookie(requester.cookie())
                .build();
        Response res = requester.sendRequest(req);
        myList = (JSONArray) res.getBody().get("myCookedList");
        dateCol.setCellValueFactory(cellData -> cellData.getValue().getExprDate());
        foodImageCol.setCellValueFactory(cellData -> cellData.getValue().getImg());
        foodNameCol.setCellValueFactory(cellData -> cellData.getValue().getText());

        data = addData();
        recentListView.setItems(data);
    }
    public ObservableList<RecentRow> addData() {
        RecipeConverter converter = new RecipeConverter();
        if (myList.size() == 0) {
            return data;
        }
        for (int i = 0; i < myList.size(); i++) {
            JSONObject jsonObject = (JSONObject) myList.get(i);
            Recipe recipe = converter.convertRecipe((JSONObject) jsonObject.get("recipe"));
            ImageView view = new ImageView(recipe.getAttFileNoMain());
            Label label = new Label(recipe.getRcpNm());
            LocalDateTime time = (LocalDateTime) jsonObject.get("regdate");
            view.setFitHeight(120);
            view.setFitWidth(120);
            view.setPreserveRatio(true);
            label.setFont(Font.font(20));
            label.setMinHeight(120);
            label.setAlignment(Pos.CENTER);
            RecentRow row = new RecentRow(view, label, time);
            data.add(row);
        }
        return data;
    }

    public void goMenu(MouseEvent mouseEvent) throws IOException {
        Stage thisStage = (Stage) logoImage.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/menu.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }
}
