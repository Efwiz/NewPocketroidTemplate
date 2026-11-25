package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.math.CumulativeDistribution;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/values/WeightMeshSpawnShapeValue.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public final class WeightMeshSpawnShapeValue extends MeshSpawnShapeValue {
    private CumulativeDistribution<MeshSpawnShapeValue.Triangle> distribution;

    public WeightMeshSpawnShapeValue(WeightMeshSpawnShapeValue weightMeshSpawnShapeValue) {
        super(weightMeshSpawnShapeValue);
        this.distribution = new CumulativeDistribution<>();
        load(weightMeshSpawnShapeValue);
    }

    public WeightMeshSpawnShapeValue() {
        this.distribution = new CumulativeDistribution<>();
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue
    public void init() {
        calculateWeights();
    }

    public void calculateWeights() {
        this.distribution.clear();
        VertexAttributes vertexAttributes = this.mesh.getVertexAttributes();
        int numIndices = this.mesh.getNumIndices();
        int numVertices = this.mesh.getNumVertices();
        short s = (short) (vertexAttributes.vertexSize / 4);
        short s2 = (short) (vertexAttributes.findByUsage(1).offset / 4);
        float[] fArr = new float[numVertices * s];
        this.mesh.getVertices(fArr);
        int i = 0;
        if (numIndices > 0) {
            short[] sArr = new short[numIndices];
            this.mesh.getIndices(sArr);
            while (i < numIndices) {
                int i2 = (sArr[i] * s) + s2;
                int i3 = (sArr[i + 1] * s) + s2;
                int i4 = (sArr[i + 2] * s) + s2;
                float f = fArr[i2];
                float f2 = fArr[i2 + 1];
                float f3 = fArr[i2 + 2];
                float f4 = fArr[i3];
                float f5 = fArr[i3 + 1];
                float f6 = fArr[i3 + 2];
                float f7 = fArr[i4];
                float f8 = fArr[i4 + 1];
                float f9 = fArr[i4 + 2];
                this.distribution.add(new MeshSpawnShapeValue.Triangle(f, f2, f3, f4, f5, f6, f7, f8, f9), Math.abs(((((f5 - f8) * f) + ((f8 - f2) * f4)) + ((f2 - f5) * f7)) / 2.0f));
                i += 3;
            }
        } else {
            while (i < numVertices) {
                int i5 = i + s2;
                int i6 = i5 + s;
                int i7 = i6 + s;
                float f10 = fArr[i5];
                float f11 = fArr[i5 + 1];
                float f12 = fArr[i5 + 2];
                float f13 = fArr[i6];
                float f14 = fArr[i6 + 1];
                float f15 = fArr[i6 + 2];
                float f16 = fArr[i7];
                float f17 = fArr[i7 + 1];
                float f18 = fArr[i7 + 2];
                this.distribution.add(new MeshSpawnShapeValue.Triangle(f10, f11, f12, f13, f14, f15, f16, f17, f18), Math.abs(((((f14 - f17) * f10) + ((f17 - f11) * f13)) + ((f11 - f14) * f16)) / 2.0f));
                i += s;
            }
        }
        this.distribution.generateNormalized();
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue
    public void spawnAux(Vector3 vector3, float f) {
        MeshSpawnShapeValue.Triangle triangleValue = this.distribution.value();
        float fRandom = MathUtils.random();
        float fRandom2 = MathUtils.random();
        vector3.set(triangleValue.x1 + ((triangleValue.x2 - triangleValue.x1) * fRandom) + ((triangleValue.x3 - triangleValue.x1) * fRandom2), triangleValue.y1 + ((triangleValue.y2 - triangleValue.y1) * fRandom) + ((triangleValue.y3 - triangleValue.y1) * fRandom2), triangleValue.z1 + (fRandom * (triangleValue.z2 - triangleValue.z1)) + (fRandom2 * (triangleValue.z3 - triangleValue.z1)));
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue
    public SpawnShapeValue copy() {
        return new WeightMeshSpawnShapeValue(this);
    }
}
