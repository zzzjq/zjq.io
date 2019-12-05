package zjq.io.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author:zhangjq
 * @time:2017年6月26日 下午5:50:27
 * @version:v1.0
 */
public class HttpClientTest {

	/**
	 * POST请求
	 * 
	 * @param name
	 * @param age
	 * @return
	 */
	public static String postRequest(String path, byte[] bt) {
		HttpURLConnection connection = null;
		DataOutputStream out = null;
		StringBuffer buf = new StringBuffer();
		try {
			// 建立连接
			URL url = new URL(path);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();

			out = new DataOutputStream(connection.getOutputStream());
			out.write(bt);
			out.flush();

			// 接受返回值
			BufferedReader read = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			while ((line = read.readLine()) != null) {
				buf.append(line);
			}
			read.close();
		} catch (Exception e) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {

			}
			connection.disconnect();
		}
		return buf.toString();
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 * @return
	 */
	public static String getRequest(String url) {
		StringBuffer buf = new StringBuffer();
		try {
			File file = new File("/Users/idealife/1.txt");
			FileOutputStream out = new FileOutputStream(file);
			// 建立连接
			URL getUrl = new URL(url);
			URLConnection connection = getUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			// 获取数据
			BufferedReader read = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = read.readLine()) != null) {
				buf.append(line);
				out.write(line.getBytes());
			}
			read.close();
			out.close();
		} catch (Exception e) {
		} finally {
		}

		return buf.toString();
	}

}
