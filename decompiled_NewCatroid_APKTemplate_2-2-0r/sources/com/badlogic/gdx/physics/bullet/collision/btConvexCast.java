package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexCast extends BulletBase {
    private long swigCPtr;

    protected btConvexCast(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConvexCast(long j, boolean z) {
        this("btConvexCast", j, z);
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

    public static long getCPtr(btConvexCast btconvexcast) {
        if (btconvexcast == null) {
            return 0L;
        }
        return btconvexcast.swigCPtr;
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
                CollisionJNI.delete_btConvexCast(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class CastResult extends BulletBase {
        private long swigCPtr;

        protected CastResult(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public CastResult(long j, boolean z) {
            this("CastResult", j, z);
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

        public static long getCPtr(CastResult castResult) {
            if (castResult == null) {
                return 0L;
            }
            return castResult.swigCPtr;
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
                    CollisionJNI.delete_btConvexCast_CastResult(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void DebugDraw(float f) {
            CollisionJNI.btConvexCast_CastResult_DebugDraw(this.swigCPtr, this, f);
        }

        public void drawCoordSystem(Matrix4 matrix4) {
            CollisionJNI.btConvexCast_CastResult_drawCoordSystem(this.swigCPtr, this, matrix4);
        }

        public void reportFailure(int i, int i2) {
            CollisionJNI.btConvexCast_CastResult_reportFailure(this.swigCPtr, this, i, i2);
        }

        public CastResult() {
            this(CollisionJNI.new_btConvexCast_CastResult(), true);
        }

        public void setHitTransformA(btTransform bttransform) {
            CollisionJNI.btConvexCast_CastResult_hitTransformA_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
        }

        public btTransform getHitTransformA() {
            long jBtConvexCast_CastResult_hitTransformA_get = CollisionJNI.btConvexCast_CastResult_hitTransformA_get(this.swigCPtr, this);
            if (jBtConvexCast_CastResult_hitTransformA_get == 0) {
                return null;
            }
            return new btTransform(jBtConvexCast_CastResult_hitTransformA_get, false);
        }

        public void setHitTransformB(btTransform bttransform) {
            CollisionJNI.btConvexCast_CastResult_hitTransformB_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
        }

        public btTransform getHitTransformB() {
            long jBtConvexCast_CastResult_hitTransformB_get = CollisionJNI.btConvexCast_CastResult_hitTransformB_get(this.swigCPtr, this);
            if (jBtConvexCast_CastResult_hitTransformB_get == 0) {
                return null;
            }
            return new btTransform(jBtConvexCast_CastResult_hitTransformB_get, false);
        }

        public void setNormal(btVector3 btvector3) {
            CollisionJNI.btConvexCast_CastResult_normal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getNormal() {
            long jBtConvexCast_CastResult_normal_get = CollisionJNI.btConvexCast_CastResult_normal_get(this.swigCPtr, this);
            if (jBtConvexCast_CastResult_normal_get == 0) {
                return null;
            }
            return new btVector3(jBtConvexCast_CastResult_normal_get, false);
        }

        public void setHitPoint(btVector3 btvector3) {
            CollisionJNI.btConvexCast_CastResult_hitPoint_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getHitPoint() {
            long jBtConvexCast_CastResult_hitPoint_get = CollisionJNI.btConvexCast_CastResult_hitPoint_get(this.swigCPtr, this);
            if (jBtConvexCast_CastResult_hitPoint_get == 0) {
                return null;
            }
            return new btVector3(jBtConvexCast_CastResult_hitPoint_get, false);
        }

        public void setFraction(float f) {
            CollisionJNI.btConvexCast_CastResult_fraction_set(this.swigCPtr, this, f);
        }

        public float getFraction() {
            return CollisionJNI.btConvexCast_CastResult_fraction_get(this.swigCPtr, this);
        }

        public void setDebugDrawer(btIDebugDraw btidebugdraw) {
            CollisionJNI.btConvexCast_CastResult_debugDrawer_set(this.swigCPtr, this, btIDebugDraw.getCPtr(btidebugdraw), btidebugdraw);
        }

        public btIDebugDraw getDebugDrawer() {
            long jBtConvexCast_CastResult_debugDrawer_get = CollisionJNI.btConvexCast_CastResult_debugDrawer_get(this.swigCPtr, this);
            if (jBtConvexCast_CastResult_debugDrawer_get == 0) {
                return null;
            }
            return new btIDebugDraw(jBtConvexCast_CastResult_debugDrawer_get, false);
        }

        public void setAllowedPenetration(float f) {
            CollisionJNI.btConvexCast_CastResult_allowedPenetration_set(this.swigCPtr, this, f);
        }

        public float getAllowedPenetration() {
            return CollisionJNI.btConvexCast_CastResult_allowedPenetration_get(this.swigCPtr, this);
        }
    }

    public boolean calcTimeOfImpact(Matrix4 matrix4, Matrix4 matrix5, Matrix4 matrix6, Matrix4 matrix7, CastResult castResult) {
        return CollisionJNI.btConvexCast_calcTimeOfImpact(this.swigCPtr, this, matrix4, matrix5, matrix6, matrix7, CastResult.getCPtr(castResult), castResult);
    }
}
