package com.suorsasoft.egonator;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public interface AvaruusHyppyPeliMaa extends PeliObjekti {
    public Rect getRect();
    public void update(Point piste);
    public void draw(Canvas canvas);
    public void lisaaY(int lisattava);
    public void hajota();
    public boolean getTuhoutunut();
}
