package org.voxelgame.client;

import org.joml.Vector2f;
import org.voxelgame.client.engine.*;
import org.voxelgame.client.engine.*;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static float MOVEMENT_SPEED = 0.005f;

    public static ClientConnection clientConnection; //Only one

    public static WorldClient worldClient;
    Player player;
    Camera camera;

    public static void main(String [] args) {
        Main main = new Main();
        GameEngine eng = new GameEngine("VoxelGame", new Window.WindowOptions(), main);
        eng.start();
    }

    @Override
    public void init(Window window, Scene scene, Render render) {

        worldClient = new WorldClient();
        player = worldClient.getPlayer();
        camera = scene.getCamera();

        try {
            clientConnection = new ClientConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void input (Window window, Scene scene,long diffTimeMillis) {

            if (!window.isESC_MODE()) {

                float move = diffTimeMillis * MOVEMENT_SPEED;
                if (window.isKeyPressed(GLFW_KEY_W)) {
                    player.getPlayerController().walkForward(player, move);
                } else if (window.isKeyPressed(GLFW_KEY_S)) {
                    player.getPlayerController().walkBackwards(player, move);
                }
                if (window.isKeyPressed(GLFW_KEY_A)) {
                    player.getPlayerController().walkLeft(player, move);
                } else if (window.isKeyPressed(GLFW_KEY_D)) {
                    player.getPlayerController().walkRight(player, move);
                }
                if (window.isKeyPressed(GLFW_KEY_UP)) {
                    MOVEMENT_SPEED += 0.001f;
                } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
                    MOVEMENT_SPEED -= 0.001f;
                }
                if (window.isKeyPressed(GLFW_KEY_SPACE)) {
                    player.getPlayerController().jump(player, move);
                } else if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
                    player.getPlayerController().crouch(player, move);
                }
                if (window.isKeyPressed(GLFW_KEY_R)) {
                    if (worldClient.getChunk("x16y0z16") != null) {
                        worldClient.removeChunk("x16y0z16", scene);
                    }
                }

                MouseInput mouseInput = window.getMouseInput();
                Vector2f displVec = mouseInput.getDisplVec();

                player.addRotation((float) Math.toRadians((displVec.x * MOUSE_SENSITIVITY)),
                        (float) Math.toRadians((displVec.y * MOUSE_SENSITIVITY)));
            }
        }

        @Override
        public void update (Window window, Scene scene,long diffTimeMillis) {
            clientConnection.processPacketsInUpdateLoop();
        }
    }


