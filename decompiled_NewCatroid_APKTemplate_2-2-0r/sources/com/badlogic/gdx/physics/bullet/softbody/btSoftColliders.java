package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.ICollide;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btGjkEpaSolver2;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftColliders extends BulletBase {
    private long swigCPtr;

    protected btSoftColliders(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSoftColliders(long j, boolean z) {
        this("btSoftColliders", j, z);
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

    public static long getCPtr(btSoftColliders btsoftcolliders) {
        if (btsoftcolliders == null) {
            return 0L;
        }
        return btsoftcolliders.swigCPtr;
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
                SoftbodyJNI.delete_btSoftColliders(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class ClusterBase extends ICollide {
        private long swigCPtr;

        protected ClusterBase(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftColliders_ClusterBase_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public ClusterBase(long j, boolean z) {
            this("ClusterBase", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftColliders_ClusterBase_SWIGUpcast(j), z);
        }

        public static long getCPtr(ClusterBase clusterBase) {
            if (clusterBase == null) {
                return 0L;
            }
            return clusterBase.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftColliders_ClusterBase(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setErp(float f) {
            SoftbodyJNI.btSoftColliders_ClusterBase_erp_set(this.swigCPtr, this, f);
        }

        public float getErp() {
            return SoftbodyJNI.btSoftColliders_ClusterBase_erp_get(this.swigCPtr, this);
        }

        public void setIdt(float f) {
            SoftbodyJNI.btSoftColliders_ClusterBase_idt_set(this.swigCPtr, this, f);
        }

        public float getIdt() {
            return SoftbodyJNI.btSoftColliders_ClusterBase_idt_get(this.swigCPtr, this);
        }

        public void setMargin(float f) {
            SoftbodyJNI.btSoftColliders_ClusterBase_margin_set(this.swigCPtr, this, f);
        }

        public float getMargin() {
            return SoftbodyJNI.btSoftColliders_ClusterBase_margin_get(this.swigCPtr, this);
        }

        public void setFriction(float f) {
            SoftbodyJNI.btSoftColliders_ClusterBase_friction_set(this.swigCPtr, this, f);
        }

        public float getFriction() {
            return SoftbodyJNI.btSoftColliders_ClusterBase_friction_get(this.swigCPtr, this);
        }

        public void setThreshold(float f) {
            SoftbodyJNI.btSoftColliders_ClusterBase_threshold_set(this.swigCPtr, this, f);
        }

        public float getThreshold() {
            return SoftbodyJNI.btSoftColliders_ClusterBase_threshold_get(this.swigCPtr, this);
        }

        public ClusterBase() {
            this(SoftbodyJNI.new_btSoftColliders_ClusterBase(), true);
        }

        public boolean SolveContact(btGjkEpaSolver2.sResults sresults, btSoftBody.Body body, btSoftBody.Body body2, btSoftBody.CJoint cJoint) {
            return SoftbodyJNI.btSoftColliders_ClusterBase_SolveContact(this.swigCPtr, this, btGjkEpaSolver2.sResults.getCPtr(sresults), sresults, btSoftBody.Body.getCPtr(body), body, btSoftBody.Body.getCPtr(body2), body2, btSoftBody.CJoint.getCPtr(cJoint), cJoint);
        }
    }

    public static class CollideCL_RS extends ClusterBase {
        private long swigCPtr;

        protected CollideCL_RS(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftColliders_CollideCL_RS_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public CollideCL_RS(long j, boolean z) {
            this("CollideCL_RS", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftColliders.ClusterBase, com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftColliders_CollideCL_RS_SWIGUpcast(j), z);
        }

        public static long getCPtr(CollideCL_RS collideCL_RS) {
            if (collideCL_RS == null) {
                return 0L;
            }
            return collideCL_RS.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftColliders.ClusterBase, com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftColliders.ClusterBase, com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftColliders_CollideCL_RS(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setPsb(btSoftBody btsoftbody) {
            SoftbodyJNI.btSoftColliders_CollideCL_RS_psb_set(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody);
        }

        public btSoftBody getPsb() {
            long jBtSoftColliders_CollideCL_RS_psb_get = SoftbodyJNI.btSoftColliders_CollideCL_RS_psb_get(this.swigCPtr, this);
            if (jBtSoftColliders_CollideCL_RS_psb_get == 0) {
                return null;
            }
            return new btSoftBody(jBtSoftColliders_CollideCL_RS_psb_get, false);
        }

        public void setColObjWrap(btCollisionObjectWrapper btcollisionobjectwrapper) {
            SoftbodyJNI.btSoftColliders_CollideCL_RS_colObjWrap_set(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper);
        }

        public btCollisionObjectWrapper getColObjWrap() {
            return btCollisionObjectWrapper.internalTemp(SoftbodyJNI.btSoftColliders_CollideCL_RS_colObjWrap_get(this.swigCPtr, this), false);
        }

        public void ProcessColObj(btSoftBody btsoftbody, btCollisionObjectWrapper btcollisionobjectwrapper) {
            SoftbodyJNI.btSoftColliders_CollideCL_RS_ProcessColObj(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper);
        }

        public CollideCL_RS() {
            this(SoftbodyJNI.new_btSoftColliders_CollideCL_RS(), true);
        }
    }

    public static class CollideCL_SS extends ClusterBase {
        private long swigCPtr;

        protected CollideCL_SS(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftColliders_CollideCL_SS_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public CollideCL_SS(long j, boolean z) {
            this("CollideCL_SS", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftColliders.ClusterBase, com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftColliders_CollideCL_SS_SWIGUpcast(j), z);
        }

        public static long getCPtr(CollideCL_SS collideCL_SS) {
            if (collideCL_SS == null) {
                return 0L;
            }
            return collideCL_SS.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftColliders.ClusterBase, com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.softbody.btSoftColliders.ClusterBase, com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    SoftbodyJNI.delete_btSoftColliders_CollideCL_SS(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setBodies(SWIGTYPE_p_p_btSoftBody sWIGTYPE_p_p_btSoftBody) {
            SoftbodyJNI.btSoftColliders_CollideCL_SS_bodies_set(this.swigCPtr, this, SWIGTYPE_p_p_btSoftBody.getCPtr(sWIGTYPE_p_p_btSoftBody));
        }

        public SWIGTYPE_p_p_btSoftBody getBodies() {
            long jBtSoftColliders_CollideCL_SS_bodies_get = SoftbodyJNI.btSoftColliders_CollideCL_SS_bodies_get(this.swigCPtr, this);
            if (jBtSoftColliders_CollideCL_SS_bodies_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_p_btSoftBody(jBtSoftColliders_CollideCL_SS_bodies_get, false);
        }

        public void ProcessSoftSoft(btSoftBody btsoftbody, btSoftBody btsoftbody2) {
            SoftbodyJNI.btSoftColliders_CollideCL_SS_ProcessSoftSoft(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody, btSoftBody.getCPtr(btsoftbody2), btsoftbody2);
        }

        public CollideCL_SS() {
            this(SoftbodyJNI.new_btSoftColliders_CollideCL_SS(), true);
        }
    }

    public static class CollideSDF_RS extends ICollide {
        private long swigCPtr;

        protected CollideSDF_RS(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftColliders_CollideSDF_RS_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public CollideSDF_RS(long j, boolean z) {
            this("CollideSDF_RS", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftColliders_CollideSDF_RS_SWIGUpcast(j), z);
        }

        public static long getCPtr(CollideSDF_RS collideSDF_RS) {
            if (collideSDF_RS == null) {
                return 0L;
            }
            return collideSDF_RS.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftColliders_CollideSDF_RS(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void DoNode(btSoftBody.Node node) {
            SoftbodyJNI.btSoftColliders_CollideSDF_RS_DoNode(this.swigCPtr, this, btSoftBody.Node.getCPtr(node), node);
        }

        public void setPsb(btSoftBody btsoftbody) {
            SoftbodyJNI.btSoftColliders_CollideSDF_RS_psb_set(this.swigCPtr, this, btSoftBody.getCPtr(btsoftbody), btsoftbody);
        }

        public btSoftBody getPsb() {
            long jBtSoftColliders_CollideSDF_RS_psb_get = SoftbodyJNI.btSoftColliders_CollideSDF_RS_psb_get(this.swigCPtr, this);
            if (jBtSoftColliders_CollideSDF_RS_psb_get == 0) {
                return null;
            }
            return new btSoftBody(jBtSoftColliders_CollideSDF_RS_psb_get, false);
        }

        public void setColObj1Wrap(btCollisionObjectWrapper btcollisionobjectwrapper) {
            SoftbodyJNI.btSoftColliders_CollideSDF_RS_colObj1Wrap_set(this.swigCPtr, this, btCollisionObjectWrapper.getCPtr(btcollisionobjectwrapper), btcollisionobjectwrapper);
        }

        public btCollisionObjectWrapper getColObj1Wrap() {
            return btCollisionObjectWrapper.internalTemp(SoftbodyJNI.btSoftColliders_CollideSDF_RS_colObj1Wrap_get(this.swigCPtr, this), false);
        }

        public void setRigidBody(btRigidBody btrigidbody) {
            SoftbodyJNI.btSoftColliders_CollideSDF_RS_rigidBody_set(this.swigCPtr, this, btRigidBody.getCPtr(btrigidbody), btrigidbody);
        }

        public btRigidBody getRigidBody() {
            return btRigidBody.getInstance(SoftbodyJNI.btSoftColliders_CollideSDF_RS_rigidBody_get(this.swigCPtr, this), false);
        }

        public void setDynmargin(float f) {
            SoftbodyJNI.btSoftColliders_CollideSDF_RS_dynmargin_set(this.swigCPtr, this, f);
        }

        public float getDynmargin() {
            return SoftbodyJNI.btSoftColliders_CollideSDF_RS_dynmargin_get(this.swigCPtr, this);
        }

        public void setStamargin(float f) {
            SoftbodyJNI.btSoftColliders_CollideSDF_RS_stamargin_set(this.swigCPtr, this, f);
        }

        public float getStamargin() {
            return SoftbodyJNI.btSoftColliders_CollideSDF_RS_stamargin_get(this.swigCPtr, this);
        }

        public CollideSDF_RS() {
            this(SoftbodyJNI.new_btSoftColliders_CollideSDF_RS(), true);
        }
    }

    public static class CollideVF_SS extends ICollide {
        private long swigCPtr;

        protected CollideVF_SS(String str, long j, boolean z) {
            super(str, SoftbodyJNI.btSoftColliders_CollideVF_SS_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public CollideVF_SS(long j, boolean z) {
            this("CollideVF_SS", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.ICollide, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(SoftbodyJNI.btSoftColliders_CollideVF_SS_SWIGUpcast(j), z);
        }

        public static long getCPtr(CollideVF_SS collideVF_SS) {
            if (collideVF_SS == null) {
                return 0L;
            }
            return collideVF_SS.swigCPtr;
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
                    SoftbodyJNI.delete_btSoftColliders_CollideVF_SS(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setPsb(SWIGTYPE_p_p_btSoftBody sWIGTYPE_p_p_btSoftBody) {
            SoftbodyJNI.btSoftColliders_CollideVF_SS_psb_set(this.swigCPtr, this, SWIGTYPE_p_p_btSoftBody.getCPtr(sWIGTYPE_p_p_btSoftBody));
        }

        public SWIGTYPE_p_p_btSoftBody getPsb() {
            long jBtSoftColliders_CollideVF_SS_psb_get = SoftbodyJNI.btSoftColliders_CollideVF_SS_psb_get(this.swigCPtr, this);
            if (jBtSoftColliders_CollideVF_SS_psb_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_p_btSoftBody(jBtSoftColliders_CollideVF_SS_psb_get, false);
        }

        public void setMrg(float f) {
            SoftbodyJNI.btSoftColliders_CollideVF_SS_mrg_set(this.swigCPtr, this, f);
        }

        public float getMrg() {
            return SoftbodyJNI.btSoftColliders_CollideVF_SS_mrg_get(this.swigCPtr, this);
        }

        public CollideVF_SS() {
            this(SoftbodyJNI.new_btSoftColliders_CollideVF_SS(), true);
        }
    }

    public btSoftColliders() {
        this(SoftbodyJNI.new_btSoftColliders(), true);
    }
}
