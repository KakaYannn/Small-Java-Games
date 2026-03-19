/*WARNING: This class shouldnt be moved to any package*/
//NOTE of todolist:
//1.Magic Number and String
//2. high cohesion and low coupling
//3.javadoc
//4.
//5.
//6.


import java.util.Scanner;

public class GameEngine {

    public static Scanner keyboard = new Scanner(System.in);
    private static int gameCount = 0;

    private String gameMode;
    private int mazeLength;
    private int mazeWidth;
    private int mazeType;
    private long seed;
    private Maze mazeOfPlayer1;
    private Maze mazeOfPlayer2;
    private Maze currentMaze;
    private Maze[] games = new Maze[1];
    private int gameOnBoard = 0;

    public static void main(String[] args) {

        GameEngine engine = new GameEngine();

        if (engine.isInvalidArgs(args)) {
            System.out.println("Invalid Inputs to set layout. Exiting the program now.");
        } else {
            engine.mazeLength = Integer.parseInt(args[0]);
            engine.mazeWidth = Integer.parseInt(args[1]);
            engine.seed = Long.parseLong(args[2]);
            engine.displayMessage();
            // write rest of the code here.
            engine.playerSelection();
        }
    }

    public GameEngine() {
    }

    private void playerSelection() {
        while (true) {
            System.out.print("Make player selection.\n" +
                    "Press 1 for Single Player.\n" +
                    "Press 2 for Multi Player.\n" +
                    "Press 3 to exit.\n" +
                    "> ");
            String selection = keyboard.nextLine();
            switch (selection) {
                case ("1"):
                case ("2"):
                    if (selection.equals("1")) {
                        gameMode = "singlePlayer";
                        //gameCount+=1;
                    } else {
                        gameMode = "multiPlayer";
                        //gameCount+=1;
                    }
                    currentMaze = null;
                    mainMenuSelect();
                    break;
                case ("3"):
                    System.out.println("Pacman says - Bye Bye Player.");
                    return;
                default:
                    System.out.println("Invalid Input.");
            }
        }
    }

    private void printMenu() {
        System.out.println("Select an option to get started.");
        System.out.println("Press 1 to select a pacman maze type.");
        System.out.println("Press 2 to play the game.");
        System.out.println("Press 3 to resume the game.");
        System.out.println("Press 4 to view the scores.");
        System.out.println("Press 5 to exit.");
        System.out.print("> ");
    }

    private boolean isInvalidArgs(String[] args) {
        boolean invalidInput = false;
        if (args.length != 3) {
            invalidInput = true;
        }
        for (int i = 0; i < args.length; i++) {
            if (Integer.parseInt(args[i]) <= 0) {
                invalidInput = true;
            }
        }
        return invalidInput;
    }

    private void displayMessage() {
        System.out.println(" ____         __          ___        _  _         __         __ _ \n" +
                "(  _ \\       / _\\        / __)      ( \\/ )       / _\\       (  ( \\\n" +
                " ) __/      /    \\      ( (__       / \\/ \\      /    \\      /    /\n" +
                "(__)        \\_/\\_/       \\___)      \\_)(_/      \\_/\\_/      \\_)__)");
        System.out.println("");
        System.out.println("Let the fun begin");
        System.out.println("(`<    ...   ...  ...  ..........  ...");
        System.out.println("");
    }

    private void mainMenuSelect() {
        while (true) {
            printMenu();
            String selection = keyboard.nextLine();
            switch (selection) {
                case ("1"):
                    mazeCreate();
                    System.out.println("Maze created. Proceed to play the game.");
                    break;
                case ("2"):
                    startGame();
                    break;
                case ("3"):
                    gameResume();
                    break;
                case ("4"):
                    scoreView();
                    break;
                case ("5"):
                    System.out.println("Exiting main menu, return to Player Selection.");
                    return;
                default:
                    System.out.println("Invalid Input.");
            }
        }
    }

    private void mazeCreate() {
        System.out.println("Please select a maze type.\n" +
                "Press 1 to select lower triangle maze.\n" +
                "Press 2 to select upper triangle maze.\n" +
                "Press 3 to select horizontal maze.");
        System.out.print("> ");
        String command = keyboard.nextLine();
        if (command.equals("1") || command.equals("2") || command.equals("3")) {
            mazeType = Integer.parseInt(command);
            gameCount += 1;
            mazeOfPlayer1 = new Maze(mazeType, mazeLength, mazeWidth, seed, "Player 1", gameCount);
            currentMaze = mazeOfPlayer1;
            if (gameMode.equals("multiPlayer")) {
                mazeOfPlayer2 = new Maze(mazeType, mazeLength, mazeWidth, seed, "Player 2", gameCount);
            }
        } else {
            System.out.println("Invalid Input.");
            mazeCreate();
        }
    }

