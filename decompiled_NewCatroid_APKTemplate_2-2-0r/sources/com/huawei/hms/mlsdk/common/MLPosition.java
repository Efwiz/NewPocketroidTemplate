package com.huawei.hms.mlsdk.common;

import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public class MLPosition {
    public final Float a;
    public final Float b;
    public final Float c;

    public MLPosition(Float f, Float f2, Float f3) {
        this.a = f;
        this.b = f2;
        this.c = f3;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MLPosition) {
            return toString().equals(((MLPosition) obj).toString());
        }
        return false;
    }

    public final Float getX() {
        return this.a;
    }

    public final Float getY() {
        return this.b;
    }

    public final Float getZ() {
        return this.c;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.a, this.b, this.c});
    }

    public final String toString() {
        return "x=" + this.a + ",y=" + this.b + ",z=" + this.c;
    }

    public MLPosition(Float f, Float f2) {
        this(f, f2, null);
    }
}
