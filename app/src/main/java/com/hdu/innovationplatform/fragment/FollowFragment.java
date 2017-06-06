package com.hdu.innovationplatform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.activity.BlogDisplayActivity;
import com.hdu.innovationplatform.adapter.BlogListAdapter;
import com.hdu.innovationplatform.helper.RecycleViewDivider;
import com.hdu.innovationplatform.listener.RecyclerItemClickListener;
import com.hdu.innovationplatform.model.Blog;
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

import static com.hdu.innovationplatform.utils.UserStatus.LOGIN_STATUS;
import static com.hdu.innovationplatform.utils.UserStatus.USER;

/**
 * com.hdu.innovationplatform.fragment
 * Created by 73958 on 2017/5/31.
 */

public class FollowFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener {


    private SwipeRefreshLayout refreshLayout;
    private RecyclerView blogList;
    private TextView login_first;
    private Spinner tag;

    private static final String[] mLabel = {"全部文章","综合","Linux","前端","后端","IOS","算法","杂谈"};
    private String userId;
    private BlogListAdapter blogAdapter;
    private ArrayList<Blog> blogs = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followed_blog_list, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_followed_blog_list);
        blogList = (RecyclerView) view.findViewById(R.id.rv_followed_blog_list);
        login_first = (TextView) view.findViewById(R.id.login_first);
        blogList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));

        return view;
    }

    public void update(){
        login_first.setVisibility(LOGIN_STATUS ? View.GONE : View.VISIBLE);
        refreshLayout.setVisibility(LOGIN_STATUS ? View.VISIBLE : View.GONE);
        if(USER != null)
            Refresh();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshLayout.setOnRefreshListener(this);
        blogList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), BlogDisplayActivity.class);
        intent.putExtra("blog", blogs.get(position));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if(USER != null)
            Refresh();
    }

    public void Refresh(){
        userId = "{\"concern_id\":\"" + USER.getUserId() + "\"}";
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), userId);
        HttpUtil.create().getFollowedBlogs(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                refreshLayout.setRefreshing(false);
                try {
                    String res = response.body().string();
                    if(res != null){
                        blogs = TransForm.parseFollowedBlog(res);
                        blogAdapter = new BlogListAdapter(getContext(), blogs);
                        blogAdapter.setOnRecyclerItemClickListener(FollowFragment.this);
                        blogList.setAdapter(blogAdapter);
                        getComments(blogs);
                    }else{
                        Toast.makeText(getContext(), "获取博客列表失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getComments(ArrayList<Blog> blogs){
        for(final Blog blog : blogs){
            String articleId = "{\"Article_Id\":\"" + blog.getArticleId() + "\"}";
            RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), articleId);
            HttpUtil.create().getComments(requestBody).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String res = response.body().string();
                        if(res != null){
                            ArrayList<Comment> comments = TransForm.parseComment(res);
                            if(comments.size() > 0){
                                blog.setComments(comments);
                            }
                        }else{
//                        Toast.makeText(getContext(), "获取评论列表失败", Toast.LENGTH_SHORT).show();
                            LogUtil.e("获取评论列表失败");
                        }
                        blogAdapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    blogAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "连接失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
