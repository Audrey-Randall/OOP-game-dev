package game.component.sprite;

import game.Entity;
import game.component.Component;

public abstract class Sprite implements Component {
    protected Entity entity;
    public void setEntity(Entity e) { entity = e; }
    public abstract void render();
}
