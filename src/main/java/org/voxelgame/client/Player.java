package org.voxelgame.client;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.voxelgame.client.packet.ClientEntityPosition;

import java.io.IOException;

import static org.voxelgame.client.engine.GameEngine.scene;

public class Player {
    private double x, y, z;
    private Vector2f rotation;
    private String username;

    private PlayerController playerController;

    public Player(String username) {
        x = 0;
        y = 0;
        z = 0;
        rotation = new Vector2f(0.0f, 0.0f);
        playerController = new PlayerController();
        this.username = username;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public double getZ() {
        return z;
    }

    public Vector2f getRotation() {
        return rotation;
    }


    public void setRotation(float x, float y) {
        rotation.set(x, y);
    }

    public void addRotation(float x, float y) {
        scene.getCamera().addRotation(x, y);
        setRotation(rotation.x += x, rotation.y += y);
    }

    public void setPosition(Vector3f pos) {
        x = pos.x;
        y = pos.y;
        z = pos.z;
        scene.getCamera().setPosition((float)x, (float) y, (float) z);

        try {
            Main.clientConnection.sendPacket(new ClientEntityPosition(x, y, z, username.hashCode()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

}
