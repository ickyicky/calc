package calc.model;

public abstract class Model {
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

	abstract public Double calculate(String equasion) throws FormatException;
}
