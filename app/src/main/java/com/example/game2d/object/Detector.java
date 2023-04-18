package com.example.game2d.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.game2d.R;

public class Detector extends Circle {
    private final Player player;

    public Detector(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.detector), positionX, positionY, radius);
        this.player = player;
    }

    public void update() {
        velocityX = 0;
        velocityY = 0;
        positionX += velocityX;
        positionY += velocityY;
    }
}
