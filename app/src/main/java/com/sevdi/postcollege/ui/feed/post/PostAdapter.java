package com.sevdi.postcollege.ui.feed.post;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.sevdi.postcollege.R;
import com.sevdi.postcollege.data.model.Announcement;
import com.sevdi.postcollege.data.model.Post;
import com.sevdi.postcollege.data.service.ImageStore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PostAdapter<T extends Post> extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final boolean isAnnouncement;
    private final Context context;
    private final List<T> posts = new ArrayList<>();

    public PostAdapter(Context context, boolean isAnnouncement) {
        this.context = context;
        this.isAnnouncement = isAnnouncement;
    }


    public void addPosts(List<T> newPosts) {
        int startPos = posts.size();
        posts.addAll(newPosts);
        notifyItemRangeInserted(startPos, newPosts.size());
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_post_feed, parent, false);
        return new PostViewHolder(view, isAnnouncement);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        T post = posts.get(position);
        holder.nameView.setText(post.getPostOwnerName());
        holder.counterView.setText(post.getCount().toString());
        holder.dateTimeView.setText(post.getCreated().format(DateTimeFormatter.ofPattern("hh:mma dd/MM/yyyy")));
        holder.messageView.setText(post.getMessage());

        if (post.getImage() != null) {
            ImageStore.getInstance().getImage(context, post.getImage(), holder.imageView::setImageBitmap);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
        if (post.getProfile() != null) {
            ImageStore.getInstance().getImage(context, post.getProfile(), holder.profileView::setImageBitmap);
        }
        if (isAnnouncement) {
            Announcement a = Announcement.from(post.toMap());
            holder.startDate.setText(a.getStartTime().format(DateTimeFormatter.ofPattern("hh:mma dd/MM/yyyy")));
            holder.endDate.setText(a.getDeadline().format(DateTimeFormatter.ofPattern("hh:mma dd/MM/yyyy")));
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final ImageView profileView;
        private final TextView nameView;
        private final TextView dateTimeView;
        private final TextView messageView;
        private final Chip upvoteView;
        private final TextView counterView;
        private final Chip downvoteView;

        private final TextView startDate;
        private final TextView endDate;

        public PostViewHolder(View view, boolean isAnnouncement) {
            super(view);
            imageView = view.findViewById(R.id.image);
            nameView = view.findViewById(R.id.name);
            dateTimeView = view.findViewById(R.id.dateTime);
            messageView = view.findViewById(R.id.message);
            upvoteView = view.findViewById(R.id.upvote);
            counterView = view.findViewById(R.id.counter);
            downvoteView = view.findViewById(R.id.downvote);
            profileView = view.findViewById(R.id.profile);
            startDate = view.findViewById(R.id.startText);
            endDate = view.findViewById(R.id.endText);
            if (!isAnnouncement) {
                view.findViewById(R.id.textView2).setVisibility(View.GONE);
                startDate.setVisibility(View.GONE);
                endDate.setVisibility(View.GONE);
            }
        }
    }
}

