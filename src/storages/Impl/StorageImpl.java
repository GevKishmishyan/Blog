package storages.Impl;

import exceptions.ModelExistingException;
import exceptions.ModelNotFoundException;
import models.Category;
import models.Post;
import models.User;
import storages.Storage;

import java.util.*;

public class StorageImpl implements Storage {
    private List<Post> posts = new ArrayList<>();
    private Map<String, User> users = new HashMap<>();

    @Override
    public void add(Post post) throws ModelExistingException {
        if (getPostByTitle(post.getTitle()) != null) {
            throw new ModelExistingException("Post with " + post.getTitle() + " title is already exist.");
        }
        posts.add(post);
    }

    @Override
    public void add(User user) throws ModelExistingException {
        if (getUserByEmail(user.getEmail()) != null) {
            throw new ModelExistingException("User with " + user.getEmail() + " email is already exist.");
        }
        users.put(user.getEmail(), user);
    }

    private Post getPostByTitle(String title) {
        for (Post post : posts) {
            if (post.getTitle().equals(title)) {
                return post;
            }
        }
        return null;
    }

    private User getUserByEmail(String email){
        for (String key : users.keySet()) {
            if (key.equals(email)){
                return users.get(key);
            }
        }
        return null;
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws ModelNotFoundException {
        for (String key : users.keySet()) {
            User user = users.get(key);
            if (user.getEmail().equals(email) && user.getPassword().equals(password)){
                return user;
            }
        }
        throw new ModelNotFoundException(String.format("User with %s and %s is not exist.", email, password));
    }


    @Override
    public void deletePost(String title, User user) throws ModelNotFoundException {
        Post post = getPostByTitle(title);
        if (post == null) {
            throw new ModelNotFoundException("Post with " + title + " title does not exist.");
        }
        if (post.getPostAuthor().equals(user)) {
            posts.remove(post);
        } else {
            throw new ModelNotFoundException("Post with " + post.getTitle() + " is not your.");
        }
    }

    @Override
    public void printPostsByUser(User user) {
        for (Post post : posts) {
            if (post.getPostAuthor().equals(user)) {
                System.out.println(post);
            }
        }
    }

    @Override
    public void searchPostsByKeyword(String keyword) throws ModelNotFoundException {
        for (Post post : posts) {
            if (post.getTitle().contains(keyword) || post.getText().contains(keyword)) {
                System.out.println(post);
            }
        }
        throw new ModelNotFoundException(String.format("The post with \"%s\" keyword does not exist.", keyword));
    }


    @Override
    public void printPostsByCategory(Category category) throws ModelNotFoundException {
        for (Post post : posts) {
            if (post.getCategory() == category) {
                System.out.println(post);
            }
        }
        throw new ModelNotFoundException(String.format("The post with \"%s\" category does not exist.", category));
    }

    @Override
    public void printAllPosts() {
        for (Post post : posts) {
            System.out.println(post);
        }
    }

    @Override
    public boolean isUsersEmpty() {
        return users.isEmpty();
    }

    @Override
    public boolean isPostsEmpty() {
        return posts.isEmpty();
    }
}
