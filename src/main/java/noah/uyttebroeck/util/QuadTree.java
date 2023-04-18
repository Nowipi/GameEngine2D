package noah.uyttebroeck.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public abstract class QuadTree <E> {

    private static final int DEFAULT_CAPACITY = 8;
    private static final int MAX_DEPTH        = 8;


    public final Rectangle boundary;
    private final int capacity;
    private final int depth;
    private final HashMap<Vec2F, E> elements = new HashMap<>();
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
        for (Vec2F v : elements.keySet()) {
            boolean inserted =
                    NE.insert(elements.get(v), v) ||
                    NW.insert(elements.get(v), v) ||
                    SE.insert(elements.get(v), v) ||
                    SW.insert(elements.get(v), v);

            if (!inserted) {
                throw new RuntimeException("capacity must be greater than 0 ");
            }
        }

        elements.clear();

    }

    public void insert(E e) {
        Vec2F[] points = getPoints(e);
        for (Vec2F v : points) {
            if (!insert(e, v))
                elements.remove(v);
        }
    }

    private boolean insert(E e, Vec2F p) {
        if (!boundary.contains(p)) {
            return false;
        }

        if (!divided) {
            if (elements.size() < capacity || depth == MAX_DEPTH) {
                elements.put(p, e);
                return true;
            }

            subdivide();
        }

        return NE.insert(e, p) || NW.insert(e, p) || SE.insert(e, p) || SW.insert(e, p);
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

        for (Vec2F v : elements.keySet()) {
            if (range.contains(v)) {
                found.add(elements.get(v));
                break;
            }
        }

        return found;
    }

    public Collection<E> getElements() {
        Collection<E> elements = this.elements.values();
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
