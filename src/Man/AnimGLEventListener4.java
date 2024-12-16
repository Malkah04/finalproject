package Man;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package project;

import Texture.TextureReader;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import javax.media.opengl.glu.GLU;

public class AnimGLEventListener4 extends AnimListener {

    public enum Directions{
        up,right,left,down,up_left,up_right,down_left,down_right
    }

    List<Bullet> bullets = new ArrayList<>();
    long lastBulletTime = System.currentTimeMillis();
    Directions direction=Directions.up;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth/2, y = maxHeight/2;
    String []textureNames ={"sprite-sheet_0 (1).png","sprite-sheet_0 (3).png","sprite-sheet_0 (4).png","sprite-sheet_0 (5).png","sprite-sheet_0 (6).png","zombie top 1.png","zombie top 2.png","zombie top 3.png","zombie top 4.png","bullets 1.png", "background.jpg"};
    TextureReader.Texture []texture= new TextureReader.Texture[textureNames.length];
    int []textures = new int[textureNames.length];
    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }




        }
    }
    public void shootBullet() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBulletTime < 200) {
            return;
        }

        int bulletIndex = 9;
        bullets.add(new Bullet(x, y, bulletIndex, direction));
        lastBulletTime = currentTime;
    }

    int cnt=0;
    List<monstor>list=new ArrayList<>();
    int zombieIndex;
    long changeLag = System.currentTimeMillis();
    long startTime = System.currentTimeMillis();
    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        DrawBackground(gl);
        handleKeyPress();
        animationIndex = animationIndex % 4;
        DrawSprite(gl, x, y, animationIndex, 1,direction);// player

        long currentTime=System.currentTimeMillis();
        if (currentTime-changeLag>=1000){
             zombieIndex=(int)(Math.random()*4)+5;
        }
        if(cnt<10) {
            if (currentTime - startTime >= 4000) {
                int rx = (int) (Math.random() * maxWidth) - maxWidth;
                int ry = (int) (Math.random() * maxHeight) - maxHeight;
                list.add(new monstor(rx, ry, zombieIndex, 1, Directions.down));
                startTime = currentTime;
                cnt++;
            }
        }

        for (monstor m : list) {
            moveMonster(m,list);
            DrawSprite(gl, m.x, m.y, zombieIndex, 1, m.dir);
        }
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<monstor> monstersToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);
                continue;
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);


    }
    public boolean isInDirection(Directions direction, float bulletX, float bulletY, float monsterX, float monsterY) {
        switch (direction) {
            case up:
                return monsterY >= bulletY && Math.abs(monsterX - bulletX) <= 20;
            case down:
                return (monsterY <= bulletY && Math.abs(monsterX - bulletX) <= 20 && bulletY - monsterY <= 30);

            case right:
                return monsterX >= bulletX && Math.abs(monsterY - bulletY) <= 20;
            case left:
                return monsterX <= bulletX && Math.abs(monsterY - bulletY) <= 20;
            case up_right:
                return monsterX >= bulletX && monsterY >= bulletY;
            case up_left:
                return monsterX <= bulletX && monsterY >= bulletY;
            case down_right:
                return monsterX >= bulletX && monsterY <= bulletY;
            case down_left:
                return monsterX <= bulletX && monsterY <= bulletY;
            default:
                return false;
        }

    }

    public double sqrdDistance(int x, int y, int x1, int y1){
        return Math.pow(x-x1,2)+Math.pow(y-y1,2);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    public void moveMonster(monstor monster,List<monstor>l) {
        int dx=monster.x-x;
        int dy=monster.y-y;
        int sqrt=dx*dx+dy*dy;
        if (sqrt<100) {
            return;
        }
        for (monstor m : l) {
            if(m!=monster) {
                int dxx=m.x-monster.x;
                int dyy=m.y-monster.y;
                int sqr=dxx*dxx+dyy*dyy;
                if (sqr<100) {
                    if (monster.x>m.x) {
                        monster.x++;
                    } else {
                        monster.x--;
                    }
                    if (monster.y>m.y) {
                        monster.y++;
                    } else {
                        monster.y--;
                    }
                }
            }
        }

        if (Math.abs(monster.y - y) < 2) {
            if (monster.x > x) {
                monster.dir = Directions.right;
                monster.x -= 1;
            } else if (monster.x < x) {
                monster.dir = Directions.left;
                monster.x += 1;
            }
        } else if (Math.abs(monster.x - x) < 2) {
            if (monster.y > y) {
                monster.dir = Directions.up;
                monster.y -= 1;
            } else if (monster.y < y) {
                monster.dir = Directions.down;
                monster.y += 1;
            }
        } else {
            if (monster.x > x && monster.y > y) {
                monster.dir = Directions.up_right;
                monster.x -= 1;
                monster.y -= 1;
            } else if (monster.x < x && monster.y > y) {
                monster.dir = Directions.up_left;
                monster.x += 1;
                monster.y -= 1;
            } else if (monster.x > x && monster.y < y) {
                monster.dir = Directions.down_right;
                monster.x -= 1;
                monster.y += 1;
            } else if (monster.x < x && monster.y < y) {
                monster.dir = Directions.down_left;
                monster.x += 1;
                monster.y += 1;
            }
        }
    }


    public void DrawSprite(GL gl,int x, int y, int index, float scale,Directions dir){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On
        int angle=0;
        switch (dir){
            case up: angle=0;break;
            case down:angle=180;break;
            case left:angle=90;break;
            case right:angle=-90;break;
            case up_left:angle=45;break;
            case up_right:angle=-45;break;
            case down_left:angle=135;break;
            case down_right:angle=-135;break;
            default:angle=0;
        }

        gl.glPushMatrix();
        gl.glTranslated( x/(maxWidth/2.0) - 0.9, y/(maxHeight/2.0) - 0.9, 0);
        gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
        gl.glScaled(0.1*scale, 0.1*scale, 1);
        //System.out.println(x +" " + y);
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

    public void drawMonstor(GL gl ,int x,int y,int index,float scale,Directions dir){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated( x/(maxWidth/2.0) - 0.9, y/(maxHeight/2.0) - 0.9, 0);
        gl.glScaled(0.1*scale, 0.1*scale, 1);
        //System.out.println(x +" " + y);
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


    public void DrawBackground(GL gl){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length-1]);
        // Turn Blending On

        gl.glPushMatrix();
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

    /*
     * KeyListener
     */

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_LEFT) && isKeyPressed(KeyEvent.VK_DOWN)) {
            if (x > 0) {
                x--;
            }
            if (y > 0) {
                y--;
            }
            direction = Directions.down_left;
            animationIndex++;

        } else if (isKeyPressed(KeyEvent.VK_RIGHT) && isKeyPressed(KeyEvent.VK_DOWN)) {
            if (x < maxWidth - 10) {
                x++;
            }
            if (y > 0) {
                y--;
            }
            direction = Directions.down_right;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_LEFT) && isKeyPressed(KeyEvent.VK_UP)) {
            if (x > 0) {
                x--;
            }
            if (y < maxHeight - 10) {
                y++;
            }
            direction = Directions.up_left;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_RIGHT) && isKeyPressed(KeyEvent.VK_UP)){
            if (x < maxWidth-10) {
                x++;
            }
            if (y < maxHeight-10) {
                y++;
            }
            direction=Directions.up_right;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (x > 0) {
                x--;
            }
            direction=Directions.left;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (x < maxWidth-10) {
                x++;
            }
            direction=Directions.right;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (y > 0) {
                y--;
            }
            direction=Directions.down;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_UP)) {
            if (y < maxHeight-10) {
                y++;
            }
            direction=Directions.up;
            animationIndex++;
        }
    }

    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
        // don't care
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        shootBullet();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}