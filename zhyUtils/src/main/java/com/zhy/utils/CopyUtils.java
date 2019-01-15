package com.zhy.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CopyUtils {
	private CopyUtils(){};
	
	private static ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
	
	public static void main(String[] args){
		try {
			System.out.println("============请输入文件路径==============");
			Scanner scan = new Scanner(System.in);
			String filePath = scan.nextLine();
			scan.close();
			copyFile(new File(filePath));
			
			threadPool.shutdown();  // 启动一次顺序关闭，执行以前提交的任务，但不接受新任务
			
			// 请求关闭、发生超时或者当前线程中断，无论哪一个首先发生之后，都将导致阻塞，直到所有任务完成执行
		        // 设置最长等待60秒
			threadPool.awaitTermination(60, TimeUnit.SECONDS);
			
			System.out.println("======== |<<<< 复制完成 >>>>| ==========");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void copyFile(File file) throws Exception {
		if(file.exists()){
			if(file.isDirectory()){
				File[] list = file.listFiles();				
				for (File f : list) {
					if (f.isDirectory()) {
						if(f.getName().equals(".svn") || f.getName().equals("target")){
							continue;
						}
						copyFile(f);
					}else {
						threadPool.execute(new ExcuteCopy(f));
					}
				}
			}else {
				throw new RuntimeException("CopyUtils copyFile method params are not a directory!!");
			}
		}else {
			throw new RuntimeException("Directory or file does not exist!!");
		}
	}
	
	public static class ExcuteCopy implements Runnable{
		private File f;
		public ExcuteCopy(File file) {
			this.f = file;
		}
		@Override
		public void run() {
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				String newdir = f.getParent().replace(f.getParent().substring(0, 1), "F"); //保存到F盘
				File newDir = new File(newdir);
				if(!newDir.exists()){
					newDir.mkdirs();
				}
				File newFile = null;
				if(f.getName().endsWith(".java")){
					newFile = new File(newdir+File.separator+f.getName().replace("java", "xyz"));
				}else {
					newFile = new File(newdir+File.separator+f.getName());
				}
				bis = new BufferedInputStream(new FileInputStream(f));
				bos = new BufferedOutputStream(new FileOutputStream(newFile));
				byte[] bytes = new byte[1024*5];
				int i;
				while ((i=bis.read(bytes)) != -1) {
					bos.write(bytes, 0, i);
				}
				System.out.println(f.getAbsolutePath()); 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					bis.close();
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
