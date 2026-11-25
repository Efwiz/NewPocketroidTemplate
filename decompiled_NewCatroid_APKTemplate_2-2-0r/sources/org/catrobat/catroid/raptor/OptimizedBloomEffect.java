package org.catrobat.catroid.raptor;

import com.crashinvaders.vfx.effects.BloomEffect;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/OptimizedBloomEffect.class
  classes14.dex
 */
/* JADX INFO: compiled from: ThreeDManager.java */
/* JADX INFO: loaded from: classes14.dex */
class OptimizedBloomEffect extends BloomEffect {
    private final float scaleFactor;

    public OptimizedBloomEffect(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    @Override // com.crashinvaders.vfx.effects.CompositeVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int width, int height) {
        float f = this.scaleFactor;
        super.resize((int) (width / f), (int) (height / f));
    }
}
