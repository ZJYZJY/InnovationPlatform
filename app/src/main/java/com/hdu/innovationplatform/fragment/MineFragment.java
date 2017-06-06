package com.hdu.innovationplatform.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hdu.innovationplatform.activity.LoginActivity;
import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.activity.UserInfoActivity;
import com.hdu.innovationplatform.helper.LoginHelper;
import com.hdu.innovationplatform.listener.LoginStatusChangedListener;

import static com.hdu.innovationplatform.utils.UserStatus.LOGIN_STATUS;
import static com.hdu.innovationplatform.utils.UserStatus.USER;

/**
 * com.hdu.innovationplatform.fragment
 * Created by 73958 on 2017/5/24.
 */

public class MineFragment extends Fragment implements View.OnClickListener, LoginStatusChangedListener {

    private LinearLayout userInfo;
    private LinearLayout setting;
    private LinearLayout about;

    private LinearLayout logined;
    private LinearLayout unlogin;

    private Button btn, exit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        btn = (Button) view.findViewById(R.id.login_btn);
        exit = (Button) view.findViewById(R.id.exit_login);
        userInfo = (LinearLayout) view.findViewById(R.id.userinfo_btn);
        setting = (LinearLayout) view.findViewById(R.id.setting);
        about = (LinearLayout) view.findViewById(R.id.about);
        logined = (LinearLayout) view.findViewById(R.id.logined);
        unlogin = (LinearLayout) view.findViewById(R.id.unlogin);

        return view;
    }

    public void update() {
        exit.setVisibility(LOGIN_STATUS ? View.VISIBLE : View.GONE);
        logined.setVisibility(LOGIN_STATUS ? View.VISIBLE : View.GONE);
        unlogin.setVisibility(LOGIN_STATUS ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userInfo.setOnClickListener(this);
        setting.setOnClickListener(this);
        about.setOnClickListener(this);
        btn.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    public void startLoginActivity() {
        LoginActivity.setOnLoginStatusChanged(this);
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userinfo_btn:
                if (LOGIN_STATUS)
                    startActivity(new Intent(getContext(), UserInfoActivity.class));
                else {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startLoginActivity();
                }
                break;
            case R.id.login_btn:
                startLoginActivity();
                break;
            case R.id.setting:

                break;
            case R.id.about:

                break;
            case R.id.exit_login:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("你确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginHelper.getInstance().logout(getContext(), MineFragment.this);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }

    @Override
    public void onLoginStatusChanged(boolean loginStatus) {
        exit.setVisibility(LOGIN_STATUS ? View.VISIBLE : View.GONE);
        logined.setVisibility(LOGIN_STATUS ? View.VISIBLE : View.GONE);
        unlogin.setVisibility(LOGIN_STATUS ? View.GONE : View.VISIBLE);
        if (USER != null && loginStatus) {
            Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "注销成功", Toast.LENGTH_SHORT).show();
        }
    }
}
