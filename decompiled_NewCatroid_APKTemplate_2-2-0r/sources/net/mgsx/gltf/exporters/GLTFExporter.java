package net.mgsx.gltf.exporters;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import net.mgsx.gltf.data.GLTF;
import net.mgsx.gltf.data.GLTFAsset;
import net.mgsx.gltf.data.data.GLTFAccessor;
import net.mgsx.gltf.data.geometry.GLTFMesh;
import net.mgsx.gltf.data.geometry.GLTFPrimitive;
import net.mgsx.gltf.data.scene.GLTFNode;
import net.mgsx.gltf.data.scene.GLTFScene;
import net.mgsx.gltf.loaders.exceptions.GLTFIllegalException;
import net.mgsx.gltf.loaders.exceptions.GLTFRuntimeException;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneModel;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFExporter {
    private static final String GENERATOR_INFO = "GDX glTF exporter 1.0";
    GLTFBinaryExporter binManager;
    private final GLTFExporterConfig config;
    protected String fileHandleName;
    final Array<Material> materialMapping;
    private final GLTFMeshExporter meshExporter;
    final Array<Node> nodeMapping;
    GLTF root;
    protected int textureFileIndex;
    final Array<Texture> textureMapping;

    public GLTFExporter() {
        this(new GLTFExporterConfig());
    }

    public GLTFExporter(GLTFExporterConfig gLTFExporterConfig) {
        this.nodeMapping = new Array<>();
        this.materialMapping = new Array<>();
        this.textureMapping = new Array<>();
        this.fileHandleName = "";
        this.config = gLTFExporterConfig;
        this.meshExporter = new GLTFMeshExporter(this);
    }

    protected String getImageName(Texture texture) {
        String str = this.fileHandleName + "texture" + this.textureFileIndex;
        this.textureFileIndex++;
        return str;
    }

    private void reset() {
        this.root = null;
        this.binManager.reset();
        this.nodeMapping.clear();
        this.materialMapping.clear();
        this.textureMapping.clear();
        this.textureFileIndex = 0;
    }

    public void export(Mesh mesh, int i, FileHandle fileHandle) {
        GLTFScene gLTFSceneBeginSingleScene = beginSingleScene(fileHandle);
        GLTFNode gLTFNodeObtainNode = obtainNode();
        gLTFSceneBeginSingleScene.nodes = new Array<>();
        gLTFSceneBeginSingleScene.nodes.add(Integer.valueOf(this.root.nodes.size - 1));
        GLTFMesh gLTFMeshObtainMesh = obtainMesh();
        gLTFNodeObtainNode.mesh = Integer.valueOf(this.root.meshes.size - 1);
        MeshPart meshPart = new MeshPart();
        meshPart.mesh = mesh;
        meshPart.offset = 0;
        meshPart.primitiveType = i;
        meshPart.size = mesh.getNumIndices();
        if (meshPart.size == 0) {
            meshPart.size = mesh.getNumVertices();
        }
        gLTFMeshObtainMesh.primitives = new Array<>();
        gLTFMeshObtainMesh.primitives.add(this.meshExporter.exportMeshPart(meshPart));
        end(fileHandle);
    }

    public void export(Model model, FileHandle fileHandle) {
        GLTFScene gLTFSceneBeginSingleScene = beginSingleScene(fileHandle);
        new GLTFMaterialExporter(this).export(model.nodes);
        gLTFSceneBeginSingleScene.nodes = exportNodes(gLTFSceneBeginSingleScene, model.nodes);
        new GLTFSkinExporter(this).export();
        new GLTFAnimationExporter(this).export(model.animations);
        end(fileHandle);
    }

    public void export(Scene scene, FileHandle fileHandle) {
        exportScene(beginSingleScene(fileHandle), scene);
        end(fileHandle);
    }

    public void export(SceneModel sceneModel, FileHandle fileHandle) {
        exportScene(beginSingleScene(fileHandle), sceneModel);
        end(fileHandle);
    }

    private void exportScene(GLTFScene gLTFScene, Scene scene) {
        new GLTFMaterialExporter(this).export(scene.modelInstance.nodes);
        gLTFScene.nodes = exportNodes(gLTFScene, scene.modelInstance.nodes);
        if (this.config.exportCameras) {
            new GLTFCameraExporter(this).export(scene.cameras);
        }
        if (this.config.exportLights) {
            new GLTFLightExporter(this).export(scene.lights);
        }
        new GLTFSkinExporter(this).export();
        new GLTFAnimationExporter(this).export(scene.modelInstance.animations);
    }

    private void exportScene(GLTFScene gLTFScene, SceneModel sceneModel) {
        new GLTFMaterialExporter(this).export(sceneModel.model.nodes);
        gLTFScene.nodes = exportNodes(gLTFScene, sceneModel.model.nodes);
        if (this.config.exportCameras) {
            new GLTFCameraExporter(this).export(sceneModel.cameras);
        }
        if (this.config.exportLights) {
            new GLTFLightExporter(this).export(sceneModel.lights);
        }
        new GLTFSkinExporter(this).export();
        new GLTFAnimationExporter(this).export(sceneModel.model.animations);
    }

    public void export(SceneAsset sceneAsset, FileHandle fileHandle) {
        export(sceneAsset.scenes, sceneAsset.scene, fileHandle);
    }

    public void export(Array<SceneModel> array, SceneModel sceneModel, FileHandle fileHandle) {
        beginMultiScene(fileHandle);
        Array.ArrayIterator<SceneModel> it = array.iterator();
        while (it.hasNext()) {
            exportScene(obtainScene(), it.next());
        }
        this.root.scene = array.indexOf(sceneModel, true);
        if (this.root.scene < 0) {
            throw new GLTFIllegalException("scene not found");
        }
        end(fileHandle);
    }

    public void export(Array<Scene> array, Scene scene, FileHandle fileHandle) {
        beginMultiScene(fileHandle);
        Array.ArrayIterator<Scene> it = array.iterator();
        while (it.hasNext()) {
            exportScene(obtainScene(), it.next());
        }
        this.root.scene = array.indexOf(scene, true);
        if (this.root.scene < 0) {
            throw new GLTFIllegalException("scene not found");
        }
        end(fileHandle);
    }

    private void beginMultiScene(FileHandle fileHandle) {
        this.fileHandleName = fileHandle.nameWithoutExtension() + "_";
        this.binManager = new GLTFBinaryExporter(fileHandle.parent(), this.config);
        GLTF gltf = new GLTF();
        this.root = gltf;
        gltf.asset = new GLTFAsset();
        this.root.asset.version = "2.0";
        this.root.asset.generator = GENERATOR_INFO;
        this.root.scenes = new Array<>();
    }

    private GLTFScene beginSingleScene(FileHandle fileHandle) {
        beginMultiScene(fileHandle);
        this.root.scene = 0;
        return obtainScene();
    }

    private GLTFScene obtainScene() {
        GLTFScene gLTFScene = new GLTFScene();
        this.root.scenes.add(gLTFScene);
        return gLTFScene;
    }

    private void end(FileHandle fileHandle) {
        this.root.bufferViews = this.binManager.views;
        this.root.buffers = this.binManager.flushAllToFiles(fileHandle.nameWithoutExtension());
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setUsePrototypes(true);
        fileHandle.writeString(json.prettyPrint(this.root), false);
        reset();
    }

    private Array<Integer> exportNodes(GLTFScene gLTFScene, Iterable<Node> iterable) {
        Array<Integer> array = null;
        for (Node node : iterable) {
            GLTFNode gLTFNodeObtainNode = obtainNode();
            this.nodeMapping.add(node);
            gLTFNodeObtainNode.f408name = node.f104id;
            if (!node.translation.isZero()) {
                gLTFNodeObtainNode.translation = GLTFExportTypes.toArray(node.translation);
            }
            if (!node.scale.epsilonEquals(1.0f, 1.0f, 1.0f)) {
                gLTFNodeObtainNode.scale = GLTFExportTypes.toArray(node.scale);
            }
            if (!node.rotation.isIdentity()) {
                gLTFNodeObtainNode.rotation = GLTFExportTypes.toArray(node.rotation);
            }
            if (array == null) {
                array = new Array<>();
            }
            array.add(Integer.valueOf(this.root.nodes.size - 1));
            if (node.parts.size > 0) {
                GLTFMesh gLTFMeshObtainMesh = obtainMesh();
                gLTFNodeObtainNode.mesh = Integer.valueOf(this.root.meshes.size - 1);
                gLTFMeshObtainMesh.primitives = new Array<>();
                Array.ArrayIterator<NodePart> it = node.parts.iterator();
                while (it.hasNext()) {
                    NodePart next = it.next();
                    GLTFPrimitive gLTFPrimitiveExportMeshPart = this.meshExporter.exportMeshPart(next.meshPart);
                    int iIndexOf = this.materialMapping.indexOf(next.material, true);
                    if (iIndexOf < 0) {
                        throw new GLTFRuntimeException("material not found");
                    }
                    gLTFPrimitiveExportMeshPart.material = Integer.valueOf(iIndexOf);
                    gLTFMeshObtainMesh.primitives.add(gLTFPrimitiveExportMeshPart);
                }
            }
            gLTFNodeObtainNode.children = exportNodes(gLTFScene, node.getChildren());
        }
        return array;
    }

    GLTFAccessor obtainAccessor() {
        GLTFAccessor gLTFAccessor = new GLTFAccessor();
        if (this.root.accessors == null) {
            this.root.accessors = new Array<>();
        }
        this.root.accessors.add(gLTFAccessor);
        return gLTFAccessor;
    }

    private GLTFNode obtainNode() {
        GLTFNode gLTFNode = new GLTFNode();
        if (this.root.nodes == null) {
            this.root.nodes = new Array<>();
        }
        this.root.nodes.add(gLTFNode);
        return gLTFNode;
    }

    private GLTFMesh obtainMesh() {
        GLTFMesh gLTFMesh = new GLTFMesh();
        if (this.root.meshes == null) {
            this.root.meshes = new Array<>();
        }
        this.root.meshes.add(gLTFMesh);
        return gLTFMesh;
    }

    void useExtension(String str, boolean z) {
        if (this.root.extensionsUsed == null) {
            this.root.extensionsUsed = new Array<>();
        }
        if (!this.root.extensionsUsed.contains(str, false)) {
            this.root.extensionsUsed.add(str);
        }
        if (z) {
            if (this.root.extensionsRequired == null) {
                this.root.extensionsRequired = new Array<>();
            }
            if (this.root.extensionsRequired.contains(str, false)) {
                return;
            }
            this.root.extensionsRequired.add(str);
        }
    }
}
