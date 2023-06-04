package org.voxelgame.client.engine;

import java.util.*;

public class Scene {

    private Map<String, Model> modelMap;
    private Map<Long, Entity> entityMap;
    private Map<String, Voxel> voxelMap;
    private Projection projection;

    public Camera camera;

    private TextureCache textureCache;

    public Scene(int width, int height) {
        modelMap = new HashMap<>();
        entityMap = new HashMap<>();
        voxelMap = new HashMap<>();
        projection = new Projection(width, height);
        textureCache = new TextureCache();
        camera = new Camera();
    }

    public void addEntity(Entity entity) {
        String modelId = entity.getModelId();
        Model model = modelMap.get(modelId);
        entityMap.put(entity.getId(), entity);
        if (model == null) {
            throw new RuntimeException("Could not find model [" + modelId + "]");
        }
        model.getEntitiesList().add(entity);
    }

    public void addVoxel(Voxel voxel) {
        if (voxel.getModel() != null) { //Check for Air voxels
            String modelId = voxel.getModel().getId();
            Model model = modelMap.get(modelId);
            //System.out.println(voxel.getPosition().toString());
            voxelMap.put(voxel.getId(), voxel);
            if (model == null) {
                throw new RuntimeException("Could not find model [" + modelId + "]");
            }
            model.getVoxelsList().add(voxel);
        }
    }

    public void removeVoxel(Voxel voxel) {
        if (voxel.getModel() != null) {
            String modelId = voxel.getModel().getId();
            Model model = modelMap.get(modelId);
            //System.out.println(voxel.getPosition().toString());
            voxelMap.remove(voxel.getId());
            model.getVoxelsList().remove(voxel);
        }
    }

    public void addModel(Model model) {
        if (model != null) {
            modelMap.put(model.getId(), model);
        } else {
            //System.out.println("Air block or null model");
        }
    }

    public Entity getEntity(String entityID) {
        return entityMap.get(entityID);
    }

    public void cleanup() {
        modelMap.values().stream().forEach(Model::cleanup);
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }

    public Projection getProjection() {
        return projection;
    }

    public Camera getCamera() {
        return camera;
    }

    public TextureCache getTextureCache() {
        return textureCache;
    }

    public void resize(int width, int height) {
        projection.updateProjMatrix(width, height);
    }
}