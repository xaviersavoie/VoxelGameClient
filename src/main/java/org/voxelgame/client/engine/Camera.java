package org.voxelgame.client.engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private Vector3f direction;
    private Vector3f position; //Position of the camera (eg. player head position)
    private Vector2f rotation; //Rotation of the camera
    private Vector3f right;
    private Vector3f up;
    private Matrix4f viewMatrix;

    public Camera() {
        direction = new Vector3f();
        position = new Vector3f();
        rotation = new Vector2f();
        right = new Vector3f();
        up = new Vector3f();
        viewMatrix = new Matrix4f();
    }

    public void addRotation(float x, float y) {
        setRotation(rotation.x += x, rotation.y += y);
        recalculate();
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector2f getRotation()  {
        return rotation;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void moveBackwards(float inc) {
        viewMatrix.positiveZ(direction).negate();
        direction.mul(inc);
        position.sub(direction);
        recalculate();
    }


    public void moveDown(float inc) {
        viewMatrix.positiveY(up).mul(inc); //I don't understand
        position.sub(up);
        recalculate();
    }


    public void moveForward(float inc) {

        viewMatrix.positiveZ(direction).negate(); //I don't understand
        direction.mul(inc);
        position.add(direction);
        recalculate();

    }


    public void moveLeft(float inc) {
        viewMatrix.positiveX(right).mul(inc); //I don't understand
        position.sub(right);
        recalculate();
    }

    public void moveRight(float inc) {
        viewMatrix.positiveX(right).mul(inc); //I don't understand
        position.add(right);
        recalculate();
    }


    public void moveUp(float inc) {
        viewMatrix.positiveY(up).mul(inc); //I don't understand
        position.add(up);
        recalculate();
    }


    public void recalculate() {
        if (rotation.x < 0) {
            rotation.x = 6.28f;
        }
        if (rotation.x > 6.28f) {
            rotation.x = 0;
        }

        if (rotation.y < 0) {
            rotation.y = 6.28f;
        }
        if (rotation.y > 6.28f) {
            rotation.y = 0;
        }
        viewMatrix.identity()
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .translate(-position.x, -position.y, -position.z);
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        recalculate();
    }


    public void setRotation(float x, float y) {
        rotation.set(x, y);
        recalculate();
    }

    public Vector3f getRight() {
        return right;
    }

    public Vector3f getUp() {
        return up;
    }
}
