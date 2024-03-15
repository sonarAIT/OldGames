struct EnemyBullet2 : EnemyBullet {
	void move() {
		x += cos(angle) * 300 * Scene::DeltaTime();
		y += sin(angle) * 300 * Scene::DeltaTime();
	}

	void draw() {
		texture.scaled(0.5).drawAt((int)x, (int)y);
	}
};