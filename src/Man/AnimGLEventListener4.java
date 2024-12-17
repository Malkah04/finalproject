package Man;
import Texture.TextureReader;
import sounddd.voice;

import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import javax.media.opengl.glu.GLU;

public class AnimGLEventListener4 extends AnimListener {

    public enum Directions{
        up,right,left,down,up_left,up_right,down_left,down_right
    }

    List<Bullet> bullets = new ArrayList<>();
    List<Bullet> bullets2 = new ArrayList<>();
    long lastBulletTime = System.currentTimeMillis();
    long lastBulletTime2 = System.currentTimeMillis();
    Directions direction1 = Directions.up;
    Directions direction2 = Directions.up;
    int zombieCount = 10;
    int bulletcount=20;
    boolean bulletcheck=true;

    private int targetX;
    private int targetY;
    private boolean isMoving = false;
    int animationIndex = 0, animationIndex2 = 0;
//    int x2=-1000,y2=-1000;// when use multi make it equal null  : int x2,y2
    int x2=1 ,y2=0;
    boolean mult=true;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth/2, y = maxHeight/2;
    boolean gameOver = false;
    int gameDuration = 67000;
    long gameStartTime;
    int initialTime = 60;
    double powerHealth = 0;
    double powerHealth2 = 0;
    int numIndex1 = 0;
    int numIndex2 = 0;
    int score;
    boolean heart1 =true;
    boolean heart2 =true;
    boolean heart3 =true;
    boolean level = false;
    boolean level1 =true;
    boolean level2 = true;
    boolean level3 = true;
<<
    boolean win = false;
    String scoreKey="scores";
    String userName = "";
     Integer highScore = Constants.highScore;


=
    boolean Heart1 =true;
    boolean Heart2 =true;
    boolean Heart3 =true;
    boolean Level = false;
    boolean Level1 =true;
    boolean Level2 = true;
    boolean Level3 = true;
    double t=1;
    double t2=1;
>

    String[] textureNames ={"sprite-sheet_0 (1).png","sprite-sheet_0 (3).png","sprite-sheet_0 (4).png"
            ,"sprite-sheet_0 (5).png","sprite-sheet_0 (6).png","zombie top 1.png","zombie top 2.png"
            ,"zombie top 3.png","zombie top 4.png","bullets 1.png","Upper_G.png","Upper_A.png","Upper_M.png","Upper_E.png",
            "Upper_O.png","Upper_V.png","Upper_R.png","Sand clock png.png","0.png","1.png","2.png","3.png","4.png","5.png"
            ,"6.png","7.png","8.png","9.png","Blood).png","RTS Status Indicator24.jpg","VIDA_8_0.png","VIDA_5.png","VIDA_3.png",
            "heart_3.png","Upper_S.png","Upper_C.png","Upper_Y.png","Upper_U.png","Upper_W.png",
            "Upper_I.png","Upper_N.png","next.png","levels.png","home.png", "background.jpg"};


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


        }
        if(mult){
            score = 20;
        } else score = 10;

        gameStartTime = System.currentTimeMillis();
    }
    public void shootBullet() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBulletTime < 300) {
            return;
        }
        int bulletIndex = 9;
        bullets.add(new Bullet(x, y, bulletIndex, direction1));
        SoundEf(2);//ok
        lastBulletTime = currentTime;
    }
    public void shootBullet2() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBulletTime2 < 200) {
            return;
        }

        int bulletIndex = 9;
        bullets2.add(new Bullet(x2, y2, bulletIndex, direction2));
        lastBulletTime2 = currentTime;
    }
    voice vic=new voice();

    int cnt=0;
    List<monstor>list=new ArrayList<>();
    List<Blood> bloodList = new ArrayList<>();

    int zombieIndex;
    long changeLag = System.currentTimeMillis();
    long startTime = System.currentTimeMillis();

    boolean isCollided = false;
    int screen =0;

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        switch (screen){
            case 0:
                drawHome(gld);
                break;
            case 1:
                drawLevels(gld);
                break;
            case 2:
                try {
                    drawLevel1(gld);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    drawLevel2(gld);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    drawLevel3(gld);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                drawLevels(gld);
                break;
            case 6:
                try {
                    drawMultiLevel1(gld);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                try {
                    drawMultiLevel2(gld);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 8:
                try {
                    drawMultiLevel3(gld);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }



    }
    public void drawHome(GLAutoDrawable gld){
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length-2]);
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
    public void drawLevels(GLAutoDrawable gld){
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length-3]);
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
    //easy
//    public static Integer min(Integer n1,Integer n2, Integer n3){
//        Integer min = n1;
//        if(n2<min){
//            min=n2;
//        }
//        if(n3<min){
//            min=n3;
//        }
//        return min;
//    }
    public void drawMultiLevel1(GLAutoDrawable gld) throws IOException{
        GL gl = gld.getGL();
        DrawBackground(gl);

        if(numIndex1 == score/10){
            drawYouWin(gl);
            win = true;
            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }

            SoundEf(5);
            return;
        }
        if(gameOver){
            drawGameOver(gl);
            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }
            playMusic(4);
            return;
        }

        drawScore(gl);
        displayNumbers(gl);
        DrawSprite(gl,0,90,17,0.3f,Directions.up);
        handleKeyPress();
