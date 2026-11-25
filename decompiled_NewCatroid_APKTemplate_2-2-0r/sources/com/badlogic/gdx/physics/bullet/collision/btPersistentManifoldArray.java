package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;

/* JADX INFO: loaded from: classes8.dex */
public class btPersistentManifoldArray extends BulletBase {
    private long swigCPtr;

    protected btPersistentManifoldArray(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btPersistentManifoldArray(long j, boolean z) {
        this("btPersistentManifoldArray", j, z);
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

    public static long getCPtr(btPersistentManifoldArray btpersistentmanifoldarray) {
        if (btpersistentmanifoldarray == null) {
            return 0L;
        }
        return btpersistentmanifoldarray.swigCPtr;
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
                CollisionJNI.delete_btPersistentManifoldArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btPersistentManifoldArray operatorAssignment(btPersistentManifoldArray btpersistentmanifoldarray) {
        return new btPersistentManifoldArray(CollisionJNI.btPersistentManifoldArray_operatorAssignment(this.swigCPtr, this, getCPtr(btpersistentmanifoldarray), btpersistentmanifoldarray), false);
    }

    public btPersistentManifoldArray() {
        this(CollisionJNI.new_btPersistentManifoldArray__SWIG_0(), true);
    }

    public btPersistentManifoldArray(btPersistentManifoldArray btpersistentmanifoldarray) {
        this(CollisionJNI.new_btPersistentManifoldArray__SWIG_1(getCPtr(btpersistentmanifoldarray), btpersistentmanifoldarray), true);
    }

    public int size() {
        return CollisionJNI.btPersistentManifoldArray_size(this.swigCPtr, this);
    }

    public btPersistentManifold atConst(int i) {
        long jBtPersistentManifoldArray_atConst = CollisionJNI.btPersistentManifoldArray_atConst(this.swigCPtr, this, i);
        if (jBtPersistentManifoldArray_atConst == 0) {
            return null;
        }
        return new btPersistentManifold(jBtPersistentManifoldArray_atConst, false);
    }

    public SWIGTYPE_p_p_btPersistentManifold at(int i) {
        return new SWIGTYPE_p_p_btPersistentManifold(CollisionJNI.btPersistentManifoldArray_at(this.swigCPtr, this, i), false);
    }

    public btPersistentManifold operatorSubscriptConst(int i) {
        long jBtPersistentManifoldArray_operatorSubscriptConst = CollisionJNI.btPersistentManifoldArray_operatorSubscriptConst(this.swigCPtr, this, i);
        if (jBtPersistentManifoldArray_operatorSubscriptConst == 0) {
            return null;
        }
        return new btPersistentManifold(jBtPersistentManifoldArray_operatorSubscriptConst, false);
    }

    public SWIGTYPE_p_p_btPersistentManifold operatorSubscript(int i) {
        return new SWIGTYPE_p_p_btPersistentManifold(CollisionJNI.btPersistentManifoldArray_operatorSubscript(this.swigCPtr, this, i), false);
    }

    public void clear() {
        CollisionJNI.btPersistentManifoldArray_clear(this.swigCPtr, this);
    }

    public void pop_back() {
        CollisionJNI.btPersistentManifoldArray_pop_back(this.swigCPtr, this);
    }

    public void resizeNoInitialize(int i) {
        CollisionJNI.btPersistentManifoldArray_resizeNoInitialize(this.swigCPtr, this, i);
    }

    public void resize(int i, btPersistentManifold btpersistentmanifold) {
        CollisionJNI.btPersistentManifoldArray_resize__SWIG_0(this.swigCPtr, this, i, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public void resize(int i) {
        CollisionJNI.btPersistentManifoldArray_resize__SWIG_1(this.swigCPtr, this, i);
    }

    public SWIGTYPE_p_p_btPersistentManifold expandNonInitializing() {
        return new SWIGTYPE_p_p_btPersistentManifold(CollisionJNI.btPersistentManifoldArray_expandNonInitializing(this.swigCPtr, this), false);
    }

    public SWIGTYPE_p_p_btPersistentManifold expand(btPersistentManifold btpersistentmanifold) {
        return new SWIGTYPE_p_p_btPersistentManifold(CollisionJNI.btPersistentManifoldArray_expand__SWIG_0(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold), false);
    }

    public SWIGTYPE_p_p_btPersistentManifold expand() {
        return new SWIGTYPE_p_p_btPersistentManifold(CollisionJNI.btPersistentManifoldArray_expand__SWIG_1(this.swigCPtr, this), false);
    }

    public void push_back(btPersistentManifold btpersistentmanifold) {
        CollisionJNI.btPersistentManifoldArray_push_back(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public int capacity() {
        return CollisionJNI.btPersistentManifoldArray_capacity(this.swigCPtr, this);
    }

    public void reserve(int i) {
        CollisionJNI.btPersistentManifoldArray_reserve(this.swigCPtr, this, i);
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
                    CollisionJNI.delete_btPersistentManifoldArray_less(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public boolean operatorFunctionCall(btPersistentManifold btpersistentmanifold, btPersistentManifold btpersistentmanifold2) {
            return CollisionJNI.btPersistentManifoldArray_less_operatorFunctionCall(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold, btPersistentManifold.getCPtr(btpersistentmanifold2), btpersistentmanifold2);
        }

        public less() {
            this(CollisionJNI.new_btPersistentManifoldArray_less(), true);
        }
    }

    public void swap(int i, int i2) {
        CollisionJNI.btPersistentManifoldArray_swap(this.swigCPtr, this, i, i2);
    }

    public int findBinarySearch(btPersistentManifold btpersistentmanifold) {
        return CollisionJNI.btPersistentManifoldArray_findBinarySearch(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public int findLinearSearch(btPersistentManifold btpersistentmanifold) {
        return CollisionJNI.btPersistentManifoldArray_findLinearSearch(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public int findLinearSearch2(btPersistentManifold btpersistentmanifold) {
        return CollisionJNI.btPersistentManifoldArray_findLinearSearch2(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public void removeAtIndex(int i) {
        CollisionJNI.btPersistentManifoldArray_removeAtIndex(this.swigCPtr, this, i);
    }

    public void remove(btPersistentManifold btpersistentmanifold) {
        CollisionJNI.btPersistentManifoldArray_remove(this.swigCPtr, this, btPersistentManifold.getCPtr(btpersistentmanifold), btpersistentmanifold);
    }

    public void initializeFromBuffer(long j, int i, int i2) {
        CollisionJNI.btPersistentManifoldArray_initializeFromBuffer(this.swigCPtr, this, j, i, i2);
    }

    public void copyFromArray(btPersistentManifoldArray btpersistentmanifoldarray) {
        CollisionJNI.btPersistentManifoldArray_copyFromArray(this.swigCPtr, this, getCPtr(btpersistentmanifoldarray), btpersistentmanifoldarray);
    }
}
