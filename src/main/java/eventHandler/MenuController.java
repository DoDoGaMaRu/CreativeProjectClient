package eventHandler;

import domainObject.Recipe;
import domainObject.RecipeConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import network.Requester;
import network.protocol.Request;
import network.protocol.RequestCode;
import network.protocol.RequestType;
import network.protocol.Response;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class MenuController {
    @FXML
    private ImageView logoImage;
    @FXML
    private Button refButton;
    @FXML
    private Button allButton;
    @FXML
    private Button exprDateButton;
    @FXML
    private Button almostButton;
    @FXML
    private Button recentButton;
    @FXML
    private Button notEatButton;

    private final Requester requester =  Requester.getRequester();

    public void goRefControl(ActionEvent actionEvent) throws IOException {
        Stage thisStage = (Stage)refButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/myRef.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void goAll(ActionEvent actionEvent) throws IOException {
        Request req = Request.builder()
                .type(RequestType.GET)
                .code((byte) (RequestCode.RECIPE | RequestCode.COOKABLE))
                .cookie(requester.cookie())
                .build();
        Response res = requester.sendRequest(req);
        goSelectedPage(allButton, res, "recipes");
    }

    public void goExprDate(ActionEvent actionEvent) {
        Request req = Request.builder()
                .type(RequestType.GET)
                .code((byte) (RequestCode.RECIPE | RequestCode.EXPRT_DATE))
                .cookie(requester.cookie())
                .build();
        Response res = requester.sendRequest(req);
        goSelectedPage(exprDateButton, res, "recipes");
    }

    public void goAlmost(ActionEvent actionEvent) {
        Request req = Request.builder()
                .type(RequestType.GET)
                .code((byte) (RequestCode.RECIPE | RequestCode.SEMI_COOKABLE))
                .cookie(requester.cookie())
                .build();
        Response res = requester.sendRequest(req);
        goSelectedPage(almostButton, res, "recipes");
    }

    public void goNotEat(ActionEvent actionEvent) {
        Request req = Request.builder()
                .type(RequestType.GET)
                .code((byte) (RequestCode.RECIPE | RequestCode.RECENT))
                .cookie(requester.cookie())
                .build();
        Response res = requester.sendRequest(req);
        goSelectedPage(notEatButton, res, "recipes");
    }

    public void goRecent(ActionEvent actionEvent) throws IOException {
        Stage signUpStage =(Stage)refButton.getScene().getWindow();
        Parent signUp = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/recent.fxml"));
        Scene sc = new Scene(signUp);
        signUpStage.setScene(sc);
        signUpStage.show();
    }

    public void goSelectedPage(Button button, Response res, String key) {
        RecipeConverter recipeConverter = new RecipeConverter();
        ArrayList<Recipe> recipes = recipeConverter.convertRecipes((JSONArray) res.getBody().get(key));

        String title = button.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/recipeList.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RecipeListController controller = loader.getController();
        controller.setTitle(title);
        controller.setRecipes(recipes);

        Stage stage = (Stage)logoImage.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        requester.resetCookie();

        Stage thisStage = (Stage)refButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

}
