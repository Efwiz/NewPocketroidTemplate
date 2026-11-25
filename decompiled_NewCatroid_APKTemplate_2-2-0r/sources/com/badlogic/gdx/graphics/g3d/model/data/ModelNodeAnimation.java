package com.badlogic.gdx.graphics.g3d.model.data;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/model/data/ModelNodeAnimation.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ModelNodeAnimation {
    public String nodeId;
    public Array<ModelNodeKeyframe<Quaternion>> rotation;
    public Array<ModelNodeKeyframe<Vector3>> scaling;
    public Array<ModelNodeKeyframe<Vector3>> translation;
}
