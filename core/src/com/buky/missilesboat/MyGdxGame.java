package com.buky.missilesboat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import static com.badlogic.gdx.Gdx.graphics;


public class MyGdxGame extends ApplicationAdapter implements InputProcessor
{

	//o the Preferences of the high score
	Preferences prefs;
	private Boat boat;		//o the boat object
	private Missile missile;		//o the bomb object
	private Explosion explosion;		//o the explosion object
	private Background background;		//o the sea background object
	private Shield shield;		//o the shield that fall object
	private Shield shield2;		//o the shield that show on boat when you active shield
	private Shield shield3;		//o the shield that show up in num of shields
	private LostMenu startMenu;		//o the start Menu window
	private LostMenu lostMenu;		//o the lost Menu window


	private int boatHeight;		//o the height of the boat
	private int boatWidth;		//o the width of the boat
	private int missileHeight;		//o the height of the missile
	private int missileWidth;		//o the width of the boat
	private int explosionHeight;		//o the height of the explosion
	private int explosionWidth;		//o the width of the explosion


	private int pos;		//o the X position of the boat
	private int posY = 0;

	private boolean isGame = true;
	private boolean isShield = false;     // is shield possible
	private boolean isShieldActive = false;    // is the shield currently active
	private boolean tookShield= false;    // is the shield was taken this round
	private boolean start = false;


	private float timePast = 0;		//o the time that passed since game started
	private float timeOfShield = 0;		//o the time the shield was activated
	private double doubleTapTime = 0.0;		//o time of the taps


	private int misslileX;		//o the X position of the missile
	private int misslileY;		//o the Y position of the missile
	private int shieldX;		//o the X position of the shield
	private int shieldY;		//o the Y position of the shield


	private int mone;		//o counter of the bombs/ level
	private int shieldNum = 0;		//o counter of the shields


	private double speed;		//o the speed the bomb fall at
	private double acc;   	//o the accuaricy of the bombs
	private double accShield;   	//o the accuaricy of the shield



	//o Text
	private Batch textBatch;		//o the batch of the boat
	private BitmapFont font;		//o the font for the score

	private int highScore;

	private AdHandler handler;
	private boolean toogle;

	public MyGdxGame (AdHandler handler){
		this.handler = handler;
	}



	@Override
	public void create ()
	{


		//o the high score
		prefs = Gdx.app.getPreferences("highScore");

		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		Gdx.input.setInputProcessor(this);		//o setting the input from the phone (pressing the screen)

		boatHeight = graphics.getHeight() / 6;		//o setting the proportions for the boat
		boatWidth = boatHeight * 2;		//o setting the proportions for the boat
		missileWidth = graphics.getWidth() / 12;		//o the width of the boat
		missileHeight =  graphics.getHeight() / 7;		//o the height of the missile
		explosionHeight = graphics.getHeight() / 3;		//o the height of the explosion
		explosionWidth = graphics.getWidth() / 6;		//o the width of the explosion

		//o setting up all of the objects and animation needed for the app
		boat = new Boat(boatHeight, boatWidth, "Right");
		missile = new Missile(missileHeight, missileWidth);
		explosion = new Explosion(explosionHeight, explosionWidth);
		background = new Background();
		shield = new Shield();
		shield2 = new Shield();
		shield3 = new Shield();
		lostMenu = new LostMenu("lost");
		startMenu = new LostMenu("start");


		//o variables for the animation
		pos = graphics.getWidth()/2 - boatWidth/2;		//o the X posiotion of the boat
		mone = 0;		//o the number of bombs escaped
		speed = 2;		//o the anitial speed of the bomb
		acc = 0.0;		//o the accuaricy of the bomb
		accShield = 0.0;	//o the accuaricy of the shield


		misslileX = randomPos(missileWidth, mone);			//o the X position of the bomb
		misslileY = graphics.getHeight() + missileHeight;		//o the Y position of the bomb
		shieldX = randomPos(shield.getSize(), mone);		//o the X position of the shield
		shieldY = graphics.getHeight() + shield.getSize();		//o the Y position of the shield


		//o setting the Text for the level mark
		textBatch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(5, 5 );

		highScore = prefs.getInteger("highScore");

	}



