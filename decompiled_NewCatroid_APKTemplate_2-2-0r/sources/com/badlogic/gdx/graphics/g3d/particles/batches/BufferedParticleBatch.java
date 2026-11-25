package com.badlogic.gdx.graphics.g3d.particles.batches;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderData;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/batches/BufferedParticleBatch.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class BufferedParticleBatch<T extends ParticleControllerRenderData> implements ParticleBatch<T> {
    protected int bufferedParticlesCount;
    protected Camera camera;
    protected Array<T> renderData;
    protected int currentCapacity = 0;
    protected ParticleSorter sorter = new ParticleSorter.Distance();

    protected abstract void allocParticlesData(int i);

    protected abstract void flush(int[] iArr);

    protected BufferedParticleBatch(Class<T> cls) {
        this.renderData = new Array<>(false, 10, cls);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch
    public void begin() {
        this.renderData.clear();
        this.bufferedParticlesCount = 0;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch
    public void draw(T t) {
        if (t.controller.particles.size > 0) {
            this.renderData.add(t);
            this.bufferedParticlesCount += t.controller.particles.size;
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch
    public void end() {
        int i = this.bufferedParticlesCount;
        if (i > 0) {
            ensureCapacity(i);
            flush(this.sorter.sort(this.renderData));
        }
    }

    public void ensureCapacity(int i) {
        if (this.currentCapacity >= i) {
            return;
        }
        this.sorter.ensureCapacity(i);
        allocParticlesData(i);
        this.currentCapacity = i;
    }

    public void resetCapacity() {
        this.bufferedParticlesCount = 0;
        this.currentCapacity = 0;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
        this.sorter.setCamera(camera);
    }

    public ParticleSorter getSorter() {
        return this.sorter;
    }

    public void setSorter(ParticleSorter particleSorter) {
        this.sorter = particleSorter;
        particleSorter.setCamera(this.camera);
        particleSorter.ensureCapacity(this.currentCapacity);
    }

    public int getBufferedCount() {
        return this.bufferedParticlesCount;
    }
}
