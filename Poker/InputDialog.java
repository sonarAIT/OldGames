import javax.swing.*;
import java.awt.*;

class InputDialog extends JFrame{
  JFrame frame;
  String mess,name;
  int type1;
  InputDialog(JFrame frame,String mess,String name,int type1){
    this.frame = frame;
    this.mess = mess;
    this.name = name;
    this.type1 = type1;
  }
  
  String dialog1(){
    String value = JOptionPane.showInputDialog(frame,mess,name,type1);
    return value;
  }
  
  int dialog2(){
    int value2 = 0;
    try{
      String value1 = JOptionPane.showInputDialog(frame,mess,name,type1);
      value2 = Integer.parseInt(value1);
    } catch(Exception error) {
    }
      return value2;
  }
}
