package eventHandler;

import eventHandler.components.RecipeRow;
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
import org.json.simple.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RecipeListController implements Initializable {
    @FXML
    private Label titleLabel;
    @FXML
    private TableView<RecipeRow> foodListTableView;
    @FXML
    private TableColumn<RecipeRow, ImageView> foodImageCol;
    @FXML
    private TableColumn<RecipeRow, Label> foodNameCol;
    ObservableList<RecipeRow> data;

    //더미데이터
    private ImageView view_1 = new ImageView("http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00028_1.png");
    private Label name_1 = new Label("새우 두부 계란찜");
    private RecipeRow test_1 = new RecipeRow(view_1, name_1);
    //private updater.Recipe dummy = new updater.Recipe();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        view_1.setFitHeight(100);
        view_1.setFitWidth(100);
        view_1.setPreserveRatio(true);
        name_1.setFont(Font.font(20));
        name_1.setMinHeight(100);
        name_1.setAlignment(Pos.CENTER);

        foodImageCol.setCellValueFactory(cellData -> cellData.getValue().getImgSrc());
        foodNameCol.setCellValueFactory(cellData -> cellData.getValue().getFoodName());
        data = FXCollections.observableArrayList();
        data.add(test_1);

        foodListTableView.setItems(data);
    }

    public void goMenu(MouseEvent mouseEvent) throws IOException {
        Stage thisStage = (Stage) titleLabel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/menu.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void goRecipe(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() > 1) {
            int idx = foodListTableView.getSelectionModel().getSelectedIndex();
            Stage thisStage = (Stage) titleLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            Parent parent = loader.load(getClass().getClassLoader().getResource("fxml/detailRecipe.fxml"));
            Scene sc = new Scene(parent);

/*
            com.example.client.controller.DetailRecipeController con = loader.getController();
            con.setRecipe(dummy);
*/

            thisStage.setScene(sc);
            thisStage.show();

        }
    }


}