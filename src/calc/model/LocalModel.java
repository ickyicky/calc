package calc.model;

import java.util.List;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

public class LocalModel extends Model {
	JShell jshell;

	public LocalModel() {
		jshell = JShell.create();
		jshell.eval("import java.lang.Math");
		jshell.eval(
				"Integer factorial(Integer val){Integer result = val; for(Integer i=val-1; i>1;i--){result *= i;}; return result;};");
	}

	public Double calculate(String equasion) throws FormatException {
		equasion = equasion.replace(" ", "");
		equasion = equasion.replaceAll("([0-9]*[\\.]?[0-9]?)\\^([0-9]*[//.]?[0-9]?)", "Math.pow($1,$2)");
		equasion = equasion.replaceAll("([0-9]*)[\\.]?[0-9]?\\!", "factorial($1)");
		List<SnippetEvent> events = jshell.eval(equasion);

		if (events.get(0).value() == null) {
			throw new FormatException("Invalid expression format, cannto calculate!");
		}

		return Double.parseDouble(events.get(0).value());
	}
}
