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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

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
    ObservableList<IngredientRow> data;
    private LocalDate today = LocalDate.now();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameTableCol.setCellValueFactory(cellData -> cellData.getValue().getName());
        exprDateTableCol.setCellValueFactory(cellData -> cellData.getValue().getExprDate());
        exprDateTableCol.setCellFactory(column -> {
            return new TableCell<IngredientRow, LocalDate>() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);

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

        //더미데이터
        IngredientRow i = new IngredientRow(new SimpleStringProperty("감자"), LocalDate.of(2025,10,21));
        IngredientRow j = new IngredientRow(new SimpleStringProperty("감자"), LocalDate.of(2022,10,21));
        IngredientRow k = new IngredientRow(new SimpleStringProperty("감자"), LocalDate.of(2023,5,23));

        data = FXCollections.observableArrayList();
        data.add(i);
        data.add(j);
        data.add(k);

        regTableView.setItems(data);


    }

    public void add(ActionEvent actionEvent) {
        //TODO null이 아닐때만 추가
        data.add(new IngredientRow(new SimpleStringProperty(searchTextField.getText()), exprtDatePicker.getValue()));
        searchTextField.clear();
        exprtDatePicker.getEditor().clear();
    }

    public void goMenu(MouseEvent mouseEvent) throws IOException {
        Stage thisStage = (Stage)logoImage.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/menu.fxml"));
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
            }
        }
    }
}
