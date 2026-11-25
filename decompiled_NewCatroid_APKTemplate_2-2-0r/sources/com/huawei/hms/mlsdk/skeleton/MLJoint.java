package com.huawei.hms.mlsdk.skeleton;

import com.huawei.hms.common.util.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLJoint {
    public static final int TYPE_HEAD_TOP = 113;
    public static final int TYPE_LEFT_ANKLE = 112;
    public static final int TYPE_LEFT_ELBOW = 105;
    public static final int TYPE_LEFT_HIP = 110;
    public static final int TYPE_LEFT_KNEE = 111;
    public static final int TYPE_LEFT_SHOULDER = 104;
    public static final int TYPE_LEFT_WRIST = 106;
    public static final int TYPE_NECK = 114;
    public static final int TYPE_RIGHT_ANKLE = 109;
    public static final int TYPE_RIGHT_ELBOW = 102;
    public static final int TYPE_RIGHT_HIP = 107;
    public static final int TYPE_RIGHT_KNEE = 108;
    public static final int TYPE_RIGHT_SHOULDER = 101;
    public static final int TYPE_RIGHT_WRIST = 103;
    public final float a;
    public final float b;
    public final int c;
    public final float d;

    public MLJoint(float f, float f2, int i, float f3) {
        this.a = f;
        this.b = f2;
        this.c = i;
        this.d = f3;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLJoint)) {
            return false;
        }
        MLJoint mLJoint = (MLJoint) obj;
        return Objects.equal(Float.valueOf(this.a), Float.valueOf(mLJoint.getPointX())) && Objects.equal(Float.valueOf(this.b), Float.valueOf(mLJoint.getPointY())) && Objects.equal(Integer.valueOf(this.c), Integer.valueOf(mLJoint.getType())) && Objects.equal(Float.valueOf(this.d), Float.valueOf(mLJoint.getScore()));
    }

    public float getPointX() {
        return this.a;
    }

    public float getPointY() {
        return this.b;
    }

    public float getScore() {
        return this.d;
    }

    public int getType() {
        return this.c;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.a), Float.valueOf(this.b), Integer.valueOf(this.c), Float.valueOf(this.d));
    }
}
