package zjq.io.file;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程不安全的写
 *
 * @author zhangjingqi
 * @date 2019年12月4日下午3:30:16
 *
 **/
public class UnsafeFile implements Runnable {

	/** 加锁，线程安全 **/
	private static ReentrantLock lock = new ReentrantLock();

	private String msg;

	public UnsafeFile(String msg) {
		this.msg = msg;
	}

	@Override
	public void run() {
		lock.lock();
		File file = new File("/Users/idealife/zjq/UnsafeWrite.txt");
		/** true 续写文件 **/
		try (FileOutputStream out = new FileOutputStream(file, true);) {
			out.write(msg.getBytes("UTF-8"));
			out.flush();
			System.out.println(Thread.currentThread().getName() + ", finish write.");
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static void main(String[] args) {
		UnsafeFile u = new UnsafeFile("123");
		UnsafeFile u_ = new UnsafeFile("456");
		ExecutorService e = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(2));
		try {
			e.submit(u);
			e.submit(u_);
		} finally {
			e.shutdown();
		}
	}

}
