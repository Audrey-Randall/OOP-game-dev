package game.component.behavior;

import game.Entity;

public class TestBehavior extends Behavior {

    int up = 1, left = 1;

    @Override
    public void update(Entity e) {

        if (e.posX() > 640-64)
            left = -1;
        else if (e.posX() < 0)
            left = 1;

        if (e.posY() > 480-64)
            up = -1;
        else if (e.posY() < 0)
            up = 1;

        e.move(left,up);
    }
}
