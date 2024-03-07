package com.suorsasoft.egonator;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE;

public class MainThread extends Thread{
    public static final int MAX_FPS = 30;
    private double keskiarvoFPS;
    private SurfaceHolder surfaceHolder;
    private PeliPaneeli peliPaneeli;
    private boolean kaynnissa;
    public static Canvas canvas;

    public void setKaynnissa(boolean kaynnissa) {
        this.kaynnissa = kaynnissa;
    }

    public MainThread(SurfaceHolder surfaceHolder, PeliPaneeli peliPaneeli) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.peliPaneeli = peliPaneeli ;
        peliPaneeli.setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE | SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

    @Override
    public void run() {
        long aloitusAika;
        long aikaMillis = 1000/MAX_FPS;
        long odotusAika;
        int kuvaMaara = 0;
        long kokonaisAika = 0;
        long tahtaysAika = 1000/MAX_FPS;


        while(kaynnissa) {
            aloitusAika = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.peliPaneeli.update();
                    this.peliPaneeli.draw(canvas);
                }
            } catch(Exception e) {e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch(Exception e) {e.printStackTrace();}
                }
            }
            aikaMillis = (System.nanoTime() - aloitusAika)/1000000;
            odotusAika = tahtaysAika - aikaMillis;
            try {
                if(odotusAika > 0) {
                    this.sleep(odotusAika);
                }
            } catch (Exception e) {e.printStackTrace();}

            kokonaisAika += System.nanoTime() - aloitusAika;
            kuvaMaara++;
            if (kuvaMaara == MAX_FPS) {
                keskiarvoFPS = 1000/((kokonaisAika/kuvaMaara)/1000000);
                kuvaMaara = 0;
                kokonaisAika = 0;
                System.out.println(keskiarvoFPS);
            }
        }
    }
}
