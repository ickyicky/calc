package calc;

import java.util.HashMap;

import calc.model.LocalModel;
import calc.model.Model;
import calc.model.Model.FormatException;
import calc.model.WebModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Klasa kontrolera g³ównego, kontroluj¹ca widok g³ówny kalkulatora. Odpowiada
 * za walidacjê wpisanychw wartoœci, co chroni przed wiêkszoœci¹ nieporz¹danych
 * zachowañ (jak dzielenie przez zero).
 * 
 * @author Doman
 *
 */
public class MainController {
	private String API_URI = "http://localhost:8080/arithmeter/calculate";

	@FXML
	private TextField text_field;
	@FXML
	private Button button_model;
	@FXML
	private Button button_AC;
	@FXML
	private Button button_substract;
	@FXML
	private Button button_modulo;
	@FXML
	private Button button_add;
	@FXML
	private Button button_equals;
	@FXML
	private Button button_coma;
	@FXML
	private Button button_divide;
	@FXML
	private Button button_switch;
	@FXML
	private Button button_multiply;
	@FXML
	private Button button_factorial;
	@FXML
	private Button button_power;
	@FXML
	private Button button_open;
	@FXML
	private Button button_close;
	@FXML
	private Button button_0;
	@FXML
	private Button button_1;
	@FXML
	private Button button_2;
	@FXML
	private Button button_3;
	@FXML
	private Button button_4;
	@FXML
	private Button button_5;
	@FXML
	private Button button_6;
	@FXML
	private Button button_7;
	@FXML
	private Button button_8;
	@FXML
	private Button button_9;

	private String currentModel;

	private HashMap<String, Model> models = new HashMap<String, Model>();

	private boolean needClear;

	/**
	 * Funkcja licz¹ca, wywo³uj¹ca na model operacjee liczenia. Obs³uguje
	 * wystêpuj¹ce b³êdy podczas operacji oraz w razie potrzeeby czyœci pamiêæ lub
	 * wyœwietla komunikaty o b³êdach za pomoc¹ funkcji error_alert().
	 */
	private void calculate() {
		try {
			String result = Double.toString(models.get(currentModel).calculate(text_field.getText()));
			text_field.setText(result);
		} catch (FormatException e) {
			error_alert(
					"Something went wrong! Please, check format of your equasion, if it's correct and compatibile with programs functionality.");
		}
	}

	/**
	 * Funkcja s³urz¹ca do czyszczenia kalkulatora. Czyœci wypisywany teekst.
	 */
	private void clear() {
		text_field.setText("");
		needClear = false;
	}

	/**
	 * Funkja odpowiadaj¹ca za wyœwietlenie w okienku typu ERROR Alert komunikatu o
	 * b³êdzie.
	 * 
	 * @param text Tekst do wyœwieetlenia w nag³ówku komunikatu.
	 */
	private void error_alert(String text) {
		Alert alert = new Alert(AlertType.ERROR);

		alert.setTitle("ERROR");
		alert.setContentText("Please click OK to continue");
		alert.setHeaderText(text);
		alert.showAndWait();
	}

	/**
	 * Funkcja g³ówna, inicjalizacyjna naszego kontrolera, która ustawia wszystkim
	 * przyciskom akcjê wykonywana podczas ich naciœniêcia. Dzieje siê to poprzez
	 * wykonanie funkcji setOnAction na ka¿dym z nich, z parametrem - lambd¹,
	 * prostym kodem funkcji obs³uguj¹cym zdarzenie naciœniêcia. Przyciski s¹ dzielo
	 * na trzy typy: numeryczne(np. 0,1), akcji(np. +,-) oraz indywidualnee, o
	 * specjalnej metodze dzia³ania.
	 */
	public void initialize() {
		currentModel = "web";
		models.put("web", new WebModel(API_URI));
		models.put("local", new LocalModel());

		button_model.setText(currentModel);
		button_model.setOnAction(e -> {
			if (currentModel == "web")
				currentModel = "local";
			else
				currentModel = "web";

			button_model.setText(currentModel);
		});

		button_AC.setOnAction(e -> {
			clear();
		});

		Button[] text_buttons = { button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7,
				button_8, button_9, button_coma, button_open, button_close };

		for (Button button : text_buttons) {
			button.setOnAction(ee -> {
				if (needClear)
					clear();

				text_field.setText(text_field.getText() + button.getText());
			});
		}

		Button[] action_buttons = { button_divide, button_multiply, button_add, button_substract, button_modulo,
				button_power, button_factorial };

		for (Button button : action_buttons) {
			button.setOnAction(ee -> {
				if (needClear)
					clear();

				text_field.setText(text_field.getText() + " " + button.getText() + " ");
			});
		}

		button_switch.setOnAction(e -> {
			if (needClear)
				clear();

			String text = text_field.getText();
			if (text.length() > 0 && text.charAt(0) == '-') {
				text_field.setText(text.substring(1));
			} else {
				text_field.setText("-" + text);
			}
		});

		button_equals.setOnAction(e -> {
			calculate();
			needClear = true;
		});
	}

}
