package com.badlogic.gdx.graphics.g3d.environment;

import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.math.Matrix4;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/environment/ShadowMap.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface ShadowMap {
    TextureDescriptor getDepthMap();

    Matrix4 getProjViewTrans();
}
