package calc;

import calc.model.Model;
import calc.model.Model.FormatException;
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
	/**
	 * Wyj¹tek, rzucany podczas b³êdu walidacji wpisanych wartoœci. Klasa jest
	 * prywatna, wyj¹tki s¹ obs³ugiwane w ciele klasy MainController
	 * 
	 * @author
	 * 
	 */
	private class ValidationException extends Exception {
		private static final long serialVersionUID = 1L;

		/**
		 * Konstruktor wyj¹tku.
		 * 
		 * @param error Opis b³êdu, zwracany przy getValue().
		 */
		ValidationException(String error) {
			super(error);
		}
	}

	@FXML
	private TextField text_field;
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

	private Double remembered = 0.0;
	private String action = "";
	private Boolean need_clear = false;

	private Model model = new Model();

	/**
	 * Metoda walidacji wpisanych wartoœci.
	 * 
	 * @exception ValidationEException Wyj¹tek jest zg³aszany, w przypadku próby
	 *                                 dzielenia przez zero (równie¿ przy operacji
	 *                                 modulo.
	 * 
	 * @param second_number Parametr drugiej zmienneej uczestnicz¹cej w operacji.
	 */
	private void validate(Double second_number) throws ValidationException {
		if ((action.equals("/") || action.equals("%")) && second_number == 0.0)
			throw new ValidationException("Cannot divide by zero!");
	}

	/**
	 * Funkcja licz¹ca, wywo³uj¹ca na model operacjee liczenia. Obs³uguje
	 * wystêpuj¹ce b³êdy podczas operacji oraz w razie potrzeeby czyœci pamiêæ lub
	 * wyœwietla komunikaty o b³êdach za pomoc¹ funkcji error_alert().
	 */
	private void calculate() {
		if (!action.equals("")) {
			try {
				if (action.equals("!"))
					remembered = model.calculate(remembered, action);
				else {
					Double second_number = Double.parseDouble(text_field.getText());
					validate(second_number);

					remembered = model.calculate(remembered, second_number, action);
				}
			} catch (ValidationException e) {
				error_alert(e.getMessage());
			} catch (FormatException e) {
				error_alert(e.getMessage());
				clear();
			} catch (NumberFormatException e) {
				error_alert("Well, you have to decide on operation, my dear.");
				clear();
			}
		} else if (text_field.getText().length() == 0)
			error_alert("Please, provide us a number first.");
		else
			remembered = Double.parseDouble(text_field.getText());
	}

	/**
	 * Funkcja s³urz¹ca do czyszczenia kalkulatora. Czyœci wypisywany teekst,
	 * ostatni¹ zapamiêtana wartoœæ oraz akcjêê, a tak¿e resetuje wskazanie na
	 * potrzebê czyszczenia.
	 */
	private void clear() {
		text_field.setText("");
		remembered = 0.0;
		action = "";
		need_clear = false;
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
		button_AC.setOnAction(e -> {
			clear();
		});

		Button[] numeric_buttons = { button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7,
				button_8, button_9 };

		for (Button button : numeric_buttons) {
			button.setOnAction(ee -> {
				if (need_clear)
					clear();

				text_field.setText(text_field.getText() + button.getText());
			});
		}

		Button[] action_buttons = { button_divide, button_multiply, button_add, button_substract, button_modulo,
				button_power, button_factorial };

		for (Button button : action_buttons) {
			button.setOnAction(ee -> {
				if (!need_clear)
					calculate();

				need_clear = false;
				action = button.getText();
				text_field.setText("");
			});
		}

		button_switch.setOnAction(e -> {
			if (need_clear)
				clear();

			String text = text_field.getText();
			if (text.length() > 0 && text.charAt(0) == '-') {
				text_field.setText(text.substring(1));
			} else {
				text_field.setText("-" + text);
			}
		});

		button_coma.setOnAction(e -> {
			if (need_clear)
				clear();

			String text = text_field.getText();

			if (text.contains("."))
				error_alert("Can't use comma twice!");
			else
				text_field.setText(text + ".");
		});

		button_equals.setOnAction(e -> {
			calculate();

			text_field.setText(remembered.toString());
			need_clear = true;
		});
	}
}
