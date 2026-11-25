package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;

/* JADX INFO: loaded from: classes8.dex */
public class btGjkEpaSolver2 extends BulletBase {
    private long swigCPtr;

    protected btGjkEpaSolver2(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGjkEpaSolver2(long j, boolean z) {
        this("btGjkEpaSolver2", j, z);
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

    public static long getCPtr(btGjkEpaSolver2 btgjkepasolver2) {
        if (btgjkepasolver2 == null) {
            return 0L;
        }
        return btgjkepasolver2.swigCPtr;
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
                CollisionJNI.delete_btGjkEpaSolver2(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class sResults extends BulletBase {
        private long swigCPtr;

        public static final class eStatus {
            public static final int EPA_Failed = 3;
            public static final int GJK_Failed = 2;
            public static final int Penetrating = 1;
            public static final int Separated = 0;
        }

        protected sResults(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public sResults(long j, boolean z) {
            this("sResults", j, z);
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

        public static long getCPtr(sResults sresults) {
            if (sresults == null) {
                return 0L;
            }
            return sresults.swigCPtr;
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
                    CollisionJNI.delete_btGjkEpaSolver2_sResults(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setStatus(int i) {
            CollisionJNI.btGjkEpaSolver2_sResults_status_set(this.swigCPtr, this, i);
        }

        public int getStatus() {
            return CollisionJNI.btGjkEpaSolver2_sResults_status_get(this.swigCPtr, this);
        }

        public void setWitnesses(btVector3 btvector3) {
            CollisionJNI.btGjkEpaSolver2_sResults_witnesses_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getWitnesses() {
            long jBtGjkEpaSolver2_sResults_witnesses_get = CollisionJNI.btGjkEpaSolver2_sResults_witnesses_get(this.swigCPtr, this);
            if (jBtGjkEpaSolver2_sResults_witnesses_get == 0) {
                return null;
            }
            return new btVector3(jBtGjkEpaSolver2_sResults_witnesses_get, false);
        }

        public void setNormal(btVector3 btvector3) {
            CollisionJNI.btGjkEpaSolver2_sResults_normal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getNormal() {
            long jBtGjkEpaSolver2_sResults_normal_get = CollisionJNI.btGjkEpaSolver2_sResults_normal_get(this.swigCPtr, this);
            if (jBtGjkEpaSolver2_sResults_normal_get == 0) {
                return null;
            }
            return new btVector3(jBtGjkEpaSolver2_sResults_normal_get, false);
        }

        public void setDistance(float f) {
            CollisionJNI.btGjkEpaSolver2_sResults_distance_set(this.swigCPtr, this, f);
        }

        public float getDistance() {
            return CollisionJNI.btGjkEpaSolver2_sResults_distance_get(this.swigCPtr, this);
        }

        public sResults() {
            this(CollisionJNI.new_btGjkEpaSolver2_sResults(), true);
        }
    }

    public static int StackSizeRequirement() {
        return CollisionJNI.btGjkEpaSolver2_StackSizeRequirement();
    }

    public static boolean Distance(btConvexShape btconvexshape, Matrix4 matrix4, btConvexShape btconvexshape2, Matrix4 matrix5, Vector3 vector3, sResults sresults) {
        return CollisionJNI.btGjkEpaSolver2_Distance(btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, btConvexShape.getCPtr(btconvexshape2), btconvexshape2, matrix5, vector3, sResults.getCPtr(sresults), sresults);
    }

    public static boolean Penetration(btConvexShape btconvexshape, Matrix4 matrix4, btConvexShape btconvexshape2, Matrix4 matrix5, Vector3 vector3, sResults sresults, boolean z) {
        return CollisionJNI.btGjkEpaSolver2_Penetration__SWIG_0(btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, btConvexShape.getCPtr(btconvexshape2), btconvexshape2, matrix5, vector3, sResults.getCPtr(sresults), sresults, z);
    }

    public static boolean Penetration(btConvexShape btconvexshape, Matrix4 matrix4, btConvexShape btconvexshape2, Matrix4 matrix5, Vector3 vector3, sResults sresults) {
        return CollisionJNI.btGjkEpaSolver2_Penetration__SWIG_1(btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, btConvexShape.getCPtr(btconvexshape2), btconvexshape2, matrix5, vector3, sResults.getCPtr(sresults), sresults);
    }

    public static float SignedDistance(Vector3 vector3, float f, btConvexShape btconvexshape, Matrix4 matrix4, sResults sresults) {
        return CollisionJNI.btGjkEpaSolver2_SignedDistance__SWIG_0(vector3, f, btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, sResults.getCPtr(sresults), sresults);
    }

    public static boolean SignedDistance(btConvexShape btconvexshape, Matrix4 matrix4, btConvexShape btconvexshape2, Matrix4 matrix5, Vector3 vector3, sResults sresults) {
        return CollisionJNI.btGjkEpaSolver2_SignedDistance__SWIG_1(btConvexShape.getCPtr(btconvexshape), btconvexshape, matrix4, btConvexShape.getCPtr(btconvexshape2), btconvexshape2, matrix5, vector3, sResults.getCPtr(sresults), sresults);
    }

    public btGjkEpaSolver2() {
        this(CollisionJNI.new_btGjkEpaSolver2(), true);
    }
}
