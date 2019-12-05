package zjq.io.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * socketServer
 * 
 * @author zhangjingqi
 * @date 2019年12月5日下午1:48:26
 *
 **/
public class SocketServer {

	private int port;

	private static ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue<Runnable>(4096));

	public SocketServer(int port) {
		this.port = port;
	}

	public void init() {
		System.out.println("socketServer starting");
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(this.port);
			System.out.println("socketServer start success");
			while (true) {
				Socket socket = serverSocket.accept();
				executorService.submit(new SocketServerHandler(socket));
			}
		} catch (IOException e) {
			System.out.println("socketServer start error" + e);
		}
	}

	private class SocketServerHandler implements Runnable {

		private Socket socket;

		public SocketServerHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			System.out.println("socketServer accept socketClient");
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				String str = null;
				while ((str = reader.readLine()) != null) {
					System.out.println("socketServer accpect : " + str);
				}
			} catch (Exception e) {
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		SocketServer socketServer = new SocketServer(8088);
		socketServer.init();
	}

}
