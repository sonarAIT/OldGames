struct Title {
	int start() {
		Window::Resize(Define::WINDOW_W, Define::WINDOW_H);
		Scene::SetBackground(ColorF(1, 1, 1));
		const Font font(60);
		const Texture g1(Resource(U"File/g1.png"));
		String pass, input = U"";
		TextReader reader(Resource(U"File/pass.txt"));
		reader.readLine(pass);
		reader.close();
		while (System::Update()) {
			font(U"��}�C���X�C�[�p�[").draw(250, Define::WINDOW_H / 2 - 200, Palette::Black);
			g1.scaled(1 + Periodic::Sine0_1(1s) * 2).rotated(Scene::Time() * 90_deg * 2).drawAt(Define::WINDOW_W / 2 + 200, Define::WINDOW_H / 2 + 100);
			g1.scaled(1 + Periodic::Sine0_1(1s) * 2).rotated(Scene::Time() * 90_deg * 2).drawAt(Define::WINDOW_W / 2 - 200, Define::WINDOW_H / 2 + 100);
			if (SimpleGUI::Button(U"�ʂ邢", Vec2(Define::WINDOW_W / 2 - 50, Define::WINDOW_H - 300))) {
				return 1;
			}
			if (SimpleGUI::Button(U"�ӂ�", Vec2(Define::WINDOW_W / 2 - 50, Define::WINDOW_H - 250))) {
				return 2;
			}
			if (SimpleGUI::Button(U"����", Vec2(Define::WINDOW_W / 2 - 50, Define::WINDOW_H - 200))) {
				return 3;
			}
			if (SimpleGUI::Button(U"���[��", Vec2(Define::WINDOW_W / 2 - 50, Define::WINDOW_H - 150))) {
				return 5;
			}
			if (SimpleGUI::Button(U"�X�g�[���[", Vec2(Define::WINDOW_W / 2 - 70, Define::WINDOW_H - 100))) {
				return 6;
			}
			TextInput::UpdateText(input);
			if (input == pass) {
				return 4;
			}
		}
		return 0;
	}
};