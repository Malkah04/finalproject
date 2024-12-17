package Man;

import javax.media.opengl.GL;

public class monstor {
    int x,y,index;
    float scale;
    AnimGLEventListener4.Directions dir;
    monstor(int x, int y, int index, float scale, AnimGLEventListener4.Directions dir){
        this.x=x;
        this.y=y;
        this.index=index;
        this.scale=scale;
        this.dir=dir;
    }

}

