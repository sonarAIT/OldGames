# include "Counter.hpp"
# include "Face.hpp"

struct UpperPanel {
	struct Counter bombcount;
	struct Counter timecount;
	struct Face face;
	Texture tBomb;
	Texture tTime;
	double Time;

	void init(int Gamemode) {
		Time = Scene::Time();
		bombcount.init(0);
		timecount.init(1);
		bombcount.countSet(Define::BOMB_NUM[Gamemode]);
		face.init();
		tBomb = Texture(Resource(U"File/g2.png"));
		tTime = Texture(Resource(U"File/g3.png"));
	}

	void draw(int faceNum,int count) {
		face.draw(faceNum);
		if (count == -1) {
			bombcount.countDown();
		} else if(count == 1) {
			bombcount.countUp();
		}
		timecount.countSet((int)(Scene::Time() - Time));
		bombcount.draw();
		timecount.draw();
		tBomb.scaled(0.9).draw(120, 0);
		tTime.scaled(0.9).draw(Define::WINDOW_W - 230,0);
	}

	double getTime(){
		return Time;
	}
};