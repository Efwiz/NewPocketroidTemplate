package net.mgsx.gltf.data.geometry;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFMorphTarget extends ObjectMap<String, Integer> implements Json.Serializable {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        ObjectMap.Entries<String, Integer> it = iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            json.writeValue((String) next.key, next.value);
        }
    }

    @Override // com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        Iterator<JsonValue> itIterator2 = jsonValue.iterator2();
        while (itIterator2.hasNext()) {
            JsonValue next = itIterator2.next();
            put(next.f125name, Integer.valueOf(next.asInt()));
        }
    }
}
