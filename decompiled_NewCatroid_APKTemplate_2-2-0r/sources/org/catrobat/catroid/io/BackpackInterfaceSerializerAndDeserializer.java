package org.catrobat.catroid.io;

import android.util.Log;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.File;
import java.lang.reflect.Type;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/BackpackInterfaceSerializerAndDeserializer.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BackpackInterfaceSerializerAndDeserializer<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String PROPERTY = "properties";
    private static final String TAG = "BackpackInterfaceSerializerAndDeserializer";
    private static final String TYPE = "type";
    File file;

    public BackpackInterfaceSerializerAndDeserializer(File file) {
        this.file = file;
    }

    @Override // com.google.gson.JsonSerializer
    public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("type", new JsonPrimitive(object.getClass().getPackage().getName() + '.' + object.getClass().getSimpleName()));
        jsonObject.add(PROPERTY, context.serialize(object, object.getClass()));
        return jsonObject;
    }

    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        String asString = asJsonObject.get("type").getAsString();
        try {
            return (T) jsonDeserializationContext.deserialize(asJsonObject.get(PROPERTY), Class.forName(asString));
        } catch (ClassNotFoundException unused) {
            Log.e(TAG, "Could not deserialize backpacked element: " + asString);
            this.file.delete();
            return null;
        }
    }
}