//        handleMouse();
        animationIndex = animationIndex % 4;
        animationIndex2 = animationIndex2 % 4;
        DrawSprite(gl,x,y,animationIndex,1, direction1);
        if(mult)
            DrawSprite(gl,x2,y2,animationIndex2,1,direction2);
        long currentTime=System.currentTimeMillis();
        if (currentTime-changeLag>=200){
            zombieIndex=(int)(Math.random()*4)+5;
        }
        if(cnt<10) {
            if (currentTime - startTime >= 4000) {
                int rx = (int) (Math.random() * maxWidth) -maxHeight-10;
                int ry = (int) (Math.random() * maxHeight) -maxHeight-10;
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
            isCollided = dist<=90;
            System.out.println(isCollided + ", "+ dist + ", "+ radii);
            if(isCollided){
                powerHealth = powerHealth+0.005;
                isCollided=false;
            }
        }
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<monstor> monstersToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);
                break;
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        list.remove(m);
                        if(numIndex2 < 9){
                            numIndex2++;
                        }
                        else  { // if score = 20
                            numIndex2 = 0;
                            numIndex1 ++;
                        }
                        bloodList.add(new Blood(m.x, m.y));
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);
//.....................
        for (Bullet bullet : bullets2) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        list.remove(m);
                        bloodList.add(new Blood(m.x, m.y));
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets2.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);

//...................
        if (currentTime - changeLag >= 1000) {
            if (initialTime > 0) {
                initialTime--;
            }
            changeLag = currentTime;
        }
        drawTimer(gl,initialTime);

        if(currentTime - gameStartTime >= gameDuration && !list.isEmpty()){
            gameOver = true;
        }
        if(level1 || level2 || level3) {
            // the basic of powerHealth
            PowerHealth(gl, 0.7, 0.9, 0.33, 0.06, 29);
            // the green of powerHealth
            PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 30);
            // the yellow of powerHealth
            if (powerHealth < 0.5 && powerHealth > 0.2) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 31);
            }
            // the red of powerHealth
            if (powerHealth < 0.7 && powerHealth > 0.5) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 32);
            }
        }
        if(heart1){
            PowerHealth(gl , 0.0079,0.9,0.05,0.05,33);}
        if(heart2){
            PowerHealth(gl , 0.15,0.9,0.05,0.05,33);}
        if(heart3){
            PowerHealth(gl , 0.3,0.9,0.05,0.05,33);}

        if (powerHealth > 0.62) {
            if (level1) {
                heart3 = false;
                level1 = false;
            } else if (level2) {
                heart2 = false;
                level2 = false;
            } else if (level3) {
                heart1 = false;
                level3 = false;
            }
            powerHealth = 0;
        }




        //apeare blood
        for (Blood blood : bloodList) {
            if (blood.isVisible && !blood.isExpired()) {
                DrawSprite(gl, blood.x, blood.y, 28, 1.0f, Directions.up);
            } else {
                blood.isVisible = false;
            }
        }
    }
    public void drawMultiLevel2(GLAutoDrawable gld) throws IOException{
        GL gl = gld.getGL();
        DrawBackground(gl);

        if(numIndex1 == score/10){
            drawYouWin(gl);
            win = true;
            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }

            SoundEf(5);
            return;
        }
        if(gameOver){
            drawGameOver(gl);
            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }
            playMusic(4);
            return;
        }

        drawScore(gl);
        displayNumbers(gl);
        DrawSprite(gl,0,90,17,0.3f,Directions.up);
        handleKeyPress();
//        handleMouse();
        animationIndex = animationIndex % 4;
        animationIndex2 = animationIndex2 % 4;
        DrawSprite(gl,x,y,animationIndex,1, direction1);
        if(mult)
            DrawSprite(gl,x2,y2,animationIndex2,1,direction2);
        long currentTime=System.currentTimeMillis();
        if (currentTime-changeLag>=200){
            zombieIndex=(int)(Math.random()*4)+5;
        }
        if(cnt<10) {
            if (currentTime - startTime >= 4000) {
                int rx = (int) (Math.random() * maxWidth) -maxHeight-10;
                int ry = (int) (Math.random() * maxHeight) -maxHeight-10;
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
            isCollided = dist<=90;
            System.out.println(isCollided + ", "+ dist + ", "+ radii);
            if(isCollided){
                powerHealth = powerHealth+0.005;
                isCollided=false;
            }
        }
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<monstor> monstersToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);
                break;
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        list.remove(m);
                        if(numIndex2 < 9){
                            numIndex2++;
                        }
                        else  { // if score = 20
                            numIndex2 = 0;
                            numIndex1 ++;
                        }
                        bloodList.add(new Blood(m.x, m.y));
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);
//.....................
        for (Bullet bullet : bullets2) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        list.remove(m);
                        bloodList.add(new Blood(m.x, m.y));
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets2.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);

