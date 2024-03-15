import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@SuppressWarnings("serial")
class Face_Running extends JFrame{
    private final int WINDOW_W = 1500;
    private final int WINDOW_H = 1000;
    private final int EndingNum = 7;
    private final int SoundNum = 14;
    private final int ImageNum = 9;
    private final int TextNum = 6;
    private final Ending[] EndingArray;
    private final Clip[] SoundArray;
    private final BufferedImage[] ImageArray;
    private final String[] TextArray;
    private BufferStrategy Strategy;

    private Face_Running(){
        TextArray = new String[TextNum];
        ImageArray = new BufferedImage[ImageNum];
        SoundArray = new Clip[SoundNum];
        EndingArray = new Ending[EndingNum+1];
    }

    public static void main(String[] args){
        Face_Running f = new Face_Running();
        f.start();
    }

    private void start(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_W,WINDOW_H);
        setLocationRelativeTo(null);
        setResizable(false);
        setIgnoreRepaint(true);
        setVisible(true);
        createBufferStrategy(2);
        Strategy = getBufferStrategy();
        loading();
    }

    private void loading(){
        setLayout(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0,0,WINDOW_W,WINDOW_H);
        panel.setBackground(Color.BLACK);
        JLabel label = new JLabel();
        label.setFont(new Font(Font.SERIF,Font.PLAIN,50));
        label.setText("The persons and events in this motion picture are fictitious.");
        label.setBounds(150,150,1500,100);
        label.setForeground(Color.WHITE);
        panel.add(label);
        JLabel label2 = new JLabel();
        label2.setFont(new Font(Font.SERIF,Font.PLAIN,50));
        label2.setText("Any similarity to actual persons or events is unintentional.");
        label2.setBounds(160,250,1500,100);
        label2.setForeground(Color.WHITE);
        panel.add(label2);
        JProgressBar bar = new JProgressBar(0,EndingNum+SoundNum+ImageNum+TextNum);
        bar.setBounds(WINDOW_W/2-500,WINDOW_H/2-50,1000,50);
        panel.add(bar);
        int barnum = 0;
        add(panel);
        repaint();
        try{
            BufferedReader fileIn = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("File/Text.txt")));
            String line;
            int i = 0;
            while((line = fileIn.readLine()) != null){
                TextArray[i] = line;
                i++;
                barnum++;
                bar.setValue(barnum);
            }
            fileIn.close();
        }catch(Exception ignored){
        }
        setTitle(TextArray[0]);
        try{
            for(int i = 0; i < ImageNum; i++){
                URL url = this.getClass().getResource(String.format("File/i%d.png",i));
                ImageArray[i] = ImageIO.read(url);
                barnum++;
                bar.setValue(barnum);
            }
        }catch(Exception ignored){
        }
        try{
            for(int i = 0; i < SoundNum; i++){
                AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource(String.format("File/s%d.wav",i)));
                SoundArray[i] = AudioSystem.getClip();
                SoundArray[i].open(audio);
                barnum++;
                bar.setValue(barnum);
            }
        }catch(Exception ignored){
        }
        EndingArray[1] = new Ending1(this,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
        barnum++;
        bar.setValue(barnum);
        EndingArray[2] = new Ending2(this,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
        barnum++;
        bar.setValue(barnum);
        EndingArray[3] = new Ending3(this,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
        barnum++;
        bar.setValue(barnum);
        EndingArray[4] = new Ending4(this,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
        barnum++;
        bar.setValue(barnum);
        EndingArray[5] = new Ending5(this,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
        barnum++;
        bar.setValue(barnum);
        EndingArray[6] = new Ending6(this,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
        barnum++;
        bar.setValue(barnum);
        EndingArray[7] = new Ending62(this,Strategy,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H);
        barnum++;
        bar.setValue(barnum);
        remove(panel);
        mainLoop();
    }

    private void mainLoop(){
        Title title = new Title(this,Strategy,ImageArray[0],TextArray,WINDOW_W,WINDOW_H);
        int Score = title.start();
        Game game = new Game(this,Strategy,EndingArray,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H,Score);
        game.game();
        while(true){
            game = new Game(this,Strategy,EndingArray,SoundArray,ImageArray,TextArray,WINDOW_W,WINDOW_H,0);
            game.game();
        }
    }
}
