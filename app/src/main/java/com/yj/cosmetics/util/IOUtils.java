package com.yj.cosmetics.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * ====================
 * 版权所有 违法必究
 *
 * @author wxj
 * @project GooglePlayz13
 * @file IOUtils
 * @create_time 14:53:35
 * @github https://github.com/wangxujie
 * @blog http://wangxujie.github.io
 */
public class IOUtils {
    /**
     * 释放资源
     * @param closeable
     */
    public static void close(Closeable closeable){
        if (closeable!= null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeAll(Closeable... closeables){
        if(closeables == null){
            return;
        }
        for (Closeable closeable : closeables) {
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
