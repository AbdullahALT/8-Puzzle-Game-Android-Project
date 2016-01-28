package com.example.abo_tam.game_8puzzel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Puzzle extends Activity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {

    private Service service;
    private ImageView[][] images;
    private ProgressBar progress;
    private Handler mHandler;
    private int nowTime;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Thread thread;
    private Thread sound;
    private int difficulty;
    private boolean isGameOver;
    private boolean isStop;
    private boolean isWin;
    private static final String TAG = "DEBUG TAG";
    private GestureDetector gestureDetector;
    private MediaPlayer effect;
    private SoundEffect soundEffect;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(TAG, "onSingleTapConfirmed");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG, "onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG, "onShowPress");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "onLongPress");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        int SWIPE_THRESHOLD = 100;
        int SWIPE_VELOCITY_THRESHOLD = 100;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
                result = true;
            } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
            }
            result = true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void onSwipeRight() {
        if (!isGameOver) {
            if(service.right(images)) {
                soundEffect.addRequst();
                checkGoal();
            }
        }
    }

    public void onSwipeLeft() {
        if (!isGameOver) {
            if(service.left(images)) {
                soundEffect.addRequst();
                checkGoal();
            }
        }
    }

    public void onSwipeBottom() {
        if (!isGameOver) {
            if(service.down(images)) {
                soundEffect.addRequst();
                checkGoal();
            }
        }
    }

    public void onSwipeTop() {
        if (!isGameOver) {
            if(service.up(images)) {
                soundEffect.addRequst();
                checkGoal();
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzel);
        Log.i("My", "servic called");
        service = new Service();
        isGameOver = false;
        prepareImages();
        service.update(images);
        builder = new AlertDialog.Builder(this);
        CharSequence[] list = {"Easy (10 min)", "Medium (5 min)", "Hard (2 min)", "No Time"};
        builder.setTitle("Select Difficulty").setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    easy();
                } else if (which == 1) {
                    normal();
                } else if (which == 2) {
                    hard();
                } else if (which == 3) {
                    thread = null;
                    progress.setProgress(0);
                    difficulty = 0;
                }

            }

        });

        //effect = MediaPlayer.create(this, R.raw.movingTile);


        dialog = builder.create();
        dialog.show();
        gestureDetector = new GestureDetector(this, this);
        gestureDetector.setOnDoubleTapListener(this);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        //builder = new AlertDialog.Builder(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        isStop = true;
        effect.release();
        effect = null;
        soundEffect = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        effect = MediaPlayer.create(this, R.raw.moving_tile);
        soundEffect = new SoundEffect(effect);
        isStop = false;
        soundPlayer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_puzzel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            service.reset();
            service.update(images);
            isStop = false;
            if(isGameOver){
                isGameOver = false;
                soundPlayer();
            }
            isWin = false;
            if (difficulty != 0)
                progressPrepare();
            return true;
        }
        if (id == R.id.easy_settings) {
            easy();
            return true;
        }
        if (id == R.id.hard_settings) {
            hard();
            return true;
        }
        if (id == R.id.medium_settings) {
            normal();
            return true;
        }
        if (id == R.id.notime_settings) {
            thread = null;
            progress.setProgress(0);
            difficulty = 0;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void prepareImages() {
        images = new ImageView[3][3];
        images[0][0] = (ImageView) findViewById(R.id.num1);
        images[0][1] = (ImageView) findViewById(R.id.num2);
        images[0][2] = (ImageView) findViewById(R.id.num3);
        images[1][0] = (ImageView) findViewById(R.id.num4);
        images[1][1] = (ImageView) findViewById(R.id.num5);
        images[1][2] = (ImageView) findViewById(R.id.num6);
        images[2][0] = (ImageView) findViewById(R.id.num7);
        images[2][1] = (ImageView) findViewById(R.id.num8);
        images[2][2] = (ImageView) findViewById(R.id.blank);

    }


    public void checkGoal() {
        if (service.testGoal()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You'v completed the puzzle successfully").setTitle("Congrats!! <3");
            AlertDialog dialog = builder.create();
            isGameOver = true;
            isWin = true;
            dialog.show();
        }
    }

    public void progressPrepare() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                builder.setMessage("you ran out of time!! try again later").setTitle("GAME OVER");
                dialog = builder.create();
                isGameOver = true;
                if(!isWin)
                dialog.show();
            }
        };

        Runnable time = new Runnable() {
            @Override
            public void run() {
                Thread thisThread = Thread.currentThread();
                int gameEnd = (int) System.currentTimeMillis() + difficulty;
                progress.setMax(difficulty);
                while ((nowTime = (int) System.currentTimeMillis()) < gameEnd && thisThread == thread && !isGameOver ) {
                    progress.setProgress((gameEnd - nowTime));

                }
                if (thisThread == thread && !isWin)
                    mHandler.sendEmptyMessage(0);

            }
        };

        thread = new Thread(time);
        thread.start();

    }

    public void soundPlayer() {


        Runnable time = new Runnable() {
            @Override
            public void run() {

                while (!isStop && !isGameOver) {
                    soundEffect.checkRequsts();
                }
            }
        };

        sound = new Thread(time);
        sound.start();

    }

    public void hard() {
        difficulty = 60000;
        service.reset();
        service.update(images);
        progressPrepare();
        if(isGameOver){
            isGameOver = false;
            soundPlayer();
        }
        isWin = false;
    }


    public void normal() {
        difficulty = 60000 * 5;
        service.reset();
        service.update(images);
        progressPrepare();
        if(isGameOver){
            isGameOver = false;
            soundPlayer();
        }
        isWin = false;
    }

    public void easy() {
        difficulty = 60000 * 10;
        service.reset();
        service.update(images);
        progressPrepare();
        if(isGameOver){
            isGameOver = false;
            soundPlayer();
        }
        isWin = false;
    }


}
