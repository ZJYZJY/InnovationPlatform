package com.hdu.innovationplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.activity.BlogDisplayActivity;
import com.hdu.innovationplatform.activity.CommentsActivity;
import com.hdu.innovationplatform.listener.RecyclerItemClickListener;
import com.hdu.innovationplatform.model.Blog;
import com.hdu.innovationplatform.utils.LogUtil;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.Collections;

/**
 * com.hdu.innovationplatform.adapter
 * Created by 73958 on 2017/5/31.
 */

public class BlogListAdapter extends RecyclerView.Adapter<BlogListAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<Blog> blogs;
    private RecyclerItemClickListener mItemClickListener;

    public BlogListAdapter(Context context, ArrayList<Blog> blogs) {
        Collections.reverse(blogs);
        this.mContext = context;
        this.blogs = blogs;
    }

    @Override
    public BlogListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog_list, parent, false);
        return new BlogListAdapter.ViewHolder(v, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(BlogListAdapter.ViewHolder holder, int position) {
        holder.author.setText(blogs.get(position).getAuthor());
        holder.label.setText(blogs.get(position).getLabel());
        holder.title.setText(blogs.get(position).getTitle());
        // 设置为Html格式
        RichText.fromHtml(blogs.get(position).getContent()).into(holder.content);
        holder.commentNum.setText(String.valueOf(getCommentCount(position)));
    }

    public int getCommentCount(int position){
        return blogs.get(position).getComments() == null ? 0 : blogs.get(position).getComments().size();
    }

    @Override
    public int getItemCount() {
        return blogs == null ? 0 : blogs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView author;
        TextView label;
        TextView title;
        TextView content;
        ImageButton collect;
        ImageButton comment;
        TextView collectNum;
        TextView commentNum;
        RecyclerItemClickListener ItemClickListener;

        ViewHolder(View itemView, RecyclerItemClickListener ItemClickListener) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.list_author);
            label = (TextView) itemView.findViewById(R.id.list_label);
            title = (TextView) itemView.findViewById(R.id.list_title);
            content = (TextView) itemView.findViewById(R.id.list_content);
            collect = (ImageButton) itemView.findViewById(R.id.blog_list_collect);
            comment = (ImageButton) itemView.findViewById(R.id.blog_list_comment);
            collectNum = (TextView) itemView.findViewById(R.id.blog_list_collect_num);
            commentNum = (TextView) itemView.findViewById(R.id.blog_list_comment_num);

            this.ItemClickListener = ItemClickListener;
            itemView.setOnClickListener(this);
            collect.setOnClickListener(this);
            comment.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == comment) {
                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("article_id", blogs.get(getLayoutPosition()).getArticleId());
                mContext.startActivity(intent);
            } else if (v == collect) {
                LogUtil.d("collect");
            } else if (v == itemView) {
                if (this.ItemClickListener != null) {
                    this.ItemClickListener.onItemClick(v, getLayoutPosition());
                }
            }
        }
    }

    public void setOnRecyclerItemClickListener(RecyclerItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
