package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class GIM_HASH_NODE_CMP_KEY_MACRO extends BulletBase {
    private long swigCPtr;

    protected GIM_HASH_NODE_CMP_KEY_MACRO(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public GIM_HASH_NODE_CMP_KEY_MACRO(long j, boolean z) {
        this("GIM_HASH_NODE_CMP_KEY_MACRO", j, z);
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

    public static long getCPtr(GIM_HASH_NODE_CMP_KEY_MACRO gim_hash_node_cmp_key_macro) {
        if (gim_hash_node_cmp_key_macro == null) {
            return 0L;
        }
        return gim_hash_node_cmp_key_macro.swigCPtr;
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
                CollisionJNI.delete_GIM_HASH_NODE_CMP_KEY_MACRO(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public GIM_HASH_NODE_CMP_KEY_MACRO() {
        this(CollisionJNI.new_GIM_HASH_NODE_CMP_KEY_MACRO(), true);
    }
}
