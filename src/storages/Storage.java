package storages;

import exceptions.ModelExistingException;
import exceptions.ModelNotFoundException;
import models.Category;
import models.Post;
import models.User;

import java.util.Collection;

public interface Storage {

    void add(Post post) throws ModelExistingException, ModelNotFoundException;

    void add(User user) throws ModelExistingException;

    User getUserByEmailAndPassword(String email, String password) throws ModelNotFoundException;

    void searchPostsByKeyword(String keyword) throws ModelNotFoundException;

    void deletePost(String title, User user) throws ModelNotFoundException;

    void printPostsByUser(User user);

    void printAllPosts();

    void printPostsByCategory(Category category) throws ModelNotFoundException;

    boolean isUsersEmpty();

    boolean isPostsEmpty();

}
