package com.badlogic.gdx.physics.bullet.collision;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleBuffer extends btTriangleCallback {
    private long swigCPtr;

    protected btTriangleBuffer(String str, long j, boolean z) {
        super(str, CollisionJNI.btTriangleBuffer_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btTriangleBuffer(long j, boolean z) {
        this("btTriangleBuffer", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btTriangleBuffer_SWIGUpcast(j), z);
    }

    public static long getCPtr(btTriangleBuffer bttrianglebuffer) {
        if (bttrianglebuffer == null) {
            return 0L;
        }
        return bttrianglebuffer.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleCallback, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btTriangleBuffer(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public int getNumTriangles() {
        return CollisionJNI.btTriangleBuffer_getNumTriangles(this.swigCPtr, this);
    }

    public btTriangle getTriangle(int i) {
        return new btTriangle(CollisionJNI.btTriangleBuffer_getTriangle(this.swigCPtr, this, i), false);
    }

    public void clearBuffer() {
        CollisionJNI.btTriangleBuffer_clearBuffer(this.swigCPtr, this);
    }

    public btTriangleBuffer() {
        this(CollisionJNI.new_btTriangleBuffer(), true);
    }
}
