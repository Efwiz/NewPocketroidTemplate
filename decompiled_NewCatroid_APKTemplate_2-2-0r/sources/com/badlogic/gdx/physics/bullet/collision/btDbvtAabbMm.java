package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btDbvtAabbMm extends BulletBase {
    private long swigCPtr;
    private static final btDbvtAabbMm temp = new btDbvtAabbMm(0, false);
    private static btDbvtAabbMm[] argumentInstances = {new btDbvtAabbMm(0, false), new btDbvtAabbMm(0, false), new btDbvtAabbMm(0, false), new btDbvtAabbMm(0, false)};
    private static int argumentIndex = -1;

    protected btDbvtAabbMm(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btDbvtAabbMm(long j, boolean z) {
        this("btDbvtAabbMm", j, z);
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

    public static long getCPtr(btDbvtAabbMm btdbvtaabbmm) {
        if (btdbvtaabbmm == null) {
            return 0L;
        }
        return btdbvtaabbmm.swigCPtr;
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
                CollisionJNI.delete_btDbvtAabbMm(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static btDbvtAabbMm internalTemp(long j, boolean z) {
        btDbvtAabbMm btdbvtaabbmm = temp;
        btdbvtaabbmm.reset(j, z);
        return btdbvtaabbmm;
    }

    protected static btDbvtAabbMm obtainForArgument(long j, boolean z) {
        btDbvtAabbMm[] btdbvtaabbmmArr = argumentInstances;
        int i = (argumentIndex + 1) & 3;
        argumentIndex = i;
        btDbvtAabbMm btdbvtaabbmm = btdbvtaabbmmArr[i];
        btdbvtaabbmm.reset(j, z);
        return btdbvtaabbmm;
    }

    public Vector3 Center() {
        return CollisionJNI.btDbvtAabbMm_Center(this.swigCPtr, this);
    }

    public Vector3 Lengths() {
        return CollisionJNI.btDbvtAabbMm_Lengths(this.swigCPtr, this);
    }

    public Vector3 Extents() {
        return CollisionJNI.btDbvtAabbMm_Extents(this.swigCPtr, this);
    }

    public Vector3 Mins() {
        return CollisionJNI.btDbvtAabbMm_Mins(this.swigCPtr, this);
    }

    public Vector3 Maxs() {
        return CollisionJNI.btDbvtAabbMm_Maxs(this.swigCPtr, this);
    }

    public static btDbvtAabbMm FromCE(Vector3 vector3, Vector3 vector4) {
        return new btDbvtAabbMm(CollisionJNI.btDbvtAabbMm_FromCE(vector3, vector4), true);
    }

    public static btDbvtAabbMm FromCR(Vector3 vector3, float f) {
        return new btDbvtAabbMm(CollisionJNI.btDbvtAabbMm_FromCR(vector3, f), true);
    }

    public static btDbvtAabbMm FromMM(Vector3 vector3, Vector3 vector4) {
        return new btDbvtAabbMm(CollisionJNI.btDbvtAabbMm_FromMM(vector3, vector4), true);
    }

    public static btDbvtAabbMm FromPoints(btVector3 btvector3, int i) {
        return new btDbvtAabbMm(CollisionJNI.btDbvtAabbMm_FromPoints__SWIG_0(btVector3.getCPtr(btvector3), btvector3, i), true);
    }

    public static btDbvtAabbMm FromPoints(SWIGTYPE_p_p_btVector3 sWIGTYPE_p_p_btVector3, int i) {
        return new btDbvtAabbMm(CollisionJNI.btDbvtAabbMm_FromPoints__SWIG_1(SWIGTYPE_p_p_btVector3.getCPtr(sWIGTYPE_p_p_btVector3), i), true);
    }

    public void Expand(Vector3 vector3) {
        CollisionJNI.btDbvtAabbMm_Expand(this.swigCPtr, this, vector3);
    }

    public void SignedExpand(Vector3 vector3) {
        CollisionJNI.btDbvtAabbMm_SignedExpand(this.swigCPtr, this, vector3);
    }

    public boolean Contain(btDbvtAabbMm btdbvtaabbmm) {
        return CollisionJNI.btDbvtAabbMm_Contain(this.swigCPtr, this, getCPtr(btdbvtaabbmm), btdbvtaabbmm);
    }

    public int Classify(Vector3 vector3, float f, int i) {
        return CollisionJNI.btDbvtAabbMm_Classify(this.swigCPtr, this, vector3, f, i);
    }

    public float ProjectMinimum(Vector3 vector3, long j) {
        return CollisionJNI.btDbvtAabbMm_ProjectMinimum(this.swigCPtr, this, vector3, j);
    }

    public Vector3 tMins() {
        return CollisionJNI.btDbvtAabbMm_tMins(this.swigCPtr, this);
    }

    public Vector3 tMaxs() {
        return CollisionJNI.btDbvtAabbMm_tMaxs(this.swigCPtr, this);
    }

    public btDbvtAabbMm() {
        this(CollisionJNI.new_btDbvtAabbMm(), true);
    }
}
