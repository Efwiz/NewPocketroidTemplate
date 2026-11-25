package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btMaterial extends BulletBase {
    private long swigCPtr;

    protected btMaterial(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMaterial(long j, boolean z) {
        this("btMaterial", j, z);
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

    public static long getCPtr(btMaterial btmaterial) {
        if (btmaterial == null) {
            return 0L;
        }
        return btmaterial.swigCPtr;
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
                CollisionJNI.delete_btMaterial(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setFriction(float f) {
        CollisionJNI.btMaterial_friction_set(this.swigCPtr, this, f);
    }

    public float getFriction() {
        return CollisionJNI.btMaterial_friction_get(this.swigCPtr, this);
    }

    public void setRestitution(float f) {
        CollisionJNI.btMaterial_restitution_set(this.swigCPtr, this, f);
    }

    public float getRestitution() {
        return CollisionJNI.btMaterial_restitution_get(this.swigCPtr, this);
    }

    public void setPad(int[] iArr) {
        CollisionJNI.btMaterial_pad_set(this.swigCPtr, this, iArr);
    }

    public int[] getPad() {
        return CollisionJNI.btMaterial_pad_get(this.swigCPtr, this);
    }

    public btMaterial() {
        this(CollisionJNI.new_btMaterial__SWIG_0(), true);
    }

    public btMaterial(float f, float f2) {
        this(CollisionJNI.new_btMaterial__SWIG_1(f, f2), true);
    }
}
