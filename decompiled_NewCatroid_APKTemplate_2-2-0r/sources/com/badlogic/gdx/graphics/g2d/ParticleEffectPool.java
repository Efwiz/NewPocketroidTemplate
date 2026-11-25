package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/ParticleEffectPool.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ParticleEffectPool extends Pool<PooledEffect> {
    private final ParticleEffect effect;

    public ParticleEffectPool(ParticleEffect particleEffect, int i, int i2) {
        super(i, i2);
        this.effect = particleEffect;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.badlogic.gdx.utils.Pool
    public PooledEffect newObject() {
        PooledEffect pooledEffect = new PooledEffect(this.effect);
        pooledEffect.start();
        return pooledEffect;
    }

    @Override // com.badlogic.gdx.utils.Pool
    public void free(PooledEffect pooledEffect) {
        super.free(pooledEffect);
        pooledEffect.reset(false);
        if (pooledEffect.xSizeScale == this.effect.xSizeScale && pooledEffect.ySizeScale == this.effect.ySizeScale && pooledEffect.motionScale == this.effect.motionScale) {
            return;
        }
        Array<ParticleEmitter> emitters = pooledEffect.getEmitters();
        Array<ParticleEmitter> emitters2 = this.effect.getEmitters();
        for (int i = 0; i < emitters.size; i++) {
            ParticleEmitter particleEmitter = emitters.get(i);
            ParticleEmitter particleEmitter2 = emitters2.get(i);
            particleEmitter.matchSize(particleEmitter2);
            particleEmitter.matchMotion(particleEmitter2);
        }
        pooledEffect.xSizeScale = this.effect.xSizeScale;
        pooledEffect.ySizeScale = this.effect.ySizeScale;
        pooledEffect.motionScale = this.effect.motionScale;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/ParticleEffectPool$PooledEffect.class
      classes8.dex
     */
    public class PooledEffect extends ParticleEffect {
        PooledEffect(ParticleEffect particleEffect) {
            super(particleEffect);
        }

        public void free() {
            ParticleEffectPool.this.free(this);
        }
    }
}
