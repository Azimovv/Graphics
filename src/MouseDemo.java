import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseDemo extends Canvas implements MouseListener
{
    private Color curColor;
    private String curMessage;

    public MouseDemo()
    {
        addMouseListener(this);

        curColor = Color.red;
        curMessage = "The square is red.";
    }

    public void paint( Graphics g )
    {
        g.setColor(Color.black);
        g.setFont(Font.decode("Calibri-BOLD-24"));
        g.drawString(curMessage, 400, 100);

        g.setColor(curColor);
        g.fillRect(250,100,100,100);
    }

    public void mouseClicked( MouseEvent evt )
    {
        System.out.println("You clicked at (" + evt.getX() + "," + evt.getY() + ")");

        curMessage = "The square is green.";

        curColor = Color.GREEN;
        repaint();
    }

    public void mousePressed( MouseEvent evt )
    {
    }

    public void mouseReleased( MouseEvent evt )
    {
    }

    public void mouseEntered( MouseEvent evt )
    {
    }

    public void mouseExited( MouseEvent evt )
    {
    }

    public static void main(String[] args)
    {
        // You can change the title or size here if you want.
        JFrame win = new JFrame("MouseDemo");
        win.setSize(1024,768);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add( new MouseDemo() );
        win.setVisible(true);
    }


}