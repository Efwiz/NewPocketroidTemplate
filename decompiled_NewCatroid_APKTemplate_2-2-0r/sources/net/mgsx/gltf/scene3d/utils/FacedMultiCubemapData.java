package net.mgsx.gltf.scene3d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.CubemapData;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX INFO: loaded from: classes12.dex */
public class FacedMultiCubemapData implements CubemapData {
    protected final TextureData[] data;
    private int levels;

    @Override // com.badlogic.gdx.graphics.CubemapData
    public boolean isPrepared() {
        return false;
    }

    public FacedMultiCubemapData(FileHandle[] fileHandleArr, int i) {
        this.levels = i;
        this.data = new TextureData[i * 6];
        for (int i2 = 0; i2 < i; i2++) {
            for (int i3 = 0; i3 < 6; i3++) {
                int i4 = (i2 * 6) + i3;
                this.data[i4] = new PixmapTextureData(new Pixmap(fileHandleArr[i4]), null, false, true);
            }
        }
    }

    public FacedMultiCubemapData(Pixmap[] pixmapArr, int i) {
        this.levels = i;
        this.data = new TextureData[i * 6];
        for (int i2 = 0; i2 < i; i2++) {
            for (int i3 = 0; i3 < 6; i3++) {
                int i4 = (i2 * 6) + i3;
                this.data[i4] = new PixmapTextureData(pixmapArr[i4], null, false, true);
            }
        }
    }

    @Override // com.badlogic.gdx.graphics.CubemapData
    public boolean isManaged() {
        for (TextureData textureData : this.data) {
            if (!textureData.isManaged()) {
                return false;
            }
        }
        return true;
    }

    public boolean isComplete() {
        int i = 0;
        while (true) {
            TextureData[] textureDataArr = this.data;
            if (i >= textureDataArr.length) {
                return true;
            }
            if (textureDataArr[i] == null) {
                return false;
            }
            i++;
        }
    }

    public TextureData getTextureData(Cubemap.CubemapSide cubemapSide) {
        return this.data[cubemapSide.index];
    }

    @Override // com.badlogic.gdx.graphics.CubemapData
    public int getWidth() {
        int width;
        int width2;
        int width3;
        int width4;
        if (this.data[Cubemap.CubemapSide.PositiveZ.index] == null || (width = this.data[Cubemap.CubemapSide.PositiveZ.index].getWidth()) <= 0) {
            width = 0;
        }
        if (this.data[Cubemap.CubemapSide.NegativeZ.index] != null && (width4 = this.data[Cubemap.CubemapSide.NegativeZ.index].getWidth()) > width) {
            width = width4;
        }
        if (this.data[Cubemap.CubemapSide.PositiveY.index] != null && (width3 = this.data[Cubemap.CubemapSide.PositiveY.index].getWidth()) > width) {
            width = width3;
        }
        return (this.data[Cubemap.CubemapSide.NegativeY.index] == null || (width2 = this.data[Cubemap.CubemapSide.NegativeY.index].getWidth()) <= width) ? width : width2;
    }

    @Override // com.badlogic.gdx.graphics.CubemapData
    public int getHeight() {
        int height;
        int height2;
        int height3;
        int height4;
        if (this.data[Cubemap.CubemapSide.PositiveZ.index] == null || (height = this.data[Cubemap.CubemapSide.PositiveZ.index].getHeight()) <= 0) {
            height = 0;
        }
        if (this.data[Cubemap.CubemapSide.NegativeZ.index] != null && (height4 = this.data[Cubemap.CubemapSide.NegativeZ.index].getHeight()) > height) {
            height = height4;
        }
        if (this.data[Cubemap.CubemapSide.PositiveX.index] != null && (height3 = this.data[Cubemap.CubemapSide.PositiveX.index].getHeight()) > height) {
            height = height3;
        }
        return (this.data[Cubemap.CubemapSide.NegativeX.index] == null || (height2 = this.data[Cubemap.CubemapSide.NegativeX.index].getHeight()) <= height) ? height : height2;
    }

    @Override // com.badlogic.gdx.graphics.CubemapData
    public void prepare() {
        if (!isComplete()) {
            throw new GdxRuntimeException("You need to complete your cubemap data before using it");
        }
        int i = 0;
        while (true) {
            TextureData[] textureDataArr = this.data;
            if (i >= textureDataArr.length) {
                return;
            }
            if (!textureDataArr[i].isPrepared()) {
                this.data[i].prepare();
            }
            i++;
        }
    }

    @Override // com.badlogic.gdx.graphics.CubemapData
    public void consumeCubemapData() {
        Pixmap pixmap;
        boolean z;
        for (int i = 0; i < this.levels; i++) {
            for (int i2 = 0; i2 < 6; i2++) {
                int i3 = (i * 6) + i2;
                if (this.data[i3].getType() == TextureData.TextureDataType.Custom) {
                    this.data[i3].consumeCustomData(GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i2);
                } else {
                    Pixmap pixmapConsumePixmap = this.data[i3].consumePixmap();
                    boolean zDisposePixmap = this.data[i3].disposePixmap();
                    if (this.data[i3].getFormat() != pixmapConsumePixmap.getFormat()) {
                        Pixmap pixmap2 = new Pixmap(pixmapConsumePixmap.getWidth(), pixmapConsumePixmap.getHeight(), this.data[i3].getFormat());
                        pixmap2.setBlending(Pixmap.Blending.None);
                        pixmap2.drawPixmap(pixmapConsumePixmap, 0, 0, 0, 0, pixmapConsumePixmap.getWidth(), pixmapConsumePixmap.getHeight());
                        if (this.data[i3].disposePixmap()) {
                            pixmapConsumePixmap.dispose();
                        }
                        pixmap = pixmap2;
                        z = true;
                    } else {
                        pixmap = pixmapConsumePixmap;
                        z = zDisposePixmap;
                    }
                    Gdx.gl.glPixelStorei(3317, 1);
                    Gdx.gl.glTexImage2D(i2 + GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X, i, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
                    if (z) {
                        pixmap.dispose();
                    }
                }
            }
        }
    }
}