//...................
        if (currentTime - changeLag >= 1000) {
            if (initialTime > 0) {
                initialTime--;
            }
            changeLag = currentTime;
        }
        drawTimer(gl,initialTime);

        if(currentTime - gameStartTime >= gameDuration && !list.isEmpty()){
            gameOver = true;
        }
        if(level1 || level2 || level3) {
            // the basic of powerHealth
            PowerHealth(gl, 0.7, 0.9, 0.33, 0.06, 29);
            // the green of powerHealth
            PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 30);
            // the yellow of powerHealth
            if (powerHealth < 0.5 && powerHealth > 0.2) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 31);
            }
            // the red of powerHealth
            if (powerHealth < 0.7 && powerHealth > 0.5) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 32);
            }
        }
        if(heart1){
            PowerHealth(gl , 0.0079,0.9,0.05,0.05,33);}
        if(heart2){
            PowerHealth(gl , 0.15,0.9,0.05,0.05,33);}
        if(heart3){
            PowerHealth(gl , 0.3,0.9,0.05,0.05,33);}

        if (powerHealth > 0.62) {
            if (level1) {
                heart3 = false;
                level1 = false;
            } else if (level2) {
                heart2 = false;
                level2 = false;
            } else if (level3) {
                heart1 = false;
                level3 = false;
            }
            powerHealth = 0;
        }




        //apeare blood
        for (Blood blood : bloodList) {
            if (blood.isVisible && !blood.isExpired()) {
                DrawSprite(gl, blood.x, blood.y, 28, 1.0f, Directions.up);
            } else {
                blood.isVisible = false;
            }
        }
    }
    public void drawMultiLevel3(GLAutoDrawable gld) throws IOException{
        GL gl = gld.getGL();
        DrawBackground(gl);

        if(numIndex1 == score/10){
            drawYouWin(gl);
            win = true;
            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }

            SoundEf(5);
            return;
        }
        if(gameOver){
            drawGameOver(gl);
            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }
            playMusic(4);
            return;
        }

        drawScore(gl);
        displayNumbers(gl);
        DrawSprite(gl,0,90,17,0.3f,Directions.up);
        handleKeyPress();
//        handleMouse();
        animationIndex = animationIndex % 4;
        animationIndex2 = animationIndex2 % 4;
        DrawSprite(gl,x,y,animationIndex,1, direction1);
        if(mult)
            DrawSprite(gl,x2,y2,animationIndex2,1,direction2);
        long currentTime=System.currentTimeMillis();
        if (currentTime-changeLag>=200){
            zombieIndex=(int)(Math.random()*4)+5;
        }
        if(cnt<10) {
            if (currentTime - startTime >= 4000) {
                int rx = (int) (Math.random() * maxWidth) -maxHeight-10;
                int ry = (int) (Math.random() * maxHeight) -maxHeight-10;
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
            isCollided = dist<=90;
            System.out.println(isCollided + ", "+ dist + ", "+ radii);
            if(isCollided){
                powerHealth = powerHealth+0.005;
                isCollided=false;
            }
        }
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<monstor> monstersToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);
                break;
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        list.remove(m);
                        if(numIndex2 < 9){
                            numIndex2++;
                        }
                        else  { // if score = 20
                            numIndex2 = 0;
                            numIndex1 ++;
                        }
                        bloodList.add(new Blood(m.x, m.y));
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);
//.....................
        for (Bullet bullet : bullets2) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        list.remove(m);
                        bloodList.add(new Blood(m.x, m.y));
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets2.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);

//...................
        if (currentTime - changeLag >= 1000) {
            if (initialTime > 0) {
                initialTime--;
            }
            changeLag = currentTime;
        }
        drawTimer(gl,initialTime);

        if(currentTime - gameStartTime >= gameDuration && !list.isEmpty()){
            gameOver = true;
        }
        if(level1 || level2 || level3) {
            // the basic of powerHealth
            PowerHealth(gl, 0.7, 0.9, 0.33, 0.06, 29);
            // the green of powerHealth
            PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 30);
            // the yellow of powerHealth
            if (powerHealth < 0.5 && powerHealth > 0.2) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 31);
            }
            // the red of powerHealth
            if (powerHealth < 0.7 && powerHealth > 0.5) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 32);
            }
        }
        if(heart1){
            PowerHealth(gl , 0.0079,0.9,0.05,0.05,33);}
        if(heart2){
            PowerHealth(gl , 0.15,0.9,0.05,0.05,33);}
        if(heart3){
            PowerHealth(gl , 0.3,0.9,0.05,0.05,33);}

        if (powerHealth > 0.62) {
            if (level1) {
                heart3 = false;
                level1 = false;
            } else if (level2) {
                heart2 = false;
                level2 = false;
            } else if (level3) {
                heart1 = false;
                level3 = false;
            }
            powerHealth = 0;
        }




        //apeare blood
        for (Blood blood : bloodList) {
            if (blood.isVisible && !blood.isExpired()) {
                DrawSprite(gl, blood.x, blood.y, 28, 1.0f, Directions.up);
            } else {
                blood.isVisible = false;
            }
        }
    }
    public void drawLevel1(GLAutoDrawable gld) throws IOException {
        GL gl = gld.getGL();
        DrawBackground(gl);

        if(numIndex1 == score/10){
            drawYouWin(gl);
            win = true;
            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }

            SoundEf(5);
            return;
        }
        if(gameOver){
            drawGameOver(gl);
            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }
            playMusic(4);
            return;
        }

        drawScore(gl);
        displayNumbers(gl);
        DrawSprite(gl,0,90,17,0.3f,Directions.up);
        handleKeyPress();
