struct Button {
private:
	int Num = 0;
	bool Open = false;
	bool Flag = false;
	bool Died = false;

public:
	void init(int Num){
		this->Num = Num;
	}

	void toggleFlag() {
		Flag = !Flag;
	}

	void toggleOpen() {
		Open = true;
	}

	void kill() {
		Died = true;
	}

	int getNum() {
		return Num;
	}

	bool getOpen() {
		return Open;
	}

	bool getFlag() {
		return Flag;
	}

	bool getDied() {
		return Died;
	}
};