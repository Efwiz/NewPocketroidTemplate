package com.badlogic.gdx.physics.bullet.inversedynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class MultiBodyTree extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected MultiBodyTree(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public MultiBodyTree(long j, boolean z) {
        this("MultiBodyTree", j, z);
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

    public static long getCPtr(MultiBodyTree multiBodyTree) {
        if (multiBodyTree == null) {
            return 0L;
        }
        return multiBodyTree.swigCPtr;
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
                InverseDynamicsJNI.delete_MultiBodyTree(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return InverseDynamicsJNI.MultiBodyTree_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        InverseDynamicsJNI.MultiBodyTree_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return InverseDynamicsJNI.MultiBodyTree_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        InverseDynamicsJNI.MultiBodyTree_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return InverseDynamicsJNI.MultiBodyTree_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        InverseDynamicsJNI.MultiBodyTree_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return InverseDynamicsJNI.MultiBodyTree_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        InverseDynamicsJNI.MultiBodyTree_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public MultiBodyTree() {
        this(InverseDynamicsJNI.new_MultiBodyTree(), true);
    }

    public int addBody(int i, int i2, int i3, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3, SWIGTYPE_p_mat33 sWIGTYPE_p_mat33, SWIGTYPE_p_vec3 sWIGTYPE_p_vec4, float f, SWIGTYPE_p_vec3 sWIGTYPE_p_vec5, SWIGTYPE_p_mat33 sWIGTYPE_p_mat34, int i4, long j) {
        return InverseDynamicsJNI.MultiBodyTree_addBody(this.swigCPtr, this, i, i2, i3, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3), SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33), SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec4), f, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec5), SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat34), i4, j);
    }

    public void setAcceptInvalidMassParameters(boolean z) {
        InverseDynamicsJNI.MultiBodyTree_setAcceptInvalidMassParameters(this.swigCPtr, this, z);
    }

    public boolean getAcceptInvalidMassProperties() {
        return InverseDynamicsJNI.MultiBodyTree_getAcceptInvalidMassProperties(this.swigCPtr, this);
    }

    public int finalizeInternal() {
        return InverseDynamicsJNI.MultiBodyTree_finalizeInternal(this.swigCPtr, this);
    }

    public void printTree() {
        InverseDynamicsJNI.MultiBodyTree_printTree(this.swigCPtr, this);
    }

    public void printTreeData() {
        InverseDynamicsJNI.MultiBodyTree_printTreeData(this.swigCPtr, this);
    }

    public int calculateInverseDynamics(SWIGTYPE_p_vecx sWIGTYPE_p_vecx, SWIGTYPE_p_vecx sWIGTYPE_p_vecx2, SWIGTYPE_p_vecx sWIGTYPE_p_vecx3, SWIGTYPE_p_vecx sWIGTYPE_p_vecx4) {
        return InverseDynamicsJNI.MultiBodyTree_calculateInverseDynamics(this.swigCPtr, this, SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx), SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx2), SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx3), SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx4));
    }

    public int calculateMassMatrix(SWIGTYPE_p_vecx sWIGTYPE_p_vecx, boolean z, boolean z2, boolean z3, SWIGTYPE_p_matxx sWIGTYPE_p_matxx) {
        return InverseDynamicsJNI.MultiBodyTree_calculateMassMatrix__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx), z, z2, z3, SWIGTYPE_p_matxx.getCPtr(sWIGTYPE_p_matxx));
    }

    public int calculateMassMatrix(SWIGTYPE_p_vecx sWIGTYPE_p_vecx, SWIGTYPE_p_matxx sWIGTYPE_p_matxx) {
        return InverseDynamicsJNI.MultiBodyTree_calculateMassMatrix__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx), SWIGTYPE_p_matxx.getCPtr(sWIGTYPE_p_matxx));
    }

    public int calculateKinematics(SWIGTYPE_p_vecx sWIGTYPE_p_vecx, SWIGTYPE_p_vecx sWIGTYPE_p_vecx2, SWIGTYPE_p_vecx sWIGTYPE_p_vecx3) {
        return InverseDynamicsJNI.MultiBodyTree_calculateKinematics(this.swigCPtr, this, SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx), SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx2), SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx3));
    }

    public int calculatePositionKinematics(SWIGTYPE_p_vecx sWIGTYPE_p_vecx) {
        return InverseDynamicsJNI.MultiBodyTree_calculatePositionKinematics(this.swigCPtr, this, SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx));
    }

    public int calculatePositionAndVelocityKinematics(SWIGTYPE_p_vecx sWIGTYPE_p_vecx, SWIGTYPE_p_vecx sWIGTYPE_p_vecx2) {
        return InverseDynamicsJNI.MultiBodyTree_calculatePositionAndVelocityKinematics(this.swigCPtr, this, SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx), SWIGTYPE_p_vecx.getCPtr(sWIGTYPE_p_vecx2));
    }

    public int setGravityInWorldFrame(SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_setGravityInWorldFrame(this.swigCPtr, this, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int numBodies() {
        return InverseDynamicsJNI.MultiBodyTree_numBodies(this.swigCPtr, this);
    }

    public int numDoFs() {
        return InverseDynamicsJNI.MultiBodyTree_numDoFs(this.swigCPtr, this);
    }

    public int getBodyOrigin(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyOrigin(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int getBodyCoM(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyCoM(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int getBodyTransform(int i, SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyTransform(this.swigCPtr, this, i, SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public int getBodyAngularVelocity(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyAngularVelocity(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int getBodyLinearVelocity(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyLinearVelocity(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int getBodyLinearVelocityCoM(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyLinearVelocityCoM(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int getBodyAngularAcceleration(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyAngularAcceleration(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int getBodyLinearAcceleration(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyLinearAcceleration(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int getParentIndex(int i, IntBuffer intBuffer) {
        return InverseDynamicsJNI.MultiBodyTree_getParentIndex(this.swigCPtr, this, i, intBuffer);
    }

    public int getJointType(int i, SWIGTYPE_p_btInverseDynamicsBullet3__JointType sWIGTYPE_p_btInverseDynamicsBullet3__JointType) {
        return InverseDynamicsJNI.MultiBodyTree_getJointType(this.swigCPtr, this, i, SWIGTYPE_p_btInverseDynamicsBullet3__JointType.getCPtr(sWIGTYPE_p_btInverseDynamicsBullet3__JointType));
    }

    public int getJointTypeStr(int i, SWIGTYPE_p_p_char sWIGTYPE_p_p_char) {
        return InverseDynamicsJNI.MultiBodyTree_getJointTypeStr(this.swigCPtr, this, i, SWIGTYPE_p_p_char.getCPtr(sWIGTYPE_p_p_char));
    }

    public int getParentRParentBodyRef(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_getParentRParentBodyRef(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int getBodyTParentRef(int i, SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyTParentRef(this.swigCPtr, this, i, SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public int getBodyAxisOfMotion(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyAxisOfMotion(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int getDoFOffset(int i, IntBuffer intBuffer) {
        return InverseDynamicsJNI.MultiBodyTree_getDoFOffset(this.swigCPtr, this, i, intBuffer);
    }

    public int getUserInt(int i, IntBuffer intBuffer) {
        return InverseDynamicsJNI.MultiBodyTree_getUserInt(this.swigCPtr, this, i, intBuffer);
    }

    public int getUserPtr(int i, SWIGTYPE_p_p_void sWIGTYPE_p_p_void) {
        return InverseDynamicsJNI.MultiBodyTree_getUserPtr(this.swigCPtr, this, i, SWIGTYPE_p_p_void.getCPtr(sWIGTYPE_p_p_void));
    }

    public int setUserInt(int i, int i2) {
        return InverseDynamicsJNI.MultiBodyTree_setUserInt(this.swigCPtr, this, i, i2);
    }

    public int setUserPtr(int i, long j) {
        return InverseDynamicsJNI.MultiBodyTree_setUserPtr(this.swigCPtr, this, i, j);
    }

    public int setBodyMass(int i, float f) {
        return InverseDynamicsJNI.MultiBodyTree_setBodyMass(this.swigCPtr, this, i, f);
    }

    public int setBodyFirstMassMoment(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_setBodyFirstMassMoment(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int setBodySecondMassMoment(int i, SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        return InverseDynamicsJNI.MultiBodyTree_setBodySecondMassMoment(this.swigCPtr, this, i, SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public int getBodyMass(int i, FloatBuffer floatBuffer) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyMass(this.swigCPtr, this, i, floatBuffer);
    }

    public int getBodyFirstMassMoment(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_getBodyFirstMassMoment(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int getBodySecondMassMoment(int i, SWIGTYPE_p_mat33 sWIGTYPE_p_mat33) {
        return InverseDynamicsJNI.MultiBodyTree_getBodySecondMassMoment(this.swigCPtr, this, i, SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33));
    }

    public void clearAllUserForcesAndMoments() {
        InverseDynamicsJNI.MultiBodyTree_clearAllUserForcesAndMoments(this.swigCPtr, this);
    }

    public int addUserForce(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_addUserForce(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }

    public int addUserMoment(int i, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3) {
        return InverseDynamicsJNI.MultiBodyTree_addUserMoment(this.swigCPtr, this, i, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3));
    }
}
