package com.hdu.innovationplatform.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.helper.LoginHelper;
import com.hdu.innovationplatform.listener.LoginStatusChangedListener;
import com.hdu.innovationplatform.utils.LoginCheck;
import com.hdu.innovationplatform.utils.UserStatus;

import static com.hdu.innovationplatform.utils.UserStatus.USER;

public class UserInfoActivity extends AppCompatActivity {

    private TextView editname, editage, editsex, editcartype, editcarnum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editname = (TextView)findViewById(R.id.edit_name);
        editage  =(TextView) findViewById(R.id.edit_age);
        editsex = (TextView) findViewById(R.id.edit_sex);
        editcartype = (TextView) findViewById(R.id.edit_cartype);
        editcarnum = (TextView) findViewById(R.id.edit_carnum);

        syncUserInfo();
    }

    private void updateData() {
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        editname.setText(pref.getString("drivername",""));
        editage.setText(pref.getString("driverage",""));
        editsex.setText(pref.getString("driversex",""));
        editcartype.setText(pref.getString("cartype",""));
        editcarnum.setText(pref.getString("carnum",""));
    }

    private void syncUserInfo() {
        editname.setText(USER.getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                Intent intent = new Intent(UserInfoActivity.this,EditInfoActivity.class);
                startActivityForResult(intent,1);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    if (data.getBooleanExtra("should_update", true)){
                        // TODO 更新 TextView 的数据
                        updateData();
                    }
                }
                break;
        }
    }
}
