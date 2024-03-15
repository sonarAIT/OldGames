import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class PhotoAnimation extends JFrame{
  ArrayList<BufferedImage> imagelist = new ArrayList<>();
  int x=0,y=0,soeji=0,kakudo=0;
  
  public void setphoto(BufferedImage image){
    imagelist.add(image);
  }
  
  public void setphoto(File image){
    try{
      BufferedImage i = ImageIO.read(image);
      imagelist.add(i);
    }catch(IOException e){
    }
  }
  
  public void showphoto(Graphics2D g,int soeji,int x,int y){
    this.soeji = soeji;
    this.x = x;
    this.y = y;
    g.drawImage(imagelist.get(soeji),x-(imagelist.get(soeji).getWidth(this)/2),y-(imagelist.get(soeji).getHeight(this)/2),null);
  }
  
  public void movephoto(Graphics2D g,int x,int y){
    this.x += x;
    this.y += y;
    g.drawImage(imagelist.get(soeji),x-(imagelist.get(soeji).getWidth(this)/2),y-(imagelist.get(soeji).getHeight(this)/2),null);
  }
  
  public void changephoto(Graphics2D g,int soeji){
    this.soeji = soeji;
  }
  
  public void rotatephoto(Graphics2D g,int kakudo){
    this.kakudo = kakudo;
    AffineTransform at = g.getTransform();
    at.setToRotation(Math.toRadians(kakudo),x,y);
    g.setTransform(at);
  }
  
  public void rotatereset(Graphics2D g){
    AffineTransform at = g.getTransform();
    at.setToRotation(0);
    g.setTransform(at);
  }
  
  /*public void sizechangephoto(Graphics g,int sizex,int sizey){
  }*/
  
  /*public void detelephoto(){
  }*/
  
}
