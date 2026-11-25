package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btSerializer;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btStridingMeshInterface extends BulletBase {
    private long swigCPtr;

    protected btStridingMeshInterface(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btStridingMeshInterface(long j, boolean z) {
        this("btStridingMeshInterface", j, z);
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

    public static long getCPtr(btStridingMeshInterface btstridingmeshinterface) {
        if (btstridingmeshinterface == null) {
            return 0L;
        }
        return btstridingmeshinterface.swigCPtr;
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
                CollisionJNI.delete_btStridingMeshInterface(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btStridingMeshInterface_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btStridingMeshInterface_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btStridingMeshInterface_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btStridingMeshInterface_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btStridingMeshInterface_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btStridingMeshInterface_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btStridingMeshInterface_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btStridingMeshInterface_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void InternalProcessAllTriangles(btInternalTriangleIndexCallback btinternaltriangleindexcallback, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btStridingMeshInterface_InternalProcessAllTriangles(this.swigCPtr, this, btInternalTriangleIndexCallback.getCPtr(btinternaltriangleindexcallback), btinternaltriangleindexcallback, vector3, vector4);
    }

    public void calculateAabbBruteForce(Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btStridingMeshInterface_calculateAabbBruteForce(this.swigCPtr, this, vector3, vector4);
    }

    public void getLockedVertexIndexBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2, int i) {
        CollisionJNI.btStridingMeshInterface_getLockedVertexIndexBase__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2), i);
    }

    public void getLockedVertexIndexBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2) {
        CollisionJNI.btStridingMeshInterface_getLockedVertexIndexBase__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2));
    }

    public void getLockedReadOnlyVertexIndexBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2, int i) {
        CollisionJNI.btStridingMeshInterface_getLockedReadOnlyVertexIndexBase__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2), i);
    }

    public void getLockedReadOnlyVertexIndexBase(SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char, SWIGTYPE_p_int sWIGTYPE_p_int, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType, SWIGTYPE_p_int sWIGTYPE_p_int2, SWIGTYPE_p_p_unsigned_char sWIGTYPE_p_p_unsigned_char2, SWIGTYPE_p_int sWIGTYPE_p_int3, SWIGTYPE_p_int sWIGTYPE_p_int4, SWIGTYPE_p_PHY_ScalarType sWIGTYPE_p_PHY_ScalarType2) {
        CollisionJNI.btStridingMeshInterface_getLockedReadOnlyVertexIndexBase__SWIG_1(this.swigCPtr, this, SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int2), SWIGTYPE_p_p_unsigned_char.getCPtr(sWIGTYPE_p_p_unsigned_char2), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int3), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int4), SWIGTYPE_p_PHY_ScalarType.getCPtr(sWIGTYPE_p_PHY_ScalarType2));
    }

    public void unLockVertexBase(int i) {
        CollisionJNI.btStridingMeshInterface_unLockVertexBase(this.swigCPtr, this, i);
    }

    public void unLockReadOnlyVertexBase(int i) {
        CollisionJNI.btStridingMeshInterface_unLockReadOnlyVertexBase(this.swigCPtr, this, i);
    }

    public int getNumSubParts() {
        return CollisionJNI.btStridingMeshInterface_getNumSubParts(this.swigCPtr, this);
    }

    public void preallocateVertices(int i) {
        CollisionJNI.btStridingMeshInterface_preallocateVertices(this.swigCPtr, this, i);
    }

    public void preallocateIndices(int i) {
        CollisionJNI.btStridingMeshInterface_preallocateIndices(this.swigCPtr, this, i);
    }

    public boolean hasPremadeAabb() {
        return CollisionJNI.btStridingMeshInterface_hasPremadeAabb(this.swigCPtr, this);
    }

    public void setPremadeAabb(Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btStridingMeshInterface_setPremadeAabb(this.swigCPtr, this, vector3, vector4);
    }

    public void getPremadeAabb(btVector3 btvector3, btVector3 btvector4) {
        CollisionJNI.btStridingMeshInterface_getPremadeAabb(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3, btVector3.getCPtr(btvector4), btvector4);
    }

    public Vector3 getScaling() {
        return CollisionJNI.btStridingMeshInterface_getScaling(this.swigCPtr, this);
    }

    public void setScaling(Vector3 vector3) {
        CollisionJNI.btStridingMeshInterface_setScaling(this.swigCPtr, this, vector3);
    }

    public int calculateSerializeBufferSize() {
        return CollisionJNI.btStridingMeshInterface_calculateSerializeBufferSize(this.swigCPtr, this);
    }

    public String serialize(long j, btSerializer btserializer) {
        return CollisionJNI.btStridingMeshInterface_serialize(this.swigCPtr, this, j, btSerializer.getCPtr(btserializer), btserializer);
    }
}
