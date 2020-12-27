
public class XO {

    public final int EMPTY = 0;
    public final int CROSS = 1;
    public final int NOUGHT = 2;

    // Name-constants to represent the various states of the game
    public final int PLAYING = 0;
    public final int DRAW = 1;
    public final int CROSS_WON = 2;
    public final int NOUGHT_WON = 3;

    // The game cell and the game status
    public int[] cell = new int[10];
    //  containing (EMPTY, CROSS, NOUGHT)
    public int currentState;  // the current state of the game
    // (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)

    public int currentCell; // current seed's row and column


    /**
     * Initialize the game-cell contents and the current states
     */
    public void initGame() {
        for (int i = 1; i < 10; ++i) {
            cell[i] = EMPTY;  // all cells empty
        }
        currentState = PLAYING; // ready to play
    }

    /**
     * Player with the "theSeed" makes one move, with input validation.
     * Update global variables "currentRow" and "currentCol".
     */
    public boolean playerMove(int theSeed, int cellNum) {
        boolean validInput = false;  // for input validation
        if (cell[cellNum] == EMPTY) {
            cell[cellNum] = theSeed;  // update game-cell content
            System.out.println("valid cell selection " + theSeed);
            validInput = true;  // input okay, exit loop
        } else {
            System.out.println("invalid cell selection " + theSeed);
            validInput = false;
        }
        return validInput;
    }


    /**
     * Update the "currentState" after the player with "theSeed" has placed on
     * (currentRow, currentCol).
     */
    public void updateGame(int theSeed, int currentCell) {
        if (hasWon(theSeed, currentCell)) {  // check if winning move
            currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
        } else if (isDraw()) {  // check for draw
            currentState = DRAW;
        }
        // Otherwise, no change to currentState (still PLAYING).
    }

    /**
     * Return true if it is a draw (no more empty cell)
     */
    // TODO: Shall declare draw if no player can "possibly" win
    public boolean isDraw() {
        for (int i = 1; i < 10; ++i) {
            if (cell[i] == EMPTY) {
                return false;  // an empty cell found, not draw, exit
            }
        }
        return true;  // no empty cell, it's a draw
    }

    /**
     * Return true if the player with "theSeed" has won after placing at
     * (currentRow, currentCol)
     */
    public boolean hasWon(int theSeed, int currentCell) {
        boolean won = false;
        switch (currentCell) {
            case 1:
                if ((cell[1] == cell[2]) && (cell[3] == cell[2])
                        || (cell[1] == cell[4]) && (cell[4] == cell[7])
                        || (cell[1] == cell[5]) && (cell[5] == cell[9])) won = true;
                break;
            case 2:
                if ((cell[1] == cell[2]) && (cell[3] == cell[2])
                        || (cell[2] == cell[5]) && (cell[5] == cell[8])) won = true;
                break;

            case 3:
                if ((cell[1] == cell[2]) && (cell[3] == cell[2])
                        || (cell[3] == cell[5]) && (cell[5] == cell[7])
                        || (cell[3] == cell[6]) && (cell[6] == cell[9])) won = true;
                break;

            case 4:
                if ((cell[1] == cell[4]) && (cell[4] == cell[7])
                        || (cell[4] == cell[5]) && (cell[5] == cell[6])) won = true;
                break;

            case 5:
                if ((cell[5] == cell[2]) && (cell[5] == cell[8])
                        || (cell[5] == cell[4]) && (cell[4] == cell[6])
                        || (cell[1] == cell[5]) && (cell[5] == cell[9])
                        || (cell[3] == cell[5]) && (cell[5] == cell[7])) won = true;
                break;

            case 6:
                if ((cell[3] == cell[6]) && (cell[6] == cell[9])
                        || (cell[6] == cell[4]) && (cell[4] == cell[5])) won = true;
                break;

            case 7:
                if ((cell[7] == cell[8]) && (cell[8] == cell[9])
                        || (cell[1] == cell[4]) && (cell[4] == cell[7])
                        || (cell[7] == cell[5]) && (cell[7] == cell[3])) won = true;
                break;

            case 8:
                if ((cell[8] == cell[5]) && (cell[8] == cell[2])
                        || (cell[8] == cell[7]) && (cell[8] == cell[9])) won = true;
                break;

            case 9:
                if ((cell[9] == cell[8]) && (cell[8] == cell[7])
                        || (cell[3] == cell[6]) && (cell[6] == cell[9])
                        || (cell[1] == cell[5]) && (cell[5] == cell[9])) won = true;
                break;
        }
        System.out.println("has won" + theSeed + " " + won);
        return won;
    }
}