	@Override
	public void render ()
	{
		//o Clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//o setting the background to the sea picture
		background.drawBackground();

		//o calculate the time that past simce game has began
		timePast += graphics.getDeltaTime();

		float accelX = Gdx.input.getAccelerometerX();		//o the X tilt of the phone
		float accelY = Gdx.input.getAccelerometerY();		//o the Y tilt of the phone
		float accelZ = Gdx.input.getAccelerometerZ();		//o the Z tilt of the phone

		//o  drawing the little menu of shield num
		shield3.setSize(shield.getSize() / 2);
		shield3.drawShield(shield3.getSize()/2+10, graphics.getHeight() - shield3.getSize()*3 -shield.getSize() / 3 -boatHeight/2 );

		if(!start)
		{
			startMenu.drawMenu();		//o draw start menu
			boat.drive(pos, posY, timePast);		//o moving the boat to the start posititio
		}

		//o drawing score text
		textBatch.begin();
		//font.draw(textBatch,"Score: "+mone + "\n", 50 , graphics.getHeight()-120);
		//o drawing shields text
		//font.draw(textBatch,"X "+shieldNum + "\n", shield3.getSize() + shield.getSize() / 3 , graphics.getHeight() - shield3.getSize()*3 +15 );

		font.draw(textBatch,"Score: "+mone + "\n", 50 , graphics.getHeight()-boatHeight);
		//o drawing shields text
		font.draw(textBatch,"X "+shieldNum + "\n", shield3.getSize() + shield.getSize() / 3 , graphics.getHeight() - shield3.getSize()*3 +15 -boatHeight/2 );

		textBatch.end();

		//o checking if the game is still being played or player has lost
		if(isGame && start) {
			//o set the possition according to the tilt of the phone  between the boundaries
			if (this.pos + accelY / 3.8 * 15 >= 0 && this.pos + boatWidth + accelY / 3.8 * 15 <= graphics.getWidth()) {
				if (accelY < -0.46 || accelY > 0.46)
					pos += accelY / 3.8 * 15;
			}
			//o moving the boat to the wanted positition
			boat.drive(pos, posY, timePast);

			//o missile work: if hasn't reached all the way down yet
			if (misslileY > - missileHeight) {
				missile.shoot(misslileX, misslileY, timePast);
				misslileY -= speed;
				speed += 0.20;
			}

			//o did not hit boat
			else {
				mone++;
				speed = 2 + acc;
				acc += 1.5;

				//o randomize a new place for the bomb
				misslileX = randomPos(missileWidth, mone);
				misslileY = graphics.getHeight() + missileHeight;
			}

			//o checks if bomb hits boat
			if (misslileY <= posY + boatHeight * 0.6) {
				if (isCrash(misslileX, pos, missileWidth, boatWidth) && !isShieldActive)
					isGame = false;


			}



			//o every 5 turns a shield comes down
			if (mone % 5 == 0 && mone > 0)
				isShield = true;

			if (isShield)
			{
				//o if shield did not arrive all the way down yet
				if ((shieldY > -(graphics.getHeight() + shield.getSize())) && !tookShield ) {
					shield.drawShield(shieldX, shieldY);
					shieldY -= 11.6 + accShield;
				}

				//o if reached all the way down
				else {
					shieldX = randomPos(shield.getSize() + missileHeight+50, mone);
					shieldY = graphics.getHeight() + shield.getSize();
					isShield = false;
					tookShield = false;
					accShield += 1.3;
				}

				//o checks if shield hits boat
				if (shieldY <= posY + boatHeight - 15 && !tookShield) {
					if (isCrash(shieldX, pos, shield.getSize(), boatWidth)) {
						shieldNum++;
						tookShield = true;
					}
				}
			}
		}

		//o if lost the game
		else if (start)
		{
			lostMenu.drawMenu();
			textBatch.begin();
			font.draw(textBatch,"High Score: " + highScore, graphics.getHeight()/2 +missileHeight+25 , graphics.getHeight()/2 +missileHeight-20);
			font.draw(textBatch,"Score: " + mone, graphics.getHeight()/2 +missileHeight+missileHeight/2 , graphics.getHeight()/2);
			textBatch.end();

			//o check if new score is better than the old high score
			if (mone > highScore)
			{
				//o put the new score as the new high score
				highScore = mone;
				prefs.putInteger("highScore", highScore);

				// bulk update your preferences
				prefs.flush();
			}


			misslileY = graphics.getHeight() + missileHeight;
			boat.drive(pos, posY, timePast);		//o keep the boat locked on the same place lost
			explosion.boom(pos, boatHeight/2, timePast);		//o keep the explosion locked on the same place lost
			isShield = false;		//o shield has arrived to end and need to be resetrd
		}


		//o checks if shield is active and not passed 3 sec
		if(isShieldActive && timePast-timeOfShield < 3.0)
		{
			//shield2.drawShield(pos + boatWidth/3, 0);
			boat.bubble(pos, 0 , timePast, true);		//o start bubble
		}

		//o checks if shield is active and did pass 3 sec
		else if (isShieldActive)
		{
			isShieldActive = false;		//o disable the shield flag
			timeOfShield = 0;		//o reseting the time passed since activing shield
			boat.bubble(pos, 0 , timePast, false);		//o end bubble
		}


		if (Gdx.input.justTouched())
		{
			handler.showAds(toogle);
			toogle = !toogle;
		}


	}

