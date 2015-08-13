package com.sctbc.googleplay.protocol;

import android.os.SystemClock;

import com.lidroid.xutils.util.IOUtils;
import com.sctbc.googleplay.http.HttpHelper;
import com.sctbc.googleplay.tools.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter;

/**
 * 作者：ZYJ
 * 时间：2015/8/5 0005 09:31
 */
public abstract class BaseProtocol<T> {
    public T load(int index) {
        SystemClock.sleep(1000);
        //加载本地数据
        String json = loadLocal(index);
        if (json == null) {
            //请求服务器
            json = loadServer(index);
            if (json != null) {
                saveLocal(json, index);
            }
        }
        if (json != null) {
            return parseJson(json);
        }else{
            return null;
        }
    }
    private String loadServer(int index) {
        HttpHelper.HttpResult httpResult=HttpHelper.get(HttpHelper.URL + getKey()
                + "?index=" + index + getParams());
        if(httpResult!=null) {
            String json = httpResult.getString();
            return json;
        }else{
            return null;
        }
    }

    /**
     * 额外带的参数
     * @return
     */
    protected String getParams() {
        return "";
    }

    private void saveLocal(String json, int index) {
        File dir= FileUtils.getCacheDir();
        BufferedWriter bw = null;
        try{
            File file=new File(dir,getKey()+"_"+index+getParams());
            FileWriter fw=new FileWriter(file);
            bw=new BufferedWriter(fw);
            //在第一行写一个过期时间
            bw.write(System.currentTimeMillis()+1000*100+"");
            bw.newLine();
            bw.write(json);
            bw.flush();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(bw);
        }

    }
    private String loadLocal(int index) {
        //如果发现文件已经过期了，就不需要复用缓存了
        File dir= FileUtils.getCacheDir();//获取缓存所在的文件夹
        File file=new File(dir,getKey()+"_"+index+getParams());

        try {
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            long outofDate=Long.parseLong(br.readLine());
            if(System.currentTimeMillis()>outofDate){
                return null;
            }else{
                String str;
                StringWriter sw=new StringWriter();
                while ((str=br.readLine())!=null){
                    sw.write(str);
                }
                return sw.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析json
     * @param json
     * @return
     */
    public abstract T parseJson(String json);
    /**
     * 说明关键字
     * @return
     */
    public abstract String getKey();
}
