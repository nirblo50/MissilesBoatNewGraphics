package com.buky.missilesboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by nirbl on 04/09/2016.
 */
public class Missile extends ApplicationAdapter
{

    private int height;    //Boat's height
    private int width;     //Boat's width
    private int pos;


    private SpriteBatch batch;
    private TextureAtlas shoot;
    private Animation animation;


    public Missile(int height, int width)
    {
        this.height = height;
        this.width = width;
        this.pos = 0;

        batch = new SpriteBatch();
        shoot = new TextureAtlas(Gdx.files.internal("bombNew.atlas"));    //creats the animation of the left boat
        animation = new Animation(1 / 30f, shoot.getRegions());
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
        return shoot;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void shoot(int posX, int posY, float timePast)
    {
        batch.begin();
        batch.draw(animation.getKeyFrame(timePast, true), posX, posY, width, height);
        batch.end();
    }
}
