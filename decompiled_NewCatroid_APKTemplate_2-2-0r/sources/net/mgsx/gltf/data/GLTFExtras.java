package net.mgsx.gltf.data;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFExtras implements Json.Serializable {
    public JsonValue value;

    @Override // com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
    }

    @Override // com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        this.value = jsonValue;
    }

    public Array<String> keys() {
        Array<String> array = new Array<>();
        for (JsonValue jsonValue = this.value.child; jsonValue != null; jsonValue = jsonValue.next) {
            array.add(jsonValue.f125name);
        }
        return array;
    }

    public Array<JsonValue> entries() {
        Array<JsonValue> array = new Array<>();
        for (JsonValue jsonValue = this.value.child; jsonValue != null; jsonValue = jsonValue.next) {
            array.add(jsonValue);
        }
        return array;
    }
}
