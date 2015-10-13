package basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelTest {

	public static void main(String[] args) throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress(9999));
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		channel.receive(buf);

		String newData = "New String to write to file..." + System.currentTimeMillis();

		buf.clear();
		buf.put(newData.getBytes());
		buf.flip();

		int bytesSent = channel.send(buf, new InetSocketAddress("jenkov.com", 80));
	}

}
