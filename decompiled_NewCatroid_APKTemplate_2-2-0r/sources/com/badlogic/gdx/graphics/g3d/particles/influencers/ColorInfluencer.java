package com.badlogic.gdx.graphics.g3d.particles.influencers;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.values.GradientColorValue;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/ColorInfluencer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class ColorInfluencer extends Influencer {
    ParallelArray.FloatChannel colorChannel;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/ColorInfluencer$Random.class
      classes8.dex
     */
    public static class Random extends ColorInfluencer {
        ParallelArray.FloatChannel colorChannel;

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            this.colorChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Color);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void activateParticles(int i, int i2) {
            int i3 = i * this.colorChannel.strideSize;
            int i4 = (i2 * this.colorChannel.strideSize) + i3;
            while (i3 < i4) {
                this.colorChannel.data[i3] = MathUtils.random();
                this.colorChannel.data[i3 + 1] = MathUtils.random();
                this.colorChannel.data[i3 + 2] = MathUtils.random();
                this.colorChannel.data[i3 + 3] = MathUtils.random();
                i3 += this.colorChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public Random copy() {
            return new Random();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/ColorInfluencer$Single.class
      classes8.dex
     */
    public static class Single extends ColorInfluencer {
        ParallelArray.FloatChannel alphaInterpolationChannel;
        public ScaledNumericValue alphaValue;
        public GradientColorValue colorValue;
        ParallelArray.FloatChannel lifeChannel;

        public Single() {
            this.colorValue = new GradientColorValue();
            ScaledNumericValue scaledNumericValue = new ScaledNumericValue();
            this.alphaValue = scaledNumericValue;
            scaledNumericValue.setHigh(1.0f);
        }

        public Single(Single single) {
            this();
            set(single);
        }

        public void set(Single single) {
            this.colorValue.load(single.colorValue);
            this.alphaValue.load(single.alphaValue);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            super.allocateChannels();
            ParticleChannels.Interpolation.f113id = this.controller.particleChannels.newId();
            this.alphaInterpolationChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Interpolation);
            this.lifeChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Life);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void activateParticles(int i, int i2) {
            int i3 = this.colorChannel.strideSize * i;
            int i4 = this.alphaInterpolationChannel.strideSize * i;
            int i5 = (i * this.lifeChannel.strideSize) + 2;
            int i6 = (i2 * this.colorChannel.strideSize) + i3;
            while (i3 < i6) {
                float fNewLowValue = this.alphaValue.newLowValue();
                float fNewHighValue = this.alphaValue.newHighValue() - fNewLowValue;
                this.colorValue.getColor(0.0f, this.colorChannel.data, i3);
                this.colorChannel.data[i3 + 3] = (this.alphaValue.getScale(this.lifeChannel.data[i5]) * fNewHighValue) + fNewLowValue;
                this.alphaInterpolationChannel.data[i4] = fNewLowValue;
                this.alphaInterpolationChannel.data[i4 + 1] = fNewHighValue;
                i3 += this.colorChannel.strideSize;
                i4 += this.alphaInterpolationChannel.strideSize;
                i5 += this.lifeChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void update() {
            int i = this.controller.particles.size * this.colorChannel.strideSize;
            int i2 = 0;
            int i3 = 2;
            int i4 = 0;
            while (i2 < i) {
                float f = this.lifeChannel.data[i3];
                this.colorValue.getColor(f, this.colorChannel.data, i2);
                this.colorChannel.data[i2 + 3] = this.alphaInterpolationChannel.data[i4] + (this.alphaInterpolationChannel.data[i4 + 1] * this.alphaValue.getScale(f));
                i2 += this.colorChannel.strideSize;
                i4 += this.alphaInterpolationChannel.strideSize;
                i3 += this.lifeChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public Single copy() {
            return new Single(this);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
        public void write(Json json) {
            json.writeValue("alpha", this.alphaValue);
            json.writeValue(TypedValues.Custom.S_COLOR, this.colorValue);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
        public void read(Json json, JsonValue jsonValue) {
            this.alphaValue = (ScaledNumericValue) json.readValue("alpha", ScaledNumericValue.class, jsonValue);
            this.colorValue = (GradientColorValue) json.readValue(TypedValues.Custom.S_COLOR, GradientColorValue.class, jsonValue);
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void allocateChannels() {
        this.colorChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Color);
    }
}
