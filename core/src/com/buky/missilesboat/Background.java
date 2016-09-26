package com.buky.missilesboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by nirbl on 07/09/2016.
 */
public class Background extends ApplicationAdapter
{
    private Texture texture;
    private Batch batch;

    public Background()
    {
        texture = new Texture(Gdx.files.internal("backGround.png"));
        //texture = new Texture(Gdx.files.internal("sea.png"));
        batch = new SpriteBatch();

    }

    public void drawBackground()
    {
        batch.begin();
        batch.draw(texture, 0, 0);
        // batch.draw(texture,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

    }

    public Texture getTexture() {
        return texture;
    }

    public Batch getBatch() {
        return batch;
    }
}

