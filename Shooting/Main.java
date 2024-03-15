import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.imageio.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
class Main extends JFrame{
  BufferStrategy strategy;
  int cou = 0;
  public static void main(String args[]){
    Main f = new Main();
    f.start();
  }

  void start(){
    setTitle("Main");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(800,600);
    setLocationRelativeTo(null);
    setVisible(true);
    setResizable(false);
    setIgnoreRepaint(true);
    createBufferStrategy(4);
    strategy = getBufferStrategy();
    loop();
  }

  void loop(){
    for(;;){
      Title ti = new Title(strategy,this);
      int a = ti.titlestart();
      Game ga = new Game(strategy,this,a%100,a/100);
      a = ga.game(cou);
      cou++;
    }
  }
}

@SuppressWarnings("serial")
class Title extends JFrame{
  BufferStrategy strategy;
  PhotoAnimation[] photo = new PhotoAnimation[7];
  PhotoAnimation z;
  JFrame f;
  Clip oto;
  AudioInputStream audio;
  int flag = 0;
  int[] kflag = new int[4];
  int kflag2 = 0;
  int jou = 0;
  int xx = 0;
  int yy = 0;
  int aiueo = 0;

  Title(BufferStrategy strategy,JFrame f){
    for(int i = 0; i < 6; i++){
      photo[i] = new PhotoAnimation();
      photo[i].setphoto("file/brack.png");
    }
    this.strategy = strategy;
    this.f = f;
    z = new PhotoAnimation();
    z.setphoto("file/s_z.png");
    z.xyset(400,550);
    try{
      audio = AudioSystem.getAudioInputStream(this.getClass().getResource("file/kz.wav"));
      oto = AudioSystem.getClip();
      oto.open(audio);
    }catch(IOException e){
    }catch(UnsupportedAudioFileException e){
    }catch(LineUnavailableException e){
    }
  }

  int titlestart(){
    Timer t1 = new Timer();
    Timer t2 = new Timer();
    for(int i = 0; i < 6; i++){
      photo[i].xyset(810,150);
    }
    t1.schedule(new Task1(),0);
    t2.schedule(new Task2(),0,90);
    f.addKeyListener(new KeyAdapter(){
      @Override
      public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
          case KeyEvent.VK_Z:
          flag = 1;
          t1.cancel();
          t2.cancel();
          break;

          case KeyEvent.VK_UP:
          if(kflag[0] == 0){
            kflag[0]++;
            kflag2++;
          }
          if(kflag2 == 4){
            oto.setFramePosition(0);
            oto.start();
          }
          break;

          case KeyEvent.VK_LEFT:
          if(kflag[1] == 0){
            kflag[1]++;
            kflag2++;
          }
          if(kflag2 == 4){
            oto.setFramePosition(0);
            oto.start();
          }
          break;

          case KeyEvent.VK_DOWN:
          if(kflag[2] == 0){
            kflag[2]++;
            kflag2++;
          }
          if(kflag2 == 4){
            oto.setFramePosition(0);
            oto.start();
          }
          break;

          case KeyEvent.VK_RIGHT:
          if(kflag[3] == 0){
            kflag[3]++;
            kflag2++;
          }
          if(kflag2 == 4){
            oto.setFramePosition(0);
            oto.start();
          }
          break;
        }
        int shift = e.getModifiersEx();
        if((shift&InputEvent.SHIFT_DOWN_MASK)!=0){
          aiueo = 1;
        }else{
          aiueo = 0;
        }
      }

      public void keyReleased(KeyEvent e){
        int shift = e.getModifiersEx();
        if((shift&InputEvent.SHIFT_DOWN_MASK)!=0){
          aiueo = 1;
        }else{
          aiueo = 0;
        }
      }
    });
    while(flag == 0){
      System.out.print("");
    }
    f.removeKeyListener(f.getKeyListeners()[0]);
    return kflag2+aiueo*100;
  }

  class Task1 extends TimerTask{
    public void run(){
      for(;flag == 0;){
        Graphics2D g = (Graphics2D)Title.this.strategy.getDrawGraphics();
        g.setBackground(Color.BLACK);
        g.clearRect(0,0,f.getWidth(),f.getHeight());
        for(int i = 0; i < 6; i++){
          photo[i].showphoto2(g);
        }
        z.showphoto2(g);
        g.dispose();
        Title.this.strategy.show();
      }
    }
  }

  class Task2 extends TimerTask{
    public void run(){
      if(jou == 0){
        for(int i = 0; i < 6; i++){
          int tmp = (i+1)*xx;
          photo[i].xyset(photo[i].x-tmp,150);
        }
        xx++;
        if(xx == 16){
          jou = 1;
        }
      }else if(jou == 1){
        for(int i = 0; i < 6; i++){
          photo[i].xyset(photo[i].x,150-yy);
        }
        yy = yy - 15;
        if(yy <= -200){
          jou = 2;
        }
      }
    }
  }
}

