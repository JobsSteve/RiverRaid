package river.raid.game;

import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;

import android.view.KeyEvent;

//placeHolder scene class for the main menu, currently only includes a start menu item 
public class MainMenuScene extends MenuScene implements IOnMenuItemClickListener {
	BaseActivity activity;
	final int MENU_START = 0;
	final int MENU_SETTINGS = 1;
	final int MENU_SCORES = 2;
	final int MENU_EXIT = 3;

	// private PopupScene popup;
	public MainMenuScene() {
		super(BaseActivity.getSharedInstance().mCamera);
		activity = BaseActivity.getSharedInstance();

		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(0, 0, TexRes.getInstance().regMenuBackground, activity.getVertexBufferObjectManager())));
		// setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		setBackground(background);
		if (!activity.menuMusic.isPlaying())
			activity.menuMusic.play();
		// setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		Sprite text = new Sprite(0, 0, TexRes.getInstance().regMenuText, activity.getVertexBufferObjectManager());
		text.setPosition(mCamera.getWidth() / 2 - text.getWidth() / 2, text.getHeight() / 10);
		attachChild(text);
		IMenuItem s = new SpriteMenuItem(MENU_START, TexRes.getInstance().regPlayButton, activity.getVertexBufferObjectManager());
		s.setPosition(mCamera.getWidth() / 2 - s.getWidth() / 2, mCamera.getHeight() / 2 - s.getHeight() / 2);
		addMenuItem(s);

		IMenuItem exit = new SpriteMenuItem(MENU_EXIT, TexRes.getInstance().regExit, activity.getVertexBufferObjectManager());
		exit.setPosition(exit.getWidth() / 10, mCamera.getHeight() - (exit.getHeight() + exit.getHeight() / 5));
		addMenuItem(exit);

		IMenuItem score = new SpriteMenuItem(MENU_SCORES, TexRes.getInstance().regScores, activity.getVertexBufferObjectManager());
		score.setPosition(mCamera.getWidth() / 2 - score.getWidth() / 2, mCamera.getHeight() - (score.getHeight() + score.getHeight() / 5));
		addMenuItem(score);

		IMenuItem settings = new SpriteMenuItem(MENU_SETTINGS, TexRes.getInstance().regSettings, activity.getVertexBufferObjectManager());
		settings.setPosition(mCamera.getWidth() - (settings.getWidth() + settings.getWidth() / 10), mCamera.getHeight() - (settings.getHeight() + settings.getHeight() / 5));
		addMenuItem(settings);

		setOnMenuItemClickListener(this);
		int mNumPart = 60;
		int mTimePart = 2;

		PointParticleEmitter particleEmitter = new PointParticleEmitter(s.getX(), s.getY());
		IEntityFactory<Sprite> recFact = new IEntityFactory<Sprite>() {

			@Override
			public Sprite create(float pX, float pY) {
				Sprite rect = new Sprite(0, 0, TexRes.getInstance().regSmoke, activity.getVertexBufferObjectManager()); // new
																											// Rectangle(400,
																											// 240,
																											// 4,
																											// 4,
																											// activity.getVertexBufferObjectManager());
				// rect.setColor(Color.GREEN);
				return rect;
			}

		};
		final ParticleSystem<Sprite> particleSystem = new ParticleSystem<Sprite>(recFact, particleEmitter, 100, 100, mNumPart);

		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(35, 45, 0, -10));

		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(1.0f, 0.0f, 2.5f, 6.5f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(1.0f, 10.0f, 0.0f, 0.0f, 102.0f, 102.0f, 0.f, 0f));
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.5f, 2.0f, 0, 2));
		// particleSystem.addParticleModifier(new
		// RotationParticleModifier<Rectangle>(0, mTimePart, 0, 360));
		// s.attachChild(particleSystem);
		// final ParticleSystem<Rectangle> particleSystem = new
		// ParticleSystem<Rectangle>(new PointParticleEmitter(-32, CAMERA_HEIGHT
		// - 32), RATE_MIN, RATE_MAX, PARTICLES_MAX, activity.regSmoke);
		// particleSystem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		//
		// particleSystem.addParticleInitializer(new VelocityInitializer(35, 45,
		// 0, -10));
		// particleSystem.addParticleInitializer(new AccelerationInitializer(5,
		// -11));
		// particleSystem.addParticleInitializer(new RotationInitializer(0.0f,
		// 360.0f));
		// particleSystem.addParticleInitializer(new ColorInitializer(1.0f,
		// 1.0f, 0.0f));
		//
		// particleSystem.addParticleModifier(new ScaleModifier(0.5f, 2.0f, 0,
		// 5));
		// particleSystem.addParticleModifier(new ExpireModifier(6.5f));
		// particleSystem.addParticleModifier(new ColorModifier(1.0f, 1.0f,
		// 1.0f, 1.0f, 0.0f, 1.0f, 2.5f, 5.5f));
		// particleSystem.addParticleModifier(new AlphaModifier(1.0f, 0.0f,
		// 2.5f, 6.5f));

		s.attachChild(particleSystem);

		// popup = new PopupScene(mCamera);

	}

	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {
		switch (arg1.getID()) {
			case MENU_START :
				if (activity.menuMusic.isPlaying())
					activity.menuMusic.stop();
				activity.setCurrentScene(new GameScene());
				return true;
			case MENU_EXIT :
				if (!arg0.hasChildScene())
					setPopup();
				return true;
			case MENU_SETTINGS :
				if (!arg0.hasChildScene())
					setSettingsPopup();
				return true;
			default :
				break;
		}
		return false;
	}
	public void setPopup(){
		if(!hasChildScene())
			setChildScene(new PopupScene(mCamera));
	}
	public void setSettingsPopup(){
		if(!hasChildScene())
			setChildScene(new SettingsScene(mCamera));
	}
}
