package com.jga.snake.screen.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.snake.SimpleSnakeGame;
import com.jga.snake.assets.AssetDescriptors;
import com.jga.snake.collision.CollisionListener;
import com.jga.snake.common.GameManager;
import com.jga.snake.config.GameConfig;
import com.jga.snake.screen.menu.MenuScreen;
import com.jga.snake.system.BoundsSystem;
import com.jga.snake.system.CoinSystem;
import com.jga.snake.system.CollisionSystem;
import com.jga.snake.system.DirectionSystem;
import com.jga.snake.system.passive.EntityFactorySystem;
import com.jga.snake.system.HudRenderSystem;
import com.jga.snake.system.PlayerControlSystem;
import com.jga.snake.system.RenderSystem;
import com.jga.snake.system.SnakeMovementSystem;
import com.jga.snake.system.WorldWrapSystem;
import com.jga.snake.system.debug.DebugCameraSystem;
import com.jga.snake.system.debug.DebugInputSystem;
import com.jga.snake.system.debug.DebugRenderSystem;
import com.jga.snake.system.debug.GridRenderSystem;
import com.jga.snake.system.passive.SnakeSystem;
import com.jga.snake.system.passive.SoundSystem;
import com.jga.snake.util.GdxUtils;

public class GameScreen extends ScreenAdapter {
    //constants
    private static final Logger log=new Logger(GameScreen.class.getName(),Logger.DEBUG);
    //attributes
    private final SimpleSnakeGame game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;

//    private EntityFactory factory;

    private OrthographicCamera camera;
    private Viewport hudViewport;

    private Viewport viewport;
    private ShapeRenderer renderer;
    private PooledEngine engine;
    private BitmapFont font;
    private Sound coinSound;
    private Sound loseSound;
    private CollisionListener listener;



    //constructor


    public GameScreen(SimpleSnakeGame game) {
        this.game = game;
        assetManager=game.getAssetManager();
        batch=game.getBatch();
    }
    //public methods
    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();

        engine.update(delta);



        if(GameManager.INSTANCE.isGameOver()){
            game.setScreen(new MenuScreen(game));
        }


    }

    @Override
    public void show() {
        camera=new OrthographicCamera();
        viewport=new FitViewport(GameConfig.WORLD_WIDTH,GameConfig.WORLD_HEIGHT,camera);
        hudViewport=new FitViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT);
        renderer=new ShapeRenderer();
        engine=new PooledEngine();
//        factory=new EntityFactory(engine,assetManager);
        font=assetManager.get(AssetDescriptors.UI_FONT);
        coinSound=assetManager.get(AssetDescriptors.COIN_SOUND);
        loseSound=assetManager.get(AssetDescriptors.LOSE_SOUND);


        engine.addSystem(new GridRenderSystem(viewport,renderer));
        engine.addSystem(new DebugCameraSystem(GameConfig.WORLD_CENTER_X,
                GameConfig.WORLD_CENTER_Y,
                camera
        ));
        engine.addSystem(new DebugRenderSystem(viewport,renderer));
        engine.addSystem(new DebugInputSystem());
        engine.addSystem(new SnakeSystem());
        engine.addSystem(new DirectionSystem());
        engine.addSystem(new SnakeMovementSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new PlayerControlSystem());
        engine.addSystem(new WorldWrapSystem());
        engine.addSystem(new CoinSystem());

        engine.addSystem(new CollisionSystem());
        engine.addSystem(new RenderSystem(batch,viewport));
        engine.addSystem(new HudRenderSystem(batch,hudViewport,font));
        engine.addSystem(new EntityFactorySystem(engine,assetManager));
        engine.addSystem(new SoundSystem(assetManager));
//
//       factory.createSnake();
//       factory.createCoin();
//       factory.createBackground();

       GameManager.INSTANCE.reset();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        hudViewport.update(width,height,true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        engine.removeAllEntities();
    }


}
