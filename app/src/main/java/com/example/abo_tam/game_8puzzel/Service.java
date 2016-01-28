package com.example.abo_tam.game_8puzzel;
import android.widget.ImageView;

import java.util.Random;
/**
 * Created by abo_tam on 3/22/2015.
 */
public class Service {
    Problem problem;

    public Service(){
        insilize();
    }

    private void insilize(){
        int state[] = new int[9];

        while(true){
            Random ran = new Random();
            int unstate[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};
            for(int i = 0 ; i < state.length ;  ){
                int index = (((int) System.currentTimeMillis()) + ran.nextInt(17)) % 9;
                if(unstate[index] != -1) {
                    state[i++] = unstate[index];
                    unstate[index] = -1;
                }
            }
            if(isSolvable(transferToDoubleArray(state))) {
                break;
            }
        }
        problem = new Problem(transferToDoubleArray(state));
    }

    private int[][] transferToDoubleArray(int[] array){
        int[][] result = new int[3][3];
        int index = 0;
        for(int i = 0 ;i < 3; i++){
            for(int j = 0 ; j < 3 ; j++){
                result[i][j] = array[index++];
            }
        }
        return result;
    }


    public boolean testGoal(){
        return problem.goalTest();
    }

    public void update(ImageView image[][]){
        Tile tiles[][] = problem.getTiles();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                image[i][j].setImageResource(tiles[i][j].getImage());
            }
        }
    }

    public void reset(){
        insilize();
    }

    public boolean right(ImageView image[][]){
        boolean result = problem.move(Action.LEFT);
        update(image);
        return result;
    }

    public boolean left(ImageView image[][]){
        boolean result = problem.move(Action.RIGHT);
        update(image);
        return result;
    }
    public boolean up(ImageView image[][]){
        boolean result = problem.move(Action.DOWN);
        update(image);
        return result;
    }
    public boolean down(ImageView image[][]){
        boolean result = problem.move(Action.UP);
        update(image);
        return result;
    }

    public boolean isSolvable(int[][] state){
        int num = 0;
        for(int x = 0 ; true ; x++){
            for(int n = 0 ; n <3 ; n++){
                int i = x ,j = n + 1;
                if ( x == 2 && n == 2){
                    return (num % 2) == 0? true: false;
                } else if( n == 2){
                    j = 0;
                    i = x + 1;
                }
                for(  ; i <3 ; i++){
                    for( ; j <3 ; j++){
                        if(state[x][n] != 0 && state[i][j] != 0 && state[x][n] > state[i][j]){
                            num++;
                        }
                    }
                    if(j == 3)
                        j = 0;
                }
            }
        }
    }

}
