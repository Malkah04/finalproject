package Man;

public class Blood {
    int x, y;
    long showTime;
    boolean isVisible;

    public Blood(int x, int y) {
        this.x = x;
        this.y = y;
        this.showTime = System.currentTimeMillis();
        this.isVisible = true;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - showTime > 1000;
    }
}
