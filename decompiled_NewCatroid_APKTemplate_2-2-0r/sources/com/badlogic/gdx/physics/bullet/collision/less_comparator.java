package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class less_comparator extends BulletBase {
    private long swigCPtr;

    protected less_comparator(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public less_comparator(long j, boolean z) {
        this("less_comparator", j, z);
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

    public static long getCPtr(less_comparator less_comparatorVar) {
        if (less_comparatorVar == null) {
            return 0L;
        }
        return less_comparatorVar.swigCPtr;
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
                CollisionJNI.delete_less_comparator(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public less_comparator() {
        this(CollisionJNI.new_less_comparator(), true);
    }
}
