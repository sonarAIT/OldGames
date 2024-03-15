import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Ship extends PhotoAnimation{
  public void up(int a){
    y-=a;
  }

  public void down(int a){
    y+=a;
  }

  public void left(int a){
    x-=a;
  }

  public void right(int a){
    x+=a;
  }
}
