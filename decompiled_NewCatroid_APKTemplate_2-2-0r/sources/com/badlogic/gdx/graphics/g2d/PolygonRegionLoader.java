package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedReader;
import java.io.IOException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PolygonRegionLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PolygonRegionLoader extends SynchronousAssetLoader<PolygonRegion, PolygonRegionParameters> {
    private PolygonRegionParameters defaultParameters;
    private EarClippingTriangulator triangulator;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/PolygonRegionLoader$PolygonRegionParameters.class
      classes8.dex
     */
    public static class PolygonRegionParameters extends AssetLoaderParameters<PolygonRegion> {
        public String texturePrefix = "i ";
        public int readerBuffer = 1024;
        public String[] textureExtensions = {"png", "PNG", "jpeg", "JPEG", "jpg", "JPG", "cim", "CIM", "etc1", "ETC1", "ktx", "KTX", "zktx", "ZKTX"};
    }

    public PolygonRegionLoader() {
        this(new InternalFileHandleResolver());
    }

    public PolygonRegionLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
        this.defaultParameters = new PolygonRegionParameters();
        this.triangulator = new EarClippingTriangulator();
    }

    @Override // com.badlogic.gdx.assets.loaders.SynchronousAssetLoader
    public PolygonRegion load(AssetManager assetManager, String str, FileHandle fileHandle, PolygonRegionParameters polygonRegionParameters) {
        return load(new TextureRegion((Texture) assetManager.get(assetManager.getDependencies(str).first())), fileHandle);
    }

    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, PolygonRegionParameters polygonRegionParameters) {
        String strName;
        if (polygonRegionParameters == null) {
            polygonRegionParameters = this.defaultParameters;
        }
        try {
            BufferedReader erVar = fileHandle.reader(polygonRegionParameters.readerBuffer);
            while (true) {
                String line = erVar.readLine();
                if (line == null) {
                    strName = null;
                    break;
                }
                if (line.startsWith(polygonRegionParameters.texturePrefix)) {
                    strName = line.substring(polygonRegionParameters.texturePrefix.length());
                    break;
                }
            }
            erVar.close();
            if (strName == null && polygonRegionParameters.textureExtensions != null) {
                for (String str2 : polygonRegionParameters.textureExtensions) {
                    FileHandle fileHandleSibling = fileHandle.sibling(fileHandle.nameWithoutExtension().concat("." + str2));
                    if (fileHandleSibling.exists()) {
                        strName = fileHandleSibling.name();
                    }
                }
            }
            if (strName == null) {
                return null;
            }
            Array<AssetDescriptor> array = new Array<>(1);
            array.add(new AssetDescriptor(fileHandle.sibling(strName), Texture.class));
            return array;
        } catch (IOException e) {
            throw new GdxRuntimeException("Error reading " + str, e);
        }
    }

    public PolygonRegion load(TextureRegion textureRegion, FileHandle fileHandle) {
        String line;
        BufferedReader erVar = fileHandle.reader(256);
        do {
            try {
                try {
                    line = erVar.readLine();
                    if (line == null) {
                        StreamUtils.closeQuietly(erVar);
                        throw new GdxRuntimeException("Polygon shape not found: " + fileHandle);
                    }
                } catch (IOException e) {
                    throw new GdxRuntimeException("Error reading polygon shape file: " + fileHandle, e);
                }
            } catch (Throwable th) {
                StreamUtils.closeQuietly(erVar);
                throw th;
            }
            StreamUtils.closeQuietly(erVar);
            throw th;
        } while (!line.startsWith("s"));
        String[] strArrSplit = line.substring(1).trim().split(",");
        int length = strArrSplit.length;
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = Float.parseFloat(strArrSplit[i]);
        }
        PolygonRegion polygonRegion = new PolygonRegion(textureRegion, fArr, this.triangulator.computeTriangles(fArr).toArray());
        StreamUtils.closeQuietly(erVar);
        return polygonRegion;
    }
}
