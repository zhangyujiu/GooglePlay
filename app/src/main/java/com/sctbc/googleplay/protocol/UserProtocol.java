package com.sctbc.googleplay.protocol;

import com.sctbc.googleplay.domain.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：ZYJ
 * 时间：2015/8/8 0008 14:18
 */
public class UserProtocol extends BaseProtocol<UserInfo> {
    @Override
    public UserInfo parseJson(String json) {

        try {
            JSONObject jsonObject=new JSONObject(json);
            String name=jsonObject.getString("name");
            String email=jsonObject.getString("email");
            String url=jsonObject.getString("url");
            UserInfo userInfo=new UserInfo(name,url,email);
            return userInfo;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "user";
    }
}
