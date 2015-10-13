package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					receive(); // must start a new thread
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		}).start();
		
		send();
	}

	public static void send() throws IOException {
		DatagramSocket datagramSocket = new DatagramSocket();

		byte[] buffer = "test udp".getBytes();
		InetAddress receiverAddress = InetAddress.getLocalHost();

		DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
				receiverAddress, 8800);
		datagramSocket.send(packet); // not block
		datagramSocket.close();
	}

	public static void receive() throws IOException {
		DatagramSocket datagramSocket = new DatagramSocket(8800);

		byte[] buffer = new byte[10];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

		datagramSocket.receive(packet); // it will block until data received
		
		System.out.println(new String(packet.getData()));

		datagramSocket.close();
	}
}
