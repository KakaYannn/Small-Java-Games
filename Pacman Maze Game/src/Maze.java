/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

public class Maze {

    /* maze details */
    private int mazeLength;
    private int mazeWidth;
    private long seed;
    private int mazeType;


    /*pacman position*/
    private int colPacman = Constants.PACMAN_INITIAL_COL;
    private int rowPacman = Constants.PACMAN_INITIAL_ROW;


    /*food position*/
    private int colPosFood;
    private int rowPosFood;

    /*score/game related attributes*/
    private boolean gamePending;
    private int hit = 0;
    private int moveStep = 0;
    private double score = Constants.INITIAL_SCORE;
    private boolean gameComplete = false;


    //write a constructor for Maze that invokes the generateFood method with appropriate params if 0 < mazeType < 4.

    public Maze() {
    }

    public Maze(int mazeLength, int mazeWidth, long seed, int mazeType) {
        this.mazeLength = mazeLength;
        this.mazeWidth = mazeWidth;
        this.seed = seed;
        this.mazeType = mazeType;
        generateFood(seed, mazeLength, mazeWidth, mazeType);
    }


    //accessor and mutator

    public boolean isGamePending() {
        return gamePending;
    }

    public void setGamePending(boolean gamePending) {
        this.gamePending = gamePending;
    }

    public boolean isGameComplete() {
        return gameComplete;
    }

    public int getHit() {
        return hit;
    }

    public double getScore() {
        return score;
    }

    public int getMoveStep() {
        return moveStep;
    }


    // methods

    // system print the game rule instruction & maze

    public void gameRuleInstruction() {
        System.out.println("Move the Pacman towards the food pellet.\n" +
                "  > You gain 20 points when Pacman get the food.\n" +
                "  > You lose 0.5 point when you hit the wall/boundary.\n" +
                "  > Score = 20 * Food - 0.5 * hits - 0.25 * moves.");
    }

    private void mazePrint() {
        for (int rowPrint = 0; rowPrint < mazeWidth; rowPrint++) {
            if (rowPrint == 0 || rowPrint == mazeWidth - 1) {
                for (int colPrint = 0; colPrint < mazeLength; colPrint++) {
                    System.out.print("#");
                }
                System.out.println();
            } else {
                for (int colPrint = 0; colPrint < mazeLength; colPrint++) {
                    if (colPrint == 0 || colPrint == mazeLength - 1) {
                        System.out.print("#");
                    } else if (colPrint == colPacman && rowPrint == rowPacman) {
                        System.out.print("P");
                    } else if (colPrint == colPosFood && rowPrint == rowPosFood) {
                        System.out.print("*");
                    } else {
                        if (mazeType == Constants.MAZE_TYPE_ONE) {
                            if (colPrint <= rowPrint) {
                                System.out.print(".");
                            } else {
                                System.out.print("-");
                            }
                        } else if (mazeType == Constants.MAZE_TYPE_TWO) {
                            if (colPrint < rowPrint) {
                                System.out.print("-");
                            } else {
                                System.out.print(".");
                            }
                        } else if (mazeType == Constants.MAZE_TYPE_THREE) {
                            if (rowPrint % Constants.MAZE3_HIT_WALL_EVEN_TWO == 1 || colPrint == 1 || colPrint == mazeLength - Constants.MAZE3_HIT_WALL_EVEN_TWO) {
                                System.out.print(".");
                            } else {
                                System.out.print("-");
                            }
                        }
                    }
                }
                System.out.println();
            }
        }
    }


    // user input a prompt to move the Pacman

    private void movePacmanMenu() {
        System.out.print("Press W to move up.\n" +
                "Press A to move left.\n" +
                "Press S to move down.\n" +
                "Press D to move right.\n" +
                "Press Q to exit.\n" +
                "> ");

    }

