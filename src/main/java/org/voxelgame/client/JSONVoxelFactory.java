package org.voxelgame.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.voxelgame.client.engine.Voxel;

public class JSONVoxelFactory {

    public static Voxel createVoxelFromJSONNode(JsonNode node) {

        long[] posArray = new long[3];
        JsonNode posNode = node.get("position");
        if (posNode != null && posNode.isArray()) {

            int count = 0;
            for(JsonNode iteratedJson : posNode) {
                posArray[count] = iteratedJson.asLong();
                count++;
            }
            JsonNode voxelTypeNode = node.get("voxel-type");
            if (voxelTypeNode != null) {
                return switch (voxelTypeNode.asText()) {
                    case "voxel-dirt" -> new VoxelDirt(posArray[0], posArray[1], posArray[2]);
                    case "voxel-johan" -> new VoxelJohan(posArray[0], posArray[1], posArray[2]);
                    default -> new VoxelAir(posArray[0], posArray[1], posArray[2]);
                };
            }
        } //If pos data is not an array, return null
        return null;
    }
}
