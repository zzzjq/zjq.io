package zjq.io.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @author zhangjingqi
 * @date 2019年12月4日下午2:27:17
 *
 **/
public class UrlReadWrite {

	private UrlReadWrite() {

	}

	public static String read(String address) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			URL url = new URL(address);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream in = connection.getInputStream();
			int len;
			byte[] bt = new byte[1024];
			while ((len = in.read(bt)) != -1) {
				out.write(bt, 0, len);
				out.flush();
			}
			return new String(out.toByteArray(), "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	public static String readString(String address) {
		try {
			StringBuilder sb = new StringBuilder();
			String str = null;
			URL url = new URL(address);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

}
