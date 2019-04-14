package calc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * G��wnia klasa widoku kalkulatora. Inicjalizuje wy�wietlane graficznie GUI.
 * 
 * @author Doman
 * @version 1.0
 */
public class Main extends Application {
	/**
	 * Start, obci��ona funkcja, wykun�j�ca si� podczas uruchomienia interfejsu
	 * graficznego. Wczytkuje z pliku fxml uk�ad, rozmieszczenie oraz elementy
	 * interfejsu, ustawia tytu� oraz styl (z pliku css).
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

			Scene scene = new Scene(root);

			scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());

			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Calculator");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Funkcja main, jedynie uruchamiaj�ca,s tartuj�ca interfejs graficzny.
	 * 
	 * @param args Argumenty uruchomienia aplikacji.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}