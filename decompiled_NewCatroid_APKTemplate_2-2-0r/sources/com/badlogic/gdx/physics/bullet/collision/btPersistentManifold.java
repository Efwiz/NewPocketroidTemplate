package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.linearmath.btTypedObject;

/* JADX INFO: loaded from: classes8.dex */
public class btPersistentManifold extends btTypedObject {
    private long swigCPtr;

    protected btPersistentManifold(String str, long j, boolean z) {
        super(str, CollisionJNI.btPersistentManifold_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btPersistentManifold(long j, boolean z) {
        this("btPersistentManifold", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btPersistentManifold_SWIGUpcast(j), z);
    }

    public static long getCPtr(btPersistentManifold btpersistentmanifold) {
        if (btpersistentmanifold == null) {
            return 0L;
        }
        return btpersistentmanifold.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btTypedObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btPersistentManifold(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btPersistentManifold_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btPersistentManifold_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btPersistentManifold_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btPersistentManifold_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btPersistentManifold_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btPersistentManifold_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btPersistentManifold_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btPersistentManifold_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setCompanionIdA(int i) {
        CollisionJNI.btPersistentManifold_companionIdA_set(this.swigCPtr, this, i);
    }

    public int getCompanionIdA() {
        return CollisionJNI.btPersistentManifold_companionIdA_get(this.swigCPtr, this);
    }

    public void setCompanionIdB(int i) {
        CollisionJNI.btPersistentManifold_companionIdB_set(this.swigCPtr, this, i);
    }

    public int getCompanionIdB() {
        return CollisionJNI.btPersistentManifold_companionIdB_get(this.swigCPtr, this);
    }

    public void setIndex1a(int i) {
        CollisionJNI.btPersistentManifold_index1a_set(this.swigCPtr, this, i);
    }

    public int getIndex1a() {
        return CollisionJNI.btPersistentManifold_index1a_get(this.swigCPtr, this);
    }

    public btPersistentManifold() {
        this(CollisionJNI.new_btPersistentManifold__SWIG_0(), true);
    }

    public btPersistentManifold(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2, int i, float f, float f2) {
        this(CollisionJNI.new_btPersistentManifold__SWIG_1(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2, i, f, f2), true);
    }

    public btCollisionObject getBody0() {
        return btCollisionObject.getInstance(CollisionJNI.btPersistentManifold_getBody0(this.swigCPtr, this), false);
    }

    public btCollisionObject getBody1() {
        return btCollisionObject.getInstance(CollisionJNI.btPersistentManifold_getBody1(this.swigCPtr, this), false);
    }

    public void setBodies(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
        CollisionJNI.btPersistentManifold_setBodies(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
    }

    public void clearUserCache(btManifoldPoint btmanifoldpoint) {
        CollisionJNI.btPersistentManifold_clearUserCache(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint);
    }

    public int getNumContacts() {
        return CollisionJNI.btPersistentManifold_getNumContacts(this.swigCPtr, this);
    }

    public void setNumContacts(int i) {
        CollisionJNI.btPersistentManifold_setNumContacts(this.swigCPtr, this, i);
    }

    public btManifoldPoint getContactPointConst(int i) {
        return new btManifoldPoint(CollisionJNI.btPersistentManifold_getContactPointConst(this.swigCPtr, this, i), false);
    }

    public btManifoldPoint getContactPoint(int i) {
        return new btManifoldPoint(CollisionJNI.btPersistentManifold_getContactPoint(this.swigCPtr, this, i), false);
    }

    public float getContactBreakingThreshold() {
        return CollisionJNI.btPersistentManifold_getContactBreakingThreshold(this.swigCPtr, this);
    }

    public float getContactProcessingThreshold() {
        return CollisionJNI.btPersistentManifold_getContactProcessingThreshold(this.swigCPtr, this);
    }

    public void setContactBreakingThreshold(float f) {
        CollisionJNI.btPersistentManifold_setContactBreakingThreshold(this.swigCPtr, this, f);
    }

    public void setContactProcessingThreshold(float f) {
        CollisionJNI.btPersistentManifold_setContactProcessingThreshold(this.swigCPtr, this, f);
    }

    public int getCacheEntry(btManifoldPoint btmanifoldpoint) {
        return CollisionJNI.btPersistentManifold_getCacheEntry(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint);
    }

    public int addManifoldPoint(btManifoldPoint btmanifoldpoint, boolean z) {
        return CollisionJNI.btPersistentManifold_addManifoldPoint__SWIG_0(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, z);
    }

    public int addManifoldPoint(btManifoldPoint btmanifoldpoint) {
        return CollisionJNI.btPersistentManifold_addManifoldPoint__SWIG_1(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint);
    }

    public void removeContactPoint(int i) {
        CollisionJNI.btPersistentManifold_removeContactPoint(this.swigCPtr, this, i);
    }

    public void replaceContactPoint(btManifoldPoint btmanifoldpoint, int i) {
        CollisionJNI.btPersistentManifold_replaceContactPoint(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint, i);
    }

    public boolean validContactDistance(btManifoldPoint btmanifoldpoint) {
        return CollisionJNI.btPersistentManifold_validContactDistance(this.swigCPtr, this, btManifoldPoint.getCPtr(btmanifoldpoint), btmanifoldpoint);
    }

    public void refreshContactPoints(Matrix4 matrix4, Matrix4 matrix5) {
        CollisionJNI.btPersistentManifold_refreshContactPoints(this.swigCPtr, this, matrix4, matrix5);
    }

    public void clearManifold() {
        CollisionJNI.btPersistentManifold_clearManifold(this.swigCPtr, this);
    }
}
