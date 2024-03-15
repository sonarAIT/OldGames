struct Enemy : EnemyBullet{
protected:
    int HP;
    bool redtoggle;

public:
    void init(Texture texture,int HP){
        this->texture = texture;
        this->HP = HP;
        redtoggle = false;
    }

    void hit(){
        HP--;
        redtoggle = !redtoggle;
    }

    int getHP(){
        return HP;
    }

};