package com.buky.missilesboat;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by nirbl on 15/09/2016.
 */
public class Shield extends ApplicationAdapter
{
    private Texture texture;
    private Batch batch;
    private int size;

    public Shield()
    {
        texture = new Texture(Gdx.files.internal("shield.png"));
        batch = new SpriteBatch();
        this.size = Gdx.graphics.getHeight()/8;
    }

    public void drawShield(int posX, int posY)
    {
        batch.begin();
        //batch.draw(texture, 0, 0);
        batch.draw(texture,posX, posY, size, size);
        batch.end();

    }

    public Texture getTexture() {
        return texture;
    }

    public Batch getBatch() {
        return batch;
    }

    public int getSize() {
        return this.size;
    }
    public void setSize(int size)
    {
        this.size = size;
    }

}

