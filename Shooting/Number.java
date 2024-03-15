import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Number extends PhotoAnimation{
	Number(){
		for(int i = 0; i < 10; i++){
			setphoto("file/s_n_" + i + ".png");
		}
		setphoto("file/s_n_0.png");
	}
}
