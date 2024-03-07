package com.suorsasoft.egonator;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PeliPaneeli extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private KohtausManageri manageri;

    public PeliPaneeli(Context konteksti) {
        super(konteksti);

        getHolder().addCallback(this);

        Vakiot.NYKYINEN_KONTEKSTI = konteksti;

        thread = new MainThread(getHolder(), this);

        manageri = new KohtausManageri();

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int formaatti, int leveys, int korkeus) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        Vakiot.SAADA_AIKA = System.currentTimeMillis();
        thread.setKaynnissa(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setKaynnissa(false);
                thread.join();
            } catch (Exception e) {e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        manageri.receiveTouch(event);
        return true;
    }

    public void update() {
        manageri.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        manageri.draw(canvas);
    }
}
