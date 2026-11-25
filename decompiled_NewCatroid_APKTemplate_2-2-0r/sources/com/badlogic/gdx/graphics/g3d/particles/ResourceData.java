package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ResourceData.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ResourceData<T> implements Json.Serializable {
    private int currentLoadIndex;
    private Array<SaveData> data;
    public T resource;
    Array<AssetData> sharedAssets;
    private ObjectMap<String, SaveData> uniqueData;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ResourceData$Configurable.class
      classes8.dex
     */
    public interface Configurable<T> {
        void load(AssetManager assetManager, ResourceData<T> resourceData);

        void save(AssetManager assetManager, ResourceData<T> resourceData);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ResourceData$SaveData.class
      classes8.dex
     */
    public static class SaveData implements Json.Serializable {
        protected ResourceData resources;
        ObjectMap<String, Object> data = new ObjectMap<>();
        IntArray assets = new IntArray();
        private int loadIndex = 0;

        public SaveData() {
        }

        public SaveData(ResourceData resourceData) {
            this.resources = resourceData;
        }

        public <K> void saveAsset(String str, Class<K> cls) {
            int assetData = this.resources.getAssetData(str, cls);
            if (assetData == -1) {
                this.resources.sharedAssets.add(new AssetData(str, cls));
                assetData = this.resources.sharedAssets.size - 1;
            }
            this.assets.add(assetData);
        }

        public void save(String str, Object obj) {
            this.data.put(str, obj);
        }

        public AssetDescriptor loadAsset() {
            if (this.loadIndex == this.assets.size) {
                return null;
            }
            Array<AssetData> array = this.resources.sharedAssets;
            IntArray intArray = this.assets;
            int i = this.loadIndex;
            this.loadIndex = i + 1;
            AssetData assetData = array.get(intArray.get(i));
            return new AssetDescriptor(assetData.filename, assetData.type);
        }

        public <K> K load(String str) {
            return (K) this.data.get(str);
        }

        @Override // com.badlogic.gdx.utils.Json.Serializable
        public void write(Json json) {
            json.writeValue("data", this.data, ObjectMap.class);
            json.writeValue("indices", this.assets.toArray(), int[].class);
        }

        @Override // com.badlogic.gdx.utils.Json.Serializable
        public void read(Json json, JsonValue jsonValue) {
            this.data = (ObjectMap) json.readValue("data", ObjectMap.class, jsonValue);
            this.assets.addAll((int[]) json.readValue("indices", int[].class, jsonValue));
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ResourceData$AssetData.class
      classes8.dex
     */
    public static class AssetData<T> implements Json.Serializable {
        public String filename;
        public Class<T> type;

        public AssetData() {
        }

        public AssetData(String str, Class<T> cls) {
            this.filename = str;
            this.type = cls;
        }

        @Override // com.badlogic.gdx.utils.Json.Serializable
        public void write(Json json) {
            json.writeValue("filename", this.filename);
            json.writeValue(ModuleXmlParser.TYPE, this.type.getName());
        }

        @Override // com.badlogic.gdx.utils.Json.Serializable
        public void read(Json json, JsonValue jsonValue) {
            this.filename = (String) json.readValue("filename", String.class, jsonValue);
            String str = (String) json.readValue(ModuleXmlParser.TYPE, String.class, jsonValue);
            try {
                this.type = ClassReflection.forName(str);
            } catch (ReflectionException e) {
                throw new GdxRuntimeException("Class not found: " + str, e);
            }
        }
    }

    public ResourceData() {
        this.uniqueData = new ObjectMap<>();
        this.data = new Array<>(true, 3, SaveData.class);
        this.sharedAssets = new Array<>();
        this.currentLoadIndex = 0;
    }

    public ResourceData(T t) {
        this();
        this.resource = t;
    }

    <K> int getAssetData(String str, Class<K> cls) {
        Array.ArrayIterator<AssetData> it = this.sharedAssets.iterator();
        int i = 0;
        while (it.hasNext()) {
            AssetData next = it.next();
            if (next.filename.equals(str) && next.type.equals(cls)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public Array<AssetDescriptor> getAssetDescriptors() {
        Array<AssetDescriptor> array = new Array<>();
        Array.ArrayIterator<AssetData> it = this.sharedAssets.iterator();
        while (it.hasNext()) {
            AssetData next = it.next();
            array.add(new AssetDescriptor(next.filename, next.type));
        }
        return array;
    }

    public Array<AssetData> getAssets() {
        return this.sharedAssets;
    }

    public SaveData createSaveData() {
        SaveData saveData = new SaveData(this);
        this.data.add(saveData);
        return saveData;
    }

    public SaveData createSaveData(String str) {
        SaveData saveData = new SaveData(this);
        if (this.uniqueData.containsKey(str)) {
            throw new RuntimeException("Key already used, data must be unique, use a different key");
        }
        this.uniqueData.put(str, saveData);
        return saveData;
    }

    public SaveData getSaveData() {
        Array<SaveData> array = this.data;
        int i = this.currentLoadIndex;
        this.currentLoadIndex = i + 1;
        return array.get(i);
    }

    public SaveData getSaveData(String str) {
        return this.uniqueData.get(str);
    }

    @Override // com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        json.writeValue("unique", this.uniqueData, ObjectMap.class);
        json.writeValue("data", this.data, Array.class, SaveData.class);
        json.writeValue("assets", this.sharedAssets.toArray(AssetData.class), AssetData[].class);
        json.writeValue("resource", this.resource, (Class) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        ObjectMap<String, SaveData> objectMap = (ObjectMap) json.readValue("unique", ObjectMap.class, jsonValue);
        this.uniqueData = objectMap;
        ObjectMap.Entries<String, SaveData> it = objectMap.entries().iterator();
        while (it.hasNext()) {
            ((SaveData) it.next().value).resources = this;
        }
        Array<SaveData> array = (Array) json.readValue("data", (Class) Array.class, SaveData.class, jsonValue);
        this.data = array;
        Array.ArrayIterator<SaveData> it2 = array.iterator();
        while (it2.hasNext()) {
            it2.next().resources = this;
        }
        this.sharedAssets.addAll((Array<? extends AssetData>) json.readValue("assets", (Class) Array.class, AssetData.class, jsonValue));
        this.resource = (T) json.readValue("resource", (Class) null, jsonValue);
    }
}
