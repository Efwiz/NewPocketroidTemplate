package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btPrimitiveManagerBase extends BulletBase {
    private long swigCPtr;

    protected btPrimitiveManagerBase(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btPrimitiveManagerBase(long j, boolean z) {
        this("btPrimitiveManagerBase", j, z);
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

    public static long getCPtr(btPrimitiveManagerBase btprimitivemanagerbase) {
        if (btprimitivemanagerbase == null) {
            return 0L;
        }
        return btprimitivemanagerbase.swigCPtr;
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
                CollisionJNI.delete_btPrimitiveManagerBase(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public boolean is_trimesh() {
        return CollisionJNI.btPrimitiveManagerBase_is_trimesh(this.swigCPtr, this);
    }

    public int get_primitive_count() {
        return CollisionJNI.btPrimitiveManagerBase_get_primitive_count(this.swigCPtr, this);
    }

    public void get_primitive_box(int i, btAABB btaabb) {
        CollisionJNI.btPrimitiveManagerBase_get_primitive_box(this.swigCPtr, this, i, btAABB.getCPtr(btaabb), btaabb);
    }

    public void get_primitive_triangle(int i, btPrimitiveTriangle btprimitivetriangle) {
        CollisionJNI.btPrimitiveManagerBase_get_primitive_triangle(this.swigCPtr, this, i, btPrimitiveTriangle.getCPtr(btprimitivetriangle), btprimitivetriangle);
    }
}
