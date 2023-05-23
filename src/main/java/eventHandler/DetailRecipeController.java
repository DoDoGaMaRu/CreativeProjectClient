package eventHandler;

import eventHandler.components.DetailRow;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import domainObject.Recipe;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DetailRecipeController implements Initializable {
    @FXML
    private Label foodNameLabel;
    @FXML
    private Label ingredientsLabel;
    @FXML
    private ImageView logoImage;
    @FXML
    private TableView<DetailRow> detailTableView;
    @FXML
    private TableColumn<DetailRow, ImageView> imgTableCol;
    @FXML
    private TableColumn<DetailRow, String> descriptionTableCol;
    private Recipe recipe;
    ObservableList<DetailRow> details;
    private List<DetailRow> detailList = new ArrayList<>();

    //더미데이터
    DetailRow temp0 = new DetailRow("http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_1.png", new SimpleStringProperty("1. 손질된 새우를 끓는 물에 데쳐 건진다.a"));
    DetailRow temp1 = new DetailRow("http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_2.png", new SimpleStringProperty("2. 연두부, 달걀, 생크림, 설탕에 녹인 무염버터를 믹서에 넣고 간 뒤 새우(1)를 함께 섞어 그릇에 담는다.b"));
    DetailRow temp2 = new DetailRow("http://www.foodsafetykorea.go.kr/uploadimg/cook/20_00028_3.png", new SimpleStringProperty("3. 시금치를 잘게 다져 혼합물 그릇(2)에 뿌리고 찜기에 넣고 중간 불에서 10분 정도 찐다.c"));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foodNameLabel.setText("새우 두부 계란찜");
        ingredientsLabel.setText("연두부 75g(3/4모), 칵테일새우 20g(5마리), 달걀 30g(1/2개), 생크림 13g(1큰술), 설탕 5g(1작은술), 무염버터 5g(1작은술)\n" +
                "고명\n" +
                "시금치 10g(3줄기)");
        detailList.add(temp0);        detailList.add(temp1);        detailList.add(temp2);

        details = FXCollections.observableArrayList();
        for (DetailRow detail : detailList) {
            details.add(detail);
        }

        imgTableCol.setCellValueFactory(cellData -> cellData.getValue().getImage());
        descriptionTableCol.setCellValueFactory(cellData -> cellData.getValue().getDescription());

        detailTableView.setItems(details);
    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void goMenu(MouseEvent mouseEvent) throws Exception {
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/recipeList.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }
}