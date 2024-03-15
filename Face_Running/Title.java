import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

class Title{
    private final JFrame Frame;
    private final BufferStrategy Strategy;
    private final BufferedImage Image;
    private final String[] TextArray;
    private final int WINDOW_W;
    private final int WINDOW_H;
    private final Font Font1;
    private final Font Font2;
    private int EndFlag;
    private int XCount;
    private int Score;

    Title(JFrame Frame,BufferStrategy Strategy,BufferedImage Image,String[] TextArray,int WINDOW_W,int WINDOW_H){
        this.Frame = Frame;
        this.Strategy = Strategy;
        this.Image = Image;
        this.TextArray = TextArray;
        this.WINDOW_W = WINDOW_W;
        this.WINDOW_H = WINDOW_H;
        Font1 = new Font(Font.SERIF,Font.PLAIN,80);
        Font2 = new Font(Font.SERIF,Font.PLAIN,50);
        EndFlag = 0;
        XCount = 0;
    }

    int start(){
        Frame.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_Z){
                    EndFlag = 1;
                }
                if(e.getKeyCode() == KeyEvent.VK_X){
                    XCount++;
                }
            }
        });
        Timer t = new Timer();
        t.schedule(new Task(),0,1);
        while(EndFlag == 0){
            System.out.print("");
            if(XCount == 5){
                XCount = 0;
                try{
                    Score = Integer.parseInt(JOptionPane.showInputDialog("Set Score"));
                    JOptionPane.showMessageDialog(null,"Complete.");
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"ERROR!");
                }
            }
        }
        Frame.removeKeyListener(Frame.getKeyListeners()[0]);
        t.cancel();
        return Score;
    }

    class Task extends TimerTask{
        public void run(){
            drawing();
        }
    }

    private void drawing(){
        Graphics2D g = (Graphics2D)Strategy.getDrawGraphics();
        g.setBackground(Color.BLACK);
        g.clearRect(0,0,WINDOW_W,WINDOW_H);
        g.setColor(Color.WHITE);
        g.fillRect(0,850,1500,10);
        g.drawImage(Image,200,(int)(850-Image.getHeight()*1.2),(int)(Image.getWidth()*1.2),(int)(Image.getHeight()*1.2),null);
        g.setFont(Font1);
        g.drawString(TextArray[0],510,350);
        g.setFont(Font2);
        g.drawString(TextArray[1],560,500);
        g.dispose();
        Strategy.show();
    }
}
