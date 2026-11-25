package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btDefaultCollisionConfiguration extends btCollisionConfiguration {
    private long swigCPtr;

    protected btDefaultCollisionConfiguration(String str, long j, boolean z) {
        super(str, CollisionJNI.btDefaultCollisionConfiguration_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btDefaultCollisionConfiguration(long j, boolean z) {
        this("btDefaultCollisionConfiguration", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btDefaultCollisionConfiguration_SWIGUpcast(j), z);
    }

    public static long getCPtr(btDefaultCollisionConfiguration btdefaultcollisionconfiguration) {
        if (btdefaultcollisionconfiguration == null) {
            return 0L;
        }
        return btdefaultcollisionconfiguration.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btDefaultCollisionConfiguration(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btDefaultCollisionConfiguration(btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo) {
        this(CollisionJNI.new_btDefaultCollisionConfiguration__SWIG_0(btDefaultCollisionConstructionInfo.getCPtr(btdefaultcollisionconstructioninfo), btdefaultcollisionconstructioninfo), true);
    }

    public btDefaultCollisionConfiguration() {
        this(CollisionJNI.new_btDefaultCollisionConfiguration__SWIG_1(), true);
    }

    public void setConvexConvexMultipointIterations(int i, int i2) {
        CollisionJNI.btDefaultCollisionConfiguration_setConvexConvexMultipointIterations__SWIG_0(this.swigCPtr, this, i, i2);
    }

    public void setConvexConvexMultipointIterations(int i) {
        CollisionJNI.btDefaultCollisionConfiguration_setConvexConvexMultipointIterations__SWIG_1(this.swigCPtr, this, i);
    }

    public void setConvexConvexMultipointIterations() {
        CollisionJNI.btDefaultCollisionConfiguration_setConvexConvexMultipointIterations__SWIG_2(this.swigCPtr, this);
    }

    public void setPlaneConvexMultipointIterations(int i, int i2) {
        CollisionJNI.btDefaultCollisionConfiguration_setPlaneConvexMultipointIterations__SWIG_0(this.swigCPtr, this, i, i2);
    }

    public void setPlaneConvexMultipointIterations(int i) {
        CollisionJNI.btDefaultCollisionConfiguration_setPlaneConvexMultipointIterations__SWIG_1(this.swigCPtr, this, i);
    }

    public void setPlaneConvexMultipointIterations() {
        CollisionJNI.btDefaultCollisionConfiguration_setPlaneConvexMultipointIterations__SWIG_2(this.swigCPtr, this);
    }
}
