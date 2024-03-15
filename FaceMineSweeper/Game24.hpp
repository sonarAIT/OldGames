struct Game24 : Game2 {
private:
	Array<Enemy4> EnemyArray;
	//Array<EnemyBullet1> EnemyBulletArray;

	struct Enemy4 enemy;
	int HP;
	int soeji;
	int startX[4] = {-100,500,1100,500};
	int startY[4] = {500,-100,500,1100};
	double ENEMY_BULLETTIME;
	double enemybullettimer;
	double delay;

public:
	int draw() {
		draw0();
		if(Scene::DeltaTime() + enemybullettimer > ENEMY_BULLETTIME && Scene::Time() - delay > 1){
			enemybullettimer = 0;
			enemy.setXY(startX[soeji],startY[soeji],soeji);
			soeji = (soeji+1)%4;
			EnemyArray << enemy;
		}else{
			enemybullettimer += Scene::DeltaTime();
		}
		draw2();
		return GameEndFlag;
	}

	void draw2(){
		for (auto it = BulletArray.begin(); it != BulletArray.end();) {
			it->move();
			bool flag = true;
			for(auto it2 = EnemyArray.begin(); it2 != EnemyArray.end();){
				if(abs(it2->getX() - it->getX()) < Define::HIT_INT*it2->getSize() && abs(it2->getY() - it->getY()) < Define::HIT_INT*it2->getSize()){
					AudioAsset(U"s2").stop();
					AudioAsset(U"s3").stop();
					AudioAsset(U"s4").stop();
					AudioAsset(U"s{}"_fmt(2+Random(2))).play();
					it2->hit();
					HP--;
					it = BulletArray.erase(it);
					flag = false;
					break;
				}
				it2++;
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
			if(abs(it->getX() - ShipX) < Define::BIG_SHIP_HIT_INT*it->getSize() && abs(it->getY() - ShipY) < Define::BIG_SHIP_HIT_INT*it->getSize()){
				GameEndFlag = 2;
			}
			if(it->getX() < -200 || it->getX() > Define::WINDOW_W + 200 || it->getY() < -200 || it->getY() > Define::WINDOW_H + 200){
				it = EnemyArray.erase(it);
			}else{
				it++;
			}
		}
		if(HP <= 0){
			GameEndFlag = 1;
		}
		/*for(auto it = EnemyBulletArray.begin(); it != EnemyBulletArray.end();){
			it->move();
			it->draw();
			if(abs(it->getX() - ShipX) < Define::SHIP_HIT_INT && abs(it->getY() - ShipY) < Define::SHIP_HIT_INT){
				GameEndFlag = 2;
			}
			if(it->getX() < -200 || it->getX() > Define::WINDOW_W + 200 || it->getY() < -200 || it->getY() > Define::WINDOW_H + 200){
				it = EnemyBulletArray.erase(it);
			}else{
				it++;
			}
		}*/

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
				/*for(auto it = EnemyBulletArray.begin(); it != EnemyBulletArray.end();){
					it->move();
					it->draw();
					if(it->getX() < -100 || it->getX() > Define::WINDOW_W + 100 || it->getY() < -100 || it->getY() > Define::WINDOW_H + 100){
						it = EnemyBulletArray.erase(it);
					}else{
						it++;
					}
				}*/
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
				/*for(auto it = EnemyBulletArray.begin(); it != EnemyBulletArray.end();){
					it->move();
					it->draw();
					if(it->getX() < -100 || it->getX() > Define::WINDOW_W + 100 || it->getY() < -100 || it->getY() > Define::WINDOW_H + 100){
						it = EnemyBulletArray.erase(it);
					}else{
						it++;
					}
				}*/
			}
		}
	}

	void init(int Gamemode) {
		init0();
		this->Gamemode = Gamemode;
		enemy.init(Texture(Resource(U"File/ge4.png")),39393939);
		HP = 25;
		if(Gamemode == 0){
			ENEMY_BULLETTIME = 1;
		}else if(Gamemode == 1){
			ENEMY_BULLETTIME = 0.5;
		}
		enemybullettimer = 1;
		delay = Scene::Time();
		soeji = 0;
	}
};