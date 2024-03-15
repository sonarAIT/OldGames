import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import javax.sound.sampled.*;

@SuppressWarnings("serial")
public class Force extends Ship{
  int jou = 0;
  int xcou = 0;
  int ycou = 0;
  int flag = 0;
  int kflag;
  int tcou = 0;
  int spe = 8;//ie 5 ga 8
  int mspe = spe*-1;
  int time = 0;
  AudioInputStream audio;
  Clip oto;
  /*
  0 = jikinomae
  1 = jikinousiro
  2 = maenitobu
  3 = usironitobu
  4 = uiteru
  5 = modoru
  */
  Force(int kflag){
    this.kflag = kflag;
    try{
      if(kflag == 0){
        audio = AudioSystem.getAudioInputStream(this.getClass().getResource("file/w_2.wav"));
      }else{
        audio = AudioSystem.getAudioInputStream(this.getClass().getResource("file/k_5.wav"));
      }
      oto = AudioSystem.getClip();
      oto.open(audio);
    }catch(IOException e){
    }catch(UnsupportedAudioFileException e){
    }catch(LineUnavailableException e){
    }
  }

  int jo(){
    return jou;
  }

  void fmove(int sx,int sy){
    if(jou == 0){
      x = sx+65;
      y = sy;
    }else if(jou == 1){
      x = sx-73;
      y = sy;
    }else if(jou == 2){
      x = x + 1;
      if(x >= 800){
        x = 800;
        jou = 4;
      }
    }else if(jou == 3){
      x = x - 1;
      if(x <= 0){
        x = 0;
        jou = 4;
      }
    }else if(jou == 4){
      if(x >= 575 || (x <= sx && x >= 225)){
        xcou--;
        if(xcou == mspe){
          x--;
          xcou = 0;
        }
        //System.out.print("a" + xcou);
      }
      if(x <= 225 || (x >= sx && x <= 575)){
        xcou++;
        if(xcou == spe){
          x++;
          xcou = 0;
        }
        //System.out.print("b");
      }
      if(y != sy){
        if(flag == 0){
          tcou++;
          if(tcou >= 150){
            flag = 1;
          }
        }else if(flag == 1){
          if(y >= sy){
            ycou--;
            if(ycou == mspe){
              y--;
              ycou = 0;
            }
          }else{
            ycou++;
            if(ycou == spe){
              y++;
              ycou = 0;
            }
          }
          if(y == sy){
            flag = 2;
            tcou = 0;
          }
        }else{
          tcou++;
          if(y >= sy){
            flag = 1;
            ycou--;
            if(ycou == mspe){
              y--;
              ycou = 0;
            }
          }else{
            flag = 1;
            ycou++;
            if(ycou == spe){
              y++;
              ycou = 0;
            }
          }
          if(y == sy){
            tcou++;
            if(tcou >= 100){
              flag = 0;
            }
          }
        }
      }else{
        flag = 0;
        tcou = 0;
      }
      if((y >= sy-50 && y <= sy+50) && (x <= sx + 50 && x >= sx)){
        jou = 0;
        audioplay();
      }else if((y >= sy-50 && y <= sy+50) && (x >= sx - 50 && x <= sx)){
        jou = 1;
        audioplay();
      }
    }else if(jou == 5){
      if(sx <= x){
        xcou--;
        if(xcou == mspe+2){
          xcou = 0;
          x--;
        }
      }else{
        xcou++;
        if(xcou == spe-2){
          xcou = 0;
          x++;
        }
      }
      if(sy <= y){
        ycou--;
        if(ycou == mspe+2){
          ycou = 0;
          y--;
        }
      }else{
        ycou++;
        if(ycou == spe-2){
          ycou = 0;
          y++;
        }
      }
      if((y >= sy-50 && y <= sy+50) && (x <= sx + 50 && x >= sx)){
        jou = 0;
        audioplay();
      }else if((y >= sy-50 && y <= sy+50) && (x >= sx - 50 && x <= sx)){
        jou = 1;
        audioplay();
      }
    }else if(jou == 6){

    }
  }

  void njou(int nj){
    jou = nj;
    if(nj == 5 || nj == 4){
      xcou = 0;
      ycou = 0;
    }
  }

  void sum(){
    time++;
    if(time == 200){//ie 150 ga 200
      time = 0;
      soeji = (soeji+1)%4;
    }
  }

  void audioplay(){
    oto.setFramePosition(0);
    oto.start();
  }
}
