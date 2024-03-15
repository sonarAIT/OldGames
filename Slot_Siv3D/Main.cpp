# include <Siv3D.hpp> // OpenSiv3D v0.4.1
# include"Face.hpp"
using namespace std;

const int WINDOW_W = 1000;
const int WINDOW_H = 800;
const Size sizea(WINDOW_W, WINDOW_H);
Face FaceArray[3] = {
	Face(260,340),
	Face(480,340),
	Face(700,340)
};
String TextArray[8] = {
	U"←",
	U"↓",
	U"→",
	U"↑ : スタート",
	U"↑ : もう一回",
	U"おめでとう！！！",
	U"顔スロット",
	U"スタート"
};
int Flag = 0;
int StopNum = 0;
int ZeroNum = 0;
int LineToggle = 0;
int LineSum = 0;

int otherPressed(int i) {
	if (FaceArray[i].getRotateToggle() == 1) {
		FaceArray[i].setRotateToggle(2);
		StopNum++;
		if (FaceArray[i].getAngle() % 45 < 23) {
			//Print << U"{0},{1}"_fmt(FaceArray[i].getAngle(), (FaceArray[i].getAngle() - FaceArray[i].getAngle() % 45) % 360);
			FaceArray[i].setAngle((FaceArray[i].getAngle() - FaceArray[i].getAngle() % 45) % 360);
		} else {
			//Print << U"{0},{1}"_fmt(FaceArray[i].getAngle(), (FaceArray[i].getAngle() - FaceArray[i].getAngle() % 45 + 45) % 360);
			FaceArray[i].setAngle(((FaceArray[i].getAngle() - FaceArray[i].getAngle() % 45) + 45) % 360);
		}
		if (FaceArray[i].getAngle() == 0) ZeroNum++;
		if (StopNum == 3) {
			Flag = 2;
		}
		if (StopNum == 3) {
			if (ZeroNum == 3) {
				return 3;
			} else if (FaceArray[0].getAngle() == FaceArray[1].getAngle() && FaceArray[1].getAngle() == FaceArray[2].getAngle()) {
				return 2;
			} else {
				return 1;
			}
		}
		return 0;
	}
	return -1;
};

void Main() {
	const Font font(60);
	constexpr Rect rect(WINDOW_W / 2 - 800 / 2, 200, 800, 200);
	Audio AudioArray[6];
	Texture Face_Tex;
	Texture Line1;
	Texture Line2;
	int GameFlag = 0;
	Window::SetTitle(TextArray[6]);
	Scene::Resize(sizea);
	Window::Resize(sizea);
	Scene::SetBackground(ColorF(1.0, 1.0, 1.0));

	for (int i = 0; i < 6; i++) {
		AudioArray[i] = Audio(Resource(U"File/s{0}.mp3"_fmt(i)));
	}
	Face_Tex = Texture(Resource(U"File/gazou.png"));
	Line1 = Texture(Resource(U"File/sen1.png"));
	Line2 = Texture(Resource(U"File/sen2.png"));

	while (System::Update() && GameFlag == 0) {
		font(TextArray[6]).draw(WINDOW_W / 2 - 800 / 2 + 250, 250, ColorF(0.25));
		rect.drawFrame(0, 5, Palette::Red);
		if (SimpleGUI::Button(TextArray[7], Vec2(WINDOW_W / 2 - 100, WINDOW_H / 2 + 150), 200)) {
			GameFlag = 1;
		}
	}
	AudioArray[0].play();
	while (GameFlag == 1 && System::Update()) {
		if (KeyUp.down()) {
			if (Flag == 0) {
				Flag = 1;
				AudioArray[1].play();
				for (int i = 0; i < 3; i++) {
					FaceArray[i].setRotateToggle(1);
				}
			} else if (Flag == 2) {
				Flag = 0;
				StopNum = 0;
				ZeroNum = 0;
				for (int i = 0; i < 3; i++) {
					FaceArray[i].setRotateToggle(0);
					FaceArray[i].setX(i * 220 + 260);
					FaceArray[i].setY(340);
				}
				AudioArray[0].play();
				for (int i = 3; i < 6; i++) {
					AudioArray[i].stop();
				}
			}
		}
		int tmp = -1;
		if (KeyLeft.down()) {
			tmp = otherPressed(0);
		}
		if (KeyDown.down()) {
			tmp = otherPressed(1);
		}
		if (KeyRight.down()) {
			tmp = otherPressed(2);
		}
		if (tmp != -1) {
			if(tmp != 0)AudioArray[0].stop();
			AudioArray[2 + tmp].stop();
			AudioArray[2 + tmp].play();
			//Print << U"get";
		}

		for (int i = 0; i < 3; i++) {
			if (ZeroNum == 3) {
				FaceArray[i].setX2((int)((rand() % 15 - 7) * Scene::DeltaTime() * 150));
				FaceArray[i].setY2((int)((rand() % 15 - 7) * Scene::DeltaTime() * 150));
			}
			if (FaceArray[i].getRotateToggle() == 1) {
				FaceArray[i].addAngle(500 * Scene::DeltaTime());
				Face_Tex.rotated(FaceArray[i].getAngle() * Math::Pi / 180).drawAt(FaceArray[i].getX(), FaceArray[i].getY());
			} else {
				Face_Tex.rotated(FaceArray[i].getAngle() * Math::Pi / 180).drawAt(FaceArray[i].getX(), FaceArray[i].getY());
			}
		}
		//Print << Scene::DeltaTime();
		//Print << U"{0},{1}"_fmt(FaceArray[0].getAngle(), (FaceArray[0].getAngle() - FaceArray[0].getAngle() % 45) % 360);
		for (int i = 0; i < 3; i++) {
			font(TextArray[i]).draw(i * 225 + 250, 450, Palette::Black);
		}
		if (Flag != 2) {
			font(TextArray[3]).draw(350, 600, Palette::Black);
		} else {
			font(TextArray[4]).draw(350, 600, Palette::Black);
		}

		if (ZeroNum == 3) {
			font(TextArray[5]).draw(300, 350, Palette::Red);
			if (LineToggle == 0) {
				Line1.draw(0, 0);
			} else {
				Line2.draw(0, 0);
			}
			LineSum += (int)(Scene::DeltaTime() * 1000);
			if (LineSum >= 30) {
				LineSum = 0;
				LineToggle = 1 - LineToggle;
			}
		}
	}
}

//
// = アドバイス =
// Debug ビルドではプログラムの最適化がオフになります。
// 実行速度が遅いと感じた場合は Release ビルドを試しましょう。
// アプリをリリースするときにも、Release ビルドにするのを忘れないように！
//
// 思ったように動作しない場合は「デバッグの開始」でプログラムを実行すると、
// 出力ウィンドウに詳細なログが表示されるので、エラーの原因を見つけやすくなります。
//
// = お役立ちリンク =
//
// OpenSiv3D リファレンス
// https://siv3d.github.io/ja-jp/
//
// チュートリアル
// https://siv3d.github.io/ja-jp/tutorial/basic/
//
// よくある間違い
// https://siv3d.github.io/ja-jp/articles/mistakes/
//
// サポートについて
// https://siv3d.github.io/ja-jp/support/support/
//
// Siv3D Slack (ユーザコミュニティ) への参加
// https://siv3d.github.io/ja-jp/community/community/
//
// 新機能の提案やバグの報告
// https://github.com/Siv3D/OpenSiv3D/issues
//
