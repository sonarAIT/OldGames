import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

class Game{
    private final int GROUND_Y;
    private final JFrame Frame;
    private final BufferStrategy Strategy;
    private final Ending[] EndingArray;
    private final Clip[] SoundArray;
    private final BufferedImage[] ImageArray;
    private final String[] TextArray;
    private final int WINDOW_W;
    private final int WINDOW_H;
    private final HashMap<Integer,ArrayList<Star>> StarMap;
    private final ArrayList<Enemy> EnemyList;
    private final Color[] ColorArray;
    private final int[] Key;
    private Founder_Face Face;
    private Task1 ts1;
    private Task2 ts2;
    private final Font Font;
    private Timeline Timer12;
    private Timer Timer3;
    private Timer Timer4;
    private Timer Timer5;
    private final Random Ra;
    private int endFlag = 0;
    private int Score;
    private int Level = 1;
    private int PoseFlag = 0;

    Game(JFrame Frame,BufferStrategy Strategy,Ending[] EndingArray,Clip[] SoundArray,BufferedImage[] ImageArray,String[] TextArray,int WINDOW_W,int WINDOW_H,int Score){
        this.Frame = Frame;
        this.Strategy = Strategy;
        this.EndingArray = EndingArray;
        this.SoundArray = SoundArray;
        this.ImageArray = ImageArray;
        this.TextArray = TextArray;
        this.WINDOW_W = WINDOW_W;
        this.WINDOW_H = WINDOW_H;
        this.Score = Score;
        Key = new int[6];
        Font = new Font(java.awt.Font.SERIF,java.awt.Font.PLAIN,80);
        EnemyList = new ArrayList<>();
        Ra = new Random();
        GROUND_Y = (int)(850-ImageArray[0].getHeight()/2*1.2);
        ColorArray = new Color[]{Color.WHITE,Color.RED,Color.YELLOW,Color.BLUE,Color.ORANGE,Color.MAGENTA};
        StarMap = new HashMap<>();
        for(int i = 0; i < 6; i++){
            StarMap.put(i,new ArrayList<>());
        }
    }

