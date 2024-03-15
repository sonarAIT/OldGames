import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Othello extends JFrame{
	Panel p = new Panel();
	JButton b[][] = new JButton[8][8];
	Color green = new Color(0,100,0);
	JLabel l = new JLabel();
  LineBorder border = new LineBorder(Color.red,15,true);
	int i,j,k,m,wisi = 2,bisi = 2,eflag = 0,turn,x,y,nflag = 0;
	int bb[][] = new int[8][8];

	//�z���̃{�^����new���邱�Ƃ��Y���Ȃ��悤��
	Othello(){
		for(i = 0; i < 8; i++) {
			for(j = 0; j < 8; j++) {
				b[i][j] = new JButton();
				bb[i][j] = 0;
			}
		}
	}

	public static void main(String args[]) {
		Othello f = new Othello();
		f.setTitle("�I�Z���Q�[��");
		f.setBounds(500,10,800,1000);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
		f.set();
	}

	void set() {
		setLayout(null);

		p.setBounds(0,0,800,800);
		p.setBackground(Color.BLACK);
		p.setLayout(null);
		add(p);

		for(i = 0; i < 8; i++) {
			for(j = 0; j < 8; j++) {
				b[i][j].setBounds(5+98*i,5+98*j,90,90);
				b[i][j].setBackground(green);
				//b[i][j].setText(i + "," + j);
				p.add(b[i][j]);
			}
		}

		l.setBounds(0,800,784,160);
		l.setOpaque(true);
    l.setBorder(border);
		l.setText("���@�@" + wisi + "�@�@" + bisi + "�@�@��");
		l.setBackground(Color.WHITE);
		l.setFont(new Font(Font.SERIF,Font.PLAIN,40));
		l.setHorizontalAlignment(JLabel.CENTER);
	  l.setVerticalAlignment(JLabel.CENTER);
		add(l);

		repaint();
		JOptionPane.showMessageDialog(this,"�I�Z���Q�[���ւ悤�����I","�I�Z���Q�[��",1);
		set2();
	}

	//�������p�ɂ������p��
	void set2() {
		for(i = 0; i < 8; i++) {
			for(j = 0; j < 8; j++) {
				bb[i][j] = 0;
			}
		}

		bb[3][3] = 1;
		bb[3][4] = 2;
		bb[4][3] = 2;
		bb[4][4] = 1;
		wisi = 2;
		bisi = 2;
    imagepaint();
		eflag = 0;

		String modename[] = {"���U","���U","���l�v���C"};
		int mode = JOptionPane.showOptionDialog(this,"�Q�[�����[�h���I���ł�������","�I�Z���Q�[��",1,3,null,modename,modename[0]);
		if(mode == 0) {
			turn = 1;
      JOptionPane.showMessageDialog(this,"���Ȃ��͔��ł��B","�I�Z���Q�[��",1);
			main0();
		} else if(mode == 1) {
			turn = 1;
      JOptionPane.showMessageDialog(this,"���Ȃ��͍��ł��B","�I�Z���Q�[��",1);
			main1();
		} else if(mode == 2) {
			turn = 1;
      JOptionPane.showMessageDialog(this,"���U�͔��ł��B","�I�Z���Q�[��",1);
			main2();
		}
	}

	void main0() {
		int mainsflag;
		while(eflag < 2 && wisi + bisi < 64) {
			mainsflag = 0;
			for(i = 0; i < 8; i++) {
				for(j = 0; j < 8; j++) {
          if(bb[i][j] == 0){
					  mainsflag += search(i,j);
          }
				}
			}
			if(mainsflag == 0) {
				JOptionPane.showMessageDialog(this,"�R�}���u�����ꏊ���Ȃ��̂ŁA�p�X���܂��B","�I�Z���Q�[��",1);
				eflag++;
			} else {
        eflag = 0;
				buttonset();
				while(nflag == 0){
          System.out.print("");//�Ȃ��������������Ȃ��ƃ��[�v�𔲂��o���Ȃ��i�{���Ȃ��{�^�����������甲�����͂�)
				}
				nflag = 0;
				reverse(x,y);
			}
			debug();
      imagepaint();
      count();
      l.setText("���@�@" + wisi + "�@�@" + bisi + "�@�@��");
      for(i = 0; i < 1000000000; i++){
      }
      if(eflag < 2 && wisi + bisi < 64){
        turn = 2;
			  mainsflag = 0;
			  for(i = 0; i < 8; i++) {
				  for(j = 0; j < 8; j++) {
            if(bb[i][j] == 0){
					    mainsflag += search(i,j);
            }
				  }
			  }
			  if(mainsflag == 0) {
				  JOptionPane.showMessageDialog(this,"�����̓p�X���܂���","�I�Z���Q�[��",1);
				  eflag++;
			  } else {
          eflag = 0;
          enemy();
          reverse(x,y);
		  	}
			  System.out.println("�G���u�����ꏊ:" + x + "," + y);
			  System.out.println("----------------");
			  debug();
        turn = 1;
	      imagepaint();
        count();
        this.setTitle("�G��" + x + "," + y + "�ɒu���܂���");
        l.setText("���@�@" + wisi + "�@�@" + bisi + "�@�@��");
		  }
	  }
    end();
  }

	void main1() {
    int mainsflag;
		while(eflag != 2 && wisi + bisi < 64) {
	    mainsflag = 0;
			for(i = 0; i < 8; i++) {
				for(j = 0; j < 8; j++) {
          if(bb[i][j] == 0){
					  mainsflag += search(i,j);
          }
				}
			}
		  if(mainsflag == 0) {
			  JOptionPane.showMessageDialog(this,"�����̓p�X���܂���","�I�Z���Q�[��",1);
		    eflag++;
			} else {
        eflag = 0;
        enemy();
        reverse(x,y);
		  }
			System.out.println("�G���u�����ꏊ:" + x + "," + y);
			System.out.println("----------------");
			debug();
	    imagepaint();
      this.setTitle("�G��" + x + "," + y + "�ɒu���܂���");
      turn = 2;
      count();
      l.setText("���@�@" + wisi + "�@�@" + bisi + "�@�@��");
      if(eflag < 2 && wisi + bisi < 64){
        mainsflag = 0;
			  for(i = 0; i < 8; i++) {
				  for(j = 0; j < 8; j++) {
            if(bb[i][j] == 0){
					    mainsflag += search(i,j);
            }
				  }
			  }
			  if(mainsflag == 0) {
				  JOptionPane.showMessageDialog(this,"�R�}���u�����ꏊ���Ȃ��̂ŁA�p�X���܂��B","�I�Z���Q�[��",1);
				  eflag++;
			  } else {
          eflag = 0;
				  buttonset();
				  while(nflag == 0){
            System.out.print("");
				  }
				  nflag = 0;
				  reverse(x,y);
			  }
			  debug();
        imagepaint();
        count();
        l.setText("���@�@" + wisi + "�@�@" + bisi + "�@�@��");
        turn = 1;
        for(i = 0; i < 1000000000; i++){
        }
		  }
    }
    end();
	}

	void main2() {
    int mainsflag;
		while(eflag != 2&& wisi + bisi < 64) {
      this.setTitle("���̔Ԃł�");
	    mainsflag = 0;
			for(i = 0; i < 8; i++) {
				for(j = 0; j < 8; j++) {
          if(bb[i][j] == 0){
					  mainsflag += search(i,j);
          }
				}
			}
      if(mainsflag == 0) {
				JOptionPane.showMessageDialog(this,"�R�}���u�����ꏊ���Ȃ��̂ŁA�p�X���܂��B","�I�Z���Q�[��",1);
				eflag++;
			} else {
        eflag = 0;
				buttonset();
				while(nflag == 0){
          System.out.print("");
				}
				nflag = 0;
				reverse(x,y);
			}
			debug();
      imagepaint();
      count();
      l.setText("���@�@" + wisi + "�@�@" + bisi + "�@�@��");
      turn = 2;
      this.setTitle("���̔Ԃł�");
      if(eflag < 2 && wisi + bisi < 64){
        mainsflag = 0;
			  for(i = 0; i < 8; i++) {
				  for(j = 0; j < 8; j++) {
            if(bb[i][j] == 0){
					    mainsflag += search(i,j);
            }
				  }
			  }
			  if(mainsflag == 0) {
				  JOptionPane.showMessageDialog(this,"�R�}���u�����ꏊ���Ȃ��̂ŁA�p�X���܂��B","�I�Z���Q�[��",1);
				  eflag++;
			  } else {
          eflag = 0;
				  buttonset();
				  while(nflag == 0){
            System.out.print("");
				  }
				  nflag = 0;
				  reverse(x,y);
			  }
			  debug();
        imagepaint();
        count();
        l.setText("���@�@" + wisi + "�@�@" + bisi + "�@�@��");
        turn = 1;
      }
		}
    end();
	}

	//���̏ꏊ�łЂ������Ԃ��������������ނ��邩���ׂ郁�\�b�h
	int search(int i,int j) {
		int sflag = 0;
		int t = 3 - turn;
		//for(i = 0; i < 8; i++) {
		//	for(j = 0; j < 8; j++) {
				if(i >= 2) {
					if(bb[i - 1][j] == t) {
						for(k = i - 2; k >= 0 && bb[k][j] != 0; k--) {
							if(bb[k][j] == turn) {
								sflag++;
								break;
							}
						}
					}
				}

				if(i <= 5) {
					if(bb[i + 1][j] == t) {
						for(k = i + 2; k <= 7 && bb[k][j] != 0; k++) {
							if(bb[k][j] == turn) {
								sflag++;
								break;
							}
						}
					}
				}

				if(j >= 2) {
					if(bb[i][j - 1] == t) {
						for(k = j - 2; k >= 0 && bb[i][k] != 0; k--) {
							if(bb[i][k] == turn) {
								sflag++;
								break;
							}
						}
					}
				}

				if(j <= 5) {
					if(bb[i][j + 1] == t) {
						for(k = j + 2; k <= 7 && bb[i][k] != 0; k++) {
							if(bb[i][k] == turn) {
								sflag++;
								break;
							}
						}
					}
				}

				if(i >= 2 && j >= 2) {
					if(bb[i - 1][j - 1] == t) {
						for(k = 2; i - k >= 0 && j - k>= 0 && bb[i - k][j - k] != 0; k++) {
							if(bb[i - k][j - k] == turn) {
								sflag++;
								break;
							}
						}
					}
				}

				if(i <= 5 && j >= 2) {
					if(bb[i + 1][j - 1] == t) {
						for(k = 2; i + k <= 7 && j - k >= 0 && bb[i + k][j - k] != 0; k++) {
							if(bb[i + k][j - k] == turn) {
								sflag++;
								break;
							}
						}
					}
				}

				if(i >= 2 && j <= 5) {
					if(bb[i - 1][j + 1] == t) {
						for(k = 2; i - k >= 0 && j + k <= 7 && bb[i - k][j + k] != 0; k++) {
							if(bb[i - k][j + k] == turn) {
								sflag++;
								break;
							}
						}
					}
				}

				if(i <= 5 && j <= 5) {
					if(bb[i + 1][j + 1] == t) {
						for(k = 2; i + k <= 7 && j + k <= 7 && bb[i + k][j + k] != 0; k++) {
							if(bb[i + k][j + k] == turn) {
								sflag++;
								break;
							}
						}
					}
				}
		//	}
		//}
		return sflag;
	}

	void buttonset() {
		for(i = 0; i < 8; i++) {
			for(j = 0; j < 8; j++) {
				b[i][j].setActionCommand("" + i + j);
				b[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						String cmdName = event.getActionCommand();//�{�^���̍��W�����߂�
						int cmd = Integer.parseInt(cmdName);
						x = cmd / 10;
						y = cmd % 10;
						int sflag = 0;
						sflag = search(x,y);
						if(sflag >= 1 && bb[x][y] == 0) {
							actionreset();
              bb[x][y] = turn;
              image(x,y,turn);
							nflag = 1;
						} else {
							JOptionPane.showMessageDialog(null,"���̏ꏊ�ɂ͒u���܂���","�I�Z���Q�[��",1);
						}
					}
				});
			}
		}
	}

	void reverse(int i,int j) {
		int sflag = 0;
		int t = 3 - turn;
		if(i >= 2) {
			if(bb[i - 1][j] == t) {
				for(k = i - 2; k >= 0 && bb[k][j] != 0; k--) {
					sflag++;
					if(bb[k][j] == turn) {
						turning1(3,sflag);
						break;
					}
				}
			}
		}

		if(i <= 5) {
			if(bb[i + 1][j] == t) {
					sflag = 0;
				for(k = i + 2; k <= 7&& bb[k][j] != 0; k++) {
					sflag++;
					if(bb[k][j] == turn) {
						turning1(1,sflag);
						break;
					}
				}
			}
		}

		if(j >= 2) {
			if(bb[i][j - 1] == t) {
				sflag = 0;
				for(k = j - 2; k >= 0 && bb[i][k] != 0; k--) {
					sflag++;
					if(bb[i][k] == turn) {
						turning1(0,sflag);
						break;
					}
				}
			}
		}

		if(j <= 5) {
			if(bb[i ][j + 1] == t) {
				sflag = 0;
				for(k = j + 2; k <= 7 && bb[i][k] != 0; k++) {
					sflag++;
					if(bb[i][k] == turn) {
						turning1(2,sflag);
						break;
					}
				}
			}
		}

		if(i >= 2 && j >= 2) {
			if(bb[i - 1 ][j - 1] == t) {
        sflag = 0;
				for(k = 2; i - k >= 0 && j - k>= 0 && bb[i - k][j - k] != 0; k++) {
          sflag++;
					if(bb[i - k][j - k] == turn) {
						turning2(0,sflag);
						break;
					}
				}
			}
		}

		if(i <= 5 && j >= 2) {
			if(bb[i + 1 ][j - 1] == t) {
        sflag = 0;
				for(k = 2; i + k <= 7 && j - k >= 0 && bb[i + k][j - k] != 0; k++) {
          sflag++;
					if(bb[i + k][j - k] == turn) {
						turning2(1,sflag);
						break;
					}
				}
			}
		}

		if(i >= 2 && j <= 5) {
			if(bb[i - 1 ][j + 1] == t) {
        sflag = 0;
			  for(k = 2; i - k >= 0 && j + k <= 7 && bb[i - k][j + k] != 0; k++) {
          sflag++;
					if(bb[i - k][j + k] == turn) {
					  turning2(2,sflag);
						break;
					}
				}
			}
		}

		if(i <= 5 && j <= 5) {
			if(bb[i + 1 ][j + 1] == t) {
        sflag = 0;
				for(k = 2; i + k <= 7 && j + k <= 7 && bb[i + k][j + k] != 0; k++) {
          sflag++;
					if(bb[i + k][j + k] == turn) {
						turning2(3,sflag);
						break;
					}
				}
			}
		}
    repaint();
	}

	void turning1(int hou,int kazu){
		switch (hou){
			case 0:
        for(i = 1; i <= kazu; i++){
          bb[x][y - i] = turn;
        }
				break;
			case 1:
        for(i = 1; i <= kazu; i++){
          bb[x + i][y] = turn;
        }
				break;
			case 2:
        for(i = 1; i <= kazu; i++){
          bb[x][y + i] = turn;
        }
				break;
			case 3:
        for(i = 1; i <= kazu; i++){
          bb[x - i][y] = turn;
        }
				break;
		}
	}

  void turning2(int hou,int kazu){
    switch (hou){
			case 0:
        for(i = 1; i <= kazu; i++){
          bb[x - i][y - i] = turn;
        }
				break;
			case 1:
        for(i = 1; i <= kazu; i++){
          bb[x + i][y - i] = turn;
        }
				break;
			case 2:
        for(i = 1; i <= kazu; i++){
          bb[x - i][y + i] = turn;
        }
				break;
			case 3:
        for(i = 1; i <= kazu; i++){
          bb[x + i][y + i] = turn;
        }
				break;
		}
  }

  void enemy(){
    int sflag = 0;
    x = 0;
    y = 0;
    ending:{
      if(bb[0][0] == 0){
        sflag = search(0,0);
        if(sflag > 0){
          x = 0;
          y = 0;
          bb[x][y] = turn;
          image(x,y,turn);
          break ending;
        }
      }
      if(bb[0][7] == 0){
        sflag = search(0,7);
        if(sflag > 0){
          x = 0;
          y = 7;
          bb[x][y] = turn;
          image(x,y,turn);
          break ending;
        }
      }
      if(bb[7][0] == 0){
        sflag = search(7,0);
        if(sflag > 0){
          x = 7;
          y = 0;
          bb[x][y] = turn;
          image(x,y,turn);
          break ending;
        }
      }
      if(bb[7][7] == 0){
        sflag = search(7,7);
        if(sflag > 0){
          x = 7;
          y = 7;
          bb[x][y] = turn;
          image(x,y,turn);
          break ending;
        }
      }
      int max = 0,maxx = 0,maxy = 0;
      for(i = 1; i < 7; i++){
        if(bb[i][0] == 0){
          sflag = search(i,0);
          if(sflag > 0){
            sflag = enemy2(i,0);
            if(sflag > max){
              max = sflag;
              maxx = i;
              maxy = 0;
            }
          }
        }
      }
      for(i = 1; i < 7; i++){
        sflag = search(7,i);
        if(sflag > 0){
          if(bb[7][i] == 0){
            sflag = enemy2(7,i);
            if(sflag > max){
              max = sflag;
              maxx = 7;
              maxy = i;
            }
          }
        }
      }
      for(i = 1; i < 7; i++){
        if(bb[i][7] == 0){
          sflag = search(i,7);
          if(sflag > 0){
            sflag = enemy2(i,7);
            if(sflag > max){
              max = sflag;
              maxx = i;
              maxy = 7;
            }
          }
        }
      }
      for(i = 1; i < 7; i++){
        if(bb[0][i] == 0){
          sflag = search(0,i);
          if(sflag > 0){
            sflag = enemy2(0,i);
            if(sflag > max){
              max = sflag;
              maxx = 0;
              maxy = i;
            }
          }
        }
      }
      x = maxx;
      y = maxy;
      if(maxx == 0 && maxy == 0){
        for(i = 1; i < 7; i++){
          for(j = 1; j < 7; j++){
            if(bb[i][j] == 0){
              sflag = search(i,j);
              if(sflag > 0){
                sflag = enemy2(i,j);
                if(sflag > max){
                  max = sflag;
                  maxx = i;
                  maxy = j;
                }
              }
            }
          }
        }
        x = maxx;
        y = maxy;
      }
      bb[x][y] = turn;
      image(x,y,turn);
    }
  }

  int enemy2(int i,int j){
		int sflag = 0;
		int t = 3 - turn;
		if(i >= 2){
			if(bb[i - 1][j] == t) {
				for(k = i - 2; k >= 0 && bb[k][j] != 0; k--) {
					sflag++;
					if(bb[k][j] == turn) {
						break;
					}
				}
			}
		}

		if(i <= 5) {
			if(bb[i + 1][j] == t) {
				for(k = i + 2; k <= 7 && bb[k][j] != 0; k++) {
					sflag++;
					if(bb[k][j] == turn) {
						break;
					}
				}
			}
		}

		if(j >= 2) {
			if(bb[i][j - 1] == t) {
				for(k = j - 2; k >= 0 && bb[i][k] != 0; k--) {
					sflag++;
					if(bb[i][k] == turn) {
						break;
					}
				}
			}
		}

		if(j <= 5) {
			if(bb[i ][j + 1] == t) {
				for(k = j + 2; k <= 7 && bb[i][k] != 0; k++) {
					sflag++;
					if(bb[i][k] == turn) {
						break;
					}
				}
			}
		}

		if(i >= 2 && j >= 2) {
			if(bb[i - 1 ][j - 1] == t) {
				for(k = 2; i - k >= 0 && j - k>= 0 && bb[i - k][j - k] != 0; k++) {
          sflag++;
					if(bb[i - k][j - k] == turn) {
						break;
					}
				}
			}
		}

		if(i <= 5 && j >= 2) {
			if(bb[i + 1 ][j - 1] == t) {
				for(k = 2; i + k <= 7 && j - k >= 0 && bb[i + k][j - k] != 0; k++) {
          sflag++;
					if(bb[i + k][j - k] == turn) {
						break;
					}
				}
			}
		}

		if(i >= 2 && j <= 5) {
			if(bb[i - 1 ][j + 1] == t) {
			  for(k = 2; i - k >= 0 && j + k <= 7 && bb[i - k][j + k] != 0; k++) {
          sflag++;
					if(bb[i - k][j + k] == turn) {
						break;
					}
				}
			}
		}

		if(i <= 5 && j <= 5) {
			if(bb[i + 1 ][j + 1] == t) {
				for(k = 2; i + k <= 7 && j + k <= 7 && bb[i + k][j + k] != 0; k++) {
          sflag++;
					if(bb[i + k][j + k] == turn) {
						break;
					}
				}
			}
		}
    return sflag;
  }

	void image(int i,int j, int n) {
	    ImageIcon icon1 = new ImageIcon("./�摜/" + n + ".png");
	    Image icon2 = icon1.getImage().getScaledInstance((int)(icon1.getIconWidth()*0.18),-1,Image.SCALE_SMOOTH);
	    ImageIcon icon3 = new ImageIcon(icon2);
		b[i][j].setIcon(icon3);
	}

	void actionreset() {
		for(i = 0; i < 8; i++) {
			for(j = 0; j < 8; j++) {
				b[i][j].removeActionListener(b[i][j].getActionListeners()[0]);
			}
		}
	}

	void imagepaint(){
		for(i = 0; i < 8; i++){
			for(j = 0; j < 8; j++){
				image(i,j,bb[i][j]);
			}
		}
	}

  void count(){
    wisi = 0;
    bisi = 0;
    for(i = 0; i < 8; i++){
      for(j = 0; j < 8; j++){
        if(bb[i][j] == 1){
          wisi++;
        }else if(bb[i][j] == 2){
          bisi++;
        }
      }
    }
  }

  void end(){
    int win;
    String win2[] = {"�ň��������ł��B","�Ŕ��̏����ł��B","�ō��̏����ł��B"};
    if(wisi > bisi){
      win = 1;
    } else if(wisi < bisi){
      win = 2;
    } else {
      win = 0;
    }
    String win3 = wisi + "��" + bisi + win2[win];
    JOptionPane.showMessageDialog(this,win3,"�I�Z���Q�[��",1);
    int re = JOptionPane.showConfirmDialog(this,"�������������܂����H","�I�Z���Q�[��",0);
    if(re == 0){
      set2();
    } else {
    }
  }

	void debug(){
		for(i = 0; i < 8; i++){
			for(j = 0; j < 8; j++){
				if(bb[j][i] == 1){
					System.out.print("��");
				} else if(bb[j][i] == 2){
					System.out.print("�Z");
				} else {
					System.out.print("�~");
				}
			}
			System.out.println();
		}
		System.out.println("----------------");
	}
}
