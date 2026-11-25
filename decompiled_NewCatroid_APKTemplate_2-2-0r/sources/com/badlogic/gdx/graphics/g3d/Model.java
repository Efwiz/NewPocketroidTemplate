package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelAnimation;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMeshPart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNode;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import java.nio.ShortBuffer;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/Model.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Model implements Disposable {
    public final Array<Animation> animations;
    protected final Array<Disposable> disposables;
    public final Array<Material> materials;
    public final Array<MeshPart> meshParts;
    public final Array<Mesh> meshes;
    private ObjectMap<NodePart, ArrayMap<String, Matrix4>> nodePartBones;
    public final Array<Node> nodes;

    public Model() {
        this.materials = new Array<>();
        this.nodes = new Array<>();
        this.animations = new Array<>();
        this.meshes = new Array<>();
        this.meshParts = new Array<>();
        this.disposables = new Array<>();
        this.nodePartBones = new ObjectMap<>();
    }

    public Model(ModelData modelData) {
        this(modelData, new TextureProvider.FileTextureProvider());
    }

    public Model(ModelData modelData, TextureProvider textureProvider) {
        this.materials = new Array<>();
        this.nodes = new Array<>();
        this.animations = new Array<>();
        this.meshes = new Array<>();
        this.meshParts = new Array<>();
        this.disposables = new Array<>();
        this.nodePartBones = new ObjectMap<>();
        load(modelData, textureProvider);
    }

    protected void load(ModelData modelData, TextureProvider textureProvider) {
        loadMeshes(modelData.meshes);
        loadMaterials(modelData.materials, textureProvider);
        loadNodes(modelData.nodes);
        loadAnimations(modelData.animations);
        calculateTransforms();
    }

    protected void loadAnimations(Iterable<ModelAnimation> iterable) {
        for (ModelAnimation modelAnimation : iterable) {
            Animation animation = new Animation();
            animation.f102id = modelAnimation.f105id;
            Array.ArrayIterator<ModelNodeAnimation> it = modelAnimation.nodeAnimations.iterator();
            while (it.hasNext()) {
                ModelNodeAnimation next = it.next();
                Node node = getNode(next.nodeId);
                if (node != null) {
                    NodeAnimation nodeAnimation = new NodeAnimation();
                    nodeAnimation.node = node;
                    if (next.translation != null) {
                        nodeAnimation.translation = new Array<>();
                        nodeAnimation.translation.ensureCapacity(next.translation.size);
                        Array.ArrayIterator<ModelNodeKeyframe<Vector3>> it2 = next.translation.iterator();
                        while (it2.hasNext()) {
                            ModelNodeKeyframe<Vector3> next2 = it2.next();
                            if (next2.keytime > animation.duration) {
                                animation.duration = next2.keytime;
                            }
                            nodeAnimation.translation.add(new NodeKeyframe<>(next2.keytime, new Vector3(next2.value == null ? node.translation : next2.value)));
                        }
                    }
                    if (next.rotation != null) {
                        nodeAnimation.rotation = new Array<>();
                        nodeAnimation.rotation.ensureCapacity(next.rotation.size);
                        Array.ArrayIterator<ModelNodeKeyframe<Quaternion>> it3 = next.rotation.iterator();
                        while (it3.hasNext()) {
                            ModelNodeKeyframe<Quaternion> next3 = it3.next();
                            if (next3.keytime > animation.duration) {
                                animation.duration = next3.keytime;
                            }
                            nodeAnimation.rotation.add(new NodeKeyframe<>(next3.keytime, new Quaternion(next3.value == null ? node.rotation : next3.value)));
                        }
                    }
                    if (next.scaling != null) {
                        nodeAnimation.scaling = new Array<>();
                        nodeAnimation.scaling.ensureCapacity(next.scaling.size);
                        Array.ArrayIterator<ModelNodeKeyframe<Vector3>> it4 = next.scaling.iterator();
                        while (it4.hasNext()) {
                            ModelNodeKeyframe<Vector3> next4 = it4.next();
                            if (next4.keytime > animation.duration) {
                                animation.duration = next4.keytime;
                            }
                            nodeAnimation.scaling.add(new NodeKeyframe<>(next4.keytime, new Vector3(next4.value == null ? node.scale : next4.value)));
                        }
                    }
                    if ((nodeAnimation.translation != null && nodeAnimation.translation.size > 0) || ((nodeAnimation.rotation != null && nodeAnimation.rotation.size > 0) || (nodeAnimation.scaling != null && nodeAnimation.scaling.size > 0))) {
                        animation.nodeAnimations.add(nodeAnimation);
                    }
                }
            }
            if (animation.nodeAnimations.size > 0) {
                this.animations.add(animation);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void loadNodes(Iterable<ModelNode> iterable) {
        this.nodePartBones.clear();
        Iterator<ModelNode> it = iterable.iterator();
        while (it.hasNext()) {
            this.nodes.add(loadNode(it.next()));
        }
        ObjectMap.Entries<NodePart, ArrayMap<String, Matrix4>> it2 = this.nodePartBones.entries().iterator();
        while (it2.hasNext()) {
            ObjectMap.Entry next = it2.next();
            if (((NodePart) next.key).invBoneBindTransforms == null) {
                ((NodePart) next.key).invBoneBindTransforms = new ArrayMap<>(Node.class, Matrix4.class);
            }
            ((NodePart) next.key).invBoneBindTransforms.clear();
            for (ObjectMap.Entry entry : ((ArrayMap) next.value).entries()) {
                ((NodePart) next.key).invBoneBindTransforms.put(getNode((String) entry.key), new Matrix4((Matrix4) entry.value).inv());
            }
        }
    }

    protected Node loadNode(ModelNode modelNode) {
        MeshPart next;
        Node node = new Node();
        node.f104id = modelNode.f110id;
        if (modelNode.translation != null) {
            node.translation.set(modelNode.translation);
        }
        if (modelNode.rotation != null) {
            node.rotation.set(modelNode.rotation);
        }
        if (modelNode.scale != null) {
            node.scale.set(modelNode.scale);
        }
        if (modelNode.parts != null) {
            for (ModelNodePart modelNodePart : modelNode.parts) {
                Material material = null;
                if (modelNodePart.meshPartId == null) {
                    next = null;
                    break;
                }
                Array.ArrayIterator<MeshPart> it = this.meshParts.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!modelNodePart.meshPartId.equals(next.f103id));
                if (modelNodePart.materialId != null) {
                    Array.ArrayIterator<Material> it2 = this.materials.iterator();
                    while (it2.hasNext()) {
                        Material next2 = it2.next();
                        if (modelNodePart.materialId.equals(next2.f100id)) {
                            material = next2;
                            break;
                        }
                    }
                }
                if (next == null || material == null) {
                    throw new GdxRuntimeException("Invalid node: " + node.f104id);
                }
                NodePart nodePart = new NodePart();
                nodePart.meshPart = next;
                nodePart.material = material;
                node.parts.add(nodePart);
                if (modelNodePart.bones != null) {
                    this.nodePartBones.put(nodePart, modelNodePart.bones);
                }
            }
        }
        if (modelNode.children != null) {
            for (ModelNode modelNode2 : modelNode.children) {
                node.addChild(loadNode(modelNode2));
            }
        }
        return node;
    }

    protected void loadMeshes(Iterable<ModelMesh> iterable) {
        Iterator<ModelMesh> it = iterable.iterator();
        while (it.hasNext()) {
            convertMesh(it.next());
        }
    }

    protected void convertMesh(ModelMesh modelMesh) {
        int length = 0;
        for (ModelMeshPart modelMeshPart : modelMesh.parts) {
            length += modelMeshPart.indices.length;
        }
        boolean z = length > 0;
        VertexAttributes vertexAttributes = new VertexAttributes(modelMesh.attributes);
        int length2 = modelMesh.vertices.length / (vertexAttributes.vertexSize / 4);
        Mesh mesh = new Mesh(true, length2, length, vertexAttributes);
        this.meshes.add(mesh);
        this.disposables.add(mesh);
        BufferUtils.copy(modelMesh.vertices, mesh.getVerticesBuffer(true), modelMesh.vertices.length, 0);
        ShortBuffer indicesBuffer = mesh.getIndicesBuffer(true);
        indicesBuffer.clear();
        int i = 0;
        for (ModelMeshPart modelMeshPart2 : modelMesh.parts) {
            MeshPart meshPart = new MeshPart();
            meshPart.f103id = modelMeshPart2.f109id;
            meshPart.primitiveType = modelMeshPart2.primitiveType;
            meshPart.offset = i;
            meshPart.size = z ? modelMeshPart2.indices.length : length2;
            meshPart.mesh = mesh;
            if (z) {
                indicesBuffer.put(modelMeshPart2.indices);
            }
            i += meshPart.size;
            this.meshParts.add(meshPart);
        }
        indicesBuffer.position(0);
        Array.ArrayIterator<MeshPart> it = this.meshParts.iterator();
        while (it.hasNext()) {
            it.next().update();
        }
    }

    protected void loadMaterials(Iterable<ModelMaterial> iterable, TextureProvider textureProvider) {
        Iterator<ModelMaterial> it = iterable.iterator();
        while (it.hasNext()) {
            this.materials.add(convertMaterial(it.next(), textureProvider));
        }
    }

    protected Material convertMaterial(ModelMaterial modelMaterial, TextureProvider textureProvider) {
        Texture textureLoad;
        Material material = new Material();
        material.f100id = modelMaterial.f107id;
        if (modelMaterial.ambient != null) {
            material.set(new ColorAttribute(ColorAttribute.Ambient, modelMaterial.ambient));
        }
        if (modelMaterial.diffuse != null) {
            material.set(new ColorAttribute(ColorAttribute.Diffuse, modelMaterial.diffuse));
        }
        if (modelMaterial.specular != null) {
            material.set(new ColorAttribute(ColorAttribute.Specular, modelMaterial.specular));
        }
        if (modelMaterial.emissive != null) {
            material.set(new ColorAttribute(ColorAttribute.Emissive, modelMaterial.emissive));
        }
        if (modelMaterial.reflection != null) {
            material.set(new ColorAttribute(ColorAttribute.Reflection, modelMaterial.reflection));
        }
        if (modelMaterial.shininess > 0.0f) {
            material.set(new FloatAttribute(FloatAttribute.Shininess, modelMaterial.shininess));
        }
        if (modelMaterial.opacity != 1.0f) {
            material.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, modelMaterial.opacity));
        }
        ObjectMap objectMap = new ObjectMap();
        if (modelMaterial.textures != null) {
            Array.ArrayIterator<ModelTexture> it = modelMaterial.textures.iterator();
            while (it.hasNext()) {
                ModelTexture next = it.next();
                if (objectMap.containsKey(next.fileName)) {
                    textureLoad = (Texture) objectMap.get(next.fileName);
                } else {
                    textureLoad = textureProvider.load(next.fileName);
                    objectMap.put(next.fileName, textureLoad);
                    this.disposables.add(textureLoad);
                }
                TextureDescriptor textureDescriptor = new TextureDescriptor(textureLoad);
                textureDescriptor.minFilter = textureLoad.getMinFilter();
                textureDescriptor.magFilter = textureLoad.getMagFilter();
                textureDescriptor.uWrap = textureLoad.getUWrap();
                textureDescriptor.vWrap = textureLoad.getVWrap();
                float f = next.uvTranslation == null ? 0.0f : next.uvTranslation.x;
                float f2 = next.uvTranslation == null ? 0.0f : next.uvTranslation.y;
                float f3 = next.uvScaling == null ? 1.0f : next.uvScaling.x;
                float f4 = next.uvScaling == null ? 1.0f : next.uvScaling.y;
                int i = next.usage;
                if (i == 2) {
                    material.set(new TextureAttribute(TextureAttribute.Diffuse, textureDescriptor, f, f2, f3, f4));
                } else if (i == 3) {
                    material.set(new TextureAttribute(TextureAttribute.Emissive, textureDescriptor, f, f2, f3, f4));
                } else if (i == 4) {
                    material.set(new TextureAttribute(TextureAttribute.Ambient, textureDescriptor, f, f2, f3, f4));
                } else if (i == 5) {
                    material.set(new TextureAttribute(TextureAttribute.Specular, textureDescriptor, f, f2, f3, f4));
                } else if (i == 7) {
                    material.set(new TextureAttribute(TextureAttribute.Normal, textureDescriptor, f, f2, f3, f4));
                } else if (i == 8) {
                    material.set(new TextureAttribute(TextureAttribute.Bump, textureDescriptor, f, f2, f3, f4));
                } else if (i == 10) {
                    material.set(new TextureAttribute(TextureAttribute.Reflection, textureDescriptor, f, f2, f3, f4));
                }
            }
        }
        return material;
    }

    public void manageDisposable(Disposable disposable) {
        if (this.disposables.contains(disposable, true)) {
            return;
        }
        this.disposables.add(disposable);
    }

    public Iterable<Disposable> getManagedDisposables() {
        return this.disposables;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        Array.ArrayIterator<Disposable> it = this.disposables.iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
    }

    public void calculateTransforms() {
        int i = this.nodes.size;
        for (int i2 = 0; i2 < i; i2++) {
            this.nodes.get(i2).calculateTransforms(true);
        }
        for (int i3 = 0; i3 < i; i3++) {
            this.nodes.get(i3).calculateBoneTransforms(true);
        }
    }

    public BoundingBox calculateBoundingBox(BoundingBox boundingBox) {
        boundingBox.inf();
        return extendBoundingBox(boundingBox);
    }

    public BoundingBox extendBoundingBox(BoundingBox boundingBox) {
        int i = this.nodes.size;
        for (int i2 = 0; i2 < i; i2++) {
            this.nodes.get(i2).extendBoundingBox(boundingBox);
        }
        return boundingBox;
    }

    public Animation getAnimation(String str) {
        return getAnimation(str, true);
    }

    public Animation getAnimation(String str, boolean z) {
        int i = this.animations.size;
        int i2 = 0;
        if (z) {
            while (i2 < i) {
                Animation animation = this.animations.get(i2);
                if (animation.f102id.equalsIgnoreCase(str)) {
                    return animation;
                }
                i2++;
            }
            return null;
        }
        while (i2 < i) {
            Animation animation2 = this.animations.get(i2);
            if (animation2.f102id.equals(str)) {
                return animation2;
            }
            i2++;
        }
        return null;
    }

    public Material getMaterial(String str) {
        return getMaterial(str, true);
    }

    public Material getMaterial(String str, boolean z) {
        int i = this.materials.size;
        int i2 = 0;
        if (z) {
            while (i2 < i) {
                Material material = this.materials.get(i2);
                if (material.f100id.equalsIgnoreCase(str)) {
                    return material;
                }
                i2++;
            }
            return null;
        }
        while (i2 < i) {
            Material material2 = this.materials.get(i2);
            if (material2.f100id.equals(str)) {
                return material2;
            }
            i2++;
        }
        return null;
    }

    public Node getNode(String str) {
        return getNode(str, true);
    }

    public Node getNode(String str, boolean z) {
        return getNode(str, z, false);
    }

    public Node getNode(String str, boolean z, boolean z2) {
        return Node.getNode(this.nodes, str, z, z2);
    }
}
