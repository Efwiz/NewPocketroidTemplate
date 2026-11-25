package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/values/PointSpawnShapeValue.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public final class PointSpawnShapeValue extends PrimitiveSpawnShapeValue {
    public PointSpawnShapeValue(PointSpawnShapeValue pointSpawnShapeValue) {
        super(pointSpawnShapeValue);
        load(pointSpawnShapeValue);
    }

    public PointSpawnShapeValue() {
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue
    public void spawnAux(Vector3 vector3, float f) {
        vector3.x = this.spawnWidth + (this.spawnWidthDiff * this.spawnWidthValue.getScale(f));
        vector3.y = this.spawnHeight + (this.spawnHeightDiff * this.spawnHeightValue.getScale(f));
        vector3.z = this.spawnDepth + (this.spawnDepthDiff * this.spawnDepthValue.getScale(f));
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue
    public SpawnShapeValue copy() {
        return new PointSpawnShapeValue(this);
    }
}