//        handleMouse();
        animationIndex = animationIndex % 4;
        animationIndex2 = animationIndex2 % 4;
        DrawSprite(gl,x,y,animationIndex,1, direction1);
        if(mult)
            DrawSprite(gl,x2,y2,animationIndex2,1,direction2);
        long currentTime=System.currentTimeMillis();
        if (currentTime-changeLag>=200){
            zombieIndex=(int)(Math.random()*4)+5;
        }
        if(cnt<10) {
            if (currentTime - startTime >= 4000) {
                int rx = (int) (Math.random() * maxWidth) -maxHeight-10;
                int ry = (int) (Math.random() * maxHeight) -maxHeight-10;
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
            isCollided = dist<=90;
            System.out.println(isCollided + ", "+ dist + ", "+ radii);
            if(isCollided){
                powerHealth = powerHealth+0.005;
                isCollided=false;
            }
        }
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<monstor> monstersToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);
                break;
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        list.remove(m);
                        if(numIndex2 < 9){
                            numIndex2++;
                        }
                        else  { // if score = 20
                            numIndex2 = 0;
                            numIndex1 ++;
                        }
                        bloodList.add(new Blood(m.x, m.y));
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);
//.....................
        for (Bullet bullet : bullets2) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        list.remove(m);
                        bloodList.add(new Blood(m.x, m.y));
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets2.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);

//...................
        if (currentTime - changeLag >= 1000) {
            if (initialTime > 0) {
                initialTime--;
            }
            changeLag = currentTime;
        }
        drawTimer(gl,initialTime);

        if(currentTime - gameStartTime >= gameDuration && !list.isEmpty()){
            gameOver = true;
        }
        if(level1 || level2 || level3) {
            // the basic of powerHealth
            PowerHealth(gl, 0.7, 0.9, 0.33, 0.06, 29);
            // the green of powerHealth
            PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 30);
            // the yellow of powerHealth
            if (powerHealth < 0.5 && powerHealth > 0.2) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 31);
            }
            // the red of powerHealth
            if (powerHealth < 0.7 && powerHealth > 0.5) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 32);
            }
        }
        if(heart1){
            PowerHealth(gl , 0.0079,0.9,0.05,0.05,33);}
        if(heart2){
            PowerHealth(gl , 0.15,0.9,0.05,0.05,33);}
        if(heart3){
            PowerHealth(gl , 0.3,0.9,0.05,0.05,33);}

        if (powerHealth > 0.62) {
            if (level1) {
                heart3 = false;
                level1 = false;
            } else if (level2) {
                heart2 = false;
                level2 = false;
            } else if (level3) {
                heart1 = false;
                level3 = false;
            }
            powerHealth = 0;
        }




        //apeare blood
        for (Blood blood : bloodList) {
            if (blood.isVisible && !blood.isExpired()) {
                DrawSprite(gl, blood.x, blood.y, 28, 1.0f, Directions.up);
            } else {
                blood.isVisible = false;
            }
        }
    }
    //medium
    public void drawLevel2(GLAutoDrawable gld) throws IOException {
        GL gl = gld.getGL();
        DrawBackground(gl);

        if(numIndex1 == score/10){
            drawYouWin(gl);

            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }
            SoundEf(5);
            return;
        }
        if(gameOver){
            drawGameOver(gl);
            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }
            playMusic(4);
            return;
        }


        drawScore(gl);
        displayNumbers(gl);
        DrawSprite(gl,0,90,17,0.3f,Directions.up);
        handleKeyPress();
//        handleMouse();
        animationIndex = animationIndex % 4;
        animationIndex2 = animationIndex2 % 4;
        DrawSprite(gl,x,y,animationIndex,1, direction1);
        if(mult)
            DrawSprite(gl,x2,y2,animationIndex2,1,direction2);
        long currentTime=System.currentTimeMillis();
        if (currentTime-changeLag>=200){
            zombieIndex=(int)(Math.random()*4)+5;
        }
        if(cnt<10) {
            if (currentTime - startTime >= 4000) {
                int rx = (int) (Math.random() * maxWidth) -maxHeight-10;
                int ry = (int) (Math.random() * maxHeight) -maxHeight-10;
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
            isCollided = dist<=90;
            System.out.println(isCollided + ", "+ dist + ", "+ radii);
            if(isCollided){
                powerHealth = powerHealth+0.005;
                isCollided=false;
            }
        }
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<monstor> monstersToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);

        int bulletIndex = 9;
        bullets2.add(new Bullet(x2, y2, bulletIndex, direction2));
        lastBulletTime2 = currentTime;
    }
    voice vic=new voice();

    int cnt=0;
    List<monstor>list=new ArrayList<>();
    List<Blood> bloodList = new ArrayList<>();

    int zombieIndex;
    long changeLag = System.currentTimeMillis();
    long startTime = System.currentTimeMillis();

    boolean isCollided = false;
    boolean isCollided2 = false;
    int screen =0;

    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        switch (screen){
            case 0:
                drawHome(gld);
                break;
            case 1:
                drawLevels(gld);

                break;
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        list.remove(m);
                        if(numIndex2 < 9){
                            numIndex2++;
                        }
                        else  { // if score = 20
                            numIndex2 = 0;
                            numIndex1 ++;
                        }
                        bloodList.add(new Blood(m.x, m.y));
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);
//.....................
        for (Bullet bullet : bullets2) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        list.remove(m);
                        bloodList.add(new Blood(m.x, m.y));
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets2.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);

