package com.example.abo_tam.game_8puzzel;

/**
 * Created by abo_tam on 3/22/2015.
 */
public class Tile {
    private int number;
    private int image;

    public Tile(int number) {
        this.number = number;
        setImage(number);
    }

    public int getNumber() {
        return number;
    }

    public int getImage() {
        return image;
    }

    public void setNumber(int number) {
        this.number = number;
        setImage(number);
    }

    public void setImage(int number) {
        switch(number){
            case 1:
                image = R.mipmap.tile1;
                break;
            case 2:
                image = R.mipmap.tile2;
                break;
            case 3:
                image = R.mipmap.tile3;
                break;
            case 4:
                image = R.mipmap.tile4;
                break;
            case 5:
                image = R.mipmap.tile5;
                break;
            case 6:
                image = R.mipmap.tile6;
                break;
            case 7:
                image = R.mipmap.tile7;
                break;
            case 8:
                image = R.mipmap.tile8;
                break;
            case 0:
                image = R.mipmap.blank;
                break;
            default:
        }
    }
}