	@Override
	public void dispose ()
	{
		boat.getBatch().dispose();
		boat.getDrive().dispose();
		boat.dispose();

		missile.getBatch().dispose();
		missile.getDrive().dispose();
		missile.dispose();

		explosion.getBatch().dispose();
		explosion.getDrive().dispose();
		explosion.dispose();

		background.getBatch().dispose();
		background.getTexture().dispose();
		background.dispose();

		lostMenu.getBatch().dispose();
		lostMenu.getTexture().dispose();
		lostMenu.dispose();

		startMenu.getBatch().dispose();
		startMenu.getTexture().dispose();
		startMenu.dispose();

		shield.getBatch().dispose();
		shield.getTexture().dispose();
		shield.dispose();

		shield2.getBatch().dispose();
		shield2.getTexture().dispose();
		shield2.dispose();

		shield3.getBatch().dispose();
		shield3.getTexture().dispose();
		shield3.dispose();

		font.dispose();
		textBatch.dispose();

	}

	//o returns a random place for the bomb in X scale
	public int randomPos(int width , int mone)
	{
		Random rnd = new Random();
		int x1 = this.pos - graphics.getWidth()/4 - mone*5;			//o the left boundary
		int x2 = this.pos + graphics.getWidth()/4 + mone*5;		//o the right boundary

		if (x1 < 0)
			x1 = 0;
		if (x2 > graphics.getWidth() - width)
			x2 = graphics.getWidth() - width;


		int pos2 = rnd.nextInt( x2 - x1 ) + x1;
		return pos2;
	}



	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{

		if(!isGame)
		{
			MyGdxGame.this.create();
			explosion.getBatch().dispose();
			explosion = new Explosion(explosionHeight, explosionWidth);
			isGame = true;
			shieldNum = 0;
		}

		/*
		if(shieldNum > 0)
		{
			shieldNum --;
			isShieldActive = true;
			timeOfShield = timePast;
		}
		*/
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		startMenu.getBatch().disableBlending();
		start = true;
		if (timePast - doubleTapTime < 0.45 && shieldNum > 0)
		{
			shieldNum --;
			isShieldActive = true;
			timeOfShield = timePast;
		}
		doubleTapTime = timePast;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}




	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	//o checks if missile hits boat
	public boolean isCrash(int missileX, int boatX, int missileWidth, int boatWidth)
	{
		int screenWidth = graphics.getWidth();
		int missileX2 = misslileX + missileWidth;
		int boatX2 = boatX + boatWidth;

		//o if Left side of bomb is inside boat
		if(missileX >= boatX + 60 && missileX + 10 <= boatX2 - 35)
			return true;

		//o if Right side of bomb is inside boat
		if(missileX2 >= boatX + 60 && missileX2 <= boatX2 - 35)
			return true;

		return false;

	}




}
