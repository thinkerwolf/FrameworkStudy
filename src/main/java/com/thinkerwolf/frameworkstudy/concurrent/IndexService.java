package com.thinkerwolf.frameworkstudy.concurrent;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IndexService {
	private static final File POSION = new File("");
	private final CrawlerThread producer = new CrawlerThread();
	private final IndexingThread consumer = new IndexingThread();
	private final BlockingQueue<File> queue = new LinkedBlockingQueue<>(2);
	private File root;
	private FileFilter fileFilter;

	public IndexService(File root, FileFilter fileFilter) {
		this.root = root;
		this.fileFilter = fileFilter;
	}

	public void start() {
		producer.start();
		consumer.start();
	}

	public void stop() {
		producer.interrupt();
	}

	public void awitTerminate() throws InterruptedException {
		consumer.join();
	}

	private class CrawlerThread extends Thread {
		@Override
		public void run() {
			try {
				crawl(root);
			} catch (InterruptedException e) {

			} finally {
				while (true) {
					try {
						queue.put(POSION);
						break;
					} catch (InterruptedException e) {

					}
				}
			}
		}

		private void crawl(File root) throws InterruptedException {
			if (!root.exists()) {
				return;
			}
			if (root.isFile()) {
				queue.put(root);
				return;
			}
			if (root.isDirectory()) {
				File[] files = root.listFiles(fileFilter);
				for (File file : files) {
					crawl(file);
				}
			}
		}
	}

	private class IndexingThread extends Thread {
		private int num;
		@Override
		public void run() {
			try {
				while (true) {
					File file = queue.take();
					if (file == POSION) {
						break;
					}
					System.out.println("File name : " + file.getName());
					num ++;
				}
			} catch (InterruptedException e) {
			} finally {
				System.out.println("Total index num : " + num);
			}
			
		}
	}
	
	public static void main(String[] args) {
		File root = new File("D:/hadoop-2.7.5");
		IndexService indexService = new IndexService(root, new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return true;
			}
		});
		indexService.start();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		indexService.stop();
//		try {
//			indexService.awitTerminate();
//		} catch (InterruptedException e) {
//		}
		
		
	}

}
