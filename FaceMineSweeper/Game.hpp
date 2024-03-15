struct Game {
private:
	int Gamemode;
	int EndFlag;
	int Score;
	struct UpperPanel upperpanel;
	struct LastPanel lastpanel;
	struct Game2* game2;
	Grid<int> FaceNum;
	Texture FaceTexture[6];
	Texture BG;
	Texture DieFace;
	Texture Halo;

public:
	void init(int tmp) {
		Gamemode = tmp;
		EndFlag = 0;
		Score = -1;
		upperpanel.init(Gamemode);
		lastpanel.init(Gamemode);
		game2 = nullptr;
		FaceNum = Grid<int>(Define::CELL_NUM_X[tmp], Define::CELL_NUM_Y[tmp], 0);
		for (int i = 0; i < Define::CELL_NUM_X[tmp]; i++) {
			for (int j = 0; j < Define::CELL_NUM_Y[tmp]; j++) {
				FaceNum[i][j] = Random(4);
			}
		}
		for (int i = 1; i < 6; i++) {
			FaceTexture[i] = Texture(Resource(U"File/ge{}.png"_fmt(i)));
		}
		BG = Texture(Resource(U"File/bg1.jpg"));
		DieFace = Texture(Resource(U"File/g5.png"));
		Halo = Texture(Resource(U"File/g9.png"));
	}

	void loop() {
		//goto Debug;
		struct zData tmp;
		AudioAsset(U"m2").play();
		while (EndFlag == 0 && System::Update()) {
			Scene::SetBackground(ColorF(1.0, 1.0, 1.0));
			while (System::Update()) {
				tmp = lastpanel.draw();
				if (tmp.getx() == -1 || tmp.gety() == -1) {
					upperpanel.draw(1, tmp.getm());
				} else {
					upperpanel.draw(FaceNum[tmp.getx()][tmp.gety()], tmp.getm());
				}
				if (tmp.gete() != 0) {
					break;
				}
				if(lastpanel.clearDecision()){
					Score = (int)(Scene::Time() - upperpanel.getTime());
					EndFlag = 1;
					break;
				}
			}
			AudioAsset(U"m2").stop();
			if (EndFlag == 0 && tmp.gete() != 0) {
				AudioAsset(U"m3").play();
				int RectSize = 840 / Define::CELL_NUM_X[Gamemode];
				int x = (int)(80 + tmp.getx() * RectSize) + RectSize / 2;
				int y = (int)(150 + tmp.gety() * RectSize) + RectSize / 2;
				int e = tmp.gete() - 10;
				double size = 0.1;
				double t = Scene::Time();
				while (System::Update() && Scene::Time() - t < 5) {
					lastpanel.draw2();
					upperpanel.draw(3, 0);
					FaceTexture[e].scaled(size).rotated(Scene::Time() * 90_deg).drawAt(x, y);
					size += 0.15;
					if (x > Define::WINDOW_W / 2) {
						x--;
					} else if (x < Define::WINDOW_W / 2) {
						x++;
					}
					if (y > Define::WINDOW_H / 2) {
						y--;
					} else if (x < Define::WINDOW_H / 2) {
						y++;
					}
				}
				switch (tmp.gete() - 10) {
				case 1:
					game2 = new Game21();
					break;
				case 2:
					game2 = new Game22();
					break;
				case 3:
					game2 = new Game23();
					break;
				case 4:
					game2 = new Game24();
					break;
				case 5:
					game2 = new Game25();
					break;
				default:
					game2 = new Game21();
				}
				//game2 = new Game25();
				game2->init(Gamemode == 4 ? 1 : 0);
				int GameEndFlag = 0;
				while (System::Update() && GameEndFlag == 0) {
					GameEndFlag = game2->draw();
				}
				delete game2;
				game2 = nullptr;
				if(GameEndFlag == 2){
					Font font(50);
					AudioAsset(U"m4").play();
					AudioAsset(U"s6").play();
					while(System::Update()){
						BG.draw(0,0);
						int x = 745+Random(10);
						int y = 745+Random(10);
						DieFace.drawAt(x,y);
						Halo.drawAt(x,y-100);
						font(U"ゲームオーバー！w").drawAt(Define::WINDOW_W/2,Define::WINDOW_H/2-100);
						font(U"Zキーでタイトルに戻る").drawAt(Define::WINDOW_W/2,Define::WINDOW_H/2-20);
						if(KeyZ.pressed()){
							break;
						}
					}
					AudioAsset(U"m4").stop();
					return;
				}
				AudioAsset(U"m2").play();
				upperpanel.draw(3, -1);
				lastpanel.killed(tmp.getx(),tmp.gety());
			}else if(EndFlag == 1){
				if(Gamemode < 4){
					Font font(70);
					Font font2(30);
					Texture tex(Resource(U"File/g1.png"));
					String pass;
					TextReader reader(Resource(U"File/pass.txt"));
					reader.readLine(pass);
					reader.close();
					double ArrayX[6];
					double ArrayY[6];
					double ArrayAngle[6];
					double ArrayAngleNum[6] = {3.0,5.0,7.0,10.0,5.0,7.0};
					double Scale = 1.0;
					for(int i = 0; i < 6; i++){
						ArrayX[i] = Random(0.0,(double)Define::WINDOW_W);
						ArrayY[i] = Random(250.0,(double)Define::WINDOW_H);
						ArrayAngle[i] = Random(0.0,359.0);
					}
					AudioAsset(U"m5").play();
					while(System::Update()){
						Scale += Scene::DeltaTime()/5;
						for(int i = 1; i < 6; i++){
							ArrayX[i] += Random(-25,25);
							ArrayY[i] += Random(-25,25);
							ArrayX[i] = Max(ArrayX[i],0.0);
							ArrayX[i] = Min(ArrayX[i],(double)Define::WINDOW_W);
							ArrayY[i] = Max(ArrayY[i],0.0);
							ArrayY[i] = Min(ArrayY[i],(double)Define::WINDOW_H);
							ArrayAngle[i] += ArrayAngleNum[i];
							FaceTexture[i].scaled(Scale).rotated(ArrayAngle[i] * Math::Pi / 180).drawAt(ArrayX[i],ArrayY[i]);
						}
						ArrayX[0] += Random(-25,25);
						ArrayY[0] += Random(-25,25);
						ArrayX[0] = Max(ArrayX[0],0.0);
						ArrayX[0] = Min(ArrayX[0],(double)Define::WINDOW_W);
						ArrayY[0] = Max(ArrayY[0],0.0);
						ArrayY[0] = Min(ArrayY[0],(double)Define::WINDOW_H);
						ArrayAngle[0] += ArrayAngleNum[0];
						tex.scaled(Scale).rotated(ArrayAngle[0] * Math::Pi / 180).drawAt(ArrayX[0],ArrayY[0]);
						font(U"おめでとう！！！！！").drawAt(Define::WINDOW_W/2,Define::WINDOW_H/2-100,RandomColor());
						font(U"あなたのスコア:{}秒"_fmt(Score)).drawAt(Define::WINDOW_W/2,Define::WINDOW_H/2,RandomColor());
						font(U"Zキーでタイトルに戻る").drawAt(Define::WINDOW_W/2,Define::WINDOW_H/2+100,RandomColor());
						if(Gamemode >= 2){
							font2(U"タイトル画面で{}と入力すると高難易度モードが遊べるよ！"_fmt(pass)).drawAt(Define::WINDOW_W/2+100,Define::WINDOW_H-50,Palette::Black);
						}
						if(KeyZ.pressed()){
							AudioAsset(U"m5").stop();
							return;
						}
					}
				}else{
					Debug:
					AudioAsset(U"m2").stop();
					for (int i = 1; i < 6; i++){
							AudioAsset::Release(U"m{}"_fmt(i));
					}
					for(int i = 6; i < 8; i++){
						AudioAsset::Preload(U"m{}"_fmt(i));
					}
					struct TEnding tending;
					tending.start();
					for (int i = 6; i < 8; i++){
						AudioAsset::Release(U"m{}"_fmt(i));
					}
					for (int i = 1; i < 6; i++){
						AudioAsset::Preload(U"m{}"_fmt(i));
					}
					return;
				}
			}
		}
	}
};