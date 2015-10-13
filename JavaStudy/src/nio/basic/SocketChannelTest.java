package basic;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class SocketChannelTest {

	public static void main(String[] args) throws IOException {
		SocketChannel channel = SocketChannel.open();
		channel.connect(new InetSocketAddress("localhost", 8080));

		RandomAccessFile toFile = new RandomAccessFile("data/nio/toFile.txt",
				"rw");
		FileChannel fileChannel = toFile.getChannel();

		String newData = "New String to write to file..." + System.currentTimeMillis() + System.lineSeparator(); // must add line separator
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.clear();
		buf.put(newData.getBytes());
		buf.flip();
		while(buf.hasRemaining()) {
		    channel.write(buf);
		}
		
		buf.clear();
		int bytesRead = channel.read(buf);
		while (-1 != bytesRead) {
			buf.flip();

			fileChannel.write(buf);

			buf.clear();
			bytesRead = channel.read(buf);
		}

		channel.close();
		toFile.close();
	}

}
