package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;

/* JADX INFO: loaded from: classes8.dex */
public class btDiscreteDynamicsWorldMt extends btDiscreteDynamicsWorld {
    private long swigCPtr;

    protected btDiscreteDynamicsWorldMt(String str, long j, boolean z) {
        super(str, DynamicsJNI.btDiscreteDynamicsWorldMt_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btDiscreteDynamicsWorldMt(long j, boolean z) {
        this("btDiscreteDynamicsWorldMt", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(DynamicsJNI.btDiscreteDynamicsWorldMt_SWIGUpcast(j), z);
    }

    public static long getCPtr(btDiscreteDynamicsWorldMt btdiscretedynamicsworldmt) {
        if (btdiscretedynamicsworldmt == null) {
            return 0L;
        }
        return btdiscretedynamicsworldmt.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld, com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld, com.badlogic.gdx.physics.bullet.collision.btCollisionWorld, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                DynamicsJNI.delete_btDiscreteDynamicsWorldMt(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld
    public long operatorNew(long j) {
        return DynamicsJNI.btDiscreteDynamicsWorldMt_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld
    public void operatorDelete(long j) {
        DynamicsJNI.btDiscreteDynamicsWorldMt_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld
    public long operatorNew(long j, long j2) {
        return DynamicsJNI.btDiscreteDynamicsWorldMt_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld
    public void operatorDelete(long j, long j2) {
        DynamicsJNI.btDiscreteDynamicsWorldMt_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld
    public long operatorNewArray(long j) {
        return DynamicsJNI.btDiscreteDynamicsWorldMt_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld
    public void operatorDeleteArray(long j) {
        DynamicsJNI.btDiscreteDynamicsWorldMt_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld
    public long operatorNewArray(long j, long j2) {
        return DynamicsJNI.btDiscreteDynamicsWorldMt_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    @Override // com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld
    public void operatorDeleteArray(long j, long j2) {
        DynamicsJNI.btDiscreteDynamicsWorldMt_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btDiscreteDynamicsWorldMt(btDispatcher btdispatcher, btBroadphaseInterface btbroadphaseinterface, btConstraintSolverPoolMt btconstraintsolverpoolmt, btCollisionConfiguration btcollisionconfiguration) {
        this(DynamicsJNI.new_btDiscreteDynamicsWorldMt(btDispatcher.getCPtr(btdispatcher), btdispatcher, btBroadphaseInterface.getCPtr(btbroadphaseinterface), btbroadphaseinterface, btConstraintSolverPoolMt.getCPtr(btconstraintsolverpoolmt), btconstraintsolverpoolmt, btCollisionConfiguration.getCPtr(btcollisionconfiguration), btcollisionconfiguration), true);
    }
}
