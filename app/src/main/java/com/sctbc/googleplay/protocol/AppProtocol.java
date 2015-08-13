package com.sctbc.googleplay.protocol;

import com.sctbc.googleplay.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ZYJ
 * 时间：2015/8/6 0006 10:52
 */
public class AppProtocol extends BaseProtocol<List<AppInfo>> {

    @Override
    public List<AppInfo> parseJson(String json) {
        List<AppInfo> appInfos=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                long id=object.getLong("id");
                String name=object.getString("name");
                String packageName=object.getString("packageName");
                String iconUrl = object.getString("iconUrl");
                float stars=Float.parseFloat(object.getString("stars"));
                long size=object.getLong("size");
                String downloadUrl = object.getString("downloadUrl");
                String des = object.getString("des");
                AppInfo info=new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
                appInfos.add(info);
            }
            return appInfos;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "app";
    }
}
