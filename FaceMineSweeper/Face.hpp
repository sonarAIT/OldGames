struct Face {
	Texture FaceTexture[5];

	void init() {
		for (int i = 0; i < 5; i++) {
			FaceTexture[i] = Texture(Resource(U"File/gf{}.png"_fmt(i)));
		}
	}

	void draw(int tmp) {
		FaceTexture[tmp].scaled(0.85).draw(Define::WINDOW_W/2-51, 10);
	}
};