struct Counter {
private:
	Texture NumTexture[10];
	int Num;
	int LRFlag;

public:
	void init(int LRFlag) {
		for (int i = 0; i < 10; i++) {
			NumTexture[i] = Texture(Resource(U"File/gn{}.png"_fmt(i)));
		}
		this->LRFlag = LRFlag;
	}

	void draw() {
		for (int i = 0; i < 3; i++) {
			int num = Max(0, Num);
			int tmp = (num / (int)pow(10,2 - i)) % 10;
			if (LRFlag == 0) {
				NumTexture[tmp].scaled(0.5).draw(60 * i + 80, 50);
			} else {
				NumTexture[tmp].scaled(0.5).draw(Define::WINDOW_W - (-60 * i + 270), 50);
			}
		}
	}

	void countSet(int Num) {
		this->Num = Num;
	}

	void countUp() {
		Num++;
	}

	void countDown() {
		Num--;
	}
};
