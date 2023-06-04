package org.voxelgame.client;

import org.voxelgame.client.engine.CubeModel;
import org.voxelgame.client.engine.Model;
import org.voxelgame.client.engine.Voxel;

public class VoxelDirt extends Voxel {

    public static final String MODEL_ID = "voxel-dirt";
    public static final String VOXEL_TYPE = "voxel-dirt";
    public static final String TEXTURE_PATH = "resources/models/dirt/dirt.png";

    public VoxelDirt(long x, long y, long z) {
        super(x, y, z);
    }

    public Model getModel() {
        return cubeModel;
    }

    public static CubeModel cubeModel = new CubeModel(MODEL_ID, TEXTURE_PATH);
}
