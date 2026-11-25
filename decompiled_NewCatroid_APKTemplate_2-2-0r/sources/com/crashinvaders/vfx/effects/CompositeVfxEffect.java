package com.crashinvaders.vfx.effects;

import com.badlogic.gdx.utils.Array;

/* JADX INFO: loaded from: classes8.dex */
public abstract class CompositeVfxEffect extends AbstractVfxEffect {
    protected final Array<VfxEffect> managedEffects = new Array<>();

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int i, int i2) {
        for (int i3 = 0; i3 < this.managedEffects.size; i3++) {
            this.managedEffects.get(i3).resize(i, i2);
        }
    }

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void rebind() {
        for (int i = 0; i < this.managedEffects.size; i++) {
            this.managedEffects.get(i).rebind();
        }
    }

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void update(float f) {
        for (int i = 0; i < this.managedEffects.size; i++) {
            this.managedEffects.get(i).update(f);
        }
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        for (int i = 0; i < this.managedEffects.size; i++) {
            this.managedEffects.get(i).dispose();
        }
    }

    protected <T extends VfxEffect> T register(T t) {
        this.managedEffects.add(t);
        return t;
    }

    protected <T extends VfxEffect> T unregister(T t) {
        this.managedEffects.removeValue(t, true);
        return t;
    }
}
