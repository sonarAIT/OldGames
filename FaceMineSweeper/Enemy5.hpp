struct Enemy5 : Enemy{
private:
    bool shotflag = false;
    bool LRflag = true;
    bool tflag = false;
    double tmpx;
    double tmpy;
    double r;
    double Speed;

public:
    void setXY(int x, int y,double option){
        this->x = x;
        this->y = y;
        //this->Speed = option;
        Speed = 500;
        tmpx = x - Define::WINDOW_W/2;
        tmpy = y - Define::WINDOW_H/2;
        r = tmpx;
    }

    void move(){
        if(LRflag){
            tmpx = Max(tmpx-Speed*Scene::DeltaTime(),-1*r);
        }else{
            tmpx = Min(tmpx+Speed*Scene::DeltaTime(),r);
        }
        if(tmpx == -1*r || tmpx == r){
            toggleShotFlag();
            LRflag = !LRflag;
            tflag = true;
        }
        tmpy = (LRflag?-1:1)*(sqrt(r*r-tmpx*tmpx));
        if((tmpx <= 0.0 && LRflag && tflag)||(tmpx >= 0.0 && !LRflag && tflag)){
            tflag = false;
            toggleShotFlag();
        }

        x = tmpx+Define::WINDOW_W/2;
        y = tmpy+Define::WINDOW_H/2;
    }

    void draw(){
        if(redtoggle){
            texture.drawAt((int)x,(int)y);
        }else{
            texture.drawAt((int)x,(int)y,Palette::Red);
            redtoggle = !redtoggle;
        }
    }

    void toggleShotFlag(){
        shotflag = !shotflag;
    }

    bool getShotFlag(){
        return shotflag;
    }
};