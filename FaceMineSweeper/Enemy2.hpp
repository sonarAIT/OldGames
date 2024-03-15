struct Enemy2 : Enemy{
    void move(){
        x -= 750*Scene::DeltaTime();
    }

    void draw(bool redtoggle){
        if(!redtoggle){
            texture.drawAt((int)x,(int)y);
        }else{
            texture.drawAt((int)x,(int)y,Palette::Red);
        }
    }

    void draw(){

    }
};