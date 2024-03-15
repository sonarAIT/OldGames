struct Description{
    void start(){
		Scene::SetBackground(ColorF(1, 1, 1));
		const Font font(30);
		const Texture g1(Resource(U"File/g1.png"));
		String text[8];
		TextReader reader(Resource(U"File/description.txt"));
        for(int i = 0; i < 8; i++){
		    reader.readLine(text[i]);
        }
		reader.close();
		while (System::Update()) {
			g1.rotated(Scene::Time() * 90_deg * 2).drawAt(700,700);
            for(int i = 0; i < 8; i++){
                font(text[i]).draw(10,10 + i * 50, Palette::Black);
            }
            if (SimpleGUI::Button(U"–ß‚é", Vec2(Define::WINDOW_W / 2 - 70, Define::WINDOW_H - 100))) {
				break;
			}
        }
    }
};