public class Maze {

    /* maze details */
    private int mazeType;
    private int mazeLength;
    private int mazeWidth;
    private char[][] maze;

    private enum Monster {RED, BLUE, GREEN, YELLOW}

    /* define other data variables here */
    private boolean gamePending;
    /*pacman position*/
    private int colPacman = 1;
    private int rowPacman = 1;

    /*score/game related attributes*/
    private int hit = 0;
    private int moveStep = 0;
    private double score = 0;
    private int superPower = 0;
    private int foodCollected = 0;
    private int monsterKilled = 0;
    private boolean gameOver;
    private String playerName;
    private int gameID;

    //private boolean gameComplete = false;


    public Maze() {
    }

    public Maze(int mazeType, int mazeLength, int mazeWidth, long seed, String playerName, int gameID) {
        // intialise the maze here
        this.mazeLength = mazeLength;
        this.mazeWidth = mazeWidth;
        this.mazeType = mazeType;
        this.playerName = playerName;
        //gameCount+=1;
        this.gameID = gameID;

        maze = new char[mazeWidth][mazeLength];
        for (int row = 0; row < mazeWidth; row++) {
            for (int col = 0; col < mazeLength; col++) {
                if (row == 0 || row == mazeWidth - 1 || col == 0 || col == mazeLength - 1) {
                    maze[row][col] = '#';
                } else if (col == 1 && row == 1) {
                    maze[row][col] = 'P';
                } else if ((mazeType == 1 && col > row) || (mazeType == 2 && col < row) ||
                        (mazeType == 3 && (row % 2 == 0 && col != 1 && col != mazeLength - 2))) {
                    maze[row][col] = '-';
                } else {
                    maze[row][col] = '.';
                }
            }
        }
        //add the position of all the entities
        LocationGenerator generator = new LocationGenerator(seed);
        for (Monster m : Monster.values()) {
            generatePosition(generator, String.valueOf(m));
        }
        for (int food = 0; food < 4; food++) {
            generatePosition(generator, "*");
        }
    }

    public boolean isGamePending() {
        return gamePending;
    }

