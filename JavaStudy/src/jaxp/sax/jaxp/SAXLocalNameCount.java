package sax.jaxp;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class SAXLocalNameCount extends DefaultHandler {
	Hashtable<String, Integer> tags;

	public static void main(String[] a) throws ParserConfigurationException,
			SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new SAXLocalNameCount());
		xmlReader.setErrorHandler(new MyErrorHandler(System.err));
		xmlReader.parse("resources/sax_example.xml");
	}

	@Override
	public void startDocument() throws SAXException {
		tags = new Hashtable<>();
	}

	@Override
	public void endDocument() throws SAXException {
		tags.entrySet().forEach(
				e -> System.out.println(e.getKey() + ":" + e.getValue()));
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		String key = localName;
		Integer value = tags.get(key);

		if (null == value) { // no value with key
			value = 0;
		}

		++value;

		tags.put(key, value);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
	}

	@Repeatable(Schedules.class)
	public @interface Schedule {
		String day();
	}

	public @interface Schedules {
		Schedule[] value();
	}

	// Repeating annotations
	@Schedule(day = "last")
	@Schedule(day = "first")
	public int sum(int a, int b) {
		return a + b;
	}

	private static class MyErrorHandler implements ErrorHandler {
		private PrintStream out;

		MyErrorHandler(PrintStream out) {
			this.out = out;
		}

		private String getParseExceptionInfo(SAXParseException spe) {
			String systemId = spe.getSystemId();

			if (systemId == null) {
				systemId = "null";
			}

			String info = "URI=" + systemId + " Line=" + spe.getLineNumber()
					+ ": " + spe.getMessage();

			return info;
		}

		public void warning(SAXParseException spe) throws SAXException {
			out.println("Warning: " + getParseExceptionInfo(spe));
		}

		public void error(SAXParseException spe) throws SAXException {
			String message = "Error: " + getParseExceptionInfo(spe);
			throw new SAXException(message);
		}

		public void fatalError(SAXParseException spe) throws SAXException {
			String message = "Fatal Error: " + getParseExceptionInfo(spe);
			throw new SAXException(message);
		}
	}
}
