import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

class Ending1 extends Ending{
    private int Toggle1 = 0;
    private int Toggle2 = 5;
    private int endFlag = 0;

    Ending1(JFrame Frame,BufferStrategy Strategy,Clip[] SoundArray,BufferedImage[] ImageArray,String[] TextArray,int WINDOW_W,int WINDOW_H){
        super(Frame,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
    }

    void setGameData(Founder_Face Face,ArrayList<Enemy> EnemyList,int Score,HashMap<Integer,ArrayList<Star>> StarMap){
        this.Face = Face;
        this.EnemyList = EnemyList;
        Ending.Score = Score;
        this.StarMap = StarMap;
    }

    void play(){
        soundPlay(3);
        Face.setImage(ImageArray[7]);
        Face.nextImage();
        Timeline timer = new Timeline(60);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(16),e->end1DrawIng()));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        synchronized(this){
            while(endFlag == 0 && Toggle1 != 1){
                try{
                    wait();
                }catch(Exception ignored){
                }
            }
        }
        timer.stop();
        soundStop(4);
        soundStop(5);
        Frame.removeKeyListener(Frame.getKeyListeners()[0]);
        endFlag = 0;
        Toggle1 = 0;
    }

    private void end1DrawIng(){
        Graphics2D g = (Graphics2D)Strategy.getDrawGraphics();
        g.setBackground(Color.BLACK);
        g.clearRect(0,0,WINDOW_W,WINDOW_H);
        g.setColor(Color.WHITE);
        g.fillRect(0,850,1500,10);
        for(int i = 0; i < 6; i++){
            try{
                g.setColor(ColorArray[i]);
                ArrayList<Star> starList = StarMap.get(i);
                for(Star star : starList){
                    g.drawRect(star.getX(),star.getY(),1,1);
                }
            }catch(Exception ignored){
            }
        }
        for(Enemy enemy : EnemyList){
            try{
                enemy.showImage(g);
            }catch(Exception ignored){
            }
        }
        if(Face.getAngle() < 90){
            Face.rotateImage2(g,1);
            Face.setX2(Toggle2*-1);
            Toggle2 *= -1;
        }else if(Toggle1 == 0){
            Face.rotateImage(g);
            soundStop(3);
            soundPlay(4);
            soundPlay(5);
            Toggle1 = 1;
            Frame.addKeyListener(new KeyAdapter(){
                @Override
                public void keyPressed(KeyEvent e){
                    if(e.getKeyCode() == KeyEvent.VK_Z){
                        skip();
                    }
                }
            });
        }else{
            Face.rotateImage(g);
            if(Face.getJumpFlag() == 0) Face.setY(780);
        }
        Face.showImage(g);
        Face.reset(g);
        g.setColor(Color.WHITE);
        if(Toggle1 == 1) printScore(g);
        g.dispose();
        Strategy.show();
    }

    private synchronized void skip(){
        endFlag = 1;
        notifyAll();
    }

    private void soundPlay(int index){
        SoundArray[index].setFramePosition(0);
        SoundArray[index].start();
    }

    private void soundStop(int index){
        SoundArray[index].stop();
        SoundArray[index].flush();
        SoundArray[index].setFramePosition(0);
    }

}
