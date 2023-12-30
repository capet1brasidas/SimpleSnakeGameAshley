package com.jga.snake.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.jga.snake.component.BoundsComponent;
import com.jga.snake.component.DimensionComponent;
import com.jga.snake.component.PositionComponent;
import com.jga.snake.util.Mappers;

public class BoundsSystem extends IteratingSystem {
    //constants
    private static final Family FAMILY= Family.all(
            BoundsComponent.class,
            DimensionComponent.class,
            PositionComponent.class
    ).get();

    //constructor
    public BoundsSystem(){
        super(FAMILY);
    }

    //process


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position= Mappers.POSITION.get(entity);
        DimensionComponent dimension=Mappers.DIMENSION.get(entity);
        BoundsComponent bounds=Mappers.BOUNDS.get(entity);

        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

    }
}
