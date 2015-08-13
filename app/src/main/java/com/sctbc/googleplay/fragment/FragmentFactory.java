package com.sctbc.googleplay.fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：ZYJ
 * 时间：2015/8/1 0001 17:02
 */
public class FragmentFactory {
    private static Map<Integer,BaseFragment> mFragments=new HashMap<Integer,BaseFragment>();

    public static BaseFragment createFragment(int position){
        BaseFragment fragment=null;
        fragment=mFragments.get(position);//把集合中的fragment取出来
        if(fragment==null) {//如果集合中没有，需要重新创建
            if (position == 0) {
                fragment = new HomeFragment();
            } else if (position == 1) {
                fragment = new AppFragment();
            } else if (position == 2) {
                fragment = new GameFragment();
            } else if (position == 3) {
                fragment = new SubjectFragment();
            } else if (position == 4) {
                fragment = new CategoryFragment();
            } else if (position == 5) {
                fragment = new TopFragment();
            }
            if(fragment!=null){
                mFragments.put(position,fragment);
            }
        }
        return fragment;
    }
}
