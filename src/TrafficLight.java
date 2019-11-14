import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

public class TrafficLight extends Canvas implements MouseListener
{
    private String message;
    private Shape circle1, circle2, circle3;

    public TrafficLight()
    {
        addMouseListener(this);

        message = "Click on one of the circles!";
        circle1 = new Ellipse2D.Double(500, 50,150,150);
        circle2 = new Ellipse2D.Double(500,210,150,150);
        circle3 = new Ellipse2D.Double(500,370,150,150);
    }

    public void paint( Graphics g )
    {
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.black);
        g2.drawString(message, 50, 100);

        g2.setColor(Color.red);
        g2.fill(circle1);
        g2.setColor(Color.yellow);
        g2.fill(circle2);
        g2.setColor(Color.green);
        g2.fill(circle3);
    }

    public void mouseClicked( MouseEvent evt )
    {
        if ( circle1.contains( evt.getPoint() ) )
            message = "You clicked on the red circle!";
        else if( circle2.contains(evt.getPoint()))
            message = "You clicked on the yellow circle!";
        else if( circle3.contains(evt.getPoint()))
            message = "You clicked on the green circle!";
        else
            message = "You clicked at (" + evt.getX() + "," + evt.getY() + ")";

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
        JFrame win = new JFrame("Traffic Light");
        win.setSize(1024,768);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add( new TrafficLight() );
        win.setVisible(true);
    }


}