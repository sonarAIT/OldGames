import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Poker extends JFrame{
  //�t�B�[���h
  int i,j,scene=0,mbet,ebet,pod,jisin,chip = 1100;
  int cardi[] = {0,0,0,0,0,0,0,0,0,0};
  int cardn[] = {0,0,0,0,0,0,0,0,0,0};
  int cardm[] = {0,0,0,0,0,0,0,0,0,0};
  String cardmark[] = {"c","h","s","d"};//�N���u�A�n�[�g�A�X�y�[�h�A�_�C���̏���
  int cardflag[] = new int[53];
  int cardflag2[] = {0,0,0,0,0};
  JButton b[] = new JButton[20];
  JLabel l[] = new JLabel[5];
  JPanel p[] = new JPanel[2];
  Random r = new Random();
  InputDialog id1;
  InputDialog id2;
  //�R���X�g���N�^
  Poker(){
    for(i = 0; i < 20; i++){
      b[i] = new JButton();
    }
    for(i = 0; i < 5; i++){
      l[i] = new JLabel();
    }
    for(i = 0; i < 2; i++){
      p[i] = new JPanel();
    }
    for(i = 0; i < 53; i++){
      cardflag[i] = 0;
    }
  id1 = new InputDialog(this,"������q���܂����H","�x�b�g",3);
  id2 = new InputDialog(this,"������q���܂����H","���C�Y",3);
  b[16].addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
    }
  });
  getRootPane().setDefaultButton(b[16]);
  }

  //���C�����\�b�h����t���[���𐶐�
  public static void main(String args[]){
    Poker f = new Poker();
    f.setTitle("�|�[�J�[");
    f.setSize(1015,835);
    f.setLocationRelativeTo(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
    f.set();
  }

  //��ʍ쐬
  void set(){
    setLayout(null);
    Color green = new Color(0,102,0);
    Border border1 = new EmptyBorder(5,5,5,5);
    Border border2 = new BevelBorder(BevelBorder.RAISED);
    Border border3 = new LineBorder(Color.RED,8,false);
    Font font1 = new Font(Font.SERIF,Font.BOLD,20);
    String bname[] = {"�x�b�g","�`�F�b�N","���C�Y","�R�[��","�t�H�[���h",
                      "�x�b�g:"+ebet,"�|�b�h:"+pod,
                      "�x�b�g:"+mbet,""+chip};
    p[0].setBounds(0,0,850,800);
    p[0].setBackground(green);
    add(p[0]);
    p[1].setBounds(850,0,150,800);
    p[1].setBackground(Color.DARK_GRAY);
    add(p[1]);
    p[0].setLayout(null);
    p[1].setLayout(null);
    for(i = 0; i < 2; i++){
      for(j = 0; j < 5; j++){
        b[j+i*5].setBounds(45+j*160,50+i*500,120,160);
        b[j+i*5].setBackground(green);
        b[j+i*5].setBorder(border1);
        p[0].add(b[j+i*5]);
      }
    }
    for(i = 0; i < 5; i++){
      b[i+11].setText(bname[i]);
      b[i+11].setBounds(10,160+i*100,130,80);
      b[i+11].setFont(font1);
      b[i+11].setBorder(border2);
      b[i+11].setEnabled(false);
      p[1].add(b[i+11]);
    }
    b[16].setText("����");
    b[16].setBounds(10,680,130,80);
    b[16].setFont(font1);
    b[16].setBorder(border2);
    b[16].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        actionreset(16);
        scene0();
      }
    });
    p[1].add(b[16]);
    for(i = 0; i < 3; i++){
      l[i].setText(bname[i+5]);
      l[i].setBounds(325,220+i*120,200,40);
      l[i].setBackground(Color.WHITE);
      l[i].setOpaque(true);
      l[i].setFont(font1);
      l[i].setBorder(border3);
      l[i].setHorizontalAlignment(JLabel.CENTER);
      l[i].setVerticalAlignment(JLabel.CENTER);
      p[0].add(l[i]);
    }
    for(i = 3; i < 5; i++){
      l[i].setOpaque(true);
      l[i].setHorizontalAlignment(JLabel.CENTER);
      l[i].setVerticalAlignment(JLabel.CENTER);
    }
    l[3].setText("����L�[�������Ă��������B");
    l[3].setBounds(0,750,850,50);
    l[3].setBackground(Color.LIGHT_GRAY);
    l[3].setFont(new Font(Font.SERIF,Font.BOLD,25));
    p[0].add(l[3]);
    l[4].setText(bname[8]);
    l[4].setBounds(10,55,130,50);
    l[4].setBackground(Color.WHITE);
    l[4].setFont(new Font(Font.SERIF,Font.BOLD,20));
    l[4].setBorder(new LineBorder(Color.RED,8,true));
    p[1].add(l[4]);
    repaint();
  }

  //�ŏ��̃R�}���h�O�̗���
  void scene0(){
    scene = 0;
    l[3].setText("�A���e�B���x�����܂��B");
    ante();
    repaint2();
    b[16].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        l[3].setText("�J�[�h��z��܂��B");
        card();
        actionreset(16);
        b[16].addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent event){
            scene1();
          }
        });
      }
    });
  }

  //�x�b�g�A�`�F�b�N�A�t�H�[���h����I������ŏ��̏��
  void scene1(){
    l[3].setText("�R�}���h�H");
  System.out.print("��D:");
  for(i = 0; i < 10; i++){
    System.out.print(cardn[i]+",");
  }
  System.out.println();
    b[11].setEnabled(true);
    b[11].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        bet();
      }
    });
    b[12].setEnabled(true);
    b[12].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        actionreset(11);
        actionreset(12);
        actionreset(15);
        check();
      }
    });
    b[15].setEnabled(true);
    b[15].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
      int flag = JOptionPane.showConfirmDialog(null,"�{���ɂ�낵���ł���?","�t�H�[���h",0,3);
      if(flag == 0){
      actionreset(11);
      actionreset(12);
      actionreset(15);
      l[3].setText("�t�H�[���h���܂����B");
      for(i = 11; i <= 12; i++){
        b[i].setEnabled(false);
      }
      b[15].setEnabled(false);
      b[16].setEnabled(true);
      actionreset(16);
      b[16].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          end(1);
        }
      });
      } else {
      }
      }
    });
    b[16].setEnabled(false);
  }

  //���C�Y�A�R�[���A�t�H�[���h����I��������
  void scene2(){
    if(scene == 0){
      scene = 1;
  }
    l[3].setText("�R�}���h�H");
    b[13].setEnabled(true);
    b[13].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        rays();
      }
    });
    b[14].setEnabled(true);
    b[14].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        actionreset(13);
        actionreset(14);
        actionreset(15);
        call();
      }
    });
    b[15].setEnabled(true);
    b[15].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        int flag = JOptionPane.showConfirmDialog(null,"�{���ɂ�낵���ł���?","�t�H�[���h",0,3);
        if(flag == 0){
          actionreset(13);
          actionreset(14);
          actionreset(15);
          l[3].setText("�t�H�[���h���܂����B");
          for(i = 13; i <= 14; i++){
            b[i].setEnabled(false);
          }
          b[15].setEnabled(false);
          b[16].setEnabled(true);
          actionreset(16);
          b[16].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
              end(1);
            }
          });
        } else {
        }
      }
    });
    b[16].setEnabled(false);
  }

  //�J�[�h������������
  void scene3(){
    l[3].setText("��������J�[�h��I��ł��������B");
    for(i = 11; i < 16; i++){
      b[i].setEnabled(false);
    }
    b[5].setEnabled(true);
      b[5].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          if(cardflag2[0] == 0){
            b[5].setBounds(45,520,120,160);
            cardflag2[0] = 1;
          } else {
            b[5].setBounds(45,550,120,160);
            cardflag2[0] = 0;
          }
      }
    });
    b[6].setEnabled(true);
      b[6].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          if(cardflag2[1] == 0){
            b[6].setBounds(205,520,120,160);
            cardflag2[1] = 1;
          } else {
            b[6].setBounds(205,550,120,160);
            cardflag2[1] = 0;
          }
      }
    });
    b[7].setEnabled(true);
      b[7].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          if(cardflag2[2] == 0){
            b[7].setBounds(365,520,120,160);
            cardflag2[2] = 1;
          } else {
            b[7].setBounds(365,550,120,160);
            cardflag2[2] = 0;
          }
      }
    });
    b[8].setEnabled(true);
      b[8].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          if(cardflag2[3] == 0){
            b[8].setBounds(525,520,120,160);
            cardflag2[3] = 1;
          } else {
            b[8].setBounds(525,550,120,160);
            cardflag2[3] = 0;
          }
      }
    });
    b[9].setEnabled(true);
      b[9].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          if(cardflag2[4] == 0){
            b[9].setBounds(685,520,120,160);
            cardflag2[4] = 1;
          } else {
            b[9].setBounds(685,550,120,160);
            cardflag2[4] = 0;
          }
      }
    });
    actionreset(16);
    b[16].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        int flag = JOptionPane.showConfirmDialog(null,"����ł����ł����H","�m�F",0,3);
        if(flag == 0){
          swap();
        } else if(flag == 1){
        }
      }
    });
  }

  //�Ō�̌��ʔ��\�̏��
  void scene4(){
  String handn[] = {"�n�C�J�[�h","�����E�y�A","�c�E�E�y�A","�X���[�J�[�h","�X�g���[�g","�t���b�V��","�t���n�E�X","�t�H�A�E�J�[�h","�X�g���[�g�E�t���b�V��","���C�����X�g���[�g�t���b�V��"};
  String winner[] = {"���Ȃ��̏����ł��B","����̏����ł��B","���������ł��B"};
  int mine,enemy,hnum,hmark,flag = 0;
  //carddebug();
  for(i = 0; i < 2; i++){
    for(j = 0; j < 4; j++){
      for(int l = j + 1; l < 5; l++){
        if(cardn[j+i*5] < cardn[l+i*5]){
          hnum = cardn[j+i*5];
          hmark = cardm[j+i*5];
          cardn[j+i*5] = cardn[l+i*5];
          cardm[j+i*5] = cardm[l+i*5];
          cardn[l+i*5] = hnum;
          cardm[l+i*5] = hmark;
        }
      }
    }
  }
  image(0);
  image(1);
  mine = hand(0);
  enemy = hand(1);
  System.out.print("�|�C���g:"+mine+","+enemy);
  System.out.println();
  if(mine > enemy){
    flag = 0;
  } else if(mine < enemy){
    flag = 1;
  } else if(mine == enemy){
    flag = 2;
  }
  mine = mine/100;
  enemy = enemy/100;
  l[3].setText(handn[mine]+"��"+handn[enemy]+"��"+winner[flag]);
  final int eflag = flag;
  actionreset(16);
  b[16].addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent event){
      end(eflag);
    }
  });
  }

  //����Ɏl�̑I��������I�΂��鏈��
  void enemy1(){
    int rand;
    int ji = 1,bet;
  if(scene == 2){
    ji = jisin;
  }
  rand = ((r.nextInt(10)+1)+ji);
    if(mbet == 0 || mbet == ebet){
      bet = chip/10;
      ebet = ebet + bet;
      pod = pod + bet;
      l[3].setText("����̓x�b�g���܂����B");
    } else if(rand >= 6){
      bet = mbet+mbet/10*rand;
    if(bet > chip){
      bet = mbet + 10;
    }
      ebet = ebet + bet;
      pod = pod + bet;
      l[3].setText("����̓��C�Y���܂����B");
    } else if(rand <= 5 && rand != 1){
      bet = mbet;
      ebet = mbet;
      pod = pod + bet;
      l[3].setText("����̓R�[�����܂����B");
    } else if(rand == 1){
    l[3].setText("����̓t�H�[���h���܂����B");
    scene = 4;
    }
    repaint2();
    actionreset(16);
    b[16].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
      if(mbet == ebet){
        if(scene == 0){
          scene3();
        } else if(scene == 2){
          scene4();
        }
      } else if (scene == 4){
        end(0);
      } else if(mbet != ebet){
          scene2();
      }
      }
    });
  }

  //�������Ȃ��悤�ɃR�[����������
  void enemy2(){
    l[3].setText("����̓R�[�����܂����B");
    int bet = mbet - ebet;
    ebet = mbet;
    pod = pod + bet;
    repaint2();
    actionreset(16);
    b[16].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
    if(scene == 1){
      scene3();
    } else if(scene == 2){
      scene4();
    }
      }
    });
  }

  //���肪���������ʁi�������y�A�łȂ��J�[�h����������B�m�[�y�A�Ȃ�S�������B�j
  void enemy3(){
    for(i = 0; i < 5; i++){
      cardflag2[i] = 0;
    }
    int flag = 0;
  cardcount();
    for(i = 0; i < 5; i++){
      if(cardflag2[i] == 0){
        int x,y;
        x = (r.nextInt(13)+1);
        y = (r.nextInt(4));
        while(flag == 0){
          if(cardflag[x+y*13] == 0){
            cardi[i+5] = (x+y*13);
            cardn[i+5] = x;
            cardm[i+5] = y;
            cardflag[x+y*13] = 1;
            flag = 1;
          } else {
            x = (r.nextInt(13)+1);
            y = (r.nextInt(4));
          }
        }
        flag = 0;
      }
    }
  scene = 2;
  jisin();
  System.out.println("����̎��M:"+jisin);
  scene1();
  }

  //�J�[�h��z�鏈��
  void card(){
    int x,y,flag = 0;
    for(i = 0; i < 10; i++){
      x = (r.nextInt(13)+1);
      y = (r.nextInt(4));
      while(flag == 0){
        if(cardflag[x+y*13] == 0){
          cardi[i] = (x+y*13);
          cardn[i] = x;
          cardm[i] = y;
          cardflag[x+y*13] = 1;
          flag = 1;
        } else {
          x = (r.nextInt(13)+1);
          y = (r.nextInt(4));
        }
      }
      flag = 0;
    }
    image(0);
    ImageIcon icon1 = new ImageIcon("./�g�����v/z02.png");
    Image icon2 = icon1.getImage().getScaledInstance((int)(icon1.getIconWidth()*0.5),-1,Image.SCALE_SMOOTH);
    ImageIcon icon3 = new ImageIcon(icon2);
    for(i = 0; i < 5; i++){
      b[i].setIcon(icon3);
    }
  }

  //��D�������I�ɕύX����i�f�o�b�O�j
  void carddebug(){
  cardn[0] = 10;
  cardn[1] = 11;
  cardn[2] = 12;
  cardn[3] = 13;
  cardn[4] = 1;
  cardn[5] = 3;
  cardn[6] = 4;
  cardn[7] = 5;
  cardn[8] = 6;
  cardn[9] = 7;
  cardm[0] = 1;
  cardm[1] = 1;
  cardm[2] = 1;
  cardm[3] = 1;
  cardm[4] = 1;
  cardm[5] = 2;
  cardm[6] = 2;
  cardm[7] = 2;
  cardm[8] = 2;
  cardm[9] = 2;
  }

  //�I�������J�[�h�����������鏈��
  void swap(){
    for(i = 0; i < 5; i++){
    b[i+5].setBounds(45+i*160,550,120,160);
    actionreset(i+5);
      if(cardflag2[i] == 1){
        int x,y,flag = 0;
        x = (r.nextInt(13)+1);
        y = (r.nextInt(4));
        while(flag == 0){
          if(cardflag[x+y*13] == 0){
            cardi[i] = (x+y*13);
            cardn[i] = x;
            cardm[i] = y;
            cardflag[x+y*13] = 1;
            flag = 1;
          } else {
            x = (r.nextInt(13)+1);
            y = (r.nextInt(4));
          }
        }
        flag = 0;
      }
    }
    image(0);
    l[3].setText("�������܂���");
    repaint2();
    actionreset(16);
    b[16].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        enemy3();
      }
    });
  }

  //��������������������̎��M�𑝂₷����
  void jisin(){
    cardcount();
  for(i = 0; i < 5; i++){
    if(cardflag2[i] == 1){
      jisin++;
    }
  }
  }

  //�킴�킴���\�b�h�ɂ���܂ł��Ȃ�����
  void ante(){
    chip = chip - 100;
    pod = pod + 200;
  }

  //�x�b�g���鏈��
  void bet(){
    int bet = id1.dialog2();//��������
    if(bet < 0){
      JOptionPane.showMessageDialog(this,"0�ȉ��̃`�b�v��q���邱�Ƃ͂ł��܂���B","�G���[",2);
    } else if(bet == 0){
      JOptionPane.showMessageDialog(this,"�L�����Z�����܂����B","�L�����Z��",2);
    } else {
      actionreset(11);
      actionreset(12);
      actionreset(15);
      mbet = mbet + bet;
      pod = pod + bet;
      chip = chip - bet;
      l[3].setText("�x�b�g���܂����B");
      repaint2();
      b[11].setEnabled(false);
      b[12].setEnabled(false);
      b[15].setEnabled(false);
      b[16].setEnabled(true);
      actionreset(16);
      b[16].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          enemy1();
        }
      });
    }
  }

  //�`�F�b�N���鏈��
  void check(){
    l[3].setText("�`�F�b�N���܂����B");
    repaint2();
    b[11].setEnabled(false);
    b[12].setEnabled(false);
    b[15].setEnabled(false);
    b[16].setEnabled(true);
    actionreset(16);
    b[16].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        enemy1();
      }
    });
  }

  //���C�Y���鏈��
  void rays(){
    int bet = id2.dialog2();
    if(bet == 0){
      JOptionPane.showMessageDialog(this,"�L�����Z�����܂����B","�L�����Z��",2);
    } else if(mbet+bet < ebet){
      JOptionPane.showMessageDialog(this,"����̃x�b�g���������q���Ă��������B","�G���[",2);
    } else {
      actionreset(13);
      actionreset(14);
      actionreset(15);
      mbet = mbet + bet;
      pod = pod + bet;
      chip = chip - bet;
      l[3].setText("���C�Y���܂����B");
      repaint2();
      b[13].setEnabled(false);
      b[14].setEnabled(false);
      b[15].setEnabled(false);
      b[16].setEnabled(true);
      actionreset(16);
      b[16].addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          enemy2();
        }
      });
    }
  }

  //�R�[�����鏈��
  void call(){
    l[3].setText("�R�[�����܂���");
    int bet = ebet - mbet;
    mbet = ebet;
    chip = chip - bet;
    pod = pod + bet;
    repaint2();
    b[13].setEnabled(false);
    b[14].setEnabled(false);
    b[15].setEnabled(false);
    b[16].setEnabled(true);
    actionreset(16);
    b[16].addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
      if(scene == 2){
        scene4();
      } else if(scene == 1){
        scene3();
      }
      }
    });
  }

  //�摜��\�����鏈��
  void image(int mode){
    if(mode == 0){
    j = 0;
    } else if(mode == 1){
    j = 5;
    }
    String c;
    for(i = 0; i < 5; i++){
      if(cardn[i+j] < 10){
        c = ("0" + cardn[i+j]);
      } else {
        c = (""+cardn[i+j]);
      }
      ImageIcon icon1 = new ImageIcon("./�g�����v/"+cardmark[cardm[i]]+(""+c)+".png");//��������
      Image icon2 = icon1.getImage().getScaledInstance((int)(icon1.getIconWidth()*0.5),-1,Image.SCALE_SMOOTH);
      ImageIcon icon3 = new ImageIcon(icon2);
      if(mode == 1){
        b[i].setIcon(icon3);
      } else {
        b[i+5].setIcon(icon3);
      }
    }
  }

  //ActionListener�����������i�����������ߏ����̖ʓ|�����烁�\�b�h�ɂ����j
  void actionreset(int a){
    b[a].removeActionListener(b[a].getActionListeners()[0]);
  }

  //���x�����ĕ`�ʂ����鏈���i����Ȃ��H�j
  void repaint2(){
    String bname[] = {"�x�b�g","�`�F�b�N","���C�Y","�R�[��","�t�H�[���h",
                      "�x�b�g:"+ebet,"�|�b�h:"+pod,
                      "�x�b�g:"+mbet,""+chip};
    for(i = 0; i < 3; i++){
      l[i].setText(bname[i+5]);
    }
    l[4].setText(bname[8]);
    repaint();
  }

  //����̎�D�̃y�A�𐔂��鏈��
  void cardcount(){
    for(i = 0; i < 5; i++){
      cardflag2[i] = 0;
    }
    int flag = 0;
    for(i = 0; i < 5; i++){
      for(j = 0; j < 5; j++){
        if(i != j){
          if(cardn[i+5] == cardn[j+5]){
            flag++;
          }
        }
      }
      if(flag > 0){
        cardflag2[i] = 1;
      }
      flag = 0;
    }
  }

  //��D�̖������߂鏈��
  int hand(int mode){
  int point = 0,max = 0,nmax = 0,mmax = 0,mo = 0,pear = 0;
  int numcount[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
  int markcount[] = {0,0,0,0};
  if(mode == 0){
    mo = 0;
  } else if(mode == 1){
    mo = 5;
    }
  for(i = 0; i < 5; i++){
    numcount[cardn[i+mo]]++;
    markcount[cardm[i+mo]]++;
    if(cardn[i+mo] > max){
      max = cardn[i+mo];
    }
  }
  for(i = 1; i <= 13 ; i++){
    if(numcount[i] > nmax){
      nmax = numcount[i];
    }
  }
  for(i = 0; i < 4; i++){
    if(markcount[i] > mmax){
      mmax = markcount[i];
    }
  }
  for(i = 1; i <= 13; i++){
    if(numcount[i] == 2){
      pear++;
    }
  }
  if(mmax == 5 && cardn[0+mo] == 13 && cardn[1+mo] == 12 && cardn[2+mo] == 11 && cardn[3+mo] == 10 && cardn[4+mo] == 1){
    return 900;
  }
  if(mmax == 5 && cardn[1+mo] == (cardn[0+mo]-1) && cardn[2+mo] == (cardn[0+mo]-2) && cardn[3+mo] == (cardn[0+mo]-3) && cardn[4+mo] == (cardn[0+mo]-4)){
    point = max + 800;
    return point;
  }
  if(nmax == 4){
    max = cardn[2+mo];
    point = max + 700;
    return point;
  }
  if(nmax == 3 && pear >= 1){
    for(i = 1; i < 14; i++){
      if(numcount[i] == 3){
        max = i;
      }
    }
    point = max + 600;
    return point;
  }
  if(mmax == 5){
    point = max + 500;
    return point;
  }

  if(cardn[1+mo] == (cardn[0+mo]-1) && cardn[2+mo] == (cardn[0+mo]-2) && cardn[3+mo] == (cardn[0+mo]-3) && cardn[4+mo] == (cardn[0+mo]-4)){
    point = max + 400;
    return point;
  }

  if(nmax == 3){
    max = cardn[2+mo];
    point = max + 300;
    return point;
  }
  if(pear >= 2){
    max = cardn[1+mo];
    point = max + 200;
    return point;
  }
  if(pear >= 1){
    for(i = 1; i < 14; i++){
      if(numcount[i] == 2){
        max = i;
      }
    }
    point = max + 100;
    return point;
  }
  for(i = 0; i < 5; i++){
    if(cardn[i+mo] > max){
      max = cardn[i+mo];
    }
  }
  return max;
  }

  //���Z��������
  void end(int flag){
    l[3].setFont(new Font(Font.SERIF,Font.BOLD,20));
    if(flag == 0){
    int get = pod;
    l[3].setText("�|�b�h����"+get+"�̃`�b�v���l�����܂����B�i����L�[�ł�����x�v���C�ł��܂��B�j");
    chip = chip + get;
    pod = 0;
    mbet = 0;
    ebet = 0;
    } else if(flag == 1){
    l[3].setText("�|�b�h����`�b�v���v������܂��B�i����L�[�ł�����x�v���C�ł��܂��B�j");
    pod = 0;
    mbet = 0;
    ebet = 0;
    } else if(flag == 2){
    l[3].setText("�|�b�h�̔����̃`�b�v���Ԋ҂���܂��B(����L�[�ł�����x�v���C�ł��܂��B)");
    int get = pod/2;
    chip = chip + get;
    pod = 0;
    mbet = 0;
    ebet = 0;
    }
  actionreset(16);
  repaint2();
  b[16].addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent event){
      reset();
    }
  });
  }
  //�ϐ��Ɖ摜��ActionListener�����Z�b�g���鏈��
  void reset(){
  int i=0,j=0,scene=0,mbet=0,ebet=0,pod=0,jisin=0;
  for(i = 0; i < 10; i++){
    cardi[i] = 0;
    cardn[i] = 0;
    cardm[i] = 0;
  }
  for(i = 0; i < 53; i++){
    cardflag[i] = 0;
  }
  for(i = 0; i < 5; i++){
    cardflag2[i] = 0;
  }
  for(i = 0; i < 53; i++){
    cardflag[i] = 0;
  }
  ImageIcon icon1 = new ImageIcon("./�g�����v/z02.png");
  Image icon2 = icon1.getImage().getScaledInstance((int)(icon1.getIconWidth()*0.5),-1,Image.SCALE_SMOOTH);
  ImageIcon icon3 = new ImageIcon(icon2);
  for(i = 0; i < 10; i++){
    b[i].setIcon(icon3);
  }
  actionreset(16);
  if(chip < 0){
    JOptionPane.showMessageDialog(this,"�����`�b�v����������̂ŁA��[���܂����B","��[",1);
    chip = 1100;
  }
  scene0();
  }
}
