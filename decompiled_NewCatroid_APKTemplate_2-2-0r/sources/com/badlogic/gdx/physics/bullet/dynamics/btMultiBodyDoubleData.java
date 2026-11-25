package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectDoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btTransformDoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;

/* JADX INFO: loaded from: classes8.dex */
public class btMultiBodyDoubleData extends BulletBase {
    private long swigCPtr;

    protected btMultiBodyDoubleData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMultiBodyDoubleData(long j, boolean z) {
        this("btMultiBodyDoubleData", j, z);
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

    public static long getCPtr(btMultiBodyDoubleData btmultibodydoubledata) {
        if (btmultibodydoubledata == null) {
            return 0L;
        }
        return btmultibodydoubledata.swigCPtr;
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
                DynamicsJNI.delete_btMultiBodyDoubleData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setBaseWorldTransform(btTransformDoubleData bttransformdoubledata) {
        DynamicsJNI.btMultiBodyDoubleData_baseWorldTransform_set(this.swigCPtr, this, btTransformDoubleData.getCPtr(bttransformdoubledata), bttransformdoubledata);
    }

    public btTransformDoubleData getBaseWorldTransform() {
        long jBtMultiBodyDoubleData_baseWorldTransform_get = DynamicsJNI.btMultiBodyDoubleData_baseWorldTransform_get(this.swigCPtr, this);
        if (jBtMultiBodyDoubleData_baseWorldTransform_get == 0) {
            return null;
        }
        return new btTransformDoubleData(jBtMultiBodyDoubleData_baseWorldTransform_get, false);
    }

    public void setBaseInertia(btVector3DoubleData btvector3doubledata) {
        DynamicsJNI.btMultiBodyDoubleData_baseInertia_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getBaseInertia() {
        long jBtMultiBodyDoubleData_baseInertia_get = DynamicsJNI.btMultiBodyDoubleData_baseInertia_get(this.swigCPtr, this);
        if (jBtMultiBodyDoubleData_baseInertia_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtMultiBodyDoubleData_baseInertia_get, false);
    }

    public void setBaseMass(double d) {
        DynamicsJNI.btMultiBodyDoubleData_baseMass_set(this.swigCPtr, this, d);
    }

    public double getBaseMass() {
        return DynamicsJNI.btMultiBodyDoubleData_baseMass_get(this.swigCPtr, this);
    }

    public void setBaseName(String str) {
        DynamicsJNI.btMultiBodyDoubleData_baseName_set(this.swigCPtr, this, str);
    }

    public String getBaseName() {
        return DynamicsJNI.btMultiBodyDoubleData_baseName_get(this.swigCPtr, this);
    }

    public void setLinks(btMultiBodyLinkDoubleData btmultibodylinkdoubledata) {
        DynamicsJNI.btMultiBodyDoubleData_links_set(this.swigCPtr, this, btMultiBodyLinkDoubleData.getCPtr(btmultibodylinkdoubledata), btmultibodylinkdoubledata);
    }

    public btMultiBodyLinkDoubleData getLinks() {
        long jBtMultiBodyDoubleData_links_get = DynamicsJNI.btMultiBodyDoubleData_links_get(this.swigCPtr, this);
        if (jBtMultiBodyDoubleData_links_get == 0) {
            return null;
        }
        return new btMultiBodyLinkDoubleData(jBtMultiBodyDoubleData_links_get, false);
    }

    public void setBaseCollider(btCollisionObjectDoubleData btcollisionobjectdoubledata) {
        DynamicsJNI.btMultiBodyDoubleData_baseCollider_set(this.swigCPtr, this, btCollisionObjectDoubleData.getCPtr(btcollisionobjectdoubledata), btcollisionobjectdoubledata);
    }

    public btCollisionObjectDoubleData getBaseCollider() {
        long jBtMultiBodyDoubleData_baseCollider_get = DynamicsJNI.btMultiBodyDoubleData_baseCollider_get(this.swigCPtr, this);
        if (jBtMultiBodyDoubleData_baseCollider_get == 0) {
            return null;
        }
        return new btCollisionObjectDoubleData(jBtMultiBodyDoubleData_baseCollider_get, false);
    }

    public void setPaddingPtr(String str) {
        DynamicsJNI.btMultiBodyDoubleData_paddingPtr_set(this.swigCPtr, this, str);
    }

    public String getPaddingPtr() {
        return DynamicsJNI.btMultiBodyDoubleData_paddingPtr_get(this.swigCPtr, this);
    }

    public void setNumLinks(int i) {
        DynamicsJNI.btMultiBodyDoubleData_numLinks_set(this.swigCPtr, this, i);
    }

    public int getNumLinks() {
        return DynamicsJNI.btMultiBodyDoubleData_numLinks_get(this.swigCPtr, this);
    }

    public void setPadding(String str) {
        DynamicsJNI.btMultiBodyDoubleData_padding_set(this.swigCPtr, this, str);
    }

    public String getPadding() {
        return DynamicsJNI.btMultiBodyDoubleData_padding_get(this.swigCPtr, this);
    }

    public btMultiBodyDoubleData() {
        this(DynamicsJNI.new_btMultiBodyDoubleData(), true);
    }
}
