package org.catrobat.catroid.io;

import android.util.Log;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.catrobat.catroid.content.Script;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/BackpackScriptSerializerAndDeserializer.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BackpackScriptSerializerAndDeserializer implements JsonSerializer<Script>, JsonDeserializer<Script> {
    private static final String PACKAGE_NAME = "org.catrobat.catroid.content.";
    private static final String PACKAGE_NAME_PHYSICS = "org.catrobat.catroid.physics.content.bricks.";
    private static final String PROPERTY = "properties";
    private static final String TAG = "BackpackScriptSerializerAndDeserializer";
    private static final String TYPE = "scripttype";

    @Override // com.google.gson.JsonSerializer
    public JsonElement serialize(Script script, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(TYPE, new JsonPrimitive(script.getClass().getSimpleName()));
        jsonObject.add(PROPERTY, context.serialize(script, script.getClass()));
        return jsonObject;
    }

    @Override // com.google.gson.JsonDeserializer
    public Script deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject asJsonObject = json.getAsJsonObject();
        String asString = asJsonObject.get(TYPE).getAsString();
        JsonElement jsonElement = asJsonObject.get(PROPERTY);
        try {
            try {
                return (Script) context.deserialize(jsonElement, Class.forName(PACKAGE_NAME + asString));
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "Could not deserialize backpacked script element!");
                throw new JsonParseException("Unknown element type: " + asString, e);
            }
        } catch (ClassNotFoundException unused) {
            return (Script) context.deserialize(jsonElement, Class.forName(PACKAGE_NAME_PHYSICS + asString));
        }
    }
}
