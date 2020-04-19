package storages.Impl;

import exceptions.ModelExistingException;
import exceptions.ModelNotFoundException;
import models.User;
import storages.UserStorage;

public class UserStorageImpl implements UserStorage {
    private User[] users;
    private int size = 0;

    public UserStorageImpl(int usersCapacity) {
        users = new User[usersCapacity];
    }

    public UserStorageImpl() {
        users = new User[3];
    }

    @Override
    public void add(User user) throws ModelExistingException {
        if (getUserByEmail(user.getEmail()) != null) {
            throw new ModelExistingException("User with " + user.getEmail() + " email is already exist.");
        }
        if (size == users.length) {
            extend();
        }
        users[size++] = user;
    }

    private void extend() {
        User[] tmp = new User[users.length + 5];
        System.arraycopy(users, 0, tmp, 0, users.length);
        users = tmp;
    }

    private User getUserByEmail(String email){
        for (int i = 0; i < size; i++) {
            if (users[i].getEmail().equals(email)){
                return users[i];
            }
        }
        return null;
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws ModelNotFoundException {
        for (int i = 0; i < size; i++) {
            User user = users[i];
            if (user.getEmail().equals(email) && user.getPassword().equals(password)){
                return user;
            }
        }
        throw new ModelNotFoundException(String.format("User with %s and %s is not exist.", email, password));
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
