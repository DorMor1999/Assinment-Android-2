package com.example.assignment_android_2.Logic;

public class GameManager {
    private final String NONE = "none";
    private final String BARRIER = "barrier";
    private final String COINS = "coins";

    private String[][] matrix;

    private int lives;
    private int numCrushes = 0;
    private int points = 0;
    private int carPosition; // 0 = left, 1 = mid, 2 = right
    private final int matrixRows;
    private final int matrixCols;
    private final int CHANCE_FOR_COINS = 5;

    public GameManager(int lives, int matrixRows, int matrixCols) {
        this.lives = lives;
        this.matrixRows = matrixRows;
        this.matrixCols = matrixCols;
        this.carPosition = matrixCols / 2;
        initMatrix();
    }

    private void initMatrix(){
        this.matrix = new String[getMatrixRows()][getMatrixCols()];
        for (int i = 0; i < getMatrixRows(); i++) {
            for (int j = 0; j < getMatrixCols(); j++) {
                this.matrix[i][j] = getNONE();
            }
        }
    }

    public int getNumCrushes() {
        return numCrushes;
    }

    public void setNumCrushes(int numCrushes) {
        this.numCrushes = numCrushes;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getMatrixRows() {
        return matrixRows;
    }

    public int getMatrixCols() {
        return matrixCols;
    }

    public String getNONE() {
        return NONE;
    }
    public String getCOINS() {
        return COINS;
    }


    public String getBARRIER() {
        return BARRIER;
    }


    public int getCarPosition() {
        return carPosition;
    }

    public int getLives() {
        return lives;
    }

    public int getPoints() {
        return points;
    }

    public void setCarPosition(int carPosition) {
        this.carPosition = carPosition;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public boolean isGameLost() {
        return getLives() == 0;
    }

    public void moveCar(String direction) {
        if (direction.equals("left")) {
            if (getCarPosition() == 0) {
                return;
            } else {
                setCarPosition(getCarPosition() - 1);
            }
        } else {
            if (getCarPosition() == getMatrixCols() - 1) {
                return;
            } else {
                setCarPosition(getCarPosition() + 1);
            }
        }
    }


    public void matrixChangePeriod() {
        for (int i = getMatrixRows() - 1; i > -1; i--) {
            for (int j = getMatrixCols() - 1; j > -1; j--) {
                // clean first row
                if (i == 0) {
                    getMatrix()[i][j] = getNONE();
                }
                //move row above to down row
                else {
                    getMatrix()[i][j] = getMatrix()[i - 1][j];
                }
            }
        }

        //fill top row add new barrier or 2 barriers if they get the same number i adding 1 barrier if they get different number i adding 2 barriers
        int randomNumberCol1 = (int) (Math.random() * getMatrixCols()); // Generates a random number between 0 (inclusive) and matrixCols (exclusive)
        int randomNumberCol2 = (int) (Math.random() * getMatrixCols()); // Generates a random number between 0 (inclusive) and matrixCols (exclusive)
        getMatrix()[0][randomNumberCol1] = getBARRIER();
        getMatrix()[0][randomNumberCol2] = getBARRIER();


        //fill top row in chance of 1/5 with coins 0-CHANCE_FOR_COINS range
        int bet = (int) (Math.random() * CHANCE_FOR_COINS); // Generates a random number between 0 (inclusive) and CHANCE_FOR_COINS (exclusive)
        int result = (int) (Math.random() * CHANCE_FOR_COINS); // Generates a random number between 0 (inclusive) and CHANCE_FOR_COINS (exclusive)
        if (bet == result){
            int randomNumberCol3;
            do {
                randomNumberCol3 = (int) (Math.random() * getMatrixCols()); // Generates a random number between 0 (inclusive) and matrixCols (exclusive)
            }while (randomNumberCol3 == randomNumberCol1 || randomNumberCol3 == randomNumberCol2);
            getMatrix()[0][randomNumberCol3] = getCOINS();
        }
    }


    public boolean checkCrushAndUpdateLivesAndNumCrushes() {
        if (getMatrix()[getMatrixRows() - 2][getCarPosition()].equals(getBARRIER())) {
            setLives(getLives() - 1);
            setNumCrushes(getNumCrushes() + 1);
            return true;
        }
        return false;
    }

    public void checkPointsAndUpdatePoints(){
        points++;
        if(getMatrix()[getMatrixRows() - 2][getCarPosition()].equals(getCOINS())){
            points += 5;
        }
    }
}
