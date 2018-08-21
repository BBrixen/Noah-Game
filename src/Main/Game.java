package Main;

import rendering.Display;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {//base code for game

    private Display display;
    private Thread thread;
    private int width, height;
    private boolean running = false;
    public String title;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void init() {
        display = new Display(title, width, height);
    }

    private void update() {
        //where you change the value of variables
    }

    private void render() {
        bufferStrategy = display.getCanvas().getBufferStrategy();
        if(bufferStrategy == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, width, height);
        //************** Draw to screen here **************//
        //for loading image use only ImageLoader.loadImage("/res/[name].[file extension]");
        //also, load images in the init method
        //graphics.drawImage(image, x, y, null); will draw an image

        //****************** End drawing ******************//
        bufferStrategy.show();
        graphics.dispose();
    }

    public void run(){
        init();

        int fps = 60;
        double time_per_tick = 1000000000 / fps;
        double delta = 0;
        long now;
        long last_time = System.nanoTime();

        //for fps counter,
        //not the fps, but a counter to make sure the fps is at the correct value
        long timer = 0;
        int ticks = 0;

        while (running) {

            //sets variable values for checking
            //if we need to run the update() and render() methods
            now = System.nanoTime();
            delta += (now - last_time) / time_per_tick;
            timer += now - last_time;   //for fps counter
            last_time = now;

            if(delta >= 1) { //checks if we need to run update() and render()
                //runs method
                update();
                render();

                ticks++; //for fps counter
                delta--;
            }

            if (timer == 1000000000) { //all of this is for the fps counter
                System.out.println("Ticks and frames: " + ticks);
                //should print out around 60 (b/t 58 and 62) once a second
                ticks = 0;
                timer = 0;
            }
        }
        stop(); //stops the thread that is running
    }

    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start(); // thread.start calls the run() method
    }

    public synchronized void stop(){

        if (!running) {
            return;
        }
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
