struct LastPanel {
private:
	Grid<Button> ButtonGrid;
	Texture NumTexture[9];
	Texture tFlag;
	Texture dieFace;
	int Gamemode;
	int CELL_X;
	int CELL_Y;
	int BOMB_NUM;
	int CellCount;
	double RectSize[5];
	bool iFlag;

public:
	void init(int Gamemode) {
		this->Gamemode = Gamemode;
		CELL_X = Define::CELL_NUM_X[Gamemode];
		CELL_Y = Define::CELL_NUM_Y[Gamemode];
		BOMB_NUM = Define::BOMB_NUM[Gamemode];
		CellCount = CELL_X * CELL_Y;
		struct Button button;
		ButtonGrid = Grid<Button>(Define::CELL_NUM_X[Gamemode], Define::CELL_NUM_Y[Gamemode], button);
		for (int i = 1; i < 9; i++) {
			NumTexture[i] = Texture(Resource(U"File/gg{}.png"_fmt(i)));
		}
		tFlag = Texture(Resource(U"File/g4.png"));
		dieFace = Texture(Resource(U"File/g5.png"));
		RectSize[0] = 0;
		for (int i = 1; i < 5; i++) {
			if (CELL_X == 0) {
				CELL_X = 1;
			}
			RectSize[i] = 840 / CELL_X;
		}
		iFlag = false;
	}

	void bombinit(int cx, int cy) {
		for (int i = 0; i < BOMB_NUM; i++) {
			int x = Random(CELL_X - 1);
			int y = Random(CELL_Y - 1);
			if (ButtonGrid[x][y].getNum() == 0 && !(x == cx && y == cy)) {
				ButtonGrid[x][y].init(Random(4) + 11);
			} else {
				i--;
			}
		}
		for (int i = 0; i < CELL_X; i++) {
			for (int j = 0; j < CELL_Y; j++) {
				if (ButtonGrid[i][j].getNum() == 0) {
					int count = 0;
					for(int k = -1; k < 2; k++){
						if(i+k >= 0 && i+k < CELL_X){
							for(int l = -1; l < 2; l++){
								if(j+l >= 0 && j+l < CELL_Y){
									if(ButtonGrid[i+k][j+l].getNum() >= 10){
										count++;
									}
								}
							}
						}
					}
					ButtonGrid[i][j].init(count);
				}
			}
		}
	}

	int clicked(int i,int j) {
		open(i, j);
		if (ButtonGrid[i][j].getNum() >= 10) {
			return ButtonGrid[i][j].getNum();
		}
		return 0;
	}

	void debug() {
		for (int i = 0; i < CELL_X; i++) {
			String s;
			for (int j = 0; j < CELL_Y; j++) {
				s += U"{},"_fmt(ButtonGrid[j][i].getNum() >= 10?9:ButtonGrid[j][i].getNum());
			}
			Print << s;
		}
	}

	void open(int i,int j) {
		if (!ButtonGrid[i][j].getOpen() && !ButtonGrid[i][j].getFlag()) {
			ButtonGrid[i][j].toggleOpen();
			if (ButtonGrid[i][j].getNum() == 0) {
				for(int k = -1; k < 2; k++){
					if(i+k >= 0 && i+k < CELL_X){
						for(int l = -1; l < 2; l++){
							if(j+l >= 0 && j+l < CELL_Y){
								if(ButtonGrid[i+k][j+l].getNum() < 10){
									open(i+k,j+l);
								}
							}
						}
					}
				}
			}
		}
	}

	void killed(int i, int j) {
		ButtonGrid[i][j].kill();
		// for(int k = -1; k < 2; k++){
		// 	if(i+k >= 0 && i+k < CELL_X){
		// 		for(int l = -1; l < 2; l++){
		// 			if(j+l >= 0 && j+l < CELL_Y){
		// 				if(ButtonGrid[i+k][j+l].getNum() < 10 && !(i == 0 && j == 0)){
		// 					ButtonGrid[i+k][j+l].init(Max(ButtonGrid[i+k][j+l].getNum()-1,0));
		// 				}
		// 			}
		// 		}
		// 	}
		// }
	}

