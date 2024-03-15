class Founder_Face extends ImageAnimationC{
    private final int GROUND_Y;
    private final int SPEED = 960;
    private double JumpTime = 0;
    private int JumpFlag = 0;

    Founder_Face(int GROUND_Y){
        this.GROUND_Y = GROUND_Y;
    }

    void left(double DeltaTime){
        if(getXX() >= 0) setX2(SPEED*-1*DeltaTime);
    }

    void right(double DeltaTime){
        if(getXX() <= 1500) setX2(SPEED*DeltaTime);
    }

    int getJumpFlag(){
        return JumpFlag;
    }

    void setJumpFlag(int Flag){
        JumpFlag = Flag;
        if(Flag == 1){
            JumpTime = 0.01;
        }
    }

    void Jump(double DeltaTime){
        if(getJumpFlag() == 1){
            int JUMP_HEIGHT = 500;
            setY((int)(GROUND_Y-JUMP_HEIGHT*Math.sin(JumpTime)));
            JumpTime += 3.6*DeltaTime;
            if(GROUND_Y-JUMP_HEIGHT*Math.sin(JumpTime) >= GROUND_Y){
                JumpFlag = 0;
                JumpTime = 0;
                setY(GROUND_Y);
            }
        }else if(getJumpFlag() == 2){
            setY2(2400*DeltaTime);
            if(getYY() >= GROUND_Y){
                JumpFlag = 0;
                JumpTime = 0;
                setY(GROUND_Y);
            }
        }
    }

    void Five(){
        setX(getXX()-getXX()%5);
        setY(getYY()-getYY()%5);
    }
}