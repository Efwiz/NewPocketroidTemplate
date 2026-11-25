package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.catrobat.catroid.common.Constants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/TmxMapLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TmxMapLoader extends BaseTmxMapLoader<Parameters> {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/TmxMapLoader$Parameters.class
      classes8.dex
     */
    public static class Parameters extends BaseTiledMapLoader.Parameters {
    }

    public TmxMapLoader() {
        super(new InternalFileHandleResolver());
    }

    public TmxMapLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    public TiledMap load(String str) {
        return load(str, new Parameters());
    }

    public TiledMap load(String str, Parameters parameters) {
        FileHandle fileHandleResolve = resolve(str);
        this.root = this.xml.parse(fileHandleResolve);
        ObjectMap objectMap = new ObjectMap();
        Array.ArrayIterator<FileHandle> it = getDependencyFileHandles(fileHandleResolve).iterator();
        while (it.hasNext()) {
            FileHandle next = it.next();
            Texture texture = new Texture(next, parameters.generateMipMaps);
            texture.setFilter(parameters.textureMinFilter, parameters.textureMagFilter);
            objectMap.put(next.path(), texture);
        }
        TiledMap tiledMapLoadTiledMap = loadTiledMap(fileHandleResolve, parameters, new ImageResolver.DirectImageResolver(objectMap));
        tiledMapLoadTiledMap.setOwnedResources(objectMap.values().toArray());
        return tiledMapLoadTiledMap;
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public void loadAsync(AssetManager assetManager, String str, FileHandle fileHandle, Parameters parameters) {
        this.map = loadTiledMap(fileHandle, parameters, new ImageResolver.AssetManagerImageResolver(assetManager));
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public TiledMap loadSync(AssetManager assetManager, String str, FileHandle fileHandle, Parameters parameters) {
        return this.map;
    }

    @Override // com.badlogic.gdx.maps.tiled.BaseTiledMapLoader
    protected Array<AssetDescriptor> getDependencyAssetDescriptors(FileHandle fileHandle, TextureLoader.TextureParameter textureParameter) {
        Array<AssetDescriptor> array = new Array<>();
        Array.ArrayIterator<FileHandle> it = getDependencyFileHandles(fileHandle).iterator();
        while (it.hasNext()) {
            array.add(new AssetDescriptor(it.next(), Texture.class, textureParameter));
        }
        return array;
    }

    protected Array<FileHandle> getDependencyFileHandles(FileHandle fileHandle) {
        Array<FileHandle> array = new Array<>();
        Array.ArrayIterator<XmlReader.Element> it = this.root.getChildrenByNameRecursively("tileset").iterator();
        while (it.hasNext()) {
            getTileSetDependencyFileHandle(array, fileHandle, it.next());
        }
        Array.ArrayIterator<XmlReader.Element> it2 = this.root.getChildrenByNameRecursively("imagelayer").iterator();
        while (it2.hasNext()) {
            String attribute = it2.next().getChildByName(Constants.TMP_IMAGE_FILE_NAME).getAttribute(FirebaseAnalytics.Param.SOURCE, null);
            if (attribute != null) {
                array.add(getRelativeFileHandle(fileHandle, attribute));
            }
        }
        return array;
    }

    protected Array<FileHandle> getTileSetDependencyFileHandle(FileHandle fileHandle, XmlReader.Element element) {
        return getTileSetDependencyFileHandle(new Array<>(), fileHandle, element);
    }

    protected Array<FileHandle> getTileSetDependencyFileHandle(Array<FileHandle> array, FileHandle fileHandle, XmlReader.Element element) {
        String attribute = element.getAttribute(FirebaseAnalytics.Param.SOURCE, null);
        if (attribute != null) {
            FileHandle relativeFileHandle = getRelativeFileHandle(fileHandle, attribute);
            XmlReader.Element element2 = this.xml.parse(relativeFileHandle);
            if (element2.getChildByName(Constants.TMP_IMAGE_FILE_NAME) != null) {
                array.add(getRelativeFileHandle(relativeFileHandle, element2.getChildByName(Constants.TMP_IMAGE_FILE_NAME).getAttribute(FirebaseAnalytics.Param.SOURCE)));
            } else {
                Array.ArrayIterator<XmlReader.Element> it = element2.getChildrenByName("tile").iterator();
                while (it.hasNext()) {
                    array.add(getRelativeFileHandle(relativeFileHandle, it.next().getChildByName(Constants.TMP_IMAGE_FILE_NAME).getAttribute(FirebaseAnalytics.Param.SOURCE)));
                }
            }
        } else if (element.getChildByName(Constants.TMP_IMAGE_FILE_NAME) != null) {
            array.add(getRelativeFileHandle(fileHandle, element.getChildByName(Constants.TMP_IMAGE_FILE_NAME).getAttribute(FirebaseAnalytics.Param.SOURCE)));
        } else {
            Array.ArrayIterator<XmlReader.Element> it2 = element.getChildrenByName("tile").iterator();
            while (it2.hasNext()) {
                array.add(getRelativeFileHandle(fileHandle, it2.next().getChildByName(Constants.TMP_IMAGE_FILE_NAME).getAttribute(FirebaseAnalytics.Param.SOURCE)));
            }
        }
        return array;
    }

    @Override // com.badlogic.gdx.maps.tiled.BaseTmxMapLoader
    protected void addStaticTiles(FileHandle fileHandle, ImageResolver imageResolver, TiledMapTileSet tiledMapTileSet, XmlReader.Element element, Array<XmlReader.Element> array, String str, int i, int i2, int i3, int i4, int i5, String str2, int i6, int i7, String str3, int i8, int i9, FileHandle fileHandle2) {
        int i10;
        MapProperties properties = tiledMapTileSet.getProperties();
        if (fileHandle2 != null) {
            TextureRegion image = imageResolver.getImage(fileHandle2.path());
            properties.put("imagesource", str3);
            properties.put("imagewidth", Integer.valueOf(i8));
            properties.put("imageheight", Integer.valueOf(i9));
            properties.put("tilewidth", Integer.valueOf(i2));
            properties.put("tileheight", Integer.valueOf(i3));
            properties.put("margin", Integer.valueOf(i5));
            properties.put("spacing", Integer.valueOf(i4));
            int regionWidth = image.getRegionWidth() - i2;
            int regionHeight = image.getRegionHeight() - i3;
            int i11 = i;
            int i12 = i5;
            while (i12 <= regionHeight) {
                int i13 = i5;
                while (true) {
                    i10 = i11;
                    if (i13 <= regionWidth) {
                        i11 = i10 + 1;
                        addStaticTiledMapTile(tiledMapTileSet, new TextureRegion(image, i13, i12, i2, i3), i10, i6, i7);
                        i13 += i2 + i4;
                    }
                }
                i12 += i3 + i4;
                i11 = i10;
            }
            return;
        }
        Array.ArrayIterator<XmlReader.Element> it = array.iterator();
        FileHandle relativeFileHandle = fileHandle2;
        while (it.hasNext()) {
            XmlReader.Element next = it.next();
            XmlReader.Element childByName = next.getChildByName(Constants.TMP_IMAGE_FILE_NAME);
            if (childByName != null) {
                String attribute = childByName.getAttribute(FirebaseAnalytics.Param.SOURCE);
                if (str2 != null) {
                    relativeFileHandle = getRelativeFileHandle(getRelativeFileHandle(fileHandle, str2), attribute);
                } else {
                    relativeFileHandle = getRelativeFileHandle(fileHandle, attribute);
                }
            }
            addStaticTiledMapTile(tiledMapTileSet, imageResolver.getImage(relativeFileHandle.path()), i + next.getIntAttribute("id"), i6, i7);
        }
    }
}
