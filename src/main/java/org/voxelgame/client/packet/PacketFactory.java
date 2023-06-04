package org.voxelgame.client.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.voxelgame.client.JSONVoxelFactory;
import org.voxelgame.client.engine.Voxel;

import java.util.ArrayList;

public class PacketFactory {
    private static ObjectMapper objectMapper = new ObjectMapper();


    //CLIENTBOUND CHUNK DATA
    public static ServerPacketChunkData createServerPacketChunkDataFromJSONData(String jsonIn) {

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(jsonIn);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Voxel> voxels = new ArrayList<>();
        JsonNode voxelDataNode = jsonNode.get("voxelData");
        if (voxelDataNode.isArray()) {
            for(JsonNode iteratedJson : voxelDataNode) {
                voxels.add(JSONVoxelFactory.createVoxelFromJSONNode(iteratedJson));
            }
        }

        JsonNode chunkPosNode = jsonNode.get("chunkPos");
        long[] chunkPos = new long[3];
        int chunkPosCount = 0;
        if (chunkPosNode.isArray()) {
            for(JsonNode iteratedJson : chunkPosNode) {
                chunkPos[chunkPosCount] = iteratedJson.asLong();
                chunkPosCount++;
            }
        }

        ServerPacketChunkData clientPacketChunkData = new ServerPacketChunkData(voxels, chunkPos);
        return clientPacketChunkData;
    }

    public static ServerEntityPosition createServerEntityPositionFromJSONData(String jsonIn) {

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(jsonIn);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode entityPosNode = jsonNode.get("pos");
        double[] entityPosArr = new double[3];
        int entityPosCount = 0;
        if (entityPosNode.isArray() && entityPosNode.size() == 3) {
            for(JsonNode iteratedJson : entityPosNode) {
                entityPosArr[entityPosCount] = iteratedJson.asDouble();
                entityPosCount++;
            }
        }


        JsonNode entityIDNode = jsonNode.get("entityID");
        long entityID = entityIDNode.asLong();
        return new ServerEntityPosition(entityPosArr[0], entityPosArr[1], entityPosArr[2], entityID);
    }
}
