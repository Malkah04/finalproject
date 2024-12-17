package Man;
import com.sun.opengl.util.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.media.opengl.*;
import javax.swing.*;

public class Anim extends JFrame {
    static Integer highScore = Constants.highScore;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Anim();
        highScore= (Integer) Cashes.get("highScore");
        System.out.println(highScore);
    }
    public Anim() {
        GLCanvas glcanvas;
        Animator animator;
        AnimListener listener = new AnimGLEventListener4();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.addMouseListener(listener);
        glcanvas.addMouseMotionListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(15);
        animator.add(glcanvas);
        animator.start();
        setTitle("Anim Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}