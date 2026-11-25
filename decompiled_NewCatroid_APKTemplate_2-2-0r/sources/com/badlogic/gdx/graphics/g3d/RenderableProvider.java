package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/RenderableProvider.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface RenderableProvider {
    void getRenderables(Array<Renderable> array, Pool<Renderable> pool);
}
