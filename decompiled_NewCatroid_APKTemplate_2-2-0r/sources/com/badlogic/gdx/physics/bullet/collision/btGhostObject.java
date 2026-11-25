package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btGhostObject extends btCollisionObject {
    private long swigCPtr;

    protected btGhostObject(String str, long j, boolean z) {
        super(str, CollisionJNI.btGhostObject_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGhostObject(long j, boolean z) {
        this("btGhostObject", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btGhostObject_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGhostObject btghostobject) {
        if (btghostobject == null) {
            return 0L;
        }
        return btghostobject.swigCPtr;
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
                CollisionJNI.delete_btGhostObject(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGhostObject() {
        this(CollisionJNI.new_btGhostObject(), true);
    }

    public void convexSweepTest(btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, ConvexResultCallback convexResultCallback, float f) {
        CollisionJNI.btGhostObject_convexSweepTest__SWIG_0(this.swigCPtr, this, btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, matrix5, ConvexResultCallback.getCPtr(convexResultCallback), convexResultCallback, f);
    }

    public void convexSweepTest(btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, ConvexResultCallback convexResultCallback) {
        CollisionJNI.btGhostObject_convexSweepTest__SWIG_1(this.swigCPtr, this, btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, matrix5, ConvexResultCallback.getCPtr(convexResultCallback), convexResultCallback);
    }

    public void rayTest(Vector3 vector3, Vector3 vector4, RayResultCallback rayResultCallback) {
        CollisionJNI.btGhostObject_rayTest(this.swigCPtr, this, vector3, vector4, RayResultCallback.getCPtr(rayResultCallback), rayResultCallback);
    }

    public void addOverlappingObjectInternal(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2) {
        CollisionJNI.btGhostObject_addOverlappingObjectInternal__SWIG_0(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2);
    }

    public void addOverlappingObjectInternal(btBroadphaseProxy btbroadphaseproxy) {
        CollisionJNI.btGhostObject_addOverlappingObjectInternal__SWIG_1(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
    }

    public void removeOverlappingObjectInternal(btBroadphaseProxy btbroadphaseproxy, btDispatcher btdispatcher, btBroadphaseProxy btbroadphaseproxy2) {
        CollisionJNI.btGhostObject_removeOverlappingObjectInternal__SWIG_0(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btDispatcher.getCPtr(btdispatcher), btdispatcher, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2);
    }

    public void removeOverlappingObjectInternal(btBroadphaseProxy btbroadphaseproxy, btDispatcher btdispatcher) {
        CollisionJNI.btGhostObject_removeOverlappingObjectInternal__SWIG_1(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public int getNumOverlappingObjects() {
        return CollisionJNI.btGhostObject_getNumOverlappingObjects(this.swigCPtr, this);
    }

    public btCollisionObject getOverlappingObject(int i) {
        return btCollisionObject.getInstance(CollisionJNI.btGhostObject_getOverlappingObject(this.swigCPtr, this, i), false);
    }

    public btCollisionObject getOverlappingObjectConst(int i) {
        return btCollisionObject.getInstance(CollisionJNI.btGhostObject_getOverlappingObjectConst(this.swigCPtr, this, i), false);
    }

    public btCollisionObjectArray getOverlappingPairs() {
        return new btCollisionObjectArray(CollisionJNI.btGhostObject_getOverlappingPairs(this.swigCPtr, this), false);
    }

    public btCollisionObjectArray getOverlappingPairsConst() {
        return new btCollisionObjectArray(CollisionJNI.btGhostObject_getOverlappingPairsConst(this.swigCPtr, this), true);
    }

    public static btGhostObject upcastConstBtCollisionObject(btCollisionObject btcollisionobject) {
        long jBtGhostObject_upcastConstBtCollisionObject = CollisionJNI.btGhostObject_upcastConstBtCollisionObject(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
        if (jBtGhostObject_upcastConstBtCollisionObject == 0) {
            return null;
        }
        return new btGhostObject(jBtGhostObject_upcastConstBtCollisionObject, false);
    }

    public static btGhostObject upcast(btCollisionObject btcollisionobject) {
        long jBtGhostObject_upcast = CollisionJNI.btGhostObject_upcast(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
        if (jBtGhostObject_upcast == 0) {
            return null;
        }
        return new btGhostObject(jBtGhostObject_upcast, false);
    }
}
