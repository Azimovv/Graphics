import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Reversi extends Canvas implements MouseListener
{
    public static void main(String[] args)
    {
        JFrame win = new JFrame("Reversi");
        win.setSize(800,900);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add( new Reversi() );
        win.setVisible(true);
    }

    private boolean gameOver = false;
    private boolean started = false;
    private boolean showLabels = true;
    private boolean showLegal = false;

    private final int NONE = 0;
    private final int BLACK = 1;
    private final int WHITE = 2;

    private int player = BLACK;
    private int other = WHITE;

    private Direction EAST      = new Direction( 0, 1);
    private Direction NORTHEAST = new Direction(-1, 1);
    private Direction NORTH     = new Direction(-1, 0);
    private Direction NORTHWEST = new Direction(-1,-1);
    private Direction WEST      = new Direction( 0,-1);
    private Direction SOUTHWEST = new Direction( 1,-1);
    private Direction SOUTH     = new Direction( 1, 0);
    private Direction SOUTHEAST = new Direction( 1, 1);
    private Direction[] compass = { EAST, NORTHEAST, NORTH, NORTHWEST, WEST, SOUTHWEST, SOUTH, SOUTHEAST };

    private Rectangle[][] boxes;
    private int[][] pieces;
    private int scoreA = 0;
    private int scoreB = 0;
    private int legalMoves = 0;

    public Reversi()
    {
        addMouseListener(this);
        boxes = new Rectangle[8][8];
        pieces = new int[8][8];
        for ( int r=0; r<boxes.length; r++ )
            for ( int c=0; c<boxes[0].length; c++ )
            {
                boxes[r][c] = new Rectangle(30+c*90,100+r*90,90,90);
            }
        pieces[3][3] = 2;
        pieces[3][4] = 1;
        pieces[4][3] = 1;
        pieces[4][4] = 2;

    }

    public void paint( Graphics g )
    {
        g.setColor( Color.green );
        g.fillRect( 0, 0, 800, 900 );
        g.setColor( Color.black );
        Graphics2D g2 = (Graphics2D)g;
        g.setFont( new Font("Arial", Font.BOLD, 48) );

        if ( ! started )
        {
            g.drawString( "REVERSI", 305, 300 );
            g.drawString( "click to play", 275, 400 );
        }
        else
        {
            // board
            for ( int r=0; r<boxes.length; r++ )
                for ( int c=0; c<boxes[0].length; c++ )
                {
                    g2.draw(boxes[r][c]);
                }
            // pieces
            scoreA = scoreB = 0;
            for ( int r=0; r<pieces.length; r++ )
                for ( int c=0; c<pieces[0].length; c++ )
                    if ( pieces[r][c] > 0 )
                    {
                        if ( pieces[r][c] == BLACK )
                        {
                            g.setColor(Color.black);
                            scoreA++;
                        }
                        else if ( pieces[r][c] == WHITE )
                        {
                            g.setColor(Color.white);
                            scoreB++;
                        }
                        Rectangle box = boxes[r][c];
                        g.fillOval(box.x+5, box.y+5, box.width-10, box.height-10);
                    }

            // count/show legal moves
            legalMoves = 0;
            g.setColor(Color.red);
            for ( int r=0; r<pieces.length; r++ )
                for ( int c=0; c<pieces[0].length; c++ )
                    if ( pieces[r][c] == NONE && isLegalMove(r,c) )
                    {
                        legalMoves++;
                        if ( showLegal )
                        {
                            Rectangle box = boxes[r][c];
                            g.drawOval(box.x+5, box.y+5, box.width-10, box.height-10);
                        }
                    }

            // labels
            if ( showLabels )
            {
                g.setFont( new Font("Arial", Font.PLAIN, 24) );
                g.setColor(Color.gray);
                for ( int r=0; r<boxes.length; r++ )
                    for ( int c=0; c<boxes[0].length; c++ )
                    {
                        g.drawString( r+","+c, boxes[r][c].x+30, boxes[r][c].y+75 );
                    }
            }

            // status
            g.setColor( Color.black );
            g.setFont( new Font("Arial", Font.BOLD, 48) );
            g.drawString( (player==BLACK ? "Black" : "White") + "'s turn.", 50, 70 );
            g.drawString( scoreA+" : "+scoreB, 620, 70 );

        }


    }



    public void mouseReleased( MouseEvent evt )
    {
        int x = evt.getX();
        int y = evt.getY();

        if ( gameOver )
        {
            started = false;
            gameOver = false;
            repaint();
            return;
        }

        if ( ! started )
        {
            started = true;
            repaint();
            return;
        }

        for ( int r=0; r<boxes.length; r++ )
            for ( int c=0; c<boxes[0].length; c++ )
                if ( boxes[r][c].contains( evt.getPoint() ) )
                {
                    if ( pieces[r][c] == 0 && isLegalMove(r,c) )
                    {
                        pieces[r][c] = player;
                        flipOthers(r, c);

                        other = player;
                        player = (player==BLACK) ? WHITE : BLACK;
                        repaint();
                    }
                    return;
                }
    }

    public boolean isLegalMove( int r, int c )
    {
        // returns true if the current player is allowed to play a piece at r,c
        // It must be adjacent to an opposite-colored piece, and then there
        //  must be a same-colored piece on the other side of that.
        Location cur = new Location(r,c);
        Direction dir = new Direction(r,c);
        String location = cur.getAdjacentLocation(dir).toString();

        int i = Character.getNumericValue(location.charAt(1));
        int j = Character.getNumericValue(location.charAt(3));
        int k,m;

       /* System.out.println("*********** " + i + "," + j + " *************");

        if (pieces[i][j] == other)
        {
            k = i-r;
            m = j-c;
            while(pieces[r+k][c+m] == other)
            {
                if (k!=0)
                    k++;
                if (m!=0)
                    m++;
            }

            if(k!=0 && m==0)
                return (pieces[r+(k+1)][c+m] == player);
            else if(k==0 && m!=0)
                return (pieces[r+k][c+(m+1)] == player);
            else
                return  (pieces[r+(k+1)][c+(m+1)] == player);
        }
        else
            return false;*/

       return true;
    }

    public Location firstMatch( int r, int c, Direction d )
    {
        Location cur = new Location(r,c);
        // returns the location of the first matching piece in the given direction
        return null;
    }

    public void flipOthers( int r, int c )
    {
        // Flips over all the pieces between r,c and the match in all 8 compass directions.
        // Already finished; no need to modify.
        for ( Direction dir : compass )
            flipOthers(r, c, dir);
    }

    public void flipOthers( int r, int c, Direction dir )
    {
        // Flips over all the pieces between r,c and the first matching color in the given direction
        Location cur = new Location(r,c);

        // your code goes here

        int k=dir.dr, m=dir.dc;
        if (isLegalMove(r,c))
        {
            r+=dir.dr;
            c+=dir.dc;
            while (pieces[r][c] == other) {
                System.out.println("dr,dc: " + dir.dr + "," + dir.dc);
                if (pieces[r+dir.dr][c+dir.dc] != player)
                    break;
                System.out.println("Piece at position: " + (r) + "," + (c) + " to be changed");
                pieces[r][c] = player;
            }
        }

    }

    public int otherStreak( int r, int c, Direction dir )
    {
        // returns the count of consecutive "others" found in the given direction
        //
        int count = 0;
        Location cur = new Location(r,c);

        while ( true )
        {
            cur = cur.getAdjacentLocation(dir);
            if ( ! (isValid(cur) && pieces[cur.r][cur.c] == other) )
                break;
            count++;
        }
        return count;
    }

    public boolean isValid( Location loc )
    {
        return 0 <= loc.r && loc.r < pieces.length && 0 <= loc.c && loc.c < pieces.length;
    }

    public boolean isValid( int r, int c )
    {
        return 0 <= r && r < pieces.length && 0 <= c && c < pieces.length;
    }

    public void mouseClicked( MouseEvent evt ) {}
    public void mousePressed ( MouseEvent evt ) {}
    public void mouseEntered ( MouseEvent evt ) {}
    public void mouseExited  ( MouseEvent evt ) {}
}

