package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.LongBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btAxisSweep3InternalInt extends btBroadphaseInterface {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btAxisSweep3InternalInt(String str, long j, boolean z) {
        super(str, CollisionJNI.btAxisSweep3InternalInt_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btAxisSweep3InternalInt(long j, boolean z) {
        this("btAxisSweep3InternalInt", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btAxisSweep3InternalInt_SWIGUpcast(j), z);
    }

    public static long getCPtr(btAxisSweep3InternalInt btaxissweep3internalint) {
        if (btaxissweep3internalint == null) {
            return 0L;
        }
        return btaxissweep3internalint.swigCPtr;
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
                CollisionJNI.delete_btAxisSweep3InternalInt(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btAxisSweep3InternalInt_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btAxisSweep3InternalInt_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btAxisSweep3InternalInt_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btAxisSweep3InternalInt_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btAxisSweep3InternalInt_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btAxisSweep3InternalInt_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btAxisSweep3InternalInt_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btAxisSweep3InternalInt_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
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
                    CollisionJNI.delete_btAxisSweep3InternalInt_Edge(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setPos(long j) {
            CollisionJNI.btAxisSweep3InternalInt_Edge_pos_set(this.swigCPtr, this, j);
        }

        public long getPos() {
            return CollisionJNI.btAxisSweep3InternalInt_Edge_pos_get(this.swigCPtr, this);
        }

        public void setHandle(long j) {
            CollisionJNI.btAxisSweep3InternalInt_Edge_handle_set(this.swigCPtr, this, j);
        }

        public long getHandle() {
            return CollisionJNI.btAxisSweep3InternalInt_Edge_handle_get(this.swigCPtr, this);
        }

        public long IsMax() {
            return CollisionJNI.btAxisSweep3InternalInt_Edge_IsMax(this.swigCPtr, this);
        }

        public Edge() {
            this(CollisionJNI.new_btAxisSweep3InternalInt_Edge(), true);
        }
    }

    public static class Handle extends btBroadphaseProxy {
        private long swigCPtr;

        protected Handle(String str, long j, boolean z) {
            super(str, CollisionJNI.btAxisSweep3InternalInt_Handle_SWIGUpcast(j), z);
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
            super.reset(CollisionJNI.btAxisSweep3InternalInt_Handle_SWIGUpcast(j), z);
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
                    CollisionJNI.delete_btAxisSweep3InternalInt_Handle(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public long operatorNew(long j) {
            return CollisionJNI.btAxisSweep3InternalInt_Handle_operatorNew__SWIG_0(this.swigCPtr, this, j);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public void operatorDelete(long j) {
            CollisionJNI.btAxisSweep3InternalInt_Handle_operatorDelete__SWIG_0(this.swigCPtr, this, j);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public long operatorNew(long j, long j2) {
            return CollisionJNI.btAxisSweep3InternalInt_Handle_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public void operatorDelete(long j, long j2) {
            CollisionJNI.btAxisSweep3InternalInt_Handle_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public long operatorNewArray(long j) {
            return CollisionJNI.btAxisSweep3InternalInt_Handle_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public void operatorDeleteArray(long j) {
            CollisionJNI.btAxisSweep3InternalInt_Handle_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public long operatorNewArray(long j, long j2) {
            return CollisionJNI.btAxisSweep3InternalInt_Handle_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy
        public void operatorDeleteArray(long j, long j2) {
            CollisionJNI.btAxisSweep3InternalInt_Handle_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
        }

        public void setMinEdges(long[] jArr) {
            CollisionJNI.btAxisSweep3InternalInt_Handle_minEdges_set(this.swigCPtr, this, jArr);
        }

        public long[] getMinEdges() {
            return CollisionJNI.btAxisSweep3InternalInt_Handle_minEdges_get(this.swigCPtr, this);
        }

        public void setMaxEdges(long[] jArr) {
            CollisionJNI.btAxisSweep3InternalInt_Handle_maxEdges_set(this.swigCPtr, this, jArr);
        }

        public long[] getMaxEdges() {
            return CollisionJNI.btAxisSweep3InternalInt_Handle_maxEdges_get(this.swigCPtr, this);
        }

        public void setDbvtProxy(btBroadphaseProxy btbroadphaseproxy) {
            CollisionJNI.btAxisSweep3InternalInt_Handle_dbvtProxy_set(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy);
        }

        public btBroadphaseProxy getDbvtProxy() {
            return btBroadphaseProxy.internalTemp(CollisionJNI.btAxisSweep3InternalInt_Handle_dbvtProxy_get(this.swigCPtr, this), false);
        }

        public void SetNextFree(long j) {
            CollisionJNI.btAxisSweep3InternalInt_Handle_SetNextFree(this.swigCPtr, this, j);
        }

        public long GetNextFree() {
            return CollisionJNI.btAxisSweep3InternalInt_Handle_GetNextFree(this.swigCPtr, this);
        }

        public Handle() {
            this(CollisionJNI.new_btAxisSweep3InternalInt_Handle(), true);
        }
    }

    public btAxisSweep3InternalInt(Vector3 vector3, Vector3 vector4, long j, long j2, long j3, btOverlappingPairCache btoverlappingpaircache, boolean z) {
        this(CollisionJNI.new_btAxisSweep3InternalInt__SWIG_0(vector3, vector4, j, j2, j3, btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache, z), true);
    }

    public btAxisSweep3InternalInt(Vector3 vector3, Vector3 vector4, long j, long j2, long j3, btOverlappingPairCache btoverlappingpaircache) {
        this(CollisionJNI.new_btAxisSweep3InternalInt__SWIG_1(vector3, vector4, j, j2, j3, btOverlappingPairCache.getCPtr(btoverlappingpaircache), btoverlappingpaircache), true);
    }

    public btAxisSweep3InternalInt(Vector3 vector3, Vector3 vector4, long j, long j2, long j3) {
        this(CollisionJNI.new_btAxisSweep3InternalInt__SWIG_2(vector3, vector4, j, j2, j3), true);
    }

    public btAxisSweep3InternalInt(Vector3 vector3, Vector3 vector4, long j, long j2) {
        this(CollisionJNI.new_btAxisSweep3InternalInt__SWIG_3(vector3, vector4, j, j2), true);
    }

    public long getNumHandles() {
        return CollisionJNI.btAxisSweep3InternalInt_getNumHandles(this.swigCPtr, this);
    }

    public long addHandle(Vector3 vector3, Vector3 vector4, long j, int i, int i2, btDispatcher btdispatcher) {
        return CollisionJNI.btAxisSweep3InternalInt_addHandle(this.swigCPtr, this, vector3, vector4, j, i, i2, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void removeHandle(long j, btDispatcher btdispatcher) {
        CollisionJNI.btAxisSweep3InternalInt_removeHandle(this.swigCPtr, this, j, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public void updateHandle(long j, Vector3 vector3, Vector3 vector4, btDispatcher btdispatcher) {
        CollisionJNI.btAxisSweep3InternalInt_updateHandle(this.swigCPtr, this, j, vector3, vector4, btDispatcher.getCPtr(btdispatcher), btdispatcher);
    }

    public Handle getHandle(long j) {
        long jBtAxisSweep3InternalInt_getHandle = CollisionJNI.btAxisSweep3InternalInt_getHandle(this.swigCPtr, this, j);
        if (jBtAxisSweep3InternalInt_getHandle == 0) {
            return null;
        }
        return new Handle(jBtAxisSweep3InternalInt_getHandle, false);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5, Vector3 vector6) {
        CollisionJNI.btAxisSweep3InternalInt_rayTest__SWIG_0(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback, vector5, vector6);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback, Vector3 vector5) {
        CollisionJNI.btAxisSweep3InternalInt_rayTest__SWIG_1(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback, vector5);
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
    public void rayTest(Vector3 vector3, Vector3 vector4, btBroadphaseRayCallback btbroadphaseraycallback) {
        CollisionJNI.btAxisSweep3InternalInt_rayTest__SWIG_2(this.swigCPtr, this, vector3, vector4, btBroadphaseRayCallback.getCPtr(btbroadphaseraycallback), btbroadphaseraycallback);
    }

    public void quantize(LongBuffer longBuffer, Vector3 vector3, int i) {
        CollisionJNI.btAxisSweep3InternalInt_quantize(this.swigCPtr, this, longBuffer, vector3, i);
    }

    public void unQuantize(btBroadphaseProxy btbroadphaseproxy, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btAxisSweep3InternalInt_unQuantize(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, vector3, vector4);
    }

    public boolean testAabbOverlap(btBroadphaseProxy btbroadphaseproxy, btBroadphaseProxy btbroadphaseproxy2) {
        return CollisionJNI.btAxisSweep3InternalInt_testAabbOverlap(this.swigCPtr, this, btBroadphaseProxy.getCPtr(btbroadphaseproxy), btbroadphaseproxy, btBroadphaseProxy.getCPtr(btbroadphaseproxy2), btbroadphaseproxy2);
    }

    public void setOverlappingPairUserCallback(btOverlappingPairCallback btoverlappingpaircallback) {
        CollisionJNI.btAxisSweep3InternalInt_setOverlappingPairUserCallback(this.swigCPtr, this, btOverlappingPairCallback.getCPtr(btoverlappingpaircallback), btoverlappingpaircallback);
    }

    public btOverlappingPairCallback getOverlappingPairUserCallback() {
        long jBtAxisSweep3InternalInt_getOverlappingPairUserCallback = CollisionJNI.btAxisSweep3InternalInt_getOverlappingPairUserCallback(this.swigCPtr, this);
        if (jBtAxisSweep3InternalInt_getOverlappingPairUserCallback == 0) {
            return null;
        }
        return new btOverlappingPairCallback(jBtAxisSweep3InternalInt_getOverlappingPairUserCallback, false);
    }
}
