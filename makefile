clean:
	rm jre -r
run:
	jre/bin/java.exe --module calc/calc.Main
jlink:
	jlink --module-path "%JAVA_HOME%\jmods;bin;" --add-modules calc --output jre --bind-services