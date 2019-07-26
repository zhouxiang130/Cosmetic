package com.yj.cosmetics.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class InstallUtil {


	private static final String TAG = "InstallUtil";

	public static  Intent startInstallAct(Context context, File downloadfile ){
		Intent install = new Intent(Intent.ACTION_VIEW);
		install.addCategory(Intent.CATEGORY_DEFAULT);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
			Uri contentUri = FileProvider.getUriForFile(context, "com.yj.robust.file_provider", downloadfile);
			Log.e(TAG, "initAPKDir run:>>>" + contentUri);
			install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			install.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
			install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			install.setDataAndType(Uri.fromFile(downloadfile), "application/vnd.android.package-archive");
		}
		return install;
	}

}
