 package Man;
import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Anim extends JFrame implements ActionListener ,KeyListener {
    private static FPSAnimator animator;
    private GLCanvas glcanvas;
    private JButton jb;
    private boolean isAnimating = false;
    private final JButton Multiplayer  ;
    private final JButton Singleplayer ;
    private final JButton Enter ;
    private final JButton Start ;
//    private final JTextArea NameArea;
   private final JButton Exit ;

    private final JTextField Name ;
    private final JPanel p ;
    private final JPanel p2 ;

    public static void main(String[] args) {
        new Anim();
    }


    public Anim() {
        AnimListener listener = new AnimGLEventListener4();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.addMouseListener(listener);

        getContentPane().add(glcanvas, BorderLayout.CENTER);

        animator = new FPSAnimator(glcanvas,15);

//        JPanel jp = new JPanel();
//        jb = new JButton("Start");
//        jb.addActionListener(this);
//        jp.add(jb);
     // getContentPane().add(jp, BorderLayout.SOUTH);

        setTitle("Zombie Killer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





        // Main Screen


       Multiplayer = new JButton("Multiplayer");
        Singleplayer = new JButton("Singleplayer");
        Enter = new JButton("Enter");
      Exit = new JButton("Exit");

        Start = new JButton("Start");
        Name = new JTextField()  ;
        Name.addActionListener(this);
        Enter.addActionListener(this);
        Multiplayer.addActionListener(this);
        Singleplayer.addActionListener(this);
        Exit.addActionListener(this);
        Start.addActionListener(this);



        p = new JPanel();
        p2 = new JPanel();
        getContentPane().add(p2, BorderLayout.CENTER);


        p.setLayout(new GridLayout(2,2,400,400));

        setLayout(new BorderLayout(100,100));

p2.setLayout(new GridLayout(2,2,400,400));



        p.add(Name);
        p.add(Enter) ;
      p2.add(Multiplayer);
      p2.add(Singleplayer);
      p2.add(Start);

//        add(p,BorderLayout.NORTH);
        getContentPane().add(p2, BorderLayout.CENTER);
        getContentPane().add(p, BorderLayout.CENTER);

//        getContentPane().add(glcanvas, BorderLayout.CENTER);
//        add(Names,BorderLayout.CENTER);
        getContentPane().add(p, BorderLayout.SOUTH);
        getContentPane().add(glcanvas, BorderLayout.CENTER);  // Game panel

        animator.add(glcanvas);
        animator.start();

        glcanvas.setVisible(false);

        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
    public void actionPerformed(ActionEvent e) {
     
      if (e.getSource().equals(Enter)) {

          String playerName = Name.getText();
          System.out.println("PlayerName" + playerName);
          p.setVisible(false);
          p2.setVisible(true);
          glcanvas.requestFocus();

      }
        if(e.getSource().equals(Start))
            animator.start();
        p2.setVisible(true);
        glcanvas.setVisible(true);
        glcanvas.requestFocus();

        if (e.getSource().equals(Exit))
            getContentPane().add(p, BorderLayout.CENTER);
        if (e.getSource().equals(Multiplayer)) {

        }
        if (e.getSource().equals(Singleplayer)) {

        }

    }

    public void centerWindow(Component frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;
        frame.setLocation(
                (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2
        );
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}