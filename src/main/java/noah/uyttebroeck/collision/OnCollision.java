package noah.uyttebroeck.collision;

import noah.uyttebroeck.component.Collider;

public interface OnCollision {
    void collisionEntered(Collider.HitResult hitResult);
    void collisionExited(Collider.HitResult hitResult);
}
