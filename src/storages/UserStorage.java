package storages;

import exceptions.ModelExistingException;
import exceptions.ModelNotFoundException;
import models.User;

public interface UserStorage {

    void add(User user) throws ModelExistingException;

    User getUserByEmailAndPassword(String email, String password) throws ModelNotFoundException;

    boolean isEmpty();

}
