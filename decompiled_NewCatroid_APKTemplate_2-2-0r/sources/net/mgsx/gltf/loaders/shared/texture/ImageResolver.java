package net.mgsx.gltf.loaders.shared.texture;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import net.mgsx.gltf.data.texture.GLTFImage;
import net.mgsx.gltf.loaders.shared.data.DataFileResolver;

/* JADX INFO: loaded from: classes12.dex */
public class ImageResolver implements Disposable {
    private DataFileResolver dataFileResolver;
    private Array<Pixmap> pixmaps = new Array<>();

    public ImageResolver(DataFileResolver dataFileResolver) {
        this.dataFileResolver = dataFileResolver;
    }

    public void load(Array<GLTFImage> array) {
        if (array != null) {
            for (int i = 0; i < array.size; i++) {
                this.pixmaps.add(this.dataFileResolver.load(array.get(i)));
            }
        }
    }

    public Pixmap get(int i) {
        return this.pixmaps.get(i);
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        Array.ArrayIterator<Pixmap> it = this.pixmaps.iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        this.pixmaps.clear();
    }

    public void clear() {
        this.pixmaps.clear();
    }

    public Array<Pixmap> getPixmaps(Array<Pixmap> array) {
        array.addAll(this.pixmaps);
        return array;
    }
}
