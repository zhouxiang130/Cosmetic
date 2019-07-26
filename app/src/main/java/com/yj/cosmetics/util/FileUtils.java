package com.yj.cosmetics.util;

import android.os.Environment;
import android.util.Log;

import com.yj.cosmetics.MyApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/8/29 0029.
 */

public class FileUtils {


	private static final String ROOT = "wendi";
	public static final String CACHE = "cache";
	private static final String TAG = "FileUtils";

	public static File getDir(String dir) {
		// sd   不存在   /data/data/包名/cache/cache
		// /mnt/sdcard/wendi/cache
		StringBuilder path = new StringBuilder();

		if (sdAvailable()) {
//        path.
			path.append(MyApplication.getApplication().getExternalCacheDir().getPath());//   /mnt/sdcard
			//  linux  /       windows \
//			path.append(File.separator);
//			path.append(ROOT);
//			path.append(File.separator);
//			path.append(dir);
		} else {
			path.append(MyApplication.getApplication().getExternalCacheDir().getPath());  //   /data/data/包名/cache
//			path.append(File.separator);
//			path.append(dir);
		}
		File file = new File(path.toString());
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		return file;

	}


	/**
	 * 缓存数据到本地
	 *
	 * @param
	 * @param json
	 */
	public static void save2Local(int index, String json) {
		// 1. json  过期时间
		//  2. db
		//  md5
		// 缓存数据    --- file
		File dir = FileUtils.getCache();
		File file = new File(dir, "home_" + index);

		// 通过流写到文件中
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file);
			//第一行添加过期时间
			bw = new BufferedWriter(fw);
			bw.write(System.currentTimeMillis() + 100 * 1000 + "");
//            fw.write("\r\n");
			bw.newLine();//换行
			bw.write(json);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关流 释放对象
			//关流 释放对象
			IOUtils.close(bw);
			IOUtils.close(fw);
		}
	}


	/**
	 * 从本地获取缓存数据
	 *
	 * @return
	 */
	public static String loadFromLocal(int index) {

		//     当前时间  >  过期时间   过期    不再 获取
		//     当前时间  < 过期时间   没有过期   获取本地数据
		File dir = FileUtils.getCache();
		File file = new File(dir, "home_" + index);


		FileReader fr;
		BufferedReader br;
		StringWriter sw;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			long outofDate = Long.parseLong(br.readLine());
			Log.i(TAG, "loadFromLocal: " + System.currentTimeMillis() + ">>>>" + outofDate);
//			if (System.currentTimeMillis() > outofDate) {
//				//  过期
//				return null;
//			} else {
			// 字节流 写到内存中
//                ByteArrayOutputStream
			// 将字符串写到内存中
//                StringWriter  sw;
			sw = new StringWriter();
			String line;
			while ((line = br.readLine()) != null) {
				//  将读到的数据写到内存中
				sw.write(line);
			}
			// 将内存中的字符串 返回
			return sw.toString();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		return null;
	}


	/**
	 * sd 卡是否可用
	 *
	 * @return
	 */
	private static boolean sdAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

	}

	public static File getCache() {
		// alt + ctrl + C    Constant
		return getDir(CACHE);
	}



	/**
	 * 获取指定文件大小
	 *
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
			Log.e("获取文件大小", "文件不存在!");
		}
		return size;
	}


	/**
	 * 获取指定文件夹
	 *
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSizes(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSizes(flist[i]);
			} else {
				size = size + getFileSize(flist[i]);
			}
		}
		return size;


	}



	//flie：要删除的文件夹的所在位置
	public static void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				deleteFile(f);
			}
//			file.delete();//如要保留文件夹，只删除文件，请注释这行
		} else if (file.exists()) {
			file.delete();
		}
	}



	/**
	 * 转换文件大小
	 *
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		String wrongSize = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}


}
