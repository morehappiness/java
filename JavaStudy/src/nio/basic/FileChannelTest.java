package basic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {

	public static void main(String[] args) throws IOException {
		RandomAccessFile aFile = new RandomAccessFile("data/nio/nio-data.txt",
				"rw");
		FileChannel inChannel = aFile.getChannel();

		// create buffer with capacity of 48 bytes
		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = inChannel.read(buf); // read into buffer.
		while (bytesRead != -1) {

			buf.flip(); // make buffer ready for read

			while (buf.hasRemaining()) {
				System.out.print((char) buf.get()); // read 1 byte at a time
			}

			buf.clear(); // make buffer ready for writing
			bytesRead = inChannel.read(buf);
		}
		aFile.close();

		RandomAccessFile fromFile = new RandomAccessFile(
				"data/nio/fromFile.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();

		RandomAccessFile toFile = new RandomAccessFile("data/nio/toFile.txt",
				"rw");
		FileChannel toChannel = toFile.getChannel();

		long position = 0;
		long count = fromChannel.size();
		System.out.println(fromChannel.position());

		fromChannel.transferTo(position, count, toChannel);

		System.out.println(fromChannel.position()); // should not change

		fromFile.close();
		toFile.close();
		
		write();
	}

	private static void write() throws IOException {
		RandomAccessFile toFile = new RandomAccessFile("data/nio/toFile.txt",
				"rw");
		FileChannel channel = toFile.getChannel();

		String newData = "New String to write to file..."
				+ System.currentTimeMillis();

		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(newData.getBytes());

		buf.flip(); // turn into read mode

		while (buf.hasRemaining()) {
			channel.write(buf);
		}
		
		toFile.close(); // it also close the channel
	}

}
