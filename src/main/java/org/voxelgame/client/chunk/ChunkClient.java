package org.voxelgame.client.chunk;

import org.voxelgame.client.engine.Voxel;

import java.util.ArrayList;

public class ChunkClient {
    private ArrayList<Voxel> chunkData;
    private long x, y, z;

    //TODO: Premature optimization is the root of all evil. Please memory optimize later though!!!!!!

    protected ChunkClient(ArrayList<Voxel> chunkData, long chunkPosX, long chunkPosY, long chunkPosZ) {
        System.out.println("New chunk");

        System.out.println("chunkPosX: " + chunkPosX + "\nchunkPosY" + chunkPosY + "\nchunkPosZ: " + chunkPosZ);

        chunkData.forEach(voxel -> {
            if (voxel != null)
            voxel.addX(chunkPosX).addY(chunkPosY).addZ(chunkPosZ);
        });

        this.chunkData = chunkData;
        this.x =  chunkPosX;
        this.y =  chunkPosY;
        this.z =  chunkPosZ;
    }

    public Voxel getVoxelAt(int chunkCoordX, int chunkCoordY, int chunkCoordZ) {
        return null; //for now
    }

    public ArrayList<Voxel> getChunkData() {
        return chunkData;
    }

    public String getChunkHash() {
        return "x" + x + "y" + y +  "z" + z;
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
}
