package game.component.behavior;

import game.Entity;

public class EmptyBehavior extends Behavior {
    @Override
    public void act() {}
    @Override
    public void onCollide(Entity e) {}
}
