package com.sctbc.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sctbc.googleplay.MainActivity;
import com.sctbc.googleplay.R;
import com.sctbc.googleplay.domain.UserInfo;
import com.sctbc.googleplay.http.HttpHelper;
import com.sctbc.googleplay.manager.ThreadManager;
import com.sctbc.googleplay.protocol.UserProtocol;
import com.sctbc.googleplay.tools.UiUtils;

/**
 * 作者：ZYJ
 * 时间：2015/8/8 0008 12:15
 */
public class MenuHolder extends BaseHolder<UserInfo> implements View.OnClickListener {
    @ViewInject(R.id.photo_layout)
    private RelativeLayout photo_layout;
    @ViewInject(R.id.image_photo)
    private ImageView image_photo;
    @ViewInject(R.id.user_name)
    private TextView user_name;
    @ViewInject(R.id.user_email)
    private TextView user_email;
    @ViewInject(R.id.exit_layout)
    private RelativeLayout exit_layout;
    private MainActivity activity;

    public MenuHolder(MainActivity mainActivity) {
        this.activity=mainActivity;
    }

    @Override
    public View initView() {
        View view=UiUtils.inflate(R.layout.menu_holder);
        ViewUtils.inject(this, view);
        photo_layout.setOnClickListener(this);
        exit_layout.setOnClickListener(this);
        return view;
    }

    @Override
    public void refreshView(UserInfo data) {
        user_name.setText(data.getName());
        user_email.setText(data.getEmail());
        String url = data.getUrl();//image/user.png
        bitmapUtils.display(image_photo, HttpHelper.URL+"image?name="+url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.photo_layout:
                //请求服务器登陆
                ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                    @Override
                    public void run() {

                        UserProtocol protocol = new UserProtocol();
                        final UserInfo load = protocol.load(0);

                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setData(load);//当调用该方法的时候，就会调用refreshView方法
                            }
                        });
                    }
                });
                break;
            case R.id.exit_layout:
                activity.killAll();
                break;
            default:
                break;
        }
    }
}
