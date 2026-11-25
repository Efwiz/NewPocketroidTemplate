package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/values/NumericValue.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class NumericValue extends ParticleValue {
    private float value;

    public float getValue() {
        return this.value;
    }

    public void setValue(float f) {
        this.value = f;
    }

    public void load(NumericValue numericValue) {
        super.load((ParticleValue) numericValue);
        this.value = numericValue.value;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.ParticleValue, com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        super.write(json);
        json.writeValue("value", Float.valueOf(this.value));
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.ParticleValue, com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        super.read(json, jsonValue);
        this.value = ((Float) json.readValue("value", Float.TYPE, jsonValue)).floatValue();
    }
}
