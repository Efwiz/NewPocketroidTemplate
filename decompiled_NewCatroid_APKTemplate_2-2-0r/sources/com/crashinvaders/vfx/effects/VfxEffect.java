package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.utils.Disposable;

/* JADX INFO: loaded from: classes8.dex */
public interface VfxEffect extends Disposable {
    boolean isDisabled();

    void rebind();

    void resize(int i, int i2);

    void setDisabled(boolean z);

    void update(float f);
}
