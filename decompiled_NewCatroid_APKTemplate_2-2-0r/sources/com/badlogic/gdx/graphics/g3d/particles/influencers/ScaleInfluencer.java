package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/ScaleInfluencer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ScaleInfluencer extends SimpleInfluencer {
    public ScaleInfluencer() {
        this.valueChannelDescriptor = ParticleChannels.Scale;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.SimpleInfluencer, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void activateParticles(int i, int i2) {
        if (this.value.isRelative()) {
            int i3 = this.valueChannel.strideSize * i;
            int i4 = i * this.interpolationChannel.strideSize;
            int i5 = (i2 * this.valueChannel.strideSize) + i3;
            while (i3 < i5) {
                float fNewLowValue = this.value.newLowValue() * this.controller.scale.x;
                float fNewHighValue = this.value.newHighValue() * this.controller.scale.x;
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
            float fNewLowValue2 = this.value.newLowValue() * this.controller.scale.x;
            float fNewHighValue2 = (this.value.newHighValue() * this.controller.scale.x) - fNewLowValue2;
            this.interpolationChannel.data[i7] = fNewLowValue2;
            this.interpolationChannel.data[i7 + 1] = fNewHighValue2;
            this.valueChannel.data[i6] = fNewLowValue2 + (fNewHighValue2 * this.value.getScale(0.0f));
            i6 += this.valueChannel.strideSize;
            i7 += this.interpolationChannel.strideSize;
        }
    }

    public ScaleInfluencer(ScaleInfluencer scaleInfluencer) {
        super(scaleInfluencer);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public ParticleControllerComponent copy() {
        return new ScaleInfluencer(this);
    }
}
