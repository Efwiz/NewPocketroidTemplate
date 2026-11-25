package com.badlogic.gdx.graphics.g3d.particles.renderers;

import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderData;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/renderers/ParticleControllerRenderer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class ParticleControllerRenderer<D extends ParticleControllerRenderData, T extends ParticleBatch<D>> extends ParticleControllerComponent {
    protected T batch;
    protected D renderData;

    public abstract boolean isCompatible(ParticleBatch<?> particleBatch);

    protected ParticleControllerRenderer() {
    }

    protected ParticleControllerRenderer(D d) {
        this.renderData = d;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void update() {
        this.batch.draw(this.renderData);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean setBatch(ParticleBatch<?> particleBatch) {
        if (!isCompatible(particleBatch)) {
            return false;
        }
        this.batch = particleBatch;
        return true;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void set(ParticleController particleController) {
        super.set(particleController);
        D d = this.renderData;
        if (d != null) {
            d.controller = this.controller;
        }
    }
}
