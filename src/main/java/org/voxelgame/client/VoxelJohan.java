package org.voxelgame.client;

import org.voxelgame.client.engine.CubeModel;
import org.voxelgame.client.engine.Model;
import org.voxelgame.client.engine.Voxel;

public class VoxelJohan extends Voxel {

    public static final String MODEL_ID = "voxel-johan";
    public static final String VOXEL_TYPE = "voxel-johan";
    public static final String TEXTURE_PATH = "resources/models/johan/johan.png";

    public VoxelJohan(long x, long y, long z) {
        super(x, y, z);
    }

    public Model getModel() {
        return cubeModel;
    }

    public static CubeModel cubeModel = new CubeModel(MODEL_ID, TEXTURE_PATH);
}
