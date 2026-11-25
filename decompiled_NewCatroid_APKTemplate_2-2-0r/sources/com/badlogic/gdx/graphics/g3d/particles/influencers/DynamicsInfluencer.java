package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.Arrays;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/DynamicsInfluencer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class DynamicsInfluencer extends Influencer {
    private ParallelArray.FloatChannel accellerationChannel;
    private ParallelArray.FloatChannel angularVelocityChannel;
    boolean has2dAngularVelocity;
    boolean has3dAngularVelocity;
    boolean hasAcceleration;
    private ParallelArray.FloatChannel positionChannel;
    private ParallelArray.FloatChannel previousPositionChannel;
    private ParallelArray.FloatChannel rotationChannel;
    public Array<DynamicsModifier> velocities;

    public DynamicsInfluencer() {
        this.velocities = new Array<>(true, 3, DynamicsModifier.class);
    }

    public DynamicsInfluencer(DynamicsModifier... dynamicsModifierArr) {
        this.velocities = new Array<>(true, dynamicsModifierArr.length, DynamicsModifier.class);
        for (DynamicsModifier dynamicsModifier : dynamicsModifierArr) {
            this.velocities.add((DynamicsModifier) dynamicsModifier.copy());
        }
    }

    public DynamicsInfluencer(DynamicsInfluencer dynamicsInfluencer) {
        this((DynamicsModifier[]) dynamicsInfluencer.velocities.toArray(DynamicsModifier.class));
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void allocateChannels() {
        for (int i = 0; i < this.velocities.size; i++) {
            this.velocities.items[i].allocateChannels();
        }
        ParallelArray.FloatChannel floatChannel = (ParallelArray.FloatChannel) this.controller.particles.getChannel(ParticleChannels.Acceleration);
        this.accellerationChannel = floatChannel;
        boolean z = floatChannel != null;
        this.hasAcceleration = z;
        if (z) {
            this.positionChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Position);
            this.previousPositionChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.PreviousPosition);
        }
        ParallelArray.FloatChannel floatChannel2 = (ParallelArray.FloatChannel) this.controller.particles.getChannel(ParticleChannels.AngularVelocity2D);
        this.angularVelocityChannel = floatChannel2;
        boolean z2 = floatChannel2 != null;
        this.has2dAngularVelocity = z2;
        if (z2) {
            this.rotationChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Rotation2D);
            this.has3dAngularVelocity = false;
            return;
        }
        ParallelArray.FloatChannel floatChannel3 = (ParallelArray.FloatChannel) this.controller.particles.getChannel(ParticleChannels.AngularVelocity3D);
        this.angularVelocityChannel = floatChannel3;
        boolean z3 = floatChannel3 != null;
        this.has3dAngularVelocity = z3;
        if (z3) {
            this.rotationChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Rotation3D);
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void set(ParticleController particleController) {
        super.set(particleController);
        for (int i = 0; i < this.velocities.size; i++) {
            this.velocities.items[i].set(particleController);
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void init() {
        for (int i = 0; i < this.velocities.size; i++) {
            this.velocities.items[i].init();
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void activateParticles(int i, int i2) {
        if (this.hasAcceleration) {
            int i3 = this.positionChannel.strideSize * i;
            int i4 = (this.positionChannel.strideSize * i2) + i3;
            while (i3 < i4) {
                this.previousPositionChannel.data[i3] = this.positionChannel.data[i3];
                int i5 = i3 + 1;
                this.previousPositionChannel.data[i5] = this.positionChannel.data[i5];
                int i6 = i3 + 2;
                this.previousPositionChannel.data[i6] = this.positionChannel.data[i6];
                i3 += this.positionChannel.strideSize;
            }
        }
        if (this.has2dAngularVelocity) {
            int i7 = this.rotationChannel.strideSize * i;
            int i8 = (this.rotationChannel.strideSize * i2) + i7;
            while (i7 < i8) {
                this.rotationChannel.data[i7] = 1.0f;
                this.rotationChannel.data[i7 + 1] = 0.0f;
                i7 += this.rotationChannel.strideSize;
            }
        } else if (this.has3dAngularVelocity) {
            int i9 = this.rotationChannel.strideSize * i;
            int i10 = (this.rotationChannel.strideSize * i2) + i9;
            while (i9 < i10) {
                this.rotationChannel.data[i9] = 0.0f;
                this.rotationChannel.data[i9 + 1] = 0.0f;
                this.rotationChannel.data[i9 + 2] = 0.0f;
                this.rotationChannel.data[i9 + 3] = 1.0f;
                i9 += this.rotationChannel.strideSize;
            }
        }
        for (int i11 = 0; i11 < this.velocities.size; i11++) {
            this.velocities.items[i11].activateParticles(i, i2);
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void update() {
        int i = 0;
        if (this.hasAcceleration) {
            Arrays.fill(this.accellerationChannel.data, 0, this.controller.particles.size * this.accellerationChannel.strideSize, 0.0f);
        }
        if (this.has2dAngularVelocity || this.has3dAngularVelocity) {
            Arrays.fill(this.angularVelocityChannel.data, 0, this.controller.particles.size * this.angularVelocityChannel.strideSize, 0.0f);
        }
        for (int i2 = 0; i2 < this.velocities.size; i2++) {
            this.velocities.items[i2].update();
        }
        if (this.hasAcceleration) {
            int i3 = 0;
            int i4 = 0;
            while (i3 < this.controller.particles.size) {
                float f = this.positionChannel.data[i4];
                int i5 = i4 + 1;
                float f2 = this.positionChannel.data[i5];
                int i6 = i4 + 2;
                float f3 = this.positionChannel.data[i6];
                this.positionChannel.data[i4] = ((f * 2.0f) - this.previousPositionChannel.data[i4]) + (this.accellerationChannel.data[i4] * this.controller.deltaTimeSqr);
                this.positionChannel.data[i5] = ((f2 * 2.0f) - this.previousPositionChannel.data[i5]) + (this.accellerationChannel.data[i5] * this.controller.deltaTimeSqr);
                this.positionChannel.data[i6] = ((2.0f * f3) - this.previousPositionChannel.data[i6]) + (this.accellerationChannel.data[i6] * this.controller.deltaTimeSqr);
                this.previousPositionChannel.data[i4] = f;
                this.previousPositionChannel.data[i5] = f2;
                this.previousPositionChannel.data[i6] = f3;
                i3++;
                i4 += this.positionChannel.strideSize;
            }
        }
        if (this.has2dAngularVelocity) {
            int i7 = 0;
            while (i < this.controller.particles.size) {
                float f4 = this.angularVelocityChannel.data[i] * this.controller.deltaTime;
                if (f4 != 0.0f) {
                    float fCosDeg = MathUtils.cosDeg(f4);
                    float fSinDeg = MathUtils.sinDeg(f4);
                    float f5 = this.rotationChannel.data[i7];
                    int i8 = i7 + 1;
                    float f6 = this.rotationChannel.data[i8];
                    this.rotationChannel.data[i7] = (f5 * fCosDeg) - (f6 * fSinDeg);
                    this.rotationChannel.data[i8] = (f6 * fCosDeg) + (f5 * fSinDeg);
                }
                i++;
                i7 += this.rotationChannel.strideSize;
            }
            return;
        }
        if (this.has3dAngularVelocity) {
            int i9 = 0;
            int i10 = 0;
            while (i < this.controller.particles.size) {
                float f7 = this.angularVelocityChannel.data[i9];
                float f8 = this.angularVelocityChannel.data[i9 + 1];
                float f9 = this.angularVelocityChannel.data[i9 + 2];
                float f10 = this.rotationChannel.data[i10];
                int i11 = i10 + 1;
                float f11 = this.rotationChannel.data[i11];
                int i12 = i10 + 2;
                float f12 = this.rotationChannel.data[i12];
                int i13 = i10 + 3;
                float f13 = this.rotationChannel.data[i13];
                TMP_Q.set(f7, f8, f9, 0.0f).mul(f10, f11, f12, f13).mul(this.controller.deltaTime * 0.5f).add(f10, f11, f12, f13).nor();
                this.rotationChannel.data[i10] = TMP_Q.x;
                this.rotationChannel.data[i11] = TMP_Q.y;
                this.rotationChannel.data[i12] = TMP_Q.z;
                this.rotationChannel.data[i13] = TMP_Q.w;
                i++;
                i10 += this.rotationChannel.strideSize;
                i9 += this.angularVelocityChannel.strideSize;
            }
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public DynamicsInfluencer copy() {
        return new DynamicsInfluencer(this);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        json.writeValue("velocities", this.velocities, Array.class, DynamicsModifier.class);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        this.velocities.addAll((Array<? extends DynamicsModifier>) json.readValue("velocities", Array.class, DynamicsModifier.class, jsonValue));
    }
}
