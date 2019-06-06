package calc.model;

public abstract class Model {
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

	abstract public Double calculate(String equasion) throws FormatException;
}
