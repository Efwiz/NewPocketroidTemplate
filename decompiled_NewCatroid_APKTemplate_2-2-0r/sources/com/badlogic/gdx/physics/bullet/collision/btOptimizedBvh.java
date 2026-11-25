package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes8.dex */
public class btOptimizedBvh extends btQuantizedBvh {
    private long swigCPtr;

    protected btOptimizedBvh(String str, long j, boolean z) {
        super(str, CollisionJNI.btOptimizedBvh_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btOptimizedBvh(long j, boolean z) {
        this("btOptimizedBvh", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btQuantizedBvh, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btOptimizedBvh_SWIGUpcast(j), z);
    }

    public static long getCPtr(btOptimizedBvh btoptimizedbvh) {
        if (btoptimizedbvh == null) {
            return 0L;
        }
        return btoptimizedbvh.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btQuantizedBvh, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btQuantizedBvh, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btOptimizedBvh(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btQuantizedBvh
    public long operatorNew(long j) {
        return CollisionJNI.btOptimizedBvh_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btQuantizedBvh
    public void operatorDelete(long j) {
        CollisionJNI.btOptimizedBvh_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btQuantizedBvh
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btOptimizedBvh_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btQuantizedBvh
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btOptimizedBvh_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btQuantizedBvh
    public long operatorNewArray(long j) {
        return CollisionJNI.btOptimizedBvh_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btQuantizedBvh
    public void operatorDeleteArray(long j) {
        CollisionJNI.btOptimizedBvh_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btQuantizedBvh
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btOptimizedBvh_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btQuantizedBvh
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btOptimizedBvh_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btOptimizedBvh() {
        this(CollisionJNI.new_btOptimizedBvh(), true);
    }

    public void build(btStridingMeshInterface btstridingmeshinterface, boolean z, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btOptimizedBvh_build(this.swigCPtr, this, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, z, vector3, vector4);
    }

    public void refit(btStridingMeshInterface btstridingmeshinterface, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btOptimizedBvh_refit(this.swigCPtr, this, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, vector3, vector4);
    }

    public void refitPartial(btStridingMeshInterface btstridingmeshinterface, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btOptimizedBvh_refitPartial(this.swigCPtr, this, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, vector3, vector4);
    }

    public void updateBvhNodes(btStridingMeshInterface btstridingmeshinterface, int i, int i2, int i3) {
        CollisionJNI.btOptimizedBvh_updateBvhNodes(this.swigCPtr, this, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, i, i2, i3);
    }

    public boolean serializeInPlace(long j, long j2, boolean z) {
        return CollisionJNI.btOptimizedBvh_serializeInPlace(this.swigCPtr, this, j, j2, z);
    }

    public static btOptimizedBvh deSerializeInPlace(long j, long j2, boolean z) {
        long jBtOptimizedBvh_deSerializeInPlace = CollisionJNI.btOptimizedBvh_deSerializeInPlace(j, j2, z);
        if (jBtOptimizedBvh_deSerializeInPlace == 0) {
            return null;
        }
        return new btOptimizedBvh(jBtOptimizedBvh_deSerializeInPlace, false);
    }
}