    public void movePacmanPrompt() {
        mazePrint();
        movePacmanMenu();

        String movePrompt = GameEngine.keyboard.nextLine().toUpperCase();
        int nextPacmanRow = rowPacman;
        int nextPacmanCol = colPacman;

        switch (movePrompt) {
            case Constants.MOVE_UP:
                nextPacmanRow -= 1;
                break;
            case Constants.MOVE_LEFT:
                nextPacmanCol -= 1;
                break;
            case Constants.MOVE_DOWN:
                nextPacmanRow += 1;
                break;
            case Constants.MOVE_RIGHT:
                nextPacmanCol += 1;
                break;
            case Constants.MAZE_QUIT:
                gamePause();
                return;
            default:
                System.out.println("Invalid Input.");
                break;
        }

        if (isBoundaryHit(nextPacmanRow, nextPacmanCol)) {
            pacmanHitBoundary();
        } else if (isFoodCollected(nextPacmanRow, nextPacmanCol)) {
            gameSucceed(nextPacmanRow, nextPacmanCol);
        } else if (isHitWall(nextPacmanRow, nextPacmanCol)) {
            pacmanHitWall();
        } else if (isValidMove(nextPacmanRow, nextPacmanCol)){
            validMove(nextPacmanRow, nextPacmanCol);
        } else {
            movePacmanPrompt();
        }

    }


    // to see if Pacman hit the boundary, and if yes, what to do next

    private boolean isBoundaryHit(int nextPacmanRow, int nextPacmanCol) {
        return nextPacmanRow == 0 || nextPacmanRow == mazeWidth - 1 || nextPacmanCol == mazeLength - 1 || nextPacmanCol == 0;
    }

    private void pacmanHitBoundary() {
        System.out.println("You have hit the boundary.");
        hit += 1;
        score -= Constants.HIT_SCORE;
        movePacmanPrompt();
    }


    // to see if Pacman collect food, and if yes, what to do next

    private boolean isFoodCollected(int nextPacmanRow, int nextPacmanCol) {
        return nextPacmanRow == rowPosFood && nextPacmanCol == colPosFood;
    }

    private void gameSucceed(int nextPacmanRow, int nextPacmanCol) {
        moveStep += 1;
        rowPacman = nextPacmanRow;
        colPacman = nextPacmanCol;
        score -= Constants.STEP_SCORE;
        mazePrint();
        if(score <= 0){
            score = 0;
        }
        System.out.println("Game has ended! Your score for this game is " + score);
        gameComplete = true;

    }


    // to see if Pacman hit the wall, and if yes, what to do next.

    private boolean isHitWall(int nextPacmanRow, int nextPacmanCol) {
        return switch (mazeType) {
            case Constants.MAZE_TYPE_ONE -> nextPacmanCol > nextPacmanRow;
            case Constants.MAZE_TYPE_TWO -> nextPacmanCol < nextPacmanRow;
            case Constants.MAZE_TYPE_THREE -> nextPacmanRow % Constants.MAZE3_HIT_WALL_EVEN_TWO == 0 &&
                    (nextPacmanCol != 1 && nextPacmanCol != (mazeLength - Constants.MAZE3_HIT_WALL_EVEN_TWO));
            default -> false;
        };
    }

    private void pacmanHitWall() {
        System.out.println("You have hit a wall.");
        hit += 1;
        score -= Constants.HIT_SCORE;
        movePacmanPrompt();
    }

    // pacman valid move
    private boolean isValidMove(int nextPacmanRow, int nextPacmanCol){
        return !(colPacman == nextPacmanCol && rowPacman == nextPacmanRow);
    }

    private void validMove(int nextPacmanRow, int nextPacmanCol) {
        rowPacman = nextPacmanRow;
        colPacman = nextPacmanCol;
        moveStep += 1;
        score -= Constants.STEP_SCORE;
        movePacmanPrompt();
    }


    // user choose to discard the paused game
    public void gameRenew() {
        colPacman = Constants.PACMAN_INITIAL_COL;
        rowPacman = Constants.PACMAN_INITIAL_ROW;
        gamePending = false;
        hit = 0;
        moveStep = 0;
        score = Constants.INITIAL_SCORE;
        gameComplete = false;
    }

    // user choose to pause the game
    private void gamePause() {
        System.out.println("Your game is paused and saved.");
        gamePending = true;
    }


    // this generates position of the special food


    //DO NOT MODIFY THIS CODE
    private void generateFood(long seed, int mazeLength, int mazeWidth, int mazeType) {
        FoodGenerator generator = new FoodGenerator(seed);
        while (true) {
            int xFood = generator.generatePosition(1, mazeLength - 2);
            int yFood = generator.generatePosition(2, mazeWidth - 2);
            if ((mazeType == 1 && xFood <= yFood) || (mazeType == 2 && xFood >= yFood) || (mazeType == 3 && !(yFood % 2 == 0 && xFood != 1 && xFood != mazeLength - 2))) {
                this.colPosFood = xFood;
                this.rowPosFood = yFood;
                break;
            }

        }
    }

}