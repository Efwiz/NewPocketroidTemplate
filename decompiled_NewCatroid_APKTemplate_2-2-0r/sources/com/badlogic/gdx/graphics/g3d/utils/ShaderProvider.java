package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.utils.Disposable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/utils/ShaderProvider.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface ShaderProvider extends Disposable {
    Shader getShader(Renderable renderable);
}
