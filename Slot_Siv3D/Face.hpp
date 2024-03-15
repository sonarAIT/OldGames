class Face {
	int RotateToggle = 0;
	int x;
	int y;
	double Angle = 0;

public:

	Face(int xx, int yy) {
		x = xx;
		y = yy;
	};

	int getX() {
		return x;
	};

	int getY() {
		return y;
	};

	void setX(int num) {
		x = num;
	};

	void setY(int num) {
		y = num;
	};

	void setX2(int num) {
		x += num;
	};

	void setY2(int num) {
		y += num;
	};

	int getRotateToggle() {
		return RotateToggle;
	};

	void setRotateToggle(int num) {
		RotateToggle = num;
	};

	int getAngle() {
		return (int)Angle;
	};

	void setAngle(double num) {
		Angle = num;
	};

	void addAngle(double num) {
		Angle += num;
	};

};
