package com.suorsasoft.egonator;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Kohtaus {
    public void update();
    public void draw(Canvas canvas);
    public void receiveTouch(MotionEvent event);
}
