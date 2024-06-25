package com.example.assignment_android_2.Logic;

public class GameManager {
    private final String NONE = "none";
    private final String BARRIER = "barrier";

    private String[][] matrix;

    private int lives;
    private int numCrushes = 0;

    private int carPosition; // 0 = left, 1 = mid, 2 = right
    private final int matrixRows;
    private final int matrixCols;

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


    public String getBARRIER() {
        return BARRIER;
    }


    public int getCarPosition() {
        return carPosition;
    }

    public int getLives() {
        return lives;
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

        //fill top row add new barrier
        int randomNumberCol = (int) (Math.random() * getMatrixCols()); // Generates a random number between 0 (inclusive) and matrixCols (exclusive)
        getMatrix()[0][randomNumberCol] = getBARRIER();


    }


    public boolean checkCrushAndUpdateLivesAndNumCrushes() {
        if (getMatrix()[getMatrixRows() - 2][getCarPosition()].equals(getBARRIER())) {
            setLives(getLives() - 1);
            setNumCrushes(getNumCrushes() + 1);
            return true;
        }
        return false;
    }
}
