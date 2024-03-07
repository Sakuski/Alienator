package com.suorsasoft.egonator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class AvaruusHyppyPeliMaaIso implements AvaruusHyppyPeliMaa {
    private Bitmap maaPala;
    private Bitmap maaPalaHajonnut;
    private Rect nelikulmio;
    private boolean onHajonnut;
    private boolean onTuhoutunut;

    @Override
    public Rect getRect() {
        return this.nelikulmio;
    }

    @Override
    public boolean getTuhoutunut() {
        return onTuhoutunut;
    }

    public AvaruusHyppyPeliMaaIso() {
        this.maaPala = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.kivimaa);
        this.maaPalaHajonnut = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.kivimaahajonnut);
        this.nelikulmio = new Rect(0, 0, 190, 47);
        onHajonnut = false;
        onTuhoutunut = false;
    }

    public void hajota() {

        if(!this.onHajonnut) {
            this.onHajonnut = true;
        } else {
            this.onTuhoutunut = true;
        }
    }

    @Override
    public void update(Point piste) {
        nelikulmio.set(piste.x - nelikulmio.width()/2, piste.y - nelikulmio.height()/2, piste.x + nelikulmio.width()/2, piste.y + nelikulmio.height()/2);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

        if(!onTuhoutunut) {
            if(onHajonnut) {
                canvas.drawBitmap(maaPalaHajonnut, null, nelikulmio, null);
            } else {
                canvas.drawBitmap(maaPala, null, nelikulmio, null);
            }
        }
    }

    @Override
    public void lisaaY(int lisattava) {
        int pisteX = ((nelikulmio.left + nelikulmio.right)/2);
        int pisteY = (nelikulmio.top + nelikulmio.bottom)/2 + lisattava;
        Point piste = new Point(pisteX, pisteY);
        update(piste);
    }


}
