package com.sevdi.postcollege.data.service;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sevdi.postcollege.data.model.Announcement;
import com.sevdi.postcollege.data.model.Post;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PostStore<T extends Post> {
    private static final int PAGE_SIZE = 25;
    private static PostStore<Announcement> postStoreAnnouncement;
    private static PostStore<Post> postStore;
    private final FirebaseFirestore db;
    private final String path;

    private PostStore(String path) {
        this.db = FirebaseFirestore.getInstance();
        this.path = path;
    }

    public static PostStore<Announcement> announcementInstance() {
        if (postStoreAnnouncement == null) postStoreAnnouncement = new PostStore<>("announcement");
        return postStoreAnnouncement;
    }

    public static PostStore<Post> postInstance() {
        if (postStore == null) postStore = new PostStore<>("posts");
        return postStore;
    }

    public List<Post> getPage(int page) throws ExecutionException, InterruptedException {
        System.out.println("GET_PAGE#" + page);
        AtomicInteger before = new AtomicInteger();
        CollectionReference collectionReference = db.collection(path);
        List<Post> p = Tasks.
                await(collectionReference.orderBy("created", Query.Direction.DESCENDING).get())
                .getDocuments().stream()
                .filter(j -> before.getAndIncrement() >= (page * PAGE_SIZE))
                .limit(PAGE_SIZE).map(T::from).collect(Collectors.toList());
        System.out.println("GET_PAGE#DONE:" + p);
        return p;
    }

    public void getPageCount(Consumer<Integer> onCount) {
        db.collection(path).get().addOnSuccessListener(i -> onCount.accept(i.getDocuments().size()));
    }


    public <K extends Post> void publish(K item, Consumer<K> onDataWritten) {
        db.collection(path).add(item.toMap()).addOnSuccessListener(i -> i.addSnapshotListener((value, error) -> {
            if (path.equals("post")) {
                onDataWritten.accept((K) Post.from(value));
            } else {
                onDataWritten.accept((K) Announcement.from(value));
            }

        }));
    }
}
