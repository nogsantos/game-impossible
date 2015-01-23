package br.eti.fabricionogueira.impossible;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class Game extends Activity implements View.OnTouchListener {
    /**
     * Compositions
     */
    Impossible view;
    /**
     * Create method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * Fullscreen mode
         */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        /*
         * Mantém a screen sempre ativa
         */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        /*
         * Game logic
         */
        view = new Impossible(this);
        /*
         * Receber os inputs de touch.
         */
        view.setOnTouchListener(this);
        /*
         * Config view
         */
        setContentView(view);
    }
    /**
     * After the activity load, call a resume method in Impossible class.
     */
    protected void onResume() {
        super.onResume();
        view.resume();
    }
    /**
     * Finish
     */
    protected void onDestroy(){
        super.finish();
    }
    /**
     * Toda vez que um toque for detectado, o Android o chama, passando as coordenadas tocadas
     * na superfície da tela. E de posse dessas coordenada, podemos tomar ações sobre os
     * objetos na tela do jogo.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        Log.i("Impossible","X "+event.getX());
//        Log.i("Impossible","Y "+event.getY());
//        Log.i("Impossible","View "+event);
        /*
         * Reinicia o jogo
         */
        if(event.getX() < 100 && event.getY() > 290 && event.getY() == 310) {
            view.restartGame();
        }
        /*
         * Exit
         */
        if(event.getX() < 100 && event.getY() > 490 && event.getY() < 500) {
            System.exit(0);
        }
        /*
         * Incrementa em 10 pixels a posição vertical do player
         */
        view.moveDown(10);
        /*
         * Incrementa o placar
         */
        view.addScore(1);
        return true;
    }
}
