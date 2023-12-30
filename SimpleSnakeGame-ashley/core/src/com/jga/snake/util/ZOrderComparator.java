package com.jga.snake.util;

import com.badlogic.ashley.core.Entity;

import java.util.Comparator;

public class ZOrderComparator implements Comparator<Entity> {

    //constants
    public static final ZOrderComparator INSTANCE=new ZOrderComparator();

    //constructor
    private ZOrderComparator(){}

    //public methods


    @Override
    public int compare(Entity entity1,Entity entity2) {

        return Integer.compare(
                Mappers.Z_ORDER.get(entity1).z,
                Mappers.Z_ORDER.get(entity2).z
        );

    }
}
