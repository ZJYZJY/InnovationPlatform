package com.hdu.innovationplatform.activity;

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
import com.zzhoujay.richtext.RichText;

public class BlogDisplayActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title, author, content;
    private EditText comment;
    private ImageButton comment_list, comment_send;
    private Blog blog;

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

    public void onFollow(View view){
        Toast.makeText(this, "关注", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comment_send:
                break;
            case R.id.comment_list:
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
