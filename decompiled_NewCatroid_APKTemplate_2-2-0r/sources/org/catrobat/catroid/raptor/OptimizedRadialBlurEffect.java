package org.catrobat.catroid.raptor;

import com.crashinvaders.vfx.effects.RadialBlurEffect;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/OptimizedRadialBlurEffect.class
  classes14.dex
 */
/* JADX INFO: compiled from: ThreeDManager.java */
/* JADX INFO: loaded from: classes14.dex */
class OptimizedRadialBlurEffect extends RadialBlurEffect {
    private final float scaleFactor;

    public OptimizedRadialBlurEffect(int passes, float scaleFactor) {
        super(passes);
        this.scaleFactor = scaleFactor;
    }

    @Override // com.crashinvaders.vfx.effects.ShaderVfxEffect, com.crashinvaders.vfx.effects.VfxEffect
    public void resize(int width, int height) {
        float f = this.scaleFactor;
        super.resize((int) (width / f), (int) (height / f));
    }
}
