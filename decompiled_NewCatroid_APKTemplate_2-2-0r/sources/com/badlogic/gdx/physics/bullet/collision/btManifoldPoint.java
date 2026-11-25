package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btManifoldPoint extends BulletBase {
    private long swigCPtr;
    private static final btManifoldPoint temp = new btManifoldPoint(0, false);
    private static btManifoldPoint[] argumentInstances = {new btManifoldPoint(0, false), new btManifoldPoint(0, false), new btManifoldPoint(0, false), new btManifoldPoint(0, false)};
    private static int argumentIndex = -1;

    protected btManifoldPoint(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btManifoldPoint(long j, boolean z) {
        this("btManifoldPoint", j, z);
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

    public static long getCPtr(btManifoldPoint btmanifoldpoint) {
        if (btmanifoldpoint == null) {
            return 0L;
        }
        return btmanifoldpoint.swigCPtr;
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
                CollisionJNI.delete_btManifoldPoint(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    protected static btManifoldPoint obtainTemp(long j, boolean z) {
        btManifoldPoint btmanifoldpoint = temp;
        btmanifoldpoint.reset(j, z);
        return btmanifoldpoint;
    }

    protected static btManifoldPoint obtainForArgument(long j, boolean z) {
        btManifoldPoint[] btmanifoldpointArr = argumentInstances;
        int i = (argumentIndex + 1) & 3;
        argumentIndex = i;
        btManifoldPoint btmanifoldpoint = btmanifoldpointArr[i];
        btmanifoldpoint.reset(j, z);
        return btmanifoldpoint;
    }

    public btManifoldPoint() {
        this(CollisionJNI.new_btManifoldPoint__SWIG_0(), true);
    }

    public btManifoldPoint(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f) {
        this(CollisionJNI.new_btManifoldPoint__SWIG_1(vector3, vector4, vector5, f), true);
    }

    public void setDistance1(float f) {
        CollisionJNI.btManifoldPoint_distance1_set(this.swigCPtr, this, f);
    }

    public float getDistance1() {
        return CollisionJNI.btManifoldPoint_distance1_get(this.swigCPtr, this);
    }

    public void setCombinedFriction(float f) {
        CollisionJNI.btManifoldPoint_combinedFriction_set(this.swigCPtr, this, f);
    }

    public float getCombinedFriction() {
        return CollisionJNI.btManifoldPoint_combinedFriction_get(this.swigCPtr, this);
    }

    public void setCombinedRollingFriction(float f) {
        CollisionJNI.btManifoldPoint_combinedRollingFriction_set(this.swigCPtr, this, f);
    }

    public float getCombinedRollingFriction() {
        return CollisionJNI.btManifoldPoint_combinedRollingFriction_get(this.swigCPtr, this);
    }

    public void setCombinedSpinningFriction(float f) {
        CollisionJNI.btManifoldPoint_combinedSpinningFriction_set(this.swigCPtr, this, f);
    }

    public float getCombinedSpinningFriction() {
        return CollisionJNI.btManifoldPoint_combinedSpinningFriction_get(this.swigCPtr, this);
    }

    public void setCombinedRestitution(float f) {
        CollisionJNI.btManifoldPoint_combinedRestitution_set(this.swigCPtr, this, f);
    }

    public float getCombinedRestitution() {
        return CollisionJNI.btManifoldPoint_combinedRestitution_get(this.swigCPtr, this);
    }

    public void setPartId0(int i) {
        CollisionJNI.btManifoldPoint_partId0_set(this.swigCPtr, this, i);
    }

    public int getPartId0() {
        return CollisionJNI.btManifoldPoint_partId0_get(this.swigCPtr, this);
    }

    public void setPartId1(int i) {
        CollisionJNI.btManifoldPoint_partId1_set(this.swigCPtr, this, i);
    }

    public int getPartId1() {
        return CollisionJNI.btManifoldPoint_partId1_get(this.swigCPtr, this);
    }

    public void setIndex0(int i) {
        CollisionJNI.btManifoldPoint_index0_set(this.swigCPtr, this, i);
    }

    public int getIndex0() {
        return CollisionJNI.btManifoldPoint_index0_get(this.swigCPtr, this);
    }

    public void setIndex1(int i) {
        CollisionJNI.btManifoldPoint_index1_set(this.swigCPtr, this, i);
    }

    public int getIndex1() {
        return CollisionJNI.btManifoldPoint_index1_get(this.swigCPtr, this);
    }

    public void setUserPersistentData(long j) {
        CollisionJNI.btManifoldPoint_userPersistentData_set(this.swigCPtr, this, j);
    }

    public long getUserPersistentData() {
        return CollisionJNI.btManifoldPoint_userPersistentData_get(this.swigCPtr, this);
    }

    public void setContactPointFlags(int i) {
        CollisionJNI.btManifoldPoint_contactPointFlags_set(this.swigCPtr, this, i);
    }

    public int getContactPointFlags() {
        return CollisionJNI.btManifoldPoint_contactPointFlags_get(this.swigCPtr, this);
    }

    public void setAppliedImpulse(float f) {
        CollisionJNI.btManifoldPoint_appliedImpulse_set(this.swigCPtr, this, f);
    }

    public float getAppliedImpulse() {
        return CollisionJNI.btManifoldPoint_appliedImpulse_get(this.swigCPtr, this);
    }

    public void setAppliedImpulseLateral1(float f) {
        CollisionJNI.btManifoldPoint_appliedImpulseLateral1_set(this.swigCPtr, this, f);
    }

    public float getAppliedImpulseLateral1() {
        return CollisionJNI.btManifoldPoint_appliedImpulseLateral1_get(this.swigCPtr, this);
    }

    public void setAppliedImpulseLateral2(float f) {
        CollisionJNI.btManifoldPoint_appliedImpulseLateral2_set(this.swigCPtr, this, f);
    }

    public float getAppliedImpulseLateral2() {
        return CollisionJNI.btManifoldPoint_appliedImpulseLateral2_get(this.swigCPtr, this);
    }

    public void setContactMotion1(float f) {
        CollisionJNI.btManifoldPoint_contactMotion1_set(this.swigCPtr, this, f);
    }

    public float getContactMotion1() {
        return CollisionJNI.btManifoldPoint_contactMotion1_get(this.swigCPtr, this);
    }

    public void setContactMotion2(float f) {
        CollisionJNI.btManifoldPoint_contactMotion2_set(this.swigCPtr, this, f);
    }

    public float getContactMotion2() {
        return CollisionJNI.btManifoldPoint_contactMotion2_get(this.swigCPtr, this);
    }

    public void setContactCFM(float f) {
        CollisionJNI.btManifoldPoint_contactCFM_set(this.swigCPtr, this, f);
    }

    public float getContactCFM() {
        return CollisionJNI.btManifoldPoint_contactCFM_get(this.swigCPtr, this);
    }

    public void setCombinedContactStiffness1(float f) {
        CollisionJNI.btManifoldPoint_combinedContactStiffness1_set(this.swigCPtr, this, f);
    }

    public float getCombinedContactStiffness1() {
        return CollisionJNI.btManifoldPoint_combinedContactStiffness1_get(this.swigCPtr, this);
    }

    public void setContactERP(float f) {
        CollisionJNI.btManifoldPoint_contactERP_set(this.swigCPtr, this, f);
    }

    public float getContactERP() {
        return CollisionJNI.btManifoldPoint_contactERP_get(this.swigCPtr, this);
    }

    public void setCombinedContactDamping1(float f) {
        CollisionJNI.btManifoldPoint_combinedContactDamping1_set(this.swigCPtr, this, f);
    }

    public float getCombinedContactDamping1() {
        return CollisionJNI.btManifoldPoint_combinedContactDamping1_get(this.swigCPtr, this);
    }

    public void setFrictionCFM(float f) {
        CollisionJNI.btManifoldPoint_frictionCFM_set(this.swigCPtr, this, f);
    }

    public float getFrictionCFM() {
        return CollisionJNI.btManifoldPoint_frictionCFM_get(this.swigCPtr, this);
    }

    public void setLifeTime(int i) {
        CollisionJNI.btManifoldPoint_lifeTime_set(this.swigCPtr, this, i);
    }

    public int getLifeTime() {
        return CollisionJNI.btManifoldPoint_lifeTime_get(this.swigCPtr, this);
    }

    public float getDistance() {
        return CollisionJNI.btManifoldPoint_getDistance(this.swigCPtr, this);
    }

    public void setDistance(float f) {
        CollisionJNI.btManifoldPoint_setDistance(this.swigCPtr, this, f);
    }

    public int getUserValue() {
        return CollisionJNI.btManifoldPoint_getUserValue(this.swigCPtr, this);
    }

    public void setUserValue(int i) {
        CollisionJNI.btManifoldPoint_setUserValue(this.swigCPtr, this, i);
    }

    public void getLocalPointA(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_getLocalPointA(this.swigCPtr, this, vector3);
    }

    public void setLocalPointA(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_setLocalPointA(this.swigCPtr, this, vector3);
    }

    public void getLocalPointB(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_getLocalPointB(this.swigCPtr, this, vector3);
    }

    public void setLocalPointB(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_setLocalPointB(this.swigCPtr, this, vector3);
    }

    public void getPositionWorldOnA(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_getPositionWorldOnA(this.swigCPtr, this, vector3);
    }

    public void setPositionWorldOnA(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_setPositionWorldOnA(this.swigCPtr, this, vector3);
    }

    public void getPositionWorldOnB(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_getPositionWorldOnB(this.swigCPtr, this, vector3);
    }

    public void setPositionWorldOnB(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_setPositionWorldOnB(this.swigCPtr, this, vector3);
    }

    public void getNormalWorldOnB(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_getNormalWorldOnB(this.swigCPtr, this, vector3);
    }

    public void setNormalWorldOnB(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_setNormalWorldOnB(this.swigCPtr, this, vector3);
    }

    public void getLateralFrictionDir1(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_getLateralFrictionDir1(this.swigCPtr, this, vector3);
    }

    public void setLateralFrictionDir1(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_setLateralFrictionDir1(this.swigCPtr, this, vector3);
    }

    public void getLateralFrictionDir2(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_getLateralFrictionDir2(this.swigCPtr, this, vector3);
    }

    public void setLateralFrictionDir2(Vector3 vector3) {
        CollisionJNI.btManifoldPoint_setLateralFrictionDir2(this.swigCPtr, this, vector3);
    }
}
