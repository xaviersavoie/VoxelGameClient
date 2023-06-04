package org.voxelgame.client.engine;


import java.util.*;

import static org.lwjgl.opengl.GL30.*;

public class SceneRender {

    private ShaderProgram shaderProgram;
    private UniformsMap uniformsMap;

    public SceneRender() {
        List<ShaderProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER));
        shaderProgram = new ShaderProgram(shaderModuleDataList);

        createUniforms();

    }

    public void cleanup() {
        shaderProgram.cleanup();
    }

        public void render(Scene scene) {
            shaderProgram.bind();
            uniformsMap.setUniform("projectionMatrix", scene.getProjection().getProjMatrix());
            uniformsMap.setUniform("txtSampler", 0);
            uniformsMap.setUniform("viewMatrix", scene.getCamera().getViewMatrix());
            Collection<Model> models = scene.getModelMap().values();
            TextureCache textureCache = scene.getTextureCache();
            for (Model model : models) {
                    glBindVertexArray(model.getMesh().getVaoId());
                    List<Entity> entities = model.getEntitiesList();
                    List<Voxel> voxels = model.getVoxelsList();

                    Texture texture = textureCache.getTexture(model.getTexturePath());
                    glActiveTexture(GL_TEXTURE0);
                    texture.bind();

                    for (Entity entity : entities) {
                        uniformsMap.setUniform("modelMatrix", entity.getModelMatrix());
                        glDrawElements(GL_TRIANGLES, model.getMesh().getNumVertices(), GL_UNSIGNED_INT, 0);
                    }

                    for (Voxel voxel : voxels) {
                        uniformsMap.setUniform("modelMatrix", voxel.getModelMatrix());
                        glDrawElements(GL_TRIANGLES, model.getMesh().getNumVertices(), GL_UNSIGNED_INT, 0);
                    }
            }
            glBindVertexArray(0);
            shaderProgram.unbind();
        }

    public void createUniforms() {
        uniformsMap = new UniformsMap(shaderProgram.getProgramId());
        uniformsMap.createUniform("projectionMatrix");
        uniformsMap.createUniform("modelMatrix");
        uniformsMap.createUniform("txtSampler");
        uniformsMap.createUniform("viewMatrix");
    }


}
