package net.mgsx.gltf.scene3d.scene;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import net.mgsx.gltf.data.GLTF;

/* JADX INFO: loaded from: classes12.dex */
public class SceneAsset implements Disposable {
    public Array<Animation> animations;
    public GLTF data;
    public int maxBones;
    public Array<Mesh> meshes;
    public Array<Pixmap> pixmaps;
    public SceneModel scene;
    public Array<SceneModel> scenes;
    public Array<Texture> textures;

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        Array<SceneModel> array = this.scenes;
        if (array != null) {
            Array.ArrayIterator<SceneModel> it = array.iterator();
            while (it.hasNext()) {
                it.next().dispose();
            }
        }
        Array<Texture> array2 = this.textures;
        if (array2 != null) {
            Array.ArrayIterator<Texture> it2 = array2.iterator();
            while (it2.hasNext()) {
                it2.next().dispose();
            }
        }
        Array<Pixmap> array3 = this.pixmaps;
        if (array3 != null) {
            Array.ArrayIterator<Pixmap> it3 = array3.iterator();
            while (it3.hasNext()) {
                it3.next().dispose();
            }
        }
        Array<Mesh> array4 = this.meshes;
        if (array4 != null) {
            Array.ArrayIterator<Mesh> it4 = array4.iterator();
            while (it4.hasNext()) {
                it4.next().dispose();
            }
        }
    }
}
