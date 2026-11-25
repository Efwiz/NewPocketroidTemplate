package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btScalarArray;

/* JADX INFO: loaded from: classes8.dex */
public class btDantzigScratchMemory extends BulletBase {
    private long swigCPtr;

    protected btDantzigScratchMemory(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btDantzigScratchMemory(long j, boolean z) {
        this("btDantzigScratchMemory", j, z);
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

    public static long getCPtr(btDantzigScratchMemory btdantzigscratchmemory) {
        if (btdantzigscratchmemory == null) {
            return 0L;
        }
        return btdantzigscratchmemory.swigCPtr;
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
                DynamicsJNI.delete_btDantzigScratchMemory(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setScratch(btScalarArray btscalararray) {
        DynamicsJNI.btDantzigScratchMemory_scratch_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getScratch() {
        long jBtDantzigScratchMemory_scratch_get = DynamicsJNI.btDantzigScratchMemory_scratch_get(this.swigCPtr, this);
        if (jBtDantzigScratchMemory_scratch_get == 0) {
            return null;
        }
        return new btScalarArray(jBtDantzigScratchMemory_scratch_get, false);
    }

    public void setL(btScalarArray btscalararray) {
        DynamicsJNI.btDantzigScratchMemory_L_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getL() {
        long jBtDantzigScratchMemory_L_get = DynamicsJNI.btDantzigScratchMemory_L_get(this.swigCPtr, this);
        if (jBtDantzigScratchMemory_L_get == 0) {
            return null;
        }
        return new btScalarArray(jBtDantzigScratchMemory_L_get, false);
    }

    public void setD(btScalarArray btscalararray) {
        DynamicsJNI.btDantzigScratchMemory_d_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getD() {
        long jBtDantzigScratchMemory_d_get = DynamicsJNI.btDantzigScratchMemory_d_get(this.swigCPtr, this);
        if (jBtDantzigScratchMemory_d_get == 0) {
            return null;
        }
        return new btScalarArray(jBtDantzigScratchMemory_d_get, false);
    }

    public void setDelta_w(btScalarArray btscalararray) {
        DynamicsJNI.btDantzigScratchMemory_delta_w_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getDelta_w() {
        long jBtDantzigScratchMemory_delta_w_get = DynamicsJNI.btDantzigScratchMemory_delta_w_get(this.swigCPtr, this);
        if (jBtDantzigScratchMemory_delta_w_get == 0) {
            return null;
        }
        return new btScalarArray(jBtDantzigScratchMemory_delta_w_get, false);
    }

    public void setDelta_x(btScalarArray btscalararray) {
        DynamicsJNI.btDantzigScratchMemory_delta_x_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getDelta_x() {
        long jBtDantzigScratchMemory_delta_x_get = DynamicsJNI.btDantzigScratchMemory_delta_x_get(this.swigCPtr, this);
        if (jBtDantzigScratchMemory_delta_x_get == 0) {
            return null;
        }
        return new btScalarArray(jBtDantzigScratchMemory_delta_x_get, false);
    }

    public void setDell(btScalarArray btscalararray) {
        DynamicsJNI.btDantzigScratchMemory_Dell_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getDell() {
        long jBtDantzigScratchMemory_Dell_get = DynamicsJNI.btDantzigScratchMemory_Dell_get(this.swigCPtr, this);
        if (jBtDantzigScratchMemory_Dell_get == 0) {
            return null;
        }
        return new btScalarArray(jBtDantzigScratchMemory_Dell_get, false);
    }

    public void setEll(btScalarArray btscalararray) {
        DynamicsJNI.btDantzigScratchMemory_ell_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
    }

    public btScalarArray getEll() {
        long jBtDantzigScratchMemory_ell_get = DynamicsJNI.btDantzigScratchMemory_ell_get(this.swigCPtr, this);
        if (jBtDantzigScratchMemory_ell_get == 0) {
            return null;
        }
        return new btScalarArray(jBtDantzigScratchMemory_ell_get, false);
    }

    public void setArows(SWIGTYPE_p_btAlignedObjectArrayT_float_p_t sWIGTYPE_p_btAlignedObjectArrayT_float_p_t) {
        DynamicsJNI.btDantzigScratchMemory_Arows_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_float_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_float_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_float_p_t getArows() {
        long jBtDantzigScratchMemory_Arows_get = DynamicsJNI.btDantzigScratchMemory_Arows_get(this.swigCPtr, this);
        if (jBtDantzigScratchMemory_Arows_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_float_p_t(jBtDantzigScratchMemory_Arows_get, false);
    }

    public void setP(SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        DynamicsJNI.btDantzigScratchMemory_p_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_int_t getP() {
        long jBtDantzigScratchMemory_p_get = DynamicsJNI.btDantzigScratchMemory_p_get(this.swigCPtr, this);
        if (jBtDantzigScratchMemory_p_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_int_t(jBtDantzigScratchMemory_p_get, false);
    }

    public void setC(SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        DynamicsJNI.btDantzigScratchMemory_C_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_int_t getC() {
        long jBtDantzigScratchMemory_C_get = DynamicsJNI.btDantzigScratchMemory_C_get(this.swigCPtr, this);
        if (jBtDantzigScratchMemory_C_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_int_t(jBtDantzigScratchMemory_C_get, false);
    }

    public void setState(SWIGTYPE_p_btAlignedObjectArrayT_bool_t sWIGTYPE_p_btAlignedObjectArrayT_bool_t) {
        DynamicsJNI.btDantzigScratchMemory_state_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_bool_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_bool_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_bool_t getState() {
        long jBtDantzigScratchMemory_state_get = DynamicsJNI.btDantzigScratchMemory_state_get(this.swigCPtr, this);
        if (jBtDantzigScratchMemory_state_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_bool_t(jBtDantzigScratchMemory_state_get, false);
    }

    public btDantzigScratchMemory() {
        this(DynamicsJNI.new_btDantzigScratchMemory(), true);
    }
}
