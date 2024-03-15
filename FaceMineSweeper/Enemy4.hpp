struct Enemy4 : Enemy{
    double size = 1;

    void move(){
        switch((int)angle){
            case 0:
            x += 750*Scene::DeltaTime();
            break;
            
            case 1:
            y += 750*Scene::DeltaTime();
            break;

            case 2:
            x -= 750*Scene::DeltaTime();
            break;

            case 3:
            y -= 750*Scene::DeltaTime();
        }
        size += 0.05;
    }

    void draw(){
        if(!redtoggle){
            texture.scaled(size).drawAt((int)x,(int)y);
        }else{
            texture.scaled(size).drawAt((int)x,(int)y,Palette::Red);
            redtoggle = false;
        }
    }

    double getSize(){
        return size;
    }
};