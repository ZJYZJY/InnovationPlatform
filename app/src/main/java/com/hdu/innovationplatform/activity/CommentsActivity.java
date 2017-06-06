package com.hdu.innovationplatform.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.adapter.CommentListAdapter;
import com.hdu.innovationplatform.helper.RecycleViewDivider;
import com.hdu.innovationplatform.listener.RecyclerItemClickListener;
import com.hdu.innovationplatform.model.Comment;
import com.hdu.innovationplatform.utils.HttpUtil;
import com.hdu.innovationplatform.utils.LogUtil;
import com.hdu.innovationplatform.utils.TransForm;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hdu.innovationplatform.utils.HttpUtil.SUCCESS;

public class CommentsActivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView commList;
    private TextView holder;
    private ArrayList<Comment> comments = new ArrayList<>();
    private CommentListAdapter commentAdapter;

    private String articleId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        articleId = getIntent().getStringExtra("article_id");
        commList = (RecyclerView) findViewById(R.id.comment_list_activity);
        holder = (TextView) findViewById(R.id.comment_list_holder);

        commList.setLayoutManager(new LinearLayoutManager(this));
        commList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));

        articleId = "{\"Article_Id\":\"" + articleId + "\"}";
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), articleId);
        HttpUtil.create().getComments(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    if(res != null){
                        comments = TransForm.parseComment(res);
                        if(comments.size() > 0){
                            commList.setVisibility(View.VISIBLE);
                            commentAdapter = new CommentListAdapter(CommentsActivity.this, comments);
                            commentAdapter.setOnRecyclerItemClickListener(CommentsActivity.this);
                            commList.setAdapter(commentAdapter);
                            holder.setVisibility(View.GONE);
                            commentAdapter.notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(CommentsActivity.this, "获取评论列表失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CommentsActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

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
