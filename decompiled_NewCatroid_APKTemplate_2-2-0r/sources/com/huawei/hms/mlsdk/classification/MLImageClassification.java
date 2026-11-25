package com.huawei.hms.mlsdk.classification;

import com.huawei.hms.common.util.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLImageClassification {
    private final String classificationIdentity;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f188name;
    private final float possibility;

    public MLImageClassification(String str, float f, String str2) {
        this.f188name = str;
        this.classificationIdentity = str2;
        if (Float.compare(f, 0.0f) < 0) {
            this.possibility = 0.0f;
        } else if (Float.compare(f, 1.0f) > 0) {
            this.possibility = 1.0f;
        } else {
            this.possibility = f;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLImageClassification)) {
            return false;
        }
        MLImageClassification mLImageClassification = (MLImageClassification) obj;
        return Objects.equal(this.classificationIdentity, mLImageClassification.getClassificationIdentity()) && Objects.equal(this.f188name, mLImageClassification.getName()) && Float.compare(this.possibility, mLImageClassification.getPossibility()) == 0;
    }

    public String getClassificationIdentity() {
        return this.classificationIdentity;
    }

    public String getName() {
        return this.f188name;
    }

    public float getPossibility() {
        return this.possibility;
    }

    public int hashCode() {
        return Objects.hashCode(this.classificationIdentity, this.f188name, Float.valueOf(this.possibility));
    }
}
