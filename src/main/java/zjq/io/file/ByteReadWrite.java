package zjq.io.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

/**
 * 字节流读写
 * 
 * @author zhangjingqi
 * @date 2019年12月4日下午2:36:06
 *
 **/
public class ByteReadWrite {

	private ByteReadWrite() {
	}

	public static String read(String filePath) {
		if (null == filePath || filePath.trim().isEmpty())
			return null;
		try (FileInputStream in = new FileInputStream(filePath);
				ByteArrayOutputStream out = new ByteArrayOutputStream();) {
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
	
	public static String readString(String msg) {
		if (msg == null || msg.length() == 0)
			return null;
		byte[] b = Base64.getDecoder().decode(msg.getBytes());
		try (ByteArrayInputStream in = new ByteArrayInputStream(b)) {
			StringBuilder sb = new StringBuilder();
			byte[] buf = new byte[1024];
			while (in.read(buf) != -1) {
				sb.append(new String(buf));
			}
			//单个读取
//			int len;
//			while ((len = in.read()) != -1) {
//				System.out.println((char)len);
//			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 写文件，msg为base64后的。
	 * 
	 * @param filePath
	 * @param msg
	 */
	public static void write(String filePath, String msg) {
		try (FileOutputStream out = new FileOutputStream(filePath);
				ByteArrayInputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(msg));) {
			int len;
			byte[] bt = new byte[1024];
			while ((len = in.read(bt)) != -1) {
				out.write(bt, 0, len);
				out.flush();
			}
		} catch (Exception e) {
		}
	}

}
