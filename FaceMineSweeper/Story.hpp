struct Story{
    void start(){
		Scene::SetBackground(ColorF(1, 1, 1));
		const Font font(28);
		const Texture g1(Resource(U"File/g6.png"));
		String text[20];
		TextReader reader(Resource(U"File/story.txt"));
        for(int i = 0; i < 20; i++){
		    reader.readLine(text[i]);
        }
		reader.close();
		while (System::Update()) {
			g1.scaled(3).drawAt(500,500,ColorF(1.0,0.3));
            for(int i = 0; i < 20; i++){
                if(i != 11){
                    font(text[i]).draw((double)(Define::WINDOW_W/2-text[i].length()*14),10 + i * 45, Palette::Black);
                }else if(i == 11){
                    font(text[i]).draw((double)(Define::WINDOW_W/2-text[i].length()*14),10 + i * 45, Palette::Red);
                }
            }
            if (SimpleGUI::Button(U"–ß‚é", Vec2(Define::WINDOW_W / 2 - 50, Define::WINDOW_H - 90))) {
				break;
			}
        }
    }
};