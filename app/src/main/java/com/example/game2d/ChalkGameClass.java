package com.example.game2d;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

public class ChalkGameClass extends View {
    //PLAYER
    private Bitmap player[] = new Bitmap[2];
    private int ranGravity;
    private boolean normGravity;
    private int playerX = 10;
    private int playerY;
    private int playerSpeed;
    private boolean touch = false;
    private int jumpcount;

    //CANVAS
    private int canvasWidth, canvasHeight;
    private Bitmap backgroundImage ;

    //MEDIA
    MediaPlayer bonk, oof;

    //CHALK
    private Bitmap chalk[] = new Bitmap[5];
        private int chalkNum = 7;
        private int chalkCol;
        private int[] chalkX = new int[chalkNum];
        private int[] chalkY = new int[chalkNum];
        private int chalkXSpeed = 20;
        private int chalkPassed;

    //HEALTH SYSTEM
    private Bitmap lives[] = new Bitmap[2];
        private int lifecounter;
        private int lifeXstart;
        private int lifeY;


    private Bitmap pauseButton;
        private int pauseX = 1700;
        private int pauseY = 20;
    private boolean pauseTouch = false;

    private Bitmap hintButton;
        private int hintX = 1800;
        private int hintY = 20;



    public ChalkGameClass(Context context)
    {
        super(context);

        //ranGravity = (int) Math.floor(Math.random() * 5) + 1; //ranGravity is a random integer between 1 and 5
        ranGravity = 2;

        if(ranGravity%2 ==1)
            normGravity = true; //if ranGravity is odd, normal gravity is used where you jump up but rest at the bottom
        else
            normGravity = false; //if ranGravity is even, abnormal gravity is used where you jump down but rest at the top


        if(normGravity) {
            playerY = 650; //make the player rest at the bottom if normal gravity
            player[0] = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
            player[1] = BitmapFactory.decodeResource(getResources(), R.drawable.girl_jump_big);
            backgroundImage= BitmapFactory.decodeResource(getResources(), R.drawable.background_normal);
            lives[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
            lives[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);
            lifeY = 20;

        }
        else {
            playerY = 0; //make the player rest at the top if abnormal gravity
            player[0] = BitmapFactory.decodeResource(getResources(), R.drawable.girl_upsidedown);
            player[1] = BitmapFactory.decodeResource(getResources(), R.drawable.girl_jump_big_upsidedown);
            backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background_upsidedown);
            lives[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts_upsidedown);
            lives[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey_upsidedown);
            lifeY = 1060;
        }

        jumpcount = 0;
        lifeXstart = 1330;


        //Initializing the chalk array
        chalk[0] = BitmapFactory.decodeResource(getResources(), R.drawable.green_chalk);
        chalk[1] = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_chalk);
        chalk[2] = BitmapFactory.decodeResource(getResources(), R.drawable.red_chalk);
        chalk[3] = BitmapFactory.decodeResource(getResources(), R.drawable.white_chalk);
        chalk[4] = BitmapFactory.decodeResource(getResources(), R.drawable.blue_chalk);
        chalkPassed = 0;

        lifecounter = 3;

        pauseButton = BitmapFactory.decodeResource(getResources(), R.drawable.pause_new);
        hintButton = BitmapFactory.decodeResource(getResources(), R.drawable.question_new);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        bonk = MediaPlayer.create(getContext(), R.raw.bonk);
        oof = MediaPlayer.create(getContext(), R.raw.oof);

        if (backgroundImage == null) return;
        // Use the same Matrix over and over again to minimize
        // allocation in onDraw.
        Matrix matrix = new Matrix();

        canvas.drawBitmap(backgroundImage, matrix, null);

        matrix = StretchMatrix(matrix, canvasWidth, canvasHeight, backgroundImage);

        int minplayerY = 0;
        int floorHeight = 80;
        int maxplayerY = canvasHeight - player[0].getHeight() - floorHeight;

        //PLAYER MECHANICS
        if(normGravity)
            playerY= playerY + playerSpeed; //makes character jump up and naturally fall down
        else
            playerY = playerY - 2* playerSpeed; //makes character jump down and naturally float up

        if (playerY < minplayerY) {
            playerY = minplayerY;
            touch = false;
            if(normGravity)
            {
                playerSpeed = 125;
                bonk.start();
            }
        }       //limiting the player's y to the top of the screen

