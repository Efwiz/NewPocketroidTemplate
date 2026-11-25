package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btBulletSerializedArrays extends BulletBase {
    private long swigCPtr;

    protected btBulletSerializedArrays(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btBulletSerializedArrays(long j, boolean z) {
        this("btBulletSerializedArrays", j, z);
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

    public static long getCPtr(btBulletSerializedArrays btbulletserializedarrays) {
        if (btbulletserializedarrays == null) {
            return 0L;
        }
        return btbulletserializedarrays.swigCPtr;
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
                LinearMathJNI.delete_btBulletSerializedArrays(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btBulletSerializedArrays() {
        this(LinearMathJNI.new_btBulletSerializedArrays(), true);
    }

    public void setBvhsDouble(SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhDoubleData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhDoubleData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_bvhsDouble_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhDoubleData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhDoubleData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhDoubleData_p_t getBvhsDouble() {
        long jBtBulletSerializedArrays_bvhsDouble_get = LinearMathJNI.btBulletSerializedArrays_bvhsDouble_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_bvhsDouble_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhDoubleData_p_t(jBtBulletSerializedArrays_bvhsDouble_get, false);
    }

    public void setBvhsFloat(SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhFloatData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhFloatData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_bvhsFloat_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhFloatData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhFloatData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhFloatData_p_t getBvhsFloat() {
        long jBtBulletSerializedArrays_bvhsFloat_get = LinearMathJNI.btBulletSerializedArrays_bvhsFloat_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_bvhsFloat_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhFloatData_p_t(jBtBulletSerializedArrays_bvhsFloat_get, false);
    }

    public void setColShapeData(SWIGTYPE_p_btAlignedObjectArrayT_btCollisionShapeData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btCollisionShapeData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_colShapeData_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btCollisionShapeData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btCollisionShapeData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btCollisionShapeData_p_t getColShapeData() {
        long jBtBulletSerializedArrays_colShapeData_get = LinearMathJNI.btBulletSerializedArrays_colShapeData_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_colShapeData_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btCollisionShapeData_p_t(jBtBulletSerializedArrays_colShapeData_get, false);
    }

    public void setDynamicWorldInfoDataDouble(SWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldDoubleData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldDoubleData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_dynamicWorldInfoDataDouble_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldDoubleData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldDoubleData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldDoubleData_p_t getDynamicWorldInfoDataDouble() {
        long jBtBulletSerializedArrays_dynamicWorldInfoDataDouble_get = LinearMathJNI.btBulletSerializedArrays_dynamicWorldInfoDataDouble_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_dynamicWorldInfoDataDouble_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldDoubleData_p_t(jBtBulletSerializedArrays_dynamicWorldInfoDataDouble_get, false);
    }

    public void setDynamicWorldInfoDataFloat(SWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldFloatData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldFloatData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_dynamicWorldInfoDataFloat_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldFloatData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldFloatData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldFloatData_p_t getDynamicWorldInfoDataFloat() {
        long jBtBulletSerializedArrays_dynamicWorldInfoDataFloat_get = LinearMathJNI.btBulletSerializedArrays_dynamicWorldInfoDataFloat_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_dynamicWorldInfoDataFloat_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btDynamicsWorldFloatData_p_t(jBtBulletSerializedArrays_dynamicWorldInfoDataFloat_get, false);
    }

    public void setRigidBodyDataDouble(SWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyDoubleData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyDoubleData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_rigidBodyDataDouble_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyDoubleData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyDoubleData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyDoubleData_p_t getRigidBodyDataDouble() {
        long jBtBulletSerializedArrays_rigidBodyDataDouble_get = LinearMathJNI.btBulletSerializedArrays_rigidBodyDataDouble_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_rigidBodyDataDouble_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyDoubleData_p_t(jBtBulletSerializedArrays_rigidBodyDataDouble_get, false);
    }

    public void setRigidBodyDataFloat(SWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyFloatData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyFloatData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_rigidBodyDataFloat_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyFloatData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyFloatData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyFloatData_p_t getRigidBodyDataFloat() {
        long jBtBulletSerializedArrays_rigidBodyDataFloat_get = LinearMathJNI.btBulletSerializedArrays_rigidBodyDataFloat_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_rigidBodyDataFloat_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btRigidBodyFloatData_p_t(jBtBulletSerializedArrays_rigidBodyDataFloat_get, false);
    }

    public void setCollisionObjectDataDouble(SWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectDoubleData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectDoubleData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_collisionObjectDataDouble_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectDoubleData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectDoubleData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectDoubleData_p_t getCollisionObjectDataDouble() {
        long jBtBulletSerializedArrays_collisionObjectDataDouble_get = LinearMathJNI.btBulletSerializedArrays_collisionObjectDataDouble_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_collisionObjectDataDouble_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectDoubleData_p_t(jBtBulletSerializedArrays_collisionObjectDataDouble_get, false);
    }

    public void setCollisionObjectDataFloat(SWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectFloatData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectFloatData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_collisionObjectDataFloat_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectFloatData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectFloatData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectFloatData_p_t getCollisionObjectDataFloat() {
        long jBtBulletSerializedArrays_collisionObjectDataFloat_get = LinearMathJNI.btBulletSerializedArrays_collisionObjectDataFloat_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_collisionObjectDataFloat_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btCollisionObjectFloatData_p_t(jBtBulletSerializedArrays_collisionObjectDataFloat_get, false);
    }

    public void setConstraintDataFloat(SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintFloatData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintFloatData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_constraintDataFloat_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintFloatData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintFloatData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintFloatData_p_t getConstraintDataFloat() {
        long jBtBulletSerializedArrays_constraintDataFloat_get = LinearMathJNI.btBulletSerializedArrays_constraintDataFloat_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_constraintDataFloat_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintFloatData_p_t(jBtBulletSerializedArrays_constraintDataFloat_get, false);
    }

    public void setConstraintDataDouble(SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintDoubleData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintDoubleData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_constraintDataDouble_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintDoubleData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintDoubleData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintDoubleData_p_t getConstraintDataDouble() {
        long jBtBulletSerializedArrays_constraintDataDouble_get = LinearMathJNI.btBulletSerializedArrays_constraintDataDouble_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_constraintDataDouble_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintDoubleData_p_t(jBtBulletSerializedArrays_constraintDataDouble_get, false);
    }

    public void setConstraintData(SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_constraintData_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintData_p_t getConstraintData() {
        long jBtBulletSerializedArrays_constraintData_get = LinearMathJNI.btBulletSerializedArrays_constraintData_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_constraintData_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btTypedConstraintData_p_t(jBtBulletSerializedArrays_constraintData_get, false);
    }

    public void setSoftBodyFloatData(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyFloatData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyFloatData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_softBodyFloatData_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyFloatData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyFloatData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyFloatData_p_t getSoftBodyFloatData() {
        long jBtBulletSerializedArrays_softBodyFloatData_get = LinearMathJNI.btBulletSerializedArrays_softBodyFloatData_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_softBodyFloatData_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyFloatData_p_t(jBtBulletSerializedArrays_softBodyFloatData_get, false);
    }

    public void setSoftBodyDoubleData(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyDoubleData_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyDoubleData_p_t) {
        LinearMathJNI.btBulletSerializedArrays_softBodyDoubleData_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyDoubleData_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyDoubleData_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyDoubleData_p_t getSoftBodyDoubleData() {
        long jBtBulletSerializedArrays_softBodyDoubleData_get = LinearMathJNI.btBulletSerializedArrays_softBodyDoubleData_get(this.swigCPtr, this);
        if (jBtBulletSerializedArrays_softBodyDoubleData_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBodyDoubleData_p_t(jBtBulletSerializedArrays_softBodyDoubleData_get, false);
    }
}
