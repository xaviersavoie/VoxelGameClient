package org.voxelgame.client.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PacketSerializer {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String serializeClientEntityPosition(ClientEntityPosition clientEntityPosition) {
        ObjectNode parentPacketNode = objectMapper.createObjectNode();
        parentPacketNode.put("packetType", "ClientEntityPosition");
        parentPacketNode.put("entityID", clientEntityPosition.entityID);
        parentPacketNode.putArray("position").add(clientEntityPosition.x).add(clientEntityPosition.y).add(clientEntityPosition.z);

        String out;
        try {

            System.out.println(parentPacketNode.toPrettyString());

            out = objectMapper.writeValueAsString(parentPacketNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
}
