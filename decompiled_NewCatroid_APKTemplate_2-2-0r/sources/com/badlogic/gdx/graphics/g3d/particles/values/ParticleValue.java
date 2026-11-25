package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/values/ParticleValue.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ParticleValue implements Json.Serializable {
    public boolean active;

    public ParticleValue() {
    }

    public ParticleValue(ParticleValue particleValue) {
        this.active = particleValue.active;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean z) {
        this.active = z;
    }

    public void load(ParticleValue particleValue) {
        this.active = particleValue.active;
    }

    @Override // com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        json.writeValue(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, Boolean.valueOf(this.active));
    }

    @Override // com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        this.active = ((Boolean) json.readValue(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, Boolean.class, jsonValue)).booleanValue();
    }
}
