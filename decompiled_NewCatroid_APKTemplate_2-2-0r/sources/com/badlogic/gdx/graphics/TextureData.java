package com.badlogic.gdx.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ETC1TextureData;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.KTXTextureData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/TextureData.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface TextureData {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/TextureData$TextureDataType.class
      classes8.dex
     */
    public enum TextureDataType {
        Pixmap,
        Custom
    }

    void consumeCustomData(int i);

    Pixmap consumePixmap();

    boolean disposePixmap();

    Pixmap.Format getFormat();

    int getHeight();

    TextureDataType getType();

    int getWidth();

    boolean isManaged();

    boolean isPrepared();

    void prepare();

    boolean useMipMaps();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/TextureData$Factory.class
      classes8.dex
     */
    public static class Factory {
        public static TextureData loadFromFile(FileHandle fileHandle, boolean z) {
            return loadFromFile(fileHandle, null, z);
        }

        public static TextureData loadFromFile(FileHandle fileHandle, Pixmap.Format format, boolean z) {
            if (fileHandle == null) {
                return null;
            }
            if (fileHandle.name().endsWith(".cim")) {
                return new FileTextureData(fileHandle, PixmapIO.readCIM(fileHandle), format, z);
            }
            if (fileHandle.name().endsWith(".etc1")) {
                return new ETC1TextureData(fileHandle, z);
            }
            if (fileHandle.name().endsWith(".ktx") || fileHandle.name().endsWith(".zktx")) {
                return new KTXTextureData(fileHandle, z);
            }
            return new FileTextureData(fileHandle, new Pixmap(fileHandle), format, z);
        }
    }
}
