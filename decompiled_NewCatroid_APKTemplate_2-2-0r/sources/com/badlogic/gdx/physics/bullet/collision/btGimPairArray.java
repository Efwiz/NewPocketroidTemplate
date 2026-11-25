package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;

/* JADX INFO: loaded from: classes8.dex */
public class btGimPairArray extends BulletBase {
    private long swigCPtr;

    protected btGimPairArray(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btGimPairArray(long j, boolean z) {
        this("btGimPairArray", j, z);
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

    public static long getCPtr(btGimPairArray btgimpairarray) {
        if (btgimpairarray == null) {
            return 0L;
        }
        return btgimpairarray.swigCPtr;
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
                CollisionJNI.delete_btGimPairArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btGimPairArray operatorAssignment(btGimPairArray btgimpairarray) {
        return new btGimPairArray(CollisionJNI.btGimPairArray_operatorAssignment(this.swigCPtr, this, getCPtr(btgimpairarray), btgimpairarray), false);
    }

    public btGimPairArray() {
        this(CollisionJNI.new_btGimPairArray__SWIG_0(), true);
    }

    public btGimPairArray(btGimPairArray btgimpairarray) {
        this(CollisionJNI.new_btGimPairArray__SWIG_1(getCPtr(btgimpairarray), btgimpairarray), true);
    }

    public int size() {
        return CollisionJNI.btGimPairArray_size(this.swigCPtr, this);
    }

    public GIM_PAIR atConst(int i) {
        return new GIM_PAIR(CollisionJNI.btGimPairArray_atConst(this.swigCPtr, this, i), false);
    }

    public GIM_PAIR at(int i) {
        return new GIM_PAIR(CollisionJNI.btGimPairArray_at(this.swigCPtr, this, i), false);
    }

    public GIM_PAIR operatorSubscriptConst(int i) {
        return new GIM_PAIR(CollisionJNI.btGimPairArray_operatorSubscriptConst(this.swigCPtr, this, i), false);
    }

    public GIM_PAIR operatorSubscript(int i) {
        return new GIM_PAIR(CollisionJNI.btGimPairArray_operatorSubscript(this.swigCPtr, this, i), false);
    }

    public void clear() {
        CollisionJNI.btGimPairArray_clear(this.swigCPtr, this);
    }

    public void pop_back() {
        CollisionJNI.btGimPairArray_pop_back(this.swigCPtr, this);
    }

    public void resizeNoInitialize(int i) {
        CollisionJNI.btGimPairArray_resizeNoInitialize(this.swigCPtr, this, i);
    }

    public void resize(int i, GIM_PAIR gim_pair) {
        CollisionJNI.btGimPairArray_resize__SWIG_0(this.swigCPtr, this, i, GIM_PAIR.getCPtr(gim_pair), gim_pair);
    }

    public void resize(int i) {
        CollisionJNI.btGimPairArray_resize__SWIG_1(this.swigCPtr, this, i);
    }

    public GIM_PAIR expandNonInitializing() {
        return new GIM_PAIR(CollisionJNI.btGimPairArray_expandNonInitializing(this.swigCPtr, this), false);
    }

    public GIM_PAIR expand(GIM_PAIR gim_pair) {
        return new GIM_PAIR(CollisionJNI.btGimPairArray_expand__SWIG_0(this.swigCPtr, this, GIM_PAIR.getCPtr(gim_pair), gim_pair), false);
    }

    public GIM_PAIR expand() {
        return new GIM_PAIR(CollisionJNI.btGimPairArray_expand__SWIG_1(this.swigCPtr, this), false);
    }

    public void push_back(GIM_PAIR gim_pair) {
        CollisionJNI.btGimPairArray_push_back(this.swigCPtr, this, GIM_PAIR.getCPtr(gim_pair), gim_pair);
    }

    public int capacity() {
        return CollisionJNI.btGimPairArray_capacity(this.swigCPtr, this);
    }

    public void reserve(int i) {
        CollisionJNI.btGimPairArray_reserve(this.swigCPtr, this, i);
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
                    CollisionJNI.delete_btGimPairArray_less(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public less() {
            this(CollisionJNI.new_btGimPairArray_less(), true);
        }
    }

    public void swap(int i, int i2) {
        CollisionJNI.btGimPairArray_swap(this.swigCPtr, this, i, i2);
    }

    public void removeAtIndex(int i) {
        CollisionJNI.btGimPairArray_removeAtIndex(this.swigCPtr, this, i);
    }

    public void initializeFromBuffer(long j, int i, int i2) {
        CollisionJNI.btGimPairArray_initializeFromBuffer(this.swigCPtr, this, j, i, i2);
    }

    public void copyFromArray(btGimPairArray btgimpairarray) {
        CollisionJNI.btGimPairArray_copyFromArray(this.swigCPtr, this, getCPtr(btgimpairarray), btgimpairarray);
    }
}
