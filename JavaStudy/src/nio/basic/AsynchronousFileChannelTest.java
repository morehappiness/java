package basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsynchronousFileChannelTest {

	public static void main(String[] args) throws IOException {
		read();
		read1();
		write();
		write1();
	}

	private static void read1() throws IOException {
		Path path = Paths.get("data/nio/test.xml");
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
				path, StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate(256);

		fileChannel.read(buffer, 0, buffer,
				new CompletionHandler<Integer, ByteBuffer>() {
					@Override
					public void completed(Integer result, ByteBuffer attachment) {
						System.out.println("result = " + result);

						attachment.flip();
						byte[] data = new byte[attachment.limit()];
						attachment.get(data);
						System.out.println(new String(data));
						attachment.clear();
					}

					@Override
					public void failed(Throwable exc, ByteBuffer attachment) {
						System.out.println("result = " + exc);
					}
				});
	}

	private static void read() throws IOException {
		Path path = Paths.get("data/nio/test.xml");
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
				path, StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate(256);
		Future<Integer> operation = fileChannel.read(buffer, 0);

		while (!operation.isDone())
			;

		buffer.flip();
		byte[] data = new byte[buffer.limit()];
		buffer.get(data);
		System.out.println(new String(data));
		buffer.clear();
	}

	private static void write1() throws IOException {
		Path path = Paths.get("data/nio/test-write.txt");
		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
				path, StandardOpenOption.WRITE);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		buffer.put("test data".getBytes());
		buffer.flip();

		fileChannel.write(buffer, position, buffer,
				new CompletionHandler<Integer, ByteBuffer>() {

					@Override
					public void completed(Integer result, ByteBuffer attachment) {
						System.out.println("bytes written: " + result);
					}

					@Override
					public void failed(Throwable exc, ByteBuffer attachment) {
						System.out.println("Write failed");
						exc.printStackTrace();
					}
				});
	}

	private static void write() throws IOException {
		Path path = Paths.get("data/nio/test-write.txt");
		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(
				path, StandardOpenOption.WRITE);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		buffer.put("test data".getBytes());
		buffer.flip();

		Future<Integer> operation = fileChannel.write(buffer, position);
		buffer.clear();

		while (!operation.isDone())
			;

		System.out.println("Write done");
	}

}
