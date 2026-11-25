package net.mgsx.gltf.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import net.mgsx.gltf.demo.data.ModelEntry;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFTest extends ApplicationAdapter {
    private String samplesPath;

    public GLTFTest() {
        this("models");
    }

    public GLTFTest(String str) {
        this.samplesPath = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void create() {
        SceneAsset sceneAssetLoad;
        FileHandle fileHandleInternal = Gdx.files.internal(this.samplesPath);
        Array.ArrayIterator it = ((Array) new Json().fromJson(Array.class, ModelEntry.class, fileHandleInternal.child("model-index.json"))).iterator();
        while (it.hasNext()) {
            ModelEntry modelEntry = (ModelEntry) it.next();
            ObjectMap.Entries<String, String> it2 = modelEntry.variants.iterator();
            while (it2.hasNext()) {
                ObjectMap.Entry next = it2.next();
                FileHandle fileHandleChild = fileHandleInternal.child(modelEntry.f410name).child((String) next.key).child((String) next.value);
                long jCurrentTimeMillis = System.currentTimeMillis();
                try {
                    if (fileHandleChild.extension().equals("gltf")) {
                        sceneAssetLoad = new GLTFLoader().load(fileHandleChild);
                    } else if (fileHandleChild.extension().equals("glb")) {
                        sceneAssetLoad = new GLBLoader().load(fileHandleChild);
                    } else {
                        throw new GdxRuntimeException("unknown file extension " + fileHandleChild.extension());
                    }
                    System.out.println(modelEntry.f410name + " | " + ((String) next.key) + " | ok | " + ((System.currentTimeMillis() - jCurrentTimeMillis) / 1000.0f));
                    sceneAssetLoad.dispose();
                } catch (GdxRuntimeException e) {
                    System.err.println(modelEntry.f410name + " | " + ((String) next.key) + " | error | " + e.getMessage());
                }
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException unused) {
                }
            }
        }
    }
}
