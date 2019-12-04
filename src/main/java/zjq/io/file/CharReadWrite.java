package zjq.io.file;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Base64;

/**
 * 字符串读写
 * 
 * @author zhangjingqi
 * @date 2019年12月4日下午2:47:14
 *
 **/
public class CharReadWrite {

	private CharReadWrite() {
	}

	public static String read(String filePath) {
		if (null == filePath || filePath.trim().isEmpty())
			return null;
		try (FileInputStream in = new FileInputStream(filePath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
			StringBuilder sb = new StringBuilder();
			String str = null;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
			// 单个读取
			// int len;
			// while((len = reader.read()) != -1) {
			// System.out.println((char)len);
			// }
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static String readString(String msg) {
		if (null == msg || msg.isEmpty())
			return null;
		byte[] bt = Base64.getDecoder().decode(msg);
		try (ByteArrayInputStream in = new ByteArrayInputStream(bt);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
			StringBuilder sb = new StringBuilder();
			String str = null;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
				sb.append(System.getProperty("line.separator"));
			}
			// 单个读取
			// int len;
			// while((len = reader.read()) != -1) {
			// System.out.println((char)len);
			// }
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

}
