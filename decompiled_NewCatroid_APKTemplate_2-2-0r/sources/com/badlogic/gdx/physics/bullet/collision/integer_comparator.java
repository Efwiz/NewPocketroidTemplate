package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class integer_comparator extends BulletBase {
    private long swigCPtr;

    protected integer_comparator(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public integer_comparator(long j, boolean z) {
        this("integer_comparator", j, z);
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

    public static long getCPtr(integer_comparator integer_comparatorVar) {
        if (integer_comparatorVar == null) {
            return 0L;
        }
        return integer_comparatorVar.swigCPtr;
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
                CollisionJNI.delete_integer_comparator(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public integer_comparator() {
        this(CollisionJNI.new_integer_comparator(), true);
    }
}
