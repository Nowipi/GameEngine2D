package noah.uyttebroeck.collision;

import noah.uyttebroeck.component.Collider;

public interface OnCollision {
    void collisionEntered(Collider other);
    void collisionExited(Collider other);
}
