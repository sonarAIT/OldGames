import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.String.format;

abstract class Ending{
    final JFrame Frame;
    final BufferStrategy Strategy;
    final Clip[] SoundArray;
    final BufferedImage[] ImageArray;
    private static String[] TextArray;
    final int WINDOW_W;
    final int WINDOW_H;
    Founder_Face Face;
    HashMap<Integer,ArrayList<Star>> StarMap;
    ArrayList<Enemy> EnemyList;
    final Color[] ColorArray;
    static int Score;
    private static Font Font1;
    private static Font Font2;

    Ending(JFrame Frame,BufferStrategy Strategy,Clip[] SoundArray,BufferedImage[] ImageArray,String[] TextArray,int WINDOW_W,int WINDOW_H){
        this.Frame = Frame;
        this.Strategy = Strategy;
        this.SoundArray = SoundArray;
        this.ImageArray = ImageArray;
        Ending.TextArray = TextArray;
        this.WINDOW_W = WINDOW_W;
        this.WINDOW_H = WINDOW_H;
        Font1 = new Font(Font.SERIF,Font.PLAIN,80);
        Font2 = new Font(Font.SERIF,Font.PLAIN,50);
        ColorArray = new Color[]{Color.WHITE,Color.RED,Color.YELLOW,Color.BLUE,Color.ORANGE,Color.MAGENTA};
    }

    abstract void play();

    abstract void setGameData(Founder_Face Face,ArrayList<Enemy> EnemyList,int Score,HashMap<Integer,ArrayList<Star>> StarMap);

    static void printScore(Graphics2D g){
        g.setFont(Font1);
        g.drawString("GameOver!",570,400);
        g.setFont(Font2);
        g.drawString(format("%s%5dm",TextArray[3],Score),500,500);
        g.drawString(format("%s%d",TextArray[4],Score/500+1),450,600);
        g.drawString(format("%s",TextArray[5]),490,700);
    }
}
