package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8080);
		Socket clientSocket = serverSocket.accept();

		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			out.println("From Server: " + line); // 必须输出换行，否则不会输出任何数据给客户
		}
		clientSocket.close();

		serverSocket.close();
	}

}
