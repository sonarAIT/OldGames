struct Game23 : Game2 {
private:
	Array<Enemy3> EnemyArray;
	Array<EnemyBullet2> EnemyBulletArray;

	struct EnemyBullet2 EnemyBulletInstance;
	double delay;
	int cnt;

public:
	int draw() {
		draw0();
		if(EnemyArray[0].getShotFlag() && cnt == 10){
			cnt = 0;
		}
		if(EnemyArray[0].getShotFlag() && cnt < 10){
			EnemyBulletInstance.setXY((int)EnemyArray[0].getX(),(int)EnemyArray[0].getY(),atan2(ShipY-EnemyArray[0].getY(),ShipX-EnemyArray[0].getX()));
			EnemyBulletArray << EnemyBulletInstance;
			EnemyBulletInstance.setXY((int)EnemyArray[0].getX(),(int)EnemyArray[0].getY(),atan2(ShipY-EnemyArray[0].getY(),ShipX-EnemyArray[0].getX())-0.872665);
			EnemyBulletArray << EnemyBulletInstance;
			EnemyBulletInstance.setXY((int)EnemyArray[0].getX(),(int)EnemyArray[0].getY(),atan2(ShipY-EnemyArray[0].getY(),ShipX-EnemyArray[0].getX())+0.872665);
			EnemyBulletArray << EnemyBulletInstance;
			cnt++;
			if(cnt == 10){
				EnemyArray[0].toggleShotFlag();
			}
		}
		draw2();
		return GameEndFlag;
	}

	void draw2(){
		for (auto it = BulletArray.begin(); it != BulletArray.end();) {
			it->move();
			bool flag = true;
			for(auto& enemy : EnemyArray){
				if(abs(enemy.getX() - it->getX()) < Define::HIT_INT && abs(enemy.getY() - it->getY()) < Define::HIT_INT){
					AudioAsset(U"s2").stop();
					AudioAsset(U"s3").stop();
					AudioAsset(U"s4").stop();
					AudioAsset(U"s{}"_fmt(2+Random(2))).play();
					enemy.hit();
					it = BulletArray.erase(it);
					flag = false;
					break;
				}
			}
			if(flag){
				BulletTex.scaled(0.4).rotated(it->getAngle() * Math::Pi / 180).drawAt((int)it->getX(),(int)it->getY());
				it++;
			}
		}
		if (BulletArray) {
			if (BulletArray.front().getX() > Define::WINDOW_W + 100) {
				BulletArray.pop_front();
			}
		}
		for(auto it = EnemyArray.begin(); it != EnemyArray.end();){
			it->move();
			it->draw();
			if(abs(it->getX() - ShipX) < Define::SHIP_HIT_INT && abs(it->getY() - ShipY) < Define::SHIP_HIT_INT){
				GameEndFlag = 2;
			}
			it++;
		}
		if(EnemyArray[0].getHP() <= 0){
			GameEndFlag = 1;
		}
		for(auto it = EnemyBulletArray.begin(); it != EnemyBulletArray.end();){
			it->move();
			it->draw();
			if(abs(it->getX() - ShipX) < Define::SHIP_HIT_INT && abs(it->getY() - ShipY) < Define::SHIP_HIT_INT){
				GameEndFlag = 2;
			}
			if(it->getX() < -100 || it->getX() > Define::WINDOW_W + 100 || it->getY() < -100 || it->getY() > Define::WINDOW_H + 100){
				it = EnemyBulletArray.erase(it);
			}else{
				it++;
			}
		}

		if(GameEndFlag == 1){
			AudioAsset(U"m3").stop();
			AudioAsset(U"s7").play();
			double time = Scene::Time();
			while(System::Update() && Scene::Time() - time < 7){
				draw0();
				for (auto it = BulletArray.begin(); it != BulletArray.end();) {
					it->move();
					BulletTex.scaled(0.4).rotated(it->getAngle() * Math::Pi / 180).drawAt((int)it->getX(),(int)it->getY());
					it++;
				}
				if (BulletArray) {
					if (BulletArray.front().getX() > Define::WINDOW_W + 100) {
						BulletArray.pop_front();
					}
				}
				if(Scene::Time() - time < 0.6){
					for(auto it = EnemyArray.begin(); it != EnemyArray.end();){
						EnemyExplosion[Min((int)((Scene::Time()-time)/0.1),5)].scaled(1.5).drawAt(it->getX(),it->getY());
						it++;
					}
				}
				for(auto it = EnemyBulletArray.begin(); it != EnemyBulletArray.end();){
					it->move();
					it->draw();
					if(it->getX() < -100 || it->getX() > Define::WINDOW_W + 100 || it->getY() < -100 || it->getY() > Define::WINDOW_H + 100){
						it = EnemyBulletArray.erase(it);
					}else{
						it++;
					}
				}
			}
		}else if(GameEndFlag == 2){
			AudioAsset(U"m3").stop();
			AudioAsset(U"s5").play();
			double time = Scene::Time();
			while(System::Update() && Scene::Time() - time < 5){
				for (auto it = BulletArray.begin(); it != BulletArray.end();) {
					it->move();
					BulletTex.scaled(0.4).rotated(it->getAngle() * Math::Pi / 180).drawAt((int)it->getX(),(int)it->getY());
					it++;
				}
				if (BulletArray) {
					if (BulletArray.front().getX() > Define::WINDOW_W + 100) {
						BulletArray.pop_front();
					}
				}
				for(auto it = EnemyArray.begin(); it != EnemyArray.end();){
					it->move();
					it->draw();
					it++;
				}
				if(Scene::Time() - time < 0.6){
					ShipExplosion[Min((int)((Scene::Time()-time)/0.1),5)].scaled(1.5).drawAt(ShipX,ShipY);
				}
				for(auto it = EnemyBulletArray.begin(); it != EnemyBulletArray.end();){
					it->move();
					it->draw();
					if(it->getX() < -100 || it->getX() > Define::WINDOW_W + 100 || it->getY() < -100 || it->getY() > Define::WINDOW_H + 100){
						it = EnemyBulletArray.erase(it);
					}else{
						it++;
					}
				}
			}
		}
	}

	void init(int Gamemode) {
		init0();
		this->Gamemode = Gamemode;
		Texture tex(Resource(U"File/ge3.png"));
		struct Enemy3 enemy;
		enemy.init(tex,20);
		enemy.setXY(800,800,0);
		if(Gamemode == 1)enemy.toggleGamemode();
		EnemyArray << enemy;
		tex = Texture(Resource(U"File/g8.png"));
		EnemyBulletInstance.init(tex);
		cnt = 10;
		delay = Scene::Time();
	}
	
};