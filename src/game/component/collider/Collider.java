package game.component.collider;

import game.Entity;
import game.component.Component;

import java.util.List;

public abstract class Collider implements Component {
    protected Entity entity;
    protected boolean solid = false;
    protected boolean breakable = false;
    public void setEntity(Entity e) { entity = e; }
    public abstract void act(List<Entity> colliders);
    public abstract boolean collidesWith(Collider c);
    public boolean getSolid(){ return solid; }
    public boolean getBreakable(){ return breakable; }
    public void setBreakable(boolean brea){ breakable = brea; }
}
