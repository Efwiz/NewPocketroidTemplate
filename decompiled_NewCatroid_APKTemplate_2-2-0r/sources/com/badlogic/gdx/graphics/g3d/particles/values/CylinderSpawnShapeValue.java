package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/values/CylinderSpawnShapeValue.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public final class CylinderSpawnShapeValue extends PrimitiveSpawnShapeValue {
    public CylinderSpawnShapeValue(CylinderSpawnShapeValue cylinderSpawnShapeValue) {
        super(cylinderSpawnShapeValue);
        load(cylinderSpawnShapeValue);
    }

    public CylinderSpawnShapeValue() {
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue
    public void spawnAux(Vector3 vector3, float f) {
        float fRandom;
        float scale = this.spawnWidth + (this.spawnWidthDiff * this.spawnWidthValue.getScale(f));
        float scale2 = this.spawnHeight + (this.spawnHeightDiff * this.spawnHeightValue.getScale(f));
        float scale3 = this.spawnDepth + (this.spawnDepthDiff * this.spawnDepthValue.getScale(f));
        float fRandom2 = MathUtils.random(scale2) - (scale2 / 2.0f);
        if (this.edges) {
            fRandom = scale / 2.0f;
        } else {
            fRandom = MathUtils.random(scale) / 2.0f;
            scale3 = MathUtils.random(scale3);
        }
        float f2 = scale3 / 2.0f;
        float fRandom3 = 0.0f;
        boolean z = fRandom == 0.0f;
        boolean z2 = f2 == 0.0f;
        if (!z && !z2) {
            fRandom3 = MathUtils.random(360.0f);
        } else if (z) {
            fRandom3 = MathUtils.random(1) == 0 ? -90.0f : 90.0f;
        } else if (z2 && MathUtils.random(1) != 0) {
            fRandom3 = 180.0f;
        }
        vector3.set(fRandom * MathUtils.cosDeg(fRandom3), fRandom2, f2 * MathUtils.sinDeg(fRandom3));
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue
    public SpawnShapeValue copy() {
        return new CylinderSpawnShapeValue(this);
    }
}
