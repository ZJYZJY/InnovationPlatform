package com.hdu.innovationplatform.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.model.Blog;
import com.hdu.innovationplatform.model.Comment;
import com.hdu.innovationplatform.utils.HttpUtil;
import com.hdu.innovationplatform.utils.LogUtil;
import com.hdu.innovationplatform.utils.UserStatus;
import com.zzhoujay.richtext.RichText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hdu.innovationplatform.utils.HttpUtil.SUCCESS;
import static com.hdu.innovationplatform.utils.UserStatus.USER;

public class BlogDisplayActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title, author, content;
    private EditText comment;
    private ImageButton comment_list, comment_send;
    private Blog blog;
    private String commentStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.display_blog_title);
        author = (TextView) findViewById(R.id.display_blog_author);
        content = (TextView) findViewById(R.id.display_blog_content);

        comment = (EditText) findViewById(R.id.comment_et);
        comment_send = (ImageButton) findViewById(R.id.comment_send);
        comment_list = (ImageButton) findViewById(R.id.comment_list);
        comment_send.setOnClickListener(this);
        comment_list.setOnClickListener(this);

        blog = getIntent().getParcelableExtra("blog");
        title.setText(blog.getTitle());
        author.setText(blog.getAuthor());
        // 设置为Html格式
        RichText.fromHtml(blog.getContent()).into(content);
    }

    public void onFollow(View view) {
        Toast.makeText(this, "关注", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_send:
                commentStr = comment.getText().toString();
                if (!Objects.equals(commentStr, "")) {
                    String request = "{" + "\"User_Id\":\"" + USER.getUserId() + "\"," +
                                            "\"Article_Id\":\"" + blog.getId() + "\"," +
                                            "\"Content\":\"" + commentStr + "\"}";
                    RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), request);
                    HttpUtil.create().sendComment(requestBody).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String res = response.body().string();
                                if (HttpUtil.stateCode(res) == SUCCESS) {
                                    Toast.makeText(BlogDisplayActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                    comment.clearFocus();
                                    comment.setText("");
                                } else {
                                    Toast.makeText(BlogDisplayActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(BlogDisplayActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.comment_list:
                Intent intent = new Intent(BlogDisplayActivity.this, CommentsActivity.class);
                intent.putExtra("article_id", blog.getId());
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
