package com.example.feeling.homework1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by feeling on 1/19/16.
 */
public class Board {
    // circlePlayer indicates if it's circle's turn or not
    private boolean circlePlayer;

    // winFlag indicates if one wins the game
    private boolean winFlag;

    // circlePosition stores the positions of circles
    private List<Integer> circlePosition;

    // crossPosition stores the positions of crosses
    private List<Integer> crossPosition;

    // winRow stores which row has 3 same patterns. Highlight this row afterwards.
    private List<Integer> winRow;

    // cellID stores the id of 9 cells
    private final int[] cellID = {
            R.id.imageButton0, R.id.imageButton1, R.id.imageButton2,
            R.id.imageButton3, R.id.imageButton4, R.id.imageButton5,
            R.id.imageButton6, R.id.imageButton7, R.id.imageButton8};

    // winSituation stores situations when 3 same patterns are in a row
    private final List<List<Integer>> winSituation = new ArrayList<>(Arrays.asList(
            Arrays.asList(0, 1, 2),
            Arrays.asList(3, 4, 5),
            Arrays.asList(6, 7, 8),
            Arrays.asList(0, 3, 6),
            Arrays.asList(1, 4, 7),
            Arrays.asList(2, 5, 8),
            Arrays.asList(0, 4, 8),
            Arrays.asList(2, 4, 6)));


    public Board() {
        circlePlayer = true;
        winFlag = false;
        circlePosition = new ArrayList<>();
        crossPosition = new ArrayList<>();
        winRow = new ArrayList<>();
    }

    public boolean getCirclePlayer() {
        return circlePlayer;
    }

    public void changePlayer() {
        circlePlayer = !circlePlayer;
    }

    public boolean getWinFlag() {
        return winFlag;
    }

    public void setWinFlag(boolean flag) {
        winFlag = flag;
    }

    public List<Integer> getCirclePosition() {
        return circlePosition;
    }

    public List<Integer> getCrossPosition() {
        return crossPosition;
    }

    public List<Integer> getWinRow() {
        return winRow;
    }

    public void setWinRow(List<Integer> list) {
        winRow = new ArrayList<>(list);
    }

    public List<List<Integer>> getWinSituation() {
        return winSituation;
    }

    public int[] getCellID() {
        return cellID;
    }
}
