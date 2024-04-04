package application;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisplayTable extends Application {
    private ObservableList<ObservableList<String>> data;
    static TableView<ObservableList<String>> tableView;


    public void showData() {
        data = FXCollections.observableArrayList();
        try {
            Main con = new Main();
            Connection connection = con.connectionTodb();
            Statement stmt = connection.createStatement();

            String query = "SELECT B.PLAYER_ID, B.FIRST_NAME || ' ' || B.LAST_NAME AS PLAYER_NAME, B.ADDRESS, B.POSTAL_CODE, B.PROVINCE, B.PHONE_NUMBER, C.GAME_TITLE, A.SCORE, A.PLAYING_DATE \r\n" +
                    "FROM PLAYERANDGAME A \r\n" +
                    "INNER JOIN PLAYER B \r\n" +
                    "ON A.PLAYER_ID = B.PLAYER_ID \r\n" +
                    "INNER JOIN GAME C \r\n" +
                    "ON A.GAME_ID = C.GAME_ID \r\n" +
                    "ORDER BY B.PLAYER_ID";

            ResultSet rs = stmt.executeQuery(query);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                int j = i;

                TableColumn<ObservableList<String>, String> tbl = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                tbl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> param) {
                        String value = param.getValue().get(j);
                        // Handle null values
                        if (value == null) {
                            return new SimpleStringProperty("");
                        } else {
                            return new SimpleStringProperty(value);
                        }
                    }
                });
                tableView.getColumns().add(tbl);
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    String value = rs.getString(i);
                    // Handle null values
                    if (value == null) {
                        row.add("");
                    } else {
                        row.add(value);
                    }
                }
                data.add(row);
            }

            tableView.setItems(data);
            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error on Building Data");
        }
    }

    @Override
    public void start(Stage arg0) throws Exception {

    }
}
