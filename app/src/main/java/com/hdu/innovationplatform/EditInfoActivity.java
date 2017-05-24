package com.hdu.innovationplatform;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditInfoActivity extends AppCompatActivity {

    private EditText finishname, finishage, finishsex, finishcartype, finishcarnum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        finishname = (EditText) findViewById(R.id.finish_name);
        finishage = (EditText) findViewById(R.id.finish_age);
        finishsex = (EditText) findViewById(R.id.finish_sex);
        finishcartype = (EditText) findViewById(R.id.finish_cartype);
        finishcarnum = (EditText) findViewById(R.id.finish_carnum);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editinfo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_finish:
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("drivername", finishname.getText().toString());
                editor.putString("driverage", finishage.getText().toString());
                editor.putString("driversex", finishsex.getText().toString());
                editor.putString("cartype", finishcartype.getText().toString());
                editor.putString("carnum", finishcarnum.getText().toString());
                editor.apply();

                Intent intent = new Intent();
                intent.putExtra("should_update", true);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
