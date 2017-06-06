package com.hdu.innovationplatform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdu.innovationplatform.R;
import com.hdu.innovationplatform.listener.RecyclerItemClickListener;
import com.hdu.innovationplatform.model.Comment;
import com.hdu.innovationplatform.utils.TransForm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * com.hdu.innovationplatform.adapter
 * Created by 73958 on 2017/6/4.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<Comment> comments;
    private RecyclerItemClickListener mItemClickListener;

    public CommentListAdapter(Context context, ArrayList<Comment> comments) {
        Collections.reverse(comments);
        this.mContext = context;
        this.comments = comments;
    }

    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_list, parent, false);
        return new CommentListAdapter.ViewHolder(v, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(CommentListAdapter.ViewHolder holder, int position) {
        holder.name.setText(comments.get(position).getUsername());
        holder.content.setText(comments.get(position).getContent());
        String date = comments.get(position).getTime().substring(0, 10);
        if(Objects.equals(date, TransForm.getDate())){
            holder.time.setText(comments.get(position).getTime().substring(11, 16));
        }else {
            holder.time.setText(comments.get(position).getTime().substring(0, 10));
        }
    }

    @Override
    public int getItemCount() {
        return comments == null ? 0 : comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name;
        TextView content;
        TextView time;
        RecyclerItemClickListener ItemClickListener;

        ViewHolder(View itemView, RecyclerItemClickListener ItemClickListener) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.comment_user_icon);
            name = (TextView) itemView.findViewById(R.id.comment_user_name);
            content = (TextView) itemView.findViewById(R.id.comment_content);
            time = (TextView) itemView.findViewById(R.id.comment_time);

            this.ItemClickListener = ItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
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
