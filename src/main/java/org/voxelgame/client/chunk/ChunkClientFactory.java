package org.voxelgame.client.chunk;

import org.voxelgame.client.engine.Voxel;

import java.util.ArrayList;

public class ChunkClientFactory {
    public static ChunkClient createChunkClient(ArrayList<Voxel> chunkData, long ChunkCoordX, long ChunkCoordY, long ChunkCoordZ) {
            return new ChunkClient(chunkData, ChunkCoordX, ChunkCoordY, ChunkCoordZ);
    }

}
