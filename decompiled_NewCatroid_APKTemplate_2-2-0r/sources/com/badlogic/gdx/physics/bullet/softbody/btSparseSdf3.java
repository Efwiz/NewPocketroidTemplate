package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;

/* JADX INFO: loaded from: classes8.dex */
public class btSparseSdf3 extends BulletBase {
    private long swigCPtr;

    protected btSparseSdf3(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSparseSdf3(long j, boolean z) {
        this("btSparseSdf3", j, z);
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

    public static long getCPtr(btSparseSdf3 btsparsesdf3) {
        if (btsparsesdf3 == null) {
            return 0L;
        }
        return btsparsesdf3.swigCPtr;
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
                SoftbodyJNI.delete_btSparseSdf3(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class IntFrac extends BulletBase {
        private long swigCPtr;

        protected IntFrac(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public IntFrac(long j, boolean z) {
            this("IntFrac", j, z);
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

        public static long getCPtr(IntFrac intFrac) {
            if (intFrac == null) {
                return 0L;
            }
            return intFrac.swigCPtr;
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
                    SoftbodyJNI.delete_btSparseSdf3_IntFrac(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setB(int i) {
            SoftbodyJNI.btSparseSdf3_IntFrac_b_set(this.swigCPtr, this, i);
        }

        public int getB() {
            return SoftbodyJNI.btSparseSdf3_IntFrac_b_get(this.swigCPtr, this);
        }

        public void setI(int i) {
            SoftbodyJNI.btSparseSdf3_IntFrac_i_set(this.swigCPtr, this, i);
        }

        public int getI() {
            return SoftbodyJNI.btSparseSdf3_IntFrac_i_get(this.swigCPtr, this);
        }

        public void setF(float f) {
            SoftbodyJNI.btSparseSdf3_IntFrac_f_set(this.swigCPtr, this, f);
        }

        public float getF() {
            return SoftbodyJNI.btSparseSdf3_IntFrac_f_get(this.swigCPtr, this);
        }

        public IntFrac() {
            this(SoftbodyJNI.new_btSparseSdf3_IntFrac(), true);
        }
    }

    public static class Cell extends BulletBase {
        private long swigCPtr;

        protected Cell(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public Cell(long j, boolean z) {
            this("Cell", j, z);
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

        public static long getCPtr(Cell cell) {
            if (cell == null) {
                return 0L;
            }
            return cell.swigCPtr;
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
                    SoftbodyJNI.delete_btSparseSdf3_Cell(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setD(SWIGTYPE_p_a_3_1__a_3_1__float sWIGTYPE_p_a_3_1__a_3_1__float) {
            SoftbodyJNI.btSparseSdf3_Cell_d_set(this.swigCPtr, this, SWIGTYPE_p_a_3_1__a_3_1__float.getCPtr(sWIGTYPE_p_a_3_1__a_3_1__float));
        }

        public SWIGTYPE_p_a_3_1__a_3_1__float getD() {
            long jBtSparseSdf3_Cell_d_get = SoftbodyJNI.btSparseSdf3_Cell_d_get(this.swigCPtr, this);
            if (jBtSparseSdf3_Cell_d_get == 0) {
                return null;
            }
            return new SWIGTYPE_p_a_3_1__a_3_1__float(jBtSparseSdf3_Cell_d_get, false);
        }

        public void setC(int[] iArr) {
            SoftbodyJNI.btSparseSdf3_Cell_c_set(this.swigCPtr, this, iArr);
        }

        public int[] getC() {
            return SoftbodyJNI.btSparseSdf3_Cell_c_get(this.swigCPtr, this);
        }

        public void setPuid(int i) {
            SoftbodyJNI.btSparseSdf3_Cell_puid_set(this.swigCPtr, this, i);
        }

        public int getPuid() {
            return SoftbodyJNI.btSparseSdf3_Cell_puid_get(this.swigCPtr, this);
        }

        public void setHash(long j) {
            SoftbodyJNI.btSparseSdf3_Cell_hash_set(this.swigCPtr, this, j);
        }

        public long getHash() {
            return SoftbodyJNI.btSparseSdf3_Cell_hash_get(this.swigCPtr, this);
        }

        public void setPclient(btCollisionShape btcollisionshape) {
            SoftbodyJNI.btSparseSdf3_Cell_pclient_set(this.swigCPtr, this, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
        }

        public btCollisionShape getPclient() {
            long jBtSparseSdf3_Cell_pclient_get = SoftbodyJNI.btSparseSdf3_Cell_pclient_get(this.swigCPtr, this);
            if (jBtSparseSdf3_Cell_pclient_get == 0) {
                return null;
            }
            return btCollisionShape.newDerivedObject(jBtSparseSdf3_Cell_pclient_get, false);
        }

        public void setNext(Cell cell) {
            SoftbodyJNI.btSparseSdf3_Cell_next_set(this.swigCPtr, this, getCPtr(cell), cell);
        }

        public Cell getNext() {
            long jBtSparseSdf3_Cell_next_get = SoftbodyJNI.btSparseSdf3_Cell_next_get(this.swigCPtr, this);
            if (jBtSparseSdf3_Cell_next_get == 0) {
                return null;
            }
            return new Cell(jBtSparseSdf3_Cell_next_get, false);
        }

        public Cell() {
            this(SoftbodyJNI.new_btSparseSdf3_Cell(), true);
        }
    }

    public void setCells(SWIGTYPE_p_btAlignedObjectArrayT_btSparseSdfT_3_t__Cell_p_t sWIGTYPE_p_btAlignedObjectArrayT_btSparseSdfT_3_t__Cell_p_t) {
        SoftbodyJNI.btSparseSdf3_cells_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSparseSdfT_3_t__Cell_p_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btSparseSdfT_3_t__Cell_p_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btSparseSdfT_3_t__Cell_p_t getCells() {
        long jBtSparseSdf3_cells_get = SoftbodyJNI.btSparseSdf3_cells_get(this.swigCPtr, this);
        if (jBtSparseSdf3_cells_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btSparseSdfT_3_t__Cell_p_t(jBtSparseSdf3_cells_get, false);
    }

    public void setVoxelsz(float f) {
        SoftbodyJNI.btSparseSdf3_voxelsz_set(this.swigCPtr, this, f);
    }

    public float getVoxelsz() {
        return SoftbodyJNI.btSparseSdf3_voxelsz_get(this.swigCPtr, this);
    }

    public void setPuid(int i) {
        SoftbodyJNI.btSparseSdf3_puid_set(this.swigCPtr, this, i);
    }

    public int getPuid() {
        return SoftbodyJNI.btSparseSdf3_puid_get(this.swigCPtr, this);
    }

    public void setNcells(int i) {
        SoftbodyJNI.btSparseSdf3_ncells_set(this.swigCPtr, this, i);
    }

    public int getNcells() {
        return SoftbodyJNI.btSparseSdf3_ncells_get(this.swigCPtr, this);
    }

    public void setClampCells(int i) {
        SoftbodyJNI.btSparseSdf3_clampCells_set(this.swigCPtr, this, i);
    }

    public int getClampCells() {
        return SoftbodyJNI.btSparseSdf3_clampCells_get(this.swigCPtr, this);
    }

    public void setNprobes(int i) {
        SoftbodyJNI.btSparseSdf3_nprobes_set(this.swigCPtr, this, i);
    }

    public int getNprobes() {
        return SoftbodyJNI.btSparseSdf3_nprobes_get(this.swigCPtr, this);
    }

    public void setNqueries(int i) {
        SoftbodyJNI.btSparseSdf3_nqueries_set(this.swigCPtr, this, i);
    }

    public int getNqueries() {
        return SoftbodyJNI.btSparseSdf3_nqueries_get(this.swigCPtr, this);
    }

    public void Initialize(int i, int i2) {
        SoftbodyJNI.btSparseSdf3_Initialize__SWIG_0(this.swigCPtr, this, i, i2);
    }

    public void Initialize(int i) {
        SoftbodyJNI.btSparseSdf3_Initialize__SWIG_1(this.swigCPtr, this, i);
    }

    public void Initialize() {
        SoftbodyJNI.btSparseSdf3_Initialize__SWIG_2(this.swigCPtr, this);
    }

    public void Reset() {
        SoftbodyJNI.btSparseSdf3_Reset(this.swigCPtr, this);
    }

    public void GarbageCollect(int i) {
        SoftbodyJNI.btSparseSdf3_GarbageCollect__SWIG_0(this.swigCPtr, this, i);
    }

    public void GarbageCollect() {
        SoftbodyJNI.btSparseSdf3_GarbageCollect__SWIG_1(this.swigCPtr, this);
    }

    public int RemoveReferences(btCollisionShape btcollisionshape) {
        return SoftbodyJNI.btSparseSdf3_RemoveReferences(this.swigCPtr, this, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
    }

    public float Evaluate(Vector3 vector3, btCollisionShape btcollisionshape, Vector3 vector4, float f) {
        return SoftbodyJNI.btSparseSdf3_Evaluate(this.swigCPtr, this, vector3, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape, vector4, f);
    }

    public void BuildCell(Cell cell) {
        SoftbodyJNI.btSparseSdf3_BuildCell(this.swigCPtr, this, Cell.getCPtr(cell), cell);
    }

    public static float DistanceToShape(Vector3 vector3, btCollisionShape btcollisionshape) {
        return SoftbodyJNI.btSparseSdf3_DistanceToShape(vector3, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
    }

    public static IntFrac Decompose(float f) {
        return new IntFrac(SoftbodyJNI.btSparseSdf3_Decompose(f), true);
    }

    public static float Lerp(float f, float f2, float f3) {
        return SoftbodyJNI.btSparseSdf3_Lerp(f, f2, f3);
    }

    public static long Hash(int i, int i2, int i3, btCollisionShape btcollisionshape) {
        return SoftbodyJNI.btSparseSdf3_Hash(i, i2, i3, btCollisionShape.getCPtr(btcollisionshape), btcollisionshape);
    }

    public btSparseSdf3() {
        this(SoftbodyJNI.new_btSparseSdf3(), true);
    }
}
