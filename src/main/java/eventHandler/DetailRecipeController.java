package eventHandler;

import domainObject.Manual;
import eventHandler.components.ImgLabelRow;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import domainObject.Recipe;
import network.Requester;
import network.protocol.Request;
import network.protocol.RequestCode;
import network.protocol.RequestType;
import network.protocol.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailRecipeController implements Initializable {
    @FXML
    private Label foodNameLabel;
    @FXML
    private Label ingredientsLabel;
    @FXML
    private ImageView logoImage;
    @FXML
    private TableView<ImgLabelRow> detailTableView;
    @FXML
    private TableColumn<ImgLabelRow, ImageView> imgTableCol;
    @FXML
    private TableColumn<ImgLabelRow, Label> descriptionTableCol;
    ObservableList<ImgLabelRow> details;
    private Recipe recipe;
    private final Requester requester =  Requester.getRequester();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        details = FXCollections.observableArrayList();
        ingredientsLabel.setWrapText(true);
        imgTableCol.setCellValueFactory(cellData -> cellData.getValue().getImg());
        descriptionTableCol.setCellValueFactory(cellData -> cellData.getValue().getText());

        detailTableView.setItems(details);
    }

    public void goMenu() throws IOException {
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/menu.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void goAfterCook(ActionEvent actionEvent) throws IOException {
        postCookedRecipe();
        JSONArray myIngredients = getMyIngredients();
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/afterCook.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AfterCookController controller = loader.getController();
        controller.setMyIngredients(myIngredients);
        controller.setRecipe(recipe);
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void postCookedRecipe() {
        JSONObject jsonObject = makeRcpSeqJSON();
        Request req = Request.builder()
                .type(RequestType.POST)
                .code((byte) (RequestCode.USER | RequestCode.COOKED))
                .cookie(requester.cookie())
                .body(jsonObject)
                .build();
        Response res = requester.sendRequest(req);
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
        foodNameLabel.setText(recipe.getRcpNm());
        ingredientsLabel.setText(recipe.getRcpPartsDtls());
        for (Manual manual : recipe.getManuals()) {
            String imgUrl = manual.getImg();
            if (imgUrl.equals("")) {
                break;
            }
            ImageView view = new ImageView(imgUrl);
            Label label = new Label(manual.getText());
            view.setPreserveRatio(true);
            view.setFitHeight(200);
            view.setFitWidth(200);
            label.setFont(Font.font(15));
            label.setWrapText(true);
            details.add(new ImgLabelRow(view, label));
        }
    }

}