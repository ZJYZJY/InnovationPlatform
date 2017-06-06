package com.hdu.innovationplatform.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
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
import static com.hdu.innovationplatform.utils.UserStatus.LOGIN_STATUS;
import static com.hdu.innovationplatform.utils.UserStatus.USER;

public class BlogDisplayActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title, author, content;
    private CheckedTextView follow;
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
        follow = (CheckedTextView) findViewById(R.id.follow);

        comment = (EditText) findViewById(R.id.comment_et);
        comment_send = (ImageButton) findViewById(R.id.comment_send);
        comment_list = (ImageButton) findViewById(R.id.comment_list);
        comment_send.setOnClickListener(this);
        comment_list.setOnClickListener(this);
        follow.setOnClickListener(this);

        blog = getIntent().getParcelableExtra("blog");
        if (LOGIN_STATUS) {
            if (Objects.equals(blog.getUser_id(), USER.getUserId())) {
                follow.setVisibility(View.GONE);
            }
            if (USER.getFollowed_id().contains(blog.getArticleId())) {
                follow.setText("已关注");
                follow.setChecked(true);
            }
        }
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
                if (LOGIN_STATUS) {
                    commentStr = comment.getText().toString();
                    if (!Objects.equals(commentStr, "")) {
                        String request = "{" + "\"User_Id\":\"" + USER.getUserId() + "\"," +
                                "\"Article_Id\":\"" + blog.getArticleId() + "\"," +
                                "\"Content\":\"" + commentStr + "\"}";
                        RequestBody requestBody = RequestBody
                                .create(okhttp3.MediaType.parse("application/json; charset=utf-8"), request);
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
                } else {
                    Toast.makeText(BlogDisplayActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.comment_list:
                Intent intent = new Intent(BlogDisplayActivity.this, CommentsActivity.class);
                intent.putExtra("article_id", blog.getArticleId());
                startActivity(intent);
                break;
            case R.id.follow:
                if (LOGIN_STATUS) {
                    int action;
                    final String actionStr[] = {""};
                    if (!((CheckedTextView) v).isChecked()) {
                        ((CheckedTextView) v).setChecked(true);
                        ((CheckedTextView) v).setText("已关注");
                        actionStr[0] = "关注";
                        action = 1;
                    } else {
                        ((CheckedTextView) v).setChecked(false);
                        ((CheckedTextView) v).setText("关注");
                        actionStr[0] = "取消关注";
                        action = 0;
                    }
                    String request = "{" + "\"author_id\":\"" + blog.getArticleId() + "\"," +
                            "\"concern_id\":\"" + USER.getUserId() + "\"," +
                            "\"action_type\":\"" + action + "\"}";
                    RequestBody requestBody = RequestBody
                            .create(okhttp3.MediaType.parse("application/json; charset=utf-8"), request);
                    HttpUtil.create().followAuthor(requestBody).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String res = response.body().string();
                                if (HttpUtil.stateCode(res) == HttpUtil.SUCCESS) {
                                    Toast.makeText(BlogDisplayActivity.this, actionStr[0] + "成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    follow.setChecked(false);
                                    Toast.makeText(BlogDisplayActivity.this, actionStr[0] + "失败", Toast.LENGTH_SHORT).show();
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
                    break;
                } else {
                    Toast.makeText(BlogDisplayActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
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
