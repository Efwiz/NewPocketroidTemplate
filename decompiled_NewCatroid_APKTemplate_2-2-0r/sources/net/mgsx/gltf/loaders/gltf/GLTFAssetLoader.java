package net.mgsx.gltf.loaders.gltf;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import net.mgsx.gltf.data.GLTF;
import net.mgsx.gltf.data.texture.GLTFImage;
import net.mgsx.gltf.data.texture.GLTFSampler;
import net.mgsx.gltf.data.texture.GLTFTexture;
import net.mgsx.gltf.loaders.shared.GLTFLoaderBase;
import net.mgsx.gltf.loaders.shared.GLTFTypes;
import net.mgsx.gltf.loaders.shared.SceneAssetLoaderParameters;
import net.mgsx.gltf.loaders.shared.texture.ImageResolver;
import net.mgsx.gltf.loaders.shared.texture.TextureResolver;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFAssetLoader extends AsynchronousAssetLoader<SceneAsset, SceneAssetLoaderParameters> {
    private SeparatedDataFileResolver dataFileResolver;
    private ManagedTextureResolver textureResolver;

    private class ManagedTextureResolver extends TextureResolver {
        private GLTF glModel;
        private ObjectMap<Integer, AssetDescriptor<Texture>> textureDescriptorsSimple = new ObjectMap<>();
        private ObjectMap<Integer, AssetDescriptor<Texture>> textureDescriptorsMipMap = new ObjectMap<>();
        private ObjectMap<Integer, Pixmap> pixmaps = new ObjectMap<>();
        private ObjectMap<Integer, TextureLoader.TextureParameter> textureParameters = new ObjectMap<>();

        @Override // net.mgsx.gltf.loaders.shared.texture.TextureResolver
        public void loadTextures(Array<GLTFTexture> array, Array<GLTFSampler> array2, ImageResolver imageResolver) {
        }

        public ManagedTextureResolver(GLTF gltf) {
            this.glModel = gltf;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        public void fetch(AssetManager assetManager) {
            ObjectMap.Entries<Integer, AssetDescriptor<Texture>> it = this.textureDescriptorsSimple.iterator();
            while (it.hasNext()) {
                ObjectMap.Entry next = it.next();
                this.texturesSimple.put(next.key, assetManager.get((AssetDescriptor) next.value));
            }
            ObjectMap.Entries<Integer, AssetDescriptor<Texture>> it2 = this.textureDescriptorsMipMap.iterator();
            while (it2.hasNext()) {
                ObjectMap.Entry next2 = it2.next();
                this.texturesMipmap.put(next2.key, assetManager.get((AssetDescriptor) next2.value));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        public void loadTextures() {
            ObjectMap.Entries<Integer, TextureLoader.TextureParameter> it = GLTFAssetLoader.this.textureResolver.textureParameters.iterator();
            while (it.hasNext()) {
                ObjectMap.Entry next = it.next();
                TextureLoader.TextureParameter textureParameter = (TextureLoader.TextureParameter) next.value;
                Texture texture = new Texture(this.pixmaps.get(this.glTextures.get(((Integer) next.key).intValue()).source), textureParameter.genMipMaps);
                texture.setFilter(textureParameter.minFilter, textureParameter.magFilter);
                texture.setWrap(textureParameter.wrapU, textureParameter.wrapV);
                if (textureParameter.genMipMaps) {
                    this.texturesMipmap.put(next.key, texture);
                } else {
                    this.texturesSimple.put(next.key, texture);
                }
            }
            ObjectMap.Entries<Integer, Pixmap> it2 = this.pixmaps.iterator();
            while (it2.hasNext()) {
                ((Pixmap) it2.next().value).dispose();
            }
        }

        public void getDependencies(Array<AssetDescriptor> array) {
            this.glTextures = this.glModel.textures;
            this.glSamplers = this.glModel.samplers;
            if (this.glTextures != null) {
                for (int i = 0; i < this.glTextures.size; i++) {
                    GLTFTexture gLTFTexture = this.glTextures.get(i);
                    GLTFImage gLTFImage = this.glModel.images.get(gLTFTexture.source.intValue());
                    FileHandle imageFile = GLTFAssetLoader.this.dataFileResolver.getImageFile(gLTFImage);
                    TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
                    if (gLTFTexture.sampler != null) {
                        GLTFSampler gLTFSampler = this.glSamplers.get(gLTFTexture.sampler.intValue());
                        if (GLTFTypes.isMipMapFilter(gLTFSampler)) {
                            textureParameter.genMipMaps = true;
                        }
                        GLTFTypes.mapTextureSampler(textureParameter, gLTFSampler);
                    } else {
                        GLTFTypes.mapTextureSampler(textureParameter);
                    }
                    if (imageFile == null) {
                        if (this.pixmaps.get(gLTFTexture.source) == null) {
                            this.pixmaps.put(gLTFTexture.source, GLTFAssetLoader.this.dataFileResolver.load(gLTFImage));
                        }
                        this.textureParameters.put(Integer.valueOf(i), textureParameter);
                    } else {
                        AssetDescriptor<Texture> assetDescriptor = new AssetDescriptor<>(imageFile, (Class<Texture>) Texture.class, textureParameter);
                        array.add(assetDescriptor);
                        if (textureParameter.genMipMaps) {
                            this.textureDescriptorsMipMap.put(gLTFTexture.source, assetDescriptor);
                        } else {
                            this.textureDescriptorsSimple.put(gLTFTexture.source, assetDescriptor);
                        }
                    }
                }
            }
        }
    }

    public GLTFAssetLoader() {
        this(new InternalFileHandleResolver());
    }

    public GLTFAssetLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public void loadAsync(AssetManager assetManager, String str, FileHandle fileHandle, SceneAssetLoaderParameters sceneAssetLoaderParameters) {
        this.textureResolver.fetch(assetManager);
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public SceneAsset loadSync(AssetManager assetManager, String str, FileHandle fileHandle, SceneAssetLoaderParameters sceneAssetLoaderParameters) {
        boolean z = sceneAssetLoaderParameters != null && sceneAssetLoaderParameters.withData;
        this.textureResolver.loadTextures();
        SceneAsset sceneAssetLoad = new GLTFLoaderBase(this.textureResolver).load(this.dataFileResolver, z);
        Array<String> dependencies = assetManager.getDependencies(str);
        if (dependencies != null) {
            Array.ArrayIterator<String> it = dependencies.iterator();
            while (it.hasNext()) {
                Object obj = assetManager.get(it.next());
                if (obj instanceof Texture) {
                    sceneAssetLoad.textures.removeValue((Texture) obj, true);
                }
            }
        }
        this.textureResolver = null;
        this.dataFileResolver = null;
        return sceneAssetLoad;
    }

    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, SceneAssetLoaderParameters sceneAssetLoaderParameters) {
        Array<AssetDescriptor> array = new Array<>();
        SeparatedDataFileResolver separatedDataFileResolver = new SeparatedDataFileResolver();
        this.dataFileResolver = separatedDataFileResolver;
        separatedDataFileResolver.load(fileHandle);
        ManagedTextureResolver managedTextureResolver = new ManagedTextureResolver(this.dataFileResolver.getRoot());
        this.textureResolver = managedTextureResolver;
        managedTextureResolver.getDependencies(array);
        return array;
    }
}
