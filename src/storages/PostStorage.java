package storages;

import exceptions.ModelExistingException;
import exceptions.ModelNotFoundException;
import models.Category;
import models.Post;
import models.User;

public interface PostStorage {

    void add(Post post) throws ModelExistingException, ModelNotFoundException;

    void searchPostsByKeyword(String keyword) throws ModelNotFoundException;

    void deletePost(String title, User user) throws ModelNotFoundException;

    void printPostsByUser(User user);

    void printAllPosts();

    void printPostsByCategory(Category category) throws ModelNotFoundException;

    boolean isEmpty();

}
