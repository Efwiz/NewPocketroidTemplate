package com.badlogic.gdx.physics.bullet.collision;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btTriangleIndexVertexMaterialArray extends btTriangleIndexVertexArray {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btTriangleIndexVertexMaterialArray(String str, long j, boolean z) {
        super(str, CollisionJNI.btTriangleIndexVertexMaterialArray_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btTriangleIndexVertexMaterialArray(long j, boolean z) {
        this("btTriangleIndexVertexMaterialArray", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btTriangleIndexVertexMaterialArray_SWIGUpcast(j), z);
    }

    public static long getCPtr(btTriangleIndexVertexMaterialArray bttriangleindexvertexmaterialarray) {
        if (bttriangleindexvertexmaterialarray == null) {
            return 0L;
        }
        return bttriangleindexvertexmaterialarray.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btTriangleIndexVertexMaterialArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public long operatorNew(long j) {
        return CollisionJNI.btTriangleIndexVertexMaterialArray_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void operatorDelete(long j) {
        CollisionJNI.btTriangleIndexVertexMaterialArray_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public long operatorNew(long j, long j2) {
        return CollisionJNI.btTriangleIndexVertexMaterialArray_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void operatorDelete(long j, long j2) {
        CollisionJNI.btTriangleIndexVertexMaterialArray_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public long operatorNewArray(long j) {
        return CollisionJNI.btTriangleIndexVertexMaterialArray_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void operatorDeleteArray(long j) {
        CollisionJNI.btTriangleIndexVertexMaterialArray_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btTriangleIndexVertexMaterialArray_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray, com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface
    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btTriangleIndexVertexMaterialArray_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btTriangleIndexVertexMaterialArray() {
        this(CollisionJNI.new_btTriangleIndexVertexMaterialArray__SWIG_0(), true);
    }

    private static long SwigConstructbtTriangleIndexVertexMaterialArray(int i, IntBuffer intBuffer, int i2, int i3, FloatBuffer floatBuffer, int i4, int i5, ByteBuffer byteBuffer, int i6, IntBuffer intBuffer2, int i7) {
        return CollisionJNI.new_btTriangleIndexVertexMaterialArray__SWIG_1(i, intBuffer, i2, i3, floatBuffer, i4, i5, byteBuffer, i6, intBuffer2, i7);
    }

    public btTriangleIndexVertexMaterialArray(int i, IntBuffer intBuffer, int i2, int i3, FloatBuffer floatBuffer, int i4, int i5, ByteBuffer byteBuffer, int i6, IntBuffer intBuffer2, int i7) {
        this(SwigConstructbtTriangleIndexVertexMaterialArray(i, intBuffer, i2, i3, floatBuffer, i4, i5, byteBuffer, i6, intBuffer2, i7), true);
    }

    public void addMaterialProperties(btMaterialProperties btmaterialproperties, int i) {
        CollisionJNI.btTriangleIndexVertexMaterialArray_addMaterialProperties__SWIG_0(this.swigCPtr, this, btMaterialProperties.getCPtr(btmaterialproperties), btmaterialproperties, i);
    }

    public void addMaterialProperties(btMaterialProperties btmaterialproperties) {
        CollisionJNI.btTriangleIndexVertexMaterialArray_addMaterialProperties__SWIG_1(this.swigCPtr, this, btMaterialProperties.getCPtr(btmaterialproperties), btmaterialproperties);
    }

    public void getLockedMaterialBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2, int i) {
        CollisionJNI.btTriangleIndexVertexMaterialArray_getLockedMaterialBase__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2), i);
    }

    public void getLockedMaterialBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2) {
        CollisionJNI.btTriangleIndexVertexMaterialArray_getLockedMaterialBase__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2));
    }

    public void getLockedReadOnlyMaterialBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2, int i) {
        CollisionJNI.btTriangleIndexVertexMaterialArray_getLockedReadOnlyMaterialBase__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2), i);
    }

    public void getLockedReadOnlyMaterialBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2) {
        CollisionJNI.btTriangleIndexVertexMaterialArray_getLockedReadOnlyMaterialBase__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2));
    }
}
