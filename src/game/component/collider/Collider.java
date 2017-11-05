package game.component.collider;

import game.Entity;
import game.component.Component;

import java.util.List;

public abstract class Collider implements Component {
    protected Entity entity;
    public void setEntity(Entity e) { entity = e; }
    public abstract void act(List<Entity> colliders);
    public abstract boolean collidesWith(Collider c);
    //
}
