package Man;


public class Player {
    int x, y, index;
    float scale1;

    private AnimGLEventListener4.Directions direction;
    public Player(int x, int y, int index, float scale1, AnimGLEventListener4.Directions direction) {
        this.x = x;
        this.y = y;
        this.index = index;
        this.scale1= scale1;
        this.direction = direction;
    }
}