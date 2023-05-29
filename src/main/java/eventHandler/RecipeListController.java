package eventHandler;

import domainObject.Recipe;
import eventHandler.components.ImgLabelRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RecipeListController implements Initializable {
    @FXML
    private Label titleLabel;
    @FXML
    private TableView<ImgLabelRow> foodListTableView;
    @FXML
    private TableColumn<ImgLabelRow, ImageView> foodImageCol;
    @FXML
    private TableColumn<ImgLabelRow, Label> foodNameCol;
    @FXML
    private ImageView logoImage;
    ObservableList<ImgLabelRow> data;
    private ArrayList<Recipe> recipes;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foodImageCol.setCellValueFactory(cellData -> cellData.getValue().getImg());
        foodNameCol.setCellValueFactory(cellData -> cellData.getValue().getText());
        data = FXCollections.observableArrayList();

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

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        for (Recipe recipe : recipes) {
            ImageView view = new ImageView(recipe.getAttFileNoMain());
            Label label = new Label(recipe.getRcpNm());

            view.setFitHeight(120);
            view.setFitWidth(120);
            view.setPreserveRatio(true);
            label.setFont(Font.font(20));
            label.setMinHeight(120);
            label.setAlignment(Pos.CENTER);

            ImgLabelRow row = new ImgLabelRow(view, label);
            data.add(row);
        }
    }

    public void goRecipe(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() > 1) {
            int idx = foodListTableView.getSelectionModel().getSelectedIndex();
            Stage stage = (Stage)logoImage.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/detailRecipe.fxml"));
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            DetailRecipeController controller = loader.getController();
            controller.setRecipe(recipes.get(idx));
            Scene sc = new Scene(parent);
            stage.setScene(sc);
            stage.show();
        }
    }


}