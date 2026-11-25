package net.mgsx.gltf.loaders.shared.texture;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import net.mgsx.gltf.loaders.exceptions.GLTFRuntimeException;
import net.mgsx.gltf.loaders.exceptions.GLTFUnsupportedException;

/* JADX INFO: loaded from: classes12.dex */
public class PixmapBinaryLoaderHack {
    public static Pixmap load(byte[] bArr, int i, int i2) {
        if (Gdx.f82app.getType() == Application.ApplicationType.WebGL) {
            throw new GLTFUnsupportedException("load pixmap from bytes not supported for WebGL");
        }
        try {
            return (Pixmap) ClassReflection.getConstructor(Pixmap.class, byte[].class, Integer.TYPE, Integer.TYPE).newInstance(bArr, Integer.valueOf(i), Integer.valueOf(i2));
        } catch (ReflectionException e) {
            throw new GLTFRuntimeException(e);
        }
    }
}
