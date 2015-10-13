package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("localhost", 8080);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println("test");

		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		System.out.println(in.readLine()); // 必须输出换行，否则不会输出任何数据给客户

		socket.close();
	}

}
