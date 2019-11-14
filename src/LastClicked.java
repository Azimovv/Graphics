import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LastClicked extends Canvas implements MouseListener
{
    private int lastX, lastY;
    private String message;

    public LastClicked()
    {
        addMouseListener(this);

        message = "Click someplace!";

        lastX = 0;
        lastY = 0;
    }

    public void paint( Graphics g )
    {
        g.setColor(Color.red);
        g.fillRect(lastX,lastY,5,5);

        g.setColor(Color.black);
        g.drawString(message, 50, 100);
    }

    public void mouseClicked( MouseEvent evt )
    {
        lastX = evt.getX();
        lastY = evt.getY();
        message = "You clicked at (" + lastX + "," + lastY + ")";

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
        JFrame win = new JFrame("Last Clicked");
        win.setSize(1024,768);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add( new LastClicked() );
        win.setVisible(true);
    }


}