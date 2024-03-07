package com.suorsasoft.egonator;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface PeliObjekti {
    public void draw(Canvas canvas);
    public void update();
    public Rect getRect();
}
