import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class ImageAnimationC{
    private final ArrayList<BufferedImage> ImageList;
    private double X = 0;
    private double Y = 0;
    private int Index = 0;
    private int Angle = 0;
    private double SizeX = 1.0;
    private double SizeY = 1.0;

    ImageAnimationC(){
        ImageList = new ArrayList<>();
    }

    public void setImage(BufferedImage image){
        ImageList.add(image);
    }

    void showImage(Graphics2D g){
        g.drawImage(ImageList.get(Index),(int)(this.X-(ImageList.get(Index).getWidth()*SizeX/2)),(int)(this.Y-(ImageList.get(Index).getHeight()*SizeY/2)),(int)(ImageList.get(Index).getWidth()*SizeX),(int)(ImageList.get(Index).getHeight()*SizeY),null);
    }

    void nextImage(){
        Index++;
    }

    void rotateImage(Graphics2D g){
        this.Angle = 90;
        AffineTransform at = g.getTransform();
        at.setToRotation(Math.toRadians(this.Angle),X,Y);
        g.setTransform(at);
    }

    void rotateImage2(Graphics2D g,int Angle){
        this.Angle += Angle;
        AffineTransform at = g.getTransform();
        at.setToRotation(Math.toRadians(this.Angle),X,Y);
        g.setTransform(at);
    }

    void reset(Graphics2D g){
        AffineTransform at = g.getTransform();
        at.setToIdentity();
        g.setTransform(at);
    }

    void setXY(int X,int Y){
        this.X = X;
        this.Y = Y;
    }

    void setXY(double X,double Y){
        this.X = X;
        this.Y = Y;
    }

    void setX(int X){
        this.X = X;
    }

    void setY(int Y){
        this.Y = Y;
    }

    void setY(double Y){
        this.Y = Y;
    }

    void setXY2(int X){
        this.X += X;
        this.Y -= Math.abs(X);
    }

    void setXY2(double X){
        this.X += X;
        this.Y += -1;
    }

    public void setX2(int X){
        this.X += X;
    }

    public void setX2(double X){
        this.X += X;
    }

    void setY2(int Y){
        this.Y += Y;
    }

    void setY2(double Y){
        this.Y += Y;
    }

    void setSize(double Size){
        this.SizeX = Size;
        this.SizeY = Size;
    }

    void setSize2A(){
        this.SizeX += 0;
        this.SizeY += 0.018;
    }

    void setSize2B(){
        this.SizeX += 0.03;
        this.SizeY += 0.03;
    }

    int getXX(){
        return (int)X;
    }

    int getYY(){
        return (int)Y;
    }

    int getAngle(){
        return Angle;
    }

    double getSizeX(){
        return SizeX;
    }

    double getSizeY(){
        return SizeY;
    }

}
