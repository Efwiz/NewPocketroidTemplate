package net.mgsx.gltf.loaders.shared.texture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import net.mgsx.gltf.data.texture.GLTFSampler;
import net.mgsx.gltf.data.texture.GLTFTexture;
import net.mgsx.gltf.data.texture.GLTFTextureInfo;
import net.mgsx.gltf.loaders.exceptions.GLTFRuntimeException;
import net.mgsx.gltf.loaders.shared.GLTFTypes;

/* JADX INFO: loaded from: classes12.dex */
public class TextureResolver implements Disposable {
    protected Array<GLTFSampler> glSamplers;
    protected Array<GLTFTexture> glTextures;
    protected final ObjectMap<Integer, Texture> texturesSimple = new ObjectMap<>();
    protected final ObjectMap<Integer, Texture> texturesMipmap = new ObjectMap<>();

    public void loadTextures(Array<GLTFTexture> array, Array<GLTFSampler> array2, ImageResolver imageResolver) {
        this.glTextures = array;
        this.glSamplers = array2;
        if (array != null) {
            for (int i = 0; i < array.size; i++) {
                GLTFTexture gLTFTexture = array.get(i);
                boolean z = gLTFTexture.sampler != null && GLTFTypes.isMipMapFilter(array2.get(gLTFTexture.sampler.intValue()));
                ObjectMap<Integer, Texture> objectMap = z ? this.texturesMipmap : this.texturesSimple;
                if (!objectMap.containsKey(gLTFTexture.source)) {
                    objectMap.put(gLTFTexture.source, new Texture(imageResolver.get(gLTFTexture.source.intValue()), z));
                }
            }
        }
    }

    public TextureDescriptor<Texture> getTexture(GLTFTextureInfo gLTFTextureInfo) {
        boolean zIsMipMapFilter;
        GLTFTexture gLTFTexture = this.glTextures.get(gLTFTextureInfo.index.intValue());
        TextureDescriptor<Texture> textureDescriptor = new TextureDescriptor<>();
        if (gLTFTexture.sampler != null) {
            GLTFSampler gLTFSampler = this.glSamplers.get(gLTFTexture.sampler.intValue());
            GLTFTypes.mapTextureSampler(textureDescriptor, gLTFSampler);
            zIsMipMapFilter = GLTFTypes.isMipMapFilter(gLTFSampler);
        } else {
            textureDescriptor.minFilter = Texture.TextureFilter.Linear;
            textureDescriptor.magFilter = Texture.TextureFilter.Linear;
            textureDescriptor.uWrap = Texture.TextureWrap.Repeat;
            textureDescriptor.vWrap = Texture.TextureWrap.Repeat;
            zIsMipMapFilter = false;
        }
        Texture texture = (zIsMipMapFilter ? this.texturesMipmap : this.texturesSimple).get(gLTFTexture.source);
        if (texture == null) {
            throw new GLTFRuntimeException("texture not loaded");
        }
        textureDescriptor.texture = texture;
        return textureDescriptor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        ObjectMap.Entries<Integer, Texture> it = this.texturesSimple.iterator();
        while (it.hasNext()) {
            ((Texture) it.next().value).dispose();
        }
        this.texturesSimple.clear();
        ObjectMap.Entries<Integer, Texture> it2 = this.texturesMipmap.iterator();
        while (it2.hasNext()) {
            ((Texture) it2.next().value).dispose();
        }
        this.texturesMipmap.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Array<Texture> getTextures(Array<Texture> array) {
        ObjectMap.Entries<Integer, Texture> it = this.texturesSimple.iterator();
        while (it.hasNext()) {
            array.add(it.next().value);
        }
        ObjectMap.Entries<Integer, Texture> it2 = this.texturesMipmap.iterator();
        while (it2.hasNext()) {
            array.add(it2.next().value);
        }
        return array;
    }
}
