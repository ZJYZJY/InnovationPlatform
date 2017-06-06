package com.hdu.innovationplatform.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hdu.innovationplatform.R;

public class UserInfoActivity extends AppCompatActivity {

    private TextView realName;
    private TextView sex;
    private TextView schoolNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_user_info);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //给页面设置工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //设置工具栏标题
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        if (collapsingToolbar != null) {
            collapsingToolbar.setTitle("用户信息");
            collapsingToolbar.setExpandedTitleColor(Color.WHITE);//设置收缩前Toolbar上字体的颜色
            collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        }
        initUserInfoView();
    }

    public void initUserInfoView(){
        realName = (TextView) findViewById(R.id.user_real_name);
        sex = (TextView) findViewById(R.id.user_sex);
        schoolNumber = (TextView) findViewById(R.id.user_school_number);
    }

    private String getPreference(String key){
        return PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getString(key, "");
    }

    public void onEditUserInfo(View view){
        Intent intent = new Intent(UserInfoActivity.this, EditUserInfo.class);
        intent.putExtra("user_real_name", getPreference("user_real_name"));
        intent.putExtra("user_sex", getPreference("user_sex"));
        intent.putExtra("user_school_number", getPreference("user_school_number"));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        realName.setText(getPreference("user_real_name"));
        sex.setText(getPreference("user_sex"));
        schoolNumber.setText(getPreference("user_school_number"));
    }
}
