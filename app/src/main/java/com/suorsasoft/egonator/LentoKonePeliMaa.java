package com.suorsasoft.egonator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class LentoKonePeliMaa implements PeliObjekti {
    private Rect nelikulmio;
    private Bitmap kuva;

    public LentoKonePeliMaa(Rect nelikulmio, Bitmap kuva) {
        this.nelikulmio = nelikulmio;
        this.kuva = kuva;
    }

    public Rect getRect() {
        return nelikulmio;
    }

    public void vahennaX(int vahennettava) {
        int pisteX = ((nelikulmio.left + nelikulmio.right)/2) - vahennettava;
        int pisteY = (nelikulmio.top + nelikulmio.bottom)/2;
        Point piste = new Point(pisteX, pisteY);

        update(piste);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(kuva, null, nelikulmio, null);
    }

    @Override
    public void update() {

    }

    public void update(Point piste) {
        nelikulmio.set(piste.x - nelikulmio.width()/2, piste.y - nelikulmio.height()/2, piste.x + nelikulmio.width()/2, piste.y + nelikulmio.height()/2);
    }
}
