import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Enemy extends Ship{
  int time = 0;
  int hou;
  int kflag;

  Enemy(int hou,int kflag){
    this.hou = hou;
    if(kflag == 0){
      for(int i = 0; i < 6; i++){
        setphoto("file/s_" + (10+i+hou*6) + ".png");
      }
    }else{
      setphoto("file/gazou3.png");
    }
  }

  void timetime(){
    time++;
    if(time == 90){//ie 60 ga 90
      soeji = (soeji+1)%6;
      time = 0;
    }
  }

  void move2(){
    if(hou == 0){
      left(4);
    }else{
      right(4);
    }
  }

}