    public void setGamePending(boolean gamePending) {
        this.gamePending = gamePending;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public double getScore() {
        return score;
    }

    private void generatePosition(LocationGenerator generator, String symbol) {
        while (true) {
            int colPos = generator.generatePosition(1, this.mazeLength - 2);
            int rowPos = generator.generatePosition(2, this.mazeWidth - 2);
            if (this.maze[rowPos][colPos] == '.') {
                // set the Monster or foods location
                maze[rowPos][colPos] = symbol.charAt(0);
                // set the position of monster or food in maze, set it with correct symbol
                break;
            }
        }
    }

    public void mazePrint() {
        for (int row = 0; row < mazeWidth; row++) {
            for (int col = 0; col < mazeLength; col++) {
                System.out.print(maze[row][col]);
            }
            System.out.println();
        }
        movePacman();

    }

    private void movePacman() {
        System.out.print("Press W to move up.\n" +
                "Press A to move left.\n" +
                "Press S to move down.\n" +
                "Press D to move right.\n" +
                "Press Q to exit.\n" +
                "> ");

        String movePrompt = GameEngine.keyboard.nextLine().toUpperCase();
        int nextPacmanRow = rowPacman;
        int nextPacmanCol = colPacman;

        switch (movePrompt) {
            case ("W"):
                nextPacmanRow -= 1;
                break;
            case ("A"):
                nextPacmanCol -= 1;
                break;
            case ("S"):
                nextPacmanRow += 1;
                break;
            case ("D"):
                nextPacmanCol += 1;
                break;
            case ("Q"):
                gamePause();
                return;
            default:
                System.out.println("Invalid Input.");
                break;
        }

        if (isBoundaryHit(nextPacmanRow, nextPacmanCol)) {
            pacmanHitBoundary();
        } else if (isFoodCollected(nextPacmanRow, nextPacmanCol)) {
            SpecialFoodCollected(nextPacmanRow, nextPacmanCol);
        } else if (isHitWall(nextPacmanRow, nextPacmanCol)) {
            pacmanHitWall();
        } else if (isNormalMove(nextPacmanRow, nextPacmanCol)) {
            normalMove(nextPacmanRow, nextPacmanCol);
        } else if (isMeetMonster(nextPacmanRow, nextPacmanCol)) {
            meetMonster(nextPacmanRow, nextPacmanCol);
        } else {
            mazePrint();
        }
    }

    // to see if Pacman hit the boundary, and if yes, what to do next

    private boolean isBoundaryHit(int nextPacmanRow, int nextPacmanCol) {
        return nextPacmanRow == 0 || nextPacmanRow == mazeWidth - 1 || nextPacmanCol == mazeLength - 1 || nextPacmanCol == 0;
    }

    private void pacmanHitBoundary() {
        System.out.println("You have hit the boundary.");
        hit += 1;
        score -= 0.5;
        mazePrint();
    }


    // to see if Pacman hit the wall, and if yes, what to do next.

    private boolean isHitWall(int nextPacmanRow, int nextPacmanCol) {
        return (mazeType == 1 && nextPacmanCol > nextPacmanRow) || (mazeType == 2 && nextPacmanCol < nextPacmanRow) ||
                (mazeType == 3 && nextPacmanRow % 2 == 0 && (nextPacmanCol != 1 && nextPacmanCol != (mazeLength - 2)));
    }

    private void pacmanHitWall() {
        System.out.println("You have hit a wall.");
        hit += 1;
        score -= 0.5;
        mazePrint();
    }

    // to see if Pacman collect food, and if yes, what to do next

    private boolean isFoodCollected(int nextPacmanRow, int nextPacmanCol) {
        return maze[nextPacmanRow][nextPacmanCol] == '*';
    }

    private void SpecialFoodCollected(int nextPacmanRow, int nextPacmanCol) {
        System.out.println("Power up!");
        moveStep += 1;
        foodCollected += 1;
        superPower += 1;
        maze[nextPacmanRow][nextPacmanCol] = 'P';
        maze[rowPacman][colPacman] = '.';
        rowPacman = nextPacmanRow;
        colPacman = nextPacmanCol;
        score = score - 0.25 + 5;
        mazePrint();
    }

    //normal move
    private boolean isNormalMove(int nextPacmanRow, int nextPacmanCol) {
        return maze[nextPacmanRow][nextPacmanCol] == '.';
    }

    private void normalMove(int nextPacmanRow, int nextPacmanCol) {
        moveStep += 1;
        maze[nextPacmanRow][nextPacmanCol] = 'P';
        maze[rowPacman][colPacman] = '.';
        rowPacman = nextPacmanRow;
        colPacman = nextPacmanCol;
        score = score - 0.25;
        mazePrint();
    }

    //kill monster
    private boolean isMeetMonster(int nextPacmanRow, int nextPacmanCol) {
        return !(colPacman == nextPacmanCol && rowPacman == nextPacmanRow);
    }

    private void meetMonster(int nextPacmanRow, int nextPacmanCol) {
        if (superPower > 0) {
            System.out.println("Hurray! A monster is killed.");
            superPower -= 1;
            moveStep += 1;
            monsterKilled += 1;
            score = score - 0.25 + 10;
            maze[nextPacmanRow][nextPacmanCol] = 'P';
            maze[rowPacman][colPacman] = '.';
            rowPacman = nextPacmanRow;
            colPacman = nextPacmanCol;
            if (monsterKilled == 4) {
                score += 20;
                System.out.println("Game has ended! Your score for this game is " + score);
                gameOver = true;
            } else {
                mazePrint();
            }
        } else {
            //moveStep += 1;
            System.out.println("Boo! Monster killed Pacman.");
            System.out.println("Game has ended! Your score for this game is " + score);
            gameOver = true;
        }
    }

    // user choose to pause the game
    private void gamePause() {
        System.out.println("Your game is paused and saved.");
        gamePending = true;
    }

    public String scoreDisplay() {
        return String.format("|%8d|%15s|%15d|%16d|%8d|%8d|%8.2f|%n", gameID, playerName,
                foodCollected, monsterKilled, hit, moveStep, score);
    }
}

