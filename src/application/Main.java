package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

public class Main extends Application {
    Connection connection;
    TextField firstname;
    TextField lastname;
    TextField address;
    TextField postalCode;
    TextField province;
    TextField phoneNumber;
    TextField idField;
    String player_Id = "";
    String gameId = "";

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        connection = connectionTodb();
    	
        firstname = new TextField();
        lastname = new TextField();
        address = new TextField();
        postalCode = new TextField();
        province = new TextField();
        phoneNumber = new TextField();
        idField = new TextField();
        
        // For Label
        Label playerInfo = new Label("Player's Information:");
        Label firstnameLabel = new Label("First Name:");
        Label lastnameLabel = new Label("Last Name:");
        Label addressLabel = new Label("Address:");
        Label provinceLabel = new Label("Province:");
        Label postalCodeLabel = new Label("Postal Code:");
        Label phoneNumberLabel = new Label("Phone Number:");

        // Providing Padding Col1
        Insets label = new Insets(0, 10, 0, 0);
        playerInfo.setPadding(label);
        playerInfo.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        firstnameLabel.setPadding(label);
        lastnameLabel.setPadding(label);
        addressLabel.setPadding(label);
        provinceLabel.setPadding(label);
        postalCodeLabel.setPadding(label);
        phoneNumberLabel.setPadding(label);

        // col2
        Insets label2 = new Insets(0,10,0,0);

        // col4 radio buttons
        HBox playerId = new HBox();
        Label idFieldLabel = new Label("Update Player by ID:");
        idFieldLabel.setPadding(label);
        //idField = new TextField(); // Removed re-declaration
        Button upbtn = new Button("Update");
        upbtn.setPrefWidth(110);
        playerId.getChildren().add(upbtn);

        // Game Information
        Insets label3 = new Insets(0, 10, 0, 0);
        Label gameInfo = new Label("Game Information:");
        Label gameTitleLabel = new Label("Game Title:");
        Label gameScoreLabel = new Label("Game Score:");
        Label datePlayedLabel = new Label("Date Played:");
        gameInfo.setPadding(label);
        gameInfo.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        gameTitleLabel.setPadding(label3);
        gameScoreLabel.setPadding(label3);
        datePlayedLabel.setPadding(label3);
    	
        TextField gameTitle = new TextField("");
        TextField gameScore = new TextField("");
        TextField datePlayed = new TextField("");

        // Display all
        GridPane displayInfo = new GridPane();
        displayInfo.setPadding(new Insets(40, 40, 40, 40));
        // Set the horizontal gap between columns 
        displayInfo.setHgap(5);
        // Set the vertical gap between rows
        displayInfo.setVgap(3);

        // Display labels
        displayInfo.add(playerInfo, 0, 0, 1, 1);
        displayInfo.add(firstnameLabel, 0, 1);
        displayInfo.add(lastnameLabel, 0, 2);
        displayInfo.add(addressLabel, 0, 3);
        displayInfo.add(provinceLabel, 0, 4);
        displayInfo.add(postalCodeLabel, 0, 5);
        displayInfo.add(phoneNumberLabel, 0, 6);

        // Display text
        displayInfo.add(firstname, 1, 1);
        displayInfo.add(lastname, 1, 2);
        displayInfo.add(address, 1, 3);
        displayInfo.add(province, 1, 4);
        displayInfo.add(postalCode, 1, 5);
        displayInfo.add(phoneNumber, 1, 6);

        // Display update
        displayInfo.add(idFieldLabel, 3, 0);
        displayInfo.add(idField, 4, 0);
        displayInfo.add(playerId, 5, 0);

        // Display game
        displayInfo.add(gameInfo, 3, 3, 1, 1);
        displayInfo.add(gameTitleLabel, 3, 4);
        displayInfo.add(gameTitle, 4, 4);
        displayInfo.add(gameScoreLabel, 3, 5);
        displayInfo.add(gameScore, 4, 5);
        displayInfo.add(datePlayedLabel, 3, 6);
        displayInfo.add(datePlayed, 4, 6);

