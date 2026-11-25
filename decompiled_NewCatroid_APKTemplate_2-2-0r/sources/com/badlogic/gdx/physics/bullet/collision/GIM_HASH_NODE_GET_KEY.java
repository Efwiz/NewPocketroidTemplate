package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_HASH_NODE_GET_KEY extends BulletBase {
    private long swigCPtr;

    protected GIM_HASH_NODE_GET_KEY(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GIM_HASH_NODE_GET_KEY(long j, boolean z) {
        this("GIM_HASH_NODE_GET_KEY", j, z);
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

    public static long getCPtr(GIM_HASH_NODE_GET_KEY gim_hash_node_get_key) {
        if (gim_hash_node_get_key == null) {
            return 0L;
        }
        return gim_hash_node_get_key.swigCPtr;
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
                CollisionJNI.delete_GIM_HASH_NODE_GET_KEY(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public GIM_HASH_NODE_GET_KEY() {
        this(CollisionJNI.new_GIM_HASH_NODE_GET_KEY(), true);
    }
}
