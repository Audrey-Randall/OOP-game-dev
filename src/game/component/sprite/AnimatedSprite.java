package game.component.sprite;

import mote4.util.texture.Texture;

public class AnimatedSprite extends StaticSprite {
    private int frames, currentFrame, maxDelay, delay;

    public AnimatedSprite(Texture t, int tileX, int tileY, int frames, int delay) {
    	super(t, tileX, tileY, 0);
        
        this.frames = frames;
        this.delay = maxDelay = delay;
        currentFrame = 0;
    }

    @Override
    public void render() {
        if (delay <= 0) {
            currentFrame++;
            currentFrame %= frames;
            setSprite(currentFrame);
            delay = maxDelay;
        } else
            delay--;

        super.render();
    }
}
