import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import java.awt.Image;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

class BlackJack extends JFrame {
  private final static int WINDOW_W = 1000;
  private final static int WINDOW_H = 800;
  private static HashMap<String,ImageIcon> ImageMap = new HashMap<>();
  private static int Count = 0;
  private static long Coin = 1000;
  private ArrayList<String> StringArrayList = new ArrayList<>();

  public static void main(String[] args) throws Exception {
    BlackJack f = new BlackJack();
    f.setTitle("BlackJack");
    f.setDefaultCloseOperation(EXIT_ON_CLOSE);
    f.setSize(WINDOW_W,WINDOW_H);
    f.setLocationRelativeTo(null);
    f.setResizable(false);
    f.setVisible(true);
    f.setLayout(null);
    f.loading();
  }

  private void loading() throws Exception {
    String[] mark = {"c","h","s","d"};
    JProgressBar bar = new JProgressBar(0,56);
    bar.setBounds(250,350,500,50);
    add(bar);
    int loading = 0;
    for(int i = 0; i < 4; i++){
      for(int j = 1; j < 14; j++){
        URL url = this.getClass().getResource(String.format("file/%s%02d.png",mark[i],j));
        ImageIcon tmp = new ImageIcon(url);
        Image tmp2 = tmp.getImage().getScaledInstance((int)(tmp.getIconWidth()*0.5),-1,Image.SCALE_SMOOTH);
        ImageMap.put(String.format("%s%02d",mark[i],j),new ImageIcon(tmp2));
        bar.setValue(++loading);
      }
    }
    for(int i = 0; i < 3; i++){
        try {
            URL url = this.getClass().getResource(String.format("file/%d.png", i));
            ImageIcon tmp = new ImageIcon(url);
            Image tmp2 = tmp.getImage().getScaledInstance((int) (tmp.getIconWidth() * 0.5), -1, Image.SCALE_SMOOTH);
            ImageMap.put(i + "", new ImageIcon(tmp2));
            bar.setValue(++loading);
        }catch(Exception e){
            System.out.print("aa");
        }
    }
    URL url = this.getClass().getResource("file/z02.png");
    ImageIcon tmp = new ImageIcon(url);
    Image tmp2 = tmp.getImage().getScaledInstance((int)(tmp.getIconWidth()*0.5),-1,Image.SCALE_SMOOTH);
    ImageMap.put("z02",new ImageIcon(tmp2));
    bar.setValue(56);
    try{
      BufferedReader fileIn = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("file/Text.txt")));
      String line;
      while((line = fileIn.readLine()) != null){
        StringArrayList.add(line);
      }
      fileIn.close();
    }catch(Exception ignored){
    }
    remove(bar);
    repaint();
    mainLoop();
  }

  private void mainLoop() throws Exception {
    for(;;){
      Game g = new Game(this, Coin, Count,ImageMap, StringArrayList);
      Coin = g.game();
      Count++;
      if(Coin < 0){
          Debt debt = new Debt();
          debt.debt(StringArrayList.get(28), StringArrayList.get(29), StringArrayList.get(30),ImageMap.get(1+""),ImageMap.get(2+""));
          debt.debt2();
          Coin += 1000000;
      }else if(JOptionPane.showConfirmDialog(this, StringArrayList.get(20),"BlackJack",JOptionPane.YES_NO_OPTION) == 1){
        String str = Encrypt.encrypt(Coin +"");
        JOptionPane.showInputDialog(this,String.format("%s%s%s", StringArrayList.get(24),str, StringArrayList.get(25)),"BlackJack",JOptionPane.INFORMATION_MESSAGE,null,null,str);
        System.exit(0);
      }
      g.clear();
    }
  }
}
