package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/MipMapGenerator.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class MipMapGenerator {
    private static boolean useHWMipMap = true;

    private MipMapGenerator() {
    }

    public static void setUseHardwareMipMap(boolean z) {
        useHWMipMap = z;
    }

    public static void generateMipMap(Pixmap pixmap, int i, int i2) {
        generateMipMap(3553, pixmap, i, i2);
    }

    public static void generateMipMap(int i, Pixmap pixmap, int i2, int i3) {
        if (!useHWMipMap) {
            generateMipMapCPU(i, pixmap, i2, i3);
        } else if (Gdx.f82app.getType() == Application.ApplicationType.Android || Gdx.f82app.getType() == Application.ApplicationType.WebGL || Gdx.f82app.getType() == Application.ApplicationType.iOS) {
            generateMipMapGLES20(i, pixmap);
        } else {
            generateMipMapDesktop(i, pixmap, i2, i3);
        }
    }

    private static void generateMipMapGLES20(int i, Pixmap pixmap) {
        Gdx.gl.glTexImage2D(i, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
        Gdx.gl20.glGenerateMipmap(i);
    }

    private static void generateMipMapDesktop(int i, Pixmap pixmap, int i2, int i3) {
        if (Gdx.graphics.supportsExtension("GL_ARB_framebuffer_object") || Gdx.graphics.supportsExtension("GL_EXT_framebuffer_object") || Gdx.gl20.getClass().getName().equals("com.badlogic.gdx.backends.lwjgl3.Lwjgl3GLES20") || Gdx.gl30 != null) {
            Gdx.gl.glTexImage2D(i, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
            Gdx.gl20.glGenerateMipmap(i);
        } else {
            generateMipMapCPU(i, pixmap, i2, i3);
        }
    }

    private static void generateMipMapCPU(int i, Pixmap pixmap, int i2, int i3) {
        Gdx.gl.glTexImage2D(i, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
        if (Gdx.gl20 == null && i2 != i3) {
            throw new GdxRuntimeException("texture width and height must be square when using mipmapping.");
        }
        int width = pixmap.getWidth() / 2;
        int height = pixmap.getHeight() / 2;
        int i4 = 1;
        Pixmap pixmap2 = pixmap;
        while (width > 0 && height > 0) {
            Pixmap pixmap3 = new Pixmap(width, height, pixmap2.getFormat());
            pixmap3.setBlending(Pixmap.Blending.None);
            pixmap3.drawPixmap(pixmap2, 0, 0, pixmap2.getWidth(), pixmap2.getHeight(), 0, 0, width, height);
            if (i4 > 1) {
                pixmap2.dispose();
            }
            pixmap2 = pixmap3;
            Gdx.gl.glTexImage2D(i, i4, pixmap3.getGLInternalFormat(), pixmap3.getWidth(), pixmap3.getHeight(), 0, pixmap3.getGLFormat(), pixmap3.getGLType(), pixmap3.getPixels());
            width = pixmap2.getWidth() / 2;
            height = pixmap2.getHeight() / 2;
            i4++;
        }
    }
}
