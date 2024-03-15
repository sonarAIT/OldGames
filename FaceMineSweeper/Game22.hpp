struct Game22 : Game2 {
private:
	Array<Enemy2> EnemyArray;
	//Array<EnemyBullet1> EnemyBulletArray;

	struct Enemy2 enemy;
	int HP;
	const double ENEMY_BULLETTIME = 0.1;
	int countmax;
	int row;
	int mrow;
	int rowcount;
	double enemybullettimer;
	double delay;
	bool redtoggle;
	
public:
	int draw() {
		draw0();
		if(Scene::DeltaTime() + enemybullettimer > ENEMY_BULLETTIME && Scene::Time() - delay > 1){
			enemybullettimer = 0;
			for(int i = 0; i < 7; i++){
				if(row != i && mrow != i){
					enemy.setXY(1100,i*160+80,0);
					EnemyArray << enemy;
				}
				if(mrow == i){
					mrow = -1;
				}
			}
			rowcount++;
			if(rowcount == countmax){
				rowcount = 0;
				mrow = row;
				if(Random(1) > 0.5){
					if(row == 0){
						row = 1;
					}else{
						row--;
					}
				}else{
					if(row == 6){
						row = 5;
					}else{
						row++;
					}
				}
			}
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
				if(abs(it2->getX() - it->getX()) < Define::HIT_INT && abs(it2->getY() - it->getY()) < Define::HIT_INT){
					AudioAsset(U"s2").stop();
					AudioAsset(U"s3").stop();
					AudioAsset(U"s4").stop();
					AudioAsset(U"s{}"_fmt(2+Random(2))).play();
					it2->hit();
					HP--;
					redtoggle = true;
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
			it->draw(redtoggle);
			if(abs(it->getX() - ShipX) < Define::BIG_SHIP_HIT_INT && abs(it->getY() - ShipY) < Define::BIG_SHIP_HIT_INT){
				GameEndFlag = 2;
			}
			if(it->getX() < -200 || it->getX() > Define::WINDOW_W + 200 || it->getY() < -200 || it->getY() > Define::WINDOW_H + 200){
				it = EnemyArray.erase(it);
			}else{
				it++;
			}
		}
		if(redtoggle)redtoggle = false;
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
					it->draw(false);
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
		Texture tex(Resource(U"File/ge2.png"));
		enemy.init(tex,39393939);
		HP = 100;
		if(Gamemode == 1){
			countmax = 5;
		}else if(Gamemode == 0){
			countmax = 10;
		}
	 	row = 3;
		mrow = -1;
		rowcount = 0;
		enemybullettimer = 0;
		delay = Scene::Time();
		redtoggle = false;
	}

};