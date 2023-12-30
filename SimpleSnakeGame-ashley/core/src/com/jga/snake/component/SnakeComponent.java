package com.jga.snake.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;

public class SnakeComponent implements Component, Pool.Poolable {
    //constants

    //attributes
    public Entity head;
    public final Array<Entity> bodyParts=new Array<>();

    //public methods
    @Override
    public void reset() {

        head=null;
        bodyParts.clear();

    }

        public boolean hasBodyParts(){
        return bodyParts.size>0;
        }
}
