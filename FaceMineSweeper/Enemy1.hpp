struct Enemy1 : Enemy{
    void move(){}

    void draw(){
        if(redtoggle){
            texture.drawAt((int)x,(int)y);
        }else{
            texture.drawAt((int)x,(int)y,Palette::Red);
            redtoggle = !redtoggle;
        }
    }
};