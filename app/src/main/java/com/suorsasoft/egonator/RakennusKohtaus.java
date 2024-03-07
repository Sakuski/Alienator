package com.suorsasoft.egonator;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class RakennusKohtaus implements Kohtaus {

    private Taustakuva taustakuva;

    private ValikkoIkoni kotiIkoni;
    private Point kotiIkoniPiste;

    private ValikkoIkoni asetusValikkoIkoni;
    private Point asetusValikkoIkoniPiste;

    private ValikkoIkoni kolikkoIkoni;
    private Point kolikkoIkoniPiste;

    public RakennusKohtaus() {
          taustakuva = new Taustakuva(new Rect(0, 0, Vakiot.NAYTTO_LEVEYS + 1, Vakiot.NAYTTO_KORKEUS + 1), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.pelitausta));

          kotiIkoni = new ValikkoIkoni(new Rect(0, 0, 200, 200), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.home));
          kotiIkoniPiste = new Point(Vakiot.NAYTTO_LEVEYS/2, 9*Vakiot.NAYTTO_KORKEUS/10);
          kotiIkoni.update(this.kotiIkoniPiste);

          asetusValikkoIkoni = new ValikkoIkoni(new Rect(100, 100, 250, 250), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.asetusvalikko));
          asetusValikkoIkoniPiste = new Point(7*Vakiot.NAYTTO_LEVEYS/8, 1*Vakiot.NAYTTO_KORKEUS/15);
          asetusValikkoIkoni.update(asetusValikkoIkoniPiste);

          kolikkoIkoni = new ValikkoIkoni(new Rect(0, 0, 150, 150), BitmapFactory.decodeResource(Vakiot.NYKYINEN_KONTEKSTI.getResources(), R.drawable.kolikko));
          kolikkoIkoniPiste = new Point(1*Vakiot.NAYTTO_LEVEYS/8, 1*Vakiot.NAYTTO_KORKEUS/15);
          kolikkoIkoni.update(kolikkoIkoniPiste);
    }

    public static void terminate() {
        KohtausManageri.AKTIIVINEN_KOHTAUS = 5;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Rect kotiIkoniNelio = kotiIkoni.getRect();

            if(kotiIkoniNelio.contains(x, y)) {
                AlkuKohtaus.terminate();
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        taustakuva.draw(canvas);

        Rect nelikulmio = new Rect(0, 4*Vakiot.NAYTTO_KORKEUS/5, Vakiot.NAYTTO_LEVEYS + 1, Vakiot.NAYTTO_KORKEUS + 1);
        Paint maali = new Paint();
        maali.setColor(Color.rgb(0,0,0));
        maali.setAlpha(150);
        canvas.drawRect(nelikulmio, maali);

        kotiIkoni.draw(canvas);
        asetusValikkoIkoni.draw(canvas);
        kolikkoIkoni.draw(canvas);

        maali.setColor(Color.rgb(255,223,0));
        maali.setTextSize(100);
        canvas.drawText("" + Vakiot.kolikkoMaara, 2*Vakiot.NAYTTO_LEVEYS/9, 2*Vakiot.NAYTTO_KORKEUS/23, maali);
    }

}
