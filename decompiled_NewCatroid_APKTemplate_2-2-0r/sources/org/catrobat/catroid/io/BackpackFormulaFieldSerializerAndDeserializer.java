package org.catrobat.catroid.io;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import java.io.File;
import java.lang.reflect.Type;
import org.catrobat.catroid.content.bricks.Brick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/BackpackFormulaFieldSerializerAndDeserializer.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BackpackFormulaFieldSerializerAndDeserializer extends BackpackInterfaceSerializerAndDeserializer<Brick.FormulaField> {
    public BackpackFormulaFieldSerializerAndDeserializer(File file) {
        super(file);
    }

    @Override // org.catrobat.catroid.io.BackpackInterfaceSerializerAndDeserializer, com.google.gson.JsonDeserializer
    public Brick.FormulaField deserialize(JsonElement json, Type interfaceType, JsonDeserializationContext context) {
        if (!json.isJsonObject()) {
            return Brick.BrickField.valueOf(json.getAsString());
        }
        return (Brick.FormulaField) super.deserialize(json, interfaceType, context);
    }
}
