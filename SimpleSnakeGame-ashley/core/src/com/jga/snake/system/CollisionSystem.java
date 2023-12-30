package com.jga.snake.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.jga.snake.common.GameManager;
import com.jga.snake.component.BodyPartComponent;
import com.jga.snake.component.BoundsComponent;
import com.jga.snake.component.CoinComponent;
import com.jga.snake.component.PositionComponent;
import com.jga.snake.component.SnakeComponent;
import com.jga.snake.config.GameConfig;
import com.jga.snake.system.passive.EntityFactorySystem;
import com.jga.snake.system.passive.SoundSystem;
import com.jga.snake.util.Mappers;

public class CollisionSystem extends IntervalSystem {
    //constants
    private static final Family SNAKE_FAMILY=Family.all(SnakeComponent.class).get();
    private static final Family COIN_FAMILY= Family.all(CoinComponent.class).get();

    //attributes
    private EntityFactorySystem factory;
    private SoundSystem listener;

    //constructors
    public CollisionSystem(){
        super(GameConfig.MOVE_TIME);


    }

    //process
    @Override
    protected void updateInterval() {

        Engine engine=getEngine();
        ImmutableArray<Entity> snakes=engine.getEntitiesFor(SNAKE_FAMILY);
        ImmutableArray<Entity> coins=getEngine().getEntitiesFor(COIN_FAMILY);
        factory=engine.getSystem(EntityFactorySystem.class);
        listener=getEngine().getSystem(SoundSystem.class);
        //head <->body parts
        for (Entity snakeEntity:snakes
             ) {
            SnakeComponent snake=Mappers.SNAKE.get(snakeEntity);
            for (Entity bodyPartEntity: snake.bodyParts
                 ) {
                BodyPartComponent bodyPart=Mappers.BODY_PART.get(bodyPartEntity);
                if(!bodyPart.justAdded&&overlaps(snake.head,bodyPartEntity)){
                    listener.lose();
                    GameManager.INSTANCE.updateHighScore();
                    GameManager.INSTANCE.setGameOver();
                }
                if(bodyPart.justAdded){
                    bodyPart.justAdded=false;
                }

            }


        }

        //head <-> coin
        for (Entity snakeEntity:snakes
             ) {
            for (Entity coinEntity:coins
                 ) {
                CoinComponent coin= Mappers.COIN.get(coinEntity);
                SnakeComponent snake=Mappers.SNAKE.get(snakeEntity);

                if(coin.available&&overlaps(snake.head,coinEntity)){
                    //mark coin as not available
                    coin.available=false;

                    //get head position and add body part
                    PositionComponent position=Mappers.POSITION.get(snake.head);
                    Entity bodyPart=factory.createBodyPart(position.x,position.y);
                    snake.bodyParts.insert(0,bodyPart);
                    GameManager.INSTANCE.incrementScore(GameConfig.COIN_SCORE);
                    listener.hitCoin();

                }


            }
        }
    }

    //private methods
    private static boolean overlaps(Entity first,Entity second){
        BoundsComponent firstBounds=Mappers.BOUNDS.get(first);
        BoundsComponent secondBounds=Mappers.BOUNDS.get(second);

        return Intersector.overlaps(firstBounds.rectangle,secondBounds.rectangle);


    }

}
