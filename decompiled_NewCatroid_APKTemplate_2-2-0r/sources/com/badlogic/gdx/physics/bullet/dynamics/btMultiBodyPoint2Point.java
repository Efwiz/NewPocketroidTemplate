package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyPoint2Point extends btMultiBodyConstraint {
    private long swigCPtr;

    protected btMultiBodyPoint2Point(String str, long j, boolean z) {
        super(str, DynamicsJNI.btMultiBodyPoint2Point_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btMultiBodyPoint2Point(long j, boolean z) {
        this("btMultiBodyPoint2Point", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btMultiBodyPoint2Point_SWIGUpcast(j), z);
    }

    public static long getCPtr(btMultiBodyPoint2Point btmultibodypoint2point) {
        if (btmultibodypoint2point == null) {
            return 0L;
        }
        return btmultibodypoint2point.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btMultiBodyPoint2Point(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint
    public long operatorNew(long j) {
        return DynamicsJNI.btMultiBodyPoint2Point_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint
    public void operatorDelete(long j) {
        DynamicsJNI.btMultiBodyPoint2Point_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btMultiBodyPoint2Point_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btMultiBodyPoint2Point_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint
    public long operatorNewArray(long j) {
        return DynamicsJNI.btMultiBodyPoint2Point_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btMultiBodyPoint2Point_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btMultiBodyPoint2Point_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btMultiBodyConstraint
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btMultiBodyPoint2Point_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btMultiBodyPoint2Point(btMultiBody btmultibody, int i, btRigidBody btrigidbody, Vector3 vector3, Vector3 vector4) {
        this(DynamicsJNI.new_btMultiBodyPoint2Point__SWIG_0(btMultiBody.getCPtr(btmultibody), btmultibody, i, btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3, vector4), true);
    }

    public btMultiBodyPoint2Point(btMultiBody btmultibody, int i, btMultiBody btmultibody2, int i2, Vector3 vector3, Vector3 vector4) {
        this(DynamicsJNI.new_btMultiBodyPoint2Point__SWIG_1(btMultiBody.getCPtr(btmultibody), btmultibody, i, btMultiBody.getCPtr(btmultibody2), btmultibody2, i2, vector3, vector4), true);
    }

    public Vector3 getPivotInB() {
        return DynamicsJNI.btMultiBodyPoint2Point_getPivotInB(this.swigCPtr, this);
    }
}
