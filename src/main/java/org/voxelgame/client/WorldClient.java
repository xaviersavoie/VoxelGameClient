package org.voxelgame.client;

import org.voxelgame.client.chunk.ChunkClient;
import org.voxelgame.client.engine.Entity;
import org.voxelgame.client.engine.Scene;
import org.voxelgame.client.engine.Voxel;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.voxelgame.client.engine.GameEngine.scene;

public class WorldClient {
    private Map<String, ChunkClient> loadedChunks;
    private HashMap<Long, Entity> loadedEntities;
    private Player player;

    public WorldClient() {
        loadedChunks = new HashMap<>();
        loadedEntities = new HashMap<>();
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        System.out.println(generatedString);
        player = new Player(generatedString);
    }

    public void debugPrintLoadedChunks() {
        loadedChunks.entrySet().forEach(entry -> {
            //System.out.println(entry.getKey() + " " + entry.getValue());
        });
    }

    public void addChunk(String key, ChunkClient chunk) {
        loadedChunks.put(key, chunk);
        ArrayList<Voxel> chunkData = chunk.getChunkData();
        chunkData.iterator().forEachRemaining((voxel -> {
            if (voxel != null) {
                scene.addModel(voxel.getModel());
                scene.addVoxel(voxel);
                voxel.updateModelMatrix();
            }
        }));
    }

    public void addEntity(Entity entity) {
        loadedEntities.put(entity.getId(), entity);
        scene.addModel(entity.getModel());
        scene.addEntity(entity);
        entity.updateModelMatrix();
    }

    public HashMap<Long, Entity> getLoadedEntities() {
        return loadedEntities;
    }

    public void removeChunk(String key, Scene scene) {
        ChunkClient chunk = loadedChunks.get(key);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    //System.out.println(i+ " " + j + " " + k);
                    Voxel temp = chunk.getVoxelAt(i, j, k);
                    scene.removeVoxel(temp);
                }
            }
        }
        System.out.println("Before chunk removal");
        loadedChunks.remove(key);
    }

    public ChunkClient getChunk(String key) {
        return loadedChunks.get(key);
    }

    public Player getPlayer() {
        return player;
    }
}
