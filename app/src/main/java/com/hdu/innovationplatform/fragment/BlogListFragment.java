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
import android.widget.Toast;

import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.activity.BlogDisplayActivity;
import com.hdu.innovationplatform.adapter.BlogListAdapter;
import com.hdu.innovationplatform.helper.RecycleViewDivider;
import com.hdu.innovationplatform.listener.RecyclerItemClickListener;
import com.hdu.innovationplatform.model.Blog;
import com.hdu.innovationplatform.utils.HttpUtil;
import com.hdu.innovationplatform.utils.LogUtil;
import com.hdu.innovationplatform.utils.TransForm;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * com.hdu.innovationplatform.fragment
 * Created by 73958 on 2017/5/31.
 */

public class BlogListFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, RecyclerItemClickListener,
        AdapterView.OnItemSelectedListener {


    private SwipeRefreshLayout refreshLayout;
    private RecyclerView blogList;
    private Spinner tag;

    private static final String[] mLabel = {"全部文章","综合","Linux","前端","后端","IOS","算法","杂谈"};
    private String label;
    private ArrayAdapter<String> adapter;
    private BlogListAdapter blogAdapter;
    private ArrayList<Blog> blogs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog_list, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_blog_list);
        blogList = (RecyclerView) view.findViewById(R.id.rv_blog_list);
        blogList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));

        tag = (Spinner) view.findViewById(R.id.blog_list_label);
        tag.setOnItemSelectedListener(this);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mLabel);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tag.setAdapter(adapter);
        return view;
    }

    public void update(){
        blogAdapter = new BlogListAdapter(getContext(), blogs);
        blogAdapter.setOnRecyclerItemClickListener(this);
        blogList.setAdapter(blogAdapter);
        blogAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshLayout.setOnRefreshListener(this);
        blogList.setLayoutManager(new LinearLayoutManager(getContext()));

        label = tag.getSelectedItemPosition() == 0 ? "all" : mLabel[tag.getSelectedItemPosition()];
        Refresh();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), BlogDisplayActivity.class);
        intent.putExtra("blog", blogs.get(position));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        label = tag.getSelectedItemPosition() == 0 ? "all" : mLabel[tag.getSelectedItemPosition()];
        Refresh();
    }

    public void Refresh(){
        label = "{\"label\":\"" + label + "\"}";
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), label);
        HttpUtil.create().getBlogs(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String res = null;
                refreshLayout.setRefreshing(false);
                try {
                    res = response.body().string();
                    if(res != null){
                        blogs = TransForm.parseBlog(res);
                        blogAdapter = new BlogListAdapter(getContext(), blogs);
                        blogAdapter.setOnRecyclerItemClickListener(BlogListFragment.this);
                        blogList.setAdapter(blogAdapter);
                        blogAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        label = position == 0 ? "all" : mLabel[position];
        Refresh();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
