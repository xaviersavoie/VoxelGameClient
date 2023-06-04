package org.voxelgame.client.engine;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.voxelgame.client.VoxelJohan;

public abstract class Voxel {

    private long x, y, z;

    private Matrix4f modelMatrix;
    private Quaternionf rotation;
    private float scale;

    public Voxel(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;

        modelMatrix = new Matrix4f();
        rotation = new Quaternionf();
        scale = 1;

    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public String getId() {
        return (x + "" + y + "" + z);
    }

    public String getModelId() {
        return VoxelJohan.cubeModel.getId();
    }

    public Vector3f getPosition() {
        return new Vector3f(x, y, z);
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setPosition(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Voxel addX(long incX) {
        this.x += incX;
        return this;
    }

    public Voxel addY(long incY) {
        this.y +=incY;
        return this;
    }

    public Voxel addZ(long incZ) {
        this.z += incZ;
        return this;
    }

    public void setRotation(float x, float y, float z, float angle) {
        this.rotation.fromAxisAngleRad(x, y, z, angle);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void updateModelMatrix() {
        modelMatrix.translationRotateScale(new Vector3f(x, y, z), rotation, scale);
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    public Model getModel() {
        return null;
    }
}