//...................
        if (currentTime - changeLag >= 1000) {
            if (initialTime > 0) {
                initialTime--;
            }
            changeLag = currentTime;
        }
        drawTimer(gl,initialTime);

        if(currentTime - gameStartTime >= gameDuration && !list.isEmpty()){
            gameOver = true;
        }
        if(level1 || level2 || level3) {
            // the basic of powerHealth
            PowerHealth(gl, 0.7, 0.9, 0.33, 0.06, 29);
            // the green of powerHealth
            PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 30);
            // the yellow of powerHealth
            if (powerHealth < 0.5 && powerHealth > 0.2) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 31);
            }
            // the red of powerHealth
            if (powerHealth < 0.7 && powerHealth > 0.5) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.05, 32);
            }
        }
        if(heart1){
            PowerHealth(gl , 0.0079,0.9,0.05,0.05,33);}
        if(heart2){
            PowerHealth(gl , 0.15,0.9,0.05,0.05,33);}
        if(heart3){
            PowerHealth(gl , 0.3,0.9,0.05,0.05,33);}

        if (powerHealth > 0.62) {
            if (level1) {
                heart3 = false;
                level1 = false;
            } else if (level2) {
                heart2 = false;
                level2 = false;
            } else if (level3) {
                heart1 = false;
                level3 = false;
            }
            powerHealth = 0;
        }




        //apeare blood
        for (Blood blood : bloodList) {
            if (blood.isVisible && !blood.isExpired()) {
                DrawSprite(gl, blood.x, blood.y, 28, 1.0f, Directions.up);
            } else {
                blood.isVisible = false;
            }
        }
    }
    //hard
    public void drawLevel3(GLAutoDrawable gld) throws IOException {
        GL gl = gld.getGL();
        DrawBackground(gl);

        if(numIndex1 == score/10){
            drawYouWin(gl);
            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }
            SoundEf(5);
            return;
        }
        if(gameOver){
            drawGameOver(gl);
            if(highScore<score){
                highScore =score;
                Cashes.put(scoreKey,highScore);
            }
            playMusic(4);
            return;
        }

        drawScore(gl);
        displayNumbers(gl);
        DrawSprite(gl,0,90,17,0.3f,Directions.up);
