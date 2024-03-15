class Enemy3 extends Enemy{
    private double JumpTime = 0;
    private final int GROUND_Y;

    Enemy3(int GROUND_Y){
        this.GROUND_Y = GROUND_Y;
    }

    void moveMove(double DeltaTime){
        setX2(-180*DeltaTime);
        int JUMP_HEIGHT = 500;
        setY((int)(GROUND_Y-JUMP_HEIGHT*Math.sin(JumpTime)));
        JumpTime += 3.6*DeltaTime;
        if(GROUND_Y-JUMP_HEIGHT*Math.sin(JumpTime) >= GROUND_Y){
            JumpTime = 0;
            setY(GROUND_Y);
        }
    }
}