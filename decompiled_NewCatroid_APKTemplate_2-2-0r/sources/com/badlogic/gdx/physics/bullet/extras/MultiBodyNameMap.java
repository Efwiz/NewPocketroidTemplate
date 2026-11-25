package com.badlogic.gdx.physics.bullet.extras;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class MultiBodyNameMap extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected MultiBodyNameMap(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public MultiBodyNameMap(long j, boolean z) {
        this("MultiBodyNameMap", j, z);
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

    public static long getCPtr(MultiBodyNameMap multiBodyNameMap) {
        if (multiBodyNameMap == null) {
            return 0L;
        }
        return multiBodyNameMap.swigCPtr;
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
                ExtrasJNI.delete_MultiBodyNameMap(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public MultiBodyNameMap() {
        this(ExtrasJNI.new_MultiBodyNameMap(), true);
    }

    public int addBody(int i, SWIGTYPE_p_std__string sWIGTYPE_p_std__string) {
        return ExtrasJNI.MultiBodyNameMap_addBody(this.swigCPtr, this, i, SWIGTYPE_p_std__string.getCPtr(sWIGTYPE_p_std__string));
    }

    public int addJoint(int i, SWIGTYPE_p_std__string sWIGTYPE_p_std__string) {
        return ExtrasJNI.MultiBodyNameMap_addJoint(this.swigCPtr, this, i, SWIGTYPE_p_std__string.getCPtr(sWIGTYPE_p_std__string));
    }

    public int getBodyName(int i, SWIGTYPE_p_std__string sWIGTYPE_p_std__string) {
        return ExtrasJNI.MultiBodyNameMap_getBodyName(this.swigCPtr, this, i, SWIGTYPE_p_std__string.getCPtr(sWIGTYPE_p_std__string));
    }

    public int getJointName(int i, SWIGTYPE_p_std__string sWIGTYPE_p_std__string) {
        return ExtrasJNI.MultiBodyNameMap_getJointName(this.swigCPtr, this, i, SWIGTYPE_p_std__string.getCPtr(sWIGTYPE_p_std__string));
    }

    public int getBodyIndex(SWIGTYPE_p_std__string sWIGTYPE_p_std__string, IntBuffer intBuffer) {
        return ExtrasJNI.MultiBodyNameMap_getBodyIndex(this.swigCPtr, this, SWIGTYPE_p_std__string.getCPtr(sWIGTYPE_p_std__string), intBuffer);
    }

    public int getJointIndex(SWIGTYPE_p_std__string sWIGTYPE_p_std__string, IntBuffer intBuffer) {
        return ExtrasJNI.MultiBodyNameMap_getJointIndex(this.swigCPtr, this, SWIGTYPE_p_std__string.getCPtr(sWIGTYPE_p_std__string), intBuffer);
    }
}
