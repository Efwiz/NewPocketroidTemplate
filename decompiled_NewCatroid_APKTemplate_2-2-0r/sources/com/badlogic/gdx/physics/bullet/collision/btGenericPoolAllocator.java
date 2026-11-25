package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btGenericPoolAllocator extends BulletBase {
    private long swigCPtr;

    protected btGenericPoolAllocator(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGenericPoolAllocator(long j, boolean z) {
        this("btGenericPoolAllocator", j, z);
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

    public static long getCPtr(btGenericPoolAllocator btgenericpoolallocator) {
        if (btgenericpoolallocator == null) {
            return 0L;
        }
        return btgenericpoolallocator.swigCPtr;
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
                CollisionJNI.delete_btGenericPoolAllocator(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPools(SWIGTYPE_p_p_btGenericMemoryPool sWIGTYPE_p_p_btGenericMemoryPool) {
        CollisionJNI.btGenericPoolAllocator_pools_set(this.swigCPtr, this, SWIGTYPE_p_p_btGenericMemoryPool.getCPtr(sWIGTYPE_p_p_btGenericMemoryPool));
    }

    public SWIGTYPE_p_p_btGenericMemoryPool getPools() {
        long jBtGenericPoolAllocator_pools_get = CollisionJNI.btGenericPoolAllocator_pools_get(this.swigCPtr, this);
        if (jBtGenericPoolAllocator_pools_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_p_btGenericMemoryPool(jBtGenericPoolAllocator_pools_get, false);
    }

    public void setPool_count(long j) {
        CollisionJNI.btGenericPoolAllocator_pool_count_set(this.swigCPtr, this, j);
    }

    public long getPool_count() {
        return CollisionJNI.btGenericPoolAllocator_pool_count_get(this.swigCPtr, this);
    }

    public long get_pool_capacity() {
        return CollisionJNI.btGenericPoolAllocator_get_pool_capacity(this.swigCPtr, this);
    }

    public btGenericPoolAllocator(long j, long j2) {
        this(CollisionJNI.new_btGenericPoolAllocator(j, j2), true);
    }

    public long allocate(long j) {
        return CollisionJNI.btGenericPoolAllocator_allocate(this.swigCPtr, this, j);
    }

    public boolean freeMemory(long j) {
        return CollisionJNI.btGenericPoolAllocator_freeMemory(this.swigCPtr, this, j);
    }
}
