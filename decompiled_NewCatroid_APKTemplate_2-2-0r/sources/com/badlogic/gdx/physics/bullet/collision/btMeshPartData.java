package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3DoubleData;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3FloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btMeshPartData extends BulletBase {
    private long swigCPtr;

    protected btMeshPartData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMeshPartData(long j, boolean z) {
        this("btMeshPartData", j, z);
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

    public static long getCPtr(btMeshPartData btmeshpartdata) {
        if (btmeshpartdata == null) {
            return 0L;
        }
        return btmeshpartdata.swigCPtr;
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
                CollisionJNI.delete_btMeshPartData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setVertices3f(btVector3FloatData btvector3floatdata) {
        CollisionJNI.btMeshPartData_vertices3f_set(this.swigCPtr, this, btVector3FloatData.getCPtr(btvector3floatdata), btvector3floatdata);
    }

    public btVector3FloatData getVertices3f() {
        long jBtMeshPartData_vertices3f_get = CollisionJNI.btMeshPartData_vertices3f_get(this.swigCPtr, this);
        if (jBtMeshPartData_vertices3f_get == 0) {
            return null;
        }
        return new btVector3FloatData(jBtMeshPartData_vertices3f_get, false);
    }

    public void setVertices3d(btVector3DoubleData btvector3doubledata) {
        CollisionJNI.btMeshPartData_vertices3d_set(this.swigCPtr, this, btVector3DoubleData.getCPtr(btvector3doubledata), btvector3doubledata);
    }

    public btVector3DoubleData getVertices3d() {
        long jBtMeshPartData_vertices3d_get = CollisionJNI.btMeshPartData_vertices3d_get(this.swigCPtr, this);
        if (jBtMeshPartData_vertices3d_get == 0) {
            return null;
        }
        return new btVector3DoubleData(jBtMeshPartData_vertices3d_get, false);
    }

    public void setIndices32(btIntIndexData btintindexdata) {
        CollisionJNI.btMeshPartData_indices32_set(this.swigCPtr, this, btIntIndexData.getCPtr(btintindexdata), btintindexdata);
    }

    public btIntIndexData getIndices32() {
        long jBtMeshPartData_indices32_get = CollisionJNI.btMeshPartData_indices32_get(this.swigCPtr, this);
        if (jBtMeshPartData_indices32_get == 0) {
            return null;
        }
        return new btIntIndexData(jBtMeshPartData_indices32_get, false);
    }

    public void set3indices16(btShortIntIndexTripletData btshortintindextripletdata) {
        CollisionJNI.btMeshPartData_3indices16_set(this.swigCPtr, this, btShortIntIndexTripletData.getCPtr(btshortintindextripletdata), btshortintindextripletdata);
    }

    public btShortIntIndexTripletData get3indices16() {
        long jBtMeshPartData_3indices16_get = CollisionJNI.btMeshPartData_3indices16_get(this.swigCPtr, this);
        if (jBtMeshPartData_3indices16_get == 0) {
            return null;
        }
        return new btShortIntIndexTripletData(jBtMeshPartData_3indices16_get, false);
    }

    public void set3indices8(btCharIndexTripletData btcharindextripletdata) {
        CollisionJNI.btMeshPartData_3indices8_set(this.swigCPtr, this, btCharIndexTripletData.getCPtr(btcharindextripletdata), btcharindextripletdata);
    }

    public btCharIndexTripletData get3indices8() {
        long jBtMeshPartData_3indices8_get = CollisionJNI.btMeshPartData_3indices8_get(this.swigCPtr, this);
        if (jBtMeshPartData_3indices8_get == 0) {
            return null;
        }
        return new btCharIndexTripletData(jBtMeshPartData_3indices8_get, false);
    }

    public void setIndices16(btShortIntIndexData btshortintindexdata) {
        CollisionJNI.btMeshPartData_indices16_set(this.swigCPtr, this, btShortIntIndexData.getCPtr(btshortintindexdata), btshortintindexdata);
    }

    public btShortIntIndexData getIndices16() {
        long jBtMeshPartData_indices16_get = CollisionJNI.btMeshPartData_indices16_get(this.swigCPtr, this);
        if (jBtMeshPartData_indices16_get == 0) {
            return null;
        }
        return new btShortIntIndexData(jBtMeshPartData_indices16_get, false);
    }

    public void setNumTriangles(int i) {
        CollisionJNI.btMeshPartData_numTriangles_set(this.swigCPtr, this, i);
    }

    public int getNumTriangles() {
        return CollisionJNI.btMeshPartData_numTriangles_get(this.swigCPtr, this);
    }

    public void setNumVertices(int i) {
        CollisionJNI.btMeshPartData_numVertices_set(this.swigCPtr, this, i);
    }

    public int getNumVertices() {
        return CollisionJNI.btMeshPartData_numVertices_get(this.swigCPtr, this);
    }

    public btMeshPartData() {
        this(CollisionJNI.new_btMeshPartData(), true);
    }
}
