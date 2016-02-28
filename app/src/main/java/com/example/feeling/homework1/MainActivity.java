package com.example.feeling.homework1;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board = new Board();
    }

    public void clickBoard(View v) {
        // get the Id of Image Resource
        String id = v.getResources().getResourceName(v.getId());

        // position indicates which cell is clicked
        int position = Character.getNumericValue(id.charAt(id.length() - 1));

        ImageButton imageButton = (ImageButton) v;
        TextView tv = (TextView) findViewById(R.id.textView);

        // set the circle or cross in the view
        if (!board.getCirclePosition().contains(position) &&
                !board.getCrossPosition().contains(position)) {
            if (board.getCirclePlayer()) {
                tv.setText("X's turn");

                imageButton.setImageResource(R.drawable.circle);
                board.getCirclePosition().add(position);

                board.setWinFlag(checkWin(board.getCirclePlayer()));
                if (board.getWinFlag()) {
                    tv.setText("O won!");
                    tv.setVisibility(View.VISIBLE);

                    highlightRow();
                    highlightNewGameButton();
                    disableCellClick();
                }
            } else {
                tv.setText("O's turn");

                imageButton.setImageResource(R.drawable.cross);
                board.getCrossPosition().add(position);

                board.setWinFlag(checkWin(board.getCirclePlayer()));
                if (board.getWinFlag()) {
                    tv.setText("X won!");
                    tv.setVisibility(View.VISIBLE);

                    highlightRow();
                    highlightNewGameButton();
                    disableCellClick();
                }
            }

            // check if it's a tie
            if ((board.getCirclePosition().size() + board.getCrossPosition().size() == 9)
                    && !board.getWinFlag()) {
                tv.setText("Tie.");
                tv.setVisibility(View.VISIBLE);
                highlightNewGameButton();
            }

            board.changePlayer();
        }
    }

    // check if 3 patterns are in a row
    private boolean checkWin(boolean isCirclePlaying) {
        List<Integer> positions;

        if (isCirclePlaying) {
            positions = board.getCirclePosition();
        } else {
            positions = board.getCrossPosition();
        }

        Collections.sort(positions);

        for (List<Integer> subList : getCombination(positions)) {
            if (board.getWinSituation().contains(subList)) {
                board.setWinRow(subList);
                return true;
            }
        }
        return false;
    }

    // get the combination of 3 numbers in a list
    private List<List<Integer>> getCombination(List<Integer> list) {
        List<List<Integer>> res = new ArrayList<>();
        getCombinationHelper(list, 0, 3, new ArrayList<Integer>(), res);

        return res;
    }

    // helper function using DFS and back-tracking
    private void getCombinationHelper(List<Integer> list, int start, int k,
                                      List<Integer> item, List<List<Integer>> res) {
        if (k == 0) {
            res.add(new ArrayList<>(item));
            return;
        }

        for (int i = start; i < list.size(); i++) {
            item.add(list.get(i));
            getCombinationHelper(list, i + 1, k - 1, item, res);
            item.remove(item.size() - 1);
        }
    }

    // clear the board when click "New Game" button
    public void clearBoard(View v) {
        unHighlightRow();
        board = new Board();

        ImageButton ib;
        for (int id : board.getCellID()) {
            ib = (ImageButton) findViewById(id);
            ib.setImageResource(android.R.color.transparent);
        }

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("O's turn");

        unHighlightNewGameButton();
        enableCellClick();
    }

    // disable cell click after one wins
    private void disableCellClick() {
        ImageButton ib;
        for (int id : board.getCellID()) {
            ib = (ImageButton) findViewById(id);
            ib.setClickable(false);
        }
    }

    // enable cell click when the game is reset
    private void enableCellClick() {
        ImageButton ib;
        for (int id : board.getCellID()) {
            ib = (ImageButton) findViewById(id);
            ib.setClickable(true);
        }
    }

    // highlight a row
    private void highlightRow() {
        ImageButton ib;
        for (int i : board.getWinRow()) {
            String s = "imageButton" + i;
            ib = (ImageButton) findViewById(getResources().getIdentifier(s, "id", getPackageName()));
            ib.setColorFilter(Color.argb(255, 255, 0, 0));
        }
    }

    // remove highlight when a new game starts
    private void unHighlightRow() {
        ImageButton ib;
        for (int i : board.getWinRow()) {
            String s = "imageButton" + i;
            ib = (ImageButton) findViewById(getResources().getIdentifier(s, "id", getPackageName()));
            ib.clearColorFilter();
        }
    }

    private void highlightNewGameButton() {
//        Button b = (Button) findViewById(R.id.newButton);
//        b.setBackgroundColor(Color.YELLOW);
        Drawable d = findViewById(R.id.newButton).getBackground();
        PorterDuffColorFilter filter =
                new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        d.setColorFilter(filter);
    }

    private void unHighlightNewGameButton() {
//        Button b = (Button) findViewById(R.id.newButton);
//        b.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
        Drawable d = findViewById(R.id.newButton).getBackground();
        findViewById(R.id.newButton).invalidateDrawable(d);
        d.clearColorFilter();
    }
}