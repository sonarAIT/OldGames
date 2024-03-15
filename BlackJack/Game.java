import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

class Game{
  private final String BURST = "BURST!";
  private final static int WINDOW_W = 1000;
  private final static int WINDOW_H = 800;
  private JButton[] Button = new JButton[3];
  private JFrame f;
  private JLabel[] MyCardLabel = new JLabel[27];
  private JLabel[] EnemyCardLabel = new JLabel[10];
  private JLabel[] BetLabel = new JLabel[2];
  private JLabel[] MyPointLabel = new JLabel[2];
  private JLabel EnemyPointLabel;
  private JLabel CoinLabel;
  private JLabel NowPointLabel;
  private JLabel NowBetLabel;
  private JPanel UpperPanel;
  private JPanel UnderPanel;
  private HashMap<String,ImageIcon> ImageMap;
  private ArrayList<String> StringArrayList;
  private ArrayList<String> MyCardList = new ArrayList<>();
  private ArrayList<Integer> MyCardNum = new ArrayList<>();
  private ArrayList<String> EnemyCardList = new ArrayList<>();
  private ArrayList<Integer> EnemyCardNum = new ArrayList<>();
  private ArrayList<Integer> JudgeCardList;
  private Random ra;
  private String MyPoint;
  private String LastMyPoint;
  private String JudgePoint;
  private long Coin;
  private int[][] CardFlag = new int[4][14];
  private int Bet = 100;
  private int LastBet;
  private int Count;
  private int GameFlag;
  private int NowCardIdx;
  private int LastButton = -1;
  private int MyQuantity;
  private int LastMyQuantity;

  Game(JFrame f, long coin, int count, HashMap<String,ImageIcon> ImageMap, ArrayList<String> StringArrayList){
    this.f = f;
    this.Coin = coin;
    this.Count = count;
    this.ImageMap = ImageMap;
    this.StringArrayList = StringArrayList;
    ra = new Random();
  }

