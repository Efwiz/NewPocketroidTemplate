package com.huawei.hms.mlsdk.face;

import com.huawei.hms.mlsdk.internal.client.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLFaceFeature {
    private static final float DEFAULT_MAX_PROBABILITY = 1.0f;
    private static final float DEFAULT_PROBABILITY = -1.0f;
    private int age;
    private float hatProbability;
    private float leftEyeOpenProbability;
    private float moustacheProbability;
    private float rightEyeOpenProbability;
    private float sexProbability;
    private float sunGlassProbability;

    public MLFaceFeature(float f, float f2, float f3, float f4, float f5, float f6, int i) {
        this.leftEyeOpenProbability = f;
        this.rightEyeOpenProbability = f2;
        this.sunGlassProbability = f3;
        this.sexProbability = f4;
        this.hatProbability = f5;
        this.moustacheProbability = f6;
        this.age = i;
    }

    public int getAge() {
        return this.age;
    }

    public float getHatProbability() {
        float f = this.hatProbability;
        return f < 0.0f ? f : 1.0f - f;
    }

    public float getLeftEyeOpenProbability() {
        return this.leftEyeOpenProbability;
    }

    public float getMoustacheProbability() {
        float f = this.moustacheProbability;
        if (f < 0.0f) {
            return -1.0f;
        }
        return 1.0f - f;
    }

    public float getRightEyeOpenProbability() {
        return this.rightEyeOpenProbability;
    }

    public float getSexProbability() {
        return this.sexProbability;
    }

    public float getSunGlassProbability() {
        float f = this.sunGlassProbability;
        if (f < 0.0f) {
            return -1.0f;
        }
        return f;
    }

    public String toString() {
        return Objects.toStringHelper(this).attribute("leftEyeOpenProbability", Float.valueOf(this.leftEyeOpenProbability)).attribute("rightEyeOpenProbability", Float.valueOf(this.rightEyeOpenProbability)).attribute("sunGlassProbability", Float.valueOf(getSunGlassProbability())).attribute("sexProbability", Float.valueOf(this.sexProbability)).attribute("hatProbability", Float.valueOf(this.hatProbability)).attribute("moustacheProbability", Float.valueOf(getMoustacheProbability())).attribute("age", Integer.valueOf(this.age)).toString();
    }
}
