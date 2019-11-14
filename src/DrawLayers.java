import javax.swing.*;
import java.awt.*;

public class DrawLayers extends Canvas
{
    private Image vMem;
    private Graphics2D gBuf;

    public void init()
    {
        vMem = createImage(getWidth(), getHeight());
        gBuf = (Graphics2D)vMem.getGraphics();
    }

    public void paint( Graphics g )
    {
        gBuf.setColor(Color.blue);
        gBuf.fillRect(20,20,100,200);

        gBuf.setColor(Color.white);
        gBuf.fillRect(120,20,100,200);

        gBuf.setColor(Color.red);
        gBuf.fillRect(220,20,100,200);

        g.drawImage(vMem, 0, 0, this);
    }

    public void update()
    {
        repaint();
    }

    public static void main(String[] args)
    {
        // You can change the title or size here if you want.
        JFrame win = new JFrame("Draw Layers");
        win.setSize(1024,768);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawLayers canvas = new DrawLayers();
        win.add( canvas );
        win.setVisible(true);
        canvas.init();
    }


}