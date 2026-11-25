package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btUnionFind extends BulletBase {
    private long swigCPtr;

    protected btUnionFind(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btUnionFind(long j, boolean z) {
        this("btUnionFind", j, z);
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

    public static long getCPtr(btUnionFind btunionfind) {
        if (btunionfind == null) {
            return 0L;
        }
        return btunionfind.swigCPtr;
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
                CollisionJNI.delete_btUnionFind(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btUnionFind() {
        this(CollisionJNI.new_btUnionFind(), true);
    }

    public void sortIslands() {
        CollisionJNI.btUnionFind_sortIslands(this.swigCPtr, this);
    }

    public void reset(int i) {
        CollisionJNI.btUnionFind_reset(this.swigCPtr, this, i);
    }

    public int getNumElements() {
        return CollisionJNI.btUnionFind_getNumElements(this.swigCPtr, this);
    }

    public boolean isRoot(int i) {
        return CollisionJNI.btUnionFind_isRoot(this.swigCPtr, this, i);
    }

    public btElement getElement(int i) {
        return new btElement(CollisionJNI.btUnionFind_getElement(this.swigCPtr, this, i), false);
    }

    public btElement getElementConst(int i) {
        return new btElement(CollisionJNI.btUnionFind_getElementConst(this.swigCPtr, this, i), false);
    }

    public void allocate(int i) {
        CollisionJNI.btUnionFind_allocate(this.swigCPtr, this, i);
    }

    public void Free() {
        CollisionJNI.btUnionFind_Free(this.swigCPtr, this);
    }

    public int find(int i, int i2) {
        return CollisionJNI.btUnionFind_find__SWIG_0(this.swigCPtr, this, i, i2);
    }

    public void unite(int i, int i2) {
        CollisionJNI.btUnionFind_unite(this.swigCPtr, this, i, i2);
    }

    public int find(int i) {
        return CollisionJNI.btUnionFind_find__SWIG_1(this.swigCPtr, this, i);
    }
}
