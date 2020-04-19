public interface Command {

    //main commands
    int EXIT = 0;
    int SEARCH_POST = 1;
    int POSTS_BY_CATEGORY = 2;
    int ALL_POSTS = 3;
    int LOGIN = 4;
    int REGISTER = 5;

    // user commands
    int ADD_POST = 0;
    int DELETE_POST = 4;
    int LOGOUT = 5;


    static void printMainCommands() {
        System.out.println("Input " + EXIT + " for EXIT.");
        System.out.println("Input " + SEARCH_POST + " for SEARCHING POST BY KEYWORD.");
        System.out.println("Input " + POSTS_BY_CATEGORY + " for SEARCHING POST BY CATEGORY.");
        System.out.println("Input " + ALL_POSTS + " for PRINTING ALL POSTS.");
        System.out.println("Input " + LOGIN + " for LOGIN.");
        System.out.println("Input " + REGISTER + " for REGISTER.");
    }

    static void printUserCommands() {
        System.out.println("Input " + ADD_POST + " for ADDING POST.");
        System.out.println("Input " + SEARCH_POST + " for SEARCHING POST BY KEYWORD.");
        System.out.println("Input " + POSTS_BY_CATEGORY + " for SEARCHING POST BY CATEGORY.");
        System.out.println("Input " + ALL_POSTS + " for PRINTING ALL POSTS.");
        System.out.println("Input " + DELETE_POST + " for DELETING MY POST");
        System.out.println("Input " + LOGOUT + " for LOGOUT.");
    }
}
