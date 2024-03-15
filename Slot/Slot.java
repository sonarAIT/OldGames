import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.*;
import javax.swing.ImageIcon;
import javax.imageio.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

class Slot extends JFrame{
	static BufferStrategy strategy;
	JButton b;
	JLabel l;
	JPanel panel;
	Clip[] oto = new Clip[7];
	AudioInputStream[] audio = new AudioInputStream[7];
	PhotoAnimation[] p = new PhotoAnimation[4];
	Random rnd;
	Font font;
	int sflag = 0;
	int hflag = 0;
	int rflag[] = {0,0,0};
	int mflag = 0;
	String[] moji = {"←","↓","→","↑:スタート","↑:もう一回","おめでとう！！！"};

	Slot(){
		b = new JButton();
		l = new JLabel();
		panel = new JPanel();
		for(int i = 0; i < 3; i++){
			p[i] = new PhotoAnimation();
			p[i].setphoto(new File("./File/gazou.png"));
		}
		p[3] = new PhotoAnimation2();
		p[3].setphoto(new File("./File/sen1.png"));
		p[3].setphoto(new File("./File/sen2.png"));
		try{
			for(int i = 0; i <= 6; i++){
				audio[i] = AudioSystem.getAudioInputStream(new File("./File/s"+i+".wav"));
				oto[i] = AudioSystem.getClip();
				oto[i].open(audio[i]);
			}
		}catch(IOException e){
		}catch(UnsupportedAudioFileException e){
		}catch(LineUnavailableException e){
		}
		font = new Font(Font.SERIF,Font.PLAIN,80);
		rnd = new Random();
	}

	public static void main(String args[]){
		Slot f = new Slot();
		f.setTitle("顔スロット");
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(1000,800);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setResizable(false);
		f.setIgnoreRepaint(true);
		f.createBufferStrategy(2);
		strategy = f.getBufferStrategy();
		f.start();
	}

	void start(){
		setLayout(null);
		panel.setLayout(null);
		panel.setBounds(0,0,1000,1000);
		panel.setBackground(Color.WHITE);
		add(panel);
		b.setBounds(400,550,200,50);
		b.setText("スタート");
		b.addActionListener(event -> {start2();});
		panel.add(b);
		l.setBounds(100,200,800,200);
		l.setOpaque(true);
		l.setBorder(new LineBorder(Color.RED,20,true));
		l.setText("顔スロット");
		l.setBackground(Color.WHITE);
		l.setFont(font);
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setVerticalAlignment(JLabel.CENTER);
		panel.add(l);
		repaint();
	}

