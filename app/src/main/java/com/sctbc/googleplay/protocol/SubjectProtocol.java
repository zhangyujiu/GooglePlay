package com.sctbc.googleplay.protocol;

import com.sctbc.googleplay.domain.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ZYJ
 * 时间：2015/8/5 0005 09:27
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>>{


    @Override
    public List<SubjectInfo> parseJson(String json) {
        List<SubjectInfo> subjectInfos=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                String des=jsonArray.getJSONObject(i).getString("des");
                String url=jsonArray.getJSONObject(i).getString("url");
                SubjectInfo info=new SubjectInfo(des,url);
                subjectInfos.add(info);
            }
            return subjectInfos;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "subject";
    }
}
