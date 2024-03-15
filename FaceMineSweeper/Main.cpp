//イヤアアァァァァァァァァァ！！！！！見ないで！！！！！！
# include  <Siv3D.hpp> // OpenSiv3D v0.4.1
# include "Define.hpp"
# include "TEnding.hpp"
# include "Title.hpp"
# include "Button.hpp"
# include "zData.hpp"
# include "UpperPanel.hpp"
# include "LastPanel.hpp"
# include "ShipBullet.hpp"
# include "EnemyBullet.hpp"
# include "EnemyBullet1.hpp"
# include "EnemyBullet2.hpp"
# include "Enemy.hpp"
# include "Enemy1.hpp"
# include "Enemy2.hpp"
# include "Enemy3.hpp"
# include "Enemy4.hpp"
# include "Enemy5.hpp"
# include "Game2.hpp"
# include "Game21.hpp"
# include "Game22.hpp"
# include "Game23.hpp"
# include "Game24.hpp"
# include "Game25.hpp"
# include "Game.hpp"
# include "Description.hpp"
# include "Story.hpp"
//include、ソート順に注意！

void Main() {
	Window::SetTitle(U"顔マインスイーパー");
	struct Title* title;
	struct Game* game;
	struct Description* description;
	struct Story* story;
	for(int i = 1; i < 8; i++){
		AudioAsset::Register(U"m{}"_fmt(i),Resource(U"File/m{}.mp3"_fmt(i)));
		if(i < 4){
			AudioAsset(U"m{}"_fmt(i)).setLoop(true);
		}
		if(i < 6){
			AudioAsset::Preload(U"m{}"_fmt(i));
		}
	}
	for(int i = 0; i < 14; i++){
		AudioAsset::Register(U"s{}"_fmt(i),Resource(U"File/s{}.wav"_fmt(i)),AssetParameter::LoadImmediately());
	}
	AudioAsset(U"m1").play();
	while (System::Update()) {
		int tmp;
		title = new Title();
		tmp = title->start();
		delete title;
		title = nullptr;
		for(int i = 0; i < 3; i++)System::Update();
		if(tmp == 5){
			description = new Description();
			description->start();
			delete description;
			description = nullptr;
		}else if(tmp == 6){
			story = new Story();
			story->start();
			delete story;
			story = nullptr;
		}else{
			AudioAsset(U"m1").stop();
			game = new Game();
			game->init(tmp);
			game->loop();
			delete game;
			game = nullptr;
			AudioAsset(U"m1").play();
		}
	}
}
