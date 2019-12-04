package zjq.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 
 * @author zhangjingqi
 * @date 2019年12月4日下午4:51:40
 *
 **/
public class NioServer {

	private static ServerSocketChannel serverSocketChannel = null;

	private static SocketChannel socketChannel = null;

	private static Selector selector = null;

	private static ByteBuffer buf = ByteBuffer.allocate(1024);

	private static void handle(SelectionKey key) throws IOException {
		if (key.isValid()) {
			if (key.isAcceptable()) {
				serverSocketChannel = (ServerSocketChannel) key.channel();
				socketChannel = serverSocketChannel.accept();
				socketChannel.configureBlocking(false);
				socketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
				System.out.println("accepted connection" + socketChannel.socket().getRemoteSocketAddress());
			}

			if (key.isReadable()) {
				socketChannel = (SocketChannel) key.channel();
				int len;
				StringBuffer sb = new StringBuffer();
				while ((len = socketChannel.read(buf)) != -1) {
					buf.flip();
					while (buf.hasRemaining()) {
						sb.append(buf.get());
					}
					buf.clear();
				}
				System.out.println(sb.toString());
			}

			if (key.isWritable()) {
				socketChannel = (SocketChannel) key.channel();
			}

		}
	}

	public static void main(String[] args) {
		try {
			selector = Selector.open();

			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(8080));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			for (;;) {
				selector.select();
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					it.remove();
					try {
						handle(key);
					} catch (IOException e) {
					}
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				selector.close();
			} catch (IOException e) {
			}
		}
	}

}
