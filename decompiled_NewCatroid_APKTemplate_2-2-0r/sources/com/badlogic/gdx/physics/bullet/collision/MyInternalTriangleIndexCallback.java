package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class MyInternalTriangleIndexCallback extends btInternalTriangleIndexCallback {
    private long swigCPtr;

    protected MyInternalTriangleIndexCallback(String str, long j, boolean z) {
        super(str, CollisionJNI.MyInternalTriangleIndexCallback_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public MyInternalTriangleIndexCallback(long j, boolean z) {
        this("MyInternalTriangleIndexCallback", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btInternalTriangleIndexCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.MyInternalTriangleIndexCallback_SWIGUpcast(j), z);
    }

    public static long getCPtr(MyInternalTriangleIndexCallback myInternalTriangleIndexCallback) {
        if (myInternalTriangleIndexCallback == null) {
            return 0L;
        }
        return myInternalTriangleIndexCallback.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btInternalTriangleIndexCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btInternalTriangleIndexCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_MyInternalTriangleIndexCallback(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setGimpactShape(btGImpactMeshShape btgimpactmeshshape) {
        CollisionJNI.MyInternalTriangleIndexCallback_gimpactShape_set(this.swigCPtr, this, btGImpactMeshShape.getCPtr(btgimpactmeshshape), btgimpactmeshshape);
    }

    public btGImpactMeshShape getGimpactShape() {
        long jMyInternalTriangleIndexCallback_gimpactShape_get = CollisionJNI.MyInternalTriangleIndexCallback_gimpactShape_get(this.swigCPtr, this);
        if (jMyInternalTriangleIndexCallback_gimpactShape_get == 0) {
            return null;
        }
        return new btGImpactMeshShape(jMyInternalTriangleIndexCallback_gimpactShape_get, false);
    }

    public void setColShape(btCompoundShape btcompoundshape) {
        CollisionJNI.MyInternalTriangleIndexCallback_colShape_set(this.swigCPtr, this, btCompoundShape.getCPtr(btcompoundshape), btcompoundshape);
    }

    public btCompoundShape getColShape() {
        long jMyInternalTriangleIndexCallback_colShape_get = CollisionJNI.MyInternalTriangleIndexCallback_colShape_get(this.swigCPtr, this);
        if (jMyInternalTriangleIndexCallback_colShape_get == 0) {
            return null;
        }
        return new btCompoundShape(jMyInternalTriangleIndexCallback_colShape_get, false);
    }

    public void setDepth(float f) {
        CollisionJNI.MyInternalTriangleIndexCallback_depth_set(this.swigCPtr, this, f);
    }

    public float getDepth() {
        return CollisionJNI.MyInternalTriangleIndexCallback_depth_get(this.swigCPtr, this);
    }

    public MyInternalTriangleIndexCallback(btCompoundShape btcompoundshape, btGImpactMeshShape btgimpactmeshshape, float f) {
        this(CollisionJNI.new_MyInternalTriangleIndexCallback(btCompoundShape.getCPtr(btcompoundshape), btcompoundshape, btGImpactMeshShape.getCPtr(btgimpactmeshshape), btgimpactmeshshape, f), true);
    }
}
