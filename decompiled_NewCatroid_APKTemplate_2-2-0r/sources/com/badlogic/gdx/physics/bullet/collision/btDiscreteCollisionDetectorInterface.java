package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;

/* JADX INFO: loaded from: classes8.dex */
public class btDiscreteCollisionDetectorInterface extends BulletBase {
    private long swigCPtr;

    protected btDiscreteCollisionDetectorInterface(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btDiscreteCollisionDetectorInterface(long j, boolean z) {
        this("btDiscreteCollisionDetectorInterface", j, z);
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

    public static long getCPtr(btDiscreteCollisionDetectorInterface btdiscretecollisiondetectorinterface) {
        if (btdiscretecollisiondetectorinterface == null) {
            return 0L;
        }
        return btdiscretecollisiondetectorinterface.swigCPtr;
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
                CollisionJNI.delete_btDiscreteCollisionDetectorInterface(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class Result extends BulletBase {
        private long swigCPtr;

        protected Result(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Result(long j, boolean z) {
            this("Result", j, z);
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

        public static long getCPtr(Result result) {
            if (result == null) {
                return 0L;
            }
            return result.swigCPtr;
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
                    CollisionJNI.delete_btDiscreteCollisionDetectorInterface_Result(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setShapeIdentifiersA(int i, int i2) {
            CollisionJNI.btDiscreteCollisionDetectorInterface_Result_setShapeIdentifiersA(this.swigCPtr, this, i, i2);
        }

        public void setShapeIdentifiersB(int i, int i2) {
            CollisionJNI.btDiscreteCollisionDetectorInterface_Result_setShapeIdentifiersB(this.swigCPtr, this, i, i2);
        }

        public void addContactPoint(Vector3 vector3, Vector3 vector4, float f) {
            CollisionJNI.btDiscreteCollisionDetectorInterface_Result_addContactPoint(this.swigCPtr, this, vector3, vector4, f);
        }
    }

    public static class ClosestPointInput extends BulletBase {
        private long swigCPtr;

        protected ClosestPointInput(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public ClosestPointInput(long j, boolean z) {
            this("ClosestPointInput", j, z);
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

        public static long getCPtr(ClosestPointInput closestPointInput) {
            if (closestPointInput == null) {
                return 0L;
            }
            return closestPointInput.swigCPtr;
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
                    CollisionJNI.delete_btDiscreteCollisionDetectorInterface_ClosestPointInput(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public ClosestPointInput() {
            this(CollisionJNI.new_btDiscreteCollisionDetectorInterface_ClosestPointInput(), true);
        }

        public void setTransformA(btTransform bttransform) {
            CollisionJNI.btDiscreteCollisionDetectorInterface_ClosestPointInput_transformA_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
        }

        public btTransform getTransformA() {
            long jBtDiscreteCollisionDetectorInterface_ClosestPointInput_transformA_get = CollisionJNI.btDiscreteCollisionDetectorInterface_ClosestPointInput_transformA_get(this.swigCPtr, this);
            if (jBtDiscreteCollisionDetectorInterface_ClosestPointInput_transformA_get == 0) {
                return null;
            }
            return new btTransform(jBtDiscreteCollisionDetectorInterface_ClosestPointInput_transformA_get, false);
        }

        public void setTransformB(btTransform bttransform) {
            CollisionJNI.btDiscreteCollisionDetectorInterface_ClosestPointInput_transformB_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
        }

        public btTransform getTransformB() {
            long jBtDiscreteCollisionDetectorInterface_ClosestPointInput_transformB_get = CollisionJNI.btDiscreteCollisionDetectorInterface_ClosestPointInput_transformB_get(this.swigCPtr, this);
            if (jBtDiscreteCollisionDetectorInterface_ClosestPointInput_transformB_get == 0) {
                return null;
            }
            return new btTransform(jBtDiscreteCollisionDetectorInterface_ClosestPointInput_transformB_get, false);
        }

        public void setMaximumDistanceSquared(float f) {
            CollisionJNI.btDiscreteCollisionDetectorInterface_ClosestPointInput_maximumDistanceSquared_set(this.swigCPtr, this, f);
        }

        public float getMaximumDistanceSquared() {
            return CollisionJNI.btDiscreteCollisionDetectorInterface_ClosestPointInput_maximumDistanceSquared_get(this.swigCPtr, this);
        }
    }

    public void getClosestPoints(ClosestPointInput closestPointInput, Result result, btIDebugDraw btidebugdraw, boolean z) {
        CollisionJNI.btDiscreteCollisionDetectorInterface_getClosestPoints__SWIG_0(this.swigCPtr, this, ClosestPointInput.getCPtr(closestPointInput), closestPointInput, Result.getCPtr(result), result, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw, z);
    }

    public void getClosestPoints(ClosestPointInput closestPointInput, Result result, btIDebugDraw btidebugdraw) {
        CollisionJNI.btDiscreteCollisionDetectorInterface_getClosestPoints__SWIG_1(this.swigCPtr, this, ClosestPointInput.getCPtr(closestPointInput), closestPointInput, Result.getCPtr(result), result, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
    }
}