@SuppressWarnings("serial")
class Game extends JFrame{
  Random ra = new Random();
  BufferStrategy strategy;
  JFrame f;
  Ship ship;
  Force force;
  ArrayList<Ship> tamalist = new ArrayList<Ship>();
  ArrayList<Enemy> enemylist = new ArrayList<Enemy>();
  ArrayList<Effect1> effect1 = new ArrayList<Effect1>();
  ArrayList<Number> number = new ArrayList<Number>();
  ArrayList<Star> star = new ArrayList<Star>();
  ArrayList<Integer> star2 = new ArrayList<Integer>();
  int[] key = new int[8];
  Clip[] oto = new Clip[9];
  AudioInputStream[] audio = new AudioInputStream[9];
  int time = 0;
  int flag = 0;
  int pflag = 0;
  int oflag = 0;
  int fflag = 0;
  int count = 0;
  int pohoz = 0;
  int speed = 13;
  int tamacou = 0;
  int level = 1;
  int score = 0;
  int shiphou = 0;
  int endtime = 0;
  int kflag = 0;
  int khoz = 1;
  PhotoAnimation gameover;
  Font font;
  Effect1 effect11;
  Effect2 effect21;
  Effect2 effect22;
  Effect3 effect3;
  Timer t1;
  Timer t2;
  Timer t3;
  Timer t4;
  Timer t5;
  Timer t12;

  Game(BufferStrategy strategy,JFrame f,int aaaaa,int bbbbb){
    kflag = aaaaa==4?1:0;
    fflag = bbbbb;
    this.strategy = strategy;
    this.f = f;
    ship = new Ship();
    force = new Force(kflag);
    if(bbbbb == 1){
      force.njou(-1);
      force.xyset(10000,10000);
    }
    for(int i = 0; i < 7; i++){
      number.add(new Number());
    }
    try{
      for(int i = 0; i <= 7+kflag; i++){
        audio[i] = AudioSystem.getAudioInputStream(this.getClass().getResource("file/" + (kflag == 1?"k_":"w_") +i+".wav"));
        oto[i] = AudioSystem.getClip();
        oto[i].open(audio[i]);
      }
    }catch(IOException e){
    }catch(UnsupportedAudioFileException e){
    }catch(LineUnavailableException e){
    }
    gameover = new PhotoAnimation();
    gameover.setphoto("file/50.png");
    gameover.xyset(400,300);
    font = new Font(Font.SERIF,Font.PLAIN,80);
    effect11 = new Effect1();
    effect21 = new Effect2(0);
    effect22 = new Effect2(1);
    effect3 = new Effect3();
  }

