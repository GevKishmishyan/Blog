import exceptions.ModelExistingException;
import exceptions.ModelNotFoundException;
import models.Category;
import models.Gender;
import models.Post;
import models.User;
import storages.Impl.PostStorageImpl;
import storages.Impl.UserStorageImpl;

import java.util.Date;
import java.util.Scanner;

public class BlogMain implements Command {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final PostStorageImpl POST_STORAGE = new PostStorageImpl();
    private static final UserStorageImpl USER_STORAGE = new UserStorageImpl();
    private static User currentUser = null;

    public static void main(String[] args) {
        boolean isRunMain = true;
        int userCommand;
        while (isRunMain) {
            Command.printMainCommands();
            try {
                userCommand = Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException e) {
                userCommand = -1;
            }
            switch (userCommand) {
                case EXIT:
                    isRunMain = false;
                    System.out.println("Bye Bye.");
                    break;
                case SEARCH_POST:
                    searchPostByKeyword();
                    break;
                case POSTS_BY_CATEGORY:
                    searchPostByCategory();
                    break;
                case ALL_POSTS:
                    // everything is ok
                    if (POST_STORAGE.isEmpty()) {
                        System.out.println("Posts list is EMPTY. Please add post first.");
                        break;
                    }
                    POST_STORAGE.printAllPosts();
                    break;
                case LOGIN:
                    userLogin();
                    break;
                case REGISTER:
                    userRegister();
                    break;
                default:
                    System.out.println("Wrong command, please choose the CORRECT.");
            }
        }
    }

    private static void userCom() {
        boolean isRun = true;
        do {
            Command.printUserCommands();
            int userCommand;
            try {
                userCommand = Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException e) {
                userCommand = -1;
            }
            switch (userCommand) {
                case ADD_POST:
                    addPost();
                    break;
                case SEARCH_POST:
                    searchPostByKeyword();
                    break;
                case POSTS_BY_CATEGORY:
                    searchPostByCategory();
                    break;
                case ALL_POSTS:
                    if (POST_STORAGE.isEmpty()) {
                        System.out.println("Posts list is EMPTY. Please add post first.");
                        break;
                    }
                    POST_STORAGE.printAllPosts();
                    break;
                case DELETE_POST:
                    deletePost();
                    break;
                case LOGOUT:
                    isRun = false;
                    System.out.println("Bye Bye.");
                    break;
                default:
                    System.out.println("Wrong command, please choose the CORRECT.");
            }
        } while (isRun);
    }

    private static void deletePost() {
        if (POST_STORAGE.isEmpty()) {
            System.out.println("Posts list is EMPTY. Please add post first.");
            return;
        }
        POST_STORAGE.printPostsByUser(currentUser);
        System.out.println("Please, input title to DELETING the post.");
        String title = SCANNER.nextLine();
        if(title.equals("")){
            System.out.println("you forgot to enter the post title, which you want to delete.");
            deletePost();
        } else {
            try {
                POST_STORAGE.deletePost(title, currentUser);
                System.out.println(String.format("Post with %s title was deleted.", title));
            } catch (ModelNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException e){
                System.out.println("Incorrect value!!!");
            }
        }
    }

    private static void userRegister() {
        System.out.println("Please, input name, surname, email, password, gender[MALE or FEMALE] for registration.");
        try {
            String userDataStr = SCANNER.nextLine();
            String[] userData = userDataStr.split(",");
            if (userDataStr.equals("")) {
                System.out.println("You forgot to fill all fields.");
                userRegister();
            } else if (userData.length < 5) {
                switch (userData.length) {
                    case 1:
                        System.out.println("You forgot to fill surname, email, password, gender[MALE or FEMALE].");
                        break;
                    case 2:
                        System.out.println("You forgot to fill email, password, gender[MALE or FEMALE].");
                        break;
                    case 3:
                        System.out.println("You forgot to fill password, gender[MALE or FEMALE].");
                        break;
                    case 4:
                        System.out.println("You forgot to fill gender[MALE or FEMALE].");
                        break;
                }
                userRegister();
            } else if (userData.length > 5) {
                System.out.println("Incorrect values!!! Please, try again.");
                userRegister();
            } else {
                Gender gender = Gender.valueOf(userData[4].toUpperCase());
                User user = new User(userData[0], userData[1], userData[2], userData[3], gender);
                USER_STORAGE.add(user);
                System.out.println("You are successfully registered.");
            }
        } catch (ModelExistingException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Please, input MALE or FEMALE on gender input.");
        }
    }

