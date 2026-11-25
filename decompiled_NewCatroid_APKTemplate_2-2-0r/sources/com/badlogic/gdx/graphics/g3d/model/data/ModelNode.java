package com.badlogic.gdx.graphics.g3d.model.data;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/model/data/ModelNode.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ModelNode {
    public ModelNode[] children;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public String f110id;
    public String meshId;
    public ModelNodePart[] parts;
    public Quaternion rotation;
    public Vector3 scale;
    public Vector3 translation;
}
