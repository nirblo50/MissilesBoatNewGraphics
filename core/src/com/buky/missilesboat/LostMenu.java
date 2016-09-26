package com.buky.missilesboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by nirbl on 17/09/2016.
 */
public class LostMenu extends ApplicationAdapter
{
    private Texture texture;
    private Batch batch;

    public LostMenu(String x)
    {
        if(x.equals("lost"))
            texture = new Texture(Gdx.files.internal("menu.png"));
        if(x.equals("start"))
            texture = new Texture(Gdx.files.internal("startMenu.png"));
        batch = new SpriteBatch();
    }

    public void drawMenu()
    {
        int x = Gdx.graphics.getWidth()/2 - this.getTexture().getWidth()/2;
        int y = Gdx.graphics.getHeight()/2 - this.getTexture().getHeight()/2 + 85;

        batch.begin();
        //batch.draw(texture, 0, 0);
        batch.draw(texture,x, y);
        batch.end();

    }

    public Texture getTexture() {
        return texture;
    }

    public Batch getBatch() {
        return batch;
    }


}
