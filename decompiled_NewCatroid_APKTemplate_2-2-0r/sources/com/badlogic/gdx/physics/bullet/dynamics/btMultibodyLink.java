package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btQuaternion;
import com.badlogic.gdx.physics.bullet.linearmath.btSpatialMotionVector;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btMultibodyLink extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    public static final class eFeatherstoneJointType {
        public static final int eFixed = 4;
        public static final int eInvalid = 5;
        public static final int ePlanar = 3;
        public static final int ePrismatic = 1;
        public static final int eRevolute = 0;
        public static final int eSpherical = 2;
    }

    protected btMultibodyLink(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMultibodyLink(long j, boolean z) {
        this("btMultibodyLink", j, z);
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

    public static long getCPtr(btMultibodyLink btmultibodylink) {
        if (btmultibodylink == null) {
            return 0L;
        }
        return btmultibodylink.swigCPtr;
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
                DynamicsJNI.delete_btMultibodyLink(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return DynamicsJNI.btMultibodyLink_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        DynamicsJNI.btMultibodyLink_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btMultibodyLink_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btMultibodyLink_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return DynamicsJNI.btMultibodyLink_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        DynamicsJNI.btMultibodyLink_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btMultibodyLink_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btMultibodyLink_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setMass(float f) {
        DynamicsJNI.btMultibodyLink_mass_set(this.swigCPtr, this, f);
    }

    public float getMass() {
        return DynamicsJNI.btMultibodyLink_mass_get(this.swigCPtr, this);
    }

    public void setInertiaLocal(btVector3 btvector3) {
        DynamicsJNI.btMultibodyLink_inertiaLocal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getInertiaLocal() {
        long jBtMultibodyLink_inertiaLocal_get = DynamicsJNI.btMultibodyLink_inertiaLocal_get(this.swigCPtr, this);
        if (jBtMultibodyLink_inertiaLocal_get == 0) {
            return null;
        }
        return new btVector3(jBtMultibodyLink_inertiaLocal_get, false);
    }

    public void setParent(int i) {
        DynamicsJNI.btMultibodyLink_parent_set(this.swigCPtr, this, i);
    }

    public int getParent() {
        return DynamicsJNI.btMultibodyLink_parent_get(this.swigCPtr, this);
    }

    public void setZeroRotParentToThis(btQuaternion btquaternion) {
        DynamicsJNI.btMultibodyLink_zeroRotParentToThis_set(this.swigCPtr, this, btQuaternion.getCPtr(btquaternion), btquaternion);
    }

    public btQuaternion getZeroRotParentToThis() {
        long jBtMultibodyLink_zeroRotParentToThis_get = DynamicsJNI.btMultibodyLink_zeroRotParentToThis_get(this.swigCPtr, this);
        if (jBtMultibodyLink_zeroRotParentToThis_get == 0) {
            return null;
        }
        return new btQuaternion(jBtMultibodyLink_zeroRotParentToThis_get, false);
    }

    public void setDVector(btVector3 btvector3) {
        DynamicsJNI.btMultibodyLink_dVector_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getDVector() {
        long jBtMultibodyLink_dVector_get = DynamicsJNI.btMultibodyLink_dVector_get(this.swigCPtr, this);
        if (jBtMultibodyLink_dVector_get == 0) {
            return null;
        }
        return new btVector3(jBtMultibodyLink_dVector_get, false);
    }

    public void setEVector(btVector3 btvector3) {
        DynamicsJNI.btMultibodyLink_eVector_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getEVector() {
        long jBtMultibodyLink_eVector_get = DynamicsJNI.btMultibodyLink_eVector_get(this.swigCPtr, this);
        if (jBtMultibodyLink_eVector_get == 0) {
            return null;
        }
        return new btVector3(jBtMultibodyLink_eVector_get, false);
    }

    public void setAbsFrameTotVelocity(btSpatialMotionVector btspatialmotionvector) {
        DynamicsJNI.btMultibodyLink_absFrameTotVelocity_set(this.swigCPtr, this, btSpatialMotionVector.getCPtr(btspatialmotionvector), btspatialmotionvector);
    }

    public btSpatialMotionVector getAbsFrameTotVelocity() {
        long jBtMultibodyLink_absFrameTotVelocity_get = DynamicsJNI.btMultibodyLink_absFrameTotVelocity_get(this.swigCPtr, this);
        if (jBtMultibodyLink_absFrameTotVelocity_get == 0) {
            return null;
        }
        return new btSpatialMotionVector(jBtMultibodyLink_absFrameTotVelocity_get, false);
    }

    public void setAbsFrameLocVelocity(btSpatialMotionVector btspatialmotionvector) {
        DynamicsJNI.btMultibodyLink_absFrameLocVelocity_set(this.swigCPtr, this, btSpatialMotionVector.getCPtr(btspatialmotionvector), btspatialmotionvector);
    }

    public btSpatialMotionVector getAbsFrameLocVelocity() {
        long jBtMultibodyLink_absFrameLocVelocity_get = DynamicsJNI.btMultibodyLink_absFrameLocVelocity_get(this.swigCPtr, this);
        if (jBtMultibodyLink_absFrameLocVelocity_get == 0) {
            return null;
        }
        return new btSpatialMotionVector(jBtMultibodyLink_absFrameLocVelocity_get, false);
    }

    public void setAxes(btSpatialMotionVector btspatialmotionvector) {
        DynamicsJNI.btMultibodyLink_axes_set(this.swigCPtr, this, btSpatialMotionVector.getCPtr(btspatialmotionvector), btspatialmotionvector);
    }

    public btSpatialMotionVector getAxes() {
        long jBtMultibodyLink_axes_get = DynamicsJNI.btMultibodyLink_axes_get(this.swigCPtr, this);
        if (jBtMultibodyLink_axes_get == 0) {
            return null;
        }
        return new btSpatialMotionVector(jBtMultibodyLink_axes_get, false);
    }

    public void setAxisTop(int i, Vector3 vector3) {
        DynamicsJNI.btMultibodyLink_setAxisTop__SWIG_0(this.swigCPtr, this, i, vector3);
    }

    public void setAxisBottom(int i, Vector3 vector3) {
        DynamicsJNI.btMultibodyLink_setAxisBottom__SWIG_0(this.swigCPtr, this, i, vector3);
    }

    public void setAxisTop(int i, float f, float f2, float f3) {
        DynamicsJNI.btMultibodyLink_setAxisTop__SWIG_1(this.swigCPtr, this, i, f, f2, f3);
    }

    public void setAxisBottom(int i, float f, float f2, float f3) {
        DynamicsJNI.btMultibodyLink_setAxisBottom__SWIG_1(this.swigCPtr, this, i, f, f2, f3);
    }

    public Vector3 getAxisTop(int i) {
        return DynamicsJNI.btMultibodyLink_getAxisTop(this.swigCPtr, this, i);
    }

    public Vector3 getAxisBottom(int i) {
        return DynamicsJNI.btMultibodyLink_getAxisBottom(this.swigCPtr, this, i);
    }

    public void setDofOffset(int i) {
        DynamicsJNI.btMultibodyLink_dofOffset_set(this.swigCPtr, this, i);
    }

    public int getDofOffset() {
        return DynamicsJNI.btMultibodyLink_dofOffset_get(this.swigCPtr, this);
    }

    public void setCfgOffset(int i) {
        DynamicsJNI.btMultibodyLink_cfgOffset_set(this.swigCPtr, this, i);
    }

    public int getCfgOffset() {
        return DynamicsJNI.btMultibodyLink_cfgOffset_get(this.swigCPtr, this);
    }

    public void setCachedRotParentToThis(btQuaternion btquaternion) {
        DynamicsJNI.btMultibodyLink_cachedRotParentToThis_set(this.swigCPtr, this, btQuaternion.getCPtr(btquaternion), btquaternion);
    }

    public btQuaternion getCachedRotParentToThis() {
        long jBtMultibodyLink_cachedRotParentToThis_get = DynamicsJNI.btMultibodyLink_cachedRotParentToThis_get(this.swigCPtr, this);
        if (jBtMultibodyLink_cachedRotParentToThis_get == 0) {
            return null;
        }
        return new btQuaternion(jBtMultibodyLink_cachedRotParentToThis_get, false);
    }

    public void setCachedRVector(btVector3 btvector3) {
        DynamicsJNI.btMultibodyLink_cachedRVector_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getCachedRVector() {
        long jBtMultibodyLink_cachedRVector_get = DynamicsJNI.btMultibodyLink_cachedRVector_get(this.swigCPtr, this);
        if (jBtMultibodyLink_cachedRVector_get == 0) {
            return null;
        }
        return new btVector3(jBtMultibodyLink_cachedRVector_get, false);
    }

    public void setAppliedForce(btVector3 btvector3) {
        DynamicsJNI.btMultibodyLink_appliedForce_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAppliedForce() {
        long jBtMultibodyLink_appliedForce_get = DynamicsJNI.btMultibodyLink_appliedForce_get(this.swigCPtr, this);
        if (jBtMultibodyLink_appliedForce_get == 0) {
            return null;
        }
        return new btVector3(jBtMultibodyLink_appliedForce_get, false);
    }

    public void setAppliedTorque(btVector3 btvector3) {
        DynamicsJNI.btMultibodyLink_appliedTorque_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAppliedTorque() {
        long jBtMultibodyLink_appliedTorque_get = DynamicsJNI.btMultibodyLink_appliedTorque_get(this.swigCPtr, this);
        if (jBtMultibodyLink_appliedTorque_get == 0) {
            return null;
        }
        return new btVector3(jBtMultibodyLink_appliedTorque_get, false);
    }

    public void setAppliedConstraintForce(btVector3 btvector3) {
        DynamicsJNI.btMultibodyLink_appliedConstraintForce_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAppliedConstraintForce() {
        long jBtMultibodyLink_appliedConstraintForce_get = DynamicsJNI.btMultibodyLink_appliedConstraintForce_get(this.swigCPtr, this);
        if (jBtMultibodyLink_appliedConstraintForce_get == 0) {
            return null;
        }
        return new btVector3(jBtMultibodyLink_appliedConstraintForce_get, false);
    }

    public void setAppliedConstraintTorque(btVector3 btvector3) {
        DynamicsJNI.btMultibodyLink_appliedConstraintTorque_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAppliedConstraintTorque() {
        long jBtMultibodyLink_appliedConstraintTorque_get = DynamicsJNI.btMultibodyLink_appliedConstraintTorque_get(this.swigCPtr, this);
        if (jBtMultibodyLink_appliedConstraintTorque_get == 0) {
            return null;
        }
        return new btVector3(jBtMultibodyLink_appliedConstraintTorque_get, false);
    }

    public void setJointPos(float[] fArr) {
        DynamicsJNI.btMultibodyLink_jointPos_set(this.swigCPtr, this, fArr);
    }

    public float[] getJointPos() {
        return DynamicsJNI.btMultibodyLink_jointPos_get(this.swigCPtr, this);
    }

    public void setJointTorque(float[] fArr) {
        DynamicsJNI.btMultibodyLink_jointTorque_set(this.swigCPtr, this, fArr);
    }

    public float[] getJointTorque() {
        return DynamicsJNI.btMultibodyLink_jointTorque_get(this.swigCPtr, this);
    }

    public void setCollider(btMultiBodyLinkCollider btmultibodylinkcollider) {
        DynamicsJNI.btMultibodyLink_collider_set(this.swigCPtr, this, btMultiBodyLinkCollider.getCPtr(btmultibodylinkcollider), btmultibodylinkcollider);
    }

    public btMultiBodyLinkCollider getCollider() {
        long jBtMultibodyLink_collider_get = DynamicsJNI.btMultibodyLink_collider_get(this.swigCPtr, this);
        if (jBtMultibodyLink_collider_get == 0) {
            return null;
        }
        return new btMultiBodyLinkCollider(jBtMultibodyLink_collider_get, false);
    }

    public void setFlags(int i) {
        DynamicsJNI.btMultibodyLink_flags_set(this.swigCPtr, this, i);
    }

    public int getFlags() {
        return DynamicsJNI.btMultibodyLink_flags_get(this.swigCPtr, this);
    }

    public void setDofCount(int i) {
        DynamicsJNI.btMultibodyLink_dofCount_set(this.swigCPtr, this, i);
    }

    public int getDofCount() {
        return DynamicsJNI.btMultibodyLink_dofCount_get(this.swigCPtr, this);
    }

    public void setPosVarCount(int i) {
        DynamicsJNI.btMultibodyLink_posVarCount_set(this.swigCPtr, this, i);
    }

    public int getPosVarCount() {
        return DynamicsJNI.btMultibodyLink_posVarCount_get(this.swigCPtr, this);
    }

    public void setJointType(int i) {
        DynamicsJNI.btMultibodyLink_jointType_set(this.swigCPtr, this, i);
    }

    public int getJointType() {
        return DynamicsJNI.btMultibodyLink_jointType_get(this.swigCPtr, this);
    }

    public void setJointFeedback(btMultiBodyJointFeedback btmultibodyjointfeedback) {
        DynamicsJNI.btMultibodyLink_jointFeedback_set(this.swigCPtr, this, btMultiBodyJointFeedback.getCPtr(btmultibodyjointfeedback), btmultibodyjointfeedback);
    }

    public btMultiBodyJointFeedback getJointFeedback() {
        long jBtMultibodyLink_jointFeedback_get = DynamicsJNI.btMultibodyLink_jointFeedback_get(this.swigCPtr, this);
        if (jBtMultibodyLink_jointFeedback_get == 0) {
            return null;
        }
        return new btMultiBodyJointFeedback(jBtMultibodyLink_jointFeedback_get, false);
    }

    public void setCachedWorldTransform(btTransform bttransform) {
        DynamicsJNI.btMultibodyLink_cachedWorldTransform_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
    }

    public btTransform getCachedWorldTransform() {
        long jBtMultibodyLink_cachedWorldTransform_get = DynamicsJNI.btMultibodyLink_cachedWorldTransform_get(this.swigCPtr, this);
        if (jBtMultibodyLink_cachedWorldTransform_get == 0) {
            return null;
        }
        return new btTransform(jBtMultibodyLink_cachedWorldTransform_get, false);
    }

    public String getLinkName() {
        return DynamicsJNI.btMultibodyLink_linkName_get(this.swigCPtr, this);
    }

    public String getJointName() {
        return DynamicsJNI.btMultibodyLink_jointName_get(this.swigCPtr, this);
    }

    public void setUserPtr(long j) {
        DynamicsJNI.btMultibodyLink_userPtr_set(this.swigCPtr, this, j);
    }

    public long getUserPtr() {
        return DynamicsJNI.btMultibodyLink_userPtr_get(this.swigCPtr, this);
    }

    public void setJointDamping(float f) {
        DynamicsJNI.btMultibodyLink_jointDamping_set(this.swigCPtr, this, f);
    }

    public float getJointDamping() {
        return DynamicsJNI.btMultibodyLink_jointDamping_get(this.swigCPtr, this);
    }

    public void setJointFriction(float f) {
        DynamicsJNI.btMultibodyLink_jointFriction_set(this.swigCPtr, this, f);
    }

    public float getJointFriction() {
        return DynamicsJNI.btMultibodyLink_jointFriction_get(this.swigCPtr, this);
    }

    public void setJointLowerLimit(float f) {
        DynamicsJNI.btMultibodyLink_jointLowerLimit_set(this.swigCPtr, this, f);
    }

    public float getJointLowerLimit() {
        return DynamicsJNI.btMultibodyLink_jointLowerLimit_get(this.swigCPtr, this);
    }

    public void setJointUpperLimit(float f) {
        DynamicsJNI.btMultibodyLink_jointUpperLimit_set(this.swigCPtr, this, f);
    }

    public float getJointUpperLimit() {
        return DynamicsJNI.btMultibodyLink_jointUpperLimit_get(this.swigCPtr, this);
    }

    public void setJointMaxForce(float f) {
        DynamicsJNI.btMultibodyLink_jointMaxForce_set(this.swigCPtr, this, f);
    }

    public float getJointMaxForce() {
        return DynamicsJNI.btMultibodyLink_jointMaxForce_get(this.swigCPtr, this);
    }

    public void setJointMaxVelocity(float f) {
        DynamicsJNI.btMultibodyLink_jointMaxVelocity_set(this.swigCPtr, this, f);
    }

    public float getJointMaxVelocity() {
        return DynamicsJNI.btMultibodyLink_jointMaxVelocity_get(this.swigCPtr, this);
    }

    public btMultibodyLink() {
        this(DynamicsJNI.new_btMultibodyLink(), true);
    }

    public void updateCacheMultiDof(FloatBuffer floatBuffer) {
        DynamicsJNI.btMultibodyLink_updateCacheMultiDof__SWIG_0(this.swigCPtr, this, floatBuffer);
    }

    public void updateCacheMultiDof() {
        DynamicsJNI.btMultibodyLink_updateCacheMultiDof__SWIG_1(this.swigCPtr, this);
    }
}
