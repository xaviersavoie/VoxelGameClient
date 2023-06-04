package org.voxelgame.client.engine;

public class Color {
    public float[] color = new float[3];

    public Color(int red, int green, int blue) {
        this.color[0] = (float) red/255;
        this.color[1] = (float) green/255;
        this.color[2] = (float) blue/255;
        System.out.println(color[0] +  " " + color[1] + " " + color[2]);
    }

    public float[] getFloatArrayValue() {
        return color;
    }
}
