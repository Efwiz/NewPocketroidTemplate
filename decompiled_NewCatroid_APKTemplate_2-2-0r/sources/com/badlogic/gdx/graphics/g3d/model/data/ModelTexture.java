package com.badlogic.gdx.graphics.g3d.model.data;

import com.badlogic.gdx.math.Vector2;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/model/data/ModelTexture.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ModelTexture {
    public static final int USAGE_AMBIENT = 4;
    public static final int USAGE_BUMP = 8;
    public static final int USAGE_DIFFUSE = 2;
    public static final int USAGE_EMISSIVE = 3;
    public static final int USAGE_NONE = 1;
    public static final int USAGE_NORMAL = 7;
    public static final int USAGE_REFLECTION = 10;
    public static final int USAGE_SHININESS = 6;
    public static final int USAGE_SPECULAR = 5;
    public static final int USAGE_TRANSPARENCY = 9;
    public static final int USAGE_UNKNOWN = 0;
    public String fileName;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public String f111id;
    public int usage;
    public Vector2 uvScaling;
    public Vector2 uvTranslation;
}
