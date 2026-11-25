package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/values/EllipseSpawnShapeValue.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public final class EllipseSpawnShapeValue extends PrimitiveSpawnShapeValue {
    PrimitiveSpawnShapeValue.SpawnSide side;

    public EllipseSpawnShapeValue(EllipseSpawnShapeValue ellipseSpawnShapeValue) {
        super(ellipseSpawnShapeValue);
        this.side = PrimitiveSpawnShapeValue.SpawnSide.both;
        load(ellipseSpawnShapeValue);
    }

    public EllipseSpawnShapeValue() {
        this.side = PrimitiveSpawnShapeValue.SpawnSide.both;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue
    public void spawnAux(Vector3 vector3, float f) {
        float f2;
        float fRandom;
        float fRandom2;
        float fRandom3;
        float scale = this.spawnWidth + (this.spawnWidthDiff * this.spawnWidthValue.getScale(f));
        float scale2 = this.spawnHeight + (this.spawnHeightDiff * this.spawnHeightValue.getScale(f));
        float scale3 = this.spawnDepth + (this.spawnDepthDiff * this.spawnDepthValue.getScale(f));
        if (this.side == PrimitiveSpawnShapeValue.SpawnSide.top) {
            f2 = 3.1415927f;
        } else {
            f2 = this.side == PrimitiveSpawnShapeValue.SpawnSide.bottom ? -3.1415927f : 6.2831855f;
        }
        float fRandom4 = MathUtils.random(0.0f, f2);
        if (!this.edges) {
            fRandom = MathUtils.random(scale / 2.0f);
            fRandom2 = MathUtils.random(scale2 / 2.0f);
            fRandom3 = MathUtils.random(scale3 / 2.0f);
        } else {
            if (scale == 0.0f) {
                vector3.set(0.0f, (scale2 / 2.0f) * MathUtils.sin(fRandom4), (scale3 / 2.0f) * MathUtils.cos(fRandom4));
                return;
            }
            if (scale2 == 0.0f) {
                vector3.set((scale / 2.0f) * MathUtils.cos(fRandom4), 0.0f, (scale3 / 2.0f) * MathUtils.sin(fRandom4));
                return;
            } else if (scale3 == 0.0f) {
                vector3.set((scale / 2.0f) * MathUtils.cos(fRandom4), (scale2 / 2.0f) * MathUtils.sin(fRandom4), 0.0f);
                return;
            } else {
                fRandom = scale / 2.0f;
                fRandom2 = scale2 / 2.0f;
                fRandom3 = scale3 / 2.0f;
            }
        }
        float fRandom5 = MathUtils.random(-1.0f, 1.0f);
        float fSqrt = (float) Math.sqrt(1.0f - (fRandom5 * fRandom5));
        vector3.set(fRandom * fSqrt * MathUtils.cos(fRandom4), fRandom2 * fSqrt * MathUtils.sin(fRandom4), fRandom3 * fRandom5);
    }

    public PrimitiveSpawnShapeValue.SpawnSide getSide() {
        return this.side;
    }

    public void setSide(PrimitiveSpawnShapeValue.SpawnSide spawnSide) {
        this.side = spawnSide;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.PrimitiveSpawnShapeValue, com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue, com.badlogic.gdx.graphics.g3d.particles.values.ParticleValue
    public void load(ParticleValue particleValue) {
        super.load(particleValue);
        this.side = ((EllipseSpawnShapeValue) particleValue).side;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue
    public SpawnShapeValue copy() {
        return new EllipseSpawnShapeValue(this);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.PrimitiveSpawnShapeValue, com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue, com.badlogic.gdx.graphics.g3d.particles.values.ParticleValue, com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        super.write(json);
        json.writeValue("side", this.side);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.PrimitiveSpawnShapeValue, com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue, com.badlogic.gdx.graphics.g3d.particles.values.ParticleValue, com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        super.read(json, jsonValue);
        this.side = (PrimitiveSpawnShapeValue.SpawnSide) json.readValue("side", PrimitiveSpawnShapeValue.SpawnSide.class, jsonValue);
    }
}
