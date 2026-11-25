package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.FloatBuffer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/FloatTextureData.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class FloatTextureData implements TextureData {
    FloatBuffer buffer;
    int format;
    int height;
    int internalFormat;
    boolean isGpuOnly;
    boolean isPrepared = false;
    int type;
    int width;

    @Override // com.badlogic.gdx.graphics.TextureData
    public boolean isManaged() {
        return true;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public boolean useMipMaps() {
        return false;
    }

    public FloatTextureData(int i, int i2, int i3, int i4, int i5, boolean z) {
        this.width = i;
        this.height = i2;
        this.internalFormat = i3;
        this.format = i4;
        this.type = i5;
        this.isGpuOnly = z;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public TextureData.TextureDataType getType() {
        return TextureData.TextureDataType.Custom;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public boolean isPrepared() {
        return this.isPrepared;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public void prepare() {
        if (this.isPrepared) {
            throw new GdxRuntimeException("Already prepared");
        }
        if (!this.isGpuOnly) {
            int i = 4;
            if (Gdx.graphics.getGLVersion().getType().equals(GLVersion.Type.OpenGL)) {
                int i2 = this.internalFormat;
                if (i2 != 34842) {
                }
                i = (i2 == 34843 || i2 == 34837) ? 3 : 4;
                if (i2 == 33327 || i2 == 33328) {
                    i = 2;
                }
                if (i2 == 33325 || i2 == 33326) {
                    i = 1;
                }
            }
            this.buffer = BufferUtils.newFloatBuffer(this.width * this.height * i);
        }
        this.isPrepared = true;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public void consumeCustomData(int i) {
        if (Gdx.f82app.getType() == Application.ApplicationType.Android || Gdx.f82app.getType() == Application.ApplicationType.iOS || (Gdx.f82app.getType() == Application.ApplicationType.WebGL && !Gdx.graphics.isGL30Available())) {
            if (!Gdx.graphics.supportsExtension("OES_texture_float")) {
                throw new GdxRuntimeException("Extension OES_texture_float not supported!");
            }
            Gdx.gl.glTexImage2D(i, 0, GL20.GL_RGBA, this.width, this.height, 0, GL20.GL_RGBA, 5126, this.buffer);
        } else {
            if (!Gdx.graphics.isGL30Available() && !Gdx.graphics.supportsExtension("GL_ARB_texture_float")) {
                throw new GdxRuntimeException("Extension GL_ARB_texture_float not supported!");
            }
            Gdx.gl.glTexImage2D(i, 0, this.internalFormat, this.width, this.height, 0, this.format, 5126, this.buffer);
        }
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public Pixmap consumePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public boolean disposePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public int getWidth() {
        return this.width;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public int getHeight() {
        return this.height;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public Pixmap.Format getFormat() {
        return Pixmap.Format.RGBA8888;
    }

    public FloatBuffer getBuffer() {
        return this.buffer;
    }
}
