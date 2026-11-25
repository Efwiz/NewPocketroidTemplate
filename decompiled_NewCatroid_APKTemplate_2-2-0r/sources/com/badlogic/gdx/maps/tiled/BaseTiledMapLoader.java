package com.badlogic.gdx.maps.tiled;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.BaseTiledMapLoader.Parameters;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/BaseTiledMapLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class BaseTiledMapLoader<P extends Parameters> extends AsynchronousAssetLoader<TiledMap, P> {
    protected static final int FLAG_FLIP_DIAGONALLY = 536870912;
    protected static final int FLAG_FLIP_HORIZONTALLY = Integer.MIN_VALUE;
    protected static final int FLAG_FLIP_VERTICALLY = 1073741824;
    protected static final int MASK_CLEAR = -536870912;
    protected boolean convertObjectToTileSpace;
    protected boolean flipY;
    protected IntMap<MapObject> idToObject;
    protected TiledMap map;
    protected int mapHeightInPixels;
    protected int mapTileHeight;
    protected int mapTileWidth;
    protected int mapWidthInPixels;
    protected ObjectMap<String, Array<ProjectClassMember>> projectClassInfo;
    protected Array<Runnable> runOnEndOfLoadTiled;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/BaseTiledMapLoader$Parameters.class
      classes8.dex
     */
    public static class Parameters extends AssetLoaderParameters<TiledMap> {
        public boolean generateMipMaps = false;
        public Texture.TextureFilter textureMinFilter = Texture.TextureFilter.Nearest;
        public Texture.TextureFilter textureMagFilter = Texture.TextureFilter.Nearest;
        public boolean convertObjectToTileSpace = false;
        public boolean flipY = true;
        public String projectFilePath = null;
    }

    protected static int unsignedByteToInt(byte b) {
        return b & 255;
    }

    protected abstract Array<AssetDescriptor> getDependencyAssetDescriptors(FileHandle fileHandle, TextureLoader.TextureParameter textureParameter);

    protected abstract TiledMap loadTiledMap(FileHandle fileHandle, P p, ImageResolver imageResolver);

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/BaseTiledMapLoader$ProjectClassMember.class
      classes8.dex
     */
    protected static class ProjectClassMember {
        public JsonValue defaultValue;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f118name;
        public String propertyType;
        public String type;

        protected ProjectClassMember() {
        }

        public String toString() {
            return "ProjectClassMember{name='" + this.f118name + "', type='" + this.type + "', propertyType='" + this.propertyType + "', defaultValue=" + this.defaultValue + "}";
        }
    }

    public BaseTiledMapLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
        this.flipY = true;
    }

    public IntMap<MapObject> getIdToObject() {
        return this.idToObject;
    }

    protected Object castProperty(String str, String str2, String str3) {
        if (str3 == null || "string".equals(str3) || "file".equals(str3)) {
            return str2;
        }
        if (str3.equals("int")) {
            return Integer.valueOf(str2);
        }
        if (str3.equals("float")) {
            return Float.valueOf(str2);
        }
        if (str3.equals("bool")) {
            return Boolean.valueOf(str2);
        }
        if (str3.equals(TypedValues.Custom.S_COLOR)) {
            return Color.valueOf(tiledColorToLibGDXColor(str2));
        }
        throw new GdxRuntimeException("Wrong type given for property " + str + ", given : " + str3 + ", supported : string, file, bool, int, float, color");
    }

    protected TiledMapTileLayer.Cell createTileLayerCell(boolean z, boolean z2, boolean z3) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        if (!z3) {
            cell.setFlipHorizontally(z);
            cell.setFlipVertically(z2);
        } else if (z && z2) {
            cell.setFlipHorizontally(true);
            cell.setRotation(3);
        } else if (z) {
            cell.setRotation(3);
        } else if (z2) {
            cell.setRotation(1);
        } else {
            cell.setFlipVertically(true);
            cell.setRotation(3);
        }
        return cell;
    }

    protected static FileHandle getRelativeFileHandle(FileHandle fileHandle, String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, "\\/");
        FileHandle fileHandleParent = fileHandle.parent();
        while (stringTokenizer.hasMoreElements()) {
            String strNextToken = stringTokenizer.nextToken();
            if (strNextToken.equals("..")) {
                fileHandleParent = fileHandleParent.parent();
            } else {
                fileHandleParent = fileHandleParent.child(strNextToken);
            }
        }
        return fileHandleParent;
    }

    protected void addStaticTiledMapTile(TiledMapTileSet tiledMapTileSet, TextureRegion textureRegion, int i, float f, float f2) {
        StaticTiledMapTile staticTiledMapTile = new StaticTiledMapTile(textureRegion);
        staticTiledMapTile.setId(i);
        staticTiledMapTile.setOffsetX(f);
        if (this.flipY) {
            f2 = -f2;
        }
        staticTiledMapTile.setOffsetY(f2);
        tiledMapTileSet.putTile(i, staticTiledMapTile);
    }

    protected void loadObjectProperty(final MapProperties mapProperties, final String str, String str2) {
        try {
            final int i = Integer.parseInt(str2);
            this.runOnEndOfLoadTiled.add(new Runnable() { // from class: com.badlogic.gdx.maps.tiled.BaseTiledMapLoader.1
                @Override // java.lang.Runnable
                public void run() {
                    mapProperties.put(str, BaseTiledMapLoader.this.idToObject.get(i));
                }
            });
        } catch (Exception e) {
            throw new GdxRuntimeException("Error parsing property [\" + name + \"] of type \"object\" with value: [" + str2 + "]", e);
        }
    }

    protected void loadBasicProperty(MapProperties mapProperties, String str, String str2, String str3) {
        mapProperties.put(str, castProperty(str, str2, str3));
    }

    protected void loadProjectFile(String str) {
        this.projectClassInfo = new ObjectMap<>();
        if (str == null || str.trim().isEmpty()) {
            return;
        }
        JsonValue jsonValue = new JsonReader().parse(resolve(str)).get("propertyTypes");
        if (jsonValue == null) {
            return;
        }
        Iterator<JsonValue> itIterator2 = jsonValue.iterator2();
        while (itIterator2.hasNext()) {
            JsonValue next = itIterator2.next();
            if ("class".equals(next.getString(ModuleXmlParser.TYPE))) {
                String string = next.getString("name");
                JsonValue jsonValue2 = next.get("members");
                if (!jsonValue2.isEmpty()) {
                    Array<ProjectClassMember> array = new Array<>();
                    this.projectClassInfo.put(string, array);
                    Iterator<JsonValue> itIterator3 = jsonValue2.iterator2();
                    while (itIterator3.hasNext()) {
                        JsonValue next2 = itIterator3.next();
                        ProjectClassMember projectClassMember = new ProjectClassMember();
                        projectClassMember.f118name = next2.getString("name");
                        projectClassMember.type = next2.getString(ModuleXmlParser.TYPE);
                        projectClassMember.propertyType = next2.getString("propertyType", null);
                        projectClassMember.defaultValue = next2.get("value");
                        array.add(projectClassMember);
                    }
                }
            }
        }
    }

    protected void loadJsonClassProperties(String str, MapProperties mapProperties, JsonValue jsonValue) {
        ObjectMap<String, Array<ProjectClassMember>> objectMap = this.projectClassInfo;
        if (objectMap == null) {
            throw new GdxRuntimeException("No class information loaded to support class properties. Did you set the 'projectFilePath' parameter?");
        }
        if (objectMap.isEmpty()) {
            throw new GdxRuntimeException("No class information available. Did you set the correct Tiled project path in the 'projectFilePath' parameter?");
        }
        Array<ProjectClassMember> array = this.projectClassInfo.get(str);
        if (array == null) {
            throw new GdxRuntimeException("There is no class with name '" + str + "' in given Tiled project file.");
        }
        Array.ArrayIterator<ProjectClassMember> it = array.iterator();
        while (it.hasNext()) {
            ProjectClassMember next = it.next();
            String str2 = next.f118name;
            JsonValue jsonValue2 = jsonValue.get(str2);
            String str3 = next.type;
            str3.hashCode();
            if (str3.equals("object")) {
                loadObjectProperty(mapProperties, str2, jsonValue2 == null ? next.defaultValue.asString() : jsonValue2.asString());
            } else if (str3.equals("class")) {
                if (jsonValue2 == null) {
                    jsonValue2 = next.defaultValue;
                }
                MapProperties mapProperties2 = new MapProperties();
                String str4 = next.propertyType;
                mapProperties2.put(ModuleXmlParser.TYPE, str4);
                mapProperties.put(str2, mapProperties2);
                loadJsonClassProperties(str4, mapProperties2, jsonValue2);
            } else {
                if (jsonValue2 == null) {
                    jsonValue2 = next.defaultValue;
                }
                loadBasicProperty(mapProperties, str2, jsonValue2.asString(), next.type);
            }
        }
    }

    public static String tiledColorToLibGDXColor(String str) {
        return (str.length() == 9 ? str.substring(3) : str.substring(1)) + (str.length() == 9 ? str.substring(1, 3) : ConfigConstants.CONFIG_KEY_FF);
    }
}
