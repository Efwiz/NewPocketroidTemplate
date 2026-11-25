package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;

/* JADX INFO: loaded from: classes8.dex */
public class btBoxBoxDetector extends btDiscreteCollisionDetectorInterface {
    private long swigCPtr;

    protected btBoxBoxDetector(String str, long j, boolean z) {
        super(str, CollisionJNI.btBoxBoxDetector_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btBoxBoxDetector(long j, boolean z) {
        this("btBoxBoxDetector", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btBoxBoxDetector_SWIGUpcast(j), z);
    }

    public static long getCPtr(btBoxBoxDetector btboxboxdetector) {
        if (btboxboxdetector == null) {
            return 0L;
        }
        return btboxboxdetector.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btBoxBoxDetector(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBox1(btBoxShape btboxshape) {
        CollisionJNI.btBoxBoxDetector_box1_set(this.swigCPtr, this, btBoxShape.getCPtr(btboxshape), btboxshape);
    }

    public btBoxShape getBox1() {
        long jBtBoxBoxDetector_box1_get = CollisionJNI.btBoxBoxDetector_box1_get(this.swigCPtr, this);
        if (jBtBoxBoxDetector_box1_get == 0) {
            return null;
        }
        return new btBoxShape(jBtBoxBoxDetector_box1_get, false);
    }

    public void setBox2(btBoxShape btboxshape) {
        CollisionJNI.btBoxBoxDetector_box2_set(this.swigCPtr, this, btBoxShape.getCPtr(btboxshape), btboxshape);
    }

    public btBoxShape getBox2() {
        long jBtBoxBoxDetector_box2_get = CollisionJNI.btBoxBoxDetector_box2_get(this.swigCPtr, this);
        if (jBtBoxBoxDetector_box2_get == 0) {
            return null;
        }
        return new btBoxShape(jBtBoxBoxDetector_box2_get, false);
    }

    public btBoxBoxDetector(btBoxShape btboxshape, btBoxShape btboxshape2) {
        this(CollisionJNI.new_btBoxBoxDetector(btBoxShape.getCPtr(btboxshape), btboxshape, btBoxShape.getCPtr(btboxshape2), btboxshape2), true);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface
    public void getClosestPoints(btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, btDiscreteCollisionDetectorInterface.Result result, btIDebugDraw btidebugdraw, boolean z) {
        CollisionJNI.btBoxBoxDetector_getClosestPoints__SWIG_0(this.swigCPtr, this, btDiscreteCollisionDetectorInterface.ClosestPointInput.getCPtr(closestPointInput), closestPointInput, btDiscreteCollisionDetectorInterface.Result.getCPtr(result), result, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, z);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface
    public void getClosestPoints(btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, btDiscreteCollisionDetectorInterface.Result result, btIDebugDraw btidebugdraw) {
        CollisionJNI.btBoxBoxDetector_getClosestPoints__SWIG_1(this.swigCPtr, this, btDiscreteCollisionDetectorInterface.ClosestPointInput.getCPtr(closestPointInput), closestPointInput, btDiscreteCollisionDetectorInterface.Result.getCPtr(result), result, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }
}
