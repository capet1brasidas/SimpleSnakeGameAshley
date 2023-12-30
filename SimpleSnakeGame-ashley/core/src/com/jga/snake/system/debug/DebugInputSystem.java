package com.jga.snake.system.debug;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class DebugInputSystem extends EntitySystem {

    //attributes
    private boolean debugGrid=false;
    private boolean debugRender=false;
    private EntitySystem gridRenderSystem;
    private EntitySystem debugRenderSystem;

    //constructors


    public DebugInputSystem() {
    }

    //public methods


    @Override
    public void addedToEngine(Engine engine) {
        gridRenderSystem=engine.getSystem(GridRenderSystem.class);
        debugRenderSystem=engine.getSystem(DebugRenderSystem.class);
        toggleSystems();




    }

    @Override
    public void update(float deltaTime) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
            debugGrid=!debugGrid;
            toggleSystems();
        }else if(Gdx.input.isKeyJustPressed(Input.Keys.G)){
            debugRender=!debugRender;
            toggleSystems();
        }
    }

    //private methods
    private void toggleSystems(){
        gridRenderSystem.setProcessing(debugGrid);
        debugRenderSystem.setProcessing(debugRender);



    }
}
