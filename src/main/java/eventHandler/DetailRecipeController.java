package eventHandler;

import eventHandler.components.DetailRow;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import domainObject.Recipe;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @FXML
    private TableView<IngredientRow> regTableView;
    @FXML
    private TableColumn<IngredientRow, String> nameTableCol;
    @FXML
    private TableColumn<IngredientRow, LocalDate> exprDateTableCol;
    private Recipe recipe;
    ObservableList<DetailRow> details;
    ObservableList<IngredientRow> data;
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

    public void goRecipeList(MouseEvent mouseEvent) throws Exception {
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/recipeList.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void delIngredient(MouseEvent mouseEvent) {
        if (data.size() == 0) {
            return;
        }
        if( mouseEvent.getClickCount() > 1 ) {
            int idx = regTableView.getSelectionModel().getSelectedIndex();
            String name = nameTableCol.getCellData(idx);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("삭제");
            alert.setHeaderText(name + "를 삭제 하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if ( result.get() == ButtonType.OK ) {
                data.remove(idx);
                refresh();
            }
        }
    }

    public void goMenu() throws IOException {
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/menu.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void addRecentlyEatList(ActionEvent actionEvent) throws IOException {
        //TODO 최근먹은 목록에 추가(영양소 추천을 위해)
        goMenu();
    }

    public void refresh() {
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/detailRecipe.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }
}