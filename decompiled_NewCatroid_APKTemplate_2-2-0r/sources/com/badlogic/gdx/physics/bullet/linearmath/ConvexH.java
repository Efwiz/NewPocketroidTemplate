package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class ConvexH extends BulletBase {
    private long swigCPtr;

    protected ConvexH(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public ConvexH(long j, boolean z) {
        this("ConvexH", j, z);
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

    public static long getCPtr(ConvexH convexH) {
        if (convexH == null) {
            return 0L;
        }
        return convexH.swigCPtr;
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
                LinearMathJNI.delete_ConvexH(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class HalfEdge extends BulletBase {
        private long swigCPtr;

        protected HalfEdge(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public HalfEdge(long j, boolean z) {
            this("HalfEdge", j, z);
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

        public static long getCPtr(HalfEdge halfEdge) {
            if (halfEdge == null) {
                return 0L;
            }
            return halfEdge.swigCPtr;
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
                    LinearMathJNI.delete_ConvexH_HalfEdge(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setEa(short s) {
            LinearMathJNI.ConvexH_HalfEdge_ea_set(this.swigCPtr, this, s);
        }

        public short getEa() {
            return LinearMathJNI.ConvexH_HalfEdge_ea_get(this.swigCPtr, this);
        }

        public void setV(short s) {
            LinearMathJNI.ConvexH_HalfEdge_v_set(this.swigCPtr, this, s);
        }

        public short getV() {
            return LinearMathJNI.ConvexH_HalfEdge_v_get(this.swigCPtr, this);
        }

        public void setP(short s) {
            LinearMathJNI.ConvexH_HalfEdge_p_set(this.swigCPtr, this, s);
        }

        public short getP() {
            return LinearMathJNI.ConvexH_HalfEdge_p_get(this.swigCPtr, this);
        }

        public HalfEdge() {
            this(LinearMathJNI.new_ConvexH_HalfEdge__SWIG_0(), true);
        }

        public HalfEdge(short s, short s2, short s3) {
            this(LinearMathJNI.new_ConvexH_HalfEdge__SWIG_1(s, s2, s3), true);
        }
    }

    public ConvexH() {
        this(LinearMathJNI.new_ConvexH__SWIG_0(), true);
    }

    public void setVertices(btVector3Array btvector3array) {
        LinearMathJNI.ConvexH_vertices_set(this.swigCPtr, this, btVector3Array.getCPtr(btvector3array), btvector3array);
    }

    public btVector3Array getVertices() {
        long jConvexH_vertices_get = LinearMathJNI.ConvexH_vertices_get(this.swigCPtr, this);
        if (jConvexH_vertices_get == 0) {
            return null;
        }
        return new btVector3Array(jConvexH_vertices_get, false);
    }

    public void setEdges(SWIGTYPE_p_btAlignedObjectArrayT_ConvexH__HalfEdge_t sWIGTYPE_p_btAlignedObjectArrayT_ConvexH__HalfEdge_t) {
        LinearMathJNI.ConvexH_edges_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_ConvexH__HalfEdge_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_ConvexH__HalfEdge_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_ConvexH__HalfEdge_t getEdges() {
        long jConvexH_edges_get = LinearMathJNI.ConvexH_edges_get(this.swigCPtr, this);
        if (jConvexH_edges_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_ConvexH__HalfEdge_t(jConvexH_edges_get, false);
    }

    public void setFacets(SWIGTYPE_p_btAlignedObjectArrayT_btPlane_t sWIGTYPE_p_btAlignedObjectArrayT_btPlane_t) {
        LinearMathJNI.ConvexH_facets_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btPlane_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btPlane_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btPlane_t getFacets() {
        long jConvexH_facets_get = LinearMathJNI.ConvexH_facets_get(this.swigCPtr, this);
        if (jConvexH_facets_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btPlane_t(jConvexH_facets_get, false);
    }

    public ConvexH(int i, int i2, int i3) {
        this(LinearMathJNI.new_ConvexH__SWIG_1(i, i2, i3), true);
    }
}
