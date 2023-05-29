package eventHandler;

import domainObject.Manual;
import eventHandler.components.DetailRow;
import eventHandler.components.ImgLabelRow;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import domainObject.Recipe;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        details = FXCollections.observableArrayList();

        imgTableCol.setCellValueFactory(cellData -> cellData.getValue().getImg());
        descriptionTableCol.setCellValueFactory(cellData -> cellData.getValue().getText());

        detailTableView.setItems(details);
    }

    public void goRecipeList(MouseEvent mouseEvent) throws Exception {
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/recipeList.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void goMenu() throws IOException {
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/menu.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
    }

    public void goAfterCook(ActionEvent actionEvent) throws IOException {
        //TODO 최근먹은 목록에 추가(영양소 추천을 위해)
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/afterCook.fxml"));
        Scene sc = new Scene(parent);
        thisStage.setScene(sc);
        thisStage.show();
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