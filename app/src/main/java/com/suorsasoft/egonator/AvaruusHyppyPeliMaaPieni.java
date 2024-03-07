package com.suorsasoft.egonator;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class AvaruusHyppyPeliMaaPieni implements AvaruusHyppyPeliMaa {

    private Rect nelikulmio;
    private boolean onHajonnut;
    private boolean onTuhoutunut;

    public AvaruusHyppyPeliMaaPieni() {
        nelikulmio = new Rect(0, 0, 1, 1);
        onHajonnut = false;
        onTuhoutunut = false;
    }

    @Override
    public Rect getRect() {
        return nelikulmio;
    }

    @Override
    public boolean getTuhoutunut() {
        return onTuhoutunut;
    }

    @Override
    public void hajota() {
        if(!onHajonnut) {
            onHajonnut = true;
        } else {
            onTuhoutunut = true;
        }
    }

    @Override
    public void update(Point piste) {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas){

    }

    @Override
    public void lisaaY(int lisattava) {
        int pisteX = ((nelikulmio.left + nelikulmio.right)/2);
        int pisteY = (nelikulmio.top + nelikulmio.bottom)/2 + lisattava;
        Point piste = new Point(pisteX, pisteY);
        update(piste);
    }
}
