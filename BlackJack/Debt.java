import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Debt extends JFrame{
    private String s1;
    private String s2;
    private String s3;
    private ImageIcon icon2;
    private int flag = 0;

    void debt(String s1,String s2,String s3,ImageIcon icon1,ImageIcon icon2){
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.icon2 = icon2;
        setTitle(s3);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        JButton b = new JButton();
        b.setBounds(50,50,300,500);
        b.addActionListener(e -> flag = 1);
        b.setIcon(icon1);
        add(b);
        setVisible(true);
    }

    void debt2(){
        JOptionPane.showMessageDialog(this,s1,s3,JOptionPane.WARNING_MESSAGE,icon2);
        while(flag == 0){
            System.out.print("");
        }
        JOptionPane.showMessageDialog(this,s2,s3,JOptionPane.INFORMATION_MESSAGE,icon2);
        setVisible(false);
        dispose();
    }
}
