package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/DynamicsModifier.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class DynamicsModifier extends Influencer {
    public boolean isGlobal;
    protected ParallelArray.FloatChannel lifeChannel;
    protected static final Vector3 TMP_V1 = new Vector3();
    protected static final Vector3 TMP_V2 = new Vector3();
    protected static final Vector3 TMP_V3 = new Vector3();
    protected static final Quaternion TMP_Q = new Quaternion();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/DynamicsModifier$FaceDirection.class
      classes8.dex
     */
    public static class FaceDirection extends DynamicsModifier {
        ParallelArray.FloatChannel accellerationChannel;
        ParallelArray.FloatChannel rotationChannel;

        public FaceDirection() {
        }

        public FaceDirection(FaceDirection faceDirection) {
            super(faceDirection);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            this.rotationChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Rotation3D);
            this.accellerationChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Acceleration);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void update() {
            int i = this.controller.particles.size * this.rotationChannel.strideSize;
            int i2 = 0;
            int i3 = 0;
            while (i2 < i) {
                Vector3 vector3Nor = TMP_V1.set(this.accellerationChannel.data[i3], this.accellerationChannel.data[i3 + 1], this.accellerationChannel.data[i3 + 2]).nor();
                Vector3 vector3Nor2 = TMP_V2.set(TMP_V1).crs(Vector3.Y).nor().crs(TMP_V1).nor();
                Vector3 vector3Nor3 = TMP_V3.set(vector3Nor2).crs(vector3Nor).nor();
                TMP_Q.setFromAxes(false, vector3Nor3.x, vector3Nor2.x, vector3Nor.x, vector3Nor3.y, vector3Nor2.y, vector3Nor.y, vector3Nor3.z, vector3Nor2.z, vector3Nor.z);
                this.rotationChannel.data[i2] = TMP_Q.x;
                this.rotationChannel.data[i2 + 1] = TMP_Q.y;
                this.rotationChannel.data[i2 + 2] = TMP_Q.z;
                this.rotationChannel.data[i2 + 3] = TMP_Q.w;
                i2 += this.rotationChannel.strideSize;
                i3 += this.accellerationChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public ParticleControllerComponent copy() {
            return new FaceDirection(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/DynamicsModifier$Strength.class
      classes8.dex
     */
    public static abstract class Strength extends DynamicsModifier {
        protected ParallelArray.FloatChannel strengthChannel;
        public ScaledNumericValue strengthValue;

        public Strength() {
            this.strengthValue = new ScaledNumericValue();
        }

        public Strength(Strength strength) {
            super(strength);
            ScaledNumericValue scaledNumericValue = new ScaledNumericValue();
            this.strengthValue = scaledNumericValue;
            scaledNumericValue.load(strength.strengthValue);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            super.allocateChannels();
            ParticleChannels.Interpolation.f113id = this.controller.particleChannels.newId();
            this.strengthChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Interpolation);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void activateParticles(int i, int i2) {
            int i3 = i * this.strengthChannel.strideSize;
            int i4 = (i2 * this.strengthChannel.strideSize) + i3;
            while (i3 < i4) {
                float fNewLowValue = this.strengthValue.newLowValue();
                float fNewHighValue = this.strengthValue.newHighValue();
                if (!this.strengthValue.isRelative()) {
                    fNewHighValue -= fNewLowValue;
                }
                this.strengthChannel.data[i3] = fNewLowValue;
                this.strengthChannel.data[i3 + 1] = fNewHighValue;
                i3 += this.strengthChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
        public void write(Json json) {
            super.write(json);
            json.writeValue("strengthValue", this.strengthValue);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
        public void read(Json json, JsonValue jsonValue) {
            super.read(json, jsonValue);
            this.strengthValue = (ScaledNumericValue) json.readValue("strengthValue", ScaledNumericValue.class, jsonValue);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/DynamicsModifier$Angular.class
      classes8.dex
     */
    public static abstract class Angular extends Strength {
        protected ParallelArray.FloatChannel angularChannel;
        public ScaledNumericValue phiValue;
        public ScaledNumericValue thetaValue;

        public Angular() {
            this.thetaValue = new ScaledNumericValue();
            this.phiValue = new ScaledNumericValue();
        }

        public Angular(Angular angular) {
            super(angular);
            this.thetaValue = new ScaledNumericValue();
            this.phiValue = new ScaledNumericValue();
            this.thetaValue.load(angular.thetaValue);
            this.phiValue.load(angular.phiValue);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Strength, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            super.allocateChannels();
            ParticleChannels.Interpolation4.f113id = this.controller.particleChannels.newId();
            this.angularChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Interpolation4);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Strength, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void activateParticles(int i, int i2) {
            super.activateParticles(i, i2);
            int i3 = i * this.angularChannel.strideSize;
            int i4 = (i2 * this.angularChannel.strideSize) + i3;
            while (i3 < i4) {
                float fNewLowValue = this.thetaValue.newLowValue();
                float fNewHighValue = this.thetaValue.newHighValue();
                if (!this.thetaValue.isRelative()) {
                    fNewHighValue -= fNewLowValue;
                }
                this.angularChannel.data[i3] = fNewLowValue;
                this.angularChannel.data[i3 + 1] = fNewHighValue;
                float fNewLowValue2 = this.phiValue.newLowValue();
                float fNewHighValue2 = this.phiValue.newHighValue();
                if (!this.phiValue.isRelative()) {
                    fNewHighValue2 -= fNewLowValue2;
                }
                this.angularChannel.data[i3 + 2] = fNewLowValue2;
                this.angularChannel.data[i3 + 3] = fNewHighValue2;
                i3 += this.angularChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Strength, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
        public void write(Json json) {
            super.write(json);
            json.writeValue("thetaValue", this.thetaValue);
            json.writeValue("phiValue", this.phiValue);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Strength, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
        public void read(Json json, JsonValue jsonValue) {
            super.read(json, jsonValue);
            this.thetaValue = (ScaledNumericValue) json.readValue("thetaValue", ScaledNumericValue.class, jsonValue);
            this.phiValue = (ScaledNumericValue) json.readValue("phiValue", ScaledNumericValue.class, jsonValue);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/DynamicsModifier$Rotational2D.class
      classes8.dex
     */
    public static class Rotational2D extends Strength {
        ParallelArray.FloatChannel rotationalVelocity2dChannel;

        public Rotational2D() {
        }

        public Rotational2D(Rotational2D rotational2D) {
            super(rotational2D);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Strength, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            super.allocateChannels();
            this.rotationalVelocity2dChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.AngularVelocity2D);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void update() {
            int i = this.controller.particles.size * this.rotationalVelocity2dChannel.strideSize;
            int i2 = 0;
            int i3 = 2;
            int i4 = 0;
            while (i2 < i) {
                float[] fArr = this.rotationalVelocity2dChannel.data;
                fArr[i2] = fArr[i2] + this.strengthChannel.data[i4] + (this.strengthChannel.data[i4 + 1] * this.strengthValue.getScale(this.lifeChannel.data[i3]));
                i4 += this.strengthChannel.strideSize;
                i2 += this.rotationalVelocity2dChannel.strideSize;
                i3 += this.lifeChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public Rotational2D copy() {
            return new Rotational2D(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/DynamicsModifier$Rotational3D.class
      classes8.dex
     */
    public static class Rotational3D extends Angular {
        ParallelArray.FloatChannel rotationChannel;
        ParallelArray.FloatChannel rotationalForceChannel;

        public Rotational3D() {
        }

        public Rotational3D(Rotational3D rotational3D) {
            super(rotational3D);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Angular, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Strength, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            super.allocateChannels();
            this.rotationChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Rotation3D);
            this.rotationalForceChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.AngularVelocity3D);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void update() {
            int i = this.controller.particles.size * this.rotationalForceChannel.strideSize;
            int i2 = 0;
            int i3 = 0;
            int i4 = 2;
            int i5 = 0;
            while (i2 < i) {
                float f = this.lifeChannel.data[i4];
                float scale = this.strengthChannel.data[i5] + (this.strengthChannel.data[i5 + 1] * this.strengthValue.getScale(f));
                float scale2 = this.angularChannel.data[i3 + 2] + (this.angularChannel.data[i3 + 3] * this.phiValue.getScale(f));
                float scale3 = this.angularChannel.data[i3] + (this.angularChannel.data[i3 + 1] * this.thetaValue.getScale(f));
                float fCosDeg = MathUtils.cosDeg(scale3);
                float fSinDeg = MathUtils.sinDeg(scale3);
                float fCosDeg2 = MathUtils.cosDeg(scale2);
                float fSinDeg2 = MathUtils.sinDeg(scale2);
                TMP_V3.set(fCosDeg * fSinDeg2, fCosDeg2, fSinDeg * fSinDeg2);
                TMP_V3.scl(scale * 0.017453292f);
                float[] fArr = this.rotationalForceChannel.data;
                fArr[i2] = fArr[i2] + TMP_V3.x;
                float[] fArr2 = this.rotationalForceChannel.data;
                int i6 = i2 + 1;
                fArr2[i6] = fArr2[i6] + TMP_V3.y;
                float[] fArr3 = this.rotationalForceChannel.data;
                int i7 = i2 + 2;
                fArr3[i7] = fArr3[i7] + TMP_V3.z;
                i5 += this.strengthChannel.strideSize;
                i2 += this.rotationalForceChannel.strideSize;
                i3 += this.angularChannel.strideSize;
                i4 += this.lifeChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public Rotational3D copy() {
            return new Rotational3D(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/DynamicsModifier$CentripetalAcceleration.class
      classes8.dex
     */
    public static class CentripetalAcceleration extends Strength {
        ParallelArray.FloatChannel accelerationChannel;
        ParallelArray.FloatChannel positionChannel;

        public CentripetalAcceleration() {
        }

        public CentripetalAcceleration(CentripetalAcceleration centripetalAcceleration) {
            super(centripetalAcceleration);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Strength, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            super.allocateChannels();
            this.accelerationChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Acceleration);
            this.positionChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Position);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void update() {
            float f;
            float f2;
            float f3;
            if (this.isGlobal) {
                f = 0.0f;
                f2 = 0.0f;
                f3 = 0.0f;
            } else {
                float[] fArr = this.controller.transform.val;
                f = fArr[12];
                f3 = fArr[13];
                f2 = fArr[14];
            }
            int i = this.controller.particles.size;
            int i2 = 2;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (i3 < i) {
                TMP_V3.set(this.positionChannel.data[i5] - f, this.positionChannel.data[i5 + 1] - f3, this.positionChannel.data[i5 + 2] - f2).nor().scl(this.strengthChannel.data[i4] + (this.strengthChannel.data[i4 + 1] * this.strengthValue.getScale(this.lifeChannel.data[i2])));
                float[] fArr2 = this.accelerationChannel.data;
                fArr2[i6] = fArr2[i6] + TMP_V3.x;
                float[] fArr3 = this.accelerationChannel.data;
                int i7 = i6 + 1;
                fArr3[i7] = fArr3[i7] + TMP_V3.y;
                float[] fArr4 = this.accelerationChannel.data;
                int i8 = i6 + 2;
                fArr4[i8] = fArr4[i8] + TMP_V3.z;
                i3++;
                i5 += this.positionChannel.strideSize;
                i4 += this.strengthChannel.strideSize;
                i6 += this.accelerationChannel.strideSize;
                i2 += this.lifeChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public CentripetalAcceleration copy() {
            return new CentripetalAcceleration(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/DynamicsModifier$PolarAcceleration.class
      classes8.dex
     */
    public static class PolarAcceleration extends Angular {
        ParallelArray.FloatChannel directionalVelocityChannel;

        public PolarAcceleration() {
        }

        public PolarAcceleration(PolarAcceleration polarAcceleration) {
            super(polarAcceleration);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Angular, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Strength, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            super.allocateChannels();
            this.directionalVelocityChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Acceleration);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void update() {
            int i = this.controller.particles.size * this.directionalVelocityChannel.strideSize;
            int i2 = 0;
            int i3 = 0;
            int i4 = 2;
            int i5 = 0;
            while (i2 < i) {
                float f = this.lifeChannel.data[i4];
                float scale = this.strengthChannel.data[i5] + (this.strengthChannel.data[i5 + 1] * this.strengthValue.getScale(f));
                float scale2 = this.angularChannel.data[i3 + 2] + (this.angularChannel.data[i3 + 3] * this.phiValue.getScale(f));
                float scale3 = this.angularChannel.data[i3] + (this.angularChannel.data[i3 + 1] * this.thetaValue.getScale(f));
                float fCosDeg = MathUtils.cosDeg(scale3);
                float fSinDeg = MathUtils.sinDeg(scale3);
                float fCosDeg2 = MathUtils.cosDeg(scale2);
                float fSinDeg2 = MathUtils.sinDeg(scale2);
                TMP_V3.set(fCosDeg * fSinDeg2, fCosDeg2, fSinDeg * fSinDeg2).nor().scl(scale);
                if (!this.isGlobal) {
                    this.controller.transform.getRotation(TMP_Q, true);
                    TMP_V3.mul(TMP_Q);
                }
                float[] fArr = this.directionalVelocityChannel.data;
                fArr[i2] = fArr[i2] + TMP_V3.x;
                float[] fArr2 = this.directionalVelocityChannel.data;
                int i6 = i2 + 1;
                fArr2[i6] = fArr2[i6] + TMP_V3.y;
                float[] fArr3 = this.directionalVelocityChannel.data;
                int i7 = i2 + 2;
                fArr3[i7] = fArr3[i7] + TMP_V3.z;
                i5 += this.strengthChannel.strideSize;
                i2 += this.directionalVelocityChannel.strideSize;
                i3 += this.angularChannel.strideSize;
                i4 += this.lifeChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public PolarAcceleration copy() {
            return new PolarAcceleration(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/DynamicsModifier$TangentialAcceleration.class
      classes8.dex
     */
    public static class TangentialAcceleration extends Angular {
        ParallelArray.FloatChannel directionalVelocityChannel;
        ParallelArray.FloatChannel positionChannel;

        public TangentialAcceleration() {
        }

        public TangentialAcceleration(TangentialAcceleration tangentialAcceleration) {
            super(tangentialAcceleration);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Angular, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Strength, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            super.allocateChannels();
            this.directionalVelocityChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Acceleration);
            this.positionChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Position);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void update() {
            int i = this.controller.particles.size * this.directionalVelocityChannel.strideSize;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 2;
            int i6 = 0;
            while (i2 < i) {
                float f = this.lifeChannel.data[i5];
                float scale = this.strengthChannel.data[i6] + (this.strengthChannel.data[i6 + 1] * this.strengthValue.getScale(f));
                float scale2 = this.angularChannel.data[i3 + 2] + (this.angularChannel.data[i3 + 3] * this.phiValue.getScale(f));
                float scale3 = this.angularChannel.data[i3] + (this.angularChannel.data[i3 + 1] * this.thetaValue.getScale(f));
                float fCosDeg = MathUtils.cosDeg(scale3);
                float fSinDeg = MathUtils.sinDeg(scale3);
                float fCosDeg2 = MathUtils.cosDeg(scale2);
                float fSinDeg2 = MathUtils.sinDeg(scale2);
                TMP_V3.set(fCosDeg * fSinDeg2, fCosDeg2, fSinDeg * fSinDeg2);
                TMP_V1.set(this.positionChannel.data[i4], this.positionChannel.data[i4 + 1], this.positionChannel.data[i4 + 2]);
                if (!this.isGlobal) {
                    this.controller.transform.getTranslation(TMP_V2);
                    TMP_V1.sub(TMP_V2);
                    this.controller.transform.getRotation(TMP_Q, true);
                    TMP_V3.mul(TMP_Q);
                }
                TMP_V3.crs(TMP_V1).nor().scl(scale);
                float[] fArr = this.directionalVelocityChannel.data;
                fArr[i2] = fArr[i2] + TMP_V3.x;
                float[] fArr2 = this.directionalVelocityChannel.data;
                int i7 = i2 + 1;
                fArr2[i7] = fArr2[i7] + TMP_V3.y;
                float[] fArr3 = this.directionalVelocityChannel.data;
                int i8 = i2 + 2;
                fArr3[i8] = fArr3[i8] + TMP_V3.z;
                i6 += this.strengthChannel.strideSize;
                i2 += this.directionalVelocityChannel.strideSize;
                i3 += this.angularChannel.strideSize;
                i5 += this.lifeChannel.strideSize;
                i4 += this.positionChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public TangentialAcceleration copy() {
            return new TangentialAcceleration(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/influencers/DynamicsModifier$BrownianAcceleration.class
      classes8.dex
     */
    public static class BrownianAcceleration extends Strength {
        ParallelArray.FloatChannel accelerationChannel;

        public BrownianAcceleration() {
        }

        public BrownianAcceleration(BrownianAcceleration brownianAcceleration) {
            super(brownianAcceleration);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier.Strength, com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier, com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void allocateChannels() {
            super.allocateChannels();
            this.accelerationChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Acceleration);
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public void update() {
            int i = this.controller.particles.size;
            int i2 = 2;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i3 < i) {
                TMP_V3.set(MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f)).nor().scl(this.strengthChannel.data[i4] + (this.strengthChannel.data[i4 + 1] * this.strengthValue.getScale(this.lifeChannel.data[i2])));
                float[] fArr = this.accelerationChannel.data;
                fArr[i5] = fArr[i5] + TMP_V3.x;
                float[] fArr2 = this.accelerationChannel.data;
                int i6 = i5 + 1;
                fArr2[i6] = fArr2[i6] + TMP_V3.y;
                float[] fArr3 = this.accelerationChannel.data;
                int i7 = i5 + 2;
                fArr3[i7] = fArr3[i7] + TMP_V3.z;
                i3++;
                i4 += this.strengthChannel.strideSize;
                i5 += this.accelerationChannel.strideSize;
                i2 += this.lifeChannel.strideSize;
            }
        }

        @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
        public BrownianAcceleration copy() {
            return new BrownianAcceleration(this);
        }
    }

    public DynamicsModifier() {
        this.isGlobal = false;
    }

    public DynamicsModifier(DynamicsModifier dynamicsModifier) {
        this.isGlobal = false;
        this.isGlobal = dynamicsModifier.isGlobal;
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent
    public void allocateChannels() {
        this.lifeChannel = (ParallelArray.FloatChannel) this.controller.particles.addChannel(ParticleChannels.Life);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        super.write(json);
        json.writeValue("isGlobal", Boolean.valueOf(this.isGlobal));
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent, com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        super.read(json, jsonValue);
        this.isGlobal = ((Boolean) json.readValue("isGlobal", Boolean.TYPE, jsonValue)).booleanValue();
    }
}
