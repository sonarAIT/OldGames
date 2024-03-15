import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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
import java.util.Timer;
import java.util.TimerTask;

class Ending62 extends Ending{
    private final MoviePanel Movie;
    private final Media media;
    private final MediaPlayer player;
    private int EndFlag;

    Ending62(JFrame Frame,BufferStrategy Strategy,Clip[] SoundArray,BufferedImage[] ImageArray,String[] TextArray,int WINDOW_W,int WINDOW_H){
        super(Frame,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
        EndFlag = 0;
        Movie = new MoviePanel("File/Ending6S.mp4",Frame);
        player = Movie.getPlayer();
        media = Movie.getMedia();
        while(player.getStatus() != MediaPlayer.Status.READY){
            System.out.print("");
        }
        Frame.setLayout(null);
        Frame.add(Movie);
        Movie.setVisible(false);
    }

    @Override
    void setGameData(Founder_Face Face,ArrayList<Enemy> EnemyList,int Score,HashMap<Integer,ArrayList<Star>> StarMap){
        Ending.Score = Score;
    }

    void play(){
        Frame.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_Z){
                    skipORend();
                }
            }
        });
        moviePlay();
        EndFlag = 1;
        while(EndFlag == 1){
            if(media.getDuration().toMillis()-5000 <= player.getCurrentTime().toMillis()){
                try{
                    Thread.sleep(5000);
                    movieEnd();
                }catch(Exception ignore){
                }
                break;
            }
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new End3Task(),0,1);
        while(EndFlag == 2){
            System.out.print("");
        }
        EndFlag = 0;
        Frame.removeKeyListener(Frame.getKeyListeners()[0]);
        timer.cancel();
    }

    private void skipORend(){
        if(EndFlag == 1){
            movieEnd();
            EndFlag = 2;
        }else if(EndFlag == 2){
            EndFlag = 3;
        }
    }

    class End3Task extends TimerTask{
        public void run(){
            End3DrawIng2();
        }
    }

    private void moviePlay(){
        player.seek(javafx.util.Duration.seconds(0.0));
        Movie.setBounds(0,0,media.getWidth(),media.getHeight());
        Movie.setVisible(true);
        player.play();
        Frame.repaint();
    }

    private void movieEnd(){
        player.stop();
        Movie.setVisible(false);
        EndFlag = 2;
    }

    private void End3DrawIng2(){
        Graphics2D g = (Graphics2D)Strategy.getDrawGraphics();
        g.setBackground(Color.BLACK);
        g.clearRect(0,0,WINDOW_W,WINDOW_H);
        g.setColor(Color.WHITE);
        printScore(g);
        g.dispose();
        Strategy.show();
    }

}
