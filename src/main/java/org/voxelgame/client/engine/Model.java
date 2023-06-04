package org.voxelgame.client.engine;


import java.util.ArrayList;
import java.util.List;


public class Model {

    private Mesh modelMesh;
    private List<Entity> entitiesList;
    private List<Voxel> voxelsList;
    private final String id;
    private String texturePath;

    public Model(String id, Mesh mesh, String texturePath) {
        this.modelMesh = mesh;
        this.id = id;
        entitiesList = new ArrayList<>();
        voxelsList = new ArrayList<>();
        this.texturePath = texturePath;
        GameEngine.scene.getTextureCache().createTexture(texturePath);
    }

    /*
    public Model(String id, Mesh mesh) {
        this.modelMesh = mesh;
        this.id = id;
        entitiesList = new ArrayList<>();
        this.texturePath = "resources/default/default.png";
        getScene().getTextureCache().createTexture(texturePath);
    }
    */

    public List<Entity> getEntitiesList() {
        return entitiesList;
    }

    public List<Voxel> getVoxelsList() {
        return voxelsList;
    }

    public Mesh getMesh() {
        return modelMesh;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public String getId() {
        return id;
    }

    public void cleanup() {
        modelMesh.cleanup();
    }
}
