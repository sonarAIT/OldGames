import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial")
class Effect3 extends Effect1{
	int time = 0;

	Effect3(){
    imagelist.clear();
		for(int i = 35; i < 41; i++){
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
}
