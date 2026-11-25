package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;

/* JADX INFO: loaded from: classes8.dex */
public class btGimQuantizedBvhNodeArray extends BulletBase {
    private long swigCPtr;

    protected btGimQuantizedBvhNodeArray(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGimQuantizedBvhNodeArray(long j, boolean z) {
        this("btGimQuantizedBvhNodeArray", j, z);
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

    public static long getCPtr(btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray) {
        if (btgimquantizedbvhnodearray == null) {
            return 0L;
        }
        return btgimquantizedbvhnodearray.swigCPtr;
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
                CollisionJNI.delete_btGimQuantizedBvhNodeArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGimQuantizedBvhNodeArray operatorAssignment(btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray) {
        return new btGimQuantizedBvhNodeArray(CollisionJNI.btGimQuantizedBvhNodeArray_operatorAssignment(this.swigCPtr, this, getCPtr(btgimquantizedbvhnodearray), btgimquantizedbvhnodearray), false);
    }

    public btGimQuantizedBvhNodeArray() {
        this(CollisionJNI.new_btGimQuantizedBvhNodeArray__SWIG_0(), true);
    }

    public btGimQuantizedBvhNodeArray(btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray) {
        this(CollisionJNI.new_btGimQuantizedBvhNodeArray__SWIG_1(getCPtr(btgimquantizedbvhnodearray), btgimquantizedbvhnodearray), true);
    }

    public int size() {
        return CollisionJNI.btGimQuantizedBvhNodeArray_size(this.swigCPtr, this);
    }

    public BT_QUANTIZED_BVH_NODE atConst(int i) {
        return new BT_QUANTIZED_BVH_NODE(CollisionJNI.btGimQuantizedBvhNodeArray_atConst(this.swigCPtr, this, i), false);
    }

    public BT_QUANTIZED_BVH_NODE at(int i) {
        return new BT_QUANTIZED_BVH_NODE(CollisionJNI.btGimQuantizedBvhNodeArray_at(this.swigCPtr, this, i), false);
    }

    public BT_QUANTIZED_BVH_NODE operatorSubscriptConst(int i) {
        return new BT_QUANTIZED_BVH_NODE(CollisionJNI.btGimQuantizedBvhNodeArray_operatorSubscriptConst(this.swigCPtr, this, i), false);
    }

    public BT_QUANTIZED_BVH_NODE operatorSubscript(int i) {
        return new BT_QUANTIZED_BVH_NODE(CollisionJNI.btGimQuantizedBvhNodeArray_operatorSubscript(this.swigCPtr, this, i), false);
    }

    public void clear() {
        CollisionJNI.btGimQuantizedBvhNodeArray_clear(this.swigCPtr, this);
    }

    public void pop_back() {
        CollisionJNI.btGimQuantizedBvhNodeArray_pop_back(this.swigCPtr, this);
    }

    public void resizeNoInitialize(int i) {
        CollisionJNI.btGimQuantizedBvhNodeArray_resizeNoInitialize(this.swigCPtr, this, i);
    }

    public void resize(int i, BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node) {
        CollisionJNI.btGimQuantizedBvhNodeArray_resize__SWIG_0(this.swigCPtr, this, i, BT_QUANTIZED_BVH_NODE.getCPtr(bt_quantized_bvh_node), bt_quantized_bvh_node);
    }

    public void resize(int i) {
        CollisionJNI.btGimQuantizedBvhNodeArray_resize__SWIG_1(this.swigCPtr, this, i);
    }

    public BT_QUANTIZED_BVH_NODE expandNonInitializing() {
        return new BT_QUANTIZED_BVH_NODE(CollisionJNI.btGimQuantizedBvhNodeArray_expandNonInitializing(this.swigCPtr, this), false);
    }

    public BT_QUANTIZED_BVH_NODE expand(BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node) {
        return new BT_QUANTIZED_BVH_NODE(CollisionJNI.btGimQuantizedBvhNodeArray_expand__SWIG_0(this.swigCPtr, this, BT_QUANTIZED_BVH_NODE.getCPtr(bt_quantized_bvh_node), bt_quantized_bvh_node), false);
    }

    public BT_QUANTIZED_BVH_NODE expand() {
        return new BT_QUANTIZED_BVH_NODE(CollisionJNI.btGimQuantizedBvhNodeArray_expand__SWIG_1(this.swigCPtr, this), false);
    }

    public void push_back(BT_QUANTIZED_BVH_NODE bt_quantized_bvh_node) {
        CollisionJNI.btGimQuantizedBvhNodeArray_push_back(this.swigCPtr, this, BT_QUANTIZED_BVH_NODE.getCPtr(bt_quantized_bvh_node), bt_quantized_bvh_node);
    }

    public int capacity() {
        return CollisionJNI.btGimQuantizedBvhNodeArray_capacity(this.swigCPtr, this);
    }

    public void reserve(int i) {
        CollisionJNI.btGimQuantizedBvhNodeArray_reserve(this.swigCPtr, this, i);
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
                    CollisionJNI.delete_btGimQuantizedBvhNodeArray_less(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public less() {
            this(CollisionJNI.new_btGimQuantizedBvhNodeArray_less(), true);
        }
    }

    public void swap(int i, int i2) {
        CollisionJNI.btGimQuantizedBvhNodeArray_swap(this.swigCPtr, this, i, i2);
    }

    public void removeAtIndex(int i) {
        CollisionJNI.btGimQuantizedBvhNodeArray_removeAtIndex(this.swigCPtr, this, i);
    }

    public void initializeFromBuffer(long j, int i, int i2) {
        CollisionJNI.btGimQuantizedBvhNodeArray_initializeFromBuffer(this.swigCPtr, this, j, i, i2);
    }

    public void copyFromArray(btGimQuantizedBvhNodeArray btgimquantizedbvhnodearray) {
        CollisionJNI.btGimQuantizedBvhNodeArray_copyFromArray(this.swigCPtr, this, getCPtr(btgimquantizedbvhnodearray), btgimquantizedbvhnodearray);
    }
}