    private static void userLogin() {
        if (USER_STORAGE.isEmpty()) {
            System.out.println("Users list is EMPTY. Please REGISTER first.");
            return;
        }
        System.out.println("Please, input EMAIL and PASSWORD for login.");
        try {
            String userDataStr = SCANNER.nextLine();
            String[] userData = userDataStr.split(",");
            if (userDataStr.equals("")){
                System.out.println("You forgot to fill all fields.");
                userLogin();
            } else if (userData.length == 1){
                System.out.println("You forgot to fill password.");
                userLogin();
            } else if (userData.length > 2){
                System.out.println("Incorrect values!!! Please, try again.");
                userLogin();
            } else {
                currentUser = USER_STORAGE.getUserByEmailAndPassword(userData[0], userData[1]);
                System.out.println(String.format("Welcome %s %s. You are successfully logged in.", currentUser.getName(), currentUser.getSurname()));
                System.out.println("------------------------------------------------------------------------------------");
                System.out.println("Choose your command.");
                userCom();
            }
        } catch (ModelNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchPostByCategory() {
        if (POST_STORAGE.isEmpty()) {
            System.out.println("Posts list is EMPTY. Please add post first.");
            return;
        }
        System.out.println("Input category (ART, TECHNOLOGY, MEDICINE, POLITICS) to see all post with searched category.");
        try {
            String category = SCANNER.nextLine();
            if (category.equals("")) {
                System.out.println("You forgot to enter searched category (ART, TECHNOLOGY, MEDICINE, POLITICS).");
                searchPostByCategory();
            } else if (category.contains(",") || category.contains(" ")) {
                System.out.println("You have to enter only one category (ART, TECHNOLOGY, MEDICINE, POLITICS).");
                searchPostByCategory();
            } else {
                POST_STORAGE.printPostsByCategory(Category.valueOf(category.toUpperCase()));
            }
        } catch (ModelNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("The CATEGORY which you entered does not exist. Plese choose another.");
            searchPostByCategory();
        }
    }

    private static void searchPostByKeyword() {
        //ստեղ ստորակետով գրելու պահը չեմ ստուգել, որովհետև հաշվել եմ որ կարող է post-ի title-ի մեջ ստորակետ էղնի

        if (POST_STORAGE.isEmpty()) {
            System.out.println("Posts list is EMPTY. Please add post first.");
            return;
        }
        System.out.println("Input ITEM for searching by keyword.");
        try {
            String keyword = SCANNER.nextLine();
            if (keyword.equals("")) {
                System.out.println("You forgot to enter searched keyword.");
                searchPostByKeyword();
            } else {
                POST_STORAGE.searchPostsByKeyword(keyword);
            }
        } catch (ModelNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addPost() {
        System.out.println("Please input all info about post: (title, text, category[ART, TECHNOLOGY, MEDICINE, POLITICS]).");
        try {
            String postDataStr = SCANNER.nextLine();
            String[] postData = postDataStr.split(",");
            if (postDataStr.equals("")) {
                System.out.println("You forgot to fill all fields.");
                addPost();
            } else if (postData.length < 3) {
                switch (postData.length) {
                    case 1:
                        System.out.println("You forgot to fill text and category[ART, TECHNOLOGY, MEDICINE, POLITICS].");
                        break;
                    case 2:
                        System.out.println("You forgot to fill category[ART, TECHNOLOGY, MEDICINE, POLITICS].");
                        break;
                }
                addPost();
            } else if (postData.length > 3) {
                System.out.println("Incorrect values!!! Please, try again.");
                addPost();
            } else {
                Date date = new Date();
                Category category = Category.valueOf(postData[2].toUpperCase());
                Post post = new Post(postData[0], postData[1], category, date, currentUser);
                POST_STORAGE.add(post);
                System.out.println("Post was added.");
            }
        } catch (ModelExistingException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("The CATEGORY, which you entered does not exist.");
            addPost();
        }
    }

}
