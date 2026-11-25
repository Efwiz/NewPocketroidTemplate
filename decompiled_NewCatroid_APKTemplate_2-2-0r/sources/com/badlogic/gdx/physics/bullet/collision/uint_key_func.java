package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class uint_key_func extends BulletBase {
    private long swigCPtr;

    protected uint_key_func(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public uint_key_func(long j, boolean z) {
        this("uint_key_func", j, z);
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

    public static long getCPtr(uint_key_func uint_key_funcVar) {
        if (uint_key_funcVar == null) {
            return 0L;
        }
        return uint_key_funcVar.swigCPtr;
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
                CollisionJNI.delete_uint_key_func(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public uint_key_func() {
        this(CollisionJNI.new_uint_key_func(), true);
    }
}
