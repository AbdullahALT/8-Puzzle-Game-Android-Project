package com.example.abo_tam.game_8puzzel;

public class Problem {
    private Tile[][] board;
    private Blank blank;

    public Problem(int state[][]) {
        board = new Tile[3][3];
        setState(state);
    }



    public boolean move(Action action) {
        // the method moves this not the child, BE AWARE!!
        try {
            switch (action) {

                case RIGHT:
                    board[blank.getRow()][blank.getColumn()].setNumber(board[blank.getRow()][blank
                            .getColumn() + 1].getNumber());
                    board[blank.getRow()][blank.getColumn() + 1].setNumber(0);
                    blank.movedTo(blank.getRow(), blank.getColumn() + 1);
                    break;
                case LEFT:
                    board[blank.getRow()][blank.getColumn()].setNumber(board[blank.getRow()][blank
                            .getColumn() - 1].getNumber());
                    board[blank.getRow()][blank.getColumn() - 1].setNumber(0);
                    blank.movedTo(blank.getRow(), blank.getColumn() - 1);
                    break;
                case UP:
                    board[blank.getRow()][blank.getColumn()].setNumber(board[blank.getRow() - 1][blank
                            .getColumn()].getNumber());
                    board[blank.getRow() - 1][blank.getColumn()].setNumber(0);
                    blank.movedTo(blank.getRow() - 1, blank.getColumn());
                    break;
                case DOWN:
                    board[blank.getRow()][blank.getColumn()].setNumber(board[blank.getRow() + 1][blank
                            .getColumn()].getNumber());
                    board[blank.getRow() + 1][blank.getColumn()].setNumber(0);
                    blank.movedTo(blank.getRow() + 1, blank.getColumn());
                    break;

                default:
                    break;

            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        String s = new String();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                s = s + board[i][j] + "\t";
            }
            s = s + "\n";
        }
        return s;
    }

    public Blank getBlank() {
        return blank;
    }



    private void setState(int state[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0)
                    blank = new Blank(i, j);
                board[i][j] = new Tile(state[i][j]);


            }
        }
    }

    public int[][] getState() {
        int[][] state = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = board[i][j].getNumber();

            }
        }
        return state;
    }

    public static Action[] actions(int state[][]) {
        Problem b = new Problem(state);
        return b.blank.getActions();
    }

    public boolean goalTest() {
        int num = 0;
        for(int x = 0 ; true ; x++){
            for(int n = 0 ; n <3 ; n++){
                int i = x ,j = n + 1;
                if ( x == 2 && n == 2){
                    return num == 0? true: false;
                } else if( n == 2){
                    j = 0;
                    i = x + 1;
                }
                for(  ; i <3 ; i++){
                    for( ; j <3 ; j++){
                        if(board[x][n].getNumber() != 0 && board[i][j].getNumber() != 0 && board[x][n].getNumber() > board[i][j].getNumber()){
                            num++;
                        }
                    }
                    if(j == 3)
                        j = 0;
                }
            }
        }

    }

    public int getNumberOfActions() {
        return blank.getActnb();
    }

    public Tile[][] getTiles() {
        return board;
    }
}
