package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btAxisSweep3InternalShort extends btBroadphaseInterface {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btAxisSweep3InternalShort(String str, long j, boolean z) {
        super(str, CollisionJNI.btAxisSweep3InternalShort_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btAxisSweep3InternalShort(long j, boolean z) {
        this("btAxisSweep3InternalShort", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btAxisSweep3InternalShort_SWIGUpcast(j), z);
    }

    public static long getCPtr(btAxisSweep3InternalShort btaxissweep3internalshort) {
        if (btaxissweep3internalshort == null) {
            return 0L;
        }
        return btaxissweep3internalshort.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btAxisSweep3InternalShort(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btAxisSweep3InternalShort_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btAxisSweep3InternalShort_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btAxisSweep3InternalShort_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btAxisSweep3InternalShort_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btAxisSweep3InternalShort_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btAxisSweep3InternalShort_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btAxisSweep3InternalShort_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btAxisSweep3InternalShort_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public static class Edge extends BulletBase {
        private long swigCPtr;

        protected Edge(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Edge(long j, boolean z) {
            this("Edge", j, z);
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

        public static long getCPtr(Edge edge) {
            if (edge == null) {
                return 0L;
            }
            return edge.swigCPtr;
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
                    CollisionJNI.delete_btAxisSweep3InternalShort_Edge(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setPos(int i) {
            CollisionJNI.btAxisSweep3InternalShort_Edge_pos_set(this.swigCPtr, this, i);
        }

        public int getPos() {
            return CollisionJNI.btAxisSweep3InternalShort_Edge_pos_get(this.swigCPtr, this);
        }

        public void setHandle(int i) {
            CollisionJNI.btAxisSweep3InternalShort_Edge_handle_set(this.swigCPtr, this, i);
        }

        public int getHandle() {
            return CollisionJNI.btAxisSweep3InternalShort_Edge_handle_get(this.swigCPtr, this);
        }

        public int IsMax() {
            return CollisionJNI.btAxisSweep3InternalShort_Edge_IsMax(this.swigCPtr, this);
        }

        public Edge() {
            this(CollisionJNI.new_btAxisSweep3InternalShort_Edge(), true);
        }
    }

    public static class Handle extends btBroadphaseProxy {
        private long swigCPtr;

        protected Handle(String str, long j, boolean z) {
            super(str, CollisionJNI.btAxisSweep3InternalShort_Handle_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public Handle(long j, boolean z) {
            this("Handle", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(CollisionJNI.btAxisSweep3InternalShort_Handle_SWIGUpcast(j), z);
        }

        public static long getCPtr(Handle handle) {
            if (handle == null) {
                return 0L;
            }
            return handle.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    CollisionJNI.delete_btAxisSweep3InternalShort_Handle(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public long operatorNew(long j) {
            return CollisionJNI.btAxisSweep3InternalShort_Handle_operatorNew__SWIG_0(this.swigCPtr, this, j);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public void operatorDelete(long j) {
            CollisionJNI.btAxisSweep3InternalShort_Handle_operatorDelete__SWIG_0(this.swigCPtr, this, j);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public long operatorNew(long j, long j2) {
            return CollisionJNI.btAxisSweep3InternalShort_Handle_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public void operatorDelete(long j, long j2) {
            CollisionJNI.btAxisSweep3InternalShort_Handle_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public long operatorNewArray(long j) {
            return CollisionJNI.btAxisSweep3InternalShort_Handle_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public void operatorDeleteArray(long j) {
            CollisionJNI.btAxisSweep3InternalShort_Handle_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public long operatorNewArray(long j, long j2) {
            return CollisionJNI.btAxisSweep3InternalShort_Handle_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public void operatorDeleteArray(long j, long j2) {
            CollisionJNI.btAxisSweep3InternalShort_Handle_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
        }

        public void setMinEdges(int[] iArr) {
            CollisionJNI.btAxisSweep3InternalShort_Handle_minEdges_set(this.swigCPtr, this, iArr);
        }

        public int[] getMinEdges() {
            return CollisionJNI.btAxisSweep3InternalShort_Handle_minEdges_get(this.swigCPtr, this);
        }

        public void setMaxEdges(int[] iArr) {
            CollisionJNI.btAxisSweep3InternalShort_Handle_maxEdges_set(this.swigCPtr, this, iArr);
        }

        public int[] getMaxEdges() {
            return CollisionJNI.btAxisSweep3InternalShort_Handle_maxEdges_get(this.swigCPtr, this);
        }

        public void setDbvtProxy(btBroadphaseProxy btbroadphaseproxy) {
            CollisionJNI.btAxisSweep3InternalShort_Handle_dbvtProxy_set(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
        }

        public btBroadphaseProxy getDbvtProxy() {
            return btBroadphaseProxy.internalTemp(CollisionJNI.btAxisSweep3InternalShort_Handle_dbvtProxy_get(this.swigCPtr, this), false);
        }

        public void SetNextFree(int i) {
            CollisionJNI.btAxisSweep3InternalShort_Handle_SetNextFree(this.swigCPtr, this, i);
        }

        public int GetNextFree() {
            return CollisionJNI.btAxisSweep3InternalShort_Handle_GetNextFree(this.swigCPtr, this);
        }

        public Handle() {
            this(CollisionJNI.new_btAxisSweep3InternalShort_Handle(), true);
        }
    }

    public btAxisSweep3InternalShort(Vector3 vector3, Vector3 vector4, int i, int i2, int i3, btOverlappingPairCache btoverlappingpaircache, boolean z) {
        this(CollisionJNI.new_btAxisSweep3InternalShort__SWIG_0(vector3, vector4, i, i2, i3, btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache, z), true);
    }

    public btAxisSweep3InternalShort(Vector3 vector3, Vector3 vector4, int i, int i2, int i3, btOverlappingPairCache btoverlappingpaircache) {
        this(CollisionJNI.new_btAxisSweep3InternalShort__SWIG_1(vector3, vector4, i, i2, i3, btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache), true);
    }

    public btAxisSweep3InternalShort(Vector3 vector3, Vector3 vector4, int i, int i2, int i3) {
        this(CollisionJNI.new_btAxisSweep3InternalShort__SWIG_2(vector3, vector4, i, i2, i3), true);
    }

    public btAxisSweep3InternalShort(Vector3 vector3, Vector3 vector4, int i, int i2) {
        this(CollisionJNI.new_btAxisSweep3InternalShort__SWIG_3(vector3, vector4, i, i2), true);
    }

    public int getNumHandles() {
        return CollisionJNI.btAxisSweep3InternalShort_getNumHandles(this.swigCPtr, this);
    }

    public int addHandle(Vector3 vector3, Vector3 vector4, long j, int i, int i2, btDispatcher btdispatcher) {
        return CollisionJNI.btAxisSweep3InternalShort_addHandle(this.swigCPtr, this, vector3, vector4, j, i, i2, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void removeHandle(int i, btDispatcher btdispatcher) {
        CollisionJNI.btAxisSweep3InternalShort_removeHandle(this.swigCPtr, this, i, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void updateHandle(int i, Vector3 vector3, Vector3 vector4, btDispatcher btdispatcher) {
        CollisionJNI.btAxisSweep3InternalShort_updateHandle(this.swigCPtr, this, i, vector3, vector4, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public Handle getHandle(int i) {
        long jBtAxisSweep3InternalShort_getHandle = CollisionJNI.btAxisSweep3InternalShort_getHandle(this.swigCPtr, this, i);
        if (jBtAxisSweep3InternalShort_getHandle == 0) {
            return null;
        }
        return new Handle(jBtAxisSweep3InternalShort_getHandle, false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5, Vector3 vector6) {
        CollisionJNI.btAxisSweep3InternalShort_rayTest__SWIG_0(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback, vector5, vector6);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5) {
        CollisionJNI.btAxisSweep3InternalShort_rayTest__SWIG_1(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback, vector5);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback) {
        CollisionJNI.btAxisSweep3InternalShort_rayTest__SWIG_2(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback);
    }

    public void quantize(IntBuffer intBuffer, Vector3 vector3, int i) {
        CollisionJNI.btAxisSweep3InternalShort_quantize(this.swigCPtr, this, intBuffer, vector3, i);
    }

    public void unQuantize(btBroadphaseProxy btbroadphaseproxy, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btAxisSweep3InternalShort_unQuantize(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, vector3, vector4);
    }

    public boolean testAabbOverlap(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2) {
        return CollisionJNI.btAxisSweep3InternalShort_testAabbOverlap(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2);
    }

    public void setOverlappingPairUserCallback(btOverlappingPairCallback btoverlappingpaircallback) {
        CollisionJNI.btAxisSweep3InternalShort_setOverlappingPairUserCallback(this.swigCPtr, this, btOverlappingPairCallback.getCPtr(btoverlappingpaircallback), btoverlappingpaircallback);
    }

    public btOverlappingPairCallback getOverlappingPairUserCallback() {
        long jBtAxisSweep3InternalShort_getOverlappingPairUserCallback = CollisionJNI.btAxisSweep3InternalShort_getOverlappingPairUserCallback(this.swigCPtr, this);
        if (jBtAxisSweep3InternalShort_getOverlappingPairUserCallback == 0) {
            return null;
        }
        return new btOverlappingPairCallback(jBtAxisSweep3InternalShort_getOverlappingPairUserCallback, false);
    }
}
