module calc {
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.base;
	requires transitive javafx.graphics;
	requires jdk.jshell;

	opens calc to javafx.fxml;

	exports calc;
}