package Man;

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
            case up: y += speed; break;
            case down: y -= speed; break;
            case left: x -= speed; break;
            case right: x += speed; break;
            case up_left: x -= speed; y += speed; break;
            case up_right: x += speed; y += speed; break;
            case down_left: x -= speed; y -= speed; break;
            case down_right: x += speed; y -= speed; break;
        }
    }
}
