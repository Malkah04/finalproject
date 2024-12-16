package sounddd;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;


public class voice {
    Clip clip;
    URL[] soundURl=new URL[6];
    public voice() {
        soundURl[2]=getClass().getResource("/music/shotgun.wav");//the best shot
        soundURl[3]=getClass().getResource("/music/walkkkk.wav");//walk
        soundURl[1]=getClass().getResource("/music/monster.wav"); //monster
        soundURl[0]=getClass().getResource("/music/mixkit-horror-sci-fi-wind-tunnel-894.wav");//the best back
        soundURl[4]=getClass().getResource("/music/gameover.wav");
        soundURl[5]=getClass().getResource("/music/win.wav");







    }
    public void setFile(int i){
       try {
           AudioInputStream ais= AudioSystem.getAudioInputStream(soundURl[i]);
           clip=AudioSystem.getClip();
           clip.open(ais);
       }catch (Exception e) {

       }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
