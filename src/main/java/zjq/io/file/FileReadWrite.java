package zjq.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件复制
 * 
 * @author zhangjingqi
 * @date 2019年12月4日下午3:27:10
 *
 **/
public class FileReadWrite {

	private FileReadWrite() {
	}

	/**
	 * 获取路径下所有的文件
	 * 
	 * @param filepath
	 * @param list
	 * @return
	 */
	public static List<File> get(String filepath, List<File> list) {
		File file = new File(filepath);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					get(f.getAbsolutePath(), list);
				} else {
					list.add(f);
				}
			}
		}
		return list;
	}

	/**
	 * 复制文件 FileInputStream/FileOutputStream
	 * 
	 * @param readPath
	 * @param writePath
	 */
	public static void copyFileByStream(String readPath, String writePath) {
		try (FileInputStream in = new FileInputStream(new File(readPath));
				FileOutputStream out = new FileOutputStream(new File(writePath));) {
			int length = 0;
			/** 写byte[]方式 **/
			byte[] bt = new byte[1024];
			while ((length = in.read(bt)) != -1) {
				out.write(bt, 0, length);
				// out.write(bt);//也可以
				out.flush();
			}
			/** 写int方式 **/
//			while ((length = in.read()) != -1) {
//				out.write(length);
//				out.flush();
//			}
		} catch (Exception e) {
		}
	}

	/**
	 * 文件复制 FileReader/FileWriter
	 * 
	 * @param readPath
	 * @param writePath
	 */
	public static void copyFileByReaderWriter(String readPath, String writePath) {
		try (FileReader reader = new FileReader(new File(readPath));
				FileWriter writer = new FileWriter(new File(writePath));) {
			int len;
			char[] ch = new char[1024];
			/** 写char[]方式 **/
			while ((len = reader.read(ch)) != -1) {
				writer.write(ch, 0, len);
				// writer.write(ch);//也可以
				writer.flush();
			}
			/** 写int方式 **/
//			while((len = reader.read()) != -1) {
//				writer.write(len);
//			}
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		copyFileByStream("/Users/idealife/zjq/read.txt", "/Users/idealife/zjq/write.txt");
		copyFileByReaderWriter("/Users/idealife/zjq/read2.txt", "/Users/idealife/zjq/write2.txt");
		System.out.println(get("/Users/idealife/zjq", new ArrayList<File>()));
	}

}
