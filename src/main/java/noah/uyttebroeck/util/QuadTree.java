package noah.uyttebroeck.util;

import java.util.ArrayList;

public abstract class QuadTree <E> {

    private static final int DEFAULT_CAPACITY = 8;
    private static final int MAX_DEPTH        = 8;


    public final Rectangle boundary;
    private final int capacity;
    private final int depth;
    public ArrayList<E> elements = new ArrayList<>();
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

    public void subdivide() {
        NE = new QuadTree<>(boundary.subdivide("ne"), capacity, depth + 1) {
            @Override
            public Vec2F getPosition(E element) {
                return QuadTree.this.getPosition(element);
            }
        };
        NW = new QuadTree<>(boundary.subdivide("nw"), capacity, depth + 1) {
            @Override
            public Vec2F getPosition(E element) {
                return QuadTree.this.getPosition(element);
            }
        };
        SE = new QuadTree<>(boundary.subdivide("se"), capacity, depth + 1) {
            @Override
            public Vec2F getPosition(E element) {
                return QuadTree.this.getPosition(element);
            }
        };
        SW = new QuadTree<>(boundary.subdivide("sw"), capacity, depth + 1) {
            @Override
            public Vec2F getPosition(E element) {
                return QuadTree.this.getPosition(element);
            }
        };

        divided = true;

        for (E e : elements) {
            boolean inserted =
                    NE.insert(e) ||
                    NW.insert(e) ||
                    SE.insert(e) ||
                    SW.insert(e);

            if (!inserted) {
                throw new RuntimeException("capacity must be greater than 0");
            }
        }

        elements.clear();

    }

    public boolean insert(E e) {
        if (!boundary.contains(getPosition(e))) {
            return false;
        }

        if (!divided) {
            if (elements.size() < capacity || depth == MAX_DEPTH) {
                elements.add(e);
                return true;
            }

            subdivide();
        }

        return NE.insert(e) || NW.insert(e) || SE.insert(e) || SW.insert(e);
    }


    public ArrayList<E> query(Rectangle range) {
        return query(range, new ArrayList<>());
    }

    public ArrayList<E> query(Rectangle range, ArrayList<E> found) {

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

        for (E e : elements) {
            if (range.contains(getPosition(e))) {
                found.add(e);
            }
        }

        return found;
    }

    public boolean isDivided() {
        return divided;
    }

    public abstract Vec2F getPosition(E element);
}
