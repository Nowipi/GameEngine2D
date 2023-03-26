package noah.uyttebroeck;

import noah.uyttebroeck.util.QuadTree;
import noah.uyttebroeck.util.Rectangle;
import noah.uyttebroeck.util.Vec2F;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class QuadTreeTest extends JPanel implements MouseListener {

    private final RectQuadTree quadTree;
    private final ArrayList<Rectangle> rectangles = new ArrayList<>();

    public QuadTreeTest() {
        setPreferredSize(new Dimension(640, 480));
        quadTree = new RectQuadTree(new Rectangle(new Vec2F(0), new Vec2F(getPreferredSize().width, getPreferredSize().height)));
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {

        for (Rectangle r : rectangles) {
            g.setColor(Color.BLACK);
            g.fillRect(r.position.x.intValue(), r.position.y.intValue(), r.size.x.intValue(), r.size.y.intValue());
            g.setColor(Color.GREEN);
            g.drawRect(r.position.x.intValue(), r.position.y.intValue(), r.size.x.intValue(), r.size.y.intValue());
        }

        g.setColor(Color.RED);
        drawQuadTree(quadTree, g);
    }

    private void drawQuadTree(QuadTree<?> quadTree, Graphics g) {
        g.drawRect(quadTree.boundary.position.x.intValue(), quadTree.boundary.position.y.intValue(), quadTree.boundary.size.x.intValue(), quadTree.boundary.size.y.intValue());
        if (quadTree.isDivided()) {
            drawQuadTree(quadTree.NE, g);
            drawQuadTree(quadTree.NW, g);
            drawQuadTree(quadTree.SE, g);
            drawQuadTree(quadTree.SW, g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("huts");
        frame.add(new QuadTreeTest(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Rectangle r = new Rectangle(new Vec2F(e.getX(), e.getY()), new Vec2F(50, 50));
        rectangles.add(r);
        quadTree.insert(r);
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
