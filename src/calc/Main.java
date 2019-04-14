package calc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * G³ównia klasa widoku kalkulatora. Inicjalizuje wyœwietlane graficznie GUI.
 * 
 * @author Doman
 * @version 1.0
 */
public class Main extends Application {
	/**
	 * Start, obci¹¿ona funkcja, wykun¹j¹ca siê podczas uruchomienia interfejsu
	 * graficznego. Wczytkuje z pliku fxml uk³ad, rozmieszczenie oraz elementy
	 * interfejsu, ustawia tytu³ oraz styl (z pliku css).
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
	 * Funkcja main, jedynie uruchamiaj¹ca,s tartuj¹ca interfejs graficzny.
	 * 
	 * @param args Argumenty uruchomienia aplikacji.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}