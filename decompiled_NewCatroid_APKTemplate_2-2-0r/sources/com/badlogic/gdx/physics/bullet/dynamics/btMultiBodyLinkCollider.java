package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyLinkCollider extends btCollisionObject {
    private long swigCPtr;

    protected btMultiBodyLinkCollider(String str, long j, boolean z) {
        super(str, DynamicsJNI.btMultiBodyLinkCollider_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMultiBodyLinkCollider(long j, boolean z) {
        this("btMultiBodyLinkCollider", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btMultiBodyLinkCollider_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMultiBodyLinkCollider btmultibodylinkcollider) {
        if (btmultibodylinkcollider == null) {
            return 0L;
        }
        return btmultibodylinkcollider.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btMultiBodyLinkCollider(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setMultiBody(btMultiBody btmultibody) {
        DynamicsJNI.btMultiBodyLinkCollider_multiBody_set(this.swigCPtr, this, btMultiBody.getCPtr(btmultibody), btmultibody);
    }

    public btMultiBody getMultiBody() {
        long jBtMultiBodyLinkCollider_multiBody_get = DynamicsJNI.btMultiBodyLinkCollider_multiBody_get(this.swigCPtr, this);
        if (jBtMultiBodyLinkCollider_multiBody_get == 0) {
            return null;
        }
        return new btMultiBody(jBtMultiBodyLinkCollider_multiBody_get, false);
    }

    public void setLink(int i) {
        DynamicsJNI.btMultiBodyLinkCollider_link_set(this.swigCPtr, this, i);
    }

    public int getLink() {
        return DynamicsJNI.btMultiBodyLinkCollider_link_get(this.swigCPtr, this);
    }

    public btMultiBodyLinkCollider(btMultiBody btmultibody, int i) {
        this(DynamicsJNI.new_btMultiBodyLinkCollider(btMultiBody.getCPtr(btmultibody), btmultibody, i), true);
    }

    public static btMultiBodyLinkCollider upcast(btCollisionObject btcollisionobject) {
        long jBtMultiBodyLinkCollider_upcast = DynamicsJNI.btMultiBodyLinkCollider_upcast(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
        if (jBtMultiBodyLinkCollider_upcast == 0) {
            return null;
        }
        return new btMultiBodyLinkCollider(jBtMultiBodyLinkCollider_upcast, false);
    }

    public static btMultiBodyLinkCollider upcastConstBtCollisionObject(btCollisionObject btcollisionobject) {
        long jBtMultiBodyLinkCollider_upcastConstBtCollisionObject = DynamicsJNI.btMultiBodyLinkCollider_upcastConstBtCollisionObject(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
        if (jBtMultiBodyLinkCollider_upcastConstBtCollisionObject == 0) {
            return null;
        }
        return new btMultiBodyLinkCollider(jBtMultiBodyLinkCollider_upcastConstBtCollisionObject, false);
    }
}
