package game.component.behavior;

import game.Entity;
import game.component.Component;

public abstract class Behavior implements Component {
    protected Entity entity;
    public void setEntity(Entity e) { entity = e; }
    public abstract void act();
    public abstract void onCollide(Entity e);
}
