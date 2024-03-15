struct Enemy3 : Enemy{
    bool shotflag = false;
    bool LRflag = true;
    double deltasum = 0;
    bool Gamemode = true;

    void move(){
        if(Gamemode){
            move1();
        }else{
            move2();
        }
    }

    void move1(){
        deltasum += Scene::DeltaTime()*2;
        y = Define::WINDOW_H*8/10 - sin(deltasum)*700;
        if(y >= Define::WINDOW_H*8/10){
            deltasum = 0;
            y = Define::WINDOW_H*8/10;
        }
        if(LRflag){
            x -= 500*Scene::DeltaTime();
        }else{
            x += 500*Scene::DeltaTime();
        }
        if(x > Define::WINDOW_W*9/10){
            LRflag = true;
            if(!shotflag)toggleShotFlag();
        }else if(x < Define::WINDOW_W*1/10){
            LRflag = false;
            if(!shotflag)toggleShotFlag();
        }
    }

    void move2(){
        deltasum += Scene::DeltaTime();
        y = Define::WINDOW_H*8/10 - sin(deltasum)*700;
        if(y >= Define::WINDOW_H*8/10){
            deltasum = 0;
            y = Define::WINDOW_H*8/10;
        }
        if(LRflag){
            x -= 600*Scene::DeltaTime();
        }else{
            x += 600*Scene::DeltaTime();
        }
        if(x > Define::WINDOW_W*9/10){
            LRflag = true;
            if(!shotflag)toggleShotFlag();
        }else if(x < Define::WINDOW_W*1/10){
            LRflag = false;
            if(!shotflag)toggleShotFlag();
        }
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

    void toggleGamemode(){
        Gamemode = !Gamemode;
    }

    bool getShotFlag(){
        return shotflag;
    }
};