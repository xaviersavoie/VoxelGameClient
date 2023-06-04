package org.voxelgame.client.engine;

import java.util.HashMap;
import java.util.Map;

public class TextureCache {
    public static final String DEFAULT_TEXTURE = "resources/models/default/default.png";

    private Map<String, Texture> textureMap;

    public TextureCache() {
        textureMap = new HashMap<>();
        textureMap.put(DEFAULT_TEXTURE, new Texture(DEFAULT_TEXTURE));
    }

    public void cleanup() {
        textureMap.values().stream().forEach(Texture::cleanup);
    }

    public Texture createTexture(String texturePath) {
        return textureMap.computeIfAbsent(texturePath, Texture::new);
    }

    public Texture getTexture(String texturePath) { //Get the texture object from the texturePath, which we use as a key for the texture HashMap. If it can't be found, the default texture is returned instead.
        Texture texture = null;
        if (texturePath != null) {
            texture = textureMap.get(texturePath);
        }
        if(texturePath == null) {
            texture = textureMap.get(DEFAULT_TEXTURE);
        }
        return texture;
    }
}
