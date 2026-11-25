package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.utils.Disposable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/Shader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public interface Shader extends Disposable {
    void begin(Camera camera, RenderContext renderContext);

    boolean canRender(Renderable renderable);

    int compareTo(Shader shader);

    void end();

    void init();

    void render(Renderable renderable);
}
