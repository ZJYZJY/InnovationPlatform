package com.hdu.innovationplatform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditBlogActivity extends AppCompatActivity {

    private static final String[] mTag = {"综合","Linux","前端","后端","IOS","算法","杂谈"};

    private EditText title, body;
    private Spinner tag;

    private String str_title, str_body, str_tag;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blog);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (EditText) findViewById(R.id.blog_title);
        body = (EditText) findViewById(R.id.blog_body);

        Spinner spinner = (Spinner) findViewById(R.id.blog_tag);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mTag);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void publishBlog(){
        Toast.makeText(this, "发表", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_blog_commit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.publish_blog:
                publishBlog();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
