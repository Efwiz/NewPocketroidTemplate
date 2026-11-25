package net.mgsx.gltf.scene3d.shaders;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes12.dex */
public class PBRCommon {
    public static final int MAX_MORPH_TARGETS = 8;
    private static final IntBuffer intBuffer = BufferUtils.newIntBuffer(16);
    private static Boolean seamlessCubemapsShouldBeEnabled = null;

    public static int getCapability(int i) {
        IntBuffer intBuffer2 = intBuffer;
        intBuffer2.clear();
        Gdx.gl.glGetIntegerv(i, intBuffer2);
        return intBuffer2.get();
    }

    public static void checkVertexAttributes(Renderable renderable) {
        int size = renderable.meshPart.mesh.getVertexAttributes().size();
        int capability = getCapability(GL20.GL_MAX_VERTEX_ATTRIBS);
        if (size > capability) {
            throw new GdxRuntimeException("too many vertex attributes : " + size + " > " + capability);
        }
    }

    public static void enableSeamlessCubemaps() {
        if (seamlessCubemapsShouldBeEnabled == null) {
            boolean z = true;
            if (Gdx.f82app.getType() == Application.ApplicationType.Desktop) {
                if (!Gdx.graphics.getGLVersion().isVersionEqualToOrHigher(3, 2) && !Gdx.graphics.supportsExtension("GL_ARB_seamless_cube_map")) {
                    z = false;
                }
                seamlessCubemapsShouldBeEnabled = Boolean.valueOf(z);
            } else {
                z = Gdx.gl30 != null;
                seamlessCubemapsShouldBeEnabled = false;
            }
            if (!z) {
                Gdx.f82app.error("PBR", "Warning seamless CubeMap is not supported by this platform and may cause filtering artifacts");
            }
        }
        if (seamlessCubemapsShouldBeEnabled.booleanValue()) {
            Gdx.gl.glEnable(34895);
        }
    }
}
