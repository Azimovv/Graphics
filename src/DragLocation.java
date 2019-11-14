import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class DragLocation extends Canvas implements MouseMotionListener
{
    private int lastX, lastY;
    private String message;

    public DragLocation()
    {
        addMouseMotionListener(this);

        message = "Click someplace and drag!";

        lastX = 10;
        lastY = 50;
    }

    public void paint( Graphics g )
    {
        g.setColor(Color.black);
        g.drawString(message, lastX, lastY);
    }

    public void mouseDragged( MouseEvent evt )
    {
        lastX = evt.getX();
        lastY = evt.getY();
        message = ". (" + lastX + "," + lastY + ")";

        repaint();
    }

    public void mouseMoved( MouseEvent evt )
    {
    }

    public static void main(String[] args)
    {
        // You can change the title or size here if you want.
        JFrame win = new JFrame("Drag Location");
        win.setSize(1024,768);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add( new DragLocation() );
        win.setVisible(true);
    }


}