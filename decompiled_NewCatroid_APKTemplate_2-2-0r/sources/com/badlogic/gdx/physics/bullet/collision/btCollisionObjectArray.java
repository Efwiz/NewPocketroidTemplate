package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;

/* JADX INFO: loaded from: classes8.dex */
public class btCollisionObjectArray extends BulletBase {
    private long swigCPtr;

    protected btCollisionObjectArray(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btCollisionObjectArray(long j, boolean z) {
        this("btCollisionObjectArray", j, z);
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

    public static long getCPtr(btCollisionObjectArray btcollisionobjectarray) {
        if (btcollisionobjectarray == null) {
            return 0L;
        }
        return btcollisionobjectarray.swigCPtr;
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
                CollisionJNI.delete_btCollisionObjectArray(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public btCollisionObjectArray operatorAssignment(btCollisionObjectArray btcollisionobjectarray) {
        return new btCollisionObjectArray(CollisionJNI.btCollisionObjectArray_operatorAssignment(this.swigCPtr, this, getCPtr(btcollisionobjectarray), btcollisionobjectarray), false);
    }

    public btCollisionObjectArray() {
        this(CollisionJNI.new_btCollisionObjectArray__SWIG_0(), true);
    }

    public btCollisionObjectArray(btCollisionObjectArray btcollisionobjectarray) {
        this(CollisionJNI.new_btCollisionObjectArray__SWIG_1(getCPtr(btcollisionobjectarray), btcollisionobjectarray), true);
    }

    public int size() {
        return CollisionJNI.btCollisionObjectArray_size(this.swigCPtr, this);
    }

    public btCollisionObject atConst(int i) {
        return btCollisionObject.getInstance(CollisionJNI.btCollisionObjectArray_atConst(this.swigCPtr, this, i), false);
    }

    public SWIGTYPE_p_p_btCollisionObject at(int i) {
        return new SWIGTYPE_p_p_btCollisionObject(CollisionJNI.btCollisionObjectArray_at(this.swigCPtr, this, i), false);
    }

    public btCollisionObject operatorSubscriptConst(int i) {
        return btCollisionObject.getInstance(CollisionJNI.btCollisionObjectArray_operatorSubscriptConst(this.swigCPtr, this, i), false);
    }

    public SWIGTYPE_p_p_btCollisionObject operatorSubscript(int i) {
        return new SWIGTYPE_p_p_btCollisionObject(CollisionJNI.btCollisionObjectArray_operatorSubscript(this.swigCPtr, this, i), false);
    }

    public void clear() {
        CollisionJNI.btCollisionObjectArray_clear(this.swigCPtr, this);
    }

    public void pop_back() {
        CollisionJNI.btCollisionObjectArray_pop_back(this.swigCPtr, this);
    }

    public void resizeNoInitialize(int i) {
        CollisionJNI.btCollisionObjectArray_resizeNoInitialize(this.swigCPtr, this, i);
    }

    public void resize(int i, btCollisionObject btcollisionobject) {
        CollisionJNI.btCollisionObjectArray_resize__SWIG_0(this.swigCPtr, this, i, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public void resize(int i) {
        CollisionJNI.btCollisionObjectArray_resize__SWIG_1(this.swigCPtr, this, i);
    }

    public SWIGTYPE_p_p_btCollisionObject expandNonInitializing() {
        return new SWIGTYPE_p_p_btCollisionObject(CollisionJNI.btCollisionObjectArray_expandNonInitializing(this.swigCPtr, this), false);
    }

    public SWIGTYPE_p_p_btCollisionObject expand(btCollisionObject btcollisionobject) {
        return new SWIGTYPE_p_p_btCollisionObject(CollisionJNI.btCollisionObjectArray_expand__SWIG_0(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject), false);
    }

    public SWIGTYPE_p_p_btCollisionObject expand() {
        return new SWIGTYPE_p_p_btCollisionObject(CollisionJNI.btCollisionObjectArray_expand__SWIG_1(this.swigCPtr, this), false);
    }

    public void push_back(btCollisionObject btcollisionobject) {
        CollisionJNI.btCollisionObjectArray_push_back(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public int capacity() {
        return CollisionJNI.btCollisionObjectArray_capacity(this.swigCPtr, this);
    }

    public void reserve(int i) {
        CollisionJNI.btCollisionObjectArray_reserve(this.swigCPtr, this, i);
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
                    CollisionJNI.delete_btCollisionObjectArray_less(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public boolean operatorFunctionCall(btCollisionObject btcollisionobject, btCollisionObject btcollisionobject2) {
            return CollisionJNI.btCollisionObjectArray_less_operatorFunctionCall(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject, btCollisionObject.getCPtr(btcollisionobject2), btcollisionobject2);
        }

        public less() {
            this(CollisionJNI.new_btCollisionObjectArray_less(), true);
        }
    }

    public void swap(int i, int i2) {
        CollisionJNI.btCollisionObjectArray_swap(this.swigCPtr, this, i, i2);
    }

    public int findBinarySearch(btCollisionObject btcollisionobject) {
        return CollisionJNI.btCollisionObjectArray_findBinarySearch(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public int findLinearSearch(btCollisionObject btcollisionobject) {
        return CollisionJNI.btCollisionObjectArray_findLinearSearch(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public int findLinearSearch2(btCollisionObject btcollisionobject) {
        return CollisionJNI.btCollisionObjectArray_findLinearSearch2(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public void removeAtIndex(int i) {
        CollisionJNI.btCollisionObjectArray_removeAtIndex(this.swigCPtr, this, i);
    }

    public void remove(btCollisionObject btcollisionobject) {
        CollisionJNI.btCollisionObjectArray_remove(this.swigCPtr, this, btCollisionObject.getCPtr(btcollisionobject), btcollisionobject);
    }

    public void initializeFromBuffer(long j, int i, int i2) {
        CollisionJNI.btCollisionObjectArray_initializeFromBuffer(this.swigCPtr, this, j, i, i2);
    }

    public void copyFromArray(btCollisionObjectArray btcollisionobjectarray) {
        CollisionJNI.btCollisionObjectArray_copyFromArray(this.swigCPtr, this, getCPtr(btcollisionobjectarray), btcollisionobjectarray);
    }
}
