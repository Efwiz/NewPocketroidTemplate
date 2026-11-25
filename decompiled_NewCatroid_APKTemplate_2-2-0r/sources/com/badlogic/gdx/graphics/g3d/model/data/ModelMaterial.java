package com.badlogic.gdx.graphics.g3d.model.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/model/data/ModelMaterial.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ModelMaterial {
    public Color ambient;
    public Color diffuse;
    public Color emissive;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public String f107id;
    public float opacity = 1.0f;
    public Color reflection;
    public float shininess;
    public Color specular;
    public Array<ModelTexture> textures;
    public MaterialType type;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/model/data/ModelMaterial$MaterialType.class
      classes8.dex
     */
    public enum MaterialType {
        Lambert,
        Phong
    }
}
