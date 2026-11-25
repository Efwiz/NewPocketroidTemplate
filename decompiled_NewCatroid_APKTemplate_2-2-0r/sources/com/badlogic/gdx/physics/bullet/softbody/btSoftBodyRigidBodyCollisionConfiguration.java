package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConstructionInfo;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftBodyRigidBodyCollisionConfiguration extends btDefaultCollisionConfiguration {
    private long swigCPtr;

    protected btSoftBodyRigidBodyCollisionConfiguration(String str, long j, boolean z) {
        super(str, SoftbodyJNI.btSoftBodyRigidBodyCollisionConfiguration_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSoftBodyRigidBodyCollisionConfiguration(long j, boolean z) {
        this("btSoftBodyRigidBodyCollisionConfiguration", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration, com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(SoftbodyJNI.btSoftBodyRigidBodyCollisionConfiguration_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSoftBodyRigidBodyCollisionConfiguration btsoftbodyrigidbodycollisionconfiguration) {
        if (btsoftbodyrigidbodycollisionconfiguration == null) {
            return 0L;
        }
        return btsoftbodyrigidbodycollisionconfiguration.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration, com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration, com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                SoftbodyJNI.delete_btSoftBodyRigidBodyCollisionConfiguration(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btSoftBodyRigidBodyCollisionConfiguration(btDefaultCollisionConstructionInfo btdefaultcollisionconstructioninfo) {
        this(SoftbodyJNI.new_btSoftBodyRigidBodyCollisionConfiguration__SWIG_0(btDefaultCollisionConstructionInfo.getCPtr(btdefaultcollisionconstructioninfo), btdefaultcollisionconstructioninfo), true);
    }

    public btSoftBodyRigidBodyCollisionConfiguration() {
        this(SoftbodyJNI.new_btSoftBodyRigidBodyCollisionConfiguration__SWIG_1(), true);
    }
}
