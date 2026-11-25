package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/glutils/FileTextureData.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class FileTextureData implements TextureData {
    final FileHandle file;
    Pixmap.Format format;
    int height;
    boolean isPrepared = false;
    Pixmap pixmap;
    boolean useMipMaps;
    int width;

    @Override // com.badlogic.gdx.graphics.TextureData
    public boolean disposePixmap() {
        return true;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public boolean isManaged() {
        return true;
    }

    public FileTextureData(FileHandle fileHandle, Pixmap pixmap, Pixmap.Format format, boolean z) {
        this.width = 0;
        this.height = 0;
        this.file = fileHandle;
        this.pixmap = pixmap;
        this.format = format;
        this.useMipMaps = z;
        if (pixmap != null) {
            this.width = pixmap.getWidth();
            this.height = this.pixmap.getHeight();
            if (format == null) {
                this.format = this.pixmap.getFormat();
            }
        }
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
        if (this.pixmap == null) {
            if (this.file.extension().equals("cim")) {
                this.pixmap = PixmapIO.readCIM(this.file);
            } else {
                this.pixmap = new Pixmap(this.file);
            }
            this.width = this.pixmap.getWidth();
            this.height = this.pixmap.getHeight();
            if (this.format == null) {
                this.format = this.pixmap.getFormat();
            }
        }
        this.isPrepared = true;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public Pixmap consumePixmap() {
        if (!this.isPrepared) {
            throw new GdxRuntimeException("Call prepare() before calling getPixmap()");
        }
        this.isPrepared = false;
        Pixmap pixmap = this.pixmap;
        this.pixmap = null;
        return pixmap;
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
        return this.format;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public boolean useMipMaps() {
        return this.useMipMaps;
    }

    public FileHandle getFileHandle() {
        return this.file;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public TextureData.TextureDataType getType() {
        return TextureData.TextureDataType.Pixmap;
    }

    @Override // com.badlogic.gdx.graphics.TextureData
    public void consumeCustomData(int i) {
        throw new GdxRuntimeException("This TextureData implementation does not upload data itself");
    }

    public String toString() {
        return this.file.toString();
    }
}
