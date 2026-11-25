package com.badlogic.gdx.physics.bullet.extras;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCompoundShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexHullShape;
import com.badlogic.gdx.physics.bullet.collision.btGImpactMeshShape;
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
import com.badlogic.gdx.physics.bullet.dynamics.btMultiBody;
import com.badlogic.gdx.physics.bullet.dynamics.btPoint2PointConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSliderConstraint;
import com.badlogic.gdx.physics.bullet.inversedynamics.MultiBodyTree;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class ExtrasJNI {
    public static final native long CloneTreeCreator_SWIGUpcast(long j);

    public static final native long CoilCreator_SWIGUpcast(long j);

    public static final native long CreateMultiBodyTree(long j, MultiBodyTreeCreator multiBodyTreeCreator);

    public static final native long DillCreator_SWIGUpcast(long j);

    public static final native int MultiBodyNameMap_addBody(long j, MultiBodyNameMap multiBodyNameMap, int i, long j2);

    public static final native int MultiBodyNameMap_addJoint(long j, MultiBodyNameMap multiBodyNameMap, int i, long j2);

    public static final native int MultiBodyNameMap_getBodyIndex(long j, MultiBodyNameMap multiBodyNameMap, long j2, IntBuffer intBuffer);

    public static final native int MultiBodyNameMap_getBodyName(long j, MultiBodyNameMap multiBodyNameMap, int i, long j2);

    public static final native int MultiBodyNameMap_getJointIndex(long j, MultiBodyNameMap multiBodyNameMap, long j2, IntBuffer intBuffer);

    public static final native int MultiBodyNameMap_getJointName(long j, MultiBodyNameMap multiBodyNameMap, int i, long j2);

    public static final native int MultiBodyTreeCreator_getBody(long j, MultiBodyTreeCreator multiBodyTreeCreator, int i, IntBuffer intBuffer, long j2, long j3, long j4, long j5, FloatBuffer floatBuffer, long j6, long j7, IntBuffer intBuffer2, long j8);

    public static final native long MultiBodyTreeCreator_getNameMap(long j, MultiBodyTreeCreator multiBodyTreeCreator);

    public static final native int MultiBodyTreeCreator_getNumBodies(long j, MultiBodyTreeCreator multiBodyTreeCreator, IntBuffer intBuffer);

    public static final native long RandomTreeCreator_SWIGUpcast(long j);

    public static final native long SimpleTreeCreator_SWIGUpcast(long j);

    public static final native void User2InternalIndex_addBody(long j, User2InternalIndex user2InternalIndex, int i, int i2);

    public static final native int User2InternalIndex_buildMapping(long j, User2InternalIndex user2InternalIndex);

    public static final native int User2InternalIndex_internal2user(long j, User2InternalIndex user2InternalIndex, int i, IntBuffer intBuffer);

    public static final native int User2InternalIndex_user2internal(long j, User2InternalIndex user2InternalIndex, int i, IntBuffer intBuffer);

    public static final native long btBulletWorldImporter_SWIGUpcast(long j);

    public static final native void btBulletWorldImporter_change_ownership(btBulletWorldImporter btbulletworldimporter, long j, boolean z);

    public static final native boolean btBulletWorldImporter_convertAllObjects(long j, btBulletWorldImporter btbulletworldimporter, long j2);

    public static final native boolean btBulletWorldImporter_convertAllObjectsSwigExplicitbtBulletWorldImporter(long j, btBulletWorldImporter btbulletworldimporter, long j2);

    public static final native void btBulletWorldImporter_director_connect(btBulletWorldImporter btbulletworldimporter, long j, boolean z, boolean z2);

    public static final native boolean btBulletWorldImporter_loadFileFromMemory__SWIG_0(long j, btBulletWorldImporter btbulletworldimporter, long j2);

    public static final native boolean btBulletWorldImporter_loadFileFromMemory__SWIG_1(long j, btBulletWorldImporter btbulletworldimporter, ByteBuffer byteBuffer, int i);

    public static final native boolean btBulletWorldImporter_loadFile__SWIG_0(long j, btBulletWorldImporter btbulletworldimporter, String str, String str2);

    public static final native boolean btBulletWorldImporter_loadFile__SWIG_1(long j, btBulletWorldImporter btbulletworldimporter, String str);

    public static final native long btMultiBodyTreeCreator_SWIGUpcast(long j);

    public static final native int btMultiBodyTreeCreator_createFromBtMultiBody__SWIG_0(long j, btMultiBodyTreeCreator btmultibodytreecreator, long j2, btMultiBody btmultibody, boolean z);

    public static final native int btMultiBodyTreeCreator_createFromBtMultiBody__SWIG_1(long j, btMultiBodyTreeCreator btmultibodytreecreator, long j2, btMultiBody btmultibody);

    public static final native String btStringArray_at__SWIG_0(long j, btStringArray btstringarray, int i);

    public static final native int btStringArray_capacity(long j, btStringArray btstringarray);

    public static final native void btStringArray_clear(long j, btStringArray btstringarray);

    public static final native void btStringArray_copyFromArray(long j, btStringArray btstringarray, long j2, btStringArray btstringarray2);

    public static final native String btStringArray_expandNonInitializing(long j, btStringArray btstringarray);

    public static final native String btStringArray_expand__SWIG_0(long j, btStringArray btstringarray, String str);

    public static final native String btStringArray_expand__SWIG_1(long j, btStringArray btstringarray);

    public static final native int btStringArray_findBinarySearch(long j, btStringArray btstringarray, String str);

    public static final native int btStringArray_findLinearSearch(long j, btStringArray btstringarray, String str);

    public static final native int btStringArray_findLinearSearch2(long j, btStringArray btstringarray, String str);

    public static final native void btStringArray_initializeFromBuffer(long j, btStringArray btstringarray, long j2, int i, int i2);

    public static final native boolean btStringArray_less_operatorFunctionCall(long j, btStringArray.less lessVar, String str, String str2);

    public static final native long btStringArray_operatorAssignment(long j, btStringArray btstringarray, long j2, btStringArray btstringarray2);

    public static final native String btStringArray_operatorSubscript__SWIG_0(long j, btStringArray btstringarray, int i);

    public static final native void btStringArray_pop_back(long j, btStringArray btstringarray);

    public static final native void btStringArray_push_back(long j, btStringArray btstringarray, String str);

    public static final native void btStringArray_remove(long j, btStringArray btstringarray, String str);

    public static final native void btStringArray_removeAtIndex(long j, btStringArray btstringarray, int i);

    public static final native void btStringArray_reserve(long j, btStringArray btstringarray, int i);

    public static final native void btStringArray_resizeNoInitialize(long j, btStringArray btstringarray, int i);

    public static final native void btStringArray_resize__SWIG_0(long j, btStringArray btstringarray, int i, String str);

    public static final native void btStringArray_resize__SWIG_1(long j, btStringArray btstringarray, int i);

    public static final native int btStringArray_size(long j, btStringArray btstringarray);

    public static final native void btStringArray_swap(long j, btStringArray btstringarray, int i, int i2);

    public static final native long btWorldImporter_createBoxShape(long j, btWorldImporter btworldimporter, Vector3 vector3);

    public static final native long btWorldImporter_createBvhTriangleMeshShape(long j, btWorldImporter btworldimporter, long j2, btStridingMeshInterface btstridingmeshinterface, long j3, btOptimizedBvh btoptimizedbvh);

    public static final native long btWorldImporter_createCapsuleShapeX(long j, btWorldImporter btworldimporter, float f, float f2);

    public static final native long btWorldImporter_createCapsuleShapeY(long j, btWorldImporter btworldimporter, float f, float f2);

    public static final native long btWorldImporter_createCapsuleShapeZ(long j, btWorldImporter btworldimporter, float f, float f2);

    public static final native long btWorldImporter_createCollisionObject(long j, btWorldImporter btworldimporter, Matrix4 matrix4, long j2, btCollisionShape btcollisionshape, String str);

    public static final native long btWorldImporter_createCompoundShape(long j, btWorldImporter btworldimporter);

    public static final native long btWorldImporter_createConeShapeX(long j, btWorldImporter btworldimporter, float f, float f2);

    public static final native long btWorldImporter_createConeShapeY(long j, btWorldImporter btworldimporter, float f, float f2);

    public static final native long btWorldImporter_createConeShapeZ(long j, btWorldImporter btworldimporter, float f, float f2);

    public static final native long btWorldImporter_createConeTwistConstraint__SWIG_0(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5);

    public static final native long btWorldImporter_createConeTwistConstraint__SWIG_1(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, Matrix4 matrix4);

    public static final native long btWorldImporter_createConvexHullShape(long j, btWorldImporter btworldimporter);

    public static final native long btWorldImporter_createConvexTriangleMeshShape(long j, btWorldImporter btworldimporter, long j2, btStridingMeshInterface btstridingmeshinterface);

    public static final native long btWorldImporter_createCylinderShapeX(long j, btWorldImporter btworldimporter, float f, float f2);

    public static final native long btWorldImporter_createCylinderShapeY(long j, btWorldImporter btworldimporter, float f, float f2);

    public static final native long btWorldImporter_createCylinderShapeZ(long j, btWorldImporter btworldimporter, float f, float f2);

    public static final native long btWorldImporter_createGearConstraint(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4, float f);

    public static final native long btWorldImporter_createGeneric6DofConstraint__SWIG_0(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z);

    public static final native long btWorldImporter_createGeneric6DofConstraint__SWIG_1(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, Matrix4 matrix4, boolean z);

    public static final native long btWorldImporter_createGeneric6DofSpring2Constraint(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, int i);

    public static final native long btWorldImporter_createGeneric6DofSpringConstraint(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z);

    public static final native long btWorldImporter_createGimpactShape(long j, btWorldImporter btworldimporter, long j2, btStridingMeshInterface btstridingmeshinterface);

    public static final native long btWorldImporter_createHingeConstraint__SWIG_0(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z);

    public static final native long btWorldImporter_createHingeConstraint__SWIG_1(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5);

    public static final native long btWorldImporter_createHingeConstraint__SWIG_2(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, Matrix4 matrix4, boolean z);

    public static final native long btWorldImporter_createHingeConstraint__SWIG_3(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, Matrix4 matrix4);

    public static final native long btWorldImporter_createMeshInterface(long j, btWorldImporter btworldimporter, long j2, btStridingMeshInterfaceData btstridingmeshinterfacedata);

    public static final native long btWorldImporter_createMultiSphereShape(long j, btWorldImporter btworldimporter, long j2, btVector3 btvector3, FloatBuffer floatBuffer, int i);

    public static final native long btWorldImporter_createOptimizedBvh(long j, btWorldImporter btworldimporter);

    public static final native long btWorldImporter_createPlaneShape(long j, btWorldImporter btworldimporter, Vector3 vector3, float f);

    public static final native long btWorldImporter_createPoint2PointConstraint__SWIG_0(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2, Vector3 vector3, Vector3 vector4);

    public static final native long btWorldImporter_createPoint2PointConstraint__SWIG_1(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, Vector3 vector3);

    public static final native long btWorldImporter_createRigidBody(long j, btWorldImporter btworldimporter, boolean z, float f, Matrix4 matrix4, long j2, btCollisionShape btcollisionshape, String str);

    public static final native long btWorldImporter_createScaledTrangleMeshShape(long j, btWorldImporter btworldimporter, long j2, btBvhTriangleMeshShape btbvhtrianglemeshshape, Vector3 vector3);

    public static final native long btWorldImporter_createSliderConstraint__SWIG_0(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, long j3, btRigidBody btrigidbody2, Matrix4 matrix4, Matrix4 matrix5, boolean z);

    public static final native long btWorldImporter_createSliderConstraint__SWIG_1(long j, btWorldImporter btworldimporter, long j2, btRigidBody btrigidbody, Matrix4 matrix4, boolean z);

    public static final native long btWorldImporter_createSphereShape(long j, btWorldImporter btworldimporter, float f);

    public static final native long btWorldImporter_createStridingMeshInterfaceData(long j, btWorldImporter btworldimporter, long j2, btStridingMeshInterfaceData btstridingmeshinterfacedata);

    public static final native long btWorldImporter_createTriangleInfoMap(long j, btWorldImporter btworldimporter);

    public static final native long btWorldImporter_createTriangleMeshContainer(long j, btWorldImporter btworldimporter);

    public static final native void btWorldImporter_deleteAllData(long j, btWorldImporter btworldimporter);

    public static final native long btWorldImporter_getBvhByIndex(long j, btWorldImporter btworldimporter, int i);

    public static final native long btWorldImporter_getCollisionShapeByIndex(long j, btWorldImporter btworldimporter, int i);

    public static final native long btWorldImporter_getCollisionShapeByName(long j, btWorldImporter btworldimporter, String str);

    public static final native long btWorldImporter_getConstraintByIndex(long j, btWorldImporter btworldimporter, int i);

    public static final native long btWorldImporter_getConstraintByName(long j, btWorldImporter btworldimporter, String str);

    public static final native String btWorldImporter_getNameForPointer__SWIG_0(long j, btWorldImporter btworldimporter, long j2);

    public static final native int btWorldImporter_getNumBvhs(long j, btWorldImporter btworldimporter);

    public static final native int btWorldImporter_getNumCollisionShapes(long j, btWorldImporter btworldimporter);

    public static final native int btWorldImporter_getNumConstraints(long j, btWorldImporter btworldimporter);

    public static final native int btWorldImporter_getNumRigidBodies(long j, btWorldImporter btworldimporter);

    public static final native int btWorldImporter_getNumTriangleInfoMaps(long j, btWorldImporter btworldimporter);

    public static final native long btWorldImporter_getRigidBodyByIndex(long j, btWorldImporter btworldimporter, int i);

    public static final native long btWorldImporter_getRigidBodyByName(long j, btWorldImporter btworldimporter, String str);

    public static final native long btWorldImporter_getTriangleInfoMapByIndex(long j, btWorldImporter btworldimporter, int i);

    public static final native int btWorldImporter_getVerboseMode(long j, btWorldImporter btworldimporter);

    public static final native void btWorldImporter_setDynamicsWorldInfo(long j, btWorldImporter btworldimporter, Vector3 vector3, long j2, btContactSolverInfo btcontactsolverinfo);

    public static final native void btWorldImporter_setVerboseMode(long j, btWorldImporter btworldimporter, int i);

    public static final native int compareInverseAndForwardDynamics(long j, long j2, long j3, Vector3 vector3, boolean z, long j4, btMultiBody btmultibody, long j5, MultiBodyTree multiBodyTree, DoubleBuffer doubleBuffer, DoubleBuffer doubleBuffer2);

    public static final native void delete_CloneTreeCreator(long j);

    public static final native void delete_CoilCreator(long j);

    public static final native void delete_DillCreator(long j);

    public static final native void delete_MultiBodyNameMap(long j);

    public static final native void delete_MultiBodyTreeCreator(long j);

    public static final native void delete_RandomTreeCreator(long j);

    public static final native void delete_SimpleTreeCreator(long j);

    public static final native void delete_User2InternalIndex(long j);

    public static final native void delete_btBulletWorldImporter(long j);

    public static final native void delete_btMultiBodyTreeCreator(long j);

    public static final native void delete_btStringArray(long j);

    public static final native void delete_btStringArray_less(long j);

    public static final native void delete_btWorldImporter(long j);

    public static final native long new_CloneTreeCreator(long j, MultiBodyTree multiBodyTree);

    public static final native long new_CoilCreator(int i);

    public static final native long new_DillCreator(int i);

    public static final native long new_MultiBodyNameMap();

    public static final native long new_RandomTreeCreator__SWIG_0(int i, boolean z);

    public static final native long new_RandomTreeCreator__SWIG_1(int i);

    public static final native long new_SimpleTreeCreator(int i);

    public static final native long new_User2InternalIndex();

    public static final native long new_btBulletWorldImporter__SWIG_0(long j, btDynamicsWorld btdynamicsworld);

    public static final native long new_btBulletWorldImporter__SWIG_1();

    public static final native long new_btMultiBodyTreeCreator();

    public static final native long new_btStringArray__SWIG_0();

    public static final native long new_btStringArray__SWIG_1(long j, btStringArray btstringarray);

    public static final native long new_btStringArray_less();

    public static final native long new_btWorldImporter(long j, btDynamicsWorld btdynamicsworld);

    public static final native long randomAxis();

    public static final native float randomFloat(float f, float f2);

    public static final native long randomInertiaMatrix();

    public static final native long randomInertiaPrincipal();

    public static final native void randomInit__SWIG_0();

    public static final native void randomInit__SWIG_1(long j);

    public static final native int randomInt(int i, int i2);

    public static final native float randomMass();

    private static final native void swig_module_init();

    public static final native int writeGraphvizDotFile(long j, MultiBodyTree multiBodyTree, long j2, MultiBodyNameMap multiBodyNameMap, String str);

    public static void SwigDirector_btBulletWorldImporter_deleteAllData(btBulletWorldImporter btbulletworldimporter) {
        btbulletworldimporter.deleteAllData();
    }

    public static void SwigDirector_btBulletWorldImporter_setDynamicsWorldInfo(btBulletWorldImporter btbulletworldimporter, Vector3 vector3, long j) {
        btbulletworldimporter.setDynamicsWorldInfo(vector3, new btContactSolverInfo(j, false));
    }

    public static long SwigDirector_btBulletWorldImporter_createRigidBody(btBulletWorldImporter btbulletworldimporter, boolean z, float f, Matrix4 matrix4, long j, String str) {
        return btRigidBody.getCPtr(btbulletworldimporter.createRigidBody(z, f, matrix4, j == 0 ? null : new btCollisionShape(j, false), str));
    }

    public static long SwigDirector_btBulletWorldImporter_createCollisionObject(btBulletWorldImporter btbulletworldimporter, Matrix4 matrix4, long j, String str) {
        return btCollisionObject.getCPtr(btbulletworldimporter.createCollisionObject(matrix4, j == 0 ? null : new btCollisionShape(j, false), str));
    }

    public static long SwigDirector_btBulletWorldImporter_createPlaneShape(btBulletWorldImporter btbulletworldimporter, Vector3 vector3, float f) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createPlaneShape(vector3, f));
    }

    public static long SwigDirector_btBulletWorldImporter_createBoxShape(btBulletWorldImporter btbulletworldimporter, Vector3 vector3) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createBoxShape(vector3));
    }

    public static long SwigDirector_btBulletWorldImporter_createSphereShape(btBulletWorldImporter btbulletworldimporter, float f) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createSphereShape(f));
    }

    public static long SwigDirector_btBulletWorldImporter_createCapsuleShapeX(btBulletWorldImporter btbulletworldimporter, float f, float f2) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createCapsuleShapeX(f, f2));
    }

    public static long SwigDirector_btBulletWorldImporter_createCapsuleShapeY(btBulletWorldImporter btbulletworldimporter, float f, float f2) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createCapsuleShapeY(f, f2));
    }

    public static long SwigDirector_btBulletWorldImporter_createCapsuleShapeZ(btBulletWorldImporter btbulletworldimporter, float f, float f2) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createCapsuleShapeZ(f, f2));
    }

    public static long SwigDirector_btBulletWorldImporter_createCylinderShapeX(btBulletWorldImporter btbulletworldimporter, float f, float f2) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createCylinderShapeX(f, f2));
    }

    public static long SwigDirector_btBulletWorldImporter_createCylinderShapeY(btBulletWorldImporter btbulletworldimporter, float f, float f2) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createCylinderShapeY(f, f2));
    }

    public static long SwigDirector_btBulletWorldImporter_createCylinderShapeZ(btBulletWorldImporter btbulletworldimporter, float f, float f2) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createCylinderShapeZ(f, f2));
    }

    public static long SwigDirector_btBulletWorldImporter_createConeShapeX(btBulletWorldImporter btbulletworldimporter, float f, float f2) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createConeShapeX(f, f2));
    }

    public static long SwigDirector_btBulletWorldImporter_createConeShapeY(btBulletWorldImporter btbulletworldimporter, float f, float f2) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createConeShapeY(f, f2));
    }

    public static long SwigDirector_btBulletWorldImporter_createConeShapeZ(btBulletWorldImporter btbulletworldimporter, float f, float f2) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createConeShapeZ(f, f2));
    }

    public static long SwigDirector_btBulletWorldImporter_createTriangleMeshContainer(btBulletWorldImporter btbulletworldimporter) {
        return btTriangleIndexVertexArray.getCPtr(btbulletworldimporter.createTriangleMeshContainer());
    }

    public static long SwigDirector_btBulletWorldImporter_createBvhTriangleMeshShape(btBulletWorldImporter btbulletworldimporter, long j, long j2) {
        return btBvhTriangleMeshShape.getCPtr(btbulletworldimporter.createBvhTriangleMeshShape(j == 0 ? null : new btStridingMeshInterface(j, false), j2 != 0 ? new btOptimizedBvh(j2, false) : null));
    }

    public static long SwigDirector_btBulletWorldImporter_createConvexTriangleMeshShape(btBulletWorldImporter btbulletworldimporter, long j) {
        return btCollisionShape.getCPtr(btbulletworldimporter.createConvexTriangleMeshShape(j == 0 ? null : new btStridingMeshInterface(j, false)));
    }

    public static long SwigDirector_btBulletWorldImporter_createGimpactShape(btBulletWorldImporter btbulletworldimporter, long j) {
        return btGImpactMeshShape.getCPtr(btbulletworldimporter.createGimpactShape(j == 0 ? null : new btStridingMeshInterface(j, false)));
    }

    public static long SwigDirector_btBulletWorldImporter_createStridingMeshInterfaceData(btBulletWorldImporter btbulletworldimporter, long j) {
        return btStridingMeshInterfaceData.getCPtr(btbulletworldimporter.createStridingMeshInterfaceData(j == 0 ? null : new btStridingMeshInterfaceData(j, false)));
    }

    public static long SwigDirector_btBulletWorldImporter_createConvexHullShape(btBulletWorldImporter btbulletworldimporter) {
        return btConvexHullShape.getCPtr(btbulletworldimporter.createConvexHullShape());
    }

    public static long SwigDirector_btBulletWorldImporter_createCompoundShape(btBulletWorldImporter btbulletworldimporter) {
        return btCompoundShape.getCPtr(btbulletworldimporter.createCompoundShape());
    }

    public static long SwigDirector_btBulletWorldImporter_createScaledTrangleMeshShape(btBulletWorldImporter btbulletworldimporter, long j, Vector3 vector3) {
        return btScaledBvhTriangleMeshShape.getCPtr(btbulletworldimporter.createScaledTrangleMeshShape(j == 0 ? null : new btBvhTriangleMeshShape(j, false), vector3));
    }

    public static long SwigDirector_btBulletWorldImporter_createMeshInterface(btBulletWorldImporter btbulletworldimporter, long j) {
        return btTriangleIndexVertexArray.getCPtr(btbulletworldimporter.createMeshInterface(new btStridingMeshInterfaceData(j, false)));
    }

    public static long SwigDirector_btBulletWorldImporter_createOptimizedBvh(btBulletWorldImporter btbulletworldimporter) {
        return btOptimizedBvh.getCPtr(btbulletworldimporter.createOptimizedBvh());
    }

    public static long SwigDirector_btBulletWorldImporter_createTriangleInfoMap(btBulletWorldImporter btbulletworldimporter) {
        return btTriangleInfoMap.getCPtr(btbulletworldimporter.createTriangleInfoMap());
    }

    public static long SwigDirector_btBulletWorldImporter_createPoint2PointConstraint__SWIG_0(btBulletWorldImporter btbulletworldimporter, long j, long j2, Vector3 vector3, Vector3 vector4) {
        return btPoint2PointConstraint.getCPtr(btbulletworldimporter.createPoint2PointConstraint(new btRigidBody(j, false), new btRigidBody(j2, false), vector3, vector4));
    }

    public static long SwigDirector_btBulletWorldImporter_createPoint2PointConstraint__SWIG_1(btBulletWorldImporter btbulletworldimporter, long j, Vector3 vector3) {
        return btPoint2PointConstraint.getCPtr(btbulletworldimporter.createPoint2PointConstraint(new btRigidBody(j, false), vector3));
    }

    public static long SwigDirector_btBulletWorldImporter_createHingeConstraint__SWIG_0(btBulletWorldImporter btbulletworldimporter, long j, long j2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        return btHingeConstraint.getCPtr(btbulletworldimporter.createHingeConstraint(new btRigidBody(j, false), new btRigidBody(j2, false), matrix4, matrix5, z));
    }

    public static long SwigDirector_btBulletWorldImporter_createHingeConstraint__SWIG_1(btBulletWorldImporter btbulletworldimporter, long j, long j2, Matrix4 matrix4, Matrix4 matrix5) {
        return btHingeConstraint.getCPtr(btbulletworldimporter.createHingeConstraint(new btRigidBody(j, false), new btRigidBody(j2, false), matrix4, matrix5));
    }

    public static long SwigDirector_btBulletWorldImporter_createHingeConstraint__SWIG_2(btBulletWorldImporter btbulletworldimporter, long j, Matrix4 matrix4, boolean z) {
        return btHingeConstraint.getCPtr(btbulletworldimporter.createHingeConstraint(new btRigidBody(j, false), matrix4, z));
    }

    public static long SwigDirector_btBulletWorldImporter_createHingeConstraint__SWIG_3(btBulletWorldImporter btbulletworldimporter, long j, Matrix4 matrix4) {
        return btHingeConstraint.getCPtr(btbulletworldimporter.createHingeConstraint(new btRigidBody(j, false), matrix4));
    }

    public static long SwigDirector_btBulletWorldImporter_createConeTwistConstraint__SWIG_0(btBulletWorldImporter btbulletworldimporter, long j, long j2, Matrix4 matrix4, Matrix4 matrix5) {
        return btConeTwistConstraint.getCPtr(btbulletworldimporter.createConeTwistConstraint(new btRigidBody(j, false), new btRigidBody(j2, false), matrix4, matrix5));
    }

    public static long SwigDirector_btBulletWorldImporter_createConeTwistConstraint__SWIG_1(btBulletWorldImporter btbulletworldimporter, long j, Matrix4 matrix4) {
        return btConeTwistConstraint.getCPtr(btbulletworldimporter.createConeTwistConstraint(new btRigidBody(j, false), matrix4));
    }

    public static long SwigDirector_btBulletWorldImporter_createGeneric6DofConstraint__SWIG_0(btBulletWorldImporter btbulletworldimporter, long j, long j2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        return btGeneric6DofConstraint.getCPtr(btbulletworldimporter.createGeneric6DofConstraint(new btRigidBody(j, false), new btRigidBody(j2, false), matrix4, matrix5, z));
    }

    public static long SwigDirector_btBulletWorldImporter_createGeneric6DofConstraint__SWIG_1(btBulletWorldImporter btbulletworldimporter, long j, Matrix4 matrix4, boolean z) {
        return btGeneric6DofConstraint.getCPtr(btbulletworldimporter.createGeneric6DofConstraint(new btRigidBody(j, false), matrix4, z));
    }

    public static long SwigDirector_btBulletWorldImporter_createGeneric6DofSpringConstraint(btBulletWorldImporter btbulletworldimporter, long j, long j2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        return btGeneric6DofSpringConstraint.getCPtr(btbulletworldimporter.createGeneric6DofSpringConstraint(new btRigidBody(j, false), new btRigidBody(j2, false), matrix4, matrix5, z));
    }

    public static long SwigDirector_btBulletWorldImporter_createGeneric6DofSpring2Constraint(btBulletWorldImporter btbulletworldimporter, long j, long j2, Matrix4 matrix4, Matrix4 matrix5, int i) {
        return btGeneric6DofSpring2Constraint.getCPtr(btbulletworldimporter.createGeneric6DofSpring2Constraint(new btRigidBody(j, false), new btRigidBody(j2, false), matrix4, matrix5, i));
    }

    public static long SwigDirector_btBulletWorldImporter_createSliderConstraint__SWIG_0(btBulletWorldImporter btbulletworldimporter, long j, long j2, Matrix4 matrix4, Matrix4 matrix5, boolean z) {
        return btSliderConstraint.getCPtr(btbulletworldimporter.createSliderConstraint(new btRigidBody(j, false), new btRigidBody(j2, false), matrix4, matrix5, z));
    }

    public static long SwigDirector_btBulletWorldImporter_createSliderConstraint__SWIG_1(btBulletWorldImporter btbulletworldimporter, long j, Matrix4 matrix4, boolean z) {
        return btSliderConstraint.getCPtr(btbulletworldimporter.createSliderConstraint(new btRigidBody(j, false), matrix4, z));
    }

    public static long SwigDirector_btBulletWorldImporter_createGearConstraint(btBulletWorldImporter btbulletworldimporter, long j, long j2, Vector3 vector3, Vector3 vector4, float f) {
        return btGearConstraint.getCPtr(btbulletworldimporter.createGearConstraint(new btRigidBody(j, false), new btRigidBody(j2, false), vector3, vector4, f));
    }

    public static boolean SwigDirector_btBulletWorldImporter_convertAllObjects(btBulletWorldImporter btbulletworldimporter, long j) {
        return btbulletworldimporter.convertAllObjects(j == 0 ? null : new SWIGTYPE_p_bParse__btBulletFile(j, false));
    }

    static {
        swig_module_init();
    }
}
