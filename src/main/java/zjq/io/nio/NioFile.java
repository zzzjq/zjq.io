package zjq.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * @author zhangjingqi
 * @date 2019年12月4日下午4:52:14
 *
 **/
public class NioFile {

	private static final int CAPACITY = 1024;

	private static RandomAccessFile aFile = null;

	private static FileChannel channel = null;

	private static final ByteBuffer buf = ByteBuffer.allocate(CAPACITY);

	/**
	 * 文件1复制到文件2
	 * 
	 * @param file1
	 * @param file2
	 */
	public static void copyFile(String path1, String path2) {
		File file1 = null;
		File file2 = null;
		FileInputStream in = null;
		FileOutputStream out = null;
		FileChannel inFc = null;
		FileChannel outFc = null;
		try {
			file1 = new File(path1);
			file2 = new File(path2);
			in = new FileInputStream(file1);
			out = new FileOutputStream(file2);
			inFc = in.getChannel();
			outFc = out.getChannel();
			inFc.transferTo(0, inFc.size(), outFc);
		} catch (IOException e) {
		} finally {
			try {
				inFc.close();
				outFc.close();
				in.close();
				out.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 读取文件
	 * 
	 * @param path
	 */
	public static void readFile(String path) {
		try {
			aFile = new RandomAccessFile(path, "rw");
			channel = aFile.getChannel();

			int len;
			while ((len = channel.read(buf)) != -1) {
				System.out.println("Read:" + buf);
				buf.flip();
				while (buf.hasRemaining())
					System.out.print((char) buf.get());
				buf.clear();
			}
		} catch (Exception e) {

		} finally {
			try {
				channel.close();
				aFile.close();
			} catch (IOException e2) {

			}
		}

	}

	/**
	 * 写文件
	 * 
	 * @param path
	 */
	public static void writeFile(String path) {
		try {
			aFile = new RandomAccessFile(path, "rw");
			channel = aFile.getChannel();
			buf.clear();
			buf.put("hello,I'm writing!!".getBytes());
			buf.flip();
			while (buf.hasRemaining())
				channel.write(buf);
		} catch (Exception e) {
		} finally {
			try {
				channel.close();
				aFile.close();
			} catch (IOException e2) {
			}
		}
	}

	public static void main(String[] args) throws InterruptedException, Exception {
		readFile("/Users/idealife/nio.txt");
	}

}
