package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btGImpactShapeInterface extends btConcaveShape {
    private long swigCPtr;

    protected btGImpactShapeInterface(String str, long j, boolean z) {
        super(str, CollisionJNI.btGImpactShapeInterface_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGImpactShapeInterface(long j, boolean z) {
        this("btGImpactShapeInterface", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btGImpactShapeInterface_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGImpactShapeInterface btgimpactshapeinterface) {
        if (btgimpactshapeinterface == null) {
            return 0L;
        }
        return btgimpactshapeinterface.swigCPtr;
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
                CollisionJNI.delete_btGImpactShapeInterface(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void updateBound() {
        CollisionJNI.btGImpactShapeInterface_updateBound(this.swigCPtr, this);
    }

    public void postUpdate() {
        CollisionJNI.btGImpactShapeInterface_postUpdate(this.swigCPtr, this);
    }

    public btAABB getLocalBox() {
        return new btAABB(CollisionJNI.btGImpactShapeInterface_getLocalBox(this.swigCPtr, this), false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public int getShapeType() {
        return CollisionJNI.btGImpactShapeInterface_getShapeType(this.swigCPtr, this);
    }

    public int getGImpactShapeType() {
        return CollisionJNI.btGImpactShapeInterface_getGImpactShapeType(this.swigCPtr, this);
    }

    public btGImpactQuantizedBvh getBoxSet() {
        long jBtGImpactShapeInterface_getBoxSet = CollisionJNI.btGImpactShapeInterface_getBoxSet(this.swigCPtr, this);
        if (jBtGImpactShapeInterface_getBoxSet == 0) {
            return null;
        }
        return new btGImpactQuantizedBvh(jBtGImpactShapeInterface_getBoxSet, false);
    }

    public boolean hasBoxSet() {
        return CollisionJNI.btGImpactShapeInterface_hasBoxSet(this.swigCPtr, this);
    }

    public btPrimitiveManagerBase getPrimitiveManager() {
        long jBtGImpactShapeInterface_getPrimitiveManager = CollisionJNI.btGImpactShapeInterface_getPrimitiveManager(this.swigCPtr, this);
        if (jBtGImpactShapeInterface_getPrimitiveManager == 0) {
            return null;
        }
        return new btPrimitiveManagerBase(jBtGImpactShapeInterface_getPrimitiveManager, false);
    }

    public int getNumChildShapes() {
        return CollisionJNI.btGImpactShapeInterface_getNumChildShapes(this.swigCPtr, this);
    }

    public boolean childrenHasTransform() {
        return CollisionJNI.btGImpactShapeInterface_childrenHasTransform(this.swigCPtr, this);
    }

    public boolean needsRetrieveTriangles() {
        return CollisionJNI.btGImpactShapeInterface_needsRetrieveTriangles(this.swigCPtr, this);
    }

    public boolean needsRetrieveTetrahedrons() {
        return CollisionJNI.btGImpactShapeInterface_needsRetrieveTetrahedrons(this.swigCPtr, this);
    }

    public void getBulletTriangle(int i, btTriangleShapeEx bttriangleshapeex) {
        CollisionJNI.btGImpactShapeInterface_getBulletTriangle(this.swigCPtr, this, i, btTriangleShapeEx.getCPtr(bttriangleshapeex), bttriangleshapeex);
    }

    public void getBulletTetrahedron(int i, btTetrahedronShapeEx bttetrahedronshapeex) {
        CollisionJNI.btGImpactShapeInterface_getBulletTetrahedron(this.swigCPtr, this, i, btTetrahedronShapeEx.getCPtr(bttetrahedronshapeex), bttetrahedronshapeex);
    }

    public void lockChildShapes() {
        CollisionJNI.btGImpactShapeInterface_lockChildShapes(this.swigCPtr, this);
    }

    public void unlockChildShapes() {
        CollisionJNI.btGImpactShapeInterface_unlockChildShapes(this.swigCPtr, this);
    }

    public void getPrimitiveTriangle(int i, btPrimitiveTriangle btprimitivetriangle) {
        CollisionJNI.btGImpactShapeInterface_getPrimitiveTriangle(this.swigCPtr, this, i, btPrimitiveTriangle.getCPtr(btprimitivetriangle), btprimitivetriangle);
    }

    public void getChildAabb(int i, Matrix4 matrix4, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btGImpactShapeInterface_getChildAabb(this.swigCPtr, this, i, matrix4, vector3, vector4);
    }

    public btCollisionShape getChildShape(int i) {
        long jBtGImpactShapeInterface_getChildShape = CollisionJNI.btGImpactShapeInterface_getChildShape(this.swigCPtr, this, i);
        if (jBtGImpactShapeInterface_getChildShape == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtGImpactShapeInterface_getChildShape, false);
    }

    public btCollisionShape getChildShapeConst(int i) {
        long jBtGImpactShapeInterface_getChildShapeConst = CollisionJNI.btGImpactShapeInterface_getChildShapeConst(this.swigCPtr, this, i);
        if (jBtGImpactShapeInterface_getChildShapeConst == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtGImpactShapeInterface_getChildShapeConst, false);
    }

    public Matrix4 getChildTransform(int i) {
        return CollisionJNI.btGImpactShapeInterface_getChildTransform(this.swigCPtr, this, i);
    }

    public void setChildTransform(int i, Matrix4 matrix4) {
        CollisionJNI.btGImpactShapeInterface_setChildTransform(this.swigCPtr, this, i, matrix4);
    }

    public void rayTest(Vector3 vector3, Vector3 vector4, RayResultCallback rayResultCallback) {
        CollisionJNI.btGImpactShapeInterface_rayTest(this.swigCPtr, this, vector3, vector4, RayResultCallback.getCPtr(rayResultCallback), rayResultCallback);
    }

    public void processAllTrianglesRay(btTriangleCallback bttrianglecallback, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btGImpactShapeInterface_processAllTrianglesRay(this.swigCPtr, this, btTriangleCallback.getCPtr(bttrianglecallback), bttrianglecallback, vector3, vector4);
    }
}
