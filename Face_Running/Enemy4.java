class Enemy4 extends Enemy{
    private double JumpTime;
    private final int GROUND_Y;

    Enemy4(int GROUND_Y,int Ra){
        this.GROUND_Y = GROUND_Y;
        JumpTime = Ra == 0 ? 0 : 1.5;
    }

    void moveMove(double DeltaTime){
        setX2(-360*DeltaTime);
        int JUMP_HEIGHT = 500;
        setY((int)(GROUND_Y-JUMP_HEIGHT*Math.sin(JumpTime)));
        JumpTime += 0.36*DeltaTime;
        if(GROUND_Y-JUMP_HEIGHT*Math.sin(JumpTime) >= GROUND_Y){
            JumpTime = 0;
            setY(GROUND_Y);
        }
    }
}