        // Display Button
        Button button1 = new Button("Create Players");
        Button button2 = new Button("Display All Players");
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(button1, button2);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.BOTTOM_RIGHT);
        vbox.setSpacing(10);
        vbox.getChildren().add(hbox);
        button1.setPrefWidth(150);
        displayInfo.add(button1,4,9);
        displayInfo.add(button2,5,9);

        // Search button
        Button button3 = new Button("Search by ID");
        button3.setPrefWidth(100);
        displayInfo.add(button3,5,1);

        
        // Create Button
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    creatPlayer();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            private void creatPlayer() throws SQLException {
				Connection connection = connectionTodb();
				
				int id_player = 0;
				int id_game = 0;
				
				//insert player 
				PreparedStatement stmt = connection.prepareStatement("insert into PLAYER (FIRST_NAME,LAST_NAME,ADDRESS,PROVINCE, POSTAL_CODE,PHONE_NUMBER) VALUES (?,?,?,?,?,?)",new String[]{"PLAYER_ID"}); 
				String FirstName = firstname.getText(); 
				String LastName = lastname.getText(); 
				String Address = address.getText(); 
				String Province = province.getText(); 
				String PostalCode = postalCode.getText(); 
				String PhoneNumber = phoneNumber.getText(); 
				stmt.setString(1,FirstName); 
				stmt.setString(2,LastName); 
				stmt.setString(3,Address); 
				stmt.setString(4,Province); 
				stmt.setString(5,PostalCode); 
				stmt.setString(6,PhoneNumber);
				
				int i= stmt.executeUpdate(); 
				//stmt.executeUpdate(); 
				ResultSet resultSet = stmt.getGeneratedKeys();

				if (resultSet.next()) {
					id_player = Integer.parseInt(resultSet.getString(1)); 
					} 
					System.out.println(i+"player records inserted");
					
					
					//insert Game 
					PreparedStatement stmt1 = connection.prepareStatement("INSERT INTO GAME (GAME_TITLE) VALUES (?)",new String[]{"GAME_ID"}); 
					stmt1.setString(1,gameTitle.getText()); 
					
					int j =stmt1.executeUpdate(); 
					ResultSet resultSet1 = stmt1.getGeneratedKeys();
					if (resultSet1.next()) 
					{ id_game = Integer.parseInt(resultSet1.getString(1)); 
				} 
				System.out.println(j+"game records inserted"); 
				System.out.println("player"+id_player); 
				System.out.println("game"+id_game);
					
				//insert PlayerAndGame
				PreparedStatement stmt2 = connection.prepareStatement("INSERT INTO PLAYERANDGAME (PLAYER_ID, GAME_ID,PLAYING_DATE,SCORE) VALUES (?, ?,?,?)"); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				stmt2.setInt(1,id_player);
				stmt2.setInt(2,id_game);
				stmt2.setString(3,datePlayed.getText());
				stmt2.setInt(4,Integer.parseInt(gameScore.getText()) );
				
				stmt2.executeUpdate(); 
				
				resultSet.close();
				resultSet1.close();
				stmt.close();
				stmt1.close(); 
				stmt2.close();
				connection.close(); 
				displayTable();
				
            }
        });
        
        // Display Table
        button2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                displayTable();
            }
        });
        
        // Search player by Id
        button3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
               try { Connection connection = connectionTodb(); 
			   player_Id =idField.getText(); 
			   Statement stmt = connection.createStatement(); 
			   if (player_Id == "") { 
				JOptionPane.showMessageDialog(null,"Enter Player ID"); 
			   return;
            }
			
			String query = "SELECT B.FIRST_NAME, B.LAST_NAME, B.ADDRESS, B.PROVINCE, B.POSTAL_CODE, B.PHONE_NUMBER, C.GAME_TITLE, A.SCORE, TO_CHAR(A.PLAYING_DATE, 'YYYY-MM-DD') AS PLAYING_DATE, A.GAME_ID \r\n" 
			+ "FROM PLAYERANDGAME A \r\n" 
			+ "INNER JOIN PLAYER B \r\n" 
			+ "ON A.PLAYER_ID = B.PLAYER_ID \r\n" 
			+ "INNER JOIN GAME C \r\n" 
			+ "ON A.GAME_ID = C.GAME_ID \r\n" 
			+ "WHERE B.PLAYER_ID = " + player_Id; 
			
			ResultSet rs = stmt.executeQuery(query);
			
			//Validation of existing Player 
			if (rs.next()== false || player_Id == "") { 
				JOptionPane.showMessageDialog(null,"There is no data for this Player ID"); 
				} 
				else 
				{ 
					firstname.setText(rs.getString(1)); 
					lastname.setText(rs.getString(2)); 
					address.setText(rs.getString(3)); 
					province.setText(rs.getString(4)); 
					postalCode.setText(rs.getString(5)); 
					phoneNumber.setText(rs.getString(6)); 
					gameTitle.setText(rs.getString(7)); 
					gameScore.setText(rs.getString(8)); 
					datePlayed.setText(rs.getString(9)); 
					gameId = rs.getString(10); 
				}
				
				rs.close();
				stmt.close(); 
				connection.close(); 
				} catch (SQLException e) { 
				throw new RuntimeException(e); 
				} 
				}
				
        });

        // Update button
        upbtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                try { 
				
				//ValidationplayerID 
				if (player_Id == "") { 
					JOptionPane.showMessageDialog(null,"Search player by id"); 
					return; 
					}
				Connection connection = connectionTodb();
				
				player_Id =idField.getText(); 
				Statement stmt = connection.createStatement(); 
				
				//Update Player 
				
				PreparedStatement prepStmt = connection.prepareStatement("UPDATE PLAYER SET FIRST_NAME = ?, LAST_NAME = ?, ADDRESS = ?, PROVINCE = ?, POSTAL_CODE = ?, PHONE_NUMBER = ? WHERE PLAYER_ID = ?"); 
				prepStmt.setString (1, firstname.getText()); 
				prepStmt.setString (2, lastname.getText()); 
				prepStmt.setString (3, address.getText()); 
				prepStmt.setString (4, province.getText()); 
				prepStmt.setString (5, postalCode.getText()); 
				prepStmt.setString (6, phoneNumber.getText()); 
				prepStmt.setString (7, player_Id); 
				prepStmt.executeUpdate(); 
				
				//Update Game 
				prepStmt = connection.prepareStatement("UPDATE GAME SET GAME_TITLE = ?WHERE GAME_ID = ?"); 
				prepStmt.setString(1,gameTitle.getText()); 
				prepStmt.setString(2,gameId); 
				prepStmt.executeUpdate(); 
				
				//update PlayerAnd Game
				prepStmt = connection.prepareStatement("UPDATE PLAYERANDGAME SET SCORE = ?, PLAYING_DATE = ?WHERE PLAYER_ID = ? AND GAME_ID = ?"); 
				prepStmt.setString(1,gameScore.getText()); 
				prepStmt.setString(2,datePlayed.getText()); 
				prepStmt.setString(3,player_Id); 
				prepStmt.setString(4,gameId); 
				prepStmt.executeUpdate(); 
				prepStmt.close();
				connection.close(); 
				displayTable();
				} catch (SQLException e)
				{ throw new RuntimeException(e); 
				} 
				
            }
        });



        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setCenter(displayInfo);
        root.setBottom(vbox);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Player Information");
        stage.setMinWidth(750);
        stage.show();
    }


    
    public Connection connectionTodb() {
        Connection connection = null;
        try {
            System.out.println("> Start Program ...");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("> Driver Loaded successfully.");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@199.212.26.208:1521:SQLD", "COMP228_W24_sy_49", "password");
          //  connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD", "COMP228_W24_sy_49", "password");
            System.out.println("Database connected successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        } 
            return connection;
        
    }
    
    // Display table Table
    private void displayTable() {
        DisplayTable.tableView = new TableView<>(); 
		DisplayTable displayPlayer = new DisplayTable(); 
		displayPlayer.showData(); 
		Scene scene = new Scene(DisplayTable.tableView); 
		Stage stage = new Stage(); stage.setScene(scene); 
		stage.setWidth(900); 
		stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
