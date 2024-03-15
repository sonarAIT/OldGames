import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial")
class Effect1 extends PhotoAnimation implements Cloneable{
	int time = 0;

	Effect1(){
		for(int i = 25; i < 31; i++){
			setphoto("file/s_" + i + ".png");
		}
	}

	void timetime(){
		time++;
		if(time == 60){//ie 30 ga 60
			soeji++;
			time = 0;
		}
	}
	
	@Override
	public Effect1 clone() {
		Effect1  b = null;
		
		try {
			b = (Effect1)super.clone();
			
		}catch(Exception e) {
		}
		return b;
	}
}
