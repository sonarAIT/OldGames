struct ShipBullet {
private:
	double x;
	double y;
	double angle;

public:
	void init(int x, int y) {
		this->x = x;
		this->y = y;
		angle = 0;
	}

	double getX() {
		return x;
	}

	double getY(){
		return y;
	}

	double getAngle(){
		return angle;
	}

	void move(){
		x += Scene::DeltaTime() * Define::BULLET_SPEED;
		angle += Define::BULLET_ROTATE_SPEED;
	}
};