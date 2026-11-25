package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/ParticleControllerFinalizerInfluencer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ParticleControllerFinalizerInfluencer extends Influencer {
    ParallelArray.ObjectChannel<ParticleController> controllerChannel;
    boolean hasRotation;
    boolean hasScale;
    ParallelArray.FloatChannel positionChannel;
    ParallelArray.FloatChannel rotationChannel;
    ParallelArray.FloatChannel scaleChannel;

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void init() {
        ParallelArray.ObjectChannel<ParticleController> objectChannel = (ParallelArray.ObjectChannel) this.controller.particles.getChannel(ParticleChannels.ParticleController);
        this.controllerChannel = objectChannel;
        if (objectChannel == null) {
            throw new GdxRuntimeException("ParticleController channel not found, specify an influencer which will allocate it please.");
        }
        this.scaleChannel = (ParallelArray.FloatChannel) this.controller.particles.getChannel(ParticleChannels.Scale);
        ParallelArray.FloatChannel floatChannel = (ParallelArray.FloatChannel) this.controller.particles.getChannel(ParticleChannels.Rotation3D);
        this.rotationChannel = floatChannel;
        this.hasScale = this.scaleChannel != null;
        this.hasRotation = floatChannel != null;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void allocateChannels() {
        this.positionChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Position);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void update() {
        float f;
        float f2;
        float f3;
        float f4;
        int i = this.controller.particles.size;
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            ParticleController particleController = this.controllerChannel.data[i2];
            float f5 = this.hasScale ? this.scaleChannel.data[i2] : 1.0f;
            if (this.hasRotation) {
                int i4 = this.rotationChannel.strideSize * i2;
                float f6 = this.rotationChannel.data[i4];
                float f7 = this.rotationChannel.data[i4 + 1];
                float f8 = this.rotationChannel.data[i4 + 2];
                f4 = this.rotationChannel.data[i4 + 3];
                f = f6;
                f2 = f7;
                f3 = f8;
            } else {
                f = 0.0f;
                f2 = 0.0f;
                f3 = 0.0f;
                f4 = 1.0f;
            }
            particleController.setTransform(this.positionChannel.data[i3], this.positionChannel.data[i3 + 1], this.positionChannel.data[i3 + 2], f, f2, f3, f4, f5);
            particleController.update();
            i2++;
            i3 += this.positionChannel.strideSize;
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public ParticleControllerFinalizerInfluencer copy() {
        return new ParticleControllerFinalizerInfluencer();
    }
}
