package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;

/* JADX INFO: loaded from: classes8.dex */
public class btGimBvhTreeNodeArray extends BulletBase {
    private long swigCPtr;

    protected btGimBvhTreeNodeArray(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGimBvhTreeNodeArray(long j, boolean z) {
        this("btGimBvhTreeNodeArray", j, z);
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

    public static long getCPtr(btGimBvhTreeNodeArray btgimbvhtreenodearray) {
        if (btgimbvhtreenodearray == null) {
            return 0L;
        }
        return btgimbvhtreenodearray.swigCPtr;
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
                CollisionJNI.delete_btGimBvhTreeNodeArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGimBvhTreeNodeArray operatorAssignment(btGimBvhTreeNodeArray btgimbvhtreenodearray) {
        return new btGimBvhTreeNodeArray(CollisionJNI.btGimBvhTreeNodeArray_operatorAssignment(this.swigCPtr, this, getCPtr(btgimbvhtreenodearray), btgimbvhtreenodearray), false);
    }

    public btGimBvhTreeNodeArray() {
        this(CollisionJNI.new_btGimBvhTreeNodeArray__SWIG_0(), true);
    }

    public btGimBvhTreeNodeArray(btGimBvhTreeNodeArray btgimbvhtreenodearray) {
        this(CollisionJNI.new_btGimBvhTreeNodeArray__SWIG_1(getCPtr(btgimbvhtreenodearray), btgimbvhtreenodearray), true);
    }

    public int size() {
        return CollisionJNI.btGimBvhTreeNodeArray_size(this.swigCPtr, this);
    }

    public GIM_BVH_TREE_NODE atConst(int i) {
        return new GIM_BVH_TREE_NODE(CollisionJNI.btGimBvhTreeNodeArray_atConst(this.swigCPtr, this, i), false);
    }

    public GIM_BVH_TREE_NODE at(int i) {
        return new GIM_BVH_TREE_NODE(CollisionJNI.btGimBvhTreeNodeArray_at(this.swigCPtr, this, i), false);
    }

    public GIM_BVH_TREE_NODE operatorSubscriptConst(int i) {
        return new GIM_BVH_TREE_NODE(CollisionJNI.btGimBvhTreeNodeArray_operatorSubscriptConst(this.swigCPtr, this, i), false);
    }

    public GIM_BVH_TREE_NODE operatorSubscript(int i) {
        return new GIM_BVH_TREE_NODE(CollisionJNI.btGimBvhTreeNodeArray_operatorSubscript(this.swigCPtr, this, i), false);
    }

    public void clear() {
        CollisionJNI.btGimBvhTreeNodeArray_clear(this.swigCPtr, this);
    }

    public void pop_back() {
        CollisionJNI.btGimBvhTreeNodeArray_pop_back(this.swigCPtr, this);
    }

    public void resizeNoInitialize(int i) {
        CollisionJNI.btGimBvhTreeNodeArray_resizeNoInitialize(this.swigCPtr, this, i);
    }

    public void resize(int i, GIM_BVH_TREE_NODE gim_bvh_tree_node) {
        CollisionJNI.btGimBvhTreeNodeArray_resize__SWIG_0(this.swigCPtr, this, i, GIM_BVH_TREE_NODE.getCPtr(gim_bvh_tree_node), gim_bvh_tree_node);
    }

    public void resize(int i) {
        CollisionJNI.btGimBvhTreeNodeArray_resize__SWIG_1(this.swigCPtr, this, i);
    }

    public GIM_BVH_TREE_NODE expandNonInitializing() {
        return new GIM_BVH_TREE_NODE(CollisionJNI.btGimBvhTreeNodeArray_expandNonInitializing(this.swigCPtr, this), false);
    }

    public GIM_BVH_TREE_NODE expand(GIM_BVH_TREE_NODE gim_bvh_tree_node) {
        return new GIM_BVH_TREE_NODE(CollisionJNI.btGimBvhTreeNodeArray_expand__SWIG_0(this.swigCPtr, this, GIM_BVH_TREE_NODE.getCPtr(gim_bvh_tree_node), gim_bvh_tree_node), false);
    }

    public GIM_BVH_TREE_NODE expand() {
        return new GIM_BVH_TREE_NODE(CollisionJNI.btGimBvhTreeNodeArray_expand__SWIG_1(this.swigCPtr, this), false);
    }

    public void push_back(GIM_BVH_TREE_NODE gim_bvh_tree_node) {
        CollisionJNI.btGimBvhTreeNodeArray_push_back(this.swigCPtr, this, GIM_BVH_TREE_NODE.getCPtr(gim_bvh_tree_node), gim_bvh_tree_node);
    }

    public int capacity() {
        return CollisionJNI.btGimBvhTreeNodeArray_capacity(this.swigCPtr, this);
    }

    public void reserve(int i) {
        CollisionJNI.btGimBvhTreeNodeArray_reserve(this.swigCPtr, this, i);
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
                    CollisionJNI.delete_btGimBvhTreeNodeArray_less(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public less() {
            this(CollisionJNI.new_btGimBvhTreeNodeArray_less(), true);
        }
    }

    public void swap(int i, int i2) {
        CollisionJNI.btGimBvhTreeNodeArray_swap(this.swigCPtr, this, i, i2);
    }

    public void removeAtIndex(int i) {
        CollisionJNI.btGimBvhTreeNodeArray_removeAtIndex(this.swigCPtr, this, i);
    }

    public void initializeFromBuffer(long j, int i, int i2) {
        CollisionJNI.btGimBvhTreeNodeArray_initializeFromBuffer(this.swigCPtr, this, j, i, i2);
    }

    public void copyFromArray(btGimBvhTreeNodeArray btgimbvhtreenodearray) {
        CollisionJNI.btGimBvhTreeNodeArray_copyFromArray(this.swigCPtr, this, getCPtr(btgimbvhtreenodearray), btgimbvhtreenodearray);
    }
}
