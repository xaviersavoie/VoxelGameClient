package org.voxelgame.client;

import org.voxelgame.client.engine.Model;
import org.voxelgame.client.engine.Voxel;

public class VoxelAir extends Voxel {

    public static final String VOXEL_TYPE = "voxel-air";

    public VoxelAir(long x, long y, long z) {
        super(x, y, z);
    }

    public Model getModel() {
        return null;
    }


}
