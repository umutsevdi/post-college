package com.sevdi.postcollege.ui.feed.post;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sevdi.postcollege.data.model.Post;
import com.sevdi.postcollege.data.service.PostStore;
import com.sevdi.postcollege.databinding.FragmentPostFeedListBinding;
import com.sevdi.postcollege.ui.dialog.PostDialogFragment;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class PostListFragment<T extends Post> extends Fragment {
    private final boolean isAnnouncement;

    public PostListFragment(boolean isAnnouncement) {
        this.isAnnouncement = isAnnouncement;
    }

    public PostListFragment() {
        isAnnouncement = false;
    }

    private FragmentPostFeedListBinding binding;

    private RecyclerView.LayoutManager layoutManager;
    private PostAdapter<T> adapter;
    private boolean isLoading = false;
    private int currentPage = 0;
    private int pageCount = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPostFeedListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.list;
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter<>(getContext(), isAnnouncement);
        recyclerView.setAdapter(adapter);
        loadMoreData(0);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                View firstVisibleView = layoutManager.getChildAt(0);
                assert firstVisibleView != null;
                int firstVisibleItemPosition = layoutManager.getPosition(firstVisibleView);
                if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + 5)) {
                    // Load more data
                    if (currentPage < pageCount) {
                        isLoading = true;
                        loadMoreData(currentPage + 1);
                    }
                }
            }
        });

        binding.button.setOnClickListener(i -> {
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            PostDialogFragment postDialogFragment = new PostDialogFragment(false);
            postDialogFragment.show(fm, "Share");
        });

        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("StaticFieldLeak")
    private void loadMoreData(int page) {
        new AsyncTask<Integer, Void, List<T>>() {
            @Override
            protected List<T> doInBackground(Integer... integers) {
                try {
                    if (isAnnouncement)
                        return (List<T>) PostStore.announcementInstance().getPage(integers[0]);
                    return (List<T>) PostStore.postInstance().getPage(integers[0]);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<T> posts) {
                if (posts != null) {
                    adapter.addPosts(posts);
                    currentPage = page;
                    if (isAnnouncement) {
                        PostStore.announcementInstance().getPageCount(i -> pageCount = i);
                    } else {
                        PostStore.postInstance().getPageCount(i -> pageCount = i);
                    }
                    isLoading = false;
                }
            }
        }.execute(page);
    }
}