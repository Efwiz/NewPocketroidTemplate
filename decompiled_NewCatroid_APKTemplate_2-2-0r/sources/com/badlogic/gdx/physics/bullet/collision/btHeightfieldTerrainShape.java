package com.badlogic.gdx.physics.bullet.collision;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btHeightfieldTerrainShape extends btConcaveShape {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btHeightfieldTerrainShape(String str, long j, boolean z) {
        super(str, CollisionJNI.btHeightfieldTerrainShape_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btHeightfieldTerrainShape(long j, boolean z) {
        this("btHeightfieldTerrainShape", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btHeightfieldTerrainShape_SWIGUpcast(j), z);
    }

    public static long getCPtr(btHeightfieldTerrainShape btheightfieldterrainshape) {
        if (btheightfieldterrainshape == null) {
            return 0L;
        }
        return btheightfieldterrainshape.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btHeightfieldTerrainShape(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j) {
        return CollisionJNI.btHeightfieldTerrainShape_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j) {
        CollisionJNI.btHeightfieldTerrainShape_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btHeightfieldTerrainShape_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btHeightfieldTerrainShape_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j) {
        return CollisionJNI.btHeightfieldTerrainShape_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j) {
        CollisionJNI.btHeightfieldTerrainShape_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btHeightfieldTerrainShape_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btHeightfieldTerrainShape_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setUseDiamondSubdivision(boolean z) {
        CollisionJNI.btHeightfieldTerrainShape_setUseDiamondSubdivision__SWIG_0(this.swigCPtr, this, z);
    }

    public void setUseDiamondSubdivision() {
        CollisionJNI.btHeightfieldTerrainShape_setUseDiamondSubdivision__SWIG_1(this.swigCPtr, this);
    }

    public void setUseZigzagSubdivision(boolean z) {
        CollisionJNI.btHeightfieldTerrainShape_setUseZigzagSubdivision__SWIG_0(this.swigCPtr, this, z);
    }

    public void setUseZigzagSubdivision() {
        CollisionJNI.btHeightfieldTerrainShape_setUseZigzagSubdivision__SWIG_1(this.swigCPtr, this);
    }

    private static long SwigConstructbtHeightfieldTerrainShape(int i, int i2, FloatBuffer floatBuffer, float f, float f2, float f3, int i3, boolean z) {
        return CollisionJNI.new_btHeightfieldTerrainShape__SWIG_0(i, i2, floatBuffer, f, f2, f3, i3, z);
    }

    public btHeightfieldTerrainShape(int i, int i2, FloatBuffer floatBuffer, float f, float f2, float f3, int i3, boolean z) {
        this(SwigConstructbtHeightfieldTerrainShape(i, i2, floatBuffer, f, f2, f3, i3, z), true);
    }

    private static long SwigConstructbtHeightfieldTerrainShape(int i, int i2, ShortBuffer shortBuffer, float f, float f2, float f3, int i3, boolean z) {
        return CollisionJNI.new_btHeightfieldTerrainShape__SWIG_1(i, i2, shortBuffer, f, f2, f3, i3, z);
    }

    public btHeightfieldTerrainShape(int i, int i2, ShortBuffer shortBuffer, float f, float f2, float f3, int i3, boolean z) {
        this(SwigConstructbtHeightfieldTerrainShape(i, i2, shortBuffer, f, f2, f3, i3, z), true);
    }
}
