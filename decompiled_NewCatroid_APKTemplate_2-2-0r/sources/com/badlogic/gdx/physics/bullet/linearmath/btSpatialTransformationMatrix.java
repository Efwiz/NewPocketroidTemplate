package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btSpatialTransformationMatrix extends BulletBase {
    private long swigCPtr;

    public static final class eOutputOperation {
        public static final int Add = 1;
        public static final int None = 0;
        public static final int Subtract = 2;
    }

    protected btSpatialTransformationMatrix(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSpatialTransformationMatrix(long j, boolean z) {
        this("btSpatialTransformationMatrix", j, z);
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

    public static long getCPtr(btSpatialTransformationMatrix btspatialtransformationmatrix) {
        if (btspatialtransformationmatrix == null) {
            return 0L;
        }
        return btspatialtransformationmatrix.swigCPtr;
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
                LinearMathJNI.delete_btSpatialTransformationMatrix(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setRotMat(btMatrix3x3 btmatrix3x3) {
        LinearMathJNI.btSpatialTransformationMatrix_rotMat_set(this.swigCPtr, this, btMatrix3x3.getCPtr(btmatrix3x3), btmatrix3x3);
    }

    public btMatrix3x3 getRotMat() {
        long jBtSpatialTransformationMatrix_rotMat_get = LinearMathJNI.btSpatialTransformationMatrix_rotMat_get(this.swigCPtr, this);
        if (jBtSpatialTransformationMatrix_rotMat_get == 0) {
            return null;
        }
        return new btMatrix3x3(jBtSpatialTransformationMatrix_rotMat_get, false);
    }

    public void setTrnVec(btVector3 btvector3) {
        LinearMathJNI.btSpatialTransformationMatrix_trnVec_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
    }

    public btVector3 getTrnVec() {
        long jBtSpatialTransformationMatrix_trnVec_get = LinearMathJNI.btSpatialTransformationMatrix_trnVec_get(this.swigCPtr, this);
        if (jBtSpatialTransformationMatrix_trnVec_get == 0) {
            return null;
        }
        return new btVector3(jBtSpatialTransformationMatrix_trnVec_get, false);
    }

    public void transformInverse(btSymmetricSpatialDyad btsymmetricspatialdyad, btSymmetricSpatialDyad btsymmetricspatialdyad2, int i) {
        LinearMathJNI.btSpatialTransformationMatrix_transformInverse__SWIG_2(this.swigCPtr, this, btSymmetricSpatialDyad.getCPtr(btsymmetricspatialdyad), btsymmetricspatialdyad, btSymmetricSpatialDyad.getCPtr(btsymmetricspatialdyad2), btsymmetricspatialdyad2, i);
    }

    public void transformInverse(btSymmetricSpatialDyad btsymmetricspatialdyad, btSymmetricSpatialDyad btsymmetricspatialdyad2) {
        LinearMathJNI.btSpatialTransformationMatrix_transformInverse__SWIG_3(this.swigCPtr, this, btSymmetricSpatialDyad.getCPtr(btsymmetricspatialdyad), btsymmetricspatialdyad, btSymmetricSpatialDyad.getCPtr(btsymmetricspatialdyad2), btsymmetricspatialdyad2);
    }

    public btSpatialTransformationMatrix() {
        this(LinearMathJNI.new_btSpatialTransformationMatrix(), true);
    }
}
