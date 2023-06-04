package org.voxelgame.client.engine;

public interface IGameLogic {
    void init(Window window, Scene scene, Render render);
    void input(Window window, Scene scene, long diffTimeMillis);
    void update(Window window, Scene scene, long diffTimeMillis);
}
