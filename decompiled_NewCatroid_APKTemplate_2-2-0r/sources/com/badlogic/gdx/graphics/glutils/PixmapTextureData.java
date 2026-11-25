package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/PixmapTextureData.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PixmapTextureData implements TextureData {
    final boolean disposePixmap;
    final Pixmap.Format format;
    final boolean managed;
    final Pixmap pixmap;
    final boolean useMipMaps;

    @Override // com.badlogic.gdx.graphics.TextureData
    public boolean isPrepared() {
        return true;
    }

    public PixmapTextureData(Pixmap pixmap, Pixmap.Format format, boolean z, boolean z2) {
        this(pixmap, format, z, z2, false);
    }

    public PixmapTextureData(Pixmap pixmap, Pixmap.Format format, boolean z, boolean z2, boolean z3) {
        this.pixmap = pixmap;
        this.format = format == null ? pixmap.getFormat() : format;
        this.useMipMaps = z;
        this.disposePixmap = z2;
        this.managed = z3;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public boolean disposePixmap() {
        return this.disposePixmap;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public Pixmap consumePixmap() {
        return this.pixmap;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public int getWidth() {
        return this.pixmap.getWidth();
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public int getHeight() {
        return this.pixmap.getHeight();
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public Pixmap.Format getFormat() {
        return this.format;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public boolean useMipMaps() {
        return this.useMipMaps;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public boolean isManaged() {
        return this.managed;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public TextureData.TextureDataType getType() {
        return TextureData.TextureDataType.Pixmap;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public void consumeCustomData(int i) {
        throw new GdxRuntimeException("This TextureData implementation does not upload data itself");
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public void prepare() {
        throw new GdxRuntimeException("prepare() must not be called on a PixmapTextureData instance as it is already prepared.");
    }
}
