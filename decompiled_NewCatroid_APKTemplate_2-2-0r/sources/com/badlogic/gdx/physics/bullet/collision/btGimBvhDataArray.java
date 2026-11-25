package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;

/* JADX INFO: loaded from: classes8.dex */
public class btGimBvhDataArray extends BulletBase {
    private long swigCPtr;

    protected btGimBvhDataArray(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGimBvhDataArray(long j, boolean z) {
        this("btGimBvhDataArray", j, z);
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

    public static long getCPtr(btGimBvhDataArray btgimbvhdataarray) {
        if (btgimbvhdataarray == null) {
            return 0L;
        }
        return btgimbvhdataarray.swigCPtr;
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
                CollisionJNI.delete_btGimBvhDataArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGimBvhDataArray operatorAssignment(btGimBvhDataArray btgimbvhdataarray) {
        return new btGimBvhDataArray(CollisionJNI.btGimBvhDataArray_operatorAssignment(this.swigCPtr, this, getCPtr(btgimbvhdataarray), btgimbvhdataarray), false);
    }

    public btGimBvhDataArray() {
        this(CollisionJNI.new_btGimBvhDataArray__SWIG_0(), true);
    }

    public btGimBvhDataArray(btGimBvhDataArray btgimbvhdataarray) {
        this(CollisionJNI.new_btGimBvhDataArray__SWIG_1(getCPtr(btgimbvhdataarray), btgimbvhdataarray), true);
    }

    public int size() {
        return CollisionJNI.btGimBvhDataArray_size(this.swigCPtr, this);
    }

    public GIM_BVH_DATA atConst(int i) {
        return new GIM_BVH_DATA(CollisionJNI.btGimBvhDataArray_atConst(this.swigCPtr, this, i), false);
    }

    public GIM_BVH_DATA at(int i) {
        return new GIM_BVH_DATA(CollisionJNI.btGimBvhDataArray_at(this.swigCPtr, this, i), false);
    }

    public GIM_BVH_DATA operatorSubscriptConst(int i) {
        return new GIM_BVH_DATA(CollisionJNI.btGimBvhDataArray_operatorSubscriptConst(this.swigCPtr, this, i), false);
    }

    public GIM_BVH_DATA operatorSubscript(int i) {
        return new GIM_BVH_DATA(CollisionJNI.btGimBvhDataArray_operatorSubscript(this.swigCPtr, this, i), false);
    }

    public void clear() {
        CollisionJNI.btGimBvhDataArray_clear(this.swigCPtr, this);
    }

    public void pop_back() {
        CollisionJNI.btGimBvhDataArray_pop_back(this.swigCPtr, this);
    }

    public void resizeNoInitialize(int i) {
        CollisionJNI.btGimBvhDataArray_resizeNoInitialize(this.swigCPtr, this, i);
    }

    public void resize(int i, GIM_BVH_DATA gim_bvh_data) {
        CollisionJNI.btGimBvhDataArray_resize__SWIG_0(this.swigCPtr, this, i, GIM_BVH_DATA.getCPtr(gim_bvh_data), gim_bvh_data);
    }

    public void resize(int i) {
        CollisionJNI.btGimBvhDataArray_resize__SWIG_1(this.swigCPtr, this, i);
    }

    public GIM_BVH_DATA expandNonInitializing() {
        return new GIM_BVH_DATA(CollisionJNI.btGimBvhDataArray_expandNonInitializing(this.swigCPtr, this), false);
    }

    public GIM_BVH_DATA expand(GIM_BVH_DATA gim_bvh_data) {
        return new GIM_BVH_DATA(CollisionJNI.btGimBvhDataArray_expand__SWIG_0(this.swigCPtr, this, GIM_BVH_DATA.getCPtr(gim_bvh_data), gim_bvh_data), false);
    }

    public GIM_BVH_DATA expand() {
        return new GIM_BVH_DATA(CollisionJNI.btGimBvhDataArray_expand__SWIG_1(this.swigCPtr, this), false);
    }

    public void push_back(GIM_BVH_DATA gim_bvh_data) {
        CollisionJNI.btGimBvhDataArray_push_back(this.swigCPtr, this, GIM_BVH_DATA.getCPtr(gim_bvh_data), gim_bvh_data);
    }

    public int capacity() {
        return CollisionJNI.btGimBvhDataArray_capacity(this.swigCPtr, this);
    }

    public void reserve(int i) {
        CollisionJNI.btGimBvhDataArray_reserve(this.swigCPtr, this, i);
    }

    public static class less extends BulletBase {
        private long swigCPtr;

        protected less(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public less(long j, boolean z) {
            this(BuiltInOperatorNames.LESS, j, z);
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

        public static long getCPtr(less lessVar) {
            if (lessVar == null) {
                return 0L;
            }
            return lessVar.swigCPtr;
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
                    CollisionJNI.delete_btGimBvhDataArray_less(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public less() {
            this(CollisionJNI.new_btGimBvhDataArray_less(), true);
        }
    }

    public void swap(int i, int i2) {
        CollisionJNI.btGimBvhDataArray_swap(this.swigCPtr, this, i, i2);
    }

    public void removeAtIndex(int i) {
        CollisionJNI.btGimBvhDataArray_removeAtIndex(this.swigCPtr, this, i);
    }

    public void initializeFromBuffer(long j, int i, int i2) {
        CollisionJNI.btGimBvhDataArray_initializeFromBuffer(this.swigCPtr, this, j, i, i2);
    }

    public void copyFromArray(btGimBvhDataArray btgimbvhdataarray) {
        CollisionJNI.btGimBvhDataArray_copyFromArray(this.swigCPtr, this, getCPtr(btgimbvhdataarray), btgimbvhdataarray);
    }
}
