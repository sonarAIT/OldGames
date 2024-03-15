struct zData {
private:
	int x;
	int y;
	int m;
	int e;
public:
	void init(int x, int y, int m, int e) {
		this->x = x;
		this->y = y;
		this->m = m;
		this->e = e;
	}

	int getx() {
		return x;
	}

	int gety() {
		return y;
	}

	int getm() {
		return m;
	}

	int gete() {
		return e;
	}
};