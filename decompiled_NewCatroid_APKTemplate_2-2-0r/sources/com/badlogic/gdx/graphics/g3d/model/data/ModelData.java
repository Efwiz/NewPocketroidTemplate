package com.badlogic.gdx.graphics.g3d.model.data;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/model/data/ModelData.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ModelData {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public String f106id;
    public final short[] version = new short[2];
    public final Array<ModelMesh> meshes = new Array<>();
    public final Array<ModelMaterial> materials = new Array<>();
    public final Array<ModelNode> nodes = new Array<>();
    public final Array<ModelAnimation> animations = new Array<>();

    public void addMesh(ModelMesh modelMesh) {
        Array.ArrayIterator<ModelMesh> it = this.meshes.iterator();
        while (it.hasNext()) {
            ModelMesh next = it.next();
            if (next.f108id.equals(modelMesh.f108id)) {
                throw new GdxRuntimeException("Mesh with id '" + next.f108id + "' already in model");
            }
        }
        this.meshes.add(modelMesh);
    }
}
