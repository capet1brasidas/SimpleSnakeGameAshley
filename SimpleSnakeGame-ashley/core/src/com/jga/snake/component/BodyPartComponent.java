package com.jga.snake.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class BodyPartComponent implements Component, Pool.Poolable {

    //note:just a flag to indicate that body part was just added
    //we need to avoid collision with just added body parts

    public boolean justAdded=true;

    //public methods



    @Override
    public void reset() {
        justAdded=true;
    }
}
