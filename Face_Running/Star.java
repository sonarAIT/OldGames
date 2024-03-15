class Star{
    private int x;
    private final int y;
    private final int speed;

    Star(int x,int y,int speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    int getX(){
        return x;
    }

    int getY(){
        return y;
    }

    void move(){
        this.x -= speed;
    }
}