//        handleMouse();
        handleKeyPress();
        if(level) {
            DrawSprite(gl, x, y, 28, 1.0f, Directions.up);
            t = t - 0.1;
            if (t < 0) {
                powerHealth = 0;
                level = false;
                t = 1;
            }
        }else {
                animationIndex = animationIndex % 4;
                DrawSprite(gl,x,y,animationIndex,1, direction1);
            }

        if(mult) {
            if(Level) {
                DrawSprite(gl, x2, y2, 28, 1.0f, Directions.up);
                t2=t2-0.1;
                if(t2 < 0){
                    powerHealth2=0;
                    Level= false;
                    t2=1;
                }
            }else {
                animationIndex2 = animationIndex2 % 4;
                DrawSprite(gl, x2, y2, animationIndex2, 1, direction2);
            }
            Health2(gl);
        }
        Health(gl);
        long currentTime=System.currentTimeMillis();
        if (currentTime-changeLag>=200){
            zombieIndex=(int)(Math.random()*4)+5;
        }
        if(cnt<10) {
            if (currentTime - startTime >= 4000) {
                int rx = (int) (Math.random() *2* maxWidth) -maxHeight;
                int ry = (int) (Math.random() *2* maxHeight) -maxHeight;
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
            isCollided = dist<=90;
            System.out.println(isCollided + ", "+ dist + ", "+ radii);
            if(isCollided){
                powerHealth = powerHealth+0.005;
                isCollided=false;
            }
            if(mult){
                double dist2 = sqrdDistance(x2,y2,m.x,m.y);
                double radii2 = Math.pow(0.5*0.1*maxHeight+0.5*0.1*maxHeight,2);
                isCollided2 = dist2<=90;
                System.out.println(isCollided2 + ", "+ dist2 + ", "+ radii2);
                if(isCollided2){
                    powerHealth2 = powerHealth2+0.005;
                    isCollided2=false;
                }
            }
        }
        if (!Level3){
            mult=false;
            x2 = -10000;
            y2 = -10000;
        }
        if(!mult){
            Level3 = false;
        }
        if(!level3 && !Level3){
            gameOver= true;
        }
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<monstor> monstersToRemove = new ArrayList<>();
        if (bulletcount>0) {
            for (Bullet bullet : bullets) {
                bullet.move();
                DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
                if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                    bulletsToRemove.add(bullet);
                    if (bulletcheck) {
                        bulletcount--;
                        if (bulletcount <= 0) {
                            bulletcheck = false;
                        }
                    }
                }
                for (monstor m : list) {
                    if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                        if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                            System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                            bulletsToRemove.add(bullet);
                            monstersToRemove.add(m);
                            zombieCount--;
                            bulletcount--;
                            list.remove(m);
                            if (numIndex2 < 9) {
                                numIndex2++;
                            } else { // if score = 20
                                numIndex2 = 0;
                                numIndex1++;
                            }
                            bloodList.add(new Blood(m.x, m.y));
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
//.....................
        for (Bullet bullet : bullets2) {
            bullet.move();
            DrawSprite(gl, bullet.x, bullet.y, bullet.textureIndex, 0.5f, bullet.direction);
            if (bullet.x <= 0 || bullet.x >= maxWidth || bullet.y <= 0 || bullet.y >= maxHeight) {
                bulletsToRemove.add(bullet);
            }
            for (monstor m : list) {
                if (sqrdDistance(bullet.x, bullet.y, m.x, m.y) < 100) {
                    if (isInDirection(bullet.direction, bullet.x, bullet.y, m.x, m.y)) {
                        System.out.println("Hit: Bullet at (" + bullet.x + ", " + bullet.y + ") Monster at (" + m.x + ", " + m.y + ")");
                        bulletsToRemove.add(bullet);
                        monstersToRemove.add(m);
                        list.remove(m);
                        bloodList.add(new Blood(m.x, m.y));
                        break;
                    } else {
                        System.out.println("Miss: Monster at (" + m.x + ", " + m.y + ") not in direction.");
                    }
                }
            }
        }
        bullets2.removeAll(bulletsToRemove);
        list.removeAll(monstersToRemove);

//...................
        if (currentTime - changeLag >= 1000) {
            if (initialTime > 0) {
                initialTime--;
            }
            changeLag = currentTime;
        }
        drawTimer(gl,initialTime);

        if(currentTime - gameStartTime >= gameDuration && !list.isEmpty()){
            gameOver = true;
        }
        //apeare blood
        for (Blood blood : bloodList) {
            if (blood.isVisible && !blood.isExpired()) {
                DrawSprite(gl, blood.x, blood.y, 28, 1.0f, Directions.up);
            } else {
                blood.isVisible = false;
            }
        }
        DrawSprite(gl, 90, 0, 5, 0.5f, Directions.down);
        DrawSprite(gl, 0, 0, 9, 0.8f, Directions.down);

        drawZombieCount(gl, zombieCount);
        drawbulletCount(gl,bulletcount);
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
        if(cnt<=10&&monster.x<=maxWidth&&monster.y<=maxHeight&&monster.x>=0&&monster.y>=0)SoundEf(1);
        int xd=monster.x-x2;
        int yd=monster.y-y2;
        int sq=xd*xd+yd*yd;
        int dx=monster.x-x;
        int dy=monster.y-y;
        int sqrt=dx*dx+dy*dy;
            if (sqrt < 100||sq<100) {
                return;
            }
            for (monstor m : l) {
                if (m != monster) {
                    int dxx = m.x - monster.x;
                    int dyy = m.y - monster.y;
                    int sqr = dxx * dxx + dyy * dyy;
                    if (sqr < 100) {
                        if (monster.x > m.x) {
                            monster.x++;
                        } else {
                            monster.x--;
                        }
                        if (monster.y > m.y) {
                            monster.y++;
                        } else {
                            monster.y--;
                        }
                    }
                }
            }


                if(sqrt<sq) {
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
                else{
                    if (Math.abs(monster.y - y2) < 2) {
                        if (monster.x > x2) {
                            monster.dir = Directions.right;
                            monster.x -= 1;
                        } else if (monster.x < x2) {
                            monster.dir = Directions.left;
                            monster.x += 1;
                        }
                    } else if (Math.abs(monster.x - x2) < 2) {
                        if (monster.y > y2) {
                            monster.dir = Directions.up;
                            monster.y -= 1;
                        } else if (monster.y < y2) {
                            monster.dir = Directions.down;
                            monster.y += 1;
                        }
                    } else {
                        if (monster.x > x2 && monster.y > y2) {
                            monster.dir = Directions.up_right;
                            monster.x -= 1;
                            monster.y -= 1;
                        } else if (monster.x < x2 && monster.y > y2) {
                            monster.dir = Directions.up_left;
                            monster.x += 1;
                            monster.y -= 1;
                        } else if (monster.x > x2 && monster.y < y2) {
                            monster.dir = Directions.down_right;
                            monster.x -= 1;
                            monster.y += 1;
                        } else if (monster.x < x2 && monster.y < y2) {
                            monster.dir = Directions.down_left;
                            monster.x += 1;
                            monster.y += 1;
                        }
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
    public void drawZombieCount(GL gl, int zombieCount) {
        int tens = zombieCount / 10;
        int ones = zombieCount % 10;
        DrawSprite(gl, 86, -1, textures.length - 25 + (ones - 1), 0.4f, Directions.up);
        DrawSprite(gl, 82, -1, textures.length - 25 + (tens - 1), 0.4f, Directions.up);

    }

    public void drawbulletCount(GL gl, int zombieCount) {
        int tens = zombieCount / 10;
        int ones = zombieCount % 10;
        DrawSprite(gl, 7, -1, textures.length - 25 + (ones - 1), 0.4f, Directions.up);
        DrawSprite(gl, 3, -1, textures.length - 25 + (tens - 1), 0.4f, Directions.up);

    }
    public void drawTimer(GL gl, int time) {
        int tens = time / 10;
        int ones = time % 10;

        DrawSprite(gl, 4, 90, textures.length - 23-3 + (tens-1), 0.3f, Directions.up);

        DrawSprite(gl, 8, 90, textures.length - 23-3 + (ones-1), 0.3f, Directions.up);

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
    public void displayNumbers(GL gl){
        DrawSprite(gl,32,90,18+numIndex1,0.3f,Directions.up);
        DrawSprite(gl,36,90,18+numIndex2,0.3f,Directions.up);
    }
    public void drawYouWin(GL gl){
        int startX = 10;
        int y = 50;
        DrawSprite(gl,startX,y,36,1,Directions.up);
        DrawSprite(gl,startX+10,y,14,1, Directions.up);
        DrawSprite(gl,startX+20,y,37,1,Directions.up);

        DrawSprite(gl,startX+40,y,38,1,Directions.up);
        DrawSprite(gl,startX+50,y,39,0.8f,Directions.up);
        DrawSprite(gl,startX+60,y,40,1,Directions.up);
        DrawSprite(gl,startX+35,y-40,textures.length-4,3,Directions.up);


    }
    public void drawScore(GL gl){
        int startX = 15;
        int y = 90;
        DrawSprite(gl,startX,y,34,0.3f,Directions.up);
        DrawSprite(gl,startX+3,y,35,0.3f, Directions.up);
        DrawSprite(gl,startX+6,y,14,0.3f,Directions.up);
        DrawSprite(gl,startX+9,y,16,0.3f,Directions.up);
        DrawSprite(gl,startX+12,y,13,0.3f,Directions.up);

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
    public void PowerHealth(GL gl,double tx , double ty,double scaleX ,double scaleY, int picture_index){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[picture_index]);
        // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(tx,ty,0);
        gl.glScaled(scaleX,scaleY,1);
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
    public void Health(GL gl){
        if(level1 || level2 || level3) {
            // the basic of powerHealth
            PowerHealth(gl, 0.7, 0.9, 0.33, 0.04, 29);
            // the green of powerHealth
            PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.03, 30);
            // the yellow of powerHealth
            if (powerHealth < 0.5 && powerHealth > 0.2) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.03, 31);
            }
            // the red of powerHealth
            if (powerHealth < 0.7 && powerHealth > 0.5) {
                PowerHealth(gl, 0.7 + powerHealth, 0.9, 0.32, 0.03, 32);
            }
        }
        if(heart1){
            PowerHealth(gl , 0.0079,0.9,0.04,0.04,33);}
        if(heart2){
            PowerHealth(gl , 0.15,0.9,0.04,0.04,33);}
        if(heart3){
            PowerHealth(gl , 0.3,0.9,0.04,0.04,33);}

        if (powerHealth > 0.62) {
            if (level1) {
                heart3 = false;
                level1 = false;
            } else if (level2) {
                heart2 = false;
                level2 = false;
            } else if (level3) {
                heart1 = false;
                level3 = false;
            }
            powerHealth = 0;
            level = true;
        }
    }
    public void Health2(GL gl){
        if(Level1 || Level2 || Level3) {
            // the basic of powerHealth
            PowerHealth(gl, 0.7, 0.8, 0.33, 0.04, 29);
            // the green of powerHealth
            PowerHealth(gl, 0.7 + powerHealth2, 0.8, 0.32, 0.03, 30);
            // the yellow of powerHealth
            if (powerHealth2 < 0.5 && powerHealth2 > 0.2) {
                PowerHealth(gl, 0.7 + powerHealth2, 0.8, 0.32, 0.03, 31);
            }
            // the red of powerHealth
            if (powerHealth2 < 0.7 && powerHealth2 > 0.5) {
                PowerHealth(gl, 0.7 + powerHealth2, 0.8, 0.32, 0.03, 32);
            }
        }
        if(Heart1){
            PowerHealth(gl , 0.0079,0.8,0.04,0.04,33);}
        if(Heart2){
            PowerHealth(gl , 0.15,0.8,0.04,0.04,33);}
        if(Heart3){
            PowerHealth(gl , 0.3,0.8,0.04,0.04,33);}

        if (powerHealth2 > 0.62) {
            if (Level1) {
                Heart3 = false;
                Level1 = false;
            } else if (Level2) {
                Heart2 = false;
                Level2 = false;
            } else if (Level3) {
                Heart1 = false;
                Level3 = false;
            }
            powerHealth2 = 0;
            Level = true;
        }
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
            direction1 = Directions.down_left;
            animationIndex++;

        } else if (isKeyPressed(KeyEvent.VK_RIGHT) && isKeyPressed(KeyEvent.VK_DOWN)) {
            if (x < maxWidth - 10) {
                x++;
            }
            if (y > 0) {
                y--;
            }
            direction1 = Directions.down_right;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_LEFT) && isKeyPressed(KeyEvent.VK_UP)) {
            if (x > 0) {
                x--;
            }
            if (y < maxHeight - 10) {
                y++;
            }
            direction1 = Directions.up_left;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_RIGHT) && isKeyPressed(KeyEvent.VK_UP)){
            if (x < maxWidth-10) {
                x++;
            }
            if (y < maxHeight-10) {
                y++;
            }
            direction1=Directions.up_right;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (x > 0) {
                x--;
            }
            direction1=Directions.left;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (x < maxWidth-10) {
                x++;
            }
            direction1=Directions.right;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (y > 0) {
                y--;
            }
            direction1=Directions.down;
            animationIndex++;
        }
        else if (isKeyPressed(KeyEvent.VK_UP)) {
            if (y < maxHeight-10) {
                y++;
            }
            direction1=Directions.up;
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_A) && isKeyPressed(KeyEvent.VK_S)) {
            if (x2 > 0) {
                x2--;
            }
            if (y2 > 0) {
                y2--;
            }
            direction2 = Directions.down_left;
            animationIndex2++;
        } else if (isKeyPressed(KeyEvent.VK_D) && isKeyPressed(KeyEvent.VK_S)) {
            if (x2 < maxWidth - 10) {
                x2++;
            }
            if (y2 > 0) {
                y2--;
            }
            direction2 = Directions.down_right;
            animationIndex2++;
        } else if (isKeyPressed(KeyEvent.VK_A) && isKeyPressed(KeyEvent.VK_W)) {
            if (x2 > 0) {
                x2--;
            }
            if (y2 < maxHeight - 10) {
                y2++;
            }
            direction2 = Directions.up_left;
            animationIndex2++;
        } else if (isKeyPressed(KeyEvent.VK_D) && isKeyPressed(KeyEvent.VK_W)) {
            if (x2 < maxWidth - 10) {
                x2++;
            }
            if (y2 < maxHeight - 10) {
                y2++;
            }
            direction2 = Directions.up_right;
            animationIndex2++;
        } else if (isKeyPressed(KeyEvent.VK_A)) {
            if (x2 > 0) {
                x2--;
            }
            direction2 = Directions.left;
            animationIndex2++;
        } else if (isKeyPressed(KeyEvent.VK_D)) {
            if (x2 < maxWidth - 10) {
                x2++;
            }
            direction2 = Directions.right;
            animationIndex2++;
        } else if (isKeyPressed(KeyEvent.VK_S)) {
            if (y2 > 0) {
                y2--;
            }
            direction2 = Directions.down;
            animationIndex2++;
        } else if (isKeyPressed(KeyEvent.VK_W)) {
            if (y2 < maxHeight - 10) {
                y2++;
            }
            direction2 = Directions.up;
            animationIndex2++;
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
                    direction1 = (dx > 0) ? Directions.right : Directions.left;
                } else {
                    y += (dy > 0) ? 1 : -1;
                    direction1 = (dy > 0) ? Directions.up : Directions.down;
                }

                if (dx != 0 && dy != 0) {
                    if (dx > 0 && dy > 0) direction1 = Directions.up_right;
                    if (dx > 0 && dy < 0) direction1= Directions.down_right;
                    if (dx < 0 && dy > 0) direction1 = Directions.up_left;
                    if (dx < 0 && dy < 0) direction1 = Directions.down_left;
                }
            }
        }
    }



    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
        if (keyCode == KeyEvent.VK_SPACE && mult) {
            shootBullet2();
        }
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

    int prevX=-1,prevY=0;


    public void mouseMoved(MouseEvent e) {
        targetX = (int) ((e.getX() / (double) maxWidth) * maxWidth);
        targetY = maxHeight - (int) ((e.getY() / (double) maxHeight) * maxHeight);
        if (prevX==-1&&prevY==-1) {
            prevX=targetX;
            prevY=targetY;
            return;
        }
        int dx=targetX-prevX;
        int dy=targetY-prevY;
        int sqrt=dx*dx+dy*dy;
        if(sqrt>100) {
            if(Math.abs(dx)>Math.abs(dy)) {
                direction1=(dx>0)?Directions.right:Directions.left;
            } else direction1=(dy>0)?Directions.up:Directions.down;

            if (dx != 0 && dy != 0) {
                if (dx>0&&dy>0)direction1=Directions.up_right;
                if (dx>0&&dy<0)direction1=Directions.down_right;
                if (dx<0&&dy>0)direction1=Directions.up_left;
                if (dx<0&&dy<0)direction1=Directions.down_left;
            }
        }
        prevX=targetX;
        prevY=targetY;


    }

    public void mouseDragged(MouseEvent e) {
       mouseMoved(e);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if(screen == 0){

            if(e.getX()>243&&e.getX()<404&&e.getY()>149&&e.getY()<208){
                screen=1;

            }
            if(e.getX()>243&&e.getX()<404&&e.getY()>235&&e.getY()<291){
                screen=5;

            }


        }else  if(screen ==1){

            if(e.getX()>236&&e.getX()<418&&e.getY()>205&&e.getY()<274){
                screen=2;
            }
            else if(e.getX()>235&&e.getX()<418&&e.getY()>290&&e.getY()<359){
                screen=3;
            }
            else if(e.getX()>232&&e.getX()<415&&e.getY()>373&&e.getY()<442){
                screen=4;
            }
        }
        else if(screen == 2){
            shootBullet();
        }
        else if(screen==5){
            if(e.getX()>236&&e.getX()<418&&e.getY()>205&&e.getY()<274){
                screen=6;
            }
            else if(e.getX()>235&&e.getX()<418&&e.getY()>290&&e.getY()<359){
                screen=7;
            }
            else if(e.getX()>232&&e.getX()<415&&e.getY()>373&&e.getY()<442){
                screen=8;
            }
        }
//        else if(win){
//            if(e.getX()>240&&e.getX()<434&&e.getY()>484&&e.getY()<607){
//                if(screen==2){
//                    screen=3;
//                }else if (screen==3){
//                    screen = 4;
//                }
//                win = false;
//            }
//        }
        System.out.println(e.getX()+"   "+e.getY());

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