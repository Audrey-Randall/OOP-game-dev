package game.component.behavior;

import game.Entity;

public class TestBehavior extends Behavior {

    int up = 1, left = 1;

    @Override
    public void act() {

        if (entity.posX() > 640-64)
            left = -1;
        else if (entity.posX() < 0)
            left = 1;

        if (entity.posY() > 480-64)
            up = -1;
        else if (entity.posY() < 0)
            up = 1;

        entity.move(left,up);
    }
    @Override
    public void onCollide(Entity e) {}
}
