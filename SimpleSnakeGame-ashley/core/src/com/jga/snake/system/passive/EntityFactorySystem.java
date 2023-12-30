package com.jga.snake.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jga.snake.assets.AssetDescriptors;
import com.jga.snake.assets.RegionNames;
import com.jga.snake.common.Direction;
import com.jga.snake.component.BodyPartComponent;
import com.jga.snake.component.BoundsComponent;
import com.jga.snake.component.CoinComponent;
import com.jga.snake.component.DimensionComponent;
import com.jga.snake.component.DirectionComponent;
import com.jga.snake.component.MovementComponent;
import com.jga.snake.component.PlayerComponent;
import com.jga.snake.component.PositionComponent;
import com.jga.snake.component.SnakeComponent;
import com.jga.snake.component.TextureComponent;
import com.jga.snake.component.WorldWrapComponent;
import com.jga.snake.component.ZOrderComponent;
import com.jga.snake.config.GameConfig;

public class EntityFactorySystem extends EntitySystem {

    //constants
    private static final int BACKGROUND_Z_ORDER=0;
    public static final int COIN_Z_ORDER=1;
    public static final int BODY_PART_Z_ORDER=2;
    public static final int HEAD_Z_ORDER=3;

    //attributes
    private final PooledEngine engine;
    private final AssetManager assetManager;
    private TextureAtlas gamePlayAtlas;

    //constructor


    public EntityFactorySystem(PooledEngine engine, AssetManager assetManager) {
        this.engine = engine;
        this.assetManager = assetManager;
        init();
    }

    private void init(){
        gamePlayAtlas=assetManager.get(AssetDescriptors.GAME_PLAY);
    }

    //public methods


    @Override
    public void update(float deltaTime) {
        //note: no processing
    }

    @Override
    public boolean checkProcessing() {
        //note: returning false since this is "passive" system
        return false;
    }

    @Override
    public void addedToEngine(Engine engine) {
        createBackground();
        createCoin();
        createCoin();
        createCoin();
        createSnake();



    }


    public void createBackground(){
        //position
        PositionComponent position=engine.createComponent(PositionComponent.class);

        //dimension
        DimensionComponent dimension=engine.createComponent(DimensionComponent.class);
        dimension.width= GameConfig.WORLD_WIDTH;
        dimension.height=GameConfig.WORLD_HEIGHT;

        //textures
        TextureComponent texture=engine.createComponent(TextureComponent.class);
        texture.region=gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        //zOrder
        ZOrderComponent zOrder=engine.createComponent(ZOrderComponent.class);
        zOrder.z=BACKGROUND_Z_ORDER;

        //entity
        Entity entity=engine.createEntity();
        //add to engine
        entity.add(position);
        entity.add(dimension);
        entity.add(texture);
        entity.add(zOrder);

        //add to engine
        engine.addEntity(entity);


    }
    public void createSnake(){
        //snake
        SnakeComponent snake=engine.createComponent(SnakeComponent.class);
        snake.head=createSnakeHead();

        //create entity
        Entity snakeEntity=engine.createEntity();
        snakeEntity.add(snake);

        //add to engine/world
        engine.addEntity(snakeEntity);

    }

    public Entity createSnakeHead(){
        //position
        PositionComponent position=engine.createComponent(PositionComponent.class);

        //dimension
        DimensionComponent dimension=engine.createComponent(DimensionComponent.class);
        dimension.width= GameConfig.SNAKE_SIZE;
        dimension.height=GameConfig.SNAKE_SIZE;

        //bounds
        BoundsComponent bounds=engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x,position.y);
        bounds.rectangle.setSize(dimension.width,dimension.height);

        //direction
        DirectionComponent direction=engine.createComponent(DirectionComponent.class);
        direction.direction= Direction.RIGHT;
        //movement
        MovementComponent movement=engine.createComponent(MovementComponent.class);

        //player
        PlayerComponent player=engine.createComponent(PlayerComponent.class);

        //world warp
        WorldWrapComponent worldWrap=engine.createComponent(WorldWrapComponent.class);

        //texture
        TextureComponent texture=engine.createComponent(TextureComponent.class);
        texture.region=gamePlayAtlas.findRegion(RegionNames.HEAD);

        //zOrder
        ZOrderComponent zOrder=engine.createComponent(ZOrderComponent.class);
        zOrder.z=HEAD_Z_ORDER;

        //entity
        Entity entity=engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(direction);
        entity.add(movement);
        entity.add(player);
        entity.add(worldWrap);
        entity.add(texture);
        entity.add(zOrder);


        //add to engine
        engine.addEntity(entity);

        return entity;

    }

    public void createCoin(){
        //position
        PositionComponent position=engine.createComponent(PositionComponent.class);

        //dimension
        DimensionComponent dimension=engine.createComponent(DimensionComponent.class);
        dimension.width=GameConfig.COIN_SIZE;
        dimension.height=GameConfig.COIN_SIZE;

        //bounds
        BoundsComponent bounds=engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x,position.y);
        bounds.rectangle.setSize(dimension.width,dimension.height);

        //coin
        CoinComponent coin=engine.createComponent(CoinComponent.class);

        //texture
        TextureComponent texture=engine.createComponent(TextureComponent.class);
        texture.region=gamePlayAtlas.findRegion(RegionNames.COIN);

        //zOrder
        ZOrderComponent zOrder=engine.createComponent(ZOrderComponent.class);
        zOrder.z=COIN_Z_ORDER;

        //entity
        Entity entity=engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(coin);
        entity.add(texture);
        entity.add(zOrder);

        //add to engine
        engine.addEntity(entity);


    }

    public Entity createBodyPart(float x,float y){
        //position
        PositionComponent position=engine.createComponent(PositionComponent.class);
        position.x=x;
        position.y=y;

        //dimension
        DimensionComponent dimension=engine.createComponent(DimensionComponent.class);
        dimension.width=GameConfig.SNAKE_SIZE;
        dimension.height=GameConfig.SNAKE_SIZE;

        //bounds
        BoundsComponent bounds=engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width,dimension.height);

        //body part
        BodyPartComponent bodyPart=engine.createComponent(BodyPartComponent.class);

        //texture
        TextureComponent texture=engine.createComponent(TextureComponent.class);
        texture.region=gamePlayAtlas.findRegion(RegionNames.BODY);

        //zOrder
        ZOrderComponent zOrder=engine.createComponent(ZOrderComponent.class);
        zOrder.z=BODY_PART_Z_ORDER;

        //entity
        Entity entity=engine.createEntity();
        entity.add(texture);
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(bodyPart);
        entity.add(zOrder);




        //add to engine
        engine.addEntity(entity);

        return entity;
    }


}
