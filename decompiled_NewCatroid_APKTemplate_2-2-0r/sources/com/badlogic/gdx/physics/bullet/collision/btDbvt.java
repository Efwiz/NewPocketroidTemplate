package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btDbvt extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DOUBLE_STACKSIZE = 128;
    public static final int SIMPLE_STACKSIZE = 64;
    private long swigCPtr;

    protected btDbvt(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btDbvt(long j, boolean z) {
        this("btDbvt", j, z);
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

    public static long getCPtr(btDbvt btdbvt) {
        if (btdbvt == null) {
            return 0L;
        }
        return btdbvt.swigCPtr;
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
                CollisionJNI.delete_btDbvt(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class sStkNN extends BulletBase {
        private long swigCPtr;

        protected sStkNN(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public sStkNN(long j, boolean z) {
            this("sStkNN", j, z);
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

        public static long getCPtr(sStkNN sstknn) {
            if (sstknn == null) {
                return 0L;
            }
            return sstknn.swigCPtr;
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
                    CollisionJNI.delete_btDbvt_sStkNN(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setA(btDbvtNode btdbvtnode) {
            CollisionJNI.btDbvt_sStkNN_a_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }

        public btDbvtNode getA() {
            return btDbvtNode.internalTemp(CollisionJNI.btDbvt_sStkNN_a_get(this.swigCPtr, this), false);
        }

        public void setB(btDbvtNode btdbvtnode) {
            CollisionJNI.btDbvt_sStkNN_b_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }

        public btDbvtNode getB() {
            return btDbvtNode.internalTemp(CollisionJNI.btDbvt_sStkNN_b_get(this.swigCPtr, this), false);
        }

        public sStkNN() {
            this(CollisionJNI.new_btDbvt_sStkNN__SWIG_0(), true);
        }

        public sStkNN(btDbvtNode btdbvtnode, btDbvtNode btdbvtnode2) {
            this(CollisionJNI.new_btDbvt_sStkNN__SWIG_1(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtNode.getCPtr(btdbvtnode2), btdbvtnode2), true);
        }
    }

    public static class sStkNP extends BulletBase {
        private long swigCPtr;

        protected sStkNP(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public sStkNP(long j, boolean z) {
            this("sStkNP", j, z);
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

        public static long getCPtr(sStkNP sstknp) {
            if (sstknp == null) {
                return 0L;
            }
            return sstknp.swigCPtr;
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
                    CollisionJNI.delete_btDbvt_sStkNP(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setNode(btDbvtNode btdbvtnode) {
            CollisionJNI.btDbvt_sStkNP_node_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }

        public btDbvtNode getNode() {
            return btDbvtNode.internalTemp(CollisionJNI.btDbvt_sStkNP_node_get(this.swigCPtr, this), false);
        }

        public void setMask(int i) {
            CollisionJNI.btDbvt_sStkNP_mask_set(this.swigCPtr, this, i);
        }

        public int getMask() {
            return CollisionJNI.btDbvt_sStkNP_mask_get(this.swigCPtr, this);
        }

        public sStkNP(btDbvtNode btdbvtnode, long j) {
            this(CollisionJNI.new_btDbvt_sStkNP(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, j), true);
        }
    }

    public static class sStkNPS extends BulletBase {
        private long swigCPtr;

        protected sStkNPS(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public sStkNPS(long j, boolean z) {
            this("sStkNPS", j, z);
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

        public static long getCPtr(sStkNPS sstknps) {
            if (sstknps == null) {
                return 0L;
            }
            return sstknps.swigCPtr;
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
                    CollisionJNI.delete_btDbvt_sStkNPS(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setNode(btDbvtNode btdbvtnode) {
            CollisionJNI.btDbvt_sStkNPS_node_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }

        public btDbvtNode getNode() {
            return btDbvtNode.internalTemp(CollisionJNI.btDbvt_sStkNPS_node_get(this.swigCPtr, this), false);
        }

        public void setMask(int i) {
            CollisionJNI.btDbvt_sStkNPS_mask_set(this.swigCPtr, this, i);
        }

        public int getMask() {
            return CollisionJNI.btDbvt_sStkNPS_mask_get(this.swigCPtr, this);
        }

        public void setValue(float f) {
            CollisionJNI.btDbvt_sStkNPS_value_set(this.swigCPtr, this, f);
        }

        public float getValue() {
            return CollisionJNI.btDbvt_sStkNPS_value_get(this.swigCPtr, this);
        }

        public sStkNPS() {
            this(CollisionJNI.new_btDbvt_sStkNPS__SWIG_0(), true);
        }

        public sStkNPS(btDbvtNode btdbvtnode, long j, float f) {
            this(CollisionJNI.new_btDbvt_sStkNPS__SWIG_1(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, j, f), true);
        }
    }

    public static class sStkCLN extends BulletBase {
        private long swigCPtr;

        protected sStkCLN(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public sStkCLN(long j, boolean z) {
            this("sStkCLN", j, z);
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

        public static long getCPtr(sStkCLN sstkcln) {
            if (sstkcln == null) {
                return 0L;
            }
            return sstkcln.swigCPtr;
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
                    CollisionJNI.delete_btDbvt_sStkCLN(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setNode(btDbvtNode btdbvtnode) {
            CollisionJNI.btDbvt_sStkCLN_node_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }

        public btDbvtNode getNode() {
            return btDbvtNode.internalTemp(CollisionJNI.btDbvt_sStkCLN_node_get(this.swigCPtr, this), false);
        }

        public void setParent(btDbvtNode btdbvtnode) {
            CollisionJNI.btDbvt_sStkCLN_parent_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }

        public btDbvtNode getParent() {
            return btDbvtNode.internalTemp(CollisionJNI.btDbvt_sStkCLN_parent_get(this.swigCPtr, this), false);
        }

        public sStkCLN(btDbvtNode btdbvtnode, btDbvtNode btdbvtnode2) {
            this(CollisionJNI.new_btDbvt_sStkCLN(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtNode.getCPtr(btdbvtnode2), btdbvtnode2), true);
        }
    }

    public static class IWriter extends BulletBase {
        private long swigCPtr;

        protected IWriter(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public IWriter(long j, boolean z) {
            this("IWriter", j, z);
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

        public static long getCPtr(IWriter iWriter) {
            if (iWriter == null) {
                return 0L;
            }
            return iWriter.swigCPtr;
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
                    CollisionJNI.delete_btDbvt_IWriter(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void Prepare(btDbvtNode btdbvtnode, int i) {
            CollisionJNI.btDbvt_IWriter_Prepare(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, i);
        }

        public void WriteNode(btDbvtNode btdbvtnode, int i, int i2, int i3, int i4) {
            CollisionJNI.btDbvt_IWriter_WriteNode(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, i, i2, i3, i4);
        }

        public void WriteLeaf(btDbvtNode btdbvtnode, int i, int i2) {
            CollisionJNI.btDbvt_IWriter_WriteLeaf(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, i, i2);
        }
    }

    public static class IClone extends BulletBase {
        private long swigCPtr;

        protected IClone(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public IClone(long j, boolean z) {
            this("IClone", j, z);
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

        public static long getCPtr(IClone iClone) {
            if (iClone == null) {
                return 0L;
            }
            return iClone.swigCPtr;
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
                    CollisionJNI.delete_btDbvt_IClone(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void CloneLeaf(btDbvtNode btdbvtnode) {
            CollisionJNI.btDbvt_IClone_CloneLeaf(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
        }

        public IClone() {
            this(CollisionJNI.new_btDbvt_IClone(), true);
        }
    }

    public void setRoot(btDbvtNode btdbvtnode) {
        CollisionJNI.btDbvt_root_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
    }

    public btDbvtNode getRoot() {
        return btDbvtNode.internalTemp(CollisionJNI.btDbvt_root_get(this.swigCPtr, this), false);
    }

    public void setFree(btDbvtNode btdbvtnode) {
        CollisionJNI.btDbvt_free_set(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
    }

    public btDbvtNode getFree() {
        return btDbvtNode.internalTemp(CollisionJNI.btDbvt_free_get(this.swigCPtr, this), false);
    }

    public void setLkhd(int i) {
        CollisionJNI.btDbvt_lkhd_set(this.swigCPtr, this, i);
    }

    public int getLkhd() {
        return CollisionJNI.btDbvt_lkhd_get(this.swigCPtr, this);
    }

    public void setLeaves(int i) {
        CollisionJNI.btDbvt_leaves_set(this.swigCPtr, this, i);
    }

    public int getLeaves() {
        return CollisionJNI.btDbvt_leaves_get(this.swigCPtr, this);
    }

    public void setOpath(long j) {
        CollisionJNI.btDbvt_opath_set(this.swigCPtr, this, j);
    }

    public long getOpath() {
        return CollisionJNI.btDbvt_opath_get(this.swigCPtr, this);
    }

    public void setStkStack(SWIGTYPE_p_btAlignedObjectArrayT_btDbvt__sStkNN_t sWIGTYPE_p_btAlignedObjectArrayT_btDbvt__sStkNN_t) {
        CollisionJNI.btDbvt_stkStack_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btDbvt__sStkNN_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btDbvt__sStkNN_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btDbvt__sStkNN_t getStkStack() {
        long jBtDbvt_stkStack_get = CollisionJNI.btDbvt_stkStack_get(this.swigCPtr, this);
        if (jBtDbvt_stkStack_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btDbvt__sStkNN_t(jBtDbvt_stkStack_get, false);
    }

    public btDbvt() {
        this(CollisionJNI.new_btDbvt(), true);
    }

    public void clear() {
        CollisionJNI.btDbvt_clear(this.swigCPtr, this);
    }

    public boolean empty() {
        return CollisionJNI.btDbvt_empty(this.swigCPtr, this);
    }

    public void optimizeBottomUp() {
        CollisionJNI.btDbvt_optimizeBottomUp(this.swigCPtr, this);
    }

    public void optimizeTopDown(int i) {
        CollisionJNI.btDbvt_optimizeTopDown__SWIG_0(this.swigCPtr, this, i);
    }

    public void optimizeTopDown() {
        CollisionJNI.btDbvt_optimizeTopDown__SWIG_1(this.swigCPtr, this);
    }

    public void optimizeIncremental(int i) {
        CollisionJNI.btDbvt_optimizeIncremental(this.swigCPtr, this, i);
    }

    public btDbvtNode insert(btDbvtAabbMm btdbvtaabbmm, long j) {
        return btDbvtNode.internalTemp(CollisionJNI.btDbvt_insert(this.swigCPtr, this, btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, j), false);
    }

    public void update(btDbvtNode btdbvtnode, int i) {
        CollisionJNI.btDbvt_update__SWIG_0(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, i);
    }

    public void update(btDbvtNode btdbvtnode) {
        CollisionJNI.btDbvt_update__SWIG_1(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
    }

    public void update(btDbvtNode btdbvtnode, btDbvtAabbMm btdbvtaabbmm) {
        CollisionJNI.btDbvt_update__SWIG_2(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm);
    }

    public boolean update(btDbvtNode btdbvtnode, btDbvtAabbMm btdbvtaabbmm, Vector3 vector3, float f) {
        return CollisionJNI.btDbvt_update__SWIG_3(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, vector3, f);
    }

    public boolean update(btDbvtNode btdbvtnode, btDbvtAabbMm btdbvtaabbmm, Vector3 vector3) {
        return CollisionJNI.btDbvt_update__SWIG_4(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, vector3);
    }

    public boolean update(btDbvtNode btdbvtnode, btDbvtAabbMm btdbvtaabbmm, float f) {
        return CollisionJNI.btDbvt_update__SWIG_5(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, f);
    }

    public void remove(btDbvtNode btdbvtnode) {
        CollisionJNI.btDbvt_remove(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
    }

    public void write(IWriter iWriter) {
        CollisionJNI.btDbvt_write(this.swigCPtr, this, IWriter.getCPtr(iWriter), iWriter);
    }

    public void clone(btDbvt btdbvt, IClone iClone) {
        CollisionJNI.btDbvt_clone__SWIG_0(this.swigCPtr, this, getCPtr(btdbvt), btdbvt, IClone.getCPtr(iClone), iClone);
    }

    public void clone(btDbvt btdbvt) {
        CollisionJNI.btDbvt_clone__SWIG_1(this.swigCPtr, this, getCPtr(btdbvt), btdbvt);
    }

    public static int maxdepth(btDbvtNode btdbvtnode) {
        return CollisionJNI.btDbvt_maxdepth(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
    }

    public static int countLeaves(btDbvtNode btdbvtnode) {
        return CollisionJNI.btDbvt_countLeaves(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode);
    }

    public static void extractLeaves(btDbvtNode btdbvtnode, SWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t sWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t) {
        CollisionJNI.btDbvt_extractLeaves(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, SWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t));
    }

    public static void benchmark() {
        CollisionJNI.btDbvt_benchmark();
    }

    public static void enumNodes(btDbvtNode btdbvtnode, ICollide iCollide) {
        CollisionJNI.btDbvt_enumNodes(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, ICollide.getCPtr(iCollide), iCollide);
    }

    public static void enumLeaves(btDbvtNode btdbvtnode, ICollide iCollide) {
        CollisionJNI.btDbvt_enumLeaves(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, ICollide.getCPtr(iCollide), iCollide);
    }

    public void collideTT(btDbvtNode btdbvtnode, btDbvtNode btdbvtnode2, ICollide iCollide) {
        CollisionJNI.btDbvt_collideTT(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtNode.getCPtr(btdbvtnode2), btdbvtnode2, ICollide.getCPtr(iCollide), iCollide);
    }

    public void collideTTpersistentStack(btDbvtNode btdbvtnode, btDbvtNode btdbvtnode2, ICollide iCollide) {
        CollisionJNI.btDbvt_collideTTpersistentStack(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtNode.getCPtr(btdbvtnode2), btdbvtnode2, ICollide.getCPtr(iCollide), iCollide);
    }

    public void collideTV(btDbvtNode btdbvtnode, btDbvtAabbMm btdbvtaabbmm, ICollide iCollide) {
        CollisionJNI.btDbvt_collideTV(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, ICollide.getCPtr(iCollide), iCollide);
    }

    public void collideTVNoStackAlloc(btDbvtNode btdbvtnode, btDbvtAabbMm btdbvtaabbmm, SWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t sWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t, ICollide iCollide) {
        CollisionJNI.btDbvt_collideTVNoStackAlloc(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm, SWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t), ICollide.getCPtr(iCollide), iCollide);
    }

    public static void rayTest(btDbvtNode btdbvtnode, Vector3 vector3, Vector3 vector4, ICollide iCollide) {
        CollisionJNI.btDbvt_rayTest(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, vector3, vector4, ICollide.getCPtr(iCollide), iCollide);
    }

    public void rayTestInternal(btDbvtNode btdbvtnode, Vector3 vector3, Vector3 vector4, Vector3 vector5, long[] jArr, float f, Vector3 vector6, Vector3 vector7, SWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t sWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t, ICollide iCollide) {
        CollisionJNI.btDbvt_rayTestInternal(this.swigCPtr, this, btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, vector3, vector4, vector5, jArr, f, vector6, vector7, SWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btDbvtNode_const_p_t), ICollide.getCPtr(iCollide), iCollide);
    }

    public static void collideKDOP(btDbvtNode btdbvtnode, btVector3 btvector3, FloatBuffer floatBuffer, int i, ICollide iCollide) {
        CollisionJNI.btDbvt_collideKDOP__SWIG_0(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btVector3.getCPtr(btvector3), btvector3, floatBuffer, i, ICollide.getCPtr(iCollide), iCollide);
    }

    public static void collideOCL(btDbvtNode btdbvtnode, btVector3 btvector3, FloatBuffer floatBuffer, Vector3 vector3, int i, ICollide iCollide, boolean z) {
        CollisionJNI.btDbvt_collideOCL__SWIG_0(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btVector3.getCPtr(btvector3), btvector3, floatBuffer, vector3, i, ICollide.getCPtr(iCollide), iCollide, z);
    }

    public static void collideOCL(btDbvtNode btdbvtnode, btVector3 btvector3, FloatBuffer floatBuffer, Vector3 vector3, int i, ICollide iCollide) {
        CollisionJNI.btDbvt_collideOCL__SWIG_1(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, btVector3.getCPtr(btvector3), btvector3, floatBuffer, vector3, i, ICollide.getCPtr(iCollide), iCollide);
    }

    public static void collideTU(btDbvtNode btdbvtnode, ICollide iCollide) {
        CollisionJNI.btDbvt_collideTU(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, ICollide.getCPtr(iCollide), iCollide);
    }

    public static int nearest(IntBuffer intBuffer, sStkNPS sstknps, float f, int i, int i2) {
        return CollisionJNI.btDbvt_nearest(intBuffer, sStkNPS.getCPtr(sstknps), sstknps, f, i, i2);
    }

    public static int allocate(SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t, SWIGTYPE_p_btAlignedObjectArrayT_btDbvt__sStkNPS_t sWIGTYPE_p_btAlignedObjectArrayT_btDbvt__sStkNPS_t, sStkNPS sstknps) {
        return CollisionJNI.btDbvt_allocate(SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t), SWIGTYPE_p_btAlignedObjectArrayT_btDbvt__sStkNPS_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btDbvt__sStkNPS_t), sStkNPS.getCPtr(sstknps), sstknps);
    }

    public static void collideKDOP(btDbvtNode btdbvtnode, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, int i, ICollide iCollide) {
        CollisionJNI.btDbvt_collideKDOP__SWIG_1(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, floatBuffer, floatBuffer2, i, ICollide.getCPtr(iCollide), iCollide);
    }

    public static void collideOCL(btDbvtNode btdbvtnode, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, Vector3 vector3, int i, ICollide iCollide, boolean z) {
        CollisionJNI.btDbvt_collideOCL__SWIG_2(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, floatBuffer, floatBuffer2, vector3, i, ICollide.getCPtr(iCollide), iCollide, z);
    }

    public static void collideOCL(btDbvtNode btdbvtnode, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, Vector3 vector3, int i, ICollide iCollide) {
        CollisionJNI.btDbvt_collideOCL__SWIG_3(btDbvtNode.getCPtr(btdbvtnode), btdbvtnode, floatBuffer, floatBuffer2, vector3, i, ICollide.getCPtr(iCollide), iCollide);
    }
}
