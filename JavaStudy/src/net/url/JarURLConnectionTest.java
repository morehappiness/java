package url;

import java.net.InetAddress;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class JarURLConnectionTest {

	public static void main(String[] args) throws Exception {

		System.out.println(InetAddress.getByName("jenkov.com").getHostAddress());
		System.out.println(InetAddress.getByName("78.46.84.171").getHostName());
		System.out.println(InetAddress.getLocalHost());
		
		//jar:&lt;url&gt;!/{entry} 
		String urlString = "jar:http://butterfly.jenkov.com/"
				+ "container/download/"
				+ "jenkov-butterfly-container-2.9.9-beta.jar!/";

		URL jarUrl = new URL(urlString);
		JarURLConnection connection = (JarURLConnection) jarUrl.openConnection();

		Manifest manifest = connection.getManifest();

		JarFile jarFile = connection.getJarFile();
		
	}

}