    private void startGame() {
        //Scenario 1: Maze Type not selected
        if (currentMaze == null) {
            System.out.println("Maze not created. Select option 1 from main menu.");
            return;
        }
        //Scenario 2: Maze Type selected new game started
        else if (currentMaze.isGamePending()) {
            System.out.print("Previous game hasn't ended yet. Do you want to discard previous game?\n" +
                    "Press N to go back to main menu to resume the game or else press any key to discard.\n" +
                    "> ");
            String choiceOfStatus = keyboard.nextLine();
            if (choiceOfStatus.equalsIgnoreCase("N")) {
                return;
            } else {
                mazeCreate();
                System.out.println("New maze created. Proceed to play the game.");
                startGame();
                //return;
            }
        } else {
            System.out.println("Move the Pacman towards the food pellet and gain super power to kill monsters.\n" +
                    "  > You gain 20 points if Pacman finishes the game without dying.\n" +
                    "  > You gain 10 more points for every monster you killed.\n" +
                    "  > You gain 5 points for every special food that you have eaten.\n" +
                    "  > You lose 0.5 point when you hit the wall/boundary.\n" +
                    "  > You lose 0.25 points for every move.\n" +
                    "  > Score = 5 * foodEaten + 10 * monsterKilled - 0.5 * numOfHits - 0.25 * numOfMoves  +  20 if not dead.");
            if (currentMaze == mazeOfPlayer1) {
                System.out.println("Player 1 game begins.");
            } else {
                System.out.println("Player 2 game begins.");
            }
            currentMaze.mazePrint();
        }
        gameOverCheck();
        multiModeSwitch();
        //currentMaze=null;
    }

    // main menu selection three: to resume the game
    private void gameResume() {
        if (currentMaze == null) {
            System.out.println("Maze not created. Select option 1 from main menu.");
            return;
        } else {
            if (currentMaze.isGamePending()) {
                System.out.println("Restart your game from the last position you saved.");
                currentMaze.setGamePending(false);
                if (currentMaze == mazeOfPlayer1) {
                    System.out.println("Player 1 game begins.");
                } else {
                    System.out.println("Player 2 game begins.");
                }
                currentMaze.mazePrint();

            } else {
                System.out.println("No paused game found. Select option 2 from main menu to start a new game.");
                return;
            }
        }
        gameOverCheck();
        multiModeSwitch();
        //currentMaze=null;
    }

    private void multiModeSwitch() {
        if (gameMode.equals("multiPlayer") && mazeOfPlayer1.isGameOver()) {
            if (mazeOfPlayer2.isGameOver()) {
                currentMaze = null;
                if (mazeOfPlayer2.getScore() > mazeOfPlayer1.getScore()) {
                    System.out.println("Player 2 wins. Returning to main menu.");
                } else {
                    System.out.println("Player 1 wins. Returning to main menu.");
                }
            } else if (currentMaze == mazeOfPlayer1) {
                currentMaze = mazeOfPlayer2;
                startGame();
            }
        }
    }

    // main menu selection four: to print the score
    private void gameOverCheck() {
        if (currentMaze == null) {
            return;
        }
        if (currentMaze.isGameOver()) {
            if (gameMode.equals("multiPlayer")) {
                if (currentMaze == mazeOfPlayer1) {
                    //currentMaze = null;
                    return;
                } else {
                    gameOnBoard += 1;
                    if (gameOnBoard > games.length) {
                        Maze[] tmp = new Maze[2 * games.length];
                        for (int i = 0; i < games.length; i++) {
                            tmp[i] = games[i];
                        }
                        games = tmp;
                    }
                    if (mazeOfPlayer1.getScore() >= mazeOfPlayer2.getScore()) {
                        games[gameOnBoard - 1] = mazeOfPlayer1;
                        currentMaze = null;
                    } else {
                        games[gameOnBoard - 1] = mazeOfPlayer2;
                        currentMaze = null;
                    }
                }
            } else {
                gameOnBoard += 1;
                if (gameOnBoard > games.length) {
                    Maze[] tmp = new Maze[2 * games.length];
                    for (int i = 0; i < games.length; i++) {
                        tmp[i] = games[i];
                    }
                    games = tmp;
                }
                games[gameOnBoard - 1] = currentMaze;
                currentMaze = null;
            }
        }
    }

    private void scoreView() {
        if (gameOnBoard == 0) {
            System.out.println("No completed games found.");
        } else {
            System.out.println("|  # Game|    Player Name|   # Food Eaten|# Monster Killed|  # Hits| # Moves| # Score|\n" +
                    "|========|===============|===============|================|========|========|========|");
            for (Maze maze : games) {
                if (maze == null) {
                    break;
                }
                System.out.print(maze.scoreDisplay());
            }
        }
    }


}

