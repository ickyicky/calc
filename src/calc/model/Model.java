package calc.model;

import java.util.Arrays;
import java.util.List;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

/**
 * Model, klasa kt�ra przy pomocy API JShell przetwarza obliczenia.
 * 
 * @author Doman
 *
 */
public class Model {
	/**
	 * Publiczny wyj�tek, rzucany w przypadku, kiedy format przekazywanych danych
	 * jest niepoprawny, b�dz operacja jest nieobs�ugiwana.
	 * 
	 * @author Doman
	 *
	 */
	public class FormatException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2L;

		/**
		 * Inicjalizator wyj�tku, error to nazwa b��du, wy�wietlania przy wywo�aniu
		 * getMessage.
		 * 
		 * @param error
		 */
		public FormatException(String error) {
			super(error);
		}

	}

	JShell jshell;

	/**
	 * Publiczny inicjalizator klasy.
	 */
	public Model() {
		jshell = JShell.create();
	}

	/**
	 * Prywatna funkcja, wywo�uj�ca jshell API i wykonuj�ca obliczenia.
	 * 
	 * @param expression Wyra�enie do obliczenia w postaci stringa.
	 * @return Zwraca obliczon� warto�� jako double.
	 * @throws FormatException Wyrzuca wyj�tek z�ego formatu przy niepowodzeniu
	 *                         oblicze�.
	 */
	private Double _calculate(String expression) throws FormatException {
		List<SnippetEvent> events = jshell.eval(expression);

		if (events.isEmpty()) {
			throw new FormatException("Invalid expression format, cannto calculate!");
		}

		return Double.parseDouble(events.get(0).value());
	}

	/**
	 * Publiczna metoda, pozwalaj�ca na korzystanie z dost�pnych funkcji dodawania,
	 * mno�enia, odejmowania, dzielenia, modulo oraz pot�gowania, czyli obs�uguje
	 * dwuargumentowe operacje matematyczne.
	 * 
	 * @param first  Pierwszy argument operacji.
	 * @param second Drugi argument operacji.
	 * @param action Operator operacji.
	 * @return Zwraca obliczon� warto�� w postaci double.
	 * @throws FormatException Wyrzuca b��d, kiedy nie znajdzie odpowiedniej metody
	 *                         obs�ugi operacji.
	 */
	public Double calculate(Double first, Double second, String action) throws FormatException {
		List<String> simple_methods = Arrays.asList("+", "-", "/", "*", "%");

		if (simple_methods.contains(action)) {
			return _calculate(first.toString() + action + second.toString());
		} else if (action.equals("^")) {
			String expression = first.toString();
			Integer power = second.intValue();

			if (!second.equals(power.doubleValue()))
				throw new FormatException("Power must be integer!");

			for (int i = 1; i < power; i++)
				expression += "*" + first.toString();

			return _calculate(expression);
		}
		throw new FormatException("Invalid operator!");
	}

	/**
	 * Metoda obs�ugi operacji matematycznych, wymagaj�cych tylko jednego argumentu,
	 * obecnie jest to tylko operacja silni (!).
	 * 
	 * @param first  Pierwszy i jedyny argument operacji.
	 * @param action Operator operacji.
	 * @return Zwraca obliczon� warto�� w formacie double.
	 * @throws FormatException Wyrzuca b��d, kiedy nie znajdzie odpowiedniej metody
	 *                         obs�ugi operacji.
	 */
	public Double calculate(Double first, String action) throws FormatException {
		if (action.equals("!")) {
			Integer casted = first.intValue();

			if (!first.equals(casted.doubleValue())) {
				throw new FormatException("You can only perform factorial on integers!");
			}

			String expression = casted.toString();

			for (Integer i = casted - 1; i > 0; i--) {
				expression += "*" + i.toString();
			}
			return _calculate(expression);
		}
		throw new FormatException("Invalid operator!");
	}
}
