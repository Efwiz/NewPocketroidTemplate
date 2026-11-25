package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformFloatData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyFloatData extends BulletBase {
    private long swigCPtr;

    protected btMultiBodyFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMultiBodyFloatData(long j, boolean z) {
        this(DynamicsConstants.btMultiBodyDataName, j, z);
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

    public static long getCPtr(btMultiBodyFloatData btmultibodyfloatdata) {
        if (btmultibodyfloatdata == null) {
            return 0L;
        }
        return btmultibodyfloatdata.swigCPtr;
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
                DynamicsJNI.delete_btMultiBodyFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBaseName(String str) {
        DynamicsJNI.btMultiBodyFloatData_baseName_set(this.swigCPtr, this, str);
    }

    public String getBaseName() {
        return DynamicsJNI.btMultiBodyFloatData_baseName_get(this.swigCPtr, this);
    }

    public void setLinks(btMultiBodyLinkFloatData btmultibodylinkfloatdata) {
        DynamicsJNI.btMultiBodyFloatData_links_set(this.swigCPtr, this, btMultiBodyLinkFloatData.getCPtr(btmultibodylinkfloatdata), btmultibodylinkfloatdata);
    }

    public btMultiBodyLinkFloatData getLinks() {
        long jBtMultiBodyFloatData_links_get = DynamicsJNI.btMultiBodyFloatData_links_get(this.swigCPtr, this);
        if (jBtMultiBodyFloatData_links_get == 0) {
            return null;
        }
        return new btMultiBodyLinkFloatData(jBtMultiBodyFloatData_links_get, false);
    }

    public void setBaseCollider(btCollisionObjectFloatData btcollisionobjectfloatdata) {
        DynamicsJNI.btMultiBodyFloatData_baseCollider_set(this.swigCPtr, this, btCollisionObjectFloatData.getCPtr(btcollisionobjectfloatdata), btcollisionobjectfloatdata);
    }

    public btCollisionObjectFloatData getBaseCollider() {
        long jBtMultiBodyFloatData_baseCollider_get = DynamicsJNI.btMultiBodyFloatData_baseCollider_get(this.swigCPtr, this);
        if (jBtMultiBodyFloatData_baseCollider_get == 0) {
            return null;
        }
        return new btCollisionObjectFloatData(jBtMultiBodyFloatData_baseCollider_get, false);
    }

    public void setBaseWorldTransform(btTransformFloatData bttransformfloatdata) {
        DynamicsJNI.btMultiBodyFloatData_baseWorldTransform_set(this.swigCPtr, this, btTransformFloatData.getCPtr(bttransformfloatdata), bttransformfloatdata);
    }

    public btTransformFloatData getBaseWorldTransform() {
        long jBtMultiBodyFloatData_baseWorldTransform_get = DynamicsJNI.btMultiBodyFloatData_baseWorldTransform_get(this.swigCPtr, this);
        if (jBtMultiBodyFloatData_baseWorldTransform_get == 0) {
            return null;
        }
        return new btTransformFloatData(jBtMultiBodyFloatData_baseWorldTransform_get, false);
    }

    public void setBaseInertia(btVector3FloatData btvector3floatdata) {
        DynamicsJNI.btMultiBodyFloatData_baseInertia_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getBaseInertia() {
        long jBtMultiBodyFloatData_baseInertia_get = DynamicsJNI.btMultiBodyFloatData_baseInertia_get(this.swigCPtr, this);
        if (jBtMultiBodyFloatData_baseInertia_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtMultiBodyFloatData_baseInertia_get, false);
    }

    public void setBaseMass(float f) {
        DynamicsJNI.btMultiBodyFloatData_baseMass_set(this.swigCPtr, this, f);
    }

    public float getBaseMass() {
        return DynamicsJNI.btMultiBodyFloatData_baseMass_get(this.swigCPtr, this);
    }

    public void setNumLinks(int i) {
        DynamicsJNI.btMultiBodyFloatData_numLinks_set(this.swigCPtr, this, i);
    }

    public int getNumLinks() {
        return DynamicsJNI.btMultiBodyFloatData_numLinks_get(this.swigCPtr, this);
    }

    public btMultiBodyFloatData() {
        this(DynamicsJNI.new_btMultiBodyFloatData(), true);
    }
}
