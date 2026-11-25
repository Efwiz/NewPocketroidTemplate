package com.badlogic.gdx.physics.bullet.dynamics;

/* JADX INFO: loaded from: classes8.dex */
public class FilterableVehicleRaycaster extends btDefaultVehicleRaycaster {
    private long swigCPtr;

    protected FilterableVehicleRaycaster(String str, long j, boolean z) {
        super(str, DynamicsJNI.FilterableVehicleRaycaster_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public FilterableVehicleRaycaster(long j, boolean z) {
        this("FilterableVehicleRaycaster", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDefaultVehicleRaycaster, com.badlogic.gdx.physics.bullet.dynamics.btVehicleRaycaster, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.FilterableVehicleRaycaster_SWIGUpcast(j), z);
    }

    public static long getCPtr(FilterableVehicleRaycaster filterableVehicleRaycaster) {
        if (filterableVehicleRaycaster == null) {
            return 0L;
        }
        return filterableVehicleRaycaster.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDefaultVehicleRaycaster, com.badlogic.gdx.physics.bullet.dynamics.btVehicleRaycaster, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDefaultVehicleRaycaster, com.badlogic.gdx.physics.bullet.dynamics.btVehicleRaycaster, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_FilterableVehicleRaycaster(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public FilterableVehicleRaycaster(btDynamicsWorld btdynamicsworld) {
        this(DynamicsJNI.new_FilterableVehicleRaycaster(btDynamicsWorld.getCPtr(btdynamicsworld), btdynamicsworld), true);
    }

    public void setCollisionFilterMask(short s) {
        DynamicsJNI.FilterableVehicleRaycaster_setCollisionFilterMask(this.swigCPtr, this, s);
    }

    public void setCollisionFilterGroup(short s) {
        DynamicsJNI.FilterableVehicleRaycaster_setCollisionFilterGroup(this.swigCPtr, this, s);
    }
}
