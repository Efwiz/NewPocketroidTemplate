package com.crashinvaders.vfx.effects;

/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractVfxEffect implements VfxEffect {
    private boolean disabled;

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public boolean isDisabled() {
        return this.disabled;
    }

    @Override // com.crashinvaders.vfx.effects.VfxEffect
    public void setDisabled(boolean z) {
        this.disabled = z;
    }
}
