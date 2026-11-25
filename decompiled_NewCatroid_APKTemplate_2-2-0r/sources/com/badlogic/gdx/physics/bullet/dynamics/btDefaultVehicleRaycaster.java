package com.badlogic.gdx.physics.bullet.dynamics;

/* JADX INFO: loaded from: classes8.dex */
public class btDefaultVehicleRaycaster extends btVehicleRaycaster {
    private long swigCPtr;

    protected btDefaultVehicleRaycaster(String str, long j, boolean z) {
        super(str, DynamicsJNI.btDefaultVehicleRaycaster_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btDefaultVehicleRaycaster(long j, boolean z) {
        this("btDefaultVehicleRaycaster", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btVehicleRaycaster, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btDefaultVehicleRaycaster_SWIGUpcast(j), z);
    }

    public static long getCPtr(btDefaultVehicleRaycaster btdefaultvehicleraycaster) {
        if (btdefaultvehicleraycaster == null) {
            return 0L;
        }
        return btdefaultvehicleraycaster.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btVehicleRaycaster, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btVehicleRaycaster, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btDefaultVehicleRaycaster(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btDefaultVehicleRaycaster(btDynamicsWorld btdynamicsworld) {
        this(DynamicsJNI.new_btDefaultVehicleRaycaster(btDynamicsWorld.getCPtr(btdynamicsworld), btdynamicsworld), true);
    }
}
