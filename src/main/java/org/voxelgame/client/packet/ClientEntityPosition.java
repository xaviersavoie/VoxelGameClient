package org.voxelgame.client.packet;

import org.voxelgame.client.CPacket;

public class ClientEntityPosition implements CPacket {
    double x, y, z;
    long entityID;

    public ClientEntityPosition(double x, double y, double z, long entityID) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.entityID = entityID;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setEntityID(long entityID) {
        this.entityID = entityID;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public long getEntityID() {
        return entityID;
    }

    @Override
    public String serialize() {
        return PacketSerializer.serializeClientEntityPosition(this);
    }
}