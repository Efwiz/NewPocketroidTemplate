package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.utils.Disposable;

/* JADX INFO: loaded from: classes8.dex */
public abstract class Shape implements Disposable {
    protected long addr;

    public enum Type {
        Circle,
        Edge,
        Polygon,
        Chain
    }

    private native void jniDispose(long j);

    private native int jniGetChildCount(long j);

    private native float jniGetRadius(long j);

    protected static native int jniGetType(long j);

    private native void jniSetRadius(long j, float f);

    public abstract Type getType();

    public float getRadius() {
        return jniGetRadius(this.addr);
    }

    public void setRadius(float f) {
        jniSetRadius(this.addr, f);
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        jniDispose(this.addr);
    }

    public int getChildCount() {
        return jniGetChildCount(this.addr);
    }
}
