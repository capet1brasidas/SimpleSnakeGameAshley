package com.jga.snake.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.jga.snake.component.PositionComponent;
import com.jga.snake.component.WorldWrapComponent;
import com.jga.snake.config.GameConfig;
import com.jga.snake.util.Mappers;

public class WorldWrapSystem extends IteratingSystem {

    //constants
    private static final Family FAMILY= Family.all(
            PositionComponent.class,
            WorldWrapComponent.class
    ).get();
    //constructors
    public WorldWrapSystem(){
        super(FAMILY);
    }

    //process

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position= Mappers.POSITION.get(entity);

        //check x bounds(left/right)
        if(position.x>= GameConfig.WORLD_WIDTH){
            position.x=0;
        } else if (position.x<0) {
            position.x=GameConfig.WORLD_WIDTH-GameConfig.SNAKE_SPEED;
        }

        //check y bounds(up/down)
        if (position.y>=GameConfig.MAX_Y) {
            position.y=0;
        } else if (position.y<0) {
            position.y=GameConfig.MAX_Y-GameConfig.SNAKE_SPEED;
        }

    }

}
