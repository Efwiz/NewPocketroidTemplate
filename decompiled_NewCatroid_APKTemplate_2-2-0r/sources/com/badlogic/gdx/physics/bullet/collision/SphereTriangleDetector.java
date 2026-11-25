package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;

/* JADX INFO: loaded from: classes8.dex */
public class SphereTriangleDetector extends btDiscreteCollisionDetectorInterface {
    private long swigCPtr;

    protected SphereTriangleDetector(String str, long j, boolean z) {
        super(str, CollisionJNI.SphereTriangleDetector_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public SphereTriangleDetector(long j, boolean z) {
        this("SphereTriangleDetector", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.SphereTriangleDetector_SWIGUpcast(j), z);
    }

    public static long getCPtr(SphereTriangleDetector sphereTriangleDetector) {
        if (sphereTriangleDetector == null) {
            return 0L;
        }
        return sphereTriangleDetector.swigCPtr;
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
                CollisionJNI.delete_SphereTriangleDetector(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface
    public void getClosestPoints(btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, btDiscreteCollisionDetectorInterface.Result result, btIDebugDraw btidebugdraw, boolean z) {
        CollisionJNI.SphereTriangleDetector_getClosestPoints__SWIG_0(this.swigCPtr, this, btDiscreteCollisionDetectorInterface.ClosestPointInput.getCPtr(closestPointInput), closestPointInput, btDiscreteCollisionDetectorInterface.Result.getCPtr(result), result, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, z);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDiscreteCollisionDetectorInterface
    public void getClosestPoints(btDiscreteCollisionDetectorInterface.ClosestPointInput closestPointInput, btDiscreteCollisionDetectorInterface.Result result, btIDebugDraw btidebugdraw) {
        CollisionJNI.SphereTriangleDetector_getClosestPoints__SWIG_1(this.swigCPtr, this, btDiscreteCollisionDetectorInterface.ClosestPointInput.getCPtr(closestPointInput), closestPointInput, btDiscreteCollisionDetectorInterface.Result.getCPtr(result), result, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public SphereTriangleDetector(btSphereShape btsphereshape, btTriangleShape bttriangleshape, float f) {
        this(CollisionJNI.new_SphereTriangleDetector(btSphereShape.getCPtr(btsphereshape), btsphereshape, btTriangleShape.getCPtr(bttriangleshape), bttriangleshape, f), true);
    }

    public boolean collide(Vector3 vector3, Vector3 vector4, Vector3 vector5, SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_float sWIGTYPE_p_float2, float f) {
        return CollisionJNI.SphereTriangleDetector_collide(this.swigCPtr, this, vector3, vector4, vector5, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float2), f);
    }
}
