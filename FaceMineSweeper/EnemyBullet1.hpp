struct EnemyBullet1 : EnemyBullet{
    int Speed = 1;

    void move(){
        x += cos(angle)*750*Speed*Scene::DeltaTime();
        y += sin(angle)*750*Speed*Scene::DeltaTime();
    }

    void draw(){
        texture.scaled(0.5).drawAt((int)x,(int)y);
    }

    void speedToggle(){
        Speed = 2;
    }
};