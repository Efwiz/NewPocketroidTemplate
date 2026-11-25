package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btSolverBody extends BulletBase {
    private long swigCPtr;

    protected btSolverBody(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSolverBody(long j, boolean z) {
        this("btSolverBody", j, z);
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

    public static long getCPtr(btSolverBody btsolverbody) {
        if (btsolverbody == null) {
            return 0L;
        }
        return btsolverbody.swigCPtr;
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
                DynamicsJNI.delete_btSolverBody(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return DynamicsJNI.btSolverBody_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        DynamicsJNI.btSolverBody_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btSolverBody_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btSolverBody_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return DynamicsJNI.btSolverBody_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        DynamicsJNI.btSolverBody_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btSolverBody_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btSolverBody_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setWorldTransform(btTransform bttransform) {
        DynamicsJNI.btSolverBody_worldTransform_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
    }

    public btTransform getWorldTransform() {
        long jBtSolverBody_worldTransform_get = DynamicsJNI.btSolverBody_worldTransform_get(this.swigCPtr, this);
        if (jBtSolverBody_worldTransform_get == 0) {
            return null;
        }
        return new btTransform(jBtSolverBody_worldTransform_get, false);
    }

    public void setDeltaLinearVelocity(btVector3 btvector3) {
        DynamicsJNI.btSolverBody_deltaLinearVelocity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getDeltaLinearVelocity() {
        long jBtSolverBody_deltaLinearVelocity_get = DynamicsJNI.btSolverBody_deltaLinearVelocity_get(this.swigCPtr, this);
        if (jBtSolverBody_deltaLinearVelocity_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverBody_deltaLinearVelocity_get, false);
    }

    public void setDeltaAngularVelocity(btVector3 btvector3) {
        DynamicsJNI.btSolverBody_deltaAngularVelocity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getDeltaAngularVelocity() {
        long jBtSolverBody_deltaAngularVelocity_get = DynamicsJNI.btSolverBody_deltaAngularVelocity_get(this.swigCPtr, this);
        if (jBtSolverBody_deltaAngularVelocity_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverBody_deltaAngularVelocity_get, false);
    }

    public void setAngularFactor(btVector3 btvector3) {
        DynamicsJNI.btSolverBody_angularFactor_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAngularFactor() {
        long jBtSolverBody_angularFactor_get = DynamicsJNI.btSolverBody_angularFactor_get(this.swigCPtr, this);
        if (jBtSolverBody_angularFactor_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverBody_angularFactor_get, false);
    }

    public void setLinearFactor(btVector3 btvector3) {
        DynamicsJNI.btSolverBody_linearFactor_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getLinearFactor() {
        long jBtSolverBody_linearFactor_get = DynamicsJNI.btSolverBody_linearFactor_get(this.swigCPtr, this);
        if (jBtSolverBody_linearFactor_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverBody_linearFactor_get, false);
    }

    public void setInvMass(btVector3 btvector3) {
        DynamicsJNI.btSolverBody_invMass_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getInvMass() {
        long jBtSolverBody_invMass_get = DynamicsJNI.btSolverBody_invMass_get(this.swigCPtr, this);
        if (jBtSolverBody_invMass_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverBody_invMass_get, false);
    }

    public void setPushVelocity(btVector3 btvector3) {
        DynamicsJNI.btSolverBody_pushVelocity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getPushVelocity() {
        long jBtSolverBody_pushVelocity_get = DynamicsJNI.btSolverBody_pushVelocity_get(this.swigCPtr, this);
        if (jBtSolverBody_pushVelocity_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverBody_pushVelocity_get, false);
    }

    public void setTurnVelocity(btVector3 btvector3) {
        DynamicsJNI.btSolverBody_turnVelocity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getTurnVelocity() {
        long jBtSolverBody_turnVelocity_get = DynamicsJNI.btSolverBody_turnVelocity_get(this.swigCPtr, this);
        if (jBtSolverBody_turnVelocity_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverBody_turnVelocity_get, false);
    }

    public void setLinearVelocity(btVector3 btvector3) {
        DynamicsJNI.btSolverBody_linearVelocity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getLinearVelocity() {
        long jBtSolverBody_linearVelocity_get = DynamicsJNI.btSolverBody_linearVelocity_get(this.swigCPtr, this);
        if (jBtSolverBody_linearVelocity_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverBody_linearVelocity_get, false);
    }

    public void setAngularVelocity(btVector3 btvector3) {
        DynamicsJNI.btSolverBody_angularVelocity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAngularVelocity() {
        long jBtSolverBody_angularVelocity_get = DynamicsJNI.btSolverBody_angularVelocity_get(this.swigCPtr, this);
        if (jBtSolverBody_angularVelocity_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverBody_angularVelocity_get, false);
    }

    public void setExternalForceImpulse(btVector3 btvector3) {
        DynamicsJNI.btSolverBody_externalForceImpulse_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getExternalForceImpulse() {
        long jBtSolverBody_externalForceImpulse_get = DynamicsJNI.btSolverBody_externalForceImpulse_get(this.swigCPtr, this);
        if (jBtSolverBody_externalForceImpulse_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverBody_externalForceImpulse_get, false);
    }

    public void setExternalTorqueImpulse(btVector3 btvector3) {
        DynamicsJNI.btSolverBody_externalTorqueImpulse_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getExternalTorqueImpulse() {
        long jBtSolverBody_externalTorqueImpulse_get = DynamicsJNI.btSolverBody_externalTorqueImpulse_get(this.swigCPtr, this);
        if (jBtSolverBody_externalTorqueImpulse_get == 0) {
            return null;
        }
        return new btVector3(jBtSolverBody_externalTorqueImpulse_get, false);
    }

    public void setOriginalBody(btRigidBody btrigidbody) {
        DynamicsJNI.btSolverBody_originalBody_set(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody);
    }

    public btRigidBody getOriginalBody() {
        return btRigidBody.getInstance(DynamicsJNI.btSolverBody_originalBody_get(this.swigCPtr, this), false);
    }

    public void getVelocityInLocalPointNoDelta(Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btSolverBody_getVelocityInLocalPointNoDelta(this.swigCPtr, this, vector3, vector4);
    }

    public void getVelocityInLocalPointObsolete(Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btSolverBody_getVelocityInLocalPointObsolete(this.swigCPtr, this, vector3, vector4);
    }

    public void getAngularVelocity(Vector3 vector3) {
        DynamicsJNI.btSolverBody_getAngularVelocity(this.swigCPtr, this, vector3);
    }

    public void applyImpulse(Vector3 vector3, Vector3 vector4, float f) {
        DynamicsJNI.btSolverBody_applyImpulse(this.swigCPtr, this, vector3, vector4, f);
    }

    public void internalApplyPushImpulse(Vector3 vector3, Vector3 vector4, float f) {
        DynamicsJNI.btSolverBody_internalApplyPushImpulse(this.swigCPtr, this, vector3, vector4, f);
    }

    public Vector3 internalGetDeltaLinearVelocity() {
        return DynamicsJNI.btSolverBody_internalGetDeltaLinearVelocity(this.swigCPtr, this);
    }

    public Vector3 internalGetDeltaAngularVelocity() {
        return DynamicsJNI.btSolverBody_internalGetDeltaAngularVelocity(this.swigCPtr, this);
    }

    public Vector3 internalGetAngularFactor() {
        return DynamicsJNI.btSolverBody_internalGetAngularFactor(this.swigCPtr, this);
    }

    public Vector3 internalGetInvMass() {
        return DynamicsJNI.btSolverBody_internalGetInvMass(this.swigCPtr, this);
    }

    public void internalSetInvMass(Vector3 vector3) {
        DynamicsJNI.btSolverBody_internalSetInvMass(this.swigCPtr, this, vector3);
    }

    public Vector3 internalGetPushVelocity() {
        return DynamicsJNI.btSolverBody_internalGetPushVelocity(this.swigCPtr, this);
    }

    public Vector3 internalGetTurnVelocity() {
        return DynamicsJNI.btSolverBody_internalGetTurnVelocity(this.swigCPtr, this);
    }

    public void internalGetVelocityInLocalPointObsolete(Vector3 vector3, Vector3 vector4) {
        DynamicsJNI.btSolverBody_internalGetVelocityInLocalPointObsolete(this.swigCPtr, this, vector3, vector4);
    }

    public void internalGetAngularVelocity(Vector3 vector3) {
        DynamicsJNI.btSolverBody_internalGetAngularVelocity(this.swigCPtr, this, vector3);
    }

    public void internalApplyImpulse(Vector3 vector3, Vector3 vector4, float f) {
        DynamicsJNI.btSolverBody_internalApplyImpulse(this.swigCPtr, this, vector3, vector4, f);
    }

    public void writebackVelocity() {
        DynamicsJNI.btSolverBody_writebackVelocity(this.swigCPtr, this);
    }

    public void writebackVelocityAndTransform(float f, float f2) {
        DynamicsJNI.btSolverBody_writebackVelocityAndTransform(this.swigCPtr, this, f, f2);
    }

    public btSolverBody() {
        this(DynamicsJNI.new_btSolverBody(), true);
    }
}
