package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btGenericMemoryPool extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btGenericMemoryPool(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGenericMemoryPool(long j, boolean z) {
        this("btGenericMemoryPool", j, z);
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

    public static long getCPtr(btGenericMemoryPool btgenericmemorypool) {
        if (btgenericmemorypool == null) {
            return 0L;
        }
        return btgenericmemorypool.swigCPtr;
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
                CollisionJNI.delete_btGenericMemoryPool(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setPool(ByteBuffer byteBuffer) {
        CollisionJNI.btGenericMemoryPool_pool_set(this.swigCPtr, this, byteBuffer);
    }

    public ByteBuffer getPool() {
        return CollisionJNI.btGenericMemoryPool_pool_get(this.swigCPtr, this);
    }

    public void setFree_nodes(SWIGTYPE_p_size_t sWIGTYPE_p_size_t) {
        CollisionJNI.btGenericMemoryPool_free_nodes_set(this.swigCPtr, this, SWIGTYPE_p_size_t.getCPtr(sWIGTYPE_p_size_t));
    }

    public SWIGTYPE_p_size_t getFree_nodes() {
        long jBtGenericMemoryPool_free_nodes_get = CollisionJNI.btGenericMemoryPool_free_nodes_get(this.swigCPtr, this);
        if (jBtGenericMemoryPool_free_nodes_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_size_t(jBtGenericMemoryPool_free_nodes_get, false);
    }

    public void setAllocated_sizes(SWIGTYPE_p_size_t sWIGTYPE_p_size_t) {
        CollisionJNI.btGenericMemoryPool_allocated_sizes_set(this.swigCPtr, this, SWIGTYPE_p_size_t.getCPtr(sWIGTYPE_p_size_t));
    }

    public SWIGTYPE_p_size_t getAllocated_sizes() {
        long jBtGenericMemoryPool_allocated_sizes_get = CollisionJNI.btGenericMemoryPool_allocated_sizes_get(this.swigCPtr, this);
        if (jBtGenericMemoryPool_allocated_sizes_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_size_t(jBtGenericMemoryPool_allocated_sizes_get, false);
    }

    public void setAllocated_count(long j) {
        CollisionJNI.btGenericMemoryPool_allocated_count_set(this.swigCPtr, this, j);
    }

    public long getAllocated_count() {
        return CollisionJNI.btGenericMemoryPool_allocated_count_get(this.swigCPtr, this);
    }

    public void setFree_nodes_count(long j) {
        CollisionJNI.btGenericMemoryPool_free_nodes_count_set(this.swigCPtr, this, j);
    }

    public long getFree_nodes_count() {
        return CollisionJNI.btGenericMemoryPool_free_nodes_count_get(this.swigCPtr, this);
    }

    public void init_pool(long j, long j2) {
        CollisionJNI.btGenericMemoryPool_init_pool(this.swigCPtr, this, j, j2);
    }

    public void end_pool() {
        CollisionJNI.btGenericMemoryPool_end_pool(this.swigCPtr, this);
    }

    public btGenericMemoryPool(long j, long j2) {
        this(CollisionJNI.new_btGenericMemoryPool(j, j2), true);
    }

    public long get_pool_capacity() {
        return CollisionJNI.btGenericMemoryPool_get_pool_capacity(this.swigCPtr, this);
    }

    public long gem_element_size() {
        return CollisionJNI.btGenericMemoryPool_gem_element_size(this.swigCPtr, this);
    }

    public long get_max_element_count() {
        return CollisionJNI.btGenericMemoryPool_get_max_element_count(this.swigCPtr, this);
    }

    public long get_allocated_count() {
        return CollisionJNI.btGenericMemoryPool_get_allocated_count(this.swigCPtr, this);
    }

    public long get_free_positions_count() {
        return CollisionJNI.btGenericMemoryPool_get_free_positions_count(this.swigCPtr, this);
    }

    public long get_element_data(long j) {
        return CollisionJNI.btGenericMemoryPool_get_element_data(this.swigCPtr, this, j);
    }

    public long allocate(long j) {
        return CollisionJNI.btGenericMemoryPool_allocate(this.swigCPtr, this, j);
    }

    public boolean freeMemory(long j) {
        return CollisionJNI.btGenericMemoryPool_freeMemory(this.swigCPtr, this, j);
    }
}
