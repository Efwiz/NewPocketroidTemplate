package com.badlogic.gdx.physics.bullet.extras;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class MultiBodyTreeCreator extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected MultiBodyTreeCreator(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public MultiBodyTreeCreator(long j, boolean z) {
        this("MultiBodyTreeCreator", j, z);
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

    public static long getCPtr(MultiBodyTreeCreator multiBodyTreeCreator) {
        if (multiBodyTreeCreator == null) {
            return 0L;
        }
        return multiBodyTreeCreator.swigCPtr;
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
                ExtrasJNI.delete_MultiBodyTreeCreator(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public int getNumBodies(IntBuffer intBuffer) {
        return ExtrasJNI.MultiBodyTreeCreator_getNumBodies(this.swigCPtr, this, intBuffer);
    }

    public int getBody(int i, IntBuffer intBuffer, SWIGTYPE_p_btInverseDynamicsBullet3__JointType sWIGTYPE_p_btInverseDynamicsBullet3__JointType, SWIGTYPE_p_vec3 sWIGTYPE_p_vec3, SWIGTYPE_p_mat33 sWIGTYPE_p_mat33, SWIGTYPE_p_vec3 sWIGTYPE_p_vec4, FloatBuffer floatBuffer, SWIGTYPE_p_vec3 sWIGTYPE_p_vec5, SWIGTYPE_p_mat33 sWIGTYPE_p_mat34, IntBuffer intBuffer2, SWIGTYPE_p_p_void sWIGTYPE_p_p_void) {
        return ExtrasJNI.MultiBodyTreeCreator_getBody(this.swigCPtr, this, i, intBuffer, SWIGTYPE_p_btInverseDynamicsBullet3__JointType.getCPtr(sWIGTYPE_p_btInverseDynamicsBullet3__JointType), SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec3), SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat33), SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec4), floatBuffer, SWIGTYPE_p_vec3.getCPtr(sWIGTYPE_p_vec5), SWIGTYPE_p_mat33.getCPtr(sWIGTYPE_p_mat34), intBuffer2, SWIGTYPE_p_p_void.getCPtr(sWIGTYPE_p_p_void));
    }

    public SWIGTYPE_p_MultiBodyNameMap getNameMap() {
        long jMultiBodyTreeCreator_getNameMap = ExtrasJNI.MultiBodyTreeCreator_getNameMap(this.swigCPtr, this);
        if (jMultiBodyTreeCreator_getNameMap == 0) {
            return null;
        }
        return new SWIGTYPE_p_MultiBodyNameMap(jMultiBodyTreeCreator_getNameMap, false);
    }
}
