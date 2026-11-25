package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btMaterialProperties extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btMaterialProperties(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btMaterialProperties(long j, boolean z) {
        this("btMaterialProperties", j, z);
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

    public static long getCPtr(btMaterialProperties btmaterialproperties) {
        if (btmaterialproperties == null) {
            return 0L;
        }
        return btmaterialproperties.swigCPtr;
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
                CollisionJNI.delete_btMaterialProperties(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setNumMaterials(int i) {
        CollisionJNI.btMaterialProperties_numMaterials_set(this.swigCPtr, this, i);
    }

    public int getNumMaterials() {
        return CollisionJNI.btMaterialProperties_numMaterials_get(this.swigCPtr, this);
    }

    public void setMaterialBase(ByteBuffer byteBuffer) {
        CollisionJNI.btMaterialProperties_materialBase_set(this.swigCPtr, this, byteBuffer);
    }

    public ByteBuffer getMaterialBase() {
        return CollisionJNI.btMaterialProperties_materialBase_get(this.swigCPtr, this);
    }

    public void setMaterialStride(int i) {
        CollisionJNI.btMaterialProperties_materialStride_set(this.swigCPtr, this, i);
    }

    public int getMaterialStride() {
        return CollisionJNI.btMaterialProperties_materialStride_get(this.swigCPtr, this);
    }

    public void setMaterialType(int i) {
        CollisionJNI.btMaterialProperties_materialType_set(this.swigCPtr, this, i);
    }

    public int getMaterialType() {
        return CollisionJNI.btMaterialProperties_materialType_get(this.swigCPtr, this);
    }

    public void setNumTriangles(int i) {
        CollisionJNI.btMaterialProperties_numTriangles_set(this.swigCPtr, this, i);
    }

    public int getNumTriangles() {
        return CollisionJNI.btMaterialProperties_numTriangles_get(this.swigCPtr, this);
    }

    public void setTriangleMaterialsBase(ByteBuffer byteBuffer) {
        CollisionJNI.btMaterialProperties_triangleMaterialsBase_set(this.swigCPtr, this, byteBuffer);
    }

    public ByteBuffer getTriangleMaterialsBase() {
        return CollisionJNI.btMaterialProperties_triangleMaterialsBase_get(this.swigCPtr, this);
    }

    public void setTriangleMaterialStride(int i) {
        CollisionJNI.btMaterialProperties_triangleMaterialStride_set(this.swigCPtr, this, i);
    }

    public int getTriangleMaterialStride() {
        return CollisionJNI.btMaterialProperties_triangleMaterialStride_get(this.swigCPtr, this);
    }

    public void setTriangleType(int i) {
        CollisionJNI.btMaterialProperties_triangleType_set(this.swigCPtr, this, i);
    }

    public int getTriangleType() {
        return CollisionJNI.btMaterialProperties_triangleType_get(this.swigCPtr, this);
    }

    public btMaterialProperties() {
        this(CollisionJNI.new_btMaterialProperties(), true);
    }
}
