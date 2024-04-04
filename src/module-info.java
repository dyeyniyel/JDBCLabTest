module JDBCTesting2 {
	requires javafx.controls;
	requires java.sql;
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
}
