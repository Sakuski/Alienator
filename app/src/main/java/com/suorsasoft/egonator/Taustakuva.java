package com.suorsasoft.egonator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Taustakuva implements PeliObjekti {

    private Rect nelikulmio;
    private Bitmap kuva;
    private final Point piste = new Point(Vakiot.NAYTTO_LEVEYS/2, Vakiot.NAYTTO_KORKEUS/2);

    public Rect getRect() {
        return nelikulmio;
    }

    public Taustakuva(Rect nelikulmio, Bitmap kuva) {
        this.nelikulmio = nelikulmio;
        this.kuva = kuva;
        nelikulmio.set(piste.x - nelikulmio.width()/2, piste.y - nelikulmio.height()/2, piste.x + nelikulmio.width()/2, piste.y + nelikulmio.height()/2);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(kuva, null, nelikulmio, null);
    }

    @Override
    public void update(){
        nelikulmio.set(piste.x - nelikulmio.width()/2, piste.y - nelikulmio.height()/2, piste.x + nelikulmio.width()/2, piste.y + nelikulmio.height()/2);
    }

}