  int game(int cou){
    t1 = new Timer();
    t2 = new Timer();
    t3 = new Timer();
    t4 = new Timer();
    t5 = new Timer();
    t12 = new Timer();
    if(kflag == 0){
      for(int i = 0; i < 5; i++){
        ship.setphoto("file/s_"+i+".png");
      }
    }else{
      ship.setphoto("file/gazou.png");
    }
    ship.xyset(300,300);
    if(kflag == 0){
      for(int i = 5; i < 9; i++){
        force.setphoto("file/s_"+i+".png");
      }
    }else{
      force.setphoto("file/gazou2.png");
    }
    f.addKeyListener(new KeyAdapter(){
      @Override
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){
          key[0] = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
          key[1] = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
          key[2] = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
          key[3] = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_Z){
          key[4] = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_X){
          key[5] = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
          pose();
        }
        int shift = e.getModifiersEx();
        if((shift&InputEvent.SHIFT_DOWN_MASK)!=0){
          key[6] = 1;
        }else{
          key[6] = 0;
        }
      }

      public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){
          key[0] = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
          key[1] = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
          key[2] = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
          key[3] = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_Z){
          key[4] = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_X){
          key[5] = 0;
        }
        int shift = e.getModifiersEx();
        if((shift&InputEvent.SHIFT_DOWN_MASK)!=0){
          key[6] = 1;
        }else{
          key[6] = 0;
        }
      }
    });
    t1.schedule(new Task1(),0);
    t2.scheduleAtFixedRate(new Task2(),0,30);
    t3.scheduleAtFixedRate(new Task3(),0,1200);
    t4.scheduleAtFixedRate(new Task4(),0,1);
    if(kflag == 0)t5.schedule(new Task5(),0);
    t12.scheduleAtFixedRate(new Task12(),0,1);
    if(kflag == 0){
      audioplay(7);
    }else{
      audioloop(8);
    }
    for(;flag == 0;){
      //System.out.printf("%1d,%1d,%1d,%1d,%1d,%1d,%1d\n",key[0],key[1],key[2],key[3],key[4],key[5],key[6]);
      System.out.print("");
    }
    t2.cancel();
    while(endtime <= 8 && kflag == 0 || kflag == 1){
      System.out.print("");
      if(endtime >= 3 && key[4] == 1){
        audiostop(5);
        break;
      }
    }
    t1.cancel();
    t3.cancel();
    t4.cancel();
    t12.cancel();
    flag++;
    if(kflag == 1){
      audiostop(7);
    }
    f.removeKeyListener(f.getKeyListeners()[0]);
    return 0;
  }

  void pose(){
    pflag = 1 - pflag;
    if(pflag == 1 && flag == 0){
      t2.cancel();
      t3.cancel();
      t4.cancel();
      t12.cancel();
      t2 = null;
      t3 = null;
      t4 = null;
      t12 = null;
      if(kflag == 0){
        oto[6].stop();
        oto[7].stop();
      }
    }else if(flag == 0){
      t2 = new Timer();
      t3 = new Timer();
      t4 = new Timer();
      t12 = new Timer();
      t2.scheduleAtFixedRate(new Task2(),0,30);
      t3.scheduleAtFixedRate(new Task3(),0,1200);
      t4.scheduleAtFixedRate(new Task4(),0,1);
      t12.scheduleAtFixedRate(new Task12(),0,1);
      if(kflag == 0){
        if(oflag == 0){
          oto[7].start();
        }else{
          oto[6].start();
        }
      }
    }
  }

  class Task4 extends TimerTask{
    public void run(){
      force.fmove(ship.x,ship.y);
      if(kflag == 0)force.sum();
      for(int i = 0; i < enemylist.size(); i++){
        try{
          if(kflag == 0){
            enemylist.get(i).timetime();
          }
        }catch(Exception e){
          continue;
        }
      }
      for(int i = 0; i < effect1.size(); i++){
        try{
          effect1.get(i).timetime();
          if(effect1.get(i).soeji%6 == 0 && effect1.get(i).soeji != 0){
            effect1.remove(i);
            i=Math.max(0,i-1);
            if(effect1.size() == 0){
              break;
            }
          }
        }catch(Exception e){
          continue;
        }
      }
      //System.out.print(4);
    }
  }

  class Task1 extends TimerTask{
    public void run(){
      for(;flag <= 1;){
        Graphics2D g = (Graphics2D)Game.this.strategy.getDrawGraphics();
        g.setBackground(Color.BLACK);
        g.clearRect(0,0,f.getWidth(),f.getHeight());
        g.setColor(Color.WHITE);
        for(int i = 0; i < star.size(); i++){
          try{
            g.drawRect(star.get(i).getx(),star.get(i).gety(),1,1);
          }catch(Exception e){
            continue;
          }
        }
        scoreprint(g);
        if(kflag == 1)force.rotatephoto2(g,1);
        force.showphoto2(g);
        if(kflag == 1)force.rotatereset(g);
        for(int i = 0; i < enemylist.size(); i++){
          try{
            if(flag == 1 && kflag == 1)enemylist.get(i).rotatephoto2(g,5);
            if(flag == 1 && kflag == 1){
              enemylist.get(i).movephoto(g,ra.nextInt(2)==0?4:-4,ra.nextInt(2)==0?4:-4);
              force.rotatereset(g);
            }else{
              enemylist.get(i).showphoto2(g);
            }
          }catch(Exception e){
            continue;
          }
        }
        for(int i = 0; i < effect1.size(); i++){
          try{
            effect1.get(i).showphoto2(g);
          }catch(Exception e){
            continue;
          }
        }
        if(kflag == 0){
          if(shiphou == 0){
            ship.changephoto(g,0);
          }else if(shiphou > 10){
            ship.changephoto(g,3);
          }else if(shiphou > 0){
            ship.changephoto(g,1);
          }else if(shiphou < -10){
            ship.changephoto(g,4);
          }else if(shiphou < 0){
            ship.changephoto(g,2);
          }
        }
        ship.showphoto2(g);
        for(int i = 0; i < tamalist.size(); i++){
          try{
            //System.out.print(1);
            tamalist.get(i).right(5);
            if(tamalist.get(i).x >= 1000){
              tamalist.remove(i);
              if(i!=0)i--;
              if(tamalist.size() == 0){
                break;
              }
            }else{
              if(kflag == 1)tamalist.get(i).rotatephoto2(g,10);
              tamalist.get(i).showphoto2(g);
            }
          }catch(Exception e){
            continue;
          }
        }
        //System.out.print(force.jo());
        if(flag == 1 && endtime >= 3 && kflag == 0){
          gameover.showphoto2(g);
        }
        force.rotatereset(g);
        if(kflag == 1 && flag == 1){
          g.setFont(font);
          g.setColor(Color.RED);
          g.drawString("Ç¥ÇÒÇÀÇÒÅI",130,300);
        }
        g.dispose();
        Game.this.strategy.show();
      }
    }
  }

  void die(){
    flag = 1;
    effect3.xyset(ship.x,ship.y);
    effect1.add(effect3);
    force.njou(6);
    ship.xyset(10000,10000);
    audiostop(kflag == 0?7:8);
    audiostop(6);
    if(kflag == 1)audioplay(7);
    audioplay(kflag == 0?4:6);
  }

  class Task12 extends TimerTask{
    public void run(){
      System.out.print("");
      for(int i = 0; i < enemylist.size(); i++){
        try{
          if(enemylist.get(i).x >= 1000 || enemylist.get(i).x <= -100){
            enemylist.remove(i);
            if(i!=0)i--;
            if(enemylist.size() == 0){
              break;
            }
          }
        }catch(Exception e){
          continue;
        }
      }
      if(enemylist.size() != 0){
        for(int i = 0; i < enemylist.size(); i++){
          for(int j = 0; j < tamalist.size(); j++){
            try{
              if(i==-1)i=0;
              if(j==-1)j=0;
              int ex = -100;
              int ey = -100;
              int tx = -100;
              int ty = -100;
              ex = enemylist.get(i).x;
              ey = enemylist.get(i).y;
              tx = tamalist.get(j).x;
              ty = tamalist.get(j).y;
              if(tx >= ex-50 && ex+50 >= tx && ty >= ey-30 && ey+30 >= ty){
                if(kflag == 0){
                  audiostop(1);
                  audioplay(1);
                }else{
                  audiostop(khoz);
                  khoz = ra.nextInt(3)+1;
                  audioplay(khoz);
                }
                score+=level*100;
                effectadd1(enemylist.get(i).x,enemylist.get(i).y);
                enemylist.remove(i);
                tamalist.remove(j);
                i--;
                j--;
                //System.out.print(2);
                if(enemylist.size() == 0 || tamalist.size() == 0){
                  break;
                }
              }
              //System.out.print(2);
            }catch(Exception e){
              continue;
            }
          }
        }
        for(int i = 0; i < enemylist.size() ; i++){
          int ex = -1;
          int ey = -1;
          int tx = -1;
          int ty = -1;
          try{
            ex = enemylist.get(i).x;
            ey = enemylist.get(i).y;
            tx = force.x;
            ty = force.y;
          }catch(Exception e){
            continue;
          }
          if(tx >= ex-50 && ex+50 >= tx && ty >= ey-50 && ey+50 >= ty){
            if(kflag == 0){
              audiostop(1);
              audioplay(1);
            }else{
              audiostop(khoz);
              khoz = ra.nextInt(3)+1;
              audioplay(khoz);
            }
            score+=level*100;
            effectadd1(enemylist.get(i).x,enemylist.get(i).y);
            enemylist.remove(i);
            if(i!=0)i--;
            //System.out.print(2);
            if(enemylist.size() == 0){
              break;
            }
          }
        }
      }
      if(enemylist.size() != 0){
        for(int i = 0; i < enemylist.size(); i++){
          try{
            int ex = enemylist.get(i).x;
            int ey = enemylist.get(i).y;
            int sx = ship.x;
            int sy = ship.y;
            if(ex-20<=sx&&sx<=ex+20&&ey-20<=sy&&sy<=ey+20){
              Game.this.die();
            }
          }catch(Exception e){
            continue;
          }
        }
      }
    }
  }

  class Task2 extends TimerTask{
    public void run(){
      Game.this.shiprun();
      Game.this.tamatama();
      Game.this.enemymove();
      Game.this.forcemove();
      Game.this.addstar();
      Game.this.movestar();
      //System.out.print(2);
    }
  }

  void addstar(){
    star.add(new Star(850,ra.nextInt(600)));
    star2.add(ra.nextInt(5)*3+7);
  }

  void movestar(){
    for(int i = 0; i < star.size(); i++){
      try{
        star.get(i).setx2(star2.get(i)*-1);
        if(star.get(i).getx() < -10){
          star.remove(i);
          star2.remove(i);
          i = Math.max(0,i-1);
          if(star.size()==0){
            break;
          }
        }
      }catch(Exception e){
        continue;
      }
    }
  }

  void shiprun(){
    if(key[0] == 1){
      if(ship.y >= 40)ship.up(speed);
      if(shiphou > 0){
        shiphou++;
      }else{
        shiphou = 1;
      }
    }
    if(key[1] == 1 && ship.x >= 0){
      ship.left(speed);
    }
    if(key[2] == 1){
      if(ship.y <= 580)ship.down(speed);
      if(shiphou < 0){
        shiphou--;
      }else{
        shiphou = -1;
      }
    }
    if(key[3] == 1 && ship.x <= 800){
      ship.right(speed);
    }
    if(key[0] == 0 && key[2] == 0){
      shiphou = 0;
    }
  }

  void tamatama(){
    if(key[4] == 1){
      if(tamacou % 5 == 0){
        Ship tama = new Ship();
        if(kflag == 0){
          tama.setphoto("file/s_9.png");
        }else{
          tama.setphoto("file/s_gazou4.png");
        }
        tama.xyset(ship.x+50,ship.y);
        tamalist.add(tama);
        tamacou = 1;
        audiostop(0);
        audioplay(0);
      }else{
        tamacou++;
      }
    }else{
      tamacou = 0;
    }
  }

  void forcemove(){
    if(key[6] == 1){
      if(force.jo() == 0){
        audioplay(3);
        effect21.xyset(force.x-10,force.y);
        effect21.soeji=0;
        effect1.add(effect21);
        force.njou(2);
      }else if(force.jo() == 1){
        audioplay(3);
        effect22.xyset(force.x+10,force.y);
        effect22.soeji=0;
        effect1.add(effect22);
        force.njou(3);
      }else if(force.jo() == 4){
        force.njou(5);
      }
    }
  }

  class Task3 extends TimerTask{
    Random ra = new Random();
    public void run(){
      System.out.print("");
      time++;
      if(flag == 1){
        endtime++;
      }
      if(endtime == 3 && kflag == 0){
        audioplay(5);
      }
      if(time == (kflag == 0?30:15)){
        time = 0;
        level = Math.min(10,level+1);
      }
      for(int i = 0; i < level; i++){
        Game.this.enemy();
      }
      //System.out.print(3);
    }
  }

  class Task5 extends TimerTask{
    public void run(){
      while(oflag == 0){
        if (oto[7].getFramePosition() >= oto[7].getFrameLength()-4000) {
          oflag = 1;
          audioloop(6);
        }
      }
    }
  }

  void enemy(){
    int raa = level <= 2?0:ra.nextInt(2);
    Enemy e = new Enemy(raa,kflag);
    e.xyset(raa == 0?850:-50,ra.nextInt(400)+100);
    enemylist.add(e);
  }

  void enemymove(){
    for(int i = 0; i < enemylist.size(); i++){
      enemylist.get(i).move2();
    }
  }

  void effectadd1(int xx,int yy){
    Effect1 e = effect11.clone();
    e.xyset(xx,yy);
    effect1.add(e);
  }

  void scoreprint(Graphics2D g){
    //System.out.println();
    //System.out.print(score);
    for(int i = 0; i < 7; i++){
      number.get(i).xyset(600+i*25,570);
      number.get(i).changephoto(g,(int)(score/Math.pow(10,6-i)%10));
      //System.out.print(" "+(int)Math.max(0,(score/Math.pow(10,6-i)%10)));
      number.get(i).showphoto2(g);
    }
  }

  void audioplay(int soeji){
    oto[soeji].setFramePosition(0);
    oto[soeji].start();
  }

  void audiostop(int soeji){
    oto[soeji].stop();
    oto[soeji].flush();
    oto[soeji].setFramePosition(0);
  }

  void audioloop(int soeji){
    oto[soeji].setFramePosition(0);
    oto[soeji].loop(Clip.LOOP_CONTINUOUSLY);
  }
}
