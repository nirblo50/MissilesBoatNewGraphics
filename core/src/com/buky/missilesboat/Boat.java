package com.buky.missilesboat;

        import com.badlogic.gdx.ApplicationAdapter;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Animation;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by nirbl on 03/09/2016.
 */
public class Boat extends ApplicationAdapter
{
    private int height;    //Boat's height
    private int width;     //Boat's width
    private String diriction;
    private int pos;


    private Texture bubbleTexture;
    private Batch bubbleBatch;


    private SpriteBatch batch;
    private TextureAtlas Drive;
    private Animation animation;


    public Boat(int height, int width, String diriction)
    {
        this.height = height;
        this.width = width;
        this.pos = 0;
        this.diriction = diriction;

        batch = new SpriteBatch();

        if(diriction.equals("Right"))
            Drive = new TextureAtlas(Gdx.files.internal("boatNew.atlas"));    //creats the animation of the right boat

        if(diriction.equals("Left"))
            Drive = new TextureAtlas(Gdx.files.internal("leftDrive.atlas"));    //creats the animation of the left boat

        animation = new Animation(1 / 30f, Drive.getRegions());

        bubbleTexture = new Texture(Gdx.files.internal("bubble.png"));
        bubbleBatch = new SpriteBatch();

    }


    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public Boat setDiriction(String diriction)
    {
        this.dispose();
        Boat boat2 = new Boat(this.height, this.width, diriction);
        return boat2;
    }


    public Batch getBatch()
    {
        return this.batch;
    }

    public int getPos() {
        return pos;
    }

    public TextureAtlas getDrive() {
        return Drive;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void bubble(int posX, int posY, float timePast ,boolean status)
    {
    if (status)
    {
        bubbleBatch.begin();
        bubbleBatch.draw(bubbleTexture, posX - height / 6, posY - width / 2 + 50, width + height / 3, width + height / 3);
        bubbleBatch.end();
    }
        else
    {
        bubbleTexture.dispose();
        bubbleTexture = new Texture(Gdx.files.internal("bubble.png"));
    }


    }

    public void drive(int posX, int posY, float timePast)
    {
        batch.begin();
        batch.draw(animation.getKeyFrame(timePast, true), posX, posY, width, height);
        batch.end();

    }

}
