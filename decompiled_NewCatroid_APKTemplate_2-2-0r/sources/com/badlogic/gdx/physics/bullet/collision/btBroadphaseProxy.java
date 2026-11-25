package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.utils.Pool;

/* JADX INFO: loaded from: classes8.dex */
public class btBroadphaseProxy extends BulletBase {
    private long swigCPtr;
    protected static final btBroadphaseProxy temp = new btBroadphaseProxy(0, false);
    protected static final Pool<btBroadphaseProxy> pool = new Pool<btBroadphaseProxy>() { // from class: com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public btBroadphaseProxy newObject() {
            return new btBroadphaseProxy(0L, false);
        }
    };

    public static final class CollisionFilterGroups {
        public static final int AllFilter = -1;
        public static final int CharacterFilter = 32;
        public static final int DebrisFilter = 8;
        public static final int DefaultFilter = 1;
        public static final int KinematicFilter = 4;
        public static final int SensorTrigger = 16;
        public static final int StaticFilter = 2;
    }

    protected btBroadphaseProxy(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btBroadphaseProxy(long j, boolean z) {
        this("btBroadphaseProxy", j, z);
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

    public static long getCPtr(btBroadphaseProxy btbroadphaseproxy) {
        if (btbroadphaseproxy == null) {
            return 0L;
        }
        return btbroadphaseproxy.swigCPtr;
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
                CollisionJNI.delete_btBroadphaseProxy(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static btBroadphaseProxy internalTemp(long j, boolean z) {
        btBroadphaseProxy btbroadphaseproxy = temp;
        btbroadphaseproxy.reset(j, z);
        return btbroadphaseproxy;
    }

    public static btBroadphaseProxy obtain(long j, boolean z) {
        btBroadphaseProxy btbroadphaseproxyObtain = pool.obtain();
        btbroadphaseproxyObtain.reset(j, z);
        return btbroadphaseproxyObtain;
    }

    public static void free(btBroadphaseProxy btbroadphaseproxy) {
        btbroadphaseproxy.dispose();
        pool.free(btbroadphaseproxy);
    }

    public long operatorNew(long j) {
        return CollisionJNI.btBroadphaseProxy_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btBroadphaseProxy_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btBroadphaseProxy_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btBroadphaseProxy_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btBroadphaseProxy_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btBroadphaseProxy_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btBroadphaseProxy_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btBroadphaseProxy_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void setClientObject(long j) {
        CollisionJNI.btBroadphaseProxy_clientObject_set(this.swigCPtr, this, j);
    }

    public long getClientObject() {
        return CollisionJNI.btBroadphaseProxy_clientObject_get(this.swigCPtr, this);
    }

    public void setCollisionFilterGroup(int i) {
        CollisionJNI.btBroadphaseProxy_collisionFilterGroup_set(this.swigCPtr, this, i);
    }

    public int getCollisionFilterGroup() {
        return CollisionJNI.btBroadphaseProxy_collisionFilterGroup_get(this.swigCPtr, this);
    }

    public void setCollisionFilterMask(int i) {
        CollisionJNI.btBroadphaseProxy_collisionFilterMask_set(this.swigCPtr, this, i);
    }

    public int getCollisionFilterMask() {
        return CollisionJNI.btBroadphaseProxy_collisionFilterMask_get(this.swigCPtr, this);
    }

    public void setUniqueId(int i) {
        CollisionJNI.btBroadphaseProxy_uniqueId_set(this.swigCPtr, this, i);
    }

    public int getUniqueId() {
        return CollisionJNI.btBroadphaseProxy_uniqueId_get(this.swigCPtr, this);
    }

    public void setAabbMin(btVector3 btvector3) {
        CollisionJNI.btBroadphaseProxy_aabbMin_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAabbMin() {
        long jBtBroadphaseProxy_aabbMin_get = CollisionJNI.btBroadphaseProxy_aabbMin_get(this.swigCPtr, this);
        if (jBtBroadphaseProxy_aabbMin_get == 0) {
            return null;
        }
        return new btVector3(jBtBroadphaseProxy_aabbMin_get, false);
    }

    public void setAabbMax(btVector3 btvector3) {
        CollisionJNI.btBroadphaseProxy_aabbMax_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getAabbMax() {
        long jBtBroadphaseProxy_aabbMax_get = CollisionJNI.btBroadphaseProxy_aabbMax_get(this.swigCPtr, this);
        if (jBtBroadphaseProxy_aabbMax_get == 0) {
            return null;
        }
        return new btVector3(jBtBroadphaseProxy_aabbMax_get, false);
    }

    public int getUid() {
        return CollisionJNI.btBroadphaseProxy_getUid(this.swigCPtr, this);
    }

    public btBroadphaseProxy() {
        this(CollisionJNI.new_btBroadphaseProxy__SWIG_0(), true);
    }

    public btBroadphaseProxy(Vector3 vector3, Vector3 vector4, long j, int i, int i2) {
        this(CollisionJNI.new_btBroadphaseProxy__SWIG_1(vector3, vector4, j, i, i2), true);
    }

    public static boolean isPolyhedral(int i) {
        return CollisionJNI.btBroadphaseProxy_isPolyhedral(i);
    }

    public static boolean isConvex(int i) {
        return CollisionJNI.btBroadphaseProxy_isConvex(i);
    }

    public static boolean isNonMoving(int i) {
        return CollisionJNI.btBroadphaseProxy_isNonMoving(i);
    }

    public static boolean isConcave(int i) {
        return CollisionJNI.btBroadphaseProxy_isConcave(i);
    }

    public static boolean isCompound(int i) {
        return CollisionJNI.btBroadphaseProxy_isCompound(i);
    }

    public static boolean isSoftBody(int i) {
        return CollisionJNI.btBroadphaseProxy_isSoftBody(i);
    }

    public static boolean isInfinite(int i) {
        return CollisionJNI.btBroadphaseProxy_isInfinite(i);
    }

    public static boolean isConvex2d(int i) {
        return CollisionJNI.btBroadphaseProxy_isConvex2d(i);
    }
}
