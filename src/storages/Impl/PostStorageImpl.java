package storages.Impl;

import exceptions.ModelExistingException;
import exceptions.ModelNotFoundException;
import models.Category;
import models.Post;
import models.User;
import storages.PostStorage;

import javax.jws.soap.SOAPBinding;

public class PostStorageImpl implements PostStorage {
    private Post[] posts;
    private int size = 0;

    public PostStorageImpl(int postsCapacity) {
        posts = new Post[postsCapacity];
    }

    public PostStorageImpl() {
        posts = new Post[3];
    }

    @Override
    public void add(Post post) throws ModelExistingException {
        if (getPostByTitle(post.getTitle()) != null) {
            throw new ModelExistingException("Post with " + post.getTitle() + " title is already exist.");
        }
        if (size == posts.length) {
            extend();
        }
        posts[size++] = post;
    }

    private void extend() {
        Post[] tmp = new Post[posts.length + 5];
        System.arraycopy(posts, 0, tmp, 0, posts.length);
        posts = tmp;
    }

    private Post getPostByTitle(String title) {
        for (int i = 0; i < size; i++) {
            if (posts[i].getTitle().equals(title)) {
                return posts[i];
            }
        }
        return null;
    }

    private int getPostIndex(Post post) throws ModelNotFoundException {
        for (int i = 0; i < size; i++) {
            if (posts[i].equals(post)){
                return i;
            }
        }
        throw new ModelNotFoundException(String.format("Post with %s title does not exist.", post.getTitle()));
    }

    @Override
    public void deletePost(String title, User user) throws ModelNotFoundException {
        Post post = getPostByTitle(title);
        if (post == null){
                throw new ModelNotFoundException("Post with " + post.getTitle() + " title does not exist.");

        }
        int index = getPostIndex(post);
        if (post.getPostAuthor().equals(user)){
            for (int i = index + 1; i < size; i++) {
                posts[i - 1] = posts[i];
            }
            size--;
        } else {
            throw new ModelNotFoundException("Post with " + post.getTitle() + " is not your.");
        }

    }

    @Override
    public void printPostsByUser(User user){
        for (int i = 0; i < size; i++) {
            if (posts[i].getPostAuthor().equals(user)){
                System.out.println(posts[i]);
            }
        }
    }

    @Override
    public void searchPostsByKeyword(String keyword) throws ModelNotFoundException {
        for (int i = 0; i < size; i++) {
            if (posts[i].getTitle().contains(keyword) || posts[i].getText().contains(keyword)) {
                System.out.println(posts[i]);
            }
        }
        throw new ModelNotFoundException(String.format("The post with \"%s\" keyword does not exist.", keyword));
    }


    @Override
    public void printPostsByCategory(Category category) throws ModelNotFoundException {
        for (int i = 0; i < size; i++) {
            if (posts[i].getCategory() == category) {
                System.out.println(posts[i]);
            }
        }
        throw new ModelNotFoundException(String.format("The post with \"%s\" category does not exist.", category));
    }

    @Override
    public void printAllPosts() {
        for (int i = 0; i < size; i++) {
            System.out.println(posts[i]);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
