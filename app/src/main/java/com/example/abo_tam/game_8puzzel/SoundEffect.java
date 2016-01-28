package com.example.abo_tam.game_8puzzel;

import android.media.MediaPlayer;

/**
 * Created by abo_tam on 3/23/2015.
 */
public class SoundEffect {
    private MediaPlayer effect;
    private int[] requsts;
    private int num;

    public SoundEffect(MediaPlayer effect) {
        this.effect = effect;
        requsts = new int[5];
        num = 0;
    }

    public void addRequst(){
        requsts[num++] = 1;
    }

    public void checkRequsts(){
        for(int i = 0 ;i < num ;i++){
            if ( i == 1 ) {
                if(!effect.isPlaying()) {
                    effect.start();
                    num--;
                }
            }
        }
    }
}
