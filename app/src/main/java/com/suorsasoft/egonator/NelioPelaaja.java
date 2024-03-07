package com.suorsasoft.egonator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;

public class NelioPelaaja implements PeliObjekti{

    private boolean hyppaa;

    private Rect nelikulmio;

    private ArrayList<HashMap<Bitmap, Boolean>> hahmot;
    public static int hahmoIndeksi = 0;

    public Rect getRect() {
        return nelikulmio;
    }

    public void setRect(int leveys, int korkeus) {
        this.nelikulmio = new Rect(0, 0, leveys, korkeus);
    }

    public boolean getHyppaa() {
        return hyppaa;
    }

    public void setHyppaa(boolean arvo) {
        hyppaa = arvo;
    }

    public NelioPelaaja(Rect nelikulmio) {
        this.hyppaa = false;

        this.nelikulmio = nelikulmio;
        this.hahmot = new ArrayList<>();

        Bitmap vihreaAlien = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.aliengreen);
        Bitmap pinkkiAlien = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.alienpink);
        Bitmap sininenAlien = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.alienblue);
        Bitmap beigeAlien = BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.alienbeige);

        HashMap<Bitmap, Boolean> vihreaMappi = new HashMap<>();
        HashMap<Bitmap, Boolean> pinkkiMappi = new HashMap<>();
        HashMap<Bitmap, Boolean> keltainenMappi = new HashMap<>();
        HashMap<Bitmap, Boolean> sininenMappi = new HashMap<>();

        vihreaMappi.put(vihreaAlien, true);
        pinkkiMappi.put(pinkkiAlien, Vakiot.pinkkiAlienOsto);
        keltainenMappi.put(beigeAlien, Vakiot.keltainenAlienOsto);
        sininenMappi.put(sininenAlien, Vakiot.sininenAlienOsto);

        this.hahmot.add(vihreaMappi);
        this.hahmot.add(pinkkiMappi);
        this.hahmot.add(sininenMappi);
        this.hahmot.add(keltainenMappi);
    }

    public void seuraavaHahmo() {
        if (hahmoIndeksi == hahmot.size() - 1) {
            hahmoIndeksi = 0;
        } else {
            hahmoIndeksi++;
        }
}

    public void edellinenHahmo() {
        if (hahmoIndeksi == 0) {
            hahmoIndeksi = hahmot.size() - 1;
        } else {
            hahmoIndeksi--;
        }
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

    public void vahennaX(int vahennettava) {
        int pisteX = ((nelikulmio.left + nelikulmio.right)/2) - vahennettava;
        int pisteY = (nelikulmio.top + nelikulmio.bottom)/2;
        Point piste = new Point(pisteX, pisteY);
        update(piste);
    }

    public void lisaaX(int lisattava) {
        int pisteX = ((nelikulmio.left + nelikulmio.right)/2) + lisattava;
        int pisteY = (nelikulmio.top + nelikulmio.bottom)/2;
        Point piste = new Point(pisteX, pisteY);
        update(piste);
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

    public boolean jalatOsuu(PeliObjekti objekti) {
        if(objekti.getRect().contains(this.getRect().centerX(), this.getRect().bottom) && !objekti.getRect().contains(this.getRect().centerX(), this.getRect().centerY())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        for(Bitmap map : hahmot.get(hahmoIndeksi).keySet()) {
            canvas.drawBitmap(map, null, nelikulmio, null);
        }

    }

    @Override
    public void update() {

    }

    public void update(Point piste) {
        float oldLeft = nelikulmio.left;

        nelikulmio.set(piste.x - nelikulmio.width()/2, piste.y - nelikulmio.height()/2, piste.x + nelikulmio.width()/2, piste.y + nelikulmio.height()/2);
    }
}
