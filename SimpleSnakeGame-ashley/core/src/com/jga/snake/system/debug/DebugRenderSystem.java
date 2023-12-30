package com.jga.snake.system.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.snake.component.BoundsComponent;
import com.jga.snake.util.Mappers;

public class DebugRenderSystem extends IteratingSystem {

    //constants
    private static final Family FAMILY= Family.all(
            BoundsComponent.class
    ).get();
    //attributes
    private final Viewport viewport;
    private final ShapeRenderer renderer;

    //constructors
   public DebugRenderSystem(Viewport viewport,ShapeRenderer renderer){
       super(FAMILY);
       this.renderer=renderer;
       this.viewport=viewport;
   }


    @Override
    public void update(float deltaTime) {
        Color oldColor=renderer.getColor();
       //calls processEntity internally in for loop
        viewport.apply();;
        renderer.setColor(Color.RED);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        //
       super.update(deltaTime);

       renderer.end();
       renderer.setColor(oldColor);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bounds= Mappers.BOUNDS.get(entity);

        renderer.rect(bounds.rectangle.x,bounds.rectangle.y,
                bounds.rectangle.width,bounds.rectangle.height);
    }
}
