package com.badlogic.gdx.graphics.g3d.particles.emitters;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.values.RangedNumericValue;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/emitters/RegularEmitter.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class RegularEmitter extends Emitter implements Json.Serializable {
    private boolean continuous;
    protected float delay;
    protected float delayTimer;
    public RangedNumericValue delayValue;
    protected float duration;
    protected float durationTimer;
    public RangedNumericValue durationValue;
    protected int emission;
    protected int emissionDelta;
    protected int emissionDiff;
    private EmissionMode emissionMode;
    public ScaledNumericValue emissionValue;
    protected int life;
    private ParallelArray.FloatChannel lifeChannel;
    protected int lifeDiff;
    protected int lifeOffset;
    protected int lifeOffsetDiff;
    public ScaledNumericValue lifeOffsetValue;
    public ScaledNumericValue lifeValue;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/emitters/RegularEmitter$EmissionMode.class
      classes8.dex
     */
    public enum EmissionMode {
        Enabled,
        EnabledUntilCycleEnd,
        Disabled
    }

    public RegularEmitter() {
        this.delayValue = new RangedNumericValue();
        this.durationValue = new RangedNumericValue();
        this.lifeOffsetValue = new ScaledNumericValue();
        this.lifeValue = new ScaledNumericValue();
        this.emissionValue = new ScaledNumericValue();
        this.durationValue.setActive(true);
        this.emissionValue.setActive(true);
        this.lifeValue.setActive(true);
        this.continuous = true;
        this.emissionMode = EmissionMode.Enabled;
    }

    public RegularEmitter(RegularEmitter regularEmitter) {
        this();
        set(regularEmitter);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void allocateChannels() {
        this.lifeChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Life);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void start() {
        this.delay = this.delayValue.active ? this.delayValue.newLowValue() : 0.0f;
        this.delayTimer = 0.0f;
        this.durationTimer = 0.0f;
        float fNewLowValue = this.durationValue.newLowValue();
        this.duration = fNewLowValue;
        this.percent = this.durationTimer / fNewLowValue;
        this.emission = (int) this.emissionValue.newLowValue();
        this.emissionDiff = (int) this.emissionValue.newHighValue();
        if (!this.emissionValue.isRelative()) {
            this.emissionDiff -= this.emission;
        }
        this.life = (int) this.lifeValue.newLowValue();
        this.lifeDiff = (int) this.lifeValue.newHighValue();
        if (!this.lifeValue.isRelative()) {
            this.lifeDiff -= this.life;
        }
        this.lifeOffset = this.lifeOffsetValue.active ? (int) this.lifeOffsetValue.newLowValue() : 0;
        this.lifeOffsetDiff = (int) this.lifeOffsetValue.newHighValue();
        if (this.lifeOffsetValue.isRelative()) {
            return;
        }
        this.lifeOffsetDiff -= this.lifeOffset;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void init() {
        super.init();
        this.emissionDelta = 0;
        this.durationTimer = this.duration;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void activateParticles(int i, int i2) {
        int i3;
        int scale = this.life + ((int) (this.lifeDiff * this.lifeValue.getScale(this.percent)));
        int scale2 = (int) (this.lifeOffset + (this.lifeOffsetDiff * this.lifeOffsetValue.getScale(this.percent)));
        if (scale2 > 0) {
            if (scale2 >= scale) {
                scale2 = scale - 1;
            }
            i3 = scale - scale2;
        } else {
            i3 = scale;
        }
        float f = i3;
        float f2 = scale;
        float f3 = 1.0f - (f / f2);
        int i4 = i * this.lifeChannel.strideSize;
        int i5 = (i2 * this.lifeChannel.strideSize) + i4;
        while (i4 < i5) {
            this.lifeChannel.data[i4] = f;
            this.lifeChannel.data[i4 + 1] = f2;
            this.lifeChannel.data[i4 + 2] = f3;
            i4 += this.lifeChannel.strideSize;
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void update() {
        float f = this.controller.deltaTime * 1000.0f;
        float f2 = this.delayTimer;
        int i = 0;
        if (f2 < this.delay) {
            this.delayTimer = f2 + f;
        } else {
            boolean z = this.emissionMode != EmissionMode.Disabled;
            float f3 = this.durationTimer;
            float f4 = this.duration;
            if (f3 < f4) {
                float f5 = f3 + f;
                this.durationTimer = f5;
                this.percent = f5 / f4;
            } else if (this.continuous && z && this.emissionMode == EmissionMode.Enabled) {
                this.controller.start();
            } else {
                z = false;
            }
            if (z) {
                this.emissionDelta = (int) (this.emissionDelta + f);
                float scale = this.emission + (this.emissionDiff * this.emissionValue.getScale(this.percent));
                if (scale > 0.0f) {
                    float f6 = 1000.0f / scale;
                    int i2 = this.emissionDelta;
                    if (i2 >= f6) {
                        int iMin = Math.min((int) (i2 / f6), this.maxParticleCount - this.controller.particles.size);
                        this.emissionDelta = (int) (((int) (this.emissionDelta - (iMin * f6))) % f6);
                        addParticles(iMin);
                    }
                }
                if (this.controller.particles.size < this.minParticleCount) {
                    addParticles(this.minParticleCount - this.controller.particles.size);
                }
            }
        }
        int i3 = this.controller.particles.size;
        int i4 = 0;
        while (i < this.controller.particles.size) {
            float[] fArr = this.lifeChannel.data;
            float f7 = fArr[i4] - f;
            fArr[i4] = f7;
            if (f7 <= 0.0f) {
                this.controller.particles.removeElement(i);
            } else {
                this.lifeChannel.data[i4 + 2] = 1.0f - (this.lifeChannel.data[i4] / this.lifeChannel.data[i4 + 1]);
                i++;
                i4 += this.lifeChannel.strideSize;
            }
        }
        if (this.controller.particles.size < i3) {
            this.controller.killParticles(this.controller.particles.size, i3 - this.controller.particles.size);
        }
    }

    private void addParticles(int i) {
        int iMin = Math.min(i, this.maxParticleCount - this.controller.particles.size);
        if (iMin <= 0) {
            return;
        }
        this.controller.activateParticles(this.controller.particles.size, iMin);
        this.controller.particles.size += iMin;
    }

    public ScaledNumericValue getLife() {
        return this.lifeValue;
    }

    public ScaledNumericValue getEmission() {
        return this.emissionValue;
    }

    public RangedNumericValue getDuration() {
        return this.durationValue;
    }

    public RangedNumericValue getDelay() {
        return this.delayValue;
    }

    public ScaledNumericValue getLifeOffset() {
        return this.lifeOffsetValue;
    }

    public boolean isContinuous() {
        return this.continuous;
    }

    public void setContinuous(boolean z) {
        this.continuous = z;
    }

    public EmissionMode getEmissionMode() {
        return this.emissionMode;
    }

    public void setEmissionMode(EmissionMode emissionMode) {
        this.emissionMode = emissionMode;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter
    public boolean isComplete() {
        return this.delayTimer >= this.delay && this.durationTimer >= this.duration && this.controller.particles.size == 0;
    }

    public float getPercentComplete() {
        if (this.delayTimer < this.delay) {
            return 0.0f;
        }
        return Math.min(1.0f, this.durationTimer / this.duration);
    }

    public void set(RegularEmitter regularEmitter) {
        super.set((Emitter) regularEmitter);
        this.delayValue.load(regularEmitter.delayValue);
        this.durationValue.load(regularEmitter.durationValue);
        this.lifeOffsetValue.load(regularEmitter.lifeOffsetValue);
        this.lifeValue.load(regularEmitter.lifeValue);
        this.emissionValue.load(regularEmitter.emissionValue);
        this.emission = regularEmitter.emission;
        this.emissionDiff = regularEmitter.emissionDiff;
        this.emissionDelta = regularEmitter.emissionDelta;
        this.lifeOffset = regularEmitter.lifeOffset;
        this.lifeOffsetDiff = regularEmitter.lifeOffsetDiff;
        this.life = regularEmitter.life;
        this.lifeDiff = regularEmitter.lifeDiff;
        this.duration = regularEmitter.duration;
        this.delay = regularEmitter.delay;
        this.durationTimer = regularEmitter.durationTimer;
        this.delayTimer = regularEmitter.delayTimer;
        this.continuous = regularEmitter.continuous;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public ParticleControllerComponent copy() {
        return new RegularEmitter(this);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        super.write(json);
        json.writeValue("continous", Boolean.valueOf(this.continuous));
        json.writeValue("emission", this.emissionValue);
        json.writeValue("delay", this.delayValue);
        json.writeValue("duration", this.durationValue);
        json.writeValue("life", this.lifeValue);
        json.writeValue("lifeOffset", this.lifeOffsetValue);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        super.read(json, jsonValue);
        this.continuous = ((Boolean) json.readValue("continous", Boolean.TYPE, jsonValue)).booleanValue();
        this.emissionValue = (ScaledNumericValue) json.readValue("emission", ScaledNumericValue.class, jsonValue);
        this.delayValue = (RangedNumericValue) json.readValue("delay", RangedNumericValue.class, jsonValue);
        this.durationValue = (RangedNumericValue) json.readValue("duration", RangedNumericValue.class, jsonValue);
        this.lifeValue = (ScaledNumericValue) json.readValue("life", ScaledNumericValue.class, jsonValue);
        this.lifeOffsetValue = (ScaledNumericValue) json.readValue("lifeOffset", ScaledNumericValue.class, jsonValue);
    }
}
