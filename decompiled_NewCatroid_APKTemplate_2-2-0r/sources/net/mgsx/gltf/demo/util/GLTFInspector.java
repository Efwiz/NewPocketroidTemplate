package net.mgsx.gltf.demo.util;

import androidx.exifinterface.media.ExifInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import net.mgsx.gltf.loaders.shared.GLTFLoaderBase;
import net.mgsx.gltf.scene3d.animation.NodeAnimationHack;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneModel;
import net.mgsx.gltf.scene3d.utils.LightUtils;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFInspector {
    private Array<BaseLight> allLights = new Array<>();
    private Array<Camera> allCameras = new Array<>();

    public void inspect(SceneAsset sceneAsset) {
        this.allLights.clear();
        this.allCameras.clear();
        if (sceneAsset.data != null) {
            logData(sceneAsset);
        }
        if (sceneAsset.scenes.size > 0) {
            logScenes(sceneAsset);
        }
        if (sceneAsset.animations.size > 0) {
            logAnimations(sceneAsset);
        }
        if (sceneAsset.textures.size > 0) {
            logTexures(sceneAsset);
        }
        if (this.allLights.size > 0) {
            logLights(this.allLights);
        }
        if (this.allCameras.size > 0) {
            logCameras(this.allCameras);
        }
    }

    private void logData(SceneAsset sceneAsset) {
        if (sceneAsset.data.extensionsUsed != null) {
            Array.ArrayIterator<String> it = sceneAsset.data.extensionsUsed.iterator();
            while (it.hasNext()) {
                String next = it.next();
                log("Extension", next, GLTFLoaderBase.supportedExtensions.contains(next) ? " (supported)" : " (not supported)");
            }
        }
    }

    private void logCameras(Array<Camera> array) {
        Array.ArrayIterator<Camera> it = array.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            Camera next = it.next();
            if (next instanceof OrthographicCamera) {
                i2++;
            } else if (next instanceof PerspectiveCamera) {
                i++;
            }
        }
        log("Cameras", "count", Integer.valueOf(array.size), "perspective", Integer.valueOf(i), "orthographic", Integer.valueOf(i2));
    }

    private void logLights(Array<BaseLight> array) {
        LightUtils.LightsInfo lightsInfo = LightUtils.getLightsInfo(new LightUtils.LightsInfo(), array);
        log("Lights", "count", Integer.valueOf(array.size), "dirs", Integer.valueOf(lightsInfo.dirLights), "points", Integer.valueOf(lightsInfo.pointLights), "spots", Integer.valueOf(lightsInfo.spotLights));
    }

    private void logScenes(SceneAsset sceneAsset) {
        ObjectSet objectSet = new ObjectSet();
        Array.ArrayIterator<SceneModel> it = sceneAsset.scenes.iterator();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int numVertices = 0;
        int numIndices = 0;
        int i5 = 0;
        int length = 0;
        while (it.hasNext()) {
            SceneModel next = it.next();
            i3 += next.model.meshes.size;
            this.allCameras.addAll(next.cameras.values().toArray());
            this.allLights.addAll(next.lights.values().toArray());
            Array.ArrayIterator<Mesh> it2 = next.model.meshes.iterator();
            while (it2.hasNext()) {
                Mesh next2 = it2.next();
                numVertices += next2.getNumVertices();
                numIndices += next2.getNumIndices() / 3;
            }
            Array<Node> allNodes = NodeUtil.getAllNodes((Array<Node>) new Array(), next.model);
            i += allNodes.size;
            Array.ArrayIterator<Node> it3 = allNodes.iterator();
            while (it3.hasNext()) {
                Node next3 = it3.next();
                if (next3.parts.size > 0) {
                    i4 += next3.parts.size;
                    Array.ArrayIterator<NodePart> it4 = next3.parts.iterator();
                    while (it4.hasNext()) {
                        NodePart next4 = it4.next();
                        i5 += next4.meshPart.size / 3;
                        objectSet.add(next4.material);
                        length += next4.bones != null ? next4.bones.length : 0;
                    }
                    i2++;
                }
            }
        }
        int i6 = objectSet.size;
        log("Scene Graph", "scenes", Integer.valueOf(sceneAsset.scenes.size), "nodes", Integer.valueOf(i), "empty", Integer.valueOf(i - i2));
        if (i3 > 0) {
            log("Mesh", "count", Integer.valueOf(i3), "parts", Integer.valueOf(i4), "Vertices", Integer.valueOf(numVertices), "Tris", Integer.valueOf(numIndices), "Rendered", Integer.valueOf(i5));
        }
        if (i6 > 0) {
            log("Materials", "count", Integer.valueOf(i6));
        }
        if (sceneAsset.maxBones > 0) {
            log("Bones", "max", Integer.valueOf(sceneAsset.maxBones), "count", Integer.valueOf(length));
        }
    }

    private void logTexures(SceneAsset sceneAsset) {
        log("Managed textures", "count", Integer.valueOf(sceneAsset.textures.size));
    }

    private void logAnimations(SceneAsset sceneAsset) {
        Array.ArrayIterator<Animation> it = sceneAsset.animations.iterator();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (it.hasNext()) {
            Array.ArrayIterator<NodeAnimation> it2 = it.next().nodeAnimations.iterator();
            while (it2.hasNext()) {
                NodeAnimation next = it2.next();
                i += next.translation == null ? 0 : next.translation.size;
                i2 += next.rotation == null ? 0 : next.rotation.size;
                i3 += next.scaling == null ? 0 : next.scaling.size;
                i4 += ((next instanceof NodeAnimationHack) && ((NodeAnimationHack) next).weights == null) ? 0 : ((NodeAnimationHack) next).weights.size;
            }
        }
        log("Animations", "count", Integer.valueOf(sceneAsset.animations.size), "KeyFrames", Integer.valueOf(i + i2 + i3 + i4), ExifInterface.GPS_DIRECTION_TRUE, Integer.valueOf(i), "R", Integer.valueOf(i2), ExifInterface.LATITUDE_SOUTH, Integer.valueOf(i3), ExifInterface.LONGITUDE_WEST, Integer.valueOf(i4));
    }

    private void log(String str, Object... objArr) {
        String str2 = "[" + str + "] ";
        for (int i = 0; i < objArr.length - 1; i += 2) {
            str2 = str2 + objArr[i] + ":" + objArr[i + 1] + " ";
        }
        Gdx.f82app.log("GLTF Inspector", str2);
    }
}
