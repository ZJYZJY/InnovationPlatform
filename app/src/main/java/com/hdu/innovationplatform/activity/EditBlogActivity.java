package com.hdu.innovationplatform.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.model.Blog;
import com.hdu.innovationplatform.utils.HttpUtil;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hdu.innovationplatform.utils.HttpUtil.SUCCESS;
import static com.hdu.innovationplatform.utils.UserStatus.USER;

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

        tag = (Spinner) findViewById(R.id.blog_tag);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mTag);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tag.setAdapter(adapter);
    }

    public void publishBlog(){
        str_tag = mTag[tag.getSelectedItemPosition()];
        str_title = title.getText().toString();
        str_body = body.getText().toString();
        if(!Objects.equals(str_title, "") && !Objects.equals(str_body, "")){
            Blog blog = new Blog(USER.getUserId(), str_title, str_tag,
                    USER.getName() == null ? USER.getUsername() : USER.getName(),
                    str_body);
            HttpUtil.create().publishBlog(blog).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String res = null;
                    try {
                        res = response.body().string();
                        if(HttpUtil.stateCode(res) == SUCCESS){
                            Toast.makeText(EditBlogActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(EditBlogActivity.this, "发表失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(EditBlogActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "请填写完整", Toast.LENGTH_SHORT).show();
        }
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
