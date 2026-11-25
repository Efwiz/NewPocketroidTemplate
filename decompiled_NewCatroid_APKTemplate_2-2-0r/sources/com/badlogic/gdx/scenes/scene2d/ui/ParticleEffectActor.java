package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/ParticleEffectActor.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ParticleEffectActor extends Actor implements Disposable {
    private boolean autoRemove;
    protected boolean isRunning;
    protected float lastDelta;
    protected boolean ownsEffect;
    private final ParticleEffect particleEffect;
    private boolean resetOnStart;

    public ParticleEffectActor(ParticleEffect particleEffect, boolean z) {
        this.particleEffect = particleEffect;
        this.resetOnStart = z;
    }

    public ParticleEffectActor(FileHandle fileHandle, TextureAtlas textureAtlas) throws Throwable {
        ParticleEffect particleEffect = new ParticleEffect();
        this.particleEffect = particleEffect;
        particleEffect.load(fileHandle, textureAtlas);
        this.ownsEffect = true;
    }

    public ParticleEffectActor(FileHandle fileHandle, FileHandle fileHandle2) {
        ParticleEffect particleEffect = new ParticleEffect();
        this.particleEffect = particleEffect;
        particleEffect.load(fileHandle, fileHandle2);
        this.ownsEffect = true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        this.particleEffect.setPosition(getX(), getY());
        float f2 = this.lastDelta;
        if (f2 > 0.0f) {
            this.particleEffect.update(f2);
            this.lastDelta = 0.0f;
        }
        if (this.isRunning) {
            this.particleEffect.draw(batch);
            this.isRunning = !this.particleEffect.isComplete();
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void act(float f) {
        super.act(f);
        this.lastDelta += f;
        if (this.autoRemove && this.particleEffect.isComplete()) {
            remove();
        }
    }

    public void start() {
        this.isRunning = true;
        if (this.resetOnStart) {
            this.particleEffect.reset(false);
        }
        this.particleEffect.start();
    }

    public boolean isResetOnStart() {
        return this.resetOnStart;
    }

    public ParticleEffectActor setResetOnStart(boolean z) {
        this.resetOnStart = z;
        return this;
    }

    public boolean isAutoRemove() {
        return this.autoRemove;
    }

    public ParticleEffectActor setAutoRemove(boolean z) {
        this.autoRemove = z;
        return this;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public ParticleEffect getEffect() {
        return this.particleEffect;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    protected void scaleChanged() {
        super.scaleChanged();
        this.particleEffect.scaleEffect(getScaleX(), getScaleY(), getScaleY());
    }

    public void cancel() {
        this.isRunning = true;
    }

    public void allowCompletion() {
        this.particleEffect.allowCompletion();
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        if (this.ownsEffect) {
            this.particleEffect.dispose();
        }
    }
}
