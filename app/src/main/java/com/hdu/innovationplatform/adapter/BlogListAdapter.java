package com.hdu.innovationplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.listener.RecyclerItemClickListener;
import com.hdu.innovationplatform.model.Blog;

import java.util.ArrayList;

/**
 * com.hdu.innovationplatform.adapter
 * Created by 73958 on 2017/5/31.
 */

public class BlogListAdapter extends RecyclerView.Adapter<BlogListAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<Blog> blogs;
    private RecyclerItemClickListener mItemClickListener;

    public BlogListAdapter(Context context, ArrayList<Blog> blogs){
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
        holder.content.setText(blogs.get(position).getContent());
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
        RecyclerItemClickListener ItemClickListener;

        ViewHolder(View itemView, RecyclerItemClickListener ItemClickListener) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.list_author);
            label = (TextView) itemView.findViewById(R.id.list_label);
            title = (TextView) itemView.findViewById(R.id.list_title);
            content = (TextView) itemView.findViewById(R.id.list_content);

            this.ItemClickListener = ItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v == itemView){
                if(this.ItemClickListener != null){
                    this.ItemClickListener.onItemClick(v, getLayoutPosition());
                }
            }
        }
    }

    public void setOnRecyclerItemClickListener(RecyclerItemClickListener listener){
        this.mItemClickListener = listener;
    }
}
