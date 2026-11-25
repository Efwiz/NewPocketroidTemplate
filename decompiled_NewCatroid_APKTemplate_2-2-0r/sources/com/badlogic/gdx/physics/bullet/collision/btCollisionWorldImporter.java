package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btBulletSerializedArrays;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionWorldImporter extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btCollisionWorldImporter(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionWorldImporter(long j, boolean z) {
        this("btCollisionWorldImporter", j, z);
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

    public static long getCPtr(btCollisionWorldImporter btcollisionworldimporter) {
        if (btcollisionworldimporter == null) {
            return 0L;
        }
        return btcollisionworldimporter.swigCPtr;
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
                CollisionJNI.delete_btCollisionWorldImporter(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btCollisionWorldImporter(btCollisionWorld btcollisionworld) {
        this(CollisionJNI.new_btCollisionWorldImporter(btCollisionWorld.getCPtr(btcollisionworld), btcollisionworld), true);
    }

    public boolean convertAllObjects(btBulletSerializedArrays btbulletserializedarrays) {
        return CollisionJNI.btCollisionWorldImporter_convertAllObjects(this.swigCPtr, this, btBulletSerializedArrays.getCPtr(btbulletserializedarrays), btbulletserializedarrays);
    }

    public void deleteAllData() {
        CollisionJNI.btCollisionWorldImporter_deleteAllData(this.swigCPtr, this);
    }

    public void setVerboseMode(int i) {
        CollisionJNI.btCollisionWorldImporter_setVerboseMode(this.swigCPtr, this, i);
    }

    public int getVerboseMode() {
        return CollisionJNI.btCollisionWorldImporter_getVerboseMode(this.swigCPtr, this);
    }

    public int getNumCollisionShapes() {
        return CollisionJNI.btCollisionWorldImporter_getNumCollisionShapes(this.swigCPtr, this);
    }

    public btCollisionShape getCollisionShapeByIndex(int i) {
        long jBtCollisionWorldImporter_getCollisionShapeByIndex = CollisionJNI.btCollisionWorldImporter_getCollisionShapeByIndex(this.swigCPtr, this, i);
        if (jBtCollisionWorldImporter_getCollisionShapeByIndex == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_getCollisionShapeByIndex, false);
    }

    public int getNumRigidBodies() {
        return CollisionJNI.btCollisionWorldImporter_getNumRigidBodies(this.swigCPtr, this);
    }

    public btCollisionObject getRigidBodyByIndex(int i) {
        return btCollisionObject.getInstance(CollisionJNI.btCollisionWorldImporter_getRigidBodyByIndex(this.swigCPtr, this, i), false);
    }

    public int getNumBvhs() {
        return CollisionJNI.btCollisionWorldImporter_getNumBvhs(this.swigCPtr, this);
    }

    public btOptimizedBvh getBvhByIndex(int i) {
        long jBtCollisionWorldImporter_getBvhByIndex = CollisionJNI.btCollisionWorldImporter_getBvhByIndex(this.swigCPtr, this, i);
        if (jBtCollisionWorldImporter_getBvhByIndex == 0) {
            return null;
        }
        return new btOptimizedBvh(jBtCollisionWorldImporter_getBvhByIndex, false);
    }

    public int getNumTriangleInfoMaps() {
        return CollisionJNI.btCollisionWorldImporter_getNumTriangleInfoMaps(this.swigCPtr, this);
    }

    public btTriangleInfoMap getTriangleInfoMapByIndex(int i) {
        long jBtCollisionWorldImporter_getTriangleInfoMapByIndex = CollisionJNI.btCollisionWorldImporter_getTriangleInfoMapByIndex(this.swigCPtr, this, i);
        if (jBtCollisionWorldImporter_getTriangleInfoMapByIndex == 0) {
            return null;
        }
        return new btTriangleInfoMap(jBtCollisionWorldImporter_getTriangleInfoMapByIndex, false);
    }

    public btCollisionShape getCollisionShapeByName(String str) {
        long jBtCollisionWorldImporter_getCollisionShapeByName = CollisionJNI.btCollisionWorldImporter_getCollisionShapeByName(this.swigCPtr, this, str);
        if (jBtCollisionWorldImporter_getCollisionShapeByName == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_getCollisionShapeByName, false);
    }

    public btCollisionObject getCollisionObjectByName(String str) {
        return btCollisionObject.getInstance(CollisionJNI.btCollisionWorldImporter_getCollisionObjectByName(this.swigCPtr, this, str), false);
    }

    public String getNameForPointer(long j) {
        return CollisionJNI.btCollisionWorldImporter_getNameForPointer(this.swigCPtr, this, j);
    }

    public btCollisionObject createCollisionObject(Matrix4 matrix4, btCollisionShape btcollisionshape, String str) {
        return btCollisionObject.getInstance(CollisionJNI.btCollisionWorldImporter_createCollisionObject(this.swigCPtr, this, matrix4, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, str), false);
    }

    public btCollisionShape createPlaneShape(Vector3 vector3, float f) {
        long jBtCollisionWorldImporter_createPlaneShape = CollisionJNI.btCollisionWorldImporter_createPlaneShape(this.swigCPtr, this, vector3, f);
        if (jBtCollisionWorldImporter_createPlaneShape == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createPlaneShape, false);
    }

    public btCollisionShape createBoxShape(Vector3 vector3) {
        long jBtCollisionWorldImporter_createBoxShape = CollisionJNI.btCollisionWorldImporter_createBoxShape(this.swigCPtr, this, vector3);
        if (jBtCollisionWorldImporter_createBoxShape == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createBoxShape, false);
    }

    public btCollisionShape createSphereShape(float f) {
        long jBtCollisionWorldImporter_createSphereShape = CollisionJNI.btCollisionWorldImporter_createSphereShape(this.swigCPtr, this, f);
        if (jBtCollisionWorldImporter_createSphereShape == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createSphereShape, false);
    }

    public btCollisionShape createCapsuleShapeX(float f, float f2) {
        long jBtCollisionWorldImporter_createCapsuleShapeX = CollisionJNI.btCollisionWorldImporter_createCapsuleShapeX(this.swigCPtr, this, f, f2);
        if (jBtCollisionWorldImporter_createCapsuleShapeX == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createCapsuleShapeX, false);
    }

    public btCollisionShape createCapsuleShapeY(float f, float f2) {
        long jBtCollisionWorldImporter_createCapsuleShapeY = CollisionJNI.btCollisionWorldImporter_createCapsuleShapeY(this.swigCPtr, this, f, f2);
        if (jBtCollisionWorldImporter_createCapsuleShapeY == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createCapsuleShapeY, false);
    }

    public btCollisionShape createCapsuleShapeZ(float f, float f2) {
        long jBtCollisionWorldImporter_createCapsuleShapeZ = CollisionJNI.btCollisionWorldImporter_createCapsuleShapeZ(this.swigCPtr, this, f, f2);
        if (jBtCollisionWorldImporter_createCapsuleShapeZ == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createCapsuleShapeZ, false);
    }

    public btCollisionShape createCylinderShapeX(float f, float f2) {
        long jBtCollisionWorldImporter_createCylinderShapeX = CollisionJNI.btCollisionWorldImporter_createCylinderShapeX(this.swigCPtr, this, f, f2);
        if (jBtCollisionWorldImporter_createCylinderShapeX == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createCylinderShapeX, false);
    }

    public btCollisionShape createCylinderShapeY(float f, float f2) {
        long jBtCollisionWorldImporter_createCylinderShapeY = CollisionJNI.btCollisionWorldImporter_createCylinderShapeY(this.swigCPtr, this, f, f2);
        if (jBtCollisionWorldImporter_createCylinderShapeY == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createCylinderShapeY, false);
    }

    public btCollisionShape createCylinderShapeZ(float f, float f2) {
        long jBtCollisionWorldImporter_createCylinderShapeZ = CollisionJNI.btCollisionWorldImporter_createCylinderShapeZ(this.swigCPtr, this, f, f2);
        if (jBtCollisionWorldImporter_createCylinderShapeZ == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createCylinderShapeZ, false);
    }

    public btCollisionShape createConeShapeX(float f, float f2) {
        long jBtCollisionWorldImporter_createConeShapeX = CollisionJNI.btCollisionWorldImporter_createConeShapeX(this.swigCPtr, this, f, f2);
        if (jBtCollisionWorldImporter_createConeShapeX == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createConeShapeX, false);
    }

    public btCollisionShape createConeShapeY(float f, float f2) {
        long jBtCollisionWorldImporter_createConeShapeY = CollisionJNI.btCollisionWorldImporter_createConeShapeY(this.swigCPtr, this, f, f2);
        if (jBtCollisionWorldImporter_createConeShapeY == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createConeShapeY, false);
    }

    public btCollisionShape createConeShapeZ(float f, float f2) {
        long jBtCollisionWorldImporter_createConeShapeZ = CollisionJNI.btCollisionWorldImporter_createConeShapeZ(this.swigCPtr, this, f, f2);
        if (jBtCollisionWorldImporter_createConeShapeZ == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createConeShapeZ, false);
    }

    public btTriangleIndexVertexArray createTriangleMeshContainer() {
        long jBtCollisionWorldImporter_createTriangleMeshContainer = CollisionJNI.btCollisionWorldImporter_createTriangleMeshContainer(this.swigCPtr, this);
        if (jBtCollisionWorldImporter_createTriangleMeshContainer == 0) {
            return null;
        }
        return new btTriangleIndexVertexArray(jBtCollisionWorldImporter_createTriangleMeshContainer, false);
    }

    public btBvhTriangleMeshShape createBvhTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface, btOptimizedBvh btoptimizedbvh) {
        long jBtCollisionWorldImporter_createBvhTriangleMeshShape = CollisionJNI.btCollisionWorldImporter_createBvhTriangleMeshShape(this.swigCPtr, this, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, btOptimizedBvh.getCPtr(btoptimizedbvh), btoptimizedbvh);
        if (jBtCollisionWorldImporter_createBvhTriangleMeshShape == 0) {
            return null;
        }
        return new btBvhTriangleMeshShape(jBtCollisionWorldImporter_createBvhTriangleMeshShape, false);
    }

    public btCollisionShape createConvexTriangleMeshShape(btStridingMeshInterface btstridingmeshinterface) {
        long jBtCollisionWorldImporter_createConvexTriangleMeshShape = CollisionJNI.btCollisionWorldImporter_createConvexTriangleMeshShape(this.swigCPtr, this, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface);
        if (jBtCollisionWorldImporter_createConvexTriangleMeshShape == 0) {
            return null;
        }
        return btCollisionShape.newDerivedObject(jBtCollisionWorldImporter_createConvexTriangleMeshShape, false);
    }

    public btStridingMeshInterfaceData createStridingMeshInterfaceData(btStridingMeshInterfaceData btstridingmeshinterfacedata) {
        long jBtCollisionWorldImporter_createStridingMeshInterfaceData = CollisionJNI.btCollisionWorldImporter_createStridingMeshInterfaceData(this.swigCPtr, this, btStridingMeshInterfaceData.getCPtr(btstridingmeshinterfacedata), btstridingmeshinterfacedata);
        if (jBtCollisionWorldImporter_createStridingMeshInterfaceData == 0) {
            return null;
        }
        return new btStridingMeshInterfaceData(jBtCollisionWorldImporter_createStridingMeshInterfaceData, false);
    }

    public btConvexHullShape createConvexHullShape() {
        long jBtCollisionWorldImporter_createConvexHullShape = CollisionJNI.btCollisionWorldImporter_createConvexHullShape(this.swigCPtr, this);
        if (jBtCollisionWorldImporter_createConvexHullShape == 0) {
            return null;
        }
        return new btConvexHullShape(jBtCollisionWorldImporter_createConvexHullShape, false);
    }

    public btCompoundShape createCompoundShape() {
        long jBtCollisionWorldImporter_createCompoundShape = CollisionJNI.btCollisionWorldImporter_createCompoundShape(this.swigCPtr, this);
        if (jBtCollisionWorldImporter_createCompoundShape == 0) {
            return null;
        }
        return new btCompoundShape(jBtCollisionWorldImporter_createCompoundShape, false);
    }

    public btScaledBvhTriangleMeshShape createScaledTrangleMeshShape(btBvhTriangleMeshShape btbvhtrianglemeshshape, Vector3 vector3) {
        long jBtCollisionWorldImporter_createScaledTrangleMeshShape = CollisionJNI.btCollisionWorldImporter_createScaledTrangleMeshShape(this.swigCPtr, this, btBvhTriangleMeshShape.getCPtr(btbvhtrianglemeshshape), btbvhtrianglemeshshape, vector3);
        if (jBtCollisionWorldImporter_createScaledTrangleMeshShape == 0) {
            return null;
        }
        return new btScaledBvhTriangleMeshShape(jBtCollisionWorldImporter_createScaledTrangleMeshShape, false);
    }

    public btMultiSphereShape createMultiSphereShape(btVector3 btvector3, FloatBuffer floatBuffer, int i) {
        long jBtCollisionWorldImporter_createMultiSphereShape = CollisionJNI.btCollisionWorldImporter_createMultiSphereShape(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, floatBuffer, i);
        if (jBtCollisionWorldImporter_createMultiSphereShape == 0) {
            return null;
        }
        return new btMultiSphereShape(jBtCollisionWorldImporter_createMultiSphereShape, false);
    }

    public btTriangleIndexVertexArray createMeshInterface(btStridingMeshInterfaceData btstridingmeshinterfacedata) {
        long jBtCollisionWorldImporter_createMeshInterface = CollisionJNI.btCollisionWorldImporter_createMeshInterface(this.swigCPtr, this, btStridingMeshInterfaceData.getCPtr(btstridingmeshinterfacedata), btstridingmeshinterfacedata);
        if (jBtCollisionWorldImporter_createMeshInterface == 0) {
            return null;
        }
        return new btTriangleIndexVertexArray(jBtCollisionWorldImporter_createMeshInterface, false);
    }

    public btOptimizedBvh createOptimizedBvh() {
        long jBtCollisionWorldImporter_createOptimizedBvh = CollisionJNI.btCollisionWorldImporter_createOptimizedBvh(this.swigCPtr, this);
        if (jBtCollisionWorldImporter_createOptimizedBvh == 0) {
            return null;
        }
        return new btOptimizedBvh(jBtCollisionWorldImporter_createOptimizedBvh, false);
    }

    public btTriangleInfoMap createTriangleInfoMap() {
        long jBtCollisionWorldImporter_createTriangleInfoMap = CollisionJNI.btCollisionWorldImporter_createTriangleInfoMap(this.swigCPtr, this);
        if (jBtCollisionWorldImporter_createTriangleInfoMap == 0) {
            return null;
        }
        return new btTriangleInfoMap(jBtCollisionWorldImporter_createTriangleInfoMap, false);
    }
}