  long game(){
    Font font = new Font(Font.DIALOG,Font.PLAIN,25);
    Color green = new Color(0,102,0);
    UpperPanel = new JPanel();
    UnderPanel = new JPanel();
    UpperPanel.setLayout(null);
    UpperPanel.setBounds(0,0,WINDOW_W,WINDOW_H-150);
    UpperPanel.setBackground(green);
    for(int i = 9; i >= 0; i--){
      EnemyCardLabel[i] = new JLabel();
      EnemyCardLabel[i].setBounds(300+30*i,30+i%2*5,120,160);
      EnemyCardLabel[i].setIcon(null);
      UpperPanel.add(EnemyCardLabel[i]);
    }
    for(int i = 26; i >= 0; i--){
      MyCardLabel[i] = new JLabel();
      MyCardLabel[i].setBounds(75+30*i,420+i%2*5,120,160);
      MyCardLabel[i].setIcon(null);
      UpperPanel.add(MyCardLabel[i]);
    }
    f.add(UpperPanel);
    UnderPanel.setLayout(null);
    UnderPanel.setBounds(0,WINDOW_H-150,WINDOW_W,150);
    UnderPanel.setBackground(Color.DARK_GRAY);
    for(int i = 0; i < 3; i++){
      Button[i] = new JButton();
      Button[i].setBounds(220*i+80,10,200,105);
      Button[i].setFont(font);
      Button[i].setText(StringArrayList.get(2+i));
      Button[i].setEnabled(false);
      final int ii = i;
      Button[i].addActionListener(e -> LastButton = ii);
      UnderPanel.add(Button[i]);
    }
    CoinLabel = new JLabel(Coin +"");
    CoinLabel.setBounds(750,15,200,90);
    CoinLabel.setOpaque(true);
    CoinLabel.setFont(font);
    CoinLabel.setHorizontalAlignment(JLabel.CENTER);
    CoinLabel.setBorder(new LineBorder(Color.RED,7,true));
    CoinLabel.setBackground(Color.WHITE);
    UnderPanel.add(CoinLabel);
    f.add(UnderPanel);
    f.repaint();
    if(Count == 0)JOptionPane.showMessageDialog(f, StringArrayList.get(0),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
    if(Count == 0) encrypt();
    bet();
    JOptionPane.showMessageDialog(f, StringArrayList.get(1),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
    MyCardList.add(getCard());
    MyCardLabel[12].setIcon(ImageMap.get(MyCardList.get(0)));
    MyCardNum.add(Math.min(10,Integer.parseInt(MyCardList.get(0).substring(1,3))));
    MyCardList.add(getCard());
    MyCardLabel[13].setIcon(ImageMap.get(MyCardList.get(1)));
    MyCardNum.add(Math.min(10,Integer.parseInt(MyCardList.get(1).substring(1,3))));
    EnemyCardList.add(getCard());
    EnemyCardNum.add(Math.min(10,Integer.parseInt(EnemyCardList.get(0).substring(1,3))));
    EnemyCardLabel[0].setIcon(ImageMap.get(EnemyCardList.get(0)));
    EnemyCardList.add(getCard());
    EnemyCardNum.add(Math.min(10,Integer.parseInt(EnemyCardList.get(1).substring(1,3))));
    EnemyCardLabel[1].setIcon(ImageMap.get("z02"));
    if((MyCardNum.get(0)==1&& MyCardNum.get(1)==10)||(MyCardNum.get(0)==10&& MyCardNum.get(1)==1)){
      BJGame();
    }else if(MyCardNum.get(0).equals(MyCardNum.get(1))){
      if(JOptionPane.showConfirmDialog(f, StringArrayList.get(9),"BlackJack",JOptionPane.YES_NO_OPTION) == 0){
        splitGame();
      }else{
        normalGame();
      }
    }else{
      normalGame();
    }
    return Coin;
  }

  private void bet(){
    try {
      int fix = 0;
      String[] betSelect = {StringArrayList.get(6), StringArrayList.get(7), "100", "500", "1000"};
      while (fix == 0) {
        int tmp = JOptionPane.showOptionDialog(f, StringArrayList.get(5) + Bet + ")","BlackJack",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, ImageMap.get(0+""), betSelect, betSelect[0]);
        switch (tmp) {
          case 0:
          fix = 1;
          break;
          case 1:
          Bet = 100;
          break;
          case 2:
          Bet += 100;
          break;
          case 3:
          Bet += 500;
          break;
          case 4:
          Bet += 1000;
          break;
        }
      }
      LastBet = Math.max(Bet,100);
      Coin -= Math.max(Bet,100);
      CoinLabel.setText(Coin + "");
    }catch(Exception e){
      JOptionPane.showMessageDialog(f, StringArrayList.get(8),"BlackJack",JOptionPane.ERROR_MESSAGE);
      bet();
    }
  }

  private String getCard(){
    String[] mark = {"c","h","s","d"};
    int tmp = ra.nextInt(4);
    int tmp2 = ra.nextInt(13)+1;
    while(CardFlag[tmp][tmp2] != 0){
      tmp = ra.nextInt(4);
      tmp2 = ra.nextInt(13)+1;
    }
    CardFlag[tmp][tmp2] = 1;
    return String.format("%s%02d",mark[tmp],tmp2);
  }

  private int returnButton(){
    LastButton = -1;
    while(LastButton == -1){
      System.out.print("");
    }
    if(LastButton != 1) Button[2].setEnabled(false);
    return LastButton;
  }

  private void normalGame(){
    Font font2 = new Font(Font.DIALOG,Font.PLAIN,20);
    BetLabel[0] = new JLabel(Bet + "");
    NowPointLabel = BetLabel[0];
    BetLabel[0].setBounds(450,590,100,50);
    BetLabel[0].setOpaque(true);
    BetLabel[0].setFont(font2);
    BetLabel[0].setHorizontalAlignment(JLabel.CENTER);
    BetLabel[0].setBorder(new LineBorder(Color.RED, 7, false));
    BetLabel[0].setBackground(Color.WHITE);
    MyPointLabel[0] = new JLabel(MyPoint);
    NowBetLabel = MyPointLabel[0];
    JudgeCardList = MyCardNum;
    JudgePoint = MyPoint;
    judge();
    MyPointLabel[0].setBounds(450,350,100,50);
    MyPointLabel[0].setOpaque(true);
    MyPointLabel[0].setFont(font2);
    MyPointLabel[0].setHorizontalAlignment(JLabel.CENTER);
    MyPointLabel[0].setBorder(new LineBorder(Color.RED, 7, false));
    MyPointLabel[0].setBackground(Color.WHITE);
    EnemyPointLabel  = new JLabel();
    EnemyPointLabel.setBounds(325,210,100,50);
    EnemyPointLabel.setOpaque(true);
    EnemyPointLabel.setFont(font2);
    EnemyPointLabel.setHorizontalAlignment(JLabel.CENTER);
    EnemyPointLabel.setBorder(new LineBorder(Color.RED, 7, false));
    EnemyPointLabel.setBackground(Color.WHITE);
    UpperPanel.add(BetLabel[0]);
    UpperPanel.add(MyPointLabel[0]);
    UpperPanel.add(EnemyPointLabel);
    f.repaint();
    NowCardIdx = 14;
    for(int i = 0; i < 3; i++){
      Button[i].setEnabled(true);
    }
    smallGame();
    MyQuantity = MyCardList.size();
    if(MyPoint.equals(BURST)){
      EnemyCardLabel[1].setIcon(ImageMap.get(EnemyCardList.get(1)));
      JudgeCardList = EnemyCardNum;
      NowBetLabel = EnemyPointLabel;
      judge();
    }else{
      enemy();
    }
    end();
  }

  private void BJGame(){
    Font font2 = new Font(Font.DIALOG,Font.PLAIN,20);
    JOptionPane.showMessageDialog(f,"BLACK JACK!","BlackJack",JOptionPane.INFORMATION_MESSAGE);
    MyPoint = "21";
    MyQuantity = 2;
    BetLabel[0] = new JLabel(Bet + "");
    BetLabel[0].setBounds(450,590,100,50);
    BetLabel[0].setOpaque(true);
    BetLabel[0].setFont(font2);
    BetLabel[0].setHorizontalAlignment(JLabel.CENTER);
    BetLabel[0].setBorder(new LineBorder(Color.RED, 7, false));
    BetLabel[0].setBackground(Color.WHITE);
    MyPointLabel[0] = new JLabel("BJ");
    MyPointLabel[0].setBounds(450,350,100,50);
    MyPointLabel[0].setOpaque(true);
    MyPointLabel[0].setFont(font2);
    MyPointLabel[0].setHorizontalAlignment(JLabel.CENTER);
    MyPointLabel[0].setBorder(new LineBorder(Color.RED, 7, false));
    MyPointLabel[0].setBackground(Color.WHITE);
    EnemyPointLabel  = new JLabel();
    EnemyPointLabel.setBounds(325,210,100,50);
    EnemyPointLabel.setOpaque(true);
    EnemyPointLabel.setFont(font2);
    EnemyPointLabel.setHorizontalAlignment(JLabel.CENTER);
    EnemyPointLabel.setBorder(new LineBorder(Color.RED, 7, false));
    EnemyPointLabel.setBackground(Color.WHITE);
    UpperPanel.add(BetLabel[0]);
    UpperPanel.add(MyPointLabel[0]);
    UpperPanel.add(EnemyPointLabel);
    f.repaint();
    enemy();
    end();
  }

  private void smallGame(){
    GameFlag = 0;
    while(GameFlag == 0) {
      int next = returnButton();
      switch (next) {
        case 0:
        hit();
        break;
        case 1:
        stand();
        break;
        case 2:
        doubleDown();
        break;
      }
    }
  }

  private void hit(){
    MyCardList.add(getCard());
    MyCardNum.add(Math.min(10,Integer.parseInt(MyCardList.get(MyCardList.size()-1).substring(1,3))));
    MyCardLabel[NowCardIdx].setIcon(ImageMap.get(MyCardList.get(MyCardList.size()-1)));
    judge();
    MyPoint = JudgePoint;
    NowCardIdx++;
    if(MyPoint.equals(BURST)){
      JOptionPane.showMessageDialog(f, StringArrayList.get(12),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
      GameFlag = 1;
    }
  }

  private void stand(){
    judge();
    MyPoint = JudgePoint;
    if(JOptionPane.showConfirmDialog(f, StringArrayList.get(10),"BlackJack",JOptionPane.YES_NO_OPTION) == 0) GameFlag = 1;
  }

  private void doubleDown(){
    Coin -= Bet;
    Bet += Bet;
    NowPointLabel.setText(Bet + "");
    CoinLabel.setText(Coin + "");
    hit();
    Button[0].setEnabled(false);
    Button[2].setEnabled(false);
  }

  private void judge(){
    int[] number = new int[11];
    for(int i : JudgeCardList){
      number[i]++;
    }
    int hoz = 999;
    int hoz2 = -1;
    for(int i = 0; i <= number[1]; i++){
      int sum = 0;
      for(int j = 2; j <= 10; j++){
        for(int k = 1; k <= number[j]; k++){
          sum += j;
        }
      }
      for(int j = 1; j <= i; j++){
        sum += 11;
      }
      for(int j = 1; j <= number[1]-i; j++){
        sum += 1;
      }
      int tmp = Math.abs(sum-21);
      if(hoz > tmp && sum < 22){
        hoz = tmp;
        hoz2 = sum;
      }
    }
    if(hoz2 != -1){
      JudgePoint = hoz2+"";
    }else{
      JudgePoint = BURST;
    }
    NowBetLabel.setText(JudgePoint);
    f.repaint();
  }

  private void splitGame(){
    Font font2 = new Font(Font.DIALOG,Font.PLAIN,20);
    String[] rightCard = new String[2];
    int[] rightNum = new int[2];
    Coin -= Math.max(100, Bet);
    CoinLabel.setText(Coin +"");
    MyCardList.add(getCard());
    MyCardNum.add(Math.min(10,Integer.parseInt(MyCardList.get(2).substring(1,3))));
    MyCardList.add(getCard());
    MyCardNum.add(Math.min(10,Integer.parseInt(MyCardList.get(3).substring(1,3))));
    MyCardLabel[12].setIcon(null);
    MyCardLabel[0].setIcon(ImageMap.get(MyCardList.get(0)));
    MyCardLabel[1].setIcon(ImageMap.get(MyCardList.get(2)));
    MyCardLabel[13].setIcon(null);
    MyCardLabel[16].setIcon(ImageMap.get(MyCardList.get(1)));
    MyCardLabel[17].setIcon(ImageMap.get(MyCardList.get(3)));
    NowCardIdx = 2;
    JudgeCardList = MyCardNum;
    JudgePoint = MyPoint;
    rightCard[0] = MyCardList.get(1);
    rightNum[0] = MyCardNum.get(1);
    MyCardList.remove(1);
    MyCardNum.remove(1);
    rightCard[1] = MyCardList.get(2);
    rightNum[1] = MyCardNum.get(2);
    MyCardList.remove(2);
    MyCardNum.remove(2);
    for(int i = 0; i < 2; i++) {
      BetLabel[i] = new JLabel(Bet + "");
      BetLabel[i].setBounds(100+475*i,590,100,50);
      BetLabel[i].setOpaque(true);
      BetLabel[i].setFont(font2);
      BetLabel[i].setHorizontalAlignment(JLabel.CENTER);
      BetLabel[i].setBorder(new LineBorder(Color.RED, 7, false));
      BetLabel[i].setBackground(Color.WHITE);
      UpperPanel.add(BetLabel[i]);
      MyPointLabel[i] = new JLabel((rightNum[0]==1?11:rightNum[0])+(rightNum[1]==1?11:rightNum[1])+"");
      MyPointLabel[i].setBounds(100+475*i,350,100,50);
      MyPointLabel[i].setOpaque(true);
      MyPointLabel[i].setFont(font2);
      MyPointLabel[i].setHorizontalAlignment(JLabel.CENTER);
      MyPointLabel[i].setBorder(new LineBorder(Color.RED, 7, false));
      MyPointLabel[i].setBackground(Color.WHITE);
      UpperPanel.add(MyPointLabel[i]);
    }
    NowPointLabel = BetLabel[0];
    NowBetLabel = MyPointLabel[0];
    judge();
    EnemyPointLabel  = new JLabel();
    EnemyPointLabel.setBounds(325,210,100,50);
    EnemyPointLabel.setOpaque(true);
    EnemyPointLabel.setFont(font2);
    EnemyPointLabel.setHorizontalAlignment(JLabel.CENTER);
    EnemyPointLabel.setBorder(new LineBorder(Color.RED, 7, false));
    EnemyPointLabel.setBackground(Color.WHITE);
    UpperPanel.add(EnemyPointLabel);
    f.repaint();
    for(int i = 0; i < 3; i++){
      Button[i].setEnabled(true);
    }
    smallGame();
    NowCardIdx = 18;
    LastMyQuantity = MyCardList.size();
    LastMyPoint = MyPoint;
    int tmp = LastBet;
    LastBet = Bet;
    Bet = tmp;
    MyCardList.clear();
    MyCardNum.clear();
    for(int i = 0; i < 2; i++){
      MyCardList.add(rightCard[i]);
      MyCardNum.add(rightNum[i]);
    }
    NowBetLabel = MyPointLabel[1];
    NowPointLabel = BetLabel[1];
    judge();
    Button[0].setEnabled(true);
    Button[2].setEnabled(true);
    JOptionPane.showMessageDialog(f, StringArrayList.get(11),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
    smallGame();
    MyQuantity = MyCardList.size();
    if(MyPoint.equals(BURST)&& LastMyPoint.equals((BURST))){
      EnemyCardLabel[1].setIcon(ImageMap.get(EnemyCardList.get(1)));
      JudgeCardList = EnemyCardNum;
      NowBetLabel = EnemyPointLabel;
      judge();
    }else{
      enemy();
    }
    end();
  }

  private void enemy(){
    try {
      Thread.sleep(500);
    } catch(InterruptedException ignored){
    }
    JudgeCardList = EnemyCardNum;
    NowBetLabel = EnemyPointLabel;
    judge();
    EnemyCardLabel[1].setIcon(ImageMap.get(EnemyCardList.get(1)));
    f.repaint();
    int en = Integer.parseInt(JudgePoint);
    while(en < 17){
      try {
        Thread.sleep(500);
      } catch(InterruptedException ignored){
      }
      EnemyCardList.add(getCard());
      EnemyCardNum.add(Math.min(10,Integer.parseInt(EnemyCardList.get(EnemyCardList.size()-1).substring(1,3))));
      EnemyCardLabel[EnemyCardList.size()-1].setIcon(ImageMap.get(EnemyCardList.get(EnemyCardList.size()-1)));
      judge();
      try{
        en = Integer.parseInt(JudgePoint);
      }catch(Exception e){
        break;
      }
    }
  }

  private void end(){
    if(LastMyPoint != null){
      String left;
      String right;
      String BJ = "(BJ)";
      double LBJRate = 2;
      double RBJRate = 2;
      if(LastMyPoint.equals("21")&& LastMyQuantity ==2)LBJRate = 2.5;
      if(MyPoint.equals("21")&& MyCardList.size()==2)RBJRate = 2.5;
      if(JudgePoint.equals(BURST)) JudgePoint = "-1";
      if(LastMyPoint.equals(BURST)){
        left = "0";
      }else{
        if(LastMyPoint.equals("21")&& JudgePoint.equals("21")&& LastMyQuantity < EnemyCardList.size()){
          left = (LastBet *LBJRate) + "";
          if(LBJRate == 2.5)left = BJ + left;
          Coin += LastBet *LBJRate;
        }else if(LastMyPoint.equals("21")&& JudgePoint.equals("21")&& LastMyQuantity > EnemyCardList.size()){
          left = "0";
        }else if(Integer.parseInt(LastMyPoint)>Integer.parseInt(JudgePoint)){
          left = (LastBet *LBJRate) + "";
          if(LBJRate == 2.5)left = BJ + left;
          Coin += LastBet *LBJRate;
        }else if(Integer.parseInt(LastMyPoint)<Integer.parseInt(JudgePoint)){
          left = "0";
        }else{
          left = "0";
          Coin += LastBet;
        }
      }
      if(MyPoint.equals(BURST)){
        right = "0";
      }else{
        if(MyPoint.equals("21")&& JudgePoint.equals("21")&& MyQuantity < EnemyCardList.size()){
          right = (Bet *RBJRate) + "";
          if(RBJRate == 2.5)right = BJ + right;
          Coin += Bet *RBJRate;
        }else if(MyPoint.equals("21")&& JudgePoint.equals("21")&& MyQuantity > EnemyCardList.size()){
          right = "0";
        }else if(Integer.parseInt(MyPoint)>Integer.parseInt(JudgePoint)){
          right = (Bet *RBJRate) + "";
          if(RBJRate == 2.5)right = BJ + right;
          Coin += Bet *RBJRate;
        }else if(Integer.parseInt(MyPoint)<Integer.parseInt(JudgePoint)){
          right = "0";
        }else{
          right = "0";
          Coin += Bet;
        }
      }
      JOptionPane.showMessageDialog(f,String.format("%s%s%s%s%s", StringArrayList.get(16),left, StringArrayList.get(17),right, StringArrayList.get(14)),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
    }else{
      double BJRate = 2;
      int BJIdx = 0;
      if(MyPoint.equals("21")&& MyQuantity ==2){
        BJRate = 2.5;
        BJIdx = 6;
      }
      if(MyPoint.equals(BURST)){
        JOptionPane.showMessageDialog(f, StringArrayList.get(15),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
      }else if(JudgePoint.equals(BURST)){
        JOptionPane.showMessageDialog(f,String.format("%s%d%s", StringArrayList.get(13+BJIdx),(int)(Bet *BJRate), StringArrayList.get(14)),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
        Coin += Bet *BJRate;
      }else if(MyPoint.equals("21")&& JudgePoint.equals("21")&& MyQuantity < EnemyCardList.size()){
        JOptionPane.showMessageDialog(f,String.format("%s%d%s", StringArrayList.get(13+BJIdx),(int)(Bet *BJRate), StringArrayList.get(14)),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
        Coin += Bet *BJRate;
      }else if(MyPoint.equals("21")&& JudgePoint.equals("21")&& MyQuantity > EnemyCardList.size()){
        JOptionPane.showMessageDialog(f, StringArrayList.get(15),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
      }else if(Integer.parseInt(MyPoint)>Integer.parseInt(JudgePoint)){
        JOptionPane.showMessageDialog(f,String.format("%s%d%s", StringArrayList.get(13+BJIdx),(int)(Bet *BJRate), StringArrayList.get(14)),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
        Coin += Bet *BJRate;
      }else if(Integer.parseInt(MyPoint)<Integer.parseInt(JudgePoint)){
        JOptionPane.showMessageDialog(f, StringArrayList.get(15),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
      }else{
        JOptionPane.showMessageDialog(f, StringArrayList.get(18),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
        Coin += Bet;
      }
    }
    CoinLabel.setText(Coin +"");
  }

  void clear(){
    f.remove(UpperPanel);
    f.remove(UnderPanel);
  }

  private void encrypt(){
    if(JOptionPane.showConfirmDialog(f, StringArrayList.get(21),"BlackJack",JOptionPane.YES_NO_OPTION) == 0){
      String str = JOptionPane.showInputDialog(f, StringArrayList.get(22),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
      if(str != null) {
        try{
          Coin = Integer.parseInt(Decrypt.decrypt(str));
          CoinLabel.setText(Coin + "");
          JOptionPane.showMessageDialog(f, StringArrayList.get(26),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e){
          e.printStackTrace();
          JOptionPane.showMessageDialog(f, StringArrayList.get(23),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
        }
      }else{
        JOptionPane.showMessageDialog(f, StringArrayList.get(27),"BlackJack",JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }
}
