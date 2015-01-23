package br.eti.fabricionogueira.impossible;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author nogsantos
 * @since 23/01/2015 10:48 AM
 *
 * No Android um objeto do tipo SurfaceView permite que desenhos sejam
 * executados sobre a superfície em vez de trabalhar com um XML de apresentação.
 *
 */
public class Impossible extends SurfaceView implements Runnable{
    /*
     * Compositions
     */
    boolean         running      = false;
    Thread          renderThread = null;
    // Certificar de que a superfície ou tela já está preparada para receber os desenhos.
    SurfaceHolder   holder;
    // Para desenhar...
    Paint           paint;
    private int     enemyX, enemyY, enemyRadius = 50;
    private int     playerX = 300, playerY = 300, playerRadius = 50;
    private double  distance;
    private boolean gameOver;
    private int     score;
    /**
     * Constructor
     */
    public Impossible(Context context){
        super(context);
        paint  = new Paint();
        holder = getHolder();
    }
    /**
     * Thread
     */
    @Override
    public void run() {

        while (running){
            /*
             * verifica se a tela já está pronta
             */
            if(!holder.getSurface().isValid()) {
                continue;
            }
            /*
             * bloqueia o canvas
             */
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            /*
             * Backgroud
             */
            //canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(
                BitmapFactory.decodeResource(getResources(),
                R.drawable.sky),
                0,
                0,
                null
            );
            /*
             * desenha o player na tela
             */
            drawPlayer(canvas);
            /**
             * Desenha o inimigo na tela
             */
            drawEnemy(canvas);
            /*
             * Detecta colisão
             */
            checkCollision(canvas);
            if(gameOver) {
                stopGame(canvas);
                //break;
            }
            /*
             * Atualiza o placar
             */
            drawScore(canvas);
            /*
             * Botões
             */
            drawButtons(canvas);
            /*
             * atualiza e libera o canvas
             */
            holder.unlockCanvasAndPost(canvas);
        }
    }
    /**
     * Make running true and create a thread to run the runnable.
     */
    public void resume() {
        running      = true;
        renderThread = new Thread(this);
        renderThread.start();
    }
    /**
     * Atributos do player
     */
    private void drawPlayer(Canvas canvas) {
        paint.setColor(Color.GREEN);
        //canvas.drawCircle(playerX, playerY, 50, paint);
        canvas.drawBitmap(
            BitmapFactory.decodeResource(
                getResources(),
                R.drawable.nave
            ),
            playerX-50,
            playerY-50,
            null
        );
    }
    /**
     * Invocado para mover o player para baixo
     */
    public void moveDown(int pixels) {
        playerY += pixels;
    }
    /**
     * Atributos do inimigo
     */
    private void drawEnemy(Canvas canvas) {
        paint.setColor(Color.YELLOW);
        enemyRadius++;
        canvas.drawCircle(enemyX, enemyY, enemyRadius, paint);
    }
    /**
     * Checar a colisão entre os elementos
     */
    private void checkCollision(Canvas canvas) {
        /*
         * calcula a hipotenusa
         */
        distance = Math.pow(playerY - enemyY, 2) + Math.pow(playerX - enemyX, 2);
        distance = Math.sqrt(distance);
        /*
         * verifica distancia entre os raios
         */
        if (distance <= playerRadius + enemyRadius) {
            gameOver = true;
        }
    }
    /**
     * Stopping
     */
    private void stopGame(Canvas canvas) {
        running = false;
        paint.setStyle(Style.FILL);
        paint.setColor(Color.RED);
        paint.setTextSize(100);
        canvas.drawText("Fim de jogo!", 50, 200, paint);
    }
    /**
     * Incrementa a pontuação
     */
    public void addScore(int points) {
        score += points;
    }
    /**
     * Score na tela
     */
    private void drawScore(Canvas canvas) {
        paint.setStyle(Style.FILL);
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        canvas.drawText(String.valueOf(score), 50, 200, paint);
    }
    /**
     * Botões de interação com o usuário
     */
    private void drawButtons(Canvas canvas) {
        /*
         * Restart
         */
//        paint.setStyle(Style.FILL);
//        paint.setColor(Color.RED);
//        paint.setTextSize(30);
//        canvas.drawText("Restart", 50, 300, paint);
        canvas.drawBitmap(
            BitmapFactory.decodeResource(
                getResources(),
                R.drawable.reset
            ),
            50,
            300,
            null
        );
        /*
         * Exit
         */
//        paint.setStyle(Style.FILL);
//        paint.setColor(Color.RED);
//        paint.setTextSize(30);
//        canvas.drawText("Exit", 50, 500, paint);
        canvas.drawBitmap(
            BitmapFactory.decodeResource(
                getResources(),
                R.drawable.exit
            ),
            50,
            500,
            null
        );
    }
    /**
     * Reiniciar as propriedades do game
     */
    public void restartGame() {
        enemyX       = enemyY  = enemyRadius = 0;
        playerX      = playerY = 300;
        playerRadius = 50;
        gameOver     = false;
        score        = 0;
    }
}
