package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectWrapper;

/* JADX INFO: loaded from: classes8.dex */
public class btDefaultSoftBodySolver extends btSoftBodySolver {
    private long swigCPtr;

    protected btDefaultSoftBodySolver(String str, long j, boolean z) {
        super(str, SoftbodyJNI.btDefaultSoftBodySolver_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btDefaultSoftBodySolver(long j, boolean z) {
        this("btDefaultSoftBodySolver", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBodySolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(SoftbodyJNI.btDefaultSoftBodySolver_SWIGUpcast(j), z);
    }

    public static long getCPtr(btDefaultSoftBodySolver btdefaultsoftbodysolver) {
        if (btdefaultsoftbodysolver == null) {
            return 0L;
        }
        return btdefaultsoftbodysolver.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBodySolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBodySolver, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                SoftbodyJNI.delete_btDefaultSoftBodySolver(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btDefaultSoftBodySolver() {
        this(SoftbodyJNI.new_btDefaultSoftBodySolver(), true);
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBodySolver
    public void optimize(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t, boolean z) {
        SoftbodyJNI.btDefaultSoftBodySolver_optimize__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t), z);
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBodySolver
    public void optimize(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t) {
        SoftbodyJNI.btDefaultSoftBodySolver_optimize__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t));
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBodySolver
    public void copyBackToSoftBodies(boolean z) {
        SoftbodyJNI.btDefaultSoftBodySolver_copyBackToSoftBodies__SWIG_0(this.swigCPtr, this, z);
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBodySolver
    public void copyBackToSoftBodies() {
        SoftbodyJNI.btDefaultSoftBodySolver_copyBackToSoftBodies__SWIG_1(this.swigCPtr, this);
    }

    public void copySoftBodyToVertexBuffer(btSoftBody btsoftbody, btVertexBufferDescriptor btvertexbufferdescriptor) {
        SoftbodyJNI.btDefaultSoftBodySolver_copySoftBodyToVertexBuffer(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, btVertexBufferDescriptor.getCPtr(btvertexbufferdescriptor), btvertexbufferdescriptor);
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBodySolver
    public void processCollision(btSoftBody btsoftbody, btCollisionObjectWrapper btcollisionobjectwrapper) {
        SoftbodyJNI.btDefaultSoftBodySolver_processCollision__SWIG_0(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper);
    }

    @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBodySolver
    public void processCollision(btSoftBody btsoftbody, btSoftBody btsoftbody2) {
        SoftbodyJNI.btDefaultSoftBodySolver_processCollision__SWIG_1(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, btSoftBody.getCPtr(btsoftbody2), btsoftbody2);
    }
}
