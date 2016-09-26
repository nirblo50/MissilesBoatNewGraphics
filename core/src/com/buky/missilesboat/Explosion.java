package com.buky.missilesboat;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by nirbl on 06/09/2016.
 */
public class Explosion extends ApplicationAdapter
{

    private int height;    //explosion's height
    private int width;     //explosion's width
    private int pos;


    private SpriteBatch batch;
    private TextureAtlas exp;
    private Animation animation;


    public Explosion(int height, int width)
    {
        this.height = height;
        this.width = width;
        this.pos = 0;

        batch = new SpriteBatch();
        exp = new TextureAtlas(Gdx.files.internal("explosion.atlas"));    //creats the animation of the left boat
        animation = new Animation(1 / 22f, exp.getRegions());
    }


    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }



    public Batch getBatch()
    {
        return this.batch;
    }

    public int getPos() {
        return pos;
    }

    public TextureAtlas getDrive() {
        return exp;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void boom(int posX, int posY, float timePast)
    {
        batch.begin();
        batch.draw(animation.getKeyFrame(timePast, true), posX, posY, width, height);
        batch.end();
    }
}
