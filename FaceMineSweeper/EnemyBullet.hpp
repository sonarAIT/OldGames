struct EnemyBullet {
protected:
    Texture texture;
    double x;
    double y;
    double angle;
public:
    void init(Texture texture){
        this->texture = texture;
    }

    void setXY(int x, int y,double angle){
        this->x = x;
        this->y = y;
        this->angle = angle;
    }

    double getX(){
        return x;
    }

    double getY(){
        return y;
    }

    virtual void move() = 0;
    virtual void draw() = 0;
};