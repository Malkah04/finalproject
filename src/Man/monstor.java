package Man;

import Man.AnimGLEventListener4;

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
    monstor(int x, int y, AnimGLEventListener4.Directions dir){
        this.x=x;
        this.y=y;
        this.dir=dir;
    }
    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getScaleX() {
        return scale;
    }

    public void setScale(float scaleX) {
        this.scale = scale;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int textureIndex) {
        this.index = index;
    }

    public void drawSprite(GL gl, int[] textures) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);
        gl.glPushMatrix();
        gl.glTranslated(x / (100f / 2.0) - 0.9, y / (100f / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }
}