class Direction
{
    public int dr;
    public int dc;

    public Direction( int r, int c )
    {
        dr = r;
        dc = c;
    }

    public String toString()
    {
        if ( dr == -1 && dc == 0 )
            return "NORTH";
        else if ( dr == 1 && dc == 0 )
            return "SOUTH";
        else if ( dr == 0 && dc == 1 )
            return "EAST";
        else if ( dr == 0 && dc == -1 )
            return "WEST";
        else if ( dr == -1 && dc == -1 )
            return "NORTHWEST";
        else if ( dr == 1 && dc == -1 )
            return "SOUTHWEST";
        else if ( dr == 1 && dc == 1 )
            return "NORTHEAST";
        else if ( dr == -1 && dc == 1 )
            return "SOUTHEAST";
        else
            return "{" + dr + "," + dc + "}";
    }

}


class Location
{
    public int r;
    public int c;

    public Location( int r, int c )
    {
        this.r = r;
        this.c = c;
    }

    public Location getAdjacentLocation( Direction dir )
    {
        Location next = new Location(r,c);
        next.r += dir.dr;
        next.c += dir.dc;

        return next;
    }

    public Location getAdjacentLocation( Direction dir, int count )
    {
        Location next = new Location(r,c);
        for ( int i=0; i<count; i++ )
        {
            next.r += dir.dr;
            next.c += dir.dc;
        }

        return next;
    }

    public String toString()
    {
        return "(" + r + "," + c + ")";
    }

    public boolean equals( Location other )
    {
        return this.r == other.r && this.c == other.c;
    }
}
