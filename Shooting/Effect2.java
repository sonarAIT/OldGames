import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial")
class Effect2 extends Effect1{
	int time = 0;
	int hou;
	int cou = 0;

	Effect2(int hou){
		this.hou = hou;
		int tmp = hou*2;
		imagelist.clear();
		for(int i = 31; i < 33; i++){
			for(int j = 0; j < 2; j++) {
				setphoto("file/s_" + (i+tmp) + ".png");
			}
		}
		for(int i = 0; i < 2; i++) {
			setphoto("file/s_" + (31+tmp) + ".png");
		}
	}

	void timetime(){
		time++;
		if(time == 15){
			soeji++;
			time = 0;
		}
	}

	public void showphoto2(Graphics2D g){
		g.drawImage(imagelist.get(soeji),x-(imagelist.get(soeji).getWidth(this)/2),y-(imagelist.get(soeji).getHeight(this)/2),null);//syori tuika
		if(cou == 0){
			cou++;
		}else{
			cou=0;
			x = x+(hou == 0?1:-1);
		}
	}
}
