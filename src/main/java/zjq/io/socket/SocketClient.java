package zjq.io.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * socketClient
 * 
 * @author zhangjingqi
 * @date 2019年12月5日下午1:48:17
 *
 **/
public class SocketClient {

	private String host;

	private int port;

	public SocketClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() {
		System.out.println("socketClient starting");
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			System.out.println("socketClient start success");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while (true) {
				bufferedWriter.write(bufferedReader.readLine());
				bufferedWriter.write("\n");
				bufferedWriter.flush();
			}
		} catch (Exception e) {
			System.out.println("socketClient start error" + e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void main(String[] args) {
		SocketClient socketClient = new SocketClient("localhost", 8088);
		socketClient.start();
	}

}
