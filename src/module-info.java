module calc {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.json;
	requires transitive javafx.base;
	requires transitive javafx.graphics;
	requires jdk.jshell;
	requires java.net.http;

	exports calc;

	opens calc to javafx.fxml;
}