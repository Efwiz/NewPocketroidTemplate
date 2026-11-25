package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btSerializer;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionShape extends BulletBase {
    private long swigCPtr;

    protected btCollisionShape(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionShape(long j, boolean z) {
        this("btCollisionShape", j, z);
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

    public static long getCPtr(btCollisionShape btcollisionshape) {
        if (btcollisionshape == null) {
            return 0L;
        }
        return btcollisionshape.swigCPtr;
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
                CollisionJNI.delete_btCollisionShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static btCollisionShape newDerivedObject(long j, boolean z) {
        if (j == 0) {
            return null;
        }
        int iBtCollisionShape_getShapeType = CollisionJNI.btCollisionShape_getShapeType(j, null);
        switch (iBtCollisionShape_getShapeType) {
            case 0:
                return new btBoxShape(j, z);
            case 1:
                return new btTriangleShape(j, z);
            case 2:
                return new btBU_Simplex1to4(j, z);
            case 3:
                return new btConvexTriangleMeshShape(j, z);
            case 4:
                return new btConvexHullShape(j, z);
            case 5:
                return new btConvexPointCloudShape(j, z);
            case 6:
            case 7:
            case 15:
            case 19:
            case 20:
            case 23:
            case 25:
            case 29:
            case 30:
            default:
                throw new RuntimeException("Unknown shape type " + Integer.toString(iBtCollisionShape_getShapeType));
            case 8:
                return new btSphereShape(j, z);
            case 9:
                return new btMultiSphereShape(j, z);
            case 10:
                return new btCapsuleShape(j, z);
            case 11:
                return new btConeShape(j, z);
            case 12:
                return new btConvexShape(j, z);
            case 13:
                return new btCylinderShape(j, z);
            case 14:
                return new btUniformScalingShape(j, z);
            case 16:
                return new btMinkowskiSumShape(j, z);
            case 17:
                return new btBox2dShape(j, z);
            case 18:
                return new btConvex2dShape(j, z);
            case 21:
                return new btBvhTriangleMeshShape(j, z);
            case 22:
                return new btScaledBvhTriangleMeshShape(j, z);
            case 24:
                return new btHeightfieldTerrainShape(j, z);
            case 26:
                return new btMultimaterialTriangleMeshShape(j, z);
            case 27:
                return new btEmptyShape(j, z);
            case 28:
                return new btStaticPlaneShape(j, z);
            case 31:
                return new btCompoundShape(j, z);
        }
    }

    public long operatorNew(long j) {
        return CollisionJNI.btCollisionShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btCollisionShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btCollisionShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btCollisionShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btCollisionShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btCollisionShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btCollisionShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btCollisionShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void getAabb(Matrix4 matrix4, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btCollisionShape_getAabb(this.swigCPtr, this, matrix4, vector3, vector4);
    }

    public void getBoundingSphere(Vector3 vector3, SWIGTYPE_p_float sWIGTYPE_p_float) {
        CollisionJNI.btCollisionShape_getBoundingSphere(this.swigCPtr, this, vector3, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float));
    }

    public float getAngularMotionDisc() {
        return CollisionJNI.btCollisionShape_getAngularMotionDisc(this.swigCPtr, this);
    }

    public float getContactBreakingThreshold(float f) {
        return CollisionJNI.btCollisionShape_getContactBreakingThreshold(this.swigCPtr, this, f);
    }

    public void calculateTemporalAabb(Matrix4 matrix4, Vector3 vector3, Vector3 vector4, float f, Vector3 vector5, Vector3 vector6) {
        CollisionJNI.btCollisionShape_calculateTemporalAabb(this.swigCPtr, this, matrix4, vector3, vector4, f, vector5, vector6);
    }

    public boolean isPolyhedral() {
        return CollisionJNI.btCollisionShape_isPolyhedral(this.swigCPtr, this);
    }

    public boolean isConvex2d() {
        return CollisionJNI.btCollisionShape_isConvex2d(this.swigCPtr, this);
    }

    public boolean isConvex() {
        return CollisionJNI.btCollisionShape_isConvex(this.swigCPtr, this);
    }

    public boolean isNonMoving() {
        return CollisionJNI.btCollisionShape_isNonMoving(this.swigCPtr, this);
    }

    public boolean isConcave() {
        return CollisionJNI.btCollisionShape_isConcave(this.swigCPtr, this);
    }

    public boolean isCompound() {
        return CollisionJNI.btCollisionShape_isCompound(this.swigCPtr, this);
    }

    public boolean isSoftBody() {
        return CollisionJNI.btCollisionShape_isSoftBody(this.swigCPtr, this);
    }

    public boolean isInfinite() {
        return CollisionJNI.btCollisionShape_isInfinite(this.swigCPtr, this);
    }

    public void setLocalScaling(Vector3 vector3) {
        CollisionJNI.btCollisionShape_setLocalScaling(this.swigCPtr, this, vector3);
    }

    public Vector3 getLocalScaling() {
        return CollisionJNI.btCollisionShape_getLocalScaling(this.swigCPtr, this);
    }

    public void calculateLocalInertia(float f, Vector3 vector3) {
        CollisionJNI.btCollisionShape_calculateLocalInertia(this.swigCPtr, this, f, vector3);
    }

    public String getName() {
        return CollisionJNI.btCollisionShape_getName(this.swigCPtr, this);
    }

    public int getShapeType() {
        return CollisionJNI.btCollisionShape_getShapeType(this.swigCPtr, this);
    }

    public Vector3 getAnisotropicRollingFrictionDirection() {
        return CollisionJNI.btCollisionShape_getAnisotropicRollingFrictionDirection(this.swigCPtr, this);
    }

    public void setMargin(float f) {
        CollisionJNI.btCollisionShape_setMargin(this.swigCPtr, this, f);
    }

    public float getMargin() {
        return CollisionJNI.btCollisionShape_getMargin(this.swigCPtr, this);
    }

    public void setUserPointer(long j) {
        CollisionJNI.btCollisionShape_setUserPointer(this.swigCPtr, this, j);
    }

    public long getUserPointer() {
        return CollisionJNI.btCollisionShape_getUserPointer(this.swigCPtr, this);
    }

    public void setUserIndex(int i) {
        CollisionJNI.btCollisionShape_setUserIndex(this.swigCPtr, this, i);
    }

    public int getUserIndex() {
        return CollisionJNI.btCollisionShape_getUserIndex(this.swigCPtr, this);
    }

    public int calculateSerializeBufferSize() {
        return CollisionJNI.btCollisionShape_calculateSerializeBufferSize(this.swigCPtr, this);
    }

    public String serialize(long j, btSerializer btserializer) {
        return CollisionJNI.btCollisionShape_serialize(this.swigCPtr, this, j, btSerializer.getCPtr(btserializer), btserializer);
    }

    public void serializeSingleShape(btSerializer btserializer) {
        CollisionJNI.btCollisionShape_serializeSingleShape(this.swigCPtr, this, btSerializer.getCPtr(btserializer), btserializer);
    }
}
