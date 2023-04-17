package noah.uyttebroeck.util;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class QuadTree <E> {

    private static final int DEFAULT_CAPACITY = 8;
    private static final int MAX_DEPTH        = 8;


    public final Rectangle boundary;
    private final int capacity;
    private final int depth;
    private final HashMap<E, Vec2F[]> elements = new HashMap<>();
    private boolean divided = false;

    public QuadTree<E> NE;
    public QuadTree<E> NW;
    public QuadTree<E> SE;
    public QuadTree<E> SW;


    public QuadTree(Rectangle boundary) {
        this(boundary, DEFAULT_CAPACITY);
    }

    public QuadTree(Rectangle boundary, int capacity) {
        this(boundary, capacity, 0);
    }

    public QuadTree(Rectangle boundary, int capacity, int depth) {
        if (capacity < 1) {
            throw new RuntimeException("capacity must be greater than 0");
        }
        this.boundary = boundary;
        this.capacity = capacity;
        this.depth = depth;
    }

    public enum Quadrant {
        NE,
        NW,
        SE,
        SW
    }

    public void subdivide() {
        NE = new QuadTree<>(boundary.subdivide(Quadrant.NE), capacity, depth + 1) {
            @Override
            protected Vec2F[] getPoints(E o) {
                return QuadTree.this.getPoints(o);
            }
        };
        NW = new QuadTree<>(boundary.subdivide(Quadrant.NW), capacity, depth + 1) {
            @Override
            protected Vec2F[] getPoints(E o) {
                return QuadTree.this.getPoints(o);
            }
        };
        SE = new QuadTree<>(boundary.subdivide(Quadrant.SE), capacity, depth + 1) {
            @Override
            protected Vec2F[] getPoints(E o) {
                return QuadTree.this.getPoints(o);
            }
        };
        SW = new QuadTree<>(boundary.subdivide(Quadrant.SW), capacity, depth + 1) {
            @Override
            protected Vec2F[] getPoints(E o) {
                return QuadTree.this.getPoints(o);
            }
        };

        divided = true;

        //insert old elements
        for (E e : elements.keySet()) {
            boolean inserted =
                    NE.insert(e) ||
                    NW.insert(e) ||
                    SE.insert(e) ||
                    SW.insert(e);

            if (!inserted) {
                throw new RuntimeException("capacity must be greater than 0 ");
            }
        }

        elements.clear();

    }

    public boolean insert(E e) {
        boolean r = true;
        Vec2F[] points = getPoints(e);
        elements.put(e, points);
        for (Vec2F v : points) {
            r &= insert(v);
        }
        if (!r)
            elements.remove(e);
        return r;
    }

    private boolean insert(Vec2F e) {
        if (!boundary.contains(e)) {
            return false;
        }

        if (!divided) {
            if (elements.size() < capacity || depth == MAX_DEPTH) {
                return true;
            }

            subdivide();
        }

        return NE.insert(e) || NW.insert(e) || SE.insert(e) || SW.insert(e);
    }


    public ArrayList<E> query(Rectangle range) {
        return query(range, new ArrayList<>());
    }

    private ArrayList<E> query(Rectangle range, ArrayList<E> found) {

        if(!range.intersects(boundary)) {
            return found;
        }

        if (divided) {
            NW.query(range, found);
            NE.query(range, found);
            SW.query(range, found);
            SE.query(range, found);
            return found;
        }

        for (E e : elements.keySet()) {
            for (Vec2F v : elements.get(e)) {
                if (range.contains(v)) {
                    found.add(e);
                }
            }
        }

        return found;
    }

    public ArrayList<E> getElements() {
        ArrayList<E> elements = new ArrayList<>(this.elements.keySet());
        if (isDivided()) {
            elements.addAll(NE.getElements());
            elements.addAll(NW.getElements());
            elements.addAll(SE.getElements());
            elements.addAll(SW.getElements());
        }
        return elements;
    }

    public boolean isDivided() {
        return divided;
    }

    protected abstract Vec2F[] getPoints(E e);
}
