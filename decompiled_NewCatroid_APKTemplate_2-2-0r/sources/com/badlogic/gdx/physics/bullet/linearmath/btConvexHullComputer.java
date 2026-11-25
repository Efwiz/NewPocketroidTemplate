package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btConvexHullComputer extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btConvexHullComputer(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btConvexHullComputer(long j, boolean z) {
        this("btConvexHullComputer", j, z);
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

    public static long getCPtr(btConvexHullComputer btconvexhullcomputer) {
        if (btconvexhullcomputer == null) {
            return 0L;
        }
        return btconvexhullcomputer.swigCPtr;
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
                LinearMathJNI.delete_btConvexHullComputer(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
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
                    LinearMathJNI.delete_btConvexHullComputer_Edge(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public int getSourceVertex() {
            return LinearMathJNI.btConvexHullComputer_Edge_getSourceVertex(this.swigCPtr, this);
        }

        public int getTargetVertex() {
            return LinearMathJNI.btConvexHullComputer_Edge_getTargetVertex(this.swigCPtr, this);
        }

        public Edge getNextEdgeOfVertex() {
            long jBtConvexHullComputer_Edge_getNextEdgeOfVertex = LinearMathJNI.btConvexHullComputer_Edge_getNextEdgeOfVertex(this.swigCPtr, this);
            if (jBtConvexHullComputer_Edge_getNextEdgeOfVertex == 0) {
                return null;
            }
            return new Edge(jBtConvexHullComputer_Edge_getNextEdgeOfVertex, false);
        }

        public Edge getNextEdgeOfFace() {
            long jBtConvexHullComputer_Edge_getNextEdgeOfFace = LinearMathJNI.btConvexHullComputer_Edge_getNextEdgeOfFace(this.swigCPtr, this);
            if (jBtConvexHullComputer_Edge_getNextEdgeOfFace == 0) {
                return null;
            }
            return new Edge(jBtConvexHullComputer_Edge_getNextEdgeOfFace, false);
        }

        public Edge getReverseEdge() {
            long jBtConvexHullComputer_Edge_getReverseEdge = LinearMathJNI.btConvexHullComputer_Edge_getReverseEdge(this.swigCPtr, this);
            if (jBtConvexHullComputer_Edge_getReverseEdge == 0) {
                return null;
            }
            return new Edge(jBtConvexHullComputer_Edge_getReverseEdge, false);
        }

        public Edge() {
            this(LinearMathJNI.new_btConvexHullComputer_Edge(), true);
        }
    }

    public void setVertices(btVector3Array btvector3array) {
        LinearMathJNI.btConvexHullComputer_vertices_set(this.swigCPtr, this, btVector3Array.getCPtr(btvector3array), btvector3array);
    }

    public btVector3Array getVertices() {
        long jBtConvexHullComputer_vertices_get = LinearMathJNI.btConvexHullComputer_vertices_get(this.swigCPtr, this);
        if (jBtConvexHullComputer_vertices_get == 0) {
            return null;
        }
        return new btVector3Array(jBtConvexHullComputer_vertices_get, false);
    }

    public void setEdges(SWIGTYPE_p_btAlignedObjectArrayT_btConvexHullComputer__Edge_t sWIGTYPE_p_btAlignedObjectArrayT_btConvexHullComputer__Edge_t) {
        LinearMathJNI.btConvexHullComputer_edges_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btConvexHullComputer__Edge_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_btConvexHullComputer__Edge_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btConvexHullComputer__Edge_t getEdges() {
        long jBtConvexHullComputer_edges_get = LinearMathJNI.btConvexHullComputer_edges_get(this.swigCPtr, this);
        if (jBtConvexHullComputer_edges_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_btConvexHullComputer__Edge_t(jBtConvexHullComputer_edges_get, false);
    }

    public void setFaces(SWIGTYPE_p_btAlignedObjectArrayT_int_t sWIGTYPE_p_btAlignedObjectArrayT_int_t) {
        LinearMathJNI.btConvexHullComputer_faces_set(this.swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(sWIGTYPE_p_btAlignedObjectArrayT_int_t));
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_int_t getFaces() {
        long jBtConvexHullComputer_faces_get = LinearMathJNI.btConvexHullComputer_faces_get(this.swigCPtr, this);
        if (jBtConvexHullComputer_faces_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_btAlignedObjectArrayT_int_t(jBtConvexHullComputer_faces_get, false);
    }

    public float compute(FloatBuffer floatBuffer, int i, int i2, float f, float f2) {
        return LinearMathJNI.btConvexHullComputer_compute__SWIG_0(this.swigCPtr, this, floatBuffer, i, i2, f, f2);
    }

    public float compute(DoubleBuffer doubleBuffer, int i, int i2, float f, float f2) {
        return LinearMathJNI.btConvexHullComputer_compute__SWIG_1(this.swigCPtr, this, doubleBuffer, i, i2, f, f2);
    }

    public btConvexHullComputer() {
        this(LinearMathJNI.new_btConvexHullComputer(), true);
    }
}
