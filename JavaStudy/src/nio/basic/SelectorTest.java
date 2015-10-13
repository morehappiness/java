package basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();

		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		SelectionKey key = channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ
				| SelectionKey.OP_WRITE);
		channel.connect(new InetSocketAddress("localhost", 8080));

		int interestSet = key.interestOps();

		boolean isInterestedInAccept = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
		boolean isInterestedInConnect = (interestSet & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT;
		boolean isInterestedInRead = (interestSet & SelectionKey.OP_READ) == SelectionKey.OP_READ;
		boolean isInterestedInWrite = (interestSet & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE;
		System.out.format("%s: %b%n", "Interested in Accept", isInterestedInAccept);
		System.out.format("%s: %b%n", "Interested in Connect", isInterestedInConnect);
		System.out.format("%s: %b%n", "Interested in Read", isInterestedInRead);
		System.out.format("%s: %b%n", "Interested in Write", isInterestedInWrite);

		//int readySet = key.readyOps();
		System.out.format("%s: %b%n", "Acceptable?", key.isAcceptable());
		System.out.format("%s: %b%n", "readable?", key.isReadable());
		System.out.format("%s: %b%n", "writable?", key.isWritable());
		System.out.format("%s: %b%n", "Connectable?", key.isConnectable());

		//Channel channel1 = key.channel();
		//Selector selector1 = key.selector();

		//key.attach(new Object());
		//Object attachedObj = key.attachment();

		while (true) {
			int readyChannels = selector.select();
			if (readyChannels == 0)
				continue;
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
			while (keyIterator.hasNext()) {
				key = keyIterator.next();
				if (key.isConnectable()) {
					// a connection was established with a remote server.
					System.out.format("%s: %b%n", "Connectable?", key.isConnectable());
				} else if (key.isReadable()) {
					// a channel is ready for reading
					System.out.format("%s: %b%n", "readable?", key.isReadable());
				} else if (key.isWritable()) {
					// a channel is ready for writing
					System.out.format("%s: %b%n", "writable?", key.isWritable());
				}
				keyIterator.remove();
			}
		}
		
	}

}
