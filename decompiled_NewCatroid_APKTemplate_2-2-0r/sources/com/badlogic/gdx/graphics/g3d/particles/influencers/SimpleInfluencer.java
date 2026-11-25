package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/SimpleInfluencer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class SimpleInfluencer extends Influencer {
    ParallelArray.FloatChannel interpolationChannel;
    ParallelArray.FloatChannel lifeChannel;
    public ScaledNumericValue value;
    ParallelArray.FloatChannel valueChannel;
    ParallelArray.ChannelDescriptor valueChannelDescriptor;

    public SimpleInfluencer() {
        ScaledNumericValue scaledNumericValue = new ScaledNumericValue();
        this.value = scaledNumericValue;
        scaledNumericValue.setHigh(1.0f);
    }

    public SimpleInfluencer(SimpleInfluencer simpleInfluencer) {
        this();
        set(simpleInfluencer);
    }

    private void set(SimpleInfluencer simpleInfluencer) {
        this.value.load(simpleInfluencer.value);
        this.valueChannelDescriptor = simpleInfluencer.valueChannelDescriptor;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void allocateChannels() {
        this.valueChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(this.valueChannelDescriptor);
        ParticleChannels.Interpolation.f113id = this.controller.particleChannels.newId();
        this.interpolationChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Interpolation);
        this.lifeChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Life);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void activateParticles(int i, int i2) {
        if (!this.value.isRelative()) {
            int i3 = this.valueChannel.strideSize * i;
            int i4 = i * this.interpolationChannel.strideSize;
            int i5 = (i2 * this.valueChannel.strideSize) + i3;
            while (i3 < i5) {
                float fNewLowValue = this.value.newLowValue();
                float fNewHighValue = this.value.newHighValue() - fNewLowValue;
                this.interpolationChannel.data[i4] = fNewLowValue;
                this.interpolationChannel.data[i4 + 1] = fNewHighValue;
                this.valueChannel.data[i3] = fNewLowValue + (fNewHighValue * this.value.getScale(0.0f));
                i3 += this.valueChannel.strideSize;
                i4 += this.interpolationChannel.strideSize;
            }
            return;
        }
        int i6 = this.valueChannel.strideSize * i;
        int i7 = i * this.interpolationChannel.strideSize;
        int i8 = (i2 * this.valueChannel.strideSize) + i6;
        while (i6 < i8) {
            float fNewLowValue2 = this.value.newLowValue();
            float fNewHighValue2 = this.value.newHighValue();
            this.interpolationChannel.data[i7] = fNewLowValue2;
            this.interpolationChannel.data[i7 + 1] = fNewHighValue2;
            this.valueChannel.data[i6] = fNewLowValue2 + (fNewHighValue2 * this.value.getScale(0.0f));
            i6 += this.valueChannel.strideSize;
            i7 += this.interpolationChannel.strideSize;
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void update() {
        int i = this.controller.particles.size * this.valueChannel.strideSize;
        int i2 = 0;
        int i3 = 2;
        int i4 = 0;
        while (i2 < i) {
            this.valueChannel.data[i2] = this.interpolationChannel.data[i4] + (this.interpolationChannel.data[i4 + 1] * this.value.getScale(this.lifeChannel.data[i3]));
            i2 += this.valueChannel.strideSize;
            i4 += this.interpolationChannel.strideSize;
            i3 += this.lifeChannel.strideSize;
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        json.writeValue("value", this.value);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        this.value = (ScaledNumericValue) json.readValue("value", ScaledNumericValue.class, jsonValue);
    }
}
