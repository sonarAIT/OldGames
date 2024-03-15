struct Game2 {
	Array<ShipBullet> BulletArray;
	Texture ShipTex;
	Texture BulletTex;
	Texture EnemyExplosion[6];
	Texture ShipExplosion[6];
	struct ShipBullet BulletInstance;
	double bullettimer;
	double ShipX;
	double ShipY;
	int GameEndFlag;
	int Gamemode;

	void init0() {
		Scene::SetBackground(ColorF(0.8, 0.9, 1.0));
		ShipTex = Texture(Resource(U"File/g1.png"));
		BulletTex = Texture(Resource(U"File/g7.png"));
		for (int i = 0; i < 6; i++) {
			EnemyExplosion[i] = Texture(Resource(U"File/gbr{}.png"_fmt(i+1)));
			ShipExplosion[i] = Texture(Resource(U"File/gbb{}.png"_fmt(i+1)));
		}
		BulletInstance = ShipBullet();
		BulletInstance.init(0,0);
		bullettimer = 0;
		ShipX = Define::WINDOW_W / 2 - 200;
		ShipY = Define::WINDOW_H / 2;
		GameEndFlag = 0;
	}

	void draw0() {
		double delta = Scene::DeltaTime();
		if (KeyLeft.pressed()) {
			ShipX = Max(0.0, ShipX - delta * Define::SHIP_SPEED);
		}
		if (KeyRight.pressed()) {
			ShipX = Min((double)Define::WINDOW_W, ShipX + delta * Define::SHIP_SPEED);
		}
		if (KeyUp.pressed()) {
			ShipY = Max(0.0, ShipY - delta * Define::SHIP_SPEED);
		}
		if (KeyDown.pressed()) {
			ShipY = Min((double)Define::WINDOW_H, ShipY + delta * Define::SHIP_SPEED);
		}
		ShipTex.drawAt(ShipX, ShipY);
		if (KeyZ.pressed()) {
			if (delta + bullettimer > Define::BULLET_TIME) {
				AudioAsset(U"s1").playOneShot();
				bullettimer = 0;
				BulletInstance.init((int)ShipX+50,(int)ShipY);
				BulletArray << BulletInstance;
			} else {
				bullettimer += delta;
			}
		}else{
			bullettimer = 1;
		}
	}

	virtual void init(int Gamemode) = 0;
	virtual int draw() = 0;
};