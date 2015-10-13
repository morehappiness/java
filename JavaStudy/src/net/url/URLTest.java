package url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class URLTest {
	public static void main(String[] args) throws IOException {
		// open local file
		//URL url = new URL("file:/c:/data/test.txt");
		URL url = new URL("http://163.com");
		URLConnection conn = url.openConnection();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));) {
			String line = null;
			while (null != (line = reader.readLine())) {
				System.out.println(line);
			}
		}
		
		conn.setDoOutput(true); // post
		PrintWriter writer = new PrintWriter(conn.getOutputStream()); //application/x-www-form-urlencoded 
		
		writer.close();
	}
}
