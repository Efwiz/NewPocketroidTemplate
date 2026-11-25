package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.collision.btConcaveShape;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftBodyCollisionShape extends btConcaveShape {
    private long swigCPtr;

    protected btSoftBodyCollisionShape(String str, long j, boolean z) {
        super(str, SoftbodyJNI.btSoftBodyCollisionShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSoftBodyCollisionShape(long j, boolean z) {
        this("btSoftBodyCollisionShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(SoftbodyJNI.btSoftBodyCollisionShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSoftBodyCollisionShape btsoftbodycollisionshape) {
        if (btsoftbodycollisionshape == null) {
            return 0L;
        }
        return btsoftbodycollisionshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                SoftbodyJNI.delete_btSoftBodyCollisionShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBody(btSoftBody btsoftbody) {
        SoftbodyJNI.btSoftBodyCollisionShape_body_set(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody);
    }

    public btSoftBody getBody() {
        long jBtSoftBodyCollisionShape_body_get = SoftbodyJNI.btSoftBodyCollisionShape_body_get(this.swigCPtr, this);
        if (jBtSoftBodyCollisionShape_body_get == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBodyCollisionShape_body_get, false);
    }

    public btSoftBodyCollisionShape(btSoftBody btsoftbody) {
        this(SoftbodyJNI.new_btSoftBodyCollisionShape(btSoftBody.getCPtr(btsoftbody), btsoftbody), true);
    }
}
