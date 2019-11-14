import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;

public class ShapesDemo extends Canvas
{
    public void paint( Graphics g )
    {
        Graphics2D g2 = (Graphics2D)g;

        Shape a, b, c, d;

        //                     x1  y1   x2   y2
        a = new Line2D.Double(150, 50, 160, 100);

        //                 x    y    w   h
        b = new Rectangle(100, 150, 200, 60);

        //                    x    y    w    h  start  extent      PIE, OPEN or CHORD
        c = new Arc2D.Double(100, 300, 150, 150, 45   , 270, Arc2D.PIE);

        int[] xpoints = { 150, 150, 220 };
        int[] ypoints = { 500, 600, 600 };
        // makes a polygon from the points (150,500) (150,600) and (220,600)
        d = new Polygon(xpoints, ypoints, xpoints.length);

        // draw outlines of the four shapes
        g2.draw(a);
        g2.draw(b);
        g2.draw(c);
        g2.draw(d);

        // create a "transform" object
        AffineTransform trans = new AffineTransform();
        trans.scale(0.75,0.75);  // shrink by 75% in both directions
        trans.translate(400,0);	 // translate +400 pixels in the x direction

        // now make new, transformed shapes
        Shape a2 = trans.createTransformedShape(a);
        Shape b2 = trans.createTransformedShape(b);
        Shape c2 = trans.createTransformedShape(c);
        Shape d2 = trans.createTransformedShape(d);

        // draw filled-in versions of the four new shapes
        g2.setColor(Color.green);
        g2.draw(a2);
        g2.fill(b2);
        g2.fill(c2);
        g2.fill(d2);

    }

    public static void main(String[] args)
    {
        JFrame win = new JFrame("Shapes Demo");
        win.setSize(1024,768);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add( new ShapesDemo() );
        win.setVisible(true);
    }


}