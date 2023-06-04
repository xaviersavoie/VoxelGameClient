package org.voxelgame.client;

import org.joml.Vector3f;

public class PlayerController {

    public void addRotation(Player player, float x, float y) {
        player.addRotation(x, y);
    }

    public void walkRight(Player player, float inc) {
        double rotY = (player.getRotation().y ) % 6.28f;
        double disZ = player.getZ() + (Math.sin(rotY) * inc);
        double disX = player.getX() + (Math.cos(rotY) * inc);
        Vector3f vector3f = new Vector3f( (float) disX, (float) player.getY(), (float) disZ);
        player.setPosition(vector3f);
    }


    public void walkLeft(Player player, float inc) {
        double rotY = (player.getRotation().y + 3.14) % 6.28f;
        double disZ = player.getZ() + (Math.sin(rotY) * inc);
        double disX = player.getX() + (Math.cos(rotY) * inc);
        Vector3f vector3f = new Vector3f( (float) disX, (float) player.getY(), (float) disZ);
        player.setPosition(vector3f);
    }

    public void walkForward(Player player, float inc) {
        double rotY = (player.getRotation().y - 1.57) % 6.28f;
        double disZ = player.getZ() + (Math.sin(rotY) * inc);
        double disX = player.getX() + (Math.cos(rotY) * inc);
        Vector3f vector3f = new Vector3f( (float) disX, (float) player.getY(), (float) disZ);
        player.setPosition(vector3f);
    }

    public void walkBackwards(Player player, float inc) {
        double rotY = (player.getRotation().y + 1.57) % 6.28f;
        double disZ = player.getZ() + (Math.sin(rotY) * inc);
        double disX = player.getX() + (Math.cos(rotY) * inc);
        Vector3f vector3f = new Vector3f( (float) disX, (float) player.getY(), (float) disZ);
        player.setPosition(vector3f);
    }


    public void jump(Player player, float inc) {
        player.setPosition(new Vector3f((float) player.getX(), (float) (player.getY() + inc), (float) player.getZ()));
    }

    public void crouch(Player player, float inc) {
        player.setPosition(new Vector3f((float) player.getX(), (float) (player.getY() - inc), (float) player.getZ()));
    }


}
