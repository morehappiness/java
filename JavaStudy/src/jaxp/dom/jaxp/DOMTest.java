package dom.jaxp;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DOMTest {

	public static void main(String[] args) throws SAXException, IOException,
			ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		// dbf.setNamespaceAware(true);
		// dbf.setValidating(true);
		DocumentBuilder db = dbf.newDocumentBuilder();

		OutputStreamWriter errorWriter = new OutputStreamWriter(System.err,
				"UTF-8");
		db.setErrorHandler(new MyErrorHandler(
				new PrintWriter(errorWriter, true)));
		Document doc = db.parse(new File("resources/dom_example.xml"));
		Element node = doc.getDocumentElement();
		traverse(node, 0);
		traverse1(node, 0);
	}

	private static void traverse(Node node, int deep) {
		if (null == node) {
			return;
		}
		Node sibling = node;
		while (null != sibling) {
			if (Node.ELEMENT_NODE == sibling.getNodeType()) {
				printNode(sibling, deep);
				traverse(sibling.getFirstChild(), deep + 1);
			}
			sibling = sibling.getNextSibling();
		}
	}

	private static void traverse1(Node node, int deep) {
		if (node instanceof Element) {
			printNode(node, deep);
		}
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node child = list.item(i);
			traverse1(child, deep + 1);
		}
	}

	private static void printNode(Node node, int deep) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < deep; i++) {
			sb.append("-");
		}
		sb.append(node.getNodeName());
		System.out.println(sb);
	}

	private static class MyErrorHandler implements ErrorHandler {

		private PrintWriter out;

		MyErrorHandler(PrintWriter out) {
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
