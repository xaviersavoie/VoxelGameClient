package org.voxelgame.client.packet;

import org.voxelgame.client.SPacket;
import org.voxelgame.client.chunk.ChunkClient;
import org.voxelgame.client.chunk.ChunkClientFactory;
import org.voxelgame.client.engine.Voxel;

import java.util.ArrayList;

public class ServerPacketChunkData implements SPacket {

    private ArrayList<Voxel> voxelsList;
    private long[] chunkPos;

    public ServerPacketChunkData(ArrayList<Voxel> voxels, long[] chunkPos) {

        this.voxelsList = voxels;

        if (chunkPos.length == 3) {
            this.chunkPos = chunkPos;
        } else { return; }

    }

    public ChunkClient toChunkClient() {
        return ChunkClientFactory.createChunkClient(voxelsList, chunkPos[0], chunkPos[1], chunkPos[2]);
    }

    @Override
    public String serialize() {

        return null;
    }
}