        if (playerY > maxplayerY) {
            playerY = maxplayerY;
            touch = false;
            if(!normGravity)
            {
                playerSpeed = 125;
            }
        }       //limiting the player's y tot he bottom of the screen

        playerSpeed = playerSpeed + 2; //gravity

        if (touch) { //jumping animation
            if (jumpcount > 0) { //double jump, draw idle character before drawing jump again
                canvas.drawBitmap(player[0], playerX, playerY, null);
                jumpcount = 0; //reset double jump
            } else
                canvas.drawBitmap(player[1], playerX, playerY, null);

        } else {
            canvas.drawBitmap(player[0], playerX, playerY, null);
        }

        //CHALK MECHANICS

        for(int ii = 0; ii < chalkNum; ii ++) {
            if (hitChalkChecker(chalkX[ii], chalkY[ii])) //if player collides with chalk
            {
                chalkX[ii] = chalkX[ii] - 200; //makes chalk disappear
                oof.start();
                lifecounter--; //reduces health;

                if(lifecounter == 0)  //if all lives used up
                {
                    Intent mainIntent = new Intent(getContext(), MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getContext().startActivity(mainIntent);

                }
            }

            chalkX[ii] = chalkX[ii] - chalkXSpeed; //making the chalk move across the screen from right to left
            if (chalkX[ii] < 0)
            {
                chalkPassed++;
                chalkX[ii] = canvasWidth + (ii * 200);
                if (ii == chalkNum - 1)
                    chalkXSpeed += 2;
                chalkY[ii] = (int) Math.floor(Math.random() * ((maxplayerY + player[0].getHeight()) - chalk[0].getHeight())) + chalk[0].getHeight(); //making the chalk appear at random  heights
            }


            if(chalkPassed == chalkNum * 3)
            {
                chalkX[ii] = chalkX[ii] - 200;
                chalkPassed = 0;
                Intent chalkQuestionIntent  = new Intent(getContext(), ChalkToQuizActivity.class);
                chalkQuestionIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                getContext().startActivity(chalkQuestionIntent);
            }
            chalkCol = ii;
            while(chalkCol > 4) //keeping chalkCol between 0 and 4;
            {
                chalkCol-= 5;
            }

            canvas.drawBitmap(chalk[chalkCol], chalkX[ii], chalkY[ii],  null);
        }

        //Health System Display

        for(int ii = 0; ii < 3; ii++)
        {
            int lifeX = (int) (lifeXstart + lives[0].getWidth() * 1.5 * ii);


            if(ii < lifecounter)
            {
                canvas.drawBitmap(lives[0], lifeX, lifeY, null); //drawing full heart
            }
            else
            {
                canvas.drawBitmap(lives[1], lifeX, lifeY, null); //drawing lost heart
            }
        }

        //PAUSE BUTTON
        canvas.drawBitmap(pauseButton, pauseX, pauseY, null);
        if (pauseTouch == true)
        {
            pauseTouch = false;
            Intent pauseIntent = new Intent(getContext(), PauseWindow.class);
            pauseIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            getContext().startActivity(pauseIntent);

        }

        canvas.drawBitmap(hintButton,hintX, hintY, null);

    }

    private Matrix StretchMatrix(Matrix fnMatrix, int x, int y, Bitmap img)
    {
        float vw = x;
        float vh = y;
        float bw = (float) img.getWidth ();
        float bh = (float) img.getHeight ();

        // Scale the bitmap to fit into the view.
        float s1x = vw / bw;
        float s1y = vh / bh;
       fnMatrix.postScale (s1x, s1y);

       return fnMatrix;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        pauseTouch = onSingleTap(event);
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            touch = true;
            playerSpeed = -30;
            jumpcount ++;
        }
        return true;
    }

    public boolean hitChalkChecker(int x, int y)
    {
        if(playerX < x && x < (playerX + player[0].getWidth())
                && playerY < y && y < (playerY + player[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    public boolean onSingleTap(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if ((pauseX < x && x < (pauseX + pauseButton.getWidth()) //if user touches pause button
                && pauseY < y && y < (pauseY + pauseButton.getHeight())) ||

                ( hintX < x && x < (hintX + hintButton.getWidth()) // or if user touches hint button
                    && hintY < y && y < (hintY + hintButton.getHeight())) )
        {
            return true;
        }

        return false;
    }
}
