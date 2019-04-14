package calc.model;

import java.util.Arrays;
import java.util.List;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

/**
 * Model, klasa która przy pomocy API JShell przetwarza obliczenia.
 * 
 * @author Doman
 *
 */
public class Model {
	/**
	 * Publiczny wyj¹tek, rzucany w przypadku, kiedy format przekazywanych danych
	 * jest niepoprawny, b¹dz operacja jest nieobs³ugiwana.
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
		 * Inicjalizator wyj¹tku, error to nazwa b³êdu, wyœwietlania przy wywo³aniu
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
	 * Prywatna funkcja, wywo³uj¹ca jshell API i wykonuj¹ca obliczenia.
	 * 
	 * @param expression Wyra¿enie do obliczenia w postaci stringa.
	 * @return Zwraca obliczon¹ wartoœæ jako double.
	 * @throws FormatException Wyrzuca wyj¹tek z³ego formatu przy niepowodzeniu
	 *                         obliczeñ.
	 */
	private Double _calculate(String expression) throws FormatException {
		List<SnippetEvent> events = jshell.eval(expression);

		if (events.isEmpty()) {
			throw new FormatException("Invalid expression format, cannto calculate!");
		}

		return Double.parseDouble(events.get(0).value());
	}

	/**
	 * Publiczna metoda, pozwalaj¹ca na korzystanie z dostêpnych funkcji dodawania,
	 * mno¿enia, odejmowania, dzielenia, modulo oraz potêgowania, czyli obs³uguje
	 * dwuargumentowe operacje matematyczne.
	 * 
	 * @param first  Pierwszy argument operacji.
	 * @param second Drugi argument operacji.
	 * @param action Operator operacji.
	 * @return Zwraca obliczon¹ wartoœæ w postaci double.
	 * @throws FormatException Wyrzuca b³¹d, kiedy nie znajdzie odpowiedniej metody
	 *                         obs³ugi operacji.
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
	 * Metoda obs³ugi operacji matematycznych, wymagaj¹cych tylko jednego argumentu,
	 * obecnie jest to tylko operacja silni (!).
	 * 
	 * @param first  Pierwszy i jedyny argument operacji.
	 * @param action Operator operacji.
	 * @return Zwraca obliczon¹ wartoœæ w formacie double.
	 * @throws FormatException Wyrzuca b³¹d, kiedy nie znajdzie odpowiedniej metody
	 *                         obs³ugi operacji.
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
