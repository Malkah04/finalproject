package Man;
import java.awt.*;
class Bullet {
    int x, y;
    int textureIndex;
    AnimGLEventListener4.Directions direction;
    float speed = 5.0f;

    public Bullet(int x, int y, int textureIndex, AnimGLEventListener4.Directions direction) {
        this.x = x;
        this.y = y;
        this.textureIndex = textureIndex;
        this.direction = direction;
    }

    public void move() {
        switch (direction) {
            case up: y += (int)speed; break;
            case down: y -= (int)speed; break;
            case left: x -=(int)speed; break;
            case right: x += (int)speed; break;
            case up_left: x -= (int)speed; y += (int)speed; break;
            case up_right: x += (int)speed; y += (int)speed; break;
            case down_left: x -= (int)speed; y -= (int)speed; break;
            case down_right: x += (int)speed; y -= (int)speed; break;
        }
    }
}
