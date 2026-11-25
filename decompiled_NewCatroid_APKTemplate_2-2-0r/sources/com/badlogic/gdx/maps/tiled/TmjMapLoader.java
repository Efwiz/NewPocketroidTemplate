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
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/TmjMapLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TmjMapLoader extends BaseTmjMapLoader<BaseTiledMapLoader.Parameters> {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/TmjMapLoader$Parameters.class
      classes8.dex
     */
    public static class Parameters extends BaseTiledMapLoader.Parameters {
    }

    public TmjMapLoader() {
        super(new InternalFileHandleResolver());
    }

    public TmjMapLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    public TiledMap load(String str) {
        return load(str, new Parameters());
    }

    public TiledMap load(String str, Parameters parameters) {
        FileHandle fileHandleResolve = resolve(str);
        this.root = this.json.parse(fileHandleResolve);
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
    public void loadAsync(AssetManager assetManager, String str, FileHandle fileHandle, BaseTiledMapLoader.Parameters parameters) {
        this.map = loadTiledMap(fileHandle, parameters, new ImageResolver.AssetManagerImageResolver(assetManager));
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public TiledMap loadSync(AssetManager assetManager, String str, FileHandle fileHandle, BaseTiledMapLoader.Parameters parameters) {
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
        Iterator<JsonValue> itIterator2 = this.root.get("tileSets").iterator2();
        while (itIterator2.hasNext()) {
            getTileSetDependencyFileHandle(array, fileHandle, itIterator2.next());
        }
        collectImageLayerFileHandles(this.root.get("layers"), fileHandle, array);
        return array;
    }

    private void collectImageLayerFileHandles(JsonValue jsonValue, FileHandle fileHandle, Array<FileHandle> array) {
        if (jsonValue == null) {
            return;
        }
        Iterator<JsonValue> itIterator2 = jsonValue.iterator2();
        while (itIterator2.hasNext()) {
            JsonValue next = itIterator2.next();
            String string = next.getString(ModuleXmlParser.TYPE, "");
            if (string.equals("imagelayer")) {
                String string2 = next.getString(Constants.TMP_IMAGE_FILE_NAME, null);
                if (string2 != null) {
                    array.add(getRelativeFileHandle(fileHandle, string2));
                }
            } else if (string.equals("group")) {
                collectImageLayerFileHandles(next.get("layers"), fileHandle, array);
            }
        }
    }

    protected Array<FileHandle> getTileSetDependencyFileHandle(FileHandle fileHandle, JsonValue jsonValue) {
        return getTileSetDependencyFileHandle(new Array<>(), fileHandle, jsonValue);
    }

    protected Array<FileHandle> getTileSetDependencyFileHandle(Array<FileHandle> array, FileHandle fileHandle, JsonValue jsonValue) {
        String string = jsonValue.getString(FirebaseAnalytics.Param.SOURCE, null);
        if (string != null) {
            FileHandle relativeFileHandle = getRelativeFileHandle(fileHandle, string);
            JsonValue jsonValue2 = this.json.parse(relativeFileHandle);
            if (jsonValue2.has(Constants.TMP_IMAGE_FILE_NAME)) {
                array.add(getRelativeFileHandle(relativeFileHandle, jsonValue2.getString(Constants.TMP_IMAGE_FILE_NAME)));
            } else {
                Iterator<JsonValue> itIterator2 = jsonValue2.get("tile").iterator2();
                while (itIterator2.hasNext()) {
                    array.add(getRelativeFileHandle(relativeFileHandle, itIterator2.next().getString(Constants.TMP_IMAGE_FILE_NAME)));
                }
            }
        } else if (jsonValue.has(Constants.TMP_IMAGE_FILE_NAME)) {
            array.add(getRelativeFileHandle(fileHandle, jsonValue.getString(Constants.TMP_IMAGE_FILE_NAME)));
        } else {
            JsonValue jsonValue3 = jsonValue.get("tiles");
            if (jsonValue3 != null) {
                Iterator<JsonValue> itIterator3 = jsonValue3.iterator2();
                while (itIterator3.hasNext()) {
                    array.add(getRelativeFileHandle(fileHandle, itIterator3.next().getString(Constants.TMP_IMAGE_FILE_NAME)));
                }
            }
        }
        return array;
    }

    @Override // com.badlogic.gdx.maps.tiled.BaseTmjMapLoader
    protected void addStaticTiles(FileHandle fileHandle, ImageResolver imageResolver, TiledMapTileSet tiledMapTileSet, JsonValue jsonValue, JsonValue jsonValue2, String str, int i, int i2, int i3, int i4, int i5, String str2, int i6, int i7, String str3, int i8, int i9, FileHandle fileHandle2) {
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
        Iterator<JsonValue> itIterator2 = jsonValue2.iterator2();
        FileHandle relativeFileHandle = fileHandle2;
        while (itIterator2.hasNext()) {
            JsonValue next = itIterator2.next();
            if (next.has(Constants.TMP_IMAGE_FILE_NAME)) {
                String string = next.getString(Constants.TMP_IMAGE_FILE_NAME);
                if (str2 != null) {
                    relativeFileHandle = getRelativeFileHandle(getRelativeFileHandle(fileHandle, str2), string);
                } else {
                    relativeFileHandle = getRelativeFileHandle(fileHandle, string);
                }
            }
            addStaticTiledMapTile(tiledMapTileSet, imageResolver.getImage(relativeFileHandle.path()), i + next.getInt("id"), i6, i7);
        }
    }
}
