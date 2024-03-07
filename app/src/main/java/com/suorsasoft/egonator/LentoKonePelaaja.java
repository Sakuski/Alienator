package com.suorsasoft.egonator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import java.util.ArrayList;

public class LentoKonePelaaja implements PeliObjekti {

    private boolean hyppaa;
    public static int lentokoneIndeksi;

    private Rect nelikulmio;
    private ArrayList<Bitmap> lentokoneet;

    public Rect getRect() {
        return nelikulmio;
    }

    public void setRect(int leveys, int korkeus) {
        this.nelikulmio = new Rect(0, 0, leveys, korkeus);
    }

    public LentoKonePelaaja() {

        this.hyppaa = false;

        nelikulmio = new Rect(0, 0, 186, 105);
        lentokoneet = new ArrayList<>();

        Bitmap vihreaKone = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.shipgreen);
        Bitmap punainenKone = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.shippink);
        Bitmap sininenKone = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.shipblue);
        Bitmap keltainenKone = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.shipyellow);

        lentokoneet.add(vihreaKone);
        lentokoneet.add(punainenKone);
        lentokoneet.add(sininenKone);
        lentokoneet.add(keltainenKone);
    }

    public boolean getHyppaa() {
        return this.hyppaa;
    }

    public void hyppaa() {
        hyppaa = true;
    }

    public void lopetaHyppy() {
        hyppaa = false;
    }

    public boolean tormaa(PeliObjekti objekti) {
        if (objekti.getRect().contains(nelikulmio.left, nelikulmio.top)
                || objekti.getRect().contains(nelikulmio.left, nelikulmio.bottom)
                || objekti.getRect().contains(nelikulmio.right, nelikulmio.top)
                || objekti.getRect().contains(nelikulmio.right, nelikulmio.bottom)) {
            return true;
        }

        if(nelikulmio.contains(objekti.getRect().left, objekti.getRect().top)
                || nelikulmio.contains(objekti.getRect().left, objekti.getRect().top)
                || nelikulmio.contains(objekti.getRect().right, objekti.getRect().top)
                || nelikulmio.contains(objekti.getRect().right, objekti.getRect().bottom)) {
            return true;
        }

        return false;
    }

    public void vahennaY(int vahennettava) {
        int pisteX = ((nelikulmio.left + nelikulmio.right)/2);
        int pisteY = (nelikulmio.top + nelikulmio.bottom)/2 - vahennettava;
        Point piste = new Point(pisteX, pisteY);
        update(piste);
    }

    public void lisaaY(int lisattava) {
        int pisteX = ((nelikulmio.left + nelikulmio.right)/2);
        int pisteY = (nelikulmio.top + nelikulmio.bottom)/2 + lisattava;
        Point piste = new Point(pisteX, pisteY);
        update(piste);
    }

    public void seuraava() {
        if(lentokoneIndeksi < lentokoneet.size() - 1) {
            lentokoneIndeksi++;
        } else {
            lentokoneIndeksi = 0;
        }
    }

    public void edellinen() {
        if(lentokoneIndeksi > 0) {
            lentokoneIndeksi--;
        } else {
            lentokoneIndeksi = this.lentokoneet.size() - 1;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(lentokoneet.get(lentokoneIndeksi), null, nelikulmio, null);
    }

    @Override
    public void update() {

    }

    public void update(Point piste) {
        nelikulmio.set(piste.x - nelikulmio.width()/2, piste.y - nelikulmio.height()/2, piste.x + nelikulmio.width()/2, piste.y + nelikulmio.height()/2);
    }
}
