package com.badlogic.gdx.physics.bullet.extras;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCompoundShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexHullShape;
import com.badlogic.gdx.physics.bullet.collision.btGImpactMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btMultiSphereShape;
import com.badlogic.gdx.physics.bullet.collision.btOptimizedBvh;
import com.badlogic.gdx.physics.bullet.collision.btScaledBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface;
import com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterfaceData;
import com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray;
import com.badlogic.gdx.physics.bullet.collision.btTriangleInfoMap;
import com.badlogic.gdx.physics.bullet.dynamics.btConeTwistConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btContactSolverInfo;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btGearConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpring2Constraint;
import com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpringConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btPoint2PointConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSliderConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btWorldImporter extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btWorldImporter(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btWorldImporter(long j, boolean z) {
        this("btWorldImporter", j, z);
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

    public static long getCPtr(btWorldImporter btworldimporter) {
        if (btworldimporter == null) {
            return 0L;
        }
        return btworldimporter.swigCPtr;
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
                ExtrasJNI.delete_btWorldImporter(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btWorldImporter(btDynamicsWorld btdynamicsworld) {
        this(ExtrasJNI.new_btWorldImporter(btDynamicsWorld.getCPtr(btdynamicsworld), btdynamicsworld), true);
    }

    public void deleteAllData() {
        ExtrasJNI.btWorldImporter_deleteAllData(this.swigCPtr, this);
    }

    public void setVerboseMode(int i) {
        ExtrasJNI.btWorldImporter_setVerboseMode(this.swigCPtr, this, i);
    }

    public int getVerboseMode() {
        return ExtrasJNI.btWorldImporter_getVerboseMode(this.swigCPtr, this);
    }

    public int getNumCollisionShapes() {
        return ExtrasJNI.btWorldImporter_getNumCollisionShapes(this.swigCPtr, this);
    }

    public btCollisionShape getCollisionShapeByIndex(int i) {
        long jBtWorldImporter_getCollisionShapeByIndex = ExtrasJNI.btWorldImporter_getCollisionShapeByIndex(this.swigCPtr, this, i);
        if (jBtWorldImporter_getCollisionShapeByIndex == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_getCollisionShapeByIndex, false);
    }

    public int getNumRigidBodies() {
        return ExtrasJNI.btWorldImporter_getNumRigidBodies(this.swigCPtr, this);
    }

    public btCollisionObject getRigidBodyByIndex(int i) {
        return btCollisionObject.getInstance(ExtrasJNI.btWorldImporter_getRigidBodyByIndex(this.swigCPtr, this, i), false);
    }

    public int getNumConstraints() {
        return ExtrasJNI.btWorldImporter_getNumConstraints(this.swigCPtr, this);
    }

    public btTypedConstraint getConstraintByIndex(int i) {
        long jBtWorldImporter_getConstraintByIndex = ExtrasJNI.btWorldImporter_getConstraintByIndex(this.swigCPtr, this, i);
        if (jBtWorldImporter_getConstraintByIndex == 0) {
            return null;
        }
        return new btTypedConstraint(jBtWorldImporter_getConstraintByIndex, false);
    }

    public int getNumBvhs() {
        return ExtrasJNI.btWorldImporter_getNumBvhs(this.swigCPtr, this);
    }

    public btOptimizedBvh getBvhByIndex(int i) {
        long jBtWorldImporter_getBvhByIndex = ExtrasJNI.btWorldImporter_getBvhByIndex(this.swigCPtr, this, i);
        if (jBtWorldImporter_getBvhByIndex == 0) {
            return null;
        }
        return new btOptimizedBvh(jBtWorldImporter_getBvhByIndex, false);
    }

    public int getNumTriangleInfoMaps() {
        return ExtrasJNI.btWorldImporter_getNumTriangleInfoMaps(this.swigCPtr, this);
    }

    public btTriangleInfoMap getTriangleInfoMapByIndex(int i) {
        long jBtWorldImporter_getTriangleInfoMapByIndex = ExtrasJNI.btWorldImporter_getTriangleInfoMapByIndex(this.swigCPtr, this, i);
        if (jBtWorldImporter_getTriangleInfoMapByIndex == 0) {
            return null;
        }
        return new btTriangleInfoMap(jBtWorldImporter_getTriangleInfoMapByIndex, false);
    }

    public btCollisionShape getCollisionShapeByName(String str) {
        long jBtWorldImporter_getCollisionShapeByName = ExtrasJNI.btWorldImporter_getCollisionShapeByName(this.swigCPtr, this, str);
        if (jBtWorldImporter_getCollisionShapeByName == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_getCollisionShapeByName, false);
    }

    public btRigidBody getRigidBodyByName(String str) {
        return btRigidBody.getInstance(ExtrasJNI.btWorldImporter_getRigidBodyByName(this.swigCPtr, this, str), false);
    }

    public btTypedConstraint getConstraintByName(String str) {
        long jBtWorldImporter_getConstraintByName = ExtrasJNI.btWorldImporter_getConstraintByName(this.swigCPtr, this, str);
        if (jBtWorldImporter_getConstraintByName == 0) {
            return null;
        }
        return new btTypedConstraint(jBtWorldImporter_getConstraintByName, false);
    }

    public String getNameForPointer(long j) {
        return ExtrasJNI.btWorldImporter_getNameForPointer__SWIG_0(this.swigCPtr, this, j);
    }

    public void setDynamicsWorldInfo(Vector3 vector3, btContactSolverInfo btcontactsolverinfo) {
        ExtrasJNI.btWorldImporter_setDynamicsWorldInfo(this.swigCPtr, this, vector3, btContactSolverInfo.getCPtr(btcontactsolverinfo), btcontactsolverinfo);
    }

    public btRigidBody createRigidBody(boolean z, float f, Matrix4 matrix4, btCollisionShape btcollisionshape, String str) {
        return btRigidBody.getInstance(ExtrasJNI.btWorldImporter_createRigidBody(this.swigCPtr, this, z, f, matrix4, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, str), false);
    }

    public btCollisionObject createCollisionObject(Matrix4 matrix4, btCollisionShape btcollisionshape, String str) {
        return btCollisionObject.getInstance(ExtrasJNI.btWorldImporter_createCollisionObject(this.swigCPtr, this, matrix4, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, str), false);
    }

    public btCollisionShape createPlaneShape(Vector3 vector3, float f) {
        long jBtWorldImporter_createPlaneShape = ExtrasJNI.btWorldImporter_createPlaneShape(this.swigCPtr, this, vector3, f);
        if (jBtWorldImporter_createPlaneShape == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createPlaneShape, false);
    }

    public btCollisionShape createBoxShape(Vector3 vector3) {
        long jBtWorldImporter_createBoxShape = ExtrasJNI.btWorldImporter_createBoxShape(this.swigCPtr, this, vector3);
        if (jBtWorldImporter_createBoxShape == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createBoxShape, false);
    }

    public btCollisionShape createSphereShape(float f) {
        long jBtWorldImporter_createSphereShape = ExtrasJNI.btWorldImporter_createSphereShape(this.swigCPtr, this, f);
        if (jBtWorldImporter_createSphereShape == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createSphereShape, false);
    }

    public btCollisionShape createCapsuleShapeX(float f, float f2) {
        long jBtWorldImporter_createCapsuleShapeX = ExtrasJNI.btWorldImporter_createCapsuleShapeX(this.swigCPtr, this, f, f2);
        if (jBtWorldImporter_createCapsuleShapeX == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createCapsuleShapeX, false);
    }

    public btCollisionShape createCapsuleShapeY(float f, float f2) {
        long jBtWorldImporter_createCapsuleShapeY = ExtrasJNI.btWorldImporter_createCapsuleShapeY(this.swigCPtr, this, f, f2);
        if (jBtWorldImporter_createCapsuleShapeY == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createCapsuleShapeY, false);
    }

    public btCollisionShape createCapsuleShapeZ(float f, float f2) {
        long jBtWorldImporter_createCapsuleShapeZ = ExtrasJNI.btWorldImporter_createCapsuleShapeZ(this.swigCPtr, this, f, f2);
        if (jBtWorldImporter_createCapsuleShapeZ == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createCapsuleShapeZ, false);
    }

    public btCollisionShape createCylinderShapeX(float f, float f2) {
        long jBtWorldImporter_createCylinderShapeX = ExtrasJNI.btWorldImporter_createCylinderShapeX(this.swigCPtr, this, f, f2);
        if (jBtWorldImporter_createCylinderShapeX == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createCylinderShapeX, false);
    }

    public btCollisionShape createCylinderShapeY(float f, float f2) {
        long jBtWorldImporter_createCylinderShapeY = ExtrasJNI.btWorldImporter_createCylinderShapeY(this.swigCPtr, this, f, f2);
        if (jBtWorldImporter_createCylinderShapeY == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createCylinderShapeY, false);
    }

    public btCollisionShape createCylinderShapeZ(float f, float f2) {
        long jBtWorldImporter_createCylinderShapeZ = ExtrasJNI.btWorldImporter_createCylinderShapeZ(this.swigCPtr, this, f, f2);
        if (jBtWorldImporter_createCylinderShapeZ == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createCylinderShapeZ, false);
    }

    public btCollisionShape createConeShapeX(float f, float f2) {
        long jBtWorldImporter_createConeShapeX = ExtrasJNI.btWorldImporter_createConeShapeX(this.swigCPtr, this, f, f2);
        if (jBtWorldImporter_createConeShapeX == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createConeShapeX, false);
    }

    public btCollisionShape createConeShapeY(float f, float f2) {
        long jBtWorldImporter_createConeShapeY = ExtrasJNI.btWorldImporter_createConeShapeY(this.swigCPtr, this, f, f2);
        if (jBtWorldImporter_createConeShapeY == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createConeShapeY, false);
    }

    public btCollisionShape createConeShapeZ(float f, float f2) {
        long jBtWorldImporter_createConeShapeZ = ExtrasJNI.btWorldImporter_createConeShapeZ(this.swigCPtr, this, f, f2);
        if (jBtWorldImporter_createConeShapeZ == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createConeShapeZ, false);
    }

    public btTriangleIndexVertexArray createTriangleMeshContainer() {
        long jBtWorldImporter_createTriangleMeshContainer = ExtrasJNI.btWorldImporter_createTriangleMeshContainer(this.swigCPtr, this);
        if (jBtWorldImporter_createTriangleMeshContainer == 0) {
            return null;
        }
        return new btTriangleIndexVertexArray(jBtWorldImporter_createTriangleMeshContainer, false);
    }

    public btBvhTriangleMeshShape createBvhTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface, btOptimizedBvh btoptimizedbvh) {
        long jBtWorldImporter_createBvhTriangleMeshShape = ExtrasJNI.btWorldImporter_createBvhTriangleMeshShape(this.swigCPtr, this, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, btOptimizedBvh.getCPtr(btoptimizedbvh), btoptimizedbvh);
        if (jBtWorldImporter_createBvhTriangleMeshShape == 0) {
            return null;
        }
        return new btBvhTriangleMeshShape(jBtWorldImporter_createBvhTriangleMeshShape, false);
    }

    public btCollisionShape createConvexTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface) {
        long jBtWorldImporter_createConvexTriangleMeshShape = ExtrasJNI.btWorldImporter_createConvexTriangleMeshShape(this.swigCPtr, this, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface);
        if (jBtWorldImporter_createConvexTriangleMeshShape == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtWorldImporter_createConvexTriangleMeshShape, false);
    }

    public btGImpactMeshShape createGimpactShape(btStridingMeshInterface btstridingmeshinterface) {
        long jBtWorldImporter_createGimpactShape = ExtrasJNI.btWorldImporter_createGimpactShape(this.swigCPtr, this, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface);
        if (jBtWorldImporter_createGimpactShape == 0) {
            return null;
        }
        return new btGImpactMeshShape(jBtWorldImporter_createGimpactShape, false);
    }

    public btStridingMeshInterfaceData createStridingMeshInterfaceData(btStridingMeshInterfaceData btstridingmeshinterfacedata) {
        long jBtWorldImporter_createStridingMeshInterfaceData = ExtrasJNI.btWorldImporter_createStridingMeshInterfaceData(this.swigCPtr, this, btStridingMeshInterfaceData.getCPtr(btstridingmeshinterfacedata), btstridingmeshinterfacedata);
        if (jBtWorldImporter_createStridingMeshInterfaceData == 0) {
            return null;
        }
        return new btStridingMeshInterfaceData(jBtWorldImporter_createStridingMeshInterfaceData, false);
    }

    public btConvexHullShape createConvexHullShape() {
        long jBtWorldImporter_createConvexHullShape = ExtrasJNI.btWorldImporter_createConvexHullShape(this.swigCPtr, this);
        if (jBtWorldImporter_createConvexHullShape == 0) {
            return null;
        }
        return new btConvexHullShape(jBtWorldImporter_createConvexHullShape, false);
    }

    public btCompoundShape createCompoundShape() {
        long jBtWorldImporter_createCompoundShape = ExtrasJNI.btWorldImporter_createCompoundShape(this.swigCPtr, this);
        if (jBtWorldImporter_createCompoundShape == 0) {
            return null;
        }
        return new btCompoundShape(jBtWorldImporter_createCompoundShape, false);
    }

    public btScaledBvhTriangleMeshShape createScaledTrangleMeshShape(btBvhTriangleMeshShape btbvhtrianglemeshshape, Vector3 vector3) {
        long jBtWorldImporter_createScaledTrangleMeshShape = ExtrasJNI.btWorldImporter_createScaledTrangleMeshShape(this.swigCPtr, this, btBvhTriangleMeshShape.getCPtr(btbvhtrianglemeshshape), btbvhtrianglemeshshape, vector3);
        if (jBtWorldImporter_createScaledTrangleMeshShape == 0) {
            return null;
        }
        return new btScaledBvhTriangleMeshShape(jBtWorldImporter_createScaledTrangleMeshShape, false);
    }

    public btMultiSphereShape createMultiSphereShape(btVector3 btvector3, FloatBuffer floatBuffer, int i) {
        long jBtWorldImporter_createMultiSphereShape = ExtrasJNI.btWorldImporter_createMultiSphereShape(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, floatBuffer, i);
        if (jBtWorldImporter_createMultiSphereShape == 0) {
            return null;
        }
        return new btMultiSphereShape(jBtWorldImporter_createMultiSphereShape, false);
    }

    public btTriangleIndexVertexArray createMeshInterface(btStridingMeshInterfaceData btstridingmeshinterfacedata) {
        long jBtWorldImporter_createMeshInterface = ExtrasJNI.btWorldImporter_createMeshInterface(this.swigCPtr, this, btStridingMeshInterfaceData.getCPtr(btstridingmeshinterfacedata), btstridingmeshinterfacedata);
        if (jBtWorldImporter_createMeshInterface == 0) {
            return null;
        }
        return new btTriangleIndexVertexArray(jBtWorldImporter_createMeshInterface, false);
    }

    public btOptimizedBvh createOptimizedBvh() {
        long jBtWorldImporter_createOptimizedBvh = ExtrasJNI.btWorldImporter_createOptimizedBvh(this.swigCPtr, this);
        if (jBtWorldImporter_createOptimizedBvh == 0) {
            return null;
        }
        return new btOptimizedBvh(jBtWorldImporter_createOptimizedBvh, false);
    }

    public btTriangleInfoMap createTriangleInfoMap() {
        long jBtWorldImporter_createTriangleInfoMap = ExtrasJNI.btWorldImporter_createTriangleInfoMap(this.swigCPtr, this);
        if (jBtWorldImporter_createTriangleInfoMap == 0) {
            return null;
        }
        return new btTriangleInfoMap(jBtWorldImporter_createTriangleInfoMap, false);
    }

    public btPoint2PointConstraint createPoint2PointConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4) {
        long jBtWorldImporter_createPoint2PointConstraint__SWIG_0 = ExtrasJNI.btWorldImporter_createPoint2PointConstraint__SWIG_0(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector3, vector4);
        if (jBtWorldImporter_createPoint2PointConstraint__SWIG_0 == 0) {
            return null;
        }
        return new btPoint2PointConstraint(jBtWorldImporter_createPoint2PointConstraint__SWIG_0, false);
    }

    public btPoint2PointConstraint createPoint2PointConstraint(btRigidBody btrigidbody, Vector3 vector3) {
        long jBtWorldImporter_createPoint2PointConstraint__SWIG_1 = ExtrasJNI.btWorldImporter_createPoint2PointConstraint__SWIG_1(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3);
        if (jBtWorldImporter_createPoint2PointConstraint__SWIG_1 == 0) {
            return null;
        }
        return new btPoint2PointConstraint(jBtWorldImporter_createPoint2PointConstraint__SWIG_1, false);
    }

    public btHingeConstraint createHingeConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        long jBtWorldImporter_createHingeConstraint__SWIG_0 = ExtrasJNI.btWorldImporter_createHingeConstraint__SWIG_0(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5, z);
        if (jBtWorldImporter_createHingeConstraint__SWIG_0 == 0) {
            return null;
        }
        return new btHingeConstraint(jBtWorldImporter_createHingeConstraint__SWIG_0, false);
    }

    public btHingeConstraint createHingeConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5) {
        long jBtWorldImporter_createHingeConstraint__SWIG_1 = ExtrasJNI.btWorldImporter_createHingeConstraint__SWIG_1(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5);
        if (jBtWorldImporter_createHingeConstraint__SWIG_1 == 0) {
            return null;
        }
        return new btHingeConstraint(jBtWorldImporter_createHingeConstraint__SWIG_1, false);
    }

    public btHingeConstraint createHingeConstraint(btRigidBody btrigidbody, Matrix4 matrix4, boolean z) {
        long jBtWorldImporter_createHingeConstraint__SWIG_2 = ExtrasJNI.btWorldImporter_createHingeConstraint__SWIG_2(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4, z);
        if (jBtWorldImporter_createHingeConstraint__SWIG_2 == 0) {
            return null;
        }
        return new btHingeConstraint(jBtWorldImporter_createHingeConstraint__SWIG_2, false);
    }

    public btHingeConstraint createHingeConstraint(btRigidBody btrigidbody, Matrix4 matrix4) {
        long jBtWorldImporter_createHingeConstraint__SWIG_3 = ExtrasJNI.btWorldImporter_createHingeConstraint__SWIG_3(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4);
        if (jBtWorldImporter_createHingeConstraint__SWIG_3 == 0) {
            return null;
        }
        return new btHingeConstraint(jBtWorldImporter_createHingeConstraint__SWIG_3, false);
    }

    public btConeTwistConstraint createConeTwistConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5) {
        long jBtWorldImporter_createConeTwistConstraint__SWIG_0 = ExtrasJNI.btWorldImporter_createConeTwistConstraint__SWIG_0(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5);
        if (jBtWorldImporter_createConeTwistConstraint__SWIG_0 == 0) {
            return null;
        }
        return new btConeTwistConstraint(jBtWorldImporter_createConeTwistConstraint__SWIG_0, false);
    }

    public btConeTwistConstraint createConeTwistConstraint(btRigidBody btrigidbody, Matrix4 matrix4) {
        long jBtWorldImporter_createConeTwistConstraint__SWIG_1 = ExtrasJNI.btWorldImporter_createConeTwistConstraint__SWIG_1(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4);
        if (jBtWorldImporter_createConeTwistConstraint__SWIG_1 == 0) {
            return null;
        }
        return new btConeTwistConstraint(jBtWorldImporter_createConeTwistConstraint__SWIG_1, false);
    }

    public btGeneric6DofConstraint createGeneric6DofConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        long jBtWorldImporter_createGeneric6DofConstraint__SWIG_0 = ExtrasJNI.btWorldImporter_createGeneric6DofConstraint__SWIG_0(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5, z);
        if (jBtWorldImporter_createGeneric6DofConstraint__SWIG_0 == 0) {
            return null;
        }
        return new btGeneric6DofConstraint(jBtWorldImporter_createGeneric6DofConstraint__SWIG_0, false);
    }

    public btGeneric6DofConstraint createGeneric6DofConstraint(btRigidBody btrigidbody, Matrix4 matrix4, boolean z) {
        long jBtWorldImporter_createGeneric6DofConstraint__SWIG_1 = ExtrasJNI.btWorldImporter_createGeneric6DofConstraint__SWIG_1(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4, z);
        if (jBtWorldImporter_createGeneric6DofConstraint__SWIG_1 == 0) {
            return null;
        }
        return new btGeneric6DofConstraint(jBtWorldImporter_createGeneric6DofConstraint__SWIG_1, false);
    }

    public btGeneric6DofSpringConstraint createGeneric6DofSpringConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        long jBtWorldImporter_createGeneric6DofSpringConstraint = ExtrasJNI.btWorldImporter_createGeneric6DofSpringConstraint(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5, z);
        if (jBtWorldImporter_createGeneric6DofSpringConstraint == 0) {
            return null;
        }
        return new btGeneric6DofSpringConstraint(jBtWorldImporter_createGeneric6DofSpringConstraint, false);
    }

    public btGeneric6DofSpring2Constraint createGeneric6DofSpring2Constraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, int i) {
        long jBtWorldImporter_createGeneric6DofSpring2Constraint = ExtrasJNI.btWorldImporter_createGeneric6DofSpring2Constraint(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5, i);
        if (jBtWorldImporter_createGeneric6DofSpring2Constraint == 0) {
            return null;
        }
        return new btGeneric6DofSpring2Constraint(jBtWorldImporter_createGeneric6DofSpring2Constraint, false);
    }

    public btSliderConstraint createSliderConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        long jBtWorldImporter_createSliderConstraint__SWIG_0 = ExtrasJNI.btWorldImporter_createSliderConstraint__SWIG_0(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, matrix4, matrix5, z);
        if (jBtWorldImporter_createSliderConstraint__SWIG_0 == 0) {
            return null;
        }
        return new btSliderConstraint(jBtWorldImporter_createSliderConstraint__SWIG_0, false);
    }

    public btSliderConstraint createSliderConstraint(btRigidBody btrigidbody, Matrix4 matrix4, boolean z) {
        long jBtWorldImporter_createSliderConstraint__SWIG_1 = ExtrasJNI.btWorldImporter_createSliderConstraint__SWIG_1(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, matrix4, z);
        if (jBtWorldImporter_createSliderConstraint__SWIG_1 == 0) {
            return null;
        }
        return new btSliderConstraint(jBtWorldImporter_createSliderConstraint__SWIG_1, false);
    }

    public btGearConstraint createGearConstraint(btRigidBody btrigidbody, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, float f) {
        long jBtWorldImporter_createGearConstraint = ExtrasJNI.btWorldImporter_createGearConstraint(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody, btRigidBody.getCPtr(btrigidbody2), btrigidbody2, vector3, vector4, f);
        if (jBtWorldImporter_createGearConstraint == 0) {
            return null;
        }
        return new btGearConstraint(jBtWorldImporter_createGearConstraint, false);
    }
}
