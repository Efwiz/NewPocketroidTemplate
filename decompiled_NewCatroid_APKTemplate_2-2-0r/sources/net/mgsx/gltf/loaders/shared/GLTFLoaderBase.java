package net.mgsx.gltf.loaders.shared;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import net.mgsx.gltf.data.GLTF;
import net.mgsx.gltf.data.camera.GLTFCamera;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import net.mgsx.gltf.data.extensions.KHRMaterialsEmissiveStrength;
import net.mgsx.gltf.data.extensions.KHRMaterialsIOR;
import net.mgsx.gltf.data.extensions.KHRMaterialsIridescence;
import net.mgsx.gltf.data.extensions.KHRMaterialsPBRSpecularGlossiness;
import net.mgsx.gltf.data.extensions.KHRMaterialsSpecular;
import net.mgsx.gltf.data.extensions.KHRMaterialsTransmission;
import net.mgsx.gltf.data.extensions.KHRMaterialsUnlit;
import net.mgsx.gltf.data.extensions.KHRMaterialsVolume;
import net.mgsx.gltf.data.extensions.KHRTextureTransform;
import net.mgsx.gltf.data.scene.GLTFNode;
import net.mgsx.gltf.data.scene.GLTFScene;
import net.mgsx.gltf.loaders.exceptions.GLTFUnsupportedException;
import net.mgsx.gltf.loaders.shared.animation.AnimationLoader;
import net.mgsx.gltf.loaders.shared.data.DataFileResolver;
import net.mgsx.gltf.loaders.shared.data.DataResolver;
import net.mgsx.gltf.loaders.shared.geometry.MeshLoader;
import net.mgsx.gltf.loaders.shared.material.MaterialLoader;
import net.mgsx.gltf.loaders.shared.material.PBRMaterialLoader;
import net.mgsx.gltf.loaders.shared.scene.NodeResolver;
import net.mgsx.gltf.loaders.shared.scene.SkinLoader;
import net.mgsx.gltf.loaders.shared.texture.ImageResolver;
import net.mgsx.gltf.loaders.shared.texture.TextureResolver;
import net.mgsx.gltf.scene3d.model.NodePlus;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneModel;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFLoaderBase implements Disposable {
    public static final String TAG = "GLTF";
    private static final ObjectSet<Material> materialSet;
    private static final ObjectSet<MeshPart> meshPartSet;
    private static final ObjectSet<Mesh> meshSet;
    public static final ObjectSet<String> supportedExtensions;
    protected AnimationLoader animationLoader;
    private ObjectMap<String, Integer> cameraMap;
    private final Array<Camera> cameras;
    protected DataFileResolver dataFileResolver;
    protected DataResolver dataResolver;
    protected GLTF glModel;
    protected ImageResolver imageResolver;
    private ObjectMap<String, Integer> lightMap;
    private final Array<BaseLight> lights;
    private final ObjectSet<Mesh> loadedMeshes;
    protected MaterialLoader materialLoader;
    protected MeshLoader meshLoader;
    protected NodeResolver nodeResolver;
    private Array<SceneModel> scenes;
    protected SkinLoader skinLoader;
    protected TextureResolver textureResolver;

    static {
        ObjectSet<String> objectSet = new ObjectSet<>();
        supportedExtensions = objectSet;
        objectSet.addAll(KHRMaterialsPBRSpecularGlossiness.EXT, KHRTextureTransform.EXT, KHRLightsPunctual.EXT, KHRMaterialsUnlit.EXT, KHRMaterialsTransmission.EXT, KHRMaterialsVolume.EXT, KHRMaterialsIOR.EXT, KHRMaterialsSpecular.EXT, KHRMaterialsIridescence.EXT, KHRMaterialsEmissiveStrength.EXT);
        materialSet = new ObjectSet<>();
        meshPartSet = new ObjectSet<>();
        meshSet = new ObjectSet<>();
    }

    public GLTFLoaderBase() {
        this(null);
    }

    public GLTFLoaderBase(TextureResolver textureResolver) {
        this.loadedMeshes = new ObjectSet<>();
        this.cameras = new Array<>();
        this.lights = new Array<>();
        this.lightMap = new ObjectMap<>();
        this.cameraMap = new ObjectMap<>();
        this.scenes = new Array<>();
        this.textureResolver = textureResolver;
        this.animationLoader = new AnimationLoader();
        this.nodeResolver = new NodeResolver();
        this.meshLoader = new MeshLoader();
        this.skinLoader = new SkinLoader();
    }

    public SceneAsset load(DataFileResolver dataFileResolver, boolean z) {
        try {
            this.dataFileResolver = dataFileResolver;
            GLTF root = dataFileResolver.getRoot();
            this.glModel = root;
            if (root.extensionsRequired != null) {
                Array.ArrayIterator<String> it = this.glModel.extensionsRequired.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    if (!supportedExtensions.contains(next)) {
                        throw new GLTFUnsupportedException("Extension " + next + " required but not supported");
                    }
                }
            }
            if (this.glModel.extensionsUsed != null) {
                Array.ArrayIterator<String> it2 = this.glModel.extensionsUsed.iterator();
                while (it2.hasNext()) {
                    String next2 = it2.next();
                    if (!supportedExtensions.contains(next2)) {
                        Gdx.f82app.error(TAG, "Extension " + next2 + " used but not supported");
                    }
                }
            }
            this.dataResolver = new DataResolver(this.glModel, dataFileResolver);
            if (this.textureResolver == null) {
                ImageResolver imageResolver = new ImageResolver(dataFileResolver);
                this.imageResolver = imageResolver;
                imageResolver.load(this.glModel.images);
                TextureResolver textureResolver = new TextureResolver();
                this.textureResolver = textureResolver;
                textureResolver.loadTextures(this.glModel.textures, this.glModel.samplers, this.imageResolver);
            }
            MaterialLoader materialLoaderCreateMaterialLoader = createMaterialLoader(this.textureResolver);
            this.materialLoader = materialLoaderCreateMaterialLoader;
            materialLoaderCreateMaterialLoader.loadMaterials(this.glModel.materials);
            loadCameras();
            loadLights();
            loadScenes();
            this.animationLoader.load(this.glModel.animations, this.nodeResolver, this.dataResolver);
            this.skinLoader.load(this.glModel.skins, this.glModel.nodes, this.nodeResolver, this.dataResolver);
            SceneAsset sceneAsset = new SceneAsset();
            if (z) {
                sceneAsset.data = this.glModel;
            }
            sceneAsset.scenes = this.scenes;
            sceneAsset.scene = this.scenes.get(this.glModel.scene);
            sceneAsset.maxBones = this.skinLoader.getMaxBones();
            sceneAsset.textures = this.textureResolver.getTextures(new Array<>());
            ImageResolver imageResolver2 = this.imageResolver;
            if (imageResolver2 != null) {
                sceneAsset.pixmaps = imageResolver2.getPixmaps(new Array<>());
                this.imageResolver.clear();
            }
            sceneAsset.animations = this.animationLoader.animations;
            Array.ArrayIterator<SceneModel> it3 = sceneAsset.scenes.iterator();
            while (it3.hasNext()) {
                it3.next().model.animations.addAll(this.animationLoader.animations);
            }
            ObjectSet<Mesh> objectSet = this.loadedMeshes;
            Array<Mesh> array = new Array<>();
            sceneAsset.meshes = array;
            copy(objectSet, array);
            this.loadedMeshes.clear();
            return sceneAsset;
        } catch (RuntimeException e) {
            dispose();
            throw e;
        }
    }

    protected MaterialLoader createMaterialLoader(TextureResolver textureResolver) {
        return new PBRMaterialLoader(textureResolver);
    }

    private void loadLights() {
        KHRLightsPunctual.GLTFLights gLTFLights;
        if (this.glModel.extensions == null || (gLTFLights = (KHRLightsPunctual.GLTFLights) this.glModel.extensions.get(KHRLightsPunctual.GLTFLights.class, KHRLightsPunctual.EXT)) == null) {
            return;
        }
        Array.ArrayIterator<KHRLightsPunctual.GLTFLight> it = gLTFLights.lights.iterator();
        while (it.hasNext()) {
            this.lights.add(KHRLightsPunctual.map(it.next()));
        }
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        ImageResolver imageResolver = this.imageResolver;
        if (imageResolver != null) {
            imageResolver.dispose();
        }
        TextureResolver textureResolver = this.textureResolver;
        if (textureResolver != null) {
            textureResolver.dispose();
        }
        Array.ArrayIterator<SceneModel> it = this.scenes.iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        ObjectSet.ObjectSetIterator<Mesh> it2 = this.loadedMeshes.iterator();
        while (it2.hasNext()) {
            it2.next().dispose();
        }
        this.loadedMeshes.clear();
    }

    private void loadScenes() {
        for (int i = 0; i < this.glModel.scenes.size; i++) {
            this.scenes.add(loadScene(this.glModel.scenes.get(i)));
        }
    }

    private void loadCameras() {
        if (this.glModel.cameras != null) {
            Array.ArrayIterator<GLTFCamera> it = this.glModel.cameras.iterator();
            while (it.hasNext()) {
                this.cameras.add(GLTFTypes.map(it.next()));
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private SceneModel loadScene(GLTFScene gLTFScene) {
        SceneModel sceneModel = new SceneModel();
        sceneModel.f412name = gLTFScene.f408name;
        sceneModel.model = new Model();
        if (gLTFScene.nodes != null) {
            Array.ArrayIterator<Integer> it = gLTFScene.nodes.iterator();
            while (it.hasNext()) {
                sceneModel.model.nodes.add(getNode(it.next().intValue()));
            }
        }
        ObjectMap.Entries<String, Integer> it2 = this.cameraMap.iterator();
        while (it2.hasNext()) {
            ObjectMap.Entry next = it2.next();
            Node node = sceneModel.model.getNode((String) next.key, true);
            if (node != null) {
                sceneModel.cameras.put(node, this.cameras.get(((Integer) next.value).intValue()));
            }
        }
        ObjectMap.Entries<String, Integer> it3 = this.lightMap.iterator();
        while (it3.hasNext()) {
            ObjectMap.Entry next2 = it3.next();
            Node node2 = sceneModel.model.getNode((String) next2.key, true);
            if (node2 != null) {
                sceneModel.lights.put(node2, this.lights.get(((Integer) next2.value).intValue()));
            }
        }
        collectData(sceneModel.model, sceneModel.model.nodes);
        ObjectSet<Mesh> objectSet = this.loadedMeshes;
        ObjectSet<Mesh> objectSet2 = meshSet;
        objectSet.addAll(objectSet2);
        copy(objectSet2, sceneModel.model.meshes);
        ObjectSet<MeshPart> objectSet3 = meshPartSet;
        copy(objectSet3, sceneModel.model.meshParts);
        ObjectSet<Material> objectSet4 = materialSet;
        copy(objectSet4, sceneModel.model.materials);
        objectSet2.clear();
        objectSet3.clear();
        objectSet4.clear();
        return sceneModel;
    }

    private void collectData(Model model, Iterable<Node> iterable) {
        for (Node node : iterable) {
            Array.ArrayIterator<NodePart> it = node.parts.iterator();
            while (it.hasNext()) {
                NodePart next = it.next();
                meshSet.add(next.meshPart.mesh);
                meshPartSet.add(next.meshPart);
                materialSet.add(next.material);
            }
            collectData(model, node.getChildren());
        }
    }

    private static <T> void copy(ObjectSet<T> objectSet, Array<T> array) {
        ObjectSet.ObjectSetIterator<T> it = objectSet.iterator();
        while (it.hasNext()) {
            array.add(it.next());
        }
    }

    private Node getNode(int i) {
        KHRLightsPunctual.GLTFLightNode gLTFLightNode;
        Node nodePlus = this.nodeResolver.get(i);
        if (nodePlus == null) {
            nodePlus = new NodePlus();
            this.nodeResolver.put(i, nodePlus);
            GLTFNode gLTFNode = this.glModel.nodes.get(i);
            if (gLTFNode.matrix != null) {
                Matrix4 matrix4 = new Matrix4(gLTFNode.matrix);
                matrix4.getTranslation(nodePlus.translation);
                matrix4.getScale(nodePlus.scale);
                matrix4.getRotation(nodePlus.rotation, true);
            } else {
                if (gLTFNode.translation != null) {
                    GLTFTypes.map(nodePlus.translation, gLTFNode.translation);
                }
                if (gLTFNode.rotation != null) {
                    GLTFTypes.map(nodePlus.rotation, gLTFNode.rotation);
                }
                if (gLTFNode.scale != null) {
                    GLTFTypes.map(nodePlus.scale, gLTFNode.scale);
                }
            }
            nodePlus.f104id = gLTFNode.f408name == null ? "glNode " + i : gLTFNode.f408name;
            if (gLTFNode.children != null) {
                Array.ArrayIterator<Integer> it = gLTFNode.children.iterator();
                while (it.hasNext()) {
                    nodePlus.addChild(getNode(it.next().intValue()));
                }
            }
            if (gLTFNode.mesh != null) {
                this.meshLoader.load(nodePlus, this.glModel.meshes.get(gLTFNode.mesh.intValue()), this.dataResolver, this.materialLoader);
            }
            if (gLTFNode.camera != null) {
                this.cameraMap.put(nodePlus.f104id, gLTFNode.camera);
            }
            if (gLTFNode.extensions != null && (gLTFLightNode = (KHRLightsPunctual.GLTFLightNode) gLTFNode.extensions.get(KHRLightsPunctual.GLTFLightNode.class, KHRLightsPunctual.EXT)) != null) {
                this.lightMap.put(nodePlus.f104id, gLTFLightNode.light);
            }
        }
        return nodePlus;
    }
}
