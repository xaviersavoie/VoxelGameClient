package org.voxelgame.client.engine;

import org.joml.*;

public class Entity {

    private final long id;
    private Matrix4f modelMatrix;
    private Vector3f position;
    private Quaternionf rotation;
    private float scale;

    public static final String MODEL_ID = "voxel-johan";
    public static final String TEXTURE_PATH = "resources/models/johan/johan.png";

    public Entity(double x, double y, double z, long entityID) {
        this.id = entityID;
        modelMatrix = new Matrix4f();
        position = new Vector3f((float)x, (float)y , (float)z);
        rotation = new Quaternionf();
        scale = 1;
    }

    public long getId() {
        return id;
    }

   public String getModelId() {
        return MODEL_ID;
   }

   public Model getModel() {
        return cubeModel;
   }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public Entity setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
        return this;
    }

    public void setRotation(float x, float y, float z, float angle) {
        this.rotation.fromAxisAngleRad(x, y, z, angle);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void updateModelMatrix() {
        modelMatrix.translationRotateScale(position, rotation, scale);
    }

    public static CubeModel cubeModel = new CubeModel(MODEL_ID, TEXTURE_PATH);
}
