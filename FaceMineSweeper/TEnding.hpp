struct TEnding {
    //世界一のクソコードはこちらでありんす！！！（このstructはまねしないでね）

	void start() {
		Scene::SetBackground(ColorF(0.0, 0.0, 0.0));
        AudioAsset(U"s13").setVolume(0.8);
        TextReader reader(Resource(U"File/sex.txt"));
		TextReader reader2(Resource(U"File/waa.txt"));
		TextReader reader3(Resource(U"File/woo.txt"));
		String str[4];
		String str2[4];
		String str3[7];
		String str4[7];
		String str5[20];
        String amari;
		for (int i = 0; i < 4; i++) {
			reader.readLine(str[i]);
			reader.readLine(str2[i]);
		}
		reader.close();
		for (int i = 0; i < 7; i++) {
			reader2.readLine(str3[i]);
			reader2.readLine(str4[i]);
		}
        reader2.readLine(amari);
		reader2.close();
		for (int i = 0; i < 20; i++) {
			reader3.readLine(str5[i]);
		}
		Texture bg2(Resource(U"File/bg2.png"));
		Texture bg3(Resource(U"File/bg3.jpg"));
		Texture bg4(Resource(U"File/bg4.jpg"));
		Texture bg5(Resource(U"File/bg5.png"));
		Texture bg6(Resource(U"File/bg6.png"));
		Texture face(Resource(U"File/g1.png"));
		Texture moji(Resource(U"File/font.png"));
		Texture tree(Resource(U"File/tree.png"));
		Texture pole(Resource(U"File/pole.png"));
		Texture fuck(Resource(U"File/g6.png"));
        Texture happy(Resource(U"File/happy.png"));
		Font font(40, Typeface::Bold);
        Font font2(30,Typeface::Bold);
		for (int i = 0; i < 4 && System::Update(); i++) {
			double d = Scene::Time();
			bg2.resized(Define::WINDOW_W).draw(0, 0);
			while (Scene::Time() - d < 3 && System::Update()) {
				int length = (int)((Scene::Time() - d) / 0.05);
				bg2.resized(Define::WINDOW_W).draw(0, 0);
				font(str[i]).draw(50, 780);
				font(str2[i].substr(0, length)).draw(50, 875);
			}
            bg2.resized(Define::WINDOW_W).draw(0, 0);
        }
		AudioAsset(U"m6").play();
		if(System::Update())AudioAsset(U"s8").play();
		double x = 100.0;
		double y = 1100.0;
		double skyx = 0;
		bool xtoggle = false;
		double treex = 800.0;
		double treey = 800.0;
		double interval = 2.0;
		double timer = 3;
		double starttime = Scene::Time();
		while (System::Update()) {
			if (Scene::Time() - starttime < 3) {
                bg3.drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2 - 100);
                Rect(0, 800, Define::WINDOW_W, 200).draw(Color(190, 123, 84));
				y -= 30 * Scene::DeltaTime();
				xtoggle = !xtoggle;
				face.scaled(3.5).drawAt(x + (xtoggle ? -5 : 5), y);
				tree.scaled(0.25).drawAt(treex, treey);
			} else if (Scene::Time() - starttime < 15) {
                bg3.drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2 - 100);
                Rect(0, 800, Define::WINDOW_W, 200).draw(Color(190, 123, 84));
				y -= 30 * Scene::DeltaTime();
				xtoggle = !xtoggle;
				face.scaled(3.5).drawAt(x + (xtoggle ? -5 : 5), y);
				moji.drawAt(Define::WINDOW_W / 2, 900);
				tree.scaled(0.25).drawAt(treex, treey);
			} else if (Scene::Time() - starttime < 17) {
                bg3.drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2 - 100);
                Rect(0, 800, Define::WINDOW_W, 200).draw(Color(190, 123, 84));
				face.scaled(3.5).drawAt(x, y);
				tree.scaled(0.25).drawAt(treex, treey);
			} else if (Scene::Time() - starttime < 30) {
				double delta = Scene::DeltaTime();
				if (timer + delta > interval) {
					interval = Max(interval - 0.2, 0.4);
					AudioAsset(U"s9").stop();
					AudioAsset(U"s9").play();
					timer = 0;
				} else {
					timer += delta;
				}
				x += delta * 100 / interval;
				treex -= delta * 500 / interval;
				if (treex < -100) {
					treex = 1100;
				}
				skyx -= delta * 10;
                bg3.drawAt(Define::WINDOW_W / 2 + skyx, Define::WINDOW_H / 2 - 100);
                Rect(0, 800, Define::WINDOW_W, 200).draw(Color(190, 123, 84));
				tree.scaled(0.25).drawAt(treex, treey);
				face.scaled(3.5).drawAt(x, y);
			} else {
				break;
			}
		}
		treex = 1100;
		treey = 940;
		timer = 0;
		int countmax = 100;
		int count = 0;
		bool treebool = false;
        while (System::Update()) {
			double delta = Scene::DeltaTime();
			if (timer + delta > 0.4) {
				AudioAsset(U"s9").stop();
				AudioAsset(U"s9").play();
				timer = 0;
			} else {
				timer += delta;
			}
			if (KeySpace.down()) {
				count++;
			}
			treex += delta * 1250;
			if (treex > 1100) {
				treex = -100;
				if(count > -10)count--;
				treebool = RandomBool();
			}
			bg4.scaled(3).drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2);
			fuck.scaled(0.75).drawAt(100, 860, Palette::Black);
			face.scaled(1.5).drawAt(900 - (800 / countmax) * count, 880, Palette::Black);
			if (treebool) {
				tree.scaled(0.25).drawAt(treex, treey, Palette::Black);
			} else {
				pole.scaled(0.25).drawAt(treex, treey, Palette::Black);
			}
			font(U"スペースキーを連打してぜんしょうくんに追いつけ！").drawAt(Define::WINDOW_W / 2, 100);
			if (count == countmax) {
				break;
			}
		}
		int NumAmiDabutsu[11] = { 3,2,2,2,3,2,1,2,1,1,1 };
		AudioAsset(U"s9").stop();
		AudioAsset(U"m6").stop();
		AudioAsset(U"m7").play();
		int vibration = 0;
		double black = 0.0;
		bg4.scaled(3).drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2);
		if(System::Update())AudioAsset(U"s10").play();
		bg4.scaled(3).drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2);
		for (int i = 0; i < 4 && System::Update(); i++) {
			double d = Scene::Time();
			bg5.resized(Define::WINDOW_W).draw(0, 0);
			while (Scene::Time() - d < 5 && System::Update()) {
				int length = (int)((Scene::Time() - d) / 0.05);
                if(vibration < 10){
                    bg5.resized(Define::WINDOW_W).draw(0, vibration%2==0?-5:5);
                    vibration++;
                }else{
				    bg5.resized(Define::WINDOW_W).draw(0, 0);
                }
				if(str3[i].length() == 3){
					font2(str3[i]).draw(70, 790);
				}else{
					font2(str3[i]).draw(20, 790);
				}
				font2(str4[i].substr(0, length)).draw(50, 875);
			}
            bg5.resized(Define::WINDOW_W).draw(0, 0);
		}
        bg6.resized(Define::WINDOW_W).draw(0, 0);
        while (black < 0.5 && System::Update()){
            black = Min(black + Scene::DeltaTime()/10, 0.5);
			bg6.resized(Define::WINDOW_W).draw(0, 0);
			Rect(0, 0, Define::WINDOW_W, Define::WINDOW_H).draw(ColorF(0.0, 0.0, 0.0, black));
		}
		int wa = 0;
		bg6.resized(Define::WINDOW_W).draw(0, 0);
		Rect(0, 0, Define::WINDOW_W, Define::WINDOW_H).draw(ColorF(0.0, 0.0, 0.0, black));
		for (int i = 0; i < 11 && System::Update(); i++) {
            int alphamode = 0;
            double alpha = 0.0;
            double d = -3939;
            bg6.resized(Define::WINDOW_W).draw(0, 0);
            Rect(0, 0, Define::WINDOW_W, Define::WINDOW_H).draw(ColorF(0.0, 0.0, 0.0, black));
            while (alphamode != 3 && System::Update()){
                bg6.resized(Define::WINDOW_W).draw(0, 0);
				Rect(0, 0, Define::WINDOW_W, Define::WINDOW_H).draw(ColorF(0.0, 0.0, 0.0, black));
                if(alphamode == 0){
                    alpha += Scene::DeltaTime()/5;
                    if(alpha >= 1.0){
                        alphamode = 1;
                        alpha = 1.0;
                        d = Scene::Time();
                    }
                } else if (alphamode == 1 && Scene::Time() - d >= 5) {
                    alphamode = 2;
                } else if (alphamode == 2){
                    alpha -= Scene::DeltaTime()/5;
                    if(alpha <= 0.0){
                        alphamode = 3;
                        alpha = 0.0;
                    }
                }
				if (NumAmiDabutsu[i] == 3) {
					font2(str5[wa]).drawAt(Define::WINDOW_W / 2, 400,ColorF(1.0,1.0,1.0,alpha));
					font2(str5[wa+1]).drawAt(Define::WINDOW_W / 2, 500, ColorF(1.0, 1.0, 1.0, alpha));
					font2(str5[wa+2]).drawAt(Define::WINDOW_W / 2, 550, ColorF(1.0, 1.0, 1.0, alpha));
				} else if (NumAmiDabutsu[i] == 2) {
					for (int j = 0; j < 2; j++) {
						font2(str5[wa + j]).drawAt(Define::WINDOW_W / 2, j * 50 + 475,ColorF(1.0,1.0,1.0,alpha));
					}
				} else if (NumAmiDabutsu[i] == 1) {
                    font2(str5[wa]).drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2, ColorF(1.0, 1.0, 1.0, alpha));
                }
			}
            wa += NumAmiDabutsu[i];
            bg6.resized(Define::WINDOW_W).draw(0, 0);
            Rect(0, 0, Define::WINDOW_W, Define::WINDOW_H).draw(ColorF(0.0, 0.0, 0.0, black));
        }
        bg6.resized(Define::WINDOW_W).draw(0, 0);
        Rect(0, 0, Define::WINDOW_W, Define::WINDOW_H).draw(ColorF(0.0, 0.0, 0.0, black));
        while (black > 0.0 && System::Update()){
            black = Max(black - Scene::DeltaTime()/10, 0.0);
			bg6.resized(Define::WINDOW_W).draw(0, 0);
			Rect(0, 0, Define::WINDOW_W, Define::WINDOW_H).draw(ColorF(0.0, 0.0, 0.0, black));
		}
        vibration = 0;
		bg6.resized(Define::WINDOW_W).draw(0, 0);
		for (int i = 4; i < 7 && System::Update(); i++) {
            double d = Scene::Time();
            if(i == 4)AudioAsset(U"s12").playOneShot();
            if(i == 6)AudioAsset(U"s13").playOneShot();
			bg6.resized(Define::WINDOW_W).draw(0, 0);
			while (Scene::Time() - d < 5 && System::Update()) {
                int length = (int)((Scene::Time() - d) / 0.05);
                if(i == 6){
                    happy.resized(Define::WINDOW_H).drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2 + (vibration % 2 == 0 ? -5 : 5));
                    vibration++;
                } else {
                    bg5.resized(Define::WINDOW_W).draw(0, 0);
                }
                if(str3[i].length() == 3){
					font2(str3[i]).draw(70, 790);
				}else{
					font2(str3[i]).draw(20, 790);
				}
                font2(str4[i].substr(0, length)).draw(50, 875);
                if(i == 6)font2(amari.substr(0, Max(length-31,0))).draw(50,900);
            }
            if(i == 6){
                happy.resized(Define::WINDOW_H).drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2 + (vibration % 2 == 0 ? -5 : 5));
                vibration++;
            } else {
                bg5.resized(Define::WINDOW_W).draw(0, 0);
            }
        }
        black = 0;
        happy.resized(Define::WINDOW_H).drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2 + (vibration % 2 == 0 ? -5 : 5));
        vibration++;
        while (System::Update() && black != 1.0) {
            happy.resized(Define::WINDOW_H).drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2 + (vibration % 2 == 0 ? -5 : 5));
            vibration++;
			font2(str3[6]).draw(20, 790);
			font2(str4[6]).draw(50, 875);
            font2(amari).draw(50,900);
            black = Min(black+Scene::DeltaTime()/10,1.0);
            Rect(0,0,Define::WINDOW_W,Define::WINDOW_H).draw(ColorF(0.0,0.0,0.0,black));
        }
        black = 0;
        while(System::Update()){
            font(U"THE END").drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2,ColorF(1.0,1.0,1.0,black));
            if(!AudioAsset(U"m7").isPlaying()){
                font2(U"Zキーでタイトルに戻る").drawAt(Define::WINDOW_W / 2, Define::WINDOW_H / 2 + 200);
                if(KeyZ.pressed()){
                    return;
                }
            }else{
                black = Min(1.0,black + Scene::DeltaTime()/10);
            }
        }
	}
};