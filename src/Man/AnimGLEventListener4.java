package Man;
import Texture.TextureReader;
import sounddd.voice;

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

    private int targetX;
    private int targetY;
    private boolean isMoving = false;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth/2, y = maxHeight/2;
    boolean gameOver = false;
    int gameDuration = 20000;
    long gameStartTime;
    int initialTime = 20;
    double powerHealth = 0;
    double distance = 0.9;

    String[] textureNames ={"sprite-sheet_0 (1).png","sprite-sheet_0 (3).png","sprite-sheet_0 (4).png"
            ,"sprite-sheet_0 (5).png","sprite-sheet_0 (6).png","zombie top 1.png","zombie top 2.png"
            ,"zombie top 3.png","zombie top 4.png","bullets 1.png","Upper_G.png","Upper_A.png","Upper_M.png","Upper_E.png",
            "Upper_O.png","Upper_V.png","Upper_R.png","Sand clock png.png","0.png","1.png","2.png","3.png","4.png","5.png","6.png","7.png","8.png","9.png","Blood).png","RTS Status Indicator24.jpg","VIDA_8_0.png","VIDA_5.png","VIDA_3.png", "background.jpg"};


    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];
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
                System.out.println();
                e.printStackTrace();
            }
            if(!gameOver)playMusic(0);
            else playMusic(4);

        }
        gameStartTime = System.currentTimeMillis();
    }
    public void shootBullet() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBulletTime < 300) {
            return;
        }

        int bulletIndex = 9;
        bullets.add(new Bullet(x, y, bulletIndex, direction));
        SoundEf(2);//ok
        lastBulletTime = currentTime;
    }
    voice vic=new voice();

    int cnt=0;
    List<monstor>list=new ArrayList<>();
    List<Blood> bloodList = new ArrayList<>();

    int zombieIndex;
    long changeLag = System.currentTimeMillis();
    long startTime = System.currentTimeMillis();

    boolean isCollided = false;
    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        DrawBackground(gl);
        if(!gameOver){
        DrawSprite(gl,2,90,17,0.5f,Directions.up);
        handleKeyPress();
        handleMouse();
        animationIndex = animationIndex % 4;
        DrawSprite(gl, x, y, animationIndex, 1,direction);// player

        long currentTime=System.currentTimeMillis();
        if (currentTime-changeLag>=200){
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
            double dist = sqrdDistance(x,y,m.x,m.y);
            double radii = Math.pow(0.5*0.1*maxHeight+0.5*0.1*maxHeight,2);
            isCollided = dist<=83;
            System.out.println(isCollided + ", "+ dist + ", "+ radii);
            if(isCollided){
                powerHealth = powerHealth+0.005;
                isCollided=false;
            }
        }
        List<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x < 0 || bullet.x > maxWidth || bullet.y < 0 || bullet.y > maxHeight) {
                bulletsToRemove.add(bullet);
            }
            //remove monstor
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    bulletsToRemove.add(bullet);
                    list.remove(m);
                    bloodList.add(new Blood(m.x, m.y));
                    break;
                }
            }
        }
        bullets.removeAll(bulletsToRemove);


            if (currentTime - changeLag >= 1000) {
                if (initialTime > 0) {
                    initialTime--;
                }
                changeLag = currentTime;
            }
             drawTimer(gl,initialTime);

            if(currentTime - gameStartTime > gameDuration && !list.isEmpty()){
              gameOver = true;
            }
        }
            else {
                drawGameOver(gl);
        }



        //apeare blood
        for (Blood blood : bloodList) {
            if (blood.isVisible && !blood.isExpired()) {
                DrawSprite(gl, blood.x, blood.y, 28, 1.0f, Directions.up);
            } else {
                blood.isVisible = false;
            }
        }

        // the basic of powerHealth
        PowerHealth(gl , 0.7,0.85,0.13,29);
        // the green of powerHealth
        PowerHealth(gl , 0.7+powerHealth,0.85,0.12,30);
        if(powerHealth < 0.5 && powerHealth >0.2) {
            PowerHealth(gl , 0.7+powerHealth,0.85,0.12,31);
        }
        if(powerHealth < 0.7 && powerHealth >0.5) {
            PowerHealth(gl , 0.7+powerHealth,0.85,0.12,32);
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
        if(cnt<=10&&monster.x<=maxWidth&&monster.y<=maxHeight&&monster.x>=0&&monster.y>=0)SoundEf(1);
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
        int angle;
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
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }
    public void drawTimer(GL gl, int time) {
        int tens = time / 10;
        int ones = time % 10;

        DrawSprite(gl, 8, 90, textures.length - 11 + (tens-1), 0.5f, Directions.up);

        DrawSprite(gl, 14, 90, textures.length - 11 + (ones-1), 0.5f, Directions.up);

    }

    public void drawGameOver(GL gl){
        int startX = 5;
        int y = 50;
        DrawSprite(gl,startX,y,10,1,Directions.up);
        DrawSprite(gl,startX+10,y,11,1, Directions.up);
        DrawSprite(gl,startX+20,y,12,1,Directions.up);
        DrawSprite(gl,startX+30,y,13,1,Directions.up);

        DrawSprite(gl,startX+50,y,14,1,Directions.up);
        DrawSprite(gl,startX+60,y,15,1,Directions.up);
        DrawSprite(gl,startX+70,y,13,1,Directions.up);
        DrawSprite(gl,startX+80,y,16,1,Directions.up);
        SoundEf(4);

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
    public void PowerHealth(GL gl,double tx , double ty,double scale , int picture_index){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[picture_index]);
        // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(tx,ty,0);
        gl.glScaled(scale+0.2,scale-0.05,1);
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

    public void handleKeyPress() {
        boolean move=false;
        if (isKeyPressed(KeyEvent.VK_LEFT) && isKeyPressed(KeyEvent.VK_DOWN)) {
            if (x > 0) {
                x--;
            }
            if (y > 0) {
                y--;
            }
            direction = Directions.down_left;
            animationIndex++;
            move=true;
        } else if (isKeyPressed(KeyEvent.VK_RIGHT) && isKeyPressed(KeyEvent.VK_DOWN)) {
            if (x < maxWidth - 10) {
                x++;
            }
            if (y > 0) {
                y--;
            }
            direction = Directions.down_right;
            animationIndex++;
            move=true;
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
            move=true;
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
            move=true;
        }
        else if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (x > 0) {
                x--;
            }
            direction=Directions.left;
            animationIndex++;
            move=true;
        }
        else if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (x < maxWidth-10) {
                x++;
            }
            direction=Directions.right;
            animationIndex++;
            move=true;
        }
        else if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (y > 0) {
                y--;
            }
            direction=Directions.down;
            animationIndex++;
            move=true;
        }
        else if (isKeyPressed(KeyEvent.VK_UP)) {
            if (y < maxHeight-10) {
                y++;
            }
            direction=Directions.up;
            animationIndex++;
            move=true;
        }
        if (isKeyPressed(KeyEvent.VK_SPACE)) {
            shootBullet();
        }

        if(move)SoundEf(3); //ok

    }
    public void handleMouse(){
        if (isMoving) {
            int dx = targetX - x;
            int dy = targetY - y;

            if (Math.abs(dx) < 2 && Math.abs(dy) < 2) {
                isMoving = false;
            } else {
                if (Math.abs(dx) > Math.abs(dy)) {
                    x += (dx > 0) ? 1 : -1;
                    direction = (dx > 0) ? Directions.right : Directions.left;
                } else {
                    y += (dy > 0) ? 1 : -1;
                    direction = (dy > 0) ? Directions.up : Directions.down;
                }

                if (dx != 0 && dy != 0) {
                    if (dx > 0 && dy > 0) direction = Directions.up_right;
                    if (dx > 0 && dy < 0) direction = Directions.down_right;
                    if (dx < 0 && dy > 0) direction = Directions.up_left;
                    if (dx < 0 && dy < 0) direction = Directions.down_left;
                }
            }
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



    public void mouseMoved(MouseEvent e) {
        targetX = (int) ((e.getX() / (double) maxWidth) * maxWidth);
        targetY = maxHeight - (int) ((e.getY() / (double) maxHeight) * maxHeight);

        isMoving = true;

    }

    public void mouseDragged(MouseEvent e) {
       mouseMoved(e);
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
    public void playMusic(int i){
        vic.setFile(i);
        vic.play();
    }
    public void SoundEf(int i){
        vic.setFile(i);
        vic.play();
    }
}