	void start2(){
		remove(panel);
		panel.remove(b);
		panel.remove(l);
		addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				switch(e.getKeyCode()){
					case KeyEvent.VK_UP:
					if(sflag == 0){
						Slot.this.rota();
					}else if(hflag >= 1){
						audioplay(0);
						after();
						hflag = 0;
						sflag = 0;
					}
					break;
					case KeyEvent.VK_LEFT:
					if(rflag[0] == 1){
						audiostop(2);
						audioplay(2);
						if(p[0].kakudo%45 < 22){
							p[0].kakudo = p[0].kakudo-p[0].kakudo%45;
						}else{
							p[0].kakudo = p[0].kakudo-p[0].kakudo%45+45;
						}
						rflag[0] = 0;
					}
					break;
					case KeyEvent.VK_DOWN:
					if(rflag[1] == 1){
						audiostop(2);
						audioplay(2);
						if(p[1].kakudo%45 < 22){
							p[1].kakudo = p[1].kakudo-p[1].kakudo%45;
						}else{
							p[1].kakudo = p[1].kakudo-p[1].kakudo%45+45;
						}
						rflag[1] = 0;
					}
					break;
					case KeyEvent.VK_RIGHT:
					if(rflag[2] == 1){
						audiostop(2);
						audioplay(2);
						if(p[2].kakudo%45 < 22){
							p[2].kakudo = p[2].kakudo-p[2].kakudo%45;
						}else{
							p[2].kakudo = p[2].kakudo-p[2].kakudo%45+45;
						}
						rflag[2] = 0;
					}
					break;
				}
			}
		});
		audioplay(0);
		set();
	}

	void set(){
		Graphics2D g = (Graphics2D)this.strategy.getDrawGraphics();
		for(int i = 0; i < 3; i++){
			p[i].showphoto(g,0,(i*220)+200+this.p[i].imagelist.get(p[i].soeji).getWidth(this)/2,250+this.p[i].imagelist.get(p[i].soeji).getHeight(this)/2);
		}
		repaint();
		timerset();
	}

	void timerset(){
		Timer t = new Timer();
		t.schedule(new Task(),0);
	}
	class Task extends TimerTask{
		public void run(){
			for(long i = 0;;i++){
			if(i%100000==0){
			Slot.this.mainroop();
			Slot.this.requestFocus();
			i = 0;
			}
			}
		}
	}

	void mainroop(){
		Graphics2D g = (Graphics2D)this.strategy.getDrawGraphics();
		check();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0,this.getWidth(), this.getHeight());
		for(int i = 0; i < 3; i++){
			if(rflag[i] == 1){
				p[i].rotatephoto(g,p[i].kakudo+1);
			}else{
				p[i].rotatephoto(g,p[i].kakudo);
			}
			p[i].showphoto(g,0,p[i].x,p[i].y);
		}
		p[0].rotatereset(g);
		g.setFont(font);
		for(int i = 0; i < 3; i++){
			g.drawString(moji[i],i*200+250,500);
		}
		if(hflag == 0){
			g.drawString(moji[3],350,650);
		}else{
			g.drawString(moji[4],350,650);
		}
		if(hflag == 3){
			best(g);
		}
		g.dispose();
		this.strategy.show();
	}

	void check(){
		if(rflag[0] == 0&&rflag[1] == 0&&rflag[2] == 0&&hflag == 0&&sflag != 0){
			audiostop(0);
			if(p[0].kakudo%360 == 0 && p[1].kakudo%360 == 0 && p[2].kakudo%360 == 0){
				audioplay(5);
				hflag = 3;
			}else if(p[0].kakudo%360/45 == p[1].kakudo%360/45 && p[1].kakudo%360/45 == p[2].kakudo%360/45){
				audioplay(4);
				hflag = 2;
			}else{
				audioplay(3);
				hflag = 1;
			}
		}
	}

	void rota(){
		audioplay(1);
		for(int i = 0; i < 3; i++){
			rflag[i] = 1;
		}
		sflag = 1;
	}

	void best(Graphics2D g){
		p[3].showphoto(g,mflag,0,0);
		mflag = 1 - mflag;
		int xx;
		int yy;
		for(int i = 0; i < 3; i++){
			xx = rnd.nextInt(15);
			yy = rnd.nextInt(15);
			p[i].x += xx-7;
			p[i].y += yy-7;
		}
		g.setFont(font);
		g.setColor(Color.RED);
		g.drawString(moji[5],200,400);
	}

	void after(){
		Graphics2D g = (Graphics2D)this.strategy.getDrawGraphics();
		for(int i = 0; i < 3; i++){
			p[i].showphoto(g,0,(i*220)+200+this.p[i].imagelist.get(p[i].soeji).getWidth(this)/2,250+this.p[i].imagelist.get(p[i].soeji).getHeight(this)/2);
		}
		repaint();
		audiostop(3);
		audiostop(4);
		audiostop(5);
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

}

class PhotoAnimation2 extends PhotoAnimation{
	public void showphoto(Graphics2D g,int soeji,int x,int y){
		this.soeji = soeji;
		this.x = x;
		this.y = y;
		g.drawImage(imagelist.get(soeji),x,y,null);
	}
}
