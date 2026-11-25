package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.physics.bullet.linearmath.btSerializer;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionWorld extends BulletBase {
    private long swigCPtr;

    protected btCollisionWorld(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionWorld(long j, boolean z) {
        this("btCollisionWorld", j, z);
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

    public static long getCPtr(btCollisionWorld btcollisionworld) {
        if (btcollisionworld == null) {
            return 0L;
        }
        return btcollisionworld.swigCPtr;
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
                CollisionJNI.delete_btCollisionWorld(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btCollisionWorld(btDispatcher btdispatcher, btBroadphaseInterface btbroadphaseinterface, btCollisionConfiguration btcollisionconfiguration) {
        this(CollisionJNI.new_btCollisionWorld(btDispatcher.getCPtr(btdispatcher), btdispatcher, btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface, btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration), true);
    }

    public void setBroadphase(btBroadphaseInterface btbroadphaseinterface) {
        CollisionJNI.btCollisionWorld_setBroadphase(this.swigCPtr, this, btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface);
    }

    public btBroadphaseInterface getBroadphaseConst() {
        long jBtCollisionWorld_getBroadphaseConst = CollisionJNI.btCollisionWorld_getBroadphaseConst(this.swigCPtr, this);
        if (jBtCollisionWorld_getBroadphaseConst == 0) {
            return null;
        }
        return new btBroadphaseInterface(jBtCollisionWorld_getBroadphaseConst, false);
    }

    public btBroadphaseInterface getBroadphase() {
        long jBtCollisionWorld_getBroadphase = CollisionJNI.btCollisionWorld_getBroadphase(this.swigCPtr, this);
        if (jBtCollisionWorld_getBroadphase == 0) {
            return null;
        }
        return new btBroadphaseInterface(jBtCollisionWorld_getBroadphase, false);
    }

    public btOverlappingPairCache getPairCache() {
        long jBtCollisionWorld_getPairCache = CollisionJNI.btCollisionWorld_getPairCache(this.swigCPtr, this);
        if (jBtCollisionWorld_getPairCache == 0) {
            return null;
        }
        return new btOverlappingPairCache(jBtCollisionWorld_getPairCache, false);
    }

    public btDispatcher getDispatcher() {
        long jBtCollisionWorld_getDispatcher = CollisionJNI.btCollisionWorld_getDispatcher(this.swigCPtr, this);
        if (jBtCollisionWorld_getDispatcher == 0) {
            return null;
        }
        return new btDispatcher(jBtCollisionWorld_getDispatcher, false);
    }

    public btDispatcher getDispatcherConst() {
        long jBtCollisionWorld_getDispatcherConst = CollisionJNI.btCollisionWorld_getDispatcherConst(this.swigCPtr, this);
        if (jBtCollisionWorld_getDispatcherConst == 0) {
            return null;
        }
        return new btDispatcher(jBtCollisionWorld_getDispatcherConst, false);
    }

    public void updateSingleAabb(btCollisionObject btcollisionobject) {
        CollisionJNI.btCollisionWorld_updateSingleAabb(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public void updateAabbs() {
        CollisionJNI.btCollisionWorld_updateAabbs(this.swigCPtr, this);
    }

    public void computeOverlappingPairs() {
        CollisionJNI.btCollisionWorld_computeOverlappingPairs(this.swigCPtr, this);
    }

    public void setDebugDrawer(btIDebugDraw btidebugdraw) {
        CollisionJNI.btCollisionWorld_setDebugDrawer(this.swigCPtr, this, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }

    public btIDebugDraw getDebugDrawer() {
        long jBtCollisionWorld_getDebugDrawer = CollisionJNI.btCollisionWorld_getDebugDrawer(this.swigCPtr, this);
        if (jBtCollisionWorld_getDebugDrawer == 0) {
            return null;
        }
        return new btIDebugDraw(jBtCollisionWorld_getDebugDrawer, false);
    }

    public void debugDrawWorld() {
        CollisionJNI.btCollisionWorld_debugDrawWorld(this.swigCPtr, this);
    }

    public void debugDrawObject(Matrix4 matrix4, btCollisionShape btcollisionshape, Vector3 vector3) {
        CollisionJNI.btCollisionWorld_debugDrawObject(this.swigCPtr, this, matrix4, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, vector3);
    }

    public int getNumCollisionObjects() {
        return CollisionJNI.btCollisionWorld_getNumCollisionObjects(this.swigCPtr, this);
    }

    public void rayTest(Vector3 vector3, Vector3 vector4, RayResultCallback rayResultCallback) {
        CollisionJNI.btCollisionWorld_rayTest(this.swigCPtr, this, vector3, vector4, RayResultCallback.getCPtr(rayResultCallback), rayResultCallback);
    }

    public void convexSweepTest(btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, ConvexResultCallback convexResultCallback, float f) {
        CollisionJNI.btCollisionWorld_convexSweepTest__SWIG_0(this.swigCPtr, this, btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, matrix5, ConvexResultCallback.getCPtr(convexResultCallback), convexResultCallback, f);
    }

    public void convexSweepTest(btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, ConvexResultCallback convexResultCallback) {
        CollisionJNI.btCollisionWorld_convexSweepTest__SWIG_1(this.swigCPtr, this, btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, matrix5, ConvexResultCallback.getCPtr(convexResultCallback), convexResultCallback);
    }

    public void contactTest(btCollisionObject btcollisionobject, ContactResultCallback contactResultCallback) {
        CollisionJNI.btCollisionWorld_contactTest(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, ContactResultCallback.getCPtr(contactResultCallback), contactResultCallback);
    }

    public void contactPairTest(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2, ContactResultCallback contactResultCallback) {
        CollisionJNI.btCollisionWorld_contactPairTest(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, ContactResultCallback.getCPtr(contactResultCallback), contactResultCallback);
    }

    public static void rayTestSingle(Matrix4 matrix4, Matrix4 matrix5, btCollisionObject btcollisionobject, btCollisionShape btcollisionshape, Matrix4 matrix6, RayResultCallback rayResultCallback) {
        CollisionJNI.btCollisionWorld_rayTestSingle(matrix4, matrix5, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, matrix6, RayResultCallback.getCPtr(rayResultCallback), rayResultCallback);
    }

    public static void rayTestSingleInternal(Matrix4 matrix4, Matrix4 matrix5, btCollisionObjectWrapper btcollisionobjectwrapper, RayResultCallback rayResultCallback) {
        CollisionJNI.btCollisionWorld_rayTestSingleInternal(matrix4, matrix5, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, RayResultCallback.getCPtr(rayResultCallback), rayResultCallback);
    }

    public static void objectQuerySingle(btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, btCollisionObject btcollisionobject, btCollisionShape btcollisionshape, Matrix4 matrix6, ConvexResultCallback convexResultCallback, float f) {
        CollisionJNI.btCollisionWorld_objectQuerySingle(btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, matrix5, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, matrix6, ConvexResultCallback.getCPtr(convexResultCallback), convexResultCallback, f);
    }

    public static void objectQuerySingleInternal(btConvexShape btconvexshape, Matrix4 matrix4, Matrix4 matrix5, btCollisionObjectWrapper btcollisionobjectwrapper, ConvexResultCallback convexResultCallback, float f) {
        CollisionJNI.btCollisionWorld_objectQuerySingleInternal(btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, matrix5, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, ConvexResultCallback.getCPtr(convexResultCallback), convexResultCallback, f);
    }

    public void addCollisionObject(btCollisionObject btcollisionobject, int i, int i2) {
        CollisionJNI.btCollisionWorld_addCollisionObject__SWIG_0(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i, i2);
    }

    public void addCollisionObject(btCollisionObject btcollisionobject, int i) {
        CollisionJNI.btCollisionWorld_addCollisionObject__SWIG_1(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, i);
    }

    public void addCollisionObject(btCollisionObject btcollisionobject) {
        CollisionJNI.btCollisionWorld_addCollisionObject__SWIG_2(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public btCollisionObjectArray getCollisionObjectArray() {
        return new btCollisionObjectArray(CollisionJNI.btCollisionWorld_getCollisionObjectArray(this.swigCPtr, this), false);
    }

    public btCollisionObjectArray getCollisionObjectArrayConst() {
        return new btCollisionObjectArray(CollisionJNI.btCollisionWorld_getCollisionObjectArrayConst(this.swigCPtr, this), false);
    }

    public void removeCollisionObject(btCollisionObject btcollisionobject) {
        CollisionJNI.btCollisionWorld_removeCollisionObject(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public void performDiscreteCollisionDetection() {
        CollisionJNI.btCollisionWorld_performDiscreteCollisionDetection(this.swigCPtr, this);
    }

    public btDispatcherInfo getDispatchInfo() {
        return new btDispatcherInfo(CollisionJNI.btCollisionWorld_getDispatchInfo(this.swigCPtr, this), false);
    }

    public btDispatcherInfo getDispatchInfoConst() {
        return new btDispatcherInfo(CollisionJNI.btCollisionWorld_getDispatchInfoConst(this.swigCPtr, this), false);
    }

    public boolean getForceUpdateAllAabbs() {
        return CollisionJNI.btCollisionWorld_getForceUpdateAllAabbs(this.swigCPtr, this);
    }

    public void setForceUpdateAllAabbs(boolean z) {
        CollisionJNI.btCollisionWorld_setForceUpdateAllAabbs(this.swigCPtr, this, z);
    }

    public void serialize(btSerializer btserializer) {
        CollisionJNI.btCollisionWorld_serialize(this.swigCPtr, this, btSerializer.getCPtr(btserializer), btserializer);
    }
}
