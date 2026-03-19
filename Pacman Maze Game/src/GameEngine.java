/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

import java.util.Scanner;

public class GameEngine {

    public static Scanner keyboard = new Scanner(System.in);

    private int mazeLength;
    private int mazeWidth;
    private long seed;
    private Maze maze;
    private String record = "";
    private int gameNumber = 0;
    private boolean mainMenuSelection = true;

    public static void main(String[] args) {

        GameEngine engine = new GameEngine();

        //to ensure the args input is legal or valid
        if (args.length == Constants.VALID_ARGS_LENGTH && Integer.parseInt(args[Constants.FIRST_ARGS]) > 0 && Integer.parseInt(args[Constants.SECOND_ARGS]) > 0 && Long.parseLong(args[Constants.THIRD_ARGS]) > 0) {
            engine.mazeLength = Integer.parseInt(args[Constants.FIRST_ARGS]);
            engine.mazeWidth = Integer.parseInt(args[Constants.SECOND_ARGS]);
            engine.seed = Long.parseLong(args[Constants.THIRD_ARGS]);
            engine.welcomeMessage();
        } else {
            System.out.println("Invalid Inputs to set layout. Exiting the program now.");
        }
    }

    //Constructor

    public GameEngine() {
    }

    //Methods

    private void welcomeMessage() {
        System.out.println(" ____         __          ___        _  _         __         __ _ \n" +
                "(  _ \\       / _\\        / __)      ( \\/ )       / _\\       (  ( \\\n" +
                " ) __/      /    \\      ( (__       / \\/ \\      /    \\      /    /\n" +
                "(__)        \\_/\\_/       \\___)      \\_)(_/      \\_/\\_/      \\_)__)\n");
        System.out.println("Let the fun begin\n"+"(`<    ...   ...  ...  ..........  ...\n");
        mainMenuSelect();
    }

    private void mainMenuPrompt() {
        System.out.print("Select an option to get started.\n" +
                "Press 1 to select a pacman maze type.\n" +
                "Press 2 to play the game.\n" +
                "Press 3 to resume the game.\n" +
                "Press 4 to view the scores.\n" +
                "Press 5 to exit.\n" +
                "> ");
    }

    // user select from the main menu
    private void mainMenuSelect() {
        while (mainMenuSelection) {
            mainMenuPrompt();
            String userSelection = keyboard.nextLine();
            switch (userSelection) {
                case Constants.SELECT_MAIN_MENU_ONE:
                    mazeTypeSelect();
                    break;
                case Constants.SELECT_MAIN_MENU_TWO:
                    startGame();
                    break;
                case Constants.SELECT_MAIN_MENU_THREE:
                    gameResume();
                    break;
                case Constants.SELECT_MAIN_MENU_FOUR:
                    scoreView();
                    break;
                case Constants.SELECT_MAIN_MENU_FIVE:
                    System.out.println("Pacman says - Bye Bye Player.");
                    mainMenuSelection = false;
                    break;
                default:
                    System.out.println("Invalid Input.");
                    break;
            }
        }
    }

    // main menu selection one: to generate a maze
    private void mazeTypeSelect() {
        System.out.println("Please select a maze type.\n" +
                "Press 1 to select lower triangle maze.\n" +
                "Press 2 to select upper triangle maze.\n" +
                "Press 3 to select horizontal maze.");
        System.out.print("> ");
        int mazeType = Integer.parseInt(keyboard.nextLine());
        if (Constants.INVALID_MAZE_TYPE_MIN < mazeType && mazeType < Constants.INVALID_MAZE_TYPE_MAX) {
            maze = new Maze(mazeLength, mazeWidth, seed, mazeType);
            System.out.println("Maze created. Proceed to play the game.");
        } else {
            System.out.println("Invalid Input.");
            mazeTypeSelect();
        }
    }

    // main menu selection two: to start a game
    private void startGame() {
        //Scenario 1: Maze Type not selected
        if (maze == null) {
            mazeNotCreated();
            return;
        }
        //Scenario 2: Maze Type selected new game started
        else if (maze.isGamePending()) {
            System.out.print("Previous game hasn't ended yet. Do you want to discard previous game?\n" +
                    "Press N to go back to main menu to resume the game or else press any key to discard.\n" +
                    "> ");
            String choiceOfStatus = keyboard.nextLine();
            if (choiceOfStatus.equals("N")) {
                return;
            } else {
                maze.gameRenew();
                maze.gameRuleInstruction();
                gameNumber += 1;
                maze.movePacmanPrompt();
            }
        } else {
            maze.gameRuleInstruction();
            gameNumber += 1;
            maze.movePacmanPrompt();
        }
        gameOverRecord();
    }

    // main menu selection three: to resume the game
    private void gameResume() {
        if (maze == null) {
            mazeNotCreated();
            return;
        } else {
            if (maze.isGamePending()) {
                System.out.println("Restart your game from the last position you saved.");
                maze.setGamePending(false);
                maze.movePacmanPrompt();
            } else {
                System.out.println("No paused game found. Select option 2 from main menu to start a new game.");
                return;
            }
        }
        gameOverRecord();
    }

    private void mazeNotCreated(){
        System.out.println("Maze not created. Select option 1 from main menu.");
    }

    // main menu selection four: to resume the game
    private void gameOverRecord() {
        while(maze.isGameComplete()){
            record = new StringBuilder(record)
                    .append(String.format("|%8d|%8d|%8d|%8.2f|%n", gameNumber, maze.getHit(), maze.getMoveStep(), maze.getScore()))
                    .toString();
            maze.gameRenew();
        }
    }

    private void scoreView() {
        if (record.isEmpty()) {
            System.out.println("No completed games found.");
        } else {
            System.out.println("|  # Game|  # Hits| # Moves| # Score|\n"+"|========|========|========|========|");
            System.out.print(record);
        }
    }


}


