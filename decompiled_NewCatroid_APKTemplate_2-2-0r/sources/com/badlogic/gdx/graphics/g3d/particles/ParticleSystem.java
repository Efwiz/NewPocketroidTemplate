package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/ParticleSystem.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public final class ParticleSystem implements RenderableProvider {
    private static ParticleSystem instance;
    private Array<ParticleBatch<?>> batches = new Array<>();
    private Array<ParticleEffect> effects = new Array<>();

    @Deprecated
    public static ParticleSystem get() {
        if (instance == null) {
            instance = new ParticleSystem();
        }
        return instance;
    }

    public void add(ParticleBatch<?> particleBatch) {
        this.batches.add(particleBatch);
    }

    public void add(ParticleEffect particleEffect) {
        this.effects.add(particleEffect);
    }

    public void remove(ParticleEffect particleEffect) {
        this.effects.removeValue(particleEffect, true);
    }

    public void removeAll() {
        this.effects.clear();
    }

    public void update() {
        Array.ArrayIterator<ParticleEffect> it = this.effects.iterator();
        while (it.hasNext()) {
            it.next().update();
        }
    }

    public void updateAndDraw() {
        Array.ArrayIterator<ParticleEffect> it = this.effects.iterator();
        while (it.hasNext()) {
            ParticleEffect next = it.next();
            next.update();
            next.draw();
        }
    }

    public void update(float f) {
        Array.ArrayIterator<ParticleEffect> it = this.effects.iterator();
        while (it.hasNext()) {
            it.next().update(f);
        }
    }

    public void updateAndDraw(float f) {
        Array.ArrayIterator<ParticleEffect> it = this.effects.iterator();
        while (it.hasNext()) {
            ParticleEffect next = it.next();
            next.update(f);
            next.draw();
        }
    }

    public void begin() {
        Array.ArrayIterator<ParticleBatch<?>> it = this.batches.iterator();
        while (it.hasNext()) {
            it.next().begin();
        }
    }

    public void draw() {
        Array.ArrayIterator<ParticleEffect> it = this.effects.iterator();
        while (it.hasNext()) {
            it.next().draw();
        }
    }

    public void end() {
        Array.ArrayIterator<ParticleBatch<?>> it = this.batches.iterator();
        while (it.hasNext()) {
            it.next().end();
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.RenderableProvider
    public void getRenderables(Array<Renderable> array, Pool<Renderable> pool) {
        Array.ArrayIterator<ParticleBatch<?>> it = this.batches.iterator();
        while (it.hasNext()) {
            it.next().getRenderables(array, pool);
        }
    }

    public Array<ParticleBatch<?>> getBatches() {
        return this.batches;
    }
}
