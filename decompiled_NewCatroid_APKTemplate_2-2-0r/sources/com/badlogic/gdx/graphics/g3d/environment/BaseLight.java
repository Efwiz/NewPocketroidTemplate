package com.badlogic.gdx.graphics.g3d.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/environment/BaseLight.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class BaseLight<T extends BaseLight<T>> {
    public final Color color = new Color(0.0f, 0.0f, 0.0f, 1.0f);

    public T setColor(float f, float f2, float f3, float f4) {
        this.color.set(f, f2, f3, f4);
        return this;
    }

    public T setColor(Color color) {
        this.color.set(color);
        return this;
    }
}
