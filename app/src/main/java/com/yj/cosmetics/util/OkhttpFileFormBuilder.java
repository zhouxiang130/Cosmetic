package com.yj.cosmetics.util;

import com.zhy.http.okhttp.builder.HasParamsable;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.request.PostFormRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Suo on 2017/9/21.
 */

public class OkhttpFileFormBuilder extends OkHttpRequestBuilder implements HasParamsable {

    private List<PostFormBuilder.FileInput> files = new ArrayList();

    public OkhttpFileFormBuilder() {
    }

    public RequestCall build() {
        return (new PostFormRequest(this.url, this.tag, this.params, this.headers, this.files)).build();
    }

    public OkhttpFileFormBuilder addFile(String name, String filename, File file) {
        this.files.add(new PostFormBuilder.FileInput(name, filename, file));
        return this;
    }

    public OkhttpFileFormBuilder url(String url) {
        this.url = url;
        return this;
    }

    public OkhttpFileFormBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public OkhttpFileFormBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public OkhttpFileFormBuilder addParams(String key, String val) {
        if(this.params == null) {
            this.params = new LinkedHashMap();
        }

        this.params.put(key, val);
        return this;
    }

    public OkhttpFileFormBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public OkhttpFileFormBuilder addHeader(String key, String val) {
        if(this.headers == null) {
            this.headers = new LinkedHashMap();
        }

        this.headers.put(key, val);
        return this;
    }

    public OkhttpFileFormBuilder addFiles(String name, Map<String, File> fileMap) {
        Iterator i$ = fileMap.keySet().iterator();

        while(i$.hasNext()) {
            String fileName = (String)i$.next();
            this.files.add(new PostFormBuilder.FileInput(name, fileName, (File)fileMap.get(fileName)));
        }

        return this;
    }
    public OkhttpFileFormBuilder addJudgeFiles(String name, Map<String,ArrayList<File>> fileMap) {
        Iterator i$ = fileMap.keySet().iterator();

        while(i$.hasNext()) {
            String pid = (String)i$.next();
            for(int i = 0;i<fileMap.get(pid).size();i++){
                String fileName = System.currentTimeMillis()+fileMap.get(pid).get(i).getName();
                LogUtils.e("fileName的值===="+fileName+"============路径是=="+fileMap.get(pid).get(i));
                this.files.add(new PostFormBuilder.FileInput(name,pid,fileMap.get(pid).get(i)));
            }
        }
        /*for (int i = 0;i<files.size();i++) {
            LogUtils.i("files的值" + this.files.size() + "....." + this.files.get(i).toString());
        }*/
        return this;
    }

    public static class FileInput {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        public String toString() {
            return "FileInput{key=\'" + this.key + '\'' + ", filename=\'" + this.filename + '\'' + ", file=" + this.file + '}';
        }
    }
}
