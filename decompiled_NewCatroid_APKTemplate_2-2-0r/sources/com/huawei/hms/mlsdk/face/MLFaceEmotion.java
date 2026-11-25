package com.huawei.hms.mlsdk.face;

import com.huawei.hms.mlsdk.internal.client.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLFaceEmotion {
    private float angryProbability;
    private float disgustProbability;
    private float fearProbability;
    private float neutralProbability;
    private float sadProbability;
    private float smilingProbability;
    private float surpriseProbability;

    public MLFaceEmotion(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        this.smilingProbability = f;
        this.neutralProbability = f2;
        this.angryProbability = f3;
        this.disgustProbability = f4;
        this.fearProbability = f5;
        this.sadProbability = f6;
        this.surpriseProbability = f7;
    }

    public float getAngryProbability() {
        return this.angryProbability;
    }

    public float getDisgustProbability() {
        return this.disgustProbability;
    }

    public float getFearProbability() {
        return this.fearProbability;
    }

    public float getNeutralProbability() {
        return this.neutralProbability;
    }

    public float getSadProbability() {
        return this.sadProbability;
    }

    public float getSmilingProbability() {
        return this.smilingProbability;
    }

    public float getSurpriseProbability() {
        return this.surpriseProbability;
    }

    public String toString() {
        return Objects.toStringHelper(this).attribute("smilingProbability", Float.valueOf(this.smilingProbability)).attribute("neutralProbability", Float.valueOf(this.neutralProbability)).attribute("angryProbability", Float.valueOf(this.angryProbability)).attribute("disgustProbability", Float.valueOf(this.disgustProbability)).attribute("fearProbability", Float.valueOf(this.fearProbability)).attribute("sadProbability", Float.valueOf(this.sadProbability)).attribute("surpriseProbability", Float.valueOf(this.surpriseProbability)).toString();
    }
}
