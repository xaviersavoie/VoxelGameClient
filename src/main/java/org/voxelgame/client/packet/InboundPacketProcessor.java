package org.voxelgame.client.packet;

import org.voxelgame.client.Main;
import org.voxelgame.client.engine.Entity;

public class InboundPacketProcessor {
    public static void processServerEntityPositionPacket(ServerEntityPosition packet) { //TODO: POOP AS FUCK LOL
        boolean entityAlreadyExists = Main.worldClient.getLoadedEntities().get(packet.entityID) != null;

        if (!entityAlreadyExists) {
            System.out.println("Creating and adding entity");
            Main.worldClient.addEntity(new Entity(packet.x, packet.y, packet.z, packet.entityID));
        } else {
            System.out.println("Loading packet normally");
            Main.worldClient.getLoadedEntities().get(packet.entityID).setPosition((float)packet.x, (float)packet.y, (float)packet.z).updateModelMatrix();
        }
    }
}
