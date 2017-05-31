package com.hdu.innovationplatform.fragment;

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

import com.hdu.innovationplatform.LoginActivity;
import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.UserInfoActivity;

import static com.hdu.innovationplatform.utils.UserStatus.LOGIN_STATUS;

/**
 * com.hdu.innovationplatform.fragment
 * Created by 73958 on 2017/5/24.
 */

public class MineFragment extends Fragment implements View.OnClickListener {

    private LinearLayout userInfor;
    private LinearLayout setting;

    private LinearLayout logined;
    private LinearLayout unlogin;

    private Button btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        btn = (Button) view.findViewById(R.id.login_btn);
        userInfor = (LinearLayout) view.findViewById(R.id.userinf_btn);
        setting = (LinearLayout) view.findViewById(R.id.setting);
        logined = (LinearLayout) view.findViewById(R.id.logined);
        unlogin = (LinearLayout) view.findViewById(R.id.unlogin);

        return view;
    }

    public void update(){
        logined.setVisibility(LOGIN_STATUS ? View.VISIBLE : View.GONE);
        unlogin.setVisibility(LOGIN_STATUS ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userInfor.setOnClickListener(this);
        setting.setOnClickListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.userinf_btn:
                if(LOGIN_STATUS)
                    startActivity(new Intent(getContext(), UserInfoActivity.class));
                else
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_btn:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.setting:

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        logined.setVisibility(LOGIN_STATUS ? View.VISIBLE : View.GONE);
        unlogin.setVisibility(LOGIN_STATUS ? View.GONE : View.VISIBLE);
    }
}
