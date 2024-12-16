package Man;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame  implements ActionListener {
    private final JButton Back  ;
    private final JButton pause ;
    private final JButton Enter ;

    private final JButton Exit ;

      private final JTextField Name ;
       private final JPanel p ;


  public GUI() {
      Back = new JButton("Back");
       pause = new JButton("pause");
       Enter = new JButton("Enter");
       Exit = new JButton("Exit");

       Name = new JTextField()  ;
       p = new JPanel();
       p.setLayout(new GridLayout(4,3,50,50));

    setLayout(new BorderLayout(50,50));


       p.add(Name) ;
       p.add(Back);
       p.add(pause) ;
       p.add(Enter) ;
       p.add(Exit);



         add(p,BorderLayout.NORTH);

      setSize(700,700);
      setTitle("Hello");
      setLocationRelativeTo(this);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

    public static void main(String[] args) {
        new GUI();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
                         if(e.getSource() == Enter) {
                       

                         }
    }
}
