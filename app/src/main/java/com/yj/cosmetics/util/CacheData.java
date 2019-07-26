package com.yj.cosmetics.util;

import android.os.Environment;

import com.yj.cosmetics.MyApplication;
import com.yj.cosmetics.model.HomeListEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/28 0028.
 */

public class CacheData {

	public static final int recent_subject_list = 8;
	private static final String TAG = " CacheData ";
	private static final String ROOT = "wendi";
	public static final String CACHE = "cache";

	/**
	 * 获取文件名
	 *
	 * @return
	 */
	public static String getFileNameById() {
		StringBuilder path = new StringBuilder();
		if (sdAvailable()) {
			path.append(Environment.getExternalStorageDirectory().getAbsolutePath());//   /mnt/sdcard
			path.append(File.separator);
			path.append(ROOT);
			path.append(File.separator);
			path.append(CACHE);
		} else {
			path.append(MyApplication.getApplication().getCacheDir().getAbsolutePath());  //   /data/data/包名/cache
			path.append(File.separator);
			path.append(CACHE);
		}

		return path.toString();
	}


	/**
	 * sd 卡是否可用
	 *
	 * @return
	 */
	private static boolean sdAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

	}


	/**
	 * 存储缓存文件：
	 */
	public static void saveRecentSubList(String uid, String studentId, List<HomeListEntity.HomeListData.HomeListItem> list) {

		String fileName = getFileNameById();
		
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
			oos.writeObject(list);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取缓存文件：
	 */

	public static List<HomeListEntity.HomeListData.HomeListItem> getRecentSubList(String uid, String studentId) {
		int type = recent_subject_list;
		List<HomeListEntity.HomeListData.HomeListItem> resultList = new ArrayList<>();
		String fileName = getFileNameById();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
			ArrayList<HomeListEntity.HomeListData.HomeListItem> list_ext = (ArrayList<HomeListEntity.HomeListData.HomeListItem>) ois.readObject();

			for (HomeListEntity.HomeListData.HomeListItem obj : list_ext) {
				HomeListEntity.HomeListData.HomeListItem bean = obj;
				if (bean != null) {
					resultList.add(bean);
				}
			}
			ois.close();
		} catch (Exception e) {
			return resultList;
		}
		return resultList;
	}
}



