package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btScalarArray;
import com.badlogic.gdx.physics.bullet.linearmath.btSerializer;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3Array;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBody extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btMultiBody(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMultiBody(long j, boolean z) {
        this("btMultiBody", j, z);
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

    public static long getCPtr(btMultiBody btmultibody) {
        if (btmultibody == null) {
            return 0L;
        }
        return btmultibody.swigCPtr;
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
                DynamicsJNI.delete_btMultiBody(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return DynamicsJNI.btMultiBody_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        DynamicsJNI.btMultiBody_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btMultiBody_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btMultiBody_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return DynamicsJNI.btMultiBody_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        DynamicsJNI.btMultiBody_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btMultiBody_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btMultiBody_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btMultiBody(int i, float f, Vector3 vector3, boolean z, boolean z2, boolean z3) {
        this(DynamicsJNI.new_btMultiBody__SWIG_0(i, f, vector3, z, z2, z3), true);
    }

    public btMultiBody(int i, float f, Vector3 vector3, boolean z, boolean z2) {
        this(DynamicsJNI.new_btMultiBody__SWIG_1(i, f, vector3, z, z2), true);
    }

    public void setupFixed(int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, boolean z) {
        DynamicsJNI.btMultiBody_setupFixed__SWIG_0(this.swigCPtr, this, i, f, vector3, i2, quaternion, vector4, vector5, z);
    }

    public void setupFixed(int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5) {
        DynamicsJNI.btMultiBody_setupFixed__SWIG_1(this.swigCPtr, this, i, f, vector3, i2, quaternion, vector4, vector5);
    }

    public void setupPrismatic(int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, Vector3 vector6, boolean z) {
        DynamicsJNI.btMultiBody_setupPrismatic(this.swigCPtr, this, i, f, vector3, i2, quaternion, vector4, vector5, vector6, z);
    }

    public void setupRevolute(int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, Vector3 vector6, boolean z) {
        DynamicsJNI.btMultiBody_setupRevolute__SWIG_0(this.swigCPtr, this, i, f, vector3, i2, quaternion, vector4, vector5, vector6, z);
    }

    public void setupRevolute(int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        DynamicsJNI.btMultiBody_setupRevolute__SWIG_1(this.swigCPtr, this, i, f, vector3, i2, quaternion, vector4, vector5, vector6);
    }

    public void setupSpherical(int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, boolean z) {
        DynamicsJNI.btMultiBody_setupSpherical__SWIG_0(this.swigCPtr, this, i, f, vector3, i2, quaternion, vector4, vector5, z);
    }

    public void setupSpherical(int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5) {
        DynamicsJNI.btMultiBody_setupSpherical__SWIG_1(this.swigCPtr, this, i, f, vector3, i2, quaternion, vector4, vector5);
    }

    public void setupPlanar(int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5, boolean z) {
        DynamicsJNI.btMultiBody_setupPlanar__SWIG_0(this.swigCPtr, this, i, f, vector3, i2, quaternion, vector4, vector5, z);
    }

    public void setupPlanar(int i, float f, Vector3 vector3, int i2, Quaternion quaternion, Vector3 vector4, Vector3 vector5) {
        DynamicsJNI.btMultiBody_setupPlanar__SWIG_1(this.swigCPtr, this, i, f, vector3, i2, quaternion, vector4, vector5);
    }

    public btMultibodyLink getLinkConst(int i) {
        return new btMultibodyLink(DynamicsJNI.btMultiBody_getLinkConst(this.swigCPtr, this, i), false);
    }

    public btMultibodyLink getLink(int i) {
        return new btMultibodyLink(DynamicsJNI.btMultiBody_getLink(this.swigCPtr, this, i), false);
    }

    public void setBaseCollider(btMultiBodyLinkCollider btmultibodylinkcollider) {
        DynamicsJNI.btMultiBody_setBaseCollider(this.swigCPtr, this, btMultiBodyLinkCollider.getCPtr(btmultibodylinkcollider), btmultibodylinkcollider);
    }

    public btMultiBodyLinkCollider getBaseColliderConst() {
        long jBtMultiBody_getBaseColliderConst = DynamicsJNI.btMultiBody_getBaseColliderConst(this.swigCPtr, this);
        if (jBtMultiBody_getBaseColliderConst == 0) {
            return null;
        }
        return new btMultiBodyLinkCollider(jBtMultiBody_getBaseColliderConst, false);
    }

    public btMultiBodyLinkCollider getBaseCollider() {
        long jBtMultiBody_getBaseCollider = DynamicsJNI.btMultiBody_getBaseCollider(this.swigCPtr, this);
        if (jBtMultiBody_getBaseCollider == 0) {
            return null;
        }
        return new btMultiBodyLinkCollider(jBtMultiBody_getBaseCollider, false);
    }

    public btMultiBodyLinkCollider getLinkCollider(int i) {
        long jBtMultiBody_getLinkCollider = DynamicsJNI.btMultiBody_getLinkCollider(this.swigCPtr, this, i);
        if (jBtMultiBody_getLinkCollider == 0) {
            return null;
        }
        return new btMultiBodyLinkCollider(jBtMultiBody_getLinkCollider, false);
    }

    public int getParent(int i) {
        return DynamicsJNI.btMultiBody_getParent(this.swigCPtr, this, i);
    }

    public int getNumLinks() {
        return DynamicsJNI.btMultiBody_getNumLinks(this.swigCPtr, this);
    }

    public int getNumDofs() {
        return DynamicsJNI.btMultiBody_getNumDofs(this.swigCPtr, this);
    }

    public int getNumPosVars() {
        return DynamicsJNI.btMultiBody_getNumPosVars(this.swigCPtr, this);
    }

    public float getBaseMass() {
        return DynamicsJNI.btMultiBody_getBaseMass(this.swigCPtr, this);
    }

    public Vector3 getBaseInertia() {
        return DynamicsJNI.btMultiBody_getBaseInertia(this.swigCPtr, this);
    }

    public float getLinkMass(int i) {
        return DynamicsJNI.btMultiBody_getLinkMass(this.swigCPtr, this, i);
    }

    public Vector3 getLinkInertia(int i) {
        return DynamicsJNI.btMultiBody_getLinkInertia(this.swigCPtr, this, i);
    }

    public void setBaseMass(float f) {
        DynamicsJNI.btMultiBody_setBaseMass(this.swigCPtr, this, f);
    }

    public void setBaseInertia(Vector3 vector3) {
        DynamicsJNI.btMultiBody_setBaseInertia(this.swigCPtr, this, vector3);
    }

    public Vector3 getBasePos() {
        return DynamicsJNI.btMultiBody_getBasePos(this.swigCPtr, this);
    }

    public Vector3 getBaseVel() {
        return DynamicsJNI.btMultiBody_getBaseVel(this.swigCPtr, this);
    }

    public Quaternion getWorldToBaseRot() {
        return DynamicsJNI.btMultiBody_getWorldToBaseRot(this.swigCPtr, this);
    }

    public Vector3 getBaseOmega() {
        return DynamicsJNI.btMultiBody_getBaseOmega(this.swigCPtr, this);
    }

    public void setBasePos(Vector3 vector3) {
        DynamicsJNI.btMultiBody_setBasePos(this.swigCPtr, this, vector3);
    }

    public void setBaseWorldTransform(Matrix4 matrix4) {
        DynamicsJNI.btMultiBody_setBaseWorldTransform(this.swigCPtr, this, matrix4);
    }

    public Matrix4 getBaseWorldTransform() {
        return DynamicsJNI.btMultiBody_getBaseWorldTransform(this.swigCPtr, this);
    }

    public void setBaseVel(Vector3 vector3) {
        DynamicsJNI.btMultiBody_setBaseVel(this.swigCPtr, this, vector3);
    }

    public void setWorldToBaseRot(Quaternion quaternion) {
        DynamicsJNI.btMultiBody_setWorldToBaseRot(this.swigCPtr, this, quaternion);
    }

    public void setBaseOmega(Vector3 vector3) {
        DynamicsJNI.btMultiBody_setBaseOmega(this.swigCPtr, this, vector3);
    }

    public float getJointPos(int i) {
        return DynamicsJNI.btMultiBody_getJointPos(this.swigCPtr, this, i);
    }

    public float getJointVel(int i) {
        return DynamicsJNI.btMultiBody_getJointVel(this.swigCPtr, this, i);
    }

    public FloatBuffer getJointVelMultiDof(int i) {
        return DynamicsJNI.btMultiBody_getJointVelMultiDof(this.swigCPtr, this, i);
    }

    public FloatBuffer getJointPosMultiDof(int i) {
        return DynamicsJNI.btMultiBody_getJointPosMultiDof(this.swigCPtr, this, i);
    }

    public FloatBuffer getJointVelMultiDofConst(int i) {
        return DynamicsJNI.btMultiBody_getJointVelMultiDofConst(this.swigCPtr, this, i);
    }

    public FloatBuffer getJointPosMultiDofConst(int i) {
        return DynamicsJNI.btMultiBody_getJointPosMultiDofConst(this.swigCPtr, this, i);
    }

    public void setJointPos(int i, float f) {
        DynamicsJNI.btMultiBody_setJointPos(this.swigCPtr, this, i, f);
    }

    public void setJointVel(int i, float f) {
        DynamicsJNI.btMultiBody_setJointVel(this.swigCPtr, this, i, f);
    }

    public void setJointPosMultiDof(int i, FloatBuffer floatBuffer) {
        DynamicsJNI.btMultiBody_setJointPosMultiDof(this.swigCPtr, this, i, floatBuffer);
    }

    public void setJointVelMultiDof(int i, FloatBuffer floatBuffer) {
        DynamicsJNI.btMultiBody_setJointVelMultiDof(this.swigCPtr, this, i, floatBuffer);
    }

    public FloatBuffer getVelocityVector() {
        return DynamicsJNI.btMultiBody_getVelocityVector(this.swigCPtr, this);
    }

    public Vector3 getRVector(int i) {
        return DynamicsJNI.btMultiBody_getRVector(this.swigCPtr, this, i);
    }

    public Quaternion getParentToLocalRot(int i) {
        return DynamicsJNI.btMultiBody_getParentToLocalRot(this.swigCPtr, this, i);
    }

    public Vector3 localPosToWorld(int i, Vector3 vector3) {
        return DynamicsJNI.btMultiBody_localPosToWorld(this.swigCPtr, this, i, vector3);
    }

    public Vector3 localDirToWorld(int i, Vector3 vector3) {
        return DynamicsJNI.btMultiBody_localDirToWorld(this.swigCPtr, this, i, vector3);
    }

    public Vector3 worldPosToLocal(int i, Vector3 vector3) {
        return DynamicsJNI.btMultiBody_worldPosToLocal(this.swigCPtr, this, i, vector3);
    }

    public Vector3 worldDirToLocal(int i, Vector3 vector3) {
        return DynamicsJNI.btMultiBody_worldDirToLocal(this.swigCPtr, this, i, vector3);
    }

    public Matrix3 localFrameToWorld(int i, Matrix3 matrix3) {
        return DynamicsJNI.btMultiBody_localFrameToWorld(this.swigCPtr, this, i, matrix3);
    }

    public float getKineticEnergy() {
        return DynamicsJNI.btMultiBody_getKineticEnergy(this.swigCPtr, this);
    }

    public Vector3 getAngularMomentum() {
        return DynamicsJNI.btMultiBody_getAngularMomentum(this.swigCPtr, this);
    }

    public void clearForcesAndTorques() {
        DynamicsJNI.btMultiBody_clearForcesAndTorques(this.swigCPtr, this);
    }

    public void clearConstraintForces() {
        DynamicsJNI.btMultiBody_clearConstraintForces(this.swigCPtr, this);
    }

    public void clearVelocities() {
        DynamicsJNI.btMultiBody_clearVelocities(this.swigCPtr, this);
    }

    public void addBaseForce(Vector3 vector3) {
        DynamicsJNI.btMultiBody_addBaseForce(this.swigCPtr, this, vector3);
    }

    public void addBaseTorque(Vector3 vector3) {
        DynamicsJNI.btMultiBody_addBaseTorque(this.swigCPtr, this, vector3);
    }

    public void addLinkForce(int i, Vector3 vector3) {
        DynamicsJNI.btMultiBody_addLinkForce(this.swigCPtr, this, i, vector3);
    }

    public void addLinkTorque(int i, Vector3 vector3) {
        DynamicsJNI.btMultiBody_addLinkTorque(this.swigCPtr, this, i, vector3);
    }

    public void addBaseConstraintForce(Vector3 vector3) {
        DynamicsJNI.btMultiBody_addBaseConstraintForce(this.swigCPtr, this, vector3);
    }

    public void addBaseConstraintTorque(Vector3 vector3) {
        DynamicsJNI.btMultiBody_addBaseConstraintTorque(this.swigCPtr, this, vector3);
    }

    public void addLinkConstraintForce(int i, Vector3 vector3) {
        DynamicsJNI.btMultiBody_addLinkConstraintForce(this.swigCPtr, this, i, vector3);
    }

    public void addLinkConstraintTorque(int i, Vector3 vector3) {
        DynamicsJNI.btMultiBody_addLinkConstraintTorque(this.swigCPtr, this, i, vector3);
    }

    public void addJointTorque(int i, float f) {
        DynamicsJNI.btMultiBody_addJointTorque(this.swigCPtr, this, i, f);
    }

    public void addJointTorqueMultiDof(int i, int i2, float f) {
        DynamicsJNI.btMultiBody_addJointTorqueMultiDof__SWIG_0(this.swigCPtr, this, i, i2, f);
    }

    public void addJointTorqueMultiDof(int i, FloatBuffer floatBuffer) {
        DynamicsJNI.btMultiBody_addJointTorqueMultiDof__SWIG_1(this.swigCPtr, this, i, floatBuffer);
    }

    public Vector3 getBaseForce() {
        return DynamicsJNI.btMultiBody_getBaseForce(this.swigCPtr, this);
    }

    public Vector3 getBaseTorque() {
        return DynamicsJNI.btMultiBody_getBaseTorque(this.swigCPtr, this);
    }

    public Vector3 getLinkForce(int i) {
        return DynamicsJNI.btMultiBody_getLinkForce(this.swigCPtr, this, i);
    }

    public Vector3 getLinkTorque(int i) {
        return DynamicsJNI.btMultiBody_getLinkTorque(this.swigCPtr, this, i);
    }

    public float getJointTorque(int i) {
        return DynamicsJNI.btMultiBody_getJointTorque(this.swigCPtr, this, i);
    }

    public FloatBuffer getJointTorqueMultiDof(int i) {
        return DynamicsJNI.btMultiBody_getJointTorqueMultiDof(this.swigCPtr, this, i);
    }

    public void computeAccelerationsArticulatedBodyAlgorithmMultiDof(float f, btScalarArray btscalararray, btVector3Array btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t, boolean z) {
        DynamicsJNI.btMultiBody_computeAccelerationsArticulatedBodyAlgorithmMultiDof__SWIG_0(this.swigCPtr, this, f, btScalarArray.getCPtr(btscalararray), btscalararray, btVector3Array.getCPtr(btvector3array), btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t), z);
    }

    public void computeAccelerationsArticulatedBodyAlgorithmMultiDof(float f, btScalarArray btscalararray, btVector3Array btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t) {
        DynamicsJNI.btMultiBody_computeAccelerationsArticulatedBodyAlgorithmMultiDof__SWIG_1(this.swigCPtr, this, f, btScalarArray.getCPtr(btscalararray), btscalararray, btVector3Array.getCPtr(btvector3array), btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t));
    }

    public void stepVelocitiesMultiDof(float f, btScalarArray btscalararray, btVector3Array btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t, boolean z) {
        DynamicsJNI.btMultiBody_stepVelocitiesMultiDof__SWIG_0(this.swigCPtr, this, f, btScalarArray.getCPtr(btscalararray), btscalararray, btVector3Array.getCPtr(btvector3array), btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t), z);
    }

    public void stepVelocitiesMultiDof(float f, btScalarArray btscalararray, btVector3Array btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t) {
        DynamicsJNI.btMultiBody_stepVelocitiesMultiDof__SWIG_1(this.swigCPtr, this, f, btScalarArray.getCPtr(btscalararray), btscalararray, btVector3Array.getCPtr(btvector3array), btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t));
    }

    public void calcAccelerationDeltasMultiDof(FloatBuffer floatBuffer, FloatBuffer floatBuffer2, btScalarArray btscalararray, btVector3Array btvector3array) {
        DynamicsJNI.btMultiBody_calcAccelerationDeltasMultiDof(this.swigCPtr, this, floatBuffer, floatBuffer2, btScalarArray.getCPtr(btscalararray), btscalararray, btVector3Array.getCPtr(btvector3array), btvector3array);
    }

    public void applyDeltaVeeMultiDof2(FloatBuffer floatBuffer, float f) {
        DynamicsJNI.btMultiBody_applyDeltaVeeMultiDof2(this.swigCPtr, this, floatBuffer, f);
    }

    public void processDeltaVeeMultiDof2() {
        DynamicsJNI.btMultiBody_processDeltaVeeMultiDof2(this.swigCPtr, this);
    }

    public void applyDeltaVeeMultiDof(FloatBuffer floatBuffer, float f) {
        DynamicsJNI.btMultiBody_applyDeltaVeeMultiDof(this.swigCPtr, this, floatBuffer, f);
    }

    public void stepPositionsMultiDof(float f, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        DynamicsJNI.btMultiBody_stepPositionsMultiDof__SWIG_0(this.swigCPtr, this, f, floatBuffer, floatBuffer2);
    }

    public void stepPositionsMultiDof(float f, FloatBuffer floatBuffer) {
        DynamicsJNI.btMultiBody_stepPositionsMultiDof__SWIG_1(this.swigCPtr, this, f, floatBuffer);
    }

    public void stepPositionsMultiDof(float f) {
        DynamicsJNI.btMultiBody_stepPositionsMultiDof__SWIG_2(this.swigCPtr, this, f);
    }

    public void fillContactJacobianMultiDof(int i, Vector3 vector3, Vector3 vector4, FloatBuffer floatBuffer, btScalarArray btscalararray, btVector3Array btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t) {
        DynamicsJNI.btMultiBody_fillContactJacobianMultiDof(this.swigCPtr, this, i, vector3, vector4, floatBuffer, btScalarArray.getCPtr(btscalararray), btscalararray, btVector3Array.getCPtr(btvector3array), btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t));
    }

    public void fillConstraintJacobianMultiDof(int i, Vector3 vector3, Vector3 vector4, Vector3 vector5, FloatBuffer floatBuffer, btScalarArray btscalararray, btVector3Array btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t) {
        DynamicsJNI.btMultiBody_fillConstraintJacobianMultiDof(this.swigCPtr, this, i, vector3, vector4, vector5, floatBuffer, btScalarArray.getCPtr(btscalararray), btscalararray, btVector3Array.getCPtr(btvector3array), btvector3array, SWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btMatrix3x3_t));
    }

    public void setCanSleep(boolean z) {
        DynamicsJNI.btMultiBody_setCanSleep(this.swigCPtr, this, z);
    }

    public boolean getCanSleep() {
        return DynamicsJNI.btMultiBody_getCanSleep(this.swigCPtr, this);
    }

    public boolean isAwake() {
        return DynamicsJNI.btMultiBody_isAwake(this.swigCPtr, this);
    }

    public void wakeUp() {
        DynamicsJNI.btMultiBody_wakeUp(this.swigCPtr, this);
    }

    public void goToSleep() {
        DynamicsJNI.btMultiBody_goToSleep(this.swigCPtr, this);
    }

    public void checkMotionAndSleepIfRequired(float f) {
        DynamicsJNI.btMultiBody_checkMotionAndSleepIfRequired(this.swigCPtr, this, f);
    }

    public boolean hasFixedBase() {
        return DynamicsJNI.btMultiBody_hasFixedBase(this.swigCPtr, this);
    }

    public int getCompanionId() {
        return DynamicsJNI.btMultiBody_getCompanionId(this.swigCPtr, this);
    }

    public void setCompanionId(int i) {
        DynamicsJNI.btMultiBody_setCompanionId(this.swigCPtr, this, i);
    }

    public void setNumLinks(int i) {
        DynamicsJNI.btMultiBody_setNumLinks(this.swigCPtr, this, i);
    }

    public float getLinearDamping() {
        return DynamicsJNI.btMultiBody_getLinearDamping(this.swigCPtr, this);
    }

    public void setLinearDamping(float f) {
        DynamicsJNI.btMultiBody_setLinearDamping(this.swigCPtr, this, f);
    }

    public float getAngularDamping() {
        return DynamicsJNI.btMultiBody_getAngularDamping(this.swigCPtr, this);
    }

    public void setAngularDamping(float f) {
        DynamicsJNI.btMultiBody_setAngularDamping(this.swigCPtr, this, f);
    }

    public boolean getUseGyroTerm() {
        return DynamicsJNI.btMultiBody_getUseGyroTerm(this.swigCPtr, this);
    }

    public void setUseGyroTerm(boolean z) {
        DynamicsJNI.btMultiBody_setUseGyroTerm(this.swigCPtr, this, z);
    }

    public float getMaxCoordinateVelocity() {
        return DynamicsJNI.btMultiBody_getMaxCoordinateVelocity(this.swigCPtr, this);
    }

    public void setMaxCoordinateVelocity(float f) {
        DynamicsJNI.btMultiBody_setMaxCoordinateVelocity(this.swigCPtr, this, f);
    }

    public float getMaxAppliedImpulse() {
        return DynamicsJNI.btMultiBody_getMaxAppliedImpulse(this.swigCPtr, this);
    }

    public void setMaxAppliedImpulse(float f) {
        DynamicsJNI.btMultiBody_setMaxAppliedImpulse(this.swigCPtr, this, f);
    }

    public void setHasSelfCollision(boolean z) {
        DynamicsJNI.btMultiBody_setHasSelfCollision(this.swigCPtr, this, z);
    }

    public boolean hasSelfCollision() {
        return DynamicsJNI.btMultiBody_hasSelfCollision(this.swigCPtr, this);
    }

    public void finalizeMultiDof() {
        DynamicsJNI.btMultiBody_finalizeMultiDof(this.swigCPtr, this);
    }

    public void useRK4Integration(boolean z) {
        DynamicsJNI.btMultiBody_useRK4Integration(this.swigCPtr, this, z);
    }

    public boolean isUsingRK4Integration() {
        return DynamicsJNI.btMultiBody_isUsingRK4Integration(this.swigCPtr, this);
    }

    public void useGlobalVelocities(boolean z) {
        DynamicsJNI.btMultiBody_useGlobalVelocities(this.swigCPtr, this, z);
    }

    public boolean isUsingGlobalVelocities() {
        return DynamicsJNI.btMultiBody_isUsingGlobalVelocities(this.swigCPtr, this);
    }

    public boolean isPosUpdated() {
        return DynamicsJNI.btMultiBody_isPosUpdated(this.swigCPtr, this);
    }

    public void setPosUpdated(boolean z) {
        DynamicsJNI.btMultiBody_setPosUpdated(this.swigCPtr, this, z);
    }

    public boolean internalNeedsJointFeedback() {
        return DynamicsJNI.btMultiBody_internalNeedsJointFeedback(this.swigCPtr, this);
    }

    public void forwardKinematics(SWIGTYPE_p_btAlignedObjectArrayT_btQuaternion_t sWIGTYPE_p_btAlignedObjectArrayT_btQuaternion_t, btVector3Array btvector3array) {
        DynamicsJNI.btMultiBody_forwardKinematics(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btQuaternion_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btQuaternion_t), btVector3Array.getCPtr(btvector3array), btvector3array);
    }

    public void compTreeLinkVelocities(btVector3 btvector3, btVector3 btvector4) {
        DynamicsJNI.btMultiBody_compTreeLinkVelocities(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4);
    }

    public void updateCollisionObjectWorldTransforms(SWIGTYPE_p_btAlignedObjectArrayT_btQuaternion_t sWIGTYPE_p_btAlignedObjectArrayT_btQuaternion_t, btVector3Array btvector3array) {
        DynamicsJNI.btMultiBody_updateCollisionObjectWorldTransforms(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btQuaternion_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btQuaternion_t), btVector3Array.getCPtr(btvector3array), btvector3array);
    }

    public int calculateSerializeBufferSize() {
        return DynamicsJNI.btMultiBody_calculateSerializeBufferSize(this.swigCPtr, this);
    }

    public String serialize(long j, btSerializer btserializer) {
        return DynamicsJNI.btMultiBody_serialize(this.swigCPtr, this, j, btSerializer.getCPtr(btserializer), btserializer);
    }

    public String getBaseName() {
        return DynamicsJNI.btMultiBody_getBaseName(this.swigCPtr, this);
    }

    public void setBaseName(String str) {
        DynamicsJNI.btMultiBody_setBaseName(this.swigCPtr, this, str);
    }

    public long getUserPointer() {
        return DynamicsJNI.btMultiBody_getUserPointer(this.swigCPtr, this);
    }

    public int getUserIndex() {
        return DynamicsJNI.btMultiBody_getUserIndex(this.swigCPtr, this);
    }

    public int getUserIndex2() {
        return DynamicsJNI.btMultiBody_getUserIndex2(this.swigCPtr, this);
    }

    public void setUserPointer(long j) {
        DynamicsJNI.btMultiBody_setUserPointer(this.swigCPtr, this, j);
    }

    public void setUserIndex(int i) {
        DynamicsJNI.btMultiBody_setUserIndex(this.swigCPtr, this, i);
    }

    public void setUserIndex2(int i) {
        DynamicsJNI.btMultiBody_setUserIndex2(this.swigCPtr, this, i);
    }
}
