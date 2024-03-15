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

class Ending2 extends Ending{
    private ImageAnimationC[] FaceArray;
    private int OFlag = 0;
    private int EndFlag = 0;
    private double SinI = 0;

    Ending2(JFrame Frame,BufferStrategy Strategy,Clip[] SoundArray,BufferedImage[] ImageArray,String[] TextArray,int WINDOW_W,int WINDOW_H){
        super(Frame,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
        FaceArray = new ImageAnimationC[8];
        for(int i = 0; i < 8; i++){
            FaceArray[i] = new ImageAnimationC();
            FaceArray[i].setImage(ImageArray[0]);
        }
    }

    void setGameData(Founder_Face Face,ArrayList<Enemy> EnemyList,int Score,HashMap<Integer,ArrayList<Star>> StarMap){
        this.Face = Face;
        this.EnemyList = EnemyList;
        Ending.Score = Score;
        this.StarMap = StarMap;
    }

    void play(){
        soundPlay();
        Timeline timer = new Timeline(60);
        timer.getKeyFrames().add(new KeyFrame(Duration.millis(16),e->end2DrawIng()));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        Frame.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_Z){
                    skip();
                }
            }
        });
        while(SoundArray[6].getFramePosition() < 720000){
            if(EndFlag == 1) SoundArray[6].setFramePosition(720000);
        }
        OFlag = 1;
        EndFlag = 1;
        while(EndFlag == 1){
            try{
                wait();
            }catch(Exception ignored){
            }
        }
        Frame.removeKeyListener(Frame.getKeyListeners()[0]);
        timer.stop();
        OFlag = 0;
        EndFlag = 0;
        SinI = 0;
        FaceArray = new ImageAnimationC[8];
        for(int i = 0; i < 8; i++){
            FaceArray[i] = new ImageAnimationC();
            FaceArray[i].setImage(ImageArray[0]);
        }
        soundStop();
    }

    private synchronized void skip(){
        EndFlag = Math.min(EndFlag+1,2);
    }

    private void end2DrawIng(){
        Graphics2D g = (Graphics2D)Strategy.getDrawGraphics();
        g.setBackground(Color.BLACK);
        g.clearRect(0,0,WINDOW_W,WINDOW_H);
        if(OFlag == 0){
            if(Face.getXX() < 750) Face.setX2(16);
            if(Face.getXX() > 750) Face.setX2(-16);
            if(Face.getYY() < 500) Face.setY2(16);
            if(Face.getYY() > 500) Face.setY2(-16);
            Face.rotateImage2(g,40);
            Face.showImage(g);
            g.dispose();
            Strategy.show();
        }else{
            FaceArray[1].setSize(Math.sin(SinI)*5);
            FaceArray[3].setSize(Math.sin(SinI)*5);
            FaceArray[5].setSize(Math.sin(SinI)*5);
            FaceArray[7].setSize(Math.sin(SinI)*5);
            FaceArray[0].setXY(750,(int)(500+500*Math.sin(SinI)));
            FaceArray[1].setXY((int)(750+500*Math.sin(SinI)),(int)(500+500*Math.sin(SinI)));
            FaceArray[2].setXY((int)(750+500*Math.sin(SinI)),500);
            FaceArray[3].setXY((int)(750+500*Math.sin(SinI)),(int)(500+500*Math.sin(SinI)*-1));
            FaceArray[4].setXY(750,(int)(500+500*Math.sin(SinI)*-1));
            FaceArray[5].setXY((int)(750+500*Math.sin(SinI)*-1),(int)(500+500*Math.sin(SinI)*-1));
            FaceArray[6].setXY((int)(750+500*Math.sin(SinI)*-1),500);
            FaceArray[7].setXY((int)(750+500*Math.sin(SinI)*-1),(int)(500+500*Math.sin(SinI)));
            for(int i = 0; i < 8; i++) FaceArray[i].showImage(g);
            g.setColor(Color.WHITE);
            printScore(g);
            SinI += 0.1;
            g.dispose();
            Strategy.show();
        }
    }

    private void soundPlay(){
        SoundArray[6].setFramePosition(0);
        SoundArray[6].start();
    }

    private void soundStop(){
        SoundArray[6].stop();
        SoundArray[6].flush();
        SoundArray[6].setFramePosition(0);
    }

}