	struct zData draw() {
		int x = -1;
		int y = -1;
		int m = 0;
		int e = 0;
		for (int i = 0; i < CELL_X; i++) {
			for (int j = 0; j < CELL_Y; j++) {
				if (ButtonGrid[i][j].getOpen()) {
					Rect Rect((int)(80 + i * RectSize[Gamemode]), (int)(150 + j * RectSize[Gamemode]), (int)(RectSize[Gamemode]), (int)(RectSize[Gamemode]));
					if (ButtonGrid[i][j].getNum() >= 1 && ButtonGrid[i][j].getNum() <= 8) {
						Rect(NumTexture[ButtonGrid[i][j].getNum()]).draw(Palette::White);
					} else if (ButtonGrid[i][j].getNum() >= 10 && ButtonGrid[i][j].getDied()) {
						Rect(dieFace).draw(Palette::White);
					} else {
						Rect.draw(Palette::White);
					}
				} else {
					Rect BigRect((int)(80 + i * RectSize[Gamemode]), (int)(150 + j * RectSize[Gamemode]), (int)(RectSize[Gamemode]), (int)(RectSize[Gamemode]));
					Rect SmallRect((int)(80 + RectSize[Gamemode] * 0.1 + i * RectSize[Gamemode]), (int)(150 + RectSize[Gamemode] * 0.1 + j * RectSize[Gamemode]), (int)(RectSize[Gamemode] * 0.8), (int)(RectSize[Gamemode] * 0.8));
					if (BigRect.leftPressed()) {
						x = i;
						y = j;
					} else if (BigRect.leftReleased()) {
						if (!iFlag) {
							bombinit(i, j);
							iFlag = true;
							AudioAsset(U"s0").stop();
							AudioAsset(U"s0").play();
						}
						if (!ButtonGrid[i][j].getFlag()) {
							e = clicked(i, j);
							AudioAsset(U"s0").stop();
							AudioAsset(U"s0").play();
						}
						x = i;
						y = j;
					} else if (BigRect.rightPressed()) {
						x = i;
						y = j;
					} else if (BigRect.rightReleased()) {
						x = i;
						y = j;
						ButtonGrid[i][j].toggleFlag();
						if (ButtonGrid[i][j].getFlag()) {
							m = -1;
						} else {
							m = 1;
						}
					} else if (BigRect.mouseOver()) {
						x = i;
						y = j;
						BigRect.draw(Palette::Lightgrey);
						if (ButtonGrid[i][j].getFlag()) {
							SmallRect(tFlag).draw(Palette::White);
						} else {
							SmallRect.draw(Palette::White);
						}
					} else {
						BigRect.draw(Palette::Gray);
						if (ButtonGrid[i][j].getFlag()) {
							SmallRect(tFlag).draw(Palette::Lightgrey);
						} else {
							SmallRect.draw(Palette::Lightgrey);
						}
					}
				}
			}
		}
		struct zData re;
		re.init(x, y, m, e);
		return re;
	}

	void draw2() {
		for (int i = 0; i < CELL_X; i++) {
			for (int j = 0; j < CELL_Y; j++) {
				if (ButtonGrid[i][j].getOpen()) {
					Rect Rect((int)(80 + i * RectSize[Gamemode]), (int)(150 + j * RectSize[Gamemode]), (int)(RectSize[Gamemode]), (int)(RectSize[Gamemode]));
					if (ButtonGrid[i][j].getNum() >= 1 && ButtonGrid[i][j].getNum() <= 8) {
						Rect(NumTexture[ButtonGrid[i][j].getNum()]).draw(Palette::White);
					} else if(ButtonGrid[i][j].getNum() >= 10 && ButtonGrid[i][j].getDied()){
						Rect(dieFace).draw(Palette::White);
					} else {
						Rect.draw(Palette::White);
					}
				} else {
					Rect BigRect((int)(80 + i * RectSize[Gamemode]), (int)(150 + j * RectSize[Gamemode]), (int)(RectSize[Gamemode]), (int)(RectSize[Gamemode]));
					Rect SmallRect((int)(80 + RectSize[Gamemode] * 0.1 + i * RectSize[Gamemode]), (int)(150 + RectSize[Gamemode] * 0.1 + j * RectSize[Gamemode]), (int)(RectSize[Gamemode] * 0.8), (int)(RectSize[Gamemode] * 0.8));
					BigRect.draw(Palette::Gray);
					if (ButtonGrid[i][j].getFlag()) {
						SmallRect(tFlag).draw(Palette::Lightgrey);
					} else {
						SmallRect.draw(Palette::Lightgrey);
					}
				}
			}
		}
	}

	bool clearDecision(){
		int cnt = 0;
		int cnt2 = 0;
		for(int i = 0; i < CELL_X; i++){
			for(int j = 0; j < CELL_Y; j++){
				if((ButtonGrid[i][j].getFlag() && ButtonGrid[i][j].getNum() >= 10) || ButtonGrid[i][j].getDied()){
					cnt++;
				}else if(ButtonGrid[i][j].getOpen()){
					cnt2++;
				}
			}
		}
		if(cnt == BOMB_NUM && cnt2 == CELL_X * CELL_Y - BOMB_NUM){
			return true;
		}else{
			return false;
		}
	}
};