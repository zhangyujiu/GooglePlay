package com.sctbc.googleplay.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ZYJ
 * 时间：2015/8/13 0013 17:51
 */
public class TopProtocol extends BaseProtocol<List<String>> {
    @Override
    public List<String> parseJson(String json) {
        List<String> datas = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                String str = jsonArray.getString(i);
                datas.add(str);
            }
            return datas;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "hot";
    }
}
