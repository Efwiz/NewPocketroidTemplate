package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.ICollide;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectConstArray;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btDbvt;
import com.badlogic.gdx.physics.bullet.collision.btDbvtNode;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMatrix3x3;
import com.badlogic.gdx.physics.bullet.linearmath.btScalarArray;
import com.badlogic.gdx.physics.bullet.linearmath.btTransform;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3Array;
import com.badlogic.gdx.physics.bullet.linearmath.btVector4;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftBody extends btCollisionObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btSoftBody(String str, long j, boolean z) {
        super(str, SoftbodyJNI.btSoftBody_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btSoftBody(long j, boolean z) {
        this("btSoftBody", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(SoftbodyJNI.btSoftBody_SWIGUpcast(j), z);
    }

    public static long getCPtr(btSoftBody btsoftbody) {
        if (btsoftbody == null) {
            return 0L;
        }
        return btsoftbody.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btCollisionObject, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                SoftbodyJNI.delete_btSoftBody(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setCollisionDisabledObjects(btCollisionObjectConstArray btcollisionobjectconstarray) {
        SoftbodyJNI.btSoftBody_collisionDisabledObjects_set(this.swigCPtr, this, btCollisionObjectConstArray.getCPtr(btcollisionobjectconstarray), btcollisionobjectconstarray);
    }

    public btCollisionObjectConstArray getCollisionDisabledObjects() {
        long jBtSoftBody_collisionDisabledObjects_get = SoftbodyJNI.btSoftBody_collisionDisabledObjects_get(this.swigCPtr, this);
        if (jBtSoftBody_collisionDisabledObjects_get == 0) {
            return null;
        }
        return new btCollisionObjectConstArray(jBtSoftBody_collisionDisabledObjects_get, false);
    }

    public void setSoftBodySolver(btSoftBodySolver btsoftbodysolver) {
        SoftbodyJNI.btSoftBody_softBodySolver_set(this.swigCPtr, this, btSoftBodySolver.getCPtr(btsoftbodysolver), btsoftbodysolver);
    }

    public btSoftBodySolver getSoftBodySolver() {
        long jBtSoftBody_softBodySolver_get = SoftbodyJNI.btSoftBody_softBodySolver_get(this.swigCPtr, this);
        if (jBtSoftBody_softBodySolver_get == 0) {
            return null;
        }
        return new btSoftBodySolver(jBtSoftBody_softBodySolver_get, false);
    }

    public static class eAeroModel extends BulletBase {
        private long swigCPtr;

        public static final class EnumFlagType {
            public static final int END = 7;
            public static final int F_OneSided = 6;
            public static final int F_TwoSided = 4;
            public static final int F_TwoSidedLiftDrag = 5;
            public static final int V_OneSided = 3;
            public static final int V_Point = 0;
            public static final int V_TwoSided = 1;
            public static final int V_TwoSidedLiftDrag = 2;
        }

        protected eAeroModel(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public eAeroModel(long j, boolean z) {
            this("eAeroModel", j, z);
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

        public static long getCPtr(eAeroModel eaeromodel) {
            if (eaeromodel == null) {
                return 0L;
            }
            return eaeromodel.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_eAeroModel(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public eAeroModel() {
            this(SoftbodyJNI.new_btSoftBody_eAeroModel(), true);
        }
    }

    public static class eVSolver extends BulletBase {
        private long swigCPtr;

        public static final class EnumFlagType {
            public static final int END = 1;
            public static final int Linear = 0;
        }

        protected eVSolver(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public eVSolver(long j, boolean z) {
            this("eVSolver", j, z);
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

        public static long getCPtr(eVSolver evsolver) {
            if (evsolver == null) {
                return 0L;
            }
            return evsolver.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_eVSolver(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public eVSolver() {
            this(SoftbodyJNI.new_btSoftBody_eVSolver(), true);
        }
    }

    public static class ePSolver extends BulletBase {
        private long swigCPtr;

        public static final class EnumFlagType {
            public static final int Anchors = 1;
            public static final int END = 4;
            public static final int Linear = 0;
            public static final int RContacts = 2;
            public static final int SContacts = 3;
        }

        protected ePSolver(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public ePSolver(long j, boolean z) {
            this("ePSolver", j, z);
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

        public static long getCPtr(ePSolver epsolver) {
            if (epsolver == null) {
                return 0L;
            }
            return epsolver.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_ePSolver(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public ePSolver() {
            this(SoftbodyJNI.new_btSoftBody_ePSolver(), true);
        }
    }

    public static class eSolverPresets extends BulletBase {
        private long swigCPtr;

        public static final class EnumFlagType {
            public static final int Default = 0;
            public static final int END = 1;
            public static final int Positions = 0;
            public static final int Velocities = 1;
        }

        protected eSolverPresets(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public eSolverPresets(long j, boolean z) {
            this("eSolverPresets", j, z);
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

        public static long getCPtr(eSolverPresets esolverpresets) {
            if (esolverpresets == null) {
                return 0L;
            }
            return esolverpresets.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_eSolverPresets(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public eSolverPresets() {
            this(SoftbodyJNI.new_btSoftBody_eSolverPresets(), true);
        }
    }

    public static class eFeature extends BulletBase {
        private long swigCPtr;

        public static final class EnumFlagType {
            public static final int END = 5;
            public static final int Face = 3;
            public static final int Link = 2;
            public static final int Node = 1;
            public static final int None = 0;
            public static final int Tetra = 4;
        }

        protected eFeature(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public eFeature(long j, boolean z) {
            this("eFeature", j, z);
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

        public static long getCPtr(eFeature efeature) {
            if (efeature == null) {
                return 0L;
            }
            return efeature.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_eFeature(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public eFeature() {
            this(SoftbodyJNI.new_btSoftBody_eFeature(), true);
        }
    }

    public static class fCollision extends BulletBase {
        private long swigCPtr;

        public static final class EnumFlagType {
            public static final int CL_RS = 2;
            public static final int CL_SELF = 64;
            public static final int CL_SS = 32;
            public static final int Default = 1;
            public static final int END = 2;
            public static final int RVSmask = 15;
            public static final int SDF_RS = 1;
            public static final int SVSmask = 48;
            public static final int VF_SS = 16;
        }

        protected fCollision(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public fCollision(long j, boolean z) {
            this("fCollision", j, z);
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

        public static long getCPtr(fCollision fcollision) {
            if (fcollision == null) {
                return 0L;
            }
            return fcollision.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_fCollision(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public fCollision() {
            this(SoftbodyJNI.new_btSoftBody_fCollision(), true);
        }
    }

    public static class fMaterial extends BulletBase {
        private long swigCPtr;

        public static final class EnumFlagType {
            public static final int DebugDraw = 1;
            public static final int Default = 1;
            public static final int END = 2;
        }

        protected fMaterial(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public fMaterial(long j, boolean z) {
            this("fMaterial", j, z);
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

        public static long getCPtr(fMaterial fmaterial) {
            if (fmaterial == null) {
                return 0L;
            }
            return fmaterial.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_fMaterial(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public fMaterial() {
            this(SoftbodyJNI.new_btSoftBody_fMaterial(), true);
        }
    }

    public static class sRayCast extends BulletBase {
        private long swigCPtr;

        protected sRayCast(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public sRayCast(long j, boolean z) {
            this("sRayCast", j, z);
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

        public static long getCPtr(sRayCast sraycast) {
            if (sraycast == null) {
                return 0L;
            }
            return sraycast.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_sRayCast(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setBody(btSoftBody btsoftbody) {
            SoftbodyJNI.btSoftBody_sRayCast_body_set(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody);
        }

        public btSoftBody getBody() {
            long jBtSoftBody_sRayCast_body_get = SoftbodyJNI.btSoftBody_sRayCast_body_get(this.swigCPtr, this);
            if (jBtSoftBody_sRayCast_body_get == 0) {
                return null;
            }
            return new btSoftBody(jBtSoftBody_sRayCast_body_get, false);
        }

        public void setFeature(int i) {
            SoftbodyJNI.btSoftBody_sRayCast_feature_set(this.swigCPtr, this, i);
        }

        public int getFeature() {
            return SoftbodyJNI.btSoftBody_sRayCast_feature_get(this.swigCPtr, this);
        }

        public void setIndex(int i) {
            SoftbodyJNI.btSoftBody_sRayCast_index_set(this.swigCPtr, this, i);
        }

        public int getIndex() {
            return SoftbodyJNI.btSoftBody_sRayCast_index_get(this.swigCPtr, this);
        }

        public void setFraction(float f) {
            SoftbodyJNI.btSoftBody_sRayCast_fraction_set(this.swigCPtr, this, f);
        }

        public float getFraction() {
            return SoftbodyJNI.btSoftBody_sRayCast_fraction_get(this.swigCPtr, this);
        }

        public sRayCast() {
            this(SoftbodyJNI.new_btSoftBody_sRayCast(), true);
        }
    }

    public static class ImplicitFn extends BulletBase {
        private long swigCPtr;

        protected ImplicitFn(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public ImplicitFn(long j, boolean z) {
            this("ImplicitFn", j, z);
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

        public static long getCPtr(ImplicitFn implicitFn) {
            if (implicitFn == null) {
                return 0L;
            }
            return implicitFn.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_ImplicitFn(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public float Eval(Vector3 vector3) {
            return SoftbodyJNI.btSoftBody_ImplicitFn_Eval(this.swigCPtr, this, vector3);
        }
    }

    public static class sCti extends BulletBase {
        private long swigCPtr;

        protected sCti(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public sCti(long j, boolean z) {
            this("sCti", j, z);
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

        public static long getCPtr(sCti scti) {
            if (scti == null) {
                return 0L;
            }
            return scti.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_sCti(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setColObj(btCollisionObject btcollisionobject) {
            SoftbodyJNI.btSoftBody_sCti_colObj_set(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
        }

        public btCollisionObject getColObj() {
            return btCollisionObject.getInstance(SoftbodyJNI.btSoftBody_sCti_colObj_get(this.swigCPtr, this), false);
        }

        public void setNormal(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_sCti_normal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getNormal() {
            long jBtSoftBody_sCti_normal_get = SoftbodyJNI.btSoftBody_sCti_normal_get(this.swigCPtr, this);
            if (jBtSoftBody_sCti_normal_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_sCti_normal_get, false);
        }

        public void setOffset(float f) {
            SoftbodyJNI.btSoftBody_sCti_offset_set(this.swigCPtr, this, f);
        }

        public float getOffset() {
            return SoftbodyJNI.btSoftBody_sCti_offset_get(this.swigCPtr, this);
        }

        public sCti() {
            this(SoftbodyJNI.new_btSoftBody_sCti(), true);
        }
    }

    public static class sMedium extends BulletBase {
        private long swigCPtr;

        protected sMedium(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public sMedium(long j, boolean z) {
            this("sMedium", j, z);
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

        public static long getCPtr(sMedium smedium) {
            if (smedium == null) {
                return 0L;
            }
            return smedium.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_sMedium(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setVelocity(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_sMedium_velocity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getVelocity() {
            long jBtSoftBody_sMedium_velocity_get = SoftbodyJNI.btSoftBody_sMedium_velocity_get(this.swigCPtr, this);
            if (jBtSoftBody_sMedium_velocity_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_sMedium_velocity_get, false);
        }

        public void setPressure(float f) {
            SoftbodyJNI.btSoftBody_sMedium_pressure_set(this.swigCPtr, this, f);
        }

        public float getPressure() {
            return SoftbodyJNI.btSoftBody_sMedium_pressure_get(this.swigCPtr, this);
        }

        public void setDensity(float f) {
            SoftbodyJNI.btSoftBody_sMedium_density_set(this.swigCPtr, this, f);
        }

        public float getDensity() {
            return SoftbodyJNI.btSoftBody_sMedium_density_get(this.swigCPtr, this);
        }

        public sMedium() {
            this(SoftbodyJNI.new_btSoftBody_sMedium(), true);
        }
    }

    public static class Element extends BulletBase {
        private long swigCPtr;

        protected Element(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Element(long j, boolean z) {
            this("Element", j, z);
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

        public static long getCPtr(Element element) {
            if (element == null) {
                return 0L;
            }
            return element.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_Element(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setTag(long j) {
            SoftbodyJNI.btSoftBody_Element_tag_set(this.swigCPtr, this, j);
        }

        public long getTag() {
            return SoftbodyJNI.btSoftBody_Element_tag_get(this.swigCPtr, this);
        }

        public Element() {
            this(SoftbodyJNI.new_btSoftBody_Element(), true);
        }
    }

    public static class Material extends Element {
        private long swigCPtr;

        protected Material(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftBody_Material_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public Material(long j, boolean z) {
            this("Material", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftBody_Material_SWIGUpcast(j), z);
        }

        public static long getCPtr(Material material) {
            if (material == null) {
                return 0L;
            }
            return material.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftBody_Material(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setKLST(float f) {
            SoftbodyJNI.btSoftBody_Material_kLST_set(this.swigCPtr, this, f);
        }

        public float getKLST() {
            return SoftbodyJNI.btSoftBody_Material_kLST_get(this.swigCPtr, this);
        }

        public void setKAST(float f) {
            SoftbodyJNI.btSoftBody_Material_kAST_set(this.swigCPtr, this, f);
        }

        public float getKAST() {
            return SoftbodyJNI.btSoftBody_Material_kAST_get(this.swigCPtr, this);
        }

        public void setKVST(float f) {
            SoftbodyJNI.btSoftBody_Material_kVST_set(this.swigCPtr, this, f);
        }

        public float getKVST() {
            return SoftbodyJNI.btSoftBody_Material_kVST_get(this.swigCPtr, this);
        }

        public void setFlags(int i) {
            SoftbodyJNI.btSoftBody_Material_flags_set(this.swigCPtr, this, i);
        }

        public int getFlags() {
            return SoftbodyJNI.btSoftBody_Material_flags_get(this.swigCPtr, this);
        }

        public Material() {
            this(SoftbodyJNI.new_btSoftBody_Material(), true);
        }
    }

    public static class Feature extends Element {
        private long swigCPtr;

        protected Feature(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftBody_Feature_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public Feature(long j, boolean z) {
            this("Feature", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftBody_Feature_SWIGUpcast(j), z);
        }

        public static long getCPtr(Feature feature) {
            if (feature == null) {
                return 0L;
            }
            return feature.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftBody_Feature(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setMaterial(Material material) {
            SoftbodyJNI.btSoftBody_Feature_material_set(this.swigCPtr, this, Material.getCPtr(material), material);
        }

        public Material getMaterial() {
            long jBtSoftBody_Feature_material_get = SoftbodyJNI.btSoftBody_Feature_material_get(this.swigCPtr, this);
            if (jBtSoftBody_Feature_material_get == 0) {
                return null;
            }
            return new Material(jBtSoftBody_Feature_material_get, false);
        }

        public Feature() {
            this(SoftbodyJNI.new_btSoftBody_Feature(), true);
        }
    }

    public static class Node extends Feature {
        private long swigCPtr;

        protected Node(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftBody_Node_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public Node(long j, boolean z) {
            this("Node", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftBody_Node_SWIGUpcast(j), z);
        }

        public static long getCPtr(Node node) {
            if (node == null) {
                return 0L;
            }
            return node.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftBody_Node(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setX(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Node_x_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getX() {
            long jBtSoftBody_Node_x_get = SoftbodyJNI.btSoftBody_Node_x_get(this.swigCPtr, this);
            if (jBtSoftBody_Node_x_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Node_x_get, false);
        }

        public void setQ(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Node_q_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getQ() {
            long jBtSoftBody_Node_q_get = SoftbodyJNI.btSoftBody_Node_q_get(this.swigCPtr, this);
            if (jBtSoftBody_Node_q_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Node_q_get, false);
        }

        public void setV(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Node_v_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getV() {
            long jBtSoftBody_Node_v_get = SoftbodyJNI.btSoftBody_Node_v_get(this.swigCPtr, this);
            if (jBtSoftBody_Node_v_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Node_v_get, false);
        }

        public void setF(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Node_f_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getF() {
            long jBtSoftBody_Node_f_get = SoftbodyJNI.btSoftBody_Node_f_get(this.swigCPtr, this);
            if (jBtSoftBody_Node_f_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Node_f_get, false);
        }

        public void setN(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Node_n_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getN() {
            long jBtSoftBody_Node_n_get = SoftbodyJNI.btSoftBody_Node_n_get(this.swigCPtr, this);
            if (jBtSoftBody_Node_n_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Node_n_get, false);
        }

        public void setIm(float f) {
            SoftbodyJNI.btSoftBody_Node_im_set(this.swigCPtr, this, f);
        }

        public float getIm() {
            return SoftbodyJNI.btSoftBody_Node_im_get(this.swigCPtr, this);
        }

        public void setArea(float f) {
            SoftbodyJNI.btSoftBody_Node_area_set(this.swigCPtr, this, f);
        }

        public float getArea() {
            return SoftbodyJNI.btSoftBody_Node_area_get(this.swigCPtr, this);
        }

        public void setLeaf(btDbvtNode btdbvtnode) {
            SoftbodyJNI.btSoftBody_Node_leaf_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }

        public btDbvtNode getLeaf() {
            return btDbvtNode.internalTemp(SoftbodyJNI.btSoftBody_Node_leaf_get(this.swigCPtr, this), false);
        }

        public void setBattach(int i) {
            SoftbodyJNI.btSoftBody_Node_battach_set(this.swigCPtr, this, i);
        }

        public int getBattach() {
            return SoftbodyJNI.btSoftBody_Node_battach_get(this.swigCPtr, this);
        }

        public Node() {
            this(SoftbodyJNI.new_btSoftBody_Node(), true);
        }
    }

    public static class Link extends Feature {
        private long swigCPtr;

        protected Link(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftBody_Link_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public Link(long j, boolean z) {
            this("Link", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftBody_Link_SWIGUpcast(j), z);
        }

        public static long getCPtr(Link link) {
            if (link == null) {
                return 0L;
            }
            return link.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftBody_Link(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setC3(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Link_c3_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getC3() {
            long jBtSoftBody_Link_c3_get = SoftbodyJNI.btSoftBody_Link_c3_get(this.swigCPtr, this);
            if (jBtSoftBody_Link_c3_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Link_c3_get, false);
        }

        public void setN(SWIGTYPE_p_p_btSoftBody__Node sWIGTYPE_p_p_btSoftBody__Node) {
            SoftbodyJNI.btSoftBody_Link_n_set(this.swigCPtr, this, SWIGTYPE_p_p_btSoftBody__Node.getCPtr(sWIGTYPE_p_p_btSoftBody__Node));
        }

        public SWIGTYPE_p_p_btSoftBody__Node getN() {
            long jBtSoftBody_Link_n_get = SoftbodyJNI.btSoftBody_Link_n_get(this.swigCPtr, this);
            if (jBtSoftBody_Link_n_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_p_btSoftBody__Node(jBtSoftBody_Link_n_get, false);
        }

        public void setRl(float f) {
            SoftbodyJNI.btSoftBody_Link_rl_set(this.swigCPtr, this, f);
        }

        public float getRl() {
            return SoftbodyJNI.btSoftBody_Link_rl_get(this.swigCPtr, this);
        }

        public void setBbending(int i) {
            SoftbodyJNI.btSoftBody_Link_bbending_set(this.swigCPtr, this, i);
        }

        public int getBbending() {
            return SoftbodyJNI.btSoftBody_Link_bbending_get(this.swigCPtr, this);
        }

        public void setC0(float f) {
            SoftbodyJNI.btSoftBody_Link_c0_set(this.swigCPtr, this, f);
        }

        public float getC0() {
            return SoftbodyJNI.btSoftBody_Link_c0_get(this.swigCPtr, this);
        }

        public void setC1(float f) {
            SoftbodyJNI.btSoftBody_Link_c1_set(this.swigCPtr, this, f);
        }

        public float getC1() {
            return SoftbodyJNI.btSoftBody_Link_c1_get(this.swigCPtr, this);
        }

        public void setC2(float f) {
            SoftbodyJNI.btSoftBody_Link_c2_set(this.swigCPtr, this, f);
        }

        public float getC2() {
            return SoftbodyJNI.btSoftBody_Link_c2_get(this.swigCPtr, this);
        }

        public long operatorNew(long j) {
            return SoftbodyJNI.btSoftBody_Link_operatorNew__SWIG_0(this.swigCPtr, this, j);
        }

        public void operatorDelete(long j) {
            SoftbodyJNI.btSoftBody_Link_operatorDelete__SWIG_0(this.swigCPtr, this, j);
        }

        public long operatorNew(long j, long j2) {
            return SoftbodyJNI.btSoftBody_Link_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
        }

        public void operatorDelete(long j, long j2) {
            SoftbodyJNI.btSoftBody_Link_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
        }

        public long operatorNewArray(long j) {
            return SoftbodyJNI.btSoftBody_Link_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
        }

        public void operatorDeleteArray(long j) {
            SoftbodyJNI.btSoftBody_Link_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
        }

        public long operatorNewArray(long j, long j2) {
            return SoftbodyJNI.btSoftBody_Link_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
        }

        public void operatorDeleteArray(long j, long j2) {
            SoftbodyJNI.btSoftBody_Link_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
        }

        public Link() {
            this(SoftbodyJNI.new_btSoftBody_Link(), true);
        }
    }

    public static class Face extends Feature {
        private long swigCPtr;

        protected Face(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftBody_Face_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public Face(long j, boolean z) {
            this("Face", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftBody_Face_SWIGUpcast(j), z);
        }

        public static long getCPtr(Face face) {
            if (face == null) {
                return 0L;
            }
            return face.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftBody_Face(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setN(SWIGTYPE_p_p_btSoftBody__Node sWIGTYPE_p_p_btSoftBody__Node) {
            SoftbodyJNI.btSoftBody_Face_n_set(this.swigCPtr, this, SWIGTYPE_p_p_btSoftBody__Node.getCPtr(sWIGTYPE_p_p_btSoftBody__Node));
        }

        public SWIGTYPE_p_p_btSoftBody__Node getN() {
            long jBtSoftBody_Face_n_get = SoftbodyJNI.btSoftBody_Face_n_get(this.swigCPtr, this);
            if (jBtSoftBody_Face_n_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_p_btSoftBody__Node(jBtSoftBody_Face_n_get, false);
        }

        public void setNormal(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Face_normal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getNormal() {
            long jBtSoftBody_Face_normal_get = SoftbodyJNI.btSoftBody_Face_normal_get(this.swigCPtr, this);
            if (jBtSoftBody_Face_normal_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Face_normal_get, false);
        }

        public void setRa(float f) {
            SoftbodyJNI.btSoftBody_Face_ra_set(this.swigCPtr, this, f);
        }

        public float getRa() {
            return SoftbodyJNI.btSoftBody_Face_ra_get(this.swigCPtr, this);
        }

        public void setLeaf(btDbvtNode btdbvtnode) {
            SoftbodyJNI.btSoftBody_Face_leaf_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }

        public btDbvtNode getLeaf() {
            return btDbvtNode.internalTemp(SoftbodyJNI.btSoftBody_Face_leaf_get(this.swigCPtr, this), false);
        }

        public Face() {
            this(SoftbodyJNI.new_btSoftBody_Face(), true);
        }
    }

    public static class Tetra extends Feature {
        private long swigCPtr;

        protected Tetra(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftBody_Tetra_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public Tetra(long j, boolean z) {
            this("Tetra", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftBody_Tetra_SWIGUpcast(j), z);
        }

        public static long getCPtr(Tetra tetra) {
            if (tetra == null) {
                return 0L;
            }
            return tetra.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Feature, com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftBody_Tetra(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setN(SWIGTYPE_p_p_btSoftBody__Node sWIGTYPE_p_p_btSoftBody__Node) {
            SoftbodyJNI.btSoftBody_Tetra_n_set(this.swigCPtr, this, SWIGTYPE_p_p_btSoftBody__Node.getCPtr(sWIGTYPE_p_p_btSoftBody__Node));
        }

        public SWIGTYPE_p_p_btSoftBody__Node getN() {
            long jBtSoftBody_Tetra_n_get = SoftbodyJNI.btSoftBody_Tetra_n_get(this.swigCPtr, this);
            if (jBtSoftBody_Tetra_n_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_p_btSoftBody__Node(jBtSoftBody_Tetra_n_get, false);
        }

        public void setRv(float f) {
            SoftbodyJNI.btSoftBody_Tetra_rv_set(this.swigCPtr, this, f);
        }

        public float getRv() {
            return SoftbodyJNI.btSoftBody_Tetra_rv_get(this.swigCPtr, this);
        }

        public void setLeaf(btDbvtNode btdbvtnode) {
            SoftbodyJNI.btSoftBody_Tetra_leaf_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }

        public btDbvtNode getLeaf() {
            return btDbvtNode.internalTemp(SoftbodyJNI.btSoftBody_Tetra_leaf_get(this.swigCPtr, this), false);
        }

        public void setC0(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Tetra_c0_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getC0() {
            long jBtSoftBody_Tetra_c0_get = SoftbodyJNI.btSoftBody_Tetra_c0_get(this.swigCPtr, this);
            if (jBtSoftBody_Tetra_c0_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Tetra_c0_get, false);
        }

        public void setC1(float f) {
            SoftbodyJNI.btSoftBody_Tetra_c1_set(this.swigCPtr, this, f);
        }

        public float getC1() {
            return SoftbodyJNI.btSoftBody_Tetra_c1_get(this.swigCPtr, this);
        }

        public void setC2(float f) {
            SoftbodyJNI.btSoftBody_Tetra_c2_set(this.swigCPtr, this, f);
        }

        public float getC2() {
            return SoftbodyJNI.btSoftBody_Tetra_c2_get(this.swigCPtr, this);
        }

        public Tetra() {
            this(SoftbodyJNI.new_btSoftBody_Tetra(), true);
        }
    }

    public static class RContact extends BulletBase {
        private long swigCPtr;

        protected RContact(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public RContact(long j, boolean z) {
            this("RContact", j, z);
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

        public static long getCPtr(RContact rContact) {
            if (rContact == null) {
                return 0L;
            }
            return rContact.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_RContact(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setCti(sCti scti) {
            SoftbodyJNI.btSoftBody_RContact_cti_set(this.swigCPtr, this, sCti.getCPtr(scti), scti);
        }

        public sCti getCti() {
            long jBtSoftBody_RContact_cti_get = SoftbodyJNI.btSoftBody_RContact_cti_get(this.swigCPtr, this);
            if (jBtSoftBody_RContact_cti_get == 0) {
                return null;
            }
            return new sCti(jBtSoftBody_RContact_cti_get, false);
        }

        public void setNode(Node node) {
            SoftbodyJNI.btSoftBody_RContact_node_set(this.swigCPtr, this, Node.getCPtr(node), node);
        }

        public Node getNode() {
            long jBtSoftBody_RContact_node_get = SoftbodyJNI.btSoftBody_RContact_node_get(this.swigCPtr, this);
            if (jBtSoftBody_RContact_node_get == 0) {
                return null;
            }
            return new Node(jBtSoftBody_RContact_node_get, false);
        }

        public void setC0(btMatrix3x3 btmatrix3x3) {
            SoftbodyJNI.btSoftBody_RContact_c0_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
        }

        public btMatrix3x3 getC0() {
            long jBtSoftBody_RContact_c0_get = SoftbodyJNI.btSoftBody_RContact_c0_get(this.swigCPtr, this);
            if (jBtSoftBody_RContact_c0_get == 0) {
                return null;
            }
            return new btMatrix3x3(jBtSoftBody_RContact_c0_get, false);
        }

        public void setC1(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_RContact_c1_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getC1() {
            long jBtSoftBody_RContact_c1_get = SoftbodyJNI.btSoftBody_RContact_c1_get(this.swigCPtr, this);
            if (jBtSoftBody_RContact_c1_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_RContact_c1_get, false);
        }

        public void setC2(float f) {
            SoftbodyJNI.btSoftBody_RContact_c2_set(this.swigCPtr, this, f);
        }

        public float getC2() {
            return SoftbodyJNI.btSoftBody_RContact_c2_get(this.swigCPtr, this);
        }

        public void setC3(float f) {
            SoftbodyJNI.btSoftBody_RContact_c3_set(this.swigCPtr, this, f);
        }

        public float getC3() {
            return SoftbodyJNI.btSoftBody_RContact_c3_get(this.swigCPtr, this);
        }

        public void setC4(float f) {
            SoftbodyJNI.btSoftBody_RContact_c4_set(this.swigCPtr, this, f);
        }

        public float getC4() {
            return SoftbodyJNI.btSoftBody_RContact_c4_get(this.swigCPtr, this);
        }

        public RContact() {
            this(SoftbodyJNI.new_btSoftBody_RContact(), true);
        }
    }

    public static class SContact extends BulletBase {
        private long swigCPtr;

        protected SContact(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public SContact(long j, boolean z) {
            this("SContact", j, z);
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

        public static long getCPtr(SContact sContact) {
            if (sContact == null) {
                return 0L;
            }
            return sContact.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_SContact(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setNode(Node node) {
            SoftbodyJNI.btSoftBody_SContact_node_set(this.swigCPtr, this, Node.getCPtr(node), node);
        }

        public Node getNode() {
            long jBtSoftBody_SContact_node_get = SoftbodyJNI.btSoftBody_SContact_node_get(this.swigCPtr, this);
            if (jBtSoftBody_SContact_node_get == 0) {
                return null;
            }
            return new Node(jBtSoftBody_SContact_node_get, false);
        }

        public void setFace(Face face) {
            SoftbodyJNI.btSoftBody_SContact_face_set(this.swigCPtr, this, Face.getCPtr(face), face);
        }

        public Face getFace() {
            long jBtSoftBody_SContact_face_get = SoftbodyJNI.btSoftBody_SContact_face_get(this.swigCPtr, this);
            if (jBtSoftBody_SContact_face_get == 0) {
                return null;
            }
            return new Face(jBtSoftBody_SContact_face_get, false);
        }

        public void setWeights(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_SContact_weights_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getWeights() {
            long jBtSoftBody_SContact_weights_get = SoftbodyJNI.btSoftBody_SContact_weights_get(this.swigCPtr, this);
            if (jBtSoftBody_SContact_weights_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_SContact_weights_get, false);
        }

        public void setNormal(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_SContact_normal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getNormal() {
            long jBtSoftBody_SContact_normal_get = SoftbodyJNI.btSoftBody_SContact_normal_get(this.swigCPtr, this);
            if (jBtSoftBody_SContact_normal_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_SContact_normal_get, false);
        }

        public void setMargin(float f) {
            SoftbodyJNI.btSoftBody_SContact_margin_set(this.swigCPtr, this, f);
        }

        public float getMargin() {
            return SoftbodyJNI.btSoftBody_SContact_margin_get(this.swigCPtr, this);
        }

        public void setFriction(float f) {
            SoftbodyJNI.btSoftBody_SContact_friction_set(this.swigCPtr, this, f);
        }

        public float getFriction() {
            return SoftbodyJNI.btSoftBody_SContact_friction_get(this.swigCPtr, this);
        }

        public void setCfm(float[] fArr) {
            SoftbodyJNI.btSoftBody_SContact_cfm_set(this.swigCPtr, this, fArr);
        }

        public float[] getCfm() {
            return SoftbodyJNI.btSoftBody_SContact_cfm_get(this.swigCPtr, this);
        }

        public SContact() {
            this(SoftbodyJNI.new_btSoftBody_SContact(), true);
        }
    }

    public static class Anchor extends BulletBase {
        private long swigCPtr;

        protected Anchor(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Anchor(long j, boolean z) {
            this("Anchor", j, z);
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

        public static long getCPtr(Anchor anchor) {
            if (anchor == null) {
                return 0L;
            }
            return anchor.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_Anchor(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setNode(Node node) {
            SoftbodyJNI.btSoftBody_Anchor_node_set(this.swigCPtr, this, Node.getCPtr(node), node);
        }

        public Node getNode() {
            long jBtSoftBody_Anchor_node_get = SoftbodyJNI.btSoftBody_Anchor_node_get(this.swigCPtr, this);
            if (jBtSoftBody_Anchor_node_get == 0) {
                return null;
            }
            return new Node(jBtSoftBody_Anchor_node_get, false);
        }

        public void setLocal(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Anchor_local_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getLocal() {
            long jBtSoftBody_Anchor_local_get = SoftbodyJNI.btSoftBody_Anchor_local_get(this.swigCPtr, this);
            if (jBtSoftBody_Anchor_local_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Anchor_local_get, false);
        }

        public void setBody(btRigidBody btrigidbody) {
            SoftbodyJNI.btSoftBody_Anchor_body_set(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody);
        }

        public btRigidBody getBody() {
            return btRigidBody.getInstance(SoftbodyJNI.btSoftBody_Anchor_body_get(this.swigCPtr, this), false);
        }

        public void setInfluence(float f) {
            SoftbodyJNI.btSoftBody_Anchor_influence_set(this.swigCPtr, this, f);
        }

        public float getInfluence() {
            return SoftbodyJNI.btSoftBody_Anchor_influence_get(this.swigCPtr, this);
        }

        public void setC0(btMatrix3x3 btmatrix3x3) {
            SoftbodyJNI.btSoftBody_Anchor_c0_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
        }

        public btMatrix3x3 getC0() {
            long jBtSoftBody_Anchor_c0_get = SoftbodyJNI.btSoftBody_Anchor_c0_get(this.swigCPtr, this);
            if (jBtSoftBody_Anchor_c0_get == 0) {
                return null;
            }
            return new btMatrix3x3(jBtSoftBody_Anchor_c0_get, false);
        }

        public void setC1(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Anchor_c1_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getC1() {
            long jBtSoftBody_Anchor_c1_get = SoftbodyJNI.btSoftBody_Anchor_c1_get(this.swigCPtr, this);
            if (jBtSoftBody_Anchor_c1_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Anchor_c1_get, false);
        }

        public void setC2(float f) {
            SoftbodyJNI.btSoftBody_Anchor_c2_set(this.swigCPtr, this, f);
        }

        public float getC2() {
            return SoftbodyJNI.btSoftBody_Anchor_c2_get(this.swigCPtr, this);
        }

        public Anchor() {
            this(SoftbodyJNI.new_btSoftBody_Anchor(), true);
        }
    }

    public static class Note extends Element {
        private long swigCPtr;

        protected Note(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftBody_Note_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public Note(long j, boolean z) {
            this("Note", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftBody_Note_SWIGUpcast(j), z);
        }

        public static long getCPtr(Note note) {
            if (note == null) {
                return 0L;
            }
            return note.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Element, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftBody_Note(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public String getText() {
            return SoftbodyJNI.btSoftBody_Note_text_get(this.swigCPtr, this);
        }

        public void setOffset(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Note_offset_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getOffset() {
            long jBtSoftBody_Note_offset_get = SoftbodyJNI.btSoftBody_Note_offset_get(this.swigCPtr, this);
            if (jBtSoftBody_Note_offset_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Note_offset_get, false);
        }

        public void setRank(int i) {
            SoftbodyJNI.btSoftBody_Note_rank_set(this.swigCPtr, this, i);
        }

        public int getRank() {
            return SoftbodyJNI.btSoftBody_Note_rank_get(this.swigCPtr, this);
        }

        public void setNodes(SWIGTYPE_p_p_btSoftBody__Node sWIGTYPE_p_p_btSoftBody__Node) {
            SoftbodyJNI.btSoftBody_Note_nodes_set(this.swigCPtr, this, SWIGTYPE_p_p_btSoftBody__Node.getCPtr(sWIGTYPE_p_p_btSoftBody__Node));
        }

        public SWIGTYPE_p_p_btSoftBody__Node getNodes() {
            long jBtSoftBody_Note_nodes_get = SoftbodyJNI.btSoftBody_Note_nodes_get(this.swigCPtr, this);
            if (jBtSoftBody_Note_nodes_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_p_btSoftBody__Node(jBtSoftBody_Note_nodes_get, false);
        }

        public void setCoords(float[] fArr) {
            SoftbodyJNI.btSoftBody_Note_coords_set(this.swigCPtr, this, fArr);
        }

        public float[] getCoords() {
            return SoftbodyJNI.btSoftBody_Note_coords_get(this.swigCPtr, this);
        }

        public Note() {
            this(SoftbodyJNI.new_btSoftBody_Note(), true);
        }
    }

    public static class Pose extends BulletBase {
        private long swigCPtr;

        protected Pose(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Pose(long j, boolean z) {
            this("Pose", j, z);
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

        public static long getCPtr(Pose pose) {
            if (pose == null) {
                return 0L;
            }
            return pose.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_Pose(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setBvolume(boolean z) {
            SoftbodyJNI.btSoftBody_Pose_bvolume_set(this.swigCPtr, this, z);
        }

        public boolean getBvolume() {
            return SoftbodyJNI.btSoftBody_Pose_bvolume_get(this.swigCPtr, this);
        }

        public void setBframe(boolean z) {
            SoftbodyJNI.btSoftBody_Pose_bframe_set(this.swigCPtr, this, z);
        }

        public boolean getBframe() {
            return SoftbodyJNI.btSoftBody_Pose_bframe_get(this.swigCPtr, this);
        }

        public void setVolume(float f) {
            SoftbodyJNI.btSoftBody_Pose_volume_set(this.swigCPtr, this, f);
        }

        public float getVolume() {
            return SoftbodyJNI.btSoftBody_Pose_volume_get(this.swigCPtr, this);
        }

        public void setPos(btVector3Array btvector3array) {
            SoftbodyJNI.btSoftBody_Pose_pos_set(this.swigCPtr, this, btVector3Array.getCPtr(btvector3array), btvector3array);
        }

        public btVector3Array getPos() {
            long jBtSoftBody_Pose_pos_get = SoftbodyJNI.btSoftBody_Pose_pos_get(this.swigCPtr, this);
            if (jBtSoftBody_Pose_pos_get == 0) {
                return null;
            }
            return new btVector3Array(jBtSoftBody_Pose_pos_get, false);
        }

        public void setWgh(btScalarArray btscalararray) {
            SoftbodyJNI.btSoftBody_Pose_wgh_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
        }

        public btScalarArray getWgh() {
            long jBtSoftBody_Pose_wgh_get = SoftbodyJNI.btSoftBody_Pose_wgh_get(this.swigCPtr, this);
            if (jBtSoftBody_Pose_wgh_get == 0) {
                return null;
            }
            return new btScalarArray(jBtSoftBody_Pose_wgh_get, false);
        }

        public void setCom(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Pose_com_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getCom() {
            long jBtSoftBody_Pose_com_get = SoftbodyJNI.btSoftBody_Pose_com_get(this.swigCPtr, this);
            if (jBtSoftBody_Pose_com_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Pose_com_get, false);
        }

        public void setRot(btMatrix3x3 btmatrix3x3) {
            SoftbodyJNI.btSoftBody_Pose_rot_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
        }

        public btMatrix3x3 getRot() {
            long jBtSoftBody_Pose_rot_get = SoftbodyJNI.btSoftBody_Pose_rot_get(this.swigCPtr, this);
            if (jBtSoftBody_Pose_rot_get == 0) {
                return null;
            }
            return new btMatrix3x3(jBtSoftBody_Pose_rot_get, false);
        }

        public void setScl(btMatrix3x3 btmatrix3x3) {
            SoftbodyJNI.btSoftBody_Pose_scl_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
        }

        public btMatrix3x3 getScl() {
            long jBtSoftBody_Pose_scl_get = SoftbodyJNI.btSoftBody_Pose_scl_get(this.swigCPtr, this);
            if (jBtSoftBody_Pose_scl_get == 0) {
                return null;
            }
            return new btMatrix3x3(jBtSoftBody_Pose_scl_get, false);
        }

        public void setAqq(btMatrix3x3 btmatrix3x3) {
            SoftbodyJNI.btSoftBody_Pose_aqq_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
        }

        public btMatrix3x3 getAqq() {
            long jBtSoftBody_Pose_aqq_get = SoftbodyJNI.btSoftBody_Pose_aqq_get(this.swigCPtr, this);
            if (jBtSoftBody_Pose_aqq_get == 0) {
                return null;
            }
            return new btMatrix3x3(jBtSoftBody_Pose_aqq_get, false);
        }

        public Pose() {
            this(SoftbodyJNI.new_btSoftBody_Pose(), true);
        }
    }

    public static class Cluster extends BulletBase {
        private long swigCPtr;

        protected Cluster(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Cluster(long j, boolean z) {
            this("Cluster", j, z);
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

        public static long getCPtr(Cluster cluster) {
            if (cluster == null) {
                return 0L;
            }
            return cluster.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_Cluster(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setMasses(btScalarArray btscalararray) {
            SoftbodyJNI.btSoftBody_Cluster_masses_set(this.swigCPtr, this, btScalarArray.getCPtr(btscalararray), btscalararray);
        }

        public btScalarArray getMasses() {
            long jBtSoftBody_Cluster_masses_get = SoftbodyJNI.btSoftBody_Cluster_masses_get(this.swigCPtr, this);
            if (jBtSoftBody_Cluster_masses_get == 0) {
                return null;
            }
            return new btScalarArray(jBtSoftBody_Cluster_masses_get, false);
        }

        public void setNodes(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_p_t) {
            SoftbodyJNI.btSoftBody_Cluster_nodes_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_p_t));
        }

        public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_p_t getNodes() {
            long jBtSoftBody_Cluster_nodes_get = SoftbodyJNI.btSoftBody_Cluster_nodes_get(this.swigCPtr, this);
            if (jBtSoftBody_Cluster_nodes_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_p_t(jBtSoftBody_Cluster_nodes_get, false);
        }

        public void setFramerefs(btVector3Array btvector3array) {
            SoftbodyJNI.btSoftBody_Cluster_framerefs_set(this.swigCPtr, this, btVector3Array.getCPtr(btvector3array), btvector3array);
        }

        public btVector3Array getFramerefs() {
            long jBtSoftBody_Cluster_framerefs_get = SoftbodyJNI.btSoftBody_Cluster_framerefs_get(this.swigCPtr, this);
            if (jBtSoftBody_Cluster_framerefs_get == 0) {
                return null;
            }
            return new btVector3Array(jBtSoftBody_Cluster_framerefs_get, false);
        }

        public void setFramexform(btTransform bttransform) {
            SoftbodyJNI.btSoftBody_Cluster_framexform_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
        }

        public btTransform getFramexform() {
            long jBtSoftBody_Cluster_framexform_get = SoftbodyJNI.btSoftBody_Cluster_framexform_get(this.swigCPtr, this);
            if (jBtSoftBody_Cluster_framexform_get == 0) {
                return null;
            }
            return new btTransform(jBtSoftBody_Cluster_framexform_get, false);
        }

        public void setIdmass(float f) {
            SoftbodyJNI.btSoftBody_Cluster_idmass_set(this.swigCPtr, this, f);
        }

        public float getIdmass() {
            return SoftbodyJNI.btSoftBody_Cluster_idmass_get(this.swigCPtr, this);
        }

        public void setImass(float f) {
            SoftbodyJNI.btSoftBody_Cluster_imass_set(this.swigCPtr, this, f);
        }

        public float getImass() {
            return SoftbodyJNI.btSoftBody_Cluster_imass_get(this.swigCPtr, this);
        }

        public void setLocii(btMatrix3x3 btmatrix3x3) {
            SoftbodyJNI.btSoftBody_Cluster_locii_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
        }

        public btMatrix3x3 getLocii() {
            long jBtSoftBody_Cluster_locii_get = SoftbodyJNI.btSoftBody_Cluster_locii_get(this.swigCPtr, this);
            if (jBtSoftBody_Cluster_locii_get == 0) {
                return null;
            }
            return new btMatrix3x3(jBtSoftBody_Cluster_locii_get, false);
        }

        public void setInvwi(btMatrix3x3 btmatrix3x3) {
            SoftbodyJNI.btSoftBody_Cluster_invwi_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
        }

        public btMatrix3x3 getInvwi() {
            long jBtSoftBody_Cluster_invwi_get = SoftbodyJNI.btSoftBody_Cluster_invwi_get(this.swigCPtr, this);
            if (jBtSoftBody_Cluster_invwi_get == 0) {
                return null;
            }
            return new btMatrix3x3(jBtSoftBody_Cluster_invwi_get, false);
        }

        public void setCom(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Cluster_com_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getCom() {
            long jBtSoftBody_Cluster_com_get = SoftbodyJNI.btSoftBody_Cluster_com_get(this.swigCPtr, this);
            if (jBtSoftBody_Cluster_com_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Cluster_com_get, false);
        }

        public void setVimpulses(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Cluster_vimpulses_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getVimpulses() {
            long jBtSoftBody_Cluster_vimpulses_get = SoftbodyJNI.btSoftBody_Cluster_vimpulses_get(this.swigCPtr, this);
            if (jBtSoftBody_Cluster_vimpulses_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Cluster_vimpulses_get, false);
        }

        public void setDimpulses(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Cluster_dimpulses_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getDimpulses() {
            long jBtSoftBody_Cluster_dimpulses_get = SoftbodyJNI.btSoftBody_Cluster_dimpulses_get(this.swigCPtr, this);
            if (jBtSoftBody_Cluster_dimpulses_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Cluster_dimpulses_get, false);
        }

        public void setNvimpulses(int i) {
            SoftbodyJNI.btSoftBody_Cluster_nvimpulses_set(this.swigCPtr, this, i);
        }

        public int getNvimpulses() {
            return SoftbodyJNI.btSoftBody_Cluster_nvimpulses_get(this.swigCPtr, this);
        }

        public void setNdimpulses(int i) {
            SoftbodyJNI.btSoftBody_Cluster_ndimpulses_set(this.swigCPtr, this, i);
        }

        public int getNdimpulses() {
            return SoftbodyJNI.btSoftBody_Cluster_ndimpulses_get(this.swigCPtr, this);
        }

        public void setLv(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Cluster_lv_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getLv() {
            long jBtSoftBody_Cluster_lv_get = SoftbodyJNI.btSoftBody_Cluster_lv_get(this.swigCPtr, this);
            if (jBtSoftBody_Cluster_lv_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Cluster_lv_get, false);
        }

        public void setAv(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Cluster_av_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getAv() {
            long jBtSoftBody_Cluster_av_get = SoftbodyJNI.btSoftBody_Cluster_av_get(this.swigCPtr, this);
            if (jBtSoftBody_Cluster_av_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Cluster_av_get, false);
        }

        public void setLeaf(btDbvtNode btdbvtnode) {
            SoftbodyJNI.btSoftBody_Cluster_leaf_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }

        public btDbvtNode getLeaf() {
            return btDbvtNode.internalTemp(SoftbodyJNI.btSoftBody_Cluster_leaf_get(this.swigCPtr, this), false);
        }

        public void setNdamping(float f) {
            SoftbodyJNI.btSoftBody_Cluster_ndamping_set(this.swigCPtr, this, f);
        }

        public float getNdamping() {
            return SoftbodyJNI.btSoftBody_Cluster_ndamping_get(this.swigCPtr, this);
        }

        public void setLdamping(float f) {
            SoftbodyJNI.btSoftBody_Cluster_ldamping_set(this.swigCPtr, this, f);
        }

        public float getLdamping() {
            return SoftbodyJNI.btSoftBody_Cluster_ldamping_get(this.swigCPtr, this);
        }

        public void setAdamping(float f) {
            SoftbodyJNI.btSoftBody_Cluster_adamping_set(this.swigCPtr, this, f);
        }

        public float getAdamping() {
            return SoftbodyJNI.btSoftBody_Cluster_adamping_get(this.swigCPtr, this);
        }

        public void setMatching(float f) {
            SoftbodyJNI.btSoftBody_Cluster_matching_set(this.swigCPtr, this, f);
        }

        public float getMatching() {
            return SoftbodyJNI.btSoftBody_Cluster_matching_get(this.swigCPtr, this);
        }

        public void setMaxSelfCollisionImpulse(float f) {
            SoftbodyJNI.btSoftBody_Cluster_maxSelfCollisionImpulse_set(this.swigCPtr, this, f);
        }

        public float getMaxSelfCollisionImpulse() {
            return SoftbodyJNI.btSoftBody_Cluster_maxSelfCollisionImpulse_get(this.swigCPtr, this);
        }

        public void setSelfCollisionImpulseFactor(float f) {
            SoftbodyJNI.btSoftBody_Cluster_selfCollisionImpulseFactor_set(this.swigCPtr, this, f);
        }

        public float getSelfCollisionImpulseFactor() {
            return SoftbodyJNI.btSoftBody_Cluster_selfCollisionImpulseFactor_get(this.swigCPtr, this);
        }

        public void setContainsAnchor(boolean z) {
            SoftbodyJNI.btSoftBody_Cluster_containsAnchor_set(this.swigCPtr, this, z);
        }

        public boolean getContainsAnchor() {
            return SoftbodyJNI.btSoftBody_Cluster_containsAnchor_get(this.swigCPtr, this);
        }

        public void setCollide(boolean z) {
            SoftbodyJNI.btSoftBody_Cluster_collide_set(this.swigCPtr, this, z);
        }

        public boolean getCollide() {
            return SoftbodyJNI.btSoftBody_Cluster_collide_get(this.swigCPtr, this);
        }

        public void setClusterIndex(int i) {
            SoftbodyJNI.btSoftBody_Cluster_clusterIndex_set(this.swigCPtr, this, i);
        }

        public int getClusterIndex() {
            return SoftbodyJNI.btSoftBody_Cluster_clusterIndex_get(this.swigCPtr, this);
        }

        public Cluster() {
            this(SoftbodyJNI.new_btSoftBody_Cluster(), true);
        }
    }

    public static class Impulse extends BulletBase {
        private long swigCPtr;

        protected Impulse(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Impulse(long j, boolean z) {
            this("Impulse", j, z);
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

        public static long getCPtr(Impulse impulse) {
            if (impulse == null) {
                return 0L;
            }
            return impulse.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_Impulse(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setVelocity(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Impulse_velocity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getVelocity() {
            long jBtSoftBody_Impulse_velocity_get = SoftbodyJNI.btSoftBody_Impulse_velocity_get(this.swigCPtr, this);
            if (jBtSoftBody_Impulse_velocity_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Impulse_velocity_get, false);
        }

        public void setDrift(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Impulse_drift_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getDrift() {
            long jBtSoftBody_Impulse_drift_get = SoftbodyJNI.btSoftBody_Impulse_drift_get(this.swigCPtr, this);
            if (jBtSoftBody_Impulse_drift_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Impulse_drift_get, false);
        }

        public void setAsVelocity(int i) {
            SoftbodyJNI.btSoftBody_Impulse_asVelocity_set(this.swigCPtr, this, i);
        }

        public int getAsVelocity() {
            return SoftbodyJNI.btSoftBody_Impulse_asVelocity_get(this.swigCPtr, this);
        }

        public void setAsDrift(int i) {
            SoftbodyJNI.btSoftBody_Impulse_asDrift_set(this.swigCPtr, this, i);
        }

        public int getAsDrift() {
            return SoftbodyJNI.btSoftBody_Impulse_asDrift_get(this.swigCPtr, this);
        }

        public Impulse() {
            this(SoftbodyJNI.new_btSoftBody_Impulse(), true);
        }

        public Impulse operatorSubtraction() {
            return new Impulse(SoftbodyJNI.btSoftBody_Impulse_operatorSubtraction(this.swigCPtr, this), true);
        }

        public Impulse operatorMultiplication(float f) {
            return new Impulse(SoftbodyJNI.btSoftBody_Impulse_operatorMultiplication(this.swigCPtr, this, f), true);
        }
    }

    public static class Body extends BulletBase {
        private long swigCPtr;

        protected Body(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Body(long j, boolean z) {
            this("Body", j, z);
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

        public static long getCPtr(Body body) {
            if (body == null) {
                return 0L;
            }
            return body.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_Body(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setSoft(Cluster cluster) {
            SoftbodyJNI.btSoftBody_Body_soft_set(this.swigCPtr, this, Cluster.getCPtr(cluster), cluster);
        }

        public Cluster getSoft() {
            long jBtSoftBody_Body_soft_get = SoftbodyJNI.btSoftBody_Body_soft_get(this.swigCPtr, this);
            if (jBtSoftBody_Body_soft_get == 0) {
                return null;
            }
            return new Cluster(jBtSoftBody_Body_soft_get, false);
        }

        public void setRigid(btRigidBody btrigidbody) {
            SoftbodyJNI.btSoftBody_Body_rigid_set(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody);
        }

        public btRigidBody getRigid() {
            return btRigidBody.getInstance(SoftbodyJNI.btSoftBody_Body_rigid_get(this.swigCPtr, this), false);
        }

        public void setCollisionObject(btCollisionObject btcollisionobject) {
            SoftbodyJNI.btSoftBody_Body_collisionObject_set(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
        }

        public btCollisionObject getCollisionObject() {
            return btCollisionObject.getInstance(SoftbodyJNI.btSoftBody_Body_collisionObject_get(this.swigCPtr, this), false);
        }

        public Body() {
            this(SoftbodyJNI.new_btSoftBody_Body__SWIG_0(), true);
        }

        public Body(Cluster cluster) {
            this(SoftbodyJNI.new_btSoftBody_Body__SWIG_1(Cluster.getCPtr(cluster), cluster), true);
        }

        public Body(btCollisionObject btcollisionobject) {
            this(SoftbodyJNI.new_btSoftBody_Body__SWIG_2(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject), true);
        }

        public void activate() {
            SoftbodyJNI.btSoftBody_Body_activate(this.swigCPtr, this);
        }

        public Matrix3 invWorldInertia() {
            return SoftbodyJNI.btSoftBody_Body_invWorldInertia(this.swigCPtr, this);
        }

        public float invMass() {
            return SoftbodyJNI.btSoftBody_Body_invMass(this.swigCPtr, this);
        }

        public Matrix4 xform() {
            return SoftbodyJNI.btSoftBody_Body_xform(this.swigCPtr, this);
        }

        public Vector3 linearVelocity() {
            return SoftbodyJNI.btSoftBody_Body_linearVelocity(this.swigCPtr, this);
        }

        public Vector3 angularVelocity(Vector3 vector3) {
            return SoftbodyJNI.btSoftBody_Body_angularVelocity__SWIG_0(this.swigCPtr, this, vector3);
        }

        public Vector3 angularVelocity() {
            return SoftbodyJNI.btSoftBody_Body_angularVelocity__SWIG_1(this.swigCPtr, this);
        }

        public Vector3 velocity(Vector3 vector3) {
            return SoftbodyJNI.btSoftBody_Body_velocity(this.swigCPtr, this, vector3);
        }

        public void applyVImpulse(Vector3 vector3, Vector3 vector4) {
            SoftbodyJNI.btSoftBody_Body_applyVImpulse(this.swigCPtr, this, vector3, vector4);
        }

        public void applyDImpulse(Vector3 vector3, Vector3 vector4) {
            SoftbodyJNI.btSoftBody_Body_applyDImpulse(this.swigCPtr, this, vector3, vector4);
        }

        public void applyImpulse(Impulse impulse, Vector3 vector3) {
            SoftbodyJNI.btSoftBody_Body_applyImpulse(this.swigCPtr, this, Impulse.getCPtr(impulse), impulse, vector3);
        }

        public void applyVAImpulse(Vector3 vector3) {
            SoftbodyJNI.btSoftBody_Body_applyVAImpulse(this.swigCPtr, this, vector3);
        }

        public void applyDAImpulse(Vector3 vector3) {
            SoftbodyJNI.btSoftBody_Body_applyDAImpulse(this.swigCPtr, this, vector3);
        }

        public void applyAImpulse(Impulse impulse) {
            SoftbodyJNI.btSoftBody_Body_applyAImpulse(this.swigCPtr, this, Impulse.getCPtr(impulse), impulse);
        }

        public void applyDCImpulse(Vector3 vector3) {
            SoftbodyJNI.btSoftBody_Body_applyDCImpulse(this.swigCPtr, this, vector3);
        }
    }

    public static class Joint extends BulletBase {
        private long swigCPtr;

        protected Joint(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Joint(long j, boolean z) {
            this("Joint", j, z);
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

        public static long getCPtr(Joint joint) {
            if (joint == null) {
                return 0L;
            }
            return joint.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_Joint(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public static class eType extends BulletBase {
            private long swigCPtr;

            public static final class EnumFlagType {
                public static final int Angular = 1;
                public static final int Contact = 2;
                public static final int Linear = 0;
            }

            protected eType(String str, long j, boolean z) {
                super(str, j, z);
                this.swigCPtr = j;
            }

            public eType(long j, boolean z) {
                this("eType", j, z);
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

            public static long getCPtr(eType etype) {
                if (etype == null) {
                    return 0L;
                }
                return etype.swigCPtr;
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
                        SoftbodyJNI.delete_btSoftBody_Joint_eType(this.swigCPtr);
                    }
                    this.swigCPtr = 0L;
                }
                super.delete();
            }

            public eType() {
                this(SoftbodyJNI.new_btSoftBody_Joint_eType(), true);
            }
        }

        public static class Specs extends BulletBase {
            private long swigCPtr;

            protected Specs(String str, long j, boolean z) {
                super(str, j, z);
                this.swigCPtr = j;
            }

            public Specs(long j, boolean z) {
                this("Specs", j, z);
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

            public static long getCPtr(Specs specs) {
                if (specs == null) {
                    return 0L;
                }
                return specs.swigCPtr;
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
                        SoftbodyJNI.delete_btSoftBody_Joint_Specs(this.swigCPtr);
                    }
                    this.swigCPtr = 0L;
                }
                super.delete();
            }

            public Specs() {
                this(SoftbodyJNI.new_btSoftBody_Joint_Specs(), true);
            }

            public void setErp(float f) {
                SoftbodyJNI.btSoftBody_Joint_Specs_erp_set(this.swigCPtr, this, f);
            }

            public float getErp() {
                return SoftbodyJNI.btSoftBody_Joint_Specs_erp_get(this.swigCPtr, this);
            }

            public void setCfm(float f) {
                SoftbodyJNI.btSoftBody_Joint_Specs_cfm_set(this.swigCPtr, this, f);
            }

            public float getCfm() {
                return SoftbodyJNI.btSoftBody_Joint_Specs_cfm_get(this.swigCPtr, this);
            }

            public void setSplit(float f) {
                SoftbodyJNI.btSoftBody_Joint_Specs_split_set(this.swigCPtr, this, f);
            }

            public float getSplit() {
                return SoftbodyJNI.btSoftBody_Joint_Specs_split_get(this.swigCPtr, this);
            }
        }

        public void setBodies(Body body) {
            SoftbodyJNI.btSoftBody_Joint_bodies_set(this.swigCPtr, this, Body.getCPtr(body), body);
        }

        public Body getBodies() {
            long jBtSoftBody_Joint_bodies_get = SoftbodyJNI.btSoftBody_Joint_bodies_get(this.swigCPtr, this);
            if (jBtSoftBody_Joint_bodies_get == 0) {
                return null;
            }
            return new Body(jBtSoftBody_Joint_bodies_get, false);
        }

        public void setRefs(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Joint_refs_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getRefs() {
            long jBtSoftBody_Joint_refs_get = SoftbodyJNI.btSoftBody_Joint_refs_get(this.swigCPtr, this);
            if (jBtSoftBody_Joint_refs_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Joint_refs_get, false);
        }

        public void setCfm(float f) {
            SoftbodyJNI.btSoftBody_Joint_cfm_set(this.swigCPtr, this, f);
        }

        public float getCfm() {
            return SoftbodyJNI.btSoftBody_Joint_cfm_get(this.swigCPtr, this);
        }

        public void setErp(float f) {
            SoftbodyJNI.btSoftBody_Joint_erp_set(this.swigCPtr, this, f);
        }

        public float getErp() {
            return SoftbodyJNI.btSoftBody_Joint_erp_get(this.swigCPtr, this);
        }

        public void setSplit(float f) {
            SoftbodyJNI.btSoftBody_Joint_split_set(this.swigCPtr, this, f);
        }

        public float getSplit() {
            return SoftbodyJNI.btSoftBody_Joint_split_get(this.swigCPtr, this);
        }

        public void setDrift(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Joint_drift_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getDrift() {
            long jBtSoftBody_Joint_drift_get = SoftbodyJNI.btSoftBody_Joint_drift_get(this.swigCPtr, this);
            if (jBtSoftBody_Joint_drift_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Joint_drift_get, false);
        }

        public void setSdrift(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_Joint_sdrift_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getSdrift() {
            long jBtSoftBody_Joint_sdrift_get = SoftbodyJNI.btSoftBody_Joint_sdrift_get(this.swigCPtr, this);
            if (jBtSoftBody_Joint_sdrift_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_Joint_sdrift_get, false);
        }

        public void setMassmatrix(btMatrix3x3 btmatrix3x3) {
            SoftbodyJNI.btSoftBody_Joint_massmatrix_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
        }

        public btMatrix3x3 getMassmatrix() {
            long jBtSoftBody_Joint_massmatrix_get = SoftbodyJNI.btSoftBody_Joint_massmatrix_get(this.swigCPtr, this);
            if (jBtSoftBody_Joint_massmatrix_get == 0) {
                return null;
            }
            return new btMatrix3x3(jBtSoftBody_Joint_massmatrix_get, false);
        }

        public void setDelete(boolean z) {
            SoftbodyJNI.btSoftBody_Joint_delete_set(this.swigCPtr, this, z);
        }

        public boolean getDelete() {
            return SoftbodyJNI.btSoftBody_Joint_delete_get(this.swigCPtr, this);
        }

        public void Prepare(float f, int i) {
            SoftbodyJNI.btSoftBody_Joint_Prepare(this.swigCPtr, this, f, i);
        }

        public void Solve(float f, float f2) {
            SoftbodyJNI.btSoftBody_Joint_Solve(this.swigCPtr, this, f, f2);
        }

        public void Terminate(float f) {
            SoftbodyJNI.btSoftBody_Joint_Terminate(this.swigCPtr, this, f);
        }

        public int Type() {
            return SoftbodyJNI.btSoftBody_Joint_Type(this.swigCPtr, this);
        }
    }

    public static class LJoint extends Joint {
        private long swigCPtr;

        protected LJoint(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftBody_LJoint_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public LJoint(long j, boolean z) {
            this("LJoint", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftBody_LJoint_SWIGUpcast(j), z);
        }

        public static long getCPtr(LJoint lJoint) {
            if (lJoint == null) {
                return 0L;
            }
            return lJoint.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftBody_LJoint(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public static class Specs extends Joint.Specs {
            private long swigCPtr;

            protected Specs(String str, long j, boolean z) {
                super(str, SoftbodyJNI.btSoftBody_LJoint_Specs_SWIGUpcast(j), z);
                this.swigCPtr = j;
            }

            public Specs(long j, boolean z) {
                this("Specs", j, z);
                construct();
            }

            @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint.Specs, com.badlogic.gdx.physics.bullet.BulletBase
            protected void reset(long j, boolean z) {
                if (!this.destroyed) {
                    destroy();
                }
                this.swigCPtr = j;
                super.reset(SoftbodyJNI.btSoftBody_LJoint_Specs_SWIGUpcast(j), z);
            }

            public static long getCPtr(Specs specs) {
                if (specs == null) {
                    return 0L;
                }
                return specs.swigCPtr;
            }

            @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint.Specs, com.badlogic.gdx.physics.bullet.BulletBase
            protected void finalize() throws Throwable {
                if (!this.destroyed) {
                    destroy();
                }
                super.finalize();
            }

            @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint.Specs, com.badlogic.gdx.physics.bullet.BulletBase
            protected synchronized void delete() {
                if (this.swigCPtr != 0) {
                    if (this.swigCMemOwn) {
                        this.swigCMemOwn = false;
                        SoftbodyJNI.delete_btSoftBody_LJoint_Specs(this.swigCPtr);
                    }
                    this.swigCPtr = 0L;
                }
                super.delete();
            }

            public void setPosition(btVector3 btvector3) {
                SoftbodyJNI.btSoftBody_LJoint_Specs_position_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
            }

            public btVector3 getPosition() {
                long jBtSoftBody_LJoint_Specs_position_get = SoftbodyJNI.btSoftBody_LJoint_Specs_position_get(this.swigCPtr, this);
                if (jBtSoftBody_LJoint_Specs_position_get == 0) {
                    return null;
                }
                return new btVector3(jBtSoftBody_LJoint_Specs_position_get, false);
            }

            public Specs() {
                this(SoftbodyJNI.new_btSoftBody_LJoint_Specs(), true);
            }
        }

        public void setRpos(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_LJoint_rpos_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getRpos() {
            long jBtSoftBody_LJoint_rpos_get = SoftbodyJNI.btSoftBody_LJoint_rpos_get(this.swigCPtr, this);
            if (jBtSoftBody_LJoint_rpos_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_LJoint_rpos_get, false);
        }

        public LJoint() {
            this(SoftbodyJNI.new_btSoftBody_LJoint(), true);
        }
    }

    public static class AJoint extends Joint {
        private long swigCPtr;

        protected AJoint(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftBody_AJoint_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public AJoint(long j, boolean z) {
            this("AJoint", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftBody_AJoint_SWIGUpcast(j), z);
        }

        public static long getCPtr(AJoint aJoint) {
            if (aJoint == null) {
                return 0L;
            }
            return aJoint.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftBody_AJoint(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public static class IControl extends BulletBase {
            private long swigCPtr;

            protected IControl(String str, long j, boolean z) {
                super(str, j, z);
                this.swigCPtr = j;
            }

            public IControl(long j, boolean z) {
                this("IControl", j, z);
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

            public static long getCPtr(IControl iControl) {
                if (iControl == null) {
                    return 0L;
                }
                return iControl.swigCPtr;
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
                        SoftbodyJNI.delete_btSoftBody_AJoint_IControl(this.swigCPtr);
                    }
                    this.swigCPtr = 0L;
                }
                super.delete();
            }

            public void Prepare(AJoint aJoint) {
                SoftbodyJNI.btSoftBody_AJoint_IControl_Prepare(this.swigCPtr, this, AJoint.getCPtr(aJoint), aJoint);
            }

            public float Speed(AJoint aJoint, float f) {
                return SoftbodyJNI.btSoftBody_AJoint_IControl_Speed(this.swigCPtr, this, AJoint.getCPtr(aJoint), aJoint, f);
            }

            public static IControl Default() {
                long jBtSoftBody_AJoint_IControl_Default = SoftbodyJNI.btSoftBody_AJoint_IControl_Default();
                if (jBtSoftBody_AJoint_IControl_Default == 0) {
                    return null;
                }
                return new IControl(jBtSoftBody_AJoint_IControl_Default, false);
            }

            public IControl() {
                this(SoftbodyJNI.new_btSoftBody_AJoint_IControl(), true);
            }
        }

        public static class Specs extends Joint.Specs {
            private long swigCPtr;

            protected Specs(String str, long j, boolean z) {
                super(str, SoftbodyJNI.btSoftBody_AJoint_Specs_SWIGUpcast(j), z);
                this.swigCPtr = j;
            }

            public Specs(long j, boolean z) {
                this("Specs", j, z);
                construct();
            }

            @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint.Specs, com.badlogic.gdx.physics.bullet.BulletBase
            protected void reset(long j, boolean z) {
                if (!this.destroyed) {
                    destroy();
                }
                this.swigCPtr = j;
                super.reset(SoftbodyJNI.btSoftBody_AJoint_Specs_SWIGUpcast(j), z);
            }

            public static long getCPtr(Specs specs) {
                if (specs == null) {
                    return 0L;
                }
                return specs.swigCPtr;
            }

            @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint.Specs, com.badlogic.gdx.physics.bullet.BulletBase
            protected void finalize() throws Throwable {
                if (!this.destroyed) {
                    destroy();
                }
                super.finalize();
            }

            @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint.Specs, com.badlogic.gdx.physics.bullet.BulletBase
            protected synchronized void delete() {
                if (this.swigCPtr != 0) {
                    if (this.swigCMemOwn) {
                        this.swigCMemOwn = false;
                        SoftbodyJNI.delete_btSoftBody_AJoint_Specs(this.swigCPtr);
                    }
                    this.swigCPtr = 0L;
                }
                super.delete();
            }

            public Specs() {
                this(SoftbodyJNI.new_btSoftBody_AJoint_Specs(), true);
            }

            public void setAxis(btVector3 btvector3) {
                SoftbodyJNI.btSoftBody_AJoint_Specs_axis_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
            }

            public btVector3 getAxis() {
                long jBtSoftBody_AJoint_Specs_axis_get = SoftbodyJNI.btSoftBody_AJoint_Specs_axis_get(this.swigCPtr, this);
                if (jBtSoftBody_AJoint_Specs_axis_get == 0) {
                    return null;
                }
                return new btVector3(jBtSoftBody_AJoint_Specs_axis_get, false);
            }

            public void setIcontrol(IControl iControl) {
                SoftbodyJNI.btSoftBody_AJoint_Specs_icontrol_set(this.swigCPtr, this, IControl.getCPtr(iControl), iControl);
            }

            public IControl getIcontrol() {
                long jBtSoftBody_AJoint_Specs_icontrol_get = SoftbodyJNI.btSoftBody_AJoint_Specs_icontrol_get(this.swigCPtr, this);
                if (jBtSoftBody_AJoint_Specs_icontrol_get == 0) {
                    return null;
                }
                return new IControl(jBtSoftBody_AJoint_Specs_icontrol_get, false);
            }
        }

        public void setAxis(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_AJoint_axis_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getAxis() {
            long jBtSoftBody_AJoint_axis_get = SoftbodyJNI.btSoftBody_AJoint_axis_get(this.swigCPtr, this);
            if (jBtSoftBody_AJoint_axis_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_AJoint_axis_get, false);
        }

        public void setIcontrol(IControl iControl) {
            SoftbodyJNI.btSoftBody_AJoint_icontrol_set(this.swigCPtr, this, IControl.getCPtr(iControl), iControl);
        }

        public IControl getIcontrol() {
            long jBtSoftBody_AJoint_icontrol_get = SoftbodyJNI.btSoftBody_AJoint_icontrol_get(this.swigCPtr, this);
            if (jBtSoftBody_AJoint_icontrol_get == 0) {
                return null;
            }
            return new IControl(jBtSoftBody_AJoint_icontrol_get, false);
        }

        public AJoint() {
            this(SoftbodyJNI.new_btSoftBody_AJoint(), true);
        }
    }

    public static class CJoint extends Joint {
        private long swigCPtr;

        protected CJoint(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftBody_CJoint_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public CJoint(long j, boolean z) {
            this("CJoint", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftBody_CJoint_SWIGUpcast(j), z);
        }

        public static long getCPtr(CJoint cJoint) {
            if (cJoint == null) {
                return 0L;
            }
            return cJoint.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftBody.Joint, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftBody_CJoint(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setLife(int i) {
            SoftbodyJNI.btSoftBody_CJoint_life_set(this.swigCPtr, this, i);
        }

        public int getLife() {
            return SoftbodyJNI.btSoftBody_CJoint_life_get(this.swigCPtr, this);
        }

        public void setMaxlife(int i) {
            SoftbodyJNI.btSoftBody_CJoint_maxlife_set(this.swigCPtr, this, i);
        }

        public int getMaxlife() {
            return SoftbodyJNI.btSoftBody_CJoint_maxlife_get(this.swigCPtr, this);
        }

        public void setRpos(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_CJoint_rpos_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getRpos() {
            long jBtSoftBody_CJoint_rpos_get = SoftbodyJNI.btSoftBody_CJoint_rpos_get(this.swigCPtr, this);
            if (jBtSoftBody_CJoint_rpos_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_CJoint_rpos_get, false);
        }

        public void setNormal(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_CJoint_normal_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getNormal() {
            long jBtSoftBody_CJoint_normal_get = SoftbodyJNI.btSoftBody_CJoint_normal_get(this.swigCPtr, this);
            if (jBtSoftBody_CJoint_normal_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_CJoint_normal_get, false);
        }

        public void setFriction(float f) {
            SoftbodyJNI.btSoftBody_CJoint_friction_set(this.swigCPtr, this, f);
        }

        public float getFriction() {
            return SoftbodyJNI.btSoftBody_CJoint_friction_get(this.swigCPtr, this);
        }

        public CJoint() {
            this(SoftbodyJNI.new_btSoftBody_CJoint(), true);
        }
    }

    public static class Config extends BulletBase {
        private long swigCPtr;

        protected Config(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Config(long j, boolean z) {
            this("Config", j, z);
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

        public static long getCPtr(Config config) {
            if (config == null) {
                return 0L;
            }
            return config.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_Config(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setAeromodel(int i) {
            SoftbodyJNI.btSoftBody_Config_aeromodel_set(this.swigCPtr, this, i);
        }

        public int getAeromodel() {
            return SoftbodyJNI.btSoftBody_Config_aeromodel_get(this.swigCPtr, this);
        }

        public void setKVCF(float f) {
            SoftbodyJNI.btSoftBody_Config_kVCF_set(this.swigCPtr, this, f);
        }

        public float getKVCF() {
            return SoftbodyJNI.btSoftBody_Config_kVCF_get(this.swigCPtr, this);
        }

        public void setKDP(float f) {
            SoftbodyJNI.btSoftBody_Config_kDP_set(this.swigCPtr, this, f);
        }

        public float getKDP() {
            return SoftbodyJNI.btSoftBody_Config_kDP_get(this.swigCPtr, this);
        }

        public void setKDG(float f) {
            SoftbodyJNI.btSoftBody_Config_kDG_set(this.swigCPtr, this, f);
        }

        public float getKDG() {
            return SoftbodyJNI.btSoftBody_Config_kDG_get(this.swigCPtr, this);
        }

        public void setKLF(float f) {
            SoftbodyJNI.btSoftBody_Config_kLF_set(this.swigCPtr, this, f);
        }

        public float getKLF() {
            return SoftbodyJNI.btSoftBody_Config_kLF_get(this.swigCPtr, this);
        }

        public void setKPR(float f) {
            SoftbodyJNI.btSoftBody_Config_kPR_set(this.swigCPtr, this, f);
        }

        public float getKPR() {
            return SoftbodyJNI.btSoftBody_Config_kPR_get(this.swigCPtr, this);
        }

        public void setKVC(float f) {
            SoftbodyJNI.btSoftBody_Config_kVC_set(this.swigCPtr, this, f);
        }

        public float getKVC() {
            return SoftbodyJNI.btSoftBody_Config_kVC_get(this.swigCPtr, this);
        }

        public void setKDF(float f) {
            SoftbodyJNI.btSoftBody_Config_kDF_set(this.swigCPtr, this, f);
        }

        public float getKDF() {
            return SoftbodyJNI.btSoftBody_Config_kDF_get(this.swigCPtr, this);
        }

        public void setKMT(float f) {
            SoftbodyJNI.btSoftBody_Config_kMT_set(this.swigCPtr, this, f);
        }

        public float getKMT() {
            return SoftbodyJNI.btSoftBody_Config_kMT_get(this.swigCPtr, this);
        }

        public void setKCHR(float f) {
            SoftbodyJNI.btSoftBody_Config_kCHR_set(this.swigCPtr, this, f);
        }

        public float getKCHR() {
            return SoftbodyJNI.btSoftBody_Config_kCHR_get(this.swigCPtr, this);
        }

        public void setKKHR(float f) {
            SoftbodyJNI.btSoftBody_Config_kKHR_set(this.swigCPtr, this, f);
        }

        public float getKKHR() {
            return SoftbodyJNI.btSoftBody_Config_kKHR_get(this.swigCPtr, this);
        }

        public void setKSHR(float f) {
            SoftbodyJNI.btSoftBody_Config_kSHR_set(this.swigCPtr, this, f);
        }

        public float getKSHR() {
            return SoftbodyJNI.btSoftBody_Config_kSHR_get(this.swigCPtr, this);
        }

        public void setKAHR(float f) {
            SoftbodyJNI.btSoftBody_Config_kAHR_set(this.swigCPtr, this, f);
        }

        public float getKAHR() {
            return SoftbodyJNI.btSoftBody_Config_kAHR_get(this.swigCPtr, this);
        }

        public void setKSRHR_CL(float f) {
            SoftbodyJNI.btSoftBody_Config_kSRHR_CL_set(this.swigCPtr, this, f);
        }

        public float getKSRHR_CL() {
            return SoftbodyJNI.btSoftBody_Config_kSRHR_CL_get(this.swigCPtr, this);
        }

        public void setKSKHR_CL(float f) {
            SoftbodyJNI.btSoftBody_Config_kSKHR_CL_set(this.swigCPtr, this, f);
        }

        public float getKSKHR_CL() {
            return SoftbodyJNI.btSoftBody_Config_kSKHR_CL_get(this.swigCPtr, this);
        }

        public void setKSSHR_CL(float f) {
            SoftbodyJNI.btSoftBody_Config_kSSHR_CL_set(this.swigCPtr, this, f);
        }

        public float getKSSHR_CL() {
            return SoftbodyJNI.btSoftBody_Config_kSSHR_CL_get(this.swigCPtr, this);
        }

        public void setKSR_SPLT_CL(float f) {
            SoftbodyJNI.btSoftBody_Config_kSR_SPLT_CL_set(this.swigCPtr, this, f);
        }

        public float getKSR_SPLT_CL() {
            return SoftbodyJNI.btSoftBody_Config_kSR_SPLT_CL_get(this.swigCPtr, this);
        }

        public void setKSK_SPLT_CL(float f) {
            SoftbodyJNI.btSoftBody_Config_kSK_SPLT_CL_set(this.swigCPtr, this, f);
        }

        public float getKSK_SPLT_CL() {
            return SoftbodyJNI.btSoftBody_Config_kSK_SPLT_CL_get(this.swigCPtr, this);
        }

        public void setKSS_SPLT_CL(float f) {
            SoftbodyJNI.btSoftBody_Config_kSS_SPLT_CL_set(this.swigCPtr, this, f);
        }

        public float getKSS_SPLT_CL() {
            return SoftbodyJNI.btSoftBody_Config_kSS_SPLT_CL_get(this.swigCPtr, this);
        }

        public void setMaxvolume(float f) {
            SoftbodyJNI.btSoftBody_Config_maxvolume_set(this.swigCPtr, this, f);
        }

        public float getMaxvolume() {
            return SoftbodyJNI.btSoftBody_Config_maxvolume_get(this.swigCPtr, this);
        }

        public void setTimescale(float f) {
            SoftbodyJNI.btSoftBody_Config_timescale_set(this.swigCPtr, this, f);
        }

        public float getTimescale() {
            return SoftbodyJNI.btSoftBody_Config_timescale_get(this.swigCPtr, this);
        }

        public void setViterations(int i) {
            SoftbodyJNI.btSoftBody_Config_viterations_set(this.swigCPtr, this, i);
        }

        public int getViterations() {
            return SoftbodyJNI.btSoftBody_Config_viterations_get(this.swigCPtr, this);
        }

        public void setPiterations(int i) {
            SoftbodyJNI.btSoftBody_Config_piterations_set(this.swigCPtr, this, i);
        }

        public int getPiterations() {
            return SoftbodyJNI.btSoftBody_Config_piterations_get(this.swigCPtr, this);
        }

        public void setDiterations(int i) {
            SoftbodyJNI.btSoftBody_Config_diterations_set(this.swigCPtr, this, i);
        }

        public int getDiterations() {
            return SoftbodyJNI.btSoftBody_Config_diterations_get(this.swigCPtr, this);
        }

        public void setCiterations(int i) {
            SoftbodyJNI.btSoftBody_Config_citerations_set(this.swigCPtr, this, i);
        }

        public int getCiterations() {
            return SoftbodyJNI.btSoftBody_Config_citerations_get(this.swigCPtr, this);
        }

        public void setCollisions(int i) {
            SoftbodyJNI.btSoftBody_Config_collisions_set(this.swigCPtr, this, i);
        }

        public int getCollisions() {
            return SoftbodyJNI.btSoftBody_Config_collisions_get(this.swigCPtr, this);
        }

        public void setVsequence(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__eVSolver____t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__eVSolver____t) {
            SoftbodyJNI.btSoftBody_Config_vsequence_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__eVSolver____t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__eVSolver____t));
        }

        public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__eVSolver____t getVsequence() {
            long jBtSoftBody_Config_vsequence_get = SoftbodyJNI.btSoftBody_Config_vsequence_get(this.swigCPtr, this);
            if (jBtSoftBody_Config_vsequence_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__eVSolver____t(jBtSoftBody_Config_vsequence_get, false);
        }

        public void setPsequence(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t) {
            SoftbodyJNI.btSoftBody_Config_psequence_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t));
        }

        public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t getPsequence() {
            long jBtSoftBody_Config_psequence_get = SoftbodyJNI.btSoftBody_Config_psequence_get(this.swigCPtr, this);
            if (jBtSoftBody_Config_psequence_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t(jBtSoftBody_Config_psequence_get, false);
        }

        public void setDsequence(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t) {
            SoftbodyJNI.btSoftBody_Config_dsequence_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t));
        }

        public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t getDsequence() {
            long jBtSoftBody_Config_dsequence_get = SoftbodyJNI.btSoftBody_Config_dsequence_get(this.swigCPtr, this);
            if (jBtSoftBody_Config_dsequence_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t(jBtSoftBody_Config_dsequence_get, false);
        }

        public Config() {
            this(SoftbodyJNI.new_btSoftBody_Config(), true);
        }
    }

    public static class SolverState extends BulletBase {
        private long swigCPtr;

        protected SolverState(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public SolverState(long j, boolean z) {
            this("SolverState", j, z);
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

        public static long getCPtr(SolverState solverState) {
            if (solverState == null) {
                return 0L;
            }
            return solverState.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftBody_SolverState(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setSdt(float f) {
            SoftbodyJNI.btSoftBody_SolverState_sdt_set(this.swigCPtr, this, f);
        }

        public float getSdt() {
            return SoftbodyJNI.btSoftBody_SolverState_sdt_get(this.swigCPtr, this);
        }

        public void setIsdt(float f) {
            SoftbodyJNI.btSoftBody_SolverState_isdt_set(this.swigCPtr, this, f);
        }

        public float getIsdt() {
            return SoftbodyJNI.btSoftBody_SolverState_isdt_get(this.swigCPtr, this);
        }

        public void setVelmrg(float f) {
            SoftbodyJNI.btSoftBody_SolverState_velmrg_set(this.swigCPtr, this, f);
        }

        public float getVelmrg() {
            return SoftbodyJNI.btSoftBody_SolverState_velmrg_get(this.swigCPtr, this);
        }

        public void setRadmrg(float f) {
            SoftbodyJNI.btSoftBody_SolverState_radmrg_set(this.swigCPtr, this, f);
        }

        public float getRadmrg() {
            return SoftbodyJNI.btSoftBody_SolverState_radmrg_get(this.swigCPtr, this);
        }

        public void setUpdmrg(float f) {
            SoftbodyJNI.btSoftBody_SolverState_updmrg_set(this.swigCPtr, this, f);
        }

        public float getUpdmrg() {
            return SoftbodyJNI.btSoftBody_SolverState_updmrg_get(this.swigCPtr, this);
        }

        public SolverState() {
            this(SoftbodyJNI.new_btSoftBody_SolverState(), true);
        }
    }

    public static class RayFromToCaster extends ICollide {
        private long swigCPtr;

        protected RayFromToCaster(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftBody_RayFromToCaster_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public RayFromToCaster(long j, boolean z) {
            this("RayFromToCaster", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftBody_RayFromToCaster_SWIGUpcast(j), z);
        }

        public static long getCPtr(RayFromToCaster rayFromToCaster) {
            if (rayFromToCaster == null) {
                return 0L;
            }
            return rayFromToCaster.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftBody_RayFromToCaster(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setRayFrom(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_RayFromToCaster_rayFrom_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getRayFrom() {
            long jBtSoftBody_RayFromToCaster_rayFrom_get = SoftbodyJNI.btSoftBody_RayFromToCaster_rayFrom_get(this.swigCPtr, this);
            if (jBtSoftBody_RayFromToCaster_rayFrom_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_RayFromToCaster_rayFrom_get, false);
        }

        public void setRayTo(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_RayFromToCaster_rayTo_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getRayTo() {
            long jBtSoftBody_RayFromToCaster_rayTo_get = SoftbodyJNI.btSoftBody_RayFromToCaster_rayTo_get(this.swigCPtr, this);
            if (jBtSoftBody_RayFromToCaster_rayTo_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_RayFromToCaster_rayTo_get, false);
        }

        public void setRayNormalizedDirection(btVector3 btvector3) {
            SoftbodyJNI.btSoftBody_RayFromToCaster_rayNormalizedDirection_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getRayNormalizedDirection() {
            long jBtSoftBody_RayFromToCaster_rayNormalizedDirection_get = SoftbodyJNI.btSoftBody_RayFromToCaster_rayNormalizedDirection_get(this.swigCPtr, this);
            if (jBtSoftBody_RayFromToCaster_rayNormalizedDirection_get == 0) {
                return null;
            }
            return new btVector3(jBtSoftBody_RayFromToCaster_rayNormalizedDirection_get, false);
        }

        public void setMint(float f) {
            SoftbodyJNI.btSoftBody_RayFromToCaster_mint_set(this.swigCPtr, this, f);
        }

        public float getMint() {
            return SoftbodyJNI.btSoftBody_RayFromToCaster_mint_get(this.swigCPtr, this);
        }

        public void setFace(Face face) {
            SoftbodyJNI.btSoftBody_RayFromToCaster_face_set(this.swigCPtr, this, Face.getCPtr(face), face);
        }

        public Face getFace() {
            long jBtSoftBody_RayFromToCaster_face_get = SoftbodyJNI.btSoftBody_RayFromToCaster_face_get(this.swigCPtr, this);
            if (jBtSoftBody_RayFromToCaster_face_get == 0) {
                return null;
            }
            return new Face(jBtSoftBody_RayFromToCaster_face_get, false);
        }

        public void setTests(int i) {
            SoftbodyJNI.btSoftBody_RayFromToCaster_tests_set(this.swigCPtr, this, i);
        }

        public int getTests() {
            return SoftbodyJNI.btSoftBody_RayFromToCaster_tests_get(this.swigCPtr, this);
        }

        public RayFromToCaster(Vector3 vector3, Vector3 vector4, float f) {
            this(SoftbodyJNI.new_btSoftBody_RayFromToCaster(vector3, vector4, f), true);
        }

        public static float rayFromToTriangle(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, Vector3 vector8, float f) {
            return SoftbodyJNI.btSoftBody_RayFromToCaster_rayFromToTriangle__SWIG_0(vector3, vector4, vector5, vector6, vector7, vector8, f);
        }

        public static float rayFromToTriangle(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, Vector3 vector8) {
            return SoftbodyJNI.btSoftBody_RayFromToCaster_rayFromToTriangle__SWIG_1(vector3, vector4, vector5, vector6, vector7, vector8);
        }
    }

    public void setCfg(Config config) {
        SoftbodyJNI.btSoftBody_cfg_set(this.swigCPtr, this, Config.getCPtr(config), config);
    }

    public Config getCfg() {
        long jBtSoftBody_cfg_get = SoftbodyJNI.btSoftBody_cfg_get(this.swigCPtr, this);
        if (jBtSoftBody_cfg_get == 0) {
            return null;
        }
        return new Config(jBtSoftBody_cfg_get, false);
    }

    public void setSst(SolverState solverState) {
        SoftbodyJNI.btSoftBody_sst_set(this.swigCPtr, this, SolverState.getCPtr(solverState), solverState);
    }

    public SolverState getSst() {
        long jBtSoftBody_sst_get = SoftbodyJNI.btSoftBody_sst_get(this.swigCPtr, this);
        if (jBtSoftBody_sst_get == 0) {
            return null;
        }
        return new SolverState(jBtSoftBody_sst_get, false);
    }

    public void setPose(Pose pose) {
        SoftbodyJNI.btSoftBody_pose_set(this.swigCPtr, this, Pose.getCPtr(pose), pose);
    }

    public Pose getPose() {
        long jBtSoftBody_pose_get = SoftbodyJNI.btSoftBody_pose_get(this.swigCPtr, this);
        if (jBtSoftBody_pose_get == 0) {
            return null;
        }
        return new Pose(jBtSoftBody_pose_get, false);
    }

    public void setTag(long j) {
        SoftbodyJNI.btSoftBody_tag_set(this.swigCPtr, this, j);
    }

    public long getTag() {
        return SoftbodyJNI.btSoftBody_tag_get(this.swigCPtr, this);
    }

    public void setWorldInfo(btSoftBodyWorldInfo btsoftbodyworldinfo) {
        SoftbodyJNI.btSoftBody_worldInfo_set(this.swigCPtr, this, btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo);
    }

    public btSoftBodyWorldInfo getWorldInfo() {
        long jBtSoftBody_worldInfo_get = SoftbodyJNI.btSoftBody_worldInfo_get(this.swigCPtr, this);
        if (jBtSoftBody_worldInfo_get == 0) {
            return null;
        }
        return new btSoftBodyWorldInfo(jBtSoftBody_worldInfo_get, false);
    }

    public void setNotes(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Note_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Note_t) {
        SoftbodyJNI.btSoftBody_notes_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Note_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Note_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Note_t getNotes() {
        long jBtSoftBody_notes_get = SoftbodyJNI.btSoftBody_notes_get(this.swigCPtr, this);
        if (jBtSoftBody_notes_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Note_t(jBtSoftBody_notes_get, false);
    }

    public void setNodes(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_t) {
        SoftbodyJNI.btSoftBody_nodes_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_t getNodes() {
        long jBtSoftBody_nodes_get = SoftbodyJNI.btSoftBody_nodes_get(this.swigCPtr, this);
        if (jBtSoftBody_nodes_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_t(jBtSoftBody_nodes_get, false);
    }

    public void setLinks(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Link_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Link_t) {
        SoftbodyJNI.btSoftBody_links_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Link_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Link_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Link_t getLinks() {
        long jBtSoftBody_links_get = SoftbodyJNI.btSoftBody_links_get(this.swigCPtr, this);
        if (jBtSoftBody_links_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Link_t(jBtSoftBody_links_get, false);
    }

    public void setFaces(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Face_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Face_t) {
        SoftbodyJNI.btSoftBody_faces_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Face_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Face_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Face_t getFaces() {
        long jBtSoftBody_faces_get = SoftbodyJNI.btSoftBody_faces_get(this.swigCPtr, this);
        if (jBtSoftBody_faces_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Face_t(jBtSoftBody_faces_get, false);
    }

    public void setTetras(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Tetra_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Tetra_t) {
        SoftbodyJNI.btSoftBody_tetras_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Tetra_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Tetra_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Tetra_t getTetras() {
        long jBtSoftBody_tetras_get = SoftbodyJNI.btSoftBody_tetras_get(this.swigCPtr, this);
        if (jBtSoftBody_tetras_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Tetra_t(jBtSoftBody_tetras_get, false);
    }

    public void setAnchors(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Anchor_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Anchor_t) {
        SoftbodyJNI.btSoftBody_anchors_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Anchor_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Anchor_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Anchor_t getAnchors() {
        long jBtSoftBody_anchors_get = SoftbodyJNI.btSoftBody_anchors_get(this.swigCPtr, this);
        if (jBtSoftBody_anchors_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Anchor_t(jBtSoftBody_anchors_get, false);
    }

    public void setRcontacts(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__RContact_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__RContact_t) {
        SoftbodyJNI.btSoftBody_rcontacts_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__RContact_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__RContact_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__RContact_t getRcontacts() {
        long jBtSoftBody_rcontacts_get = SoftbodyJNI.btSoftBody_rcontacts_get(this.swigCPtr, this);
        if (jBtSoftBody_rcontacts_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__RContact_t(jBtSoftBody_rcontacts_get, false);
    }

    public void setScontacts(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__SContact_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__SContact_t) {
        SoftbodyJNI.btSoftBody_scontacts_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__SContact_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__SContact_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__SContact_t getScontacts() {
        long jBtSoftBody_scontacts_get = SoftbodyJNI.btSoftBody_scontacts_get(this.swigCPtr, this);
        if (jBtSoftBody_scontacts_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__SContact_t(jBtSoftBody_scontacts_get, false);
    }

    public void setJoints(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Joint_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Joint_p_t) {
        SoftbodyJNI.btSoftBody_joints_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Joint_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Joint_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Joint_p_t getJoints() {
        long jBtSoftBody_joints_get = SoftbodyJNI.btSoftBody_joints_get(this.swigCPtr, this);
        if (jBtSoftBody_joints_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Joint_p_t(jBtSoftBody_joints_get, false);
    }

    public void setMaterials(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Material_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Material_p_t) {
        SoftbodyJNI.btSoftBody_materials_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Material_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Material_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Material_p_t getMaterials() {
        long jBtSoftBody_materials_get = SoftbodyJNI.btSoftBody_materials_get(this.swigCPtr, this);
        if (jBtSoftBody_materials_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Material_p_t(jBtSoftBody_materials_get, false);
    }

    public void setTimeacc(float f) {
        SoftbodyJNI.btSoftBody_timeacc_set(this.swigCPtr, this, f);
    }

    public float getTimeacc() {
        return SoftbodyJNI.btSoftBody_timeacc_get(this.swigCPtr, this);
    }

    public void setBounds(btVector3 btvector3) {
        SoftbodyJNI.btSoftBody_bounds_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getBounds() {
        long jBtSoftBody_bounds_get = SoftbodyJNI.btSoftBody_bounds_get(this.swigCPtr, this);
        if (jBtSoftBody_bounds_get == 0) {
            return null;
        }
        return new btVector3(jBtSoftBody_bounds_get, false);
    }

    public void setBUpdateRtCst(boolean z) {
        SoftbodyJNI.btSoftBody_bUpdateRtCst_set(this.swigCPtr, this, z);
    }

    public boolean getBUpdateRtCst() {
        return SoftbodyJNI.btSoftBody_bUpdateRtCst_get(this.swigCPtr, this);
    }

    public void setNdbvt(btDbvt btdbvt) {
        SoftbodyJNI.btSoftBody_ndbvt_set(this.swigCPtr, this, btDbvt.getCPtr(btdbvt), btdbvt);
    }

    public btDbvt getNdbvt() {
        long jBtSoftBody_ndbvt_get = SoftbodyJNI.btSoftBody_ndbvt_get(this.swigCPtr, this);
        if (jBtSoftBody_ndbvt_get == 0) {
            return null;
        }
        return new btDbvt(jBtSoftBody_ndbvt_get, false);
    }

    public void setFdbvt(btDbvt btdbvt) {
        SoftbodyJNI.btSoftBody_fdbvt_set(this.swigCPtr, this, btDbvt.getCPtr(btdbvt), btdbvt);
    }

    public btDbvt getFdbvt() {
        long jBtSoftBody_fdbvt_get = SoftbodyJNI.btSoftBody_fdbvt_get(this.swigCPtr, this);
        if (jBtSoftBody_fdbvt_get == 0) {
            return null;
        }
        return new btDbvt(jBtSoftBody_fdbvt_get, false);
    }

    public void setCdbvt(btDbvt btdbvt) {
        SoftbodyJNI.btSoftBody_cdbvt_set(this.swigCPtr, this, btDbvt.getCPtr(btdbvt), btdbvt);
    }

    public btDbvt getCdbvt() {
        long jBtSoftBody_cdbvt_get = SoftbodyJNI.btSoftBody_cdbvt_get(this.swigCPtr, this);
        if (jBtSoftBody_cdbvt_get == 0) {
            return null;
        }
        return new btDbvt(jBtSoftBody_cdbvt_get, false);
    }

    public void setClusters(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Cluster_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Cluster_p_t) {
        SoftbodyJNI.btSoftBody_clusters_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Cluster_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Cluster_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Cluster_p_t getClusters() {
        long jBtSoftBody_clusters_get = SoftbodyJNI.btSoftBody_clusters_get(this.swigCPtr, this);
        if (jBtSoftBody_clusters_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Cluster_p_t(jBtSoftBody_clusters_get, false);
    }

    public void setClusterConnectivity(SWIGTYPE_p_btAlignedObjectArrayT_bool_t sWIGTYPE_p_btAlignedObjectArrayT_bool_t) {
        SoftbodyJNI.btSoftBody_clusterConnectivity_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_bool_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_bool_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_bool_t getClusterConnectivity() {
        long jBtSoftBody_clusterConnectivity_get = SoftbodyJNI.btSoftBody_clusterConnectivity_get(this.swigCPtr, this);
        if (jBtSoftBody_clusterConnectivity_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_bool_t(jBtSoftBody_clusterConnectivity_get, false);
    }

    public void setInitialWorldTransform(btTransform bttransform) {
        SoftbodyJNI.btSoftBody_initialWorldTransform_set(this.swigCPtr, this, btTransform.getCPtr(bttransform), bttransform);
    }

    public btTransform getInitialWorldTransform() {
        long jBtSoftBody_initialWorldTransform_get = SoftbodyJNI.btSoftBody_initialWorldTransform_get(this.swigCPtr, this);
        if (jBtSoftBody_initialWorldTransform_get == 0) {
            return null;
        }
        return new btTransform(jBtSoftBody_initialWorldTransform_get, false);
    }

    public void setWindVelocity(btVector3 btvector3) {
        SoftbodyJNI.btSoftBody_windVelocity_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getWindVelocity() {
        long jBtSoftBody_windVelocity_get = SoftbodyJNI.btSoftBody_windVelocity_get(this.swigCPtr, this);
        if (jBtSoftBody_windVelocity_get == 0) {
            return null;
        }
        return new btVector3(jBtSoftBody_windVelocity_get, false);
    }

    public void setRestLengthScale(float f) {
        SoftbodyJNI.btSoftBody_restLengthScale_set(this.swigCPtr, this, f);
    }

    public float getRestLengthScale() {
        return SoftbodyJNI.btSoftBody_restLengthScale_get(this.swigCPtr, this);
    }

    private static long SwigConstructbtSoftBody(btSoftBodyWorldInfo btsoftbodyworldinfo, int i, btVector3 btvector3, FloatBuffer floatBuffer) {
        return SoftbodyJNI.new_btSoftBody__SWIG_0(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, i, btVector3.getCPtr(btvector3), btvector3, floatBuffer);
    }

    public btSoftBody(btSoftBodyWorldInfo btsoftbodyworldinfo, int i, btVector3 btvector3, FloatBuffer floatBuffer) {
        this(SwigConstructbtSoftBody(btsoftbodyworldinfo, i, btvector3, floatBuffer), true);
    }

    public btSoftBody(btSoftBodyWorldInfo btsoftbodyworldinfo) {
        this(SoftbodyJNI.new_btSoftBody__SWIG_1(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo), true);
    }

    public void initDefaults() {
        SoftbodyJNI.btSoftBody_initDefaults(this.swigCPtr, this);
    }

    public void setUserIndexMapping(SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        SoftbodyJNI.btSoftBody_userIndexMapping_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_int_t getUserIndexMapping() {
        long jBtSoftBody_userIndexMapping_get = SoftbodyJNI.btSoftBody_userIndexMapping_get(this.swigCPtr, this);
        if (jBtSoftBody_userIndexMapping_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_int_t(jBtSoftBody_userIndexMapping_get, false);
    }

    public boolean checkLink(int i, int i2) {
        return SoftbodyJNI.btSoftBody_checkLink__SWIG_0(this.swigCPtr, this, i, i2);
    }

    public boolean checkLink(Node node, Node node2) {
        return SoftbodyJNI.btSoftBody_checkLink__SWIG_1(this.swigCPtr, this, Node.getCPtr(node), node, Node.getCPtr(node2), node2);
    }

    public boolean checkFace(int i, int i2, int i3) {
        return SoftbodyJNI.btSoftBody_checkFace(this.swigCPtr, this, i, i2, i3);
    }

    public Material appendMaterial() {
        long jBtSoftBody_appendMaterial = SoftbodyJNI.btSoftBody_appendMaterial(this.swigCPtr, this);
        if (jBtSoftBody_appendMaterial == 0) {
            return null;
        }
        return new Material(jBtSoftBody_appendMaterial, false);
    }

    public void appendNote(String str, Vector3 vector3, btVector4 btvector4, Node node, Node node2, Node node3, Node node4) {
        SoftbodyJNI.btSoftBody_appendNote__SWIG_0(this.swigCPtr, this, str, vector3, btVector4.getCPtr(btvector4), btvector4, Node.getCPtr(node), node, Node.getCPtr(node2), node2, Node.getCPtr(node3), node3, Node.getCPtr(node4), node4);
    }

    public void appendNote(String str, Vector3 vector3, btVector4 btvector4, Node node, Node node2, Node node3) {
        SoftbodyJNI.btSoftBody_appendNote__SWIG_1(this.swigCPtr, this, str, vector3, btVector4.getCPtr(btvector4), btvector4, Node.getCPtr(node), node, Node.getCPtr(node2), node2, Node.getCPtr(node3), node3);
    }

    public void appendNote(String str, Vector3 vector3, btVector4 btvector4, Node node, Node node2) {
        SoftbodyJNI.btSoftBody_appendNote__SWIG_2(this.swigCPtr, this, str, vector3, btVector4.getCPtr(btvector4), btvector4, Node.getCPtr(node), node, Node.getCPtr(node2), node2);
    }

    public void appendNote(String str, Vector3 vector3, btVector4 btvector4, Node node) {
        SoftbodyJNI.btSoftBody_appendNote__SWIG_3(this.swigCPtr, this, str, vector3, btVector4.getCPtr(btvector4), btvector4, Node.getCPtr(node), node);
    }

    public void appendNote(String str, Vector3 vector3, btVector4 btvector4) {
        SoftbodyJNI.btSoftBody_appendNote__SWIG_4(this.swigCPtr, this, str, vector3, btVector4.getCPtr(btvector4), btvector4);
    }

    public void appendNote(String str, Vector3 vector3) {
        SoftbodyJNI.btSoftBody_appendNote__SWIG_5(this.swigCPtr, this, str, vector3);
    }

    public void appendNote(String str, Vector3 vector3, Node node) {
        SoftbodyJNI.btSoftBody_appendNote__SWIG_6(this.swigCPtr, this, str, vector3, Node.getCPtr(node), node);
    }

    public void appendNote(String str, Vector3 vector3, Link link) {
        SoftbodyJNI.btSoftBody_appendNote__SWIG_7(this.swigCPtr, this, str, vector3, Link.getCPtr(link), link);
    }

    public void appendNote(String str, Vector3 vector3, Face face) {
        SoftbodyJNI.btSoftBody_appendNote__SWIG_8(this.swigCPtr, this, str, vector3, Face.getCPtr(face), face);
    }

    public void appendNode(Vector3 vector3, float f) {
        SoftbodyJNI.btSoftBody_appendNode(this.swigCPtr, this, vector3, f);
    }

    public void appendLink(int i, Material material) {
        SoftbodyJNI.btSoftBody_appendLink__SWIG_0(this.swigCPtr, this, i, Material.getCPtr(material), material);
    }

    public void appendLink(int i) {
        SoftbodyJNI.btSoftBody_appendLink__SWIG_1(this.swigCPtr, this, i);
    }

    public void appendLink() {
        SoftbodyJNI.btSoftBody_appendLink__SWIG_2(this.swigCPtr, this);
    }

    public void appendLink(int i, int i2, Material material, boolean z) {
        SoftbodyJNI.btSoftBody_appendLink__SWIG_3(this.swigCPtr, this, i, i2, Material.getCPtr(material), material, z);
    }

    public void appendLink(int i, int i2, Material material) {
        SoftbodyJNI.btSoftBody_appendLink__SWIG_4(this.swigCPtr, this, i, i2, Material.getCPtr(material), material);
    }

    public void appendLink(int i, int i2) {
        SoftbodyJNI.btSoftBody_appendLink__SWIG_5(this.swigCPtr, this, i, i2);
    }

    public void appendLink(Node node, Node node2, Material material, boolean z) {
        SoftbodyJNI.btSoftBody_appendLink__SWIG_6(this.swigCPtr, this, Node.getCPtr(node), node, Node.getCPtr(node2), node2, Material.getCPtr(material), material, z);
    }

    public void appendLink(Node node, Node node2, Material material) {
        SoftbodyJNI.btSoftBody_appendLink__SWIG_7(this.swigCPtr, this, Node.getCPtr(node), node, Node.getCPtr(node2), node2, Material.getCPtr(material), material);
    }

    public void appendLink(Node node, Node node2) {
        SoftbodyJNI.btSoftBody_appendLink__SWIG_8(this.swigCPtr, this, Node.getCPtr(node), node, Node.getCPtr(node2), node2);
    }

    public void appendFace(int i, Material material) {
        SoftbodyJNI.btSoftBody_appendFace__SWIG_0(this.swigCPtr, this, i, Material.getCPtr(material), material);
    }

    public void appendFace(int i) {
        SoftbodyJNI.btSoftBody_appendFace__SWIG_1(this.swigCPtr, this, i);
    }

    public void appendFace() {
        SoftbodyJNI.btSoftBody_appendFace__SWIG_2(this.swigCPtr, this);
    }

    public void appendFace(int i, int i2, int i3, Material material) {
        SoftbodyJNI.btSoftBody_appendFace__SWIG_3(this.swigCPtr, this, i, i2, i3, Material.getCPtr(material), material);
    }

    public void appendFace(int i, int i2, int i3) {
        SoftbodyJNI.btSoftBody_appendFace__SWIG_4(this.swigCPtr, this, i, i2, i3);
    }

    public void appendTetra(int i, Material material) {
        SoftbodyJNI.btSoftBody_appendTetra__SWIG_0(this.swigCPtr, this, i, Material.getCPtr(material), material);
    }

    public void appendTetra(int i, int i2, int i3, int i4, Material material) {
        SoftbodyJNI.btSoftBody_appendTetra__SWIG_1(this.swigCPtr, this, i, i2, i3, i4, Material.getCPtr(material), material);
    }

    public void appendTetra(int i, int i2, int i3, int i4) {
        SoftbodyJNI.btSoftBody_appendTetra__SWIG_2(this.swigCPtr, this, i, i2, i3, i4);
    }

    public void appendAnchor(int i, btRigidBody btrigidbody, boolean z, float f) {
        SoftbodyJNI.btSoftBody_appendAnchor__SWIG_0(this.swigCPtr, this, i, btRigidBody.getCPtr(btrigidbody), btrigidbody, z, f);
    }

    public void appendAnchor(int i, btRigidBody btrigidbody, boolean z) {
        SoftbodyJNI.btSoftBody_appendAnchor__SWIG_1(this.swigCPtr, this, i, btRigidBody.getCPtr(btrigidbody), btrigidbody, z);
    }

    public void appendAnchor(int i, btRigidBody btrigidbody) {
        SoftbodyJNI.btSoftBody_appendAnchor__SWIG_2(this.swigCPtr, this, i, btRigidBody.getCPtr(btrigidbody), btrigidbody);
    }

    public void appendAnchor(int i, btRigidBody btrigidbody, Vector3 vector3, boolean z, float f) {
        SoftbodyJNI.btSoftBody_appendAnchor__SWIG_3(this.swigCPtr, this, i, btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3, z, f);
    }

    public void appendAnchor(int i, btRigidBody btrigidbody, Vector3 vector3, boolean z) {
        SoftbodyJNI.btSoftBody_appendAnchor__SWIG_4(this.swigCPtr, this, i, btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3, z);
    }

    public void appendAnchor(int i, btRigidBody btrigidbody, Vector3 vector3) {
        SoftbodyJNI.btSoftBody_appendAnchor__SWIG_5(this.swigCPtr, this, i, btRigidBody.getCPtr(btrigidbody), btrigidbody, vector3);
    }

    public void appendLinearJoint(LJoint.Specs specs, Cluster cluster, Body body) {
        SoftbodyJNI.btSoftBody_appendLinearJoint__SWIG_0(this.swigCPtr, this, LJoint.Specs.getCPtr(specs), specs, Cluster.getCPtr(cluster), cluster, Body.getCPtr(body), body);
    }

    public void appendLinearJoint(LJoint.Specs specs, Body body) {
        SoftbodyJNI.btSoftBody_appendLinearJoint__SWIG_1(this.swigCPtr, this, LJoint.Specs.getCPtr(specs), specs, Body.getCPtr(body), body);
    }

    public void appendLinearJoint(LJoint.Specs specs) {
        SoftbodyJNI.btSoftBody_appendLinearJoint__SWIG_2(this.swigCPtr, this, LJoint.Specs.getCPtr(specs), specs);
    }

    public void appendLinearJoint(LJoint.Specs specs, btSoftBody btsoftbody) {
        SoftbodyJNI.btSoftBody_appendLinearJoint__SWIG_3(this.swigCPtr, this, LJoint.Specs.getCPtr(specs), specs, getCPtr(btsoftbody), btsoftbody);
    }

    public void appendAngularJoint(AJoint.Specs specs, Cluster cluster, Body body) {
        SoftbodyJNI.btSoftBody_appendAngularJoint__SWIG_0(this.swigCPtr, this, AJoint.Specs.getCPtr(specs), specs, Cluster.getCPtr(cluster), cluster, Body.getCPtr(body), body);
    }

    public void appendAngularJoint(AJoint.Specs specs, Body body) {
        SoftbodyJNI.btSoftBody_appendAngularJoint__SWIG_1(this.swigCPtr, this, AJoint.Specs.getCPtr(specs), specs, Body.getCPtr(body), body);
    }

    public void appendAngularJoint(AJoint.Specs specs) {
        SoftbodyJNI.btSoftBody_appendAngularJoint__SWIG_2(this.swigCPtr, this, AJoint.Specs.getCPtr(specs), specs);
    }

    public void appendAngularJoint(AJoint.Specs specs, btSoftBody btsoftbody) {
        SoftbodyJNI.btSoftBody_appendAngularJoint__SWIG_3(this.swigCPtr, this, AJoint.Specs.getCPtr(specs), specs, getCPtr(btsoftbody), btsoftbody);
    }

    public void addForce(Vector3 vector3) {
        SoftbodyJNI.btSoftBody_addForce__SWIG_0(this.swigCPtr, this, vector3);
    }

    public void addForce(Vector3 vector3, int i) {
        SoftbodyJNI.btSoftBody_addForce__SWIG_1(this.swigCPtr, this, vector3, i);
    }

    public void addAeroForceToNode(Vector3 vector3, int i) {
        SoftbodyJNI.btSoftBody_addAeroForceToNode(this.swigCPtr, this, vector3, i);
    }

    public void addAeroForceToFace(Vector3 vector3, int i) {
        SoftbodyJNI.btSoftBody_addAeroForceToFace(this.swigCPtr, this, vector3, i);
    }

    public void addVelocity(Vector3 vector3) {
        SoftbodyJNI.btSoftBody_addVelocity__SWIG_0(this.swigCPtr, this, vector3);
    }

    public void setVelocity(Vector3 vector3) {
        SoftbodyJNI.btSoftBody_setVelocity(this.swigCPtr, this, vector3);
    }

    public void addVelocity(Vector3 vector3, int i) {
        SoftbodyJNI.btSoftBody_addVelocity__SWIG_1(this.swigCPtr, this, vector3, i);
    }

    public void setMass(int i, float f) {
        SoftbodyJNI.btSoftBody_setMass(this.swigCPtr, this, i, f);
    }

    public float getMass(int i) {
        return SoftbodyJNI.btSoftBody_getMass(this.swigCPtr, this, i);
    }

    public float getTotalMass() {
        return SoftbodyJNI.btSoftBody_getTotalMass(this.swigCPtr, this);
    }

    public void setTotalMass(float f, boolean z) {
        SoftbodyJNI.btSoftBody_setTotalMass__SWIG_0(this.swigCPtr, this, f, z);
    }

    public void setTotalMass(float f) {
        SoftbodyJNI.btSoftBody_setTotalMass__SWIG_1(this.swigCPtr, this, f);
    }

    public void setTotalDensity(float f) {
        SoftbodyJNI.btSoftBody_setTotalDensity(this.swigCPtr, this, f);
    }

    public void setVolumeMass(float f) {
        SoftbodyJNI.btSoftBody_setVolumeMass(this.swigCPtr, this, f);
    }

    public void setVolumeDensity(float f) {
        SoftbodyJNI.btSoftBody_setVolumeDensity(this.swigCPtr, this, f);
    }

    public void transform(Matrix4 matrix4) {
        SoftbodyJNI.btSoftBody_transform(this.swigCPtr, this, matrix4);
    }

    public void translate(Vector3 vector3) {
        SoftbodyJNI.btSoftBody_translate(this.swigCPtr, this, vector3);
    }

    public void rotate(Quaternion quaternion) {
        SoftbodyJNI.btSoftBody_rotate(this.swigCPtr, this, quaternion);
    }

    public void scale(Vector3 vector3) {
        SoftbodyJNI.btSoftBody_scale(this.swigCPtr, this, vector3);
    }

    public void setPose(boolean z, boolean z2) {
        SoftbodyJNI.btSoftBody_setPose(this.swigCPtr, this, z, z2);
    }

    public void resetLinkRestLengths() {
        SoftbodyJNI.btSoftBody_resetLinkRestLengths(this.swigCPtr, this);
    }

    public float getVolume() {
        return SoftbodyJNI.btSoftBody_getVolume(this.swigCPtr, this);
    }

    public int clusterCount() {
        return SoftbodyJNI.btSoftBody_clusterCount(this.swigCPtr, this);
    }

    public static Vector3 clusterCom(Cluster cluster) {
        return SoftbodyJNI.btSoftBody_clusterCom__SWIG_0(Cluster.getCPtr(cluster), cluster);
    }

    public Vector3 clusterCom(int i) {
        return SoftbodyJNI.btSoftBody_clusterCom__SWIG_1(this.swigCPtr, this, i);
    }

    public static Vector3 clusterVelocity(Cluster cluster, Vector3 vector3) {
        return SoftbodyJNI.btSoftBody_clusterVelocity(Cluster.getCPtr(cluster), cluster, vector3);
    }

    public static void clusterVImpulse(Cluster cluster, Vector3 vector3, Vector3 vector4) {
        SoftbodyJNI.btSoftBody_clusterVImpulse(Cluster.getCPtr(cluster), cluster, vector3, vector4);
    }

    public static void clusterDImpulse(Cluster cluster, Vector3 vector3, Vector3 vector4) {
        SoftbodyJNI.btSoftBody_clusterDImpulse(Cluster.getCPtr(cluster), cluster, vector3, vector4);
    }

    public static void clusterImpulse(Cluster cluster, Vector3 vector3, Impulse impulse) {
        SoftbodyJNI.btSoftBody_clusterImpulse(Cluster.getCPtr(cluster), cluster, vector3, Impulse.getCPtr(impulse), impulse);
    }

    public static void clusterVAImpulse(Cluster cluster, Vector3 vector3) {
        SoftbodyJNI.btSoftBody_clusterVAImpulse(Cluster.getCPtr(cluster), cluster, vector3);
    }

    public static void clusterDAImpulse(Cluster cluster, Vector3 vector3) {
        SoftbodyJNI.btSoftBody_clusterDAImpulse(Cluster.getCPtr(cluster), cluster, vector3);
    }

    public static void clusterAImpulse(Cluster cluster, Impulse impulse) {
        SoftbodyJNI.btSoftBody_clusterAImpulse(Cluster.getCPtr(cluster), cluster, Impulse.getCPtr(impulse), impulse);
    }

    public static void clusterDCImpulse(Cluster cluster, Vector3 vector3) {
        SoftbodyJNI.btSoftBody_clusterDCImpulse(Cluster.getCPtr(cluster), cluster, vector3);
    }

    public int generateBendingConstraints(int i, Material material) {
        return SoftbodyJNI.btSoftBody_generateBendingConstraints__SWIG_0(this.swigCPtr, this, i, Material.getCPtr(material), material);
    }

    public int generateBendingConstraints(int i) {
        return SoftbodyJNI.btSoftBody_generateBendingConstraints__SWIG_1(this.swigCPtr, this, i);
    }

    public void randomizeConstraints() {
        SoftbodyJNI.btSoftBody_randomizeConstraints(this.swigCPtr, this);
    }

    public void releaseCluster(int i) {
        SoftbodyJNI.btSoftBody_releaseCluster(this.swigCPtr, this, i);
    }

    public void releaseClusters() {
        SoftbodyJNI.btSoftBody_releaseClusters(this.swigCPtr, this);
    }

    public int generateClusters(int i, int i2) {
        return SoftbodyJNI.btSoftBody_generateClusters__SWIG_0(this.swigCPtr, this, i, i2);
    }

    public int generateClusters(int i) {
        return SoftbodyJNI.btSoftBody_generateClusters__SWIG_1(this.swigCPtr, this, i);
    }

    public void refine(ImplicitFn implicitFn, float f, boolean z) {
        SoftbodyJNI.btSoftBody_refine(this.swigCPtr, this, ImplicitFn.getCPtr(implicitFn), implicitFn, f, z);
    }

    public boolean cutLink(int i, int i2, float f) {
        return SoftbodyJNI.btSoftBody_cutLink__SWIG_0(this.swigCPtr, this, i, i2, f);
    }

    public boolean cutLink(Node node, Node node2, float f) {
        return SoftbodyJNI.btSoftBody_cutLink__SWIG_1(this.swigCPtr, this, Node.getCPtr(node), node, Node.getCPtr(node2), node2, f);
    }

    public boolean rayTest(Vector3 vector3, Vector3 vector4, sRayCast sraycast) {
        return SoftbodyJNI.btSoftBody_rayTest__SWIG_0(this.swigCPtr, this, vector3, vector4, sRayCast.getCPtr(sraycast), sraycast);
    }

    public void setSolver(int i) {
        SoftbodyJNI.btSoftBody_setSolver(this.swigCPtr, this, i);
    }

    public void predictMotion(float f) {
        SoftbodyJNI.btSoftBody_predictMotion(this.swigCPtr, this, f);
    }

    public void solveConstraints() {
        SoftbodyJNI.btSoftBody_solveConstraints(this.swigCPtr, this);
    }

    public void staticSolve(int i) {
        SoftbodyJNI.btSoftBody_staticSolve(this.swigCPtr, this, i);
    }

    public static void solveCommonConstraints(SWIGTYPE_p_p_btSoftBody sWIGTYPE_p_p_btSoftBody, int i, int i2) {
        SoftbodyJNI.btSoftBody_solveCommonConstraints(SWIGTYPE_p_p_btSoftBody.getCPtr(sWIGTYPE_p_p_btSoftBody), i, i2);
    }

    public static void solveClusters(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t) {
        SoftbodyJNI.btSoftBody_solveClusters__SWIG_0(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t));
    }

    public void integrateMotion() {
        SoftbodyJNI.btSoftBody_integrateMotion(this.swigCPtr, this);
    }

    public void defaultCollisionHandler(btCollisionObjectWrapper btcollisionobjectwrapper) {
        SoftbodyJNI.btSoftBody_defaultCollisionHandler__SWIG_0(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper);
    }

    public void defaultCollisionHandler(btSoftBody btsoftbody) {
        SoftbodyJNI.btSoftBody_defaultCollisionHandler__SWIG_1(this.swigCPtr, this, getCPtr(btsoftbody), btsoftbody);
    }

    public void setWindVelocity(Vector3 vector3) {
        SoftbodyJNI.btSoftBody_setWindVelocity(this.swigCPtr, this, vector3);
    }

    public static btSoftBody upcastConstBtCollisionObject(btCollisionObject btcollisionobject) {
        long jBtSoftBody_upcastConstBtCollisionObject = SoftbodyJNI.btSoftBody_upcastConstBtCollisionObject(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
        if (jBtSoftBody_upcastConstBtCollisionObject == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBody_upcastConstBtCollisionObject, false);
    }

    public static btSoftBody upcast(btCollisionObject btcollisionobject) {
        long jBtSoftBody_upcast = SoftbodyJNI.btSoftBody_upcast(btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
        if (jBtSoftBody_upcast == 0) {
            return null;
        }
        return new btSoftBody(jBtSoftBody_upcast, false);
    }

    public void getAabb(Vector3 vector3, Vector3 vector4) {
        SoftbodyJNI.btSoftBody_getAabb(this.swigCPtr, this, vector3, vector4);
    }

    public void pointersToIndices() {
        SoftbodyJNI.btSoftBody_pointersToIndices(this.swigCPtr, this);
    }

    public void indicesToPointers(IntBuffer intBuffer) {
        SoftbodyJNI.btSoftBody_indicesToPointers__SWIG_0(this.swigCPtr, this, intBuffer);
    }

    public void indicesToPointers() {
        SoftbodyJNI.btSoftBody_indicesToPointers__SWIG_1(this.swigCPtr, this);
    }

    public int rayTest(Vector3 vector3, Vector3 vector4, SWIGTYPE_p_float sWIGTYPE_p_float, SWIGTYPE_p_btSoftBody__eFeature___ sWIGTYPE_p_btSoftBody__eFeature___, SWIGTYPE_p_int sWIGTYPE_p_int, boolean z) {
        return SoftbodyJNI.btSoftBody_rayTest__SWIG_1(this.swigCPtr, this, vector3, vector4, SWIGTYPE_p_float.getCPtr(sWIGTYPE_p_float), SWIGTYPE_p_btSoftBody__eFeature___.getCPtr(sWIGTYPE_p_btSoftBody__eFeature___), SWIGTYPE_p_int.getCPtr(sWIGTYPE_p_int), z);
    }

    public void initializeFaceTree() {
        SoftbodyJNI.btSoftBody_initializeFaceTree(this.swigCPtr, this);
    }

    public Vector3 evaluateCom() {
        return SoftbodyJNI.btSoftBody_evaluateCom(this.swigCPtr, this);
    }

    public boolean checkContact(btCollisionObjectWrapper btcollisionobjectwrapper, Vector3 vector3, float f, sCti scti) {
        return SoftbodyJNI.btSoftBody_checkContact(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper, vector3, f, sCti.getCPtr(scti), scti);
    }

    public void updateNormals() {
        SoftbodyJNI.btSoftBody_updateNormals(this.swigCPtr, this);
    }

    public void updateBounds() {
        SoftbodyJNI.btSoftBody_updateBounds(this.swigCPtr, this);
    }

    public void updatePose() {
        SoftbodyJNI.btSoftBody_updatePose(this.swigCPtr, this);
    }

    public void updateConstants() {
        SoftbodyJNI.btSoftBody_updateConstants(this.swigCPtr, this);
    }

    public void updateLinkConstants() {
        SoftbodyJNI.btSoftBody_updateLinkConstants(this.swigCPtr, this);
    }

    public void updateArea(boolean z) {
        SoftbodyJNI.btSoftBody_updateArea__SWIG_0(this.swigCPtr, this, z);
    }

    public void updateArea() {
        SoftbodyJNI.btSoftBody_updateArea__SWIG_1(this.swigCPtr, this);
    }

    public void initializeClusters() {
        SoftbodyJNI.btSoftBody_initializeClusters(this.swigCPtr, this);
    }

    public void updateClusters() {
        SoftbodyJNI.btSoftBody_updateClusters(this.swigCPtr, this);
    }

    public void cleanupClusters() {
        SoftbodyJNI.btSoftBody_cleanupClusters(this.swigCPtr, this);
    }

    public void prepareClusters(int i) {
        SoftbodyJNI.btSoftBody_prepareClusters(this.swigCPtr, this, i);
    }

    public void solveClusters(float f) {
        SoftbodyJNI.btSoftBody_solveClusters__SWIG_1(this.swigCPtr, this, f);
    }

    public void applyClusters(boolean z) {
        SoftbodyJNI.btSoftBody_applyClusters(this.swigCPtr, this, z);
    }

    public void dampClusters() {
        SoftbodyJNI.btSoftBody_dampClusters(this.swigCPtr, this);
    }

    public void applyForces() {
        SoftbodyJNI.btSoftBody_applyForces(this.swigCPtr, this);
    }

    public static void PSolve_Anchors(btSoftBody btsoftbody, float f, float f2) {
        SoftbodyJNI.btSoftBody_PSolve_Anchors(getCPtr(btsoftbody), btsoftbody, f, f2);
    }

    public static void PSolve_RContacts(btSoftBody btsoftbody, float f, float f2) {
        SoftbodyJNI.btSoftBody_PSolve_RContacts(getCPtr(btsoftbody), btsoftbody, f, f2);
    }

    public static void PSolve_SContacts(btSoftBody btsoftbody, float f, float f2) {
        SoftbodyJNI.btSoftBody_PSolve_SContacts(getCPtr(btsoftbody), btsoftbody, f, f2);
    }

    public static void PSolve_Links(btSoftBody btsoftbody, float f, float f2) {
        SoftbodyJNI.btSoftBody_PSolve_Links(getCPtr(btsoftbody), btsoftbody, f, f2);
    }

    public static void VSolve_Links(btSoftBody btsoftbody, float f) {
        SoftbodyJNI.btSoftBody_VSolve_Links(getCPtr(btsoftbody), btsoftbody, f);
    }

    public static SWIGTYPE_p_f_p_btSoftBody_float_float__void getSolver(int i) {
        long jBtSoftBody_getSolver__SWIG_0 = SoftbodyJNI.btSoftBody_getSolver__SWIG_0(i);
        if (jBtSoftBody_getSolver__SWIG_0 == 0) {
            return null;
        }
        return new SWIGTYPE_p_f_p_btSoftBody_float_float__void(jBtSoftBody_getSolver__SWIG_0, false);
    }

    private static long SwigConstructbtSoftBody(btSoftBodyWorldInfo btsoftbodyworldinfo, FloatBuffer floatBuffer, int i, int i2, int i3, ShortBuffer shortBuffer, int i4, int i5, ShortBuffer shortBuffer2, int i6) {
        return SoftbodyJNI.new_btSoftBody__SWIG_2(btSoftBodyWorldInfo.getCPtr(btsoftbodyworldinfo), btsoftbodyworldinfo, floatBuffer, i, i2, i3, shortBuffer, i4, i5, shortBuffer2, i6);
    }

    public btSoftBody(btSoftBodyWorldInfo btsoftbodyworldinfo, FloatBuffer floatBuffer, int i, int i2, int i3, ShortBuffer shortBuffer, int i4, int i5, ShortBuffer shortBuffer2, int i6) {
        this(SwigConstructbtSoftBody(btsoftbodyworldinfo, floatBuffer, i, i2, i3, shortBuffer, i4, i5, shortBuffer2, i6), true);
    }

    public int getNodeCount() {
        return SoftbodyJNI.btSoftBody_getNodeCount(this.swigCPtr, this);
    }

    public Node getNode(int i) {
        long jBtSoftBody_getNode = SoftbodyJNI.btSoftBody_getNode(this.swigCPtr, this, i);
        if (jBtSoftBody_getNode == 0) {
            return null;
        }
        return new Node(jBtSoftBody_getNode, false);
    }

    public int getLinkCount() {
        return SoftbodyJNI.btSoftBody_getLinkCount(this.swigCPtr, this);
    }

    public Link getLink(int i) {
        long jBtSoftBody_getLink = SoftbodyJNI.btSoftBody_getLink(this.swigCPtr, this, i);
        if (jBtSoftBody_getLink == 0) {
            return null;
        }
        return new Link(jBtSoftBody_getLink, false);
    }

    public void getVertices(FloatBuffer floatBuffer, int i, int i2, int i3) {
        SoftbodyJNI.btSoftBody_getVertices__SWIG_0(this.swigCPtr, this, floatBuffer, i, i2, i3);
    }

    public void getVertices(FloatBuffer floatBuffer, int i, int i2, ShortBuffer shortBuffer, int i3, int i4, ShortBuffer shortBuffer2, int i5) {
        SoftbodyJNI.btSoftBody_getVertices__SWIG_1(this.swigCPtr, this, floatBuffer, i, i2, shortBuffer, i3, i4, shortBuffer2, i5);
    }

    public void getVertices(FloatBuffer floatBuffer, int i, int i2, int i3, ShortBuffer shortBuffer, int i4, int i5, ShortBuffer shortBuffer2, int i6) {
        SoftbodyJNI.btSoftBody_getVertices__SWIG_2(this.swigCPtr, this, floatBuffer, i, i2, i3, shortBuffer, i4, i5, shortBuffer2, i6);
    }

    public int getFaceCount() {
        return SoftbodyJNI.btSoftBody_getFaceCount(this.swigCPtr, this);
    }

    public Face getFace(int i) {
        long jBtSoftBody_getFace = SoftbodyJNI.btSoftBody_getFace(this.swigCPtr, this, i);
        if (jBtSoftBody_getFace == 0) {
            return null;
        }
        return new Face(jBtSoftBody_getFace, false);
    }

    public void getIndices(ShortBuffer shortBuffer, int i) {
        SoftbodyJNI.btSoftBody_getIndices(this.swigCPtr, this, shortBuffer, i);
    }

    public void setConfig_kVCF(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kVCF(this.swigCPtr, this, f);
    }

    public void setConfig_kDP(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kDP(this.swigCPtr, this, f);
    }

    public void setConfig_kDG(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kDG(this.swigCPtr, this, f);
    }

    public void setConfig_kLF(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kLF(this.swigCPtr, this, f);
    }

    public void setConfig_kPR(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kPR(this.swigCPtr, this, f);
    }

    public void setConfig_kVC(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kVC(this.swigCPtr, this, f);
    }

    public void setConfig_kDF(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kDF(this.swigCPtr, this, f);
    }

    public void setConfig_kMT(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kMT(this.swigCPtr, this, f);
    }

    public void setConfig_kCHR(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kCHR(this.swigCPtr, this, f);
    }

    public void setConfig_kKHR(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kKHR(this.swigCPtr, this, f);
    }

    public void setConfig_kSHR(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kSHR(this.swigCPtr, this, f);
    }

    public void setConfig_kAHR(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kAHR(this.swigCPtr, this, f);
    }

    public void setConfig_kSRHR_CL(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kSRHR_CL(this.swigCPtr, this, f);
    }

    public void setConfig_kSKHR_CL(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kSKHR_CL(this.swigCPtr, this, f);
    }

    public void setConfig_kSSHR_CL(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kSSHR_CL(this.swigCPtr, this, f);
    }

    public void setConfig_kSR_SPLT_CL(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kSR_SPLT_CL(this.swigCPtr, this, f);
    }

    public void setConfig_kSK_SPLT_CL(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kSK_SPLT_CL(this.swigCPtr, this, f);
    }

    public void setConfig_kSS_SPLT_CL(float f) {
        SoftbodyJNI.btSoftBody_setConfig_kSS_SPLT_CL(this.swigCPtr, this, f);
    }

    public void setConfig_maxvolume(float f) {
        SoftbodyJNI.btSoftBody_setConfig_maxvolume(this.swigCPtr, this, f);
    }

    public void setConfig_timescale(float f) {
        SoftbodyJNI.btSoftBody_setConfig_timescale(this.swigCPtr, this, f);
    }

    public void setConfig_viterations(int i) {
        SoftbodyJNI.btSoftBody_setConfig_viterations(this.swigCPtr, this, i);
    }

    public void setConfig_piterations(int i) {
        SoftbodyJNI.btSoftBody_setConfig_piterations(this.swigCPtr, this, i);
    }

    public void setConfig_diterations(int i) {
        SoftbodyJNI.btSoftBody_setConfig_diterations(this.swigCPtr, this, i);
    }

    public void setConfig_citerations(int i) {
        SoftbodyJNI.btSoftBody_setConfig_citerations(this.swigCPtr, this, i);
    }

    public void setConfig_collisions(int i) {
        SoftbodyJNI.btSoftBody_setConfig_collisions(this.swigCPtr, this, i);
    }
}
