package zjq.io.http;

import java.io.IOException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * httpClient调用
 * 
 * @author:zhangjq
 * @time:2017年5月19日 下午5:39:48
 * @version:v1.0
 */
public class HttpClientUtil {

	private static CloseableHttpClient client = null;

	private static HttpPost httpPost = null;

	private static HttpGet httpGet = null;

	private static HttpResponse response = null;

	private static HttpEntity entity = null;

	public static String post(String url, List<NameValuePair> list) {
		String result = null;
		client = HttpClients.createDefault();
		httpPost = new HttpPost(url);
		httpPost.addHeader("User-Agent", "Mozilla/5.0");
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			response = client.execute(httpPost);
			entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (client != null)
					client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static String get(String url) {
		String result = null;
		client = HttpClients.createDefault();
		httpGet = new HttpGet(url);
		try {
			response = client.execute(httpGet);
			entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (client != null)
					client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String postJson(String url, String json) {
		String result = null;
		client = HttpClients.createDefault();
		httpPost = new HttpPost(url);
		httpPost.addHeader("User-Agent", "Mozilla/5.0");
		try {
			httpPost.setEntity(new StringEntity(json, "utf-8"));
			response = client.execute(httpPost);
			entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (client != null)
					client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static boolean get2(String url) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
//		List<NameValuePair> list = new ArrayList<NameValuePair>();
//		list.add(new BasicNameValuePair("version", "1.0"));
//		list.add(new BasicNameValuePair("service", "test.test"));
//		list.add(new BasicNameValuePair("format", "JSON"));
//		list.add(new BasicNameValuePair("timestamp", "2019-01-01 00:00:00"));
//		list.add(new BasicNameValuePair("terminal_type", "web"));
//		list.add(new BasicNameValuePair("terminal_info", "web"));
//		list.add(new BasicNameValuePair("sign_type", "RSA"));
//		list.add(new BasicNameValuePair("sign", ""));
//		String response = post("https://127.0.0.1:8080/test/test.htm", list);
//		System.out.println(response);
		get2("https://www.baidu.com/");
	}
}
