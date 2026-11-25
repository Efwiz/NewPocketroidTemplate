package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/values/MeshSpawnShapeValue.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class MeshSpawnShapeValue extends SpawnShapeValue {
    protected Mesh mesh;
    protected Model model;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/values/MeshSpawnShapeValue$Triangle.class
      classes8.dex
     */
    public static class Triangle {
        float x1;
        float x2;
        float x3;
        float y1;
        float y2;
        float y3;
        float z1;
        float z2;
        float z3;

        public Triangle(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
            this.x1 = f;
            this.y1 = f2;
            this.z1 = f3;
            this.x2 = f4;
            this.y2 = f5;
            this.z2 = f6;
            this.x3 = f7;
            this.y3 = f8;
            this.z3 = f9;
        }

        public static Vector3 pick(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Vector3 vector3) {
            float fRandom = MathUtils.random();
            float fRandom2 = MathUtils.random();
            return vector3.set(((f4 - f) * fRandom) + f + ((f7 - f) * fRandom2), ((f5 - f2) * fRandom) + f2 + ((f8 - f2) * fRandom2), (fRandom * (f6 - f3)) + f3 + (fRandom2 * (f9 - f3)));
        }

        public Vector3 pick(Vector3 vector3) {
            float fRandom = MathUtils.random();
            float fRandom2 = MathUtils.random();
            float f = this.x1;
            float f2 = ((this.x2 - f) * fRandom) + f + ((this.x3 - f) * fRandom2);
            float f3 = this.y1;
            float f4 = ((this.y2 - f3) * fRandom) + f3 + ((this.y3 - f3) * fRandom2);
            float f5 = this.z1;
            return vector3.set(f2, f4, (fRandom * (this.z2 - f5)) + f5 + (fRandom2 * (this.z3 - f5)));
        }
    }

    public MeshSpawnShapeValue(MeshSpawnShapeValue meshSpawnShapeValue) {
        super(meshSpawnShapeValue);
    }

    public MeshSpawnShapeValue() {
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue, com.badlogic.gdx.graphics.g3d.particles.values.ParticleValue
    public void load(ParticleValue particleValue) {
        super.load(particleValue);
        MeshSpawnShapeValue meshSpawnShapeValue = (MeshSpawnShapeValue) particleValue;
        setMesh(meshSpawnShapeValue.mesh, meshSpawnShapeValue.model);
    }

    public void setMesh(Mesh mesh, Model model) {
        if (mesh.getVertexAttribute(1) == null) {
            throw new GdxRuntimeException("Mesh vertices must have Usage.Position");
        }
        this.model = model;
        this.mesh = mesh;
    }

    public void setMesh(Mesh mesh) {
        setMesh(mesh, null);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue, com.badlogic.gdx.graphics.g3d.particles.ResourceData.Configurable
    public void save(AssetManager assetManager, ResourceData resourceData) {
        if (this.model != null) {
            ResourceData.SaveData saveDataCreateSaveData = resourceData.createSaveData();
            saveDataCreateSaveData.saveAsset(assetManager.getAssetFileName(this.model), Model.class);
            saveDataCreateSaveData.save("index", Integer.valueOf(this.model.meshes.indexOf(this.mesh, true)));
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue, com.badlogic.gdx.graphics.g3d.particles.ResourceData.Configurable
    public void load(AssetManager assetManager, ResourceData resourceData) {
        ResourceData.SaveData saveData = resourceData.getSaveData();
        AssetDescriptor assetDescriptorLoadAsset = saveData.loadAsset();
        if (assetDescriptorLoadAsset != null) {
            Model model = (Model) assetManager.get(assetDescriptorLoadAsset);
            setMesh(model.meshes.get(((Integer) saveData.load("index")).intValue()), model);
        }
    }
}
