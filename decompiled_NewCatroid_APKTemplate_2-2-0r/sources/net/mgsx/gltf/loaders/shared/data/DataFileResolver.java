package net.mgsx.gltf.loaders.shared.data;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import java.nio.ByteBuffer;
import net.mgsx.gltf.data.GLTF;
import net.mgsx.gltf.data.texture.GLTFImage;

/* JADX INFO: loaded from: classes12.dex */
public interface DataFileResolver {
    ByteBuffer getBuffer(int i);

    GLTF getRoot();

    Pixmap load(GLTFImage gLTFImage);

    void load(FileHandle fileHandle);
}