    void game(){
        for(int i = 0; i < 75; i++){
            StarMap.get(0).add(new Star(Ra.nextInt(1500),Ra.nextInt(800),Ra.nextInt(5)*3+7));
        }
        Face = new Founder_Face(GROUND_Y);
        Face.setXY(272,GROUND_Y);
        Face.setSize(1.2);
        Face.setImage(ImageArray[0]);
        Frame.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_SPACE && endFlag == 0){
                    pose();
                }
                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
                    Key[1] = 1;
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
                    Key[2] = 1;
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
                    Key[3] = 1;
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
                    Key[4] = 1;
                }
            }

            public void keyReleased(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
                    Key[1] = 0;
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
                    Key[2] = 0;
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
                    Key[3] = 0;
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
                    Key[4] = 0;
                }
            }
        });
        ts1 = new Task1();
        ts2 = new Task2();
        Timer12 = new Timeline(60);
        Timer12.getKeyFrames().add(new KeyFrame(Duration.millis(16),e -> ts1.handle()));
        Timer12.getKeyFrames().add(new KeyFrame(Duration.millis(16),e -> ts2.handle()));
        Timer12.setCycleCount(Timeline.INDEFINITE);
        Timer3 = new Timer();
        Timer4 = new Timer();
        Timer5 = new Timer();
        Timer12.play();
        Timer3.schedule(new Task3(),0,100);
        Timer4.schedule(new Task4(),0,3000);
        Timer5.schedule(new Task5(),0,30);
        soundLoop();
        synchronized(this){
          while(endFlag == 0){
              try{
                  wait();
              }catch(Exception ignored){
              }
          }
        }
        Frame.removeKeyListener(Frame.getKeyListeners()[0]);
        Timer3.cancel();
        Timer4.cancel();
        Timer5.cancel();
        soundStop(0);
        Timer12.stop();
        if(Score < 500){
            EndingArray[1].setGameData(Face,EnemyList,Score,StarMap);
            EndingArray[1].play();
        }else if(Score < 1000){
            EndingArray[2].setGameData(Face,EnemyList,Score,StarMap);
            EndingArray[2].play();
        }else if(Score < 1500){
            EndingArray[3].setGameData(Face,EnemyList,Score,StarMap);
            EndingArray[3].play();
        }else if(Score < 2000){
            EndingArray[4].setGameData(Face,EnemyList,Score,StarMap);
            EndingArray[4].play();
        }else if(Score < 2500){
            EndingArray[5].setGameData(Face,EnemyList,Score,StarMap);
            EndingArray[5].play();
        }else{
            EndingArray[6].setGameData(Face,EnemyList,Score,StarMap);
            EndingArray[7].setGameData(Face,EnemyList,Score,StarMap);
            EndingArray[6].play();
            EndingArray[7].play();
        }
    }

    class Task1{
        long now;
        long old = System.nanoTime();

        void handle(){
            now = System.nanoTime();
            double DeltaTime = (double)(now-old)/1000000;
            drawing(DeltaTime/1000);
            //System.out.println(DeltaTime/1000);
            old = now;
        }
    }

    class Task2{
        long now;
        long old = System.nanoTime();

        void handle(){
            now = System.nanoTime();
            double DeltaTime = (double)(now-old)/1000000;
            faceMove(DeltaTime/1000);
            level();
            old = now;
        }
    }

    class Task3 extends TimerTask{
        public void run(){
            Score++;
        }
    }

    class Task4 extends TimerTask{
        public void run(){
            enemySpawn();
            if(Level > 2) enemySpawn();
        }
    }

    class Task5 extends TimerTask{
        public void run(){
            moveStar();
            addStar();
        }
    }

    synchronized private void drawing(double DeltaTime){
        Graphics2D g = (Graphics2D)Strategy.getDrawGraphics();
        g.setBackground(Color.BLACK);
        g.clearRect(0,0,WINDOW_W,WINDOW_H);
        g.setColor(Color.WHITE);
        g.fillRect(0,850,1500,10);
        g.setFont(Font);
        g.drawString(String.format("%s%dm",TextArray[2],Score),490,960);
        g.drawString(String.format("LEVEL:%d",Level),30,100);
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
        Face.showImage(g);
        int size = EnemyList.size();
        for(int i = 0; i < size; i++){
            try{
                EnemyList.get(i).moveMove(DeltaTime);
                if(EnemyList.get(i).getXX() <= -200 || EnemyList.get(i).getXX() >= 1700){
                    EnemyList.remove(i);
                    i = Math.max(0,i-1);
                }else{
                    EnemyList.get(i).showImage(g);
                }
            }catch(Exception ignored){
            }
        }
        size = EnemyList.size();
        for(int i = 0; i < size; i++){
            try{
                int ex = EnemyList.get(i).getXX();
                int ey = EnemyList.get(i).getYY();
                double exs = EnemyList.get(i).getSizeX();
                double eys = EnemyList.get(i).getSizeY();
                int sx = Face.getXX();
                int sy = Face.getYY();
                if((int)(ex-50*exs) <= sx && sx <= (int)(ex+50*exs) && (int)(ey-70*eys) <= sy && sy <= (int)(ey+70*eys)){
                    endFlag = 1;
                    notifyAll();
                }
            }catch(Exception ignored){
            }
        }
        g.dispose();
        Strategy.show();
    }

    private void addStar(){
        int tmp = Ra.nextInt(2);
        if(tmp == 0){
            StarMap.get(0).add(new Star(1550,Ra.nextInt(800),Ra.nextInt(5)*3+7));
        }else{
            tmp = Ra.nextInt(Math.min(6,Level));
            StarMap.get(tmp).add(new Star(1550,Ra.nextInt(800),Ra.nextInt(5)*3+7));
        }
    }

    private void moveStar(){
        for(int i = 0; i < 6; i++){
            ArrayList<Star> starList = StarMap.get(i);
            int size = starList.size();
            for(int j = 0; j < size; j++){
                try{
                    Star star = starList.get(j);
                    star.move();
                    if(star.getX() < -10){
                        starList.remove(j);
                        j = Math.max(0,j-1);
                        if(starList.size() == 0){
                            break;
                        }
                    }
                }catch(Exception ignored){
                }
            }
        }
    }

    private void faceMove(double DeltaTime){
        if(Key[2] == 1) Face.left(DeltaTime);
        if(Key[4] == 1) Face.right(DeltaTime);
        if((Key[0] == 1 || Key[1] == 1) && Face.getJumpFlag() == 0 && Key[3] != 1){
            Face.setJumpFlag(1);
            soundStop(1);
            soundPlay(1);
        }
        if(Key[3] == 1 && Face.getJumpFlag() == 1){
            Face.setJumpFlag(2);
            soundStop(2);
            soundPlay(2);
        }
        Face.Jump(DeltaTime);
    }

    private void level(){
        Level = Score/500+1;
    }

    private void enemySpawn(){
        int tmp = Ra.nextInt(Math.min(Level+1,6))+1;
        Enemy e = null;
        switch(tmp){
            case 1:
                e = new Enemy1();
                break;
            case 2:
                e = new Enemy2();
                break;
            case 3:
                e = new Enemy3(GROUND_Y);
                break;
            case 4:
                e = new Enemy4(GROUND_Y,Ra.nextInt(2));
                break;
            case 5:
                e = new Enemy5();
                break;
            case 6:
                e = new Enemy6();
                break;
        }
        if(tmp != 6){
            Objects.requireNonNull(e).setXY(1600,GROUND_Y);
        }else{
            e.setXY(-100,Ra.nextInt(GROUND_Y));
        }
        e.setSize(1.2);
        e.setImage(ImageArray[tmp]);
        EnemyList.add(e);
    }

    private void pose(){
        if(PoseFlag == 0){
            PoseFlag = 1;
            Timer12.stop();
            Timer3.cancel();
            Timer4.cancel();
            Timer5.cancel();
            SoundArray[0].stop();
        }else{
            PoseFlag = 0;
            ts1.old = System.nanoTime();
            ts2.old = System.nanoTime();
            Timer12.play();

            Timer3 = new Timer();
            Timer4 = new Timer();
            Timer5 = new Timer();
            Timer3.schedule(new Task3(),0,100);
            Timer4.schedule(new Task4(),0,3000);
            Timer5.schedule(new Task5(),0,30);
            SoundArray[0].start();
        }
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

    private void soundLoop(){
        SoundArray[0].setFramePosition(0);
        SoundArray[0].loop(Clip.LOOP_CONTINUOUSLY);
    }

}
