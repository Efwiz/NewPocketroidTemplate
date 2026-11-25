package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class GdxCollisionObjectBridge extends BulletBase {
    private long swigCPtr;

    protected GdxCollisionObjectBridge(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GdxCollisionObjectBridge(long j, boolean z) {
        this("GdxCollisionObjectBridge", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(j, z);
    }

    public static long getCPtr(GdxCollisionObjectBridge gdxCollisionObjectBridge) {
        if (gdxCollisionObjectBridge == null) {
            return 0L;
        }
        return gdxCollisionObjectBridge.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_GdxCollisionObjectBridge(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setUserValue(int i) {
        CollisionJNI.GdxCollisionObjectBridge_userValue_set(this.swigCPtr, this, i);
    }

    public int getUserValue() {
        return CollisionJNI.GdxCollisionObjectBridge_userValue_get(this.swigCPtr, this);
    }

    public void setContactCallbackFlag(int i) {
        CollisionJNI.GdxCollisionObjectBridge_contactCallbackFlag_set(this.swigCPtr, this, i);
    }

    public int getContactCallbackFlag() {
        return CollisionJNI.GdxCollisionObjectBridge_contactCallbackFlag_get(this.swigCPtr, this);
    }

    public void setContactCallbackFilter(int i) {
        CollisionJNI.GdxCollisionObjectBridge_contactCallbackFilter_set(this.swigCPtr, this, i);
    }

    public int getContactCallbackFilter() {
        return CollisionJNI.GdxCollisionObjectBridge_contactCallbackFilter_get(this.swigCPtr, this);
    }

    public GdxCollisionObjectBridge() {
        this(CollisionJNI.new_GdxCollisionObjectBridge(), true);
    }
}
