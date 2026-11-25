package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btDbvtProxy extends btBroadphaseProxy {
    private long swigCPtr;

    protected btDbvtProxy(String str, long j, boolean z) {
        super(str, CollisionJNI.btDbvtProxy_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btDbvtProxy(long j, boolean z) {
        this("btDbvtProxy", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btDbvtProxy_SWIGUpcast(j), z);
    }

    public static long getCPtr(btDbvtProxy btdbvtproxy) {
        if (btdbvtproxy == null) {
            return 0L;
        }
        return btdbvtproxy.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btDbvtProxy(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setLeaf(btDbvtNode btdbvtnode) {
        CollisionJNI.btDbvtProxy_leaf_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
    }

    public btDbvtNode getLeaf() {
        return btDbvtNode.internalTemp(CollisionJNI.btDbvtProxy_leaf_get(this.swigCPtr, this), false);
    }

    public void setLinks(SWIGTYPE_p_p_btDbvtProxy sWIGTYPE_p_p_btDbvtProxy) {
        CollisionJNI.btDbvtProxy_links_set(this.swigCPtr, this, SWIGTYPE_p_p_btDbvtProxy.getCPtr(sWIGTYPE_p_p_btDbvtProxy));
    }

    public SWIGTYPE_p_p_btDbvtProxy getLinks() {
        long jBtDbvtProxy_links_get = CollisionJNI.btDbvtProxy_links_get(this.swigCPtr, this);
        if (jBtDbvtProxy_links_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_p_btDbvtProxy(jBtDbvtProxy_links_get, false);
    }

    public void setStage(int i) {
        CollisionJNI.btDbvtProxy_stage_set(this.swigCPtr, this, i);
    }

    public int getStage() {
        return CollisionJNI.btDbvtProxy_stage_get(this.swigCPtr, this);
    }

    public btDbvtProxy(Vector3 vector3, Vector3 vector4, long j, int i, int i2) {
        this(CollisionJNI.new_btDbvtProxy(vector3, vector4, j, i, i2), true);
    }
}
