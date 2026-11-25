package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btVector3;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btGImpactMeshShapePart extends btGImpactShapeInterface {
    private long swigCPtr;

    protected btGImpactMeshShapePart(String str, long j, boolean z) {
        super(str, CollisionJNI.btGImpactMeshShapePart_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btGImpactMeshShapePart(long j, boolean z) {
        this("btGImpactMeshShapePart", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGImpactShapeInterface, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(CollisionJNI.btGImpactMeshShapePart_SWIGUpcast(j), z);
    }

    public static long getCPtr(btGImpactMeshShapePart btgimpactmeshshapepart) {
        if (btgimpactmeshshapepart == null) {
            return 0L;
        }
        return btgimpactmeshshapepart.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGImpactShapeInterface, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.collision.btGImpactShapeInterface, com.badlogic.gdx.physics.bullet.collision.btConcaveShape, com.badlogic.gdx.physics.bullet.collision.btCollisionShape, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btGImpactMeshShapePart(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static class TrimeshPrimitiveManager extends btPrimitiveManagerBase {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private long swigCPtr;

        protected TrimeshPrimitiveManager(String str, long j, boolean z) {
            super(str, CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_SWIGUpcast(j), z);
            this.swigCPtr = j;
        }

        public TrimeshPrimitiveManager(long j, boolean z) {
            this("TrimeshPrimitiveManager", j, z);
            construct();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btPrimitiveManagerBase, com.badlogic.gdx.physics.bullet.BulletBase
        protected void reset(long j, boolean z) {
            if (!this.destroyed) {
                destroy();
            }
            this.swigCPtr = j;
            super.reset(CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_SWIGUpcast(j), z);
        }

        public static long getCPtr(TrimeshPrimitiveManager trimeshPrimitiveManager) {
            if (trimeshPrimitiveManager == null) {
                return 0L;
            }
            return trimeshPrimitiveManager.swigCPtr;
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btPrimitiveManagerBase, com.badlogic.gdx.physics.bullet.BulletBase
        protected void finalize() throws Throwable {
            if (!this.destroyed) {
                destroy();
            }
            super.finalize();
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.btPrimitiveManagerBase, com.badlogic.gdx.physics.bullet.BulletBase
        protected synchronized void delete() {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    CollisionJNI.delete_btGImpactMeshShapePart_TrimeshPrimitiveManager(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setMargin(float f) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_margin_set(this.swigCPtr, this, f);
        }

        public float getMargin() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_margin_get(this.swigCPtr, this);
        }

        public void setMeshInterface(btStridingMeshInterface btstridingmeshinterface) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_meshInterface_set(this.swigCPtr, this, btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface);
        }

        public btStridingMeshInterface getMeshInterface() {
            long jBtGImpactMeshShapePart_TrimeshPrimitiveManager_meshInterface_get = CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_meshInterface_get(this.swigCPtr, this);
            if (jBtGImpactMeshShapePart_TrimeshPrimitiveManager_meshInterface_get == 0) {
                return null;
            }
            return new btStridingMeshInterface(jBtGImpactMeshShapePart_TrimeshPrimitiveManager_meshInterface_get, false);
        }

        public void setScale(btVector3 btvector3) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_scale_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getScale() {
            long jBtGImpactMeshShapePart_TrimeshPrimitiveManager_scale_get = CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_scale_get(this.swigCPtr, this);
            if (jBtGImpactMeshShapePart_TrimeshPrimitiveManager_scale_get == 0) {
                return null;
            }
            return new btVector3(jBtGImpactMeshShapePart_TrimeshPrimitiveManager_scale_get, false);
        }

        public void setPart(int i) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_part_set(this.swigCPtr, this, i);
        }

        public int getPart() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_part_get(this.swigCPtr, this);
        }

        public void setLock_count(int i) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_lock_count_set(this.swigCPtr, this, i);
        }

        public int getLock_count() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_lock_count_get(this.swigCPtr, this);
        }

        public void setVertexbase(ByteBuffer byteBuffer) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_vertexbase_set(this.swigCPtr, this, byteBuffer);
        }

        public ByteBuffer getVertexbase() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_vertexbase_get(this.swigCPtr, this);
        }

        public void setNumverts(int i) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_numverts_set(this.swigCPtr, this, i);
        }

        public int getNumverts() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_numverts_get(this.swigCPtr, this);
        }

        public void setType(int i) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_type_set(this.swigCPtr, this, i);
        }

        public int getType() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_type_get(this.swigCPtr, this);
        }

        public void setStride(int i) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_stride_set(this.swigCPtr, this, i);
        }

        public int getStride() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_stride_get(this.swigCPtr, this);
        }

        public void setIndexbase(ByteBuffer byteBuffer) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_indexbase_set(this.swigCPtr, this, byteBuffer);
        }

        public ByteBuffer getIndexbase() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_indexbase_get(this.swigCPtr, this);
        }

        public void setIndexstride(int i) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_indexstride_set(this.swigCPtr, this, i);
        }

        public int getIndexstride() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_indexstride_get(this.swigCPtr, this);
        }

        public void setNumfaces(int i) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_numfaces_set(this.swigCPtr, this, i);
        }

        public int getNumfaces() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_numfaces_get(this.swigCPtr, this);
        }

        public void setIndicestype(int i) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_indicestype_set(this.swigCPtr, this, i);
        }

        public int getIndicestype() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_indicestype_get(this.swigCPtr, this);
        }

        public TrimeshPrimitiveManager() {
            this(CollisionJNI.new_btGImpactMeshShapePart_TrimeshPrimitiveManager__SWIG_0(), true);
        }

        public TrimeshPrimitiveManager(TrimeshPrimitiveManager trimeshPrimitiveManager) {
            this(CollisionJNI.new_btGImpactMeshShapePart_TrimeshPrimitiveManager__SWIG_1(getCPtr(trimeshPrimitiveManager), trimeshPrimitiveManager), true);
        }

        public TrimeshPrimitiveManager(btStridingMeshInterface btstridingmeshinterface, int i) {
            this(CollisionJNI.new_btGImpactMeshShapePart_TrimeshPrimitiveManager__SWIG_2(btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, i), true);
        }

        public void lock() {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_lock(this.swigCPtr, this);
        }

        public void unlock() {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_unlock(this.swigCPtr, this);
        }

        public int get_vertex_count() {
            return CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_get_vertex_count(this.swigCPtr, this);
        }

        public void get_indices(int i, SWIGTYPE_p_unsigned_int sWIGTYPE_p_unsigned_int, SWIGTYPE_p_unsigned_int sWIGTYPE_p_unsigned_int2, SWIGTYPE_p_unsigned_int sWIGTYPE_p_unsigned_int3) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_get_indices(this.swigCPtr, this, i, SWIGTYPE_p_unsigned_int.getCPtr(sWIGTYPE_p_unsigned_int), SWIGTYPE_p_unsigned_int.getCPtr(sWIGTYPE_p_unsigned_int2), SWIGTYPE_p_unsigned_int.getCPtr(sWIGTYPE_p_unsigned_int3));
        }

        public void get_vertex(long j, Vector3 vector3) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_get_vertex(this.swigCPtr, this, j, vector3);
        }

        public void get_bullet_triangle(int i, btTriangleShapeEx bttriangleshapeex) {
            CollisionJNI.btGImpactMeshShapePart_TrimeshPrimitiveManager_get_bullet_triangle(this.swigCPtr, this, i, btTriangleShapeEx.getCPtr(bttriangleshapeex), bttriangleshapeex);
        }
    }

    public btGImpactMeshShapePart() {
        this(CollisionJNI.new_btGImpactMeshShapePart__SWIG_0(), true);
    }

    public btGImpactMeshShapePart(btStridingMeshInterface btstridingmeshinterface, int i) {
        this(CollisionJNI.new_btGImpactMeshShapePart__SWIG_1(btStridingMeshInterface.getCPtr(btstridingmeshinterface), btstridingmeshinterface, i), true);
    }

    public TrimeshPrimitiveManager getTrimeshPrimitiveManager() {
        long jBtGImpactMeshShapePart_getTrimeshPrimitiveManager = CollisionJNI.btGImpactMeshShapePart_getTrimeshPrimitiveManager(this.swigCPtr, this);
        if (jBtGImpactMeshShapePart_getTrimeshPrimitiveManager == 0) {
            return null;
        }
        return new TrimeshPrimitiveManager(jBtGImpactMeshShapePart_getTrimeshPrimitiveManager, false);
    }

    public int getVertexCount() {
        return CollisionJNI.btGImpactMeshShapePart_getVertexCount(this.swigCPtr, this);
    }

    public void getVertex(int i, Vector3 vector3) {
        CollisionJNI.btGImpactMeshShapePart_getVertex(this.swigCPtr, this, i, vector3);
    }

    public int getPart() {
        return CollisionJNI.btGImpactMeshShapePart_getPart(this.swigCPtr, this);
    }
}
