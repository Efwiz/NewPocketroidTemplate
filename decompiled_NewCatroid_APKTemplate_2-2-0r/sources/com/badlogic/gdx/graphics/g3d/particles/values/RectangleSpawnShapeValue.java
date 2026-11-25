package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/values/RectangleSpawnShapeValue.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public final class RectangleSpawnShapeValue extends PrimitiveSpawnShapeValue {
    public RectangleSpawnShapeValue(RectangleSpawnShapeValue rectangleSpawnShapeValue) {
        super(rectangleSpawnShapeValue);
        load(rectangleSpawnShapeValue);
    }

    public RectangleSpawnShapeValue() {
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue
    public void spawnAux(Vector3 vector3, float f) {
        float fRandom;
        float fRandom2;
        float fRandom3;
        float f2;
        float fRandom4;
        float fRandom5;
        float scale = this.spawnWidth + (this.spawnWidthDiff * this.spawnWidthValue.getScale(f));
        float scale2 = this.spawnHeight + (this.spawnHeightDiff * this.spawnHeightValue.getScale(f));
        float scale3 = this.spawnDepth + (this.spawnDepthDiff * this.spawnDepthValue.getScale(f));
        if (this.edges) {
            int iRandom = MathUtils.random(-1, 1);
            if (iRandom == -1) {
                f2 = MathUtils.random(1) == 0 ? (-scale) / 2.0f : scale / 2.0f;
                if (f2 == 0.0f) {
                    fRandom3 = MathUtils.random(1) == 0 ? (-scale2) / 2.0f : scale2 / 2.0f;
                    fRandom2 = MathUtils.random(1) == 0 ? (-scale3) / 2.0f : scale3 / 2.0f;
                } else {
                    fRandom3 = MathUtils.random(scale2) - (scale2 / 2.0f);
                    fRandom2 = MathUtils.random(scale3) - (scale3 / 2.0f);
                }
            } else if (iRandom == 0) {
                float f3 = MathUtils.random(1) == 0 ? (-scale3) / 2.0f : scale3 / 2.0f;
                if (f3 == 0.0f) {
                    if (MathUtils.random(1) == 0) {
                        scale2 = -scale2;
                    }
                    fRandom4 = scale2 / 2.0f;
                    if (MathUtils.random(1) == 0) {
                        scale = -scale;
                    }
                    fRandom5 = scale / 2.0f;
                } else {
                    fRandom4 = MathUtils.random(scale2) - (scale2 / 2.0f);
                    fRandom5 = MathUtils.random(scale) - (scale / 2.0f);
                }
                float f4 = fRandom4;
                fRandom2 = f3;
                f2 = fRandom5;
                fRandom3 = f4;
            } else {
                float f5 = MathUtils.random(1) == 0 ? (-scale2) / 2.0f : scale2 / 2.0f;
                if (f5 == 0.0f) {
                    if (MathUtils.random(1) == 0) {
                        scale = -scale;
                    }
                    fRandom = scale / 2.0f;
                    fRandom2 = MathUtils.random(1) == 0 ? (-scale3) / 2.0f : scale3 / 2.0f;
                } else {
                    fRandom = MathUtils.random(scale) - (scale / 2.0f);
                    fRandom2 = MathUtils.random(scale3) - (scale3 / 2.0f);
                }
                float f6 = fRandom;
                fRandom3 = f5;
                f2 = f6;
            }
            vector3.x = f2;
            vector3.y = fRandom3;
            vector3.z = fRandom2;
            return;
        }
        vector3.x = MathUtils.random(scale) - (scale / 2.0f);
        vector3.y = MathUtils.random(scale2) - (scale2 / 2.0f);
        vector3.z = MathUtils.random(scale3) - (scale3 / 2.0f);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue
    public SpawnShapeValue copy() {
        return new RectangleSpawnShapeValue(this);
    }
}
