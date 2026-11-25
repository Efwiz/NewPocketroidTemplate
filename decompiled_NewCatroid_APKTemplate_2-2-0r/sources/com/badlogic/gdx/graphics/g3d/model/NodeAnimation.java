package com.badlogic.gdx.graphics.g3d.model;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/model/NodeAnimation.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class NodeAnimation {
    public Node node;
    public Array<NodeKeyframe<Vector3>> translation = null;
    public Array<NodeKeyframe<Quaternion>> rotation = null;
    public Array<NodeKeyframe<Vector3>> scaling = null;
}
