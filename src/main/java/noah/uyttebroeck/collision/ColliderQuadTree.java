 package noah.uyttebroeck.collision;

import noah.uyttebroeck.component.BoxCollider;
import noah.uyttebroeck.component.Collider;
import noah.uyttebroeck.util.QuadTree;
import noah.uyttebroeck.util.Rectangle;
import noah.uyttebroeck.util.Vec2F;
import noah.uyttebroeck.util.VectorMath;

import java.util.ArrayList;

 public class ColliderQuadTree extends QuadTree<Collider> {

     private final int cellSize;

     //TODO create points based on cellSize to prevent not colliding when the borders are only intersecting
     //boundary may be wrong (too small or big idk)

    public ColliderQuadTree(Rectangle boundary, int cellSize) {
        super(boundary);
        this.cellSize = cellSize;
    }

     @Override
     public ArrayList<Collider> query(Rectangle range) {
        if (range.size.x < cellSize)
            range.size.x = (float) cellSize;
         if (range.size.y < cellSize)
             range.size.y = (float) cellSize;
         return super.query(range);
     }

     @Override
    protected Vec2F[] getPoints(Collider collider) {
        if (collider instanceof BoxCollider) {

            ArrayList<Vec2F> points = new ArrayList<>();

            Vec2F topL = collider.getPosition();
            Vec2F topR = new Vec2F(collider.getPosition().x + collider.getSize().x, collider.getPosition().y);
            Vec2F bottomR = VectorMath.add(collider.getPosition(), collider.getSize());
            Vec2F bottomL = new Vec2F(collider.getPosition().x, collider.getPosition().y + collider.getSize().y);

            Vec2F[] top    = list(values(topL.x, topR.x)      , topL.y                   ); //top
            Vec2F[] right  = list(topR.x                      , values(topR.y, bottomR.y)); //right
            Vec2F[] bottom = list(values(bottomL.x, bottomR.x), bottomR.y                ); //bottom
            Vec2F[] left   = list(topL.x                      , values(topL.y, bottomL.y)); //left

            return append(append(append(top, right), bottom), left);

        } else {
            return new Vec2F[]{collider.getPosition()};
        }
    }

    private Vec2F[] append(Vec2F[] a, Vec2F[] b) {
        Vec2F[] l = new Vec2F[a.length + b.length];
        System.arraycopy(a, 0, l, 0, a.length);
        System.arraycopy(b, 0, l, a.length, b.length);
        return l;
    }

    private Vec2F[] list(float[] x, float y) {
        Vec2F[] l = new Vec2F[x.length];
        for (int i = 0; i < l.length; i++) {
            l[i] = new Vec2F(x[i], y);
        }
        return l;
    }

     private Vec2F[] list(float x, float[] y) {
         Vec2F[] l = new Vec2F[y.length];
         for (int i = 0; i < l.length; i++) {
             l[i] = new Vec2F(x, y[i]);
         }
         return l;
     }

    private float[] values(float min, float max) {
        float[] ps = new float[(int) Math.ceil((Math.abs(max - min)) / cellSize) + 1];
        for (int i = 0; i < ps.length; i++) {
            ps[i] = min + (i * cellSize);
        }
        if (ps[ps.length-1] > max) {
            ps[ps.length-1] = max;
        }
        return ps;
    }

     public static void main(String[] args) {
         float[] t = new ColliderQuadTree(null, 15).values(10, 20);
         System.out.println(t.length);
     }

}
