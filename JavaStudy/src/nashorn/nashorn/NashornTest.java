package nashorn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class NashornTest {

	public static String greeting(String name) {
		System.out.println("From Java " + name);
		return "From Java";
	}

	public static void getCls(Object obj) {
		System.out.println(obj.getClass());
	}
	
	public static void f(ScriptObjectMirror mirror) {
	    System.out.println(mirror.getClassName() + ": " +
	        Arrays.toString(mirror.getOwnKeys(true)));
	}
	
	public static void callMethodOfJSObject(ScriptObjectMirror person) {
	    System.out.println("Full Name is: " + person.callMember("getFullName"));
	}
	

	public static void main(String[] args) throws ScriptException,
			FileNotFoundException, NoSuchMethodException {
		ScriptEngine engine = new ScriptEngineManager()
				.getEngineByName("nashorn");
		engine.eval("print('hello');"); // execute js from source
		File f = new File("resources/scripts.js");
		engine.eval(new FileReader(f)); // execute js from file
		Invocable invocable = (Invocable) engine;
		Object sum = invocable.invokeFunction("add", 1, 2);
		System.out.println(sum);
		invocable.invokeFunction("f2", new Date());
		invocable.invokeFunction("f2", LocalDateTime.now());
	}
}
